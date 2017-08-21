/**
 * @Title: RandomRowFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;

import java.util.Map;

/**
 * @ClassName: RandomRowFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class RandomRowFilterHandler extends BaseExclusiveFilterHandler {

    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("chance")) {
            Object chance = params.get("chance").getValue(Float.class);
            if (chance instanceof Float) {
                return new RandomRowFilter((Float) chance);
            } else {
                return new RandomRowFilter(NumberUtils.toFloat(chance.toString()));
            }
        }
        return new RandomRowFilter(1f);
    }
}
