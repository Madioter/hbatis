/**
 * @Title: MappingException.java
 * @Package com.madiot.hbatis.resultset
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.resultset;

import com.madiot.hbatis.exceptions.PersistenceException;

/**
 * @ClassName: MappingException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class MappingException extends PersistenceException {

    private static final long serialVersionUID = 8935197089745865786L;

    public MappingException() {
        super();
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }
}
