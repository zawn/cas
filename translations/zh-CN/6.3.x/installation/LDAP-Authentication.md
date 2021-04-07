---
layout: 默认
title: CAS-LDAP认证
category: 验证
---

# LDAP验证

通过在叠加层中包含以下依赖项来启用LDAP集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-authentication)。

## 密码策略执行

要了解如何为LDAP实施密码策略，请 [本指南](Password-Policy-Enforcement.html)。

## 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：

```xml
<Logger name="org.ldaptive" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
