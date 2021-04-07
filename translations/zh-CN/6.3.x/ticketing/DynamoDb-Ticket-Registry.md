---
layout: 默认
title: CAS-DynamoDb票务注册表
category: 售票处
---

# DynamoDb票务注册表

通过在WAR叠加中包含以下依赖项来启用DynamoDb票证注册表集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-dynamodb-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

该注册表将票证存储在 [DynamoDb](https://aws.amazon.com/dynamodb/) 实例中。 每种故障单类型都链接到一个不同的表。

## 配置

您将需要向CAS提供您的 [AWS凭证](https://aws.amazon.com/console/)。 此外，为了更好地理解 DynamoDb的核心组件和概念的，请 [开始本指南](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html) 首。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-ticket-registry)。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
