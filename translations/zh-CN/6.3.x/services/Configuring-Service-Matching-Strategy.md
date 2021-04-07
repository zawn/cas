---
layout: 默认
title: CAS-配置服务匹配策略
category: 服务
---

# 配置服务匹配策略

将携带客户端应用程序标识符的身份验证请求与分配给服务定义 默认情况下，服务标识符被视为正则表达式模式 ，需要对其进行正确编码和定义以使匹配操作成功执行。 该策略可以 ，以允许替代选项或完整的策略实施，可能需要 考虑外部因素和变量。

有关匹配策略的详细信息，请参见下文。

## 全正则表达式

这是将 `serviceId` 视为正则表达式的默认选项。 使用此选项， CAS尝试将表达式与整个请求的服务标识符进行匹配，并且隐式 在定义的模式的开头添加 `^` `$` ，这意味着它将不查找子字符串匹配项。

以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ https：//.*”，
  “ name”：“ sample”，
  “ id”：
  “ matchingStrategy”：{
    “ @class”：“ org.apereo.cas.services.FullRegexRegisteredServiceMatchingStrategy”
  }
}
```

## 部分正则表达式

此策略将 `serviceId` 视为正则表达式。 使用此选项，CAS将查找并允许子字符串匹配。

以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ \\ d \\ d \\ d”，
  “ name”：“ sample”，
  “ id”：
  “ matchingStrategy”：{
    “ @class”：“ org.apereo.cas.services.PartialRegexRegisteredServiceMatchingStrategy”
  }
}
```

例如，上述模式将与 `https://example123.com`匹配。

## 文字

此策略将 `serviceId` 视为文字文本，并将查找完全匹配的内容。 编码单个/特殊字符（例如 `？）的情况下，这可能很有用。 URL中的`

以下是一个示例JSON文件：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://example.com?key=value”，
  “名称”： “样品”，
  “ID “：
  ” matchingStrategy“：{
    ” @class“：” org.apereo.cas.services.LiteralRegisteredServiceMatchingStrategy“，
    ” caseInsensitive“：true
  }
}
```

