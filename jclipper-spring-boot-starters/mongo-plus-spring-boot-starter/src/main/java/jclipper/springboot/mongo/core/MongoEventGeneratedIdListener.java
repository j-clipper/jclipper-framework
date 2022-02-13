package jclipper.springboot.mongo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 自动生成id
 */
public class MongoEventGeneratedIdListener extends AbstractMongoEventListener<Object> {
    private static final Logger logger= LoggerFactory.getLogger(MongoEventGeneratedIdListener.class);
    @Autowired
    private MongoTemplate mongo;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                if (field.isAnnotationPresent(GeneratedValue.class) && matchType(field.getType())) {
                    ReflectionUtils.makeAccessible(field);
                    if(needGenerated(field,source)){
                        // 通过反射设置自增ID
                        field.set(source, getNextId(source.getClass().getSimpleName()));
                    }
                }
            });
        }

    }

    private boolean matchType(Class<?> fieldType){
        return ( fieldType.equals(long.class) || fieldType.equals(Long.class));
    }

    private boolean needGenerated(Field field, Object source) {
        try {
            Object value = field.get(source);
            if (value == null || value.equals(0)) {
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Long getNextId(String collectionName) {
        Query query = new Query(Criteria.where("collectionName").is(collectionName));
        Update update = new Update();
        update.inc("sequenceId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        MongoSequence sequence= mongo.findAndModify(query, update, options, MongoSequence.class);
        return sequence.getSequenceId();
    }
}
