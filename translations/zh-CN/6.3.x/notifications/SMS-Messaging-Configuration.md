---
layout: 默认
title: CAS-短信
category: 通知事项
---

# 短信服务

CAS提供了通过SMS消息通知用户有关选择操作的功能。 示例操作包括通知危险的身份验证尝试 或密码重置链接/令牌。 下面列出了CAS支持的SMS提供程序。 供应商可能需要活跃/专业订阅。

相关模块使用以下模块自动启用/包括对SMS通知的默认支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-core-notifications</artifactId>
     <version>${cas.version}</version>
</dependency>
```

除非需要在编译时访问组件和API，否则您无需在WAR Overlay配置中明确包含此模块。 请参阅下文，了解如何使用特定提供程序自定义或覆盖默认行为。

## 风俗

使用您自己的自定义实现发送短信。

```java
@Bean
public SmsSender smsSender（）{
...
}    
```

## Groovy

使用外部Groovy脚本发送文本消息。

```groovy
import java.util。*

def run（Object [] args）{
    def from = args[0]
    def to = args[1]
    def message = args[2]
    def logger = args[3]

    logger.debug（“将消息 ${message} 发送到 ${to} 从 ${from}“）
    是
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#groovy)。

## 休息

  使用RESTful API发送短信。 这是具有以下参数 `POST`

| 场地                | 描述        |
| ----------------- | --------- |
| `clientIpAddress` | 客户端IP地址。  |
| `服务器的IP地址`        | 服务器的IP地址。 |
| `从`               | 短信的发件人地址。 |
| `到`               | 短信的目标收件人。 |

请求正文包含实际的消息。 端点期望状态码为 `200`

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-2)。

## 特威里奥

要了解更多信息， [请访问此站点](https://www.twilio.com/)。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-sms-twilio</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#twilio)。

## TextMagic

要了解更多信息， [请访问此站点](https://www.textmagic.com/)。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-sms-textmagic</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#textmagic)。

## Clickatell

要了解更多信息， [请访问此站点](http://www.clickatell.com/)。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-sms-clickatell</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#clickatell)。

## 亚马逊SNS

要了解更多信息， [请访问此站点](https://docs.aws.amazon.com/sns)。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-sms-aws-sns</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-sns)。

## Nexmo

要了解更多信息， [请访问此站点](https://dashboard.nexmo.com/)。

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-sms-nexmo</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#nexmo)。
