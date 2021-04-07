---
layout: 违约
title: 中科院 - 哨兵监测集成
category: 日志 & 审计
---

# 概述

[哨兵](https://sentry.io) 允许您实时跟踪日志和错误。 它提供了对生产部署和信息的洞察，以复制和修复崩溃。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持哨兵</artifactId>
  <version>${cas.version}</version>
</dependency>
```

必须调整 [记录](../logging/Logging.html) 配置文件，以匹配以下情况：

```xml
<Configuration packages="...,org.apache.logging.log4j.core,com.getsentry.raven.log4j2">
    <Appenders>
        <Raven name="Sentry">
          <dsn><!--由哨兵提供 -></dsn>
          <tags>标签1：价值1，标签2：价值2</tags>
        </Raven>
    。。。
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

`哨兵` 应用程序可以映射到定义的任何可用的记录器元素。
