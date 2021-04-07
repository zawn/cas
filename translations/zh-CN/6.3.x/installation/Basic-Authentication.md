---
layout: 默认
title: CAS-基本认证
category: 验证
---

# 基本认证

使用基本身份验证来验证和身份验证凭据。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-basic</artifactId>
  <version>${cas.version}</version>
</dependency>
```

`curl`类的命令行客户端访问受CAS保护的应用程序，可以使用以下命令：

```bash
卷曲 <APPLICATION-URL> -L -u <USER>：<PASSWORD>
```

使用 `--insecure -v` 标志绕过证书验证，并从 `curl`接收其他日志。

如果您的 `APPLICATION-URL` 和CAS服务器url不在同一主机上，则curl将 **NOT** 重定向时将基本身份验证标头发送到CAS `--location-trusted` 标志传递给curl来覆盖curl中的此行为。

从CURL手册页：

```bash
--location-trusted
        （HTTP / HTTPS）与-L一样，--location，但是允许将名称和密码发送到站点可能重定向到的所有主机。 这可能会或可能
        不是，如果该网站重定向到一个网站，你会送您的身份验证信息引入安全漏洞 
        （这是明文在HTTP的情况下，基本身份验证）。
```
