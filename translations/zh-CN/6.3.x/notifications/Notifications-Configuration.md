---
layout: 违约
title: CAS - 通知
category: 通知
---

# 通知

CAS 提供通过特定平台的通知通知用户和帐户有关特定操作的功能。 示例操作包括 通知危险的身份验证尝试或密码重置链接/令牌或多因素身份验证的一次性令牌。 下面列出了由 CAS 支持的提供商 和平台。 请注意，某些提供商可能需要主动/专业订阅。

## 谷歌火基云消息

支持通过相关模块启用，使用以下模块：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-通知-fcm</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-cloud-firebase-messaging)。
