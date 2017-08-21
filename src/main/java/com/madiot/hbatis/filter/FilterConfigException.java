/**
 * @Title: FilterConfigException.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter;

import com.madiot.hbatis.exceptions.PersistenceException;

/**
 * @ClassName: FilterConfigException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class FilterConfigException extends PersistenceException {

    private static final long serialVersionUID = 7642570221267566591L;

    public FilterConfigException() {
        super();
    }

    public FilterConfigException(String message) {
        super(message);
    }

    public FilterConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterConfigException(Throwable cause) {
        super(cause);
    }
}
