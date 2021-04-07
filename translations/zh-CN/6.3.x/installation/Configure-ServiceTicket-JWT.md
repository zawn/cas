---
layout: 默认
title: CAS-JWT服务票
category: 售票处
---

# JWT服务票

[JSON Web令牌](http://jwt.io/) 是一种开放的行业标准RFC 7519方法，用于在两方之间安全地表示声明。 也可能允许CAS完全创建签名/加密的JWT，并将它们以服务票证的形式传递回应用程序。

JWT完全是独立的，并且包含经过身份验证的主体以及所有JWT声明形式的授权属性。

<div class="alert alert-info"><strong>JCE要求</strong><p>
环境中安装了正确的JCE捆绑软件，特别是在需要使用特定的签名/加密算法和方法的情况下。 确保为您的Java版本 
 <code>java -version</code> 命令检测Java版本。</p></div>

## 概述

[CAS协议](../protocol/CAS-Protocol.html)定义的相同语义发布给应用程序。 `/登录` 端点接收到身份验证请求的CAS将有条件地通过请求的http方法 `票证` `JWT` 服务票证 应用程序。

默认情况下，所有JWT均由CAS根据部署期间生成和控制的密钥进行签名和加密。 可以将此类密钥 ，以解压缩JWT和访问声明。

## 流程图

<a href="../images/cas_flow_jwt_diagram.png" target="_blank"><img src="../images/cas_flow_jwt_diagram.png" alt="CAS Web流程JWT图" title="CAS Web流程JWT图" /></a>

请注意，根据上图，默认情况下，JWT请求在内部使CAS `ST` ，然后立即以 对其进行验证，以便根据与该应用程序中的注册记录相关联的策略访问已认证的主体和属性。 CAS服务注册表。 该响应转换为 `JWT` ，然后传递到客户端应用程序。

换句话说，接收服务票证（`ST`）并对其进行验证的责任全部由CAS内部处理。 该应用程序仅需要学习如何解密和解压缩最终的 `JWT` 并确保其有效性。

`JWT` 的到期时间由作为验证事件一部分返回的断言的长度控制。 如果 断言有效期长度，则到期时间由定义为CAS服务器的SSO到期策略的一部分的SSO会话的长度控制。 

<div class="alert alert-warning"><strong>没有OpenID连接</strong><p>请记住，您刚刚收到的JWT形式的票证为 
从而消除了客户验证常规服务票证的需要。 票证由CAS内部验证，您作为客户端 
的责任仅由验证JWT本身负责。 不要将此与OpenID Connect混淆。 在JWT中，令牌本身不是ID令牌， 
并且一旦认为过期就必须重新获取。 如果您需要更多，请考虑改用OpenID Connect协议。 
请注意，验证JWT的责任被推送到客户端</b> <b>，而不是CAS服务器本身。</p></div>

## 行政端点

CAS提供了以下端点：

| 终点                          | 描述                      |
| --------------------------- | ----------------------- |
| `jwtTicketSigningPublicKey` | 公开签名公用密钥，接受可选的 `服务` 参数。 |

## 配置

通过在WAR叠加中包含以下依赖关系来启用JWT支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持令牌凭证</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jwt-tickets)。

### 注册客户

在CAS服务注册表中用信号通知相关应用程序，以生成用于服务票证的JWT：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.*”，
  “ name”：“ Sample”，
  “ id”：10，
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ jwtAsServiceTicket”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”：[ “ java.util.HashSet”，[“ true”]]
    }
  }
}
```

### 配置每个服务的密钥

默认情况下，用于对JWT进行编码的签名和加密密钥对于CAS服务器是全局的，并且可以通过CAS设置进行定义。 也可以使用 逐个服务覆盖全局密钥，从而允许每个应用程序使用其自己的签名和加密密钥集。 这样做，请在注册表中将服务定义

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.*”，
  “ name”：“ Sample”，
  “ id”：10，
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ jwtAsServiceTicket”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”：[ “ java.util.HashSet”，[“ true”]]
    }，
    “ jwtAsServiceTicketSigningKey”：{
       “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
       “ values”：[“ java。 util.HashSet“，[” ...“ ]]
    }，
    “ jwtAsServiceTicketEncryptionKey”：{
         “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
         “ values”：[“ java.util.HashSet”，[“ ...” ]]
    }，
    “ jwtAsServiceTicketCipherStrategyType”：{
         “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
         “ values”：[“ java.util.HashSet”，[“ ENCRYPT_AND_SIGN”]]
    }
  }
}
```

可以使用以下密码策略类型：

| 类型                 | 描述             |
| ------------------ | -------------- |
| `ENCRYPT_AND_SIGN` | 默认策略；加密值，然后签名。 |
| `SIGN_AND_ENCRYPT` | 签名值，然后加密。      |


## JWT验证-AES

下面的 *示例* 代码片段演示了如何验证和解析CAS产生的JWT `AES`使用共享机密创建的：

```java
导入org.apache.commons.codec.binary.Base64;
import org.jose4j.jwe.JsonWebEncryption;
导入org.jose4j.jwk.JsonWebKey;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.keys.AesKey;

导入java.nio.charset.StandardCharsets;
导入java.security.Key;

...

final String signingKey =“ ...”;
最后的String加密密钥=“ ...”;

最终的钥匙钥匙=新的AesKey（signingKey.getBytes（StandardCharsets.UTF_8））;

最终的JsonWebSignature jws =新的JsonWebSignature（）;
jws.setCompactSerialization（secureJwt）;
jws.setKey（key）;
if（！jws.verifySignature（））{
    抛出新异常（“ JWT验证失败”）；
}

最后的byte []字节数= Base64.decodeBase64（jws.getEncodedPayload（）。getBytes（StandardCharsets.UTF_8））;
最后的String encodedPayload = new String（decodedBytes，StandardCharsets.UTF_8）;

最后的JsonWebEncryption jwe = new JsonWebEncryption（）;
最后的JsonWebKey jsonWebKey = JsonWebKey.Factory
    .newJwk（“\n” +“ {\” kty \“：\” oct \“，\n” +“ \” k \“：\”“ + encryptionKey +” \“\n“ +”}“）;

jwe.setCompactSerialization（decodedPayload）;
jwe.setKey（new AesKey（jsonWebKey.getKey（）。getEncoded（）））;
System.out.println（jwe.getPlaintextString（））;
```
