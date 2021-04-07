---
layout: 默认
title: CAS-RADIUS身份验证
category: 多因素身份验证
---

# RADIUS认证

通过仅在覆盖中包括以下依赖项来启用RADIUS支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-radius</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#radius-authentication)。

您可能还需要在 声明以下存储库，以便能够解决依赖关系：

```groovy       
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://dl.bintray.com/apereocas/jradius” 
    }
}
```

# RSA RADIUS MFA

通过仅在覆盖中包括以下依赖项来启用对MFA的RSA RADIUS OTP支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-radius-mfa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#radius-otp)。

## 资料库

您可能还需要在 声明以下存储库，以便能够解决依赖关系：

```xml 
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://jitpack.io” 
    }
}
```
