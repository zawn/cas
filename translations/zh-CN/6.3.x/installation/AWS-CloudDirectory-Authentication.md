---
layout: 违约
title: CAS - 亚马逊云目录认证
category: 认证
---

# 亚马逊云目录认证

亚马逊云目录是 AWS 中高度可用的基于租户目录的商店。 这些目录会根据应用需要自动缩放到数亿个对象。 这让运营人员专注于开发和部署驱动业务的应用程序，而不是管理目录基础设施。

要了解更多，请 [本指南](http://docs.aws.amazon.com/directoryservice/latest/admin-guide/directory_amazon_cd.html)。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-云目录-认证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-cloud-directory-authentication)。

AWS 凭据会自动从以下来源获取，如果相关，则通过 CAS 配置实现：

1. 与 IAM 角色关联的 EC2 实例元数据。
2. 包含 `访问的外部属性文件键` 和 `密钥` 作为属性密钥。
3. AWS配置文件路径和配置文件名称。
4. 系统属性， 包括 `aws. 访问键`， `aws. 秘密钥匙` 和 `aws. 会话`
5. 包括 `AWS_ACCESS_KEY_ID`、 `AWS_SECRET_KEY` 和 `AWS_SESSION_TOKEN`的环境变量。
6. 分类路径上的属性文件为 `ascredentials.包含 <code>访问键` 和 `密钥` 作为属性密钥的属性</code> 。
7. 访问密钥和由手头配置直接提供的秘密的静态凭据（记录等）。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

" ...
<Logger name="com.amazonaws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
