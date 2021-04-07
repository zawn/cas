---
layout: 违约
title: CAS - 委托认证
category: 认证
---

# 委托身份验证

CAS 可以使用 [Pac4j 库](https://github.com/pac4j/pac4j) 充当客户端（即服务提供商或代理），并将认证委托给：

* CAS服务器
* SAML2 身份提供者
* OAuth2提供商，如脸谱网、推特、GitHub、谷歌、LinkedIn等
* 开放ID连接身份提供商，如谷歌，苹果
* [民主同盟军](ADFS-Integration.html)

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - pac4j - 网络流</artifactId>
    <version>${cas.version}</version>
</dependency>
```

<div class="alert alert-info"><strong>注意</strong><p>发出身份验证请求的客户端可以是任何类型的（SAML、OAuth2、OpenID 连接等），并且允许使用 CAS 服务器支持并配置为理解的任何协议提交身份验证请求。 这意味着，您可能有 OAuth2 客户端在委托模式下使用 CAS 在外部 SAML2 身份提供商、另一个 CAS 服务器或 Facebook 进行身份验证，并在该流程结束时接收 OAuth2 用户配置文件。 CAS 服务器能够充当代理，在中间执行协议翻译。</p></div>

## 注册提供商

身份提供商是一种服务器，可以验证用户（如谷歌，雅虎...），而不是CAS服务器。 例如，如果您想将 CAS 身份验证委托给 Twitter，则必须为 Twitter 提供商添加一个 OAuth 客户端，一旦向 CAS 传授提供商设置，该客户端将自动为您完成。

请注意，对于每个非授权提供商，CAS 服务器被视为非授权客户端，因此应 非授权提供商的非授权客户端声明。 申报后，非授权提供商会提供密钥和机密，该提供商 在 CAS 配置中定义。

### 违约

授权身份验证的身份提供商可以使用设置在 CAS 注册。 有关 中科院物业名单，请 [本指南](../configuration/Configuration-Properties.html#pac4j-delegated-authn)。

### 休息

授权身份验证的身份提供商可以使用外部 REST 端点提供给 CAS。 这使得 CAS 服务器能够到达 远程 REST 端点，其职责是在响应机构中生成以下有效载荷：

```json
\
    "回调url"："https://sso.example.org/cas/login"，
    "属性"：{
        "github.id"："。。。"，
        "github.秘密"："。。。"，

        "cas.loginUrl.1"："。。。"，
        "cas.协议1"："。。。"
    •
}
```

上述有效载荷中</code> 可用 `属性的语法和集合由 [Pac4j]（https://pac4j.org/docs/index.html）控制。 
返回的响应必须附有 200 状态代码。</p>

<p spaces-before="0">要查看 CAS 属性的相关列表，请 <a href="../configuration/Configuration-Properties.html#pac4j-delegated-authn">查看本指南</a>。</p>

<h2 spaces-before="0">用户界面</h2>

<p spaces-before="0">所有可用的客户端将自动显示在登录页面上，作为可点击按钮。
CAS 确实允许将身份验证流自动重定向到提供商的选项，
只要有一个可用和配置的单个提供商。</p>

<h2 spaces-before="0">已验证用户 ID</h2>

<p spaces-before="0">在成功委托身份验证后，在 CAS 服务器内创建了具有特定标识符的用户：
只能从外部身份提供商（如 1234`） 或作为"键入标识符"（如 `FacebookProfile#1234`） `技术标识符创建此标识符，这是默认的。</p>

<p spaces-before="0">要查看 CAS 属性的相关列表，请 <a href="../configuration/Configuration-Properties.html#pac4j-delegated-authn">查看本指南</a>。</p>

<h2 spaces-before="0">返回的有效载荷</h2>

<p spaces-before="0">一旦您将 CAS 服务器配置（见上文信息）作为非正统、
CAS、OpenID（连接）或 SAML 客户端，用户将能够在非真实/CAS/OpenID/SAML
提供商（如 Facebook）进行身份验证，而不是直接在 CAS 服务器内进行身份验证。</p>

<p spaces-before="0">在 CAS 服务器中，此类委托身份验证后，用户具有特定的身份验证数据。 其中包括：</p>

<ul>
<li>个人资料类型+ <code>#` +此提供商用户标识符（即 `Facebook 配置文件#000000001`）</li>
* 由从提供商检索的数据填充的属性（姓名、姓氏、出生日...）</ul>

## 配置文件属性

在 CAS 保护的应用程序中，通过服务票证验证，将用户信息 推至 CAS 客户端，从而推至应用程序本身。

用户标识符始终被推入 CAS 客户端。 对于用户属性，它既涉及服务器上的配置 ，也涉及验证服务票证的方式。

在 CAS 服务器端，要向 CAS 客户端推送属性，应将其配置在预期服务中：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：100，
  "描述"："样本"，
  "属性释放政策"：{
    "@class"："org.apereo.cas.服务"，
    "允许的归因"："java.util.Arraylist"，"名称"，"first_name"，"middle_name"]]
  =
}
```

## 访问策略

服务定义可以通过定义自己的访问策略和策略有条件地授权使用外部身份提供商：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：100，
  "访问战略"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务访问战略"，
    "授权验证政策"： [
      "@class"： "org. apereo. cas. 服务. 默认注册服务服务授权服务" ，
      "允许的提供者"： [java. util. Arraylist"， [脸谱'， '推特] ]]，
      "允许未定义"： 真实，
      "独家"： 真正的
    [
  ]
}
```

请注意：

- 允许的提供商列表应包含外部身份提供商名称（即客户名）。
- `允许未定义的` 标志决定在未明确定义允许的提供商的情况下是否应授予访问权限。
- `独家` 标志决定认证是否应仅限于允许的提供商，禁用其他方法，如用户名/密码等。

## 供应

默认情况下，从外部身份提供商提取并合并到 CAS 认证本金的用户配置文件不会在任何地方存储或跟踪。 CAS 确实提供了其他选项，允许 此类配置文件在 CAS 之外管理和/或配置到身份存储中，允许您可选择地将 外部/客户帐户与 CAS 使用的身份验证源中的等效帐户等联系起来。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#pac4j-delegated-authn)。

### 格罗夫提供器

可使用具有以下结构的外部 Groovy 脚本执行配置任务：

```groovy
def运行（对象[]args）{
    def本金=args[0]
    定义用户档案=args[1]
    def客户端=args[2]
    除号器=args[3]
    。。。
}
```

预计脚本不会返回值。 以下参数传递到脚本：

| 参数     | 描述                                |
| ------ | --------------------------------- |
| `主要`   | CAS 认证了包含所有属性和索赔的 `主` 。           |
| `用户档案` | 原始 `用户档案` 从外部身份提供商提取。             |
| `客户`   | `客户端` 配置，负责 CAS 与身份提供商之间的交换。      |
| `记录`   | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |

### 休息提供者

可使用预期接收以下的外部 REST 端点执行备置任务：

| 页眉       | 描述                        |
| -------- | ------------------------- |
| `主要ID`   | CAS 验证了主要标识符。             |
| `主要属性`   | CAS 验证了主要属性。              |
| `配置文件`   | 从身份提供商提取的用户配置文件标识符。       |
| `配置文件打卡` | *从身份提供商提取的用户配置文件中键入* 标识符。 |
| `简介属性`   | 从身份提供商的响应中提取的属性集合。        |
| `客户名`    | 负责 CAS 与身份提供商之间交换的客户名。    |

## SAML2 身份提供者

要了解有关将身份认证授权给 SAML2 身份提供商的更多内容，请 [查看本指南](Delegate-Authentication-SAML.html)。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下 级别：

```xml
...
<Logger name="org.pac4j" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
