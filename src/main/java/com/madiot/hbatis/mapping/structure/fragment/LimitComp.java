/**
 * @Title: LimitComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.executor.proxy.ScanProxy;
import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: LimitComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class LimitComp extends FragmentComp {

    private ParamMapping startRow;

    private ParamMapping stopRow;

    public LimitComp(XNode node, Configuration configuration) {
        super(configuration);
        String startRow = node.getStringAttribute("startRow");
        if (StringUtils.isNotBlank(startRow)) {
            this.startRow = new ParamMapping(startRow, configuration);
        }
        String stopRow = node.getStringAttribute("stopRow");
        if (StringUtils.isNotBlank(stopRow)) {
            this.stopRow = new ParamMapping(stopRow, configuration);
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.LIMIT;
    }

    public void addConditions(ScanProxy scanProxy, Object parameter) {
        scanProxy.setLimit(new ParamProxy(startRow, parameter).getByteArray(String.class),
                new ParamProxy(stopRow, parameter).getByteArray(String.class));
    }
}
