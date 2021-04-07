---
layout: 默认
title: CAS-简单的多因素身份验证
category: 多因素身份验证
---

# 简单的多因素身份验证

允许CAS自己充当多因素身份验证提供者，发行令牌并通过预定义的通讯 通道（例如，电子邮件或文本消息）将其发送给最终用户。 [票证注册表](../ticketing/Configuring-Ticketing-Components.html) 跟踪由CAS发行的令牌，并为其分配通过CAS设置控制的可配置到期策略。

## 配置

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持简单mfa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#simple-multifactor-authentication)。

## 登记

预计注册是作为带外过程进行的。 最终，CAS希望 ，以确定电子邮件和/或短信的通信渠道。 预计采用者将 用户记录，以指示一个电话号码和/或电子邮件地址，然后可以在其中配置CAS以进行提取，并 属性以共享生成的令牌。

## 沟通策略

可以通过文本消息和/或电子邮件向用户通知CAS颁发的令牌。 经过身份验证的CAS主体应携带足够的属性（ ，以使CAS能够正确发送文本消息和/或电子邮件给最终用户。 令牌也可以通过Google Firebase等平台通过通知策略

要了解有关可用选项的更多信息，请 [请参阅本指南](../notifications/SMS-Messaging-Configuration.html) 或 [本指南](../notifications/Sending-Email-Configuration.html)或 [本指南](../notifications/Notifications-Configuration.html)。
