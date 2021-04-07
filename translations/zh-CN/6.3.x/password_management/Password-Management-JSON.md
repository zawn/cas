---
layout: 违约
title: CAS - 密码管理
category: 密码管理
---

# 密码管理 - 杰森

帐户和密码可以存储在静态适度的 JSON 资源中，其位置通过设置传授给 CAS。 此选项在开发、测试和演示过程中最有用，不适合生产。

JSON 文件的大纲可能与以下文件相匹配：

```json
{
  "卡瑟"：{
    "电子邮件"："casuser@example.org"，
    "密码"："p@ssw0rd"，
    "电话"："1234567890"，
    "安全问题"：{
      "问题1"："答案1"，
      "问题2"："答案2"
    }
  =
}
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-password-management)。
