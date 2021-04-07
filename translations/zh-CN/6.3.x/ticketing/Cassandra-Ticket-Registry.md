---
layout: 违约
title: CAS - 卡桑德拉票务注册处
category: 票务
---

# 卡桑德拉票务登记处

卡桑德拉集成通过在战争覆盖中包括以下依赖性而实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-卡桑德拉-票证-注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此注册处存储 [阿帕奇卡桑德拉](http://cassandra.apache.org/) 实例中的门票。 门票预计将被发现/存储在 `` 桌 与默认书写一致性 `LOCAL_QUORUM` 和读取一 `一`的一致性。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="com.datastax.driver" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cassandra-ticket-registry)。
