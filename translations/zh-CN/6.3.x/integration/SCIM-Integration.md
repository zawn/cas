---
layout: 默认
title: CAS-SCIM设置集成
category: 一体化
---

# 概述

通过定义用于表示用户和组的架构以及用于所有必要CRUD操作的REST API，创建了 [SCIM标准](http://www.simplecloud.info/) SCIM与CAS的集成使部署人员可以将经过身份验证的CAS主体自动提供给SCIM服务器/目标，并提供额外的支持，以将主体属性映射到用户资源的适当声明和属性中。

[UnboundID](https://github.com/PingIdentity)提供的SDK，都支持SCIM版本1.1和2。

启用SCIM的典型用例是及时将用户帐户同步和配置到 服务和应用程序中，以进行单点登录。 如果应用程序还具有自己的帐户存储， 的CAS规范帐户存储（LDAP，JDBC等）与应用程序之间的用户帐户映射。 为了解决此问题，可以允许CAS 的身份验证的主体提供给供应/身份/实体引擎，然后将动态 将用户配置文件同步到目标系统。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-scim</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#provisioning)。
