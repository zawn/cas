---
layout: 违约
title: 中科院 - 监测
category: 监测 & 统计
---

# 中科院春季启动管理

中科院利用 [春靴管理][bootadmindocs] ，对其内部状态进行目视管理和监控。 作为春季启动管理客户端，CAS 通过 HTTP 在春季启动管理服务器上注册，并将其状态和健康状况报告给服务器的 Web 界面。

## 管理服务器

要运行春季启动管理服务器，请使用此WAR覆盖 [](https://github.com/apereo/cas-bootadmin-overlay)。

<div class="alert alert-warning"><strong>安全端点</strong><p>请注意，必须保护管理员服务器的 API 端点。 最好同时在 HTTPS 下运行管理员服务器和注册的 CAS 服务器节点，特别是当凭据用于身份验证到端点时。</p></div>

要了解有关选项的更多了解，请 [][bootadmindocs]查看本指南。

## CAS服务器作为客户端

如果配置可用于指导 CAS 服务器如何定位并连接到管理员服务器，则每个 CAS 服务器都能够在管理员服务器上自动注册。

通过在 WAR 叠加中包括以下依赖项来增加支持：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡斯服务器支持-启动管理员-客户端</artifactId>
    <version>${cas.version}</version>
</dependency>
```

请注意，CAS 服务器的执行器端点默认已安全。 为了使 CAS 服务器和弹簧启动管理服务器之间的安全通信， [请参阅指南][bootadmindocs]。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-boot-admin-server)。

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/

[bootadmindocs]: https://codecentric.github.io/spring-boot-admin/current/
