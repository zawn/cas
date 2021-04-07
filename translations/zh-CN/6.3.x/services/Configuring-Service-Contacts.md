---
layout: 默认
title: CAS-配置服务联系人
category: 服务
---

# 配置服务联系人

CAS具有将联系信息分配给服务定义的能力。 这些是个人和/或实体，可以被分类为应用程序的所有者，如果更改应用到服务定义，则可能会得到通知。

以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“示例服务”，
  “ id”：100 ，
  “联系人”：[
    “ java.util.ArrayList”，[{
        “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceContact”，
        “ name”：“ John Smith”，
        “ email” ：“ jsmith@example.org”，
        “电话”：“ 123-456-7890”，
        “部门”：“ IT”
      }
    ]
  ]
}
```
