---
layout: 违约
title: CAS - 谷歌身份验证器身份验证
category: 多因素认证
---

# 谷歌身份验证器身份验证

谷歌身份验证器在您的手机上生成两步验证码。 登录 2 步验证后，除了原始身份验证之外，还需要 Google 身份验证器应用生成的代码。 了解更多关于这个主题 [在这里](https://en.wikipedia.org/wiki/Google_Authenticator)。

请注意，此处显示的功能还应与 [LastPass 身份验证器](https://lastpass.com/auth)等功能兼容。

## 配置

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持高斯</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator)。

## 行政终点

CAS 提供以下端点：

| 端点        | 描述                                                                                                                                                                                                   |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `高思信用文献库` | 管理和控制 [谷歌身份验证器帐户记录](GoogleAuthenticator-Authentication.html)。 `获取` 操作会生成所有帐户记录的列表。 `删除` 操作将删除所有帐户记录。 `GET` 操作中，使用 `/{username}` 的参数选择器生成，将列出分配给用户的记录。 使用 `/{username}` 的参数选择器生成的 `删除` 操作将删除分配给用户的记录。 |

## 令牌存储库

为防止代币的重复使用，中科院将努力跟踪成功用于用户身份验证的代币。 定期扫描和清理保存注册记录和代币的存储库，以便删除过期和以前使用的代币 。

## 注册

默认情况下，将包括一个帐户注册表实施，该实现收集用户设备注册并将其保存到内存中。 已发行的代币还被捕获到自清洁缓存中，以防止令牌在可配置的时间内重复使用。 此选项仅应用于演示和测试目的。 此功能的生产部署将需要单独 实现能够将帐户注册到持久存储中的注册表。

请注意，允许每个单个帐户注册多个设备，以便以后用于多因素身份验证。 认证流程持续时间，如果发现多个设备注册记录 ，将要求用户选择适当的设备进行身份验证。 处理多个设备注册记录的能力可以通过 CAS 设置进行控制。

### 日本经济与经济、经济、经济

注册记录和代币可通过以下模块保存在数据库实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持高斯 - jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序， [请参阅本指南](../installation/JDBC-Drivers.html)。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-jpa)。

### 库奇德布

注册记录和代币可通过以下模块保存在 CouchDb 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 高斯 - 沙发</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-couchdb)。

### 蒙古德布

注册记录和代币可通过以下模块保存在 MongoDb 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持高斯 - 蒙戈</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-mongodb)。

### 雷迪斯

注册记录和代币可通过以下模块保存在 Redis 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持高斯 - 雷迪斯</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-redis)。

### 阿尔达普

注册记录可通过以下模块保存在 LDAP/AD 系统内：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持高斯 - 阿尔达普</artifactId>
     <version>${cas.version}</version>
</dependency>
```

帐户注册记录保存在指定可配置的多值属性中，称为 JSON blobs。 属性值 进行解析以加载、保存、更新或删除帐户。 如有必要，可以签署/加密每个属性值的内容。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-ldap)。

### 休息

注册记录也可以传递到 REST 终点。 仅在提供端点网址时激活行为。

| 方法   | 头                                | 预期响应               | 行为            |
| ---- | -------------------------------- | ------------------ | ------------- |
| `获取` | `用户名`                            | `200`。 用户身体中的帐户记录。 | 获取用户记录        |
| `获取` | `ID`                             | `200`。 用户身体中的帐户记录。 | 获取给定标识符的记录。   |
| `获取` | `id`， `用户名`                      | `200`。 用户身体中的帐户记录。 | 获取给定标识符的用户记录。 |
| `获取` | 不适用                              | `200`。 当前注册的帐户记录。  | 获取所有记录        |
| `删除` | 不适用                              | `200`。             | 删除所有记录。       |
| `删除` | `用户名`                            | `200`。 计数已删除的记录。   | 删除分配给用户的所有记录  |
| `发布` | `用户名`， `验证码`， `密钥`， `划痕代码`， `名称` | `200`。 `真假` 在体内。   | 保存用户记录        |

列出用户设备注册记录的示例有效载荷可能是：

```json 
•
    "java.util.列列表"，[{
        "@class"："组织.apereo.cas.高斯.证书.谷歌授权人帐户"，
        "刮码"： [java.util.Arraylist"， [14883628，81852839，40126334，86724930，5435526]]，
        "id"： 123456，
        "秘密钥匙"："UM6ALPJU34CBNFTBRFMKBNMFAIBW"，
        "验证码"：565889，
        "用户名"："用户名"，

        "姓名"："所需帐号"，"注册日"："2018-06-20T09：47：31.761155Z"
    []
]
```

还需要提供以下端点：

| 方法   | 端点   | 头     | 预期响应        | 行为        |
| ---- | ---- | ----- | ----------- | --------- |
| `获取` | `计数` | 不适用   | `200`。 数值计数 | 计算所有记录    |
| `获取` | `计数` | `用户名` | `200`。 数值计数 | 为用户计算所有记录 |

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-rest)。

### 杰森

注册记录也可以保存在一个单一的 JSON 文件中，供所有用户使用。 只有当提供 JSON 数据存储文件的路径时，才会激活该行为， ，否则 CAS 可能会返回到记忆中保留记录。 此功能在开发过程中和演示目的中大多 有用。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-json)。

## REST 协议凭据提取

如果打开 [CAS REST 协议](../protocol/REST-Protocol.html) ，则向 REST 认证引擎注入特殊的凭据提取器，以便识别凭据并将其作为 REST 请求的一部分进行身份验证。 请求主体中的预期参数名称 `高索特普`。 帐户标识符也可以使用请求主体中的 `高塔克特` 参数传递。
