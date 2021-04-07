---
layout: 默认
title: CAS-U2F-FIDO通用第二因素认证
category: 多因素身份验证
---

# U2F-FIDO通用身份验证

U2F是一个开放的认证标准，使互联网用户能够安全地访问任何数量的在线服务，与一个单一的设备，即刻 与无驱动程序，或需要的客户端软件。 CAS U2F实施建立在 [Yubico](https://www.yubico.com/about/background/fido/) 和 之上，技术规范由开放认证行业联盟 [FIDO Alliance](https://fidoalliance.org/)托管。

请注意，并非今天所有的浏览器都支持U2F。 虽然似乎存在最新版本的Chrome和 Opera的支持，但您应始终验证目标浏览器是否支持U2F。

通过在WAR叠加中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-u2f</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f)。

## 登记

U2F设备注册流程会自动烘焙到CAS中。 后台 *清除程序* 进程也将自动计划为 存储库，并根据配置的参数删除过期的设备注册记录。 在默认设置中，自用户注册U2F令牌（与上次使用令牌的时间无关）以来， 如果将U2F MFA部署到令牌被集中分发和吊销的设置，则可能需要 [延长间隔](../configuration/Configuration-Properties.html#fido-u2f)。

<div class="alert alert-warning"><strong>清洁使用</strong><p>在集群式CAS部署中，最好使清理程序 
CAS节点上运行，并通过CAS设置在所有其他节点上将其关闭。 使清洁程序在所有节点上运行可能会导致严重的性能和锁定问题。</p></div>

### 行政端点

CAS提供了以下端点：

| 终点           | 描述                                                                                                                                                                                                                |
| ------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `u2fDevices` | `GET` 请求表示已注册设备的集合。 可以使用选择器路径（即 `u2fDevices /{username}`） `GET` 查询单个用户的注册设备。 可以使用选择器路径（即 `u2fDevices /{username}`） `DELETE` 删除单个用户的注册设备。 用于单个用户的单个注册设备可以通过被删除 `DELETE` 通过使用选择路径（即 `u2fDevices /{username}/{id}`）。 |

### 默认

默认情况下，包含一个存储库实现，该实现收集用户设备注册并将其保存到内存中。 此选项仅应用于演示和测试目的。

### JSON格式

一种设备存储库实现，该实现收集用户设备注册并将其保存到JSON文件中，该文件的路径通过设置传给CAS。 这是一个非常适度的选项，应主要用于演示和测试目的。 不用说，此JSON资源充当数据库，必须可供集群中的所有CAS服务器节点使用。

存储在JSON文件中的设备采用以下格式：

```json
{
  “ @class”：“ java.util.HashMap”，
  “ devices”：[“ java.util.ArrayList”，[{
    “ @class”：“ org.apereo.cas.adaptors.u2f.storage。 U2FDeviceRegistration “
    ”ID“：1508515100762，
    ”用户名“： ”casuser“，
    ”记录“： ”{\“ keyHandle \ ”：\“ keyhandle11 \” \ “公钥\”：\ “publickey1 \”， \“ counter \”：1，\“ compromised \”：false}“，
    ” createdDate“：” 2016-10-15“
  }]]
}
```

### Groovy

设备注册可以通过外部Groovy脚本进行管理。 该脚本可以设计如下：

```groovy
import java.util。*
import org.apereo.cas.adaptors.u2f.storage。*

Map<String, List<U2FDeviceRegistration>> read（final Object ... args）{
    def logger = args[0]
...
    null
}

布尔值写入（最终对象... args）{
    列表<U2FDeviceRegistration> 列表= args[0]
    def logger = args[1]
...
    true
}

void removeAll（final Object ... args）{
    def logger = args[0]
}          

def remove（Object [] args）{
    def device = args[0]
    def logger = args[1]
}
```

### JPA

通过在WAR叠加中包含以下模块，可以将设备注册保留在关系数据库中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-u2f-jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-jpa)。

### MongoDb

通过在WAR叠加层中包含以下模块，可以将设备注册保留在MongoDb实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-u2f-mongo</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-mongodb)。

### DynamoDb

通过在WAR叠加层中包含以下模块，可以将设备注册保留在DynamoDb实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-u2f-dynamodb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-dynamodb)。

### 雷迪斯

通过在WAR叠加层中包含以下模块，可以将设备注册保留在Redis实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-u2f-redis</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-redis)。

### CouchDb

通过在WAR叠加层中包含以下模块，可以将设备注册保留在CouchDb实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-u2f-couchdb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-couchdb)。

### 休息

设备注册可以通过REST API进行管理。 端点必须设计为接受/处理 `application / json`。 来回传递设备集合的语法是用JSON设计的，并且与上面定义的JSON结构相同。

传递了以下参数：

| 手术   | 参数               | 描述                | 结果                            |
| ---- | ---------------- | ----------------- | ----------------------------- |
| `得到` | 不适用              | 检索所有注册的设备。        | `200` 状态码响应主体中以JSON形式收集已注册设备。 |
| `邮政` | 将已注册设备收集为JSON    | 存储已注册的设备。         | `200`。                        |
| `删除` | 不适用              | 删除所有设备记录          | `200`。                        |
| `删除` | `/${id}`  作为路径变量 | 删除所有与该标识符匹配的设备记录。 | `200`。                        |

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-rest)。
