---
layout: 默认
title: CAS-配置管理-重新加载更改
category: 配置
---

# 重新加载更改

该CAS弹簧云配置服务器是能够消耗的属性和设置 经由 [这里概述型材](Configuration-Server-Management.html)。 服务器不断地监视 变化到自动底层属性来源，但是没有办法来广播这些变化 到它自己的客户端，如CAS服务器本身，这将作为 *的配置的客户端 服务器* 期望变化通知以静默方式重新加载其配置。

因此，为了广播这种 `改变` 事件，CAS 提供 [不同的端点](../monitoring/Monitoring-Statistics.html) ，这些端点4允许采用者 到 **刷新** 需要的配置。 这意味着采用者将 更改所需的CAS设置，然后将 的请求提交给CAS以刷新其当前状态。 的CAS内部组件都将被安静地重新加载 并且该设置立即生效，从而完全消除了重新启动容器或重新部署CAS的需要。

<div class="alert alert-info"><strong>不要歧视！</strong><p>大多数（如果不是全部）CAS设置都符合资格为
进行重新加载。 无论设置/模块最终使用该设置
，CAS都应该足够聪明以重新加载适当的配置。 一切都是公平的游戏，因为包括所有模块和所有
相关设置的整个CAS Web应用程序可能是完全完全可以重新加载的。 如果您发现该声明不成立的实例，请大声说出来。</p></div>

要查看CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#cloud-configuration-bus)。

## 重新加载策略

CAS使用 [Spring Cloud](https://github.com/spring-cloud/spring-cloud-config) 来管理配置的内部状态。 由嵌入在CAS中的Spring Cloud提供的配置服务器 ，并且更改后将自动刷新其自身。

### 应用环境

可以使用以下管理端点重新加载 的CAS应用程序上下文和运行时环境：

CAS提供了以下端点：

| 终点              | 描述                          |
| --------------- | --------------------------- |
| `reloadContext` | 必要时重新加载CAS应用程序上下文和所有bean定义。 |

### 配置

可以使用以下概述的策略重新加载CAS配置设置和属性中的更改。

#### 单机版

如果使用 [独立配置配置文件](Configuration-Server-Management.html#standalone) 来控制和直接设置，并且禁用了Spring Cloud配置服务器，则 CAS将开始自动监视和监视配置文件指示的配置文件，并将自动重新加载配置文件的状态。运行时 应用程序上下文自动。 您也可以尝试通过CAS管理员端点 [刷新设置](../monitoring/Monitoring-Statistics.html)

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-core-events-configuration</artifactId>
  <version>${cas.version}</version>
</dependency>
```

#### 春云

配置服务器的客户端（即 CAS服务器Web应用程序）也确实公开了 `/ refresh` 端点 ，该端点2可以基于配置服务器的当前状态刷新配置并重新配置 应用程序运行时，而无需重新启动JVM。

```bash
curl -X POST https：//cas.server.url/cas/actuator/refresh
```

[请参阅本指南](../monitoring/Monitoring-Statistics.html) 以了解有关各种监视端点的更多信息。
