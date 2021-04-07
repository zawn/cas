---
layout: 默认
title: CAS-记录配置
category: 记录 & 审核
---

# 记录中

CAS提供了一种日志记录功能，用于记录重要的信息事件，例如身份验证成功和 失败；可以对其进行自定义，以生成其他信息以进行故障排除。 默认情况下，CAS使用Slf4j 记录框架作为 [Log4j引擎](http://logging.apache.org)

默认的log4j配置文件位于 `cas-server-webapp-resources` 源模块的 `src / main / resources / log4j2.xml` 中。 在 `cas.war` `cas-server-webapp-resources * .jar`的根目录中。 cas-overlay带有etc / cas / config中的外部log42.xml和属性 `logging.config = file：/etc/cas/config/log4j2.xml` 设置为引用它。 `org.apereo.cas` 代码相关的所有功能的日志记录都设置为 `INFO` 为了进行调试和诊断，您可能需要将这些级别设置为 `DEBUG` 或 `TRACE`。

<div class="alert alert-warning"><strong>生产</strong><p>您应该始终在
<code>WARN</code>下运行所有内容。 在生产中，警告和错误是您关心的事情。 其他一切都只是诊断。 如果您需要研究特定问题，则仅将
打开 <code>DEBUG</code> 或 <code>INFO</code></p></div>

## CAS Custom Log4j2插件
CAS使用的log4j2.xml文件包括自定义Log4j2插件：
- CasAppender：CasAppender包装另一个常规的附加程序，并从日志条目 删除敏感值，例如票证授予票证或代理授予票证。
- ExceptionOnlyFilter：为了允许CAS自由地在WARN和ERROR处记录意外错误而不会 ，默认情况下禁用日志中的异常，但是有log4j2.xml属性可以将其重新 默认情况下，所有异常都写入专用的stacktrace滚动日志文件 ，这是使用嵌套在CasAppender中的自定义ExceptionOnlyFilter来完成的。

## Log4j2属性
log4j2.xml文件包含用于各种设置的属性，可以 中，在类路径上 `log4j2.component.properties` 的属性文件中 属性中进行设置。 如果将属性设置为 `log4j2.component.properties`，请确保包括：
```
Log4jContextSelector = org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
```
为了继续使用默认情况下由CAS设置的异步日志记录。 要关闭异步日志记录，请将以下内容包含在 `log4j2.component.properites` 或作为系统属性：
```
Log4jContextSelector = org.apache.logging.log4j.core.selector.BasicContextSelector
```

## 配置

`log4j2.xml` 文件外部化到系统路径以保留两次升级之间的设置通常会很有帮助。 `log4j2.xml` 文件的位置在运行时类路径上，并且可以通过CAS属性

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logging)。

### 日志级别

虽然可以通过本机 `log4j2.xml` 语法直接查看日志级别，但也可以使用常规CAS属性 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logging)。

### 刷新间隔

`log4j2.xml` 本身控制日志记录配置的刷新间隔。 Log4j的功能为 可以自动检测对配置文件的更改并自行重新配置。 如果在 `monitorInterval` 属性并将其设置为非零值，则下次评估和/或记录日志事件时 开始经过 `monitorInterval` 最后检查。 这将允许您调整日志级别和配置，而无需重新启动 服务器环境。

```xml
<！-以秒为单位指定内部刷新。 >
<Configuration monitorInterval="15" ...>
...
</Configuration>
```

### 追加者

Appender负责将日志事件传递到其目的地。 追加者通常仅负责将事件数据写入 目标目的地。 在大多数情况下，他们委派负责将事件格式化为布局的责任。 一些附加程序包装其他附加程序，以便他们可以修改日志事件， `Appender`的故障，基于高级过滤标准将事件路由到下级 `Appender` 或提供不直接格式化事件的 供观看。 `Appender`始终具有一个名称，以便可以从 `Logger`s中引用它们。

以下 `Appender` 元素只是可用选项的部分集合。

| 布局                    | 描述                                                                    |
| --------------------- | --------------------------------------------------------------------- |
| `AsyncAppender`       | 接受对其他Appender的引用，并使LogEvent在单独的线程上写入它们。                               |
| `CassandraAppender`   | 将其输出写入Apache Cassandra数据库。 必须提前配置键空间和表，并且应将列映射到配置文件中。                 |
| `ConsoleAppender`     | 将其输出写入 `System.out` 或 `System.err` 其中 `System.out` 是默认目标。             |
| `故障转移Appender`        | 包装一组附加程序。 如果主Appender失败，则将依次尝试次要Appender，直到一个成功或没有其他次要Appender为止。     |
| `FileAppender`        | `fileName` 参数命名的File。                                                 |
| `CsvParameterLayout`  | 将事件的参数转换为CSV记录，而忽略该消息。                                                |
| `JDBCAppender`        | 使用标准JDBC将日志事件写入关系数据库表。                                                |
| `JPAAppender`         | `2.1`将日志事件写入关系数据库表。                                                   |
| `HttpAppender`        | 通过HTTP发送日志事件。 必须提供布局以格式化日志事件。                                         |
| `KafkaAppender`       | 将事件记录到Apache Kafka主题。 每个日志事件均作为Kafka记录发送。                             |
| `NoSQLAppender`       | 将日志事件写入NoSQL数据库；当前存在MongoDB和Apache CouchDB的提供程序实现。                    |
| `RoutingAppender`     | 评估日志事件，然后将其路由到下级 `Appender`。                                          |
| `SMTPAppender`        | 当发生特定的日志记录事件时（通常是在错误或致命错误时）发送电子邮件。                                    |
| `JeroMQ`              | ZeroMQ附加器使用JeroMQ库将日志事件发送到一个或多个ZeroMQ端点。                              |
| `RollingFileAppender` | 写入fileName参数中命名的File，然后根据 `TriggeringPolicy` 和 `RolloverPolicy`将文件翻转。 |
| `RewriteAppender`     | `Appender`处理日志事件之前对其进行操作。 这可用于掩盖敏感信息（例如密码）或将信息注入每个事件。                 |

有关完整的详细信息，请查阅官方的 [Log4j文档](http://logging.apache.org)

### 日志模式

默认情况下，通过 `log4j2.xml` 文件 个基于模式的布局来格式化日志消息。 也可以使用以下替代布局：

| 布局                   | 描述                                                                   |
| -------------------- | -------------------------------------------------------------------- |
| `CsvParameterLayout` | 将事件的参数转换为CSV记录，而忽略该消息。                                               |
| `GelfLayout`         | 以Graylog扩展日志格式（`GELF`）布置事件。                                          |
| `HTMLLayout`         | 生成一个HTML页面并将每个LogEvent添加到表中的一行                                       |
| `JSONLayout`         | 以格式正确或分散的JSON创建日志事件。                                                 |
| `模式布局`               | 甚至基于转换模式来格式化日志。                                                      |
| `RFC5424布局`          | 根据增强的Syslog规范 [RFC 5424](http://tools.ietf.org/html/rfc5424)格式化日志事件。 |
| `序列化版面`              | 日志事件被转换为可用于JMS或套接字连接的字节数组。                                           |
| `SyslogLayout`       | 将日志事件格式化为BSD Syslog记录。                                               |
| `XML布局`              | 以格式正确或分散的XML创建日志事件。                                                  |
| `YamlLayout`         | 在YAML中创建日志事件。                                                        |

要了解有关每个细节和配置设置的更多信息，请参阅 [官方Log4J指南](http://logging.apache.org)。

## 日志文件轮换

默认配置指定在启动，大小或特定时间滚动日志的触发策略。 这些策略适用于 `RollingFile` 附加程序。

例如，以下XML片段定义了以下策略：在JVM启动，日志大小达到 `10` 兆字节以及当前日期与日志的开始日期不匹配时，对日志进行滚动。

```xml
<RollingFile name="file" fileName="${baseDir}/cas.log" append="true"
                    filePattern="${baseDir}/cas-%d{yyyy-MM-dd-HH}-%i.log">
...
    <Policies>
        <OnStartupTriggeringPolicy />
        <SizeBasedTriggeringPolicy size="10 MB"/>
        <TimeBasedTriggeringPolicy interval="24" />
    </Policies>
...
</RollingFile>
```

触发策略确定是否</strong> **，也可以设计翻转策略以指示 **应该如何执行** 如果未配置任何策略，则将使用默认策略。</p>

要查找更全面的文档，请 [在这里查看指南](http://logging.apache.org)。

### 展期策略

自定义的过渡策略提供了一种删除操作，与DefaultRolloverStrategy max属性所提供的功能相比，它使用户可以更好地控制在过渡时间删除哪些文件。 删除操作使用户可以配置一个或多个条件，以选择要相对于基本目录删除的文件。

例如，以下追加程序在过渡时会删除基目录下与 `* / *。log` glob相匹配且已存在 `7` 天或更早的所有文件。

```xml
<RollingFile name="file" fileName="${baseDir}/cas.log" append="true"
             filePattern="${baseDir}/cas-%d{yyyy-MM-dd-HH}-%i.log">
...
    <DefaultRolloverStrategy max="5">
        <Delete basePath="${baseDir}" maxDepth="2">
            <IfFileName glob="*/*.log" />
            <IfLastModified age="7d" />
        </Delete>
    </DefaultRolloverStrategy>
...
</RollingFile>
```

要查找更全面的文档，请 [在这里查看指南](http://logging.apache.org)。

## 日志数据清理

为了安全起见，CAS默认情况下将尝试从所有日志数据中删除授予票证的票证和提供代理票证的票证ID。 当然，这将包括由日志框架路由到日志目标的消息以及所有审核消息。

以下是一个示例：

```bash
谁：审核：未知
：TGT-****************** 123456-cas01.example.org
行动：TICKET_GRANTING_TICKET_DESTROYED
申请：CAS
时间：周六04年7月12日：10:35 PDT 2014
客户IP地址：...
服务器的IP地址： ...
```

票证ID的末尾保留一定数量的字符，以帮助进行故障排除和诊断。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logging)。
