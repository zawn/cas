---
layout: 违约
title: 中科院 - 中科院客户
category: 集成
---

# 概述

CAS 客户端也是一个软件包，可以与各种软件平台和应用程序集成，以便 使用或更多支持的协议与 CAS 服务器通信。 中科院客户支持多个软件平台和产品已经开发。


## 官方客户

* [。网CAS客户端](https://github.com/apereo/dotnet-cas-client)
* [爪哇CAS客户端](https://github.com/apereo/java-cas-client)
* [PHP 案例客户端](https://github.com/Jasig/phpCAS)
* [阿帕奇CAS客户端](https://github.com/Jasig/mod_auth_cas)


## 其他客户

其他非官方或孵化的CAS客户可能会 [在这里找到](https://wiki.jasig.org/display/CASC)。 鉴于上述项目是非官方的，不受中科院的直接维护， 其可用性和准确性可能会有所不同。

## 样品

- [使用弗拉斯克的卡化 Python 网络应用程序](https://github.com/cas-projects/cas-sample-python-webapp)
- [使用爪哇CAS客户端的CAS化爪哇网络应用程序](https://github.com/cas-projects/cas-sample-java-webapp)
- [转到 CAS 管理端点的 CLI](https://github.com/cas-projects/casctl)
- [烟雾测试 CAS HA 部署](https://github.com/cas-projects/duct)
- [卡化启动爪哇网络应用程序](https://github.com/cas-projects/bootiful-cas-client)
- [通过春季安全进行卡化启动爪哇网络应用程序](https://github.com/cas-projects/springsecurity-bootiful-cas-client)

## 框架支持

以下编程框架为 CAS 提供内置支持：

* [春季安全](http://static.springsource.org/spring-security/site/)
* [阿帕奇·希罗](http://shiro.apache.org/cas.html)
* [帕克4j](https://github.com/pac4j/pac4j)


## 构建自己的 CAS 客户端

由于许多中科院客户已经存在，因此开发CAS客户端的机会很少，应尽量避免。 事实上，创建自己的客户端并非易事，您最有可能产生安全漏洞。

不过，如果您真的需要创建自己的 CAS 客户端，请注意以下不完整的准则：

* 依靠静态内部配置，而不是利用接收到的输入上的行为，这些输入可以伪造
* 确保使用呼叫 CAS 或其他服务时，所有外部输入均正确解码和编码
* 确保输入得到验证，并丢弃过大的输入。

