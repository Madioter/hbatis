/**
 * @Title: ParamComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @ClassName: ParamComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class ParamComp extends FragmentComp {

    private String key;

    private ParamMapping value;

    public ParamComp(XNode node, Configuration configuration) {
        super(configuration);
        this.key = node.getStringAttribute("key");
        String value = node.getStringAttribute("value");
        if (StringUtils.isNotBlank(value)) {
            this.value = new ParamMapping(value, configuration);
        }
    }

    public void put(Map<String, ParamProxy> params, Object parameter) {
        params.put(this.key, new ParamProxy<>(value, parameter));
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.PARAM;
    }
}
