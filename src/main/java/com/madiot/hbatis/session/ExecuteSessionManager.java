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
package com.madiot.hbatis.session;

import com.madiot.hbatis.executor.BatchResult;
import com.madiot.hbatis.reflection.ExceptionUtil;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Larry Meadors
 */
public class ExecuteSessionManager implements ExecuteSessionFactory, ExecuteSession {

    private final ExecuteSessionFactory ExecuteSessionFactory;
    private final ExecuteSession sqlSessionProxy;

    private final ThreadLocal<ExecuteSession> localSqlSession = new ThreadLocal<ExecuteSession>();

    private ExecuteSessionManager(ExecuteSessionFactory ExecuteSessionFactory) {
        this.ExecuteSessionFactory = ExecuteSessionFactory;
        this.sqlSessionProxy = (ExecuteSession) Proxy.newProxyInstance(
                ExecuteSessionFactory.class.getClassLoader(),
                new Class[]{ExecuteSession.class},
                new SqlSessionInterceptor());
    }

    public static ExecuteSessionManager newInstance(Reader reader) {
        return new ExecuteSessionManager(new ExecuteSessionFactoryBuilder().build(reader, null, null));
    }

    public static ExecuteSessionManager newInstance(Reader reader, String environment) {
        return new ExecuteSessionManager(new ExecuteSessionFactoryBuilder().build(reader, environment, null));
    }

    public static ExecuteSessionManager newInstance(Reader reader, Properties properties) {
        return new ExecuteSessionManager(new ExecuteSessionFactoryBuilder().build(reader, null, properties));
    }

    public static ExecuteSessionManager newInstance(InputStream inputStream) {
        return new ExecuteSessionManager(new ExecuteSessionFactoryBuilder().build(inputStream, null));
    }

    public static ExecuteSessionManager newInstance(InputStream inputStream, Properties properties) {
        return new ExecuteSessionManager(new ExecuteSessionFactoryBuilder().build(inputStream, properties));
    }

    public static ExecuteSessionManager newInstance(ExecuteSessionFactory ExecuteSessionFactory) {
        return new ExecuteSessionManager(ExecuteSessionFactory);
    }

    public void startManagedSession() {
        this.localSqlSession.set(openSession());
    }

    public void startManagedSession(boolean autoCommit) {
        this.localSqlSession.set(openSession(autoCommit));
    }

    public void startManagedSession(Connection connection) {
        this.localSqlSession.set(openSession(connection));
    }

    public void startManagedSession(ExecutorType execType) {
        this.localSqlSession.set(openSession(execType));
    }

    public void startManagedSession(ExecutorType execType, boolean autoCommit) {
        this.localSqlSession.set(openSession(execType, autoCommit));
    }

    public void startManagedSession(ExecutorType execType, Connection connection) {
        this.localSqlSession.set(openSession(execType, connection));
    }

    public boolean isManagedSessionStarted() {
        return this.localSqlSession.get() != null;
    }

    @Override
    public ExecuteSession openSession(boolean autoCommit) {
        return ExecuteSessionFactory.openSession(autoCommit);
    }

    @Override
    public ExecuteSession openSession(Connection connection) {
        return ExecuteSessionFactory.openSession(connection);
    }

    @Override
    public ExecuteSession openSession() {
        return ExecuteSessionFactory.openSession();
    }

    @Override
    public ExecuteSession openSession(ExecutorType execType) {
        return ExecuteSessionFactory.openSession(execType);
    }

    @Override
    public ExecuteSession openSession(ExecutorType execType, boolean autoCommit) {
        return ExecuteSessionFactory.openSession(execType, autoCommit);
    }

    @Override
    public ExecuteSession openSession(ExecutorType execType, Connection connection) {
        return ExecuteSessionFactory.openSession(execType, connection);
    }

    @Override
    public Configuration getConfiguration() {
        return ExecuteSessionFactory.getConfiguration();
    }

    @Override
    public <T> T selectOne(String statement) {
        return sqlSessionProxy.<T>selectOne(statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return sqlSessionProxy.<T>selectOne(statement, parameter);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return sqlSessionProxy.<K, V>selectMap(statement, mapKey);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return sqlSessionProxy.<K, V>selectMap(statement, parameter, mapKey);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return sqlSessionProxy.<K, V>selectMap(statement, parameter, mapKey, rowBounds);
    }

    @Override
    public <T> List<T> selectScan(String statement) {
        return sqlSessionProxy.selectScan(statement);
    }

    @Override
    public <T> List<T> selectScan(String statement, Object parameter) {
        return sqlSessionProxy.selectScan(statement, parameter);
    }

    @Override
    public <T> List<T> selectScan(String statement, Object parameter, RowBounds rowBounds) {
        return sqlSessionProxy.selectScan(statement, parameter, rowBounds);
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return sqlSessionProxy.<E>selectList(statement);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return sqlSessionProxy.<E>selectList(statement, parameter);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return sqlSessionProxy.<E>selectList(statement, parameter, rowBounds);
    }

    @Override
    public void select(String statement) {
        sqlSessionProxy.select(statement);
    }

    @Override
    public void select(String statement, Object parameter) {
        sqlSessionProxy.select(statement, parameter);
    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds) {
        sqlSessionProxy.select(statement, parameter, rowBounds);
    }

    @Override
    public int insert(String statement) {
        return sqlSessionProxy.insert(statement);
    }

    @Override
    public int insert(String statement, Object parameter) {
        return sqlSessionProxy.insert(statement, parameter);
    }

    @Override
    public int update(String statement) {
        return sqlSessionProxy.update(statement);
    }

    @Override
    public int update(String statement, Object parameter) {
        return sqlSessionProxy.update(statement, parameter);
    }

    @Override
    public int delete(String statement) {
        return sqlSessionProxy.delete(statement);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return sqlSessionProxy.delete(statement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }

    @Override
    public void clearCache() {
        final ExecuteSession ExecuteSession = localSqlSession.get();
        if (ExecuteSession == null) {
            throw new ExecuteSessionException("Error:  Cannot clear the cache.  No managed session is started.");
        }
        ExecuteSession.clearCache();
    }

    @Override
    public void commit() {
        final ExecuteSession ExecuteSession = localSqlSession.get();
        if (ExecuteSession == null) {
            throw new ExecuteSessionException("Error:  Cannot commit.  No managed session is started.");
        }
        ExecuteSession.commit();
    }

    @Override
    public void commit(boolean force) {
        final ExecuteSession ExecuteSession = localSqlSession.get();
        if (ExecuteSession == null) {
            throw new ExecuteSessionException("Error:  Cannot commit.  No managed session is started.");
        }
        ExecuteSession.commit(force);
    }

    @Override
    public List<BatchResult> flushStatements() {
        final ExecuteSession ExecuteSession = localSqlSession.get();
        if (ExecuteSession == null) {
            throw new ExecuteSessionException("Error:  Cannot rollback.  No managed session is started.");
        }
        return ExecuteSession.flushStatements();
    }

    @Override
    public void close() {
        final ExecuteSession ExecuteSession = localSqlSession.get();
        if (ExecuteSession == null) {
            throw new ExecuteSessionException("Error:  Cannot close.  No managed session is started.");
        }
        try {
            ExecuteSession.close();
        } finally {
            localSqlSession.set(null);
        }
    }

    private class SqlSessionInterceptor implements InvocationHandler {
        public SqlSessionInterceptor() {
            // Prevent Synthetic Access
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final ExecuteSession ExecuteSession = ExecuteSessionManager.this.localSqlSession.get();
            if (ExecuteSession != null) {
                try {
                    return method.invoke(ExecuteSession, args);
                } catch (Throwable t) {
                    throw ExceptionUtil.unwrapThrowable(t);
                }
            } else {
                final ExecuteSession autoSqlSession = openSession();
                try {
                    final Object result = method.invoke(autoSqlSession, args);
                    autoSqlSession.commit();
                    return result;
                } catch (Throwable t) {
                    throw ExceptionUtil.unwrapThrowable(t);
                } finally {
                    autoSqlSession.close();
                }
            }
        }
    }

}
