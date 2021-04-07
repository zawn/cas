---
layout: 默认
title: CAS-CAS客户
category: 一体化
---

# 概述

CAS客户端也是一个软件包，可以与各种软件平台和应用程序集成，以便使用或更多受支持的协议与CAS服务器进行 已经开发了支持许多软件平台和产品的CAS客户。


## 官方客户

* [.NET CAS客户端](https://github.com/apereo/dotnet-cas-client)
* [Java CAS客户端](https://github.com/apereo/java-cas-client)
* [PHP CAS客户端](https://github.com/Jasig/phpCAS)
* [Apache CAS客户端](https://github.com/Jasig/mod_auth_cas)


## 其他客户

其他非官方的或正在孵化的CAS客户可能是 [在这里](https://wiki.jasig.org/display/CASC)。 鉴于以上项目是非官方的，并且不在CAS的直接维护下，因此 的可用性和准确性可能会有所不同。

## 样品

- [使用Flask的CASified Python Web应用程序](https://github.com/cas-projects/cas-sample-python-webapp)
- [使用Java CAS Client的CASified Java Web应用程序](https://github.com/cas-projects/cas-sample-java-webapp)
- [进入CLI以访问CAS管理员端点](https://github.com/cas-projects/casctl)
- [烟雾测试CAS HA部署](https://github.com/cas-projects/duct)
- [CASified Bootiful Java Web应用程序](https://github.com/cas-projects/bootiful-cas-client)
- [通过Spring Security进行CASified Bootiful Java Web应用程序](https://github.com/cas-projects/springsecurity-bootiful-cas-client)

## 框架支持

以下编程框架具有对CAS的内置支持：

* [春季安全](http://static.springsource.org/spring-security/site/)
* [阿帕克史郎](http://shiro.apache.org/cas.html)
* [Pac4j](https://github.com/pac4j/pac4j)


## 建立自己的CAS客户

由于已有许多CAS客户，因此开发CAS客户的机会很少，应尽可能避免。 确实，创建自己的客户端并不是一件容易的事，并且您最有可能产生安全漏洞。

但是，如果确实需要创建自己的CAS客户端，请注意以下不完整的准则：

* 依靠静态内部配置，而不是利用可伪造的已接收输入的行为
* 使用CAS或其他服务调用时，请确保所有外部输入均已正确解码和编码
* 确保输入经过验证，并且丢弃过多的输入。

