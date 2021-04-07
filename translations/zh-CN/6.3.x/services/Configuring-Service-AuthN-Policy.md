---
layout: 违约
title: CAS - 服务认证政策
category: 服务业
---

# 服务认证政策

注册表中的每份注册申请都可能获得一份认证政策，说明 CAS 在处理给定服务时应如何验证和执行认证交易。 认证策略有时可能覆盖 CAS 认证引擎中的全球发现，也可能 提供补充功能，以增强认证流程。

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："https://app.example.org/.+"，
  "名称"："示例应用程序"，
  "id"：1，
  "认证政策"：+
    "@class"："org.apereo.cas.服务。默认注册服务授权政策"，  
    "必需的授权手"： [java. util. Treeset]， [奥思汉德勒名称]]，
    "排除身份汉德勒"： [java. util. Treeset"， []]
  [
]
```

可将以下字段分配给策略：

| 参数        | 描述                                                                            |
| --------- | ----------------------------------------------------------------------------- |
| `需要授权汉德勒` | CAS中可用和配置的所需身份验证处理程序的一组标识符/名称。 这些名称可用于执行服务定义，仅在向 CAS 提交身份验证请求时使用带有该名称的身份验证策略。 |
| `排除授权汉德勒` | 排除身份验证处理程序的一组标识符/名称。 这些名称可用于执行服务定义，以 *排除* ，并在向 CAS 提交身份验证请求时取消某些身份验证处理程序的资格。  |

请注意，虽然 CAS 中的认证方法都被赋予了默认名称，但大多数（如果不是所有方法）都可以通过 CAS 设置分配名称。

## 身份验证策略标准

认证策略标准也可以分配给每个应用程序定义，该定义应覆盖为部署定义的全球策略。 这些政策应紧跟那些可以在全球</a>定义之后，是完全可选的，并且可以是以下类型之一：</p> 



### 允许

`所需的` [认证策略的地图](../configuration/Configuration-Properties.html#required)。



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）"，
  "名称"："示例"，
  "id"： 1，
  "认证政策"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务验证政策"，
    "必需授权手"： ["java. util. Treeset"， [ "" JSON"[]，
    "标准"：{
      "@class"："org.apereo.cas.服务。允许授权汉德勒注册服务"
    =
  =
}
```




### 排除

启用身份验证策略标准，以名称排除和取消所示身份验证处理程序的资格。



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："^（https|图片）："/*"，
  "名称"："示例"，
  "id"： 1，
  "认证政策"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务验证政策"，
    "排除身份" ： ["java. util. Treeset"， ["JSON"]，
    "标准"：{
      "@class"："org.apereo.cas.服务

  
    。
```




### 任何

地图到 `任何` [认证政策](../configuration/Configuration-Properties.html#authentication-policy)。



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："https://app.example.org/.+"，
  "名称"："示例应用程序"，
  "id"： 1，
  "认证政策"：{
    "@class"："org.apereo.cas.服务.默认注册服务认证政策"，
    "标准"：{
      "@class
      "："org.apereo.cas.服务。任何真实汉德勒注册服务政策"，"尝试所有"：真正的
    }
  }
}
```




### 都

地图到 `所有` [认证政策](../configuration/Configuration-Properties.html#authentication-policy)。



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："https://app.example.org/.+"，
  "名称"："示例应用程序"，
  "id"：1、
  "认证政策"：+
    "@class"："org.apereo.cas.服务.默认注册服务"，
    "标准"：{
      "@class ""："组织.apereo.cas.服务.所有授权汉德勒注册服务"
    =
  =
}
```




### 未阻止

地图到 `不妨碍` [认证政策](../configuration/Configuration-Properties.html#authentication-policy)。



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："https://app.example.org/.+"，
  "名称"："示例App"，
  "id"：1、
  "认证政策"：+
    "@class"："org.apereo.cas.服务.默认注册服务"，
    "标准"：{
      " @class"："org.apereo.cas.服务。未注册服务"
    =
  =
}
```




### 槽的

地图到 `格罗夫` [认证政策](../configuration/Configuration-Properties.html#authentication-policy)。



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："https://app.example.org/.+"，
  "名称"："示例应用程序"，
  "id"： 1，
  "认证政策"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务验证政策"，
    "标准" ：{
      "@class"："org.apereo.cas.服务。格劳维注册服务认证政策"，
      "脚本"："。。。"
    [
  }
}
```


`脚本` 属性可以是内联 Groovy 脚本，也可以是外部文件的引用。 



### 休息

`休息` [认证策略](../configuration/Configuration-Properties.html#authentication-policy)地图。



```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："https://app.example.org/.+"，
  "名称"："示例应用程序"，
  "id"：1，
  "认证政策"：+
    "@class"："org.apereo.cas.服务。默认注册服务授权政策"，
    "标准"： [
      "@class"： "org. apereo. cas. 服务. 重新注册服务" ，
      "url"： "。。。"，
      "基本使用"： "。。。"，
      "基本真实密码"： "。。。"
    [
  }
}
```
