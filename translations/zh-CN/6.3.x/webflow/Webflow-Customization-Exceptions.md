---
layout: 默认
title: CAS-Web流定制
category: Webflow管理
---

# Webflow错误定制

默认情况下，CAS配置为在身份验证期间识别和处理Web流的许多异常。 `messages.properties` 都有特定的消息束映射。因此，可以在登录表单上向最终用户显示特定的消息。 任何无法识别或未映射的异常都将导致 `UNKNOWN` 映射与通用 `无效凭据。` 消息。

要在Webflow中映射自定义异常，需要在CAS设置中映射异常，然后在 `messages.properties`定义相关的错误：

```properties
authenticationFailure.MyAuthenticationException =身份验证失败，但是我做到了！
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-exceptions)。
