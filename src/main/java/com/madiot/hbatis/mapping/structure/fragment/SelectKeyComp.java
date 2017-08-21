/**
 * @Title: RowKeyComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.executor.proxy.DeleteProxy;
import com.madiot.hbatis.executor.proxy.GetProxy;
import com.madiot.hbatis.executor.proxy.PutProxy;
import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;

/**
 * @ClassName: RowKeyComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class SelectKeyComp extends FragmentComp {

    private ParamMapping value;

    private ParamMapping version;

    public SelectKeyComp(XNode node, Configuration configuration) {
        super(configuration);
        if (node.getStringAttribute("timestamp") != null) {
            this.version = new ParamMapping(node.getStringAttribute("timestamp"), configuration);
        }
        this.value = new ParamMapping(node.getStringAttribute("value"), configuration);
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.SELECTKEY;
    }

    public PutProxy getPutProxy(Object parameter) {
        Long timestamp = getTimeStamp(parameter);
        if (timestamp != null) {
            return new PutProxy(getRowKey(parameter), timestamp);
        } else {
            return new PutProxy(getRowKey(parameter));
        }
    }

    public DeleteProxy getDeleteProxy(Object parameter) {
        Long timestamp = getTimeStamp(parameter);
        if (timestamp != null) {
            return new DeleteProxy(getRowKey(parameter), timestamp);
        } else {
            return new DeleteProxy(getRowKey(parameter));
        }
    }

    public GetProxy getGetProxy(Object parameter) {
        return new GetProxy(getRowKey(parameter));
    }

    public byte[] getRowKey(Object parameter) {
        ParamProxy paramProxy = new ParamProxy(this.value, parameter);
        return paramProxy.getByteArray(String.class);
    }

    public Long getTimeStamp(Object parameter) {
        if (this.version != null) {
            ParamProxy<Long> versionProxy = new ParamProxy<>(this.version, parameter);
            Long data = versionProxy.getValue(Long.class);
            if (data != null) {
                return data;
            }
        }
        if (configuration.getVersionProvider() != null) {
            return configuration.getVersionProvider().getNextVersion();
        }
        return null;
    }
}
