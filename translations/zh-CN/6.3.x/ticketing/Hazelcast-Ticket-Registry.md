---
layout: 违约
title: CAS - 黑兹尔卡斯特票务注册处
category: 票务
---

# 黑兹尔卡斯特票务登记处

黑兹尔卡斯特票务登记处是一个基于 [黑兹尔卡斯特分布式网格库](http://hazelcast.org/)的分布式票证登记实施 。 注册表实施 群集感知，能够自动加入暴露此注册表的所有 CAS 节点的集群。 黑兹尔卡斯特将使用端口自动增量功能，从最初提供的任意端口开始 ，向集群的每个成员分配一个 TCP 端口，默认情况下，该端口通常 `5701` 。

黑兹尔卡斯特将以非常 高效的方式在集群的所有成员之间均匀地分发票证数据。 此外，默认情况下，每个节点上的数据收集都配置为 1 个备份副本， 以便 Hazelcast 将用它来提供强大的数据一致性保证，即如果任何其他 *主要数据所有者* 成员死亡， 则不会发生实时节点上的数据丢失。 数据将在其余实时群集成员中 重新分区。

支持由以下模块启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 票务注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

此模块具有配置策略，默认情况下，该策略自动配置票务注册表实施中用于构建和检索黑兹尔卡斯特地图以存储其分布式票证的淡褐色实例。 此自动配置模式中的淡褐色铸配置的某些方面由 CAS 属性控制。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#hazelcast-ticket-registry)。

<div class="alert alert-warning"><strong>会话监控</strong><p>请注意，在非常沉重的负载下，并且随着时间的推移，由于门票的收集量非常大，CAS</a> <a href="../monitoring/Configuring-Monitoring.html">会话监控功能，根据潜在的黑兹尔卡斯特票务注册表报告退票统计数据，最终可能会超时。 这是由于担心黑兹尔卡斯特试图在整个网络中运行分布式查询，以收集、分析和聚合可能仍然活跃或不断变化的门票。 如果您确实遇到此行为，则最好关闭会话监视器。
</p></div>

有关黑兹尔卡斯特配置选项的更多信息， 请参阅黑兹尔卡斯特配置文档 [](http://docs.hazelcast.org/docs/3.9.1/manual/html-single/index.html#hazelcast-configuration)

## AWS EC2 自动发现

CAS 中的黑兹尔卡斯特支持可能会自动处理 EC2 自动发现。 当您不想提供或无法为群集成员提供可能的 IP 地址列表时，它是有用的。 您还可以选择性地指定分区组，以便区域感知。 使用区域感知配置时，在其他 AZ 中创建备份。 每个区域将被接受为一个分区组。 使用 AWS 发现功能需要您关闭并禁用 CAS 设置中的多播和 TCP/IP 配置，CAS 应在运行时自动完成。

支持由以下模块启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 发现 - 沃斯</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 阿帕奇云自动发现

中科院的黑兹尔卡斯特支持可以通过 [阿帕奇云自动处理自动发现®](https://jclouds.apache.org/)。 当您不想提供或无法为群集成员提供可能的 IP 地址列表时，它是有用的。 Apache jclouds®是 Java 平台的开源多云工具包，可让您自由创建跨云便携式应用程序，同时让您完全控制使用云特定功能。 要查看支持的云环境的完整列表， [请参阅此链接](https://jclouds.apache.org/reference/providers/#compute)。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 发现 - 云</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 微软蔚蓝汽车发现

CAS 中的黑兹尔卡斯特支持可能会通过微软 Azure 自动处理自动发现。 发现策略将通过返回 Azure 资源组中标有指定值的 VM 来提供所有黑兹尔卡斯特实例。 您将需要设置 [Azure 活动目录服务主体凭据](https://azure.microsoft.com/en-us/documentation/articles/resource-group-create-service-principal-portal/) ，以供您的 Azure 订阅此插件工作。 在资源组中部署的每台黑兹尔卡斯特虚拟机中，您需要确保每个 VM 都标有 CAS 黑兹尔卡斯特配置中定义的 `聚类` 值。 唯一的要求是，每个VM都可以通过私人或公共IP地址访问对方。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 发现 - 蔚蓝</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 库贝内茨汽车发现

此淡褐色发现插件通过解决针对 [库伯涅茨](http://kubernetes.io/) 服务发现系统的 请求，提供了查找其他成员 IP 地址的可能性。

此模块支持针对发现注册表解决的两种不同选项：

- 向休息 API 提出的请求
- DNS 查找给定无头 DNS 服务名称

有关详细信息，请参阅此链接</a>。</p> 



```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 发现 - 库贝内茨</artifactId>
    <version>${cas.version}</version>
</dependency>
```




## 多克沼泽汽车发现

此淡褐色发现插件提供了基于 Docker Swarm 模式的发现策略。 

有关详细信息，请参阅此链接</a> 。</p> 



```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 发现 - 温暖</artifactId>
    <version>${cas.version}</version>
</dependency>
```




## 多播自动发现

借用多播自动发现机制，黑兹尔卡斯特允许集群成员使用多播通信找到彼此。 集群成员不需要知道其他成员的具体地址，因为他们只是多播给所有其他成员听。 多播是可能的还是允许的 **取决于你的环境**。

启用多播时要特别注意超时。 多播超时在几秒钟内指定了会员应等待来自网络中运行的另一个成员的有效多播响应的时间，然后宣布自己是领导者成员（加入群集的第一个成员）并创建自己的群集。 这仅适用于尚未指派领导者的成员的初创公司。 如果您指定了高值（如 60 秒），则意味着在选择领导者之前，每个成员将等待 60 秒后再继续。 提供高价值时要小心。 此外，请小心不要将值设置得太低，否则成员可能会过早放弃并创建自己的集群。



## 广域网复制

黑兹尔卡斯特 WAN 复制允许您通过在 WAN 环境中复制其状态（如 Internet）来保持多个黑兹尔卡斯特群集同步。

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用黑兹尔卡斯特广域网复制需要黑兹尔卡斯特企业订阅。 在激活此功能之前，请确保您 
从黑兹尔卡斯特获得适当的许可证、SDK 和模制。 请联系黑兹尔卡斯特了解更多信息。</p></div>

黑兹尔卡斯特支持 WAN 复制的两种不同的操作模式：

- 主动-被动：此模式主要用于故障转移场景，其中要将主动群集复制到一个或多个被动集群，以维护备份。
- 主动性：每个群集相等，每个集群复制到所有其他群集。 这通常用于将不同的客户端连接到不同的群集，以便客户端和服务器之间的最短路径。

有关更多信息，请参阅此页面</a> 。</p> 

在 CAS 中定义 WAN 复制端点是使用静态端点和发现完成的。



## 伐木

要为注册表启用其他记录，请配置 log4j 配置文件以添加以下 级别：



```xml
...
<Logger name="com.hazelcast" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
