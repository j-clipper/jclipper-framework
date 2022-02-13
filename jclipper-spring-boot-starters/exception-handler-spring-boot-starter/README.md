# exception-handler-spring-boot-starter
## 项目说明
- 全局Http接口统一异常处理，核心类`jclipper.springboot.exception.GlobalExceptionHandler`
- 全局Dubbo接口统一异常处理，核心类`jclipper.springboot.exception.DubboExceptionFilter`

使用Dubbo异常处理需要禁用Dubbo自带的异常处理过滤器,添加配置：
```properties
dubbo.provider.filter=-exception,dubboExceptionFilter
```


## Maven坐标
```xml
<dependency>
    <groupId>com.wf2311.jclipper</groupId>
    <artifactId>exception-handler-spring-boot-starter</artifactId>
    <version>2022.1-SNAPSHOT</version>
</dependency>
```

## 异常告警配置
目前支持Http接口异常与Dubbo接口异常进行钉钉机器人告警与企业微信告警

### token获取
#### 钉钉机器人
1. 配置告警机器人，获取token
> 参考官方文档[自定义机器人接入](https://developers.dingtalk.com/document/robots/custom-robot-access) 

在想要进行告警通知的钉钉群中配置自定义类型的机器人后，获取url中的token,
即webhook url信息中的`xxxxxxxxxx`
```bash
https://oapi.dingtalk.com/robot/send?access_token=xxxxxxxxxx
```

#### 企业微信机器人
> `选中需要添加机器人的群` -> `右键选择"管理聊天信息"` -> `添加机器人`

添加完机器人后在，，获取url中的token，
即webhook url信息中的`xxxxxxxxxx`
```bash
https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxx
```

### 告警参数配置
- Properties方式
```properties
#业务或参数错误是否需要进行告警，默认为false
jclipper.alert.alert-when-biz-code=false
#环境名称，例如：test-20.2,pre-207，非必填，当填写该值时，会在告警信息标题中显示环境名称
jclipper.alert.env=test-20.2
#是否启用钉钉告警，默认为false
jclipper.alert.dingtalk.enable=true
#钉钉告警的webhook token
jclipper.alert.dingtalk.token=<TOKEN?>
# 需要艾特的用户，使用数组形式进行指定（非必须）
jclipper.alert.dingtalk.at-mobiles[0]=xxx
jclipper.alert.dingtalk.at-mobiles[1]=xxx
#是否进行艾特全员，默认为false
jclipper.alert.dingtalk.at-all=false
#是否启用企业微信告警，默认为false
jclipper.alert.qiye-wechat.enable=true
#企业微信告警的webhook token
jclipper.alert.qiye-wechat.token=<TOKEN?>
# 需要艾特的用户，使用数组形式进行指定（非必须）
jclipper.alert.qiye-wechat.at-mobiles[0]=xxx
jclipper.alert.qiye-wechat.at-mobiles[1]=xxx
#是否进行艾特全员，默认为false
jclipper.alert.qiye-wechat.at-all=false
```

- YAML方式
```yaml
jclipper:
  alert:
    alert-when-biz-code: false #业务或参数错误是否需要进行告警，默认为false
    env: test-20.2 #环境名称，例如：test-20.2,pre-207，非必填，当填写该值时，会在告警信息标题中显示环境名称
    dingtalk: #钉钉告警配置
     enable: true #是否启用钉钉告警，默认为false
     token: <TOKEN> #钉钉告警的webhook token
     at-mobiles: # 需要艾特的用户，使用数组形式进行指定（非必须）
       - 18111234123
       - 18111234124
     at-all: false  #是否进行艾特全员，默认为false
    work-wechat: #企业微信告警配置
      enable: true  #是否启用企业微信告警，默认为false
      token: <TOKEN>  #企业微信告警的webhook token
      at-mobiles: # 需要艾特的用户，使用数组形式进行指定（非必须）
        - 18111234123
        - 18111234124
      at-all: false  #是否进行艾特全员，默认为false
```
更多自定义告警配置参考[jclipper-alert-notice](../../jclipper-alert-notice/README.md)模块