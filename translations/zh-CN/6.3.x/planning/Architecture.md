---
layout: 默认
title: CAS-建筑
category: 规划
---

# 建筑学

![CAS体系结构图](../images/cas_architecture.png "CAS体系结构图")

## 系统组成

CAS服务器和客户端包括CAS系统体系结构的两个物理组件，它们通过各种协议进行

### CAS服务器

CAS服务器是基于Spring框架构建的Java Servlet，其主要职责是 并授予对启用CAS的服务（通常称为CAS客户端）的访问权限。 当服务器成功登录后向用户颁发票据授予票证（TGT）时，将创建SSO会话。 应用户的请求，使用TGT作为令牌，通过浏览器重定向将服务票证（ST）发行给服务。 随后，通过反向通道通信在CAS服务器上验证ST。 这些交互在CAS协议文档中进行了详细描述。


### CAS客户

术语“ CAS客户”在其常用中具有两个不同的含义。 CAS客户端是任何启用了CAS的应用程序， 可以通过受支持的协议与服务器通信。 CAS客户端也是一个软件包，可以与各种软件平台和应用程序集成 身份验证协议（例如CAS，SAML，OAuth）与CAS服务器进行通信。 已经开发了支持许多软件平台和产品

平台：

* Apache httpd服务器（[mod_auth_cas模块](https://github.com/Jasig/mod_auth_cas)）
* Java（[Java CAS客户端](https://github.com/apereo/java-cas-client)）
* .NET（[.NET CAS客户端](https://github.com/apereo/dotnet-cas-client)）
* PHP（[phpCAS](https://github.com/Jasig/phpCAS)）
* Perl（PerlCAS）
* Python（pycas）
* Ruby（rubycas客户端）

应用范围：

* 帆布
* Atlassian汇合
* Atlassian JIRA
* Drupal
* 生命之光
* 门户网站
* ...

当“ CAS客户端”一词在本手册中出现而没有进一步的限定时，它是指 组件，而不是依赖于CAS服务器（作为其客户端）的应用程序。


## 支持的协议

客户端通过几种受支持的协议中的任何一种与服务器进行通信。  所有受支持的协议在 ，但是有些协议具有使它们对于特定应用程序或用例而言理想的特征。 例如，CAS协议支持委托（代理）身份验证，而SAML协议支持属性释放和单一签出。

支持的协议：

* [CAS（版本1、2和3）](../protocol/CAS-Protocol.html)
* [SAML 1.1和2](../protocol/SAML-Protocol.html)
* [OpenID连接](../protocol/OIDC-Protocol.html)
* [OpenID](../protocol/OpenID-Protocol.html)
* [OAuth 2.0](../protocol/OAuth-Protocol.html)
* [WS联合会](../protocol/WS-Federation-Protocol.html)


## 软件组成

用三层子系统来描述CAS服务器是有帮助的：

* Web（Spring MVC / Spring Webflow）
* [售票处](../ticketing/Configuring-Ticketing-Components.html)
* [验证](../installation/Configuring-Authentication-Components.html)

几乎所有部署注意事项和组件配置都涉及这三个子系统。 Web层是与包括CAS客户端在内的所有外部系统进行通信的端点。 Web层委托到票证子系统以生成用于CAS客户端访问的票证。 SSO会话从成功身份验证后颁发票证授予票证开始，因此票证子系统经常委派给身份验证子系统。

身份验证系统通常仅在SSO会话开始时处理请求，尽管在其他情况下也可以调用它（例如，强制身份验证）。

### 春季框架

CAS使用了Spring框架的许多方面。最值得注意的是 [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) 和 [Spring Webflow](https://projects.spring.io/spring-webflow)。 核心CAS代码库以及部署程序提供了一个完整且可扩展的框架； CAS和Spring API扩展点，可以很容易地自定义或扩展CAS行为。 Spring的常识有助于理解 某些框架组件之间的相互作用，但这不是严格要求的。

### 春季靴

CAS还很大程度上基于 [Spring Boot](http://projects.spring.io/spring-boot/)，这使它可以 的观点出发，创建一个独立的Web应用程序，而不必担心XML配置的麻烦。 Spring Boot使CAS可以隐藏其组件及其配置的许多内部复杂性，而是提供自动配置模块  
并自动配置正在运行的应用程序上下文，而无需太多手动干预。 
