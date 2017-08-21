/**
 *    Copyright 2009-2015 the original author or authors.
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

import com.madiot.hbatis.cache.CacheKey;
import com.madiot.hbatis.mapping.MappedStatement;
import com.madiot.hbatis.reflection.MetaObject;
import com.madiot.hbatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Clinton Begin
 */
public interface Executor {

    int update(MappedStatement ms, Object parameter);

    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, CacheKey cacheKey) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

    <E> List<E> queryScan(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

    List<BatchResult> flushStatements() throws SQLException;

    void commit(boolean required) throws SQLException;

    CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds);

    boolean isCached(MappedStatement ms, CacheKey key);

    void clearLocalCache();

    void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

    void close(boolean forceRollback);

    boolean isClosed();

    void setExecutorWrapper(Executor executor);

    int delete(MappedStatement ms, Object o);
}
