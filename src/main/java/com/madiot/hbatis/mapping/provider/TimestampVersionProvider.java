/**
 * @Title: TimestampVersionProvider.java
 * @Package com.madiot.hbatis.mapping.provider
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/11
 * @version
 */
package com.madiot.hbatis.mapping.provider;

import com.madiot.hbatis.mapping.VersionProvider;

/**
 * @ClassName: TimestampVersionProvider
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/11
 */
public class TimestampVersionProvider implements VersionProvider {

    @Override
    public Long getNextVersion() {
        return System.currentTimeMillis();
    }
}
