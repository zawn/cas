---
layout: 默认
title: CAS-发行说明
category: 规划
---

# RC1发行说明

强烈建议您在发布候选版本时加以利用。 等待 `GA` 发布只会使 感到不愉快。 `GA` 只是 [个标签，仅此而已](https://apereo.github.io/2017/03/08/the-myth-of-ga-rel/)。 请注意，CAS 版本是 *严格的* 个基于时间的版本。它们不是预定的，也不是基于特定的基准，统计数据或功能完成情况。 要使 ，强烈建议您从实验候选版本和/或后续快照开始，并尽早开始。

## Apereo会员资格

如果您从Apereo CAS作为免费和开源软件受益，我们邀请您 [加入Apereo Foundation](https://www.apereo.org/content/apereo-membership) 并以最适合您的部署的能力为该项目提供财务支持。 请注意，所有开发活动都是 *几乎完全* 的活动，没有任何期望，承诺或附加条件。 拥有能够更好地维持工程活动的财务手段，将使开发人员社区可以 *专用时间和承诺的* 时间，尤其是在及时解决关键和安全问题时。 资金将确保对您所依赖的软件的支持，您将获得优势并以Apereo以及当时的CAS项目的运行方式进行运营。 如果您认为CAS部署是身份和访问管理生态系统的关键部分，那么这是一个可行的选择。

## 参与其中

- 立即开始CAS部署。 试用功能，然后 [分享反馈](/cas/Mailing-Lists.html)。
- 更好的是， [贡献了补丁](/cas/developer/Contributor-Guidelines.html)。
- 建议并应用文档改进。

## 资源

- [发布时间表](https://github.com/apereo/cas/milestones)
- [发行政策](/cas/developer/Release-Policy.html)

## 覆盖

在 [CAS WAR叠加层](../installation/WAR-Overlay-Installation.html)`gradle.properties` 中，调整以下设置：

```properties
cas.version = 6.3.0-RC1
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>此版本对最低系统/平台要求没有更改。
</div>

## 新增 & 值得注意

### Spring Boot 2.3

CAS已切换到Spring Boot `2.3.x.RELEASE`。 CAS覆盖图也已更新，可以与此更改同步。 尽管 归类为Spring Boot功能/次要版本，但鉴于该框架在CAS中的大量使用，升级的效果可能会更加明显。

以下设置受升级影响，应进行以下调整以匹配：

```properties
server.tomcat.threads.min-spare = 10
server.tomcat.threads.max = 200

server.servlet.encoding.charset = UTF-8
server.servlet.encoding.enabled = true
server.servlet.encoding。 force = true

management.endpoint.health.status.order =警告，向下，OUT_OF_SERVICE，未知，向上

server.tomcat.connection-timeout = PT20S
server.tomcat.max-http-form-post-size = 2097152

server.tomcat.remoteip.port-header = X-Forwarded-Port
.tomcat.remoteip.protocol-header = X-Forwarded-Proto
server.tomcat.remoteip.protocol-header-https-value = https
服务器。 tomcat.remoteip.remote-ip-header = X-FORWARDED-FOR
```

升级过程中未发现兼容性问题，并且配置名称空间在很大程度上不受CAS的影响。 也就是说，请怀疑并验证。

### 通过CodeCov进行测试

现在，代码库中所有模块的CAS测试覆盖率已达到 `82％` 并继续攀升。 附加验证规则也将应用为 以使所有低于此阈值的拉取请求失败。 随着工作的进展，该领域将受到严密监控并改善为 ，以期在最终发布Google Analytics（分析）之前 `85％` 当然，这不会成为最终版本的阻碍。

### Redis集群支持

Redis支持和配置名称空间现在能够支持与Redis集群的连接。

### DynamoDb用于CAS事件的存储

[CAS事件](../installation/Configuring-Authentication-Events.html) 存储在DynamoDb实例中。

### Couchbase可接受的使用政策

[现在可以通过Couchbase数据库管理和跟踪“可接受的使用策略](../webflow/Webflow-Customization-AUP.html)

### 通过Git存储库的SAML2元数据

现在可以从Git存储库中获取和提取[SAML2元数据](../installation/Configuring-SAML2-DynamicMetadata.html) 此功能支持服务和身份提供程序构件。

### 多因素身份验证Web流

现在，通过Webflow自动配置（而不是静态XML定义）在运行时 多因素身份验证提供程序（即Google Authenticator，Authy等）的Webflow定义。 当涉及到定制时，这提供了更好的灵活性以及测试覆盖率。

### U2F多因素身份验证可信设备

现在将对 [多因素身份验证受信任的设备/浏览器](../mfa/Multifactor-TrustedDevice-Authentication.html) 支持扩展为 使其还包括 [](../mfa/FIDO-U2F-Authentication.html)。 此外，一些新的行政执行端点 提交注册设备或删除/注销设备上的汇报。

### 认证执行器端点

呈现 新的 [管理执行器端点](../installation/Configuring-Authentication-Components.html) ，以报告已注册的身份验证处理程序和策略。

### 用于U2F多因素身份验证的DynamoDb存储

[U2F Multifactor Authentication](../mfa/FIDO-U2F-Authentication.html) 设备存储在DynamoDb实例中。

### Gradle远程构建缓存

现在，CAS Gradle构建已连接到远程构建缓存服务器，以最大程度地提高连续集成构建的性能。

![图像](https://user-images.githubusercontent.com/1205228/84562682-9d46f300-ad6b-11ea-8ed8-3042a3facbec.png)

### Google Authenticator帐户注册

现在，用于多因素身份验证的Google身份验证器得到了增强，可以在完成帐户注册过程之前要求令牌。 所提供的令牌经过验证后，该帐户将在CAS中注册，并准备进行后续的多因素身份验证。

![图像](https://user-images.githubusercontent.com/1205228/86023135-83323380-ba40-11ea-8d16-4fe8ff560c99.png)

### Apache JMeter性能测试

[阿帕奇JMeter的性能测试](../high_availability/Performance-Testing-JMeter.html) 与CAS船现在 加入到 [的GitHub操作](https://github.com/apereo/cas/actions)。 此时，只有 *CAS* 变体进行了测试和 其它测试类别SAML2和OAuth将一次CAS运行时上下文被逐渐加入（即 可以使用模块选择菜单按需动态构建 WAR叠加）。 目的是确保从一个CAS版本到下一个CAS版本，

### Google Firebase云消息传递

初步支持可用于基于 [Google Firebase Cloud Messaging](../notifications/Notifications-Configuration.html)通知。 此功能的第一个使用者 [简单多因素身份验证](../mfa/Simple-Multifactor-Authentication.html) 模块。

### 通过Apache Kafka进行服务注册表复制

如果未通过集中存储全局管理CAS服务定义，则在部署多个节点时，群集中所有CAS节点的 如果您不想使用外部工具和流程，或者如果 部署的本机选项没有那么吸引人，则可以利用由Apache Kafka</a>
，该工具提供 分布式缓存来广播服务。整个集群中的定义文件。</p> 



### Google Authenticator多种设备

<div class="alert alert-warning">
  <strong>！</strong><br />这可能是一个重大变化。 修改了用于管理用户设备记录的基础数据模型和存储库实现，以处理每个用户的设备集合。 这确实会影响期望集合而不是单个结果的数据库或文件系统架构和API调用。
</div>

现在，用于多因素身份验证的Google Authenticator可以接受和注册多个设备。 找到多个注册记录时，必须在注册时为帐户或设备分配一个名称，该名称用于设备选择菜单。 通过REST验证Google Authenticator令牌时，如果用户帐户具有多个注册设备，则必须指定帐户标识符。 此外，请注意，允许每个用户使用多个设备是通过CAS设置控制的，默认情况下处于禁用状态，以保持与以前版本的行为兼容性。

|                                                                                                            |                                                                                                            |
| ---------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| ![图像](https://user-images.githubusercontent.com/1205228/85271898-ad0fb700-b490-11ea-9f69-60ae4aa59bd2.png) | ![图像](https://user-images.githubusercontent.com/1205228/85271811-8a7d9e00-b490-11ea-9d49-5689f7f539f2.png) |




### YubiKey设备的DynamoDb存储

[YubiKey设备](../mfa/YubiKey-Authentication.html) 现在可以存储在DynamoDb实例中。



### Swagger整合

[Swagger集成](../integration/Swagger-Integration.html) [SpringDoc](https://springdoc.org/)升级为使用Swagger v2。



## 其他的东西

- 纠正了映射到外部Groovy脚本的属性定义，以更友好的资源方式处理缓存。
- 服务定义的管理现在将搜索操作委派给服务注册表，而不是内部过滤匹配项，同时还利用缓存层来尽可能地提高性能。
- 现在已正确审核了OAuth / OIDC `代码` 此外， `who` 标志被还原回活动的主体ID。
- [Apache Syncope](../installation/Syncope-Authentication.html) 支持的身份验证策略，使其不需要依赖于Apache Syncope模块，从而允许该集成与所有Apache Syncope版本一起使用。 进行了其他改进，以确保配置可以符合重新加载请求等，例如 `@RefreshScope`。
- & 委派身份验证的无密码帐户的资格 `TriStateBoolean` 类型，以便在对全局设置进行检查时可以更轻松地覆盖和未定义状态。
- 使用Git集成时，用于提交操作的用户名和电子邮件属性现在可以通过本地，全局和系统git配置进行解析，然后再恢复为默认的CAS控制值。
- 现在将服务管理 `findServiceBy（）` 操作直接委派给服务注册表，并在两者之间使用适度的缓存层，以尽可能提高并保留性能。
- 测试改进以减少引导运行时上下文所需的重复配置类的数量。
- 现在，可以使用从密钥库中获取的算法对OpenID Connect ID令牌进行正确签名，并且 `iss` 字段应正确反映CAS配置中已配置的发行者。
- [蝗虫性能测试](../high_availability/Performance-Testing-Locust.html) 现在已升级为使用蝗虫 `1.1`。
- 现在强化了OAuth或OpenID Connect的ID令牌或用户信息有效载荷的生成，以防止发现设置中未定义 `none`



## 图书馆升级

- ErrorProne编译器
- UnboundID LDAP SDK
- 春季靴
- 春云
- 春季数据
- Spring Boot管理员
- 雨云
- 昂首阔步
- 昂首阔步
- 亚马逊SDK
- 阿帕奇雄猫
- Pac4j
- 特威利奥
- ActiveMQ
- 充气城堡
- 昂首阔步
- DropWizard
- 阿帕奇策展人
- 刺槐
- OpenSAML
- 大志
- Couchbase驱动程序
- MongoDb驱动程序
- 雨云OIDC
