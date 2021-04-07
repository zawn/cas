---
layout: 违约
title: CAS - 服务管理
category: 服务业
---

# 服务管理

CAS 服务管理设施允许 CAS 服务器管理员申报和配置哪些服务 （CAS 客户端）可以以哪些方式使用 CAS。 服务管理设施的核心组成部分是 服务注册表，其中存储一个或多个包含元数据的注册服务，这些元数据驱动着许多 CAS 行为：

* [授权服务](Configuring-Service-Access-Strategy.html) - 控制哪些服务可以参加 CAS SSO 会议。
* 强制身份验证 - 为强制身份验证提供行政控制。
* [属性发布](../integration/Attribute-Release.html) - 为授权和个性化服务提供用户详细信息。
* [代理控制](Configuring-Service-Proxy-Policy.html) - 通过授予/拒绝代理身份验证功能进一步限制授权服务。
* [主题控制](../ux/User-Interface-Customization.html) - 定义用于特定服务的备用 CAS 主题。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry)。

## 行政终点

CAS 提供以下端点：

| 端点       | 描述                                                                                                                                                                             |
| -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `注册服务`   | 提供 [CAS服务注册表](Service-Management.html)的JSON表示。 端点还可以接受哑剧类型的 `应用程序/vnd.cas.服务+yaml` ，以产生YAML输出。 具有 `/{id}` 参数选择器的 `获取` 操作将获取特定的服务定义。 使用 `/{id}` 参数选择器的 `删除` 操作将删除其 ID 中的特定服务定义。 |
| `出口注册服务` | 提供 [CAS服务注册表的ZIP文件表示](Service-Management.html)。                                                                                                                                |
| `进口注册服务` | 将服务定义导入 [CAS 服务注册表](Service-Management.html)。 请求机构预计将是服务定义本身。                                                                                                                  |

## 服务管理网络应用

服务管理 Webapp 是一个独立的 Web 应用程序，可沿 CAS 侧部署，提供 GUI 来管理服务注册表数据。 管理网络应用程序 *必须* 共享与CAS服务器本身相同的注册表配置 ，以便整个系统可以加载相同的服务数据。 要了解有关管理webapp 更多信息，请 [](Installing-ServicesMgmt-Webapp.html)查看本指南。

## 注册服务

注册服务提供以下元数据：
描述逻辑服务</a> 需要 常规表达。 逻辑服务定义服务或服务所在的一个或多个网址。 必须 **仔细** 地对网址模式的定义进行，因为它可以打开安全漏洞。</td> </tr> 

当服务请求票证时，可用于自定义 CAS UI 的可选主题名称。 值可以使用 [弹簧表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。 有关详细信息，请参阅本指南</a> 。</td> </tr> 

确定该服务是否能够代理身份验证。 有关详细信息，请参阅本指南</a> 。</td> </tr> 

作为全球认证引擎的补充或覆盖的认证策略。 有关详细信息，请参阅本指南</a> 。</td> </tr> 

定义在启动注销协议后应如何处理此服务。 可接受的值是 `LogoutType.BACK_CHANNEL`、 `LogoutType.FRONT_CHANNEL` 或 `注销`类型。 有关注销的更多详细信息，请参阅本指南</a> 。</td> </tr> 

定义 CAS 应如何响应此服务的请求。 有关详细信息，请参阅本指南</a> 。</td> </tr> 

此服务接收注销请求的 URL 终点。 有关详细信息，请参阅本指南</a> </td> </tr> 

指定与拥有应用程序的服务相关的联系人的集合。 有关详细信息，请参阅本指南</a> 。</td> </tr> 

指定用于根据身份验证请求匹配服务定义的策略。 有关详细信息，请参阅本指南</a> 。</td> </tr> </tbody> </table>

<div class="alert alert-info"><strong>服务类型</strong><p>请注意，虽然上述属性适用于所有 <strong>通用</strong> 服务定义，但 CAS 中可能会激活并需要其他服务类型，具体取决于所使用的协议和客户端应用的性质。 始终检查专用指南，了解您心目中的能力（即 非授权、山姆尔等）。</p></div>

### 服务访问策略

[有关详细信息，请参阅本指南](Configuring-Service-Access-Strategy.html) 。



### 代理身份验证策略

[有关详细信息，请参阅本指南](Configuring-Service-Proxy-Policy.html) 。



### 所需身份验证

[有关详细信息，请参阅本指南](Configuring-Service-AuthN-Policy.html) 。



### 标签 & 属性

[有关详细信息，请参阅本指南](Configuring-Service-Custom-Properties.html) 。



### 联系人 & 所有者

[有关详细信息，请参阅本指南](Configuring-Service-Contacts.html) 。



### 到期政策

[有关详细信息，请参阅本指南](Configuring-Service-Expiration-Policy.html) 。



### 匹配策略

有关详细信息，请参阅本指南</a> 。</p> 



## 存储

以下选项可用于在 CAS 中存储服务。

| 存储            | 描述                                         | 用法                                      |
| ------------- | ------------------------------------------ | --------------------------------------- |
| 记忆            | [见本指南](InMemory-Service-Management.html)。  | 存储服务定义存储在内存中的XML。 更改需要 CAS 重新包装和服务器重新启动 |
| 杰森            | [见本指南](JSON-Service-Management.html)。      | 在扁平的JSON文件中存储服务定义。 医管局的部署需要复制服务定义。      |
| 亚姆尔           | [见本指南](YAML-Service-Management.html)。      | 和 `· 杰森 ·`一样                            |
| 吉特            | [见本指南](Git-Service-Management.html)。       | Git 存储库中的存储服务定义。 医管局部署的候选人。             |
| 蒙古德布          | [见本指南](MongoDb-Service-Management.html)。   | 蒙古数据库中的商店服务定义。 医管局部署的候选人。               |
| 雷迪斯           | [见本指南](Redis-Service-Management.html)。     | 雷迪斯的商店服务定义。 医管局部署的候选人。                  |
| 阿尔达普          | [见本指南](LDAP-Service-Management.html)。      | 目录服务器中的存储服务定义。 医管局部署的候选人。               |
| 日本经济与经济、经济、经济 | [见本指南](JPA-Service-Management.html)。       | 在关系数据库（甲骨文、MySQL 等）中存储服务定义。 医管局部署的候选人。  |
| 沙发基地          | [见本指南](Couchbase-Service-Management.html)。 | 沙发基地中的商店服务定义。 医管局部署的候选人。                |
| 沙发基地          | [见本指南](CouchDb-Service-Management.html)。   | 库奇数据库中的商店服务定义。 医管局部署的候选人。               |
| 迪纳莫德布         | [见本指南](DynamoDb-Service-Management.html)。  | Dynamo数据库中的商店服务定义。 医管局部署的候选人。           |
| 亚马逊 S3        | [见本指南](AmazonS3-Service-Management.html)。  | 亚马逊 S3 存储桶中的商店服务定义。 医管局部署的候选人。          |
| 宇宙数据库         | [见本指南](CosmosDb-Service-Management.html)。  | 在蔚蓝宇宙数据库中存储服务定义。 医管局部署的候选人。             |
| 卡珊德拉          | [见本指南](Cassandra-Service-Management.html)。 | 阿帕奇卡桑德拉中的商店服务定义。 医管局部署的候选人。             |
| 休息            | [见本指南](REST-Service-Management.html)。      | 将您自己的服务注册表实现设计为 REST API。 医管局部署的候选人。    |
| 习惯            | [见本指南](Custom-Service-Management.html)。    | 使用 CAS ABI 作为扩展设计您自己的服务注册表。 医管局部署的候选人。  |




### 如何选择？

菜单上有各种各样的服务注册。 选择标准概述如下：

- 选择您最熟悉的技术，并具有排除故障、调整和扩展以赢得胜利的技能和耐心。
- 选择一种不会强制 CAS 配置与群集中的任何单个服务器/节点绑定的技术，因为这将显示自动缩放问题和手动工作。
- 选择一种与您的网络和防火墙配置配合良好的技术，并且基于您的网络拓扑技术，该技术性能良好且足够可靠。
- 选择一种技术，显示有希望的结果下， *你的预期负荷*，运行 [性能和压力测试](../high_availability/High-Availability-Performance-Testing.html)。
- 选择一种不依赖于外部流程、系统和体力劳动的技术，是自力更生和自我控制的。
