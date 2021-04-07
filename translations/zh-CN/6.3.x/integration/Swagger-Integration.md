---
layout: 默认
title: CAS-Swagger API集成
category: 一体化
---

# 概述

CAS利用 [Swagger](https://swagger.io/) 优势自动生成API文档。 生成的文档支持所有CAS终结点和REST API，只要它们可用于运行时 应用程序上下文并存在于叠加层中即可。

通过在叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-swagger</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 终点

以下Swagger端点可用于分析和测试API：

| 描述            | 网址                                            |
| ------------- | --------------------------------------------- |
| Swagger API规范 | `https://sso.example.org/cas/v3/api-docs`     |
| 招摇UI          | `https://sso.example.org/cas/swagger-ui.html` |       
