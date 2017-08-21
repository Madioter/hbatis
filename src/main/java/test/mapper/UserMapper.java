/**
 * @Title: XmlTestMapper.java
 * @Package test
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package test.mapper;

import com.madiot.hbatis.annotations.Param;
import test.domain.User;

import java.util.List;

/**
 * @ClassName: XmlTestMapper
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public interface UserMapper {

    public int put(User user);

    public int putBatch(List<User> userList);

    public int delete(@Param("rowkey") String rowkey, @Param("version") Long yesterday);

    public int deleteBatch(List<String> users);

    public List<User> getById(String rowKey);

    public List<User> selectBaseInfo(String row);
}
