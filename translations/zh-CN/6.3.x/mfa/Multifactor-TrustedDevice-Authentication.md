---
layout: 违约
title: CAS - 受信任的设备多因子认证
category: 多因素认证
---

# 多因素身份验证受信任的设备/浏览器

除了 CAS [MFA 功能](Configuring-Multifactor-Authentication.html) 提供的触发器外，您还希望让用户决定当前的浏览器/设备是否值得信任，从而跳过后续的 MFA 请求，从而可能存在 案例。 目标是让中科院记住这一决定，在决定 被强行撤销或被认为过期之前，不要用 MFA 打扰用户。

在 MFA 工作流期间信任设备将意味着记住最终决定，因为该 **用户** 该 **位置** 该 **设备**。 这些密钥安全地组合在一起并分配给最终决策。

在部署之前，您应该考虑以下事项：

- 是否应该允许用户选择性地授权"当前"设备？
- ...或者必须自动发生，一旦MFA开始？
- 如何记住用户的决策和选择？ 它们存放在哪里？
- CAS 应该信任用户决策多长时间？
- 受信任的身份验证会话如何传达回应用程序？

请注意，默认情况下启用此功能意味着，如果您打开多个 MFA 提供商，则该功能将在全球范围内应用于所有应用。 这可以是可选禁用的，并且仅适用于选定的一组提供商。

## 配置

支持通过以下模块提供：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-信任-mfa</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 行政终点

CAS 提供以下端点：

| 端点         | 描述                                                                                                                                                                                                              |
| ---------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `多因素信任的装饰` | 暴露设备目前 [注册和信任的](Multifactor-TrustedDevice-Authentication.html) 由CAS多因素认证引擎。 `获取` 操作会生成所有受信任设备的列表。 将 URL 中的用户名指定为占位符/选择器将获取为该用户注册的设备（即 `多因素信托设备/{username}`）。 使用设备密钥 ID 的 `删除` 操作将尝试删除受信任的设备（即 `多因素信任的设备/{id}`）。 |

## 设置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-trusted-devicebrowser)。

## 身份验证上下文

如果由于受信任的身份验证决定而绕过 MFA 请求，则应用程序将收到一个特殊属性，作为 表示此行为的验证有效载荷的一部分。 申请必须进一步说明他们要求 MFA 模式，但鉴于身份验证会话值得信任且 MFA 被绕过，因此在回复中未收到确认。

## 设备指纹

为了区分相互信任的设备，我们需要计算一个设备指纹，该指纹 识别单个设备。 此设备指纹的计算可以利用请求 多个组件的组合。

设备指纹可以使用以下方式计算：

- 客户端 IP 地址
- 随机生成的曲奇加上客户端 IP（默认）
- [地理位置地址](../installation/GeoTracking-Authentication-Requests.html)。 您确实需要确保 CAS 允许 [询问和处理浏览器提供的地理数据](../installation/Configuring-Authentication-Events.html) 。
- 用户代理标题

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#trusted-device-fingerprint)。

## 旁路

作为 MFA 工作流程的一部分，允许用户选择不向 CAS 注册受信任的设备。 此外，每个应用程序可以绕过 值得信赖的 MFA 设备工作流程：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）://app.example.org"
  "名称"： "示例"，
  "id"： 1，
  "多因素政策"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务多因素政策"，
    "可绕过信任" ： 真正的
  =
}

```
## 存储

用户决策必须在以后的后续请求中记住并处理。  还自动安排背景 *清洁* 过程， 定期扫描所选存储库/数据库/注册表，并根据配置的阈值参数删除过期的记录。

<div class="alert alert-warning"><strong>更清洁的使用</strong><p>在分组的 CAS 部署中，最好仅在指定的 CAS 
节点上保持清洁器运行，并通过 CAS 设置将其关闭。 在所有节点上保持清洁运行可能导致严重的性能和锁定问题。</p></div>

### 违约

如果您无所作为，默认情况下，记录将保存在运行时间内存中，并缓存可配置的时间。 如果您有一个非常小的部署与一个小的用户群，或者如果你想演示的功能，这是最有用的。

### 杰森

记录可以保存在静态 json 资源中，其路径通过 CAS 设置定义。 如果您有一个非常小的部署与一个小的用户群，或者如果你想演示的功能，这也是最有用的。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-storage)。

### 京城

用户决策也可能保留在您自己选择的常规 RDBMS 中。

支持通过以下模块提供：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-信任-mfa-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序， [请参阅本指南](../installation/JDBC-Drivers.html)。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jdbc-storage)。

### 库奇德布

用户决策也可能保留在沙发数据库实例中。

支持通过以下模块提供：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持信任 - mfa - 沙发</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-storage)。

### 蒙古德布

用户决策也可能保留在 MongoDb 实例中。

支持通过以下模块提供：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持信任 - mfa - mongo</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-storage)。

### 迪纳莫德布

用户决策也可能保留在 DynamoDb 实例中。

支持通过以下模块提供：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持信任 - mfa - dynamodb</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-storage)。

### 休息

如果您希望完全委托用户决策的管理、验证和持久性，您可以设计一个 REST API CAS 应联系该以验证用户决策并记住这些决策供以后使用。

支持通过以下模块提供：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-信任-mfa-休息</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-storage)。

#### 检索可信记录

`获取` 请求，返回所有有效且未过期的可信身份验证记录。

```bash
卷曲-i-H"接受：应用程序/json"-H"内容类型：应用程序/json"-X获取"${endpointUrl}/[校长|日""
```

响应有效载荷可能会生成包含：

```json
•
    •
      "校长"："卡苏瑟"，
      "设备手指打印"："。。。。。。"，
      "记录日期"："YY-MM-dd"，  
      "过期日期"："YY-MM-dd"， 
      "名称"："办公室"，
      "记录键"："。。。。。。"
    [
]
```

#### 存储受信任的记录

`邮政` 存储新信任的设备记录的请求。

```bash
卷曲-H"内容类型：应用程序/json"-X POST-d"${json}" ${endpointUrl}
```

`邮政` 数据将匹配以下块：

```json
\
    "校长"："。。。"，
    "设备指纹"："。。。"，
    "记录日期"："。。。"，    
    "过期日期"："YY-MM-dd"， 
    "名称"："。。。"，
    "记录键"："。。。"
}
```

响应有效载荷应生成 `200` http 状态代码，以指示操作成功。
