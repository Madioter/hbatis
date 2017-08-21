/**
 *    Copyright 2009-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.madiot.hbatis.executor;


import com.madiot.hbatis.executor.proxy.ScanProxy;
import com.madiot.hbatis.mapping.Entity;
import com.madiot.hbatis.mapping.MappedStatement;
import com.madiot.hbatis.mapping.structure.DeleteStatement;
import com.madiot.hbatis.mapping.structure.PutStatement;
import com.madiot.hbatis.mapping.structure.SelectStatement;
import com.madiot.hbatis.reflection.ArrayUtil;
import com.madiot.hbatis.resultset.DefaultResultSetHandler;
import com.madiot.hbatis.resultset.ResultSetHandler;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.session.RowBounds;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Clinton Begin
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration) {
        super(configuration);
    }

    @Override
    public int doUpdate(MappedStatement ms, Object parameter) {
        Table table = tableFactory.getTable(ms.getTableName());
        List<Put> puts;
        try {
            PutStatement putStatement = (PutStatement) ms.getBoundStatement();
            puts = putStatement.buildPuts(parameter);
            if (!puts.isEmpty()) {
                table.put(puts);
                setResultToParameter(puts, parameter);
                return puts.size();
            }
        } catch (IOException e) {
            throw new ExecutorException("put options execute error, course:" + e.getCause(), e);
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                throw new ExecutorException("table closed error, course:" + e.getCause(), e);
            }
        }
        return 0;
    }

    private void setResultToParameter(List<Put> puts, Object parameter) {
        for (Map.Entry<String, Object> entry : ((Map<String, Object>) parameter).entrySet()) {
            if (entry.getValue() instanceof List) {
                for (Object item : (List) entry.getValue()) {
                    if (item instanceof Entity) {
                        for (Put put : puts) {
                            if (ArrayUtil.equals(put.getRow(), ((Entity) item).getRowKey())) {
                                ((Entity) item).setVersion(put.getTimeStamp());
                            }
                        }
                    }
                }
            } else if (entry.getValue() instanceof Entity) {
                for (Put put : puts) {
                    if (ArrayUtil.equals(put.getRow(), ((Entity) entry.getValue()).getRowKey())) {
                        ((Entity) entry.getValue()).setVersion(put.getTimeStamp());
                    }
                }
            }
        }
    }

    @Override
    public int doDelete(MappedStatement ms, Object parameter) {
        Table table = tableFactory.getTable(ms.getTableName());
        List<Delete> deletes;
        try {
            DeleteStatement putStatement = (DeleteStatement) ms.getBoundStatement();
            deletes = putStatement.buildDeletes(parameter);
            if (!deletes.isEmpty()) {
                int total = deletes.size();
                table.delete(deletes);
                if (deletes.size() > 0) {
                    throw new ExecutorException("failed delete error list:" + deletes.toString());
                }
                return total;
            }
        } catch (IOException e) {
            throw new ExecutorException("delete options execute error, course:" + e.getCause(), e);
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                throw new ExecutorException("table closed error, course:" + e.getCause(), e);
            }
        }
        return 0;
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        SelectStatement selectStatement = (SelectStatement) ms.getBoundStatement();
        ResultSetHandler<E> resultSetHandler = new DefaultResultSetHandler<>(ms.getResultMaps(), configuration);
        if (selectStatement.containsRowKey()) {
            Table table = tableFactory.getTable(ms.getTableName());
            Get get = selectStatement.buildGet(parameter);
            if (get == null) {
                return new ArrayList<>();
            } else {
                try {
                    Result result = table.get(get);
                    return resultSetHandler.handleResult(result);
                } catch (IOException e) {
                    throw new ExecutorException("get options execute error, cause:" + e.getCause(), e);
                }
            }
        } else {
            return doQueryScan(ms, parameter, rowBounds, resultSetHandler);
        }
    }

    @Override
    protected <E> List<E> doQueryScan(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultSetHandler<E> resultSetHandler) throws SQLException {
        Table table = tableFactory.getTable(ms.getTableName());
        SelectStatement selectStatement = (SelectStatement) ms.getBoundStatement();
        ScanProxy scanProxy = selectStatement.buildScan(parameter);
        ResultScanner resultScanner = null;
        try {
            resultScanner = table.getScanner(scanProxy.getOperation());
            List<E> resultList = new ArrayList<>();
            for (Result result : resultScanner) {
                resultList.addAll(resultSetHandler.handleResult(result));
            }
            return resultList;
        } catch (IOException e) {
            throw new ExecutorException("scan options execute error, cause:" + e.getCause(), e);
        } finally {
            if (resultScanner != null) {
                resultScanner.close();
            }
        }
    }

    @Override
    public List<BatchResult> doFlushStatements(boolean isRollback) throws SQLException {
        return Collections.emptyList();
    }
}
