/**
 * @Title: BaseConfigurationFactory.java
 * @Package com.madiot.hbatis.datasource.factory
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.datasource;

import com.madiot.hbatis.exceptions.ConnectionRefusedException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: BaseConfigurationFactory
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class BaseTableFactory implements TableFactory {

    private ConfigurationSet configurationSet;

    private Configuration getConfiguration() {
        Configuration conf = HBaseConfiguration.create();
        if (configurationSet != null) {
            Map<String, String> configSet = configurationSet.getConfigurations();
            if (configSet != null && !configSet.isEmpty()) {
                for (Map.Entry<String, String> entry : configSet.entrySet()) {
                    conf.set(entry.getKey(), entry.getValue());
                }
            }
        }
        return conf;
    }

    public ConfigurationSet getConfigurationSet() {
        return configurationSet;
    }

    public void setConfigurationSet(ConfigurationSet configurationSet) {
        this.configurationSet = configurationSet;
    }

    @Override
    public Table getTable(String tableName) {
        Configuration conf = getConfiguration();
        try {
            Connection connection = ConnectionFactory.createConnection(conf);
            return connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            throw new ConnectionRefusedException("hbase table connection refused, table:" + tableName +
                    ",course:" + e.getCause(), e);
        }
    }
}
