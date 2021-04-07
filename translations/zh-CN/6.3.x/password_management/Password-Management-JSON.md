---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理-JSON

帐户和密码可以存储在静态适度的JSON资源中，该资源通过设置告知CAS。 此选项在开发，测试和演示期间最有用，不适用于生产。

JSON文件的轮廓可能与以下内容匹配：

```json
{
  “ casuser”：{
    “ email”：“ casuser@example.org”，
    “ password”：“ p @ ssw0rd”，
    “ phone”：“ 1234567890”，
    “ securityQuestions”：{
      “ question1” ：“ answer1”，
      “ question2”：“ answer2”
    }
  }
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-password-management)。
