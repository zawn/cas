---
layout: 默认
title: CAS-SOAP验证
category: 验证
---

# SOAP认证

在CAS充当SOAP客户端的情况下验证和认证凭据。 将凭据提交到SOAP端点，然后进行身份验证， ，以返回用户名，一组属性以及可能松散地基于HTTP状态代码的状态，这可能有助于 确定 *帐户状态*。

当前架构如下：

```xml
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:tns="http://apereo.org/cas"
           targetNamespace="http://apereo.org/cas"
           elementFormDefault="qualified">

    <xs:element name="getSoapAuthenticationRequest">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="username" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
    <xs:element name="getSoapAuthenticationResponse">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="attributes" type="tns:MapItemType" minOccurs="0" maxOccurs="unbounded"/>
                <xs:element name="status" type="xs:int"/>
                <xs:element name="username" type="xs:string" minOccurs="0"/>
                <xs:element name="message" type="xs:string" minOccurs="0"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
    <xs:complexType name="MapItemType">
        <xs:sequence>
            <xs:element name="key" type="xs:anyType"/>
            <xs:element name="value" type="xs:anyType"/>
        </xs:sequence>
    </xs:complexType>
</xs:schema>
```

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-soap-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#soap-authentication)。

SOAP响应中返回的 `状态` 被解释为以下值之一：

| 代码    | 结果                                        |
| ----- | ----------------------------------------- |
| `200` | 成功认证。                                     |
| `403` | 产生一个 `AccountDisabledException`           |
| `404` | 产生一个 `AccountNotFoundException`           |
| `423` | 产生一个 `AccountLockedException`             |
| `412` | 产生一个 `AccountExpiredException`            |
| `428` | 产生一个 `AccountPasswordMustChangeException` |
| 其他    | 产生一个 `FailedLoginException`               |

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="org.springframework.ws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
<Logger name="org.apache.ws" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
<Logger name="org.apache.wss4j" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
