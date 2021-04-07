---
layout: 默认
title: CAS-LDAP服务注册表
category: 服务
---

# LDAP服务注册表

服务注册表实现，该服务将服务存储在LDAP目录中，并尝试将 ** 服务记录映射到LDAP条目0，以便配置 设置以进行服务定义的检索，搜索和持久化。 默认情况下，条目被分配 `对象类` ，即 `casRegisteredService` 属性，并由 `uid` 属性查找。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-ldap-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 配置

默认的映射器支持以下可选项：

| 场地                           | 默认值                  |
| ---------------------------- | -------------------- |
| `objectClass`                | casRegisteredService |
| `serviceDefinitionAttribute` | 描述                   |
| `idAttribute`                | uid                  |

默认情况下，服务定义存储在 `serviceDefinitionAttribute` 属性内，作为 JSON对象。 JSON的格式和语法与 [JSON Service Registry](JSON-Service-Management.html)。 就架构而言，仅此而已。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-service-registry)。

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)
