---
layout: 违约
title: CAS - 配置票证到期策略组件
category: 票务
---

# 票证到期政策

中科院支持一个可插入和可扩展的政策框架，以控制票务赠与 票（`TGT`）、代理赠票（`PGT`）、服务票（`ST`）和代理票（`PT`）的到期政策。

<div class="alert alert-info"><strong>还有更多</strong><p>CAS 中还有许多其他类型的文物，它们以票证抽象为基本形式。 每个协议或功能都可能引入一种带有其到期策略的新票证类型，您需要咨询该功能或行为的文件，以了解如何调整和控制其自身票务类型的过期策略。</p></div>

## 出票政策

TGT 到期策略规定了身份验证用户无需重新验证 即可向 ST 授予有效（未过期）TGT 的时间跨度。 尝试授予具有过期 TGT 的 ST 将要求用户重新验证 以获得新的（有效的）TGT。

### 违约

这是默认选项，它提供了一个困难的时间以及一个滑动窗口。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 每种服务

门票发放的到期政策可以按申请有条件地确定。 应聘服务 其门票授予票证到期政策偏离默认配置时，必须这样设计：

```json
•
    "@class"："org.apereo.cas.服务.注册服务"，
    "服务id"："^https://.*"，
    "名称"："示例"，
    "id"： 10，
    "门票优惠门票探索政策"： [
      "@class"： "org. apereo. cas. 服务. 默认注册服务票证驱逐政策"，
      "最大时间第二次"： 5
    =

```

### 超时

适用于 TGT 的到期策略提供了最近使用的过期策略，类似于 Web 服务器会话超时。 例如，此策略生效后需要每 2 小时或更短的时间段使用 TGT，否则 将其标记为过期。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 硬超时

硬超时策略提供了从创建时间衡量的有限票证寿命。 例如，此保单 的 4 小时时间跨度意味着下午 1 点创建的机票可以使用到下午 5 点：随后的尝试使用它将标记它过期 ，用户将被迫重新验证。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 扼杀

节流超时政策扩展了超时体验政策，其概念是限制每 N 秒最多 使用票证的位置。 此策略旨在通过在短时间内请求大量服务票证来阻止恶意或配置错误的客户端 尝试消耗 CAS 服务器资源时拒绝服务条件。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#tgt-expiration-policy)。

### 从不

永不到期的政策允许门票无限期存在。 

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用此策略对总体
安全政策有重大影响，只有在合格的安全团队进行彻底审查后才能启用。 
由文件系统存储支持的票务注册的服务器资源使用也有影响。 由于对于具有此策略的注册机构，无法回收票证磁盘存储
，
强烈劝阻使用此策略与这些票务登记实施。</p></div>

## 服务票务政策

ST 到期策略规定经过验证的用户可以尝试验证 ST 的时间跨度。

### 违约

这是适用于在固定次数的使用后或在最长 期间后机票过期的服务机票的默认策略。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-tickets-behavior)。

### 每种服务

服务票的到期政策可以按申请有条件地确定。 服务票到期策略偏离默认配置的候选服务 必须设计成：

```json
•
    "@class"："org.apereo.cas.服务.注册服务"，
    "服务id"："^https://.*"，
    "名称"："样本"，
    "id"：10，
    "服务车牌体验政策"： [
      "@class"： "org. apereo. cas. 服务. 默认注册服务服务开发政策"，
      "服务数量"： 1，
      "时间生活"： "10"
    =
}
```

## 代理票务政策

PT 到期策略管理经过验证的用户可能尝试验证 PT 的时间跨度。

### 违约

这是适用于代理票证的默认策略，即在固定次数的使用后或在最长 不活动期过去之后，机票过期。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#proxy-tickets-behavior)。

### 每种服务

代理机票的到期政策可以按每份申请有条件地决定。 代理票证到期策略偏离默认配置的候选服务 必须这样设计：

```json
•
    "@class"："org.apereo.cas.服务.注册服务"，
    "服务ID"："^https://.*"，
    "名称"："示例"，
    "id"：10，
    "代理车牌探索政策"： [
     "@class"： "org. apereo. cas. 服务. 默认注册服务价格开发政策"，
     "使用次数"： 1，
     "时间生活"： "30"
    = 
}
```

## 代理授予票务政策

PGT 到期政策规定了 CAS 向 PT 授予有效（未过期）PGT 的时间跨度。

### 违约

默认情况下，分配给代理授予票证的到期政策由分配给赠票票的相同保单控制。

### 每种服务

代理赠票的到期政策可以按申请有条件地确定。 应聘服务 其代理授予票证到期政策是偏离默认配置必须设计成这样：

```json
•
    "@class"："org.apereo.cas.服务.注册服务"，
    "服务id"："^https://.*"，
    "名称"："示例"，
    "id"：10、
    "代理代客车证开发政策"：[
     "@class"："org.apereo.cas.服务.默认注册服务""代理代客车证开发政策"，
     "最高时间到第二秒"：30
    = 

```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#proxy-granting-tickets-behavior)。
