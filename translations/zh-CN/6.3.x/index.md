---
layout: default
title: CAS - 首页
---

# CAS 企业单点登录

欢迎来到Apereo中央认证服务项目，该项目通常称为CAS。 CAS 是一家为 Web 多语种单一登录解决方案的企业，并尝试成为满足您的认证 和授权需求的综合平台。

CAS 是一个开放且有据可查的身份验证协议。 协议的主要实施是 同名托管的开源 Java 服务器组件，并支持大量 附加身份验证协议和功能。

下列项目包括 CAS 项目提供的特征和技术摘要：

* [春网流](webflow/Webflow-Customization.html)/春靴 [爪哇服务器组件](planning/Architecture.html)。
* [可插入式认证支持](installation/Configuring-Authentication-Components.html) （[LDAP](installation/LDAP-Authentication.html)， [数据库](installation/Database-Authentication.html)， [X.509](installation/X509-Authentication.html)， [斯涅戈](installation/SPNEGO-Authentication.html)、 [贾斯](installation/JAAS-Authentication.html)、 [](installation/JWT-Authentication.html)、 [拉迪乌斯·](mfa/RADIUS-Authentication.html)、 [·蒙戈德](installation/MongoDb-Authentication.html)等）
* 支持多个协议（[CAS](protocol/CAS-Protocol.html)、 [SAML](protocol/SAML-Protocol.html)、 [WS-联邦](protocol/WS-Federation-Protocol.html)、 [OAuth2](protocol/OAuth-Protocol.html)、 [开放](protocol/OpenID-Protocol.html)、 [开放ID连接](protocol/OIDC-Protocol.html)、 [REST](protocol/REST-Protocol.html)）
* 通过各种 提供商（[双安全](mfa/DuoSecurity-Authentication.html)</a>、 [FIDO U2F](mfa/FIDO-U2F-Authentication.html)、 [YubiKey](mfa/YubiKey-Authentication.html)、 [谷歌认证](mfa/GoogleAuthenticator-Authentication.html)、 [奥蒂](mfa/AuthyAuthenticator-Authentication.html)、 [接受托](mfa/Acceptto-Authentication.html)等）支持
多因素认证。</p></li> 
  
  * 支持 [将身份验证](integration/Delegate-Authentication.html) 委托给外部提供商，如 [ADFS](integration/ADFS-Integration.html)、Facebook、Twitter、SAML2 Idps 等。

* 内置支持 [密码管理](password_management/Password-Management.html)， [通知](webflow/Webflow-Customization-Interrupt.html)， [使用条款](webflow/Webflow-Customization-AUP.html) 和 [冒充](installation/Surrogate-Authentication.html)。
* 支持 [属性发布](integration/Attribute-Release.html) 包括 [用户同意](integration/Attribute-Release-Consent.html)。
* [实时监控和跟踪](monitoring/Monitoring-Statistics.html) 应用行为、统计数据和日志。
* 通过特定的身份验证策略</a> 管理和注册 客户端应用程序和服务。</li> 
  
  * [跨平台客户端支持](integration/CAS-Clients.html) （爪哇、。Net、PHP、佩尔、阿帕奇等）。
* 集成与 [InCommon， Box， Office365， 服务现在， 销售人员， 工作日， 网络顾问](integration/Configuring-SAML-SP-Integrations.html)， 德鲁帕尔， 黑板， 穆德尔， [谷歌应用程序](integration/Google-Apps-Integration.html)等。</ul> 



## 贡献

要了解如何为项目做出贡献，请 [本指南](/cas/developer/Contributor-Guidelines.html)。



## 开始使用

我们建议阅读以下文档，以便规划和执行一次CAS部署。

* [软件架构](planning/Architecture.html)
* [开始使用](planning/Getting-Started.html)
* [安装要求](planning/Installation-Requirements.html)
* [安装](installation/WAR-Overlay-Installation.html)
* [博客](https://apereo.github.io)



## 提供者

CAS 的发展由以下工具、项目和服务提供动力。

{:.list-group}

* {:.list-group-item} <a href="https://www.jetbrains.com/idea/"><img src="https://user-images.githubusercontent.com/1205228/31548576-1ac3d688-b038-11e7-9565-ffd89501872e.png" width="150"></a>
* {:.list-group-item} <a href="https://www.eclipse.org"><img width="130" src="https://user-images.githubusercontent.com/1205228/32225495-ac7b1e94-be5a-11e7-8f83-5c7399398fb8.png"></a>
* {:.list-group-item} <a href="http://projects.spring.io/spring-boot/"><img width="130" src="https://user-images.githubusercontent.com/1205228/32322526-0b58ac44-bfda-11e7-822e-ad763eb80faf.png"></a>
* {:.list-group-item} <a href="https://www.yourkit.com"><img src="https://user-images.githubusercontent.com/1205228/38207124-f6c6db34-36c1-11e8-9bbf-8dee5bd199c4.png" width="130"></a><br/>您的Kit支持开源项目与其功能齐全的Java配置文件。 您的Kit，有限责任公司是 <a href="https://www.yourkit.com/java/profiler/">您的Kit Java探查器</a> 和 <a href="https://www.yourkit.com/.net/profiler/">您的Kit.NET探查器</a>，创新和智能的工具，用于分析Java和。NET应用程序的创建者。
