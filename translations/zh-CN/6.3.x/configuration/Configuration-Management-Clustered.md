---
layout: 默认
title: CAS-配置管理群集部署
category: 配置
---

# 集群部署

CAS使用 [Spring Cloud Bus](http://cloud.spring.io/spring-cloud-static/spring-cloud.html) 来管理分布式部署中的配置。 Spring Cloud Bus通过轻量级消息代理 然后可以将其用于广播状态 更改（例如，配置更改）或其他管理指令。

总线支持向所有侦听节点发送消息。 广播的事件将尝试更新，刷新和 重新加载每个CAS服务器应用程序的配置。

如果CAS节点未共享配置属性的中央位置，例如每个 节点都包含一份设置副本，则必须复制对一个节点所做的任何更改，并 所做的更改，以便将它们保留在磁盘上。 上面提到的广播机制只有 会将更改应用于运行时和正在运行的CAS实例。 理想情况下，您应该 （或者更好的是，在私有Github存储库中） ，在一个位置进行更改并将其广播到所有节点。 此模型无需在磁盘和CAS节点之间

要查看CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#configuration-storage)。

Spring Cloud Config总线保护并公开了以下端点：

| 范围          | 描述                           |
| ----------- | ---------------------------- |
| `/执行器/总线刷新` | 如果云总线已打开，请重新加载群集中所有CAS节点的配置。 |
| `/执行器/总线环境` | 如果云总线已打开，则发送键/值对以更新每个CAS节点。  |

总线广播事件的传输机制是通过以下组件之一来处理的。

## 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：

```xml
<Logger name="org.springframework.amqp" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```

## 兔子MQ

这是将更改事件广播到CAS节点的默认选项。 [RabbitMQ](https://www.rabbitmq.com/) Advanced Message Queuing Protocol（AMQP）的开源消息代理 软件（有时称为面向消息的中间件）。

通过在最终叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-amqp</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#rabbitmq)。

## 卡夫卡

Apache Kafka是由Apache Software Foundation开发的开源消息代理项目。 该项目旨在为处理实时数据提要提供一个统一的，高吞吐量，低延迟的平台。 从本质上讲，它是“设计为分布式事务日志的可大规模伸缩的发布/订阅消息队列”， 对企业基础结构处理流数据非常有价值。

通过在最终叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-kafka</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#kafka)。
