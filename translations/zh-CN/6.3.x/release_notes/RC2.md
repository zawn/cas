---
layout: 默认
title: CAS-发行说明
category: 规划
---

# RC2发行说明

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
cas.version = 6.3.0-RC2
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>此版本对最低系统/平台要求没有更改。
</div>

## 新增 & 值得注意

以下各项是此版本中提供的新改进和增强功能。

### 亚马逊SDK v2

CAS现在正在使用Amazon SDK v2，该版本可有效升级并影响DynamoDb，SNS，SSM， S3以及基于Amazon Web Services的所有其他功能的功能。

### 通过REST进行YubiKey设备管理

现在，可以通过REST API从外部管理 [多因素身份验证](../mfa/YubiKey-Authentication.html) YubiKey设备。

### 通过CodeCov进行测试

现在，代码库中所有模块的CAS测试覆盖率已达到 `84％` 并继续攀升。 附加验证规则也将应用为 以使所有低于此阈值的拉取请求失败。 随着工作的进展，该领域将受到严密监控并改善为 ，以期在最终发布Google Analytics（分析）之前 `85％` 当然，这不会成为最终版本的阻碍。

### YubiKey多种设备

现在可以向CAS注册多个YubiKey设备，以进行 [多因素身份验证](../mfa/YubiKey-Authentication.html)。 可以通过CAS设置控制此功能。

![图像](https://user-images.githubusercontent.com/1205228/88883051-8b9caa80-d248-11ea-9ad5-487c6071fbc5.png)

![图像](https://user-images.githubusercontent.com/1205228/88883117-bf77d000-d248-11ea-98c9-e88088fdd975.png)

<div class="alert alert-warning">
  <strong>！</strong><br />这可能是一个重大变化。 修改了用于管理用户设备记录的基础数据模型和存储库实现，以处理每个用户的设备集合。 这确实会影响期望集合而不是单个结果的数据库或文件系统架构和API调用。
</div>

### Amazon S3服务注册表

现在可以将CAS注册的服务定义本机存储在 [Amazon S3存储桶](../services/AmazonS3-Service-Management.html)。

### 动态JPA服务管理

[JPA服务注册表](../services/JPA-Service-Management.html) 管理的CAS注册服务定义在运行时将经过更精细的动态注册过程。 以前，如果在类路径中找到代表每种客户端应用程序类型的适当实体类，则会自动创建数据库模式 在此版本中，实体类 必须在CAS服务管理工具中明确注册，并且每个适当的自动配置模块 [WAR叠加层](../installation/WAR-Overlay-Installation.html) 正确地提名相关实体。

<div class="alert alert-info">
  <strong>记住</strong><br />如果您不使用关系数据库来管理应用程序定义，
  则您无需执行任何操作。 继续！
</div>

进行此更改的主要动机是避免CAS Web应用程序服务器与 CAS管理应用程序之间发生冲突，尤其是当两者都配置为使用JPA来管理服务定义时。 管理 应用程序需要对CAS服务定义API进行编译时访问才能处理数据映射，但是在进行 自动发现过程的情况下 对JPA Service Registry对应该存在的数据库模式和表的期望。 例如，CAS服务器部署可以声明对CAS和SAML应用程序类型 支持，从而允许它基于这两种定义类型自动创建适当的模式。 下次部署CAS管理应用程序 ，它可能会抱怨OAUTH和OIDC应用程序缺少架构，因为在类 ，但部署实际上并未使用/不支持该定义。

应用程序不会自动预期或创建数据库表和架构，从而使代码库可以使用类路径上的实体类进行数据映射操作。 为了处理注册，管理应用程序可以使用简单的属性通过CAS JPA服务注册表 声明部署支持的服务集。

### SAML2注销请求 & 响应

[SAML2身份提供者](../installation/Configuring-SAML2-Authentication.html)运行时，SAML2单次注销处理处理现在可以在单次注销序列完成后为服务提供者 此外，注销请求 不再发送到发起单个注销流程的原始服务提供商。

### OpenID Connect注销

`post_logout_redirect_uri` 将由OpenID Connect身份验证流处理的注销请求正确重定向到请求的URL，前提 `id_token_hint` ，并且注销URL被授权给依赖方。

### Okta SDK v2

CAS现在使用Okta SDK v2，主要用于处理CAS和Okta之间的集成，以进行身份验证和属性解析。

### 属性同意激活

[属性同意](../integration/Attribute-Release-Consent.html) 激活规则，以允许每个应用程序 覆盖全局策略激活规则。 现在提供了其他文档更新，以演示如何将多个 属性同意策略链接在一起。

此外，激活规则也可以外包给外部Groovy脚本。

<div class="alert alert-warning">
  <strong>！</strong><br />这可能是一个重大更改，因为 <code>DefaultRegisteredServiceConsentPolicy</code> 
  的数据模型已删除 <code>enabled</code> 字段，将其替换为 <code>status</code>。 查看文档以调整适当的语法。
</div>

### 授予票务的票务过期政策（按服务）

分配给服务定义</a>的到期策略
在每个服务上覆盖授予票证的凭单到期策略。</p> 



### 服务匹配策略

在CAS服务注册表中为应用程序定义的服务标识符始终被定义为模式。 此版本公开了 个几个 [附加选项](../services/Configuring-Service-Matching-Strategy.html) 同时还允许将匹配策略外部化到自定义组件。 



### SSO参与政策

事件中， 特定服务禁用了 [单点登录参与](../services/Configuring-Service-SSO-Policy.html) ，尤其是在Open ID Connect身份验证流程中，进行了调整以正确定位和重建身份验证事务。 此外，现在将服务定义的身份验证策略标准 默认为与CAS的全局身份验证策略和默认身份验证策略匹配。  



### 通配服务定义

考虑向CAS注册的SAML服务提供者定义，该定义授权 在XML元数据聚合文件中找到的所有服务提供者：



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“。+”，
  “ name”：“ SAML”，
  “ id”：
  “evaluationOrder”：10，
  “metadataLocation”： “https://example.org/md-aggregate.xml”
}
```


然后，假设相同的CAS部署希望授权所有启用CAS的Web应用程序：



```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“。+”，
  “ name”：“ ALL”，
  “ id”：
  “ evaluationOrder”： 9
}
```


这里的问题是，取决于 `评估顺序` 的设置方式，对于SAML或CAS协议身份验证请求，可能会匹配并处理错误的服务定义 根本原因在于，CAS匹配引擎尝试通过服务定义 `服务` （可能与实体ID或重定向URI等相关联） ， 身份验证协议本身。 在此版本中，一些额外的改进到位，以允许的分组 由两个类型和评价顺序应用定义和匹配引擎被增强以处理这样的基团，同时考虑 二者的组的评价优先级以及每个单独的服务的评估顺序。

<div class="alert alert-info">
  <strong>注意</strong><br />此版本中提供的修复程序仍在进行中，以完善其他用例。 在后续发行版中，该区域可能会被
  ，以确保所有协议中的通配服务定义可以正确协同工作。
</div>

## 其他的东西

- 调整了SAML2元数据解析缓存，以确保有足够的容量供已解析的元数据提供程序使用。
- 将CAS审核日志推送到Oracle数据库时，对SQL查询执行的次要修复。
- 现在，访问令牌的过期已正确传达给OAuth依赖方，特别是如果每个应用程序都定义了访问令牌的过期策略。
- 审查和调整了身份验证请求的处理（设置为强制CAS挑战用户凭据），以确保此类请求可以针对每个已配置的触发器，为符合资格的请求正确履行多因素身份验证流程。
- 注销处理策略略微分解，以引入 `LogoutRedirectionStrategy`，主要负责根据每个身份验证协议来处理对授权应用程序/端点的后续重定向。
- 现在，将通过Memcached序列化引擎进行的组件注册分解，并委派给拥有所述组件的相应模块。
- 将检查并调整将签名嵌入URL中的已签名SAML身份验证请求，以避免创建超出浏览器限制的长URL。
- 放宽了JSON / YAML服务定义文件的命名策略，以允许文件名中包含多个单词。
- 已调整服务定义到JSON或YAML的转换，以排除具有默认值的字段以产生更精简的有效负载。



## 图书馆升级

- 公地郎
- 莫基托
- DropWizard
- 春天
- 春季靴
- 亚马逊SDK
- 臭虫
- 摇篮
- Okta
- 四郎
- Hazelcast AWS
- Hazelcast Kubernetes
- Hazelcast Azure
- 高速缓存
- Spring Boot管理员
- 适应性
- 检举
- Nexmo
- 特威里奥
- 引导程序
- 春季数据
- 人员目录
- Azure DocumentDb
- 石斑鱼客户
- InfluxDb
