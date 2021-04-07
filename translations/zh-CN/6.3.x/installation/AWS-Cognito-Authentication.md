---
layout: 默认
title: CAS-Amazon Cognito身份验证
category: 验证
---

# Amazon Cognito身份验证

[Amazon Cognito](https://aws.amazon.com/cognito/)验证和认证凭据。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aws-cognito-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-cognito-authentication)。

在 *应用程序客户端* 条目时，请确保该应用程序能够支持 `ADMIN_NO_SRP_AUTH` 身份验证流程，并且为 *NOT* 分配了密钥。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
