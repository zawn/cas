---
layout: 违约
title: 中科院 - LDAP 认证
category: 认证
---

# LDAP 身份验证

LDAP 集成通过在叠加中包括以下依赖关系而实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-authentication)。

## 密码政策执行

要了解如何执行 LDAP 的密码策略，请 [查看本指南](Password-Policy-Enforcement.html)。

## 故障 排除

要启用其他日志，请修改记录配置文件以添加以下：

```xml
<Logger name="org.ldaptive" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
