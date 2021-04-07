---
layout: 违约
title: CAS - 旋转安全认证
category: 多因素认证
---

# 旋转安全身份验证

旋转安全提供广泛的身份验证因子，允许使用 2FA 和基于图像的身份验证。 欲了解更多，请参阅 [官方网站](https://swivelsecure.com/)。

CAS 支持旋转安全基于 TURing 图像的身份验证。 TURing 使用 PIN 安全协议提供一次性身份验证代码。 每个图像都是该会话的独一无二的。

![图像](https://user-images.githubusercontent.com/1205228/27012173-e8e32020-4e98-11e7-935f-c5166f228bd5.png)

## 配置

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-旋转</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#swivel-secure)。

## 伐木

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="com.swiveltechnologies" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 旋转SDK

请注意，旋转 SDK 工件不会发布到 Maven 存储库。 这意味着您需要下载必要的 JAR 文件，并将内聚于您的构建配置中。 SDK 可以从 CAS 代码库</a>下载。 然后，假设 SDK 放在 [WAR 覆盖](../installation/WAR-Overlay-Installation.html) 目录的 `lib` 目录内，则可以在构建配置中引用：</p> 



```gradle
编译文件（"${projectDir}/lib/针安全.客户端.jar"）
```
