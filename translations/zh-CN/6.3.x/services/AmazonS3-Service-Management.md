---
layout: 违约
title: CAS - 亚马逊S3服务注册处
category: 服务业
---

# 亚马逊 S3 服务注册表

将注册服务数据存储在亚马逊 S3</a> 桶

中。 每个服务定义都管理在自己的单独存储桶 中，服务定义的主体管理为 JSON blob，类似于 [JSON 服务注册表](JSON-Service-Management.html)。</p> 

支持通过在覆盖中添加以下模块来实现：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-aws-s3-服务-注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```




## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-s3-service-registry)。



## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：



```xml
...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```




## 自动初始化

在启动和配置允许的情况下，注册表能够自动初始化自己从默认的JSON服务 CAS可用的定义。 有关详细信息，请参阅本指南</a> 。</p>
