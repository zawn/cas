---
layout: 默认
title: CAS-Hazelcast票务注册表
category: 售票处
---

# Hazelcast票务登记处

Hazelcast Ticket Registry是基于 [Hazelcast分布式网格库](http://hazelcast.org/)的分布式票证注册表实现 。 注册表实现可 群集，并且能够自动加入所有公开此注册表的CAS节点的群集。 Hazelcast将使用端口自动递增功能为群集的每个成员分配一个TCP端口，该端口从最初提供的任意端口 ，默认情况下 `5701`

Hazelcast将以非常有效的 方式在群集的所有成员之间平均分配票证数据。 同样，默认情况下，每个节点上的数据收集都配置有1个备份副本，即 以便Hazelcast将使用它来提供强大的数据一致性保证，即， *主要数据拥有者都不会发生 * 名成员死亡。 数据将在剩余的活动群集成员中重新分区

以下模块启用了支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持hazelcast票务注册表</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

该模块具有一种配置策略，默认情况下会自动配置票证注册表实现所使用的hazelcast实例，以构建和检索Hazelcast的地图以用于其分布式票证存储。 在此自动配置模式下，Hazelcast配置的某些方面由CAS属性控制。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#hazelcast-ticket-registry)。

<div class="alert alert-warning"><strong>会话监控</strong><p>请注意，在非常重的负载下，并且随着时间的推移收集了大量票证 <a href="../monitoring/Configuring-Monitoring.html">会话监视功能</a> 基于基础的Hazelcast票证注册表报告票证统计信息，最终可能会超时。 这是由于担心，Hazelcast尝试在整个网络上运行分布式查询，以收集，分析和汇总可能仍处于活动状态或不断变化的票证。 如果您确实遇到这种现象，则最好关闭会话监视器。
</p></div>

有关可用Hazelcast配置选项的详细信息， 是指 [所述Hazelcast配置文档](http://docs.hazelcast.org/docs/3.9.1/manual/html-single/index.html#hazelcast-configuration)

## AWS EC2自动发现

CAS中的Hazelcast支持可能会自动处理EC2自动发现。 当您不想提供或无法提供群集成员的可能IP地址的列表时，此功能很有用。 您还可以选择指定可以识别区域的分区组。 使用区域感知配置时，将在其他可用区中创建备份。 每个区域将被接受为一个分区组。 使用AWS Discovery功能要求您在CAS设置中关闭并禁用多播和TCP / IP配置，这应由CAS在运行时自动完成。

以下模块启用了支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-hazelcast-discovery-aws</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## Apache jclouds自动发现

在CAS Hazelcast支持可以通过自动处理自动发现 [阿帕奇jclouds®](https://jclouds.apache.org/)。 当您不想提供或无法提供群集成员的可能IP地址的列表时，此功能很有用。 Apachejclouds®是Java平台的开源多云工具包，它使您可以自由创建跨云可移植的应用程序，同时可以完全控制使用特定于云的功能。 要查看受支持的云环境的完整列表，请参阅 [](https://jclouds.apache.org/reference/providers/#compute)。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-hazelcast-discovery-jclouds</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## Microsoft Azure自动发现

CAS中的Hazelcast支持可以通过Microsoft Azure自动处理自动发现。 发现策略将通过返回Azure资源组中标记有指定值的VM，从而提供所有Hazelcast实例。 您需要 [Azure Active Directory服务主体凭据](https://azure.microsoft.com/en-us/documentation/articles/resource-group-create-service-principal-portal/) ，此插件才能正常工作。 对于您在资源组中部署的每个Hazelcast虚拟机，您需要确保每个VM都标记有CAS Hazelcast配置中定义 `clusterId` 唯一的要求是每个VM都可以通过私有IP地址或公共IP地址相互访问。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-hazelcast-discovery-azure</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## Kubernetes自动发现

这个hazelcast发现插件提供了通过将 [Kubernetes](http://kubernetes.io/) Service Discovery系统 请求来查找其他成员的IP地址的可能性。

此模块支持针对发现注册表进行解析的两个不同选项：

- 对REST API的请求
- 针对给定的无头DNS服务名称的DNS查找

有关更多信息，请参见 [此链接](https://github.com/hazelcast/hazelcast-kubernetes)

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-hazelcast-discovery-kubernetes</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## Docker Swarm自动发现

这个hazelcast发现插件提供了基于Docker Swarm模式的发现策略。

有关更多信息，请参见 [此链接](https://github.com/bitsofinfo/hazelcast-docker-swarm-discovery-spi/)

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-hazelcast-discovery-swarm</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 组播自动发现

通过多播自动发现机制，Hazelcast允许群集成员使用多播通信相互查找。 集群成员不需要知道其他成员的具体地址，因为它们只是组播给所有其他成员以进行监听。 多播是可能的还是被允许的 **取决于您的环境**。

启用多播时，请特别注意超时。 组播超时指定以秒为单位的时间，该成员应等待网络中另一个成员的有效组播响应，然后再声明自己为领导者成员（加入集群的第一个成员）并创建自己的集群。 这仅适用于尚未分配领导者的成员的启动。 如果您指定一个较高的值（例如60秒），则意味着在选择领导者之前，每个成员将等待60秒才能继续前进。 提供高价值时要小心。 另外，请注意不要将值设置得太低，否则成员可能会放弃得太早而创建自己的集群。

## 广域网复制

Hazelcast WAN复制允许您通过在WAN环境（例如Internet）上复制它们的状态来使多个Hazelcast群集保持同步。

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用Hazelcast WAN复制需要Hazelcast Enterprise订阅。 在激活此功能之前，请确保您 
已从Hazelcast获得了正确的许可证，SDK和工具。 请联系Hazelcast以获取更多信息。</p></div>

Hazelcast支持WAN复制的两种不同操作模式：

- 主动-被动：此模式主要用于故障转移方案，在这种情况下，您需要将主动群集复制到一个或多个被动群集中，以维护备份。
- Active-Active：每个集群都相等，每个集群都复制到所有其他集群。 通常，这是为了将客户端和服务器之间的最短路径连接到不同的群集上。

有关更多信息，请参见 [第](https://hazelcast.com/products/wan-replication/)

在CAS中定义WAN复制端点是使用静态端点和发现完成的。

## 记录中

要为注册表启用其他日志记录，请配置log4j配置文件以添加以下 级：

```xml
...
<Logger name="com.hazelcast" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
