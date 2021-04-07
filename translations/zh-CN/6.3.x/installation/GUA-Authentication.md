---
layout: 默认
title: CAS-GUA认证
category: 验证
---

# 图形用户认证

图形用户身份验证（有时也称为“登录图像”）是登录验证（即第二因素）的一种形式，其中站点向用户提供用户在创建帐户时预先选择的图像。 这是与用户名相关联的“帐户秘密”，网络钓鱼活动试图假冒合法网站不应该轻易复制该用户名。

实际上，CAS仅提示用户输入用户名，然后显示一个页面，显示应该是用户的预选图像以及密码字段以完成身份验证。 如果CAS无法提供正确的图像，则应训练用户，使其拒绝将其余的登录凭据提交到冒充合法身份的站点。

## 概述

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-gua</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#gua-authentication)。

### 资源资源

主要用于演示和测试目的，此选项允许CAS将全局和静态图像资源 作为用户标识符加载到登录流上。

### LDAP

也可以允许CAS从LDAP中为用户找到二进制图像属性。 然后将二进制属性值 作为用户标识符加载到登录流程中。
