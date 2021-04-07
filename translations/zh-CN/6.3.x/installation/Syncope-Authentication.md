---
layout: 默认
title: CAS-Apache Syncope身份验证
category: 验证
---

# Apache Syncope身份验证

[Apache Syncope](http://syncope.apache.org/)处理身份验证事件。 这是通过使用正在运行的Syncope实例公开 `rest / users / self` 作为成功身份验证尝试的一部分，将提供的用户对象的属性转换为CAS属性，然后可以将其发布给应用程序等。


## 成分

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-syncope-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#syncope-authentication)。

## 属性

作为成功身份验证尝试的一部分，CAS收集了Apache Syncope提供的以下属性：

| 属性名称                          |
| ----------------------------- |
| `syncopeUserRoles`            |
| `syncopeUserSecurityQuestion` |
| `syncopeUserStatus`           |
| `syncopeUserRealm`            |
| `syncopeUserCreator`          |
| `syncopeUserCreationDate`     |
| `syncopeUserChangePwdDate`    |
| `syncopeUserLastLoginDate`    |
| `syncopeUserDynRoles`         |
| `syncopeUserDynRealms`        |
| `syncopeUserMemberships`      |
| `syncopeUserDynMemberships`   |
| `syncopeUserDynRelationships` |
| `syncopeUserAttrs`            |

请注意，仅当属性包含值时才收集它们。
