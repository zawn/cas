---
layout: 默认
title: CAS-Shibboleth集成
category: 一体化
---

# 概述

通过 不同的策略，可以将CAS与 [Shibboleth联邦SSO平台](http://shibboleth.net/) 可以指定CAS作为Shibboleth IdP的身份验证提供者。 通过这种设置，当用户被路由到IdP时，可能会发生以下情况：

- 如果用户已经通过CAS认证并且具有有效的CAS SSO会话，则IdP将透明地 执行所请求的操作，例如属性释放。
- 如果用户没有有效的CAS SSO会话，则该用户将被重定向到CAS，并且必须为 身份验证，然后IdP才能继续执行所请求的操作。

<div class="alert alert-info"><strong>笔记</strong><p>请记住，此页面专门用于Shibboleth Identity Provider的集成选项。 如果您需要CAS自己充当SAML2身份提供者，则应从 <a href="../installation/Configuring-SAML2-Authentication.html">开始，而不是</a>。</p></div>

## Shibboleth IdP的SSO（外部）

这是一个Shibboleth IdP外部身份验证插件，它将 身份验证委派给CAS。 该解决方案具有 种完整的本机CAS协议功能（例如 `续订` 和 `网关`。

该插件可用于 Shibboleth Identity Provider [v2](https://github.com/Unicon/shib-cas-authn2) 和 [v3](https://github.com/Unicon/shib-cas-authn3) 和 [v4](https://github.com/Unicon/shib-cas-authn)。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-shibboleth</artifactId>
  <version>${cas.version}</version>
</dependency>
```

### 依赖方EntityId

身份验证插件可以根据身份验证请求将 的依赖方的实体ID传递给CAS服务器。 实体ID以url参数的形式传递给CAS服务器，如下所示：

```
https://sso.example.org/cas/login?service=<authentication-plugin-url>&entityId =<relying-party-entity-id>
```

您还可以利用 `entityId` 参数并将其视为普通的CAS服务定义 以便将其用于多因素身份验证和授权。

有关更多信息，请参见 [本指南](../mfa/Configuring-Multifactor-Authentication-Triggers.html)

## 显示SAML MDUI

CAS服务器能够识别 `entityId` 上显示SAML MDUI，该页面由与依赖方关联的元数据提供。

### 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-mdui</artifactId>
  <version>${cas.version}</version>
</dependency>
```

### 依赖方元数据

您可以允许CAS直接从通过设置提供给CAS的元数据文档中识别SAML MDUI。 如果依赖方的元数据与请求的 `entityId` 匹配并且包含MDUI元素，则这些元素将传递到登录页面上进行修饰。 如果元数据中没有MDUI，则将完全使用服务注册表中匹配服务的相关元素。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-metadata-ui)。

### 服务注册表元数据

您也可以在CAS服务注册表中将依赖方注册为常规服务应用程序，并只需在注册记录的正文中指定许多类似MDUI的元素。 下面是一个示例：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ relying-party-entity-id”，
  “ name”：“ Test”，
  “ id”：100，
  “描述”： “这是测试应用程序。”，
  “evaluationOrder”：10000，
  “标志”： “图像/ logo.png”，
  “informationUrl”：“https://test.example.org/ info”，
  “ privacyUrl”：“ https://test.example.org/privacy”
}
```
