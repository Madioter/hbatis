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
package com.madiot.hbatis.session.defaults;


import com.madiot.hbatis.executor.Executor;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.session.ExecuteSession;
import com.madiot.hbatis.session.ExecuteSessionFactory;
import com.madiot.hbatis.session.ExecutorType;
import com.madiot.hbatis.session.TransactionIsolationLevel;

import java.sql.Connection;

/**
 * @author Clinton Begin
 */
public class DefaultExecuteSessionFactory implements ExecuteSessionFactory {

    private final Configuration configuration;

    public DefaultExecuteSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public ExecuteSession openSession() {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), false);
    }

    @Override
    public ExecuteSession openSession(boolean autoCommit) {
        return openSessionFromDataSource(configuration.getDefaultExecutorType(), autoCommit);
    }

    @Override
    public ExecuteSession openSession(ExecutorType execType) {
        return openSessionFromDataSource(execType, false);
    }

    @Override
    public ExecuteSession openSession(ExecutorType execType, boolean autoCommit) {
        return openSessionFromDataSource(execType, autoCommit);
    }

    @Override
    public ExecuteSession openSession(Connection connection) {
        return openSessionFromConnection(configuration.getDefaultExecutorType(), connection);
    }

    @Override
    public ExecuteSession openSession(ExecutorType execType, Connection connection) {
        return openSessionFromConnection(execType, connection);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private ExecuteSession openSessionFromDataSource(ExecutorType execType, boolean autoCommit) {
        final Executor executor = configuration.newExecutor(execType);
        return new DefaultExecuteSession(configuration, executor, autoCommit);
    }

    private ExecuteSession openSessionFromConnection(ExecutorType execType, Connection connection) {
    /*try {
      boolean autoCommit;
      try {
        autoCommit = connection.getAutoCommit();
      } catch (SQLException e) {
        // Failover to true, as most poor drivers
        // or databases won't support transactions
        autoCommit = true;
      }      
      final Environment environment = configuration.getEnvironment();
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
      final Transaction tx = transactionFactory.newTransaction(connection);
      final Executor executor = configuration.newExecutor(tx, execType);
      return new DefaultExecuteSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }*/
        return null;
    }

  /*private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
    if (environment == null || environment.getTransactionFactory() == null) {
      return new ManagedTransactionFactory();
    }
    return environment.getTransactionFactory();
  }

  private void closeTransaction(Transaction tx) {
    if (tx != null) {
      try {
        tx.close();
      } catch (SQLException ignore) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }*/

}
