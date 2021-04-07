---
layout: 默认
title: CAS-JDBC驱动程序
category: 配置
---

# JDBC驱动程序

尽管在大多数情况下这是不必要的，并由CAS自动处理，但是 可能还需要包括以下模块来说明各种数据库驱动程序：

```xml
<dependency>
   <groupId>org.apereo.cas</groupId>
   <artifactId>cas-server-support-jdbc-drivers</artifactId>
   <version>${cas.version}</version>
</dependency>
```

## 数据库支持

驱动程序的自动支持包括以下数据库。 所有其他驱动程序都需要手动添加到构建配置中。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties-Common.html#hibernate--jdbc)。

### H2

可用的驱动程序是：

1. `org.h2.Driver`

| 方言                                |
| --------------------------------- |
| `org.hibernate.dialect.H2Dialect` |

### 数据库

可用的驱动程序是：

1. `org.hsqldb.jdbcDriver`

| 方言                                  |
| ----------------------------------- |
| `org.hibernate.dialect.HSQLDialect` |

### 甲骨文

可用的驱动程序是：

1. `oracle.jdbc.driver.OracleDriver`

| 方言                                       |
| ---------------------------------------- |
| `org.hibernate.dialect.Oracle8iDialect`  |
| `org.hibernate.dialect.Oracle9iDialect`  |
| `org.hibernate.dialect.Oracle10gDialect` |
| `org.hibernate.dialect.Oracle12cDialect` |

### MySQL数据库

可用的驱动程序是：

1. `com.mysql.jdbc.Driver`
2. `com.mysql.cj.jdbc.Driver`

| 方言                                           |
| -------------------------------------------- |
| `org.hibernate.dialect.MySQLDialect`         |
| `org.hibernate.dialect.MySQL5Dialect`        |
| `org.hibernate.dialect.MySQLInnoDBDialect`   |
| `org.hibernate.dialect.MySQLMyISAMDialect`   |
| `org.hibernate.dialect.MySQL5InnoDBDialect`  |
| `org.hibernate.dialect.MySQL57InnoDBDialect` |
| `org.hibernate.dialect.MySQL8Dialect`        |

### PostgreSQL的

可用的驱动程序是：

1. `org.postgresql.Driver`

| 方言                                          |
| ------------------------------------------- |
| `org.hibernate.dialect.PostgreSQL81Dialect` |
| `org.hibernate.dialect.PostgreSQL82Dialect` |
| `org.hibernate.dialect.PostgreSQL9Dialect`  |
| `org.hibernate.dialect.PostgreSQL91Dialect` |
| `org.hibernate.dialect.PostgreSQL92Dialect` |
| `org.hibernate.dialect.PostgreSQL93Dialect` |
| `org.hibernate.dialect.PostgreSQL94Dialect` |
| `org.hibernate.dialect.PostgreSQL95Dialect` |
| `org.hibernate.dialect.PostgresPlusDialect` |

### 玛丽亚数据库

可用的驱动程序是：

1. `org.mariadb.jdbc.Driver`

| 方言                                        |
| ----------------------------------------- |
| `org.hibernate.dialect.MariaDBDialect`    |
| `org.hibernate.dialect.MariaDB53Dialect`  |
| `org.hibernate.dialect.MariaDB10Dialect`  |
| `org.hibernate.dialect.MariaDB102Dialect` |
| `org.hibernate.dialect.MariaDB103Dialect` |

### Microsoft SQL服务器

可用的驱动程序是：

1. `net.sourceforge.jtds.jdbc.Driver`
2. `com.microsoft.sqlserver.jdbc.SQLServerDriver`

| 方言                                           |
| -------------------------------------------- |
| `org.hibernate.dialect.SQLServerDialect`     |
| `org.hibernate.dialect.SQLServer2005Dialect` |
| `org.hibernate.dialect.SQLServer2008Dialect` |
| `org.hibernate.dialect.SQLServer2012Dialect` |  
