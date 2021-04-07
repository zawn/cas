---
layout: 违约
title: 中科院 - 基本认证
category: 认证
---

# 基本身份验证

使用基本身份验证验证和验证凭据。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-基本</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要使用命令线客户端（如 `卷曲`）访问 CAS 保护的应用程序，可以使用以下命令：

```bash
卷曲 <APPLICATION-URL> -L-u <USER>：<PASSWORD>
```

使用 `- 不安全 - v` 标志绕过证书验证，并接收来自 `卷曲`的其他日志。

如果您的 `应用-URL` 和 CAS 服务器 url 不在同一主机上，则卷曲将 **不** 重定向时将基本身份验证标题发送到 CAS 服务器。 卷曲中的这种行为可以通过传递 `来覆盖 - 位置信任的` 标志卷曲。

从CURL人页面：

```bash
-位置信任的
        （HTTP/HTTPS）喜欢-L，位置，但将允许发送名称+密码到所有主机，该网站可能会重定向。 如果网站将您重定向到 
        （HTTP 基础身份验证情况下为简体），则可能会或可能不会
        引入安全漏洞。
```
