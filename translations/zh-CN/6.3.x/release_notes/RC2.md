---
layout: 违约
title: CAS - 发布说明
category: 规划
---

# RC2 发布说明

我们强烈建议您利用释放候选人，因为他们出来。 等待一个 `GA` 发布只会设置 你不愉快的惊喜。 一个 `的大会` [一个标签，没有更多的](https://apereo.github.io/2017/03/08/the-myth-of-ga-rel/)。 请注意，CAS 版本 *严格* 基于时间的版本：它们不是根据特定的基准、统计或功能完成来安排的。 为了获得 对特定版本的信心，强烈建议您尽早开始尝试发布候选和/或后续快照。

## 阿佩雷奥会员

如果您作为免费和开源软件受益于 Apereo CAS，我们邀请您 [加入 Apereo 基金会](https://www.apereo.org/content/apereo-membership) ，并以最适合您部署的容量为项目提供财政支持。 请注意， 的所有发展活动几乎完全 *自愿* 进行，不附加任何期望、承诺或条件。 拥有更好的财务手段 维持工程活动，将使开发人员社区能够分配 *专门和投入的* 时间进行长期支持、 维护和发布规划，特别是在及时解决关键和安全问题方面。 资金将 确保对您所依赖的软件的支持，并且您获得优势，并在 Apereo 和 CAS 项目运行 和运营的方式中表示。 如果您认为 CAS 部署是身份和访问管理生态系统的关键部分，则这是一个可行的选择。

## 参与

- 今天开始您的CAS部署。 尝试功能， [分享反馈](/cas/Mailing-Lists.html)。
- 更妙的是， [](/cas/developer/Contributor-Guidelines.html)提供补丁。
- 建议并应用文档改进。

## 资源

- [发布时间表](https://github.com/apereo/cas/milestones)
- [发布策略](/cas/developer/Release-Policy.html)

## 覆盖

在 [CAS战争覆盖](../installation/WAR-Overlay-Installation.html)`` ，调整下列设置：

```properties
卡斯.版本=6.3.0-RC2
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>本版本的最低系统/平台要求没有更改。
</div>

## 值得注意的新 &

以下项目是本版本中提出的新改进和增强功能。

### 亚马逊 SDK v2

CAS 目前正在使用亚马逊 SDK v2，它有效地升级和影响 DynamoDb、SNS、SSM、 S3 以及基于亚马逊 Web 服务的所有其他功能的功能。

### 尤比基设备管理通过 REST

用于 [多因素认证](../mfa/YubiKey-Authentication.html) 的 YubiKey 设备现在可以通过 REST API 进行外部管理。

### 通过代码库进行测试覆盖

目前，在代码库中，CAS 所有模块的测试覆盖率已达到 84%</code> `，并且还在继续攀升。 
，还应用了其他验证规则，以失败所有低于此阈值的拉取请求。 随着取得进展，这一领域将得到密切监测和改进，
有望在最终大会发布前达到至少85%` `。 当然，这不会是最终版本的拦截器。</p>

<h3 spaces-before="0">尤比基多设备</h3>

<p spaces-before="0">多个YubiKey设备现在可以在中科院注册 <a href="../mfa/YubiKey-Authentication.html">多因素认证</a>。 此能力可以通过 CAS 设置进行控制。</p>

<p spaces-before="0"><img src="https://user-images.githubusercontent.com/1205228/88883051-8b9caa80-d248-11ea-9ad5-487c6071fbc5.png" alt="图像" /></p>

<p spaces-before="0"><img src="https://user-images.githubusercontent.com/1205228/88883117-bf77d000-d248-11ea-98c9-e88088fdd975.png" alt="图像" /></p>

<div class="alert alert-warning">
  <strong>小心！</strong><br />这可能是一个突破性的变化。 对管理用户设备记录的基础数据模型和存储库实现进行了修改，以处理每个用户的设备集合。 这确实会影响数据库或文件系统计划和 API 调用，其中需要收集而不是单个结果。
</div>

<h3 spaces-before="0">亚马逊 S3 服务注册表</h3>

<p spaces-before="0">CAS 注册的服务定义现在可以本地存储在亚马逊 S3 存储桶</a><a href="../services/AmazonS3-Service-Management.html">中。</p>

<h3 spaces-before="0">动态 JPA 服务管理</h3>

<p spaces-before="0">由 <a href="../services/JPA-Service-Management.html">JPA服务注册处</a>
管理的CAS注册服务定义，现在在运行时通过更微调的动态注册流程。 以前，如果在类路径上找到代表每个客户端应用程序类型的适当实体类别，则会自动创建数据库
。 在此版本中，实体类 
必须明确注册在 CAS 服务管理设施，每个适当的自动配置模块应
正确指定相关实体时，宣布在 <a href="../installation/WAR-Overlay-Installation.html">WAR 覆盖</a>。 </p>

<div class="alert alert-info">
  <strong>请记住</strong><br />如果您没有使用关系数据库来管理应用程序定义，
  这里没有什么可做的。 继续！
</div>

<p spaces-before="0">这一变化的主要动机是避免 CAS 网络应用服务器和 
CAS 管理应用程序之间的冲突，特别是当两者都配置为使用 JPA 来管理服务定义时。 管理
应用程序需要编译时间访问 CAS 服务定义 ABI 来处理数据映射，但这样做会干扰
JPA 服务注册处对数据库计划和表的期望，因为分类路径
自动发现过程。 例如，CAS 服务器部署可以声明对 CAS 和 SAML 应用程序类型的支持
允许它根据这两种定义类型自动创建适当的计划。 当 CAS 管理应用程序 
下一次部署时，它可能会抱怨非统组织和 OIDC 应用程序缺少计划，因为类型 
位于类路径上，但该定义实际上并未被部署使用/支持。</p>

<p spaces-before="0">使用此新策略，CAS 管理
 应用程序不会自动预期或创建数据库表和计划，允许代码库使用分类路径上的实体类进行数据映射操作。 
 为了处理注册，管理应用程序有权使用简单的属性向 CAS JPA 服务注册处注册每个 
 申请类型的实体类，允许操作员明确 
 申报部署支持的服务集。</p>

<h3 spaces-before="0">SAML2 注销请求 & 响应</h3>

<p spaces-before="0">SAML2 单一注销处理处理，当 CAS 作为 <a href="../installation/Configuring-SAML2-Authentication.html">SAML2 身份提供商</a>运行时，现在 
能够在单个注销序列完成后为服务提供商生成注销响应。 此外，
的注销请求不再发送到启动单一注销流的原始服务提供商。 </p>

<h3 spaces-before="0">打开ID连接注销</h3>

<p spaces-before="0">由 OpenID 连接身份验证流处理的注销请求现在可以通过指定 <code>id_token_hint` `post_logout_redirect_uri` 正确地重定向到请求的 URL，并且登录 URL 已授权给依赖方。

### 冈田SDK v2

CAS现在使用冈田SDK v2主要用于处理CAS和大田之间的集成进行身份验证和属性解析。

### 属性同意激活

重新设计了 [属性同意](../integration/Attribute-Release-Consent.html) 的激活规则，以允许每个应用程序 覆盖全球政策激活规则。 现在还提供了其他文档更新，以演示如何将多个 属性同意策略链接在一起。

此外，激活规则也可以外包给外部 Groovy 脚本。

<div class="alert alert-warning">
  <strong>小心！</strong><br />这可能是一个突破性的变化，因为 <code>默认注册服务政策</code> 
  的数据模型已经删除了启用</code> 字段 <code>，代之以 <code>状态</code>。 查看文档以调整以获得适当的语法。
</div>

### 每项服务的出票票到期政策

分配给服务定义</a>的过期保单
，每份服务可覆盖出票期政策。</p> 



### 服务匹配策略

CAS 服务注册表中为应用程序定义的服务标识符始终定义为模式。 此版本 暴露了</a> 的一些 其他选项，同时允许将匹配策略外部化为自定义组件。 </p> 



### SSO 参与政策

为正确定位和重建身份验证交易，在 情况下， 特定服务（特别是在开放 ID Connect 身份验证流中）禁用 [单个登录参与](../services/Configuring-Service-SSO-Policy.html) 时，将进行调整。 此外，服务定义 的身份验证策略标准现在已默认，以匹配 CAS 的全球和默认身份验证策略。  



### 通配符服务定义

考虑在 CAS 注册的 SAML 服务提供商定义，该定义授权 XML 元数据聚合文件中发现的所有服务提供商：



```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务。萨姆注册服务"，
  "服务Id"："。+"，
  "名称"："SAML"，
  "id"：2，
  "评估序"：10，
  "元数据定位"："https://example.org/md-aggregate.xml"
}
```


然后，假设相同的 CAS 部署希望授权所有支持 CAS 的 Web 应用程序：



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："。+"，
  "名称"："ALL"，
  "id"：1、
  "评估订购"：9
}
```


这里的问题是，根据 `评估单` 的设置方式，错误的服务定义可能会在 SAML 或 CAS 协议认证请求 进行匹配和处理。 其根本原因是，CAS 匹配引擎试图在不考虑 认证协议本身的情况下，通过其 `服务 Id` （可能与实体 ID 或重定向 URI 等相关）来查找 的服务定义。 在此版本中，还进行了一些额外的改进，以便按类型和评估顺序对 应用定义进行分组，并增强匹配引擎来处理此类组，同时考虑 组的评估优先级以及每个服务的评估顺序。

<div class="alert alert-info">
  <strong>注意</strong><br />本新闻稿中提出的修复程序仍在改进其他使用案例。 此区域可能会在后续版本中
  重新审视，以确保所有协议中的通配符服务定义能够正确协同工作。
</div>

## 其他内容

- 对 SAML2 元数据分辨率缓存的调整，以确保为已解决的元数据提供商提供足够的容量。
- 将 CAS 审计日志推至甲骨文数据库时，对 SQL 查询执行的次要修复。
- 访问令牌的到期现在被正确地传达回OAuth依赖方，特别是如果访问令牌到期政策是每个应用程序定义的。
- 认证请求的处理，旨在迫使 CAS 挑战用户凭据，经过审查和调整，以确保此类请求能够适当遵守每个配置触发器的合格请求的多因素身份验证流程。
- 登录处理策略稍有不同，引入 `的注销重定向策略`，主要负责根据每个身份验证协议酌情处理后续重定向到授权应用/端点。
- Memcach 系列化发动机的组件注册现已拆分，并委托给拥有该组件的相应模块。
- 已签署在网址中嵌入签名的 SAML 身份验证请求经过审查和调整，以避免创建超过浏览器限制的长网址。
- 放宽了 JSON/YAML 服务定义文件的命名策略，允许在文件名称中使用多个单词。
- 将服务定义转换为 JSON 或 YAML，以排除具有默认值的字段以生成更精简的有效载荷。



## 库升级

- 公地郎
- 莫基托
- 滴维扎德
- 春天
- 弹簧启动
- 亚马逊 SDK
- 斑点虫
- 格拉德尔
- 奥克塔
- 希罗
- 黑兹尔卡斯特·沃斯
- 黑兹尔卡斯特·库贝内茨
- 黑兹尔卡斯特·阿祖尔
- 埃卡奇
- 弹簧启动管理
- 焊接
- 因斯佩克特
- 内克斯莫
- 特维利奥
- 启动
- 春季数据
- 人员目录
- 蔚蓝文档数据库
- 分组客户端
- 拉布斯德布
