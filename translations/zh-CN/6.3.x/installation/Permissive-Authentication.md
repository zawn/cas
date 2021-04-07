---
layout: 默认
title: CAS-许可认证
category: 验证
---

# 允许身份验证

允许的身份验证组件分为两类：接受 的组件和接受来自服务器上文件资源的一组凭据的组件。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-generic</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#file-authentication)。

## 密码文件示例

```bash
scott :: password
bob :: password2
```


## JSON文件

密码文件也可以指定为JSON资源，从而允许以 到0指定其他帐户详细信息，这些详细信息对于开发和基本测试非常有用。 文件的轮廓可以定义为：

```json
{
  “ @class”：“ java.util.LinkedHashMap”，
  “ casuser”：{
    “ @class”：“ org.apereo.cas.adaptors.generic.CasUserAccount”，
    “ password”：“ Mellon”，
    “ attributes”：{
      “ @class”：“ java.util.LinkedHashMap”，
      “ firstName”：[“ java.util.List”，[“ Apereo”]]，
      “ lastName”：[“ java。 util.List“，[” CAS“]]
    }，
    ” status“：” OK“，
    ” expirationDate“：” 2050-01-01“
  }
}
```

所接受的状态是 `行`， `LOCKED`， `DISABLED`， `EXPIRED` 和 `MUST_CHANGE_PASSWORD`。 要查看 相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-authentication)。
