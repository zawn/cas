---
layout: 违约
title: 中科院 - 中科院WS联合会议定书
category: 协议
---

# WS联邦议定书


CAS可以作为一个独立的身份提供者，为 [WS-联邦被动请求者配置文件](http://docs.oasis-open.org/wsfed/federation/v1.2/os/ws-federation-1.2-spec-os.html#_Toc223175002)提供支持。 的核心功能是建立在阿帕奇·费迪兹</a>之上的，他的架构在这里被描述为 [](http://cxf.apache.org/fediz-architecture.html)。</p> 



## 安全令牌服务

WS-信任OASIS标准指定了一个名为"安全令牌服务"的运行时组件。 服务消费者向发送给服务提供商的 STS 请求安全令牌。 要么服务提供商可以自行验证安全令牌，也可以向 STS 发送验证请求。 此模式基于服务提供商和 STS 之间的间接信任关系，而不是服务提供商和服务消费者之间的直接信任。 只要服务消费者持有受信任的 STS 签发的安全令牌，服务提供商接受此安全令牌。

STS 的一个主要好处是降低了应用程序的复杂性。 网络服务消费者不必知道如何创建其服务提供商所需的各种类型的安全 令牌。 相反，它向 STS 发送包含客户端和服务提供商要求的请求，并将退回的安全令牌附加到即将离任的 SOAP 消息中，然后发送给服务提供商。

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - ws - sts</artifactId>
  <version>${cas.version}</version>
</dependency>
```

<div class="alert alert-info"><strong>亚格尼</strong><p>您不需要在配置和叠加中明确包含此组件
。 这只是为了教你它的存在。 安全令牌服务将在您声明身份提供商后自动 
中提取。 只有当您 
需要对内部组件进行编译时间访问时，才能将此模块包含在叠加中。</p></div>

### 端点

| 端点     | 描述                                   |
| ------ | ------------------------------------ |
| `/周/圣` | 为配置中定义的每个领域提供可用 SOAP 服务列表及其 WSDL 配置。 |





### 安全令牌

签发的安全令牌被视为 CAS 票证，存储在票证登记簿中， 前缀 `STS` ，并在持久性、 复制等方面遵循与所有其他票种相同的语义。 这些代币与赠票票的使用寿命密切相关，并与其到期政策 匹配。 代币本身在有效赠票票 之外没有使用寿命，并且不存在对机票终身配置的支持。



## WS 联邦身份提供商

STS 的安全模型建立在 WS 安全与 WS 信托建立的基础基础上。 Web 浏览器的主要问题是，没有简单的方法直接发送 Web 服务 （SOAP） 请求。 因此，处理必须在基础 HTTP 1.1 功能（`获取`、 `POST`、重定向和 Cookie） 范围内进行，并尽可能严格地遵守 WS-Trust 协议以进行代币收购。 IdP 负责将浏览器的登录请求转换为 STS 的 SOAP 请求，以及 STS 对浏览器登录响应的响应。 此外，浏览器用户必须使用 IdP 进行身份验证。

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - ws - idp</artifactId>
  <version>${cas.version}</version>
</dependency>
```




### 端点

| 端点            | 描述                                  |
| ------------- | ----------------------------------- |
| `/ws/idp/元数据` | 根据身份提供商的配置领域显示当前联盟元数据。              |
| `/ws/伊德普/联邦`  | 接收来自客户的初始 `获取` 认证请求的终点，通常确定为 `发行人`。 |




## 领域

此时，默认情况下，安全令牌服务的终端使用单一领域配置和身份提供商进行操作， 配置只能识别和请求单个领域的令牌。 虽然尚未支持多个领域，但总体而言，基础配置 应允许该功能在以后的版本中存在。 中科院认定的默认领域设置为 `骨灰盒：org：apereo：cas：ws：idp：领域-CAS`。 客户注册需要确保此值匹配。



## 注册客户端

客户和依托方可在中科院注册：



```json
•
  "@class"："org.apereo.cas.ws.idp.服务.WSFeder 注册服务"，
  "服务ID"："https://wsfed.example.org/.+"，
  "名称"："样本 WsFed 应用程序"，
  "id"：100

```


| 田      | 描述                                                                         |
| ------ | -------------------------------------------------------------------------- |
| `服务ID` | 回拨/消费者网址，其中令牌可能 `POST`ed，通常匹配 `拧` 参数。                                      |
| `领域`   | 应用程序的境界标识符，通过 `wtrealm` 参数识别。 这需要匹配为身份提供商定义的领域。 默认情况下，它被设置为为CAS身份提供商定义的领域。 |
| `适用于`  | 适用于安全令牌的控件。 `领域的默认`。                                                       |


服务定义可由 [服务管理](../services/Service-Management.html) 设施管理。



### 标准索赔

属性筛选和释放策略由每个依赖方定义。 有关详细信息，请参阅本指南</a> 。</p> 

CAS 支持以下标准索赔以供发布：

| 索赔                            | 描述                                                                                |
| ----------------------------- | --------------------------------------------------------------------------------- |
| `EMAIL_ADDRESS_2005`          | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress`              |
| `EMAIL_ADDRESS`               | `http://schemas.xmlsoap.org/claims/EmailAddress`                                  |
| `GIVEN_NAME`                  | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname`                 |
| `名字`                          | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name`                      |
| `USER_PRINCIPAL_NAME_2005`    | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/upn`                       |
| `USER_PRINCIPAL_NAME`         | `http://schemas.xmlsoap.org/claims/UPN`                                           |
| `COMMON_NAME`                 | `http://schemas.xmlsoap.org/claims/CommonName`                                    |
| `群`                           | `http://schemas.xmlsoap.org/claims/Group`                                         |
| `MS_ROLE`                     | `http://schemas.microsoft.com/ws/2008/06/identity/claims/role`                    |
| `角色`                          | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role`                      |
| `姓`                           | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname`                   |
| `PRIVATE_ID`                  | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/privatepersonalidentifier` |
| `NAME_IDENTIFIER`             | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier`            |
| `AUTHENTICATION_METHOD`       | `http://schemas.microsoft.com/ws/2008/06/identity/claims/authenticationmethod`    |
| `DENY_ONLY_GROUP_SID`         | `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/denyonlysid`               |
| `DENY_ONLY_PRIMARY_SID`       | `http://schemas.microsoft.com/ws/2008/06/identity/claims/denyonlyprimarysid`      |
| `DENY_ONLY_PRIMARY_GROUP_SID` | `http://schemas.microsoft.com/ws/2008/06/identity/claims/denyonlyprimarygroupsid` |
| `GROUP_SID`                   | `http://schemas.microsoft.com/ws/2008/06/identity/claims/groupsid`                |
| `PRIMARY_GROUP_SID`           | `http://schemas.microsoft.com/ws/2008/06/identity/claims/primarygroupsid`         |
| `PRIMARY_SID`                 | `http://schemas.microsoft.com/ws/2008/06/identity/claims/primarysid`              |
| `WINDOWS_ACCOUNT_NAME`        | `http://schemas.microsoft.com/ws/2008/06/identity/claims/windowsaccountname`      |
| `小狗`                          | `http://schemas.xmlsoap.org/claims/PUID`                                          |


分配给依赖方和服务的属性释放策略能够将给定标准索赔链接到应已可用的属性 映射。 配置如下所示：



```json
•
  "@class"："org.apereo.cas.ws.idp.服务.WSFederation 注册服务"，
  "服务ID"："https://wsfed.example.org/.+"，
  "领域"："urn：wsfed：示例：org：样本申请"，
  "名称"："WSFED"，
  "id"： 1，
  "属性释放政策"： [
    "@class"： "org. apereo. cas. ws. idp. 服务. Wsfeder 索赔重新发布政策"，
    "允许归因"：{
      "@class"："java.util.TreeMap"，
      "GIVEN_NAME"："给定名称"
    }
  }
}
```


上述片段允许 CAS 发布索赔 `http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname` 其价值 由已检索为经过验证的委托人的 `的` 属性的值标识。



### 内联沟索赔

索赔可以从内联 Groovy 脚本生成其值。 例如，索赔 `EMAIL_ADDRESS_2005` 可以 构建为动态属性，其值由内联 Groovy 脚本属性和 `cn` 属性决定：



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：300，
  "属性发布政策"：{
    "@class"："组织.apereo.cas.ws.idp.服务。WSFeed索赔释放政策"，
    "允许属性"：{
      " @class"："java.util.TreeMap"，
      "EMAIL_ADDRESS_2005"："凹凸不休的{返回属性[cn'。get（0）=[@example.org'}"
    }
  }
}
```




### 基于文件的格罗夫索赔

索赔可以从外部 Groovy 脚本生成其值。 例如，索赔 `EMAIL_ADDRESS_2005` 可以 构建为动态属性，其值由 Groovy 脚本属性和 `cn` 属性决定：



```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：300，
  "属性释放政策"： [
    "@class"： "org. apereo. cas. ws. idp. 服务. Wsfeed 索赔释放政策"，
    "允许属性" ：{
      "@class"： "java. util. treemap"，
      "EMAIL_ADDRESS_2005"： "文件/ 路径 / 脚本. groovy"
    =
  =
}
```


此组件的配置有资格使用 [弹簧表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。 脚本 本身可能有以下大纲：



```groovy
定义运行（最终对象。。。args）{
    def属性=args[0]
    def记录器=args[1]

    logger.info"属性当前已解决： ${attributes}"
    返回[属性["cn"][0] +"@example.org"]
}
```




### 自定义索赔

您还可以决定发布非标准索赔，作为自定义名称空间的一部分。 例如，下文片段允许 CAS 发布索赔 `https://github.com/apereo/cas/employeeNumber` 其价值由已检索到的 `人安全` 属性的值标识。



```json
•
  "@class"： "组织. apereo. cas. ws. idp. 服务. Wsfeder 注册服务"，
  "服务Id"："https://wsfed.example.org/.+"，
  "领域"："urn：wsfed：示例：示例：示例应用"，
  "名称"："WSFED"，
  "id"：1，
  "属性释放政策"： [
    "@class"： "org. apereo. cas. ws. idp. 服务. 自定义名称空间搜索释放政策"，
    "命名空间"： "https://github.com/apereo/cas"，
    "允许归因"：{
      "@class"："java.util.TreeMap"，
      "员工编号"："人安全"
    }
  }
}
```




## 代币类型

CAS 支持以下代币类型：

| 类型                                                                         |
| -------------------------------------------------------------------------- |
| `http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1` |
| `http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0` |
| `骨灰盒：艾特夫：参数：陶氏：令牌类型：jwt`                                                   |
| `http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct`        |


代币类型可按服务配置：



```json
•
  "@class"： "组织. apereo. cas. ws. idp. 服务. Wsfeder 注册服务"，
  "服务id"："https://wsfed.example.org/.+"，
  "领域"："http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1：示例：示例：样本应用"，
  "名称"："WSFED"，
  "id"：1，
  "令牌类型"："http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1"
}
```




## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ws-federation)。

您可能需要在 CAS 叠加 中申报以下存储库，以解决依赖关系：



```groovy
存储库{
    maven{ 
        马文康顿{发布（）=
        网址"https://build.shibboleth.net/nexus/content/repositories/releases" 
    }
}
```




## 故障 排除

要启用其他日志，请修改记录配置文件以添加以下：



```xml
<Logger name="org.apache.cxf" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
