---
layout: 违约
title: 卡斯 - 谷歌回顾
category: 集成
---

# 谷歌回顾

reCAPTCHA是一个 [谷歌服务](https://developers.google.com/recaptcha) ，保护您的CAS部署免受垃圾邮件和滥用。 它使用先进的风险分析技术来区分人类和机器人。 CAS 支持回顾 API `v2` 和 `v3`。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持卡普查</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-recaptcha-integration)。

## 互联网浏览器

请记住禁用IE浏览器的"兼容视图"模式。 当该模式打开时，重述可能无法正确呈现。
