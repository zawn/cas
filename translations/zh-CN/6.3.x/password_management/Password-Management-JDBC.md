---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理-JDBC

帐户密码和安全性问题可以存储在数据库中。

通过在WAR叠加中包含以下依赖项来启用JDBC支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-pm-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jdbc-password-management)。

用户帐户的预期数据库架构为：

```sql
创建表pm_table_accounts（id int，userid varchar（255），密码varchar（255），电子邮件varchar（255），电话varchar（255））;
```

帐户安全性问题的预期数据库架构为：

```sql
创建表pm_table_questions（id int，userid varchar（255），问题varchar（255），答案varchar（255））;
```

## 密码记录

此功能还可以启用密码历史记录跟踪和存储。 通过JDBC管理密码将使CAS更改密码历史记录以使用相同的JDBC配置。
