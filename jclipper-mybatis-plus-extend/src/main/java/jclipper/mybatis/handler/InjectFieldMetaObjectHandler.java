package jclipper.mybatis.handler;

import jclipper.common.thread.ThreadLocalHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class InjectFieldMetaObjectHandler implements MetaObjectHandler {

    public static final String SESSION_ATTRIBUTE_USER_USERID = "user.userId";

    @Override
    public void insertFill(MetaObject metaObject) {
        Long currentUserId = getCurrentUserId();
        this.strictInsertFill(metaObject, "createdOn", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "modifiedOn", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createdBy", Long.class, currentUserId);
        this.strictInsertFill(metaObject, "modifiedBy", Long.class, currentUserId);

        //备注LocalDateTime::now() 就会报错

    }

    private Long getCurrentUserId() {
        Long exists = ThreadLocalHolder.getResource(SESSION_ATTRIBUTE_USER_USERID);
        return exists == null ? 0L : exists;
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "modifiedOn", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "modifiedBy", Long.class, getCurrentUserId());
    }
}
