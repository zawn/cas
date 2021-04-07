---
layout: 默认
title: CAS-Apache Cassandra身份验证
category: 验证
---

# Apache Cassandra身份验证

[Apache Cassandra](http://cassandra.apache.org/)验证和认证凭据。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-cassandra-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cassandra-authentication)。
