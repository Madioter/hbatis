/**
 * @Title: DeleteStatement.java
 * @Package com.madiot.hbatis.mapping.structure
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.mapping.structure;

import com.madiot.hbatis.executor.proxy.DeleteProxy;
import com.madiot.hbatis.mapping.structure.fragment.ColumnComp;
import com.madiot.hbatis.mapping.structure.fragment.ForEachComp;
import com.madiot.hbatis.mapping.structure.fragment.FragmentComp;
import com.madiot.hbatis.mapping.structure.fragment.SelectKeyComp;
import com.madiot.hbatis.mapping.structure.fragment.SetComp;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Delete;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DeleteStatement
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class DeleteStatement extends BoundStatement {

    private List<FragmentComp> fragmentComps = new ArrayList<>();

    private final XNode context;

    public DeleteStatement(XNode context, Configuration configuration) {
        super(configuration);
        this.context = context;
    }

    public List<Delete> buildDeletes(Object parameter) {
        List<DeleteProxy> deletes = new ArrayList<>();
        List<ColumnComp> globalColumns = new ArrayList<>();
        explainFragment(null, this.fragmentComps, parameter, globalColumns, deletes);
        List<Delete> deleteList = new ArrayList<>();
        for (DeleteProxy deleteProxy : deletes) {
            deleteList.add(deleteProxy.getOperation());
        }
        return deleteList;
    }

    public void explainFragment(DeleteProxy currentDelete, List<FragmentComp> currentFragments, Object parameter, List<ColumnComp> globalColumns, List<DeleteProxy> deletes) {
        List<ColumnComp> currentColumns = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(globalColumns)) {
            currentColumns.addAll(globalColumns);
        }
        for (FragmentComp comp : currentFragments) {
            if (comp.getType() == FragmentComp.FragmentTypeEnum.SELECTKEY) {
                if (currentDelete == null) {
                    currentDelete = ((SelectKeyComp) comp).getDeleteProxy(parameter);
                    deletes.add(currentDelete);
                    for (ColumnComp comp1 : currentColumns) {
                        comp1.addColumn(currentDelete, parameter);
                    }
                }
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.COLUMN) {
                if (currentDelete == null) {
                    currentColumns.add((ColumnComp) comp);
                } else {
                    ((ColumnComp) comp).addColumn(currentDelete, parameter);
                }
            } else if (comp.getType() == FragmentComp.FragmentTypeEnum.FOREACH) {
                for (Object item : ((ForEachComp) comp).getParameters(parameter)) {
                    explainFragment(currentDelete, ((ForEachComp) comp).getChildren(), item, currentColumns, deletes);
                }
            }
        }
    }

    @Override
    public void parse() {
        List<XNode> nodes = context.getChildren();
        for (XNode xNode : nodes) {
            fragmentComps.add(FragmentComp.newInstance(xNode, configuration));
        }
    }
}
