---
layout: 默认
title: CAS-YAML服务注册表
category: 服务
---

# YAML服务注册表

该注册表在应用程序上下文初始化时从YAML配置文件中读取服务定义。 预期会在配置的目录位置内找到YAML文件，并且该注册表将递归地 查找相关文件。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-yaml-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yaml-service-registry)。


以下是一个示例YAML文件：

```yml
---！<org.apereo.cas.services.RegexRegisteredService>
serviceId：“ testId”
名称：“ YAML”
id：1000
描述：“ description”
attributeReleasePolicy ：！<org.apereo.cas.services.ReturnAllAttributeReleasePolicy> {}
accessStrategy ：！<org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy>
  启用：是
  ssoEnabled：是
```

<div class="alert alert-warning"><strong>YAML验证</strong><p>
包含类名提示（<code>！&lt;classname&gt;</code>）的标签会导致许多YAML验证程序出现问题。 如果您需要验证您的YAML，请尝试删除这些标签以进行验证。 请记住，如果您不包括属性的任何属性，则标记后可能需要一个空映射（<code>{}</code>
</p></div>

<div class="alert alert-warning"><strong>集群服务</strong><p>
您必须考虑到，如果您的CAS服务器部署是集群的，则集群中的每个CAS节点必须
访问权限，否则您可能必须制定一种策略，以使
更改从一个节点同步到另一个节点。下一个。
</p></div>

服务注册表还能够自动检测对指定目录的更改。 它将监视更改以识别 文件的添加，删除和更新，并将自动刷新CAS，因此更改会立即发生。

<div class="alert alert-info"><strong>转义字符</strong><p>
请确保正确地转义了Blob中的所有字段值，尤其是对于服务ID。 如果将服务定义为正则表达式，则某些正则表达式结构（例如“。”）。和“ \ d”必须加倍转义。
</p></div>

建议新文件的命名约定如下：

```bash
YAML fileName = serviceName +“-” + serviceNumericId +“ .yml”
```

请记住，由于文件是基于 `serviceName`创建的，因此您需要确保不会将对</a>

字符用作名称的一部分。 此外，请注意，必须 **** 对包含服务定义文件的目录的完全读取/写入权限。</p>

<div class="alert alert-warning"><strong>重复服务</strong><p>
当您向目录中添加更多文件时，需要绝对确保没有两个服务定义
具有相同的ID。 如果发生这种情况，加载一个定义将停止加载另一个定义。 尽管可以任意选择服务ID
，但是请确保所有服务数字标识符都是唯一的。 如果发现重复数据，CAS也将输出警告

</p></div>

## 复写

如果将CAS部署在群集中，则必须对所有CAS节点保持服务定义文件同步。 请 [本指南](Configuring-Service-Replication.html) 以了解有关可用选项的更多信息。
