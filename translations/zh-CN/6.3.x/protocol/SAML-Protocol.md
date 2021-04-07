---
layout: 违约
title: CAS - 卡斯山姆协议
category: 协议
---

# 萨姆尔议定书

CAS 在特定范围内支持 SAML 协议的 1.1 和 2 版本。 本文档涉及 CAS 特定问题。

## 萨姆尔2

CAS 为 SAML2 认证</a>提供支持，使 CAS 能够 作为 SAML2 身份提供商行事。</p> 



## 谷歌应用程序

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能被弃用，并计划在将来删除。</strong></p>
</div>

CAS 为 [谷歌应用集成](../integration/Google-Apps-Integration.html)提供支持。



## 萨姆尔 1.1

CAS 支持 [标准化 SAML 1.1 协议，主要](http://en.wikipedia.org/wiki/SAML_1.1) ：

- 支持属性释放</a>方法</li> 
  
  - [单一注销](../installation/Logout-Single-Signout.html)</ul> 

通过在 `/萨姆瓦利德URI`通过POST验证机票，获得SAML 1.1票证验证响应。

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔</artifactId>
  <version>${cas.version}</version>
</dependency>
```




### 行政终点

CAS 提供以下端点：

| 端点        | 描述                                               |
| --------- | ------------------------------------------------ |
| `萨姆尔·瓦利特` | 通过提供 `用户名`、 `密码` 和 `服务` 作为参数，获得 SAML 1.1 验证有效载荷。 |




### 示例请求



```xml
post/cas/萨姆瓦利特？票证]
主持人：cas.example.com
内容长度：491
内容类型：文本/xml

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




### 样本响应



```xml
<？xml 版本="1.0"编码="UTF-8"？>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Body>
    <saml1p：响应xmlns：saml1p="恩：绿洲：名称：tc：SAML：1.0：协议"响应"。。。" 问题灌输="2017-08-15T06：30：04.622Z"主要版本="1"次要版本="1"响应ID=" _bf6957bad275fc74a1c079a445581441">
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
            <saml1:NameIdentifier>测试原则</saml1:NameIdentifier>
            <saml1:SubjectConfirmation>
              <saml1:ConfirmationMethod>骨灰盒：绿洲：名称：tc：SAML：1.0：cm：文物</saml1:ConfirmationMethod>
            </saml1:SubjectConfirmation>
          </saml1:Subject>
        </saml1:AuthenticationStatement>
        <saml1:AttributeStatement>
          <saml1:Subject>
            <saml1:NameIdentifier>测试原则</saml1:NameIdentifier>
            <saml1:SubjectConfirmation>
              <saml1:ConfirmationMethod>骨灰盒：绿洲：名称：tc：SAML：1.0：cm：文物</saml1:ConfirmationMethod>
            </saml1:SubjectConfirmation>
          </saml1:Subject>
          <saml1:Attribute AttributeName="testAttribute" AttributeNamespace="whatever">
            <saml1:AttributeValue>测试价值</saml1:AttributeValue>
          </saml1:Attribute>
          <saml1:Attribute AttributeName="samlAuthenticationStatementAuthMethod" AttributeNamespace="whatever">
            <saml1:AttributeValue>骨灰盒：ietf：rfc：2246</saml1:AttributeValue>
          </saml1:Attribute>
          <saml1:Attribute AttributeName="testSamlAttribute" AttributeNamespace="whatever">
            <saml1:AttributeValue>值</saml1:AttributeValue>
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

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-core)。

您可能需要在 CAS 叠加 中申报以下存储库，以解决依赖关系：



```groovy
存储库{
    maven{ 
        马文康顿{发布（）=
        网址"https://build.shibboleth.net/nexus/content/repositories/releases" 
    }
}
```
