---
layout: 默认
title: CAS-OpenID协议
category: 通讯协定
---

# OpenID协议

OpenID是一个开放，分散，免费的框架，用于以用户为中心的数字身份。 用户使用URI自己 有关更多信息，请参见 [http://www.openid.net](http://www.openid.net)。

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>不推荐使用此功能，并计划在将来将其删除。</strong> 如果可以，请考虑使用
作为主流和最新的身份验证协议。</p>
</div>

CAS支持OpenID协议的“哑”和“智能”模式。 哑模式以类似于现有CAS协议的 智能模式的不同之处在于，它在客户端和 开头的openId提供程序（OP）之间建立了关联。 由于该关联以及关联期间完成的密钥交换，因此使用此密钥对客户端和提供者之间交换的 最终请求不需要 （在CAS协议中等同于票证验证）。

OpenID标识符是URI。 CAS支持中的默认机制是一个以实际用户登录名 结尾的uri（即 `http：//my.cas.server/openid/myusername` ，其中实际用户登录ID为 `myusername`）。 不建议这样做，您应该考虑一种为用户提供URI的更精细的方法。

<div class="alert alert-info"><strong>请注意！</strong><p>OpenID协议是 <strong>NOT</strong> 同样的事情
作为ID连接协议，其细节是 <a href="OIDC-Protocol.html">记录在这里</a>。</p></div>

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-openid-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-authentication)。

## 注册客户

在CAS服务注册表中注册客户端：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://openid.example.org/myapp”，
  “名称”： “的OpenID”，
  “描述“：” OpenID样本应用程序“，
  ” id“：10
}
```

## 样例客户端应用程序

- [OpenID客户端Webapp](https://github.com/cas-projects/openid-sample-java-webapp)

# OpenID提供者委托

使用OpenID协议，也可以将CAS服务器配置为 [将身份验证](../integration/Delegate-Authentication.html) 委派给OpenID提供者。
