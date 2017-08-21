/**
 * @Title: StatementStructureException.java
 * @Package com.madiot.hbatis.exceptions
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.exceptions;

/**
 * @ClassName: StatementStructureException
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class StatementStructureException extends PersistenceException {

    private static final long serialVersionUID = 7642570221267566595L;

    public StatementStructureException() {
        super();
    }

    public StatementStructureException(String message) {
        super(message);
    }

    public StatementStructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatementStructureException(Throwable cause) {
        super(cause);
    }

}