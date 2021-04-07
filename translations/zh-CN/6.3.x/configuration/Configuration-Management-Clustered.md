---
layout: 违约
title: CAS - 配置管理分组部署
category: 配置
---

# 聚类部署

CAS 使用 [春云总线](http://cloud.spring.io/spring-cloud-static/spring-cloud.html) 在分布式部署中管理配置。 春云总线将 分布式系统的节点与轻量级消息中间商连接。 然后，这可用于广播状态 更改（例如配置更改）或其他管理说明。

总线支持向所有收听节点发送消息。 广播事件将尝试更新、刷新和 重新加载每个 CAS 服务器应用程序的配置。

如果 CAS 节点没有共享配置属性的中心位置，以便每个 节点都包含设置的副本，则必须复制您对一个节点的任何更改，并在所有节点之间同步 ，以便它们持续在磁盘上。 上述广播机制仅 对运行时间和运行 CAS 实例进行更改。 理想情况下，您应该在共享（git）存储库（或者更好的是，在私人 Github 存储库内）中跟踪 CAS 设置的 ， 您在一个地方进行更改，并将其广播到所有节点。 此模型无需在磁盘和 CAS 节点之间同步更改 。

要查看 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#configuration-storage)。

以下端点由春云配置总线固定并暴露：

| 参数          | 描述                             |
| ----------- | ------------------------------ |
| `/执行器/总线刷新` | 如果云总线已打开，则重新加载聚类中所有 CAS 节点的配置。 |
| `/执行器/总线`   | 如果云总线已打开，将发送密钥/值对以更新每个 CAS 节点。 |

公共汽车广播事件的运输机制通过以下一个组件处理。

## 故障 排除

要启用其他日志，请修改记录配置文件以添加以下：

```xml
<Logger name="org.springframework.amqp" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```

## 兔子

这是向 CAS 节点广播更改事件的默认选项。 [兔子MQ](https://www.rabbitmq.com/) 是开源消息经纪人 软件（有时称为消息导向中间件），实现 高级消息排队协议（AMQP）。

支持通过在最终叠加中包括以下依赖性来实现：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>套机服务器支持-配置-云-amqp</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#rabbitmq)。

## 卡 夫 卡

阿帕奇卡夫卡是由阿帕奇软件基金会开发的开源消息经纪人项目。 该项目旨在提供一个统一、高通量、低延迟的平台，用于处理实时数据源。 从本质上讲，它是一个"大规模可扩展的酒吧/子消息队列，设计为分布式交易日志"， 使企业基础设施处理流数据具有很高的价值。

支持通过在最终叠加中包括以下依赖性来实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持配置 - 云卡夫卡</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#kafka)。
