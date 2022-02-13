package jclipper.springboot.mongo.core;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MongoSequence {
    @Id
    private String id;

    private long sequenceId;

    private String collectionName;
}
