---
layout: 默认
title: CAS-登录配置
category: 记录 & 审核
---

# 登录回日志

CAS还支持 [Logback](https://logback.qos.ch/) 作为备用日志记录引擎。 在较高的级别上， 的Logback架构类似于 [Log4j](Logging.html) ，其中您具有 `Logger` `Appender` 和 `Layout` 组件，这些组件通常在 `logback.xml` 文件中定义。

请参阅 [Logback文档](https://logback.qos.ch/documentation.html) 以了解更多信息。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-logback</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您还必须确保WAR叠加层中排除了以下模块和依赖项：

```groovy
configuration.all {
    排除（组：“ org.apache.logging.log4j”，模块：“ log4j-api”）
    排除（组：“ org.apache.logging.log4j”，模块：“ log4j-web”）
    排除（组：“ org.apache.logging.log4j”，模块：“ log4j-jcl”）
    排除（组：“ org.apache.logging.log4j”，模块：“ log4j-slf4j-impl”）

    排除（组：“ org.apereo.cas”，模块：“ cas-server-core-logging”）
}
```

<div class="alert alert-warning"><strong>青年汽车</strong><p>
Java 9及更高版本的Logback支持仍未最终确定和发布。 在WAR叠加中，您可能需要严格 <i>强制</i>
将Logback和Slf4j模块版本分别设为 <code>1.2.3</code> 和 <code>1.7.5</code> 以解决JDK兼容性问题。
正式发布Logback后，预计将在将来的CAS版本中解决此问题，您应该密切注意CAS版本说明中的相关更改和修复。
</p></div>

示例 `logback.xml` 文件如下：

```xml
<吗？xml版本=“ 1.0”编码=“ UTF-8”？>
<configuration scan="true" scanPeriod="30 seconds">
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>％白色（%d{yyyy-MM-dd HH:mm:ss}） %highlight（%-5level） %cyan（%logger{15}） %msg%n</Pattern>
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
使用Logback时，CAS不会处理对日志数据进行消毒以删除敏感的凭单ID（如授予凭单的凭单或授予代理凭单的凭单）的情况。 尽管 
，但在与外部系统（例如Splunk或Syslog等）共享日志数据之前，应格外小心地清理日志数据。 
</p></div>

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logging)。
