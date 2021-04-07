---
layout: 默认
title: CAS-CouchDB票务注册表
category: 售票处
---

# CouchDB票务注册表

通过在WAR覆盖中包含以下依赖项来启用CouchDB集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-couchdb-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```


[CouchDB](http://couchdb.apache.org) [Erlang / OTP](http://www.erlang.org) 及其mnesia数据库的高度可用的开源NoSQL数据库服务器。 注册表的目的是利用CouchDB服务器的多主控，多数据中心功能为CAS提供高可用性。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-ticket-registry)。


唯一真正必需的设置是URL。 但是，不应在生产中以管理方模式使用CouchDB，因此也需要用户名和密码。

## 警告

条记录，则需要在多个数据中心之间进行多主机复制。 根据部署规模，使用情况和可用存储，数据库可能需要通过常规CouchDB技术

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
