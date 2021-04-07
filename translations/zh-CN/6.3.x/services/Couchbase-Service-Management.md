---
layout: 默认
title: CAS-Couchbase服务注册表
category: 服务
---

# Couchbase服务注册表
通过在WAR叠加中包含以下依赖项来启用Couchbase集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-couchbase-service-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

[Couchbase](http://www.couchbase.com) [Erlang / OTP](http://www.erlang.org) 及其mnesia数据库的高度可用的开源NoSQL数据库服务器。 该注册表的目的是利用Couchbase 服务器的功能为CAS提供高可用性。

<div class="alert alert-info"><strong>兼容性</strong><p>目前，CAS中对Couchbase的支持仅限于Couchbase v4。</p></div>

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-service-registry)。

Couchbase集成当前假定服务注册表在其自己的存储桶 根据正常的Couchbase配置，可以选择为存储桶设置密码，可以选择设置

唯一真正必需的设置是节点列表。 其他设置是可选的，但这是为了将数据存储在存储桶 而设计的，因此实际上也必须设置存储桶属性。

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)


## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下 级：

```xml
...
<Logger name="com.couchbase" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
