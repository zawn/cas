---
layout: 默认
title: CAS-可信设备多因素身份验证
category: 多因素身份验证
---

# 多因素身份验证受信任的设备/浏览器

除了CAS [MFA功能](Configuring-Multifactor-Authentication.html) 提供的触发器外， 情况下，您还希望用户让用户决定是否应该信任当前的浏览器/设备，从而跳过后续的MFA请求。 目标是让CAS在可配置的时间内记住该决定，并且在决定 被强行撤销或被认为已过期之前，不会用MFA困扰用户。

在MFA工作流程期间信任设备将意味着该 **设备****位置** **用户** 被记住了最终决定。 这些密钥安全地组合在一起，并分配给最终决策。

部署之前，应考虑以下事项：

- 是否应该允许用户选择授权“当前”设备？
- ...或者一旦开始MFA，那一定会自动发生吗？
- 应该如何记住用户的决定和选择？ 它们存储在哪里？
- CAS应该信任用户决定多长时间？
- 可信身份验证会话如何传达回应用程序？

请注意，默认情况下启用此功能意味着，如果您打开了多个MFA提供程序，则该功能将全局应用于所有功能。 可以选择禁用此功能，并将其仅应用于选定的一组提供程序。

## 配置

通过以下模块提供支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-trusted-mfa</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 行政端点

CAS提供了以下端点：

| 终点                          | 描述                                                                                                                                                                                                                                                    |
| --------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `multifactorTrustedDevices` | 公开当前由CAS多因素身份验证引擎 [且受信任的设备为](Multifactor-TrustedDevice-Authentication.html) `GET` 操作将生成所有受信任设备的列表。 在URL中指定用户名作为占位符/选择器将获取为该用户注册的设备（即 `multifactorTrustedDevices /{username}`）。 使用设备密钥ID的 `DELETE` 操作将尝试删除受信任的设备（即 `multifactorTrustedDevices /{id}`）。 |

## 设定值

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-trusted-devicebrowser)。

## 身份验证上下文

如果MFA请求由于可信任的身份验证决定而被绕过，则应用程序将收到一个特殊属性，作为表示此行为的验证有效负载的 应用程序必须进一步考虑以下情况：他们要求MFA 模式，但由于身份验证会话是受信任的并且MFA被绕过，因此在响应中未收到对它的确认。

## 设备指纹

为了将受信任的设备彼此区分开，我们需要计算设备指纹，该指纹唯一地 标识各个设备。 该设备指纹的计算可以利用请求

可以使用以下方法来计算设备指纹：

- 客户端IP地址
- 随机生成的Cookie和客户端IP（默认）
- [地理位置地址](../installation/GeoTracking-Authentication-Requests.html)。 你需要确保CAS是 允许 [问和处理地理数据](../installation/Configuring-Authentication-Events.html) 浏览器提供。
- 用户代理标头

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#trusted-device-fingerprint)。

## 旁路

作为MFA工作流程的一部分，允许用户选择退出向CAS注册受信任的设备。 此外， MFA的受信任设备工作流程：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^（https | imaps）：//app.example.org”，
  “ name”：“ Example”，
  “ id”：
  “ multifactorPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ bypassTrustedDeviceEnabled”：true
  }
}

```
## 贮存

必须记住用户的决定并在以后的后续请求中进行处理。  后台 *清理程序* 进程也将自动调度为 定期扫描所选的存储库/数据库/注册表，并根据配置的阈值参数删除过期的记录。

<div class="alert alert-warning"><strong>清洁使用</strong><p>在集群式CAS部署中，最好使清理程序 
节点上运行，并通过CAS设置在所有其他节点上将其关闭。 使清洁程序在所有节点上运行可能会导致严重的性能和锁定问题。</p></div>

### 默认

如果不执行任何操作，默认情况下，记录将保留在运行时内存中，并缓存可配置的时间。 如果您的部署规模很小，用户群很小，或者您想演示功能，这将非常有用。

### JSON格式

记录可以保存在静态json资源中，该资源的路径是通过CAS设置定义的。 如果您的部署规模很小，用户群很小，或者您想演示该功能，则这也是最有用的。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-storage)。

### JDBC

用户决定也可以保留在您自己选择的常规RDBMS中。

通过以下模块提供支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持信任的mfa-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序，请参阅本指南</a>

。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jdbc-storage)。</p> 



### CouchDb

用户决定也可以保留在CouchDb实例中。

通过以下模块提供支持：



```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持信任的mfa-couchdb</artifactId>
    <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-storage)。



### MongoDb

用户决定也可以保留在MongoDb实例中。

通过以下模块提供支持：



```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持信任的mfa-mongo</artifactId>
    <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-storage)。



### DynamoDb

用户决策也可以保留在DynamoDb实例中。

通过以下模块提供支持：



```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持信任的mfa-dynamodb</artifactId>
    <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-storage)。



### 休息

如果您希望完全委派用户决策的管理，验证和持久性，则可以设计一个REST API ，CAS应与该REST API联系以验证用户决策，并记住这些决策供以后使用。

通过以下模块提供支持：



```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持信任的mfa-rest</artifactId>
    <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-storage)。



#### 检索可信记录

`GET` 请求，该请求返回所有有效且未过期的受信任身份验证记录。



```bash
curl -i -H“接受：应用程序/ json” -H“内容类型：应用程序/ json” -X GET“${endpointUrl}/ [主要|日期]”
```


响应有效负载可能会产生包含以下内容的对象的集合：



```json
[
    {
      “ principal”：“ casuser”，
      “ deviceFingerprint”：“ ...”，
      “ recordDate”：“ YYYY-MM-dd”，  
      “ expirationDate”：“ YYYY-MM-dd”， 
      “名称”：“办公室”，
      “ recordKey”：“ ...”
    }
]
```




#### 存储可信记录

存储新信任设备记录的 `POST`



```bash
卷曲-H “内容类型：应用/ JSON” -X POST -d '${json}' ${endpointUrl}
```


`POST` 数据将匹配以下块：



```json
{
    “ principal”：“ ...”，
    “ deviceFingerprint”：“ ...”，
    “ recordDate”：“ ...”，    
    “ expirationDate”：“ YYYY-MM-dd”， 
    “名称“：” ...“，
    ” recordKey“：” ...“
}
```


响应有效载荷应产生一个 `200` http状态码，以指示操作成功。
