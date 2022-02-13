package jclipper.springboot.mongo.test.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@ToString
public abstract class BaseModel {
    @CreatedDate
    protected Date createdTime;

    protected String createdBy;

    @LastModifiedDate
    protected Date modifiedTime;

    protected String modifiedBy;

    protected Boolean deleted;
}
