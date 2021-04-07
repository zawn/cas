---
layout: 违约
title: CAS - 埃卡奇票务注册处
category: 票务
---

# 埃卡奇v3票务登记处

Ehcache 3.x 集成通过在 WAR 叠加中包括以下依赖关系而实现：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-埃卡切3-票证注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

这个登记处存储门票使用 [Ehcache 3.x](http://ehcache.org/) 缓存库 和 [一个可选的兵器集群](https://www.ehcache.org/documentation/3.3/clustered-cache.html)。

## 内存存储与磁盘持久性

Ehcache 3.x 不支持在没有兵器的情况下分发缓存，因此无需指向兵马甲 服务器或群集即可使用它，因此不支持一次使用多个 CAS 服务器。 磁盘缓存的位置和大小 可以使用根目录和每个缓存大小的磁盘属性进行配置。 如果磁盘上的持久属性 设置为真，则缓存将在重新启动后存活下来。

### 兵器聚类

通过将此 Ehcaache 模块指向兵马甲服务器，然后多个 CAS 服务器可以共享门票。 CAS使用 `自动创建` 来创建兵马甲群组配置。 运行兵器服务器的一个简单方法是使用 [码头](https://github.com/Terracotta-OSS/docker)容器。

```bash
码头运行-rm-名称tc服务器-p 9410：9410-d=
 -env OFFHEAP_RESOURCE1_NAME]主-
 -env OFFHEAP_RESOURCE2_NAME=额外-

-env OFFHEAP_RESOURCE1_SIZE=256 [
 - env OFFHEAP_RESOURCE2_SIZE=16 ]  赤陶/陶器服务器-oss：5.6.4
```

使用</a>的兵马
掌舵图，可以在库伯内特斯上运行兵器群。</p> 



#### 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ehcache-3-ticket-registry)。 CAS目前不支持或需要XML配置来配置埃卡奇。 



### 驱逐政策

Ehcaache 可以配置为 "永恒"， 在这种情况下， Cas 的常规清洁过程将删除过期的门票。 如果 永恒属性设置为错误，则存储超时将基于单个缓存的元数据设置。



# 埃卡奇v2票务登记处

由于 Ehcache 2.x 代码库的相对不受支持的状态，此模块被弃用，并可能在未来的 CAS 版本中 删除。 与 Ehcache 3.x 库不同，它可以在 CAS 服务器之间直接复制，而无需 需要外部缓存群集（例如 Ehcache 3.x 中的兵器）。

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能被弃用，并计划在未来删除。</strong> 如果可以，请考虑使用 CAS 中的 Ehcache v3 票证注册功能
来处理此集成。</p>
</div>

通过在战争覆盖中包括以下依赖性，实现了 Ehcache 集成：



```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 埃卡切 - 票务注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```


此注册表使用 [Ehcache](http://ehcache.org/) 版本 2.x 库存储门票。



## 分布式缓存

HA 架构推荐分布式缓存，因为它们在票证存储 子系统中提供故障耐受性。 创建一个缓存实例来存放所有类型的票证，并在配置中定义的节点群中同步复制 。



### RMI 复制

Ehcache 支持由两个或多个节点组成的分布式缓存的 [RMI](https://docs.oracle.com/javase/tutorial/rmi/index.html) 复制。 要了解有关 RMI 与 Ehcache 复制的更多信息， [请参阅此资源](https://www.ehcache.org/documentation/2.8/replication/rmi-replicated-caching.html)。



#### 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ehcache-ticket-registry)。

下面提到的配置为 `个埃卡奇复制.xml` 的Ehcache配置。 请注意， `${ehcache.otherServer}` 将被系统属性所取代： `-德卡奇.其他服务器+cas2`。



```xml
<ehcache name="ehCacheTicketRegistryCache"
    updateCheck="false"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd">

    <diskStore path="java.io.tmpdir/cas"/><!--自动同行发现
    <cacheManagerPeerProviderFactory
    class="net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory"
    properties="peerDiscovery=automatic, multicastGroupAddress=230.0.0.1, multicastGroupPort=4446, timeToLive=32"
    propertySeparator="," />
    -- --><!--手动同行发现 -- --><cacheManagerPeerProviderFactory
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

Ehcaache 通过闲置和活的设置管理缓存对象的内部驱逐策略。 这些设置控制用于存储各种票务类型的缓存的一般策略。 一般来说， ，你需要确保缓存是活的足够长的时间，以支持门票的个人到期政策，并让 CAS清洁门票作为自己的清洁剂的一部分。



### 故障排除指南

* 您需要确保允许跨 CAS 节点进行网络通信，并且没有防火墙或其他组件 阻止流量。

* 如果您在具有活动防火墙的服务器上运行此功能，则可能需要在 `缓存管理器"`"中指定固定 `远程目标端口` 。

* 根据所使用的环境设置和 Ehcache 版本，您可能还必须调整共享</code> 设置 。</p></li>
<li><p spaces-before="0">确保每个缓存管理器指定与 Ehcache 配置本身匹配的名称。</p></li>
<li>您可能还需要调整您的到期策略，以便延长时间跨度，特别是服务票
，具体取决于 CAS 节点的网络流量和通信延迟，尤其是在节点尝试加入群集时
。</li>
</ul>
