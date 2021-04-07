---
layout: 违约
title: CAS - 回退配置
category: 日志 & 审计
---

# 注销记录

CAS 还支持 [日志返还](https://logback.qos.ch/) 作为替代记录引擎。 在高层次上， 日志备份架构类似于 [Log4j](Logging.html) ，其中您有 `记录器`、 `应用程序` 和 `布局` 组件通常定义在 `回溯.xml` 文件中。

请参阅 [日志回溯文档](https://logback.qos.ch/documentation.html) 了解更多。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持-回溯</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您还必须确保以下模块和依赖项排除在 WAR 叠加之外：

```groovy
配置.all =
    排除（组："org.apache.log4j"，模块："log4j-api"）
    排除（组："org.apache.log4j"，模块："log4j-web"）
    排除（组："org.apache.log.log4j"， 模块："log4j-jcl"）
    排除（组："org.apache.log.log4j"，模块："log4j-slf4j-impl"）

    排除（组："org.apereo.cas"，模块："cas-服务器-核记录"）
}
```

<div class="alert alert-warning"><strong>基督教青年会</strong><p>
Java 9 及以上的日志备份支持尚未完全完成并发布。 在 WAR 叠加中，您可能需要严格 <i>强制</i>
日志备份和 Slf4j 模块版本，分别 <code>1.2.3</code> 和 17.5</code> <code>，以解决 JDK 兼容性问题。
一旦 Logback 正式发布，预计这将在未来的 CAS 版本中修复，并且您应该留意 CAS 发布说明中的相关更改和修复。
</p></div>

`回溯的示例.xml` 文件如下：

```xml
<？xml 版本="1.0"编码="UTF-8"？>
<configuration scan="true" scanPeriod="30 seconds">
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>% 白色 （%d{yyyy-MM-dd HH:mm:ss}） %hi格莱特 （%-5level） %c燕 （%logger{15}） - %msg%n</Pattern>
        </layout>
    </appender>
    <logger name="org.apereo.cas" level="info" additivity="false">
        <appender-ref ref="console" />
    </logger>
    <root level="info">
        <appender-ref ref="console" />
    </root>
</configuration>
```

<div class="alert alert-warning"><strong>小心</strong><p>
使用日志返还时，CAS 不会处理对日志数据进行消毒以删除敏感的票证 ID（如发票票或代理赠与票）。 虽然此 
可能会在未来版本中制定出来，但在与 Splunk 或 Syslog 等外部系统共享日志数据之前，您应该格外小心地清理日志数据。 
</p></div>

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logging)。
