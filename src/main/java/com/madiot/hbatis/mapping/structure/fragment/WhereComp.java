/**
 * @Title: WhereComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: WhereComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class WhereComp extends FragmentComp {

    private List<FragmentComp> children = new ArrayList<>();

    public WhereComp(XNode node, Configuration configuration) {
        super(configuration);
        List<XNode> childrenNode = node.getChildren();
        for (XNode temp : childrenNode) {
            children.add(FragmentComp.newInstance(temp, configuration));
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.WHERE;
    }

    public List<FragmentComp> getChildren() {
        return children;
    }
}
