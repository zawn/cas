---
layout: 默认
title: CAS-Web流定制
category: Webflow管理
---

# Webflow会话

CAS使用 [Spring Webflow](https://github.com/spring-projects/spring-webflow) 来管理 身份验证序列。 弹簧的Webflow提供可插入体系结构，由此各种动作， 的决定和行动在整个初级认证工作流可以容易地控制 和导航。 为了使此导航正常工作，必须保持某种形式的会话状态。

## 客户端会话

CAS提供了一种在Spring Webflow中将流执行状态存储在客户端上的功能。 呈现视图时，流状态作为编码的字节 流存储在提供给客户端的流执行标识符中。 默认情况下，CAS会自动尝试存储 来以加密形式跟踪客户端上的此状态，从而无需进行会话清除，终止和复制。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

如果部署者未生成密钥，则CAS将尝试自动生成密钥，并为每个受尊重的密钥 部署者务必尝试将生成的密钥复制到其CAS属性文件中，特别是当 运行多节点CAS部署时。 否则，将阻止CAS 适当解密和加密Webflow状态，并阻止成功的单点登录。

<div class="alert alert-warning"><strong>使用警告！</strong><p>
尽管上述设置都是可选的，但建议您提供自己的配置和设置，以对Web会话状态
</p></div>

## 服务器端会话

如果您希望使用服务器端会话存储来管理Webflow会话，则需要通过CAS属性 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

这样做可能需要您在CAS的群集部署中还启用粘性会话和/或会话复制。

<div class="alert alert-warning"><strong>使用警告！</strong><p>
一般而言，除非您进行了相当专业的部署，或者需要将一点一点的数据存储到服务器支持的会话对象中的功能，否则不需要启用服务器端会话。 建议您坚持使用默认的客户端会话存储，并且仅在特定CAS行为要求的情况下进行切换。</p></div>

### Hazelcast会话复制

如果您不希望将本机容器的策略用于会话复制， 用作CAS对Hazelcast会话复制的支持。

可通过以下模块启用此功能：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-session-hazelcast</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

### Redis会话复制

如果您不希望将本机容器的策略用于会话复制，则将 用作CAS对Redis会话复制的支持。

可通过以下模块启用此功能：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-session-redis</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

### MongoDb会话复制

如果您不希望将本机容器的策略用于会话复制，则将 用作CAS对Mongo会话复制的支持。

可通过以下模块启用此功能：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-session-mongo</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

### JDBC会话复制

如果您不希望将本机容器的策略用于会话复制，则将 用作CAS对JDBC会话复制的支持。

可通过以下模块启用此功能：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-session-jdbc</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。
