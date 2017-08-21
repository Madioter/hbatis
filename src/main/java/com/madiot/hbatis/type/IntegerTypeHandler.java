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
public class IntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    public Integer stringToObject(String express) throws ParseException {
        return Integer.valueOf(express);
    }

    @Override
    public byte[] getByteArray(Integer parameter) {
        return Bytes.toBytes(parameter);
    }

    @Override
    public Integer arrayToObject(byte[] bytes) {
        return Bytes.toInt(bytes);
    }
}
