---
layout: 违约
title: CAS - 远程地址认证
category: 认证
---

# 远程地址身份验证

此处理程序使用请求的远程地址对用户进行透明身份验证，该地址已根据一系列配置的 IP 地址 验证。 这种方法的机制与 X.509 证书认证 非常相似，但信任却放在客户端内部网络地址上。

这种方法的好处是，透明认证是在大型企业 网络内实现的，而无需管理证书。

<div class="alert alert-danger"><strong>小心</strong><p>请记住，此身份验证
机制仅应启用具有相对静态 IP 地址的内部网络客户端。</p></div>

## 警告

此身份验证方法假定内部客户端将直接 攻击 CAS 服务器，而不是通过 Web 代理。 如果客户使用 Web 代理，远程地址查找成功的可能性会降低 ，因为 CAS 的客户端地址是代理服务器的 ，而不是客户端。 鉴于这种形式的 CAS 身份验证通常 部署在内部网络中，这通常不是问题。


## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套件服务器支持-通用远程网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#remote-address-authentication)。
