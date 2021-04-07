---
layout: 违约
title: CAS - 点火票务注册处
category: 票务
---

# 点火票务登记处
通过在 WAR 叠加中包括以下依赖性，启用点火集成：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡服务器支持-点火-票证注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此注册表以 [点火](http://ignite.apache.org/) 实例存储门票。


## 分布式缓存

HA 架构推荐分布式缓存，因为它们在票证存储子系统中提供故障耐受性。


## TLS 复制

点火支持通过 TLS 复制由两个或多个节点组成的分布式缓存。 要了解有关点火的 TLS 复制的更多信息， [请参阅此资源](https://apacheignite.readme.io/docs/ssltls)。


## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ignite-ticket-registry)。

## 故障 排除

* 您需要确保允许跨 CAS 节点进行网络通信，并且没有防火墙或其他组件阻止流量。
* 如果使用 CAS 实例外部的节点，请确保每个缓存管理器指定与 Ignite 配置匹配的名称 本身。
* 您可能还需要调整您的到期策略，以允许更大的时间跨度，特别是服务票，具体取决于整个 CAS 节点的网络 流量和通信延迟，尤其是在节点试图加入群集的情况下。
