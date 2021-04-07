---
layout: 违约
title: CAS - 领事服务发现
category: 高可用性
---

# 领事服务器发现服务

[HashiCorp 领事](https://www.consul.io) 具有多个组件，但总的来说，它是发现和配置基础设施中服务的工具。 它提供了关键功能：

- **服务发现**：领事的客户可以提供一项服务，如api或mysql，其他客户可以使用领事发现给定服务的提供者。 使用 DNS 或 HTTP，应用程序可以轻松找到他们依赖的服务。

- **健康检查**：领事客户可以提供任意数量的健康检查，或与给定服务相关（"网络服务器返回 200 OK"），或与当地节点（"内存利用率低于 90%"）。 此信息可用于操作员监控群集健康，服务发现组件用于将流量从不健康的主机中路由。

- **KV商店**：应用程序可以利用领事的分层键/价值存储为任何数量的目的，包括动态配置，功能标记，协调，领导人选举，等等。 简单的 HTTP API 便于使用。

- **多数据中心**：领事支持多个数据中心开箱即用。 这意味着领事的用户不必担心建立额外的抽象层，以增长到多个区域。

CAS 提供基于 [春云领事](https://cloud.spring.io/spring-cloud-consul/) 的领事服务发现服务器，并通过 [春云](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)进行引导。

### 安装

- 要运行领事发现服务器，请 [查看此指南](https://www.consul.io/) 以获得安装说明。 一个简单的领事装置可以作为 `领事代理运行 -dev-ui`
- 寻找一个合适的和相关的现成的多克图像通过 `码头搜索领事`。

当部署并假设默认设置时，领事仪表板将可在： `http://localhost:8500/ui`。

请注意，所有 CAS 服务器节点都必须提供领事代理客户端。 默认情况下，代理客户端预计在 `本地主席：8500`。 有关如何启动代理客户端的具体细节，请参阅 [代理文档](https://consul.io/docs/agent/basics.html) 。

### 配置管理

领事提供存储配置和其他元数据的 [密钥/价值存储](https://consul.io/docs/agent/http/kv.html) 。 配置在运行时的特殊"引导"阶段加载到 CAS 环境中。 默认情况下，配置存储在 `/配置` 文件夹中。 多个 `属性源` 实例是根据应用程序的名称和模拟 解决属性的春季云配置文件创建的。 例如， `cas` 和具有 `开发` 配置文件的应用程序将创建以下属性来源：

```bash
配置/cas、开发/
配置/cas/
配置/应用程序、开发/
配置/应用/
```

最具体的属性源位于顶部，最不特定的属性来源位于底部。 `配置/应用程序` 文件夹中的属性适用于使用领事进行配置的所有应用程序。 `配置/cas` 文件夹中的属性仅适用于名为 `cas`的服务实例。

配置当前在应用程序启动时读取。 将 HTTP POST 发送到 `/刷新` 将导致重新加载配置。 观看关键价值商店（领事支持）目前是不可能的，但将是这个项目的未来补充。

领事康菲格手表利用领事的能力， [观看一个关键的前缀](https://www.consul.io/docs/agent/watches.html)。 康菲格手表发出阻止领事 HTTP API 呼叫，以确定当前应用程序是否有任何相关配置数据更改。 如果有新的配置数据，则会发布 `刷新事件` 。 这相当于调用 `/刷新` 弹簧启动执行器端点。

## CAS 发现服务客户端

每个 CAS 服务器都能够在发现服务器上自动注册，前提是提供配置来指导 CAS 服务器如何定位并连接到发现服务器服务。

通过在 WAR 叠加中包括以下依赖项来增加支持：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡斯服务器支持-领事-客户端</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#consul-service-discovery)查看本指南。

### 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
<Logger name="org.springframework.cloud.consul" level="debug" additivity="false">
  <AppenderRef ref="casConsole"/>
  <AppenderRef ref="casFile"/>
</Logger>
```
