---
layout: 默认
title: CAS-SysLog日志记录配置
category: 记录 & 审核
---

# SysLog记录

CAS日志记录框架确实具有将消息路由到外部 syslog实例的功能。 要配置此属性，请先配置 ，然后再 `` 消息路由到此实例：

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

SSL配置远程目标输出，并指定相关的密钥库配置：

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
