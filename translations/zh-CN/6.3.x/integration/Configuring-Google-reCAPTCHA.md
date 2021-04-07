---
layout: 默认
title: CAS-Google reCAPTCHA
category: 一体化
---

# Google reCAPTCHA

验证码是 [谷歌服务](https://developers.google.com/recaptcha) 保护免受垃圾邮件和滥用您的CAS部署。 它使用先进的风险分析技术来区分人和机器人。 CAS支持reCAPTCHA API `v2` 和 `v3`。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-captcha</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-recaptcha-integration)。

## IE浏览器

切记禁用Internet Explorer的“兼容性视图”模式。 打开该模式时，reCAPTCHA可能无法正确呈现。
