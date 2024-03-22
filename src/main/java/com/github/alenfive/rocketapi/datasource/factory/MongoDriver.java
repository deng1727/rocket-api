package com.github.alenfive.rocketapi.datasource.factory;

import com.github.alenfive.rocketapi.datasource.DataSourceDialect;
import com.github.alenfive.rocketapi.datasource.MongoDataSource;
import com.github.alenfive.rocketapi.entity.DBConfig;
import com.github.alenfive.rocketapi.utils.MongoDBUtils;
import org.springframework.stereotype.Component;

/**
 * mongodb  构造器
 */
@Component
public class MongoDriver extends IDataSourceDialectDriver {

    @Override
    public String getName() {
        return "MongoDB";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/MongoDB.png";
    }

    @Override
    public String getFormat() {
        return "mongodb://172.0.0.1:27017/test1";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) {
        return new MongoDataSource(MongoDBUtils.getMongoTemplate(config));
    }
}
