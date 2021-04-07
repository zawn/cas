---
layout: 默认
title: CAS-Redis票务注册表
category: 售票处
---

# Redis票务注册表

通过在WAR叠加中包含以下依赖项来启用Redis集成：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-redis-ticket-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此注册表将票证存储在一个或多个 [Redis](http://redis.io/) 实例中。 该 [spring data redis](http://projects.spring.io/spring-data-redis/) 库将Redis呈现为 键/值存储，该存储接受 `String` 键和CAS票证对象作为值。 密钥以 `CAS_TICKET：`开头。

Redis票务注册表支持Redis Sentinel，它为Redis提供了高可用性。 实际上，这意味着使用Sentinel可以创建Redis部署，该部署可以在无需人工干预的情况下抵抗某些类型的故障。 Redis Sentinel还提供其他附带任务，例如监视，通知，并充当客户端的配置提供程序。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-ticket-registry)。

### 驱逐政策

Redis通过其活动时间设置来管理缓存对象的内部逐出策略。 超时是故障 `` 值。 因此，您需要确保缓存的生存期足够长，以支持 单独到期策略，并在必要时让CAS作为自己的清除器的一部分来清理票证。
