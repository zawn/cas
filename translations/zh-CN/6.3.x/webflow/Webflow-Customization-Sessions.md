---
layout: 违约
title: CAS - 网络流自定义
category: 网络流管理
---

# 网络流会话

CAS使用 [春网流](https://github.com/spring-projects/spring-webflow) 来管理 认证序列。 Spring Webflow 提供了一个可插入式架构，通过该架构，可以在 和导航时轻松控制整个主要身份验证工作流中的各种操作、 决策和操作。 为了使这种导航工作，必须保持某种形式的会话状态。

## 客户端会话

CAS 提供了在春季网络流中存储客户端流执行状态的设施。 流状态存储为在渲染视图时提供给客户端的流执行标识符中 流编码字流。 默认情况下，CAS 会通过加密和签署密钥 自动尝试存储 并以加密形式跟踪客户端上的此状态，以消除会话清理、终止和复制的需要。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

如果密钥不是由部署器生成的，CAS 将尝试自动生成密钥，并将输出 每个受尊重密钥的结果。 部署人员必须尝试将生成的密钥复制到其 CAS 属性文件中，尤其是在 运行多节点 CAS 部署时。 否则将阻止 CAS 适当解密和加密 Webflow 状态，并阻止成功的单次登录。

<div class="alert alert-warning"><strong>使用警告！</strong><p>
虽然上述设置都是可选的，但建议您提供自己的配置和设置，以便加密和
网络会话状态的转编码。</p></div>

## 服务器端会话

如果您希望使用服务器端会话存储来管理 Webflow 会话，您将需要通过 CAS 属性启用此行为 。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

这样做可能还需要您在 CAS 的聚类部署中启用粘性会话和/或会话复制。

<div class="alert alert-warning"><strong>使用警告！</strong><p>
一般来说，您不需要启用服务器端会话，除非您有一个相当专业的部署，或者需要将位和部分数据存储到服务器支持的会话对象中的功能。 建议您坚持默认的客户端会话存储，并且仅在特定 CAS 行为授权时切换。</p></div>

### 黑兹尔卡斯特会话复制

如果您不想使用本机容器的策略进行会话复制， 您可以使用 CAS 支持黑兹尔卡斯特会话复制。

此功能通过以下模块启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持会话 - 哈泽尔卡斯特</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

### 重新分配会话复制

如果您不想使用本机容器的策略进行会话复制， 您可以使用 CAS 支持 Redis 会话复制。

此功能通过以下模块启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-会话-</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

### 蒙古数据库会话复制

如果您不想使用本机容器的策略进行会话复制， 您可以使用 CAS 对 Mongo 会话复制的支持。

此功能通过以下模块启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持会话 - 蒙古</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

### JDBC 会话复制

如果您不想使用本机容器的策略进行会话复制， 您可以使用 CAS 对 JDBC 会话复制的支持。

此功能通过以下模块启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持会话 - jdbc</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。
