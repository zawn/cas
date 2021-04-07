---
layout: 默认
title: CAS-Okta身份验证
category: 验证
---

# Okta身份验证

与Okta的集成是一个方便的包装，可以将 [封装起来。Okta的Authentication API](https://developer.okta.com/docs/api/resources/authn.html) 和 在您需要接受和验证Okta管理的凭据时非常有用。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-okta-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#okta-authentication)。
