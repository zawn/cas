---
layout: 违约
title: CAS - LDAP 服务注册处
category: 服务业
---

# LDAP 服务注册表

服务注册表实施，将服务存储在 LDAP 目录中，并尝试 *将* 服务记录映射到 LDAP 条目中，以便配置 设置，以便检索、搜索和持续执行服务定义。 默认情况下，条目被分配 `对象类` ，该 `cas 注册服务` 属性，并由 `uid` 属性查找。

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-ldap-服务-注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

默认映像器支持以下可选项目：

| 田        | 默认值    |
| -------- | ------ |
| `对象类`    | 卡斯注册服务 |
| `服务定义归因` | 描述     |
| `伊德属性`   | 乌伊德    |

默认情况下，服务定义存储在 `服务定义属性` 属性中， JSON 对象。 JSON 的格式和语法与 [JSON 服务注册处的格式和语法](JSON-Service-Management.html)相同。 就模式而言，就全部而言。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-service-registry)。

## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>
