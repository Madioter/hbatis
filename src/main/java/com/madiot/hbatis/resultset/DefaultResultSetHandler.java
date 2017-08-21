/**
 *    Copyright 2009-2017 the original author or authors.
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
package com.madiot.hbatis.resultset;

import com.madiot.hbatis.mapping.Entity;
import com.madiot.hbatis.mapping.ResultMap;
import com.madiot.hbatis.mapping.ResultMapping;
import com.madiot.hbatis.reflection.MetaObject;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.type.ColumnType;
import com.madiot.hbatis.type.TypeHandler;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Clinton Begin
 * @author Eduardo Macarron
 * @author Iwao AVE!
 * @author Kazuki Shimizu
 */
public class DefaultResultSetHandler<E> implements ResultSetHandler<E> {

    private List<ResultMap> resultMaps;

    private Configuration configuration;

    public DefaultResultSetHandler(List<ResultMap> resultMaps, Configuration configuration) {
        this.resultMaps = resultMaps;
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> handleResult(Result result) {
        ResultSet resultSet = new ResultSet();
        byte[] rowKey = result.getRow();
        for (Cell cell : result.rawCells()) {
            ColumnType columnType = new ColumnType(new String(CellUtil.cloneFamily(cell)), new String(CellUtil.cloneQualifier(cell)));
            byte[] bytes = CellUtil.cloneValue(cell);
            resultSet.add(cell.getTimestamp(), columnType, bytes);
        }
        List<E> resultList = new ArrayList<>();
        Iterator<ResultList> iterator = resultSet.iterator();
        while (iterator.hasNext()) {
            resultList.add(buildInstance(iterator.next(), rowKey));
        }

        return resultList;
    }

    private <E> E buildInstance(ResultList resultItem, byte[] rowKey) {
        ResultMap resultMap = this.resultMaps.get(0);
        Iterator<ResultMapping> resultMappingIterator = resultMap.getResultMappings().iterator();
        MetaObject metaObject = null;
        E instance = null;
        try {
            instance = (E) resultMap.getType().newInstance();
            metaObject = configuration.newMetaObject(instance);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MappingException("class instance error, class type:" + resultMap.getType().getName());
        }
        while (resultMappingIterator.hasNext()) {
            ResultMapping resultMapping = resultMappingIterator.next();
            ColumnType columnType = resultMapping.getColumn();
            Class<?> setType = metaObject.getSetterType(resultMapping.getProperty());
            TypeHandler typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(setType);
            if (metaObject.hasSetter(resultMapping.getProperty()) && resultItem.get(columnType) != null) {
                metaObject.setValue(resultMapping.getProperty(), typeHandler.getResult(resultItem.get(columnType).value));
            }
        }

        for (ResultMapping resultMapping : resultMap.getRowKeyMappings()) {
            Class<?> setType = metaObject.getSetterType(resultMapping.getProperty());
            TypeHandler typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(setType);
            if (metaObject.hasSetter(resultMapping.getProperty())) {
                metaObject.setValue(resultMapping.getProperty(), typeHandler.getResult(rowKey));
            }
        }

        if (instance instanceof Entity) {
            ((Entity) instance).setVersion(resultItem.getTimestamp());
        }
        return instance;
    }

    private class ResultSet extends HashMap<Long, ResultList> {

        public void add(Long key, ColumnType columnType, byte[] value) {
            ResultList list;
            if (!super.containsKey(key)) {
                list = new ResultList(key);
                super.put(key, list);
            } else {
                list = super.get(key);
            }
            list.add(new ResultItem(columnType, value));
        }

        public Iterator<ResultList> iterator() {
            return super.values().iterator();
        }
    }

    private class ResultList extends ArrayList<ResultItem> {

        private long timestamp;

        ResultList(long timestamp) {
            this.timestamp = timestamp;
        }

        public ResultItem get(ColumnType columnType) {
            Iterator<ResultItem> itemIterator = super.iterator();
            while (itemIterator.hasNext()) {
                ResultItem item = itemIterator.next();
                if (item.isType(columnType)) {
                    return item;
                }
            }
            return null;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    private class ResultItem {

        ColumnType columnType;
        byte[] value;

        ResultItem(ColumnType columnType, byte[] value) {
            this.columnType = columnType;
            this.value = value;
        }

        public boolean isType(ColumnType columnType) {
            return this.columnType.equals(columnType);
        }
    }

}
