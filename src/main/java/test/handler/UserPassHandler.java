/**
 * @Title: UserPassHandler.java
 * @Package com.igdata.app.manager.common.mybatis.plugin
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/7/19
 * @version
 */
package test.handler;

import com.madiot.hbatis.type.BaseTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;
import test.domain.UserPass;

import java.text.ParseException;

/**
 * @ClassName: UserPassHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/7/19
 */
public class UserPassHandler extends BaseTypeHandler<UserPass> {

    @Override
    public UserPass stringToObject(String express) throws ParseException {
        return new UserPass(express, true);
    }

    @Override
    public byte[] getByteArray(UserPass parameter) {
        return Bytes.toBytes(parameter.toString());
    }

    @Override
    public UserPass arrayToObject(byte[] bytes) {
        return new UserPass(Bytes.toString(bytes), false);
    }
}
