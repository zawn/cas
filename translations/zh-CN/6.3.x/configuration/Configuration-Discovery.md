---
layout: 默认
title: CAS-配置发现
category: 配置
---

# 配置发现

可以通过发现端点来通告CAS服务器部署的某些方面，以向客户端应用程序和消费者指示已打开的一组功能。 通过在叠加层中包含以下模块来启用 [发现配置文件端点](../monitoring/Monitoring-Statistics.html)

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-discovery-profile</artifactId>
     <version>${cas.version}</version>
</dependency>
```

发现配置文件中报告的元数据通常包括两类项目：

- 可以使用该功能但尚未完全配置和启用的CAS服务器 **能力设置**
- 处于运行状态的CAS服务器当前主动支持和配置的能力为 ****

报告项目的示例包括：

- 服务定义类型（CAS，SAML，OAuth等）
- 多因素身份验证提供程序类型（Authy，Duo Security等）
- ...

<div class="alert alert-info"><strong>Docs变老了</strong><p>要检查报告的元数据的最新集合，请打开端点并观察实际行为。 每个CAS版本的元数据都将继续增长和改进，以适应更多的发现尝试。</p></div>

请注意，此功能和端点默认情况下处于关闭状态，其访问与所有其他CAS管理端点一样受到控制。 </a>

安全选项，仅将适当的访问权限授予授权方。</p> 



## 行政端点

CAS提供了以下端点：

| 终点                 | 描述                                                 |
| ------------------ | -------------------------------------------------- |
| `DiscoveryProfile` | [CAS配置和功能](Configuration-Discovery.html)的JSON表示形式。 |
