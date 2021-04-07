---
layout: 默认
title: CAS-监控
category: 监控 & 统计
---

# CAS Spring Boot管理

CAS利用 [Spring Boot Admin][bootadmindocs] 来可视化地管理和监视其内部状态。 作为Spring Boot Admin客户端，CAS通过HTTP向Spring Boot Admin服务器注册，并将其状态和运行状况报告给服务器的Web界面。

## 管理服务器

要运行Spring Boot Admin服务器，请使用 [此WAR叠加层](https://github.com/apereo/cas-bootadmin-overlay)。

<div class="alert alert-warning"><strong>安全端点</strong><p>请注意，必须确保管理服务器的API端点是安全的。 最好同时在HTTPS下运行管理服务器和正在注册的CAS服务器节点，尤其是在使用凭据对端点进行身份验证的情况下。</p></div>

要了解有关选项的更多信息，请参见本指南</a>

。</p> 



## CAS服务器作为客户端

每个单独的CAS服务器都具有向管理服务器自动注册的能力，前提是可以进行配置以指示CAS服务器如何定位和连接到管理服务器。

通过在WAR叠加中包含以下依赖关系来添加支持：



```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-bootadmin-client</artifactId>
    <version>${cas.version}</version>
</dependency>
```


请注意，默认情况下，CAS服务器的执行器端点是安全的。 为了允许CAS服务器和Spring Boot Admin服务器之间的安全通信，请使用 [][bootadmindocs]。



## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-boot-admin-server)。

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/
