---
layout: 默认
title: CAS-Couchbase身份验证
category: 验证
---

# Couchbase身份验证

[Couchbase](http://www.couchbase.com/)验证和认证凭据。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-couchbase-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

身份验证策略能够获取用户属性作为身份验证事件的一部分。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-authentication)。

## Couchbase主要属性

在需要从Couchbase数据库获取主体属性而不必针对Couchbase认证凭据的情况下，也可以使用上述依赖关系。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase)。
