---
layout: 违约
title: CAS - 尤里卡服务发现
category: 高可用性
---

# 尤里卡服务器发现服务

[尤里卡](https://github.com/Netflix/eureka) 是一种基于RESE的服务，主要 用于定位服务，用于中端服务器的负载平衡和故障转移。 Eureka 既提供发现服务器，也为客户端提供支持，这些客户端将是池中的单个 CAS 服务器本身。 服务器可以进行配置和部署，以便高度可用，每个服务器都会向其他服务器复制有关已注册服务的状态。

CAS 提供支持尤里卡的服务发现服务器，该服务器基于 [春云 Netflix](http://cloud.spring.io/spring-cloud-netflix) ，并通过 [春云](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)启动。

### 安装

- 要运行尤里卡发现服务器，请 [使用此WAR覆盖](https://github.com/apereo/cas-discoveryserver-overlay)。
- 寻找一个合适的和相关的现成的多克图像通过 `码头工人搜索尤里卡`。

部署后，可用以下网址：

| 网址          | 描述        |
| ----------- | --------- |
| `/`         | 主页列表服务注册。 |
| `/尤里卡/应用程序` | 原始注册元数据。  |

### 高可用性模式

您始终希望确保发现服务器在高可用性模式下运行。 一种选择是确保每个尤里卡服务器都处于同行意识。 请参阅本指南 [](http://cloud.spring.io/spring-cloud-static/spring-cloud.html#_peer_awareness) ，了解如何管理该指南。

## CAS 发现服务客户端

每个 CAS 服务器都能够在发现服务器上自动注册，前提是提供配置来指导 CAS 服务器如何定位并连接到发现服务器服务。

通过在 WAR 叠加中包括以下依赖项来增加支持：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡斯服务器支持-尤里卡-客户端</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#eureka-service-discovery)查看本指南。

### 认证

如果配置中的尤里卡服务器网址中嵌入了凭据（卷曲样式，如 `http://user:password@localhost:8761/eureka`），则将自动添加对 HTTP 基本身份验证的支持。

### 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

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
