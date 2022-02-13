# lettuce应用框架

## 项目加载

1. 依赖加入

```$xslt
        <dependency>
            <groupId>com.wf2311.jclipper</groupId>
            <artifactId>lettuce-spring-boot-starter</artifactId>
            <version>2022.1-SNAPSHOT</version> 
        </dependency>
   需要使用不同logger可以排除掉不必jar包 比如：
           <exclusions>
                   <exclusion>
                       <artifactId>log4j</artifactId>
                       <groupId>log4j</groupId>
                   </exclusion>
                   <exclusion>
                       <artifactId>log4j-slf4j-impl</artifactId>
                       <groupId>org.apache.logging.log4j</groupId>
                   </exclusion>
                   <exclusion>
                       <artifactId>log4j-jul</artifactId>
                       <groupId>org.apache.logging.log4j</groupId>
                   </exclusion>
                   <exclusion>
                       <artifactId>log4j-core</artifactId>
                       <groupId>org.apache.logging.log4j</groupId>
                   </exclusion>
               </exclusions>     
```

2. 注解加入,pom引入需要手动开启是否使用,以下注解根据需要选择其中一个

```$xslt
1. @EnableLettuceRedisDiscovery    // 初始化掉默认connction和redisTemplate
2. @EnableLettuceRedisFactoryDiscovery  // 初始化掉connection
3. @EnableLettuceRedisTemplateDiscovery  // 初始化掉redisTemplate
```

3. 注解说明

```$xslt
 实际项目中可以需要使用到自定义的connectionFacotry和redisTemplate.
 场景一：自定义redisTemplate 主要是需要自定义序列化解析器。这时候只选择初始化connection
 场景二：connection参数需要进行调整。使用EnableLettuceRedisTemplateDiscovery注解。对connection不进行初始化
 场景三：即需要自定义序列化也需要自定义connection。注解都不要引入。
```

4. 初始化bean参考

```$xslt
redisTemplate初始化使用RedisTemplateBeanInit类
connection初始化使用LettuceSpringbootConnectionFactory类
```