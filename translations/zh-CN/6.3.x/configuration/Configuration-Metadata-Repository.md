---
layout: 违约
title: CAS 配置元数据存储库
category: 配置
---

# 配置元数据

CAS 将附带元数据文件，提供所有支持的配置属性和设置的详细信息。 所有配置元数据 的存储库通过处理代码库内 `@ConfigurationProperties` 注释的所有项目，在生成和发布时间自动生成。 然后，此存储库 可用于其他查询和筛选，以查找给定属性的定义或查找适用于 CAS 中特定组项（如 LDAP 身份验证）的相关设置 。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-核心-配置-元数据-存储库</artifactId>
  <version>${cas.version}</version>
</dependency>
```

也可以使用 CAS 执行器端点访问和查询配置元数据。 [本指南](../monitoring/Monitoring-Statistics.html) 了解更多。

## 行政终点

CAS 提供以下端点：

| 端点       | 描述                                                                                                                                                           |
| -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `配置梅塔达塔` | 暴露 [CAS 配置元数据](Configuration-Metadata-Repository.html) 可用于查询设置。 默认端点显示 CAS 认可的所有设置的列表，并增加了以部分 `名称` 作为选择器搜索特定 CAS 设置的功能。 默认情况下，此接口会与 CAS 联运，您不需要做任何特别的事情来启用它。 |

## 通过命令线的元数据

元数据存储库也可以使用命令行作为单独的实用程序进行检查。 [有关详细信息，请参阅本指南](../installation/Configuring-Commandline-Shell.html) 。
