---
layout: 默认
title: CAS-身份验证
category: 多因素身份验证
---

# 身份验证

CAS为Authy的 [TOTP API](http://docs.authy.com/totp.html)提供支持。 这样做是 通过Authy的REST API做所有繁重。

首先访问 [Authy文档](https://www.authy.com/developers/)。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-authy</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authy)。

## 登记

默认情况下，将根据CAS检索到的电话和电子邮件属性向用户注册authy。
