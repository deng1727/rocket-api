package com.github.alenfive.rocketapi.datasource.factory;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.alenfive.rocketapi.entity.DBConfig;

import javax.sql.DataSource;

public abstract class DruidJdbcDriver extends IDataSourceDialectDriver {

    protected DataSource getDataSource(DBConfig config){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(config.getUrl());
        druidDataSource.setUsername(config.getUser());
        druidDataSource.setPassword(config.getPassword());
        druidDataSource.setDriverClassName("org.elasticsearch.xpack.sql.jdbc.EsDriver");
        druidDataSource.setDriver(null);
        druidDataSource.setBreakAfterAcquireFailure(true);
        druidDataSource.setConnectionErrorRetryAttempts(0);
        return druidDataSource;
    }

}
