---
layout: 违约
title: CAS - 短信消息
category: 通知
---

# 短信消息

CAS 提供通过短信通知用户选择操作的能力。 示例操作包括通知 或密码重置链接/令牌的风险身份验证尝试。 中科院支持的短信提供商如下。 请注意，某些 提供商可能需要主动/专业订阅。

相关模块使用以下模块自动启用/包括对短信通知的默认支持：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-核心通知</artifactId>
     <version>${cas.version}</version>
</dependency>
```

您不需要在 WAR 叠加配置中明确包含此模块，除非需要在编译时间访问组件和 ABI。 有关如何自定义或覆盖特定提供商的默认行为，请参阅下文。

## 习惯

使用您自己的自定义实现发送短信。

```java
@Bean
公开短信发送者（）{
    ...
}    
```

## 槽的

使用外部 Groovy 脚本发送短信。

```groovy
导入java.ul.*

def运行（对象[]args）{
    def从=args[0]
    def到=args[1]
    def消息=args[2]
    def记录器=args[3]

    记录器.debug（"发送消息 ${message} ${to} 从 ${from}"）
    真正的
}
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#groovy)。

## 休息

  使用回复API发送短信。 这是一个具有以下参数的 `开机自检` ：

| 田       | 描述         |
| ------- | ---------- |
| `客户地址`  | 客户端 IP 地址。 |
| `服务器地址` | 服务器 IP 地址。 |
| `从`     | 短信的地址。     |
| `自`     | 短信的目标收件人。  |

请求主体包含实际消息。 预计端点将 `200` 的状态代码。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-2)。

## 特维利奥

要了解更多信息， [访问此网站](https://www.twilio.com/)。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 短信 - twilio</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#twilio)。

## 文本魔术

要了解更多信息， [访问此网站](https://www.textmagic.com/)。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 短信 - 文本魔术</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#textmagic)。

## 点击

要了解更多信息， [访问此网站](http://www.clickatell.com/)。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 短信 - 点击</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#clickatell)。

## 亚马逊 SNS

要了解更多信息， [访问此网站](https://docs.aws.amazon.com/sns)。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 短信 - aws - sns</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#amazon-sns)。

## 内克斯莫

要了解更多信息， [访问此网站](https://dashboard.nexmo.com/)。

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 短信 - 内克斯莫</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#nexmo)。
