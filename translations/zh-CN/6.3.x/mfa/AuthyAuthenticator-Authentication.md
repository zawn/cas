---
layout: 违约
title: CAS - 奥蒂认证
category: 多因素认证
---

# 奥蒂认证

中科院为奥西 [托普API](http://docs.authy.com/totp.html)提供支持。 这是通过奥蒂的 REST API 完成 ，该 API 可以完成所有繁重的工作。

首先参观 [奥蒂文件](https://www.authy.com/developers/)。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 -</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authy)。

## 注册

默认情况下，用户根据 CAS 检索到的手机和电子邮件属性以 authy 注册。
