---
layout: 默认
title: CAS-Google身份验证器身份验证
category: 多因素身份验证
---

# Google身份验证器身份验证

Google身份验证器会在您的手机上生成两步验证码。 使用两步验证登录后，除了主要身份验证外，还需要由Google Authenticator应用生成的代码。 在此处</a>了解有关主题

更多信息。</p> 

请注意，此处提供的功能还应该与 [LastPass Authenticator](https://lastpass.com/auth)等类似物兼容。



## 配置

通过在叠加层中包含以下模块来启用支持：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-gauth</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator)。



## 行政端点

CAS提供了以下端点：

| 终点                          | 描述                                                                                                                                                                                                                        |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `gauthCredentialRepository` | 管理和控制 [Google Authenticator帐户记录](GoogleAuthenticator-Authentication.html)。 `GET` 操作将生成所有帐户记录的列表。 `DELETE` 操作将删除所有帐户记录。 甲 `GET` 操作用的参数选择器产生 `/{username}` 将列出分配给该用户的记录。 甲 `DELETE` 操作用的参数选择器产生 `/{username}` 将删除分配给该用户的记录。 |




## 令牌库

为了防止重用已发行的令牌，CAS将尝试跟踪成功用于验证用户身份的令牌。 定期扫描和清理保存注册记录和令牌的存储库，以便可以删除 



## 登记

默认情况下，包括一个帐户注册表实现，该实现收集用户设备注册并将其保存到内存中。 发行的令牌也被捕获到自清理缓存中，以防止令牌在可配置的时间段内重复使用。 此选项仅应用于演示和测试目的。 此功能的生产部署将需要 实现，该实现能够将帐户注册到持久性存储中。

请注意，每个帐户都可以注册多个设备，以便以后用于多因素身份验证。 在 身份验证流程期间，如果找到多个设备注册记录 ，则将要求用户选择适当的设备进行身份验证。 可以通过CAS设置来控制处理多个设备注册记录的能力。



### JPA

注册记录和令牌可以通过以下模块保存在数据库实例中：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-gauth-jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要了解如何配置数据库驱动程序，请参阅本指南</a>。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-jpa)。</p> 



### CouchDb

注册记录和令牌可以通过以下模块保存在CouchDb实例中：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-gauth-couchdb</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-couchdb)。



### MongoDb

注册记录和令牌可以通过以下模块保存在MongoDb实例中：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-gauth-mongo</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-mongodb)。



### 雷迪斯

注册记录和令牌可以通过以下模块保存在Redis实例中：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-gauth-redis</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-redis)。



### LDAP

注册记录可以通过以下模块保存在LDAP / AD系统中：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-gauth-ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```


帐户注册记录以JSON Blob的形式保存在指定的可配置多值属性内。 属性值被解析为 以加载，保存，更新或删除帐户。 如有必要，可以对每个属性值的内容进行签名/加密。 

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-ldap)。



### 休息

注册记录也可以传递到REST端点。 仅当提供端点URL时，才会激活该行为。

| 方法   | 标头                                                          | 预期回应                | 行为            |
| ---- | ----------------------------------------------------------- | ------------------- | ------------- |
| `得到` | `用户名`                                                       | `200`。 用户体内的帐户记录。   | 获取用户记录        |
| `得到` | `ID`                                                        | `200`。 用户体内的帐户记录。   | 获取给定标识符的记录。   |
| `得到` | `ID`， `的用户名`                                                | `200`。 用户体内的帐户记录。   | 获取给定标识符的用户记录。 |
| `得到` | 不适用                                                         | `200`。 当前注册的帐户记录。   | 获取所有记录        |
| `删除` | 不适用                                                         | `200`。              | 删除所有记录。       |
| `删除` | `用户名`                                                       | `200`。 计算已删除的记录。    | 删除分配给用户的所有记录  |
| `邮政` | `的用户名`， `validationCode`， `SecretKey的`， `scratchCodes`， `名` | `200`。 `正确/错误` 在体内。 | 保存用户记录        |


列出用户的设备注册记录的有效负载示例可能是：



```json 
[
    “ java.util.ArrayList”，[{
        “ @class”：“ org.apereo.cas.gauth.credential.GoogleAuthenticatorAccount”，
        “ scratchCodes”：[“ java.util.ArrayList”，[14883628,81852839 ，40126334,86724930,54355266]，
        “ID”：123456，
        “秘密密钥”： “UM6ALPJU34CBNFTBBLRFMKBNANMFAIBW”，
        “validationCode”：565889，
        “用户名”： “casuser”，
        “名称”：“所需的帐户名称“，
        ” registrationDate“：” 2018-06-20T09：47：31.761155Z“
    }]
]
```


还需要以下端点：

| 方法   | 终点   | 标头    | 预期回应        | 行为        |
| ---- | ---- | ----- | ----------- | --------- |
| `得到` | `数数` | 不适用   | `200`。 数值计数 | 计算所有记录    |
| `得到` | `数数` | `用户名` | `200`。 数值计数 | 计算用户的所有记录 |


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-rest)。



### JSON格式

对于所有用户，注册记录也可以保存在一个JSON文件中。 仅当提供了指向JSON数据存储文件的路径时才激活该行为 ，否则CAS可能会回退以将记录保留在内存中。 在开发过程中和用于演示目的时，此功能通常为 

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-authenticator-json)。



## REST协议凭证提取

如果 [CAS REST协议](../protocol/REST-Protocol.html) 处于打开状态，则将特殊的凭据提取器注入REST身份验证引擎中，以便识别凭据并将其作为REST请求的一部分进行身份验证。 请求正文中的预期参数名称为 `gauthotp`。 帐户标识符也可以使用请求正文中 `gauthacct`
