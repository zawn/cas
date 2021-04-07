---
layout: 违约
title: CAS - SCIM 拨备集成
category: 集成
---

# 概述

[SCIM 标准](http://www.simplecloud.info/) 旨在通过定义代表用户和组的模式以及所有必要的 CRUD 操作的 REST API 来简化云中的用户管理和配置。 SCIM 与 CAS 的集成允许部署人员自动将经过验证的 CAS 委托人配置到 SCIM 服务器/目标，并提供额外支持，以便将主要属性映射到用户资源的适当声明和属性中。

由于 [未绑定ID](https://github.com/PingIdentity)提供的 SDK，支持 SCIM 版本 1.1 和 2。

启用 SCIM 的典型用例是及时同步和提供用户帐户，以 与 CAS 集成的服务和应用程序进行单次登录。 如果应用程序还拥有自己的帐户存储，可能需要 CAS 规范帐户存储（LDAP、JDBC 等）之间绘制用户帐户的地图，以及应用程序。 为适应这一问题，CAS 可以允许通过 SCIM 将经过验证的本金提供到拨备/身份/实体引擎，然后动态 将用户配置文件同步到目标系统。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 西姆</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#provisioning)。
