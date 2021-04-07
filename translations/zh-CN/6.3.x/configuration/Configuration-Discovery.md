---
layout: 违约
title: CAS - 配置发现
category: 配置
---

# 配置发现

CAS 服务器部署的某些方面可以通过发现端点进行广告宣传，以便向客户端应用程序和消费者指示已启用的一组功能和功能。 [发现配置文件端点](../monitoring/Monitoring-Statistics.html) 通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>套机服务器支持-发现-配置文件</artifactId>
     <version>${cas.version}</version>
</dependency>
```

发现配置文件中报告的元数据通常包括两类项目：

- **的功能可以由 CAS 服务器** 支持，该功能可用，但尚未完全配置并打开。
- **的功能正在积极** ，目前由运行中的 CAS 服务器支持并配置。

报告项目的例子包括：

- 服务定义类型（CAS、SAML、OAuth等）
- 多因素身份验证提供商类型（Authy、双安全等）
- ...

<div class="alert alert-info"><strong>文档变老</strong><p>要检查报告元数据的最新集合，打开终点并观察操作中的行为。 元数据将继续增长和改善每个 CAS 版本，以适应更奇特的发现尝试。</p></div>

请注意，此功能和端点默认关闭，其访问权限与所有其他 CAS 管理端点类似。 一旦端点打开，您将需要确保仅通过 CAS</a>提供的适当

安全选项授予授权方适当的访问权限。</p> 



## 行政终点

CAS 提供以下端点：

| 端点     | 描述                                                     |
| ------ | ------------------------------------------------------ |
| `发现档案` | 提供 [CAS 配置和功能](Configuration-Discovery.html)的 JSON 表示。 |
