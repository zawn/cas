---
layout: 违约
title: CAS - 沙发基础认证
category: 认证
---

# 沙发底座验证

使用 [沙发基地](http://www.couchbase.com/)验证和验证凭据。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 沙发基地 - 认证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

身份验证策略能够获取用户属性作为身份验证事件的一部分。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-authentication)。

## 沙发基地主要属性

如果主要属性需要从 Couchbase 数据库中提取，而不一定对 Couchbase 进行身份验证，也可以使用上述依赖性。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase)。
