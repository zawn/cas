---
layout: 违约
title: CAS - 配置身份验证事件
category: 认证
---

# 身份验证事件

CAS 提供一个设施，用于将身份验证事件消耗和记录到持久存储中。 此功能类似于 [审计日志](Audits.html) 保存的记录 ，但功能和存储格式是通过 CAS 本身而不是审计引擎控制的。 此外，虽然审计数据可用于报告和监控，但通过此功能存储到存储中的事件以后可能会以历史方式评估 ，以评估身份验证请求、评估与它们相关的风险并采取进一步行动。 事件主要是 设计为由开发人员和随后的 CAS 模块使用，而审计数据则针对最终用户功能和报告的部署人员。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-核心事件</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 记录的数据

启用后，事件机制捕获并记录以下元数据：

| 田       | 描述             |
| ------- | -------------- |
| `主要ID`  | 已验证主体的主要 ID    |
| `时间戳`   | 此事件的时戳         |
| `创建时间`  | 此身份验证事件的时戳     |
| `客户地址`  | 客户端 IP 地址      |
| `服务器地址` | 服务器 IP 地址      |
| `代理`    | 浏览器的用户代理       |
| `地理环境`  | 身份验证请求原产地的地理纬度 |
| `地理龙度`  | 认证请求的原产地的地理经度  |
| `地理准确性` | 位置的准确度量        |
| `地理时间戳` | 地理位置请求的时戳      |

## 地理位置

CAS试图通过允许浏览器征求用户同意来记录身份验证请求的地理位置属性。 如果未获得同意或浏览器不支持地理位置，CAS 在尝试 记录事件时将忽略地理位置数据。 要了解更多，请 [本指南](GeoTracking-Authentication-Requests.html)。

## 行政终点

CAS 提供以下端点：

| 端点   | 描述                      |
| ---- | ----------------------- |
| `事件` | 提供所有 CAS 记录事件的 JSON 表示。 |

## 配置

以下存储后端可用于事件的消费。

### 蒙古德布

将身份验证事件存储到蒙古数据库 NoSQL 数据库中。

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持事件 - 蒙古</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-events)。

### 迪纳莫德布

将身份验证事件存储到 DynamoDb 数据库中。

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-事件-动态</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-events)。

### 库奇德布

将身份验证事件存储在沙发数据库实例中。

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持事件 - 沙发</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-events)。

### 日本经济与经济、经济、经济

将身份验证事件存储到 RDBMS 中。

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持事件 - jpa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-events)。

### 拉布斯德布

将身份验证事件存储在"影响数据库"实例中。

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-事件-</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#influxdb-events)。

### 记忆

将身份验证事件存储到内存中的时间段非常有限。

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-事件-内存</artifactId>
  <version>${cas.version}</version>
</dependency>
```
