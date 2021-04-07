---
layout: 默认
title: CAS-MongoDb认证
category: 验证
---

# MongoDb验证

[MongoDb](https://www.mongodb.org/) 实例验证和认证凭据。 通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-mongo</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#mongodb-authentication)。

可以在集合中找到这样的帐户：

```json
{
    “用户名”：“ casuser”，
    “密码”：“ 34598dfkjdjk3487jfdkh874395”，
    “ first_name”：“ john”，
    “ last_name”：“ smith”
}
```
