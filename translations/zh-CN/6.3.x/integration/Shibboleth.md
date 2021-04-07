---
layout: 违约
title: 中科院 - 希伯莱整合
category: 集成
---

# 概述

CAS 可以与 [Shibboleth 联邦 SSO 平台集成，由几个 不同的策略](http://shibboleth.net/) 。 可以指定中科院作为希伯莱思IdP的认证提供商。 使用此设置时，当用户被路由到 IdP 时，可能会发生以下情况：

- 如果用户已对 CAS 进行身份验证，并且具有有效的 CAS SSO 会话，则 IdP 将透明地 执行所要求的操作，例如属性发布。
- 如果用户没有有效的 CAS SSO 会话，则用户将被重定向到 CAS，并且必须在 IDP 继续执行所要求的行动之前 身份验证。

<div class="alert alert-info"><strong>注意</strong><p>请记住，此页面专门用于与 Shiboleth 身份提供商的集成选项。 如果您需要 CAS 自行充当 SAML2 身份提供商，您应该 <a href="../installation/Configuring-SAML2-Authentication.html">从这里开始，而不是</a>。</p></div>

## 希博莱思 Idp 的 Sso （外部）

这是一个 Shibboleth IdP 外部身份验证插件， 认证委托给 CAS。 此解决方案能够 利用各种本地 CAS 协议功能，如 `续订` 和 `网关`。

该插件适用于 Shibboleth 身份提供商 [v2](https://github.com/Unicon/shib-cas-authn2) 和 [v3](https://github.com/Unicon/shib-cas-authn3) 和 [v4](https://github.com/Unicon/shib-cas-authn)。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 希博莱斯</artifactId>
  <version>${cas.version}</version>
</dependency>
```

### 依赖方实体Id

身份验证插件能够将依赖方的实体 ID 通过 传递到 CAS 服务器，以实现身份验证请求。 实体 ID 以网址参数的形式传递到 CAS 服务器：

```
https://sso.example.org/cas/login?service=<authentication-plugin-url>&实体=<relying-party-entity-id>
```

您还可以利用 `实体Id` 参数，将其视为一个正常的CAS服务定义， 以便用于多因素认证和授权。

有关详细信息，请参阅本指南</a>。</p> 



## 显示萨姆尔·穆杜伊

CAS服务器能够识别 `实体Id` 参数，并在登录页面上显示SAML MDUI， 由与依赖方关联的元数据提供。



### 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 姆杜伊</artifactId>
  <version>${cas.version}</version>
</dependency>
```




### 依赖方元数据

您可以允许 CAS 通过设置直接从馈送至 CAS 的元数据文档中识别 SAML MDUI。 如果依赖方的元数据与所要求的 `实体Id` 并包含 MDUI 元素相匹配，则这些元数据将传递到登录页面以进行装饰。 如果元数据中不可用 MDUI，则服务注册表中匹配服务的相关元素将全部相同。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-metadata-ui)。



### 服务注册表元数据

您也可以在 CAS 服务注册处注册依赖方作为常规服务应用程序，只需在注册记录的主体中指定一些类似 MDUI 的元素。 示例如下：



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："依靠方实体-id"，
  "名称"："测试"，
  "id"：100，
  "说明"："这是测试申请"，
  "评估序"：10000，
  "logo"："图像/徽标.png"，
  "信息"："https://test.example.org/info"，
  "隐私"："https://test.example.org/privacy"
}
```
