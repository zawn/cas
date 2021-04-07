---
layout: 违约
title: CAS - 库奇德布票务注册处
category: 票务
---

# 库奇德布票务登记处

沙发数据库集成通过在 WAR 叠加中包括以下依赖性而实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 沙发 - 票登记</artifactId>
     <version>${cas.version}</version>
</dependency>
```


[沙发数据库](http://couchdb.apache.org) 是基于 [Erlang/OTP](http://www.erlang.org) 及其 mnesia 数据库的高度可用的开源 NoSQL 数据库服务器。 此 注册表的目的是利用 CouchDB 服务器的多主、多数据中心功能，为 CAS 提供高可用性。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-ticket-registry)。


唯一真正强制性的设置是网址。 但是，在生产中不应在管理员方模式中使用 CouchDB，因此也需要用户名和密码。

## 警告

如果 CouchDB 未完全删除 记录，则多个数据中心的多主复制将进行权衡。 根据部署规模、使用情况和可用存储，数据库可能需要通过正常的 CouchDB 技术定期进行清洁 。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下 级别：

```xml
...
<Logger name="org.apache.couchdb" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
