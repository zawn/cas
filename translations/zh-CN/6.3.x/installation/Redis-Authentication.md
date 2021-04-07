---
layout: 默认
title: CAS-Redis身份验证
---

# Redis认证

[Redis](https://redis.io/)验证和认证凭据。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-redis-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

用户帐户被映射到 `用户名` 字段作为键。 用户帐户记录将包含以下字段：

| 场地   | 描述                                                                |
| ---- | ----------------------------------------------------------------- |
| `密码` | 具有适用编码的用户密码（如果有）。                                                 |
| `地位` | 之一的 `行`， `LOCKED`， `DISABLED`， `EXPIRED`， `MUST_CHANGE_PASSWORD`。 |
| `属性` | 用户属性建模为 `Map<String, List<Object>>`。                  |

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#redis-authentication)。

## Redis主体属性

Redis数据库中获取主体属性而不必对Redis进行身份验证的情况下，也可以使用上述依赖关系。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis)。
