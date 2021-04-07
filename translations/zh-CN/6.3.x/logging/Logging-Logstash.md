---
layout: 违约
title: CAS - 日志记录配置
category: 日志 & 审计
---

# 日志记录

CAS 测井框架具有将日志消息路由到 TCP/UDP 端点的能力。 此配置假定 Logstash 服务器已启用其 [TCP 输入](https://www.elastic.co/guide/en/logstash/current/plugins-inputs-tcp.html) 端口 `9500`：

```xml
...
<Appenders>
    <Socket name="socket" host="localhost" connectTimeoutMillis="3000"
            port="9500" protocol="TCP" ignoreExceptions="false">
      <SerializedLayout />
    </Socket>
</Appenders>
...
<Logger name="org.apereo" additivity="true" level="debug">
    <appender-ref ref="cas" />
    <appender-ref ref="socket" />
</Logger>
...
```
