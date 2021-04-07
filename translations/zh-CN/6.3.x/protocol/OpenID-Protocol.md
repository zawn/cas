---
layout: 违约
title: CAS - 开放式协议
category: 协议
---

# 开放式协议

OpenID 是一个开放、分散、以用户为中心的数字身份自由框架。 用户使用 UIS 代表 自己。 欲了解更多信息，请参阅 [http://www.openid.net](http://www.openid.net)。

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能被弃用，并计划在未来删除。</strong> 如果可以，请考虑使用
更主流和最新的身份验证协议。</p>
</div>

CAS 支持 OpenID 协议的"愚蠢"和"智能"模式。 哑巴模式的行为方式与现有的 CAS 协议 类似。 智能模式的不同之处之处是，它在开头就建立了客户端和 开放式提供商 （OP） 之间的关联。 由于该协会和协会期间进行的关键交换，客户和提供商之间交换的 信息使用此密钥进行签名和验证。 最终请求无需 （这相当于 CAS 协议中的机票验证）。

开放ID标识符是URI。 CAS 支持的默认机制是以实际用户登录 （即） 结束的 uri。 `http://my.cas.server/openid/myusername` 实际用户登录ID `缪斯纳梅`）。 不建议这样做，您应该考虑一种更详细的方式向用户提供 UURI。

<div class="alert alert-info"><strong>注意！</strong><p>OpenID 协议 <strong>与开放连接协议
</strong> 相同，该协议的详细信息 <a href="OIDC-Protocol.html">记录在这里</a>。</p></div>

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-开放-网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-authentication)。

## 注册客户端

在 CAS 服务注册处注册客户：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："https://openid.example.org/myapp"，
  "名称"："开放"，
  "描述"："开放ID示例应用"，
  "id"：10
}
```

## 示例客户端应用程序

- [打开ID客户端网络应用程序](https://github.com/cas-projects/openid-sample-java-webapp)

# 开放ID提供商代表团

使用 OpenID 协议，CAS 服务器也可以配置为 [将身份验证](../integration/Delegate-Authentication.html) 委托给 OpenID 提供商。
