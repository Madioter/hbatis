/**
 * @Title: BaseDecoratingFilterHandler.java
 * @Package com.madiot.hbatis.filter.decorating
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.decorating;

import com.madiot.hbatis.filter.BaseFilterHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.filter.Filter;

/**
 * @ClassName: BaseDecoratingFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public abstract class BaseDecoratingFilterHandler extends BaseFilterHandler {

    @Override
    public Filter getFilter(Object... param) {
        Filter filter = null;
        if (ArrayUtils.isNotEmpty(param)) {
            for (Object paramItem : param) {
                if (paramItem instanceof Filter) {
                    filter = (Filter) paramItem;
                }
            }
        }
        return getCurrentFilter(filter);
    }

    public abstract Filter getCurrentFilter(Filter filter);
}
