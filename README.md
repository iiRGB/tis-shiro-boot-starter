#### tools-shiro-boot-starter

从tools-web-tools中分离出shiro模块，用于其他应用的引用。

##### 使用方法

1. 添加依赖

```xml
<dependency>
    <groupId>org.tis</groupId>
    <artifactId>tools-shiro-boot-starter</artifactId>
     <version>1.0.0-SNAPSHOT</version>
</dependency>
```

2. 在配置中增加如下：

```yaml
abf:
  shiro: 
    enabled: true #是否启用
    anons:  # 可匿名掉用的接口
    permission:
      enabled: true # 是否开启角色接口控制
    dubbo:
      application:
        name: tools-web-shiro 
        owner: zhaoch
        organization: org.tis
        logger: slf4j
      registry:
        address: zookeeper://139.196.145.67:2181
        timeout: 50000
```