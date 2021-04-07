---
layout: 违约
title: CAS - 肥皂认证
category: 认证
---

# 肥皂认证

在 CAS 充当 SOAP 客户端的情况下，验证和验证凭据。 凭据被提交到 SOAP 端点，因此通过身份验证， 预期响应是返回用户名、一组属性，以及可能松散地基于 HTTP 状态代码的状态状态，这可能有助于 确定 *帐户状态*。

当前的模式如下：

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

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持肥皂认证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#soap-authentication)。

`状态` SOAP响应中返回，解释为以下值之一：

| 法典    | 结果              |
| ----- | --------------- |
| `200` | 成功的身份验证。        |
| `403` | 生成 `帐户禁用例外`     |
| `404` | 生成 `帐户未发现例外`    |
| `423` | 生成 `帐户锁定例外`     |
| `412` | 生成 `帐户初始`       |
| `428` | 生成 `帐户密码必须改变例外` |
| 其他    | 生成 `失败日志初始`     |

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

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
