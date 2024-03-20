package com.github.alenfive.rocketapi.datasource;

import com.github.alenfive.rocketapi.entity.ApiEntity;
import com.github.alenfive.rocketapi.entity.vo.Page;
import com.github.alenfive.rocketapi.entity.vo.ScriptContext;
import com.github.alenfive.rocketapi.entity.vo.TableInfo;
import com.github.alenfive.rocketapi.extend.IApiPager;
import com.github.alenfive.rocketapi.utils.FieldUtils;
import org.apache.poi.ss.formula.functions.T;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * redis 数据源操作
 */
@SuppressWarnings("ALL")
public class RedisDataSource extends DataSourceDialect {

    public RedisTemplate redisTemplate;

    /**
     * string里面输入的redis操作命令截取
     */
    private static final String PATTERN = "[A-Za-z0-9_.]+('?[A-Za-z0-9_.])?";


    private RedisDataSource(){}

    public RedisDataSource(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisDataSource(RedisTemplate redisTemplate, boolean storeApi) {
        this.redisTemplate = redisTemplate;
        this.storeApi = storeApi;
    }

    @Override
    public <T extends ApiEntity> void saveEntity(T entity) {

//        redisTemplate.save(entity, ApiAnnotationUtil.getApiTableName(entity.getClass()));
    }

    @Override
    public <T extends ApiEntity> T findEntityById(T entity) {

//        return (T) redisTemplate.findById(entity.getId(),entity.getClass(),ApiAnnotationUtil.getApiTableName(entity.getClass()));
        return null;
    }

    @Override
    public <T extends ApiEntity> void removeEntityById(T entity) {
//        redisTemplate.remove(Query.query(Criteria.where("_id").is(new ObjectId(entity.getId()))),ApiAnnotationUtil.getApiTableName(entity.getClass()));
    }

    @Override
    public <T extends ApiEntity> void updateEntityById(T entity) {
//        redisTemplate.save(entity,ApiAnnotationUtil.getApiTableName(entity.getClass()));
    }

    @Override
    public <T extends ApiEntity> List<T> listByEntity(T entity) {
//        Query query = Query.query(buildCriteria(entity));
//        query.with(Sort.by(Sort.Direction.DESC,"_id"));
//        return redisTemplate.find(query,(Class <T>) (entity.getClass()),ApiAnnotationUtil.getApiTableName(entity.getClass()));
        return null;
    }

    @Override
    protected Map<String, Object> toReplaceKeyLow(Map<String, Object> map) {
//        return super.toReplaceKeyLow(map);
        return null;
    }

    private <T extends ApiEntity> Criteria buildCriteria(T entity){
        Criteria criteria = new Criteria();
        FieldUtils.allFields(entity.getClass()).stream().filter(item->{
            item.setAccessible(true);
            try {
                Object value = item.get(entity);
                return value != null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        }).forEach(item->{
            try {
                criteria.and(item.getName()).is(item.get(entity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return criteria;
    }

    @Override
    public <T extends ApiEntity> List<T> pageByEntity(T entity, IApiPager apiPager, Page page) {
//        Query query = Query.query(buildCriteria(entity));
//        query.skip(apiPager.getOffset(page.getPageSize(),page.getPageNo())).limit(page.getPageSize());
//        query.with(Sort.by(Sort.Direction.DESC,"_id"));
//        return redisTemplate.find(query,(Class <T>) (entity.getClass()),ApiAnnotationUtil.getApiTableName(entity.getClass()));
        return null;
    }


    @Override
    public List find(ScriptContext scriptContext)  throws Exception {
          formatISODate(scriptContext.getScript());
          formatObjectIdList(scriptContext.getScript());

        List list = new ArrayList();
        String s = scriptContext.getScript().toString();
        Object result = null;
        List<String> commond = createCommond(s);
        String redisCommond = commond.get(0);

        if (redisCommond.equals("GET")){
            //redis 的 get操作
            ValueOperations<String, T> operation = redisTemplate.opsForValue();
            result = operation.get(commond.get(1));
        }else if (redisCommond.equals("SMEMBERS")){
            // redis的set 操作
            result = redisTemplate.opsForSet().members(commond.get(1));
        }else if (redisCommond.equals("HGETALL")){
            result = redisTemplate.opsForHash().entries(commond.get(1));
        }else if (redisCommond.equals("HGET")){
            result = redisTemplate.opsForHash().get(commond.get(1),commond.get(2));
        }else if (redisCommond.equals("LRANGE")){
            result = redisTemplate.opsForList().range(commond.get(1), 0, -1);
        }
        list.add(result);
        return list;
    }

    @Override
    public int update(ScriptContext scriptContext) throws Exception {
//        formatISODate(scriptContext.getScript());
//        formatObjectIdList(scriptContext.getScript());
//        Document result = redisTemplate.executeCommand(scriptContext.getScript().toString());
//        return result.getInteger("n");
        return 1;
    }

    @Override
    public int[] batchUpdate(ScriptContext scriptContext) throws Exception {
        return new int[0];
    }

    @Override
    public int remove(ScriptContext scriptContext) throws Exception {

        String s = scriptContext.getScript().toString();
        Object result = null;
        List<String> commond = createCommond(s);
        String redisCommond = commond.get(0);
        if (redisCommond.equals("DEL")){
            redisTemplate.delete(commond.get(1));
        }else if (redisCommond.equals("HDEL")){
            redisTemplate.opsForHash().delete(commond.get(1),commond.get(2));
        }
        return  1;
    }

    @Override
    public Object insert(ScriptContext scriptContext) throws Exception {
        String s = scriptContext.getScript().toString();
        Object result = new Object();
        List<String> commond = createCommond(s);
        String redisCommond = commond.get(0);
        String key = commond.get(1);
        Long count = 0L;
        if (redisCommond.equals("LPUSH")){
            String s1 = commond.get(2);
            List<String> dataList = new ArrayList<String>();
            dataList.add(s1);
            count = redisTemplate.opsForList().leftPush(key, s1);
        }else if (redisCommond.equals("SET")){
            redisTemplate.opsForValue().set(key, commond.get(2));
        }else if (redisCommond.equals("HMSET")){
            redisTemplate.opsForHash().putAll(key, createMap(commond));
        }
        return count;
    }


    /**
     * 遍历命令列表，从第三个开始，已key-value的形态存储到hash里
     * @param commond
     * @return
     */
    private Map<String, String> createMap (List<String> commond){
        Map<String, String> map = new HashMap<>();

        for (int i = 2; i < commond.size(); i++) {
            if (i%2==0){
                int j = i+1;
                map.put(commond.get(i),commond.get(j));
            }
        }
        return map;

    }

    private List<Object> batchInsert(StringBuilder script){
        Document insertDoc = Document.parse(script.toString());
        List<Document> docList = getList(insertDoc,"documents",Document.class,null);
        if (CollectionUtils.isEmpty(docList)){
            throw new RuntimeException("insert documents is empty");
        }
        for (Document doc : docList ){
            ObjectId id = doc.getObjectId("_id");
            if (id == null){
                doc.put("_id",ObjectId.get());
            }
        }
//        redisTemplate.executeCommand(insertDoc);
        return docList.stream().map(item->item.get("_id")).collect(Collectors.toList());
    }

    private <T> List<T> getList(Document document,final Object key, final Class<T> clazz, final List<T> defaultValue) {
        List<?> value = document.get(key, List.class);
        if (value == null) {
            return defaultValue;
        }

        for (Object item : value) {
            if (!clazz.isAssignableFrom(item.getClass())) {
                throw new ClassCastException(format("List element cannot be cast to %s", clazz.getName()));
            }
        }
        return (List<T>) value;
    }

    private void formatObjectIdList(StringBuilder script) {
        String flag = "ObjectId(";
        for (int i=0;i<script.length();i++){
            int startIndex = script.indexOf(flag,i);
            if (startIndex == -1){
                break;
            }

            int endIndex = script.indexOf(")",startIndex+flag.length());
            if (endIndex == -1){
                throw new IllegalArgumentException("missed ObjectId( close ')'");
            }

            String objectIdStr = script.substring(startIndex+flag.length(),endIndex);
            String[] objectIdArr = objectIdStr.split(",");
            if (objectIdArr.length == 1){
                i = endIndex;
                continue;
            }
            String newObjectIds = Stream.of(objectIdArr).map(item->"ObjectId("+item+")").collect(Collectors.joining(","));
            i = startIndex + newObjectIds.length();
            script = script.replace(startIndex,endIndex+1,newObjectIds);
        }
    }

    public void formatISODate(StringBuilder script){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String flag = "ISODate(";
        for (int i=0;i<script.length();i++){
            int startIndex = script.indexOf(flag,i);
            if (startIndex == -1){
                break;
            }

            int endIndex = script.indexOf(")",startIndex+flag.length());
            if (endIndex == -1){
                throw new IllegalArgumentException("missed ISODate( close ')'");
            }

            String timeStr = script.substring(startIndex+flag.length()+1,endIndex-1);


            try {
                sdfUtc.parse(timeStr);
                i = endIndex;
                continue;
            } catch (ParseException e) {}

            boolean isLocal = timeStr.endsWith("+08:00");
            if (isLocal){
                timeStr = timeStr.replace("+08:00","").replace("T"," ");
            }

            Date time = null;
            try {
                time = sdf1.parse(timeStr);
            } catch (ParseException e) {
                try {
                    time = sdf2.parse(timeStr);
                } catch (ParseException ex) {
                    try {
                        time = sdf3.parse(timeStr);
                    } catch (ParseException exx) {
                        throw new MissingFormatArgumentException("format ISODate error "+script.substring(startIndex,endIndex));
                    }
                }
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.HOUR_OF_DAY,-8);

            String newTime = sdfUtc.format(calendar.getTime());
            i = startIndex+flag.length()+1 + newTime.length();
            script = script.replace(startIndex+flag.length()+1,endIndex-1,newTime);
        }
    }

    private Map<String,Object>  toMap(Document item) {
        Map<String, Object> map = new HashMap<>(item.size());
        Set<String> keys = item.keySet();
        for (String key : keys){
           Object value = item.get(key);
           if ("_id".equals(key)){
               key = "id";
           }
           if (value instanceof Document){
               map.put(key,toMap((Document) value));
               continue;
           }
            map.put(key,value instanceof ObjectId?((ObjectId)value).toHexString():value);
        }
        return map;
    }


    @Override
    public String buildCountScript(String script, IApiPager apiPager, Page page) {
        Document document = Document.parse(script);
        document.put("count",document.get("find"));
        document.put("query",document.get("filter"));
        document.remove("find");
        document.remove("filter");
        document.remove("sort");
        return document.toJson();
    }

    @Override
    public String buildPageScript(String script,IApiPager apiPager, Page page) {
        Document document = Document.parse(script);
        document.put("skip",apiPager.getOffset(page.getPageSize(),page.getPageNo()));
        document.put("limit",page.getPageSize());
        return document.toJson();
    }

    @Override
    public String transcoding(String param) {
        return param
                .replace("\\","\\\\")
                .replace("\"","\\\"")
                .replace("\'","\\\'");
    }

    @Override
    public void close() {

    }

    @Override
    public List<TableInfo> buildTableInfo() {
        return null;
    }


/**
 * 以下是redis的操作，
 */



    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key
     * @param hkey
     */
    public void delCacheMapValue(final String key, final String hkey)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 清除所有prefix_开头的缓存
     */
    public void clearCache(String prefix){
        deleteObject(keys(prefix+ "_*")) ;
    }



    /**
     * 获取redis自增
     * @param key
     * @param liveTime
     * @return
     */
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.MINUTES);
        }
        return increment;
    }


    private List<String> createCommond(String s){
        String execStr = s.trim().replace("'","");
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(execStr);
        List<String> strs = new ArrayList<>();
        while(m.find()) {
            if (strs.size() == 0){
                strs.add(m.group().toUpperCase());
            }else {
                strs.add(m.group());
            }

        }
        return strs;
    }




    public static void main(String[] argvs) {
        String line = "sadd cust-set-001 redis7";

        String pattern = "[A-Za-z0-9/-]+('?[A-Za-z0-9])?";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        List<String> strs = new ArrayList<>();
        while(m.find()) {
            strs.add(m.group());
        }
        System.out.println(strs.toString());
    }

}
