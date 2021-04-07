---
layout: 默认
title: CAS-CAS SAML协议
category: 通讯协定
---

# SAML协议

CAS在特定程度上支持SAML协议的版本1.1和2。 本文档涉及CAS特定的问题。

## SAML2

CAS提供对 [SAML2身份验证](../installation/Configuring-SAML2-Authentication.html)支持，从而允许CAS充当 SAML2身份提供者。

## Google Apps

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能已弃用，并计划在将来删除。</strong></p>
</div>

CAS提供了对 [Google Apps Integration](../integration/Google-Apps-Integration.html)。

## SAML 1.1

CAS支持 [标准化SAML 1.1协议](http://en.wikipedia.org/wiki/SAML_1.1) 主要用于：

- 支持 [属性释放](../integration/Attribute-Release.html)
- [单次登出](../installation/Logout-Single-Signout.html)

`/ samlValidate URI`处通过POST验证票证，可以获得SAML 1.1票证验证响应。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml</artifactId>
  <version>${cas.version}</version>
</dependency>
```

### 行政端点

CAS提供了以下端点：

| 终点             | 描述                                             |
| -------------- | ---------------------------------------------- |
| `samlValidate` | 通过提供获得SAML 1.1验证有效载荷 `的用户名`， `密码` 和 `服务` 作为参数。 |

### 样品申请

```xml
POST / cas / samlValidate？ticket =
主机：cas.example.com
内容长度：491
内容类型：text / xml

<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
    <samlp:Request xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" MajorVersion="1"
      MinorVersion="1" RequestID="_192.168.16.51.1024506224022"
      IssueInstant="2002-06-19T17:03:44.022Z">
      <samlp:AssertionArtifact>
        ST-1-u4hrm3td92cLxpCvrjylcas.example.com
      </samlp:AssertionArtifact>
    </samlp:Request>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

### 样品响应

```xml
<吗？xml版本=“ 1.0”编码=“ UTF-8”？>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <saml1p：响应xmlns：saml1p =“ urn：oasis：names：tc：SAML：1.0：protocol” InResponseTo =“ ...”。 IssueInstant="2017-08-15T06:30:04.622Z" MajorVersion="1" MinorVersion="1" ResponseID="_bf6957bad275fc74a1c079a445581441">
      <saml1p:Status>
        <saml1p:StatusCode Value="saml1p:Success" />
      </saml1p:Status>
      <saml1:Assertion xmlns:saml1="urn:oasis:names:tc:SAML:1.0:assertion" AssertionID="_d9673d8af414cc9612929480b58cb2a1" IssueInstant="2017-08-15T06:30:04.622Z" Issuer="testIssuer" MajorVersion="1" MinorVersion="1">
        <saml1:Conditions NotBefore="2017-08-15T06:30:04.622Z" NotOnOrAfter="2017-08-15T06:30:05.622Z">
          <saml1:AudienceRestrictionCondition>
            <saml1:Audience>https://google.com</saml1:Audience>
          </saml1:AudienceRestrictionCondition>
        </saml1:Conditions>
        <saml1:AuthenticationStatement AuthenticationInstant="2017-08-15T06:46:43.585Z" AuthenticationMethod="urn:ietf:rfc:2246">
          <saml1:Subject>
            <saml1:NameIdentifier>testPrincipal</saml1:NameIdentifier>
            <saml1:SubjectConfirmation>
              <saml1:ConfirmationMethod>urn:oasis:names:tc:SAML:1.0:cm:artifact</saml1:ConfirmationMethod>
            </saml1:SubjectConfirmation>
          </saml1:Subject>
        </saml1:AuthenticationStatement>
        <saml1:AttributeStatement>
          <saml1:Subject>
            <saml1:NameIdentifier>testPrincipal</saml1:NameIdentifier>
            <saml1:SubjectConfirmation>
              <saml1:ConfirmationMethod>urn:oasis:names:tc:SAML:1.0:cm:artifact</saml1:ConfirmationMethod>
            </saml1:SubjectConfirmation>
          </saml1:Subject>
          <saml1:Attribute AttributeName="testAttribute" AttributeNamespace="whatever">
            <saml1:AttributeValue>testValue</saml1:AttributeValue>
          </saml1:Attribute>
          <saml1:Attribute AttributeName="samlAuthenticationStatementAuthMethod" AttributeNamespace="whatever">
            <saml1:AttributeValue>urn:ietf:rfc:2246</saml1:AttributeValue>
          </saml1:Attribute>
          <saml1:Attribute AttributeName="testSamlAttribute" AttributeNamespace="whatever">
            <saml1:AttributeValue>value</saml1:AttributeValue>
          </saml1:Attribute>
          <saml1:Attribute AttributeName="testAttributeCollection" AttributeNamespace="whatever">
            <saml1:AttributeValue>tac1</saml1:AttributeValue>
            <saml1:AttributeValue>tac2</saml1:AttributeValue>
          </saml1:Attribute>
        </saml1:AttributeStatement>
      </saml1:Assertion>
    </saml1p:Response>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```


## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-core)。

您可能还需要在 声明以下存储库，以便能够解决依赖关系：

```groovy
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://build.shibboleth.net/nexus/content/repositories/releases” 
    }
}
```
