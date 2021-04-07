---
layout: 默认
title: CAS-JavaMelody监视
category: 监控 & 统计
---

# JavaMelody监视

使用 [JavaMelody](https://github.com/javamelody/javamelody) 用于监视QA和生产环境中的CAS。

通过在WAR叠加中包含以下依赖关系来添加支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-javamelody</artifactId>
    <version>${cas.version}</version>
</dependency>
```

JavaMelody监视默认情况下为 `${context-path}/ monitoring` ，其中 `${context-path}` 通常设置为 `/ cas`。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#javamelody)。
