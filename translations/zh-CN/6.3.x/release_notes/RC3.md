---
layout: 默认
title: CAS-发行说明
category: 规划
---

# RC3发行说明

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
cas.version = 6.3.0-RC3
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>此版本对最低系统/平台要求没有更改。
</div>

## 新增 & 值得注意

以下各项是此版本中提供的新改进和增强功能。

### 通过CodeCov进行测试

现在，代码库中所有模块的CAS测试覆盖率已达到 `86％` 并继续攀升。 附加验证规则也将应用为 以使所有低于此阈值的拉取请求失败。 随着工作的进展，该领域将受到密切监控并改善为 ，以期在最终的Google Analytics（分析）发布之前 `88％` 当然， 不会成为最终版本的阻止者。

### WebAuthn FIDO2支持

CAS现在可以支持 [WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 进行多因素身份验证。

### 服务认证策略

</a> 的附加
身份验证策略现在 ，以要求允许/定义的身份验证处理程序。</p> 



### 委派身份验证SSO参与

参与单点登录会话的应用程序是 分配 [委派认证策略](../integration/Delegate-Authentication.html#access-strategy) 现在可以有条件地更新 ，如果单点登录会话中使用的应用程序不允许的供应商建立。



### SAML2元数据URL代理

从URL检索其元数据的SAML2应用程序定义现在可以在应用程序定义中指定代理端点，如果元数据位于代理之后 



### Git服务注册组

[Git Service Registry](../services/Git-Service-Management.html) 管理的服务定义提供了一个选项，默认情况下为 ，该选项可以按服务定义的类型来定位和分组服务定义，并将它们存储在专用文件夹中，以便于管理。



### 注册服务属性

现在，将 [自定义属性](../services/Configuring-Service-Custom-Properties.html) 分配给已注册的服务定义 可以利用 [Spring Expressions](../configuration/Configuration-Spring-Expressions.html)优势。



## 其他的东西

- 密码重置验证尝试现在可以正确处理身份验证Webflow中过期或无效的重置尝试。
- 现在，已记录的ascii-art语句将路由到其专用的 `AsciiArt` 记录类别。
- 如果将CAS设置为根据上下文自动配置cookie路径，则可以更正配置分布式会话存储及其对OAuth，OpenID Connect和委派身份验证的复制，以自动确定cookie路径。 `autoConfigureCookiePath` 设置为false，则该项目特别适用。
- 可以指定多个LDAP base-dn，并使用特殊的定界符将它们合并为一个LDAP配置块。
- [与Ehcache](../ticketing/Ehcache-Ticket-Registry.html) 集成获得了一个新设置，以允许磁盘持久性。
- 现在， `应用程序的CAS配置属性。yml` `classpath：/ application-{profile}` 配置文件 活动配置文件识别设置。



## 图书馆升级

- Cassandra驱动程序
- 大志
- 特威里奥
- JUnit的
- 蛇YAML
- 尤里卡
- 阿帕奇雄猫
- 春天
- 春季靴
- 春季数据
- 吉特
- 莫基托
- 春季安全
- 春云

