---
layout: 违约
title: CAS - 雷迪斯票务注册处
category: 票务
---

# 雷迪斯票务登记处

通过在 WAR 叠加中包括以下依赖性，支持重新集合：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 重新迪斯 - 票卡注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此注册表将门票存储在一个或多个 [雷迪斯](http://redis.io/) 实例中。 此组件使用的 [弹簧数据重新](http://projects.spring.io/spring-data-redis/) 库将 Redis 显示为 密钥/值存储，接受 `字符串` 键和 CAS 票证对象为值。 关键是从 `CAS_TICKET开始：`。

雷迪斯机票注册表支持雷迪斯哨兵，这为雷迪斯提供了很高的可用性。 实际上，这意味着使用哨兵可以创建一个 Redis 部署，无需人工干预即可抵抗某些类型的故障。 Redis Sentinel 还提供其他附带任务，如监控、通知和充当客户配置提供商。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-ticket-registry)。

### 驱逐政策

Redis 通过其时间活期设置管理缓存对象的内部驱逐政策。 超时是票的 `时间到` 值。 因此，您需要确保缓存足够长的时间支持门票 个人过期政策，并在必要时让 CAS 将门票作为自己的清洁剂的一部分进行清洁。
