---
layout: 默认
title: CAS-JWT认证
category: 验证
---

# JWT认证

[JSON Web令牌](http://jwt.io/) 是一种开放的行业标准RFC 7519方法，用于在两方之间安全地表示声明。 CAS在JWT的顶部提供了对基于令牌的身份验证的支持，其中可以基于JWT的凭据形式向身份

## JWT服务票

也可能允许CAS完全创建签名/加密的JWT，并将它们以服务票证的形式传递回应用程序。 在这种情况下，JWT完全是独立的，并且包含经过身份验证的主体以及所有以JWT声明的形式 要了解更多关于此功能， [请参阅本指南](Configure-ServiceTicket-JWT.html)。

## 概述

CAS希望将 `令牌` 参数（或请求标头）传递给 `/ login` 端点。 参数值必须是JWT。

<div class="alert alert-info"><strong>JCE要求</strong><p>确保您已在CAS使用的Java环境中安装了正确的JCE软件包是安全的，特别是在您需要使用特定的签名/加密算法和方法的情况下。 确保为您的Java版本选择正确的JCE版本。 <code>java -version</code> 命令检测Java版本。</p></div>

这是如何通过 [Pac4j](https://github.com/pac4j/pac4j)生成JWT的示例：

```java
final String signingSecret = RandomUtils.randomAlphanumeric（256）;
最终的String加密密钥秘密= RandomUtils.randomAlphanumeric（48）;

System.out.println（“ signingSecret” + signingSecret）;
System.out.println（“ encryptionSecret” + encryptionSecret）;

最后的JwtGenerator<CommonProfile> g =新的JwtGenerator<>（）;
g.setSignatureConfiguration（new SecretSignatureConfiguration（signingSecret，JWSAlgorithm.HS256））;
g.setEncryptionConfiguration（新的SecretEncryptionConfiguration（encryptionSecret，
        JWEAlgorithm.DIR，EncryptionMethod.A192CBC_HS384））;

最终的CommonProfile配置文件= new CommonProfile（）;
profile.setId（“ casuser”）;
最后的String令牌= g.generate（profile）;
System.out.println（“ token：” +令牌）;
```

生成令牌后，您可以将其传递给 `/ login` 端点，如下所示：

```bash
/ cas / login？service = https：// ...&令牌=<TOKEN_VALUE>
```

`令牌` 参数也可以作为请求标头传递。

## 配置

通过在WAR叠加中包含以下依赖项来启用JWT身份验证支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-token-webflow</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jwttoken-authentication)。

在服务注册表中配置适当的服务以保存机密：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ https：//.+”，
  “ name”：“ testId”，
  “ id”：
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ jwtSigningSecret”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”：[“ java.util.HashSet“，[”<SECRET>“]]
    }，
    ” jwtEncryptionSecret“：{
      ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceProperty“，
      ” values“：[” java.util .HashSet“，[”<SECRET>“]]
    }，
    ” jwtSigningSecretAlg“：{
      ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceProperty“，
      ” values“：[” java.util.HashSet“ ，[“ HS256”]]
    }，
    “ jwtEncryptionSecretAlg”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”：[“ java.util.HashSet”，[“ dir“]]
    }，
    ” jwtEncryptionSecretMethod“：{
      ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceProperty“，
      ” values“：[” java.util.HashSet“，[” A192CBC-HS384 “]]
    }，
    ” jwtSecret sAreBase64Encoded“：{
       ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceProperty“，
       ” values“：[” java.util.HashSet“，[” false“]]
    }
  }
}
```

请注意，唯一必需的属性是 `jwtSigningSecret`。
