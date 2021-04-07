---
layout: 违约
title: 中科院 - 监测
category: 监测 & 统计
---

# CAS 监控

可以定义 CAS 监视器以报告票证注册表的健康状况以及 CAS 正在使用的系统的其他基础连接。 Spring Boot 提供许多称为 `健康指示器`的监视器，这些监视器在特定设置（即 `春天. mail. *`）. CAS 本身根据下面列出的相同组件提供其他一些监视器，其操作可能需要结合特定的依赖模块及其相关设置。

## 违约

默认监视器报告返回简短的内存和票证统计数据。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-核心监视器</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 物业的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#monitoring) 并 [本指南](../configuration/Configuration-Properties.html#memory)。

<div class="alert alert-warning"><strong>基督教青年会</strong><p>为了准确可靠地报告机票统计数据，您受基础票务登记处的摆布，以执行的方式支持该行为，这意味着必须考虑和仔细调整基础设施和网络功能和延迟。 这可能在分组部署中变得特别相关，因为根据选择的票证注册表，CAS 可能需要通过运行分布式查询来计算票证使用情况， <i>询问整个集群</i> 。</p></div>

## 梅卡奇德

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>套机服务器支持-机械监控</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#memcached-monitors)。

实际的 memcach 实现可以通过以下选项之一进行支持，预计在叠加中定义。

### 间谍梅卡奇

通过 [间谍图书馆](https://code.google.com/p/spymemcached/)提供支持。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 机械 - 间谍</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### AWS弹性缓存

对于运行 Memcached 引擎的集群，ElastiCache 支持自动发现-客户端程序自动识别缓存群集中的所有节点的能力 ， 并启动并维护与所有这些节点的连接。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 机械 - aws - 弹性</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 埃卡奇

监视由 Ehcache 支持的缓存的状态和状态。

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-埃卡奇-监视器</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cache-monitors)。

## 蒙古德布

监控蒙古数据库的状态和可用性。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 蒙古监控</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-monitors)。

## 黑兹尔卡斯特

监视黑兹尔卡斯特支持的缓存的状态和状态。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 哈泽尔卡斯特 - 监视器</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cache-monitors)。

## 京城

监控关系 SQL 数据库的状态和可用性。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - jdbc - 监视器</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-monitoring)。

## 阿尔达普

监控LDAP服务器的状态和可用性。

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡服务器支持-ldap-监视器</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-server-monitoring)。
