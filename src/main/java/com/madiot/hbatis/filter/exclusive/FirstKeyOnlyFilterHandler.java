/**
 * @Title: FirstKeyOnlyFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.BaseFilterHandler;
import com.madiot.hbatis.type.ColumnType;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;

import java.util.Map;

/**
 * @ClassName: FirstKeyOnlyFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class FirstKeyOnlyFilterHandler extends BaseExclusiveFilterHandler {
    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        return new FirstKeyOnlyFilter();

    }
}
