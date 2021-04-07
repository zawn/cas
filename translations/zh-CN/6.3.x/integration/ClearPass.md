---
layout: 默认
title: CAS-ClearPass
category: 验证
---

# ClearPass：凭证缓存和重播

要使单点登录进入某些旧版应用程序，可能需要向他们提供实际的密码。 尽管这种方法不可避免地会增加安全风险，但有时为了将 应用程序与CAS集成起来，这可能是一个必不可少的弊端。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认情况下，ClearPass是关闭的。

配置显式打开了ClearPass，否则任何应用程序都无法获取用户凭据。 想想 <strong>非常仔细地</strong> 开启此功能之前，因为这 <strong>MUST</strong> 是
中得到一个集成的工作不得已...也许甚至没有然后。</p></div>

## 概述

CAS能够直接在CAS验证响应中发出证书密码。 处理，并获得了ClearPass服务的代理授予票证；为在客户端应用程序与CAS服务器之间建立信任关系， 本文描述了配置 ，可以应用该配置0来接收凭据密码，作为CAS验证响应中的一个属性。

为了成功地建立之间的信任 CAS服务器和应用程序，由客户端应用生成的私钥/公钥对，然后 **的公开密钥** 分布和配置内部CAS。 仅当服务被授权接收 CAS才会使用公共密钥对凭据0密码进行加密，并在验证响应中发布新的属性 `<credential>`

应用程序向 `/ p3 / serviceValidate` 端点（或 `/ p3 / proxyValidate`）发出请求，则仅由CAS验证响应来执行凭证的返回。 返回到CAS的其他方法 **而不是** 支持该值的附加返回。

另请注意，默认情况下，CAS会尝试通过其自己的预生成密钥 用于签名和加密）对内存中的缓存凭据进行加密。 当将属性发布给应用程序时，CAS将首先在内部对 解码，然后这次将尝试使用服务的公钥凭据再次对其进行加密。 [设置](../configuration/Configuration-Properties.html#clearpass)来控制此行为。

<div class="alert alert-info"><strong>通过代理进行ClearPass！</strong><p>CAS不再支持通过代理机制
 打算获取凭证
应用程序需要更新，以解决此处描述的以下方法。</p></div>

## 缓存凭证

在CAS属性中启用凭据的缓存和捕获。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#clearpass)。

## 建立金钥

密钥对必须由希望获得用户证书的应用程序本身生成。 公用密钥与CAS共享。 应用程序使用私钥来解密凭据。

```bash
openssl genrsa -out private.key 1024
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outder DER -nocrypt -in private.key -out private .p8
```

注意，虽然 `1024` 是完全没有问题，则可能需要密钥大小这样调整为越大的值 为 `4096` 以允许CAS到太长，其长度加密凭证。

## 注册公钥

从客户端应用程序所有者那里收到公钥后，必须先在CAS服务器的服务注册表中将其注册 持有以上公共密钥的服务还必须 以接收密码作为选择的给定属性释放策略的属性。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo .cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” authorizedToReleaseCredentialPassword“：true
  }，
  ” publicKey“：{
    ” @class“：” org.apereo.cas.services.RegisteredServicePublicKeyImpl“，
    ” location“：” classpath：public .key“，
    ” algorithm“：” RSA“
  }
}
```

## 解密密码

客户端应用程序在CAS验证响应中 `凭据` 属性后，便可以通过自己的私钥 由于该属性默认为base64编码，因此需要先进行解码，然后才能进行 解密。 这是一个示例代码片段：

```java
最终地图<？，？> 属性= ...
最终String编码的Psw =（String）attribute.get（“ credential”）;

/ *使用上面生成的private.key文件。 * /
最终的PrivateKey privateKey = ...
最终密码密码= Cipher.getInstance（privateKey.getAlgorithm（））;
最终字节[] cred64 = encodeBase64（encodedPsw）;
cipher.init（Cipher.DECRYPT_MODE，privateKey）;
最后的字节[] cipherData = cipher.doFinal（cred64）;
返回新的String（cipherData）;
```
