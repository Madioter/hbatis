/**
 * @Title: TimestampFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.FilterConfigException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.TimestampsFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TimestampFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class TimestampFilterHandler extends BaseExclusiveFilterHandler {
    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params)) {
            List<Long> timestampList = new ArrayList<>();
            for (ParamProxy item : params.values()) {
                Object dateItem = item.getValue(Date.class);
                if (dateItem instanceof Date) {
                    timestampList.add(((Date) dateItem).getTime());
                } else {
                    timestampList.add(NumberUtils.toLong(dateItem.toString()));
                }
            }
            return new TimestampsFilter(timestampList);
        }
        throw new FilterConfigException("PrefixFilter config error, cause: param(timestamp) must not be null");
    }
}
