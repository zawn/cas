---
layout: 默认
title: CAS-OAuth身份验证
category: 验证
---

# OAuth / OpenID身份验证

允许CAS充当OAuth / OpenID身份验证提供者。 请 [查看规格](https://oauth.net/2/) 以了解更多信息。

<div class="alert alert-info"><strong>CAS作为OAuth服务器</strong><p>本页专门介绍如何
OAuth / OpenID服务器支持。 
其他提供程序（例如Google，Facebook等）进行通信的OAuth / OpenID客户端 <a href="../integration/Delegate-Authentication.html">参见此页面</a>。</p></div>

## 行政端点

CAS提供了以下端点：

| 终点            | 描述                                                                                                                                                                                  |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `oauthTokens` | 管理和控制 [OAuth2访问令牌](OAuth-OpenId-Authentication.html)。 `GET` 操作将生成所有访问/刷新令牌的列表。 `DELETE` 操作将删除以参数选择器形式提供的访问/刷新令牌。 （即 `/{token}`）。 甲 `GET` 操作用的参数选择器产生 `/{token}` 将列出的所取出的存取/刷新令牌的细节。 |

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-oauth-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2)。

## 终点

启用OAuth支持后，以下端点将可用：

| 终点                                        | 描述                                                                                                                         | 方法   |
| ----------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | ---- |
| `/oauth2.0/authorize`                     | 授权用户并启动CAS身份验证流程。                                                                                                          | `得到` |
| `/oauth2.0/accessToken`，`/oauth2.0/token` | 获取纯文本或JSON的访问令牌                                                                                                            | `邮政` |
| `/oauth2.0/profile`                       | `access_token` 参数获取JSON中经过身份验证的用户配置文件。                                                                                     | `得到` |
| `/oauth2.0/introspect`                    | [内省](https://tools.ietf.org/html/rfc7662)检测给定访问令牌的状态。 该端点期望使用OAuth2服务 `client_id` 和 `client_secret` 作为用户名和密码关联的HTTP基本身份验证。 | `邮政` |
| `/oauth2.0/device`                        | [设备流协议](https://tools.ietf.org/html/draft-denniss-oauth-device-flow)批准设备用户代码。                                              | `邮政` |
| `/oauth2.0/revoke`                        | [撤消](https://tools.ietf.org/html/rfc7009) 访问或刷新令牌。 该端点期望使用OAuth2服务 `client_id` 和 `client_secret` 作为用户名和密码关联的HTTP基本身份验证。    |      |

## 回应/赠款类型

支持以下类型；它们使您可以获得代表当前用户和OAuth 客户端应用程序的访问令牌。 使用访问令牌，您将能够查询 `/ profile` 端点并获取用户配置文件。

### 授权码

授权代码类型用于UI交互：用户将输入凭据，将接收代码，并将该代码交换为访问令牌。

| 终点                      | 参数                                                                                                        | 回复                           |
| ----------------------- | --------------------------------------------------------------------------------------------------------- | ---------------------------- |
| `/oauth2.0/authorize`   | `response_type =代码&client_id =<ID>&redirect_uri =<CALLBACK>`                          | OAuth代码作为 `CALLBACK` url的参数。 |
| `/oauth2.0/accessToken` | `grant_type =授权码&client_id = ID`<br/>`&client_secret =秘密&代码= CODE&redirect_uri =回叫` | 访问令牌。                        |

#### 证明密钥交换（PKCE）

代码交换</a> 的

证明密钥（PKCE，发音为pixie）扩展描述了一种用于公共客户端减轻授权代码被拦截的威胁的技术。 该技术涉及客户端首先创建一个秘密，然后在将授权代码交换为访问令牌时再次使用该秘密。 这样，如果代码被截获，则它将无效，因为令牌请求依赖于初始机密。</p> 

`/oauth2.0/authorize` 的授权代码类型能够接受以下参数来激活PKCE：

| 范围                      | 描述                                    |
| ----------------------- | ------------------------------------- |
| `挑战`                    | 使用以下方法生成的代码质询。                        |
| `code_challenge_method` | `平原`， `S256`。 此参数是可选的，默认情况下假定 `Plain` |


`/oauth2.0/accessToken`  端点能够接受以下参数来激活PKCE：

| 范围              | 描述                               |
| --------------- | -------------------------------- |
| `code_verifier` | 应用程序最初在授权请求之前生成的PKCE请求的原始代码验证程序。 |


如果方法是 `plain`，则CAS仅需要检查提供的 `code_verifier` 与预期的 `code_challenge` 字符串匹配。 如果方法为 `S256`，则CAS应采用提供的 `code_verifier` 并使用客户端最初使用的相同方法对其进行转换。 这意味着计算验证程序的SHA256哈希并对其进行base64-url编码，然后将其与存储的 `code_challenge`进行比较。

如果验证者与期望值匹配，则CAS可以继续正常运行，发出访问令牌并做出适当响应。



### 代币/隐含

`令牌` 类型也用于UI交互以及间接非交互（即 Javascript）应用程序。

| 终点                    | 参数                                                                 | 回复                         |
| --------------------- | ------------------------------------------------------------------ | -------------------------- |
| `/oauth2.0/authorize` | `response_type =令牌&client_id = ID&redirect_uri = CALLBACK` | 访问令牌作为 `CALLBACK` url的锚参数。 |




### 资源所有者凭证

`密码` 授予类型允许OAuth客户端直接将用户的凭据发送到OAuth服务器。 对于Web和本机设备应用程序中的受信任的第一方客户端，此赠款都是可观的用户体验。

| 终点                      | 参数                                                                                                                                              | 回复    |
| ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| `/oauth2.0/accessToken` | `grant_type =密码&client_id = ID`<br/>`&client_secret =<SECRET>`<br/>`&username = USERNAME&password = PASSWORD` | 访问令牌。 |


因为 `redirect_uri` ，所以将CAS识别并在服务注册表中匹配的服务标识符作为 `client_id`。 您也可以选择传递 `服务` 或 `X服务` 标头值，以标识目标应用程序url。 标头值必须与链接到客户端ID的注册表中的OAuth服务定义匹配。



### 客户凭证

这是所有OAuth授予中最简单的授予，适用于不需要特定用户访问数据权限的 

| 终点                      | 参数                                                                             | 回复    |
| ----------------------- | ------------------------------------------------------------------------------ | ----- |
| `/oauth2.0/accessToken` | `grant_type = client_credentials&client_id = client&client_secret =秘密` | 访问令牌。 |


因为 `redirect_uri` ，所以将CAS识别并在服务注册表中匹配的服务标识符作为 `client_id`。 您也可以选择传递 `服务` 或 `X服务` 标头值，以标识目标应用程序url。 标头值必须与链接到客户端ID的注册表中的OAuth服务定义匹配。



### 刷新令牌

刷新令牌授予类型从刷新令牌（为先前的访问令牌发出）中检索新的访问令牌，当该先前的访问令牌过期时为 

| 终点                      | 参数                                                                                                                          | 回复      |
| ----------------------- | --------------------------------------------------------------------------------------------------------------------------- | ------- |
| `/oauth2.0/accessToken` | `grant_type = refresh_token&client_id =<ID>`<br/>`&client_secret =秘密&refresh_token = REFRESH_TOKEN` | 新的访问令牌。 |




### 设备流程

| 终点                      | 参数                                                                       | 回复                   |
| ----------------------- | ------------------------------------------------------------------------ | -------------------- |
| `/oauth2.0/accessToken` | `response_type =设备代码&client_id =<ID>`                          | 设备授权网址，设备代码和用户代码。    |
| `/oauth2.0/accessToken` | `response_type =设备&client_id =<ID>&代码=<DEVICE_CODE>` | 用户代码获得批准后，将使用新的访问令牌。 |




## 赠款类型选择

授权是一种获取访问令牌的方法。 确定要实施的补助金取决于最终用户将使用的客户端类型以及用户想要的体验。

![](https://alexbilbie.com/images/oauth-grants.svg)

要了解有关个人资料和赠款类型的更多信息，请 [本指南](https://alexbilbie.com/guide-to-oauth-2-grants/)。



## 注册客户

必须将每个OAuth客户端定义为CAS服务（请注意，新的 *clientId* 和 *clientSecret* 属性专门针对OAuth）：



```json
{
  “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
  “ clientId”：“ clientid”，
  “ clientSecret”：“ clientSecret”，
  “ serviceId”：“ ^（https | IMAPS）：//<redirect-uri>* “
  ”名称“： ”OAuthService“，
  ”ID“：100，
  ”supportedGrantTypes“：[ ”java.util.HashSet中“，[” ... “”。 ..” ]]，
  “ supportedResponseTypes”：[“ java.util.HashSet”，[“ ...”，“ ...” ]]
}
```


支持以下字段：

| 场地                       | 描述                                                                                                                                    |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| `clientId`               | 应用程序/服务的客户端标识符。                                                                                                                       |
| `客户秘密`                   | 应用程序/服务的客户端密码。                                                                                                                        |
| `supportedGrantTypes`    | 这项服务支持的补助金类型的集合。                                                                                                                      |
| `supportedResponseTypes` | 该服务支持的响应类型的集合。                                                                                                                        |
| `绕过ApprovalPrompt`       | 是否应跳过批准提示/同意屏幕。 默认值为 `false`。                                                                                                         |
| `generateRefreshToken`   | 是否应该与访问令牌一起生成刷新令牌。 默认值为 `false`。                                                                                                      |
| `renewRefreshToken`      | 每当请求新的访问令牌时，现有的刷新令牌是否应该过期，是否生成一个新的令牌（并随其发送）（ `grant_type` = `refresh_token`）。 只有将 `generateRefreshToken` 设置为 `true`才可能。 默认值为 `false`。 |
| `jwtAccessToken`         | 是否应将访问令牌创建为JWT。 默认值为 `false`。                                                                                                         |
| `serviceId`              | 授权类型不需要授权重定向URI的模式，或与 `clientId` 相同的模式，授予类型不需要 `redirect_uri` `client_credentials`等）。                                                 |

<div class="alert alert-info"><strong>保留您所需要的！</strong><p>鼓励您仅保留和维护特定集成所需的属性和设置。 它是 <strong>UNNECESSARY</strong> 以获取所有服务字段的副本，并尝试根据其默认值再次对其进行配置。 尽管您可能希望保留一份副本作为参考，但此策略最终会导致升级不佳，从而增加破坏更改的可能性，并造成混乱的部署。</p></div>

服务定义通常由 [服务管理](../services/Service-Management.html) 设施管理。

<div class="alert alert-warning"><strong>使用警告！</strong><p>今天，出于向后兼容的原因，CAS并没有严格执行授权的受支持响应/授权类型的收集。 这意味着，如果未定义，则服务定义和相关策略可能会允许所有授予和响应类型。 请注意，此行为是 <strong>，在将来的版本中可能会更改</strong> ，因此，强烈建议在服务定义中立即声明每个配置文件的所有授权授予/响应类型，以免日后出现意外情况。</p></div>

### 可加密的客户端机密

OAuth依赖方的客户机密可以定义为以 `{cas-cipher}`为前缀的加密值：



```json
{
  “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
  “ clientId”：“ clientid”，
  “ clientSecret”：“{cas-cipher}eyJhbGciOiJIUzUxMiIs ...”，
  “ serviceId”： “ ^（https | imaps）：//<redirect-uri>。*”，
  “ name”：“ Sample”，
  “ id”：100
}
```


可以使用CAS提供的密码操作手动或通过 [CAS命令行外壳](Configuring-Commandline-Shell.html)来加密客户端密钥。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2)。



### 属性发布

每个OAuth服务都定义了属性/声明过滤和发布策略。 有关更多信息，请参见 [本指南](../integration/Attribute-Release-Policies.html)



## OAuth令牌过期政策

OAuth令牌的过期策略由CAS设置和属性控制。 请注意，尽管访问令牌和刷新令牌可能具有其自己的生存期和过期策略，但它们通常是CAS单一登录会话长度的上限。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2)。



### 每项服务

可以根据每个应用程序有条件地确定某些OAuth令牌的到期策略。 令牌到期策略要偏离默认配置的候选服务 必须按以下代码片段所示进行设计。



#### OAuth代码



```json
{
  “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
  “ clientId”：“ clientid”，
  “ clientSecret”：“ clientSecret”，
  “ serviceId”：“ ^（https | IMAPS）：//<redirect-uri>。* “
  ”名称“： ”OAuthService“，
  ”ID“：100，
  ”codeExpirationPolicy“：{
    ”@class“：” org.apereo.cas.support.oauth。 services.DefaultRegisteredServiceOAuthCodeExpirationPolicy”，
    “numberOfUses”：1，
    “传输TimeToLive”： “10”
  }
}
```




#### OAuth访问令牌



```json
{
  “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
  “ clientId”：“ clientid”，
  “ clientSecret”：“ clientSecret”，
  “ serviceId”：“ ^（https | IMAPS）：//<redirect-uri>。* “
  ”名称“： ”OAuthService“，
  ”ID“：100，
  ”accessTokenExpirationPolicy“：{
    ”@class“：” org.apereo.cas.support.oauth。 services.DefaultRegisteredServiceOAuthAccessTokenExpirationPolicy“，
    ” maxTimeToLive“：” 1000“，
    ” timeToLive“：” 100“
  }
}
```




#### OAuth设备令牌



```json
{
  “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
  “ clientId”：“ clientid”，
  “ clientSecret”：“ clientSecret”，
  “ serviceId”：“ ^（https | IMAPS）：//<redirect-uri>。* “
  ”名称“： ”OAuthService“，
  ”ID“：100，
  ”accessTokenExpirationPolicy“：{
    ”@class“：” org.apereo.cas.support.oauth。 services.DefaultRegisteredServiceOAuthDeviceTokenExpirationPolicy“，
    ” timeToLive“：” 100“
  }
}
```




#### OAuth刷新令牌



```json
{
  “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
  “ clientId”：“ clientid”，
  “ clientSecret”：“ clientSecret”，
  “ serviceId”：“ ^（https | IMAPS）：//<redirect-uri>。* “
  ”名称“： ”OAuthService“，
  ”ID“：100，
  ”accessTokenExpirationPolicy“：{
    ”@class“：” org.apereo.cas.support.oauth。 services.DefaultRegisteredServiceOAuthRefreshTokenExpirationPolicy“，
    ” timeToLive“：” 100“
  }
}
```




## JWT访问令牌

默认情况下，OAuth访问令牌被创建为不透明标识符。 还可以选择基于每个服务将JWT生成为访问令牌：



```json
{
    “ @class”：“ org.apereo.cas.support.oauth.services.OAuthRegisteredService”，
    “ clientId”：“ clientid”，
    “ clientSecret”：“ clientSecret”，
    “ serviceId”：“ ^（https | IMAPS）：//<redirect-uri>* “
    ”名称“： ”OAuthService“，
    ”ID“：100，
    ”jwtAccessToken“：真，
    ”特性“：{
      ”@class“：”的java.util .HashMap”，
      “ accessTokenAsJwtSigningKey”：{
         “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
         “ values”：[“ java.util.HashSet”，[“ ...” ]]
      }，
      “ accessTokenAsJwtEncryptionKey”：{
           “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
           “ values”：[“ java.util.HashSet”，[“ ...” ]]
      }，
      “ accessTokenAsJwtSigningEnabled”：{
         “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
         “ values”：[“ java.util.HashSet”，[“ true”]]
      }，
      “ accessTokenAsJwtEncryptionEnabled”：{
         “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
         “ values”：[“ java.util.HashSet”，[“ true”]]
      }，
      “ accessTokenAsJwtCipherStrategyType”：{
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


签名和加密密钥也可以按服务定义，也可以通过CAS设置进行全局定义。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2)。



## OAuth用户个人资料结构

可以使用以下选项由应用程序渲染和使用请求的用户配置文件。



### 巢状

默认情况下，所请求的用户配置文件使用 `NESTED` 格式呈现，其中，经过身份验证的主体和属性分别放置在最终结构的 `id` 和 `attribute`



```json
{
  “ id”：“ casuser”，
  “属性”：{
    “ email”：“ casuser@example.org”，
    “ name”：“ CAS”
  }，
  “ something”：“ else”
}
```




### 平坦的

此选项将主要属性展平一度，使它们与 `id`处于同一级别。 最终有效负载中的其他嵌套元素保持不变。



```json
{
  “ id”：“ casuser”，
  “ email”：“ casuser@example.org”，
  “ name”：“ CAS”，
  “ something”：“ else”
}
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2)。



### 风俗

如果您希望创建自己的配置文件结构，则需要设计一个组件并将其注册到CAS以处理用户配置文件的呈现：



```java
包org.apereo.cas.support.oauth;

@Configuration（“ MyOAuthConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyOAuthConfiguration {

    @Bean
    @RefreshScope
    公共OAuth20UserProfileViewRenderer oauthUserProfileViewRenderer（）{
...
    }
}
```


[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。



## 节流

`/oauth2.0/accessToken` 启用身份验证限制，前提是该支持包括在覆盖中，以 [打开身份验证 限制](Configuring-Authentication-Throttling.html) 支持。 然后，为支持节流的OAuth端点激活处理通常的CAS服务器端点以进行身份验证 

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2)。



## 服务器配置

请记住，CAS的OAuth功能需要会话亲缘关系（以及可选的会话复制），因为整个登录流程中的授权响应都是通过服务器支持的会话存储机制存储的 您将需要配置您的Deployment 环境和相应的负载均衡器。



## 样例客户端应用程序

- [OAuth2示例Webapp](https://github.com/cas-projects/oauth2-sample-java-webapp)



# OpenID验证

要将CAS配置为充当OpenID提供者，请 [参见此页面](../protocol/OpenID-Protocol.html)。
