---
layout: 违约
title: CAS - 爪哇旋律监测
category: 监测 & 统计
---

# 爪哇旋律监测

使用 [爪哇旋律](https://github.com/javamelody/javamelody) 是监测CAS在QA和生产环境。

通过在 WAR 叠加中包括以下依赖项来增加支持：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 贾瓦梅洛迪</artifactId>
    <version>${cas.version}</version>
</dependency>
```

JavaMelody 监测默认在 `${context-path}/监测` 中暴露，其中 `${context-path}` 通常设置为 `/cas`。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#javamelody)。
