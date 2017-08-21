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
package com.madiot.hbatis.mapping;

import com.madiot.hbatis.cache.Cache;
import com.madiot.hbatis.logging.Log;
import com.madiot.hbatis.logging.LogFactory;
import com.madiot.hbatis.mapping.structure.BoundStatement;
import com.madiot.hbatis.session.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Clinton Begin
 */
public final class MappedStatement {

    private String resource;
    private Configuration configuration;
    private ParameterMap parameterMap;
    private String id;
    private String tableName;
    private Integer fetchSize;
    private Integer timeout;
    private Cache cache;
    private ExecuteCommandType executeCommandType;
    private List<ResultMap> resultMaps;
    private boolean flushCacheRequired;
    private boolean useCache;
    private boolean resultOrdered;
    private String[] keyProperties;
    private String[] keyColumns;
    private boolean hasNestedResultMaps;
    private String databaseId;
    private Log statementLog;
    private String[] resultSets;
    private BoundStatement boundStatement;

    MappedStatement() {
        // constructor disabled
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, ExecuteCommandType executeCommandType, String tableName, BoundStatement boundStatement) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.tableName = tableName;
            mappedStatement.executeCommandType = executeCommandType;
            mappedStatement.parameterMap = new ParameterMap.Builder(configuration, "defaultParameterMap", null, new ArrayList<ParameterMapping>()).build();
            mappedStatement.resultMaps = new ArrayList<>();
            mappedStatement.boundStatement = boundStatement;
            String logId = id;
            /*if (configuration.getLogPrefix() != null) {
                logId = configuration.getLogPrefix() + id;
            }*/
            mappedStatement.statementLog = LogFactory.getLog(logId);
            /*mappedStatement.lang = configuration.getDefaultScriptingLanguageInstance();*/
        }

        public Builder resource(String resource) {
            mappedStatement.resource = resource;
            return this;
        }

        public String id() {
            return mappedStatement.id;
        }

        public Builder parameterMap(ParameterMap parameterMap) {
            mappedStatement.parameterMap = parameterMap;
            return this;
        }

        public Builder resultMaps(List<ResultMap> resultMaps) {
            mappedStatement.resultMaps = resultMaps;
            for (ResultMap resultMap : resultMaps) {
                mappedStatement.hasNestedResultMaps = mappedStatement.hasNestedResultMaps || resultMap.hasNestedResultMaps();
            }
            return this;
        }

        public Builder fetchSize(Integer fetchSize) {
            mappedStatement.fetchSize = fetchSize;
            return this;
        }

        public Builder timeout(Integer timeout) {
            mappedStatement.timeout = timeout;
            return this;
        }

        public Builder cache(Cache cache) {
            mappedStatement.cache = cache;
            return this;
        }

        public Builder flushCacheRequired(boolean flushCacheRequired) {
            mappedStatement.flushCacheRequired = flushCacheRequired;
            return this;
        }

        public Builder useCache(boolean useCache) {
            mappedStatement.useCache = useCache;
            return this;
        }

        public Builder resultOrdered(boolean resultOrdered) {
            mappedStatement.resultOrdered = resultOrdered;
            return this;
        }

        /*public Builder keyGenerator(KeyGenerator keyGenerator) {
            mappedStatement.keyGenerator = keyGenerator;
            return this;
        }*/

        public Builder keyProperty(String keyProperty) {
            mappedStatement.keyProperties = delimitedStringToArray(keyProperty);
            return this;
        }

        public Builder keyColumn(String keyColumn) {
            mappedStatement.keyColumns = delimitedStringToArray(keyColumn);
            return this;
        }

        public Builder databaseId(String databaseId) {
            mappedStatement.databaseId = databaseId;
            return this;
        }

        /*public Builder lang(LanguageDriver driver) {
            mappedStatement.lang = driver;
            return this;
        }*/

        public Builder resultSets(String resultSet) {
            mappedStatement.resultSets = delimitedStringToArray(resultSet);
            return this;
        }

        /** @deprecated Use {@link #resultSets} */
        @Deprecated
        public Builder resulSets(String resultSet) {
            mappedStatement.resultSets = delimitedStringToArray(resultSet);
            return this;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            mappedStatement.resultMaps = Collections.unmodifiableList(mappedStatement.resultMaps);
            return mappedStatement;
        }
    }

    /*public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }*/

    public ParameterMap getParameterMap() {
        return parameterMap;
    }

    public ExecuteCommandType getExecuteCommandType() {
        return executeCommandType;
    }

    public String getResource() {
        return resource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getId() {
        return id;
    }

    public boolean hasNestedResultMaps() {
        return hasNestedResultMaps;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public Integer getTimeout() {
        return timeout;
    }

    /*public SqlSource getSqlSource() {
        return sqlSource;
    } */

    public List<ResultMap> getResultMaps() {
        return resultMaps;
    }

    public Cache getCache() {
        return cache;
    }

    public boolean isFlushCacheRequired() {
        return flushCacheRequired;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public boolean isResultOrdered() {
        return resultOrdered;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public String[] getKeyProperties() {
        return keyProperties;
    }

    public String[] getKeyColumns() {
        return keyColumns;
    }

    public Log getStatementLog() {
        return statementLog;
    }

    public String[] getResultSets() {
        return resultSets;
    }

    public String getTableName() {
        return tableName;
    }

    public BoundStatement getBoundStatement() {
        return boundStatement;
    }

    /** @deprecated Use {@link #getResultSets()} */
    @Deprecated
    public String[] getResulSets() {
        return resultSets;
    }

    private static String[] delimitedStringToArray(String in) {
        if (in == null || in.trim().length() == 0) {
            return null;
        } else {
            return in.split(",");
        }
    }

}
