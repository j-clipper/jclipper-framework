package jclipper.springboot.mongo.test;


import jclipper.springboot.mongo.test.mongo.ProjectDao;
import jclipper.springboot.mongo.test.domain.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoPlusTestApplication.class)
public class MongoPlusTest {
    @Resource
    private ProjectDao projectDao;

    @Test
    public void testSave() throws InterruptedException {
        Project project = projectDao.save(new Project("111","detail"));
        project.setDetail("detail11");
        Thread.sleep(10000L);
        projectDao.update(project.getId(),project.getDetail());
    }
}
