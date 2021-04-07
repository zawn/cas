---
layout: 违约
title: CAS - 发送电子邮件
category: 通知
---

# 发送电子邮件

CAS 提供通过电子邮件通知用户选择操作的能力。 示例操作包括危险身份验证尝试的通知 或密码重置链接/令牌等。 配置电子邮件提供商（即 亚马逊简单电子邮件服务 ） 是定义 SMTP 设置的问题。 每个需要电子邮件功能的特定功能都应该能够 优雅地继续，以防未定义设置。

相关模块使用以下模块自动启用/包括对电子邮件通知的默认支持：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-核心通知</artifactId>
     <version>${cas.version}</version>
</dependency>
```

您不需要在 WAR 叠加配置中明确包含此模块，除非需要在编译时间访问组件和 ABI。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#email-submissions)。
