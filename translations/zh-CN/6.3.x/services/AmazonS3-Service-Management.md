---
layout: 默认
title: CAS-Amazon S3服务注册表
category: 服务
---

# Amazon S3服务注册表

将注册的服务数据存储在 [Amazon S3](https://aws.amazon.com/s3/) 存储桶中。 每个服务定义都在其自己的单独存储区 中进行管理，并且服务定义的主体作为JSON Blob（类似于 [JSON服务注册表](JSON-Service-Management.html)。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-aws-s3-service-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-s3-service-registry)。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)
