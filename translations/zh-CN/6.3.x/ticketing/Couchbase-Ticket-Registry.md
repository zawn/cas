---
layout: 违约
title: CAS - 沙发基地票务注册处
category: 票务
---

# 沙发基地票务登记处

沙发基地集成通过在战争覆盖中包括以下依赖性而实现：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 沙发基地 - 票务注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```


[沙发基地](http://www.couchbase.com) 是基于 [Erlang/OTP](http://www.erlang.org) 及其 mnesia 数据库的高度可用的开源 NoSQL 数据库服务器。 这个 注册表的目的是利用沙发基地服务器的能力，为中科院提供高可用性。

<div class="alert alert-info"><strong>兼容性</strong><p>目前CAS的沙发基地支持仅限于沙发基地v4。</p></div>

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-ticket-registry)。

沙发库集成目前假定票务登记簿 存储在自己的存储桶中。 您可以根据正常的 Couchbase 配置可选地为存储桶设置密码，并可选地配置 冗余和复制。

唯一真正强制性的设置是节点列表。 其他设置是可选的，但设计用于将数据存储在存储桶中 因此在现实中还必须设置存储桶属性。

## 到期政策

您需要记住，Couchbase 中的每份文档都包含 `到期` 财产。 `0` 的到期活期值意味着根本不设置到期。 到期时间开始于文档成功存储在服务器上时，而不是在 CAS 服务器上创建文档时， 。 在实践中，三角洲应该非常可以忽略不计。 在几秒钟内超过 `30` 天的任何到期时间都被视为绝对值（如 Unix 时间戳中所 任何较小的时间在数秒内被视为相对值。

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
