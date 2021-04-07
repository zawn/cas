---
layout: 默认
title: CAS-Google Apps集成
category: 一体化
---

# 概述

Google Apps for Education（或任何Google Apps）利用SAML 2.0为外部身份验证服务 

<div class="alert alert-warning"><strong>用法</strong>
<p>此处描述的Google Apps for Education集成允许CAS充当小型化的SAML2身份提供者， 
表示可能无法准备开启的部署，并允许CAS完全充当SAML2身份提供者。 
<strong>不推荐使用此功能，并计划在将来将其删除。</strong> 这不
多大意义打开，并在同一时间使用CAS这两项功能，作为一个级别高于对方，很可能
，能同时使用CAS功能都将与这两者的功能造成干扰。 如果可以，请考虑像其他任何SAML2服务提供者一样，在CAS中
</p>
</div>

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-googleapps</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 生成公钥/私钥

第一步是生成DSA / RSA公钥和私钥。 这些用于签署和阅读断言。 创建密钥后，需要向Google注册公共密钥。

密钥也需要通过类路径可供CAS应用程序使用（但不能通过Internet公开使用） ，尽管可以接受运行Web服务器 实例的用户访问且不公开提供给Internet的任何位置。  因此，在 `src / main / resources` 是 不错，因为它的作用域是Web应用程序，但通常不提供服务。 `/ etc / cas /` 也很好，它可以防止密钥在部署新的CAS Webapp版本时被覆盖。

```bash
openssl genrsa -out private.key 1024
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outder DER -nocrypt -in private.key -out private .p8
openssl req -new -x509 -key private.key -out x509.pem -days 365
```

`x509.pem` 文件应上传到Google Apps中的“安全性/ SSO”下。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-apps-authentication)。

## 注册Google Apps

确保Google Apps已在您的 [服务注册表](../services/Service-Management.html)注册。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://www.google.com/a/YourGoogleDomain/acs”，
  “名称”： “的GoogleApps” ，
  “ID”：1000，
  “evaluationOrder”：10
}
```

## 配置用户名属性

作为可选步骤，您可以配置备用用户名，以在SAML回复中发送给Google。 [用户名属性提供程序](../services/Service-Management.html) 为注册的Google Apps服务在CAS服务注册表中指定该备用用户名

## 配置Google

您需要向Google提供基于SAML的SSO服务的URL，以及用户在退出托管的Google应用程序 为Google Apps配置时，请使用以下网址：

* 登录页面网址： `https://sso.school.edu/cas/login`
* 登出页面网址： `https://sso.school.edu/cas/logout`
* 更改密码网址： `https://mgmt.password.edu/`

## 测试

尝试使用以下网址访问Google托管的应用程序，例如Google Calendar `https://calendar.google.com/a/YourGoogleDomain`
