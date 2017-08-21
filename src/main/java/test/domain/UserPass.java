/**
 * @Title: UserPass.java
 * @Package test.domain
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/9
 * @version
 */
package test.domain;

import java.security.MessageDigest;

/**
 * @ClassName: UserPass
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/9
 */
public class UserPass {

    private String userPass;

    public UserPass() {

    }

    public UserPass(String userPass, boolean isPlainText) {
        if (isPlainText) {
            this.userPass = getMD5(userPass);
        } else {
            this.userPass = userPass;
        }
    }

    public void setUserPass(String plaintext) {
        this.userPass = getMD5(plaintext);
    }

    @Override
    public String toString() {
        return userPass;
    }

    public static String getMD5(String code) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = code.getBytes();
            byte[] results = messageDigest.digest(bytes);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte result : results) {
                // 将byte数组转化为16进制字符存入stringbuilder中
                stringBuilder.append(String.format("%02x", result));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
