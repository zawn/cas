---
layout: 默认
title: CAS-CAS REST协议
category: 通讯协定
---

# REST协议

REST协议允许人们将应用程序建模为用户，以编程方式获取 张服务凭单以对其他应用程序进行身份验证。 这意味着其他应用程序将能够 使用CAS客户端来接受服务票证，而不是依靠另一种技术（例如 客户端SSL证书）来进行请求的应用程序到应用程序身份验证。 通过公开一种REST完全获取票证授予票证，然后使用该方法来获取服务票证的方法，可以将其实现为 

<div class="alert alert-warning"><strong>使用警告！</strong><p>REST终结点可能为
 成为CAS服务器上蛮力字典攻击的极为方便的目标。 考虑将
 启用节流支持，以确保在身份验证失败时防止暴力攻击。</p></div>

## 配置

通过在叠加层中包含以下内容来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-rest</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 申请门票授予门票

```bash
POST / CAS / V1 /车票HTTP / 1.0
'内容类型'： '应用程序/ X WWW的窗体-urlencoded'
的用户名= battags&密码=密码&additionalParam1 = paramvalue
```

您还可以指定 `service` 参数来验证是否可以允许通过身份验证的用户访问给定的服务。

### 成功回应

```bash
201创建了
位置：http：//www.whatever.com/cas/v1/tickets/{TGT id}
```

### 回应失败

如果发送了错误的凭据，则CAS将以 `401未经授权`响应。 由于缺少参数等，将发送 `400错误请求` 如果发送的媒体类型无法识别，它将发送 `415不支持的媒体类型`。

### JWT门票授予门票

可以将由REST协议创建的授予票证的票证作为JWT发行。 通过在叠加图中包括以下内容来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持休息令牌</artifactId>
    <version>${cas.version}</version>
</dependency>
```

接下来要以JWT形式请求授予票证的票证，请确保 `POST` 请求与以下内容匹配：

```bash
POST / cas / v1 / tickets HTTP / 1.0

用户名= battags&密码=密码&令牌= true&AdditionalParam1 = paramvalue
```

`令牌` 参数可以作为请求参数或请求标头传递。 响应的主体将包括授予票证的票证作为JWT。 请注意，默认情况下，通常使用预先生成的密钥对创建的JWT进行签名和加密。 要控制设置或查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#jwt-tickets)。

## 认证凭证

类似于索取授予票证的票证，此端点仅允许一个人验证从请求正文中提取的所提供凭证的有效性：

```bash
POST / cas / v1 /用户HTTP / 1.0

用户名= battags&密码=密码
```

您还可以指定 `service` 参数来验证是否可以允许通过身份验证的用户访问给定的服务。 虽然上面的示例显示了 `用户名` 和 `密码` 作为提供的凭据，但是实际上允许您提供多组凭据和不同类型的凭据，前提是CAS具备从请求正文中提取和识别那些凭据的能力。 有关更多信息，请参见 [](#multiple-credentials)。

成功的响应将产生 `200 OK` 状态代码以及身份验证结果的JSON表示，其中可能包括身份验证对象，已身份验证的主体以及为身份验证的用户获取的任何捕获的属性和/或元数据。

## 索取服务票

以下代码片段显示了一个可能会使用CAS协议的语义来请求服务票证的情况：

```bash
POST / cas / v1 / tickets /{TGT id} HTTP / 1.0

服务={form encoded parameter for the service url}
```

您还可以指定 `renew` 参数以获得服务票证，该服务票证只能由仅希望从用户主要凭据的显示中发出的票证的服务接受。 在这种情况下，必须在请求中传递用户凭据，例如，将其作为 `用户名` 和 `密码` 参数。

```bash
POST / cas / v1 / tickets /{TGT id} HTTP / 1.0

服务={form encoded parameter for the service url}&续订= true&用户名= battags&密码=密码
```

您还可以使用语义 [SAML1协议](SAML-Protocol.html)提交服务票证请求。

### 成功回应

```bash
200 OK
ST-1-FFDFHDSJKHSDFJKSDHFJKRUEYREWUIFSD2132
```

### JWT服务票

可以将由REST协议创建的服务票证作为JWT发行。 请参阅 [本指南](../installation/Configure-ServiceTicket-JWT.html) 以了解更多信息。

通过在叠加图中包括以下内容来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持休息令牌</artifactId>
    <version>${cas.version}</version>
</dependency>
```

请注意，默认情况下，通常使用预先生成的密钥对创建的JWT进行签名和加密。 要控制设置或查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#jwt-tickets)。

## 验证服务票

服务票证验证是通过 [CAS协议](CAS-Protocol.html) 通过任何验证端点（例如 `/ p3 / serviceValidate`。

```bash
GET / cas / p3 / serviceValidate？service ={service url}&票={service ticket}
```

### 回应失败

CAS将发送400错误请求。 如果发送了错误的媒体类型 ，它将发送415不支持的媒体类型。

## 登出

通过删除已发行的票证来破坏SSO会话：

```bash
删除/ cas / v1 / tickets / TGT-fdsjfsdfjkalfewrihfdhfaie HTTP / 1.0
```

## 票务状态

验证获取的票证的状态，以确保其仍为有效 且尚未过期。

```bash
GET / cas / v1 / tickets / TGT-fdsjfsdfjkalfewrihfdhfaie HTTP / 1.0
```

### 成功回应

```bash
200 OK
```

### 回应失败

```bash
404未找到
```

## 添加服务

通过在叠加图中包括以下内容来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持休息服务</artifactId>
    <version>${cas.version}</version>
</dependency>
```

调用CAS将应用程序注册到其自己的服务注册表中。 REST调用必须使用基本身份验证进行身份验证，其中凭据由现有CAS身份验证策略进行身份验证并接受，此外，还必须使用通过CAS配置在CAS配置中指定的预配置角色/属性名称和值来授权已身份验证的主体CAS属性。 请求的主体必须是应以JSON格式注册的服务定义，并且当然，必须将CAS配置为接受在主体中定义的特定服务类型。 此请求接受的媒体类型为 `application / json`。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-api)。

```bash
POST / cas / v1 / services HTTP / 1.0
```

...请求的正文可能是：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ...”，
  “ name”：“ ...”，
  “ id”：
  “描述”： ”...”
}
```

成功的响应将 `200` 状态码。

## X.509验证

该特征延长了CAS REST API通信模型到非交互式认证X.509 其中REST凭证可以从嵌入在该请求，而不是一个证书被检索 的常用和默认用户名/密码。

的CAS服务器对外部用户隐藏在防火墙，反向代理或消息传递总线后面，而 仅允许受信任的应用程序直接连接到CAS服务器，则这种模式可能会引起人们的兴趣。

<div class="alert alert-warning"><strong>使用警告！</strong><p>使用主体参数或http标头的
的X.509功能
用户身份或获取TGT（无需证明私钥所有权）提供了极为方便的目标。
为了安全地使用此功能，网络配置 <strong>必须</strong> 允许
，而受信任的主机又具有严格的安全性限制和
日志记录。
此外，还建议，以确保身体参数或HTTP头只能来自
可信任主机从原来的身份验证客户端，而不是。</p></div>

也可以让servlet容器在TLS握手期间验证TLS客户端密钥/ X.509证书 ，并让CAS服务器从容器中检索证书。

通过在叠加图中包括以下内容来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-rest-x509</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 请求票证授予票证（使用body参数的代理TLS客户端身份验证）

```bash
POST / cas / v1 / tickets HTTP / 1.0
cert =<ascii certificate>
```

### 请求票证授予票证（使用http标头的代理TLS客户端身份验证）

应该在登录页面上将cas服务器配置为进行X509身份验证，以使其正常运行

### 请求票证授予票证（来自Servlet容器的TLS客户端身份验证）

应该在登录页面上将cas服务器配置为进行X509身份验证，以使其正常运行

#### 成功回应

```bash
201创建了
位置：http：//www.whatever.com/cas/v1/tickets/{TGT id}
```

## 多个凭证

CAS REST API机械具有使用多个 *凭证提取器* 的能力，这些凭证提取器1负责分析请求主体，以获取凭证并将其传递。 虽然默认情况下可以提取的预期凭据基于用户名/密码，但是其他模块会自动将其自身引入此设计，并将其自认为的凭据提取器自动注入REST引擎，以便可以将凭据的最终集合用于发行票证等。 。 从某种意义上讲，这就是 [X.509身份验证](#x509-authentication) 与CAS REST协议集成在一起的方式。

这表明您可以将多个凭据传递给请求正文中的REST协议，只要将CAS配置为理解和提取那些凭据，并且将身份验证机制配置为还执行和验证那些凭据即可。 例如，您可能会提供一个用例，其中向REST协议提供了两组以用户名/密码和OTP形式提供的凭据，然后CAS将尝试对这两个凭据进行身份验证，并在成功进行验证的情况下产生响应，并假定身份验证策略用户名/密码和OTP的密码已在CAS中正确配置。

## CAS REST客户端

为了与CAS REST API进行交互，必须使用REST客户端提交凭据， 接收票证并对其进行验证。 以下Java REST客户端 [pac4j](https://github.com/pac4j/pac4j)：

```java
import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.credentials.authenticator.CasRestAuthenticator;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.HttpAction;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Map;
import java.util.Set;

public class RestTestClient {

    public static void main(String[] args ) throws HttpAction {
        final String casUrlPrefix = "http://localhost:8080/cas";
        String username = args[0];
        String password = args[1];
        String serviceUrl = args[2];
        CasConfiguration casConfiguration = new CasConfiguration(casUrlPrefix);
        final CasRestAuthenticator authenticator = new CasRestAuthenticator(casConfiguration);
        final CasRestFormClient client = new CasRestFormClient(casConfiguration,"username","password");
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        final WebContext webContext = new JEEContext(request, response);
        casConfiguration.init(webContext);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username,password,"testclient");
        CasRestAuthenticator restAuthenticator = new CasRestAuthenticator(casConfiguration);
        // authenticate with credentials (validate credentials)
        restAuthenticator.validate(credentials, webContext);
        final CasRestProfile profile = (CasRestProfile) credentials.getUserProfile();
        // get service ticket
        final TokenCredentials casCredentials = client.requestServiceTicket(serviceUrl, profile, webContext);
        // validate service ticket
        final CasProfile casProfile = client.validateServiceTicket(serviceUrl, casCredentials, webContext);
        Map<String,Object> attributes = casProfile.getAttributes();
        Set<Map.Entry<String,Object>> mapEntries = attributes.entrySet();
        for (Map.Entry entry : mapEntries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        client.destroyTicketGrantingTicket(profile,webContext);
    }
}


```

## 节流

要了解如何在CAS，节流作品 请查看 [可用的选项](../installation/Configuring-Authentication-Throttling.html)。 默认情况下，限制REST请求处于关闭状态。 要激活此功能，您将需要选择合适的节流阀并通过声明相关模块来激活它。 然后，为支持节流的REST端点激活与通常的CAS服务器端点进行身份验证

要查看相关选项， [查看本指南](../configuration/Configuration-Properties.html#rest-api)。

## Swagger API

CAS REST API可能会自动与Swagger集成。 [有关更多信息，请参见本指南](../integration/Swagger-Integration.html)
