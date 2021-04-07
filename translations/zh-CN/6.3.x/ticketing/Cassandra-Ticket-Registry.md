---
layout: 默认
title: CAS-卡桑德拉机票登记处
category: 售票处
---

# 卡桑德拉门票登记处

通过在WAR叠加中包含以下依赖项来启用Cassandra集成：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-cassandra-ticket-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

该注册表将票证存储在 [Apache Cassandra](http://cassandra.apache.org/) 实例中。 票证预计将在 `个小城堡` 表 找到/存储，默认写一致性为 `LOCAL_QUORUM` ，读一致性为 `ONE`。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="com.datastax.driver" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cassandra-ticket-registry)。
