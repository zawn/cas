---
layout: 默认
title: CAS-YubiKey身份验证
category: 多因素身份验证
---

# YubiKey身份验证

Yubico是一项基于云的服务，可通过其旗舰产品YubiKey使用一次性密码进行强大，易于使用且价格合理的两因素身份验证。 一旦获得了Yubico `clientId` 和 `secretKey` ，则这 配置选项可用于将YubiKey设备用作CAS服务器可用于认证用户的主要认证源。

要配置YubiKey帐户并获得API键， [指的是文档](https://upgrade.yubico.com/getapikey/)。

[YubiKey](https://www.yubico.com/products/yubikey-hardware) 身份验证组件通过在WAR叠加中包括以下依赖项来启用：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-yubikey</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 行政端点

CAS提供了以下端点：

| 终点                         | 描述                                                                                                                                                                                                            |
| -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `yubikeyAccountRepository` | 管理和控制 [Google Authenticator帐户记录](YubiKey-Authentication.html)。 `GET` 操作将生成所有帐户记录的列表。 `DELETE` 操作将删除所有帐户记录。 甲 `GET` 操作用的参数选择器产生 `/{username}` 将列出分配给该用户的记录。 甲 `DELETE` 操作用的参数选择器产生 `/{username}` 将删除分配给该用户的记录。 |

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

默认情况下，允许所有用户的YubiKey帐户进行身份验证。 需要授权进行身份验证的设备必须遵循带外注册过程，在该过程中，可以在以下存储后端之一中找到它们的记录。 进行身份验证后，CAS将开始在配置的注册数据库中搜索经过身份验证的用户和设备的匹配记录，以允许成功的身份验证事件。

### JSON格式

如果在CAS设置中指定了文件路径，则可以在JSON文件中跟踪注册记录。 请参阅 [查看本指南](../configuration/Configuration-Properties.html#yubikey) 以获取更多信息。

JSON结构是用户ID到代表任何特定设备的yubikey公共ID的映射：

```json
{
  “ uid1”：[“ yubikeyPublicId1”]，
  “ uid2”：[“ yubikeyPublicId2”]
}
```

### 休息

可以通过外部REST API管理注册记录。 请参阅 [查看本指南](../configuration/Configuration-Properties.html#yubikey) 以获取更多信息。

REST API有望提供并实现以下端点：

| 方法   | 终点             | 描述                    |
| ---- | -------------- | --------------------- |
| `得到` | `/`            | 获取所有注册记录。             |
| `得到` | `/{user}`      | 获取该用户的所有注册记录。         |
| `删除` | `/`            | 删除所有注册记录。             |
| `删除` | `/{user}`      | 删除该用户的所有注册记录。         |
| `删除` | `/{user}/{id}` | 从用户的注册记录中按其ID删除注册的设备。 |
| `邮政` | `/`            | 存储作为请求正文传递的注册记录。      |

### 宽容的

可以通过CAS设置以映射的形式静态指定注册记录，该映射将注册的用户名 与YubiKey设备的公共ID链接起来。 请参阅 [查看本指南](../configuration/Configuration-Properties.html#yubikey) 以获取更多信息。

### JPA

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-yubikey-jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

CAS自动创建和配置的预期数据库架构包含一个表，该表为 `YubiKeyAccount` 其中包含以下字段：

| 场地         | 描述                  |
| ---------- | ------------------- |
| `ID`       | 唯一记录标识符，充当主键。       |
| `publicId` | 用于身份验证的设备的公共标识符/密钥。 |
| `用户名`      | 设备注册的用户名。           |

### CouchDb

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-yubikey-couchdb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

注册记录保存在您选择的单个CouchDb数据库中，该数据库将由CAS自动创建。 该数据库文档的结构如下：

| 场地         | 描述                  |
| ---------- | ------------------- |
| `ID`       | 唯一记录标识符，充当主键。       |
| `publicId` | 用于身份验证的设备的公共标识符/密钥。 |
| `用户名`      | 设备注册的用户名。           |

### 雷迪斯

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持yubikey-redis</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

### DynamoDb

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-yubikey-dynamodb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

### MongoDb

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-yubikey-mongo</artifactId>
     <version>${cas.version}</version>
</dependency>
```

注册记录保存在您选择的单个MongoDb集合中，该集合将由CAS自动创建。 该集合的结构如下：

| 场地         | 描述                  |
| ---------- | ------------------- |
| `ID`       | 唯一记录标识符，充当主键。       |
| `publicId` | 用于身份验证的设备的公共标识符/密钥。 |
| `用户名`      | 设备注册的用户名。           |

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

### 风俗

如果您希望插入将确定 允许哪些用户使用其YubiKey帐户进行身份验证 `YubiKeyAccountRegistry` 的自定义实现，该实现允许您提供用户名和YubiKey public之间的映射。键。


```java
软件包org.apereo.cas.support.yubikey;

@Configuration（“ myYubiKeyConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyYubiKeyConfiguration {

  @Bean
  公共YubiKeyAccountRegistry yubiKeyAccountRegistry（）{
...
  }
}
```

## 设备注册

如果应该注册一个新的YubiKey，则可能需要在将帐户注册到基础商店之前执行其他验证过程。 默认情况下，设备注册步骤仅验证设备令牌。 如果您想扩展此行为，则可以设计自己的验证器，以与其他来源和数据库交叉检查该帐户的有效性和授权：

```java
软件包org.apereo.cas.support.yubikey;

@Configuration（“ myYubiKeyConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyYubiKeyConfiguration {

  @Bean
  公共YubiKeyAccountValidator yubiKeyAccountValidator（）{
...
  }
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

## REST协议凭证提取

如果 [CAS REST协议](../protocol/REST-Protocol.html) 处于打开状态，则将特殊的凭据提取器注入REST身份验证引擎中，以便识别YubiKey凭据并将其作为REST请求的一部分进行身份验证。 请求正文中的预期参数名称为 `yubikeyotp`。
