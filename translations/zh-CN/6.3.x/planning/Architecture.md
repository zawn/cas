---
layout: 违约
title: 中科院 - 建筑
category: 规划
---

# 建筑

![中科院建筑图](../images/cas_architecture.png "中科院建筑图")

## 系统组件

CAS 服务器和客户端由 CAS 系统架构的两个物理组件组成，它们通过各种协议 进行通信。

### CAS服务器

CAS 服务器是建在"春季框架"基础上的 Java 伺服器，其主要职责是通过签发和验证门票来验证用户 并授予对 CAS 启用服务（通常称为 CAS 客户端）的访问权限。 当服务器在成功登录时向用户发出赠票票证 （TGT） 时，将创建 SSO 会话。 应用户要求，通过使用 TGT 作为代币的浏览器重定向向向服务签发服务票证 （ST）。 ST随后通过后通道通信在CAS服务器上进行验证。 CAS 礼宾文件详细描述了这些交互。


### 中科院客户

术语"CAS 客户端"在其常用中具有两个不同的含义。 CAS 客户端是任何支持 CAS 的应用程序， 可以通过支持的协议与服务器通信。 CAS 客户端也是一个软件包，可以 与各种软件平台和应用程序集成，以便通过一些 认证协议（例如 CAS、SAML、OAuth）与 CAS 服务器进行通信。 中科院客户支持一些软件平台和产品 已经开发。

平台：

* 阿帕奇 httpd 服务器 （[mod_auth_cas模块](https://github.com/Jasig/mod_auth_cas)）
* 爪哇 （[爪哇中科院客户](https://github.com/apereo/java-cas-client)）
* 。NET （[.NET CAS 客户端](https://github.com/apereo/dotnet-cas-client)）
* PHP （[菲普卡斯](https://github.com/Jasig/phpCAS)）
* 佩尔 （佩尔卡斯）
* Python （皮卡斯）
* 红宝石（红宝石客户）

应用：

* 帆布
* 阿特拉斯汇合
* 阿特拉斯·吉拉
* 德鲁帕尔
* 生命射线
* u波尔塔尔
* ...

当"CAS 客户端"一词在没有进一步资格的情况下出现在本手册中时，它是指 组件（如 Java CAS 客户端）的集成，而不是指向依赖 CAS 服务器（客户端）的应用程序。


## 支持的协议

客户端通过多个支持的协议中的任何一个与服务器通信。  所有支持的协议在概念上 相似，但有些协议具有特定应用或使用案例所需的功能或特征。 例如，CAS 协议支持委托（代理）身份验证，SAML 协议支持属性发布和单个签出。

支持的协议：

* [CAS （版本 1、2 和 3）](../protocol/CAS-Protocol.html)
* [萨姆尔 1.1 和 2](../protocol/SAML-Protocol.html)
* [打开ID连接](../protocol/OIDC-Protocol.html)
* [开放ID](../protocol/OpenID-Protocol.html)
* [非授权 2.0](../protocol/OAuth-Protocol.html)
* [WS 联合会](../protocol/WS-Federation-Protocol.html)


## 软件组件

用三个分层子系统来描述 CAS 服务器是有帮助的：

* 网络（春季 MVC/春季网络流）
* [票务](../ticketing/Configuring-Ticketing-Components.html)
* [认证](../installation/Configuring-Authentication-Components.html)

几乎所有的部署考虑和组件配置都涉及这三个子系统。 Web 层是与包括 CAS 客户端在内的所有外部系统通信的终点。 Web 层代表到票务子系统，为 CAS 客户端访问生成票证。 SSO 会议从成功认证时签发出票证开始，因此票务子系统经常委托给身份验证子系统。

身份验证系统通常仅在 SSO 会话开始时处理请求，但还有其他可以调用请求的情况（例如强制身份验证）。

### 春季框架

中科院运用了《春季框架》的许多方面：最引人注目的是， [春MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) 和 [春网流](https://projects.spring.io/spring-webflow)。 Spring 为 CAS 核心代码库以及部署人员提供了完整且可扩展的框架：通过连接 CAS 和春季 API 扩展点来自定义或扩展 CAS 行为非常简单。 对 Spring 的一般知识有助于理解 某些框架组件之间的相互作用，但并非严格要求。

### 弹簧启动

中科院也在很大程度上基于 [春靴](http://projects.spring.io/spring-boot/)，这使得它能够采取意见的观点 春天平台和第三方图书馆创建一个独立的网络应用程序，而无需XML配置的麻烦尽可能多。 Spring Boot 允许 CAS 隐藏其组件及其配置的大部分内部复杂性，而是提供自动配置模块，这些模块  
并自动配置运行中的应用上下文，而不会受到太多手动干扰。 
