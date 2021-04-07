---
layout: 默认
title: CAS-旋转安全认证
category: 多因素身份验证
---

# 旋转安全认证

Swivel Secure提供了广泛的身份验证因素，允许使用2FA和基于图像的身份验证。 要了解更多信息，请访问 [的官方网站](https://swivelsecure.com/)。

CAS支持Swivel Secure的基于TURing映像的身份验证。 TURing使用PINsafe协议来提供用于身份验证的一次性代码。 每个图像在该会话中都是唯一的。

![图像](https://user-images.githubusercontent.com/1205228/27012173-e8e32020-4e98-11e7-935f-c5166f228bd5.png)

## 配置

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-swivel</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#swivel-secure)。

## 记录中

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="com.swiveltechnologies" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 旋转SDK

请注意，Swivel SDK工件不会发布到Maven存储库。 这意味着您将需要下载必要的JAR文件，并将其包含在您的构建配置中。 可以从CAS代码库</a>下载SDK。 然后，假设将SDK放置在 [WAR overlay](../installation/WAR-Overlay-Installation.html) `lib` 目录内，则可以在构建配置中如下引用它：</p> 



```gradle
编译文件（“${projectDir}/lib/pinsafe.client.jar”）
```
