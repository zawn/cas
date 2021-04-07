---
layout: 默认
title: CAS-可信认证
category: 验证
---

# 可信认证

可信认证处理程序提供对HTTP请求处理链中 执行的可信认证的支持。 代理（包括反向代理场景中的Apache）是在CAS前面执行身份验证

通过在WAR叠加中包含以下依赖关系来启用受信任的身份验证处理程序支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-trusted-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

受信任的身份验证可以通过以下方式提取远程身份验证的用户：

1. `HttpServletRequest＃getRemoteUser（）`提取用户名。
2. `HttpServletRequest＃getUserPrincipal（）`提取用户名
3. 可以从在CAS设置中定义名称的请求标头中提取用户名。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#trusted-authentication)。
