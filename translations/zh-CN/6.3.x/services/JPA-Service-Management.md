---
layout: 默认
title: CAS-JPA服务注册中心
category: 服务
---

# JPA服务注册中心
将注册的服务数据存储在数据库中。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-jpa-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序，请参阅本指南</a>

。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-service-registry)。</p> 



## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)