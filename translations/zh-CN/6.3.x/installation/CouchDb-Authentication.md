---
layout: 默认
title: CAS-CouchDb身份验证
---

# CouchDb验证

通过pac4j [CouchDb](http://couchdb.apache.org/) 实例验证和认证凭据。 CAS将自动创建pac4j所需的设计文档。 通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-couchdb-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#couchdb-authentication)。
