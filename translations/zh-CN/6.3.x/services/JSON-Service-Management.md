---
layout: 违约
title: CAS - 杰森服务注册处
category: 服务业
---

# 杰森服务注册处

此注册表在应用程序上下文初始化时间读取来自 JSON 配置文件的服务定义。 JSON 文件预计将在配置的目录位置内找到，此注册表将反复查看目录结构以查找相关的 JSON 文件。

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡斯服务器支持-杰森-服务-注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

示例 JSON 文件如下：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："测试ID"，
  "名称"："testJsonFile"，
  "id"：103935657744185，
  "评估号"：10

```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-service-registry)。

<div class="alert alert-warning"><strong>聚类服务</strong><p>
您必须考虑，如果您的 CAS 服务器部署是聚类的，则聚类中的每个 CAS 节点必须具有
访问与另一组 JSON 配置文件相同的访问权限，否则您可能需要设计一个策略来保持
更改从一个节点同步到下一个节点。
</p></div>

JSON 服务注册表还能够自动检测指定目录的更改。 它将监控更改以识别 文件添加、删除和更新，并将自动刷新 CAS，以便更改立即发生。

<div class="alert alert-info"><strong>逃避字符</strong><p>
请确保 JSON blob 中的所有字段值都正确逃脱，特别是服务 ID。 如果服务被定义为常规表达式，则需要加倍逃脱某些 regex 结构，如"" 和"\d"。
</p></div>

新 JSON 文件的命名约定建议如下：

```bash
JSON文件名=服务名+"-"+服务号码ID+"。json"
```

基于上述公式，例如上述JSON片段应命名： `测试JsonFile-103935657744185.json`。 请记住，由于文件是根据 `服务名称`创建的，因此您需要确保文件名称</a> 被视为无效的

字符不用作名称的一部分。 此外，请注意，CAS **必须** 包含服务定义文件的目录上获得完整的读/写权限。</p>

<div class="alert alert-warning"><strong>重复服务</strong><p>
当您向目录添加更多文件时，您需要绝对确保
没有两个服务定义具有相同的 ID。 如果发生这种情况，加载一个定义将停止加载另一个定义。 虽然可以任意选择服务 id
，但请确保所有服务数字标识符都是唯一的。 如果发现重复的数据，CAS 还将
输出警告。
</p></div>

## 杰森语法

CAS 使用 [JSON 语法](http://hjson.org/) 版本，该版本提供了更轻松的 语法，能够指定注释。

例如，在 CAS 中可以格式化给定的 JSON 文件：



```json
•
  /*
    适用于希望向CAS注册进行认证的https/imaps网址
    的通用服务定义。
  */
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）"，
  "名称"："HTTPS和IMAPS"，
  "id"：1000001，

```


请注意结尾的尾随逗号。 有关替代语法的更多信息，请参阅上述链接。



## 传统语法

以下列出了一些由 CAS 自动支持的旧服务定义。



## CAS 附加组件

该附加组件最初 [作为 CAS `3.5.x`的扩展](https://github.com/Unicon/cas-addons/wiki/Configuring-JSON-Service-Registry) 开发，以包含所有服务定义的单个文件的形式提供 JSON 语法支持。 下面列出了一个示例，以供参考：



```json
•
    "服务"：[
        ]
            "id"：1，
            "服务ID"："https://www.example.com/**"，
            "名称"："GOOGLE"，
            "描述"："测试服务与蚂蚁式模式匹配"，
            "主题"："my_example_theme"，
            "允许托普罗西"：真实，
            "启用"：真实，
            "允许"：真实，
            "匿名访问"：虚假，
            "评估序"：1，
            "允许归因"：["uid"，"邮件"]
        [
    ]

```


CAS 能够将此定义转换为得到官方支持的定义。 转换结果被写入一个临时文件，其中提醒用户有关此遗留行为的存在和转换文件的位置。 应审查更改，并最终在相关目录位置投入使用，由注册表加载。

要激活对此旧语法的支持，服务注册文件需要 `服务注册.json` 重命名，并且必须与所有其他 JSON 服务定义文件置于同一目录 中。

需要注意的几件事：

- `` 财产的额外属性被忽略，并且可能不会被转换。
- 旧语法中的服务标识符模式可以指定为 [蚂蚁模式](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html)。 CAS 会以 *小的方式自动按摩这些模式，在转换过程中* ，以确保它们尽可能变成有效的常规表达方式。 当然，您应该审查结果，并进行必要的手动修改，使模式正常工作。



### 贾西格命名空间

CAS 应自动与 CAS `4.2.x` 实例创建的服务定义 保持向后兼容。 当发现此类废弃的服务定义时，警告应显示 日志中。 建议部署人员 审查每个定义，并咨询文档以应用新语法。

下面列出了一个示例，以供参考：



```json
•
  "@class"："org.jasig.cas.services.服务.注册服务"，
  "服务ID"："^https://www.jasig.org/cas"，
  "名称"："遗产"，
  "id"：100，
  "描述"："此服务定义授权遗留的 jasig/cas URL。 It is solely here to demonstrate service backwards-compatibility",
  "proxyPolicy" : {
    "@class" : "org.jasig.cas.services.RefuseRegisteredServiceProxyPolicy"
  },
  "evaluationOrder" : 100,
  "usernameAttributeProvider" : {
    "@class" : "org.jasig.cas.services.DefaultRegisteredServiceUsernameProvider"
  },
  "logoutType" : "BACK_CHANNEL",
  "attributeReleasePolicy" : {
    "@class" : "org.jasig.cas.services.ReturnAllowedAttributeReleasePolicy",
    "principalAttributesRepository" : {
      "@class" : "org.jasig.cas.authentication.principal.cache.CachingPrincipalAttributesRepository",
      "timeUnit" : "HOURS",
      "expiration" : 2,
      "mergingStrategy" : "NONE"
    },
    "authorizedToReleaseCredentialPassword" : false,
    "authorizedToReleaseProxyGrantingTicket" : false
  },
  "accessStrategy" : {
    "@class" : "org.jasig.cas.services.DefaultRegisteredServiceAccessStrategy",
    "enabled" : true,
    "ssoEnabled" : true
  }
}
```




## 复制

如果 CAS 部署在聚类中，则所有 CAS 节点必须保持服务定义文件同步。 请 [本指南](Configuring-Service-Replication.html) 查看，以了解有关可用选项的更多信息。



## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>
