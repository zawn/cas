---
layout: 默认
title: CAS-代理身份验证
category: 验证
---

# 代理验证

默认情况下，对CAS v1 +协议的代理身份验证支持处于启用状态，因此，利用代理身份验证功能 

<div class="alert alert-info"><strong>服务配置</strong><p>
请注意，注册表中每个已注册的应用程序都必须显式配置为
以允许代理身份验证。 请参阅本指南</a>
<a href="../services/Service-Management.html">以了解有关在注册表中注册服务的信息。</p></div>

对于希望从策略上从安全策略上 身份验证的部署，建议禁用代理身份验证组件。

## 用例

代理身份验证最常见的用例之一是能够为 和后端 [REST-based] 服务获得票证的能力，该服务也受CAS保护。 该方案通常是：

- 用户面对受CAS保护的应用程序A。
- 后端的应用程序A需要联系服务S来生成数据。
- 服务S本身受CAS本身的保护。

因为A通过不涉及浏览器的服务器到服务方法联系服务S，所以 服务S将无法识别SSO会话已经存在。 在这些情况下， 应用程序A需要进行代理才能获得服务S的代理票证。代理票证 传递到服务S的相关端点，以便它可以通过CAS 对其进行检索和验证，并最终产生响应。

跟踪路由可能如下所示：

1. 浏览器导航到A。
2. 重定向到CAS。
3. CAS进行身份验证，并使用 `ST`
4. 尝试验证 `ST`并要求 `PGT`。
5. CAS确认 `ST` 验证，并颁发授权代理票证 `PGT`。
6. A要求CAS为服务S `PT` ，并在其请求中 `PGT`
7. CAS为服务S生成PT。
8. A与服务S端点联系，并在请求中 `PT`
9. 服务S尝试通过CAS `PT`
10. CAS验证 `PT` 并产生成功的响应。
11. 服务S接收响应，并为A生成数据。
12. A接收并在浏览器中显示数据。

有关更多信息，请参见 [CAS协议](../protocol/CAS-Protocol.html)

## 处理启用SSL的代理URL

默认情况下，CAS附带一个捆绑的HTTP客户端，该客户端部分负责回调URL 以进行代理身份验证。 请注意，在进行回调之前，该URL还需要经过CAS服务注册表 [有关更多信息，请参见本指南](../services/Service-Management.html)

如果回调URL由服务注册表授权，并且端点处于HTTPS 并且受SSL证书保护，则CAS还将尝试在建立成功连接之前 如果证书无效，已过期， 缺少其签名中的某个步骤，自签名或其他原因，则CAS将无法执行回调。

CAS的HTTP客户端确实提供了与Java平台相似的本地信任库。 建议使用此信任库来管理所有需要将 导入到平台中的证书的管理，以允许CAS成功执行回调URL。 虽然在默认情况下， 本地信任存储到CAS是空的，CAS将仍然使用 **两个** 的默认和本地信任存储。 本地信任存储应只用于课程的CAS相关的功能，以及信任存储文件 可以在CAS和Java升级结转，当然由源控制系统应该管理 台主机的所有CAS配置。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#http-client)。

## 验证响应中的PGT

`CAS20ProxyHandler` 情况下，例如调用回调URL来接收代理授予票证是不可行的， CAS配置为直接在验证响应中返回代理授予票证ID。 为了成功地在 CAS服务器和应用程序之间建立信任，客户端应用程序会生成私钥/公钥对，然后在CAS内部分配 **公钥** 仅当服务被授权接收CAS时，CAS才会使用公共密钥对代理授予票证ID进行加密，并 `<proxyGrantingTicketId>`

应用程序向 `/ p3 / serviceValidate` 端点（或 `/ p3 / proxyValidate`）发出请求，则仅通过CAS验证响应来执行代理授予票证ID的返回。 将属性返回到CAS的其他方式，例如SAML1 将 **而不** 支持额外返回代理授权票证。

<div class="alert alert-warning">如果将CAS配置为直接在验证响应中返回授予代理票证的ID，则
将 <code>pgtIou</code> 参数，并且不执行对应用程序的回调。</div>

### 注册服务

从客户端应用程序所有者那里收到公钥后，必须先在CAS服务器的服务注册表中将其注册 持有以上公共密钥的服务还必须 以接收PGT作为选择的给定属性释放策略的属性。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“ test”，
  “ id”：1，
  “evaluationOrder”：0，
  “attributeReleasePolicy”：{
    “@class”： “org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
    “authorizedToReleaseProxyGrantingTicket”：真
  }，
  “公钥”：{
    “@class “：”“ org.apereo.cas.services.RegisteredServicePublicKeyImpl”，
    “位置”：“ classpath：public.key”，
    “算法”：“ RSA”
  }
}
```

密钥对必须由希望获取PGT的应用程序本身生成。 公用密钥与CAS共享。 应用程序使用私钥来解密PGT。 生成密钥对的示例说明如下：

```bash
openssl genrsa -out private.key 4096
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outer DER -nocrypt -in private.key -out private .p8
```

`4096` 的大密钥大小，以允许CAS加密 冗长的授权代理票证。 选择较小的密钥大小可能会阻止CAS正确地 加密，因为特定大小的加密算法可以处理的长度受到限制。

### 解密PGT

一旦客户端应用程序已收到 `proxyGrantingTicket` 中CAS验证回应id属性，它可以将其解密 通过其自己的私钥。 由于该属性默认为base64编码，因此需要先进行解码，然后才能进行 解密。 这是一个示例代码片段：

```java
最终地图<？，？> 属性= ...
最终的String编码的Pgt =（字符串）attributes.get（“ proxyGrantingTicket”）;
最后的PrivateKey privateKey = ...
最终密码密码= Cipher.getInstance（privateKey.getAlgorithm（））;
最后的字节[] cred64 = encodeBase64（encodedPgt）;
cipher.init（Cipher.DECRYPT_MODE，privateKey）;
最后的字节[] cipherData = cipher.doFinal（cred64）;
返回新的String（cipherData）;
```
