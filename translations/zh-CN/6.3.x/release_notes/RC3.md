---
layout: 违约
title: CAS - 发布说明
category: 规划
---

# RC3 发行说明

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
卡斯.版本=6.3.0-RC3
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>本版本的最低系统/平台要求没有更改。
</div>

## 值得注意的新 &

以下项目是本版本中提出的新改进和增强功能。

### 通过代码库进行测试覆盖

目前，CAS 在代码库中所有模块的测试覆盖率已达到 86%</code> `，并且还在继续攀升。 
，还应用了其他验证规则，以失败所有低于此阈值的拉取请求。 随着取得进展，
将密切监测和改进这一领域，目标是有望在最终大会发布前达到至少88% <code>` 。 当然， ，这将不会是最终版本的拦截器。

### 网络授权 FIDO2 支持

CAS 现在可以支持 [Web 授权 FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 进行多因素认证。

### 服务认证政策

现在 为</a> 服务提供额外的
认证政策，以要求允许/定义身份验证处理程序。</p> 



### 委托身份验证 SSO 参与

如果单个登录会话是使用不允许的应用程序提供商建立的，则参加 分配 [授权身份验证策略](../integration/Delegate-Authentication.html#access-strategy) 的单一登录会话现在可以有条件地续订 。



### SAML2 元数据 URL 代理

从网址检索元数据的 SAML2 应用程序定义现在可以在应用定义中指定代理端点， 元数据在代理后面的情况下。



### Git 服务注册表组

[Git 服务注册处](../services/Git-Service-Management.html) 管理的服务定义现在被赋予一个选项，默认情况下， 按其类型定位和分组服务定义，并将它们存储在专用文件夹中，以便于管理。



### 注册服务属性

</a> 注册服务定义分配 自定义属性，现在可以 利用 [春季表达式](../configuration/Configuration-Spring-Expressions.html)。</p> 



## 其他内容

- 密码重置验证尝试现在可以正确处理身份验证 Web 流中的过期或无效重置尝试。
- 已记录的阿西艺术语句现在被路由到他们自己的专用 `AsciiArt` 伐木类别。
- 配置分布式会话存储及其对 OAuth、OpenID 连接和委托身份验证的复制进行更正，以自动确定 Cookie 路径，如果 CAS 设置为基于上下文自动配置 Cookie 路径。 如果 `自动配置库奇路径` 设置为错误，则此项目将特别适用。
- 可以指定多个 LDAP 基础 dns，并使用特殊的分界器字符将单个 LDAP 配置块连接在一起。
- [与 Ehcache 集成](../ticketing/Ehcache-Ticket-Registry.html) 获得了允许磁盘持久性的新设置。
- CAS 基于嵌入式 `应用程序的配置属性。yml` 现在可以通过 `类路径/应用程序-{profile}.yml` 配置文件识别基于 活动配置文件的设置。



## 库升级

- 卡桑德拉司机
- 奥希
- 特维利奥
- 朱尼特
- 蛇形
- 尤里卡
- 阿帕奇·汤姆卡特
- 春天
- 弹簧启动
- 春季数据
- 吉特
- 莫基托
- 春季安全
- 春云

