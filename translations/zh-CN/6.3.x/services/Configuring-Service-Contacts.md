---
layout: 违约
title: CAS - 配置服务联系人
category: 服务业
---

# 配置服务联系人

CAS 能够将联系信息分配给服务定义。 这些是个人和/或实体，可以归类为应用程序的所有者，如果适用于服务定义的更改，可能会通知这些实体。

示例 JSON 文件如下：

```json
•
  "@class"："org.apereo.cas.services.服务.注册服务"，
  "服务id"："^https://.+"，
  "名称"："示例服务"，
  "id"：100，
  "联系人"：[
    "java.util.Arraylist"， [{
        "@class"："org.apereo.cas.服务.默认注册服务联系"，
        "名称"："约翰·史密斯"，
        "电子邮件"："jsmith@example.org
        "电话"："123-456-7890"，
        "部门"："IT"
      [
    ]
  ]
}
```
