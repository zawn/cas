---
layout: 违约
title: CAS - 日志记录配置
category: 日志 & 审计
---

# 日志配置

[Loggly](https://www.loggly.com) 是一种基于云的日志管理服务，可轻松访问和分析日志中对任务至关重要的信息。 日志数据可以通过Rsyslog自动路由到日志。 使用 Rsyslog 的优点是，它可以在不阻止您的应用程序的情况下发送 TCP 事件，可以可选地加密数据，甚至队列数据以增加网络故障的稳健性。

有关详细信息，请参阅本指南</a>。</p> 



```xml
...
<Appenders>
    <Socket name="Loggly" host="localhost" port="514" protocol="UDP">
        <PatternLayout>
        <pattern>${hostName} 爪哇 %d{yyyy-MM-dd HH:mm:ss,SSS}{GMT} %p %t
            %c %M - %m%n</pattern>
        </PatternLayout>
    </Socket>
</Appenders>
...
<Loggers>
    <Root level="INFO">
        <AppenderRef ref="Loggly" />
    </Root>
</Loggers>
```
