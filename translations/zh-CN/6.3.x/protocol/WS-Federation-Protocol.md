---
layout: 默认
title: CAS-CAS WS联合协议
category: 通讯协定
---

# WS联合协议


CAS可以充当独立的身份提供者，为 [WS-Federation Passive Requestor Profile](http://docs.oasis-open.org/wsfed/federation/v1.2/os/ws-federation-1.2-spec-os.html#_Toc223175002)提供支持。 核心功能 是建立在的顶部 [的Apache Fediz](http://cxf.apache.org/fediz.html) ，其结构被描述 [这里](http://cxf.apache.org/fediz-architecture.html)。

## 安全令牌服务

WS-Trust OASIS标准指定了一个称为“安全令牌服务”的运行时组件。 服务使用者从STS请求安全令牌，该令牌被发送到服务提供商。 服务提供商可以自己验证安全令牌，也可以向STS发送请求以进行验证。 此模式基于服务提供者和STS之间的间接信任关系，而不是服务提供者和服务使用者之间的直接信任。 只要服务使用者拥有由受信任的STS发出的安全令牌，服务提供者就可以接受此安全令牌。

STS的主要优点是降低了应用程序的复杂性。 Web服务使用者不必知道如何创建其服务提供商所需的 而是，它将包含客户端和服务提供者要求的请求发送到STS，并将返回的安全令牌附加到服务提供者的传出SOAP消息中。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-ws-sts</artifactId>
  <version>${cas.version}</version>
</dependency>
```

<div class="alert alert-info"><strong>亚尼</strong><p>您无需在配置和覆盖中明确包含此组件
 这只是要告诉您它的存在。 声明身份提供者后，安全令牌服务将 
 如果您需要编译时访问其中的组件，则仅在叠加中包括此模块（ 
</p></div>

### 终点

| 终点           | 描述                                   |
| ------------ | ------------------------------------ |
| `/ ws / sts` | 显示配置中定义的每个REALM的可用SOAP服务及其WSDL配置的列表。 |


### 安全令牌

发出的安全性令牌被视为CAS票证，存储在票证注册表中的 （前缀 `STS` 复制等方面遵循与所有其他票证类型相同的语义。 这些令牌与授予票证的票证的生命周期紧密相关，并与它们的到期策略 令牌本身在有效的授予票证的票证 之外没有有效期，并且不存在对票证生存期配置的支持。

## WS Federation身份提供者

STS的安全模型建立在WS-Security和WS-Trust建立的基础上。 Web浏览器的主要问题是没有简单的方法可以直接发送Web服务（SOAP）请求。 因此，处理必须的基HTTP 1.1官能度（的范围内进行`GET`， `POST`，重定向，和饼干） 和尽可能接近地符合WS-信任协议令牌获取。 IdP负责将浏览器的登录请求转换为针对STS的SOAP请求，并将 STS的响应转换为浏览器的登录响应。 此外，浏览器用户必须使用IdP进行身份验证。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-ws-idp</artifactId>
  <version>${cas.version}</version>
</dependency>
```

### 终点

| 终点                        | 描述                                    |
| ------------------------- | ------------------------------------- |
| `/ ws / idp /元数据`         | 根据身份提供者的配置领域显示当前联盟元数据。                |
| `/ ws / idp / federation` | 从客户端（通常标识为 `发行者`接收初始 `GET` 身份验证请求的端点。 |

## 境界

此时，默认情况下，安全令牌服务的端点使用单个领域配置运行，并且身份提供者 配置仅能够识别和请求单个领域的令牌。 尽管尚不支持多个领域，但总体而言，底层配置 应该允许该功能存在于更高版本中。 CAS识别的默认领域设置为 `urn：org：apereo：cas：ws：idp：realm-CAS`。 客户注册需要确保该值匹配。

## 注册客户

客户和依赖方可以在CAS上进行以下注册：

```json
{
  “@class”： “org.apereo.cas.ws.idp.services.WSFederationRegisteredService”，
  “服务Id”： “https://wsfed.example.org/.+”，
  “名称”：“样品WsFed应用程序”，
  “ id”：100
}
```

| 场地          | 描述                                                                     |
| ----------- | ---------------------------------------------------------------------- |
| `serviceId` | 回调/消费者url，其中令牌可以是 `POST`ed，通常与 `wreply` 参数匹配。                          |
| `领域`        | `wtrealm` 参数标识的应用程序的领域标识符。 这需要匹配为身份提供者定义的领域。 默认情况下，它设置为为CAS身份提供者定义的领域。 |
| `适用于`       | 控制将安全令牌应用到的对象。 默认为 ``。                                                 |

服务定义可以由 [服务管理](../services/Service-Management.html) 设施管理。

### 标准索偿

属性过滤和释放策略是每个依赖方定义的。 有关更多信息，请参见 [本指南](../integration/Attribute-Release-Policies.html)

CAS支持以下标准声明以供发布：

| 宣称                            | 描述                                                                                |
| ----------------------------- | --------------------------------------------------------------------------------- |
| `EMAIL_ADDRESS_2005`          | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress`              |
| `电子邮件地址`                      | `http://schemas.xmlsoap.org/claims/EmailAddress`                                  |
| `给定的名称`                       | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname`                 |
| `名称`                          | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name`                      |
| `USER_PRINCIPAL_NAME_2005`    | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/upn`                       |
| `USER_PRINCIPAL_NAME`         | `http://schemas.xmlsoap.org/claims/UPN`                                           |
| `COMMON_NAME`                 | `http://schemas.xmlsoap.org/claims/CommonName`                                    |
| `团体`                          | `http://schemas.xmlsoap.org/claims/Group`                                         |
| `MS_ROLE`                     | `http://schemas.microsoft.com/ws/2008/06/identity/claims/role`                    |
| `角色`                          | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role`                      |
| `姓`                           | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname`                   |
| `PRIVATE_ID`                  | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/privatepersonalidentifier` |
| `NAME_IDENTIFIER`             | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier`            |
| `身份验证方法`                      | `http://schemas.microsoft.com/ws/2008/06/identity/claims/authenticationmethod`    |
| `DENY_ONLY_GROUP_SID`         | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/denyonlysid`               |
| `DENY_ONLY_PRIMARY_SID`       | `http://schemas.microsoft.com/ws/2008/06/identity/claims/denyonlyprimarysid`      |
| `DENY_ONLY_PRIMARY_GROUP_SID` | `http://schemas.microsoft.com/ws/2008/06/identity/claims/denyonlyprimarygroupsid` |
| `GROUP_SID`                   | `http://schemas.microsoft.com/ws/2008/06/identity/claims/groupsid`                |
| `PRIMARY_GROUP_SID`           | `http://schemas.microsoft.com/ws/2008/06/identity/claims/primarygroupsid`         |
| `PRIMARY_SID`                 | `http://schemas.microsoft.com/ws/2008/06/identity/claims/primarysid`              |
| `WINDOWS_ACCOUNT_NAME`        | `http://schemas.microsoft.com/ws/2008/06/identity/claims/windowsaccountname`      |
| `PUID`                        | `http://schemas.xmlsoap.org/claims/PUID`                                          |

分配给依赖方和服务的属性释放策略能够链接给定的标准声明，并将其映射到应该已经可用 配置如下所示：

```json
{
  “@class”： “org.apereo.cas.ws.idp.services.WSFederationRegisteredService”，
  “服务Id”： “https://wsfed.example.org/.+”，
  “的境界”：“瓮：wsfed：example：org：sampleapplication“，
  ”名称“：” WSFED“，
  ” id“：
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.ws.idp.services .WSFederationClaimsReleasePolicy“，
    ” allowedAttributes“：{
      ” @class“：” java.util.TreeMap“，
      ” GIVEN_NAME“：” givenName“
    }
  }
}
```

上面的代码片段允许CAS释放声明 `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname` 其值 由已经检索 `namedName` 属性的值标识对于经过身份验证的委托人。

### 内联Groovy索赔

声明可以从嵌入式Groovy脚本中产生其值。 作为一个例子，权利要求 `EMAIL_ADDRESS_2005` 可以被构造 作为动态属性，其值由内联Groovy脚本属性来确定和 `CN` 属性：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.ws.idp.services.WSFederationClaimsReleasePolicy”，
    “ allowedAttributes”：{
      “ @class”：“ java.util.TreeMap”，
      “ EMAIL_ADDRESS_2005”：“ groovy {返回属性['cn']。get（0）+'@ example.org'}“
    }
  }
}
```

### 基于文件的Groovy声明

声明可以从外部Groovy脚本中产生其值。 作为一个例子，权利要求 `EMAIL_ADDRESS_2005` 可以被构造 作为动态属性，其值由Groovy脚本属性来确定和 `CN` 属性：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：300，
  “attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.ws.idp.services.WSFederationClaimsReleasePolicy”，
    “ allowedAttributes”：{
      “ @class”：“ java.util.TreeMap”，
      “ EMAIL_ADDRESS_2005”：“文件： /path/to/script.groovy“
    }
  }
}
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。 脚本 本身可能具有以下轮廓：

```groovy
def run（final Object ... args）{
    def属性= args[0]
    def logger = args[1]

    logger.info“当前解析的属性： ${attributes}”
    return [attributes [“ cn”][0] +“ @ example.org “]
}
```

### 自定义声明

您还可以决定释放非标准声明作为自定义名称空间的一部分。 例如，下面的代码片段允许CAS释放声明 `https://github.com/apereo/cas/employeeNumber` ，该声明的值由已为身份验证的主体检索 `personSecurityId`

```json
{
  “@class”： “org.apereo.cas.ws.idp.services.WSFederationRegisteredService”，
  “服务Id”： “https://wsfed.example.org/.+”，
  “的境界”：“瓮：wsfed：example：org：sampleapplication“，
  ”名称“：” WSFED“，
  ” id“：
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.ws.idp.services .CustomNamespaceWSFederationClaimsReleasePolicy “
    ”命名空间“： ”https://github.com/apereo/cas“，
    ”allowedAttributes“：{
      ”@class“： ”java.util.TreeMap中“，
      ”employeeNumber“：” personSecurityId “
    }
  }
}
```

## 代币类型

CAS支持以下令牌类型：

| 类型                                                                         |
| -------------------------------------------------------------------------- |
| `http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1` |
| `http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0` |
| `urn：ietf：params：oauth：token-type：jwt`                                     |
| `http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct`        |

令牌类型可以基于每个服务进行配置：

```json
{
  “@class”： “org.apereo.cas.ws.idp.services.WSFederationRegisteredService”，
  “服务Id”： “https://wsfed.example.org/.+”，
  “的境界”：“瓮：wsfed：example：org：sampleapplication“，
  ”名称“：” WSFED“，
  ” id“：
  ” tokenType“：” http://docs.oasis-open.org/wss/oasis-wss- saml-token-profile-1.1＃SAMLV1.1“
}
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ws-federation)。

您可能还需要在 声明以下存储库，以便能够解决依赖关系：

```groovy
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://build.shibboleth.net/nexus/content/repositories/releases” 
    }
}
```

## 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：

```xml
<Logger name="org.apache.cxf" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
