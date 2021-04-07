---
layout: 默认
title: CAS-配置服务HTTP安全标头
category: 服务
---

# 配置服务HTTP安全标头

CAS能够根据每个服务控制是否应将某些与安全性相关的HTTP标头注入响应中。 [CAS安全筛选器](../planning/Security-Guide.html#cas-security-filter)一部分来全局启用和定义头，但是此处描述的策略允许针对某些应用程序和服务请求禁用/启用这些头的注入并覆盖全局默认值。

以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“示例服务”，
  “ id”：100 ，
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ httpHeaderEnableXContentOptions”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”： [“ java.util.HashSet”，[“ true”]]
    }
  }
}
```

支持的服务属性形式的HTTP标头包括：

| 标头                                        | 描述                                   |
| ----------------------------------------- | ------------------------------------ |
| `httpHeaderEnableCacheControl`            | 在此服务的响应中 `Cache-Control`             |
| `httpHeaderEnableXContentOptions`         | 在此服务的响应中 `X-Content-Type-Options`    |
| `httpHeaderEnableStrictTransportSecurity` | 在此服务的响应中 `Strict-Transport-Security` |
| `httpHeaderEnableXFrameOptions`           | 在此服务的响应中 `X-Frame-Options`           |
| `httpHeaderEnableContentSecurityPolicy`   | 在此服务的响应中 `Content-Security-Policy`   |
| `httpHeaderEnableXSSProtection`           | 在此服务的响应中 `X-XSS-Protection`          |
| `httpHeaderXFrameOptions`                 | 覆盖此服务的响应 `X-Frame-Options`           |

标头值是从CAS属性中选取的。 有关设置，请参见本指南的 [](../configuration/Configuration-Properties.html#http-web-requests)

