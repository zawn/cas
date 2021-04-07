---
layout: 默认
title: CAS-配置服务自定义属性
category: 服务
---

# 配置服务自定义属性

CAS具有向注册服务添加任意属性的能力。 这些属性被视为有关服务的额外元数据，其中 表示诸如联系人电话号码，电子邮件等之类的设置，或者 额外属性和字段可被扩展 用于基于每个服务的自定义功能。

以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“示例服务”，
  “ id”：100 ，
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ email”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”： [“ java.util.HashSet”，[“ person@place.edu”，“ admin@place.edu”]]
    }
  }
}
```

注册的服务属性值可以使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法。
