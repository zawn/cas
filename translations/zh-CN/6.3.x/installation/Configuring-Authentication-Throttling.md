---
layout: 违约
title: CAS - 配置身份验证限制
category: 认证
---

# 限制身份验证尝试

## 容量限制

CAS 能够支持基于代币存储桶算法的请求速率限制。 这意味着在时间窗口内达到特定可配置 容量的身份验证请求可能会被阻止或 _限制_ 以减速。 这样做是为了保护系统免于超载，允许您 引入一个场景，允许 CAS 每分钟 120 个身份验证请求，每秒 10 个请求的重新填充速率，从而在容量存储桶中不断增加。

在配置叠加中启用以下模块：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持油门桶 4j</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 故障限制

CAS 提供了一个设施，用于限制失败的登录尝试，以支持密码猜测和相关滥用情景。 为跟踪失败的尝试提供了几个策略：

1. 源 IP - 限制来自同一 IP 地址的任何用户名的连续失败登录。
2. 源 IP 和用户名 - 限制来自同一 IP 地址的特定用户的连续失败登录。

与 CAS 一起发货的所有登录限制组件都限制连续失败的登录尝试，这些尝试超过阈值， 每秒故障率。 提供以下属性来定义故障率。

* `故障第二` - 阈值适用的几秒钟内的时间段。
* `失败 保持` - 上述期间允许的失败登录尝试数。

每 3 秒超过 1 次的失败率表示自动身份验证尝试，这是限制策略的 合理基础。 无论政策如何，都应谨慎权衡安全性与获取性： 过于严格的政策可能会阻止合法身份验证尝试。

在配置叠加中启用以下模块：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-油门</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 行政终点

CAS 提供以下端点：

| 端点   | 描述              |
| ---- | --------------- |
| `油门` | `获取` 请求以获取节流记录。 |

### IP地址

使用内存映射来防止来自同一 IP 地址的连续失败登录尝试。

### IP 地址和用户名

使用内存映射来防止从同一 IP 地址 特定用户名的连续失败登录尝试。

### 京城

查询 CAS 审计设施用于防止来自同一 IP 地址的特定用户名的连续失败登录尝试的数据库数据源。 此组件需要并依赖于通过数据库</a>

CAS 审计功能。</p> 

在配置叠加中启用以下模块：



```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 油门 - jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```


有关如何配置审计的其他说明，请 [查看以下指南](Audits.html)。



### 蒙古德布

查询 CAS 审计设施用于防止来自同一 IP 地址的特定用户名的连续失败登录尝试的 MongoDb 数据源。 此组件需要并依赖于通过 MongoDb</a> CAS 审计功能。</p> 

在配置叠加中启用以下模块：



```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 油门 - 蒙古</artifactId>
    <version>${cas.version}</version>
</dependency>
```




### 雷迪斯

查询 CAS 审计设施用于防止从同一 IP 地址 特定用户名的连续失败登录尝试的 Redis 数据源。 此组件需要并 取决于通过 Redis</a> 的 CAS 审计功能 。</p> 

在配置叠加中启用以下模块：



```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-油门-</artifactId>
    <version>${cas.version}</version>
</dependency>
```




### 黑兹尔卡斯特

此功能使用分布式黑兹尔卡斯特地图来记录节流身份验证尝试。 此组件要求并取决于 CAS [审计功能](Audits.html)

在配置叠加中启用以下模块：



```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 油门 - 哈泽尔卡斯特</artifactId>
    <version>${cas.version}</version>
</dependency>
```




### 库奇德布

查询 CAS 审计设施用于防止从同一 IP 地址 特定用户名的连续失败登录尝试的 CouchDb 数据源。 此组件需要并 取决于通过 CouchDb</a> 的 CAS 审计功能 。</p> 

在配置叠加中启用以下模块：



```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持油门 - 沙发 - 沙发</artifactId>
    <version>${cas.version}</version>
</dependency>
```


有关如何配置审计的其他说明，请 [查看以下指南](Audits.html)。



## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-throttling)。



## 高可用性

所有节流组件都适合 CAS 部署，以满足 [推荐的 HA 架构](../high_availability/High-Availability-Guide.html)。 特别是具有多个 CAS 节点的部署，配置了具有会话亲和力的负载平衡器后面，可以使用内存或 _inspektr_ 组件。 讨论这个理由 很有启发性。 由于负载平衡器会话亲和力由源 IP 地址决定，而源 IP 地址 是应用油门策略的相同标准，则来自固定位置的攻击者应被绑定到 相同的 CAS 服务器节点，以进行连续身份验证尝试。 另一方面，分布式攻击，如果连续的 请求将不确定地路由，将导致对内存 CAS 组件的随意跟踪，因为 尝试将被划分为 N 系统。 但是，由于来源不同，准确的会计是毫无意义的，因为 节流组件本身承担了用于跟踪目的的恒定源 IP。 登录限制组件不足以检测或防止分布式密码蛮力攻击。
