---
layout: 默认
title: CAS-Cassandra服务注册中心
category: 服务
---

# Cassandra服务注册表

将注册的服务数据存储在 [Apache Cassandra](http://cassandra.apache.org/) 实例中。 `casservices` 表中找到/存储，默认写一致性为 `LOCAL_QUORUM` ，读一致性为 `ONE`。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-cassandra-service-registry</artifactId>
     <version>${cas.version}</version>
</dependency>                                                            
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cassandra-service-registry)。

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

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)
