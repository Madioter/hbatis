/**
 * @Title: SingleColumnValueExcludeFilterHandler.java
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
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueExcludeFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * @ClassName: SingleColumnValueExcludeFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class SingleColumnValueExcludeFilterHandler extends BaseComparisonFilterHandler {

    @Override
    public Filter getCurrentFilter(ColumnType columnType, CompareFilter.CompareOp compareFilterOp, Comparable comparator, Map<String, ParamProxy> params) {
        if (columnType == null || StringUtils.isBlank(columnType.getQualifier())) {
            throw new FilterConfigException("SingleColumnValueExcludeFilter config error, cause: column config family and qualifier can't be null");
        }
        if (compareFilterOp == null) {
            throw new FilterConfigException("SingleColumnValueExcludeFilter config error, cause: compareFilterOp config can't be null");
        }
        byte[] value = null;
        if (MapUtils.isNotEmpty(params) && params.containsKey("value")) {
            value = params.get("value").getByteArray(String.class);
        }
        if (value != null) {
            return new SingleColumnValueExcludeFilter(columnType.getFamilyBytes(), columnType.getQualifierBytes(), compareFilterOp, value);
        } else if (comparator instanceof ByteArrayComparable) {
            return new SingleColumnValueExcludeFilter(columnType.getFamilyBytes(), columnType.getQualifierBytes(), compareFilterOp,
                    (ByteArrayComparable) comparator);
        } else {
            throw new FilterConfigException("SingleColumnValueExcludeFilter config error, cause: comparator type must be ByteArrayComparable");
        }
    }
}
