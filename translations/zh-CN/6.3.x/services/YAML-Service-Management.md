---
layout: 违约
title: CAS - 雅姆服务注册处
category: 服务业
---

# 雅姆服务注册处

此注册表在应用程序上下文初始化时间从 YAML 配置文件中读取服务定义。 YAML 文件预计将在配置的目录位置内找到，此注册表将反复查看目录结构 以查找相关文件。

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - yaml - 服务 - 注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#yaml-service-registry)。


样本 YAML 文件如下：

```yml
--- !<org.apereo.cas.services.RegexRegisteredService>
服务Id："testId"
名称："YAML"
ID：1000
描述："描述"
属性发布政策： ！<org.apereo.cas.services.ReturnAllAttributeReleasePolicy> {}
访问战略： ！启用<org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy>
  ：真实
  可归纳：真实
```

<div class="alert alert-warning"><strong>亚姆尔验证</strong><p>
包含类名提示（<code>！&lt;classname&gt;</code>）的标签会导致许多 YAML 验证器出现问题。 如果您需要验证您的YML，请尝试删除这些标签以进行验证。 请记住，如果您不包括任何属性，则标签后可能需要一张空地图（<code>[}</code>） 。
</p></div>

<div class="alert alert-warning"><strong>聚类服务</strong><p>
您必须考虑，如果您的 CAS 服务器部署是聚类的，则聚类中的每个 CAS 节点必须具有
访问与另一组配置文件相同的访问权限，或者您可能需要设计一个策略来保持
更改从一个节点同步到下一个节点。
</p></div>

服务注册表还能够自动检测指定目录的更改。 它将监控更改以识别 文件添加、删除和更新，并将自动刷新 CAS，以便更改立即发生。

<div class="alert alert-info"><strong>逃避字符</strong><p>
请确保blob中的所有字段值都正确逃脱，特别是服务ID。 如果服务被定义为常规表达式，则需要加倍逃脱某些 regex 结构，如"" 和"\d"。
</p></div>

新文件的命名约定建议如下：

```bash
YAML文件名=服务名+"-"+服务号码ID+"。yml"
```

请记住，由于文件是根据 `服务名称`创建的，因此您需要确保文件名称</a> 被视为无效的

字符不用作名称的一部分。 此外，请注意，CAS **必须** 包含服务定义文件的目录上获得完整的读/写权限。</p>

<div class="alert alert-warning"><strong>重复服务</strong><p>
当您向目录添加更多文件时，您需要绝对确保
没有两个服务定义具有相同的 ID。 如果发生这种情况，加载一个定义将停止加载另一个定义。 虽然可以任意选择服务 id
，但请确保所有服务数字标识符都是唯一的。 如果发现重复的数据，CAS 还将
输出警告。
</p></div>

## 复制

如果 CAS 部署在聚类中，则所有 CAS 节点必须保持服务定义文件同步。 请 [本指南](Configuring-Service-Replication.html) 查看，以了解有关可用选项的更多信息。
