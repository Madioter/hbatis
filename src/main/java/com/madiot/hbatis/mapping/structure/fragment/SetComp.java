/**
 * @Title: SetComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SetComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class SetComp extends FragmentComp {

    private List<FragmentComp> children = new ArrayList<>();

    public SetComp(XNode node, Configuration configuration) {
        super(configuration);
        List<XNode> childrenNode = node.getChildren();
        for (XNode temp : childrenNode) {
            children.add(FragmentComp.newInstance(temp, configuration));
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.SET;
    }

    public List<FragmentComp> getChildren() {
        return children;
    }
}
