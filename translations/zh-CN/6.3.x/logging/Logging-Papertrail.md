---
layout: 默认
title: CAS-Papertrail日志记录配置
category: 记录 & 审核
---

# 纸轨伐木

[Papertrail](https://papertrailapp.com) 是基于云的日志管理服务，提供聚合的日志记录工具， 灵活的系统组，团队范围的访问，长期归档，图表和分析导出，监视Webhooks等。

有关更多信息，请参见 [本指南](http://help.papertrailapp.com/kb/configuration/java-log4j-logging/#log4j2)

```xml
...
<Appenders>
    <Syslog name="Papertrail"
            host="<host>.papertrailapp.com“
            端口=” XXXXX“
            协议=” TCP“ appName =” MyApp“ mdcId =” mdc“
            设施=” LOCAL0“ enterpriseNumber =” 18060“ newLine =” true“
            format =” RFC5424“ ignoreExceptions =” false“ exceptionPattern =”％throwable{full}“>
    </Syslog>
</Appenders>
...
<Loggers>
    <Root level="INFO">
        <AppenderRef ref="Papertrail" />
    </Root>
</Loggers>
```