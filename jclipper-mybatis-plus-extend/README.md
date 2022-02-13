
## 模块功能说明
封装mybatis-plus
提供公共model，并提供createBy,createdOn, modifiedOn,modifiedBy字段填充。
提供逻辑删除，字段名isDeleted.
开启注解事务
   
## 使用mybatis-plus
引入依赖
```
     <dependency>
            <groupId>com.wf2311.jclipper</groupId>
            <artifactId>jclipper-mybatis-plus-extend</artifactId>
      </dependency>
```
进行配置
```yml
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
    #log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  mapper-locations: classpath*:/jclipper/**/*.xml
  global-config:
    # 逻辑删除配置
    db-config:
      # 删除前
      logic-not-delete-value: 0
      # 删除后
      logic-delete-value: 1
```
## 注意事项
配置逻辑删除后，所有的更新查询条件都会默认带上 is_deleted = 0.

