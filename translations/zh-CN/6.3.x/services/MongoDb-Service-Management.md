---
layout: 违约
title: 中科院 - 蒙古服务注册处
category: 服务业
---

# 蒙古服务注册处

此注册表使用 [MongoDb](https://www.mongodb.org/) 实例加载并保留服务定义。 支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-蒙古-服务-注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

此实现自动配置大部分内部详细信息。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-service-registry)。

## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>