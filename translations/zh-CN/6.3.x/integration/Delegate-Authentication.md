---
layout: 默认
title: CAS-委托身份验证
category: 验证
---

# 委托认证

和 [Pac4j库](https://github.com/pac4j/pac4j) 充当客户端（即服务提供商或代理），并将身份验证委派给：

* CAS服务器
* SAML2身份提供者
* OAuth2提供程序，例如Facebook，Twitter，GitHub，Google，LinkedIn等
* OpenID Connect身份提供商，例如Google，Apple
* [广告管理系统](ADFS-Integration.html)

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-pac4j-webflow</artifactId>
    <version>${cas.version}</version>
</dependency>
```

<div class="alert alert-info"><strong>笔记</strong><p>发出身份验证请求的客户端可以是任何类型（SAML，OAuth2，OpenID Connect等），并且可以使用CAS服务器支持并配置为理解的任何协议来提交身份验证请求。 这意味着您可能有一个OAuth2客户端以委派模式使用CAS在外部SAML2身份提供者，另一个CAS服务器或Facebook上进行身份验证，并且在该流程的最后收到OAuth2用户配置文件。 CAS服务器能够充当代理，在中间进行协议转换。</p></div>

## 注册提供商

身份提供者是可以对用户（例如Google，Yahoo ...）进行身份验证的服务器，而不是CAS服务器。 例如，如果要将CAS身份验证委派给Twitter，则必须 OAuth客户端，一旦向CAS教授了提供程序设置，该操作将自动为您完成。

请注意，对于每个OAuth提供程序，CAS服务器都被视为OAuth客户端，因此应在OAuth提供程序上将OAuth客户端 声明之后，由OAuth提供程序提供密钥和机密，该密钥也将在CAS配置中定义为

### 默认

可以使用设置向CAS注册用于委派身份验证的身份提供者。 CAS属性的相关列表 [查看本指南](../configuration/Configuration-Properties.html#pac4j-delegated-authn)。

### 休息

可以使用外部REST端点将用于委派身份验证的身份提供程序提供给CAS。 这允许CAS服务器将 该远程REST端点的职责是在响应主体中生成以下有效负载：

```json
{
    “callbackUrl”： “https://sso.example.org/cas/login”
    “的属性”：{
        “github.id”：” ... “
        ”github.secret“：”。 ..“，

        ” cas.loginUrl.1“：” ...“，
        ” cas.protocol.1“：” ...“
    }
}
```

上面的有效负载中 `属性` 的语法和集合 [Pac4j]（（https://pac4j.org/docs/index.html）控制。 返回的响应必须带有200状态代码。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#pac4j-delegated-authn)。

## 用户界面

所有可用的客户端都将作为可单击按钮自动显示在登录页面上。 CAS确实允许将身份验证流自动重定向到提供程序的选项，如果只有一个可用的提供程序并且已配置，则为

## 身份验证的用户ID

在成功进行委派身份验证之后，将在CAS服务器内部创建一个具有特定标识符的用户： 该用户只能根据从外部身份提供商（例如 `1234`） 接收到的技术标识符来创建，也可以作为“类型标识符”来创建。 （例如 `FacebookProfile＃1234`），这是默认设置。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#pac4j-delegated-authn)。

## 退货有效载荷

将您的CAS服务器配置为充当OAuth， CAS，OpenID（连接）或SAML客户端后（请参阅上述信息），用户将能够在OAuth / CAS / OpenID / SAML 提供程序（如Facebook）上进行身份验证而不是直接在CAS服务器内部进行身份验证。

在CAS服务器中，经过这种委托身份验证后，用户具有特定的身份验证数据。 这些包括：

* 标识符是配置文件类型+ `＃` +该提供者的用户标识符（即 `FacebookProfile＃0000000001`）
* 由提供者检索的数据填充的属性（名字，姓氏，生日…）

## 配置文件属性

在受CAS保护的应用程序中，通过服务票证验证，用户信息 被推送到CAS客户端，因此被推送到应用程序本身。

用户的标识符始终被推送到CAS客户端。 对于用户属性，它涉及 和验证服务票证的方式。

在CAS服务器端，要将属性推送到CAS客户端，应在预期的服务中对其进行配置：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “描述”：“ sample“，
  ” attributeReleasePolicy“：{
    ” @class“：” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
    ” allowedAttributes“：[” java.util.ArrayList“，[” name“，” first_name“， “ middle_name”]]
  }
}
```

## 访问策略

通过定义自己的访问策略和策略，可以有条件地授权服务定义使用外部标识提供者：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ delegatedAuthenticationPolicy”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceDelegatedAuthenticationPolicy”，
      “ allowedProviders”：[“” java .util.ArrayList“，[” Facebook“，” Twitter“]]，
      ” permitUndefined“：true，
      ” exclusive“：true
    }
  }
}
```

注意：

- 允许的提供程序列表应包含外部身份提供程序名称（即客户端名称）。
- `permitUndefined` 标志决定在未明确定义允许的提供程序的情况下是否应授予访问权限。
- `独占` 标志决定是否仅将身份验证限制于允许的提供者，并禁用其他方法，例如用户名/密码等。

## 调配

默认情况下，从外部身份提供商提取并合并到CAS 身份验证的主体中的用户配置文件不会存储或跟踪到任何地方。 CAS确实提供了其他选项，以允许 此类配置文件和/或将其配置到身份存储中，从而使您可以选择将 外部/来宾帐户与在CAS使用的身份验证源中找到的等价帐户链接起来。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#pac4j-delegated-authn)。

### Groovy预配器

可以使用具有以下结构的外部Groovy脚本来执行供应任务：

```groovy
def run（Object [] args）{
    def主体= args[0]
    def userProfile = args[1]
    def客户端= args[2]
    def logger = args[3]
...
}
```

脚本不应返回任何值。 以下参数传递到脚本：

| 范围     | 描述                                 |
| ------ | ---------------------------------- |
| `主要的`  | CAS身份验证的 `主体` 包含所有属性和声明。           |
| `用户资料` | 从外部标识提供者提取的原始 `UserProfile`        |
| `客户`   | `客户` 配置负责CAS和身份提供者之间的交换。           |
| `记录器`  | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

### REST预配器

可以使用预期会收到以下内容的外部REST端点来执行预配任务：

| 标头                    | 描述                    |
| --------------------- | --------------------- |
| `PrincipalId`         | CAS认证的主体标识符。          |
| `PrincipalAttributes` | CAS认证的主体属性。           |
| `profileId`           | 从身份提供者提取的用户配置文件的标识符。  |
| `profileTypedId`      | 从身份提供者提取的用户配置文件的 *类型* |
| `profileAttributes`   | 从身份提供者的响应中提取的属性的集合。   |
| `客户名称`                | 负责CAS和身份提供者之间交换的客户名称。 |

## SAML2身份提供者

要了解有关将身份验证委派给SAML2身份提供者的更多信息，请 [查看本指南](Delegate-Authentication-SAML.html)。

## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下 级：

```xml
...
<Logger name="org.pac4j" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
