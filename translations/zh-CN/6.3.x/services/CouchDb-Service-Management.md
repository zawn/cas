---
layout: 默认
title: CAS-CouchDB服务注册表
category: 服务
---

# CouchDB服务注册表
通过在WAR覆盖中包含以下依赖项来启用CouchDB集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-couchdb-service-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

[CouchDB](http://couchdb.apache.org/) [Erlang / OTP](http://www.erlang.org) 及其mnesia数据库的高度可用的开源NoSQL数据库服务器。 该注册表的目的是利用CouchDB 服务器的功能为多个数据中心的CAS提供高可用性。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-service-registry)。

唯一真正必需的设置是URL。 但是，不应在生产中以管理方模式使用CouchDB，因此也需要用户名和密码。

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)


## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下 级：

```xml
...
<Logger name="org.apache.couchdb" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
