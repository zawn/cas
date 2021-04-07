---
layout: 默认
title: CAS-配置服务SSO策略
category: 服务
---

# 配置服务SSO策略

基于每个服务设计的单点登录参与策略应优先于全局SSO行为。 此类策略通常适用于 以参与单点登录会话，创建SSO cookie等。

## 单点登录Cookie

CAS采用者可能希望允许以下行为： 既不创建CAS SSO会话，又不支持其创建的SSO会话用于随后向SSO参与应用程序 。 可以在每个服务的基础上定义此行为。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ...”，
  “ name”：“ ...”，
  “ id”：
  “ singleSignOnParticipationPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceSingleSignOnParticipationPolicy”，
    “ createCookieOnRenewedAuthentication”：“ TRUE”
  }
}
```

为可接受的值 `createCookieOnRenewedAuthentication` 是 `TRUE`， `FALSE` 或 `UNDEFINED`。

## 参与政策

可以将其他策略分配给每个服务定义，以控制应用程序在现有的单点登录会话中的参与。 如果条件成立，CAS将兑现现有的SSO会话，并且不会向用户挑战凭据。 如果条件失败，则 用户提供凭据。 可以将这些策略链接在一起并按顺序执行。

### 认证日期

`5` 秒，请遵守现有的单点登录会话（如果有）。 否则，向 以获取凭据，并忽略现有会话。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ...”，
  “ name”：“ ...”，
  “ id”：
  “ singleSignOnParticipationPolicy“：
    {
      ” @class“：” org.apereo.cas.services.ChainingRegisteredServiceSingleSignOnParticipationPolicy“，
      ” policies“：[” java.util.ArrayList“，
        [
          {
            ” @class“：”组织。 apereo.cas.services.AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy”，
            “TIMEUNIT”： “秒”
            “TIMEVALUE”：5，
            “顺序”：0
          }
        ]
      ]
    }
}
```

### 上次使用时间

`5` 秒，则请遵守现有的单点登录会话（如果有）。 否则，请向 用户询问凭据，并忽略现有会话。

这里的策略计算通常包括评估链接到SSO会话的授予票证的票证的最后使用时间，以检查票证是否继续有效地发行服务票证等为

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ...”，
  “ name”：“ ...”，
  “ id”：
  “ singleSignOnParticipationPolicy“：
    {
      ” @class“：” org.apereo.cas.services.ChainingRegisteredServiceSingleSignOnParticipationPolicy“，
      ” policies“：[” java.util.ArrayList“，
        [
          {
            ” @class“：”组织。 apereo.cas.services.LastUsedTimeRegisteredServiceSingleSignOnParticipationPolicy”，
            “TIMEUNIT”： “秒”
            “TIMEVALUE”：5，
            “顺序”：0
          }
        ]
      ]
    }
}
```

## 风俗

可以使用以下语法使用在CAS中注册的自定义策略来自定义和控制单个登录会话的参与：

```java
@Bean
public SingleSignOnParticipationStrategyConfigurer customSsoConfigurer（）{
    返回链> chain.addStrategy（...）;
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

