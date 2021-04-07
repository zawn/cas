---
layout: 违约
title: CAS - 拉迪乌斯认证
category: 多因素认证
---

# 拉迪乌斯身份验证

RADIUS 支持仅通过在叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡 - 服务器支持半径</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#radius-authentication)。

您可能需要在 CAS 叠加 中申报以下存储库，以解决依赖关系：

```groovy       
存储库{
    马文{ 
        马文康森特{发布（）=
        网址"https://dl.bintray.com/apereocas/jradius" 
    }
}
```

# RSA拉迪乌斯·姆法

RSA RADIUS OTP对MFA的支持仅通过在叠加中包括以下依赖性来实现：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持半径 - mfa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#radius-otp)。

## 存储 库

您可能需要在 CAS 叠加 中申报以下存储库，以解决依赖关系：

```xml 
存储库{
    maven{ 
        马文康森特{发布（）=
        网址"https://jitpack.io" 
    }
}
```
