---
layout: 默认
title: CAS-Redis服务注册中心
category: 服务
---

# Redis服务注册表

此服务注册表将票证存储在一个或多个 [Redis](http://redis.io/) 实例中。 该 [spring data redis](http://projects.spring.io/spring-data-redis/) 库将Redis呈现为 键/值存储，该存储接受 `String` 键和CAS服务定义对象作为值。 密钥以 `CAS_SERVICE：`开头。

Redis服务注册表支持Redis Sentinel，它为Redis提供了高可用性。 实际上，这意味着使用Sentinel可以创建Redis部署，该部署可以在无需人工干预的情况下抵抗某些类型的故障。 Redis Sentinel还提供其他附带任务，例如监视，通知，并充当客户端的配置提供程序。

通过在叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-redis-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-service-registry)。

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)