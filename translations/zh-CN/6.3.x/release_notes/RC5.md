---
layout: 违约
title: CAS - 发布说明
category: 规划
---

# RC5 发布说明

我们强烈建议您利用释放候选人，因为他们出来。 等待一个 `GA` 发布只会设置 你不愉快的惊喜。 一个 `的大会` [一个标签，没有更多的](https://apereo.github.io/2017/03/08/the-myth-of-ga-rel/)。 请注意，CAS 版本 *严格* 基于时间的版本：它们不是根据特定的基准、统计或功能完成来安排的。 为了获得 对特定版本的信心，强烈建议您尽早开始尝试 发布候选和/或后续快照。

## 阿佩雷奥会员

如果您作为免费和开源软件受益于 Apereo CAS，我们 邀请您 [加入 Apereo 基金会](https://www.apereo.org/content/apereo-membership) ，并以最适合您部署的能力为项目提供财政支持。 请注意， 的所有发展活动几乎完全 *自愿* 进行，不附加任何期望、承诺或条件。 拥有更好的财务手段 维持工程活动，将使开发人员社区能够分配 *专门和投入的* 时间进行长期支持、 维护和发布规划，特别是在及时解决关键和安全问题方面。 资金将 确保对您所依赖的软件的支持，并且您获得优势，并在 Apereo 和 CAS 项目运行 和运营的方式中表示。 如果您认为 CAS 部署是身份和访问 管理生态系统的关键部分，则这是一个可行的选择。

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
卡斯.版本=6.3.0-RC5
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>本版本的最低系统/平台要求没有更改。
</div>

## 值得注意的新 &

以下项目是本版本中提出的新改进和增强功能。

### 木偶测试

文档现在可用于突出 CAS 项目和开发人员/贡献者</a> 使用的
测试过程。 最新的新增功能/浏览器测试 由木偶框架支持的机制的可用性。 设计的测试方案由 CAS 连续集成系统 执行，并将随着时间的推移而改进 以考虑高级使用案例，例如确保协议兼容性和身份验证 Web 流的其他变化。  </p> 



### 苹果签名

[授权认证](../integration/Delegate-Authentication.html) 现在可以 认证请求， [与苹果](https://developer.apple.com/sign-in-with-apple/)签约。



### 主要身份验证的网络授权

[WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 多因素身份验证 现在可任选地作为主要身份验证的独立因素，用于用户帐户 以及具有 CAS 现有注册记录的设备。

![图像](https://user-images.githubusercontent.com/1205228/98920646-96243c80-24e5-11eb-9ebc-b7eb5ac755af.png)



### 双安全通用提示

现已提供对 Duo 安全 [通用提示](../mfa/DuoSecurity-Authentication.html) 多因素身份验证的支持。



### QR 代码身份验证

[QR码身份验证](../installation/QRCode-Authentication.html) 是一种策略，允许用户扫描由 CAS服务器生成的QR码，使用移动设备，并在成功验证后进行登录。

![图像](https://user-images.githubusercontent.com/1205228/100055418-111c1a00-2e39-11eb-840f-e9c9b866f106.png)



### 网络授权LDAP存储库

[WebAuthn FIDO2](../mfa/FIDO2-WebAuthn-Authentication.html) 多因素认证 现在可以管理并跟踪 LDAP 目录内的设备注册记录。



### 测试覆盖范围

目前，CAS 在代码库中所有模块的测试覆盖率已达到 90%</code> `，并且还在继续攀升。 还适用于其他验证规则，以失败所有低于此阈值的拉取请求。 </p>

<h2 spaces-before="0">其他内容</h2>

<ul>
<li><a href="../integration/Attribute-Resolution.html">属性分辨率</a> 已获得一个新的选项，强制所有属性存储库生成数据，如果任何存储库无法解决人员详细信息，则会缩短分辨率逻辑的短路。</li>
<li><a href="https://spring.io/blog/2020/10/29/notice-of-permissions-changes-to-repo-spring-io-fall-and-winter-2020">由春季项目管理</a> 马文仓库从 CAS 格栅建筑中删除。</li>
<li><a href="../password_management/Password-Management.html">密码管理的改进</a> 流程，以便更优雅地处理无效代币，并允许在单个登录会话下或没有单个登录会话的情况下重置密码。</li>
<li>由于配置错误，在属性名称中以空格释放属性将迫使 CAS 在发布时用 <code>_` 替换空间。</li> 

- 加载 Kerberos 和 JAAS 配置文件以 [SPNEGO 身份验证](../installation/SPNEGO-Authentication.html) 更改为使用弹簧资源进行语法。 您可能需要确保 CAS 配置中的路径从 `文件开始：` 前缀。
- [LDAP 的属性分辨率](../integration/Attribute-Resolution.html) 已得到改进，以支持带有标记/选项的取取和映射属性。
- CAS 文档中发现的所有外部链接都会更正为指向有效资源。 验证过程也进行了调整，以防止不良链接。</ul> 



## 库升级

- 杰拉迪乌斯
- 黑兹尔卡斯特
- 迈斯基尔驱动程序
- 蒙古德布司机
- HSQL 驱动程序
- 马里亚德布司机
- 槽的
- 咖啡因
- 帕克4j
- 阿帕奇·汤姆卡特
- 弹簧启动
- 冬眠
- 英菲尼斯潘
- 提梅利夫方言
- 千分尺
