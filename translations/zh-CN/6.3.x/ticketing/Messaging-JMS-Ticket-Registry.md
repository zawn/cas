---
layout: 违约
title: CAS - 消息传递 （JMS） 票务注册处
category: 票务
---

# JMS 票务注册处

CAS 可以通过各种消息传递系统进行启用，以便分发和共享票证数据：从简化使用 JMS API 到全套基础设施以异步接收消息， 。 与消息系统的集成完全建立在 [春靴](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html) 顶部。

支持通过在叠加中包括以下依赖关系而启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - jms - 票证注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此注册表在很大程度上是 [默认票证注册表](Default-Ticket-Registry.html)的延伸。 不同的是，适用于注册表的票务操作使用 到队列上其他收听 CAS 节点的消息传递队列进行广播。 每个节点都自行保存票证状态的副本，只有 指示其他人通过广播与每个节点相关的消息和数据来保持其副本的准确性。 在群集的 CAS 节点内运行的每个消息和票证注册实例都标有唯一的 标识符，以避免无休止的循环行为和递归不必要的入站操作。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jms-ticket-registry)。

## 主动MQ

CAS 可以在检测到类路径上可用的 ActiveMQ 时配置票证注册表。 如果经纪人在场，则启动嵌入式经纪商，并自动 配置，只要没有通过配置指定经纪商 URL。 默认情况下，ActiveMQ 会在尚未存在时创建目的地，因此目的地会根据其提供的名称进行解决。

主动MQ配置由中科院</a>外部配置属性控制。</p> 

ActiveMQ 的默认设置是，交易 之外的所有持久消息都同步发送给经纪人。 这意味着发送方法被阻止，直到 消息被经纪人接收，然后写到磁盘 - 然后响应返回 客户端和 `发送（）` 解除阻止与成功或抛出错误，如果发送不能完成（例如，由于安全例外）。



## 忒

CAS 在检测到类路径上可用 [阿特米斯](https://activemq.apache.org/artemis/) 时，可以自动配置票证注册表。 如果经纪人在场，则启动嵌入式经纪商并自动 配置（除非已明确设置模式属性）。 支持的模式是： 嵌入式（以明确说明需要嵌入式经纪商，如果 在类路径中不可用，则应导致错误），并且原生使用 netty 传输协议连接到经纪商。 当配置后者时，CAS 会用默认设置配置连接到本地机器上运行的经纪商的注册表。

支持通过在叠加中包括以下依赖关系而启用：



```xml
<dependency>
    <groupId>组织.弹簧框架.启动</groupId>
    <artifactId>春靴启动器-青霉素</artifactId>
    <version>${springboot.version}</version>
</dependency>
```


</a>，在CAS的 设置中，阿特米斯配置由外部配置属性控制。</p> 



## 詹迪

如果您 [在应用服务器](../installation/Configuring-Servlet-Container.html)中运行 CAS， CAS 将尝试使用 JNDI 找到 JMS 连接。 默认情况下，将检查 `爪哇：/JmsXA` 和 `java：/XA 连接工厂` 的位置。 当然，其他地点可以 指定使用 [CAS设置](../configuration/Configuration-Properties.html#jms-ticket-registry)。




## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：



```xml
...
<Logger name="org.springframework.jms" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
