---
layout: 默认
title: CAS-配置身份验证限制
category: 验证
---

# 限制认证尝试

## 容量限制

CAS能够支持基于令牌桶算法的请求速率限制。 这意味着在某个时间窗口内达到某个可配置 容量的身份验证请求可能会被阻止，也可能会限制 __ 来减慢速度。 这样做是为了防止系统超载，允许您引入 个方案，以允许CAS 120身份验证请求每分钟，每秒重新填充速率为10个请求，这会在容量存储桶中不断增加。

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-throttle-bucket4j</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 故障节流

CAS提供了一种限制登录失败尝试的功能，以支持密码猜测和相关的滥用情况。 提供了一些策略来跟踪失败的尝试：

1. 源IP-对来自同一IP地址的任何用户名限制连续失败的登录。
2. 源IP和用户名-限制来自同一IP地址的特定用户的连续登录失败。

CAS附带的所有登录限制组件均限制连续的失败登录尝试，这些尝试的失败次数超过 的失败率。 提供以下属性来定义故障率。

* `failureRangeInSeconds` 应用阈值的时间段（以秒为单位）。
* `failureThreshold` 在上述时间段内允许的失败登录尝试次数。

每3秒大于1的故障率表示自动进行身份验证尝试，这是 合理基础。 无论采取何种政策，都应权衡安全性与访问权之间的关系； 过于严格的策略可能会阻止合法的身份验证尝试。

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-throttle</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 行政端点

CAS提供了以下端点：

| 终点   | 描述                 |
| ---- | ------------------ |
| `油门` | `GET` 请求以获取受限制的记录。 |

### IP地址

使用内存映射来防止从同一IP地址连续失败的登录尝试。

### IP地址和用户名

使用内存映射来防止连续失败的登录尝试，该登录尝试是将来自同一IP地址的特定用户名

### JDBC

查询CAS审核工具使用的数据库数据源，以防止来自同一IP地址的特定用户名的连续登录尝试失败。 该组件要求并取决于数据库 [CAS审核功能](Audits.html)

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-throttle-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

有关如何配置审核的其他说明，请 [查看以下指南](Audits.html)。

### MongoDb

查询CAS审核工具使用的MongoDb数据源，以防止来自同一IP地址的特定用户名的连续登录尝试失败。 该组件要求并取决于通过MongoDb [CAS审核功能](Audits.html)

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-throttle-mongo</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 雷迪斯

查询CAS审核工具使用的Redis数据源，以防止来自同一IP地址的特定用户名 该组件要求， 取决于 [通过Redis的CAS审核功能](Audits.html)

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-throttle-redis</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 淡褐色

此功能使用分布式Hazelcast映射来记录受限制的身份验证尝试。 该组件要求且取决于 [CAS审核功能](Audits.html)

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持油门hazelcast</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### CouchDb

查询CAS审核工具使用的CouchDb数据源，以防止来自同一IP地址的特定用户名 此组件要求，并且 取决于 [通过CouchDb的CAS审核功能](Audits.html)

在配置叠加层中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-throttle-couchdb</artifactId>
    <version>${cas.version}</version>
</dependency>
```

有关如何配置审核的其他说明，请 [查看以下指南](Audits.html)。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-throttling)。

## 高可用性

所有节流组件都适合于满足 [推荐的HA体系结构](../high_availability/High-Availability-Guide.html)的CAS部署。 在特定的部署中，在配置有会话亲缘关系的负载均衡器后面 _inspektr_ 组件。 讨论其原理是有 由于负载平衡器会话亲缘关系由源IP地址确定，该 与应用节流策略所基于的标准相同，因此来自固定位置的攻击者应绑定到 相同的CAS服务器节点上，以进行连续的身份验证尝试。 请求的分布式攻击将导致对内存中CAS组件的随便跟踪，因为尝试 将在N个系统之间进行分配。 但是，由于源变化，因此精确的记帐将毫无意义，因为 限制组件本身为跟踪目的假定了恒定的源IP。 登录限制组件 不足以检测或阻止分布式密码蛮力攻击。
