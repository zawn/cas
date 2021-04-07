---
layout: 违约
title: CAS - 允许认证
category: 认证
---

# 允许身份验证

允许身份验证组件分为两类：接受 直接存储在配置中的一组凭据的组件，以及接受服务器上文件资源中的一组凭据的组件。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持通用</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#file-authentication)。

## 示例密码文件

```bash
斯科特：:p剑
鲍勃：:p剑2
```


## 杰森文件

密码文件也可以指定为 JSON 资源，从而允许用户 指定其他帐户详细信息，这些详细信息大多可用于开发和基本测试。 文件的大纲可定义为：

```json
•
  "@class"： "java. util. Linkedhashmap"，
  "卡瑟"： [
    "@class"： "org. apereo. cas. 适配器. 通用. 卡塞帐户"，
    "密码"： "梅隆"，
    "属性"： [
      "@class"： "java. util. Linkedhashmap"，
      "第一个名字"： [ "java. util. list"， ["Apereo"]，
      "最后名称"："java.ul.list"，"CAS"]
    [，
    "状态"："确定"，
    "到期日期"："2050-01-01"
  [
]
```

接受的地位 `确定`， `锁定`， `残疾`， `过期` 和 `MUST_CHANGE_PASSWORD`。 有关中科院物业的 清单，请 [本指南](../configuration/Configuration-Properties.html#json-authentication)。
