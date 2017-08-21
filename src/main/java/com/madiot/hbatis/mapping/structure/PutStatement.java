/**
 * @Title: InsertStatement.java
 * @Package com.madiot.hbatis.mapping.structure
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure;

import com.madiot.hbatis.executor.proxy.PutProxy;
import com.madiot.hbatis.mapping.structure.fragment.ColumnComp;
import com.madiot.hbatis.mapping.structure.fragment.ForEachComp;
import com.madiot.hbatis.mapping.structure.fragment.FragmentComp;
import com.madiot.hbatis.mapping.structure.fragment.SelectKeyComp;
import com.madiot.hbatis.mapping.structure.fragment.SetComp;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Put;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: InsertStatement
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class PutStatement extends BoundStatement {

    private List<FragmentComp> fragmentComps = new ArrayList<>();

    private final XNode context;

    public PutStatement(XNode context, Configuration configuration) {
        super(configuration);
        this.context = context;
    }

    @Override
    public void parse() {
        List<XNode> nodes = context.getChildren();
        for (XNode xNode : nodes) {
            fragmentComps.add(FragmentComp.newInstance(xNode, configuration));
        }
    }

    public List<Put> buildPuts(Object parameter) {
        List<PutProxy> puts = new ArrayList<PutProxy>();
        List<ColumnComp> globalColumns = new ArrayList<>();
        explainFragment(null, this.fragmentComps, parameter, globalColumns, puts);
        List<Put> putList = new ArrayList<>();
        for (PutProxy putProxy : puts) {
            putList.add(putProxy.getOperation());
        }
        return putList;
    }

    public void explainFragment(PutProxy currentPut, List<FragmentComp> currentFragments, Object parameter, List<ColumnComp> globalColumns, List<PutProxy> puts) {
        List<ColumnComp> currentColumns = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(globalColumns)) {
            currentColumns.addAll(globalColumns);
        }
        for (FragmentComp comp : currentFragments) {
            if (comp.getType() == FragmentComp.FragmentTypeEnum.SELECTKEY) {
                if (currentPut == null) {
                    currentPut = ((SelectKeyComp) comp).getPutProxy(parameter);
                    puts.add(currentPut);
                    for (ColumnComp comp1 : currentColumns) {
                        comp1.addColumn(currentPut, parameter);
                    }
                }
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.COLUMN) {
                if (currentPut == null) {
                    currentColumns.add((ColumnComp) comp);
                } else {
                    ((ColumnComp) comp).addColumn(currentPut, parameter);
                }
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.SET) {
                explainFragment(currentPut, ((SetComp) comp).getChildren(), parameter, currentColumns, puts);
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.FOREACH) {
                for (Object item : ((ForEachComp) comp).getParameters(parameter)) {
                    explainFragment(currentPut, ((ForEachComp) comp).getChildren(), item, currentColumns, puts);
                }
            }
        }
    }
}
