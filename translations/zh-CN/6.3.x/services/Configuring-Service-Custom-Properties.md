---
layout: 违约
title: CAS - 配置服务自定义属性
category: 服务业
---

# 配置服务自定义属性

CAS 能够向注册服务添加任意属性。 这些属性被视为有关服务的额外元数据， 表示联系电话、电子邮件等设置，或 扩展 在每个服务的基础上用于自定义功能的额外属性和字段。

示例 JSON 文件如下：

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："^https://.+"，
  "名称"："样本服务"，
  "id"：100，
  "属性"：{
    "@class"："java.util.哈希图"，
    "电子邮件"：{
      "@class"："org.apereo.cas.服务"，
      "价值"："java.util.HashSet"，"person@place.edu"，"admin@place.edu"]]
    [
  ]
}
```

注册服务属性值可以使用 [春季表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。
