---
layout: 默认
title: CAS-发送电子邮件
category: 通知事项
---

# 发送邮件

CAS提供了通过电子邮件将选择的操作通知用户的功能。 示例操作包括 或密码重置链接/令牌等。 配置电子邮件提供商（即 Amazon Simple Email Service） 是定义SMTP设置的问题。 在未定义设置的情况下，需要电子邮件功能的每个特定功能都应能够

相关模块使用以下模块自动启用/包括对电子邮件通知的默认支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-core-notifications</artifactId>
     <version>${cas.version}</version>
</dependency>
```

除非需要在编译时访问组件和API，否则您无需在WAR Overlay配置中明确包含此模块。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#email-submissions)。
