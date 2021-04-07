---
layout: 默认
title: CAS-配置服务环境
category: 服务
---

# 配置服务环境

注册表中的每个已注册应用程序都可以分配一组环境名称。 环境名称充当过滤器，如果运行时环境实际上与注册的服务环境匹配 这样一来，一个人就可以在CAS上多次注册同一应用程序的多个版本，而每个版本可能仅与特定的运行时配置文件相关。 可以使用指定为环境变量或命令行标志等 `spring.profiles.active` 属性在CAS中激活环境。

`生产` 或 `生产前`之一时，CAS才能识别和加载以下服务定义：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ”环境“：[” java.util.HashSet“，[”生产“，”预生产“]]
}
```

请注意，无论运行时配置文件如何，都将加载没有分配环境的注册服务定义。 同样，如果将CAS设置为在没有活动配置文件的情况下运行，则所有
