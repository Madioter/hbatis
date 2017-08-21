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
package com.madiot.hbatis.session;

import com.madiot.hbatis.builder.xml.XMLConfigBuilder;
import com.madiot.hbatis.exceptions.ExceptionFactory;
import com.madiot.hbatis.executor.ErrorContext;
import com.madiot.hbatis.session.defaults.DefaultExecuteSessionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * Builds {@link ExecuteSession} instances.
 *
 * @author Clinton Begin
 */
public class ExecuteSessionFactoryBuilder {

    public ExecuteSessionFactory build(Reader reader) {
        return build(reader, null, null);
    }

    public ExecuteSessionFactory build(Reader reader, String environment) {
        return build(reader, environment, null);
    }

    public ExecuteSessionFactory build(Reader reader, Properties properties) {
        return build(reader, null, properties);
    }

    public ExecuteSessionFactory build(Reader reader, String environment, Properties properties) {
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, properties);
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    public ExecuteSessionFactory build(InputStream inputStream) {
        return build(inputStream, null);
    }

    public ExecuteSessionFactory build(InputStream inputStream, Properties properties) {
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, properties);
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                inputStream.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    public ExecuteSessionFactory build(Configuration config) {
        return new DefaultExecuteSessionFactory(config);
    }

}
