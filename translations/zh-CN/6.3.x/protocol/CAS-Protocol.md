---
layout: 违约
title: 中科院 - CAS 协议
category: 协议
---


# 中科院议定书

CAS 协议是一个简单而强大的基于票证的协议。 完整的协议规范可以在这里找到 [](CAS-Protocol-Specification.html)。

它涉及一个或多个客户端和一个服务器。 客户嵌入 *CAS化* 应用程序（称为"CAS 服务"），而 CAS 服务器是一个独立的组件：

- [CAS 服务器](../installation/Configuring-Authentication-Components.html) 负责对用户进行身份验证并授予对应用程序的访问权限
- [CAS客户端](../integration/CAS-Clients.html) 保护CAS应用程序，并从CAS服务器检索授权用户的身份。

主要概念是：

- 存储在 `TGC` cookie 中的 `TGT` （票务赠与票证）代表用户的 SSO 会话。
- `ST` （服务票）作为网址中的 `GET` 参数传输，代表 CAS 服务器授予特定用户的 *CAS 化* 应用程序的访问权限。


## 规格版本

以下规范版本由阿佩雷奥中科院认可并实施。

### 3.0.3

目前中科院协议规范为 `303`。 实际协议规范 [](CAS-Protocol-Specification.html)在这里提供，这是由Apereo CAS服务器作为官方参考实施实施。 它主要是捕获建立在 CAS 协议修订的基础上的最常见增强功能 `2.0`。 除其他功能外，版本之间最明显的更新 `2.0` 和 `3.0` 是能够通过新的 `/p3/服务验证` 端点返回身份验证/用户属性。

### 2.0

2.0</code> 协议规范 版本可在CAS-协议规范</a><a href="CAS-Protocol-V2-Specification.html">提供。 </p>

<h2 spaces-before="0">网络流图</h2>

<a href="../images/cas_flow_diagram.png" target="_blank"><img src="../images/cas_flow_diagram.png" alt="CAS 网络流图" title="CAS 网络流图" /></a>

<h2 spaces-before="0">代理 Web 流图</h2>

<p spaces-before="0">CAS 协议最强大的功能之一是 CAS 服务能够充当另一个 CAS 服务的代理，传输用户身份。</p>

<a href="../images/cas_proxy_flow_diagram.jpg" target="_blank"><img src="../images/cas_proxy_flow_diagram.jpg" alt="CAS 代理 Web 流图" title="CAS 代理 Web 流图" /></a>

<h2 spaces-before="0">其他协议</h2>

<p spaces-before="0">即使 CAS 服务器的主要目标是实施 CAS 协议，其他协议也作为扩展支持：</p>

<ul>
<li><a href="../protocol/OpenID-Protocol.html">开放ID</a></li>
<li><a href="../protocol/OAuth-Protocol.html">非统组织2</a></li>
<li><a href="../protocol/SAML-Protocol.html">萨姆尔</a></li>
<li><a href="../protocol/OIDC-Protocol.html">打开ID连接</a></li>
<li><a href="../protocol/REST-Protocol.html">休息</a></li>
<li><a href="../protocol/WS-Federation-Protocol.html">威斯费德</a></li>
</ul>

<hr />

<h1 spaces-before="0">委托身份验证</h1>

<p spaces-before="0">使用 CAS 协议，CAS 服务器也可以配置为 <a href="../integration/Delegate-Authentication.html">将身份验证</a> 委托给另一个 CAS 服务器。</p>

