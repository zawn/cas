---
layout: 默认
title: CAS-数据库身份验证
category: 验证
---

# 数据库认证

通过在WAR覆盖中包括以下依赖项来启用数据库身份验证：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序，请参阅本指南</a>

。</p> 



## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-authentication)。



## 密码策略执行

某些数据库身份验证方案对通过CAS设置中定义的列名检测锁定/禁用/等帐户 要了解如何实施密码策略，请 [本指南](Password-Policy-Enforcement.html)。
