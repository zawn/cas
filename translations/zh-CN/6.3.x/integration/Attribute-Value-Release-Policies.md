---
layout: 默认
title: CAS-属性值发布策略
category: 属性
---

# 属性值过滤器

虽然每个策略定义了给定服务可以允许哪些主要属性，但是 可以为每个策略设置可选的属性过滤器，以基于其 **值**进一步淘汰属性。

## 链式过滤器

可以将属性过滤器链接在一起，以便将多个过滤器与单个服务定义相关联。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceChainingAttributeFilter”，
      “过滤器”：[“ java.util.ArrayList”，
        [
            {
              “ @class”：“ org.apereo.cas.services.support.RegisteredServiceRegexAttributeFilter”，
              “ pattern”：“ ^ \\ w{3}$“，
              ” order“：10
            }，
            {
              ” @class“：” ...“
            }
        ]
      ]
    }，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ uid”，“ groupMembership”]]
  }
}
```

链接的属性过滤器在执行之前首先 `阶`

## 正则表达式

匹配某个正则表达式模式的属性的正则表达式过滤器。

假设解决了以下属性：

| 姓名                | 价值     |
| ----------------- | ------ |
| `uid`             | 史密斯    |
| `groupMembership` | 性病     |
| `cn`              | 约翰·史密斯 |

例如下面的配置考虑的初始列表 `UID`， `groupMembership` ，然后只允许并释放属性，其值的长度 是3个字符。 因此，在上面的列表中，只有 `groupMembership` 被释放到应用程序。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceRegexAttributeFilter“，
      ” pattern“：” ^ \\ w{3}$“
    }，
    ” allowedAttributes“：[” java.util.ArrayList“，[” uid“，” groupMembership“]]
  }
}
```

## 映射正则表达式

负责确保仅释放其值与某个正则表达式模式匹配的一组选定属性的正则表达式过滤器。 过滤器有选择地将模式应用于配置中映射的属性。 如果映射了属性，则仅当它与链接的模式匹配时才允许释放该属性。 如果未映射属性，则可以选择将其从已发布的属性集中排除。

例如，下面的示例仅允许释放 `memberOf` 如果它包含一个长度为3个字符的值）。 如果未找到任何值，则从最终发布的包中排除 `memberOf`

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceMappedRegexAttributeFilter“，
      ”模式“：{
          ” @class“：” java.util.TreeMap“，
          ” memberOf“：” ^ \\ w{3}$“
      }，
      ” excludeUnmappedAttributes“：false，
      ” completeMatch“ ：false，
      “ caseInsensitive”：true，
      “ order”：0
    }，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ uid”，“ memberOf”]]
  }
}
```

此过滤器支持以下字段：

| 姓名                          | 描述                      |
| --------------------------- | ----------------------- |
| `模式`                        | 属性及其关联模式的映射尝试使用值。       |
| `completeMatch`             | 指示是否应该在整个值区域上执行模式匹配。    |
| `excludeUnmappedAttributes` | 指示是否应从最终捆绑包中删除未映射的属性。   |
| `不区分大小写`                    | 指示是否应该以不区分大小写的方式完成模式匹配。 |

## 反向映射正则表达式

与 *映射的正则表达式* 过滤器相同，不同之处在于该过滤器仅允许选定的一组属性值 **** 不匹配4的某个正则表达式模式被释放。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceReverseMappedRegexAttributeFilter“，
      ”模式“：{
          ” memberOf“：” ^ \\ w{3}$“
      }，
      ” excludeUnmappedAttributes“：false，
      ” completeMatch“：false，
      ” caseInsensitive“：true，
      ”命令“ ：0
    }，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ uid”，“ memberOf”]]
  }
}
```

## 突变映射正则表达式

就设置和属性而言，此过滤器在结构上与 *Mapped Regex* 过滤器相同。 它的主要功能是通过一组模式过滤属性值，然后根据正则表达式匹配的结果动态替换该值。

例如，以下定义尝试基于给定的模式 `memberOf` 每个图案通过链接 `->` 到可引用特定的基团中所产生的正则表达式结果预期的返回值。 假设属性 `memberOf` 值为 `math101` 和 `marathon101`，则过滤器在处理后将生成值 `courseA-athon101` 和 `courseB-h101`。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceMutantRegexAttributeFilter“，
      ”模式“：{
          ” @class“：” java.util.TreeMap“，
          ” memberOf“：[” java.util.ArrayList“，[” ^ mar（。+）（101）> courseA-$1$2“，” ^ mat（。+）（101）> courseB-$1$2“]]
      }，
      ” excludeUnmappedAttributes“：false，
      ” completeMatch“：false，
      ” caseInsensitive“：true，
      “ order”：0
    }，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ uid”，“ memberOf”]]
  }
}
```

## Groovy

属性值过滤也可以使用内联或外部Groovy脚本进行。 `属性` 和 `记录器`访问当前解析的属性。 脚本的返回结果必须是 `Map<String, Object>`。

### 内联Groovy

内联groovy过滤器使您可以将脚本直接嵌入服务定义中。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceScriptedAttributeFilter“，
      ” script“：” groovy { return attributes }“
    }，
    ” allowedAttributes“：[” java.util.ArrayList“，[” uid“，” groupMembership“]]
  }
}
```

### 外部槽

外部groovy过滤器使您可以在CAS Web应用程序外部的文件中定义脚本。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：200，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” attributeFilter“：{
      ” @class“：” org.apereo.cas.services.support。 RegisteredServiceScriptedAttributeFilter”，
      “脚本”：“ file：/etc/cas/filter-this.groovy”
    }，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ uid”，“ groupMembership”]]
  }
}
```

脚本的概要如下：

```groovy
import java.util。*

def run（final Object ... args）{
    def属性= args[0]
    def logger = args[1]

    logger.info“当前解析的属性： ${attributes}”
    def map = ...
    返回地图
}
```

传递的参数如下：

| 范围    | 描述                                 |
| ----- | ---------------------------------- |
| `属性`  | 从源解析的当前属性的 `映射`                    |
| `记录器` | 负责发布日志消息的对象，例如 `logger.info（...）`。 |
