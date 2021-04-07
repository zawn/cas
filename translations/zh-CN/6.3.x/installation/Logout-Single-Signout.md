---
layout: 违约
title: CAS - 单一注销 & 注销
category: 索 & 斯洛
---

# 注销和单一注销 （SLO）

CAS 单个登录会话期间可能有许多活动应用会话， 注销和单个注销之间的区别基于在 _登录_ 操作中结束的会话数。

<div class="alert alert-info"><strong>协议支持</strong><p>请注意，此处描述的 SLO 专门涉及 CAS 协议的语义。 CAS 中的所有其他可用协议在处理、接收和发布注销请求时，无论 CAS 是作为身份提供商还是服务提供商，都可能提供和表现不同。 对每个协议实施的 SLO 支持可能有所不同，您应始终验证每个协议实施的可用功能范围。</p></div>

注销范围由行动发生地点决定：

1. 应用程序注销 - 结束单个应用程序会话
2. CAS 注销 - 结束 CAS SSO 会话

请注意，在简单的情况下，每个情况下的注销操作对另一个情况没有影响。 结束申请会话不会结束 CAS 会话，结束 CAS 会话也不会影响应用会话。 对于 SSO 系统的新用户和部署人员来说，这是造成 混乱的常见原因。

CAS 中的单一注销支持试图调和 CAS 注销和应用程序注销之间的差距。 当 CAS 配置为 SLO 时，它尝试在 SSO 会话期间向请求认证 CAS 的每个应用程序发送注销消息。 虽然这是一个尽最大努力的过程，但在许多情况下，它工作得很好，并通过在登录和注销之间创建对称性来提供一致的 用户体验。

<div class="alert alert-info"><strong>SSO 会话</strong><p>可以审查当前主动 SSO 会话的集合，
并确定 CAS 本身是否通过 <a href="../monitoring/Monitoring-Statistics.html">CAS 管理面板保持活跃的 SSO 会话。</a></p></div>

## CAS 注销

根据 [CAS议定书](../protocol/CAS-Protocol.html)， `/` 端点负责破坏当前的SSO会话。 在注销后，可能还可将重定向回服务。 这是通过指定重定向 链接通过 `服务` 参数来控制的。 指定 `服务` 必须在中科院服务注册处注册，并允许启用和 中科院遵循服务重定向。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logout)。

## 单次注销（SLO）

CAS 旨在支持单个签出：这意味着除了自己的 SSO 会话之外，它将能够使客户端应用会话失效。  
每当出票票明确过期时，将启动注销协议。 不支持 注销协议的客户可能会注意到其访问日志中似乎没有任何内容的额外请求。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认情况下，单个注销已打开。</p></div>

CAS 会话结束时，它会通知每个服务 SSO 会话不再有效，并且依赖方 需要使自己的会话失效。 请记住，提交给每个 CAS 保护的申请的回调 通知：而已。 应用程序</strong> **责任是拦截该通知并正确 通过特定端点手动或更常见地通过支持 SLO 的 CAS 客户端库手动销毁用户身份验证会话。</p>

另请注意，由于 SLO 是一个全球事件，默认情况下，所有具有 CAS 身份验证记录的应用都将 联系，如果这些应用彼此不同，这可能会对用户体验造成负面影响。 例如，如果用户已登录门户应用程序和电子邮件应用程序，则通过 SLO 注销一个应用程序也会 破坏另一个应用程序中的用户会话，如果应用程序没有仔细管理其会话和用户活动，则可能意味着数据丢失。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#single-logout)。

### 后通道

CAS 会直接向服务发送 HTTP POST 消息。 这是执行服务通知的传统方式。

示例回通道 SLO 消息：

```xml
<samlp:LogoutRequest
    xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol"
    xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion"
    ID="[RANDOM ID]"
    Version="2.0"
    IssueInstant="[CURRENT DATE/TIME]">
    <saml:NameID>@NOT_USED@</saml:NameID>
    <samlp:SessionIndex>[会话识别器]</samlp:SessionIndex>
</samlp:LogoutRequest>
```

### 前通道

CAS 通过 `JSONP` 向认证服务发出异步 AJAX `获取` 的注销请求。 CAS 客户的预期行为是使应用程序 Web 会话失效。

<div class="alert alert-warning"><strong>使用情况警告</strong><p>前通道注销可能不适用于所有 CAS 客户端。 在尝试之前，请确保您的 CAS 客户端支持此行为。</p></div>

CAS 提交的示例前通道 SLO 请求类似于以下格式：

```
卷曲 "https：//服务. url？ 回拨] jQuery112409319555380089843_1509437967792&注销查询[ BASE64 编码请求]&_=1509437967793"
```

## 斯洛请求

通知的完成方式（_后_ 或前</em> 通道 _）通过 `登录类型` 属性 配置在服务级别。 此值在默认情况下设置为 `LogoutType.BACK_CHANNEL` 。 消息 传递，或者将重定向发送到原始 CAS 协议票证请求的 _服务_ 参数中提供的 URL。</p>

会话标识符是 CAS 服务票证 ID，该 ID 是在最初 向 CAS 验证时提供给该服务的。 会话标识符用于将 CAS 会话与应用会话关联：例如，SLO 会话标识符映射到随后可以销毁以终止应用会话的伺服会话。

### 关闭单个注销

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#single-logout)。

### 将注销重定向到服务

注销请求可以选择地路由到绕过 CAS 登录屏幕的外部 URL。 为此，您需要根据 CAS 协议规范</a>

，通常以 `服务` 参数的形式指定目标目标。</p> 



### 每项服务单次注销

CAS 注册的应用程序可以选择通过 [服务管理](../services/Service-Management.html) 组件单独控制单个注销行为。 服务注册表中的每项注册服务都将包含描述如何提交注销请求的配置 。 此行为通过 `登录类型` 属性 进行控制，该允许指定注销请求是应通过后/前通道提交还是关闭此申请。

示例配置如下：



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："测试"，
  "名称"："测试"，
  "id"：1，
  "注销类型"："BACK_CHANNEL"
}
```




### 注销请求的服务端点

默认情况下，注销请求将提交到身份验证时收集的原始服务 ID。 CAS 可以选择将此类请求提交到与原始服务 ID 不同的 特定服务端点，当然也可以在每个服务级别上进行配置。 在 情况下，与 CAS 集成的应用程序并不完全使用支持拦截此类请求的 CAS 客户端，而是暴露了其注销操作的 不同端点的情况下，这非常有用。

要配置特定端点的服务，请尝试以下示例：



```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："testId"，
  "名称"："测试"，
  "id"：1，
  "登录类型"："BACK_CHANNEL"，
  "登录"："https://web.application.net/logout"
}
```




### 异步 SLO 消息

默认情况下，后路注销消息以异步方式发送到终点。 此行为可以通过 CAS 设置进行修改。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logout)。



## SSO 会话与应用会话

为了更好地了解中科院的SSO会话管理及其如何对待应用会话， 一个重要的事项是首先考虑：

<div class="alert alert-info"><strong>CAS不是会话管理器</strong><p>申请会话是应用程序的责任。</p></div>

CAS 希望以 `票务票务` 和 TGT ID 的形式维护和控制 SSO 会话，该 ID 以安全 cookie 的形式在 用户代理和 CAS 服务器之间共享。

CAS 不是应用会话管理器，因为维护和控制自己的 应用会话是申请 责任。  认证完成后，CAS 在应用会话方面通常 出图。 因此，应用程序会话本身的到期策略 完全独立于 CAS，如果应用会话过期，可能会根据理想的用户体验进行松散的 协调和调整。

通常，如果单个注销未激活，应用程序可能会暴露一个注销端点以破坏会话，接下来， 将代理重定向到 CAS `` 端点，以便完全销毁 SSO 会话。

下面是一个简短的图表，显示各种应用程序会话配置和与 CAS 的交互：

![](http://i.imgur.com/0XyuLgz.png)
