---
layout: 违约
title: CAS - 中科院休息协议
category: 协议
---

# 休息协议

REST 协议允许用户对应用程序进行建模，以编程方式获取 服务票证以验证其他应用程序。 这意味着其他应用程序将能够 使用 CAS 客户端接受服务票证，而不是依赖其他技术，如 客户端 SSL 证书来申请申请到应用程序验证的请求。 这是通过暴露一种方法，以 REST 完全获得门票赠与票，然后用它来获得服务票，从而 实现。

<div class="alert alert-warning"><strong>使用警告！</strong><p>REST端点可能
 成为CAS服务器上暴力词典攻击的极其方便的目标。 考虑
 启用限制支持，以确保在身份验证失败时防止暴力攻击。</p></div>

## 配置

支持通过将以下内容包含到叠加中来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-休息</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 请求门票赠与票

```bash
POST/cas/v1/门票HTTP/1.0
"内容类型"："应用程序/x-www-形式-已编码"
用户名=&密码=密码&附加Param1=参数值
```

您还可以指定 `服务` 参数，以验证是否允许经过验证的用户访问给定服务。

### 成功响应

```bash
201 创建
位置：http://www.whatever.com/cas/v1/tickets/{TGT id}
```

### 响应不成功

如果发送了不正确的凭据，CAS 将以 `401 未授权`进行响应。 `400 坏请求` 错误将发送丢失参数等。 如果您发送一个媒体类型，它不明白，它会发送 `415不受支持的媒体类型`。

### Jwt 门票赠与门票

由 REST 协议创建的出票票可以改为 JWT 签发。 支持通过在叠加中包括以下内容来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-休息-代币</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要请求作为 JWT 的下一张出票票，请确保 `POST` 请求符合以下要求：

```bash
POST/cas/v1/门票HTTP/1.0

用户名=标签&密码=密码&令牌=真实&其他Param1=参数值
```

`令牌` 参数可以作为请求参数或请求标头传递。 响应的主体将包括作为 JWT 的出票票证。 请注意，创建的 JWT 通常在默认情况下使用预生成的密钥进行签名和加密。 要控制设置或查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jwt-tickets)。

## 身份验证凭据

与索要赠票类似，此终点仅允许从请求机构提取提供的凭据的有效性：

```bash
帖子/cas/v1/用户HTTP/1.0

用户名=标签&密码=密码
```

您还可以指定 `服务` 参数，以验证是否允许经过验证的用户访问给定服务。 虽然上述示例显示 `用户名` 和 `密码` 作为提供的凭据，但实际上允许您提供多套和不同类型的凭据，前提是 CAS 配备从请求机构提取和识别这些凭据。 有关</a>的更多信息，请参阅此

。</p> 

成功的响应将生成 `200 确定` 状态代码以及身份验证结果的 JSON 表示，其中可能包括身份验证对象、经过身份验证的委托以及为身份验证用户提取的任何捕获属性和/或元数据。



## 请求服务票

下文片段显示，使用 CAS 协议的语义，可以请求服务票证：



```bash
邮政/卡斯/v1/门票/{TGT id} HTTP/1.0

服务={form encoded parameter for the service url}
```


您还可以指定一个 `续订` 参数，以获得服务票证，该服务票证只能由只希望从用户的主要凭据演示中签发的机票接受。 在这种情况下，用户凭据必须通过请求，例如， `用户名` 和 `密码` 参数。



```bash
邮政/卡斯/v1/票务/{TGT id} HTTP/1.0

服务={form encoded parameter for the service url}&续订=真实&用户名=标签&密码=密码
```


您也可以使用 SAML1 协议 [语义](SAML-Protocol.html)提交服务票证请求。



### 成功响应



```bash
200确定
ST-1-FFDFHDS肯尼迪肯尼迪2132
```




### JWT 服务门票

由 REST 协议创建的服务票证可以改为 JWT 签发。 请参阅本指南 [](../installation/Configure-ServiceTicket-JWT.html) 了解更多。

支持通过在叠加中包括以下内容来实现：



```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-休息-代币</artifactId>
    <version>${cas.version}</version>
</dependency>
```


请注意，创建的 JWT 通常在默认情况下使用预生成的密钥进行签名和加密。 要控制设置或查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jwt-tickets)。



## 验证服务票

服务票证验证通过 [CAS 协议](CAS-Protocol.html) 通过任何验证端点（如 `/p3/服务验证`）进行。 



```bash
获取/cas/p3/服务验证服务={service url}&票={service ticket}
```




### 响应不成功

CAS 将发送 400 个坏请求。 如果发送了不正确的媒体类型 ，它将发送 415 不受支持的媒体类型。



## 注销

通过删除已发行的票证来破坏 SSO 会话：



```bash
删除/卡斯/v1/门票/TGT-fdsjfsdfalfewrihfdfaie HTTP/1.0
```




## 票务状态

验证已获得的机票的状态，以确保它仍然有效 并且尚未过期。



```bash
获取/卡斯/v1/门票/TGT-fdsjfdfdfalfewrihfdhfaie HTTP/1.0
```




### 成功响应



```bash
200 确定
```




### 响应不成功



```bash
404未找到
```




## 添加服务

支持通过在叠加中包括以下内容来实现：



```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>套机服务器支持休息服务</artifactId>
    <version>${cas.version}</version>
</dependency>
```


调用 CAS 将申请注册到自己的服务注册表中。 REST 呼叫必须使用基本身份验证进行身份验证，在现有 CAS 认证策略中，凭据经过身份验证并接受证书，此外，必须授权经过认证的委托人使用 CAS 配置中通过 CAS 属性指定的预配置角色/属性名称和值进行认证。 请求的主体必须是应以 JSON 格式注册的服务定义，当然，CAS 必须配置以接受机构中定义的特定服务类型。 此请求的接受媒体类型为 `应用程序/json`。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-api)。



```bash
开机自检/卡斯/v1/服务 HTTP/1.0
```


...请求的主体可能是：



```json
•
  "@class"： "org. apereo. cas. 服务. 注册服务"，
  "服务"： "。。。"，
  "名称"： "。。。"，
  "id"： 1，
  "描述"： "。。。"
}
```


一个成功的响应将产生一个 `200` 状态代码作为回报。



## X.509 身份验证

该功能将 CAS REST API 通信模型扩展到非交互式 X.509 身份验证 其中可以从请求中嵌入的证书中检索 REST 凭据，而不是 通常和默认的用户名/密码。

如果内部网络架构隐藏在 CAS 服务器 防火墙、反向代理或消息总线后面的外部用户那里，并且 仅允许受信任的应用程序直接连接到 CAS 服务器，则此模式可能值得关注。

<div class="alert alert-warning"><strong>使用警告！</strong><p>X.509 功能超过 REST
使用车身参数或 http 标头，为在没有私钥所有权证明的情况下申请
用户身份或获取 TGT 提供了非常方便的目标。
为了安全地使用此功能，网络配置 <strong>必须</strong> 仅允许
与 CAS 服务器的连接，而该主机又具有严格的安全限制和
记录。
还建议确保车身参数或 http 标头只能从受信任的主机
，而不能来自原始身份验证客户端。</p></div>

也可以让伺服容器在 TLS 握手期间验证 TLS 客户端键 / X.509 证书 ，并让 CAS 服务器从容器中检索证书。

支持通过在叠加中包括以下内容来实现：



```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡斯服务器支持-休息-x509</artifactId>
    <version>${cas.version}</version>
</dependency>
```




### 请求出票票证（使用身体参数的代理 TLS 客户端身份验证）



```bash
邮政/卡斯/v1/票HTTP/1.0
证书=<ascii certificate>
```




### 请求票证授予票证（使用 http 标题的代理 TLS 客户端身份验证）

cas服务器应在登录页面上配置为X509身份验证，以 正常工作。



### 请求出票（从伺服容器中获取 TLS 客户端身份验证）

cas服务器应在登录页面上配置为X509身份验证，以 正常工作。



#### 成功响应



```bash
201 创建
位置：http://www.whatever.com/cas/v1/tickets/{TGT id}
```




## 多重凭据

CAS REST API 机械能够使用多个 *凭据提取器* ，这些提取器负责分析请求主体，以便获取凭据并传递凭据。 虽然默认情况下，可能提取的预期凭据基于用户名/密码，但其他模块会自动加入此设计，并自动将其意见的凭据提取器注入 REST 发动机，以便最终收集凭据可用于签发机票等。 从某种意义上说，这就是 [X.509 认证](#x509-authentication) 如何与 CAS REST 协议集成。 

这表明，您可以将多个凭据传递给请求主体中的 REST 协议，只要 CAS 被配置为理解和提取这些凭据，并且身份验证机制也配置为执行和验证这些凭据。 例如，您可以提供一个使用案例，其中两组以用户名/密码和 OTP 形式提供给 REST 协议的凭据，然后 CAS 将尝试对凭据进行身份验证，并在成功验证时生成响应，前提是用户名/密码和 OTP 的身份验证策略在 CAS 中进行了正确配置。



## 卡斯休息客户

为了与 CAS REST API 交互，必须使用 REST 客户端提交凭据， 收到门票并进行验证。 以下爪哇 REST 客户端可 [pac4j](https://github.com/pac4j/pac4j) ：



```java
导入组织. pac4j. cas. 配置文件. 卡斯雷斯特计划;
进口组织. pac4j. cas. 客户. 休息. 卡斯雷斯特形式公司;
进口组织.pac4j.cas.配置.卡配置;
导入组织.pac4j.cas.凭据.验证器.卡斯雷斯特验证器;
进口组织. pac4j. cas. 配置文件. 卡斯档案;
导入组织.pac4j.核心.上下文。JE康泰：
导入组织. pac4j. 核心. 上下文. 网络通文：
进口组织.pac4j.核心.凭据.代币信用;
导入组织.pac4j.核心.凭据。用户名密码信用;
导入组织.pac4j.核心.例外.特普行动;
进口组织. 弹簧框架. mock. web. 莫克特普服务要求：
进口组织. 弹簧框架. mock. web. 莫克特普服务响应;

进口爪哇。利用。地图：
进口爪哇。

公共类休息测试+

    公共静态空虚主 （字符串 [] args ） 抛出 Httpaction =
        最后的字符串 casurlPrefix = "http://localhost:8080/cas"：
        字符串用户名=args[0]：
        字符串密码=args[1]：
        字符串服务url=args[2]：
        卡卡配置卡卡配置=新卡壳配置（casUrlPrefix）;
        最后的卡斯雷斯特验证器 身份验证器=新的卡斯雷斯特验证器（cas配置）;
        最终的卡斯雷斯特信息客户端=新的卡斯雷斯特信息公司（casReestFormclient（卡什配置，"用户名"，"密码"）;
        最终的模拟服务请求= 新的模拟服务请求（）;
        最终的模拟服务回复=新的模拟服务回复（）;

        最终的WebConxt网络通信=新的JEE通信（请求， 响应）：
        个cas配置。init（web机密）;
        用户名密码信用凭据=新的用户名密码信用（用户名，密码，"测试"）：
        卡斯雷斯特验证器重新授权器=新的卡斯雷斯特验证器（卡萨配置）：
        //使用凭据（验证凭据）
        重新验证器进行身份验证。
        最后的卡斯雷斯特档案=（卡斯雷斯特档案）凭据。
        //获取服务票
        最终代币信用案例=客户端。
        //验证服务票证
        最终的CasProfile案例=客户端。
        地图<String,Object> 属性=cas属性。
        设置<Map.Entry<String,Object>> 地图=属性。
        （地图。条目：地图输入）^
            系统。out.println（输入。getKey）="："+条目。获取价值（）
        =
        客户端。
    }
}


```




## 节流

要了解如何限制在中科院的工作， 请审查 [可用的选项](../installation/Configuring-Authentication-Throttling.html)。 默认情况下，限制 REST 请求将关闭。 要激活此功能，您需要选择适当的节气门，并通过声明相关模块来激活它。 然后，为支持限制的 REST 端点激活处理通常的 CAS 服务器端点以进行身份验证 和票证验证等的相同节流机制。 

要查看相关选项，请 [](../configuration/Configuration-Properties.html#rest-api)查看本指南。



## 斯瓦格阿皮

CAS REST API 可以自动与斯瓦格集成。 [有关详细信息，请参阅本指南](../integration/Swagger-Integration.html) 。
