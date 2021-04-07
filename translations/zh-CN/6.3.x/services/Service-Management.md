---
layout: 默认
title: CAS-服务管理
category: 服务
---

# 服务管理

CAS服务管理工具允许CAS服务器管理员声明和配置 （CAS客户端）可以使用哪种方式使用CAS的服务。 服务管理工具的核心组件是 服务注册表，该注册表存储一个或多个注册的服务，其中包含驱动许多CAS行为的元数据：

* [授权服务](Configuring-Service-Access-Strategy.html) 控制哪些服务可以参与CAS SSO会话。
* 强制认证-提供对强制认证的管理控制。
* [属性版本](../integration/Attribute-Release.html) 为服务提供用户详细信息以进行授权和个性化。
* [代理控制](Configuring-Service-Proxy-Policy.html) 通过授予/拒绝代理认证功能进一步限制授权服务。
* [主题控件](../ux/User-Interface-Customization.html) 定义要用于特定服务的替代CAS主题。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry)。

## 行政端点

CAS提供了以下端点：

| 终点                         | 描述                                                                                                                                                                                                     |
| -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `已注册的服务`                   | [CAS服务注册表](Service-Management.html)的JSON表示形式。 端点还可以接受mime类型的 `application / vnd.cas.services + yaml` 来产生YAML输出。 甲 `GET` 与的参数选择操作 `/{id}` 将获取一个特定的服务定义。 甲 `DELETE` 与的参数选择操作 `/{id}` 将通过它的id删除所述特定服务的定义。 |
| `exportRegisteredServices` | [CAS服务注册表](Service-Management.html)的ZIP文件表示形式。                                                                                                                                                         |
| `importRegisteredServices` | 将服务定义导入到 [CAS服务注册表](Service-Management.html)。 请求主体应该是服务定义本身。                                                                                                                                           |

## 服务管理Web应用程序

服务管理Web应用程序是一个独立的Web应用程序，可以与提供了GUI 来管理服务注册表数据的CAS一起部署。 管理Web应用程序 *必须* CAS服务器本身共享相同的注册表配置，以便整个系统可以加载相同的服务数据。 要了解有关 管理webapp [更多信息，请参阅本指南](Installing-ServicesMgmt-Webapp.html)。

## 注册服务

注册的服务提供以下元数据：
描述该属性集的策略允许释放到应用程序，以及清除某些所需的任何其他过滤逻辑。 有关属性释放和过滤器的更多详细信息，请参见本指南</a> </td> </tr> 

指示将什么值作为“用户名”的提供程序配置应发送回应用程序。 有关属性释放和过滤器的更多详细信息，请参见本指南</a> </td> </tr> 

概述和访问此服务规则的策略配置。 它描述了服务是被允许，被授权参与SSO还是可以基于特定的属性定义角色（又称为RBAC）从CAS角度被授予访问权限。 有关属性释放和过滤器的更多详细信息，请参见本指南</a> </td> </tr> 

与此服务关联的公共密钥，用于通过加密CAS验证协议响应中的某些元素和属性来授权请求，例如 [表示PGT](../installation/Configuring-Proxy-Authentication.html) 或 [表示证书](../integration/ClearPass.html)。 有关属性释放和过滤器的更多详细信息，请参见本指南</a> </td> </tr> 

</tbody> </table>

<div class="alert alert-info"><strong>服务类型</strong><p>请注意，尽管以上属性适用于所有 <strong>通用</strong> 服务定义，但CAS中还有其他服务类型可能会被激活和需要，具体取决于所使用的协议和客户端应用程序的性质。 始终检查专用指南以了解您所考虑的功能（即 OAuth，SAML等）。</p></div>

### 服务访问策略

[有关更多信息，请参见本指南](Configuring-Service-Access-Strategy.html)



### 代理认证策略

[有关更多信息，请参见本指南](Configuring-Service-Proxy-Policy.html)



### 要求的验证

[有关更多详细信息，请参见本指南](Configuring-Service-AuthN-Policy.html)



### 标签 & 属性

[有关更多信息，请参见本指南](Configuring-Service-Custom-Properties.html)



### 联系人 & 所有者

[有关更多信息，请参见本指南](Configuring-Service-Contacts.html)



### 过期政策

[有关更多信息，请参见本指南](Configuring-Service-Expiration-Policy.html)



### 匹配策略

有关更多信息，请参见 [本指南](Configuring-Service-Matching-Strategy.html)



## 贮存

以下选项可用于在CAS中存储服务。

| 贮存        | 描述                                          | 用法                                         |
| --------- | ------------------------------------------- | ------------------------------------------ |
| 记忆        | [参见本指南](InMemory-Service-Management.html)。  | 将服务定义XML存储在内存中。 更改需要重新包装CAS并重新启动服务器        |
| JSON格式    | [参见本指南](JSON-Service-Management.html)。      | 将服务定义存储在平面JSON文件中。 高可用性部署需要复制服务定义。         |
| YAML      | [参见本指南](YAML-Service-Management.html)。      | 与 `JSON`相同。                                |
| GIT       | [参见本指南](Git-Service-Management.html)。       | 将服务定义存储在Git存储库中。 高可用性部署的候选人。               |
| MongoDb   | [参见本指南](MongoDb-Service-Management.html)。   | 将服务定义存储在MongoDb中。 高可用性部署的候选人。              |
| 雷迪斯       | [参见本指南](Redis-Service-Management.html)。     | 将服务定义存储在Redis中。 高可用性部署的候选人。                |
| LDAP      | [参见本指南](LDAP-Service-Management.html)。      | 将服务定义存储在目录服务器中。 高可用性部署的候选人。                |
| JPA       | [参见本指南](JPA-Service-Management.html)。       | 将服务定义存储在关系数据库（Oracle，MySQL等）中。 高可用性部署的候选人。 |
| Couchbase | [参见本指南](Couchbase-Service-Management.html)。 | 将服务定义存储在Couchbase中。 高可用性部署的候选人。            |
| Couchbase | [参见本指南](CouchDb-Service-Management.html)。   | 将服务定义存储在CouchDb中。 高可用性部署的候选人。              |
| DynamoDb  | [参见本指南](DynamoDb-Service-Management.html)。  | 将服务定义存储在DynamoDb中。 高可用性部署的候选人。             |
| 亚马逊S3     | [参见本指南](AmazonS3-Service-Management.html)。  | 将服务定义存储在Amazon S3存储桶中。 高可用性部署的候选人。         |
| 宇宙Db      | [参见本指南](CosmosDb-Service-Management.html)。  | 将服务定义存储在Azure CosmosDb中。 高可用性部署的候选人。       |
| 卡桑德拉      | [参见本指南](Cassandra-Service-Management.html)。 | 将服务定义存储在Apache Cassandra中。 高可用性部署的候选人。     |
| 休息        | [参见本指南](REST-Service-Management.html)。      | 将您自己的服务注册表实现设计为REST API。 高可用性部署的候选人。       |
| 风俗        | [参见本指南](Custom-Service-Management.html)。    | 使用CAS API作为扩展来设计您自己的服务注册表。 高可用性部署的候选人。     |




### 我该如何选择？

菜单上有各种各样的服务注册表。 选择标准概述如下：

- 选择一种您最熟悉的技术，并具有技巧和耐心来对故障进行排除，调整和扩展以取得胜利。
- 选择一种不会强制将CAS配置绑定到群集中任何单个服务器/节点的技术，因为这会带来自动扩展问题和手动工作。
- 选择一种适合您的网络和防火墙配置，并且根据您的网络拓扑具有足够性能和可靠性的技术。
- 选择一种技术，该技术在 *的预期负载*下会显示出令人鼓舞的结果，并已进行了 [性能和压力测试](../high_availability/High-Availability-Performance-Testing.html)。
- 选择一种尽可能不依赖外部流程，系统和人工工作，自力更生且自成体系的技术。
