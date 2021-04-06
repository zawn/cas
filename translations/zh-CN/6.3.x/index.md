---
layout: default
title: CAS - 首页
---

# CAS 企业单点登录

欢迎来到Apereo中央认证服务项目，该项目通常称为CAS。 CAS is an enterprise multilingual single sign-on solution for the web and attempts to be a comprehensive platform for your authentication and authorization needs.

CAS is an open and well-documented authentication protocol. The primary implementation of the protocol is an open-source Java server component by the same name hosted here, with support for a plethora of additional authentication protocols and features.

The following items include a summary of features and technologies presented by the CAS project:

* [Spring Webflow](webflow/Webflow-Customization.html)/Spring Boot [Java server component](planning/Architecture.html).
* [Pluggable authentication support](installation/Configuring-Authentication-Components.html) ([LDAP](installation/LDAP-Authentication.html), [Database](installation/Database-Authentication.html), [X.509](installation/X509-Authentication.html), [SPNEGO](installation/SPNEGO-Authentication.html), [JAAS](installation/JAAS-Authentication.html), [JWT](installation/JWT-Authentication.html), [RADIUS](mfa/RADIUS-Authentication.html), [MongoDb](installation/MongoDb-Authentication.html), etc)
* Support for multiple protocols ([CAS](protocol/CAS-Protocol.html), [SAML](protocol/SAML-Protocol.html), [WS-Federation](protocol/WS-Federation-Protocol.html), [OAuth2](protocol/OAuth-Protocol.html), [OpenID](protocol/OpenID-Protocol.html), [OpenID Connect](protocol/OIDC-Protocol.html), [REST](protocol/REST-Protocol.html))
* Support for [multifactor authentication](mfa/Configuring-Multifactor-Authentication.html) via a variety of providers ([Duo Security](mfa/DuoSecurity-Authentication.html), [FIDO U2F](mfa/FIDO-U2F-Authentication.html), [YubiKey](mfa/YubiKey-Authentication.html), [Google Authenticator](mfa/GoogleAuthenticator-Authentication.html), [Authy](mfa/AuthyAuthenticator-Authentication.html), [Acceptto](mfa/Acceptto-Authentication.html), etc.)
* Support for [delegated authentication](integration/Delegate-Authentication.html) to external providers such as [ADFS](integration/ADFS-Integration.html), Facebook, Twitter, SAML2 IdPs, etc.
* Built-in support for [password management](password_management/Password-Management.html), [notifications](webflow/Webflow-Customization-Interrupt.html), [terms of use](webflow/Webflow-Customization-AUP.html) and [impersonation](installation/Surrogate-Authentication.html).
* Support for [attribute release](integration/Attribute-Release.html) including [user consent](integration/Attribute-Release-Consent.html).
* [Monitor and track](monitoring/Monitoring-Statistics.html) application behavior, statistics and logs in real time.
* Manage and register [client applications and services](services/Service-Management.html) with specific authentication policies.
* [Cross-platform client support](integration/CAS-Clients.html) (Java, .Net, PHP, Perl, Apache, etc).
* Integrations with [InCommon, Box, Office365, ServiceNow, Salesforce, Workday, WebAdvisor](integration/Configuring-SAML-SP-Integrations.html), Drupal, Blackboard, Moodle, [Google Apps](integration/Google-Apps-Integration.html), etc.

## Contribute

To learn how to contribute to the project, [please see this guide](/cas/developer/Contributor-Guidelines.html).

## 开始使用

我们建议阅读以下文档，以便规划和执行一次CAS部署。

* [软件架构](planning/Architecture.html)
* [开始使用](planning/Getting-Started.html)
* [安装要求](planning/Installation-Requirements.html)
* [安装](installation/WAR-Overlay-Installation.html)
* [博客](https://apereo.github.io)

## Powered By

CAS development is powered by the following tools, projects and services.

{:.list-group}
* {:.list-group-item} <a href="https://www.jetbrains.com/idea/"><img src="https://user-images.githubusercontent.com/1205228/31548576-1ac3d688-b038-11e7-9565-ffd89501872e.png" width="150"></a>
* {:.list-group-item} <a href="https://www.eclipse.org"><img width="130" src="https://user-images.githubusercontent.com/1205228/32225495-ac7b1e94-be5a-11e7-8f83-5c7399398fb8.png"></a>
* {:.list-group-item} <a href="http://projects.spring.io/spring-boot/"><img width="130" src="https://user-images.githubusercontent.com/1205228/32322526-0b58ac44-bfda-11e7-822e-ad763eb80faf.png"></a>
* {:.list-group-item} <a href="https://www.yourkit.com"><img src="https://user-images.githubusercontent.com/1205228/38207124-f6c6db34-36c1-11e8-9bbf-8dee5bd199c4.png" width="130"></a><br/>YourKit supports open source projects with its full-featured Java Profiler. YourKit, LLC is the creator of <a href="https://www.yourkit.com/java/profiler/">YourKit Java Profiler</a> and <a href="https://www.yourkit.com/.net/profiler/">YourKit .NET Profiler</a>, innovative and intelligent tools for profiling Java and .NET applications.
