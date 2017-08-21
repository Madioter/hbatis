/**
 * @Title: SkipFilterHandler.java
 * @Package com.madiot.hbatis.filter.decorating
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.decorating;

import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SkipFilter;

/**
 * @ClassName: SkipFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class SkipFilterHandler extends BaseDecoratingFilterHandler {

    @Override
    public Filter getCurrentFilter(Filter filter) {
        return new SkipFilter(filter);
    }
}
