---
layout: 违约
title: CAS - 阿帕奇要塞认证
category: 认证
---

# 阿帕奇要塞认证

连接到 CAS 的服务可以使用 [阿帕奇要塞](http://directory.apache.org/fortress/) 处理与阿帕奇要塞的认证和授权。 这个想法是因为阿帕奇要塞没有任何SSO机制。 然而，阿帕奇要塞是遵守 `ANSI INCITS 359 RBAC`。  
[请参阅此链接](http://directory.apache.org/fortress/testimonials.html) 了解背景和历史。

## 概述

下图是典型的 CAS 部署与阿帕奇要塞集成： ![](https://cloud.githubusercontent.com/assets/493782/26521160/f9987de0-430b-11e7-833d-a0e6257a9ebd.PNG)

在上图中，CAS 将代表堡垒管理员用户将身份验证委托给要塞，该管理员用户 配置在 `` 要塞中。 CAS 会自动搜索此文件（假设类路径） ，并构建一个访问管理器组件，管理员用户作为默认通信用户到堡垒。

要启用此功能，请确保阿帕奇要塞 [安装](http://directory.apache.org/fortress/installation.html "apache fortress installation")。

下一个内容包括 WAR 叠加中的以下模块：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡斯服务器支持堡垒</artifactId>
    <version>${cas.version}</version>
</dependency>
```

此时，阿帕奇要塞的支持仅限于阿帕奇汤姆卡特作为网络容器。 在未来的版本中，将制定对码头等额外集装箱的支持。

## CAS 配置

- 配置 `堡垒.属性` 文件，并将其置于您的 `$TOMCAT_HOME/lib` ，或者您可以附加您自己的类路径配置。 文件 示例配置如下：

```properties
http.user=堡垒超级用户
http.pw]非常秘密的密码
http.主机=本地主机
http.port=8080
http.协议
```

- 把堡垒王国代理下你的 `$TOMCAT_HOME/图书馆`。
- 添加 `-Dversion®<your.fortress.version>` 到 `JAVA_OPTS` 或 `CATALINA_OPTS`。

## 客户端配置

堡垒会话存储为堡垒 `堡垒`的主要属性。 作为客户端，您需要提取 此密钥，以便以 xml 形式获得 [会话](http://directory.apache.org/fortress/gen-docs/latest/apidocs/org/apache/directory/fortress/core/model/Session.html) 。 稍后通过堡垒会话，您可以通过呼叫堡垒休息来获得角色或动态获得权限。
