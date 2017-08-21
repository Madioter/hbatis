/**
 * @Title: BooleanTypeHandler.java
 * @Package com.madiot.hbatis.type
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.type;

import org.apache.hadoop.hbase.util.Bytes;

import java.text.ParseException;

/**
 * @ClassName: BooleanTypeHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public Boolean stringToObject(String express) throws ParseException {
        return Boolean.parseBoolean(express);
    }

    @Override
    public byte[] getByteArray(Boolean parameter) {
        return Bytes.toBytes(parameter);
    }

    @Override
    public Boolean arrayToObject(byte[] bytes) {
        return Bytes.toBoolean(bytes);
    }
}
