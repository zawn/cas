---
layout: 违约
title: CAS - 地理跟踪身份验证请求
category: 认证
---

# 地理跟踪身份验证请求

身份验证请求可以映射并翻译到物理位置。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持地理位置</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 谷歌地图

使用谷歌地图地理编码 API</a>

将 身份验证请求转换为地理位置。</p> 



```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>的卡-服务器-支持-地理定位-谷歌图</artifactId>
    <version>${cas.version}</version>
</dependency>
```


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#googlemaps-geotracking)。



## 马克斯明德

使用 maxmind</a> 将 身份验证请求转换为地理位置。</p> 



```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>套机服务器支持-地理定位-最大</artifactId>
    <version>${cas.version}</version>
</dependency>
```


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#maxmind-geotracking)。
