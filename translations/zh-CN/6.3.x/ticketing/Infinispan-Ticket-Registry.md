---
layout: 违约
title: CAS - 英菲尼斯潘票务注册处
category: 票务
---

# 英菲尼斯潘票务登记处

英菲尼斯潘集成通过在战争覆盖中包括以下依赖性而实现：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-英菲尼斯潘-票证注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#infinispan-ticket-registry)。

[英菲尼斯潘](http://infinispan.org/) 是一个分布式内存密钥/值数据存储器，具有可选的模式。 它既可用作嵌入式 Java 库，也可用作通过各种协议远程访问的语言独立服务。 它提供高级功能，如交易、事件、查询和分布式处理。

缓存实例可以与

- J缓存 （JSR-107）
- 冬眠二级缓存
- 野飞模块
- 阿帕奇 · 卢塞内目录由英菲尼斯潘支持
- 冬眠搜索目录提供商
- 弹簧缓存 3.x 和 4.x
- CDI
- 奥斯吉
- [阿帕奇火花](https://github.com/infinispan/infinispan-spark)
- [阿帕奇·哈杜普](https://github.com/infinispan/infinispan-hadoop)

有各种各样的缓存商店可供选择，其中一些是：

- JPA/京百货商店
- 单个文件 & 软索引
- 休息
- 卡珊德拉
- 雷迪斯
- 赫巴兹
- 蒙古德布

请参阅</a>实施

完整列表。</p> 



## 分布式缓存

`无限.xml` 配置文件的示例：



```xml
<？xml 版本="1.0"编码="UTF-8"？>
<infinispan xsi:schemaLocation="urn:infinispan:config:8.2 http://www.infinispan.org/schemas/infinispan-config-8.2.xsd"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="urn:infinispan:config:8.2">

   <cache-container default-cache="cas">
       <jmx duplicate-domains="true" />
       <local-cache name="cas" />
   </cache-container>
</infinispan>

```


请参阅 infinispan</a> 文档 ，以了解有关缓存配置以及如何 管理各种票务类型的驱逐策略。</p>
