---
layout: 违约
title: CAS - 密码同步
category: 认证
---

# 密码同步

作为身份验证活动的一部分，CA 提供在各种 目的地同步和更新帐户密码的能力。 如果身份验证尝试成功， CAS 将尝试捕获在 CAS 设置中指定的 提供的密码和更新目的地。 未能同步帐户密码通常会 日志中产生错误，并且事件不被视为灾难性故障。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#password-synchronization)。

## 阿尔达普

将帐户密码与一个或多个LDAP服务器同步。 支持通过在 WAR 叠加中包括以下依赖项的 来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```
