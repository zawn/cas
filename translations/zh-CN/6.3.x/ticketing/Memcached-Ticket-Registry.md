---
layout: 违约
title: CAS - 梅卡奇票务注册处
category: 票务
---

# 被没收的机票注册处

通过在 WAR 叠加中包括以下依赖性，启用了 Memcach 集成：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 梅卡奇 - 票务注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此注册表在一个或多个 [](http://memcached.org/) 实例中存储门票。 Memcached 将数据存储在分布式缓存中的多个节点中的多个节点中，从而避免了在节点之间复制 或其他共享数据的要求。 一个决定性函数用于定位节点， _N'_，用于存储 关键 _K_：

    N'=f（h（K），N1，N2，N3，... Nm）

其中 _h（K）_ 是关键 _K_的哈希， _N1.。 Nm_ 是缓存节点的集合， _N'_ ∈ _N.。。 恩姆_。

该函数具有决定性，因为它始终为给定密钥和缓存节点集生成相同的结果。 请注意，可用缓存节点集的更改可能会产生存储密钥的不同目标节点。

实际的 memcach 实现可以通过以下选项之一进行支持，预计在叠加中定义。

## 间谍梅卡奇

通过 [间谍图书馆](https://code.google.com/p/spymemcached/)提供支持。 这是一个简单的、异步的、 单线程 memcached 客户端，它应该是大多数部署的默认选择。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 机械 - 间谍</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## AWS弹性缓存

您还可以使用 [AWS 弹性缓存](https://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/AutoDiscovery.html) 这是一个 Web 服务，便于在云中设置、管理和扩展分布式内存 数据存储或缓存环境。 它提供了高性能、可扩展且经济高效的缓存 解决方案，同时消除了与部署和管理分布式缓存环境相关的复杂性。

对于运行 Memcached 引擎的集群，ElastiCache 支持自动发现-客户端程序自动识别缓存群集中的所有节点的能力 ， 并启动并维护与所有这些节点的连接。 通过自动发现， CAS不需要手动连接到单个缓存节点：相反，CAS 连接到一个 memcached 节点并检索节点列表。 从该列表中，CAS 知道聚类中的节点的其余 ，并且可以连接到其中任何一个节点。 您不需要硬 配置中的单个缓存节点端点编码

聚类中的所有缓存节点都保留有关所有其他节点的元数据列表。 每当从群集中添加或删除节点时，都会更新此元数据。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 机械 - aws - 弹性</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置考虑

有三个核心配置问题与机械：

1. 哈希算法
2. 节点定位器策略
3. 对象序列化机制

### 哈希算法

哈希算法用于将键值转换为 memcached 存储密钥，该存储密钥能够唯一识别 相应的值。 哈希算法的选择对故障转移行为有影响，而故障转移行为对于 HA 部署 非常重要。 建议使用 `FNV1_64_HASH` 算法，因为它提供了良好的速度平衡和低 碰撞速率：看到 [爪哇](https://github.com/couchbase/spymemcached/blob/2.8.1/src/main/java/net/spy/memcached/DefaultHashAlgorithm.java) 替代品。

### 节点定位器

节点定位器是 底层间谍记忆库提供的 memcached 客户端的决定性节点选择功能。 有两种选择：

1. [ARRAY_MOD](https://github.com/couchbase/spymemcached/blob/2.8.1/src/main/java/net/spy/memcached/ArrayModNodeLocator.java)
2. [一致](https://github.com/couchbase/spymemcached/blob/2.9.0/src/main/java/net/spy/memcached/KetamaNodeLocator.java)

阵列模组机制是默认的，适用于 预期为一致的模组池中的节点数量时的情况。 该算法只需将索引计算到 memcach 节点的阵列中：

    哈希（键）% 长度（节点）

显然，选定的索引是 memcached 节点数的函数，因此节点数的差异在选定的存储键的节点中产生 方差，这是不可取的。

一致的策略通常提供一个目标节点，该节点不会随节点数量而变化。 此策略 应用于 memcach 池可能动态增长或收缩的情况，包括由于频繁的节点 故障。


### 对象序列化

存储了大量数据，因此 CAS 门票必须在存储前串行到字节阵列中。 CAS 基于 [Kryo](https://code.google.com/p/kryo/) 系列化 框架， `KryoTranscoder` 定制序列化组件。 此组件建议采用默认的 Java 序列化机制，因为它可生成更 紧凑的数据，从而既有利于存储要求，也有利于吞吐量。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#memcached-ticket-registry)。

## 高可用性考虑

Memcached 不提供复制的设计，但客户端是宽容的节点故障与 `失败模"重新分配"`。 在此模式下，写入失败只会导致客户端将节点标记为失败 并将其从可用节点集中删除。 随后，它将节点定位功能与已缩小的 节点重新计算，以找到存储密钥的新节点。 如果节点位置函数选择同一节点， 这可能是 _"一致_ 策略"，则将计算备份节点。 该值从故障转移节点 写入并读取，直到主节点恢复。 客户端将定期检查故障节点的活力 ，并在恢复后立即将其恢复到节点池中。 当主节点复活时，如果它包含特定密钥的值 ，它将取代故障转移节点已知的值。 在这种情况下，对 CAS 行为的最常见影响 当出票票具有重复值时，可能会影响单个签出 并阻止使用服务。 特别是，当故障节点恢复时，在 故障转移服务处于活动状态时访问的服务和强制身份验证将丢失。 在大多数情况下，这种行为是可以容忍的， 但在重新加入缓存池之前重新启动故障节点上的 memcached 服务是可以避免的。

_重新分配_ 模式的读数故障导致节点从可用节点集中删除，计算故障转移节点 ，并从该节点读取值。 在大多数情况下，这会导致未找到的关键情况。 对 CAS 行为的影响取决于所要求的机票类型：

* 服务票 - 请求的机票将拒绝服务访问，但允许后续尝试，因为 将生成和验证新机票。
* 出票 - 将终止 SSO 会话，并需要重新认证。

因此，对于可以进行重新认证的环境，阅读失败是完全无害的。
