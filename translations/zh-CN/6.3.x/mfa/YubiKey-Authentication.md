---
layout: 违约
title: 中科院 - 尤比基认证
category: 多因素认证
---

# 尤比基认证

Yubico 是一种基于云的服务，通过其旗舰产品 YubiKey 提供强大、易于使用且经济实惠的双重身份验证，并具有一次性密码。 一旦 Yubico `客户端id` 和 `秘密密钥` 获得，那么可用的 配置选项将 YubiKey 设备用作 CAS 服务器可用于对用户进行身份验证的主要身份验证源。

要配置 YubiKey 帐户并获取 API 密钥， [参考](https://upgrade.yubico.com/getapikey/)文档。

[YubiKey](https://www.yubico.com/products/yubikey-hardware) 身份验证组件通过在 WAR 叠加中包含以下依赖项而启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡服务器支持-尤比基</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 行政终点

CAS 提供以下端点：

| 端点         | 描述                                                                                                                                                                                       |
| ---------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `尤比基帐户存储库` | 管理和控制 [谷歌身份验证器帐户记录](YubiKey-Authentication.html)。 `获取` 操作会生成所有帐户记录的列表。 `删除` 操作将删除所有帐户记录。 `GET` 操作中，使用 `/{username}` 的参数选择器生成，将列出分配给用户的记录。 使用 `/{username}` 的参数选择器生成的 `删除` 操作将删除分配给用户的记录。 |

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

默认情况下，用户的所有 YubiKey 帐户都允许进行身份验证。 需要授权进行身份验证的设备需要遵循带外注册流程，其中记录位于以下存储后端之一。 认证后，CAS 将开始搜索已配置的注册数据库，以匹配经过验证的用户和设备的记录，以便成功进行身份验证。

### 杰森

注册记录可以在 JSON 文件中跟踪，前提是在 CAS 设置中指定文件路径。 有关详细信息，请参阅 [查看本指南](../configuration/Configuration-Properties.html#yubikey) 。

JSON 结构是用户 ID 到 yubikey 公共 ID 的地图，代表任何特定设备：

```json
{
  "uid1"：["尤比基公民1"]，
  "uid2"：["尤比基公民2"]
}
```

### 休息

注册记录可以通过外部 REST API 进行管理。 有关详细信息，请参阅 [查看本指南](../configuration/Configuration-Properties.html#yubikey) 。

下列端点预计将由 REST API 提供并实施：

| 方法   | 端点             | 描述                    |
| ---- | -------------- | --------------------- |
| `获取` | `/`            | 获取所有注册记录。             |
| `获取` | `/{user}`      | 获取用户的所有注册记录。          |
| `删除` | `/`            | 删除所有注册记录。             |
| `删除` | `/{user}`      | 删除用户的所有注册记录。          |
| `删除` | `/{user}/{id}` | 从用户的注册记录中删除其 ID 注册设备。 |
| `发布` | `/`            | 商店注册记录作为请求主体通过。       |

### 许可的

注册记录可以通过 CAS 设置静态指定，其形式是将注册用户名 与 YubiKey 设备的公共 ID 连接在一起的地图。 有关详细信息，请参阅 [查看本指南](../configuration/Configuration-Properties.html#yubikey) 。

### 日本经济与经济、经济、经济

支持通过在 WAR 叠加中包括以下依赖项来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 尤比基 - jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

CAS 自动创建和配置的预期数据库模式包含一个表， `YubiKey 帐户` 以下字段：

| 田      | 描述                  |
| ------ | ------------------- |
| `ID`   | 唯一的记录标识符，作为主要键。     |
| `公共Id` | 用于身份验证的设备的公共标识符/密钥。 |
| `用户名`  | 其设备已注册的用户名。         |

### 库奇德布

支持通过在 WAR 叠加中包括以下依赖项来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 尤比基 - 库奇德布</artifactId>
     <version>${cas.version}</version>
</dependency>
```

注册记录保存在您选择的单个 CouchDb 数据库中，该数据库将由 CAS 自动创建。 该数据库文档的结构如下：

| 田      | 描述                  |
| ------ | ------------------- |
| `ID`   | 唯一的记录标识符，作为主要键。     |
| `公共Id` | 用于身份验证的设备的公共标识符/密钥。 |
| `用户名`  | 其设备已注册的用户名。         |

### 雷迪斯

支持通过在 WAR 叠加中包括以下依赖项来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 尤比基 - 雷迪斯</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

### 迪纳莫德布

支持通过在 WAR 叠加中包括以下依赖项来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 尤比基 - 迪纳莫德</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

### 蒙古德布

支持通过在 WAR 叠加中包括以下依赖项来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 尤比基 - 蒙古</artifactId>
     <version>${cas.version}</version>
</dependency>
```

注册记录保存在您选择的单个 MongoDb 集合中，该集合将由 CAS 自动创建。 此集合的结构如下：

| 田      | 描述                  |
| ------ | ------------------- |
| `ID`   | 唯一的记录标识符，作为主要键。     |
| `公共Id` | 用于身份验证的设备的公共标识符/密钥。 |
| `用户名`  | 其设备已注册的用户名。         |

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yubikey)。

### 习惯

如果您希望插入自定义注册表实施， 以确定哪些用户可以使用其 YubiKey 帐户进行身份验证，则可以插入 `YubiKey 国家注册` 的自定义实施，该允许您在用户名和 YubiKey 公共密钥之间提供映射。


```java
包组织. 阿佩雷奥. 卡斯. 支持. 尤比基;

@Configuration（"myYubiKey配置"）
@EnableConfigurationProperties（配置专业.class）
公共类MyYubiKey配置=

  @Bean
  公共尤比基国家注册yubiKey帐户注册（）{
      。。。
  •
}
```

## 设备注册

如果应注册新的 YubiKey，最好在帐户在基础商店注册之前执行额外的验证过程。 默认情况下，设备注册步骤仅验证设备令牌。 如果您想扩展此行为，您可以设计自己的验证器，根据替代源和数据库对账进行交叉检查，以获得有效性和授权：

```java
包组织. 阿佩雷奥. 卡斯. 支持. 尤比基;

@Configuration（"myYubiKey配置"）
@EnableConfigurationProperties（配置.class）
公共类MyYubiKey配置=

  @Bean
  公共尤比基计数器yubiKey帐户验证器（）{
      。。。
  •
}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。

## REST 协议凭据提取

如果 [CAS REST 协议](../protocol/REST-Protocol.html) 打开，则向 REST 身份验证引擎注入特殊的凭据提取器，以便识别 YubiKey 凭据并将其作为 REST 请求的一部分进行身份验证。 请求主体中的预期参数名称 ``。
