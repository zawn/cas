---
layout: 违约
title: CAS - JPA 票务注册处
category: 票务
---


# JPA 票务注册处
JPA 票务注册处允许 CAS 将客户身份验证的状态 数据 （票证） 存储在数据库后端（如 MySQL）中。

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用关系数据库作为
票证注册状态管理的后端持久性选择是一个相当必要和复杂的
过程。 除非您已经配备了分组数据库技术和管理它的资源，否则
复杂性可能不值得麻烦。</p></div>

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - jpa - 票证注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jpa-ticket-registry)。

还自动安排一个背景 *清洁* 过程，定期扫描所选数据库，并根据配置的阈值参数删除过期的记录。

<div class="alert alert-warning"><strong>更清洁的使用</strong><p>在聚类 CAS 部署中，最好仅在指定的 CAS 节点上保持清洁器运行，并通过 CAS 设置将其关闭。 在所有节点上保持清洁运行可能导致严重的性能和锁定问题。</p></div>

## 出票票锁定

TGT 几乎总是从数据库中加载的同一事务中更新，但在某些处理延迟后 。 因此，JPA 票证注册处利用数据库中所有 TGT 负载的写锁来防止死锁，并确保单个 TGT 被多个请求同时使用时使用元数据一致性。

这会降低 JPA 票证注册处的性能，并且可能不可取或没有必要进行某些部署，具体取决于所使用的数据库 、其配置的事务隔离级别以及单个 TGT 的预期并发性。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jpa-ticket-registry)。
