---
layout: 默认
title: CAS-密码同步
category: 验证
---

# 密码同步

作为身份验证事件的一部分， 目标中同步和更新帐户密码的功能。 如果身份验证尝试成功，则 CAS将尝试捕获提供的密码并更新在CAS设置 无法同步帐户密码通常会在日志 产生错误，并且该事件不被视为灾难性故障。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#password-synchronization)。

## LDAP

将帐户密码与一台或多台LDAP服务器同步。 通过在WAR叠加中 依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```
