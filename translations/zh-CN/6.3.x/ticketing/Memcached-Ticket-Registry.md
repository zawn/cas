---
layout: 默认
title: CAS-Memcached票务注册表
category: 售票处
---

# Memcached票务注册表

通过在WAR叠加层中包含以下依赖项来启用Memcached集成：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-memcached-ticket-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此注册表将票证存储在一个或多个 [memcached](http://memcached.org/) 实例中。 Memcached将数据存储在分布式缓存中许多节点中的一个节点中，从而避免了在节点之间 确定性函数来定位节点， _N”_，在其上存储 键 _ķ_：

    N'= f（h（K），N1，N2，N3，... Nm）

其中 _H（K）_ 是密钥的散列 _ķ_， _N1 ...... 纳米_ 是一组高速缓存节点，并且 _N”_ ∈ _Ñ... Nm_。

该函数是确定性的，因为它对于给定的键和一组高速缓存节点始终产生相同的结果。 注意，一组可用缓存节点的更改可能会产生一个不同的目标节点，在该目标节点上存储密钥。

可以通过以下选项之一来支持实际的内存缓存实现，这些选项预计将在叠加层中定义。

## Spymemcached

[spymemcached库](https://code.google.com/p/spymemcached/)启用支持。 这是一个简单的异步 单线程memcached客户端，应将其作为大多数部署的默认选择。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-memcached-spy</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## AWS ElastiCache

您还可以使用 [AWS ElastiCache](https://docs.aws.amazon.com/AmazonElastiCache/latest/UserGuide/AutoDiscovery.html) ，这是一项Web服务，可轻松设置，管理和扩展 数据存储或缓存环境。 它提供了高性能，可扩展且经济高效的缓存 解决方案，同时消除了与部署和管理分布式缓存环境相关的复杂性。

对于运行Memcached的发动机集群，ElastiCache支持自动发现，能力 的客户端程序来自动识别的所有节点的缓存群集， 和启动和维持所有这些节点的连接。 使用自动发现， CAS不需要手动连接到单个缓存节点；相反，CAS连接到一个 Memcached节点并检索节点列表。 从该列表中，CAS知道 个节点，并且可以连接到其中的任何一个。 您无需对配置中的各个缓存节点端点

集群中的所有缓存节点都维护有关所有其他节点的元数据列表。 每当在群集中添加节点或从群集中删除节点时，此元数据都会更新。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-memcached-aws-elasticache</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置注意事项

memcached涉及三个核心配置问题：

1. 哈希算法
2. 节点定位器策略
3. 对象序列化机制

### 哈希算法

哈希算法用于将键值转换为唯一标识 对应值的内存缓存存储键。 哈希算法的选择对故障转移行为有影响，对于HA部署 推荐使用 `FNV1_64_HASH` 算法，因为它在速度和低 碰撞率之间提供了很好的平衡。有关替代方法，请参见 [javadocs](https://github.com/couchbase/spymemcached/blob/2.8.1/src/main/java/net/spy/memcached/DefaultHashAlgorithm.java)

### 节点定位器

底层spymemcached库提供的memcached客户端的确定性节点选择功能。 有两种选择：

1. [ARRAY_MOD](https://github.com/couchbase/spymemcached/blob/2.8.1/src/main/java/net/spy/memcached/ArrayModNodeLocator.java)
2. [持续的](https://github.com/couchbase/spymemcached/blob/2.9.0/src/main/java/net/spy/memcached/KetamaNodeLocator.java)

数组模数机制是默认设置，适用于内存缓存池中的节点数预期 该算法仅计算内存缓存节点数组的索引：

    hash（key）％长度（节点）

显然，所选索引是内存缓存节点数的函数，因此，节点数的方差会导致所选存储键的节点的方差为

一致策略通常提供不随节点数量而变化的目标节点。 在内存缓存池可能动态增长或收缩的情况下（包括由于频繁发生的节点 故障），应使用此策略


### 对象序列化

Memcached存储字节数据，因此在存储之前必须将CAS票证序列化为字节数组。 CAS附带 一个自定义序列元件 `KryoTranscoder` 基于该 [KRYO](https://code.google.com/p/kryo/) 系列化 的框架。 与默认的Java序列化机制相比，建议使用此组件，因为它会产生更多的 压缩数据，这既有利于存储需求，又有利于吞吐量。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#memcached-ticket-registry)。

## 高可用性注意事项

Memcached在设计上不提供复制功能，但是客户端可以容忍节点故障，其 `failureMode =“ Redistribute”`。 在这种模式下，写失败将仅导致客户端将节点标记为失败 ，并将其从可用节点集中删除。 随后，它使用减少的 节点集重新计算节点定位函数，以找到要在其上存储密钥的新节点。 如果节点定位功能选择了相同的节点（ _CONSISTENT_ 策略而言可能为 ，则将计算一个备用节点。 该值将写入 读取，直到主节点恢复为止。 客户端将定期检查故障节点的活动性 并在其恢复后立即将其还原到节点池中。 当主节点复活时，如果它包含 ，它将取代故障转移节点已知的值。 在这种情况下，当授予票证的票证具有重复值时，将对 产生最普遍的影响 并阻止访问服务。 故障转移服务处于活动状态时进行的访问服务和强制身份验证将丢失。 在大多数情况下，此行为是可以接受的，为 但可以通过在重新加入缓存池之前在故障节点上重新启动memcached服务来避免此行为。

_Redistribute_ 模式下的读取失败会导致将节点从可用节点集中删除， ，然后从该节点读取值。 在大多数情况下，这会导致找不到关键情况。 CAS行为的影响 取决于请求的票证类型：

* 服务票证-将拒绝所请求票证的服务访问，但由于 会生成并验证新票证，因此允许进行后续尝试。
* 授予票证的票证-SSO会话将终止，并且需要重新认证。

因此，对于可以接受重新身份验证的环境，读取失败完全是无害的。
