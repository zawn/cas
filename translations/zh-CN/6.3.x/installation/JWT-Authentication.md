---
layout: 违约
title: 中科院 - JWT 认证
category: 认证
---

# JWT 身份验证

[JSON 网络代币](http://jwt.io/) 是一种开放的行业标准 RFC 7519 方法，用于安全地代表双方之间的索赔。 CAS 在 JWT 之上为基于令牌的身份验证提供支持，在 JWT 的凭据形式基础上，可以授予基于 SSO 会话的 SSO 会话。

## JWT 服务门票

CAS 也可以允许完全创建已签名/加密的 JWT，并以服务票的形式将其传回应用程序。 在这种情况下，JWT 完全自成一体，包含经过验证的本金以及以 JWT 索赔形式 的所有授权属性。 要了解有关此功能的更多内容，请 [查看本指南](Configure-ServiceTicket-JWT.html)。

## 概述

CAS 预计 `令牌` 参数（或请求标题）将传递到端点 `/登录` 端点。 参数值必须为 JWT。

<div class="alert alert-info"><strong>JCE 要求</strong><p>确保您在Java环境中安装适当的JCE捆绑包是安全的，该捆绑包由CAS使用，特别是如果您需要使用特定的签名/加密算法和方法。 请务必为您的Java版本选择正确的JCE版本。 爪哇版本可以通过 <code>爪哇版本</code> 命令检测到。</p></div>

以下是如何通过 Pac4j</a>生成 Jwt

示例：</p> 



```java
最终字符串签名秘密=随机使用。随机数字（256）：
最后的字符串加密秘密=随机使用。随机数字（48）：

系统.out.println（"签名秘密"+签名秘密）：
系统.out.println（"加密秘密"+加密秘密）：

最后的Jwt生成器<CommonProfile> g=新的Jwt生成器<>（）：
g.集签名配置（新秘密签名配置（签名秘密，JWSAlgorithm.HS256）：新秘密签名配置（签名秘密，JWSAlgorithm.HS256）：新秘密签名配置（签名秘密，JWSAlgorithm.HS256）：新秘密签名配置（签名秘密，JWSAlgorithm.HS256）：
g.set加密配置（新密密加密配置（加密秘密，
        JWEAlgoritm.DIR，EncryptionMethod.A192CBC_HS384）：新密加密配置（加密秘密，JWEAlgorithm.DIR，EncryptionMethod.A192CBC_HS384）：新密加密配置（加密秘密，JWEAlgorithm.DIR，EncryptionMethod.A192CBC_HS384））

最终的共同配置文件配置文件=新的共同配置文件（）：
配置文件。
最后的字符串令牌=g.生成（配置文件）：
系统. out. 打印 （"令牌： " + 令牌）;
```


生成令牌后，您可以将其传递到 CAS 的 `/登录` 端点：



```bash
/卡斯/登录？服务=&令牌=<TOKEN_VALUE>
```


`令牌` 参数也可以作为请求标题传递。



## 配置

JWT 身份验证支持通过在 WAR 叠加中包括以下依赖关系而启用：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-令牌-网络流</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jwttoken-authentication)。

在服务注册表中配置适当的服务以保存机密：



```json
{
  "@class" : "org.apereo.cas.services.RegexRegisteredService",
  "serviceId" : "https://.+",
  "name" : "testId",
  "id" : 1,
  "properties" : {
    "@class" : "java.util.HashMap",
    "jwtSigningSecret" : {
      "@class" : "org.apereo.cas.services.DefaultRegisteredServiceProperty",
      "values" : [ "java.util.HashSet", [ "<SECRET>" ] ]
    },
    "jwtEncryptionSecret" : {
      "@class" : "org.apereo.cas.services.DefaultRegisteredServiceProperty",
      "values" : [ "java.util.HashSet", [ "<SECRET>" ] ]
    },
    "jwtSigningSecretAlg" : {
      "@class" : "org.apereo.cas.services.DefaultRegisteredServiceProperty",
      "values" : [ "java.util.HashSet", [ "HS256" ] ]
    },
    "jwtEncryptionSecretAlg" : {
      "@class" : "org.apereo.cas.services.DefaultRegisteredServiceProperty",
      "values" : [ "java.util.HashSet", [ "dir" ] ]
    },
    "jwtEncryptionSecretMethod" : {
      "@class" : "org.apereo.cas.services.DefaultRegisteredServiceProperty",
      "values" : [ "java.util.HashSet", [ "A192CBC-HS384" ] ]
    }，
    "jwtSecretsAreBase64编码"：{
       "@class"："org.apereo.cas.服务.服务"，
       "价值"："java.util.HashSet"，"假"]
    =
  =

```


请注意，唯一需要的财产是 `jwt 签名秘密`。
