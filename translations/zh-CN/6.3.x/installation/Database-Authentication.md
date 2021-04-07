---
layout: 违约
title: CAS - 数据库认证
category: 认证
---

# 数据库认证

通过在 WAR 叠加中包括以下依赖项，启用数据库身份验证：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序， [请参阅本指南](JDBC-Drivers.html)。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-authentication)。

## 密码政策执行

某些数量的数据库身份验证计划对于通过 CAS 设置中定义的列名检测锁定/禁用/等帐户 的支持有限。 要了解如何执行密码政策，请 [本指南](Password-Policy-Enforcement.html)。
