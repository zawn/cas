---
layout: 默认
title: CAS-通知
category: 通知事项
---

# 通知事项

CAS提供了通过特定于平台的通知向用户和帐户发出有关选择操作的通知的功能。 示例操作包括 危险身份验证尝试通知或密码重置链接/令牌或用于多重身份验证的一次性令牌。 下面列出了提供程序 和CAS支持的平台。 请注意，某些提供商可能需要活跃/专业订阅。

## Google Firebase云消息传递

使用以下模块，可以通过相关模块启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-notifications-fcm</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-cloud-firebase-messaging)。
