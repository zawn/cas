---
layout: 默认
title: CAS-Apache Fortress身份验证
category: 验证
---

# Apache Fortress身份验证

连接到CAS的服务可以使用 [Apache Fortress](http://directory.apache.org/fortress/) 来处理对Apache Fortress的身份验证和授权。 之所以这样做，是因为Apache Fortress没有任何SSO机制。 但是，Apache Fortress符合 `ANSI INCITS 359 RBAC`。  
[有关背景和历史记录，请参见此链接](http://directory.apache.org/fortress/testimonials.html)

## 概述

下图是与Apache Fortress集成的典型CAS部署： ![](https://cloud.githubusercontent.com/assets/493782/26521160/f9987de0-430b-11e7-833d-a0e6257a9ebd.PNG)

在上图中，CAS将代表Fortress管理员用户将身份验证委派给Fortress，该用户在 `fortress.properties` 文件中 CAS自动搜索此文件（假定类路径）为 并使用admin用户作为要塞的默认通信用户来构造访问管理器组件。

要启用此功能，确保阿帕奇要塞是 [安装](http://directory.apache.org/fortress/installation.html "apache fortress installation")。

接下来，在WAR叠加层中包含以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持堡垒</artifactId>
    <version>${cas.version}</version>
</dependency>
```

目前，Apache Fortress支持仅限于作为Web容器的Apache Tomcat。 在将来的版本中将支持对其他容器（如Jetty）的支持。

## CAS配置

- 配置 `fortress.properties` 文件并将其放在 `$TOMCAT_HOME/ lib` 或者可以附加自己的类路径配置。 下面是一个示例配置 文件：

```properties
http.user =堡垒超级用户
http.pw = verysecretpassword
http.host = localhost
http.port = 8080
http.protocol = http
```

- 将Fortress Realm Proxy放在您的 `$TOMCAT_HOME/ lib`。
- 将 `-Dversion =<your.fortress.version>` 添加到 `JAVA_OPTS` 或 `CATALINA_OPTS`。

## 客户端配置

要塞会话存储为要塞会话</code>。 作为客户端，您需要提取 
这个键，以便以xml形式 <a href="http://directory.apache.org/fortress/gen-docs/latest/apidocs/org/apache/directory/fortress/core/model/Session.html">Session</a> 
 稍后在Fortress会话中，您可以通过调用Fortress Rest来动态获取角色或获得许可。</p>
