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
package com.madiot.hbatis.mapping;

import com.madiot.hbatis.datasource.BaseTableFactory;

/**
 * @author Clinton Begin
 */
public final class Environment {
    private final String id;
    private final BaseTableFactory baseTableFactory;

    public Environment(String id, BaseTableFactory baseTableFactory) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter 'id' must not be null");
        }
        if (baseTableFactory == null) {
            throw new IllegalArgumentException("Parameter 'transactionFactory' must not be null");
        }
        this.id = id;
        this.baseTableFactory = baseTableFactory;
    }

    public static class Builder {
        private String id;
        private BaseTableFactory baseTableFactory;

        public Builder(String id) {
            this.id = id;
        }

        public String id() {
            return this.id;
        }

        public Environment build() {
            return new Environment(this.id, this.baseTableFactory);
        }

    }

    public String getId() {
        return this.id;
    }

    public BaseTableFactory getBaseTableFactory() {
        return baseTableFactory;
    }
}
