package com.github.alenfive.rocketapi.datasource;

import com.github.alenfive.rocketapi.entity.vo.Page;
import com.github.alenfive.rocketapi.entity.vo.ScriptContext;
import com.github.alenfive.rocketapi.extend.IApiPager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * mongodb 数据源操作
 */
@Slf4j
public class ClickHouseDataSource extends JdbcDataSource {

    public ClickHouseDataSource(DataSource dataSource) {
        super(dataSource,Boolean.FALSE);
    }

    @Override
    public List<Map<String,Object>> find(ScriptContext scriptContext) {
        List<Map<String,Object>> resultList = jdbcTemplate.queryForList(scriptContext.getScript().toString(), scriptContext.getParams()[0]);
        return resultList.stream().map(this::toReplaceKeyLow).collect(Collectors.toList());
    }

    @Override
    public int update(ScriptContext scriptContext) {
        throw new UnsupportedOperationException("The operation is not allowed");
    }

    @Override
    public int remove(ScriptContext scriptContext) {
        throw new UnsupportedOperationException("The operation is not allowed");
    }

    @Override
    public Object insert(ScriptContext scriptContext) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
 //       jdbcTemplate.execute(scriptContext.getScript().toString() );
//        try {
//            jdbcTemplate.update(scriptContext.getScript().toString(), new MapSqlParameterSource(scriptContext.getParams()[0]) );
//
//        }catch (Exception e){
//            log.warn("error",e);
//        }
        jdbcTemplate.update(scriptContext.getScript().toString(), new MapSqlParameterSource(scriptContext.getParams()[0]), keyHolder);
        return keyHolder.getKeyList().stream().map(item->item.get("GENERATED_KEY")).collect(Collectors.toList());
    }

    @Override
    public String buildCountScript(String script,IApiPager apiPager,Page page) {
        return script;
    }

    @Override
    public String buildPageScript(String script,IApiPager apiPager,Page page) {
        return script;
    }

}
