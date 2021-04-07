---
layout: 默认
title: CAS-SAML2属性发布
category: 属性
---

# SAML2属性发布

每个SAML服务都定义了属性过滤和发布策略。 有关更多信息，请参见 [本指南](../integration/Attribute-Release-Policies.html)

## 属性值类型

默认情况下，在最终SAML2响应中创建的属性值块在编码的XML中不携带任何类型信息。 如果有必要，可以根据SAML2服务提供者的要求对属性值强制使用特定类型。 用特定类型信息编码的属性的示例为：

```xml
<saml2:Attribute FriendlyName="givenName" Name="givenName" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
    <saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">HelloWorld</saml2:AttributeValue>
</saml2:Attribute>
```

支持以下属性值类型：

| 类型               | 描述                             |
| ---------------- | ------------------------------ |
| `XS字符串`          | 将属性值类型标记为 `字符串`。               |
| `XSURI`          | 将属性值类型标记为 `uri`。               |
| `XSBoolean`      | 将属性值类型标记为 `布尔`。                |
| `XSInteger`      | 将属性值类型标记为 `整数`。                |
| `XSDateTime`     | 将属性值类型标记为 `datetime`。          |
| `XSBase64Binary` | 将属性值类型标记为 `base64Binary`。      |
| `XS对象`           | 跳过属性值类型，并将值序列化为复杂的XML对象/ POJO。 |

...每个属性的类型应定义如下：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAML服务“
  ”metadataLocation“：” ../../sp-metadata.xml “
  ”ID“：1，
  ”attributeValueTypes“：{
    ”@class“： ”的java.util.HashMap“，
    ”<attribute-name>“：”<attribute-value-type>“
  }
}
```

## 属性名称格式

可以在服务注册表中为每个依赖方指定属性名称格式。

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAML服务“
  ”metadataLocation“：” ../../sp-metadata.xml “
  ”ID“：100001，
  ”attributeNameFormats“：{
    ”@class“： ”的java.util.HashMap“，
    ” attributeName“：”基本| uri |未指定|自定义格式等“
  }
}
```

您还可以选择通过CAS属性全局定义属性及其相关名称格式 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-idp)。

## 属性友好名称

可以在服务注册中心为每个依赖方指定属性友好名称，也可以通过CAS设置在全局范围内指定属性名称。 如果没有为属性定义友好名称，则将使用 属性名称代替它。 请注意，该属性的名称是设计用于发布给服务提供商的名称，特别是如果原始属性为 *映射为* 到另一个名称

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAML服务“
  ”metadataLocation“：” ../../sp-metadata.xml “
  ”ID“：100001，
  ”attributeFriendlyNames“：{
    ”@class“： ”的java.util.HashMap“，
    ” urn：oid：2.5.4.42“：”要使用的友好名称“
  }
}
```


## 不常见的研究和奖学金

可以使用特定的属性释放策略 `http://id.incommon.org/category/research-and-scholarship`释放InCommon Research和Scholarship服务提供商所需 [属性束](https://spaces.internet2.edu/display/InCFederation/Research+and+Scholarship+Attribute+Bundle) ：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ incommon / metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.services.ChainingAttributeReleasePolicy”，
    “政策“：[” java.util.ArrayList“，
      [
         {” @class“：” org.apereo.cas.support.saml.services.InCommonRSAttributeReleasePolicy“}
      ]
    ]
  }
}
```

属性授权释放被设定为 `eduPersonPrincipalName`， `eduPersonTargetedID`， `电子邮件`， `的displayName`， `给定名称`， `姓`， `eduPersonScopedAffiliation`。

## REFEDS研究与奖学金

可以使用特定的属性释放策略来释放REFEDS研究和奖学金服务提供商使用实体属性值 `http://refeds.org/category/research-and-scholarship`[属性束](https://refeds.org/category/research-and-scholarship) ：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ incommon / metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.services.ChainingAttributeReleasePolicy”，
    “政策“：[” java.util.ArrayList“，
      [
         {” @class“：” org.apereo.cas.support.saml.services.RefedsRSAttributeReleasePolicy“}
      ]
    ]
  }
}
```

此策略是对 `InCommonRSAttributeReleasePolicy` 的扩展，它基于不同的实体属性值进行操作。

## 发布 `eduPersonTargetedID`

如果没有要在发布前获取 `eduPersonTargetedID` 可以让CAS使用以下策略在发布时动态地 `eduPersonTargetedID`

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.support.saml.services.EduPersonTargetedIdAttributeReleasePolicy”，
    “ salt”：“ OqmG80fEKBQt”，
    “ attribute”：“”
  }
}
```

所生成的id可以基于现有的主体属性。 如果未指定或找不到属性， 身份验证的主体ID。

## Groovy脚本

该策略允许Groovy脚本计算已发布属性的集合。

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ incommon / metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.support.saml.services.GroovySamlRegisteredServiceAttributeReleasePolicy” ，
    “ groovyScript”：“文件：/etc/cas/config/script.groovy”
  }
}
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。

脚本的大纲可以设计为：

```groovy
import java.util。*
import org.apereo.cas.support.saml.services。*
import org.apereo.cas.support.saml。*

def Map<String, Object> run（final Object ... args）{
    def属性= args[0]
    def服务= args[1]
    def解析器= args[2]
    def外观= args[3]
    def实体描述符= args[4]
    def applicationContext = args[5]
    def logger = args[6]
...
    返回null；
}
```

以下参数传递到脚本：

| 范围                   | 描述                                      |
| -------------------- | --------------------------------------- |
| `属性`                 | 当前属性的映射已解决，可以发布。                        |
| `服务`                 | 服务注册表中匹配的SAML服务定义。                      |
| `解析器`                | 此服务提供者的元数据解析器实例。                        |
| `正面`                 | 元数据解析器顶部的包装，允许访问实用程序功能。                 |
| `实体描述符`              | `EntityDescriptor` 对象已匹配并链接到该服务提供商的元数据。 |
| `applicationContext` | CAS应用程序上下文允许直接访问bean等                   |
| `记录器`                | 负责发布日志消息的对象，例如 `logger.info（...）`。      |

示例脚本如下：

```groovy
import java.util。*
import org.apereo.cas.support.saml.services。*
import org.apereo.cas.support.saml。*

def Map<String, Object> run（final Object ... args）{
    def属性= args[0]
    def服务= args[1]
    def解析器= args[2]
    def外观= args[3]
    def entityDescriptor = args[4]
    def applicationContext = args[5]
    def logger = args[6]

    if（entityDescriptor.entityId ==“ TestingSAMLApplication “）{
      返回[用户名：[”某物“]，另一个：”属性“]
    }
    返回[：]
}
```

## 模式匹配实体ID

如果定义的聚合包含多个实体ID，则以下属性释放策略可用于将允许的属性的集合释放给按正则表达式模式分组在一起的实体ID：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ incommon / metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.support.saml.services.PatternMatchingEntityIdAttributeReleasePolicy” ，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ cn”，“ mail”，“ sn”]]，
    “ fullMatch”：“ true”，
    “ reverseMatch”：“ false”，
    “ entityIds“：” entityId1 | entityId2 |某处。+“
  }
}
```

## 实体属性过滤器

如果服务提供者随附的元数据包含与某些值匹配的属性，则此属性释放策略将授权释放已定义的属性。

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.support.saml.services.MetadataEntityAttributesAttributeReleasePolicy”，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ cn”，“ mail”，“ sn”]]，
    “ entityAttributeValues”：[“ java.util.LinkedHashSet”，[“实体属性值” ]，
    “entityAttribute”： “http://somewhere.org/category-x”，
    “entityAttributeFormat”： “瓮：绿洲：名称：TC：SAML：2.0：attrname格式：未指定”
  }
}
```

`实体属性格式` 的规范是可选的。

## 请求的属性过滤器

`AttributeConsumingService` 元素的一部分的服务提供商的随附元数据，授权释放已定义的属性。

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ entity-ids-allowed-via-regex”，
  “ name”：“ SAML”，
  “ID”：10，
  “metadataLocation”： “路径/到/ metadata.xml中”，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.support.saml.services.MetadataRequestedAttributesAttributeReleasePolicy”，
    “ useFriendlyName”：假
  }
}
```

`useFriendlyName` 允许过滤器将请求的属性的友好名称与已解析的属性进行比较。

### SAML IdP属性定义

可以将SAML属性定义为 [属性定义存储](../integration/Attribute-Definitions.html)。 `SamlIdPAtrributeDefinition` `DefaultAttributeDefinition` 形式的所有属性，并添加了两个特定于 SAML属性的可选属性。  使用此定义定义属性不会阻止其被其他协议释放。

```json
{
  “ @class”：“ java.util.TreeMap”，
  “ eduPersonPrincipalName”：{
    “ @class”：“ org.apereo.cas.support.saml.web.idp.profile.builders.attr.SamlIdPAttributeDefinition” ，
    “ key”：“ eduPersonPrincipalName”，
    “ name”：“ eduPersonPrincipalName”，
    “ urn”：“ urn：oid：1.3.6.1.4.1.5923.1.1.1.6”，
    “ scoped”：true，
    “ encrypted”：假，
    “ attribute”：“ uid”，
    “ friendlyName”：“ eduPersonPrincipalName”
  }
}
```

可以为Saml IdP属性定义指定以下附加设置：

| 姓名     | 描述                                                   |
| ------ | ---------------------------------------------------- |
| `友好名称` | （可选）在属性发布期间与目标应用程序共享的属性的友好名称。                        |
| `瓮`    | （可选）为属性定义的通用资源名称（即urn：oid：1.3.6.1.4.1.5923.1.1.1.6）。 |
