---
layout: 默认
title: CAS-Splunk日志记录配置
category: 记录 & 审核
---

# Splunk记录

日志数据可以自动路由到 [Splunk](https://splunk.com/)。 通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-logging-config-splunk</artifactId>
     <version>${cas.version}</version>
</dependency>
```

您可能还需要在CAS覆盖中声明以下存储库，以便能够解决依赖关系：

```groovy       
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://splunk.jfrog.io/splunk/ext-releases-local” 
    }
}
```

通过上述模块，您可以声明一个特定的附加程序以与Splunk进行通信。 下面的示例假定您具有本地运行的Splunk Enterprise（IP地址为 `127.0.0.1`）， `15000`上配置了TCP输入。 TCP输入的端口号与 Splunk Enterprise管理端口不同。

```xml
<Appenders>
   <Socket name="SocketAppender" host="127.0.0.1" port="15000">
      <PatternLayout pattern="%p: %m%n" charset="UTF-8"/>
   </Socket>
...
   <SplunkAppender name="SplunkAppender">
      <AppenderRef ref="SocketAppender" />
   </SplunkAppender>
</Appenders>
...
<Loggers>
   <Logger name="org.apereo" level="debug">
      <AppenderRef ref="SplunkAppender"/>
   </Logger>
</Loggers>
```

当然，您需要在Splunk Enterprise中创建一个TCP输入，CAS将向其写入日志。
