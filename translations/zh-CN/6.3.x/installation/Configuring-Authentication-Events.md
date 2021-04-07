---
layout: 默认
title: CAS-配置身份验证事件
category: 验证
---

# 认证事件

CAS提供了一种用于使用身份验证事件并将其记录到持久性存储中的功能。 此功能类似于 [审核日志](Audits.html) 保留 ，不同之处在于，功能和存储格式是通过CAS本身而不是审核引擎来控制的。 另外，虽然审核数据可用于报告和监视，但以后可以 ，以评估身份验证请求，评估与请求关联的风险并对其采取进一步的措施。 事件主要 ，以供开发人员和后续的CAS模块使用，而审核数据则针对最终用户功能和报告的部署者。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-core-events</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 记录数据

启用后，事件机制将捕获并记录以下元数据：

| 场地                | 描述            |
| ----------------- | ------------- |
| `PrincipalId`     | 认证主题的主体ID     |
| `时间戳记`            | 此事件的时间戳       |
| `creationTime`    | 此认证事件的时间戳     |
| `clientIpAddress` | 客户端IP地址       |
| `服务器的IP地址`        | 服务器的IP地址      |
| `代理人`             | 浏览器的用户代理      |
| `geoLatitude`     | 身份验证请求来源的地理位置 |
| `地理经度`            | 身份验证请求来源的地理经度 |
| `地理精度`            | 位置精度测量        |
| `geoTimestamp`    | 地理位置请求的时间戳    |

## 地理位置

CAS尝试通过允许浏览器征求用户同意来记录身份验证请求的地理位置属性。 如果浏览器未授予同意或不支持地理位置，则CAS尝试将事件记录 要了解更多信息，请 [本指南](GeoTracking-Authentication-Requests.html)。

## 行政端点

CAS提供了以下端点：

| 终点    | 描述                     |
| ----- | ---------------------- |
| `大事记` | 提供所有CAS记录的事件的JSON表示形式。 |

## 配置

以下存储后端可用于事件消费。

### MongoDb

将身份验证事件存储到MongoDb NoSQL数据库中。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-events-mongo</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-events)。

### DynamoDb

将身份验证事件存储到DynamoDb数据库中。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-events-dynamodb</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-events)。

### CouchDb

将身份验证事件存储在CouchDb实例中。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-events-couchdb</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-events)。

### JPA

将身份验证事件存储到RDBMS中。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-events-jpa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-events)。

### InfluxDb

将身份验证事件存储在InfluxDb实例中。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-events-influxdb</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#influxdb-events)。

### 记忆

在非常有限的时间段内将身份验证事件存储到内存中。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas服务器支持事件内存</artifactId>
  <version>${cas.version}</version>
</dependency>
```
