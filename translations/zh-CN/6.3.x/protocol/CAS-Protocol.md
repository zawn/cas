---
layout: 默认
title: CAS-CAS协议
category: 通讯协定
---


# CAS协议

CAS协议是一种简单且功能强大的基于票证的协议。 完整的协议规范可在此处</a>
。</p> 

它涉及一个或多个客户端和一台服务器。 客户端被嵌入 *CASified* 应用程序（称为“ CAS服务”）中，而CAS服务器是一个独立的组件：

- [CAS服务器](../installation/Configuring-Authentication-Components.html) 负责验证用户身份并授予对应用程序的访问权限
- [CAS客户端](../integration/CAS-Clients.html) 保护CAS应用程序，并从CAS服务器检索被授予用户的身份。

关键概念是：

- `TGC` cookie中的 `TGT` （票证授予票证）代表用户的SSO会话。
- `GET` 参数传输的 `ST` （服务票证）代表CAS服务器授予特定用户 *CASified*




## 规格版本

以下规范版本是Apereo CAS认可并实现的。



### 3.0.3

当前的CAS协议规范为 `3.0.3`。 实际的协议规范 [在此处](CAS-Protocol-Specification.html)可用，它由Apereo CAS Server实施，作为官方参考实现。 它主要捕获了基于CAS协议修订版 `2.0`构建的最常见的增强功能。 在其他功能中，版本 `2.0` 和 `3.0` `/ p3 / serviceValidate` 端点返回身份验证/用户属性的功能。



### 2.0

版本 `2.0` 协议规范可在 [CAS-Protocol-Specification](CAS-Protocol-V2-Specification.html)。 



## 网络流程图

<a href="../images/cas_flow_diagram.png" target="_blank"><img src="../images/cas_flow_diagram.png" alt="CAS Web流程图" title="CAS Web流程图" /></a>

## 代理Web流程图

CAS协议最强大的功能之一是使CAS服务能够充当另一个CAS服务的代理，从而传输用户身份。

<a href="../images/cas_proxy_flow_diagram.jpg" target="_blank"><img src="../images/cas_proxy_flow_diagram.jpg" alt="CAS代理Web流程图" title="CAS代理Web流程图" /></a>

## 其他协议

即使CAS服务器的主要目标是实现CAS协议，也支持其他协议作为扩展：

- [OpenID](../protocol/OpenID-Protocol.html)
- [OAuth2](../protocol/OAuth-Protocol.html)
- [SAML](../protocol/SAML-Protocol.html)
- [OpenID连接](../protocol/OIDC-Protocol.html)
- [休息](../protocol/REST-Protocol.html)
- [联合会](../protocol/WS-Federation-Protocol.html)



***



# 委托认证

使用CAS协议，也可以将CAS服务器配置为 [将身份验证](../integration/Delegate-Authentication.html) 委派给另一台CAS服务器。

