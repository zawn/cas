---
layout: 默认
title: CAS-配置票证组件
category: 售票处
---

# 售票处

有两个核心可配置票证组件：

* `TicketRegistry` 提供持久的票证存储。
* `ExpirationPolicy` 提供票证过期语义的策略框架。

## 票务登记处

部署环境和技术专长通常确定特定的 `TicketRegistry` 组件。 对于HA部署，建议使用缓存支持的实现，而默认的内存中注册表可能适用于小型部署。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ticket-registry)。

### 我该如何选择？

菜单上有各种各样的票务登记处。 选择标准概述如下：

- 选择一种您最熟悉的技术，并具有技巧和耐心来对故障进行排除，调整和扩展以取得胜利。
- 选择一种不会强制将CAS配置绑定到群集中任何单个服务器/节点的技术，因为这会带来自动扩展问题和手动工作。
- 选择一种适合您的网络和防火墙配置，并且根据您的网络拓扑具有足够性能和可靠性的技术。
- 选择一种技术，该技术在 *的预期负载*下会显示出令人鼓舞的结果，并已进行了 [性能和压力测试](../high_availability/High-Availability-Performance-Testing.html)。
- 选择一种尽可能不依赖外部流程和系统，自力更生且自我约束的技术。

上面仅概述了您可能需要考虑的建议和准则。 每个选项都有各自的优缺点，最后，您必须确定哪些缺点或优势为您提供了最佳的体验。

### 基于缓存的票证注册表

基于缓存的票证注册表为高可用性 部署中的票证存储提供了高性能的解决方案。 提供了用于以下缓存技术的组件：

* [默认](Default-Ticket-Registry.html)
* [淡褐色](Hazelcast-Ticket-Registry.html)
* [高速缓存](Ehcache-Ticket-Registry.html)
* [点燃](Ignite-Ticket-Registry.html)
* [记忆快取](Memcached-Ticket-Registry.html)
* [Infinispan](Infinispan-Ticket-Registry.html)

### 基于消息的票证注册中心

* [JMS](Messaging-JMS-Ticket-Registry.html)

### RDBMS票证注册中心

基于RDBMS的票证注册表提供了跨多个CAS节点的分布式票证存储。 提供了用于以下缓存技术的组件：

* [JPA](JPA-Ticket-Registry.html)

### NoSQL票证注册表

CAS还为票据存储和持久性提供了对Redis，MongoDb和Apache

* [Infinispan](Infinispan-Ticket-Registry.html)
* [Couchbase](Couchbase-Ticket-Registry.html)
* [雷迪斯](Redis-Ticket-Registry.html)
* [CouchDb](CouchDb-Ticket-Registry.html)
* [MongoDb](MongoDb-Ticket-Registry.html)
* [DynamoDb](DynamoDb-Ticket-Registry.html)

### 安全缓存复制

许多基于缓存的票证注册表都支持通过网络安全地复制票证数据（ 以便在复制尝试时对票证进行加密和签名，以防止嗅探和窃听。 [有关更多信息，请参见本指南](../installation/Ticket-Registry-Replication-Encryption.html)

## 票证过期政策

CAS支持可插拔和可扩展的策略框架，以控制 票证授予票证（TGT）和服务票证（ST）的过期策略。 [有关如何配置到期策略的详细信息，请参阅本指南](Configuring-Ticket-Expiration-Policy.html)
