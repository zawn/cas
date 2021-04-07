---
layout: 违约
title: CAS - pac4j 身份验证
category: 认证
---

<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-cas.png" width="300" />
</p>

## 概述

[pac4j](https://github.com/pac4j/pac4j) 项目是一个安全引擎，具有特定的身份验证机制， 称为身份验证器，用于蒙古银行、LDAP、库奇德布、JWT、RDBMS.。。

pac4j 身份验证器（和配置文件创建者）可以包裹在 CAS 身份验证处理器中 ，并用于身份验证。

## 屬地

通过在 WAR 叠加中包括以下依赖项来增加支持：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - pac4j - 身份验证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

您可以使用 CAS 用户名/密码凭据的实现： `用户名密码用户名用户名验证汉德勒`。
