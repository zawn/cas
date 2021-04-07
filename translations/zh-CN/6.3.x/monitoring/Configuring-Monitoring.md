---
layout: 默认
title: CAS-监控
category: 监控 & 统计
---

# CAS监控

可以定义CAS监视器，以报告票证注册表的健康状况以及与CAS使用的系统的其他基础连接。 Spring Boot提供了许多称为 `HealthIndicator`的监视器，这些监视器在存在特定设置（即 `spring.mail。*`）的情况下被激活。 CAS本身基于下面列出的相同组件提供了许多其他监视器，它们的操作可能需要特定依赖模块及其相关设置的组合。

## 默认

默认监视器将向后报告简短的内存和票证统计信息。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-core-monitor</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#monitoring) 和 [本指南](../configuration/Configuration-Properties.html#memory)。

<div class="alert alert-warning"><strong>青年汽车</strong><p>为了准确，可靠地报告票证统计信息，您将受基础票证注册表的支配，无法以高性能的方式支持该行为，这意味着必须考虑并仔细调整基础结构，网络功能和延迟。 这可能在群集部署中变得特别相关，这取决于所选择的票证注册表，CAS可能需要通过运行分布式查询来计算票证使用率， <i>询问</i></p></div>

## 记忆快取

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-memcached-monitor</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#memcached-monitors)。

可以通过以下选项之一来支持实际的内存缓存实现，这些选项预计将在叠加层中定义。

### Spymemcached

[spymemcached库](https://code.google.com/p/spymemcached/)启用支持。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-memcached-spy</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### AWS ElastiCache

对于运行Memcached的发动机集群，ElastiCache支持自动发现，能力 的客户端程序来自动识别的所有节点的缓存群集， 和启动和维持所有这些节点的连接。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-memcached-aws-elasticache</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 高速缓存

监视由Ehcache支持的缓存的状态和状态。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-ehcache-monitor</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cache-monitors)。

## MongoDb

监视MongoDb数据库的状态和可用性。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-mongo-monitor</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-monitors)。

## 淡褐色

监视由Hazelcast支持的缓存的状态和状态。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-hazelcast-monitor</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cache-monitors)。

## JDBC

监视关系SQL数据库的状态和可用性。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-jdbc-monitor</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-monitoring)。

## LDAP

监视LDAP服务器的状态和可用性。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-ldap-monitor</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-server-monitoring)。
