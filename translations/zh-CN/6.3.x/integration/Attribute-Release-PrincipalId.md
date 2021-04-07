---
layout: 默认
title: CAS-发布主要ID
category: 属性
---

# 主体ID属性

已注册的CAS应用程序具有允许配置 用户名属性提供程序的能力，该名称提供程序控制返回给应用程序 默认情况下，用户标识符是经过身份验证的CAS主体ID，但可选地，它也可以基于现有的 属性，该属性已经为主体可用并已解析。 实际上，此组件确定应在返回给应用程序的最终CAS验证有效负载中 `<cas:user>`

<div class="alert alert-warning"><strong>主体ID作为属性</strong><p>您还可以在最终的CAS有效负载中将经过身份验证的主体ID作为额外的属性返回。 请参阅 <a href="Attribute-Release-Policies.html">本指南</a> 以了解更多信息。</p></div>

许多提供程序都可以对返回的最终用户ID进行规范化，以将其 转换为大写/小写。 这是通过注意到 `canonicalizationMode` ，其允许值是 `UPPER`， `LOWER` 或 `NONE`。

## 默认

无需显式定义的默认配置，将返回解析的 主体ID作为此服务的用户名。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “描述”：“示例“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceUsernameProvider“，
    ” canonicalizationMode“：” NONE“
  }
}
```

如果您不需要调整此供应商的行为（即修改 `规范化` 模式）， ，那么你完全可以离开了这个块。

## 加密的

假设服务定义具有公共密钥，则大多数（如果不是全部）提供程序都能够加密解析的用户名。

可以通过以下命令生成密钥：

```bash
openssl genrsa -out private.key 1024
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outder DER -nocrypt -in private.key -out private .p8
```

然后，在CAS中为服务定义配置公用密钥：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “描述”：“ sample“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceUsernameProvider“，
    ” encryptUsername“：” true“
  }，
  ” publicKey“：{
    ” @class“： “ org.apereo.cas.services.RegisteredServicePublicKeyImpl”，
    “位置”：“ classpath：public.key”，
    “算法”：“ RSA”
  }
}
```

公钥组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。

然后，应用程序可以使用其自己的私钥继续解密用户名。 以下示例代码演示了如何在Java中完成此操作：

```java
最终字符串casUsername = ...
最终的PrivateKey privateKey = ...
最终密码密码= Cipher.getInstance（privateKey.getAlgorithm（））;
最终字节[] cred64 = encodeBase64（encodedPsw）;
cipher.init（Cipher.DECRYPT_MODE，privateKey）;
最后的字节[] cipherData = cipher.doFinal（casUsername）;
返回新的String（cipherData）;
```

## 属性

返回一个已经为主体解析的属性，作为该服务的用户名。 如果属性 不可用，将使用默认的主体ID。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：600，
  “描述”：“示例“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.PrincipalAttributeRegisteredServiceUsernameProvider“，
    ” usernameAttribute“：” cn“，
    ” canonicalizationMode“：” UPPER“
  }
}
```

## Javascript / Python / Groovy脚本

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能已弃用，并计划在将来删除。</strong></p>
</div>

让外部javascript，groovy或python脚本决定应如何确定主体ID属性。 这种方法利用了Java平台内置的脚本功能。 尽管CAS本身应支持Javascript和Groovy，但Python脚本可能需要 来调整CAS配置，以包括 [Python模块](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jython-standalone%22)。

脚本将接收并有权访问以下变量绑定：

- `id`：已认证主体的现有标识符。
- `属性`：当前为委托人解析的属性映射。
- `logger`：一个logger对象，能够提供 `logger.info（）` 操作，等等。


```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：500，
  “描述”：“ sample“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.ScriptedRegisteredServiceUsernameProvider“，
    ” script“：” file：/// etc / cas / sampleService。[groovy | js |。 py]“，
    ” canonicalizationMode“：” UPPER“
  }
}
```

示例Groovy脚本如下：

```groovy
def run（Object [] args）{
    def属性= args[0]
    def id = args[1]
    def logger = args[2]
    logger.info（“测试用户名属性”）
    返回“ test”
}
```

示例javascript函数如下：

```javascript
函数run（uid，logger）{
   返回“测试”
}
```

## Groovy

返回用户名属性值，作为执行Groovy脚本的最终结果。 Groovy脚本（无论是内联脚本还是外部脚本）将接收并有权访问以下变量绑定：

- `id`：已认证主体的现有标识符。
- `属性`：当前为委托人解析的属性映射。
- `服务`：与注册的服务定义匹配的服务对象。
- `logger`：一个logger对象，能够提供 `logger.info（...）` 操作，等等。

### 排队

将groovy脚本直接嵌入服务配置中。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：600，
  “描述”：“示例“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.GroovyRegisteredServiceUsernameProvider“，
    ” groovyScript“：” groovy {返回属性['uid'][0] +'123456789'}“，
    “ canonicalizationMode”：“ UPPER”
  }
}
```

请注意，上例中的 `uid` 属性在内部被解析为多值属性，CAS读取所有属性时也应将其解析为多值属性。 因此，上述示例中的 `[0]` 语法来获取属性的第一个值。

### 外部的

将groovy脚本作为服务配置之外的外部资源进行引用。 该脚本必须返回单个 `字符串` 值。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：600，
  “描述”：“ sample“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.GroovyRegisteredServiceUsernameProvider“，
    ” groovyScript“：” file：///etc/cas/sampleService.groovy“，
    ” canonicalizationMode “：” UPPER“
  }
}
```

示例Groovy脚本如下：

```groovy
logger.info（“从属性 $attributes选择用户名属性”）
返回“ newPrincipalId”
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。

## 匿名/暂时

提供用户名的不透明标识符。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：500，
  “描述”：“ sample“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.AnonymousRegisteredServiceUsernameAttributeProvider“
  }
}
```

## 匿名/持久

提供用户名的不透明标识符。 默认情况下，不透明标识符符合 [eduPersonTargetedID](https://spaces.at.internet2.edu/display/federation/Persistent+Identifier+Support) 属性 所生成的id可以基于现有的主体属性。 如果未指定或找不到属性，则使用经过身份验证的主体ID。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：500，
  “描述”：“ sample“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.AnonymousRegisteredServiceUsernameAttributeProvider“，
    ” persistentIdGenerator“：{
      ” @class“：” org.apereo.cas.authentication.principal。 ShibbolethCompatiblePersistentIdGenerator“，
      ” salt“：” aGVsbG93b3JsZA ==“，
      ” attribute“：”“
    }
  }
}
```

若要模拟行为，您也可以尝试以下命令：

```bash
perl -e'使用Digest :: SHA qw（sha1_base64）; \
    $digest = sha1_base64（“$SERVICE!$USER！$SALT”）; \
    $eqn =长度（$digest）％4;打印 $digest;打印“ =” x（4-$eqn）。 “\n” 
```

用适当的测试值 `$SERVICE` （被测应用程序的URL）， `$USER` 和 `$SALT`

