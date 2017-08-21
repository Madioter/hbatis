/**
 * @Title: Test.java
 * @Package PACKAGE_NAME
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */

import com.madiot.hbatis.io.Resources;
import com.madiot.hbatis.session.ExecuteSession;
import com.madiot.hbatis.session.ExecuteSessionFactory;
import com.madiot.hbatis.session.ExecuteSessionFactoryBuilder;
import test.domain.User;
import test.mapper.UserMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Test
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class Test {

    public static void main(String[] args) throws IOException {
        /*XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(new FileInputStream(Resources.getResourceAsFile("hbatis-config.xml")));
        xmlConfigBuilder.parse();
        System.out.print(1);*/
        annotatedSubject();
    }

    public static void annotatedSubject() throws IOException {
        ExecuteSessionFactory executeSessionFactory = new ExecuteSessionFactoryBuilder().build(new FileInputStream(Resources.getResourceAsFile("hbatis-config.xml")));
        final ExecuteSession executeSession = executeSessionFactory.openSession();
        try {
            final UserMapper mapper = executeSession.getMapper(UserMapper.class);
            List<User> userList = new ArrayList<>();
            userList.add(new User("aaa", "123", 12));
            userList.add(new User("bbb", "456", 13));
            System.out.print(mapper.selectBaseInfo("aaa"));
            //System.out.print(mapper.putBatch(userList));
            //System.out.print(mapper.delete("aaa", 1502688829792L));

            /*List<String> users = new ArrayList<>();
            users.add("aaa");
            users.add("bbb");
            System.out.print(mapper.deleteBatch(users));*/
        } finally {
            executeSession.close();
        }
    }
}
