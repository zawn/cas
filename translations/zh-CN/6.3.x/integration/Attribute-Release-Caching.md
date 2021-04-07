---
layout: 默认
title: CAS-属性发布缓存
category: 属性
---

# 属性发布缓存

默认情况下，将 [解析属性](Attribute-Resolution.html) 缓存到SSO会话 开始以来属性值发生任何更改，则更改不会反映出来，并在释放时将

注意：请记住，虽然以下策略通常在每个服务级别的发布时应用，但是 CAS会自动在更全局的范围内创建属性发布缓存策略，并具有可配置的超时 和持续时间。 有关更多信息，请参见 [相关设置](../configuration/Configuration-Properties.html#authentication-attributes)

所有主要属性存储库共享以下设置：

| 姓名                         | 价值                                                    |
| -------------------------- | ----------------------------------------------------- |
| `合并策略`                     | 组合来自多个来源的属性时，指示合并策略。 可接受的值为 `多值`， `ADD`， `NONE`， `多值` |
| `attributeRepositoryIds`   | 属性集 `` ，以在发布时进行属性解析。                                  |
| `ignoreResolvedAttributes` | 忽略通常在属性存储库中在主体解析阶段可能已解决的属性的集合。                        |

## 默认

`主体` 和基础属性 存储库源之间的默认关系，这样就可以保持主体属性不变，而无需 额外的过程来评估和更新它们。 无需显式配置。

## 快取

一个CAS之间的关系 `特等` 和下面的属性 库源，它描述了如何和在什么长度CAS `主要` 属性应该 被缓存。 在属性释放时间之后，将根据缓存过期策略咨询此组件，以确保将适当的 属性值释放到范围服务。 如果过期策略已通过，则将向基础属性存储库源查询 以找出可用的属性集。

该组件还具有解决现有主体属性与 `mergingStrategy` 属性从存储库源检索的那些属性）之间的冲突的能力。 如果要保留在身份验证事件等过程中从其他位置检索到的主体 可用的属性的集合，这将很有用。

<div class="alert alert-info"><strong>释放时缓存</strong><p>注意
：只有在服务票证验证事件发生时，才在发布时参考该策略。 如果有
自定义Web流，并且希望依赖于已解析的 <code>委托人</code> 并且还希望
接收到更新的属性集，则这些组件必须在不依赖于 <code>委托人</code>
。</p></div>

配置示例如下：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “ principalAttributesRepository”：{
      “ @class”：“ org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository”，
      “ timeUnit” ： “HOURS”，
      “过期”：2，
      “mergingStrategy”： “NONE”
    }
  }
}
```

## 合并策略

默认情况下，不执行任何合并策略，这意味着始终忽略主体属性，并且始终返回源中的 但是，以下任何合并策略都可能是合适的选择：

### 合并

具有相同名称的属性将合并到多值列表中。

例如：

1. 主体具有属性 `{email=eric.dalquist@example.com，电话= 123-456-7890}`
2. 来源具有属性 `{phone = [111-222-3333，000-999-8888]，office = 3233}`
3. 合并后的结果将具有以下属性： `{email=eric.dalquist@example.com，电话= [123-456-7890、111-222-3333、000-999-8888]，office = 3233}`


```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “ principalAttributesRepository”：{
      “ @class”：“ org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository”，
      “ timeUnit” ： “HOURS”，
      “过期”：2，
      “mergingStrategy”： “多值”
    }
  }
}
```

### 添加

合并属性，以便从主体中生成源中不存在的属性。

例如：

1. 主体具有属性 `{email=eric.dalquist@example.com，电话= 123-456-7890}`
2. 来源具有属性 `{phone = [111-222-3333，000-999-8888]，office = 3233}`
3. 合并后的结果将具有以下属性： `{email=eric.dalquist@example.com，电话= 123-456-7890，办公室= 3233}`

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “ principalAttributesRepository”：{
      “ @class”：“ org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository”，
      “ timeUnit” ： “HOURS”，
      “过期”：2，
      “mergingStrategy”： “ADD”
    }
  }
}
```

### 代替

合并属性，以便源中的属性始终替换主体属性。

例如：

1. 主体具有属性 `{email=eric.dalquist@example.com，电话= 123-456-7890}`
2. 来源具有属性 `{phone = [111-222-3333，000-999-8888]，office = 3233}`
3. 合并后的结果将具有以下属性： `{email=eric.dalquist@example.com，电话= [111-222-3333，000-999-8888]，office = 3233}`


```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “ principalAttributesRepository”：{
      “ @class”：“ org.apereo.cas.authentication.principal.cache.CachingPrincipalAttributesRepository”，
      “ timeUnit” ： “HOURS”，
      “过期”：2，
      “mergingStrategy”： “替换”
    }
  }
}
```


## 属性存储库过滤

主要属性存储库可以查询由 [人员目录](Attribute-Resolution.html)定义和控制的属性源。 `MyJsonRepository`定义了JSON属性 存储库源，则以下定义不考虑所有先前解析的属性，并 `MyJsonRepository` 来获取属性并将其缓存 `30` 分钟。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^（https | imaps）：//.*”，
  “ name”：“ HTTPS和IMAPS”，
  “ id”：
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllAttributeReleasePolicy”，
    “ principalAttributesRepository”：{
        “ @class”：“ org.apereo.cas.authentication .principal.cache.CachingPrincipalAttributesRepository”，
        “TIMEUNIT”： “MINUTES”，
        “过期”：30，
        “ignoreResolvedAttributes”：真的，
        “attributeRepositoryIds”：[ “java.util.HashSet中”，[ “MyJsonRepository”] ]，
        “ mergingStrategy”：“ MULTIVALUED”
    }
  }
}
```

这是一个类似的示例，其中服务已关闭缓存，其中CAS尝试将先前解析的属性与来自标识为 `MyJsonRepository` 存储库的结果进行组合。 期间从主体解析中排除 `MyJsonRepository` 并且仅应在此服务的发布时间联系该属性源：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^（https | imaps）：//.*”，
  “ name”：“ HTTPS和IMAPS”，
  “ id”：
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllAttributeReleasePolicy”，
    “ principalAttributesRepository”：{
        “ @class”：“ org.apereo.cas.authentication .principal.DefaultPrincipalAttributesRepository“，
        ” ignoreResolvedAttributes“：false，
        ” attributeRepositoryIds“：[” java.util.HashSet“，[” MyJsonRepository“]]，
        ” mergingStrategy“：” MULTIVALUED“
    }
  }
}
```
