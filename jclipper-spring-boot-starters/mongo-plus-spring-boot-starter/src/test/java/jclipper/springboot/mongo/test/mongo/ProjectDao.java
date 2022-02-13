package jclipper.springboot.mongo.test.mongo;


import jclipper.springboot.mongo.test.domain.Project;
import org.bson.types.ObjectId;

public interface ProjectDao {
    Project findById(Long id);

    Project save(Project project);

    void update(Project project);

    void update(ObjectId id, String detail);
}
