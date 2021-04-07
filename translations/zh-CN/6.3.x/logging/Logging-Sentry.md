---
layout: 默认
title: CAS-哨兵监控集成
category: 记录 & 审核
---

# 概述

[Sentry](https://sentry.io) 允许您实时跟踪日志和错误。 它提供对生产部署和信息的洞察力，以重现和修复崩溃。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-sentry</artifactId>
  <version>${cas.version}</version>
</dependency>
```

[Logging](../logging/Logging.html) 配置文件以匹配以下内容：

```xml
<Configuration packages="...,org.apache.logging.log4j.core,com.getsentry.raven.log4j2">
    <Appenders>
        <Raven name="Sentry">
          <dsn><！-由哨兵提供></dsn>
          <tags>tag1：value1，tag2：value2</tags>
        </Raven>
...
    </Appenders>
...
    <Loggers>
...
        <Logger name="org.apereo" level="info" additivity="false" includeLocation="true">
            <AppenderRef ref="casConsole"/>
            <AppenderRef ref="casFile"/>
            <AppenderRef ref="Sentry"/>
        </Logger>
...
    </Loggers>
...
</Configuration>
```

`Sentry` 附加程序可以映射到定义的任何可用记录器元素。
