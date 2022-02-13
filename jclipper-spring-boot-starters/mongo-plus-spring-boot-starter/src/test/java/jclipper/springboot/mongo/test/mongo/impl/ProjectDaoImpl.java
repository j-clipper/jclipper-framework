package jclipper.springboot.mongo.test.mongo.impl;

import jclipper.springboot.mongo.dao.MongodbBaseDao;
import jclipper.springboot.mongo.test.mongo.ProjectDao;
import jclipper.springboot.mongo.test.mongo.repository.ProjectRepository;
import jclipper.springboot.mongo.test.domain.Project;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProjectDaoImpl extends MongodbBaseDao implements ProjectDao {
    @Autowired
    private ProjectRepository repository;

    @Override
    public Project save(Project project) {
        return repository.save(project);
    }

    @Override
    public Project findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void update(Project project) {
        Assert.notNull(project.getId(), "作品ID不能为空");
        Map<String,Object> inputs = new HashMap<>();
        if(project.getDetail()!=null){
            inputs.put("detail",project.getDetail());
        }

        if(project.getDeleted()!=null){
            inputs.put("deleted",project.getDeleted());
        }
        updateMultiAttributes(project.getId(), inputs,Project.class);
    }

    @Override
    public void update(ObjectId id, String detail) {
        Project project = new Project(id,detail);
        mongoTemplate.save(project);
    }
}
