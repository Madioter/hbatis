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
package com.madiot.hbatis.type;

import org.apache.hadoop.hbase.util.Bytes;

import java.text.ParseException;

/**
 * @author Clinton Begin
 */
public class EnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public E stringToObject(String express) throws ParseException {
        return Enum.valueOf(type, express);
    }

    @Override
    public byte[] getByteArray(E parameter) {
        return Bytes.toBytes(parameter.name());
    }

    @Override
    public E arrayToObject(byte[] bytes) {
        return Enum.valueOf(type, Bytes.toString(bytes));
    }
}