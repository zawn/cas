---
layout: 违约
title: CAS - SAML2 认证
category: 协议
---

# SAML2 身份验证

CAS 可以充当 SAML2 身份提供商，接受身份验证请求并生成 SAML 断言。

如果您打算允许 CAS 将身份验证委托给外部 SAML2 身份提供商，则需要 [查看本指南](../integration/Delegate-Authentication.html)。

<div class="alert alert-info"><strong>萨姆尔规格</strong><p>本文档仅侧重于如何打开 CAS 内部的 SAML2 支持。 它不是描述/解释SAML2协议本身的许多特征。 如果您不确定本页中提及的概念，请从查看 SAML2 规范</a><a href="http://docs.oasis-open.org/security/saml/Post2.0/sstc-saml-tech-overview-2.0.html">开始。</p></div>

## 联邦互联评估

中科院项目力求符合 [SAML V2.0实施简介，为联邦互操作性](https://kantarainitiative.github.io/SAMLprofiles/fedinterop.html)。 </a>，这里可以评估对当前 CAS 版本的要求。 建议您查看、评估和评论当前在需要验证时不存在或标记有问题的功能。</p> 



## 萨姆尔终点

以下 CAS 端点响应支持的 SAML2 配置文件：

- `/idp/配置文件/萨姆尔2/重定向/SSO`
- `/idp/配置文件/萨姆尔2/开机自检/SSO`
- `/idp/配置文件/萨姆尔2/邮政简单签名/SSO`
- `/idp/配置文件/萨姆尔2/开机自检/斯洛`
- `/idp/配置文件/萨姆尔2/重定向/斯洛`
- `/idp/配置文件/萨姆尔2/主动/SSO`
- `/idp/配置文件/萨姆尔2/肥皂/欧洲经委会`
- `/idp/配置文件/萨姆尔2/肥皂/属性奎里`
- `/idp/配置文件/SAML1/肥皂/文物恢复`



## IDP元数据

以下 CAS 端点处理 SAML2 元数据的生成：

- `/idp/元数据`

此端点将在收到 GET 请求后显示 CAS IDP SAML2 元数据。 如果元数据已可用并生成，则将显示 元数据。 如果元数据不存在，将自动生成元数据。 下面的 CAS 配置规定了元数据文件/密钥的生成和存储位置。

请注意，端点可以接受按实体 ID 或数字标识符</code> 参数的 `服务。 此参数
与 CAS 服务注册表匹配，允许端点计算和合并任何身份提供商 
可能已指定的元数据覆盖。</p>

<p spaces-before="0">下面是生成的元数据文件作为示例：</p>

<pre><code class="xml"><？xml 版本="1.0"编码="UTF-8"？>
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

        <NameIDFormat>骨灰盒：mace：石器：1.0：名称识别器</NameIDFormat>
        <NameIDFormat>骨灰盒：绿洲：名称：tc：SAML：2.0：名称格式：瞬态</NameIDFormat>

        <SingleLogoutService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"
                             Location="https://HOST_NAME/cas/idp/profile/SAML2/POST/SLO"/>
        <SingleSignOnService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"
                             Location="https://HOST_NAME/cas/idp/profile/SAML2/POST/SSO"/>
        <SingleSignOnService Binding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect"
                             Location="https://HOST_NAME/cas/idp/profile/SAML2/Redirect/SSO"/>
    </IDPSSODescriptor>
</EntityDescriptor>
`</pre> 

SAML2 身份提供商元数据也可以以动态方式进行管理。 要了解更多，请 [本指南](Configuring-SAML2-DynamicMetadata.html)。



### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于特定服务定义并通过文件系统进行管理的元数据工件需要存储 以服务定义名称和数字标识符命名的目录位置， 在规范元数据目录内。 例如， ，如果将全球元数据工件管理在磁盘上 `/等/cas/config/saml/元数据`，则元数据适用于名称被配置为 `示例的服务定义
服务` 的id `1000` 预计将在 `/等/cas/配置/萨姆尔/元数据/样品服务-1000`找到。

SAML2 身份提供商元数据也可以以动态方式进行管理。 要了解更多，请 [本指南](Configuring-SAML2-DynamicMetadata.html)。



## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普</artifactId>
  <version>${cas.version}</version>
</dependency>
```


您可能需要在 CAS 叠加 中申报以下存储库，以解决依赖关系：



```groovy
存储库{
    maven{ 
        马文康顿{发布（）=
        网址"https://build.shibboleth.net/nexus/content/repositories/releases" 
    }
}
```


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-idp)。



### 行政终点

CAS 提供以下端点：

| 端点          | 描述                                              |
| ----------- | ----------------------------------------------- |
| `萨姆波斯特档案回应` | 通过提供 `用户名`、 `密码` 和 `实体id作为参数` 来获得 SAML2 响应有效载荷。 |




### 山姆服务

SAML 依赖方和服务必须在 CAS 服务注册册中注册，类似于以下示例：



```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务.萨姆注册服务"，
  "服务ID"："实体-id-sp"，
  "名称"："SAML服务"，
  "id"：1000003，
  "评估"：10，
  "元数据定位"："https://url/to/metadata.xml"
}
```


SAML 服务提供以下字段：

| 田                 | 描述                                                                                                                                         |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| `元数据定位`           | 从系统文件、类路径、目录或 URL 资源定义的服务元数据的位置。                                                                                                           |
| `元数据氧化定位`         | 代理端点（`https://proxy-address:8901`）从URL资源获取服务元数据。                                                                                           |
| `元数据签名位置`         | 元数据签名证书/公钥的位置，以验证必须从系统文件或类路径定义的元数据。 如果定义，将强制执行元数据上的 `签名验证过滤器` 验证过滤器。                                                                       |
| `元数据解释`           | 如果定义，将在指示的持续时间后在缓存中过期元数据，这将迫使 CAS 再次检索和解决元数据。                                                                                              |
| `需要签名的根`          | 是否需要签名传入元数据的根元素。 默认值 `真实`。                                                                                                                 |
| `签名不请自来`          | 在处理未经请求的 SSO 时，确定是否应强制签署身份验证请求。                                                                                                            |
| `符号附录`            | 是否应签署断言。 违约 `虚假`。                                                                                                                          |
| `符号响应`            | 是否应签署答复。 默认值 `真实`。                                                                                                                         |
| `加密选项`            | 尽可能加密（即在同行的元数据中找到兼容密钥），或者跳过加密。 违约 `虚假`。                                                                                                    |
| `加密附录`            | 是否应加密断言。 违约 `虚假`。                                                                                                                          |
| `加密属性`            | 是否应加密断言属性。 违约 `虚假`。                                                                                                                        |
| `可加密属性`           | 为加密指定的属性集，取消此集合中不存在的其他属性的资格。 默认值（即 `*`）是加密所有一旦 `加密归因` 是真实的。                                                                                |
| `要求授权机密类`         | 如果定义，将在最终响应中指定 SAML 身份验证上下文类。 如未定义，认证类将 `骨灰盒：绿洲：名称：tc：SAML：2.0：ac：类：未指定的` 或 `骨灰盒：绿洲：名称：tc：SAML：2.0：ac：类：密码保护运输` 取决于SAFL认证请求。               |
| `所需名模表`           | 如果定义，将强制在最终SAML响应中注明姓名ID格式。                                                                                                                |
| `偏斜`              | 如果定义，则表示用于偏斜身份验证日期的秒数，例如有效日期和有效直至元素等。                                                                                                      |
| `元数据克里特里亚数据`      | 如果定义，将强制基于 `谓词过滤器` 对元数据聚合的实体 ID 过滤器根据有效的 regex 模式包含/排除特定实体 ID。                                                                             |
| `元数据克里特里亚方向`      | 如果定义，将强制基于 `谓词过滤器`对元数据聚合体进行实体 ID 筛选。 允许的值 `包括`、`排除`。                                                                                       |
| `元数据克里特里亚罗尔斯`     | 如果定义，将允许定义元数据角色（即 ``， ``）。 默认值 `SPS索德脚本`。                                                                                                  |
| `元数据克里特里亚移动空位描述符` | 控制是否保留不包含实体描述符的实体描述符。 默认值 `真实`。                                                                                                            |
| `元数据克里特里亚移动无序描述符` | 控制是否保留不包含角色的实体描述符。 默认值 `真实`。                                                                                                               |
| `属性名表`            | 为在 SAML 响应中编码的给定属性名称定义属性名称格式的地图。                                                                                                           |
| `属性友好名`           | 为在 SAML 响应中编码的给定属性名称定义属性友好名称的地图。                                                                                                           |
| `属性价值类型`          | 定义给定属性名称属性值类型的地图。                                                                                                                          |
| `名称识别器`           | 如果定义，将覆盖生成主体名称 ID 的 `名称资格` 属性。                                                                                                             |
| `注销响应绑定`          | 如果定义，将覆盖用于为服务提供商准备注销响应的绑定。                                                                                                                 |
| `发行人`             | 如果定义，将用给定的身份提供商实体 ID 覆盖问题值。 在 CAS 需要维护多个身份提供商实体 ID 的情况下，这可能很有用。                                                                            |
| `断言`              | 逗号分离的受众网址列表，除实体 ID 外，还包含在断言中。                                                                                                              |
| `服务提供者名称资格认证器`    | 如果定义，将覆盖所生成主体名称 ID 的 `SP 名称资格` 属性。                                                                                                         |
| `跳过生成的生成名`        | 是否应跳过名称标识符的生成以进行断言。 违约 `虚假`。                                                                                                               |
| `跳过生成递质名Id`       | 是否应该跳过瞬态名称标识符生成。 违约 `虚假`。                                                                                                                  |
| `跳过生成主体确认响应到`     | 应跳过 `响应到` 元素的生成以进行主题确认。 违约 `虚假`。                                                                                                           |
| `跳过生成主体确认不或后`     | 是否生成 `Notonor 之前` 元素应跳过主题确认。 违约 `虚假`。                                                                                                      |
| `跳过生成主体确认启动`      | 是否应跳过 `接收者` 元素的生成以进行主题确认。 违约 `虚假`。                                                                                                         |
| `跳过生成主体确认之前不`     | 是否应该跳过 `不之前` 元素的生成，以进行主题确认。 默认值 `真实`。                                                                                                      |
| `跳过生成主体确认名Id`     | 是否应跳过 `nameID` 元素的生成以进行主题确认。 默认值 `真实`。                                                                                                     |
| `签署信用手指打印`        | `SHA-1` 签名凭证的公钥摘要，作为常规表达式解析，用于处理多个凭据时的关键旋转。                                                                                                |
| `签署信用类型`          | 可接受的值 `基本` 和 `X509`。 此设置控制此应用程序的最终 SAML 响应中生成的签名块的类型。 后者作为默认值，在 `X509Data` 块内以 `PEM` 格式编码签名，而前者则根据已解决的公共密钥在 `DEREncodeKeyValue` 块下对签名进行编码。 |
| `签署签名参考最美的`       | 收集签名参考摘要方法（如果有的话）以覆盖全球默认值。                                                                                                                 |
| `签署基阿戈里特姆`        | 在加载私钥时强制用于签名操作的签名关键算法。 默认值 `RSA`。                                                                                                          |
| `签署签名`            | 收集签名算法（如果有的话）以覆盖全球默认值。                                                                                                                     |
| `签署签名黑色列表阿戈里特姆斯`  | 收集被拒绝的签名算法（如果有的话）以覆盖全球默认值。                                                                                                                 |
| `签署签名白名单阿戈里特姆斯`   | 收集允许的签名算法（如果有的话）以覆盖全球默认值。                                                                                                                  |
| `签署签名加拿大化阿戈里特姆`   | 签名签名规范算法（如果有的话）可覆盖全球默认值。                                                                                                                   |
| `加密数据`            | 收集加密数据算法（如果有的话）以覆盖全球默认值。                                                                                                                   |
| `加密键自动`           | 收集加密密钥传输算法（如果有的话）以覆盖全球默认值。                                                                                                                 |
| `加密黑列表的自动取款机`     | 收集被拒绝的加密算法（如果有的话）以覆盖全球默认值。                                                                                                                 |
| `加密白名单的方解密`       | 收集允许的加密算法（如果有的话）以覆盖全球默认值。                                                                                                                  |
| `白名单黑名单预演`        | 首选项值表示当白名单和黑名单均为非空时，应优先于哪个优先级。 接受的值 `白名单` 或 `黑名单`。 默认值 `白名单`。                                                                              |

<div class="alert alert-info"><strong>保留您需要的！</strong><p>我们鼓励您只保留和维护特定集成所需的属性和设置， 
特定集成。 获取所有服务字段的副本并尝试根据它们的默认值再次配置它们是不必要的。 虽然 
您可能希望保留副本作为参考，但此策略最终会导致升级不力，从而增加打破更改的机会，并导致混乱的 
部署。</p></div>

### 元数据聚合

CAS 服务通常通过 常规表达式向 CAS 传授的服务标识符进行基本识别和加载。 这允许通过 网址模式（即"属于 `example.org` 的所有内容均在 CAS 注册）对应用程序和服务进行常见分组。 对于聚合元数据，CAS 基本上会进行双重 授权检查，因为它将首先尝试在其已解决元数据组件的集合中查找实体 ID ，然后 查看该实体 ID 是否通过分配给该服务定义 模式获得授权。 这意味着您可以做几件事情之一：

1. 打开模式，允许元数据中授权的所有内容。
2. 将模式限制为仅在 元数据中找到的少数实体 ID。 这与定义元数据标准 过滤已解决的依赖方和实体 ID 列表大致相同，只是一旦元数据完全加载并解析，其完成 。

3. 您还可以指示 CAS 在读取 元数据本身时，通过定义的标准对元数据 实体进行筛选。 这与强制模式 匹配实体 ID 大致相同，只是在 CAS 读取 元数据时完成，因此加载时间得到改进。



### 元数据分辨率

服务提供商元数据会根据每个服务的需求进行提取和加载，然后在全球缓存中缓存 可配置的持续时间。 后续的服务元数据请求始终会首先咨询缓存，如果错过， 将诉诸于通过加载或联系配置的资源来实际解决元数据。 在 CAS 注册的每个服务提供商定义也可以选择 元数据分辨率的到期期，以覆盖默认的全球值。



#### 动态元数据分辨率

除了管理服务提供商元数据（如直接 XML 文件或网址）的更传统方法外，CAS 还支持许多其他策略，以更动态地获取元数据，例如 MDQ 等。 要了解更多，请 [本指南](Configuring-SAML2-DynamicMetadata.html)。



### 安全配置

有几个级别的配置可以控制已签名、加密等对象的安全配置。 这些配置包括 的东西，如要使用的密钥、首选/默认算法以及允许、执行或拒绝的算法。 

配置通常根据以下顺序确定：

- 服务提供商元数据
- 每种服务配置覆盖
- 全球 CAS 默认设置
- 打开萨姆初始默认值

在几乎所有情况下，您都应该保留默认值。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-algorithms--security)。



#### 加密

以下示例显示每个服务提供商的加密安全配置覆盖。



#### 加拿大广播公司

以下示例演示了如何为特定服务提供商配置 CAS `CBC` 加密：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务"，
  "服务id"："sp.example.org"，
  "名称"："SAML"，
  "id"：1，
  "元数据定位"："/路径/到/sp元数据.xml"
  "加密数据algorithms"：[
    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2001/04/xmlenc#aes128-cbc"
    ]
  ]，

    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p"
    ]
  ]
}
```




#### 全科

以下示例演示了如何为特定服务提供商配置 CAS `GCM` 加密：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务"，
  "服务id"："sp.example.org"，
  "名称"："SAML"，
  "id"：1，
  "元数据定位"："/路径/到/sp元数据.xml"
  "加密数据algorithms"：[
    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2009/xmlenc11#aes128-gcm"
    ]
  ]，

"加密钥匙词"：[
    "java.ul.ul.Arraylist"，
    [
      "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p"
    ]
  ]}
```




#### 签署

以下示例显示每个服务提供商的签名安全配置覆盖。



##### 沙-1

以下示例演示了如何配置 CAS 以使用 `SHA-1` 特定服务提供商的签名和摘要算法：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务"，
  "服务id"："sp.example.org"，
  "名称"："SAML"，
  "id"：1，
  "元数据定位"："/路径/到/到/p元数据.xml"
  "签名"："
    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2000/09/xmldsig#rsa-sha1"，
      "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1"
    ]
  ] ，
  "签名参考"："
    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2000/09/xmldsig#sha1"
    ]
  ]
}
```




##### 沙-256

以下示例演示了如何配置 CAS 以使用 `SHA-256` 特定服务提供商的签名和摘要算法：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务"，
  "服务ID"："sp.example.org"，
  "名称"："SAML"，
  "id"：1，
  "元数据定位"："/路径/到/p-元数据.xml"
  "签名"："
    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"，
      "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"
    ]
  ]，
  "签署签名参考最小"：[
    "java.ul.Arraylist"，
    [
      "http://www.w3.org/2001/04/xmlenc#sha256"
    ]
  ]
]
```




### 属性释放

属性筛选和发布策略是根据 SAML 服务定义的。 有关详细信息，请参阅本指南</a> 。</p> 



### 姓名 ID 选择

每项服务均可指定所需的姓名 ID 格式。 如果未定义，将咨询元数据以查找正确的格式。 名称 ID 值始终是设计为返回此服务的经过验证的用户。 换句话说，如果您 决定将 CAS 将特定属性 [此服务的经过验证的用户名](../integration/Attribute-Release-PrincipalId.html)返回，则该值 将用于构建名称 ID 以及正确的格式。



#### 例子

下列服务定义指示中科院使用 `骨灰盒：绿洲：名称：tc：SAML：1.1：名称格式：电子邮件地址` 作为最终名称ID格式， 并使用 `邮件` 属性值作为最终名称ID值。



```json
•
  "@class"： "组织. apereo. cas. 支持. saml. 服务. 萨姆注册服务"，
  "服务id"："实体-id-sp"，
  "名称"："SAML服务"，
  "元数据定位"："/路径/到/sp-元数据.xml"，
  "id"：1，
  "要求名称"："urn：绿洲：名称：tc：SAML：1.1：名称格式：电子邮件地址"，
  "用户名归因提供者"：{
    @class："org.apereo.cas.服务.委托注册服务用户"，
    "用户名归因"："邮件"，
  =
}
```


下列服务定义指示中科院使用 `骨灰盒：绿洲：名称：tc：SAML：2.0：名称格式：瞬态` 为最终名称ID格式， ，并使用 `cn` 属性值作为上部最终名称 ID 值，跳过按所需格式生成瞬态值。



```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务。萨姆注册服务"，
  "服务id"："实体-id-sp"，
  "名称"："SAML服务"，
  "元数据定位"："/路径/到/sp-元数据.xml"，
  "id"： 1，
  "要求名称表"："urn：绿洲：名称：tc：SAML：2.0：名称格式：瞬态"，
  "跳过生成递质名称"：真实，
  "用户名归因提供者"：{
    @class："org.apereo.cas.服务.委托属性注册服务用户提供者"，
    "用户名归因"："cn"，
    "规范化"："上"
  =
}
```


以下服务定义指示 CAS 使用 `cn` 属性值创建持久名称 ID。



```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务。萨姆注册服务"，
  "服务id"："实体-id-sp"，
  "名称"："SAML服务"，
  "元数据定位"："/路径/到/sp-元数据.xml"，
  "id"： 1，
  "要求名称表"："urn：绿洲：名称：tc：SAML：2.0：名称格式：持久"，
  "用户名归因提供者"：\
    "@class"："org.apereo.cas.服务。匿名注册服务使用者"，
    "持久生成器"： [
      "@class"： "org. apereo. cas. 认证. 校长. 希博莱特兼容持久生成器"，
      "盐"： "agvsbg93b3jsza="，
      "属性"： "cn"
    [
  =

```




## 未经请求的 SSO

SAML2 idP `主动/SSO` 配置文件支持以下参数：

| 参数      | 描述                       |
| ------- | ------------------------ |
| `提供商Id` | 必填。 服务提供商的实体 ID。         |
| `郡`     | 自选。 服务提供商的响应位置（ACS URL）。 |
| `目标`    | 自选。 中继状态。                |
| `时间`    | 自选。 扭曲身份验证请求。            |




## 属性查询

为了让 CAS 支持和响应属性查询，您需要确保生成的元数据已 启用了 `属性授权描述器` 元素，并启用了 `骨灰盒：绿洲：名称：tc：SAML：2.0：协议` 以及与 CAS 端点对应的相关绑定。 您还必须确保 `属性授权描述器` 标签列出所有用于签名和身份验证的 `键描述器` 元素和证书，特别是如果服务提供商 的 SOAP 客户端需要将 CAS 端点后面的证书与 `属性授权描述符`定义的证书进行交叉比较。 CAS 默认情况下 始终使用自己的签名证书来签署因属性查询而生成的响应。

另请注意，需要明确启用对属性查询的支持，并且行为默认关闭，因为这将给 CAS 和基础票证注册表带来负担，以跟踪属性和响应作为票证，并让它们以后使用并查找。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-idp)。



## 服务提供商集成

一些 SAML2 服务提供商集成由 CAS 本地提供。 要了解更多，请 [本指南](../integration/Configuring-SAML-SP-Integrations.html)。



## 服务提供商元数据

如果您希望集成的SP不生成SAML元数据，您可以 使用此服务 [](https://www.samltool.com/sp_metadata.php) 创建元数据， 将其保存在 XML 文件中，然后向 CAS 引用并注册为 SP。

或者，您也可以利用一个独立的 `saml-sp-元数据.json` 文件，该文件可能与 CAS 元数据工件在同一目录 中找到。 本文件的内容如下：



```json
{
  "https://example.org/saml"：{
    "实体"："https://example.org/saml"，
    "证书"："MIIDUj.。。"，
    "断言""https://example.org/sso/"
  }
}
```


文件中的每个条目都由服务提供商实体 ID 标识，允许 CAS 动态定位并生成所需的元数据， 恢复身份验证流程。 对于那些仅为 集成提供 URL 和签名证书的服务提供商来说，这可能更容易，从而缓解您单独创建和管理 XML 元数据文件的困难。

服务提供商在 CAS 服务注册处注册如下：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务"，
  "服务id"："https://example.org/saml"，
  "名称"："SAML服务"，
  "id"：1000003，
  "元数据定位"："json://"

```

<div class="alert alert-info"><strong>元数据位置</strong><p>上述注册记录中的元数据位置需要指定为 <code>json://</code> 向 CAS 发出信号，即注册服务提供商的 SAML 元数据必须从指定的 JSON 文件中提取。</p></div>

## 客户端库

对于基于 Java 的应用程序，以下框架可用于将您的应用程序与作为 SAML2 身份提供商的 CAS 集成：

- [春季安全山姆尔](http://projects.spring.io/spring-security-saml/)
- [帕克4j](http://www.pac4j.org/docs/clients/saml.html)



## 示例客户端应用程序

- [春季安全山姆样本网络应用程序](https://github.com/cas-projects/saml2-sample-java-webapp)
- [奥克塔](https://developer.okta.com/standards/SAML/setting_up_a_saml_application_in_okta)



## 故障 排除

要启用其他日志，请修改记录配置文件以添加以下：



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
