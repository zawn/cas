---
layout: 违约
title: CAS - 拒绝身份验证
category: 认证
---

# 拒绝身份验证

拒绝身份验证允许 CAS 拒绝访问一组凭据。 那些不能与预先定义的集匹配的人将被盲目地接受。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持通用</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#reject-users-authentication)。
