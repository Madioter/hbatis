/**
 * @Title: ValueFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter.comparison;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.FilterConfigException;
import com.madiot.hbatis.type.ColumnType;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.ValueFilter;

import java.util.Map;

/**
 * @ClassName: ValueFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class ValueFilterHandler extends BaseComparisonFilterHandler {
    @Override
    public Filter getCurrentFilter(ColumnType columnType, CompareFilter.CompareOp compareFilterOp, Comparable comparator, Map<String, ParamProxy> params) {
        if (compareFilterOp == null) {
            throw new FilterConfigException("ValueFilter config error, cause: compareFilterOp can't be null");
        }
        if (comparator instanceof ByteArrayComparable) {
            return new ValueFilter(compareFilterOp, (ByteArrayComparable) comparator);
        } else {
            throw new FilterConfigException("ValueFilter config error, cause: comparator type must be ByteArrayComparable");
        }
    }
}
