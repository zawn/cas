---
layout: 默认
title: CAS-Ignite票务注册表
category: 售票处
---

# 点燃票务注册表
通过在WAR叠加层中包含以下依赖项来启用Ignite集成：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-ignite-ticket-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

该注册表将票证存储在 [Ignite](http://ignite.apache.org/) 实例中。


## 分布式缓存

对于HA体系结构，建议使用分布式缓存，因为它们在票证存储子系统中具有容错能力。


## TLS复制

Ignite支持通过TLS复制由两个或更多节点组成的分布式缓存。 要了解有关使用Ignite进行TLS复制的更多信息，请参见 [](https://apacheignite.readme.io/docs/ssltls)。


## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ignite-ticket-registry)。

## 故障排除

* 您将需要确保允许跨CAS节点的网络通信，并且没有防火墙或其他组件阻止通信。
* 如果利用了CAS实例外部的节点，请确保每个缓存管理器都指定一个与Ignite配置 本身匹配的名称。
* 您可能还需要调整到期策略以允许更长的时间跨度，尤其是对于服务票证，具体取决于网络 流量和CAS节点之间的通信延迟，尤其是在节点尝试加入群集的情况下。
