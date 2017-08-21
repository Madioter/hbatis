/**
 * @Title: ColumnComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.parameter.ColumnProxy;
import com.madiot.hbatis.executor.proxy.IExecutorProxy;
import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.executor.parameter.VersionProxy;
import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.type.ColumnType;
import com.madiot.hbatis.type.TypeException;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ColumnComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class ColumnComp extends FragmentComp {

    private ColumnType columnType;

    private ParamMapping value;

    private ParamMapping timestamp;

    private VersionType type;

    public ColumnComp(XNode node, Configuration configuration) {
        super(configuration);
        String family = node.getStringAttribute("family");
        String qualifier = node.getStringAttribute("qualifier");
        this.columnType = new ColumnType(family, qualifier);
        String value = node.getStringAttribute("value");
        if (StringUtils.isNotBlank(value)) {
            this.value = new ParamMapping(node.getStringAttribute("value"), configuration);
        }
        String version = node.getStringAttribute("timestamp");
        if (StringUtils.isNotBlank(version)) {
            this.timestamp = new ParamMapping(version, Long.class.getName(), configuration);
        }
        String versionType = node.getStringAttribute("type");
        if (StringUtils.isNotBlank(versionType)) {
            this.type = VersionType.valueOf(versionType.toUpperCase());
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.COLUMN;
    }

    public void addColumn(IExecutorProxy executorProxy, Object parameter) {
        ParamProxy paramProxy = new ParamProxy(this.value, parameter);

        VersionProxy version = null;
        if (this.timestamp != null) {
            version = new VersionProxy(this.timestamp, this.type, parameter);
        }

        try {
            executorProxy.addElement(new ColumnProxy(paramProxy, version, columnType));
        } catch (Exception e) {
            throw new TypeException("Error setting non null for parameter #" + columnType.toString() + " . " +
                    "Try setting a different configuration property. " +
                    "Cause: " + e, e);
        }
    }

    public enum VersionType {
        BEFORE, CURRENT, LASTEST;
    }
}
