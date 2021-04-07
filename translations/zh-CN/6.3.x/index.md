---
layout: 默认
title: CAS-主页
---

# CAS企业单点登录

欢迎来到Apereo中央身份验证服务项目（通常更称为CAS）的主页。 CAS是 多语言单点登录解决方案，它试图成为满足身份验证 和授权需求的综合平台。

CAS是一个开放且有据可查的身份验证协议。 该协议的主要实现是在此处托管的具有相同名称的开源Java服务器组件 其他身份验证协议和功能。

以下各项包括CAS项目介绍的功能和技术的摘要：

* [Spring Webflow](webflow/Webflow-Customization.html)/ Spring Boot [Java服务器组件](planning/Architecture.html)。
* [可插拔认证支持](installation/Configuring-Authentication-Components.html) （[LDAP](installation/LDAP-Authentication.html)， [的数据库](installation/Database-Authentication.html)， [X.509](installation/X509-Authentication.html)， [SPNEGO](installation/SPNEGO-Authentication.html)， [JAAS](installation/JAAS-Authentication.html)， [JWT](installation/JWT-Authentication.html)， [RADIUS](mfa/RADIUS-Authentication.html)， [的MongoDB](installation/MongoDb-Authentication.html)等）
* 支持多种协议（[CAS](protocol/CAS-Protocol.html)， [SAML](protocol/SAML-Protocol.html)， [WS联盟](protocol/WS-Federation-Protocol.html)， [的OAuth2](protocol/OAuth-Protocol.html)， [的OpenID](protocol/OpenID-Protocol.html)， [ID连接](protocol/OIDC-Protocol.html)， [REST](protocol/REST-Protocol.html)）
* 为支持 [多因素认证](mfa/Configuring-Multifactor-Authentication.html) 经由各种 提供商（[朵安全](mfa/DuoSecurity-Authentication.html)， [FIDO U2F](mfa/FIDO-U2F-Authentication.html)， [YubiKey](mfa/YubiKey-Authentication.html)， [谷歌身份验证器](mfa/GoogleAuthenticator-Authentication.html)， [Authy](mfa/AuthyAuthenticator-Authentication.html)， [Acceptto](mfa/Acceptto-Authentication.html)等）
* 支持向外部提供者（例如 [ADFS](integration/ADFS-Integration.html)，Facebook，Twitter，SAML2 IdP等） [委派身份验证](integration/Delegate-Authentication.html)
* 内置支持为 [密码管理](password_management/Password-Management.html)， [通知](webflow/Webflow-Customization-Interrupt.html)， [使用条件](webflow/Webflow-Customization-AUP.html) 和 [模拟](installation/Surrogate-Authentication.html)。
* 支持 [属性版本](integration/Attribute-Release.html) 包括 [用户同意](integration/Attribute-Release-Consent.html)。
* [实时监视和跟踪](monitoring/Monitoring-Statistics.html) 应用程序的行为，统计信息和日志。
* 使用特定的身份验证策略管理和注册 [客户端应用程序和服务](services/Service-Management.html)
* [跨平台客户端支持](integration/CAS-Clients.html) （Java，.Net，PHP，Perl，Apache等）。
* 与 [InCommon，Box，Office365，ServiceNow，Salesforce，Workday，WebAdvisor](integration/Configuring-SAML-SP-Integrations.html)，Drupal，Blackboard，Moodle， [Google Apps](integration/Google-Apps-Integration.html)等集成。

## 贡献

要了解如何作出贡献的项目， [请参阅本指南](/cas/developer/Contributor-Guidelines.html)。

## 入门

我们建议您阅读以下文档，以计划和执行CAS部署。

* [建筑学](planning/Architecture.html)
* [入门](planning/Getting-Started.html)
* [安装要求](planning/Installation-Requirements.html)
* [安装](installation/WAR-Overlay-Installation.html)
* [博客](https://apereo.github.io)

## 供电

CAS开发由以下工具，项目和服务支持。

{:.list-group}
* {:.list-group-item} <a href="https://www.jetbrains.com/idea/"><img src="https://user-images.githubusercontent.com/1205228/31548576-1ac3d688-b038-11e7-9565-ffd89501872e.png" width="150"></a>
* {:.list-group-item} <a href="https://www.eclipse.org"><img width="130" src="https://user-images.githubusercontent.com/1205228/32225495-ac7b1e94-be5a-11e7-8f83-5c7399398fb8.png"></a>
* {:.list-group-item} <a href="http://projects.spring.io/spring-boot/"><img width="130" src="https://user-images.githubusercontent.com/1205228/32322526-0b58ac44-bfda-11e7-822e-ad763eb80faf.png"></a>
* {:.list-group-item} <a href="https://www.yourkit.com"><img src="https://user-images.githubusercontent.com/1205228/38207124-f6c6db34-36c1-11e8-9bbf-8dee5bd199c4.png" width="130"></a><br/>YourKit通过其功能齐全的Java Profiler支持开源项目。 <a href="https://www.yourkit.com/java/profiler/">YourKit Java Profiler</a> 和 <a href="https://www.yourkit.com/.net/profiler/">YourKit .NET Profiler</a>的创建者，它们是用于分析Java和.NET应用程序的创新和智能工具。
