---
layout: 默认
title: CAS-发行说明
category: 规划
---

# RC4发行说明

强烈建议您在发布候选版本时加以利用。 等待 `GA` 发布只会使 感到不愉快。 `GA` 是 [的标记，仅此而已是](https://apereo.github.io/2017/03/08/the-myth-of-ga-rel/)。 请注意，CAS 版本是 *严格的* 个基于时间的版本。它们不是预定的，也不是基于特定的基准，统计数据或功能完成情况。 要使 ，强烈建议您从实验候选版本和/或后续快照开始，并尽早开始。

## Apereo会员资格

如果您从Apereo CAS作为免费和开源软件受益，我们邀请您 [加入Apereo Foundation](https://www.apereo.org/content/apereo-membership) 并以最适合您部署的能力为该项目提供财务支持。 请注意，所有开发活动 都是在自愿的基础上几乎完全是</em> 进行的 *，没有任何期望，承诺或附加条件。 可持续工程活动的财务能力，将使开发人员社区可以分配 *专用* 时间来提供长期支持， 维护和发布计划，尤其是在及时解决关键和安全问题时。 资助将 ，从而获得优势并以Apereo和当时的CAS项目运行 并运行的方式发言。 如果您认为CAS部署是身份和访问管理生态系统的关键部分，那么这是一个可行的选择。</p>

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
cas.version = 6.3.0-RC4
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>此版本对最低系统/平台要求没有更改。
</div>

## 新增 & 值得注意

以下各项是此版本中提供的新改进和增强功能。

### JDK 15兼容性

CAS能够针对最新版本的JDK 15构建并成功运行。 将继续在JDK 11上设置JDK基准要求 ，并且此发行版与JDK发行版保持一致，以确保CAS 可以在必要时正确切换基准要求。

### 通过CodeCov进行测试

现在，代码库中所有模块的CAS测试覆盖率已达到 `88％` 并继续攀升。 附加验证规则也将应用为 以使所有低于此阈值的拉取请求失败。 随着工作的进展，该领域将受到密切监控并改善为 ，以期在最终发布Google Analytics（分析）之前 `90％` 当然， 不会成为最终版本的阻止者。

### 通配服务定义

在遵循以前的发行说明之后，现在应该通过将服务改进为协议感知的方式， 例如，在OAuth 和CAS服务提供者的 `*` 的情况下，CAS应该能够 根据验证请求是否正确来正确定位正确的定义。由 OAUth依赖方或启用CAS的客户端应用程序提交。

### UI主题包

[UI主题](../ux/User-Interface-Customization-Themes.html) 当 涉及每个应用程序定义自定义主题）进行了较小的改进。 现在还提供了一个主题包，这些包将逐渐充当多个 用户界面常见设计和主题的抽屉和工具箱，并且还可以用作有关如何定义或修改CAS主题的参考示例。

### WebAuthN FIDO2 DynamoDb

[WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 多因素身份验证能够管理和维护DynamoDb中的注册记录。

### Kryo v5

在 [KRYO库](https://github.com/EsotericSoftware/kryo) 通过使用 的 [Memcached的票据注册表](../ticketing/Memcached-Ticket-Registry.html) 和家庭现在升级到版本 `5.0.0`。 这是一项重大升级，并且对影响Kryo池操作的许多内部组件也进行了修订和升级。 但是，升级在大多数情况下应该保持不可见。

### 带有LDAP / AD的Google身份验证器

[Google Authenticator](../mfa/GoogleAuthenticator-Authentication.html) 注册记录可以保存在 LDAP / AD系统中。 帐户注册记录以JSON Blob的形式保存在指定的可配置多值属性内。

### 集成测试升级

更新了许多用于后端集成测试的Docker映像，以确保每个系统的 其中包括以下系统：

- 亚马逊DynamoDb
- Couchbase
- MongoDb
- 雷迪斯
- 阿帕奇·卡桑德拉（Apache Cassandra）

此外，除了389-ds和Samba（AD）之外，还更新了测试基础结构以支持OpenLDAP。

### 可选（混合）X.509身份验证

[X.509身份验证](../installation/X509-Authentication.html)（在运行嵌入式Apache Tomcat时）经过增强，可以提供混合模式身份验证。 在这种模式下，为用户提供了一个额外的选项，使其可以通过X.509登录，而不会被提示先输入证书。

## 其他的东西

- 现在可以在CAS属性中指定LDAP信任管理器。
- CI现在自动为 `最新的` 标记发布Apereo CAS [Docker映像](https://hub.docker.com/r/apereo/cas)
- 现在，CAS [发行过程](../developer/Release-Process.html) 可以自动关闭并发布有关Sonatype的发行版。
- Webflow自动配置略有改进，以允许Kerberos / SPNEGO和X509模块一起工作。
- [改进了配置元数据](../configuration/Configuration-Metadata-Repository.html) ，以更好地跟踪CAS设置所需的模块，属性和所有者。
- 改进了物料清单（BOM）的发布过程，将所有CAS模块包含在 `dependencyManagement` 块中，并将它们从最终生成的POM中 `依赖` `property` 部分中的属性。
- [现在对用于多因素身份验证的Google身份验证器](../mfa/GoogleAuthenticator-Authentication.html) 进行了修补，以安全地生成QR码。

## 图书馆升级

- 生菜
- 阿帕奇雄猫
- Java旋律
- 大志
- 番石榴
- 雨云Jose
- 特威利奥
- ry
- Couchbase驱动程序
- 龙目岛
- 人员目录
- WebAuthN FIDO2
- 最大思维
- 亚马逊SDK
- 尤里卡客户
- 摇篮
- 春季靴



