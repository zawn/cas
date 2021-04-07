---
layout: 违约
title: CAS - 网络流自定义
category: 网络流管理
---

# 网流错误自定义

默认情况下，CAS 被配置为在身份验证期间识别和处理 Web 流的多个例外情况。 每个例外都有 `消息中的特定消息捆绑映射。 属性` 以便向登录表单上的最终用户呈现特定消息。 任何未识别或未映射的例外会导致具有通用 `无效凭据的 <code>未知` 映射。</code> 信息。

要映射 Webflow 中的自定义异常，则需要在 CAS 设置中映射异常，然后定义 `消息中的相关错误`。

```properties
认证失败。我验证例外=认证失败，但它做到了我的方式！
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-exceptions)。
