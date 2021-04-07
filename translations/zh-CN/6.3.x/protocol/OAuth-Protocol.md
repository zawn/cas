---
layout: 默认
title: CAS-OAuth协议
category: 通讯协定
---

# OAuth协议

您可以使用以下方式配置CAS服务器：

* [OAuth客户端支持](../integration/Delegate-Authentication.html)，这意味着可以 委托给CAS，OpenID或OAuth提供程序。
* [的OAuth服务器支持](../installation/OAuth-OpenId-Authentication.html)，这意味着你将能够 通过与CAS服务器通信 [的OAuth 2.0协议](http://oauth.net/2/)。

## 乌玛

用户管理的访问（UMA）是一种轻量级的访问控制协议，该协议定义了集中式工作流程，以允许实体（用户或公司） 管理对其资源的访问。 UMA扩展了OAuth协议，并通过在集中式授权服务器（如CAS）上创建授权策略，为资源所有者提供对其受保护资源 请求方授予委派同意，以授权谁，什么人可以访问他们的数据以及访问时间。

要了解更多关于CAS UMA支持， [请参阅本指南](OAuth-UMA-Protocol.html)。
