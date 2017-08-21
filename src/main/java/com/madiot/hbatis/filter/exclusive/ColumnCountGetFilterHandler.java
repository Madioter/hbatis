/**
 * @Title: ColumnCountGetFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.FilterConfigException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Map;

/**
 * @ClassName: ColumnCountGetFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class ColumnCountGetFilterHandler extends BaseExclusiveFilterHandler {
    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("pageSize")) {
            Object pageSize = params.get("pageSize").getValue(Integer.class);
            if (pageSize instanceof Integer) {
                return new ColumnCountGetFilter((Integer) pageSize);
            } else {
                return new ColumnCountGetFilter(NumberUtils.toInt(pageSize.toString()));
            }
        }
        throw new FilterConfigException("ColumnCountGetFilter config error, cause: param(pageSize) must not be null");
    }
}
