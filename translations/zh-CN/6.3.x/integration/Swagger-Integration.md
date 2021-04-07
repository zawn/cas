---
layout: 违约
title: CAS - 斯瓦格API集成
category: 集成
---

# 概述

CAS 利用 [斯瓦格](https://swagger.io/) 自动生成 API 文档。 生成的文档支持所有 CAS 端点和 REST ABI，前提是它们可以提供给运行时间 应用上下文，并且存在于叠加中。

支持通过在叠加中包括以下依赖关系而启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 摇摆</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 端点

以下斯瓦格端点可用于分析和测试 ABI：

| 描述       | 网址                                            |
| -------- | --------------------------------------------- |
| 斯瓦格API规范 | `https://sso.example.org/cas/v3/api-docs`     |
| 斯瓦格 · 乌伊 | `https://sso.example.org/cas/swagger-ui.html` |       
