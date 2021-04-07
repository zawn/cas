---
layout: 违约
title: CAS - 谷歌分析
category: 集成
---

# 谷歌分析

谷歌分析可用于提供有用的统计数据。 创建自定义维度和指标，以获得对 CAS 和用户流量的 洞察。


支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-谷歌-分析</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此外，CAS 还能够在成功的身份验证事件后将一个特殊的 Cookie 中掉入，以在 Google 分析 和消费。 此 Cookie 的值被确定为主/身份验证属性。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-analytics)。
