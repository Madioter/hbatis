/**
 * @Title: FilterComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.FilterHandler;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.type.ColumnType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FilterComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class FilterComp extends FragmentComp {

    private String typeAlias;

    private CompareFilter.CompareOp compareFilterOp;

    private ColumnType columnType;

    private List<FragmentComp> children = new ArrayList<>();

    public FilterComp(XNode node, Configuration configuration) {
        super(configuration);
        this.typeAlias = node.getStringAttribute("type");
        String compareOp = node.getStringAttribute("compareOp");
        if (StringUtils.isNotBlank(compareOp)) {
            this.compareFilterOp = CompareFilter.CompareOp.valueOf(compareOp);
        }
        String family = node.getStringAttribute("family");
        if (StringUtils.isNotBlank(family)) {
            this.columnType = new ColumnType(family, node.getStringAttribute("qualifier"));
        }
        List<XNode> childrenNode = node.getChildren();
        for (XNode temp : childrenNode) {
            children.add(FragmentComp.newInstance(temp, configuration));
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.FILTER;
    }

    public List<FragmentComp> getChildren() {
        return children;
    }

    public Filter getFilter(Object parameter) {
        FilterHandler filterHandler = configuration.getFilerHandlerRegistry().getFilterHandler(typeAlias);
        return filterHandler.getFilter(this.columnType, this.compareFilterOp, getComparator(parameter), getParams(parameter), getChildFilter(parameter));
    }

    private Filter getChildFilter(Object parameter) {
        if (CollectionUtils.isNotEmpty(children)) {
            for (FragmentComp comp : children) {
                if (comp instanceof FilterComp) {
                    return ((FilterComp) comp).getFilter(parameter);
                }
            }
        }
        return null;
    }

    private Map<String, ParamProxy> getParams(Object parameter) {
        Map<String, ParamProxy> params = new HashMap<>();
        if (CollectionUtils.isNotEmpty(children)) {
            for (FragmentComp comp : children) {
                if (comp instanceof ParamComp) {
                    ((ParamComp) comp).put(params, parameter);
                }
            }
        }
        if (!params.isEmpty()) {
            return params;
        }
        return null;
    }

    public Comparable getComparator(Object parameter) {
        if (CollectionUtils.isNotEmpty(children)) {
            for (FragmentComp comp : children) {
                if (comp instanceof ComparatorComp) {
                    return ((ComparatorComp) comp).getComparatorHandler(parameter);
                }
            }
        }
        return null;
    }
}
