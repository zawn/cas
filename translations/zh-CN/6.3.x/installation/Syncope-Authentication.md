---
layout: 违约
title: CAS - 阿帕奇同步身份验证
category: 认证
---

# 阿帕奇同步身份验证

CAS 支持通过 [阿帕奇同步](http://syncope.apache.org/)处理身份验证事件。 这是通过使用运行同步实例所暴露的 `休息/用户/自我` REST API 来完成的。 作为成功身份验证尝试的一部分，所提供的用户对象的属性被转换为 CAS 属性，然后可以发布到应用程序等。


## 组件

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-同步-身份验证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#syncope-authentication)。

## 属性

作为成功身份验证尝试的一部分，CAS 收集了 Apache 同步提供的以下属性：

| 属性名称          |
| ------------- |
| `同步使用器罗尔斯`    |
| `同步使用器安全问题`   |
| `同步使用者状态`     |
| `同步使用者雷姆`     |
| `同步使用者创建器`    |
| `同步使用器创建日`    |
| `同步使用者改变交换日`  |
| `同步使用器最后日志`   |
| `同步使用者丁罗尔斯`   |
| `同步使用者丁雷亚尔姆斯` |
| `同步使用者记忆`     |
| `同步使用`        |
| `同步使用者关系`     |
| `同步使用器`       |

请注意，只有当属性包含一个值时，才会收集属性。
