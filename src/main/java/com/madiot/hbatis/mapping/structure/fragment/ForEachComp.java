/**
 * @Title: ListComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.reflection.MetaObject;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.session.defaults.DefaultExecuteSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: ListComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class ForEachComp extends FragmentComp {

    private List<FragmentComp> children = new ArrayList<>();

    private String index;
    private String item;
    private String collection;

    public ForEachComp(XNode node, Configuration configuration) {
        super(configuration);
        this.index = node.getStringAttribute("index");
        this.item = node.getStringAttribute("item");
        this.collection = node.getStringAttribute("collection");
        List<XNode> xNodes = node.getChildren();
        for (XNode xNode : xNodes) {
            children.add(FragmentComp.newInstance(xNode, configuration));
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.FOREACH;
    }

    public List<FragmentComp> getChildren() {
        return children;
    }

    public List<Object> getParameters(Object parameter) {
        MetaObject metaObject = configuration.newMetaObject(parameter);
        Object value = metaObject.getValue(this.collection);
        List<Object> result = new ArrayList<>();
        if (value instanceof Collection) {
            int index = 0;
            for (Object temp : (Collection) value) {
                DefaultExecuteSession.StrictMap map = new DefaultExecuteSession.StrictMap();
                map.put(this.item, temp);
                map.put(this.index, index);
                map.putAll((DefaultExecuteSession.StrictMap) parameter);
                result.add(map);
                index++;
            }
        }
        return result;
    }
}
