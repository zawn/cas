---
layout: 违约
title: CAS - FIDO2 网络授权多因子认证
category: 多因素认证
---

# FIDO2 网络授权多因子认证

[WebAuthn](https://webauthn.io/) 是一种 API，它使依赖方（如 Web 服务）能够非常容易地将强大的 身份验证集成到应用中，使用内置于所有领先浏览器和平台的支持。 这意味着 ，网络服务现在可以轻松地为其用户提供强大的身份验证， 选择身份验证器，如安全密钥或内置平台身份验证器（如生物识别读卡器）。

支持通过在 WAR 叠加中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡斯服务器支持网络授权</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn)。

您可能需要在 CAS 叠加 中申报以下存储库，以解决依赖关系：

```groovy       
存储库{
    马文{ 
        马文康特{发布（）=
        网址"https://dl.bintray.com/apereocas/webauthn-cas" 
    }
}
```

## 初级身份验证

允许 WebAuthN 作为初级身份验证的独立身份验证策略是可能的。 使用此方法，已在 CAS 注册的 用户帐户和 FIDO2 启用设备可选择使用其 FIDO2 启用的 设备登录，只需提供链接到其注册记录的用户名即可获得无密码身份验证体验。

设备注册可以使用可用的 CAS ABI 进行带外注册，或者允许用户通过注册流程 作为典型多因素身份验证的一部分。 有关设备注册的详细信息，请参阅下文。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn)。

## 注册

设备注册流程自动烘焙到 CAS 中。 还自动安排背景 *清洁* 过程，定期扫描 存储库，并根据配置的参数删除过期的设备注册记录。 在默认设置中，自用户注册设备以来，设备 在固定期限后过期。 如果您将 U2F MFA 部署到集中分发和撤销代币的设置中， 您可能需要 [延长间隔](../configuration/Configuration-Properties.html#fido2-webauthn)。

<div class="alert alert-warning"><strong>更清洁的使用</strong><p>在分组的 CAS 部署中，最好仅让 
清洁器仅在指定的 CAS 节点上运行，并通过 CAS 设置将其关闭。 在所有节点上保持清洁运行 
可能导致严重的性能和锁定问题。</p></div>

### 行政终点

CAS 提供以下端点：

| 端点                             | 描述                        |
| ------------------------------ | ------------------------- |
| `网络授权/{username}`              | `获取` 请求，为用户获取设备注册记录。      |
| `网络授权/{username}`              | `删除删除用户所有设备注册记录` 请求。      |
| `网络授权/{username}/{credential}` | `删除删除用户设备注册记录` 请求。        |
| `网络授权/{username}`              | `邮政` 要求为用户添加设备注册记录， `记录`。 |

### 违约

默认情况下，将包括一个存储库实施，用于收集用户设备注册并将其保存到内存中。 此选项仅应用于演示和测试目的。

### 杰森

收集用户设备注册并将它们保存到 JSON 文件的设备存储库实施，其 路径通过设置传授给 CAS。 这是一个非常温和的选择，应该主要用于演示和测试 目的。 不用说，此 JSON 资源充当数据库，必须提供给群集中的所有 CAS 服务器节点。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-json)。

### 蒙古德布

可以通过在 WAR 叠加中包含以下模块来将设备注册保存在 MongoDb 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 网络授权 - 蒙古</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-mongodb)。

### 阿尔达普

可以通过在 WAR 覆盖中包含以下模块，将设备注册保存在 LDAP 目录内：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 网络授权 - ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```

设备注册记录保存在指定可配置的多值属性中，称为 JSON blobs。 属性值 进行解析以加载、保存、更新或删除帐户。 如有必要，可以签署/加密每个属性值的内容。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-ldap)。

### 日本经济与经济、经济、经济

可以通过在 WAR 叠加中包含以下模块，将设备注册保存在关系数据库实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 网络授权 - jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-jpa)。

### 雷迪斯

设备注册可以通过在 WAR 叠加中包含以下模块保留在 Redis 数据库实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 网络授权 - 雷迪斯</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-redis)。

### 迪纳莫德布

可以通过在 WAR 叠加中包含以下模块来将设备注册保存在 DynamoDb 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 网络授权 - 发电机</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-dynamodb)。
