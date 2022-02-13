# dynamic-arthas-spring-boot-starter

## 参数配置：
```yaml
arthas:
  tunnel-server: ws://<ip>:<port>/ws
  #客户端id,应用名@随机值，js会截取前面的应用名
  agent-id: ${spring.application.name}@${random.value}
  http-port: 0  #为0表示随机
  telnet-port: 0  #为0表示随机
```
或
```properties
arthas.tunnel-server=ws://<ip>:<port>/ws
#客户端id,应用名@随机值，js会截取前面的应用名
arthas.agent-id=${spring.application.name}@${random.value}
#为0表示随机
arthas.http-port=0
#为0表示随机
arthas.telnet-port=0
```

是否启用arthas是通过`spring.arthas.enabled`来控制的