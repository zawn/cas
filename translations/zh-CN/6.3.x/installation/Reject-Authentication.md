---
layout: 默认
title: CAS-拒绝身份验证
category: 验证
---

# 拒绝认证

拒绝身份验证允许CAS拒绝访问一组凭据。 那些与预定义集合不匹配的将被盲目接受。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-generic</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#reject-users-authentication)。
