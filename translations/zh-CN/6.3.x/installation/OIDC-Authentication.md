---
layout: 违约
title: CAS - 开放ID连接认证
category: 认证
---

# 打开ID 连接身份验证

允许 CAS 担任 [开放式连接提供商 （OP）](http://openid.net/connect/)。

<div class="alert alert-info"><strong>记得</strong><p>OpenId 连接是 <a href="OAuth-OpenId-Authentication.html">非授权协议</a> 的延续，还有其他一些变化。 如果您启用了开放连接，您也将自动启用OAuth。 为 <a href="OAuth-OpenId-Authentication.html">非统组织协议记录的选项和行为</a> 支持可能同样适用于此处。</p></div>

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 -</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要了解有关 OpenId 连接的更多内容，请 [查看本指南](http://openid.net/specs/openid-connect-basic-1_0.html)。

当前的实施为：

- [授权代码流](http://openid.net/specs/openid-connect-basic-1_0.html)
- [隐性流](https://openid.net/specs/openid-connect-implicit-1_0.html)
- [动态发现](https://openid.net/specs/openid-connect-discovery-1_0.html)
- [网络手指发行人发现](https://openid.net/specs/openid-connect-discovery-1_0-21.html)
- [OIDC 客户和依赖方的管理和注册](../services/Service-Management.html)。
- 通过 [动态客户注册协议](https://tools.ietf.org/html/draft-ietf-oauth-dyn-reg-management-01)</a>

OIDC 客户和依托方的管理和注册。</li> 
  
  - [解决、映射和发布索赔的能力](../integration/Attribute-Release-Policies.html)。
- 能够为各种令牌配置过期策略。</ul> 



## 端点

| 田                      | 描述                                                                                                                                       |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `/奥德克/。众所周知`           | 用于查询 CAS OIDC 配置信息和元数据的发现端点。                                                                                                             |
| `/oidc/.知名/开放式配置`      | 与 `.众所周知的` 发现终点相同。                                                                                                                       |
| `/奥德克/。众所周知/温格`        | [网络手指](http://tools.ietf.org/html/rfc7033) 发现终点                                                                                          |
| `/奥德克/杰克斯`             | 包含服务器的公开签名密钥，客户端可以使用这些密钥来验证 CAS 签发的访问令牌和 ID 代币的数字签名。                                                                                     |
| `/奥德克/授权`              | 授权请求在此处处理。                                                                                                                               |
| `/奥德克/简介`              | 此处处理用户配置文件请求。                                                                                                                            |
| `/奥德克/内省`              | 查询 CAS，通过 [反省](https://tools.ietf.org/html/rfc7662)检测给定访问令牌的状态。 此端点期望使用 OIDC 服务进行 HTTP 基本身份验证， `client_id` 并将 `client_secret` 关联为用户名和密码。 |
| `/oidc/访问`， `/oidc/令牌` | 生成授权访问令牌。                                                                                                                                |
| `/奥德克/撤销`              | [撤销](https://tools.ietf.org/html/rfc7009) 访问或刷新令牌。 此端点期望使用 OIDC 服务进行 HTTP 基本身份验证， `client_id` 并将 `client_secret` 关联为用户名和密码。              |
| `/奥德克/注册`              | 通过 [动态客户注册](https://tools.ietf.org/html/draft-ietf-oauth-dyn-reg-management-01) 协议注册客户端。                                                 |




## 注册客户端

客户可以通过以下方式在中科院注册。



### 静态

OpenID 连接客户端可以静态 ** 在 CAS 注册：



```json
•
  "@class"： "组织. apereo. cas. 服务. Oidc 注册服务"，
  "客户ID"："客户"，
  "客户秘密"："秘密"，
  "服务ID"："^<https://the-redirect-uri>"，
  "名称"："OIDC"，
  "id"：1000
}
```


请注意，OpenID 将客户端作为服务定义连接是 CAS</a> 非授权服务的延伸。 适用于非授权服务定义的所有设置也应同样适用于此处。 开放 ID 连接服务特别适用于以下字段：</p> 

| 田             | 描述                                                                                                   |
| ------------- | ---------------------------------------------------------------------------------------------------- |
| `客户ID`        | 必填。 此客户端应用程序的标识符。                                                                                    |
| `客户安全`        | 必填。 此客户端应用程序的秘密。                                                                                     |
| `服务ID`        | 必填。 此OIDC客户端的授权重定向URI。                                                                               |
| `支持格兰特类型`     | 自选。 为这项服务收集支持的赠款类型。                                                                                  |
| `支持响应类型`      | 自选。 收集此服务的支持响应类型。                                                                                    |
| `签名托肯`        | 自选。 是否应签署 ID 令牌。 默认值 `真实`。                                                                           |
| `杰克斯`         | 自选。 资源路径到持有此应用程序密钥的密钥存储位置。                                                                           |
| `杰克斯·切斯杜迪`    | 自选。 适用于此应用程序的加载/缓存密钥的到期保单时间值。                                                                        |
| `杰克斯·卡奇时间联合号` | 自选。 措施的到期保单时间单位（即 `秒`， `分钟`等）适用于加载/缓存键。                                                              |
| `加密加密`        | 自选。 是否应加密 ID 令牌。 违约 `虚假`。                                                                            |
| `伊德托肯加密Alg`   | 自选。 用于加密 ID 令牌的算法标头值。                                                                                |
| `伊德托肯签名`      | 自选。 用于签署 ID 令牌的算法标头值。                                                                                |
| `用户信息签名`      | 自选。 用于签署用户配置文件响应的算法标头值。                                                                              |
| `用户信息加密响应`    | 自选。 用于加密用户配置文件响应的算法标题值。                                                                              |
| `令牌端点验证模因`    | 自选。 要求的客户端身份验证方法到令牌端点。 默认值 `client_secret_basic`。                                                    |
| `应用类型`        | 自选。 `网络`， `原生`，或空白。 定义应用程序的类型。 默认值（如果省略）是 `网络`。                                                      |
| `伊德托肯加密编码`    | 自选。 用于加密 ID 令牌的算法方法标题值。                                                                              |
| `用户信息加密响应编码`  | 自选。 用于加密用户配置文件响应的算法方法标题值。                                                                            |
| `主题类型`        | 从公共 `` 或 `对`中选择的可选价值。 生成主标识符时要键入使用。 违约是公共</code>`。</td>
</tr>
<tr>
  <td><code>部门标识符` | 自选。 此 URL 的主机值用作对向标识符计算的扇区标识符。 如果未定义，将改为使用 `服务Id` 的主机值。 |

<div class="alert alert-info"><strong>保留您需要的！</strong><p>我们鼓励您只保留和维护特定集成所需的属性和设置， 
特定集成。 获取所有服务字段的副本并尝试根据它们的默认值再次配置它们是不必要的。 虽然 
您可能希望保留副本作为参考，但此策略最终会导致升级不力，从而增加打破更改的机会，并导致混乱的 
部署。</p></div>

服务定义通常由 [服务管理](../services/Service-Management.html) 设施管理和注册。

<div class="alert alert-warning"><strong>使用警告！</strong><p>CAS 今天没有严格执行出于向后兼容性原因的授权支持 
响应/授予类型的收集（如果留空）。 这意味着，如果未定义， 
服务定义和相关政策允许所有授予和响应类型。 请注意，此行为 <strong>在未来版本 
中更改</strong> ，因此，强烈建议在服务定义中立即声明每个配置文件的所有授权授予/响应类型， 
立即声明，以避免将来出现意外。</p></div>

### 动态

客户端申请可在 CAS 动态注册以进行身份验证。 默认情况下，CAS 在注册端点需要用户身份验证的 `保护` 模式下运行 。 此行为可以通过 CAS 设置来放松，使 CAS 能够以 `开放` 模式运行。



## 设置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect)。



## 服务器配置

请记住，CAS 的 OpenID 连接功能需要会话亲和力（和可选会话复制）， ，因为整个登录流中的授权响应都通过服务器支持的会话存储机制存储。 您需要相应地配置部署环境和负载平衡器。



## 示例客户端应用程序

- [米特雷德样本爪哇网络应用程序](https://github.com/cas-projects/oidc-sample-java-webapp)



## 索赔

OpenID连接索赔只是被视为正常的CAS属性，需要 [解决，映射和释放](../integration/Attribute-Release-Policies.html)。



### 基于范围的索赔

您可以根据特定范围链接授权索赔释放的各种属性发布策略：



```json
•
  "@class"："org.apereo.cas.服务.Oid注册服务"，
  "客户id"："。。。"，
  "客户安全"："。。。"，
  "服务ID"："。。。"
  "名称"："OIDC测试"，
  "id"：10，
  "范围"："java.util.HashSet"， 
    "配置文件"，"电子邮件"，"地址"，"电话"，"offline_access"，"显示名称"，"爱德华森"[
  ]

```


内部目录预先定义的索赔的标准范围都属于名称空间 `组织.apereo.cas.oidc.索赔` ，如下所述：

| 政策                                      | 描述                        |
| --------------------------------------- | ------------------------- |
| `o.a.c.o.c.Oidc 可专业可归因发布政策`             | 发布映射到指定预定义的 `配置文件` 范围的索赔。 |
| `o.a.c.o.o.c.Oidc电子邮件放大缩小字体功能 放大缩小字体功能` | 发布映射到指定 `电子邮件` 范围的请求。     |
| `o.a.c.o.o.c.奥德卡地址范围分配重新发布政策`           | 已映射到指定预定义的 `地址` 范围的发布声明。  |
| `o.a.c.o.c.Oidcphone 放大缩小字体功能 放大缩小字体功能` | 发布映射到指定 `手机` 范围的索赔。       |




### 映射索赔

与范围相关的索赔（即 `配置文件`）的 `given_name` [开放ID规范](http://openid.net/specs/openid-connect-basic-1_0.html)中固定。 在自定义任意属性映射到索赔 情况下，可以在 CAS 设置中定义映射，以便将 CAS 定义的属性链接到固定给定范围。 例如，CAS 配置可能 允许将属性 `sys_given_name` 值映射并分配给索赔 `given_name` 而不会对属性分辨率配置和所有其他支持 CAS 的应用程序产生影响。 

如果不定义映射，默认情况下，CAS 属性应与索赔名称匹配。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect)。



### 用户定义的范围

请注意，除了标准系统示波器外，您还可以在：



```json
•
  "@class"： "org. apereo. cas. 服务. Oidc 注册服务"，
  "客户id"： "。。。"
  "客户安全"："..."，
  "服务ID"："..."，
  "名称"："OIDC测试"，
  "id"：10，
  "范围"："java.util.HashSet"，"显示姓名"，"爱德华森"][
]
```


这些，如上面显示 `名` ，捆绑成一个 `自定义` 范围，可以使用和要求的服务和客户。

但是，如果您希望将自定义范围定义为 OpenID Connect 定义 以便将属性捆绑在一起，则您需要首先</code>注册您的 `范围，
定义其属性捆绑包，然后使用上述 <code>eduPerson` 等给定服务定义。 这种用户定义的范围也能够覆盖系统范围的定义。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect)。



### 发布索赔

定义给定服务定义控制的范围，并在内部构建属性释放策略。 此类属性发布 政策允许发布标准索赔、将属性重新标记为标准索赔，或完全定义自定义索赔和范围。 

也可以定义和使用 *自由形式的* 属性发布策略，</em> 自由构建和发布索赔/属性的范围之外 *范围。  </p> 

例如，以下服务定义将基于</code> 和 `电子邮件`的范围 `语义
决定相关属性发布策略。 无需设计或列出个人索赔，因为 CAS 将自动配置
相关属性发布策略：</p>

<pre><code class="json">•
  "@class"："org.apereo.cas.服务.Oidc 注册服务"，
  "客户ID"："客户"，
  "客户安全"："秘密"，
  "服务ID"："......"，
  "名称"："OIDC"，
  "id"：1，
  "范围"："java.util.HashSet"，
    ["配置文件"，"电子邮件"=
  ]

`</pre> 

*无范围* 属性发布策略同样适用，允许 以下示例中的一个发布 `用户 X` 作为 *索赔*：



```json
•
  "@class"："org.apereo.cas.服务.Oid注册服务"，
  "客户"："客户"，
  "客户秘密"："秘密"，
  "服务ID"："。。。"，
  "名称"："OIDC"，
  "id"： 1，
  "属性释放政策"： [
    "@class"： "org. apereo. cas. 服务. 返回地图属性释放政策"，
    "允许属性"： [
      "@class"： "java. util. Treemap"，
      "用户 X" "："凹凸不全{返回属性['uid'。get（0）='-X'}"
    }
  }
}
```


也可以将 *自由形式的* 发布政策与基于范围操作的政策混合在一起，将此类政策串联在一起。 例如，以下政策 允许发布 `用户 x` 作为索赔，以及为标准 `电子邮件` 范围分配和内部定义的所有索赔。



```json
{
  "@class": "org.apereo.cas.services.OidcRegisteredService",
  "clientId": "client",
  "clientSecret": "secret",
  "serviceId": "...",
  "name": "OIDC",
  "id": 10,
  "attributeReleasePolicy": {
    "@class": "org.apereo.cas.services.ChainingAttributeReleasePolicy",
    "policies": [
      "java.util.ArrayList",
      [
        {
          "@class" : "org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy",
          "allowedAttributes" : [ "java.util.ArrayList", [ "cn", "uid", "givenName" ] ],
          "order": 0  
        },
        {
          "@class": "org.apereo.cas.services.ReturnMappedAttributeReleasePolicy",
          "allowedAttributes": {
            "@class": "java.util.TreeMap",
            "user-x": "groovy { return attributes['uid'].get(0) + '-X' }"
          },
          "order": 1
        },
        {
          "@class": "org.apereo.cas.oidc.claims.OidcEmailScopeAttributeReleasePolicy",
          "order"：2
        [
      ]
    ]
  }
}
```


有关属性发布政策和指挥链的更多了解，请 [](../integration/Attribute-Release-Policies.html)查看本指南。



## 身份验证上下文类

作为原始 授权请求的一部分，对身份验证上下文类引用的支持以 `acr_values` 的形式实施，这主要通过 中科院</a> 多因素认证功能来考虑。 一旦成功， `acr` 和 `amr` 值作为 ID 令牌的一部分传回依赖方。</p> 



## 对对标识符

当使用 `对` 主题类型时，CAS 将计算每个扇区标识符的独特 `子` 值。 此标识符 不应由 CAS 以外的任何一方可逆，并且与 CAS 产生持续匿名用户 标识符有些类似。 提供给每个依赖方的每一个值都不同，因此不允许客户端在未经许可的情况下关联用户的活动。



```json
•
  "@class"："org.apereo.cas.服务.Oid注册服务"，
  "客户ID"："客户"，
  "客户秘密"："秘密"，
  "服务ID"："^<https://the-redirect-uri>"，
  "用户名归因提供者"：{
    "@class"："org.apereo.cas.服务。对等机构注册服务"，
    "持续生成器"：{
      "@class"："组织.apereo.cas.认证.校长.OidcPair"，
      "盐"："aGVsbG93b3JsZA+"
    =
  =
}
```




## 钥匙店

CAS中的每份注册申请都可以包含自己的钥匙店作为 `jwks` 资源。 默认情况下， 可以通过 CAS 属性预期和定义全球钥匙店。 密钥存储 文件的格式类似于以下内容：



```json
{
  "键"：[
    ]
      "d"："。。。"，
      "e"："AQAB"，
      "n"："。。。"，
      "kty"："RSA"，
      "孩子"："卡斯"
    }
  ]
}
```


CAS 将尝试自动生成钥匙存储（如果找不到钥匙店），但如果您希望手动生成钥匙存储，则可以使用此工具 [生成 JWKS](https://mkjwk.org/) 或</a>此工具。</p> 



## 网络手指发行人发现

开放ID提供商发行人发现是确定开放ID提供商位置的过程。 发行人发现是可选的：如果依赖方 通过带外机制知道OP的发行人位置，它可以跳过此步骤。

发行人发现需要以下信息才能提出发现请求：

| 参数   | 描述                                                           |
| ---- | ------------------------------------------------------------ |
| `资源` | 必填。 作为发现请求主题的目标最终用户的标识符。                                     |
| `主机` | 托管Web手指服务的服务器。                                               |
| `相对` | URI 识别请求位置的服务类型：`http://openid.net/specs/connect/1.0/issuer` |


要开始发现 OpenID 端点，最终用户会向依赖方提供标识符。 RP 将规范化规则应用于标识符，以 确定资源和主机。 然后，它向 CAS WebFinger 端点提出 HTTP `获取` 请求，该端点具有 `资源` 和 `rel` 参数，以 请求服务的位置获取。 发行人位置 **必须在 WebFinger 响应中** 退回，作为链接阵列元素 `` 成员的值 ， `rel` 成员值 `http://openid.net/specs/connect/1.0/issuer`。

`织机` 终点的示例如下：



```bash
卷曲 https://sso.example.org/cas/oidc/.well-known/webfinger?resource=acct:casuser@somewhere.example.org
```


预期响应应符合以下示例：



```json
{
  "主题"："acct:casuser@somewhere.example.org"，
  "链接"：[
    ]
      "rel"："http://openid.net/specs/connect/1.0/issuer"，
      "href"："https://sso.example.org/cas/oidc/"
    [
  ]
}
```


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect-webfinger)。



### 网络手指资源用户信息

要确定正确的发行人，使用 `acct` URI 方案 提供给 `webfinger` 发现端点的资源可以通过 `电子邮件` 或 `用户名`找到并提取外部用户存储库。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认存储库实施将 
简单地回呼所提供的电子邮件或用户名等，因为它 <strong>只有</strong> 相关演示/测试目的。</p></div>

以下用户信息存储库选项可用于配置和生产使用。



#### 格罗夫用户信息存储库

定位与韦伯资源相关的帐户的任务可以使用外部 Groovy 脚本处理，其大纲将匹配以下内容：



```groovy
定义查找通过用户名（对象[]args）{
    def用户名=args[0]
    def记录器=args[1]
    返回[用户名：用户名]
}

def查找通过电子邮件地址（对象[]args）{
    def电子邮件=args[0]
    def记录器=args[1]
    返回[电子邮件：电子邮件]
}
```


脚本的预期回报值是包含键值对象（表示用户帐户详细信息）的 `映射` 。 空 `地图` 将指示缺少用户记录，导致 `404` 响应状态返回给依赖方。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect-webfinger)。



#### 休息用户信息存储库

REST 存储库允许 CAS 服务器通过配置的 HTTP 方法到达远程 REST 端口以获取用户帐户信息。

查询数据通过 `电子邮件` 或 `用户名` HTTP 标题传递。 返回的响应必须附有 `200` 状态代码，其中主体应包含代表用户帐户信息的 `地图` 。 所有其他响应将导致 `404` 响应状态返回给依赖方。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#openid-connect-webfinger)。



#### 自定义用户信息存储库

可以设计并注入您自己的版本的织机用户存储库到 CAS 中。 首先，您需要设计 `@Configuration` 类，以包含您自己的 `OidcWebFiner 信息存储库` 实现：



```java
@Configuration（"自定义网络手指使用者信息配置"）
@EnableConfigurationProperties（cas配置.class）
公共类自定义网络手指使用者信息配置=

    @Bean
    公共OidcWeb手指信息存储器 oidcWeb手指信息存储器信息存储器（）\
        。。。
    •
}
```


您的配置类需要在CAS注册。 [请参阅本指南](../configuration/Configuration-Management-Extensions.html) 了解更多详细信息。
