---
layout: 违约
title: CAS - 属性释放同意书
category: 属性
---

# 属性同意

CAS 提供在属性发布时强制执行用户知情同意的能力。 实际上，这意味着在访问目标应用程序之前， 用户将收到允许发布到应用程序的属性集合，其中选项可以继续或拒绝发布该属性。 还有其他选项，说明同意引擎应如何考虑属性释放策略中的潜在更改。 如果属性释放策略中未检测到任何更改，用户还可以设置提醒。

存储在配置存储库中的同意属性记录已签名并加密。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡斯服务器支持-同意-网络流</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 行政终点

CAS 提供以下端点：

| 端点     | 描述                                                                                                                               |
| ------ | -------------------------------------------------------------------------------------------------------------------------------- |
| `属性一致` | 管理和控制 [属性同意决定](Attribute-Release-Consent.html)。 `获取` 操作会生成所有同意决定的列表。 带有记录密钥 ID 的 `删除` 操作将尝试删除和撤销注册设备（即 `属性一致/{principal}/{id}`）。 |


## 属性选择

默认情况下，标记为释放的所有属性都有资格获得同意。 为了控制此过程，您可以定义一个同意策略，该政策指示执行属性选择以进行同意的标准。

分配给每个服务的策略包括以下功能：

| 田       | 描述                           |
| ------- | ---------------------------- |
| `排除属性`  | 将指示的属性排除在同意之外。               |
| `包括仅属性` | 强制包括同意中指示的属性，前提是属性已解决。       |
| `地位`    | 控制是否应激活此服务的同意。 有关激活规则，请参阅下文。 |

示例定义如下：

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："样本"，
  "名称"："样本"，
  "id"：100，
  "描述"："样本"，
  "属性释放政策"：\
    "@class"："org.apereo.cas.服务。返回所有发布政策"，
    "同意政策"： [
      "@class"： "org. apereo. cas. 服务. 同意. 默认注册服务礼貌"，
      "排除属性"： ["java. util. Linkedhash" 设置， ["测试"]，
      "仅包括属性"： [java. util. linkedhashset"， ["测试"]，
      "状态"： "错误"
    [
  ]
]
```

## 激活

属性同意激活可以在全球和每项服务级别进行控制。 默认情况下，打开 属性同意的全球激活规则，并禁用服务定义的同意政策规则。 服务定义的同意政策可以采用接受以下值的 `状态` 字段来推翻全球规则：

| 价值   | 描述                   |
| ---- | -------------------- |
| `假`  | 同意政策被禁用，覆盖全球配置。      |
| `真`  | 已启用同意策略，覆盖全球配置。      |
| `定义` | 同意政策未定义，将决策权下放给全球配置。 |

请注意，属性同意策略也可能被链接在一起以组成多个策略。 每个策略都可以单独禁用或启用，整个属性同意策略的总体聚合状态 将用于确定属性同意激活和选择。 属性同意政策样本链如下：

```json
{
  "@class" : "org.apereo.cas.services.RegexRegisteredService",
  "serviceId" : "sample",
  "name" : "sample",
  "id" : 100,
  "description" : "sample",
  "attributeReleasePolicy": {
    "@class": "org.apereo.cas.services.ChainingAttributeReleasePolicy",
    "policies": [ "java.util.ArrayList",
      [
        {
          "@class" : "org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy",
          "allowedAttributes" : [ "java.util.ArrayList", [ "cn", "mail", "sn" ] ],
          "consentPolicy": {
            "@class": "org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy",
            "includeOnlyAttributes": ["java.util.LinkedHashSet", ["cn"]],
            "status": "TRUE"
          }
        },
        {
          "@class" : "org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy",
          "allowedAttributes" : [ "java.util.ArrayList", [ "displayName" ] ],
          "consentPolicy": {
            "@class": "org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy",
            "includeOnlyAttributes": ["java.util.LinkedHashSet", ["displayName"]],
            "status": "FALSE"
          }
        }
      ]
    ]
  }
}
```

### 通过格罗夫激活

默认同意激活策略可以替换为外部 Groovy 脚本，以确定请求 是否有资格获得同意。 脚本路径是通过 CAS 配置属性定义的。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#attribute-consent)。

脚本本身可以这样设计：

```groovy
导入组织.apereo.cas.使用.model.模型.三州布尔

def运行（对象[]args）{
    def同意工程师=args[0]
    d def casProperties=args[1]
    def服务=args[2]
    def注册服务=args[3]
    def身份验证=args[4]
    def请求通写=args[5]
    def记录器=args[6]

    记录器。debug（"激活 ${registeredService.name}同意"）
    返回真实：
}
```

以下参数传递到脚本：

| 参数        | 描述                                |
| --------- | --------------------------------- |
| `同意工程师`   | 引用 `同意引擎` 对象。                     |
| `卡斯普罗佩里斯` | 从属性源加载的 CAS 配置属性的引用。              |
| `服务`      | `服务` 代表请求申请的对象。                   |
| `注册服务`    | 注册服务 `` 代表注册表中服务定义的对象。            |
| `认证`      | `身份验证` 表示活动身份验证交易的对象。             |
| `请求康德信`   | 代表春季网络流的对象 `请求信`。                 |
| `记录`      | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |

脚本预计将返回 `真实` 或 `虚假` ，以确定是否需要同意。

## 存储

用户同意决定可以使用以下选项之一进行存储和记忆。

### 杰森

这是默认选项，最有用的演示和测试目的。 同意决定都保存 在静态 JSON 资源中，该资源通过设置向 CAS 传授路径。

示例记录如下：

```json
{
   "id"：1000，
   "校长"："卡瑟"，
   "服务"："https://google.com"，
   "创建日期"：[2017，7，10，14，10，17]，
   "选项"："ATTRIBUTE_NAME"，
   "提醒"：14，
   "提醒时间"："天"，
   "属性"："。。。"
}
```

可用以下字段：

| 田        | 描述                                                              |
| -------- | --------------------------------------------------------------- |
| `ID`     | 现有记录的有效数字值。                                                     |
| `主要`     | 已验证的用户ID。                                                       |
| `服务`     | 目标应用程序网址的属性即将发布。                                                |
| `创建日`    | 决策记录的日期/时间。                                                     |
| `选项`     | 指示如何为此应用程序确定属性更改。 （即 `ATTRIBUTE_NAME`， `ATTRIBUTE_VALUE`， `永远`） |
| `提醒`     | 指示提醒用户再次同意的期限，以防发现任何更改。                                         |
| `提醒时间统一` | 提醒时间单位（即 `个月`， `天`， `小时`等）。                                     |
| `属性`     | 此应用程序的属性名称 Base64，已签名并加密。                                       |

</code> `选项的有效值包括：</p>

<table spaces-before="0">
<thead>
<tr>
  <th>田</th>
  <th>描述</th>
</tr>
</thead>
<tbody>
<tr>
  <td><code>ATTRIBUTE_NAME`</td> 

</tr> 

</tbody> </table>

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-attribute-consent)。

### 槽的

同意操作可以通过 Groovy 脚本处理，该脚本的路径通过设置传授给 CAS。

脚本可设计为：

```groovy
导入 java. util.*
进口组织. apereo. cas. 同意. *

设置<ConsentDecision> 读取 （最终对象...args）{
    def同意决定=args[0]
    定义记录器=args[1]
    。。。
    返回无效;
=

德布尔写（最终对象。。。args）{
    def同意决定=args[0]
    def记录器=args[1]
    。。。
    返回真实：
=

德布尔删除（最终对象。。。args）{
    定义决策ID=阿格斯[0]
    德伐木机=args[1]
    ...
    返回真实：
=

德布尔删除所有（最终对象。。。args）{
    def本金=阿格斯[0]
    德伐木机=阿格斯[1]
    ...
    返回真实：
}
```


### 京城

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-同意-jdbc</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jpa-attribute-consent)。

### 蒙古德布

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 同意 - 蒙古</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-attribute-consent)。

### 雷迪斯

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 同意 - 重新</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-attribute-consent)。

### 库奇德布

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 同意 - 沙发</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-attribute-consent)。


### 休息

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡斯服务器支持-同意-休息</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-attribute-consent)。

端点必须设计为接受/处理 `申请/json`。

| 操作        | 方法   | 数据                          | 预期响应                 |
| --------- | ---- | --------------------------- | -------------------- |
| 查找同意决定    | `获取` | `服务`， `主要` 为标题              | `200`。 同意决定对象在主体中。   |
| 为用户查找同意决定 | `获取` | `校长` 作为标题                   | `200`。 同意决定在主体中提出异议。 |
| 查找所有同意决定  | `获取` | 不适用                         | `200`。 同意决定在主体中提出异议。 |
| 商店同意决定    | `发布` | 体中的同意决定对象                   | `200`。               |
| 删除同意决定    | `删除` | `/<decisionId>` 附加到网址 | `200`。               |
| 删除同意决定    | `删除` | `校长` 作为标题                   | `200`。               |

过境过程中的同意决定对象将且必须与上述 JSON 结构匹配。


### 阿尔达普

同意决定可以存储在LDAP用户对象上。 这些决策序列化为 JSON，并逐一存储在多值字符串属性中。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 同意 - ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-attribute-consent)。


### 习惯

您也可以将自己的属性同意管理实施注入 CAS，该 CAS 本身将处理存储同意决定等。 为此，您需要设计大致匹配以下配置类：

```java
包组织. 阿佩雷奥. 卡斯. 同意;

@Configuration（"我的配置"）
@EnableConfigurationProperties（cas配置专业.class）
公共类"我的配置"=

    @Bean
    公众同意存储库（）{
        。。。
    •
}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。
