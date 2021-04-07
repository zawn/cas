---
layout: 默认
title: CAS-Mongo服务注册中心
category: 服务
---

# Mongo服务注册中心

该注册表使用 [MongoDb](https://www.mongodb.org/) 实例来加载和保留服务定义。 通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-mongo-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

此实现会自动配置大多数内部细节。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-service-registry)。

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)