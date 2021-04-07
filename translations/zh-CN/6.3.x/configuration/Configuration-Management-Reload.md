---
layout: 违约
title: CAS - 配置管理 - 重新加载更改
category: 配置
---

# 重新加载更改

CAS 弹簧云配置服务器能够通过此处概述的</a>

配置文件 消耗属性和设置。 服务器不断自动监控基础属性源的 更改，但无法 向自己的客户端（如 CAS 服务器本身）广播这些更改，CAS 服务器本身将充当配置 服务器的客户端 *，* 期待更改通知悄悄地重新加载其配置。</p> 

因此，为了广播此类 `更改` 事件 CAS 呈现 [种端点](../monitoring/Monitoring-Statistics.html) ，使采用者 能够根据需要 **刷新** 配置。 这意味着采用者将 更改所需的 CAS 设置，然后向 CAS 提交 请求以刷新其当前状态。 受外部变化影响 的所有 CAS 内部组件都悄悄地重新加载 ，设置立即生效，完全无需容器重新启动或 CAS 重新部署。

<div class="alert alert-info"><strong>不要歧视！</strong><p>大多数（如果不是全部）CAS设置都是符合资格的候选人
重新加载。 CAS 应该足够聪明，能够重新加载适当的配置，无论
最终使用该设置的设置/模块如何。 一切都是公平的游戏，因为整个CAS网络应用程序包括所有模块和所有
相关设置可能是完全和完全可重新加载。 如果您发现此语句不成立的实例，请直言不讳。</p></div>

要查看 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#cloud-configuration-bus)。



## 重新加载策略

CAS 使用 [春云](https://github.com/spring-cloud/spring-cloud-config) 来管理配置的内部状态。 由 CAS 嵌入的 Spring Cloud 提供的配置服务器不断监控 CAS 设置 源，一更改后将自动刷新。



### 应用上下文

CAS 应用程序上下文和运行时间环境包含所有春季组件和豆子定义 可使用以下管理端点重新加载：

CAS 提供以下端点：

| 端点        | 描述                         |
| --------- | -------------------------- |
| `重新加载康德信` | 必要时重新加载 CAS 应用程序上下文和所有豆定义。 |




### 配置

CAS 配置设置和属性的变化可以使用下面概述的策略重新加载。



#### 独立

如果 [独立配置文件](Configuration-Server-Management.html#standalone) 用于控制和直接设置，并禁用了Spring Cloud配置服务器， CAS 将开始自动监视和监控配置文件指示的配置文件，并将自动重新加载运行时间的状态 应用程序上下文。 您也可以尝试通过 CAS 管理端点手动</a> 刷新设置。</p> 

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-核心-事件-配置</artifactId>
  <version>${cas.version}</version>
</dependency>
```




#### 春云

配置服务器的客户端（即 CAS服务器web应用程序）也暴露了一个 `/刷新` 端点 ，允许一个人刷新配置的基础上配置服务器的当前状态，并重新配置 应用程序运行时间，而无需重新启动JVM。



```bash
卷曲-X开机自检 https://cas.server.url/cas/actuator/refresh
```


[本指南](../monitoring/Monitoring-Statistics.html) 了解有关各种监控端点等的更多情况。
