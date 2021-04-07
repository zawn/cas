---
layout: 违约
title: CAS - 密码管理
category: 密码管理
---

# 密码管理 - 京建联

帐户密码和安全问题可能存储在数据库中。

JDBC 支持通过在战争覆盖中包括以下依赖项来实现：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - pm - jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jdbc-password-management)。

用户帐户的预期数据库模式是：

```sql
创建表pm_table_accounts（id int、使用性varchar（255）、密码瓦尔查尔（255）、电子邮件瓦尔查尔（255）、电话瓦查尔（255））：
```

帐户安全问题的预期数据库模式是：

```sql
创建表pm_table_questions（id int、使用性瓦尔查尔（255）、问题瓦尔查尔（255）、答案瓦尔查尔（255））：
```

## 密码历史记录

此功能还启用密码历史记录跟踪和存储。 通过 JDBC 管理密码将切换 CAS 以使用相同的 JDBC 配置进行密码历史记录。
