---
layout: 默认
title: CAS-Couchbase票务注册表
category: 售票处
---

# Couchbase票务注册表

通过在WAR叠加中包含以下依赖项来启用Couchbase集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-couchbase-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```


[Couchbase](http://www.couchbase.com) [Erlang / OTP](http://www.erlang.org) 及其mnesia数据库的高度可用的开源NoSQL数据库服务器。 该 注册表的目的是利用Couchbase服务器的功能为CAS提供高可用性。

<div class="alert alert-info"><strong>兼容性</strong><p>目前，CAS中对Couchbase的支持仅限于Couchbase v4。</p></div>

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-ticket-registry)。

Couchbase集成当前假定票证注册表在其自己的存储桶 您可以根据常规Couchbase配置为存储桶设置密码，还可以选择配置

唯一真正必需的设置是节点列表。 其他设置是可选的，但这是为了将数据存储在存储桶 而设计的，因此实际上也必须设置存储桶属性。

## 过期政策

您将需要记住，Couchbase中的每个文档都包含 `有效期` 属性。 到期生存时间值为 `0` 表示根本没有设置任何到期。 到期时间从文档成功存储在服务器上开始， 在CAS服务器上创建文档时开始。 实际上，增量应该非常小到可以忽略不计。 `30` 天（以秒为单位）的到期时间都被认为是绝对的（如Unix时间戳记） ，任何小于秒的相对时间都被视为相对的（以秒为单位）。

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
