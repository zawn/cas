---
layout: 违约
title: CAS - 发布说明
category: 规划
---

# RC4 发布说明

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
卡斯.版本=6.3.0-RC4
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>本版本的最低系统/平台要求没有更改。
</div>

## 值得注意的新 &

以下项目是本版本中提出的新改进和增强功能。

### JDK 15 兼容性

CAS 能够根据最新版本的 JDK 15 成功构建和运行。 JDK 基线要求 继续设置为 JDK 11，此版本与 JDK 版本保持一定速度，以确保 CAS 在必要时能够正确切换基线要求。

### 通过代码库进行测试覆盖

目前，在代码库中，CAS 所有模块的测试覆盖率已达到 88%</code> `，并且还在继续攀升。 
，还应用了其他验证规则，以失败所有低于此阈值的拉取请求。 随着取得进展，
将密切监测和改进这一领域，目标是有望在最终发布大会之前达到至少 <code>90%` 。 当然， ，这将不会是最终版本的拦截器。

### 通配符服务定义

继之前的发布说明之后，通配符服务定义现在应通过 CAS 服务管理设施正确定位， 改进查找操作，使之了解协议。 例如，在 OAuth 和 CAS 服务提供商的两个服务定义可能对服务 URL 模式具有非常宽松的 `。*` 的情况下，CAS 应能够 根据 OAUth 依赖方还是支持 CAS 的客户端应用程序提交身份验证请求来正确定位正确的定义。

### UI 主题捆绑包

在每个应用程序的基础上定义自定义主题 时，对 [UI 主题的处理略有改进](../ux/User-Interface-Customization-Themes.html) 。 主题捆绑包现在也可用，将逐步作为一个抽屉和工具箱的一些常见的 用户界面设计和主题，也可以作为如何定义或修改CAS主题的参考示例。

### 网络授权 FIDO2 发电机数据库

[WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 多因素认证能够管理和维护 DynamoDb 中的注册记录。

### 克里欧 v5

[Kryo库](https://github.com/EsotericSoftware/kryo) [的Memcached票务登记册](../ticketing/Memcached-Ticket-Registry.html) 和家庭使用，现在升级为版本 `50.0`。 这是一次重大升级，一些影响 Kryo 汇集运营的内部组件也进行了修订和升级。 但是，升级在大多数情况下仍应保持不可见状态。

### 带 LDAP/AD 的谷歌身份验证器

[谷歌身份验证器](../mfa/GoogleAuthenticator-Authentication.html) 注册记录可以保存在LDAP/AD系统 内。 帐户注册记录保存在指定可配置的多值属性中，称为 JSON blobs。

### 集成测试升级

更新了用于后端集成测试的多个 Docker 图像，以确保对每个系统的最新版本 支持。 其中包括以下系统：

- 亚马逊迪纳莫德布
- 沙发基地
- 蒙古德布
- 雷迪斯
- 阿帕奇·卡桑德拉

此外，除了 389 ds 和桑巴 （AD） 之外，还更新了测试基础设施，以支持 OpenLDAP。

### 可选（混合） X.509 身份验证

[X.509认证](../installation/X509-Authentication.html)，当运行嵌入的Apache Tomcat时，增强，以提供混合模式认证。 在此模式下，用户将获得通过 X.509 登录的附加选项，而无需先发制人地提示其获得证书。

## 其他内容

- LDAP 信托管理器现在可以在 CAS 属性中指定。
- 阿佩雷奥CAS [多克图像](https://hub.docker.com/r/apereo/cas) 现在由CI自动发布 `最新的` 标签。
- CAS [发布过程](../developer/Release-Process.html) 现在可以自动关闭并发布 Sonatype 上的版本。
- Webflow 自动配置略有改进，使 Kerberos/SPNEGO 和 X509 模块能够协同工作。
- [配置元数据](../configuration/Configuration-Metadata-Repository.html) 得到改进，以便更好地跟踪 CAS 设置所需的模块、属性和所有者。
- 改进了材料法案 （BOM） 的发布流程，将所有 CAS 模块包含在 `依赖管理` 块中，将其从最终生成的 POM 中的 `依赖` 块中删除，其版本在 `属性` 部分中分离并提取为属性。
- [谷歌身份验证器](../mfa/GoogleAuthenticator-Authentication.html) 多因素身份验证现在已修补，以安全地生成 QR 代码。

## 库升级

- 生菜
- 阿帕奇·汤姆卡特
- 爪哇旋律
- 奥希
- 番石榴
- 尼姆布斯·何塞
- 特维利奥
- 克里欧
- 沙发基地驱动程序
- 隆博克
- 人员目录
- 网络授权FIDO2
- 马克斯明德
- 亚马逊 SDK
- 尤里卡客户端
- 格拉德尔
- 弹簧启动



