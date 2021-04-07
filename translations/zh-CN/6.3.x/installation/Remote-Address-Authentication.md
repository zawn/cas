---
layout: 默认
title: CAS-远程地址认证
category: 验证
---

# 远程地址认证

该处理程序使用请求的远程地址来透明验证用户，并已针对一系列已配置的IP地址 这种方法的机制与 ，但是信任放在客户端内部网络地址上。

这种方法的好处是，无需管理证书即可在 

<div class="alert alert-danger"><strong>小心</strong><p>请记住，仅对具有相对静态IP地址的内部网络客户端启用
</p></div>

## 注意事项

这种身份验证方法假定内部客户端将直接命中CAS服务器 而不是通过Web代理访问。 在使用Web代理客户端的情况下的可能性 远程地址查找成功的降低，因为给CAS客户端地址是 的代理服务器，而不是在客户端。 假定这种形式的CAS身份验证通常在内部网络中部署


## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-generic-remote-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#remote-address-authentication)。
