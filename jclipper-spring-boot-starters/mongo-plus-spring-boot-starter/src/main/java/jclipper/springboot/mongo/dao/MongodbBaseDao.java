package jclipper.springboot.mongo.dao;

import jclipper.springboot.mongo.utils.CustomReflectionUtils;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.util.List;
import java.util.Map;

public abstract class MongodbBaseDao {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 根据id单属性更新
     *
     * @param <T>
     * @param id
     * @param field 属性名
     * @param input 属性值
     * @param entityClass 实体类
     * @return
     * @throws Throwable 异常
     */
    protected <T> void updateSingleAttribute(Object id, String field, Object input, Class<T> entityClass) {
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), new Update().set(field, input), entityClass);
    }

    /**
     * 根据id 多属性更新
     *
     * @param id
     * @param inputs
     * @param entityClass
     * @param <T>
     * @throws Throwable
     */
    protected <T> void updateMultiAttributes(Object id, Map<String, Object> inputs, Class<T> entityClass) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        for (String key : inputs.keySet()) {
            update.set(key, inputs.get(key));
        }
        mongoTemplate.updateMulti(query, update, entityClass);
    }

    /**
     * 根据多个查询条件 更新 一条记录的一个个字段
     * @param conditions
     * @param field
     * @param input
     * @param entityClass
     * @param <T>
     */
    protected <T> void updateFirst(Map<String, Object> conditions, String field,Object input, Class<T> entityClass) {
        Query query = new Query();
        for (String key : conditions.keySet()) {
            query.addCriteria(Criteria.where(key).is(conditions.get(key)));
        }

        Update update = new Update();
        update.set(field, input);

        mongoTemplate.updateFirst(query, update, entityClass);
    }


    /**
     * 根据多个查询条件 更新 一条记录的多个字段
     *
     * @param conditions
     * @param inputs
     * @param entityClass
     * @param <T>
     * @throws Throwable
     */
    protected <T> void updateFirst(Map<String, Object> conditions, Map<String, Object> inputs, Class<T> entityClass) {
        Query query = new Query();
        for (String key : conditions.keySet()) {
            query.addCriteria(Criteria.where(key).is(conditions.get(key)));
        }

        Update update = new Update();
        for (String field : inputs.keySet()) {
            update.set(field, inputs.get(field));
        }
        mongoTemplate.updateFirst(query, update, entityClass);
    }

    /**
     * 根据多个查询条件 更新 多个记录的个字段
     * @param conditions
     * @param field
     * @param input
     * @param entityClass
     * @param <T>
     */
    protected <T> void updateMulti(Map<String, Object> conditions, String field,Object input, Class<T> entityClass) {
        Query query = new Query();
        for (String key : conditions.keySet()) {
            query.addCriteria(Criteria.where(key).is(conditions.get(key)));
        }

        Update update = new Update();
        update.set(field, input);

        mongoTemplate.updateMulti(query, update, entityClass);
    }

    /**
     * 根据多个查询条件， 更新多条记录的 多个字段
     *
     * @param conditions
     * @param inputs
     * @param entityClass
     * @param <T>
     * @throws Throwable
     */
    protected <T> void updateMulti(Map<String, Object> conditions, Map<String, Object> inputs, Class<T> entityClass) {
        Query query = new Query();
        for (String key : conditions.keySet()) {
            query.addCriteria(Criteria.where(key).is(conditions.get(key)));
        }

        Update update = new Update();
        for (String field : inputs.keySet()) {
            update.set(field, inputs.get(field));
        }
        mongoTemplate.updateMulti(query, update, entityClass);
    }

    /**
     * 根据id 原子性增减字段值
     * @param conditions
     * @param field
     * @param input
     * @param entityClass
     * @return
     */
    protected<T> T incSingleAttribute(Map<String, Object> conditions, String field, Number input, Class<?> entityClass) {
        Query query = new Query();
        for (String key : conditions.keySet()) {
            query.addCriteria(Criteria.where(key).is(conditions.get(key)));
        }
        Update update = new Update();
        update.inc(field, input);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(false);
        options.returnNew(true);
        Object t = mongoTemplate.findAndModify(query,update,options,entityClass);
        return (T)CustomReflectionUtils.getField(field,t);
    }

    /**
     * 根据id 原子性增减字段值
     * @param id
     * @param field
     * @param input
     * @param entityClass
     * @return
     */
    protected<T> T incSingleAttributeById(Object id, String field, Number input, Class<?> entityClass) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.inc(field, input);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(false);
        options.returnNew(true);
        Object t = mongoTemplate.findAndModify(query,update,options,entityClass);
        return (T) CustomReflectionUtils.getField(field,t);
    }

    /**
     * n个条件（n>=0)查询（分页） （条件为String类型：模糊查询） （条件为非String类型：精确查询）
     *
     * @param paramMap 参数map
     * @param start 起始记录位置
     * @param size 每页大小
     * @param sort 排序（根据某个属性按升序或降序排列）
     * @param entityClass 实体类
     * @return
     */
    protected <T> List<T> searchLike(Map<String, Object> paramMap, int start, int size, Sort sort,
                                     Class<T> entityClass) {
        Query queryBySearch = getQueryBySearch(paramMap);
        queryBySearch.skip(start);
        queryBySearch.limit(size);
        queryBySearch.with(sort);
        List<T> list = mongoTemplate.find(queryBySearch, entityClass);
        return list;
    }

    /**
     * n个条件（n>=0)查询（分页）
     *
     * @param conditions 参数map
     * @param start 起始记录位置
     * @param size 每页大小
     * @param sort 排序（根据某个属性按升序或降序排列）
     * @param entityClass 实体类
     * @return
     */
    protected <T> List<T> findList(Map<String, Object> conditions, int start, int size, Sort sort,
                                     Class<T> entityClass) {
        Query query = new Query();
        for (String field : conditions.keySet()) {
            query.addCriteria(Criteria.where(field).is(conditions.get(field)));
        }
        query.skip(start);
        query.limit(size);
        query.with(sort);
        List<T> list = mongoTemplate.find(query, entityClass);
        return list;
    }

    /**
     * n个条件（n>=0) 计数
     *
     * @param conditions 参数map
     * @param entityClass 实体类
     * @return
     */
    protected int countList(Map<String, Object> conditions, Class<?> entityClass) {
        Query query = new Query();
        for (String field : conditions.keySet()) {
            query.addCriteria(Criteria.where(field).is(conditions.get(field)));
        }
        return (int)mongoTemplate.count(query, entityClass);
    }

    /**
     * paramMap
     *
     * @param paramMap
     * @param param       范围指定查询字段
     * @param startPoint  起始值
     * @param endPoint    结束值
     * @param start
     * @param size
     * @param sort
     * @param entityClass
     * @return
     */
    protected <T> List<T> searchLike(Map<String, Object> paramMap, String param, Long startPoint, Long endPoint,
                                     int start, int size, Sort sort, Class<T> entityClass) {
        Query queryBySearch = getQueryBySearch(paramMap);
        if (startPoint != null && endPoint != null) {
            queryBySearch.addCriteria(Criteria.where(param).gte(startPoint).and(param).lte(endPoint));
        }
        queryBySearch.skip(start);
        queryBySearch.limit(size);
        queryBySearch.with(sort);
        List<T> list = mongoTemplate.find(queryBySearch, entityClass);
        return list;
    }

    /**
     * n个条件（n>=0)查询（不分页） （条件为String类型：模糊查询） （条件为非String类型：精确查询）
     *
     * @param paramMap 参数map
     * @param entityClass 实体类
     * @return
     */
    protected <T> List<T> searchLike(Map<String, Object> paramMap, Class<T> entityClass) {
        Query queryBySearch = getQueryBySearch(paramMap);
        List<T> list = mongoTemplate.find(queryBySearch, entityClass);
        return list;
    }

    /**
     * n个条件（n>=0)查询得到总记录数（分页/非分页） （条件为String类型：模糊查询） （条件为非String类型：精确查询）
     *
     * @param paramMap 参数map
     * @param entityClass 实体类
     * @return
     */
    protected <T> int searchLikeCount(Map<String, Object> paramMap, Class<T> entityClass) {
        Query queryBySearch = getQueryBySearch(paramMap);
        int count = (int) mongoTemplate.count(queryBySearch, entityClass);
        return count;
    }

    /**
     * @param paramMap
     * @param param       范围指定查询字段
     * @param startPoint  起始值
     * @param endPoint    结束值
     * @param entityClass
     * @return
     */
    protected <T> int searchLikeCount(Map<String, Object> paramMap, String param, Long startPoint, Long endPoint,
                                      Class<T> entityClass) {
        Query queryBySearch = getQueryBySearch(paramMap);
        if (startPoint != null && endPoint != null) {
            queryBySearch.addCriteria(Criteria.where(param).gte(startPoint).and(param).lte(endPoint));
        }
        int count = (int) mongoTemplate.count(queryBySearch, entityClass);
        return count;
    }

    protected <T> int count(Query query, Class<T> entityClass) {
        return (int) mongoTemplate.count(query, entityClass);
    }

    private Query getQueryBySearch(Map<String, Object> paramMap) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (paramMap != null) {
            paramMap.keySet().forEach(key -> {
                if (key != null) {
                    if (paramMap.get(key) instanceof String) {
                        String v = paramMap.get(key).toString();
                        while (v.endsWith("\\")) {
                            v = v.substring(0, v.length() + 1 - 2);
                        }
                        criteria.and(key).regex(v);
                    } else {
                        criteria.and(key).is(paramMap.get(key));
                    }

                }

            });

        }
        query.addCriteria(criteria);
        return query;
    }

    protected <T> List<T> findList(Class<T> clazz, int start, int size) {
        Query query = new Query();
        query.skip(start);
        query.limit(size);
        return mongoTemplate.find(query, clazz);
    }

    protected <T> List<T> findList(Class<T> clazz, Query query, int start, int size) {
        query.skip(start);
        query.limit(size);
        return mongoTemplate.find(query, clazz);
    }

    /**
     * 根据多个与条件删除
     *
     * @param conditions
     * @param entityClass
     * @return 删除数据个数
     */
    protected long remove(Map<String, Object> conditions, Class<?> entityClass) {
        Query query = new Query();
        for (String key : conditions.keySet()) {
            query.addCriteria(Criteria.where(key).is(conditions.get(key)));
        }
        DeleteResult result = mongoTemplate.remove(query, entityClass);
        return result.getDeletedCount();
    }
}
