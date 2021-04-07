---
layout: 默认
title: CAS-JSON服务注册表
category: 服务
---

# JSON服务注册表

该注册表在应用程序上下文初始化时从JSON配置文件读取服务定义。 预计将在配置的目录位置内找到JSON文件，并且此注册表将递归地浏览目录结构以查找相关的JSON文件。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-json-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

以下是一个示例JSON文件：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “testId”，
  “名称”： “testJsonFile”，
  “ID”：103935657744185，
  “evaluationOrder”：10
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-service-registry)。

<div class="alert alert-warning"><strong>集群服务</strong><p>
您必须考虑到，如果您的CAS服务器部署是集群的，则集群中的每个CAS节点必须具有与其他集群
访问权限，否则您可能必须制定一种策略来使一个节点的
到下一个。
</p></div>

JSON服务注册表也能够自动检测对指定目录的更改。 它将监视更改以识别 文件的添加，删除和更新，并将自动刷新CAS，因此更改会立即发生。

<div class="alert alert-info"><strong>转义字符</strong><p>
请确保正确转换了JSON Blob中的所有字段值，尤其是针对服务ID。 如果将服务定义为正则表达式，则某些正则表达式结构（例如“。”）。和“ \ d”必须加倍转义。
</p></div>

建议新JSON文件的命名约定如下：

```bash
JSON fileName = serviceName +“-” + serviceNumericId +“ .json”
```

基于上述公式，例如，上述JSON代码段应命名为： `testJsonFile-103935657744185.json`。 请记住，由于文件是基于 `serviceName`创建的，因此您需要确保不会将对</a>

字符用作名称的一部分。 此外，请注意，必须 **** 对包含服务定义文件的目录的完全读取/写入权限。</p>

<div class="alert alert-warning"><strong>重复服务</strong><p>
当您向目录中添加更多文件时，需要绝对确保没有两个服务定义
具有相同的ID。 如果发生这种情况，加载一个定义将停止加载另一个定义。 尽管可以任意选择服务ID
，但是请确保所有服务数字标识符都是唯一的。 如果发现重复数据，CAS也将输出警告

</p></div>

## JSON语法

CAS使用 [](http://hjson.org/) 的版本，该版本提供了更为宽松的 语法并可以指定注释。

例如，给定的JSON文件可以在CAS中这样格式化：



```json
{
  / *
    适用于希望向CAS注册以进行身份验证的https / imaps url

  * /
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^（https | imaps）：//.*”，
  “ name”：“ HTTPS和IMAPS”，
  “ID”：10000001，
}
```


请注意末尾的逗号。 有关替代语法的更多信息，请参见上面的链接。



## 传统语法

下面列出了CAS自动支持的许多旧服务定义。



## CAS附加组件

最初， [是作为CAS `3.5.x`](https://github.com/Unicon/cas-addons/wiki/Configuring-JSON-Service-Registry) 开发的，此附件以包含所有服务定义的单个文件的形式提供了JSON语法支持。 下面列出了一个示例旧版JSON文件，以供参考：



```json
{
    “服务”：[
        {
            “ID”：1，
            “服务Id”： “https://www.example.com/**”，
            “名称”： “GOOGLE”，
            “说明”： “具有蚂蚁样式模式匹配的测试服务”，
            “主题”：“ my_example_theme”，
            “ allowedToProxy”：true，
            “ enabled”：true，
            “ ssoEnabled”：true，
            “ anonymousAccess”：false，
            “ evaluationOrder “：1，
            ”allowedAttributes“：[” UID”， “邮件”]
        }
    ]
}
```


CAS可以将这一定义转换为正式支持的定义。 转换的结果将写入一个临时文件中，在该文件中会警告用户有关此旧行为的存在以及转换后的文件的位置。 更改应进行审查，并最终在注册表要加载的相关目录位置使用。

要激活对此旧语法的支持，需要将服务注册表文件重命名为 `servicesRegistry.json` 并且必须将其与所有其他JSON服务定义文件 

注意事项：

- `extraAttributes` 属性将被忽略，并且可能无法转换。
- 可以将传统语法中的服务标识符模式指定为 [ant模式](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html)。 *小方式* 自动对这些模式进行处理，以确保将它们尽可能多地转换为有效的正则表达式。 当然，您应该查看结果并进行必要的手动修改以使图案起作用。



### Jasig命名空间

CAS应该自动保持与由CAS `4.2.x` 实例 找到不赞成使用的服务定义时，警告应显示在日志 建议部署人员检查每个定义 并查阅文档以应用新语法。

下面列出了一个示例旧版JSON文件，以供参考：



```json
{
  “@class”： “org.jasig.cas.services.RegexRegisteredService”，
  “服务Id”： “^ HTTPS：//www.jasig.org/cas”，
  “名称”： “遗产”，
  “ ID “：100，
  ”说明“：”此服务定义授权遗留jasig / CAS URL。 它完全是在这里展示服务向后兼容性”，
  “proxyPolicy”：{
    “@class”： “org.jasig.cas.services.RefuseRegisteredServiceProxyPolicy”
  }，
  “evaluationOrder”：100，
  “usernameAttributeProvider”： {
    “ @class”：“ org.jasig.cas.services.DefaultRegisteredServiceUsernameProvider”
  }，
  “ logoutType”：“ BACK_CHANNEL”，
  “ attributeReleasePolicy”：{
    “ @class”：“ org.jasig.cas.services .ReturnAllowedAttributeReleasePolicy “
    ”principalAttributesRepository“：{
      ”@class“： ”org.jasig.cas.authentication.principal.cache.CachingPrincipalAttributesRepository“，
      ”TIMEUNIT“： ”HOURS“，
      ”过期“：2，
      ” mergingStrategy“：” NONE“
    }，
    ” authorizedToReleaseCredentialPassword“：false，
    ” authorizedToReleaseProxyGrantingTicket“：false
  }，
  ” accessStrategy“：{
    ” @class“：” org.jasig.cas.services.DefaultRegisteredServiceAccessStrategy“，
    “ enabled”：true，
    “ ssoEnabled”：true
  }
}
```




## 复写

如果将CAS部署在群集中，则必须对所有CAS节点保持服务定义文件同步。 请 [本指南](Configuring-Service-Replication.html) 以了解有关可用选项的更多信息。



## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)
