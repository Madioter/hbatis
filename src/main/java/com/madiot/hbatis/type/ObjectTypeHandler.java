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
package com.madiot.hbatis.type;

import org.apache.commons.lang.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;

/**
 * @author Clinton Begin
 */
public class ObjectTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public Object stringToObject(String express) throws ParseException {
        return express;
    }

    @Override
    public byte[] getByteArray(Object parameter) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(parameter);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("object:" + parameter.getClass().getName() + "can not be serialized, cause:" + e.getCause(), e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object arrayToObject(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException("bytes de-serialization failure:" + e.getCause(), e);
        }
    }
}
