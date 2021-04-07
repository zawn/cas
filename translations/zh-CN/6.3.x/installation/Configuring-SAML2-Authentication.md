---
layout: 默认
title: CAS-SAML2身份验证
category: 通讯协定
---

# SAML2身份验证

CAS可以充当SAML2身份提供者，接受身份验证请求并生成SAML声明。

如果您打算允许CAS将身份验证委派给外部SAML2身份提供者，则需要 [本指南](../integration/Delegate-Authentication.html)。

<div class="alert alert-info"><strong>SAML规范</strong><p>本文档仅侧重于如何在CAS内打开SAML2支持。 并非要描述/解释SAML2协议本身的众多特征。 如果不确定此页面上引用的概念，请先阅读 <a href="http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0.html">SAML2规范</a>。</p></div>

## 联邦互操作性评估

CAS项目致力于遵循</a>SAML V2.0实施配置文件。 针对当前的CAS版本的要求进行评估，请 [这里](https://docs.google.com/spreadsheets/d/1NYN5n6AaNxz0UxwkzIDuXMYL1JUKNZZlSzLZEDUw4Aw/edit?usp=sharing)。 建议您在需要验证的地方查看，评估和评论当前不存在或标记为可疑的功能。</p> 



## SAML端点

以下CAS端点响应受支持的SAML2配置文件：

- `/ idp / profile / SAML2 / Redirect / SSO`
- `/ idp / profile / SAML2 / POST / SSO`
- `/ idp / profile / SAML2 / POST-SimpleSign / SSO`
- `/ idp / profile / SAML2 / POST / SLO`
- `/ idp / profile / SAML2 / Redirect / SLO`
- `/ idp / profile / SAML2 /未经请求/ SSO`
- `/ idp / profile / SAML2 / SOAP / ECP`
- `/ idp / profile / SAML2 / SOAP / AttributeQuery`
- `/ idp / profile / SAML1 / SOAP / ArtifactResolution`



## IdP元数据

以下CAS端点处理SAML2元数据的生成：

- `/ idp /元数据`

接收到GET请求后，此端点将显示CAS IdP SAML2元数据。 如果元数据已经可用并生成，则将显示 如果缺少元数据，将自动生成一个。 下面的CAS配置指示将在何处生成和存储元数据文件/密钥。

请注意，端点可以通过实体ID或数字标识符 `服务` 该参数 与CAS服务注册表匹配，允许端点计算和组合可能已指定的 

这是一个生成的元数据文件作为示例：



```xml
<吗？xml版本=“ 1.0”编码=“ UTF-8”？>
<EntityDescriptor xmlns="urn:oasis:names:tc:SAML:2.0:metadata" xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
                xmlns:shibmd="urn:mace:shibboleth:metadata:1.0" xmlns:xml="http://www.w3.org/XML/1998/namespace"
                xmlns:mdui="urn:oasis:names:tc:SAML:metadata:ui" entityID="ENTITY_ID">
    <IDPSSODescriptor protocolSupportEnumeration="urn:oasis:names:tc:SAML:2.0:protocol">
        <Extensions>
            <shibmd:Scope regexp="false">范围</shibmd:Scope>
        </Extensions>
        <KeyDescriptor use="signing">
            <ds:KeyInfo>
              <ds:X509Data>
                  <ds:X509Certificate>...</ds:X509Certificate>
              </ds:X509Data>
            </ds:KeyInfo>
        </KeyDescriptor>
        <KeyDescriptor use="encryption">
            <ds:KeyInfo>
              <ds:X509Data>
                  <ds:X509Certificate>...</ds:X509Certificate>
              </ds:X509Data>
            </ds:KeyInfo>
        </KeyDescriptor>

        <NameIDFormat>urn：mace：shibboleth：1.0：nameIdentifier</NameIDFormat>
        <NameIDFormat>urn：oasis：names：tc：SAML：2.0：nameid-format：transient</NameIDFormat>

        <SingleLogoutService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"
                             Location="https://HOST_NAME/cas/idp/profile/SAML2/POST/SLO"/>
        <SingleSignOnService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"
                             Location="https://HOST_NAME/cas/idp/profile/SAML2/POST/SSO"/>
        <SingleSignOnService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect"
                             Location="https://HOST_NAME/cas/idp/profile/SAML2/Redirect/SSO"/>
    </IDPSSODescriptor>
</EntityDescriptor>
```


SAML2身份提供者元数据也可以通过动态方式进行管理。 要了解更多信息，请 [本指南](Configuring-SAML2-DynamicMetadata.html)。



### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 需要将适用于特定服务定义并通过文件系统管理的元数据工件存储在以标准元数据目录内的服务定义名称和数字标识符命名的目录位置中，该位置为 例如， 如果全局元数据的伪影在磁盘上的管理 `的/ etc / CAS /配置/ SAML /元数据`，则元数据适用于服务定义 ，其名称被配置为 `SampleService` 具有一个id `1000` 是预期可在 `/ etc / cas / config / saml / metadata / SampleService-1000`。

SAML2身份提供者元数据也可以通过动态方式进行管理。 要了解更多信息，请 [本指南](Configuring-SAML2-DynamicMetadata.html)。



## 配置

通过在WAR叠加中包含以下依赖项来启用支持：



```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp</artifactId>
  <version>${cas.version}</version>
</dependency>
```


您可能还需要在 声明以下存储库，以便能够解决依赖关系：



```groovy
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://build.shibboleth.net/nexus/content/repositories/releases” 
    }
}
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-idp)。



### 行政端点

CAS提供了以下端点：

| 终点                        | 描述                                                |
| ------------------------- | ------------------------------------------------- |
| `samlPostProfileResponse` | 通过提供获得SAML2响应有效载荷 `的用户名`， `密码` 和 `ENTITYID` 作为参数。 |




### SAML服务

SAML依赖方和服务必须在CAS服务注册表中注册，类似于以下示例：



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “evaluationOrder”：10，
  “metadataLocation”： “HTTPS：//url/to/metadata.xml”
}
```


以下字段可用于SAML服务：

| 场地                                                | 描述                                                                                                                                                                                   |
| ------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `metadataLocation`                                | 从系统文件，类路径，目录或URL资源定义的服务元数据的位置。                                                                                                                                                       |
| `metadataProxyLocation`                           | 代理端点（`https：// proxy-address：8901`）以从URL资源获取服务元数据。                                                                                                                                   |
| `metadataSignatureLocation`                       | 元数据签名证书/公共密钥的位置，以验证必须从系统文件或类路径定义的元数据。 如果定义，将对 `SignatureValidationFilter` 验证过滤器。                                                                                                     |
| `metadataExpirationDuration`                      | 如果定义，则将在指定的持续时间后使高速缓存中的元数据过期，这将迫使CAS再次检索和解析元数据。                                                                                                                                      |
| `requireSignedRoot`                               | 是否要求对传入的元数据的根元素进行签名。 默认值为 `true`。                                                                                                                                                    |
| `signUnsolicitedAuthnRequest`                     | 在处理未经请求的SSO时，请确定是否应对签名请求进行强制签名。                                                                                                                                                      |
| `标志断言`                                            | 断言是否应该签名。 默认值为 `false`。                                                                                                                                                              |
| `signRespons`                                     | 是否应签署答复。 默认值为 `true`。                                                                                                                                                                |
| `加密可选`                                            | 尽可能进行加密（即在对等方的元数据中找到兼容的密钥），否则跳过加密。 默认值为 `false`。                                                                                                                                     |
| `cryptoAssertions`                                | 断言是否应该加密。 默认值为 `false`。                                                                                                                                                              |
| `cryptoAttributes`                                | 断言属性是否应该加密。 默认值为 `false`。                                                                                                                                                            |
| `cryptoableAttributes`                            | 指定用于加密的属性集，使此集合中不存在的其他属性失去资格。 默认值（即 `*`）是将所有一次加密 `cryptoAttributes` 为true时进行加密。                                                                                                      |
| `requiredAuthenticationContextClass`              | 如果定义，将在最终响应中指定SAML身份验证上下文类。 如果未定义，则身份验证类将为 `urn：oasis：names：tc：SAML：2.0：ac：classes：unspecified` 或 `urn：oasis：names：tc：SAML：2.0：ac：classes：PasswordProtectedTransport` 取决于SAML身份验证请求。 |
| `requiredNameIdFormat`                            | 如果定义，将在最终的SAML响应中强制使用指定的名称ID格式。                                                                                                                                                      |
| `偏斜`                                              | 如果定义，则指示用于歪曲认证日期（如有效期自和有效期至元素等）的秒数。                                                                                                                                                  |
| `metadataCriteriaPattern`                         | `PredicateFilter` 的元数据聚合上的实体ID过滤器基于有效的正则表达式模式包含/排除特定的实体ID。                                                                                                                           |
| `metadataCriteriaDirection`                       | `PredicateFilter`强制对元数据聚合进行实体id过滤。 允许的值是 `INCLUDE`，`EXCLUDE`。                                                                                                                        |
| `metadataCriteriaRoles`                           | 如果定义，将允许定义的元数据的角色（即 `SPSSODescriptor`， `IDPSSODescriptor`）。 默认值为 `SPSSODescriptor`。                                                                                                  |
| `metadataCriteriaRemoveEmptyEntitiesDescriptors`  | 控制是否保留不包含实体描述符的实体描述符。 默认值为 `true`。                                                                                                                                                   |
| `metadataCriteriaRemoveRolelessEntityDescriptors` | 控制是否保留不包含任何角色的实体描述符。 默认值为 `true`。                                                                                                                                                    |
| `attributeNameFormats`                            | 映射，用于定义要在SAML响应中编码的给定属性名称的属性名称格式。                                                                                                                                                    |
| `attributeFriendlyNames`                          | 映射，用于为要在SAML响应中编码的给定属性名称定义属性友好名称。                                                                                                                                                    |
| `attributeValueTypes`                             | 映射，用于定义给定属性名称的属性值的类型。                                                                                                                                                                |
| `nameIdQualifier`                                 | 如果定义，将覆盖产生的使用者名称ID ``                                                                                                                                                                |
| `logoutResponseBinding`                           | 如果定义，将覆盖用于准备服务提供者注销响应的绑定。                                                                                                                                                            |
| `issuerEntityId`                                  | 如果定义，将使用给定的身份提供者实体ID覆盖发行值。 在CAS需要维护多个身份提供者实体ID的情况下，这可能很有用。                                                                                                                           |
| `断言`                                              | 除实体ID之外，以逗号分隔的受众URL列表，包括在断言中。                                                                                                                                                        |
| `serviceProviderNameIdQualifier`                  | 如果定义，将覆盖产生的使用者名称ID ``                                                                                                                                                                |
| `skipGeneratingAssertionNameId`                   | 对于声明，是否应跳过名称标识符的生成。 默认值为 `false`。                                                                                                                                                    |
| `skipGeneratingTransientNameId`                   | 是否应该跳过临时名称标识符的生成。 默认值为 `false`。                                                                                                                                                      |
| `skipGeneratingSubjectConfirmationInResponseTo`   | `InResponseTo` 元素的生成以进行主题确认。 默认值为 `false`。                                                                                                                                           |
| `skipGeneratingSubjectConfirmationNotOnOrAfter`   | `NotOnOrBefore` 元素的生成以进行主题确认。 默认值为 `false`。                                                                                                                                          |
| `skipGeneratingSubjectConfirmationRecipient`      | `收件人` 元素的生成以进行主题确认。 默认值为 `false`。                                                                                                                                                    |
| `skipGeneratingSubjectConfirmationNotBefore`      | `NotBefore` 元素的生成以进行主题确认。 默认值为 `true`。                                                                                                                                               |
| `skipGeneratingSubjectConfirmationNameId`         | `NameID` 元素的生成以进行主题确认。 默认值为 `true`。                                                                                                                                                  |
| `signingCredentialFingerprint`                    | `SHA-1` 签名凭据的公钥摘要，解析为正则表达式，用于在处理多个凭据时进行密钥轮换。                                                                                                                                         |
| `signingCredentialType`                           | 可接受的值为 `BASIC` 和 `X509`。 此设置控制在此应用程序的最终SAML响应中生成的签名块的类型。 后者是默认值，它在 `X509Data` 块内 `PEM` `DEREncodedKeyValue` 块下的已解析公钥对签名进行编码。                                                         |
| `signingSignatureReferenceDigestMethods`          | 签名签名参考摘要方法的集合（如果有），以覆盖全局默认值。                                                                                                                                                         |
| `signingKeyAlgorithm`                             | 加载私钥时强制使用签名密钥算法进行签名操作。 默认值为 `RSA`。                                                                                                                                                   |
| `signingSignature算法`                              | 集合签名签名算法（如果有）以覆盖全局默认值。                                                                                                                                                               |
| `signingSignatureBlackListed算法`                   | 收集拒绝签名签名算法（如果有）以覆盖全局默认值。                                                                                                                                                             |
| `signingSignatureWhiteListed算法`                   | 允许的签名签名算法的集合（如果有），以覆盖全局默认值。                                                                                                                                                          |
| `签名签名规范化算法`                                       | 签名签名规范化算法（如果有）可以覆盖全局默认值。                                                                                                                                                             |
| `EncryptionDataAlgorithms`                        | 收集加密数据算法（如果有）以覆盖全局默认值。                                                                                                                                                               |
| `EncryptionKeyAlgorithms`                         | 集合加密密钥传输算法（如果有）以覆盖全局默认值。                                                                                                                                                             |
| `加密黑名单算法`                                         | 收集拒绝的加密算法（如果有）以覆盖全局默认值。                                                                                                                                                              |
| `加密白名单算法`                                         | 允许的加密算法的集合（如果有），以覆盖全局默认值。                                                                                                                                                            |
| `whiteListBlackListPrecedence`                    | 指示白名单和黑名单均为非空时应优先使用的优先级值。 接受的值为 `WHITELIST` 或 `BLACKLIST`。 默认值为 `WHITELIST`。                                                                                                         |

<div class="alert alert-info"><strong>保留您所需要的！</strong><p>鼓励您仅保留和维护 
特定集成所需的属性和设置。 获取所有服务字段的副本并尝试根据其默认值再次进行配置是不必要的。 虽然 
您可能希望保留一份副本作为参考，这种策略最终将导致不好的升级提高重大更改和凌乱的机会 
，在该部署。</p></div>

### 元数据汇总

从根本上来说，CAS服务是通过通常通过 正则表达式传给CAS的服务标识符来识别和加载的。 URL模式对应用程序和服务进行常见分组（即，“属于 `example.org` 内容均已在CAS中注册”）。 对于聚合的元数据，CAS本质上会进行双 授权检查，因为它会首先尝试在其已解析的元数据组件的集合中 的模式对实体ID进行了授权。该服务定义。 这意味着您可以执行以下其中一项操作：

1. 打开模式以允许元数据中已授权的所有内容。
2. 将模式限制为仅在 元数据中找到的少数几个实体ID。 这与定义元数据标准 来过滤已解析的依赖方和实体ID的列表本质上是相同的，除了在元数据完全加载并解析之后 

3. 您还可以指示CAS在解析时读取定义的 元数据本身 这与强制模式 匹配实体ID基本相同，只是在CAS读取 元数据时完成了此操作，从而缩短了加载时间。



### 元数据解析

服务提供商元数据将针对每个服务按需获取和加载，然后以 配置的持续时间缓存在全局缓存中。 后续对服务元数据的请求将始终始终先查询高速缓存，如果未命中，则 将通过加载或联系已配置的资源来求助于实际解析元数据。 向CAS注册的每个服务提供者定义也可以有选择地专门指定 元数据解析的有效期限以覆盖默认全局值。



#### 动态元数据解析

除了管理服务提供商元数据（例如直接XML文件或URL）的更传统的方法外，CAS 还支持许多其他策略，以借助MDQ等更动态地获取元数据。 要了解更多信息，请 [本指南](Configuring-SAML2-DynamicMetadata.html)。



### 安全配置

有多个配置级别可控制已签名，加密等对象的安全性配置。 这些配置包括 例如要使用的键，首选/默认算法以及允许，强制或拒绝的算法。 

通常根据以下顺序确定配置：

- 服务提供商元数据
- 每个服务的配置覆盖
- 全局CAS默认设置
- OpenSAML初始默认值

在几乎所有情况下，都应保留默认设置。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-algorithms--security)。



#### 加密

以下示例演示了每个服务提供商的加密安全性配置替代。



#### 哥伦比亚广播公司

下面的示例演示如何将CAS配置为对特定服务提供商 `CBC`



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ sp.example.org”，
  “ name”：“ SAML”，
  “ id”： 1，
  “metadataLocation”： “/path/to/sp-metadata.xml”，
  “encryptionDataAlgorithms”：[
    “的java.util.ArrayList”，
    [
      “http://www.w3.org/2001 / 04 / xmlenc＃aes128-cbc“
    ]
  ]，
  ” encryptionKeyAlgorithms“：[
    ” java.util.ArrayList“，
    [
      ” http://www.w3.org/2001/04/xmlenc#rsa -oaep-mgf1p“
    ]
  ]
}
```




#### GCM

下面的示例演示如何将CAS配置为对特定服务提供商 `GCM`



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ sp.example.org”，
  “ name”：“ SAML”，
  “ id”： 1，
  “metadataLocation”： “/path/to/sp-metadata.xml”，
  “encryptionDataAlgorithms”：[
    “的java.util.ArrayList”，
    [
      “http://www.w3.org/2009 / xmlenc11＃aes128-gcm“
    ]
  ]，
  ” encryptionKeyAlgorithms“：[
    ” java.util.ArrayList“，
    [
      ” http://www.w3.org/2001/04/xmlenc#rsa-oaep -mgf1p“
    ]
  ]
}
```




#### 签收

以下示例演示了每个服务提供商的签名安全性配置替代。



##### SHA-1

下面的示例演示如何将CAS配置为对特定服务提供商 `SHA-1`



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ sp.example.org”，
  “ name”：“ SAML”，
  “ id”： 1，
  “metadataLocation”： “/path/to/sp-metadata.xml”，
  “signingSignatureAlgorithms”：[
    “的java.util.ArrayList”，
    [
      “http://www.w3.org/2000 / 09 / xmldsig＃rsa-sha1“，
      ” http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1“
    ]
  ]，
  ” signingSignatureReferenceDigestMethods“：[
    ” java.util .ArrayList”，
    [
      “ http://www.w3.org/2000/09/xmldsig#sha1”
    ]
  ]
}
```




##### SHA-256

下面的示例演示如何将CAS配置为对特定服务提供商 `SHA-256`



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ sp.example.org”，
  “ name”：“ SAML”，
  “ id”： 1，
  “metadataLocation”： “/path/to/sp-metadata.xml”，
  “signingSignatureAlgorithms”：[
    “的java.util.ArrayList”，
    [
      “http://www.w3.org/2001 / 04 / xmldsig-more＃rsa-sha256“，
      ” http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256“
    ]
  ]，
  ” signingSignatureReferenceDigestMethods“：[
    ” java .util.ArrayList”，
    [
      “ http://www.w3.org/2001/04/xmlenc#sha256”
    ]
  ]
}
```




### 属性发布

每个SAML服务都定义了属性过滤和发布策略。 有关更多信息，请参见 [本指南](Configuring-SAML2-Attribute-Release.html)



### 名称ID选择

每个服务都可以指定所需的名称ID格式。 如果未定义，将查询元数据以找到正确的格式。 名称ID值始终是设计为返回此服务的经过身份验证的用户。 换句话说，如果你 决定配置CAS返回一个特定属性为 [的身份验证的用户名此服务](../integration/Attribute-Release-PrincipalId.html)， 该值将被用于构建名称ID用正确的格式一起。



#### 例子

以下服务定义指示CAS使用 `瓮：绿洲：名称：TC：SAML：1.1：填充NameID格式：EmailAddress的` 作为最终的名称ID格式， 并使用 `邮件` 属性值作为最终的名称ID值。



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-sp”，
  “ name”：“ SAML服务“
  ”metadataLocation“： ”/path/to/sp-metadata.xml“，
  ”ID“：1，
  ”requiredNameIdFormat“：”瓮：绿洲：名称：TC：SAML：1.1：填充NameID格式：EMAILADDRESS “，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.PrincipalAttributeRegisteredServiceUsernameProvider“，
    ” usernameAttribute“：” mail“，
  }
}
```


以下服务定义指示CAS使用 `urn：oasis：names：tc：SAML：2.0：nameid-format：transient` 作为最终的Name ID格式， 并使用 `cn` 属性值作为最终名称ID值，跳过按要求的格式生成瞬态值的过程。



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAML服务“
  ”metadataLocation“： ”/path/to/sp-metadata.xml“，
  ”ID“：1，
  ”requiredNameIdFormat“：”瓮：绿洲：名称：TC：SAML：2.0：填充NameID格式：瞬态“，
  ” skipGeneratingTransientNameId“：true，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.PrincipalAttributeRegisteredServiceUsernameProvider“，
    ” usernameAttribute“：” cn“，
    ” canonicalizationMode“：” UPPER“
  }
}
```


以下服务定义指示CAS使用 `cn` 属性值来创建持久名称Name。



```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAML服务“
  ”metadataLocation“： ”/path/to/sp-metadata.xml“，
  ”ID“：1，
  ”requiredNameIdFormat“：”瓮：绿洲：名称：TC：SAML：2.0：填充NameID格式：持续性“，
  ” usernameAttributeProvider“：{
    ” @class“：” org.apereo.cas.services.AnonymousRegisteredServiceUsernameAttributeProvider“，
    ” persistentIdGenerator“：{
      ” @class“：” org.apereo.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator “，
      ” salt“：” aGVsbG93b3JsZA ==“，
      ” attribute“：” cn“
    }
  }
}
```




## 不请自来的SSO

SAML2 IdP `主动/ SSO` 配置文件支持以下参数：

| 范围           | 描述                        |
| ------------ | ------------------------- |
| `providerId` | 必需的。 服务提供者的实体ID。          |
| `郡`          | 可选的。 服务提供商的响应位置（ACS URL）。 |
| `目标`         | 可选的。 中继状态。                |
| `时间`         | 可选的。 倾斜身份验证请求。            |




## 属性查询

为了使CAS支持并响应属性查询，您需要确保生成的元数据已 的 `AttributeAuthorityDescriptor` 元素，并且为 `urn：oasis：names：tc：SAML：2.0：protocol` 和与CAS端点相对应的相关绑定。 您还必须确保 `AttributeAuthorityDescriptor` 标记列出所有 `KeyDescriptor` 元素和证书，特别是如果服务提供商 的SOAP客户端需要将CAS端点后面的证书与 `AttributeAuthorityDescriptor`定义了什么。 默认情况下，CAS 将始终使用其自己的签名证书对由于属性查询而生成的响应进行签名。

还要注意，需要显式启用对属性查询的支持，并且默认情况下该行为处于禁用状态，因为这会给 CAS和基础票证注册表带来负担，以跟踪作为票证的属性和响应，并在以后使用和查看它们向上。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-idp)。



## 服务提供商集成

CAS本身提供了许多SAML2服务提供商集成。 要了解更多信息，请 [本指南](../integration/Configuring-SAML-SP-Integrations.html)。



## 服务提供商元数据

如果您希望与之集成的SP不会产生SAML元数据，则您可以 使用 [该服务](https://www.samltool.com/sp_metadata.php) 创建元数据， 将其保存在XML文件中，然后为SP引用并在CAS中注册。

另外，您可以利用一个独立的 `saml-sp-metadata.json` 文件，该文件可以在与CAS元数据工件 该文件的内容可能如下：



```json
{
  “https://example.org/saml”：{
    “ENTITYID”： “https://example.org/saml”，
    “证书”： “MIIDUj ...”，
    “assertionConsumerServiceUrl”：“ https://example.org/sso/“
  }
}
```


文件中的每个条目都由服务提供商实体ID标识，从而允许CAS动态地定位并构建所需的元数据 以恢复身份验证流程。 集成签名证书的服务提供商，这可能会变得更加容易，从而使您不必分别创建和管理XML元数据文件。

服务提供者已在CAS服务注册表中注册，如下所示：



```json
{
  “@class”： “org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “服务Id”： “https://example.org/saml”，
  “名称”： “SAMLService”，
  “ID”：10000003，
  “metadataLocation”： “JSON：//”
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>上面的注册记录中的元数据位置需要指定为 <code>json：//</code> 以向CAS发出信号，表明必须从指定的JSON文件中获取注册服务提供商的SAML元数据。</p></div>

## 客户图书馆

对于基于Java的应用程序，可以使用以下框架将您的应用程序与充当SAML2身份提供者的CAS集成在一起：

- [春季安全SAML](http://projects.spring.io/spring-security-saml/)
- [Pac4j](http://www.pac4j.org/docs/clients/saml.html)



## 样例客户端应用程序

- [Spring Security SAML示例Webapp](https://github.com/cas-projects/saml2-sample-java-webapp)
- [Okta](https://developer.okta.com/standards/SAML/setting_up_a_saml_application_in_okta)



## 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：



```xml
<Logger name="org.opensaml" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
<Logger name="PROTOCOL_MESSAGE" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
