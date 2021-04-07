---
layout: 违约
title: CAS - 可信认证
category: 认证
---

# 可信身份验证

受信任的身份验证处理程序支持 HTTP 请求处理链中其他组件 执行的信任身份验证。 代理（包括反向代理场景中的 Apache）是最常见的在 CAS 前执行身份验证的 组件。

通过在 WAR 叠加中包括以下依赖关系，支持可信身份验证处理器支持：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-信任-网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

可信身份验证能够通过以下方式提取远程身份验证用户：

1. 用户名可以从 `中提取，`
2. 用户名可以从 `赫特普服务要求#获取用户名（）`
3. 用户名可以从在 CAS 设置中定义名称的请求标题中提取。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#trusted-authentication)。
