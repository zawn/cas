---
layout: 默认
title: CAS-Amazon Cloud Directory身份验证
category: 验证
---

# Amazon Cloud Directory身份验证

Amazon Cloud Directory是AWS中高度可用的基于多租户目录的存储。 这些目录会根据应用程序的需要自动缩放到数亿个对象。 这使运营人员可以专注于开发和部署可推动业务发展的应用程序，而不是管理目录基础结构。

要了解更多信息，请 [参见本指南](http://docs.aws.amazon.com/directoryservice/latest/admin-guide/directory_amazon_cd.html)。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas服务器支持云目录身份验证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-cloud-directory-authentication)。

在相关时会自动从以下来源获取AWS凭证，并通过CAS配置使其成为可能：

1. 链接到IAM角色的EC2实例元数据。
2. 外部属性文件，其中包含 `accessKey` 和 `secretKey` 作为属性键。
3. AWS配置文件路径和配置文件名称。
4. 包括系统属性 `aws.accessKeyId`， `aws.secretKey` 和 `aws.sessionToken`
5. `AWS_ACCESS_KEY_ID` `AWS_SECRET_KEY` 和 `AWS_SESSION_TOKEN`环境变量。
6. 类路径上的属性文件为 `awscredentials.properties` ，其中包含 `accessKey` 和 `secretKey` 作为属性键。
7. 访问密钥和机密的静态凭据直接由手头的配置提供（日志记录等）。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

xml ...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
