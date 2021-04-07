---
layout: 默认
title: CAS-发行说明
category: 规划
---

# RC5发行说明

强烈建议您在发布候选版本时加以利用。 等待 `GA` 发布只会使 感到不愉快。 `GA` 是 [的标记，仅此而已是](https://apereo.github.io/2017/03/08/the-myth-of-ga-rel/)。 请注意，CAS 版本是 *严格的* 个基于时间的版本。它们不是预定的，也不是基于特定的基准，统计数据或功能完成情况。 要获得 信任度，强烈建议您从 候选发行版和/或后续快照开始尝试。

## Apereo会员资格

如果您从Apereo CAS作为免费和开源软件受益，我们 邀请您 [加入Apereo Foundation](https://www.apereo.org/content/apereo-membership) 并以最适合您的部署的能力为该项目提供财务支持。 请注意，所有开发活动 都是在自愿的基础上几乎完全是</em> 进行的 *，没有任何期望，承诺或附加条件。 可持续工程活动的财务能力，将使开发人员社区可以分配 *专用* 时间来提供长期支持， 维护和发布计划，尤其是在及时解决关键和安全问题时。 资助将 ，从而获得优势并以Apereo和当时的CAS项目运行 并运行的方式发言。 如果您认为您的CAS部署是身份和访问 管理生态系统的关键部分，那么这是一个可行的选择。</p>

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
cas.version = 6.3.0-RC5
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>此版本对最低系统/平台要求没有更改。
</div>

## 新增 & 值得注意

以下各项是此版本中提供的新改进和增强功能。

### 木偶测验

现在可以使用文档来突出显示 CAS项目和开发人员/贡献者所使用的 [测试过程](../developer/Test-Process.html) 最新添加的功能/浏览器测试 机制由Puppeteer框架支持。 被设计测试场景 由CAS持续集成系统执行的，并且将随着时间的推移能够提高 以考虑高级用例如确保协议的兼容性和认证的Webflow的其它变型。

### 苹果登录

[委派的身份验证](../integration/Delegate-Authentication.html) 现在可以将 身份验证请求传递给 [用Apple](https://developer.apple.com/sign-in-with-apple/)登录。

### 用于主要身份验证的WebAuthN

[WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 多因素身份验证 现在可以选择作为用户身份验证 和具有CAS已有注册记录的设备的主要身份验证的独立因素。

![图像](https://user-images.githubusercontent.com/1205228/98920646-96243c80-24e5-11eb-9ebc-b7eb5ac755af.png)

### Duo Security通用提示

现在支持Duo Security的 [Universal Prompt](../mfa/DuoSecurity-Authentication.html) 进行多因素身份验证。

### QR码认证

[QR码认证](../installation/QRCode-Authentication.html) 是一种策略，允许用户 CAS服务器生成的QR码，并在成功验证其后登录。

![图像](https://user-images.githubusercontent.com/1205228/100055418-111c1a00-2e39-11eb-840f-e9c9b866f106.png)

### WebAuthN LDAP存储库

[WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 多因素身份验证 现在可以管理和跟踪LDAP目录中的设备注册记录。

### 测试覆盖率

现在，代码库中所有模块的CAS测试覆盖率已达到 `90％` 并继续攀升。 其他验证规则也适用于使所有低于此阈值的拉取请求失败。

## 其他的东西

- [属性解析](../integration/Attribute-Resolution.html) 获得了一个新选项，可以强制所有属性存储库生成数据，并在任何存储库无法解析人员详细信息的情况下缩短解析逻辑。
- [Maven存储库](https://spring.io/blog/2020/10/29/notice-of-permissions-changes-to-repo-spring-io-fall-and-winter-2020) 已从CAS gradle构建中删除。
- [密码管理](../password_management/Password-Management.html) 改进可更优雅地处理无效令牌，并允许在有或没有单点登录会话的情况下重置密码。
- 释放在属性名的空间，由于错误配置，属性将迫使CAS与替换空间 `_` 时释放。
- [SPNEGO身份验证](../installation/SPNEGO-Authentication.html) Kerberos和JAAS配置文件已更改为将Spring资源用于语法。 您可能需要确保CAS配置中的路径以 `文件：` 前缀开头。
- [LDAP的属性解析](../integration/Attribute-Resolution.html) 已得到改进，以支持使用标签/选项获取和映射属性。
- 更正了CAS文档中找到的所有外部链接，以指向有效资源。 验证过程也进行了调整，以防止不良链接。

## 图书馆升级

- JRadius
- 淡褐色
- MySQL驱动
- MongoDb驱动程序
- HSQL驱动程序
- MariaDb驱动程序
- Groovy
- 咖啡因
- Pac4j
- 阿帕奇雄猫
- 春季靴
- 冬眠
- Infinispan
- 胸腺方言
- 千分尺
