---
layout: 默认
title: CAS-JPA票务注册表
category: 售票处
---


# JPA票务注册表
JPA票证注册表允许CAS将客户端身份验证的状态 数据（票证）存储在数据库后端（例如MySQL）中。

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用关系数据库作为
，票证注册表状态管理的后端持久性选择是一个相当不必要和复杂的
过程。 除非您已经配备了集群数据库技术和用于管理它的资源，否则
的复杂性可能不值得麻烦。</p></div>

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-jpa-ticket-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jpa-ticket-registry)。

后台 *清理程序* 进程也将自动调度为定期扫描所选数据库，并根据配置的阈值参数删除过期的记录。

<div class="alert alert-warning"><strong>清洁使用</strong><p>在集群式CAS部署中，最好使清理程序仅在一个指定的CAS节点上运行，并通过CAS设置在所有其他CAS节点上将其关闭。 使清洁程序在所有节点上运行可能会导致严重的性能和锁定问题。</p></div>

## 授予票证的票证锁定

TGT几乎总是在它们从数据库装入的同一事务中进行更新，但是在某些处理延迟之后为 因此，当多个请求同时使用 TGT时，JPA票务注册表会对 TGT的负载进行写锁定，以防止死锁并确保使用元数据的一致性。

这会降低JPA票务注册表的性能，对于某些部署可能不是理想的或不必要的，这取决于 ，其配置的事务隔离级别以及单个 TGT的预期并发性。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jpa-ticket-registry)。
