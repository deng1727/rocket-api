package com.github.alenfive.rocketapi.core.factory;

import com.github.alenfive.rocketapi.core.impl.JdbcTemplateWrapper;
import com.github.alenfive.rocketapi.function.AbsIFunction;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SQL  构造器
 */
@Component
public class JdbcTemplateWrapperFactoryBean implements FactoryBean<JdbcTemplateWrapper> {

    @Override
    public JdbcTemplateWrapper getObject() throws Exception {

        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return JdbcTemplateWrapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
