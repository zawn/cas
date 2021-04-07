---
layout: 默认
title: CAS-属性解析
category: 属性
---

# 属性解析

属性解析策略由 [人员目录项目](https://github.com/apereo/person-directory)。 人员目录依赖项自动与CAS服务器捆绑在一起。 因此， 声明其他依赖项。 此人员目录项目支持LDAP和JDBC属性解析， 缓存，来自多个属性源的属性聚合等。

<div class="alert alert-info"><strong>默认缓存策略</strong><p>默认情况下，将
属性缓存到SSO会话的长度。
这意味着，尽管“个人目录”提供的基础组件可能具有
个不同的缓存模型，但是默认情况下，属性（从

上刷新和再次检索CAS透视图，只要存在SSO会话即可。</p></div>

## 行政端点

CAS提供了以下端点：

| 终点                          | 描述                                                        |
| --------------------------- | --------------------------------------------------------- |
| `resolveAttributes /{name}` | 调用CAS [属性解析](Attribute-Resolution.html) 引擎以找到 `{name}`属性。 |

## 人员目录

解决各种潜在来源中的人员和属性的框架。 它由一组组件组成，这些组件从JDBC，LDAP等中检索，缓存，解析，聚合，

要查看与解析主体有关的CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#principal-resolution)。

定义和配置属性源，以描述要为每个经过身份验证的主体 然后，该全局属性集由 服务管理器根据特定于服务的属性释放规则进行过滤。

请注意，可以为每个属性存储库源分配一个唯一的标识符，以用于其他过滤。 由人员目录提供的属性解析引擎 还可以配置为仅查询属性仓库源的所有信息，而不是选择所有属性， *将* 属性检索的任务 推迟到身份验证过程中的后续阶段，例如 [释放属性](Attribute-Release-Caching.html)。

<div class="alert alert-info"><strong>主要决议</strong><p>请注意，在大多数情况下（即使不是全部情况下），
CAS身份验证也可以从身份验证源检索和解析属性，这将
消除了对单独配置解析器的需求，特别是在身份验证和属性源都相同的情况下。
仅在来源不同或需要处理更高级的属性
分辨率用例（例如级联，合并等） <a href="../installation/Configuring-Principal-Resolution.html">有关更多信息，请参见本指南</a></p></div>

解析程序的目标是为CAS构造一个最终可识别的经过身份验证的主体，该主体内部包含许多属性。 人员目录解析器的行为是尝试定位主体ID，在大多数情况下，它与 ID相同，或者可以通过自定义属性进行记录。 然后，解析程序开始从定义的属性存储库构造属性。 如果意识到使用自定义属性来确定主体ID，并且还将同一属性设置为要收集到最终属性集中，则它将从最终集合中删除该属性。

请注意，默认情况下，CAS自动创建适用于LDAP，JDBC等的属性存储库源。 如果您还需要更多东西，则将需要采取更详尽的措施来定义Bean配置。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-attributes)。 有关人员目录及其可配置源 [更多信息，请参见此处](https://github.com/apereo/person-directory)。

### JDBC

CAS确实允许从各种SQL数据库中检索属性。 要了解如何配置数据库驱动程序，请参阅本指南</a>

。</p> 

可以基于以下机制定义JDBC属性源：



#### 单排

设计用于针对将一行映射到一个用户的表。 该表格式的示例为：

| uid   | 名    | 姓     | 电子邮件                 |
| ----- | ---- | ----- | -------------------- |
| `史密斯` | `约翰` | `史密斯` | `jsmith@example.org` |




#### 多行

设计用于针对将一行映射到一个用户的表。 该表格式的示例为：

| uid   | attr_name | attr_value           |
| ----- | --------- | -------------------- |
| `史密斯` | `名`       | `约翰`                 |
| `史密斯` | `姓`       | `史密斯`                |
| `史密斯` | `电子邮件`    | `jsmith@example.org` |


您将需要在配置中 `attr_name` 列 `attr_value` 列



## 例子

假设将CAS配置为根据Active Directory进行身份验证。 详细信息定义在 `sAMAccountName`身份验证。

| 属性               | 价值       |
| ---------------- | -------- |
| `sAMAccountName` | `约翰·史密斯` |
| `cn`             | `约翰·史密斯` |




### 范例＃1

如果将解析程序配置为使用 `sAMAccoutName` 作为主体ID的属性，则在身份验证完成后，解析程序会尝试 从属性存储库源构造属性，它将 `sAMAccoutName` 作为属性，并看到主体ID为 由 `sAMAccoutName`创建。 因此，它将从属性中 `sAMAccoutName` 最终结果是ID为 `约翰·史密斯` `cn` 属性为 ``。



### 范例＃2

如果将解析程序配置为使用 `cn` 作为主体ID的属性，则在身份验证完成后，解析程序将尝试从属性存储库源中构造 然后，它将 `sAMAccoutName` 作为属性，并看到将由 `cn`创建主体ID。 因此它将从属性中 `cn` 最终结果是ID为 `的委托人John Smith` ，其 `sAMAccountName` 属性为 `johnsmith`。



## 属性定义

CAS属性可以用其他元数据修饰，以后可以根据 要求和与目标应用程序集成的性质来使用这些元数据。 要了解 ，请 [请参阅本指南](Attribute-Definitions.html)。
