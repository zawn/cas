---
layout: 默认
title: CAS配置元数据存储库
category: 配置
---

# 配置元数据

CAS附带了元数据文件，这些文件提供了所有受支持的配置属性和设置的详细信息。 通过在代码库中 `@ConfigurationProperties` 注释的项，将在构建和发布时自动生成所有配置元数据 然后，该存储库 可用于其他查询和过滤，以查找给定属性的定义或找到适用于CAS中特定功能组（例如LDAP身份验证）的

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas服务器核心配置元数据存储库</artifactId>
  <version>${cas.version}</version>
</dependency>
```

还可以使用CAS执行器端点来访问和查询配置元数据。 [请参阅本指南](../monitoring/Monitoring-Statistics.html) 了解更多信息。

## 行政端点

CAS提供了以下端点：

| 终点      | 描述                                                                                                                                                         |
| ------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `配置元数据` | 公开 [可用于查询设置的CAS配置元数据](Configuration-Metadata-Repository.html) 默认端点显示了CAS识别的所有设置的列表，并具有通过其部分 `名称` 作为选择器来搜索特定CAS设置的附加功能。 默认情况下，此接口随CAS一起提供，您无需执行任何特殊操作即可启用它。 |

## 通过命令行元数据

也可以使用命令行作为单独的实用程序来检查元数据存储库。 [有关更多信息，请参见本指南](../installation/Configuring-Commandline-Shell.html)
