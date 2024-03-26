package com.github.alenfive.rocketapi.datasource.factory;

import com.github.alenfive.rocketapi.datasource.DataSourceDialect;
import com.github.alenfive.rocketapi.datasource.ESDataSource;
import com.github.alenfive.rocketapi.entity.DBConfig;
import org.springframework.stereotype.Component;

/**
 * SQL  构造器
 */
@Component
public class ESDriver extends DruidJdbcDriver {

    @Override
    public String getName() {
        return "Elasticsearch";
    }

    @Override
    public String getIcon() {
        return "rocketapi/images/ES.jpeg";
    }

    @Override
    public String getFormat() {
        return "jdbc:es://localhost:9200/";
    }

    @Override
    public DataSourceDialect factory(DBConfig config) throws Exception {
        return new ESDataSource(super.getDataSource(config));
    }
}
