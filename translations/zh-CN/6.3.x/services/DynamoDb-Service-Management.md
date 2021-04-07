---
layout: 违约
title: CAS - 迪纳莫德布服务注册处
category: 服务业
---

# 迪纳莫德布服务注册处

在 [DynamoDb](https://aws.amazon.com/dynamodb/) 实例中存储注册服务数据。

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-动态-服务-注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

您需要向 CAS 提供您的 [AWS 凭据](https://aws.amazon.com/console/)。 此外，为了更好地了解 DynamoDb 的核心组件和概念 ，请首先 [本指南](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html) 开始。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-service-registry)。

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

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>