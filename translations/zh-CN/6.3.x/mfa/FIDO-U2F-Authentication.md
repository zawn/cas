---
layout: 违约
title: CAS - U2F - FIDO 通用第二因子认证
category: 多因素认证
---

# U2F - FIDO 通用认证

U2F 是一个开放的身份验证标准，使互联网用户能够安全地访问任意数量的在线服务，只需一台设备，立即 ，无需驱动程序或客户端软件。 CAS U2F 的实施建立在 [尤比科](https://www.yubico.com/about/background/fido/) 之上， 技术规格由名为" [FIDO 联盟](https://fidoalliance.org/)的开放认证行业联盟主办。

请注意，今天并非所有浏览器都支持 U2F。 虽然最近版本的 Chrome 和 Opera 的支持似乎存在，但您应该始终验证 U2F 支持是否可用于目标浏览器。

支持通过在 WAR 叠加中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡服务器支持-u2f</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f)。

## 注册

U2F 设备注册流自动烘烤到 CAS 中。 还自动安排背景 *清洁* 过程，定期扫描 存储库，并根据配置的参数删除过期的设备注册记录。 在默认设置中，U2F 设备 在用户注册 U2F 令牌后在固定期限后过期（独立于上次使用令牌）：如果您将 U2F MFA 部署到集中分发和撤销代币的设置中，您可能需要 [延长间隔](../configuration/Configuration-Properties.html#fido-u2f)。

<div class="alert alert-warning"><strong>更清洁的使用</strong><p>在分组的 CAS 部署中，最好仅在 CAS 节点的指定 
上保持清洁器运行，并通过 CAS 设置将其关闭。 在所有节点上保持清洁运行可能导致严重的性能和锁定问题。</p></div>

### 行政终点

CAS 提供以下端点：

| 端点      | 描述                                                                                                                                                                                  |
| ------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `u2f设备` | `获取` 请求提供了注册设备的集合。 单个用户的注册设备可以通过 `` 使用选择器路径（即 `u2f 设备/{username}`）。 单个用户的注册设备可以通过使用选择器路径删除 `删除` （即 `u2f 设备/{username}`）。 单个用户的单个注册设备可以通过 `使用选择器路径删除删除` （即 `u2f设备/{username}/{id}`）。 |

### 违约

默认情况下，将包括一个存储库实施，用于收集用户设备注册并将其保存到内存中。 此选项仅应用于演示和测试目的。

### 杰森

收集用户设备注册并将其保存到 JSON 文件的设备存储库实施，该文件的路径通过设置传授给 CAS。 这是一个非常温和的选择，应该主要用于演示和测试的目的。 不用说，此 JSON 资源充当数据库，必须提供给群集中的所有 CAS 服务器节点。

存储到 JSON 文件中的设备采用以下格式：

```json
•
  "@class"："java.利用。哈希马普"，
  "设备"： [ java. util. Arraylist"， [
    "@class"： "org. apereo. cas. 适配器. u2f. 存储. U2f 设备注册"，
    "id"： 1508515100762，
    "用户名"："卡苏瑟"，
    "记录"："\"钥匙手"："钥匙处理11\"，"公共钥匙"\"公共钥匙1\" \"反"：1，"妥协"："虚假"，
    "创建日"："2016-10-15"
  []]]]
}
```

### 槽的

设备注册可以通过外部 Groovy 脚本进行管理。 脚本的设计如下：

```groovy
导入 java. util.*
进口组织. apereo. cas. 适配器. u2f. 存储. *

地图<String, List<U2FDeviceRegistration>> 读取 （最终对象...阿格斯）{
    除伐木机=阿格斯[0]
    ...
    空
}

布尔写（最终对象。。。args）{
    列表<U2FDeviceRegistration> 列表=args[0]
    除伐木机=args[1]
    ...
    真正的
}

无效删除所有（最终对象。。。args）{
    def记录器=args[0]
=          

去除（对象[]args）{
    def设备=args[0]
    除号器=args[1]
}
```

### 日本经济与经济、经济、经济

可以通过在 WAR 叠加中包含以下模块，将设备注册保存在关系数据库中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - u2f - jpa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-jpa)。

### 蒙古德布

可以通过在 WAR 叠加中包含以下模块来将设备注册保存在 MongoDb 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - u2f - 蒙古</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-mongodb)。

### 迪纳莫德布

可以通过在 WAR 叠加中包含以下模块来将设备注册保存在 DynamoDb 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - u2f - 发电机</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-dynamodb)。

### 雷迪斯

可以通过在 WAR 叠加中包括以下模块来将设备注册保存在 Redis 实例中：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - u2f - 雷迪斯</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-redis)。

### 库奇德布

设备注册可保留在 CouchDb 实例中，在 WAR 叠加中包含以下模块：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - u2f - 沙发</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-couchdb)。

### 休息

设备注册可以通过RES ABI进行管理。 端点必须设计为接受/处理 `申请/json`。 他收集来回传递的设备的语法在 JSON 中设计，与上面定义的 JSON 结构相同。

传递以下参数：

| 操作   | 参数               | 描述                | 结果                             |
| ---- | ---------------- | ----------------- | ------------------------------ |
| `获取` | 不适用              | 检索所有注册设备。         | `200` 状态代码 收集注册设备作为JSON在响应的主体。 |
| `发布` | 注册设备集合为 JSON     | 存储已注册设备。          | `200`。                         |
| `删除` | 不适用              | 删除所有设备记录          | `200`。                         |
| `删除` | `/${id}`  作为路径变量 | 删除与该标识符匹配的所有设备记录。 | `200`。                         |

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#fido-u2f-rest)。
