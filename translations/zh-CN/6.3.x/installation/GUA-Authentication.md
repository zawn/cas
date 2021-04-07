---
layout: 违约
title: 中科院 - 瓜认证
category: 认证
---

# 图形用户身份验证

图形用户身份验证（有时也称为"登录图像"）是登录验证的一种形式（即第二个因子），其中网站向用户展示用户在创建帐户时以前选择的图像。 这是一个与用户名相关的"帐户机密"，不应轻易被试图冒充合法网站的网络钓鱼活动转载。

在实践中，CAS 仅向用户提示其用户名，并使用显示用户预选图像的页面以及密码字段来响应以完成其身份验证。 反过来，如果 CAS 未能呈现正确的图像，则用户将接受培训，拒绝将其余登录凭据提交到假定合法的站点。

## 概述

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 瓜</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#gua-authentication)。

### 资源

此选项主要用于演示和测试目的，允许 CAS 将全球和静态图像资源 作为用户标识符加载到登录流中。

### 阿尔达普

CAS 也可以允许从 LDAP 为用户定位二进制图像属性。 然后将二进制属性值加载 作为用户标识符加载到登录流中。
