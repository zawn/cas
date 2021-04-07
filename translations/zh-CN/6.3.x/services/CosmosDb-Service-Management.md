---
layout: 违约
title: CAS - 宇宙数据库服务注册处
category: 服务业
---

# 宇宙数据库服务注册处

存储注册服务数据在 [蔚蓝宇宙数据库](https://docs.microsoft.com/en-us/azure/cosmos-db/introduction) 实例。

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 宇宙数据库 - 服务 - 注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cosmosdb-service-registry)。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="com.microsoft.azure" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```


## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>