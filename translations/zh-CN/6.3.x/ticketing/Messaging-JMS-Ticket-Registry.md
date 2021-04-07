---
layout: 默认
title: CAS-消息传送（JMS）票务注册表
category: 售票处
---

# JMS票务注册表

可以使用各种消息传递系统来启用CAS，以便分发和共享票证数据： 从JMS API的简化使用到完整的基础结构，以异步接收消息）。 与消息传递系统的集成完全建立在 [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html) top之上。

通过在叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-jms-ticket-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

该注册表是 [默认票证注册表](Default-Ticket-Registry.html)的扩展。 不同之处在于，使用消息传递队列 将应用于注册表的票证操作广播到队列中的其他侦听CAS节点。 每个节点自己保留票证状态的副本，只有 指示其他节点通过广播消息和与之关联的数据来保持其副本的准确性。 在集群中的CAS节点内运行的每个消息和票证注册表实例都标记有唯一的 标识符，以避免无限循环行为和递归的不必要的入站操作。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jms-ticket-registry)。

## ActiveMQ

当CAS在类路径上 可用时，可以配置票证注册表。 如果存在代理，则只要没有通过配置指定代理URL， 默认情况下，ActiveMQ将创建目的地（如果尚不存在），因此将根据目的地名称提供的名称对其进行解析。

[CAS设置](../configuration/Configuration-Properties.html#jms-ticket-registry)外部配置属性控制。

ActiveMQ的默认设置是事务 之外的所有持久消息发送到代理都是同步的。 这意味着send方法将被阻塞，直到 消息，然后将其写入磁盘-然后将响应 返回给客户端，并将 `send（）` 成功取消阻塞，或者如果send则抛出错误无法完成（例如由于安全异常）。

## 阿耳emi弥斯

当CAS检出类路径上有 [Artemis](https://activemq.apache.org/artemis/) 可用时，它可以自动配置票证注册表。 如果存在代理，则将启动嵌入式代理并 （除非已显式设置mode属性）。 支持的模式为： 嵌入式（明确表示需要嵌入式代理，如果 则嵌入式代理将导致错误），本机使用Netty传输协议连接到代理。 配置后者后，CAS将使用默认设置配置连接到在本地计算机上运行的代理的注册表。

通过在叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-artemis</artifactId>
    <version>${springboot.version}</version>
</dependency>
```

[CAS设置](../configuration/Configuration-Properties.html#jms-ticket-registry)外部配置属性控制。

## 日本国家发展研究院

如果 [正在应用服务器](../installation/Configuring-Servlet-Container.html)运行CAS，则 CAS将尝试使用JNDI定位JMS连接。 默认情况下，将检查位置 `java：/ JmsXA` 和 `java：/ XAConnectionFactory`。 当然，可以使用 [CAS设置](../configuration/Configuration-Properties.html#jms-ticket-registry) 。


## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="org.springframework.jms" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
