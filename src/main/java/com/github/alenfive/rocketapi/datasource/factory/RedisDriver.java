package com.github.alenfive.rocketapi.datasource.factory;

import com.github.alenfive.rocketapi.datasource.DataSourceDialect;
import com.github.alenfive.rocketapi.datasource.RedisDataSource;
import com.github.alenfive.rocketapi.entity.DBConfig;
import com.github.alenfive.rocketapi.utils.RedisDBUtils;
import org.springframework.stereotype.Component;

/**
 * redis  构造器
 */
@Component
public class RedisDriver extends IDataSourceDialectDriver {

    @Override
    public String getName() {
        return "Redis";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/Redis.jpeg";
    }

    @Override
    public String getFormat() {
        return "redis://localhost:6379/1";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) {
        return new RedisDataSource(RedisDBUtils.getRedisTemplate(config));
    }
}
