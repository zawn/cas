---
layout: 默认
title: CAS-Google Analytics（分析）
category: 一体化
---

# 谷歌分析

Google Analytics（分析）可用于提供有用的统计信息。 创建自定义维度和指标，以获得 洞察CAS和用户流量。


通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-google-analytics</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此外，CAS提供了在成功的身份验证事件后放入特殊cookie的功能，以便稍后处理 并由Google Analytics（分析）使用。 此cookie的值确定为主体/身份验证属性。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-analytics)。
