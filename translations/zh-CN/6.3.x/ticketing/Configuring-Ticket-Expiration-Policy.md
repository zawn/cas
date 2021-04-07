---
layout: 默认
title: CAS-配置票证过期策略组件
category: 售票处
---

# 票证过期政策

CAS支持可插入和可扩展的策略框架，以控制授予票证的 张票证（`TGT`），授予票证的代理票证（`PGT`），服务票证（`ST`）和代理票证（`PT`）的过期策略。 。

<div class="alert alert-info"><strong>还有更多</strong><p>CAS中还有许多其他类型的工件，它们采用票证抽象的基本形式。 每个协议或功能都可能引入带有其自己的过期策略的新票证类型，您将需要查阅该功能或行为的文档，以了解如何调整和控制其自己的票证类型的过期策略。</p></div>

## 票务授予票务政策

TGT过期策略控制经过身份验证的用户可以向ST授予有效（未过期）TGT的时间跨度，而 身份验证。 尝试使用过期的TGT授予ST的请求将要求用户重新验证 以获得新的（有效）TGT。

### 默认

这是默认选项，它提供了硬超时和滑动窗口。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 每项服务

可以根据每个应用程序有条件地确定授予票证的票证的过期策略。 其票证授予票证到期策略要偏离默认配置的候选服务

```json
{
    “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
    “ serviceId”：“ ^ https：//.*”，
    “ name”：“ Sample”，
    “ id”：10，
    “ ticketGrantingTicketExpirationPolicy”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceTicketGrantingTicketExpirationPolicy”，
      “ maxTimeToLiveInSeconds”：5
    }
}
```

### 超时

应用于TGT的到期策略提供了最近使用的到期策略，类似于Web服务器会话超时。 例如，如果此策略有效期为2小时，则需要每2小时或更短的时间使用一次TGT，否则应将 标记为已过期。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 硬超时

硬超时策略提供了有限的票证生命周期，从创建时间起算。 例如，此策略的4小时时间跨度为 ，表示在下午1点创建的故障单可以使用到下午5点；以后尝试使用它将把它标记为过期 并且将迫使用户重新进行身份验证。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 节流的

节流超时策略通过节流的概念扩展了TimeoutExpirationPolicy，其中每N秒最多 此策略旨在防止拒绝服务状况，在这种情况下，流氓或错误配置的客户端 会通过在短时间内请求大量服务票证来尝试消耗CAS服务器资源。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 绝不

永不过期策略允许票证无限期存在。 

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用此策略会对整体
安全策略产生重大影响，只有在合格的安全团队进行全面审查后才能启用此策略。 由文件系统存储支持的票证注册表的
服务器资源使用率也有影响。 由于对于生效此策略的那些注册表，永远不能将票证的磁盘存储回收为
，因此强烈建议不要
</p></div>

## 服务票政策

ST到期策略控制经过身份验证的用户可能尝试验证ST的时间跨度。

### 默认

这是适用于服务票证的默认策略，在该票证中，票证在固定使用次数后或最多闲置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-tickets-behavior)。

### 每项服务

服务票证的到期策略可以根据每个应用程序有条件地确定。 其服务票证过期策略要偏离默认配置的候选服务

```json
{
    “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
    “ serviceId”：“ ^ https：//.*”，
    “ name”：“ Sample”，
    “ id”：10，
    “serviceTicketExpirationPolicy”：{
      “@class”： “org.apereo.cas.services.DefaultRegisteredServiceServiceTicketExpirationPolicy”，
      “numberOfUses”：1，
      “传输TimeToLive”： “10”
    }
}
```

## 代理票务政策

PT过期策略控制经过身份验证的用户可能尝试验证PT的时间跨度。

### 默认

这是适用于代理票证的默认策略，在该票证中，票证在固定使用次数后或在最多不活动的

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#proxy-tickets-behavior)。

### 每项服务

可以根据每个应用程序有条件地决定代理凭单的到期策略。 其代理票证过期策略要偏离默认配置的候选服务

```json
{
    “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
    “ serviceId”：“ ^ https：//.*”，
    “ name”：“ Sample”，
    “ id”：10，
    “proxyTicketExpirationPolicy”：{
     “@class”： “org.apereo.cas.services.DefaultRegisteredServiceProxyTicketExpirationPolicy”，
     “numberOfUses”：1，
     “传输TimeToLive”： “30”
    } 
}
```

## 代理授予票证政策

PGT过期策略控制CAS可以向PT授予有效（未过期）PGT的时间跨度。

### 默认

默认情况下，分配给授权代理凭单的到期策略由分配给凭单授予凭单的同一策略控制。

### 每项服务

可以根据每个应用程序有条件地确定代理授予票证的到期策略。 代理授予票证过期策略要偏离默认配置的候选服务

```json
{
    “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
    “ serviceId”：“ ^ https：//.*”，
    “ name”：“ Sample”，
    “ id”：10，
    “ proxyGrantingTicketExpirationPolicy”：{
     “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy”，
     “ maxTimeToLiveInSeconds”：30
    } 
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#proxy-granting-tickets-behavior)。
