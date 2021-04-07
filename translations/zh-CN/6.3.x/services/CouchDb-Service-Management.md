---
layout: 违约
title: 中科院 - 沙发数据库服务注册处
category: 服务业
---

# 库奇德布服务注册处
沙发数据库集成通过在 WAR 叠加中包括以下依赖性而实现：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-沙发-服务-注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

[沙发数据库](http://couchdb.apache.org/) 是基于 [Erlang/OTP](http://www.erlang.org) 及其 mnesia 数据库的高度可用的开源 NoSQL 数据库服务器。 此注册表的目的是利用 CouchDB 服务器的功能，在多个数据中心为 CAS 提供高可用性。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-service-registry)。

唯一真正强制性的设置是网址。 但是，在生产中不应在管理员方模式中使用 CouchDB，因此也需要用户名和密码。

## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a>。</p> 




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
