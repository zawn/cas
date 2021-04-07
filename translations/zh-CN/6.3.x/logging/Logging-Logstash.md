---
layout: 默认
title: CAS-Logstash日志记录配置
category: 记录 & 审核
---

# Logstash日志记录

CAS日志记录框架具有将日志消息路由到TCP / UDP端点的功能。 此配置假定Logstash服务器已在端口 `9500`[TCP输入](https://www.elastic.co/guide/en/logstash/current/plugins-inputs-tcp.html)：

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
