---
layout: 默认
title: CAS-Ehcache票务注册表
category: 售票处
---

# Ehcache v3票务注册表

通过在WAR叠加中包含以下依赖项来启用Ehcache 3.x集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-ehcache3-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

该注册表使用 [Ehcache 3.x](http://ehcache.org/) 缓存库 和 [（可选的Terracotta集群](https://www.ehcache.org/documentation/3.3/clustered-cache.html)存储票证。

## 具有磁盘持久性的内存中存储

Ehcache 3.x在没有Terracotta的情况下不支持分布式缓存，因此在不指向Terracotta 服务器或群集的情况下使用它不支持一次使用多个CAS服务器。 可以使用根目录和per-cache-size-on-disk属性配置 的位置和大小。 如果将persist-on-disk属性 设置为true，则高速缓存将在重新启动后幸免。

### 兵马俑聚类

通过将此Ehcache模块指向Terracotta服务器，则多个CAS服务器可以共享票证。 CAS使用 `` 创建Terracotta群集配置。 运行Terracotta服务器的一种简单方法是使用 [docker容器](https://github.com/Terracotta-OSS/docker)。

```bash
docker run --rm --name tc-server -p 9410：9410 -d \
 --env OFFHEAP_RESOURCE1_NAME = main \
 --env OFFHEAP_RESOURCE2_NAME = extra \
 --env OFFHEAP_RESOURCE1_SIZE = 256 \
 --env OFFHEAP_RESOURCE2_SIZE = 16 \
兵马俑/terracotta-server-oss:5.6.4
```

[舵图](https://github.com/helm/charts/tree/master/stable/terracotta)可以轻松地在Kubernetes上运行Terracotta集群。

#### 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ehcache-3-ticket-registry)。 CAS当前不支持或不需要XML配置来配置Ehcache。

### 驱逐政策

可以将Ehcache配置为“永久”，在这种情况下，CAS的常规清理过程将删除过期的凭单。 如果将 eternal属性设置为false，则将基于各个缓存的元数据设置存储超时。

# Ehcache v2票务注册表

由于Ehcache 2.x代码库的状态相对不受支持，因此不建议使用此模块，并且在将来的CAS版本中 与Ehcache 3.x库不同，它可以直接在CAS服务器之间复制，而无需 使用外部缓存集群（例如，Ehcache 3.x中的Terracotta）。

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>不推荐使用此功能，并计划在将来将其删除。</strong> 如果可以，请考虑使用
CAS中的Ehcache v3票证注册表功能来处理此集成。</p>
</div>

通过在WAR叠加中包含以下依赖项来启用Ehcache集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-ehcache-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

该注册表使用 [Ehcache](http://ehcache.org/) 版本2.x库存储票证。

## 分布式缓存

对于HA体系结构，建议使用分布式缓存，因为它们在票证存储 子系统中具有容错能力。 创建单个高速缓存实例以容纳所有类型的票证，并在配置中定义的节点群集之间

### RMI复制

Ehcache支持由两个或更多节点组成的分布式缓存的 [RMI](https://docs.oracle.com/javase/tutorial/rmi/index.html) 要了解有关使用Ehcache进行 复制的更多信息，请参阅 [](https://www.ehcache.org/documentation/2.8/replication/rmi-replicated-caching.html)。

#### 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ehcache-ticket-registry)。

配置中提到的 `ehcache-replicated.xml` 的Ehcache配置如下。 请注意，将 `${ehcache.otherServer}` 替换为系统属性： `-Dehcache.otherserver = cas2`。

```xml
<ehcache name="ehCacheTicketRegistryCache"
    updateCheck="false"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd">

    <diskStore path="java.io.tmpdir/cas"/>

    <-自动对等点发现
    <cacheManagerPeerProviderFactory
    class="net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory"
    properties="peerDiscovery=automatic, multicastGroupAddress=230.0.0.1, multicastGroupPort=4446, timeToLive=32"
    propertySeparator="," />
    ->

    <- ！手册对等体发现->
    <cacheManagerPeerProviderFactory
        class="net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory"
        properties="peerDiscovery=manual,rmiUrls=//${ehcache.otherServer}:41001/proxyGrantingTicketsCache| \
            //${ehcache.otherServer}:41001/ticketGrantingTicketsCache|//${ehcache.otherServer}:41001/proxyTicketsCache| \
            //${ehcache.otherServer}:41001/oauthCodesCache|//${ehcache.otherServer}:41001/samlArtifactsCache| \
            //${ehcache.otherServer}:41001/oauthDeviceUserCodesCache|//${ehcache.otherServer}:41001/samlAttributeQueryCache| \
            //${ehcache.otherServer}:41001/oauthAccessTokensCache|//${ehcache.otherServer}:41001/serviceTicketsCache| \
            //${ehcache.otherServer}:41001/oauthRefreshTokensCache|//${ehcache.otherServer}:41001/transientSessionTicketsCache| \
            //${ehcache.otherServer}:41001/oauthDeviceTokensCache" />

    <cacheManagerPeerListenerFactory
        class="net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory"
        properties="port=41001,remoteObjectPort=41002" />
</ehcache>
```

### 驱逐政策

Ehcache通过空闲和活动设置管理缓存对象的内部驱逐策略。 这些设置控制用于存储各种故障单类型的缓存的常规策略。 通常， 缓存有效期足够长，以支持票证的各个过期策略，并让 CAS清除票证作为其自身清除器的一部分。

### 故障排除准则

* 您将需要确保允许跨CAS节点的网络通信，并且没有防火墙或其他组件 阻止流量。

* 如果您正在使用主动防火墙的服务器上运行此，你可能需要指定 固定 `remoteObjectPort`中，内 `cacheManagerPeerListenerFactory`。
* 根据环境设置和使用的Ehcache的版本，您可能还需要调整 `shared` 设置。
* 确保每个缓存管理器都指定了一个与Ehcache配置本身匹配的名称。
* 您可能还需要调整您的过期策略，以允许更大的时间跨度，特别是 的依赖于整个CAS网络流量和通信延迟的服务票据节点尤其 中的情况下，一个节点试图加入群集。
