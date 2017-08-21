/**
 * @Title: FiltersComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.proxy.ScanProxy;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FiltersComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class FiltersComp extends FragmentComp {

    private List<FragmentComp> children = new ArrayList<>();

    private FilterList.Operator operator = FilterList.Operator.MUST_PASS_ALL;

    public FiltersComp(XNode node, Configuration configuration) {
        super(configuration);
        String operator = node.getStringAttribute("operator");
        if (StringUtils.isNotBlank(operator)) {
            this.operator = FilterList.Operator.valueOf(operator);
        }
        List<XNode> childrenNode = node.getChildren();
        for (XNode temp : childrenNode) {
            children.add(FragmentComp.newInstance(temp, configuration));
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.FILTERS;
    }

    public List<FragmentComp> getChildren() {
        return children;
    }

    public void setFilters(ScanProxy scan, Object parameter) {
        FilterList filterList = new FilterList(operator);
        for (FragmentComp comp : children) {
            if (comp instanceof FilterComp) {
                Filter filter = ((FilterComp) comp).getFilter(parameter);
                filterList.addFilter(filter);
            }
        }
        scan.addFilterList(filterList);
    }
}
