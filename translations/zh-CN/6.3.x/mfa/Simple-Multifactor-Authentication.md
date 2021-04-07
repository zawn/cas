---
layout: 违约
title: CAS - 简单多因素认证
category: 多因素认证
---

# 简单的多因素身份验证

允许 CAS 自行充当多因素身份验证提供商，通过预先定义的通信 电子邮件或短信等渠道向最终用户发行代币并将其发送给最终用户。 CAS 发行的代币使用 [票证注册表](../ticketing/Configuring-Ticketing-Components.html) 进行跟踪，并通过 CAS 设置分配可配置的到期策略。

## 配置

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>的卡-服务器-支持-简单-mfa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#simple-multifactor-authentication)。

## 注册

注册预计将作为带外过程进行。 最终，CAS 希望从配置的属性源获取必要的属性 ，以确定电子邮件和/或短信的通信渠道。 预计采用者将填充 用户记录，其中包含足够的信息，以指示一个电话号码和/或电子邮件地址，然后 CAS 可以配置为提取和 检查这些属性以共享生成的代币。

## 沟通战略

可以通过短信和/或电子邮件通知用户 CAS 发行的代币。 认证的 CAS 委托人预计将携带足够的属性， 通过 CAS 设置进行配置，以便 CAS 正确向最终用户发送短信和/或电子邮件。 代币也可以通过通知策略 通过谷歌火力基础等平台共享。

要了解有关可用选项的更多信息，请 [本指南](../notifications/SMS-Messaging-Configuration.html) 或本指南</a>，或 [本指南](../notifications/Notifications-Configuration.html)。</p>
