package jclipper.springboot.mongo.config;

import jclipper.springboot.mongo.core.MongoEventGeneratedIdListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
@EnableMongoAuditing
@AutoConfigureAfter(MongoAutoConfiguration.class)
public class MongoPlusAutoConfiguration {

//    @Bean
//    public AuditorAware<AuditableUser> myAuditorProvider() {
//        return new AuditorAwareImpl();
//    }

    @Primary
    @Bean
    public MongoEventGeneratedIdListener mongoEventGeneratedIdListener() {
        return new MongoEventGeneratedIdListener();
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context,
                                                       MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);

        // Don't save _class to mongo
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return mappingConverter;
    }
}
