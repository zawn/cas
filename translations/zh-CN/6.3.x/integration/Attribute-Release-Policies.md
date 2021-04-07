---
layout: 默认
title: CAS-属性发布政策
category: 属性
---

# 属性发布政策

属性发布策略决定如何在最终的CAS响应中选择属性并将其提供给给定的应用程序。 此外，每个策略都可以应用可选的过滤器，以根据其值清除其属性。

所有属性发布策略都共享以下设置：

| 姓名                                            | 价值                                                                                          |
| --------------------------------------------- | ------------------------------------------------------------------------------------------- |
| `authorizedToReleaseCredentialPassword`       | 定义服务是否被授权为 [布尔值，将凭证作为属性](ClearPass.html)释放。                                                 |
| `AuthorizedToReleaseProxyGrantingTicket`      | 定义服务是否被授权为 [布尔值，将授予代理授权的票证ID作为属性](../installation/Configuring-Proxy-Authentication.html)释放。 |
| `excludeDefaultAttributes`                    | 布尔值，用于定义此策略是否应排除要发布的默认全局属性包。                                                                |
| `authorizedToReleaseAuthenticationAttributes` | 布尔值，用于定义此策略是否应排除要发布的身份验证/协议属性。 身份验证属性被视为与特定主体无关的属性，并且定义了有关身份验证事件本身的额外补充元数据，例如开始日期。          |
| `PrincipalIdAttribute`                        | 您自己选择的属性名称，将填充到最终的属性束中，并带有CAS身份验证的主体标识符。 默认情况下，主体ID为 *不是* 作为属性发布。                           |

<div class="alert alert-warning"><strong>使用警告！</strong><p>想想 <strong>非常仔细地</strong> 打开上面的设置之前。 盲目授权应用程序接收授权代理票证或用户凭证
可能会导致安全漏洞和攻击。 确保您确实需要启用这些功能，并且了解其原因。 避免在何时何地，特别是在共享用户凭据时。</p></div>

CAS区分传递有关身份验证事件的元数据的属性与包含已身份验证的主体的个人可识别数据的

## 行政端点

CAS提供了以下端点：

| 终点                  | 描述                                                                 |
| ------------------- | ------------------------------------------------------------------ |
| `releaseAttributes` | 调用CAS [属性版本](../integration/Attribute-Release.html) 引擎以将属性释放到应用程序。 |

支持的参数如下：

| 查询参数  | 描述           |
| ----- | ------------ |
| `用户名` | 用于身份验证的用户名。  |
| `密码`  | 用于身份验证的密码。   |
| `服务`  | 应该将属性释放到的服务。 |

上面的参数可以添加为查询字符串参数，也可以添加为通过POST提交的JSON对象：

```json
{ 
  “用户名”：“ USERNAME”，
  “密码”：“ PASSWORD”，
  “服务”：“ SERVICE_URL”
}
```

## 认证属性

在身份验证过程中，CAS 捕获并收集了许多属性，以描述有关身份验证事件本身性质的元数据和其他属性。 这些通常包括由基础协议 记录和分类的属性，或特定于CAS的属性，这些属性可以描述所使用的凭据的类型，成功执行的 身份验证处理程序，身份验证的日期/时间等。

向服务提供商和应用程序释放身份验证属性可以在某种程度上被控制为 要了解更多信息并查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#authentication-attributes)。

## 主要属性

主体属性通常传达有关经过身份验证的用户的个人身份数据，例如地址，姓氏等 释放策略在CAS中可用，并记录在 以下，以明确控制可被授权释放到给定应用程序的属性的集合。

<div class="alert alert-info"><strong>记住</strong><p>根据使用的协议和在CAS中注册的服务类型/类别（即信赖方），
其他释放策略，这些策略可以对属性释放进行更精细的控制，从而更好地满足特定
身份验证协议的需求在眼前。 切记通过访问并研究每种协议的适当文档来验证CAS的属性释放功能。</p></div>

### 默认

默认情况下，CAS提供了向所有服务释放一整套主体属性的功能。 此捆绑包不是在每个服务的基础上定义的，而是始终与服务的特定释放策略所产生的属性结合使用，例如，您可以设计规则以始终向每个应用程序 `namedName` 和 `cn` 并根据其属性发布策略仅允许某些应用程序使用其他特定的主体属性。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#default-bundle)。

### 全部归还

将所有已解析的主体属性返回给服务。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllAttributeReleasePolicy”
  }
}
```

### 全部拒绝

永远不要将主体属性返回给应用程序。 请注意，此策略 还会跳过并拒绝释放默认属性（如果有）。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.DenyAllAttributeReleasePolicy“
  }
}
```

### 允许退货

仅返回服务定义明确允许的主体属性。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ cn”，“ mail”，“ sn”]]
  }
}
```

### 返回加密

使用分配的注册服务公共密钥对base-64中所有允许的属性进行加密和编码。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnEncryptedAttributeReleasePolicy”，
    “ allowedAttributes”：[“ java.util.ArrayList”，[“ cn”，“ mail”，“ sn”]]
  }，
  “ publicKey”：{
    “ @class”：“ org.apereo.cas.services.RegisteredServicePublicKeyImpl”，
    “ location”：“ classpath：public.key”，
    “ algorithm”：“ RSA”
  }
}
```

密钥可以通过以下命令生成：

```bash
openssl genrsa -out private.key 1024
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outder DER -nocrypt -in private.key -out private .p8
```

### 休息

仅返回通过联系REST端点显式允许的主体属性。 端点必须设计为接受/处理 `application / json`。 预期的响应状态代码为 `200` ，其中响应的主体包括 `Map` 属性，这些属性链接到它们的值。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnRestfulAttributeReleasePolicy”，
    “ endpoint”：“ https://somewhere.example.org”
  }
}
```

以下参数传递到端点：

| 范围    | 描述               |
| ----- | ---------------- |
| `主要的` | 代表已验证主体的对象。      |
| `服务`  | 代表注册表中相应服务定义的对象。 |

提交的请求的主体还可以包括当前已解析属性 `映射`

### 返回映射

与上述类似，此策略将返回 服务允许的主体属性的集合，但也允许在更精细的服务级别上映射和“重命名”这些主体属性。

例如，以下配置将识别已解析的 属性 `eduPersonAffiliation` 和 `groupMembership` ，然后 将 `关联` 和 `组` 释放到配置的Web应用程序。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnMappedAttributeReleasePolicy“，
    ” allowedAttributes“：{
      ” @class“：” java.util.TreeMap“，
      ” eduPersonAffiliation “：”隶属关系“，
      ” groupMembership“：”组“
    }
  }
}
```

### 返回多重映射

相同的策略可以允许将属性定义重命名并重新映射到多个属性名称，其中 重复的属性值映射到不同的名称。

例如，以下配置将识别已解析的属性 `eduPersonAffiliation` ，然后释放 `隶属` 和 `personAffiliation` 其值源自原始 `eduPersonAffiliation` 属性，而 `groupMembership` 作为 `组`释放。 换句话说， `eduPersonAffiliation` 属性在两个共享相同值的不同名称下释放了两次。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnMappedAttributeReleasePolicy”，
    “ allowedAttributes”：{
      “ @class”：“ java.util.TreeMap”，
      “ eduPersonAffiliation”：[“ java.util.ArrayList “，[”会员关系“，” personAffiliation“]]，
      ” groupMembership“：” group“
    }
  }
}
```

### 内联Groovy属性

映射的主要属性可以从内联Groovy脚本中产生其值。 例如，如果您当前 解析了一个值为 `piper``uid` 属性，则可以考虑以下内容：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnMappedAttributeReleasePolicy”，
    “ allowedAttributes”：{
      “ @class”：“ java.util.TreeMap”，
      “ uid”：“ groovy {返回属性[' uid']。get（0）+'棒极了'}“
    }
  }
}
```

在以上代码段中， `uid` 属性名称的值映射到内联groovy脚本的结果。 内联脚本始终以语法 `groovy {...}` 开头，并将当前解析的 属性 `属性` 绑定变量传递。 脚本的结果可以是单个值/一个或多个值的集合。

上面的配置将为 `uid` 属性，该属性的值是 的串联，原始值是 `uid` 加上单词“ wright”，因此最终结果将是“ piper is great”。

### 基于文件的Groovy属性

与内联groovy属性定义相同，除了groovy脚本也可以外部化为 `` 文件：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnMappedAttributeReleasePolicy”，
    “ allowedAttributes”：{
      “ @class”：“ java.util.TreeMap”，
      “ uid”：“ file：/ etc / cas /sample.groovy“
    }
  }
}
```

`sample.groovy` 脚本本身可能具有以下轮廓：

```groovy
import java.util。*

def run（最终对象... args）{
    def属性= args[0]
    def logger = args[1]
    logger.debug（“当前属性为{}”，属性）
    return []
}
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。

### Groovy脚本

让外部Groovy脚本决定应如何释放主体属性。 组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.GroovyScriptAttributeReleasePolicy”，
    “ groovyScript”：“ classpath：/script.groovy”
  }
}
```

脚本本身可以在Groovy中设计为：

```groovy
import java.util。*

def Map<String, List<Object>> run（final Object ... args）{
    def currentAttributes = args[0]
    def logger = args[1]
    def主体= args[2]
    def服务= args[3]

    logger.debug（ “收到的当前属性是{}”，currentAttributes）
    返回[用户名：[“某物”]，喜欢：[“奶酪”，“食物”]，id：[1234,2,3,4,5]，另一个： “属性”]
}
```

以下参数传递到脚本：

| 范围                  | 描述                                 |
| ------------------- | ---------------------------------- |
| `currentAttributes` | `当前已解析且可用于发布的属性的映射`                |
| `记录器`               | 负责发布日志消息的对象，例如 `logger.info（...）`。 |
| `主要的`               | 代表已验证主体的对象。                        |
| `服务`                | 代表注册表中相应服务定义的对象。                   |

### 脚本引擎

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能已弃用，并计划在将来删除。</strong></p>
</div>

使用备用脚本引擎实现和其他编程语言来配置属性释放策略。 该方法 利用了通过附加库和驱动程序内置在Java平台中的脚本功能。 尽管Groovy应该是 ，但是叠加层中需要以下模块才能包含对其他语言 例如Python等）的支持。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持脚本引擎</artifactId>
    <version>${cas.version}</version>
</dependency>
```

然后可以将服务定义设计为：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ScriptedRegisteredServiceAttributeReleasePolicy”，
    “ scriptFile”：“ classpath：/ script。[py | js | groovy]”
  }
}
```

此组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法 的资格。 脚本 需要设计一个 `运行` 函数，该函数接收参数列表。 的当前属性以及记录器对象的集合将传递给此函数。 结果必须生成一个映射，该映射的 `键`s是属性名称 而其 `值`s是属性值的列表。

例如，脚本本身可以在Groovy中设计为：

```groovy
import java.util。*

def Map<String, List<Object>> run（final Object ... args）{
    def currentAttributes = args[0]
    def logger = args[1]

    logger.debug（“收到的当前属性为{}”，currentAttributes）
    return [username：[“ something”]，likes：[“ cheese”，“ food”]，id：[1234,2,3,4,5]，另一个：“ attribute”]
}
```

这是用Python编写的相同脚本：

```python
def run（* Params）：
  属性=参数[0]
  记录器=参数[1]
  ＃计算属性并返回新的属性字典...
  返回 ...
```

您还可以将内联的Groovy脚本填充到 `scriptFile` 属性中。 脚本 可以访问已解析的 `属性` 的集合以及 `logger` 对象。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ScriptedRegisteredServiceAttributeReleasePolicy”，
    “ scriptFile”：“ groovy { return attributes }”
  }
}
```

### 连锁政策

可以将属性释放策略链接在一起以处理多个规则。 策略调用的顺序与为服务本身定义的定义顺序相同。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ChainingAttributeReleasePolicy”，
    “ mergingPolicy”：“ replace”，
    “ policies”：[“ java.util.ArrayList”，
      [
          {“ @class”： “ ...”}，
          {“ @class”：“ ...”}
      ]
    ]
  }
}
```

支持以下合并策略：

| 政策   | 描述                     |
| ---- | ---------------------- |
| `代替` | 合并属性，以便源中的属性始终替换主体属性。  |
| `添加` | 合并属性，以便从主体中生成源中不存在的属性。 |
| `多值` | 具有相同名称的属性将合并为多值属性。     |

#### 订购政策

请注意，可以为链中的每个策略分配一个数字 `阶` ，该数字将在执行前确定其在链中的位置。 这 ，如果你有一个应该传递到前动态先计算出的值属性释放政策顺序可能是重要的 下一个政策链。

例如，下面的策略链允许CAS首先使用 `GeneratesFancyAttributeReleasePolicy` 策略 生成属性，然后将该属性下一步传递到链中的下一个策略，即 `ReleaseFancyAttributeReleasePolicy`，以决定 该属性是否应被发布。 请注意，策略 `顺序` 的配置确定执行顺序。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ChainingAttributeReleasePolicy”，
    “ policies”：[“ java.util.ArrayList”，
      [
          {
            “ @class”：“ org.apereo.cas.ReleaseFancyAttributeReleasePolicy “，
            ” order“：1
          }，
          {
            ” @class“：” org.apereo.cas.GeneratesFancyAttributeReleasePolicy“， 
            ” order“：0
          }
      ]
    ]
  }
}
```

## 属性值过滤器

虽然每个策略定义了给定服务可以允许哪些主要属性，但是 可以为每个策略设置可选的属性过滤器，以基于其 **值**进一步淘汰属性。

[请参阅本指南](Attribute-Value-Release-Policies.html) 了解更多信息。
