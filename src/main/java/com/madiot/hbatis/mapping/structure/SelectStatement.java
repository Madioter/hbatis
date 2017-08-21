/**
 * @Title: SelectStatement.java
 * @Package com.madiot.hbatis.mapping.structure
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.mapping.structure;

import com.madiot.hbatis.executor.proxy.GetProxy;
import com.madiot.hbatis.executor.proxy.ScanProxy;
import com.madiot.hbatis.mapping.structure.fragment.ColumnComp;
import com.madiot.hbatis.mapping.structure.fragment.FilterComp;
import com.madiot.hbatis.mapping.structure.fragment.FiltersComp;
import com.madiot.hbatis.mapping.structure.fragment.FragmentComp;
import com.madiot.hbatis.mapping.structure.fragment.LimitComp;
import com.madiot.hbatis.mapping.structure.fragment.SelectKeyComp;
import com.madiot.hbatis.mapping.structure.fragment.VersionsComp;
import com.madiot.hbatis.mapping.structure.fragment.WhereComp;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SelectStatement
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class SelectStatement extends BoundStatement {

    private XNode context;

    private Integer caching;

    private Integer batch;

    private List<FragmentComp> fragmentComps = new ArrayList<>();

    public SelectStatement(XNode context, Configuration configuration) {
        super(configuration);
        this.caching = context.getIntAttribute("caching");
        this.batch = context.getIntAttribute("batch");
        this.context = context;
    }

    @Override
    public void parse() {
        List<XNode> nodes = context.getChildren();
        for (XNode xNode : nodes) {
            fragmentComps.add(FragmentComp.newInstance(xNode, configuration));
        }
    }

    public Get buildGet(Object parameter) {
        List<GetProxy> gets = new ArrayList<>();
        List<ColumnComp> globalColumns = new ArrayList<>();
        explainFragment(null, this.fragmentComps, parameter, globalColumns, gets);
        for (GetProxy getProxy : gets) {
            return getProxy.getOperation();
        }
        return null;
    }

    public void explainFragment(GetProxy currentGet, List<FragmentComp> currentFragments, Object parameter, List<ColumnComp> globalColumns, List<GetProxy> gets) {
        List<ColumnComp> currentColumns = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(globalColumns)) {
            currentColumns.addAll(globalColumns);
        }
        for (FragmentComp comp : currentFragments) {
            if (comp.getType() == FragmentComp.FragmentTypeEnum.SELECTKEY) {
                if (currentGet == null) {
                    currentGet = ((SelectKeyComp) comp).getGetProxy(parameter);
                    gets.add(currentGet);
                    for (ColumnComp comp1 : currentColumns) {
                        comp1.addColumn(currentGet, parameter);
                    }
                }
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.COLUMN) {
                if (currentGet == null) {
                    currentColumns.add((ColumnComp) comp);
                } else {
                    ((ColumnComp) comp).addColumn(currentGet, parameter);
                }
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.VERSIONS) {
                ((VersionsComp) comp).addConditions(currentGet, parameter);
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.WHERE) {
                explainFragment(currentGet, ((WhereComp) comp).getChildren(), parameter, currentColumns, gets);
            }
        }
    }

    public boolean containsRowKey() {
        for (FragmentComp comp : fragmentComps) {
            if (comp instanceof SelectKeyComp) {
                return true;
            }
        }
        return false;
    }

    public ScanProxy buildScan(Object parameter) {
        ScanProxy scan = new ScanProxy(this.batch, this.caching);
        explainFragment(scan, this.fragmentComps, parameter);
        return scan;
    }

    private void explainFragment(ScanProxy scan, List<FragmentComp> fragmentComps, Object parameter) {
        for (FragmentComp comp : fragmentComps) {
            if (comp.getType() == FragmentComp.FragmentTypeEnum.COLUMN) {
                ((ColumnComp) comp).addColumn(scan, parameter);
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.LIMIT) {
                ((LimitComp) comp).addConditions(scan, parameter);
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.WHERE) {
                explainFragment(scan, ((WhereComp) comp).getChildren(), parameter);
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.FILTERS) {
                ((FiltersComp) comp).setFilters(scan, parameter);
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.FILTER) {
                Filter filter = ((FilterComp) comp).getFilter(parameter);
                scan.addFilter(filter);
            }
        }
    }
}
