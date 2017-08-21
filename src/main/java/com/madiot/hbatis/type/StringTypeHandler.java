/**
 * @Title: StringTypeHandler.java
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
 * @ClassName: StringTypeHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public String stringToObject(String express) throws ParseException {
        return express;
    }

    @Override
    public byte[] getByteArray(String parameter) {
        return Bytes.toBytes(parameter);
    }

    @Override
    public String arrayToObject(byte[] bytes) {
        return Bytes.toString(bytes);
    }
}
