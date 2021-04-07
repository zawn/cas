---
layout: 违约
title: CAS - 斯普伦克记录配置
category: 日志 & 审计
---

# 斯普伦克伐木

日志数据可以自动路由到 [斯普伦克](https://splunk.com/)。 支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 记录 - 配置 - 斯普伦克</artifactId>
     <version>${cas.version}</version>
</dependency>
```

您可能还需要在 CAS 叠加中申报以下存储库，才能解决依赖关系：

```groovy       
存储库{
    maven{ 
        马文康顿{发布（）=
        网址"https://splunk.jfrog.io/splunk/ext-releases-local" 
    }
}
```

然后，通过上述模块，您可以声明特定的应用程序，以便与 Splunk 进行通信。 下面是假设您有 Splunk 企业本地运行（IP 地址为 127.0.1 `127.0.1`）， 端口上配置了 TCP 输入 `15000`。 TCP 输入的端口编号与 斯普伦克企业管理端口不同。

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

当然，您还需要在Splunk企业中创建一个TCP输入，CAS将向该输入编写日志。
