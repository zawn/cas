---
layout: 默认
title: CAS-Infinispan票务注册表
category: 售票处
---

# Infinispan票务注册表

通过在WAR叠加中包含以下依赖项来启用Infinispan集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持infinispan票务注册表</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#infinispan-ticket-registry)。

[Infinispan](http://infinispan.org/) 是具有可选模式的分布式内存键/值数据存储。 它既可以用作嵌入式Java库，也可以用作通过各种协议远程访问的与语言无关的服务。 它提供高级功能，例如事务，事件，查询和分布式处理。

缓存实例可以与

- JCache（JSR-107）
- 休眠二级缓存
- WildFly模块
- 由Infinispan支持的Apache Lucene目录
- 休眠搜索的目录提供程序
- Spring Cache 3.x和4.x
- CDI
- OSGi
- [阿帕奇火花](https://github.com/infinispan/infinispan-spark)
- [阿帕奇Hadoop](https://github.com/infinispan/infinispan-hadoop)

有多种缓存存储可供选择，其中一些是：

- JPA / JDBC存储
- 单个文件 & 软索引
- 休息
- 卡桑德拉
- 雷迪斯
- HBase的
- MongoDB的

参见 [完整的实现列表](https://infinispan.org/cache-store-implementations)。

## 分布式缓存

示例 `infinispan.xml` 配置文件：

```xml
<吗？xml版本=“ 1.0”编码=“ UTF-8”？>
<infinispan xsi:schemaLocation="urn:infinispan:config:8.2 http://www.infinispan.org/schemas/infinispan-config-8.2.xsd"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:infinispan:config:8.2">

   <cache-container default-cache="cas">
       <jmx duplicate-domains="true" />
       <local-cache name="cas" />
   </cache-container>
</infinispan>

```

请参阅 [Infinispan](http://infinispan.org/) 文档，以了解有关缓存配置的更多信息，以及如何 管理各种故障单类型的驱逐策略的更多信息。
