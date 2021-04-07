---
layout: 默认
title: CAS-注销 & 单次注销
category: SSO & SLO
---

# 注销和单次注销（SLO）

CAS单点登录会话期间可能有许多活动的应用程序会话，并且 注销和单注销 _注销_ 操作结束的会话数。

<div class="alert alert-info"><strong>协议支持</strong><p>请注意，此处描述的SLO特别处理CAS协议的语义。 无论是CAS作为身份提供者还是服务提供者，在处理，接收和发布注销请求时，CAS中的所有其他可用协议都可能会提供不同的服务并表现不同的行为。 SLO对每种协议实现的支持可能会有所不同，您应始终验证每种协议实现的可用功能范围。</p></div>

注销的范围由操作发生的位置决定：

1. 应用程序注销-结束单个应用程序会话
2. CAS注销-结束CAS SSO会话

请注意，在简单情况下，注销操作在每种情况下都不会对其他情况产生影响。 结束应用程序会话 不会结束CAS会话，并且结束CAS会话不会影响应用程序会话。 对于SSO系统的新用户和部署者，这是造成

CAS中的单一注销支持尝试调和CAS注销和应用程序注销之间的差异。 CAS时，它将尝试在SSO会话期间 CAS进行身份验证的应用程序发送注销消息。 尽管这是尽力而为的过程，但在许多情况下，它都能很好地发挥作用，并且通过在登录和注销之间建立对称性来 

<div class="alert alert-info"><strong>SSO会议</strong><p>可以查看当前活动SSO会话的集合
 <a href="../monitoring/Monitoring-Statistics.html">CAS管理面板确定CAS本身是否维护活动SSO会话。</a></p></div>

## CAS注销

每 [CAS协议](../protocol/CAS-Protocol.html)中， `/注销` 端点是负责销毁当前SSO会话。 注销后，也可能需要重定向回服务。 这是 `service` 参数 链接来控制的。 指定的 `服务` 必须在CAS的服务注册表中注册并启用，并且 CAS必须被允许遵循服务重定向。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logout)。

## 单一登出（SLO）

CAS旨在支持单点注销：这意味着除了其自己的SSO会话之外，它将能够使客户端应用程序会话无效。  
每当授予票证的票证明确到期时，都会启动注销协议。 不支持 注销协议的客户端可能会在其访问日志中注意到额外的请求，这些请求似乎没有任何作用。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认情况下，“单一注销”处于打开状态。</p></div>

当CAS会话结束时，它将通知每个服务SSO会话不再有效，并且依赖方 需要使自己的会话无效。 请记住，提交给每个受CAS保护的应用程序的回调为 是一个通知；而已。 </strong> 负责拦截该通知，并正确地 破坏用户身份验证会话，这是 **责任，这既可以通过特定端点手动进行，也可以通过支持SLO的CAS客户端库更常见地进行。</p>

还要注意，由于SLO是一个全球性的活动，即有CAS认证记录所有的应用程序在默认情况下是 联系，如果这些应用程序是彼此独立不同，这可能不利破坏用户体验。 举个例子，如果用户已经登录到门户应用程序和电子邮件应用程序，通过SLO记录一出将 还破坏其他用户会话这可能意味着数据丢失，如果应用没有谨慎地管理其会话和用户活动。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#single-logout)。

### 后通道

CAS将HTTP POST消息直接发送到服务。 这是对服务执行通知的传统方式。

样本反向通道SLO消息：

```xml
<samlp:LogoutRequest
    xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol"
    xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion"
    ID="[RANDOM ID]"
    Version="2.0"
    IssueInstant="[CURRENT DATE/TIME]">
    <saml:NameID>@ NOT_USED @</saml:NameID>
    <samlp:SessionIndex>[会议标识符]</samlp:SessionIndex>
</samlp:LogoutRequest>
```

### 前通道

`JSONP` 向已认证的服务发出异步AJAX `GET` 注销请求。 CAS客户端的预期行为是使应用程序Web会话无效。

<div class="alert alert-warning"><strong>使用警告</strong><p>前通道注销可能不适用于所有CAS客户端。 在尝试之前，请确保您的CAS客户端确实支持此行为。</p></div>

CAS提交的示例前通道SLO请求类似于以下格式：

```
curl'https：//service.url？callback = jQuery11240931955555589089843_1509437967792&logoutRequest = [BASE64编码的请求]&_ = 1509437967793'
```

## SLO要求

通过 `logoutType` 属性在服务级别 配置完成通知的方式（_后_ 或 _前_ 默认情况下，此值设置为 `LogoutType.BACK_CHANNEL` 消息已 或将重定向发送到原始CAS协议票证请求 _service_

会话标识符是最初向CAS 时提供给服务的CAS服务凭单ID。 会话标识符用于将CAS会话与应用程序会话相关联；例如，SLO 会话标识符映射到一个Servlet会话，该Servlet会话随后可以被销毁以终止应用程序会话。

### 关闭单一登出

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#single-logout)。

### 将注销重定向到服务

注销请求可以选择绕过CAS注销屏幕，路由到外部URL。 为此，通常需要 [CAS协议规范](../protocol/CAS-Protocol-Specification.html)为CAS注销端点 `服务` 参数的形式指定目标目的地。

### 每项服务单次登出

通过CAS注册的应用程序可以选择通过 [服务管理](../services/Service-Management.html) 组件分别控制单个注销行为。 服务注册表中的每个已注册服务都将包含配置 ，该配置0描述了应如何提交注销请求。 此行为通过 `logoutType` 属性 进行控制，该属性2可以指定注销请求是应通过后向/前向通道提交还是应针对该应用程序关闭。

配置示例如下：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “testId”，
  “名称”： “testId”，
  “ID”：1，
  “logoutType”：“ BACK_CHANNEL“
}
```

### 注销请求的服务端点

默认情况下，注销请求将提交到身份验证时收集的原始服务ID。 CAS可以选择将此类请求提交到 的特定服务端点，并且当然可以在每个服务级别上进行配置。 集成的应用程序不完全使用支持拦截此类请求的CAS客户端，而是 不同的终结点时，这在

要配置特定于服务的端点，请尝试以下示例：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “testId”，
  “名称”： “testId”，
  “ID”：1，
  “logoutType”：“ BACK_CHANNEL“，
  ” logoutUrl“：” https://web.application.net/logout“
}
```

### 异步SLO消息

默认情况下，反向通道注销消息以异步方式发送到端点。 可以通过CAS设置修改此行为。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logout)。

## SSO会话与应用程序会话

为了更好地了解CAS的SSO会话管理，以及如何对应用的会话， 一个重要的一点是要首先考虑的：

<div class="alert alert-info"><strong>CAS不是会话管理器</strong><p>应用程序会话是应用程序的责任。</p></div>

CAS希望维持和的形式控制所述SSO会话 的 `TicketGrantingTicket` 以及设置在之间共享的TGT ID 的用户代理和在安全cookie的形式的CAS服务器。

CAS不是应用程序会话管理器，因为维护和控制自己的 应用程序会话 一旦完成身份验证，就应用程序会话而言 因此，应用程序会话本身的到期策略 完全独立于CAS，可以在应用程序会话到期的情况下根据理想的用户体验

在未激活“单一注销”的情况下，通常，应用程序可能会公开注销端点以破坏会话，然后将 重定向到CAS `注销` 端点，以便也完全破坏SSO会话。

这是一个简短的图，演示了各种应用程序会话配置以及与CAS的交互：

![](http://i.imgur.com/0XyuLgz.png)
