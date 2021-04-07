---
layout: 违约
title: CAS - 纸挂记录配置
category: 日志 & 审计
---

# 纸面记录

[纸轨](https://papertrailapp.com) 是一种基于云的日志管理服务，提供聚合伐木工具、 灵活的系统组、全团队访问、长期档案、图表和分析出口、监控网钩等。

有关详细信息，请参阅本指南</a>。</p> 



```xml
...
<Appenders>
    <Syslog name="Papertrail"
            host="<host>.papertrailapp.com"
            端口="XXX"
            协议="TCP"应用名="MyApp"mdcId="mdc"
            设施="LOCAL0"企业N "18060"新线="真"
            格式="RFC5424"忽略例外="假"例外="可投掷{full}%">
    </Syslog>
</Appenders>
...
<Loggers>
    <Root level="INFO">
        <AppenderRef ref="Papertrail" />
    </Root>
</Loggers>
```