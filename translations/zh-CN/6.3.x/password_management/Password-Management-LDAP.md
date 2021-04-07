---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理-LDAP

帐户密码和安全性问题可以存储在LDAP服务器中。

通过在WAR叠加中包含以下依赖项来启用LDAP支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-pm-ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-password-management)。