---
layout: 默认
title: CAS-OpenID Connect身份验证
category: 验证
---

# OpenID Connect身份验证

允许CAS充当 [OpenId Connect Provider（OP）](http://openid.net/connect/)。

<div class="alert alert-info"><strong>记住</strong><p>OpenId Connect是 <a href="OAuth-OpenId-Authentication.html">OAuth协议</a> 的延续，带有一些其他变体。 如果启用OpenId Connect，则也会自动启用OAuth。 <a href="OAuth-OpenId-Authentication.html">OAuth协议</a> 支持而记录的选项和行为可能在此处同样适用。</p></div>

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-oidc</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要了解有关OpenId Connect的更多信息，请 [本指南](http://openid.net/specs/openid-connect-basic-1_0.html)。

当前的实现为以下方面提供支持：

- [授权码流程](http://openid.net/specs/openid-connect-basic-1_0.html)
- [隐式流](https://openid.net/specs/openid-connect-implicit-1_0.html)
- [动态发现](https://openid.net/specs/openid-connect-discovery-1_0.html)
- [WebFinger发行者发现](https://openid.net/specs/openid-connect-discovery-1_0-21.html)
- [OIDC客户和依赖方的管理和注册](../services/Service-Management.html)。
- 通过 [动态客户端注册协议](https://tools.ietf.org/html/draft-ietf-oauth-dyn-reg-management-01)管理和注册 [OIDC客户端和依赖方](../services/Service-Management.html)。
- 能够解决，映射和发布声明</a>能力为

。</li> 
  
  - 能够为各种令牌配置到期策略。</ul> 



## 终点

| 场地                                       | 描述                                                                                                   |
| ---------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| `/ oidc /。众所周知`                          | 发现端点用于查询CAS OIDC配置信息和元数据。                                                                            |
| `/oidc/.well-known/openid-configuration` | 与 `知名` 发现端点相同。                                                                                       |
| `/oidc/.well-known/webfinger`            | [WebFinger](http://tools.ietf.org/html/rfc7033) 发现端点                                                 |
| `/ oidc / jwks`                          | 包含服务器的公共签名密钥，客户端可以使用它们来验证CAS颁发的访问令牌和ID令牌的数字签名。                                                       |
| `/ oidc /授权`                             | 授权请求在这里处理。                                                                                           |
| `/ oidc / profile`                       | 用户个人资料请求在此处处理。                                                                                       |
| `/ oidc / introspect`                    | [内省](https://tools.ietf.org/html/rfc7662)检测给定访问令牌的状态。 该端点期望使用用户名和密码关联的 `client_id` 和 `client_secret` |
| `/ OIDC /的accessToken`， `/ OIDC /令牌`     | 产生授权的访问令牌。                                                                                           |
| `/ oidc /撤销`                             | [撤消](https://tools.ietf.org/html/rfc7009) 访问或刷新令牌。 该端点期望使用用户名和密码关联的 `client_id` 和 `client_secret`    |
| `/ oidc /注册`                             | [动态客户端注册](https://tools.ietf.org/html/draft-ietf-oauth-dyn-reg-management-01) 协议注册客户端。               |




## 注册客户

可以通过以下方式向CAS注册客户。



### 静态地

OpenID Connect客户端可以通过CAS静态 *静态注册为*



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“ client”，
  “ clientSecret”：“ secret”，
  “ serviceId”：“ ^<https://the-redirect-uri>”，
  “名称“：” OIDC“，
  ” id“：1000
}
```


请注意，作为服务定义的OpenID连接客户端是CAS [OAuth服务](OAuth-OpenId-Authentication.html) 适用于OAuth服务定义的所有设置也应同样适用于此处。 以下字段专门用于OpenID连接服务：

| 场地                                  | 描述                                                         |
| ----------------------------------- | ---------------------------------------------------------- |
| `clientId`                          | 必需的。 此客户端应用程序的标识符。                                         |
| `客户秘密`                              | 必需的。 此客户端应用程序的秘密。                                          |
| `serviceId`                         | 必需的。 此OIDC客户端的授权重定向URI。                                    |
| `supportedGrantTypes`               | 可选的。 这项服务支持的补助金类型的集合。                                      |
| `supportedResponseTypes`            | 可选的。 该服务支持的响应类型的集合。                                        |
| `signIdToken`                       | 可选的。 ID令牌是否应该签名。 默认值为 `true`。                              |
| `ks`                                | 可选的。 存放此应用程序密钥的密钥库位置的资源路径。                                 |
| `jwksCacheDuration`                 | 可选的。 过期策略时间值应用于此应用程序的已加载/缓存的键。                             |
| `jwksCacheTimeUnit`                 | 可选的。 措施的过期策略时间单元（即 `秒`， `分钟`等）施加到加载/高速缓存的密钥。               |
| `cryptoIdToken`                     | 可选的。 ID令牌是否应加密。 默认值为 `false`。                              |
| `idTokenEncryptionAlg`              | 可选的。 用于加密ID令牌的算法标头值。                                       |
| `idTokenSigningAlg`                 | 可选的。 用于对ID令牌进行签名的算法标头值。                                    |
| `userInfoSigningAlg`                | 可选的。 用于签署用户配置文件响应的算法标头值。                                   |
| `userInfoEncryptedResponseAlg`      | 可选的。 用于加密用户配置文件响应的算法标头值。                                   |
| `tokenEndpointAuthenticationMethod` | 可选的。 向令牌端点请求的客户端身份验证方法。 默认值为 `client_secret_basic`。        |
| `申请类型`                              | 可选的。 `卷筒纸`， `本机`，或空白。 定义了应用程序的种类。 如果省略，则默认值为 `web`。        |
| `idTokenEncryptionEncoding`         | 可选的。 用于加密ID令牌的算法方法标头值。                                     |
| `userInfoEncryptedResponseEncoding` | 可选的。 用于加密用户配置文件响应的算法方法标头值。                                 |
| `subjectType`                       | `public` 或 `pairwise`选择的可选值。 生成主体标识符时使用的类型。 默认值为 `public`。 |
| `segmentIdentifierUri`              | 可选的。 该URL的主机值用作成对标识符计算的扇区标识符。 如果未定义，则将使用 `serviceId`       |

<div class="alert alert-info"><strong>保留您所需要的！</strong><p>鼓励您仅保留和维护 
特定集成所需的属性和设置。 获取所有服务字段的副本并尝试根据其默认值再次进行配置是不必要的。 虽然 
您可能希望保留一份副本作为参考，这种策略最终将导致不好的升级提高重大更改和凌乱的机会 
，在该部署。</p></div>

服务定义通常由 [服务管理](../services/Service-Management.html) 设施管理并向CAS注册。

<div class="alert alert-warning"><strong>使用警告！</strong><p>今天，出于向后兼容性的原因，如果留为空白，CAS不会严格执行授权的受支持的 
 这意味着，如果未定义，则服务定义和相关策略的所有许可和响应类型都可以由 
 请注意，此行为是 <strong>，在将来的发行版 
可能会发生更改</strong> 
立即声明每个配置文件的所有授权授予/响应类型，以免日后出现意外情况。</p></div>

### 动态地

客户端应用程序可以动态地向CAS注册以进行身份验证。 默认情况下，CAS `保护` 在该模式下注册端点需要用户身份验证。 CAS设置放松此行为，以允许CAS在 `OPEN` 模式下运行。



## 设定值

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect)。



## 服务器配置

请记住，CAS的OpenID Connect功能需要会话关联性（以及可选的会话复制），因为整个登录流程中的授权响应都是通过服务器支持的会话存储机制存储的 您将需要相应地配置部署环境和负载均衡器。



## 样例客户端应用程序

- [MITREid示例Java Webapp](https://github.com/cas-projects/oidc-sample-java-webapp)



## 索偿

OpenID连接声明仅被视为普通CAS属性，需要 被 [解析，映射和释放](../integration/Attribute-Release-Policies.html)。



### 基于范围的声明

您可以链接各种基于特定范围授权声明发布的属性发布策略：



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“ ...”，
  “ clientSecret”：“ ...”，
  “ serviceId”：“ ... ”
  “名字”： “OIDC测试”，
  “标识”：10，
  “范围”： “java.util.HashSet中”， 
    [ “轮廓”， “电子邮件”， “地址”， “手机” ，“ offline_access”，“ displayName”，“ eduPerson”]
  ]
}
```


在内部对预定义声明进行目录的标准范围都属于名称空间 `org.apereo.cas.oidc.claims` 并在下面进行描述：

| 政策                                            | 描述                        |
| --------------------------------------------- | ------------------------- |
| `oacocOidcProfileScopeAttributeReleasePolicy` | 释放声明映射到规范预定义的 `配置文件` 范围。  |
| `oacocOidcEmailScopeAttributeReleasePolicy`   | 发布声明已映射到规范预定义的 `电子邮件` 范围。 |
| `oacocOidcAddressScopeAttributeReleasePolicy` | 发布声明映射到规范预定义的 `地址` 范围。    |
| `oacocOidcPhoneScopeAttributeReleasePolicy`   | 释放声明映射到规范预定义的 `电话` 范围。    |




### 映射声明

与相关联的范围的权利要求（即 `GIVEN_NAME` 为 `型材`）被固定在 的 [OpenID规范](http://openid.net/specs/openid-connect-basic-1_0.html)。 在将自定义任意属性映射到声明 设置中定义映射，以将CAS定义的属性链接到固定的给定范围。 例如，CAS配置可以 允许将属性 `sys_given_name` 的值映射并分配给权利要求 `给定名称` 而不会影响属性解析配置和所有其他启用CAS的应用程序。 

如果未定义映射，则默认情况下，CAS属性应与声明名称匹配。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect)。



### 用户定义的范围

请注意，除了标准系统作用域之外，您还可以在以下范围内使用许多属性定义自己的自定义作用域：



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“ ...”，
  “ clientSecret”：“ ...”，
  “ serviceId”：“ ... ”，
  “名称”： “OIDC测试”，
  “ID”：10，
  “范围”：[ “java.util.HashSet中”，[ “显示名”， “eduPerson”]]
}
```


这些，例如 `displayName` ，被捆绑到 `自定义` 范围中，服务和客户端可以使用和请求它们。

不过，若你想定义自己的定制范围作为什么ID连接定义的扩展 ，这样你可以捆绑属性在一起，那么你需要首先注册 `范围`， 界定其属性束，然后用它给定的服务定义例如上面的 `eduPerson` 这样的用户定义范围也可以覆盖系统范围的定义。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect)。



### 下达索偿

给定服务定义的定义范围在内部控制和构建属性释放策略。 此类属性发布 策略允许发布标准声明，将属性重新映射到标准声明或完全定义自定义声明和范围。 

还可以在 *范围* *自由格式* 属性释放策略，以自由构建和释放声明/属性。  

例如，以下服务定义将基于范围 `概要` 和 `电子邮件` 来决定相关的属性释放策略。 无需设计或列出单个声明，因为CAS将自动配置 相关的属性释放策略：



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“ client”，
  “ clientSecret”：“秘密”，
  “ serviceId”：“ ...”，
  “名称”：“ OIDC”，
  “ id”：
  “作用域”：[“ java.util.HashSet”，
    [“配置文件”，“电子邮件”]
  ]
}
```


*范围* 属性释放策略也同样适用，在以下示例中 `就有一个 <em x-id="3">声明</em>`：



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“客户端”，
  “ clientSecret”：“秘密”，
  “ serviceId”：“ ...”，
  “名称”：“ OIDC”，
  “ id”：
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnMappedAttributeReleasePolicy”，
    “ allowedAttributes”：{
      “ @class”： “ java.util.TreeMap”，
      “ userX”：“ groovy {返回attribute ['uid']。get（0）+'-X'}”
    }
  }
}
```


通过将此类自由策略链接在一起，还可以将 *自由格式* 释放策略与基于范围操作的策略混合在一起。 例如，以下策略 允许释放 `用户x` `电子邮件` 范围分配和内部定义的所有声明。



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“ client”，
  “ clientSecret”：“秘密”，
  “ serviceId”：“ ...”，
  “名“： ”OIDC“，
  ”ID“：10，
  ”attributeReleasePolicy“：{
    ”@class“： ”org.apereo.cas.services.ChainingAttributeReleasePolicy“，
    ”政策“：[
      ” java.util中。 ArrayList“，
      [
        {
          ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
          ” allowedAttributes“：[” java.util.ArrayList“，[” cn“，” uid“，” givenName “]]，
          ” order“：0  
        }，
        {
          ” @class“：” org.apereo.cas.services.ReturnMappedAttributeReleasePolicy“，
          ” allowedAttributes“：{
            ” @class“：” java.util。 TreeMap”，
            “ user-x”：“ groovy {返回attribute ['uid']。get（0）+'-X'}”
          }，
          “ order”：1
        }，
        {
          “ @class “：”“ org.apereo.cas.oidc.claims.OidcEmailScopeAttributeReleasePolicy”，
          “顺序”：2
        }
      ]
    ]
  }
}
```


要了解更多关于属性释放政策和指挥链，请 [参阅本指南](../integration/Attribute-Release-Policies.html)。



## 身份验证上下文类

授权请求的一部分，以 `acr_values` 形式实现了对身份验证上下文类引用的支持，CAS 的 [多因素身份验证功能](../mfa/Configuring-Multifactor-Authentication.html) 大多将其考虑在内。 一旦成功， `acr` 和 `amr` 值作为id令牌的一部分传递回依赖方。



## 成对标识符

当 `成对` 被用于拍摄对象的类型，CAS将计算独特 `子` 对每个扇区标识符值。 标识符 除CAS以外的任何其他方都不能不可逆，并且有点类似于CAS生成永久匿名用户 标识符的过程。 提供给每个依赖方的每个值都是不同的，以免使客户未经允许就将用户的活动关联起来。



```json
{
  “ @class”：“ org.apereo.cas.services.OidcRegisteredService”，
  “ clientId”：“ client”，
  “ clientSecret”：“秘密”，
  “ serviceId”：“ ^<https://the-redirect-uri>”，
  “ usernameAttributeProvider “：{
    ” @class“：” org.apereo.cas.services.PairwiseOidcRegisteredServiceUsernameAttributeProvider“，
    ” persistentIdGenerator“：{
      ” @class“：” org.apereo.cas.authentication.principal.OidcPairwisePersistentIdGenerator“，
      ” salt “：” aGVsbG93b3JsZA ==“
    }
  }
}
```




## 密钥库

CAS中的每个已注册应用程序都可以包含自己的密钥库，作为 `jwks` 资源。 默认情况下，可以通过CAS属性将全局密钥库 密钥库 文件的格式类似于以下内容：



```json
{
  “ keys”：[
    {
      “ d”：“ ...”，
      “ e”：“ AQAB”，
      “ n”：“ ...”，
      “ kty”：“ RSA”，
      “ kid”：“ cas”
    }
  ]
}
```


如果找不到密钥存储，CAS将尝试自动生成密钥存储，但是如果您希望手动生成一个密钥存储，则可以 [这个工具](https://mkjwk.org/) 或 [这个工具](http://connect2id.com/products/nimbus-jose-jwt/generator)来生成 JWKS。



## WebFinger发行者发现

OpenID Provider发行者发现是确定OpenID Provider位置的过程。 发行者发现是可选的；如果依赖方 通过带外机制知道OP的发行方位置，则可以跳过此步骤。

发行者发现需要以下信息来发出发现请求：

| 范围    | 描述                                                            |
| ----- | ------------------------------------------------------------- |
| `资源`  | 必需的。 作为发现请求主题的目标最终用户的标识符。                                     |
| `主持人` | 托管WebFinger服务的服务器。                                            |
| `rel` | 标识请求其位置的服务类型的URI：`http://openid.net/specs/connect/1.0/issuer` |


为了开始发现OpenID端点，最终用户向依赖方提供一个标识符。 RP将规范化规则应用于标识符，以 确定资源和主机。 然后，它 `资源` 和 `rel` 参数向CAS WebFinger端点 `GET` 请求的服务的位置。 发行人位置 **MUST** 中的WebFinger响应作为值被返回 所述的 `HREF` 一个链接阵列元件的构件用 `相对` 成员值 `http://openid.net/specs/connect/1.0/issuer`。

以下是 `webfinger` 端点的示例调用：



```bash
卷曲https://sso.example.org/cas/oidc/.well-known/webfinger?resource=acct:casuser@somewhere.example.org
```


预期响应应与以下示例匹配：



```json
{
  “受试者”： “ACCT：casuser@somewhere.example.org”，
  “链接”：[
    {
      “相对”： “http://openid.net/specs/connect/1.0/issuer”，
      “ href”：“ https://sso.example.org/cas/oidc/”
    }
  ]
}
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect-webfinger)。



### WebFinger资源UserInfo

为了确定正确的发行者，可以使用外部用户存储库（通过 `电子邮件` 或 `用户名``acct` URI方案 `webfinger` 发现端点的资源。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认库实现将 
简单地回显提供的电子邮件或用户名，等等，因为它是 <strong>只</strong> 用于演示/测试的目的有关。</p></div>

以下用户信息存储库选项可用于配置和生产。



#### Groovy UserInfo存储库

可以使用外部Groovy脚本来处理查找链接到webfinger资源的帐户的任务，该脚本的轮廓将与以下内容匹配：



```groovy
def findByUsername（Object [] args）{
    def用户名= args[0]
    def logger = args[1]
    return [用户名：用户名]
}

def findByEmailAddress（Object [] args）{
    def email = args[0]
    def logger = args[1]
    return [email：email]
}
```


脚本的预期返回值为 `Map` ，其中包含键值对象，代表用户帐户详细信息。 空的 `Map` 将指示缺少用户记录，从而导致 `404` 响应状态返回给依赖方。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect-webfinger)。



#### REST用户信息存储库

REST存储库允许CAS服务器通过配置的HTTP方法访问远程REST端点，以获取用户帐户信息。

查询数据是通过 `电子邮件` 或 `用户名` HTTP标头传递的。 返回的响应必须随附一个 `200` 状态代码，其中主体应包含 `` 表示用户帐户信息的Map 4。 所有其他响应将导致 `404` 响应状态返回给依赖方。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect-webfinger)。



#### 自定义UserInfo存储库

可以将自己的版本的webfinger用户存储库设计并注入到CAS中。 首先，您将需要设计 a `@Configuration` 类以包含您自己的 `OidcWebFingerUserInfoRepository` 实现：



```java
@Configuration（“ customWebFingerUserInfoConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类CustomWebFingerUserInfoConfiguration {

    @Bean
    公共OidcWebFingerUserInfoRepository oidcWebFingerUserInfoRepository（）{
...
    }
}
```


您的配置类需要在CAS中注册。 [有关更多详细信息，请参见本指南](../configuration/Configuration-Management-Extensions.html)
