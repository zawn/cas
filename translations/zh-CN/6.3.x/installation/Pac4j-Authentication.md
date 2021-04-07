---
layout: 默认
title: CAS-pac4j身份验证
category: 验证
---

<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-cas.png" width="300" />
</p>

## 概述

[pac4j](https://github.com/pac4j/pac4j) 项目是一个具有特定身份验证机制的安全引擎，其中 称为身份验证器，用于MongoDB，LDAP，CouchDb，JWT，RDBMS ...

pac4j身份验证器（和配置文件创建者）可以在CAS身份验证处理程序中

## 相依性

通过在WAR叠加中包含以下依赖关系来添加支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-pac4j-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

您可以将实现用于CAS用户名/密码凭据： `UsernamePasswordWrapperAuthenticationHandler`。
