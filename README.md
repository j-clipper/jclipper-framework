# jclipper

## 结构说明
   
```text
.
├── jclipper-alert-notice   # 告警通知工具
├── jclipper-common   # 公共基础包
├── jclipper-framework-parent # 公共bom
├── jclipper-mybatis-plus-extend  # MyBatisPlus扩展增强 
├── jclipper-spring-boot-starters
│   ├── dynamic-arthas-spring-boot-starter   # arthas动态注册
│   ├── exception-handler-spring-boot-starter   # 全局统一异常处理
│   ├── java-time-converter-spring-boot-starter # java Date和java8 Time的序列化与反序列化
│   ├── lettuce-spring-boot-starter # lettuce版的Redis客户端
│   ├── xxl-job-spring-boot-starter # 封装xxljob配置
│   ├── mongo-plus-spring-boot-starter # spring-data-mongodb封装
│   ├── validation-spring-boot-starter # 参数校验封装
│   └── swagger-spring-boot-starter # swagger3，springfox3，使用knife4j增强
└── jclipper-webapp-base  # jclipper webapp 基础依赖


```

## 模块功能说明

### exception-handler-spring-boot-starter
> 全局统一异常处理
集成全局统一异常处理，详见`jclipper.springboot.exception.GlobalExceptionHandler`
> 推荐使用，如下方式抛出业务异常：
> 1. 枚举类`CommonErrorCode`；
> 2. 实现`BaseErrorCode`自定义枚举类;
> 3. 使用`CustomErrorCode`对象；

预设的业务异常类，位于`jclipper-common`依赖的`jclipper.common.exception`包中；


### java-time-converter-spring-boot-starter
> java Date和java8 Time的序列化与反序列化

### lettuce-spring-boot-starter

> lettuce版的Redis客户端

#### 参数配置
```properties
spring.redis.cluster.nodes=192.168.9.45:7001,192.168.9.45:7002,192.168.9.45:7003,192.168.9.45:7004,192.168.9.45:7005,192.168.9.45:7006
spring.redis.cluster.max-redirects=3
spring.redis.timeout=10000
spring.redis.lettuce.pool.max-wait=10000
spring.redis.lettuce.pool.max-idle=20
spring.redis.lettuce.pool.min-idle=20
```

#### 使用：
1. 工具类：`CacheLettuceUtils`
2. Bean: `CacheLettuceClient`

### xxl-job-spring-boot-starter
> 封装xxljob配置

#### 参数配置
```properties
xxl.job.adminAddresses=http://192.168.20.3:8080/,http://192.168.20.4:8080/
### xxl-job executor 执行器名称 可以直接去项目名称
xxl.job.executor.appname=smart-campus-user-account-dev
### xxl-job executor 调读中心地址默认使用admin.addresses
### 调度器端口设置 默认 9999 不同项目端口不同
xxl.job.executor.port=10030
### xxl-job executor log-path 日志配置
xxl.job.executor.logpath=../logs/jobhandler
### 日志保留30天
xxl.job.executor.logretentiondays=30
```

### mongo-plus-spring-boot-starter
> spring-data-mongodb封装

### swagger-spring-boot-starter
> swagger3，springfox3，使用knife4j增强

### jclipper-webapp-base
> jclipper webapp 基础依赖

1. 集成nacos配置管理功能；
2. 集成nacos服务配置功能，注册的元数据中会携带服务启动时间；
3. 集成Dubbo远程调用，注册中心使用的SpringCloud；
4. 集成全局统一异常处理，详见`jclipper.springboot.exception.GlobalExceptionHandler`
   > 推荐使用，如下方式抛出业务异常：
   > 1. 枚举类`CommonErrorCode`；
   > 2. 实现`BaseErrorCode`自定义枚举类;
   > 3. 使用`CustomErrorCode`对象；
5. 集成swagger3，springfox3，使用knife4j增强，访问地址 ip:port/doc.htm;
   > 生产环境要禁用此功能请添加配置：jclipper.swagger.enabled=false
6. java Date和java8 Time的序列化与反序列化，参见`ExampleController`和`DateModel`类；
7. 集成log4j2配置动态刷新功能，在nacos的配置中心中修改dataId=log4j2-spring.xml，即可实现日志配置的动态刷新；
   > 如果不想使用此功能，请设置jvm启动参数 -Dnacos.log4j2.dynamic.enabled=false ，或配置Spring参数 nacos.log4j2.dynamic.enabled=false
9. mybatis-plus generator,用法详见测试目录下的类`MyBatisCodeGenerator`；


