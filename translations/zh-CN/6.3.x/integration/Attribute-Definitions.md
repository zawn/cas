---
layout: 默认
title: CAS-属性定义
category: 属性
---

# 属性定义

当从身份验证或属性存储库源中获取并解析CAS中的属性时，该属性的定义通常定义为 并使用其名称进行引用，而无需任何其他 *元数据* 或修饰。 例如，您可能希望检索 `uid` 属性，实际上 重命名并将其映射到全局或特定应用程序集成 `userIdentifier` 对于大多数使用情况，此配置 相当舒适，但是，取决于目标应用程序的性质和用于完成集成的身份验证协议， 附加要求，并且可能必须指定1个附加要求才能定义带有附加属性的属性 依赖方共享和释放的指针。 例如，SAML2服务提供商可能要求 `eduPersonPrincipalName` 的范围 ** 属性，该2 eduPersonPrincipalName 3的值 始终由 `uid` 属性确定，无论目标应用程序如何，该特殊友好名称始终提供。

全局定义或在服务定义中定义有关给定属性的一点点元数据，但是属性定义存储库允许人们使用必要的 ，并在属性解析和释放。 属性定义存储库的规范完全是 可选的，并且该存储库可能不包含任何属性定义。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#attribute-definitions)。

## JSON属性定义

可以在通过CAS设置提供位置的JSON文件中定义属性定义。 JSON文件 的结构可能与以下内容匹配：

```json 
{
    “ @class”：“ java.util.TreeMap”，
    “ employeeId”：{
      “ @class”：“ org.apereo.cas.authentication.attribute.DefaultAttributeDefinition”，
      “ key”：“ employeeId”，
      “作用域”：true，
      “属性”：“ empl_identifier”
    }
}
```

`映射` 来指定属性定义，该映射的键是属性名称，由CAS [属性解析引擎](Attribute-Resolution.html)解析。 `映射` 的键的属性名称必须与属性定义本身 `键`

可以通过属性定义指定以下设置：

| 姓名              | 描述                                                  |
| --------------- | --------------------------------------------------- |
| `钥匙`            | 属性名称，由CAS [属性解析引擎](Attribute-Resolution.html)       |
| `名称`            | 在属性发布期间要与目标应用程序使用并共享的属性名称。                          |
| `范围`            | （可选）如果 `true`，则属性值的范围应为设置中定义的CAS服务器部署的范围。           |
| `加密的`           | （可选）如果 `true`，则将使用服务定义的已定义公共密钥在base-64中对属性值进行加密和编码。 |
| `属性`            | （可选）源属性，用于为属性定义本身提供值，替换原始源的值。                       |
| `patternFormat` | （可选）在 `java.text.MessageFormat` 使用的模板来装饰属性值。        |
| `脚本`            | （可选）外部或嵌入式的Groovy脚本，用于处理和产生属性值。                     |

如果属性定义要产生值，则应按照给定的顺序执行以下操作：

- `属性` 设置（如果有）生成属性值。
- `脚本` 设置（如果有）生成属性值。
- `作用域` 设置（如果有）生成属性值。
- `patternFormat` 设置（如果有）生成属性值。
- `加密` 设置（如果有）生成属性值。

## 例子

### 基本的

`员工ID` 定义一个属性定义，以基于另一个属性 `` 作为源来产生作用域属性

```json 
{
    “ @class”：“ java.util.TreeMap”，
    “ employeeId”：{
      “ @class”：“ org.apereo.cas.authentication.attribute.DefaultAttributeDefinition”，
      “ key”：“ employeeId”，
      “作用域”：true，
      “属性”：“ empl_identifier”
    }
}
```

现在，定义全球可用，属性 [然后可以释放](Attribute-Release-Policies.html) 照常使用以下定义：

```json
...
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ employeeId”]]
  }
...
```

### 加密属性

与上述相同的用例，不同之处在于，将使用服务定义的公共密钥对属性值进行加密和编码：

```json 
{
    “ @class”：“ java.util.TreeMap”，
    “ employeeId”：{
      “ @class”：“ org.apereo.cas.authentication.attribute.DefaultAttributeDefinition”，
      “ key”：“ employeeId”，
      “ encrypted”：true，
      “ attribute”：“ empl_identifier”
    }
}
```

服务定义应该已经指定了公钥定义：

```json
...
  “ publicKey”：{
    “ @class”：“ org.apereo.cas.services.RegisteredServicePublicKeyImpl”，
    “ location”：“ classpath：public.key”，
    “ algorithm”：“ RSA”
  }
...
```

密钥可以通过以下命令生成：

```bash
openssl genrsa -out private.key 1024
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outder DER -nocrypt -in private.key -out private .p8
```

### 模式格式

定义属性定义以基于模式格式产生值：

```json 
{
    “ @class”：“ java.util.TreeMap”，
    “ eduPersonPrincipalName”：{
      “ @class”：“ org.apereo.cas.authentication.attribute.DefaultAttributeDefinition”，
      “ key”：“ eduPersonPrincipalName”，
      “ name”：“ urn：oid：1.3.6.1.4.1.5923.1.1.1.6”，
      “ friendlyName”：“ eduPersonPrincipalName”，
      “ scoped”：true，
      “ patternFormat”：“ hello，{0}”，
      “ attribute”：“ uid”
    }
}
```

如果解析的属性集为 `uid = [test1，test2]` ，并且CAS服务器的范围为 `example.org`，则 的最终值 `eduPersonPrincipalName` 将为[`hello，test1 @ example.org`，`hello，test2 @ example.org`] 发布为 `urn：oid：1.3.6.1.4.1.5923.1.1.1.6` ，其友好名称为 `eduPersonPrincipalName`。

### 嵌入式脚本

与上述相同的用例，除了属性值由嵌入式Groovy脚本额外处理

```json 
{
    “ @class”：“ java.util.TreeMap”，
    “ eduPersonPrincipalName”：{
      “ @class”：“ org.apereo.cas.authentication.attribute.DefaultAttributeDefinition”，
      “ key”：“ eduPersonPrincipalName”，
      “ name”：“ urn：oid：1.3.6.1.4.1.5923.1.1.1.6”，
      “ friendlyName”：“ eduPersonPrincipalName”，
      “ scoped”：true，
      “ script”：“ groovy {logger.info （\“名称： ${attributeName}，值： ${attributeValues} \”）；返回['hello'，'world']}“
    }
}
```

如果CAS服务器具有的范围 `example.org`， 的最终值 `eduPersonPrincipalName` 将[`hello@example.org`， `world@example.org`] 释放为 `瓮：OID：1.3 .6.1.4.1.5923.1.1.1.6` 的友好名称为 `eduPersonPrincipalName`。

### 外部脚本

与上述相同的用例，除了属性值由外部Groovy脚本额外处理：

```json 
{
    “ @class”：“ java.util.TreeMap”，
    “ eduPersonPrincipalName”：{
      “ @class”：“ org.apereo.cas.authentication.attribute.DefaultAttributeDefinition”，
      “ key”：“ eduPersonPrincipalName”，
      “ name”：“ urn：oid：1.3.6.1.4.1.5923.1.1.1.6”，
      “ friendlyName”：“ eduPersonPrincipalName”，
      “ scoped”：true，
      “ script”：“ file：/ attribute- definitions.groovy“
    }
}
```

Groovy脚本的轮廓应定义为：

```groovy
def run（Object [] args）{
    def attributeName = args[0]
    def attributeValues = args[1]
    def logger = args[2]
    logger.info（“ name： ${attributeName}，values： ${attributeValues}”）
    return [“ casuser”，“ groovy“]
}
```

如果CAS服务器具有的范围 `example.org`， 的最终值 `eduPersonPrincipalName` 将[`casuser@example.org`， `groovy@example.org`] 释放为 `瓮：OID：1.3 .6.1.4.1.5923.1.1.1.6` 的友好名称为 `eduPersonPrincipalName`。
