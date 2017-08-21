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
 * @ClassName: LongTypeHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public Long stringToObject(String express) throws ParseException {
        return Long.valueOf(express);
    }

    @Override
    public byte[] getByteArray(Long parameter) {
        return Bytes.toBytes(parameter);
    }

    @Override
    public Long arrayToObject(byte[] bytes) {
        return Bytes.toLong(bytes);
    }
}
