---
layout: 违约
title: CAS - 迪纳莫德布票务注册处
category: 票务
---

# 迪纳莫德布票务注册处

DynamoDb 票证注册表集成通过在 WAR 叠加中包括以下依赖关系而实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 发电机 - 票务注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此注册表在 [DynamoDb](https://aws.amazon.com/dynamodb/) 实例中存储门票。 每个票务类型都链接到一个独特的表。

## 配置

您需要向 CAS 提供您的 [AWS 凭据](https://aws.amazon.com/console/)。 此外，为了更好地了解 DynamoDb 的核心组件和概念 ，请首先 [本指南](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html) 开始。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-ticket-registry)。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
