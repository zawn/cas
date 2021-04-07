---
layout: 默认
title: CAS-领事服务发现
category: 高可用性
---

# 领事服务器发现服务

[HashiCorp Consul](https://www.consul.io) 具有多个组件，但总体而言，它是用于发现和配置基础结构中的服务的工具。 它提供了关键功能：

- **服务发现**：Consul的客户端可以提供服务，例如api或mysql，其他客户端可以使用Consul来发现给定服务的提供者。 使用DNS或HTTP，应用程序可以轻松找到它们依赖的服务。

- **检查**：Consul客户端可以提供任何数量的运行状况检查，这些检查可以与给定服务（“ Web服务器返回200 OK”）或与本地节点（“内存利用率低于90％”）相关。 操作员可以使用此信息来监视群集的运行状况，服务发现组件可以使用此信息来将流量路由到运行状况不佳的主机之外。

- **KV商店**：应用程序可以出于多种目的使用Consul的分层键/值存储，包括动态配置，功能标记，协调，领导者选举等。 简单的HTTP API使其易于使用。

- **多数据中心**：Consul开箱即用地支持多个数据中心。 这意味着Consul的用户不必担心会构建其他抽象层以扩展到多个区域。

CAS提供了一个基于Consul的服务发现服务器，该服务器基于 [Spring Cloud Consul](https://cloud.spring.io/spring-cloud-consul/) 并通过 [Spring Cloud](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)引导。

### 安装

- 要运行Consul发现服务器，请 [请参阅本指南](https://www.consul.io/) 以获取安装说明。 一个简单的Consul安装可以作为 `consul agent -dev -ui`
- `docker search consul`寻找合适且相关的现成Docker映像。

部署并采用默认设置后，Consul仪表板将位于： `http：// localhost：8500 / ui`。

请注意，Consul Agent客户端必须对所有CAS服务器节点均可用。 默认情况下，代理客户端应位于 `localhost：8500`。 有关如何启动代理客户端的详细信息，请参见 [代理文档](https://consul.io/docs/agent/basics.html)

### 配置管理

领事提供了 [键/值存储](https://consul.io/docs/agent/http/kv.html) 用于存储配置和其他元数据。 在运行时的特殊“引导”阶段，将配置加载到CAS环境中。 默认情况下，配置存储在 `/ config` 基于应用程序的名称和模拟 Spring Cloud Config解析属性顺序的活动配置文件，将创建多个 `PropertySource` 例如，名称为 `cas` 且 `dev` 的应用程序将创建以下属性源：

```bash
config / cas，dev /
config / cas /
config / application，dev /
config / application /
```

最具体的属性来源在顶部，最不具体的属性在底部。 `config / application` 文件夹中的属性适用于所有使用consul进行配置的应用程序。 `config / cas` 文件夹中的属性 `cas`的服务的实例。

当前在启动应用程序时读取配置。 发送HTTP POST到 `/ refresh` 将导致重新加载配置。 目前尚无法观看键值存储（Consul支持），但是将来会添加到该项目中。

Consul Config Watch利用consul的能力来监视键前缀</a>。 Config Watch进行阻塞Consul HTTP API调用，以确定当前应用程序的任何相关配置数据是否已更改。 如果有新的配置数据，则会发布 `刷新事件` 这等效于调用 `/ refresh` Spring Boot执行器端点。</p> 



## CAS发现服务客户

只要配置可用以指示CAS服务器如何定位和连接发现服务器服务，每个单独的CAS服务器都可以向发现服务器自动注册。

通过在WAR叠加中包含以下依赖关系来添加支持：



```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-consul-client</artifactId>
  <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#consul-service-discovery)。



### 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：



```xml
<Logger name="org.springframework.cloud.consul" level="debug" additivity="false">
  <AppenderRef ref="casConsole"/>
  <AppenderRef ref="casFile"/>
</Logger>
```
