/**
 * @Title: Fragment.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.proxy.DeleteProxy;
import com.madiot.hbatis.executor.proxy.GetProxy;
import com.madiot.hbatis.executor.proxy.PutProxy;
import com.madiot.hbatis.executor.proxy.ScanProxy;
import com.madiot.hbatis.logging.Log;
import com.madiot.hbatis.logging.LogFactory;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;

/**
 * @ClassName: Fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public abstract class FragmentComp {

    protected Configuration configuration;

    protected static Log log;

    public FragmentComp(Configuration configuration) {
        this.configuration = configuration;
        if (log == null) {
            this.log = LogFactory.getLog(configuration.getLogImpl());
        }
    }

    public static FragmentComp newInstance(XNode node, Configuration configuration) {
        FragmentTypeEnum nodeName = FragmentTypeEnum.valueOf(node.getName().toUpperCase());
        switch (nodeName) {
            case SELECTKEY:
                return new SelectKeyComp(node, configuration);
            case SET:
                return new SetComp(node, configuration);
            case COLUMN:
                return new ColumnComp(node, configuration);
            case FOREACH:
                return new ForEachComp(node, configuration);
            case WHERE:
                return new WhereComp(node, configuration);
            case VERSIONS:
                return new VersionsComp(node, configuration);
            case LIMIT:
                return new LimitComp(node, configuration);
            case FILTERS:
                return new FiltersComp(node, configuration);
            case FILTER:
                return new FilterComp(node, configuration);
            case COMPARATOR:
                return new ComparatorComp(node, configuration);
            case PARAM:
                return new ParamComp(node, configuration);
        }
        return null;
    }

    public abstract FragmentTypeEnum getType();

    public static enum FragmentTypeEnum {
        SELECTKEY, SET, COLUMN, IF, FOREACH, WHERE, VERSIONS, LIMIT, FILTERS, FILTER, COMPARATOR, PARAM;
    }
}
