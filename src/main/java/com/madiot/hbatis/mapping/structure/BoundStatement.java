/**
 * @Title: BoundStatement.java
 * @Package com.madiot.hbatis.mapping.structure
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure;

import com.madiot.hbatis.exceptions.StatementStructureException;
import com.madiot.hbatis.mapping.ExecuteCommandType;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;

/**
 * @ClassName: BoundStatement
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public abstract class BoundStatement {

    protected Configuration configuration;

    public BoundStatement(Configuration configuration) {
        this.configuration = configuration;
    }

    public static BoundStatement newInstance(ExecuteCommandType executeCommandType, XNode context, Configuration configuration) {
        BoundStatement statement = null;
        switch (executeCommandType) {
            case PUT:
                statement = new PutStatement(context, configuration);
                break;
            case DELETE:
                statement = new DeleteStatement(context, configuration);
                break;
            case SELECT:
                statement = new SelectStatement(context, configuration);
                break;
            default:
                throw new StatementStructureException("Statement type can't be parse, type:" + executeCommandType);
        }
        statement.parse();
        return statement;
    }

    public abstract void parse();

}
