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

import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Clinton Begin
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public byte[] getByteArray(Date parameter) {
        return Bytes.toBytes(parameter.getTime());
    }

    @Override
    public Date arrayToObject(byte[] bytes) {
        return new Date(Bytes.toLong(bytes));
    }

    public Date stringToObject(String express) throws ParseException {
        return DateUtils.parseDate(express, new String[]{"yyyy-MM-dd HH:mm:ss"});
    }
}
