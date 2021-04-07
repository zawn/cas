---
layout: 违约
title: CAS - 沙发基地服务注册处
category: 服务业
---

# 沙发基地服务注册处
沙发基地集成通过在战争覆盖中包括以下依赖性而实现：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-沙发基地-服务-注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

[沙发基地](http://www.couchbase.com) 是基于 [Erlang/OTP](http://www.erlang.org) 及其 mnesia 数据库的高度可用的开源 NoSQL 数据库服务器。 此注册表的目的是利用沙发基地 服务器的功能，为 CAS 提供高可用性。

<div class="alert alert-info"><strong>兼容性</strong><p>目前CAS的沙发基地支持仅限于沙发基地v4。</p></div>

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-service-registry)。

Couchbase 集成目前假定服务注册 存储在自己的存储桶中。 可选地为存储桶设置密码，根据正常的 Couchbase 配置可选地设置 冗余和复制。

唯一真正强制性的设置是节点列表。 其他设置是可选的，但设计用于将数据存储在存储桶中 因此在现实中还必须设置存储桶属性。

## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a>。</p> 




## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下 级别：



```xml
...
<Logger name="com.couchbase" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
