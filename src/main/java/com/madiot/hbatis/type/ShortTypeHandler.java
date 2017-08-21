/**
 * @Title: ShortTypeHandler.java
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
 * @ClassName: ShortTypeHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class ShortTypeHandler extends BaseTypeHandler<Short> {
    @Override
    public Short stringToObject(String express) throws ParseException {
        return Short.valueOf(express);
    }

    @Override
    public byte[] getByteArray(Short parameter) {
        return Bytes.toBytes(parameter);
    }

    @Override
    public Short arrayToObject(byte[] bytes) {
        return Bytes.toShort(bytes);
    }
}
