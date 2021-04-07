---
layout: 违约
title: CAS - 谷歌应用集成
category: 集成
---

# 概述

谷歌教育应用（或任何谷歌应用程序）使用SAML 2.0为外部身份验证服务提供 集成点。

<div class="alert alert-warning"><strong>用法</strong>
<p>此处描述的 Google 教育应用集成允许 CAS 充当小型 SAML2 身份提供商， 
可能不准备打开的部署，并允许 CAS 完全充当 SAML2 身份提供商。 
<strong>此功能被弃用，并计划在未来删除。</strong> 同时打开和使用 CAS 中的两个功能
没有多大意义，因为其中一个功能胜过另一个功能，而且很可能
同时在 CAS 中同时使用这两个功能会干扰两者的功能。 如果可以，请考虑使用
CAS 中的 SAML2 身份提供商功能来处理此集成，就像任何其他 SAML2 服务提供商一样。</p>
</div>

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 谷歌应用程序</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 生成公钥/私钥

第一步是生成 DSA/RSA 公钥和私钥。 这些用于签署和阅读断言。 创建密钥后，公共密钥需要在 Google 注册。

CAS 应用程序（但不能通过互联网公开提供）也需要通过类路径 提供密钥，尽管运行 Web 服务器的用户 实例访问且未向互联网公开服务的任何位置都是可以接受的。  因此，内部 `src/主/资源` 很好，因为它的范围到网络应用程序，但通常不服务。 `/等/cas/` 也很好，并保护密钥在部署新的 CAS Webapp 版本时不会被覆盖。

```bash
打开sl genrsa-出私人.key 1024
打开sl rsa-酒吧-在私人.key-出公共.key-通知PEM-超越DER
打开sl pkcs8-topk 8 -通知PER-外形DER-非晶体-在私人.key-出私人.p8
打开-新-x509-关键私人.key-出x509.pem-天365
```

`x509.pem` 文件应该在安全/SSO下上传到谷歌应用程序。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-apps-authentication)。

## 注册谷歌应用程序

确保 Google 应用在您的 [服务注册表](../services/Service-Management.html)中注册。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："https://www.google.com/a/YourGoogleDomain/acs"，
  "名称"："googleApps"，
  "id"：1000，
  "评估订购"：10

```

## 配置用户名属性

作为可选步骤，您可以在 SAML 回复中配置另一个用户名发送到 Google。 此备用用户名 可以通过注册的 Google Apps 服务</a> 的

用户名属性提供商在 CAS 服务注册表中指定。</p> 



## 配置谷歌

您需要为基于 SAML 的 SSO 服务向 Google 提供 URL，以及用户在注销托管的 Google 应用程序时 重定向到的 URL。 在为 Google 应用进行配置时，请使用以下网址：

* 登录页面网址： `https://sso.school.edu/cas/login`
* 签出页面网址： `https://sso.school.edu/cas/logout`
* 更改密码网址： `https://mgmt.password.edu/`



## 测试

尝试访问谷歌托管的应用程序，如谷歌日历 与网址： `https://calendar.google.com/a/YourGoogleDomain`
