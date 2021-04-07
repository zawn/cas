---
layout: 默认
title: CAS-配置服务代理策略
category: 服务
---

# 配置代理身份验证策略

可以为注册表中的每个注册应用程序分配一个代理策略，以确定是否允许该服务进行代理身份验证。 这意味着除非将代理策略配置为允许PGT，否则不会将PGT发布给服务。 另外，该策略还可以定义实际上允许哪些端点URL接收PGT。

请注意，默认情况下，所有应用程序都不允许使用代理身份验证。

<div class="alert alert-warning"><strong>使用警告！</strong><p>在允许应用程序执行代理身份验证之前，请考虑 <strong>非常谨慎</strong> 盲目授权应用程序接收授权代理票证可能会导致安全漏洞和攻击。 确保您确实需要启用这些功能，并且了解其原因。 避免在何时何地可以。</p></div>

## 拒绝

禁止服务的代理身份验证。 这是默认策略，无需明确配置。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ proxyPolicy”：{
    “ @class”：“ org.apereo.cas.services.RefuseRegisteredServiceProxyPolicy”
  }
}
```

## 正则表达式

仅允许代理到与指定正则表达式模式匹配的PGT url的代理策略。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ proxyPolicy”：{
    “ @class”：“ org.apereo.cas.services.RegexMatchingRegisteredServiceProxyPolicy”，
    “ pattern”：“ ^ https？：//.*”
  }
}
```
