---
layout: 违约
title: CAS - 卡桑德拉服务注册处
category: 服务业
---

# 卡桑德拉服务注册处

在阿帕奇·卡桑德拉</a> 实例中存储

注册服务数据。 服务预计将被发现/存储在 `砂锅` 表与默认书写一致性 `LOCAL_QUORUM` 和读取一致性 `一`。</p> 

支持通过在覆盖中添加以下模块来实现：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-卡桑德拉-服务-注册</artifactId>
     <version>${cas.version}</version>
</dependency>                                                            
```




## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#cassandra-service-registry)。



## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：



```xml
...
<Logger name="com.datastax.driver" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```




## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>
