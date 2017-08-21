/**
 * @Title: ConnectionRefusedException.java
 * @Package com.madiot.hbatis.exceptions
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.exceptions;

/**
 * @ClassName: ConnectionRefusedException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class ConnectionRefusedException extends PersistenceException {

    public ConnectionRefusedException() {
    }

    public ConnectionRefusedException(String message) {
        super(message);
    }

    public ConnectionRefusedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionRefusedException(Throwable cause) {
        super(cause);
    }
}
