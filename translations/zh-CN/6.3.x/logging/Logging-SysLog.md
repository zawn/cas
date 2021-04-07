---
layout: 违约
title: CAS - 赛斯洛格记录配置
category: 日志 & 审计
---

# 赛斯洛格记录

CAS 记录框架确实能够将消息路由到外部 syslog 实例。 要配置此功能， 您首先配置 `SysLog 应用程序` ，然后指定需要路由到此实例的 消息：

```xml
...
<Appenders>
    <Syslog name="SYSLOG" format="RFC5424" host="localhost" port="8514"
            protocol="TCP" appName="MyApp" includeMDC="true" mdcId="mdc"
            facility="LOCAL0" enterpriseNumber="18060" newLine="true"
            messageId="Audit" id="App"/>
</Appenders>
...
<Logger name="org.apereo" additivity="true" level="debug">
    <appender-ref ref="cas" />
    <appender-ref ref="SYSLOG" />
</Logger>

```

您还可以在 SSL 上配置远程目的地输出，并指定相关的钥匙存储配置：

```xml
...

<Appenders>
    <TLSSyslog name="bsd" host="localhost" port="6514">
      <SSL>
        <KeyStore location="log4j2-keystore.jks" password="changeme"/>
        <TrustStore location="truststore.jks" password="changeme"/>
      </SSL>
    </TLSSyslog>
</Appenders>

...
```
