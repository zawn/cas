---
layout: 默认
title: CAS-尤里卡服务发现
category: 高可用性
---

# 尤里卡服务器发现服务

[Eureka](https://github.com/Netflix/eureka) 是基于REST的服务，主要 来定位服务，以实现负载均衡和中间层服务器的故障转移。 Eureka不仅提供发现服务器，而且还支持客户端（客户端可能是池中的各个CAS服务器）。 可以将服务器配置和部署为高度可用，每个服务器将有关已注册服务的状态复制到其他服务器。

CAS提供了一个基于Eureka的服务发现服务器，该服务器基于 [Spring Cloud Netflix](http://cloud.spring.io/spring-cloud-netflix) 并通过 [Spring Cloud](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)引导。

### 安装

- 要运行Eureka发现服务器，请 [使用此WAR叠加层](https://github.com/apereo/cas-discoveryserver-overlay)。
- `docker search eureka`寻找合适且相关的现成Docker映像。

部署后，以下URL可用：

| 网址                | 描述        |
| ----------------- | --------- |
| `/`               | 主页列出服务注册。 |
| `/ eureka / apps` | 原始注册元数据。  |

### 高可用性模式

您始终要确保发现服务器在高可用性模式下运行。 一种选择是确保每个单独的Eureka服务器都是对等的。 请参阅 [本指南](http://cloud.spring.io/spring-cloud-static/spring-cloud.html#_peer_awareness) 以了解如何进行管理。

## CAS发现服务客户

只要配置可用以指示CAS服务器如何定位和连接发现服务器服务，每个单独的CAS服务器都可以向发现服务器自动注册。

通过在WAR叠加中包含以下依赖关系来添加支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-eureka-client</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#eureka-service-discovery)。

### 验证

如果配置中的其中一个Eureka服务器URL嵌入了凭据（curl样式，例如 `http：// user：password @ localhost：8761 / eureka`），则将自动添加对HTTP基本身份验证的支持。

### 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
<Logger name="org.springframework.cloud" level="debug" additivity="false">
  <AppenderRef ref="casConsole"/>
  <AppenderRef ref="casFile"/>
</Logger>
<Logger name="com.netflix" level="debug" additivity="false">
    <AppenderRef ref="casConsole"/>
    <AppenderRef ref="casFile"/>
</Logger>
```
