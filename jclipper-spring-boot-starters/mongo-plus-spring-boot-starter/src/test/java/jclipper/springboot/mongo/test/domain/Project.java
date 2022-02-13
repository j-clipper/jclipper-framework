package jclipper.springboot.mongo.test.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * 一个创作项目（项目内部数据）
 */
@Data
@NoArgsConstructor
public class Project extends BaseModel {
    @Id
    private ObjectId id;
    private String userId;
    private String detail;  //项目内部数据

    public Project(String detail) {
        this.detail = detail;
    }

    public Project(String userId, String detail) {
        this.userId = userId;
        this.detail = detail;
    }

    public Project(ObjectId id, String detail) {
        this.id = id;
        this.detail = detail;
    }
}

