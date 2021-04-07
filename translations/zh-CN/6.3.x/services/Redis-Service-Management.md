---
layout: 违约
title: CAS - 雷迪斯服务注册处
category: 服务业
---

# 重新分配服务注册表

此服务注册表在一个或多个 [雷迪斯](http://redis.io/) 实例中存储门票。 此组件使用的 [弹簧数据重新](http://projects.spring.io/spring-data-redis/) 库将 Redis 显示为 密钥/值存储，接受 `字符串` 密钥和 CAS 服务定义对象为值。 关键是从 `CAS_SERVICE开始：`。

雷迪斯服务注册表支持雷迪斯哨兵，它为雷迪斯提供了高可用性。 实际上，这意味着使用哨兵可以创建一个 Redis 部署，无需人工干预即可抵抗某些类型的故障。 Redis Sentinel 还提供其他附带任务，如监控、通知和充当客户配置提供商。

支持通过在叠加中包括以下依赖关系而启用：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-重新-服务-注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-service-registry)。

## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>