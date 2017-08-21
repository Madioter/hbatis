/**
 * @Title: ComparatorComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.comparator.ComparatorHandler;
import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ComparatorComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class ComparatorComp extends FragmentComp {

    private ParamMapping value;

    private String comparatorAlias;

    private String param;

    public ComparatorComp(XNode node, Configuration configuration) {
        super(configuration);
        this.comparatorAlias = node.getStringAttribute("type");
        String value = node.getStringAttribute("value");
        if (StringUtils.isNotBlank(value)) {
            this.value = new ParamMapping(value, configuration);
        }
        this.param = node.getStringAttribute("param");
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.COMPARATOR;
    }

    public Comparable getComparatorHandler(Object parameter) {
        ComparatorHandler comparatorHandler = configuration.getComparatorHandlerRegistry().getMappingFilterHandler(comparatorAlias);
        return comparatorHandler.getComparator(new ParamProxy(value, parameter), this.param);
    }
}
