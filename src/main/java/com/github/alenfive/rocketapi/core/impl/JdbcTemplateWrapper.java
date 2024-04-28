package com.github.alenfive.rocketapi.core.impl;

import com.github.alenfive.rocketapi.function.AbsIFunction;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SQL  构造器
 */
public class JdbcTemplateWrapper extends AbsIFunction implements JdbcOperations {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateWrapper(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T execute(ConnectionCallback<T> connectionCallback) throws DataAccessException {
        return jdbcTemplate.execute(connectionCallback);
    }

    @Override
    public <T> T execute(StatementCallback<T> statementCallback) throws DataAccessException {
        return jdbcTemplate.execute(statementCallback);
    }

    @Override
    public void execute(String s) throws DataAccessException {
            jdbcTemplate.execute(s);
    }

    @Override
    public <T> T query(String s, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(s,resultSetExtractor);
    }

    @Override
    public void query(String s, RowCallbackHandler rowCallbackHandler) throws DataAccessException {
        jdbcTemplate.query(s,rowCallbackHandler);
    }

    @Override
    public <T> List<T> query(String s, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(s,rowMapper);
    }

    @Override
    public <T> T queryForObject(String s, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,rowMapper);
    }

    @Override
    public <T> T queryForObject(String s, Class<T> aClass) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,aClass);
    }

    @Override
    public Map<String, Object> queryForMap(String s) throws DataAccessException {
        return jdbcTemplate.queryForMap(s);
    }

    @Override
    public <T> List<T> queryForList(String s, Class<T> aClass) throws DataAccessException {
        return jdbcTemplate.queryForList(s,aClass);
    }

    @Override
    public List<Map<String, Object>> queryForList(String s) throws DataAccessException {
        return jdbcTemplate.queryForList(s);
    }

    @Override
    public SqlRowSet queryForRowSet(String s) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(s);
    }

    @Override
    public int update(String s) throws DataAccessException {
        return jdbcTemplate.update(s);
    }

    @Override
    public int[] batchUpdate(String... strings) throws DataAccessException {
        return jdbcTemplate.batchUpdate(strings);
    }

    @Override
    public <T> T execute(PreparedStatementCreator preparedStatementCreator, PreparedStatementCallback<T> preparedStatementCallback) throws DataAccessException {
        return jdbcTemplate.execute(preparedStatementCreator,preparedStatementCallback);
    }

    @Override
    public <T> T execute(String s, PreparedStatementCallback<T> preparedStatementCallback) throws DataAccessException {
        return jdbcTemplate.execute(s,preparedStatementCallback);
    }

    @Override
    public <T> T query(PreparedStatementCreator preparedStatementCreator, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(preparedStatementCreator,resultSetExtractor);
    }

    @Override
    public <T> T query(String s, PreparedStatementSetter preparedStatementSetter, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(s,preparedStatementSetter,resultSetExtractor);
    }

    @Override
    public <T> T query(String s, Object[] objects, int[] ints, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(s,objects,ints,resultSetExtractor);
    }

    @Override
    public <T> T query(String s, Object[] objects, ResultSetExtractor<T> resultSetExtractor) throws DataAccessException {
        return jdbcTemplate.query(s,objects,resultSetExtractor);
    }

    @Override
    public <T> T query(String s, ResultSetExtractor<T> resultSetExtractor, Object... objects) throws DataAccessException {
        return jdbcTemplate.query(s,resultSetExtractor,objects);
    }

    @Override
    public void query(PreparedStatementCreator preparedStatementCreator, RowCallbackHandler rowCallbackHandler) throws DataAccessException {
            jdbcTemplate.query(preparedStatementCreator,rowCallbackHandler);
    }

    @Override
    public void query(String s, PreparedStatementSetter preparedStatementSetter, RowCallbackHandler rowCallbackHandler) throws DataAccessException {
            jdbcTemplate.query(s,preparedStatementSetter,rowCallbackHandler);
    }

    @Override
    public void query(String s, Object[] objects, int[] ints, RowCallbackHandler rowCallbackHandler) throws DataAccessException {
            jdbcTemplate.query(s,objects,ints,rowCallbackHandler);
    }

    @Override
    public void query(String s, Object[] objects, RowCallbackHandler rowCallbackHandler) throws DataAccessException {
        jdbcTemplate.query(s,objects,rowCallbackHandler);
    }

    @Override
    public void query(String s, RowCallbackHandler rowCallbackHandler, Object... objects) throws DataAccessException {
        jdbcTemplate.query(s,rowCallbackHandler,objects);
    }

    @Override
    public <T> List<T> query(PreparedStatementCreator preparedStatementCreator, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(preparedStatementCreator,rowMapper);
    }

    @Override
    public <T> List<T> query(String s, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(s,preparedStatementSetter,rowMapper);
    }

    @Override
    public <T> List<T> query(String s, Object[] objects, int[] ints, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(s,objects,ints,rowMapper);
    }

    @Override
    public <T> List<T> query(String s, Object[] objects, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.query(s,objects,rowMapper);
    }

    @Override
    public <T> List<T> query(String s, RowMapper<T> rowMapper, Object... objects) throws DataAccessException {
        return jdbcTemplate.query(s,rowMapper,objects);
    }

    @Override
    public <T> T queryForObject(String s, Object[] objects, int[] ints, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,objects,ints,rowMapper);
    }

    @Override
    public <T> T queryForObject(String s, Object[] objects, RowMapper<T> rowMapper) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,objects,rowMapper);
    }

    @Override
    public <T> T queryForObject(String s, RowMapper<T> rowMapper, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,rowMapper,objects);
    }

    @Override
    public <T> T queryForObject(String s, Object[] objects, int[] ints, Class<T> aClass) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,objects,ints,aClass);
    }

    @Override
    public <T> T queryForObject(String s, Object[] objects, Class<T> aClass) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,objects,aClass);
    }

    @Override
    public <T> T queryForObject(String s, Class<T> aClass, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForObject(s,aClass,objects);
    }

    @Override
    public Map<String, Object> queryForMap(String s, Object[] objects, int[] ints) throws DataAccessException {
        return jdbcTemplate.queryForMap(s,objects,ints);
    }

    @Override
    public Map<String, Object> queryForMap(String s, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForMap(s,objects);
    }

    @Override
    public <T> List<T> queryForList(String s, Object[] objects, int[] ints, Class<T> aClass) throws DataAccessException {
        return jdbcTemplate.queryForList(s,objects,ints,aClass);
    }

    @Override
    public <T> List<T> queryForList(String s, Object[] objects, Class<T> aClass) throws DataAccessException {
        return jdbcTemplate.queryForList(s,objects,aClass);
    }

    @Override
    public <T> List<T> queryForList(String s, Class<T> aClass, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForList(s,aClass,objects);
    }

    @Override
    public List<Map<String, Object>> queryForList(String s, Object[] objects, int[] ints) throws DataAccessException {
        return jdbcTemplate.queryForList(s,objects,ints);
    }

    @Override
    public List<Map<String, Object>> queryForList(String s, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForList(s,objects);
    }

    @Override
    public SqlRowSet queryForRowSet(String s, Object[] objects, int[] ints) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(s,objects,ints);
    }

    @Override
    public SqlRowSet queryForRowSet(String s, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForRowSet(s,objects);
    }

    @Override
    public int update(PreparedStatementCreator preparedStatementCreator) throws DataAccessException {
        return jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public int update(PreparedStatementCreator preparedStatementCreator, KeyHolder keyHolder) throws DataAccessException {
        return jdbcTemplate.update(preparedStatementCreator,keyHolder);
    }

    @Override
    public int update(String s, PreparedStatementSetter preparedStatementSetter) throws DataAccessException {
        return jdbcTemplate.update(s,preparedStatementSetter);
    }

    @Override
    public int update(String s, Object[] objects, int[] ints) throws DataAccessException {
        return jdbcTemplate.update(s,objects,ints);
    }

    @Override
    public int update(String s, Object... objects) throws DataAccessException {
        return jdbcTemplate.update(s,objects);
    }

    @Override
    public int[] batchUpdate(String s, BatchPreparedStatementSetter batchPreparedStatementSetter) throws DataAccessException {
        return jdbcTemplate.batchUpdate(s,batchPreparedStatementSetter);
    }

    @Override
    public int[] batchUpdate(String s, List<Object[]> list) throws DataAccessException {
        return jdbcTemplate.batchUpdate(s,list);
    }

    @Override
    public int[] batchUpdate(String s, List<Object[]> list, int[] ints) throws DataAccessException {
        return jdbcTemplate.batchUpdate(s,list,ints);
    }

    @Override
    public <T> int[][] batchUpdate(String s, Collection<T> collection, int i, ParameterizedPreparedStatementSetter<T> parameterizedPreparedStatementSetter) throws DataAccessException {
        return jdbcTemplate.batchUpdate(s,collection,i,parameterizedPreparedStatementSetter);
    }

    @Override
    public <T> T execute(CallableStatementCreator callableStatementCreator, CallableStatementCallback<T> callableStatementCallback) throws DataAccessException {
        return jdbcTemplate.execute(callableStatementCreator,callableStatementCallback);
    }

    @Override
    public <T> T execute(String s, CallableStatementCallback<T> callableStatementCallback) throws DataAccessException {
        return jdbcTemplate.execute(s,callableStatementCallback);
    }

    @Override
    public Map<String, Object> call(CallableStatementCreator callableStatementCreator, List<SqlParameter> list) throws DataAccessException {
        return jdbcTemplate.call(callableStatementCreator,list);
    }
}
