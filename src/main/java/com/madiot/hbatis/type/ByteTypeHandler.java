/**
 * @Title: ByteTypeHandler.java
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
 * @ClassName: ByteTypeHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class ByteTypeHandler extends BaseTypeHandler<Byte> {

    @Override
    public Byte stringToObject(String express) throws ParseException {
        return express.getBytes()[0];
    }

    @Override
    public byte[] getByteArray(Byte parameter) {
        return Bytes.toBytes(parameter);
    }

    @Override
    public Byte arrayToObject(byte[] bytes) {
        return (byte) Bytes.toShort(bytes);
    }
}
