---
layout: 违约
title: 中科院 - 非大议定书
category: 协议
---

# 非航空议定书

您可以将 CAS 服务器配置为：

* [OAuth 客户端支持](../integration/Delegate-Authentication.html)，这意味着身份验证可以通过登录页面上的链接 委托给 CAS、OpenID 或 OAuth 提供商。
* [非授权服务器支持](../installation/OAuth-OpenId-Authentication.html)，这意味着您将能够通过 [OAuth 2.0 协议](http://oauth.net/2/)与 CAS 服务器 通信。

## 乌马

用户管理访问 （UMA） 是一种轻量级的访问控制协议，它定义了集中的工作流，允许实体（用户或公司） 管理对其资源的访问。 UMA 扩展了 OAuth 协议，并通过在 CAS 等集中授权服务器上创建授权策略，为资源所有者提供对其受保护资源 的精细管理。 授权服务器授予授权 代表资源所有者请求方授权谁和什么可以访问其数据以及访问时间。

有关中科院 UMA 支持的更多了解， [请参阅本指南](OAuth-UMA-Protocol.html)。
