---
layout: 默认
title: CAS-FIDO2 WebAuthn多因素身份验证
category: 多因素身份验证
---

# FIDO2 WebAuthn多因素身份验证

[WebAuthn](https://webauthn.io/) 是一个API，使依赖方（例如Web服务）可以很容易地使用所有领先的浏览器和平台内置的支持 这意味着 ，表示Web服务现在可以轻松地通过选择身份验证器 例如安全密钥）或内置平台身份验证器（例如生物识别读取器）来为其用户提供强大的身份验证。

通过在WAR叠加中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-webauthn</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn)。

您可能还需要在 声明以下存储库，以便能够解决依赖关系：

```groovy       
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://dl.bintray.com/apereocas/webauthn-cas” 
    }
}
```

## 主要认证

可以允许WebAuthN充当主要身份验证的独立身份验证策略。 使用此方法，通过仅提供链接到其注册记录的用户名以获得无密码身份验证体验 用户帐户和已在CAS中注册的启用FIDO2的设备选择使用其启用FIDO2的

设备注册可以使用可用的CAS API在带外进行，或者作为典型的多因素身份验证的一部分， 有关设备注册的详细信息，请参见下文。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn)。

## 登记

设备注册流程会自动烘焙到CAS中。 后台 *清除程序* 进程也将自动计划为 存储库，并根据配置的参数删除过期的设备注册记录。 在默认设置中， 在固定时间后过期。 如果将U2F MFA部署到令牌被集中分发和吊销的设置，则 可能需要 [延长间隔](../configuration/Configuration-Properties.html#fido2-webauthn)。

<div class="alert alert-warning"><strong>清洁使用</strong><p>在集群式CAS部署中，最好使 
清除程序仅在一个指定的CAS节点上运行，并通过CAS设置在所有其他CAS节点上将其关闭。 使清洁程序 
运行可能会导致严重的性能和锁定问题。</p></div>

### 行政端点

CAS提供了以下端点：

| 终点                                         | 描述                                  |
| ------------------------------------------ | ----------------------------------- |
| `webAuthnDevices /{username}`              | `GET` 请求以获取用户的设备注册记录。               |
| `webAuthnDevices /{username}`              | `DELETE` 请求删除用户的所有设备注册记录。           |
| `webAuthnDevices /{username}/{credential}` | `DELETE` 请求删除用户的设备注册记录。             |
| `webAuthnDevices /{username}`              | `POST` 请求为用户添加带有设备请求参数 `记录`的设备注册记录。 |

### 默认

默认情况下，包含一个存储库实现，该实现收集用户设备注册并将其保存到内存中。 此选项仅应用于演示和测试目的。

### JSON格式

一种设备存储库实现，它收集用户设备注册并将其保存到JSON文件中，该JSON文件的 路径通过设置传给CAS。 这是一个非常适度的选项，应主要用于演示和测试 目的。 不用说，此JSON资源充当数据库，必须可供集群中的所有CAS服务器节点使用。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-json)。

### MongoDb

通过在WAR叠加层中包含以下模块，可以将设备注册保留在MongoDb实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-webauthn-mongo</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-mongodb)。

### LDAP

通过在WAR叠加层中包含以下模块，可以将设备注册保留在LDAP目录中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-webauthn-ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```

设备注册记录以JSON Blob的形式保存在指定的可配置多值属性中。 属性值被解析为 以加载，保存，更新或删除帐户。 如有必要，可以对每个属性值的内容进行签名/加密。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-ldap)。

### JPA

通过在WAR覆盖中包含以下模块，可以将设备注册保留在关系数据库实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-webauthn-jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-jpa)。

### 雷迪斯

通过在WAR覆盖中包含以下模块，可以将设备注册保留在Redis数据库实例内：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-webauthn-redis</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-redis)。

### DynamoDb

通过在WAR叠加层中包含以下模块，可以将设备注册保留在DynamoDb实例中：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-webauthn-dynamodb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido2-webauthn-dynamodb)。
