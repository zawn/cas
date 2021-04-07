---
layout: 违约
title: CAS - 蒙哥银行票务注册处
category: 票务
---

# 蒙古银行票务登记处

MongoDb 票证注册表集成通过在 WAR 叠加中包括以下依赖关系而实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 蒙古票 - 注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此注册表在一个或多个 [MongoDb](https://www.mongodb.com/) 实例中存储门票。 门票作为 JSON 自动转换并包裹成文档对象。 创建特殊指标，让 MongoDb 处理每个文档的过期和清理任务。 请注意，CAS 通常尝试自动创建相关集合以管理不同的票证类型。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-ticket-registry)。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下 级别：

```xml
...
<Logger name="com.mongo" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
