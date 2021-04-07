---
layout: 违约
title: CAS - 亚马逊认知认证
category: 认证
---

# 亚马逊认知认证

使用 [亚马逊认知](https://aws.amazon.com/cognito/)验证和验证凭据。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持-aws-认知-认证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-cognito-authentication)。

当您在 Amazon Cognito 管理控制台中创建 *应用客户端* 条目时，请确保该应用能够支持 `ADMIN_NO_SRP_AUTH` 身份验证流，并且 *未* 分配秘密密钥。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
