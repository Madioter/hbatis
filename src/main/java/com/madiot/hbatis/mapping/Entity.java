/**
 * @Title: Entity.java
 * @Package com.madiot.hbatis.mapping
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.mapping;

/**
 * @ClassName: Entity
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public abstract class Entity {

    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public abstract byte[] getRowKey();
}
