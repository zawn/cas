---
layout: 默认
title: CAS-Loggly日志记录配置
category: 记录 & 审核
---

# Loggly配置

[Loggly](https://www.loggly.com) 是基于云的日志管理服务，可轻松访问和分析日志中的关键任务信息。 日志数据可以通过Rsyslog自动路由到Loggly。 使用Rsyslog的优点是它可以发送TCP事件而不会阻塞您的应用程序，可以选择加密数据，甚至可以对数据进行排队，以增强网络故障的稳定性。

有关更多信息，请参见 [本指南](https://www.loggly.com/docs/java-log4j-2/)

```xml
...
<Appenders>
    <Socket name="Loggly" host="localhost" port="514" protocol="UDP">
        <PatternLayout>
        <pattern>${hostName} 的java %d{yyyy-MM-dd HH:mm:ss,SSS}{GMT} %p %t
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
