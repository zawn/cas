---
layout: 默认
title: CAS-MongoDb票务注册表
category: 售票处
---

# MongoDb票务注册表

通过在WAR叠加层中包含以下依赖项来启用MongoDb票务注册表集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-mongo-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此注册表将票证存储在一个或多个 [MongoDb](https://www.mongodb.com/) 实例中。 票证会自动转换并以JSON形式包装到文档对象中。 创建特殊索引 ，以使MongoDb处理每个文档和清理任务的到期。 请注意，CAS通常会尝试自动创建相关集合以管理不同的凭单类型。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-ticket-registry)。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下 级：

```xml
...
<Logger name="com.mongo" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
