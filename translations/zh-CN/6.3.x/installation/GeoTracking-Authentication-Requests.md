---
layout: 默认
title: CAS-GeoTracking身份验证请求
category: 验证
---

# GeoTracking身份验证请求

身份验证请求可以映射并转换到物理位置。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-geolocation</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 谷歌地图

使用 [Google Maps Geocoding API](https://developers.google.com/maps/documentation/geocoding/start) 将 身份验证请求转换为地理位置。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-geolocation-googlemaps</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#googlemaps-geotracking)。

## 最大思维

使用 [Maxmind](https://www.maxmind.com/en/home) 将 身份验证请求转换为地理位置。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-geolocation-maxmind</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#maxmind-geotracking)。
