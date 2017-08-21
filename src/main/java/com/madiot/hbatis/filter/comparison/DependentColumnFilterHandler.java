/**
 * @Title: DependentColumnFilterHandler.java
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
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.DependentColumnFilter;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Map;

/**
 * @ClassName: DependentColumnFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class DependentColumnFilterHandler extends BaseComparisonFilterHandler {

    @Override
    public Filter getCurrentFilter(ColumnType columnType, CompareFilter.CompareOp compareFilterOp, Comparable comparator, Map<String, ParamProxy> params) {
        if (columnType == null || StringUtils.isBlank(columnType.getQualifier())) {
            throw new FilterConfigException("DependentColumnFilter config error, cause: column config family and qualifier can't be null");
        }
        Boolean dropDependentColumn = null;
        if (MapUtils.isNotEmpty(params)) {
            Object value = params.get("dropDependentColumn").getValue(Boolean.class);
            if (value instanceof Boolean) {
                dropDependentColumn = (Boolean) value;
            } else {
                dropDependentColumn = BooleanUtils.toBoolean(value.toString());
            }
        }
        if (dropDependentColumn == null) {
            return new DependentColumnFilter(columnType.getFamilyBytes(), columnType.getQualifierBytes());
        } else if (compareFilterOp == null) {
            return new DependentColumnFilter(columnType.getFamilyBytes(), columnType.getQualifierBytes(), dropDependentColumn);
        } else if (compareFilterOp != null && comparator instanceof ByteArrayComparable) {
            return new DependentColumnFilter(columnType.getFamilyBytes(), columnType.getQualifierBytes(), dropDependentColumn,
                    compareFilterOp, (ByteArrayComparable) comparator);
        } else {
            throw new FilterConfigException("DependentColumnFilter config error, cause: compareFilterOp config not null but comparator type is not ByteArrayComparable");
        }
    }
}
