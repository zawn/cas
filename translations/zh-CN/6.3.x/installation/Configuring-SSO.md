---
layout: 默认
title: CAS-配置SSO会话
category: SSO & SLO
---

# SSO会议

授予票证的cookie是CAS在建立单点登录会话时设置的HTTP cookie。 该cookie维护 状态，当它有效时，客户端可以代替主要凭据将其提供给CAS。 `renew` 参数选择退出单点登录，或者CAS服务器可以根据在服务注册表中为应用程序定义的策略有条件地选择退出服务 有关更多信息，请参见 [CAS协议](../protocol/CAS-Protocol.html)

cookie值链接到活动票据授予票据，发起请求 的远程IP地址以及提交请求的用户代理。 然后，最终的cookie值将被加密并签名。

这些密钥 **必须在您的特定环境中重新生成** 每个密钥 是一个JSON Web令牌，具有用于加密和签名的算法所定义的长度。

如果部署者未生成密钥，则CAS将尝试自动生成密钥，并为每个受尊重的密钥 部署者务必尝试将生成的密钥复制到 设置上，特别是在运行多节点CAS部署时。 否则，将导致CAS 无法适当解密和加密cookie值，并且将阻止成功的单点登录。

<div class="alert alert-info"><strong>SSO会议</strong><p>可以查看当前活动SSO会话的集合
 <a href="../monitoring/Monitoring-Statistics.html">CAS管理面板确定CAS本身是否维护活动SSO会话。</a></p></div>

## 行政端点

CAS提供了以下端点：

| 终点   | 描述                                                                                                                                                                                            |
| ---- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `会话` | 查看与CAS建立的当前单点登录会话，并远程管理每个会话。 甲 `GET` 操作产生由一个提供滤波电流SSO会话的列表 `类型` 参数与值 `ALL`， `代理` 或 `DIRECT`。 `DELETE` 操作将尝试破坏所有SSO会话。 在URL中指定允许票据的票据标识符作为占位符/选择器，将尝试破坏该票据所控制的会话。 （即 `ssoSessions /{ticket}`）。 |
| `so` | 指示绑定到浏览器会话和SSO cookie的单点登录会话的当前状态。                                                                                                                                                            |

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ticket-granting-cookie)。

Cookie具有以下属性：

1. 它被标记为安全。
2. 根据容器的支持，该cookie将被自动标记为仅http。
3. cookie值通过需要在部署时生成的秘密密钥进行加密和签名。

如果未定义密钥，则CAS会在启动时注意到未定义任何密钥，并且会自动为您自动生成密钥。 然后，您的CAS日志将显示以下代码段：

```bash
WARN [...] - <加密密钥是没有定义。 CAS将自动生成加密密钥>
WARN [...] - <生成大小的加密密钥ABC ...。 生成的密钥必须添加到CAS设置中。>
WARN [...] - <用于签名的秘密密钥不被限定。 CAS将自动生成的签名密钥>
WARN [...] - <生成的签名大小的关键XYZ ...。 生成的密钥必须添加到CAS设置中。>
```

然后，您应该获取每个生成的密钥以进行加密和签名，并将其放入每个设置的CAS属性中。 如果希望手动生成密钥，则可以使用以下工具</a>

。</p> 



## 用于更新身份验证的Cookie生成

默认情况下，通过 [`续订` 请求参数](../protocol/CAS-Protocol.html) 或通过 [CAS服务注册表的服务特定设置](../services/Service-Management.html) of 强制身份验证请求仍将始终生成票证授予cookie 。 这意味着通过CAS登录到非SSO参与应用程序 仍会创建有效的CAS单点登录会话，该会话将在 尝试向SSO参与应用程序进行身份验证时兑现。

cookie生成策略也可以基于每个应用程序进行自定义。 有关其他详细信息，请 [本指南](../services/Configuring-Service-SSO-Policy.html)。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。



## 全局禁用SSO

可以通过CAS属性全局禁用参与单点登录会话。 要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。



## 禁用服务SSO访问

可以在每个应用程序上禁用参与现有的单点登录会话。 例如， 以提供凭据，从而不使用SSO：



```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ...”，
  “ name”：“ ...”，
  “ id”：
  “ accessStrategy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy“，
    ” ssoEnabled“：false
  }
}
```




## SSO参与政策

单一登录参与策略也可以基于每个应用程序进行定制。 有关其他详细信息，请 [本指南](../services/Configuring-Service-SSO-Policy.html)。



## SSO警告会话Cookie

根据用户在CAS登录页面上的请求，在建立SSO会话时由CAS设置的警告cookie。 该cookie稍后用于在生成服务票证和授予对服务应用程序的访问权之前警告并提示用户。

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#warning-cookie)。



## 公共工作站

在公共工作站上进行，CAS具有允许用户选择退出SSO的能力。 通过选择这样做，CAS将不会接受后续的SSO会话 ，也不会生成旨在这样做的TGC。



```html
...
<input id="publicWorkstation"
       name="publicWorkstation"
       value="false" tabindex="4"
       type="checkbox" />
<label for="publicWorkstation" th:utext="#{screen.welcome.label.publicstation}"/>
...
```




## 默认服务

如果没有将 `服务` 提交给CAS，则可以指定CAS将重定向到 请注意，此默认服务（与 一样）必须在CAS中进行授权和注册。

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#views)。



## 所需服务

可以将CAS配置为要求用户从应用程序进行身份验证，然后才能向所有其他注册服务授予 一旦CAS找到所需的 应用程序的记录作为单点登录会话记录的一部分，它将允许 身份验证尝试，直到破坏单点登录会话。

可以根据每个应用程序关闭和跳过此类验证检查：



```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//www.example.com”，
  “ name”：“ Example”，
  “ id” ：
  “描述”：“示例”，
  “属性”：{
    “ @class”：“ java.util.HashMap”，
    “ skipRequiredServiceCheck”：{
      “ @class”：“ org.apereo.cas .services.DefaultRegisteredServiceProperty”，
      “值”：[“ java.util.HashSet”，[“ true”]]
    }
  }
}
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。
