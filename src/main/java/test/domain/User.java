/**
 * @Title: User.java
 * @Package test.domain
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package test.domain;

import com.madiot.hbatis.mapping.Entity;

/**
 * @ClassName: User
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class User extends Entity {

    private UserPass userPass;

    private String userName;

    private Integer age;

    public User() {

    }

    public User(String userName, String userPass, Integer age) {
        this.userName = userName;
        this.age = age;
        setUserPass(userPass);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserPass getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = new UserPass(userPass, true);
    }

    public void setUserPass(UserPass userPass) {
        this.userPass = userPass;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public byte[] getRowKey() {
        return this.userName.getBytes();
    }
}
