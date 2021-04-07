---
layout: 默认
title: CAS-多因素身份验证
category: 多因素身份验证
---

# 多因素身份验证（MFA）

CAS提供了对多种多因素身份验证提供程序和选项的支持，同时允许他们自己设计。 二级身份验证因子始终在</em> 之后 *，如果请求或触发器要求，则现有身份验证会话将被要求逐步升级到所需的多因子身份验证因子。 满意的身份验证上下文也将传递回应用程序，以表示成功的多因素身份验证事件。</p>

至少，您需要回答以下问题：

- 我们使用哪个提供商进行多因素身份验证？
- 我们如何以及针对谁触发多因素身份验证？

## 受支持的提供商

CAS支持以下多因素提供程序。

| 提供者            | ID             | 指示                                                |
| -------------- | -------------- | ------------------------------------------------- |
| 二重奏安全          | `姆多多`          | [参见本指南](DuoSecurity-Authentication.html)。         |
| 身份验证器          | `制造商`          | [参见本指南](AuthyAuthenticator-Authentication.html)。  |
| 接受             | `接受`           | [参见本指南](Acceptto-Authentication.html)。            |
| YubiKey        | `MFA-Yubikey`  | [参见本指南](YubiKey-Authentication.html)。             |
| RSA / RADIUS   | `半径`           | [参见本指南](RADIUS-Authentication.html)。              |
| WiKID          | `半径`           | [参见本指南](RADIUS-Authentication.html)。              |
| Google身份验证器    | `mfa-gauth`    | [参见本指南](GoogleAuthenticator-Authentication.html)。 |
| FIDO U2F       | `mfa-u2f`      | [参见本指南](FIDO-U2F-Authentication.html)。            |
| FIDO2 WebAuthN | `mfa-webauthn` | [参见本指南](FIDO2-WebAuthn-Authentication.html)。      |
| CAS简单          | `简单的`          | [参见本指南](Simple-Multifactor-Authentication.html)。  |
| 旋转安全           | `旋转`           | [参见本指南](SwivelSecure-Authentication.html)。        |
| 风俗             | 风俗             | [参见本指南](../mfa/Custom-MFA-Authentication.html)。   |

## 扳机

可以通过许多触发器来激活多因素身份验证。 要了解更多信息， [请参阅本指南](Configuring-Multifactor-Authentication-Triggers.html)。

## 绕过规则

每个多因素提供者都配备了允许MFA绕过的选项。 要了解更多信息， [请参阅本指南](../mfa/Configuring-Multifactor-Authentication-Bypass.html)。

## 失败模式

默认情况下，身份验证策略支持失败关闭模式，这意味着，如果您尝试 提供程序并且无法访问该提供程序，则身份验证将停止并显示错误 。 当然，您可以更改此行为，以便在提供者无法响应的

支持以下故障模式：

| 场地     | 描述                                   |
| ------ | ------------------------------------ |
| `关闭`   | 如果无法访问提供程序，则身份验证将被阻止。                |
| `打开`   | 如果提供程序不可用，则继续进行身份验证，但请求的MFA不会传达给客户端。 |
| `幻影`   | 身份验证继续进行，如果提供程序不可用，则将请求的MFA传达给客户端。   |
| `没有任何` | 根本不要联系提供商以检查是否有空。 假设提供者可用。           |

### 故障模式选择

万一无法访问请求的提供者，CAS将咨询当前配置，以确定如何进行。  
可以在以下位置配置故障模式，CAS将按以下顺序使用第一个定义的故障模式：

- 注册服务多因素身份验证策略
- 多因素身份验证提供程序配置
- 全局多因素身份验证配置

如果没有遇到可操作的失败模式，将向用户显示一条通用的“身份验证失败”消息。

### 注册服务的失败模式

设置为“ multifactorPolicy”的一部分。  此位置将覆盖在任何其他位置设置的故障模式。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “multifactorPolicy”： {
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ multifactorAuthenticationProviders”：[“ java.util.LinkedHashSet”，[“ mfa-duo”]]，
    “ failureMode”：“关闭”
  }
}
```

### 多因素身份验证提供程序的故障模式

每个定义的多因素身份验证提供程序都可以设置自己的故障模式策略。 在此位置设置的故障模式将覆盖全局故障模式，但会遵循已注册服务设置的任何故障模式。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties-Common.html#multifactor-authentication-providers)。

### 全局故障模式

可以通过CAS属性全局指定默认故障模式，并且在提供商或注册服务中未设置故障模式的情况下将使用默认故障模式。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 多供应商选择

如果为一个多因素身份验证事务确定了多个多因素身份验证提供者，则默认情况下，CAS将尝试根据提供者的排名对提供者集合进行排序，并选择优先级最高的一个。 如果定义了多个触发器，每个触发器决定一个不同的多因素身份验证提供程序，或者同一提供程序实例被配置了许多实例多次，则可能会出现此用例。

使用Groovy脚本策略还可以更动态地执行提供者选择。 以下示例应概述如何根据Groovy脚本选择多因素提供程序：

```groovy
导入java.util。*

类SampleGroovyProviderSelection {
    def字符串运行（最终对象... args）{
        def服务= args[0]
        def主体= args[1]
        def providerCollection = args[2]
        def logger = args[3]
        .. 。
        返回“ mfa-duo”
    }
}
```

传递的参数如下：

| 范围                   | 描述                                 |
| -------------------- | ---------------------------------- |
| `服务`                 | 表示请求中提供的传入服务的对象（如果有）。              |
| `主要的`                | 代表已认证主体的对象及其属性。                    |
| `providerCollection` | 代表有资格进行交易的候选多因素提供者集合的对象。           |
| `记录器`                | 负责发布日志消息的对象，例如 `logger.info（...）`。 |


要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 排名提供者

有时，当需要逐步身份验证时，CAS需要确定正确的提供者。 暂时考虑一下CAS 已经与提供者/没有提供者建立了SSO会话，并且已经达到认证级别。 另一个传入 请求尝试行使与可能不同不同，经常相互竞争的认证要求，即SSO会话 从CAS已经建立认证级别。 具体而言，示例可能是：

- CAS已经实现了SSO会话，但是现在单独的请求要求使用DuoSecurity进行逐步身份验证。
- CAS已经实现了DuoSecurity满足的身份验证级别的SSO会话，但是现在单独的请求要求使用YubiKey进行逐步身份验证。

在某些情况下，CAS将尝试对身份验证级别进行排名，并将它们相互比较。 如果CAS已经达到了 ，则将不执行升压身份验证。 如果相反，则CAS将 将身份验证流路由到所需的身份验证级别，并且一旦成功，将以 身份验证级别来调整SSO会话。

身份验证方法的排名是通过CAS设置中每个提供商的特定属性来完成的。 请注意， 越高，它保留的安全等级就越高。 权重值较大的供应商会胜过 而权重值较小的供应商会被覆盖。

## 供应商选择菜单

如果一个以上的多因素身份验证提供程序符合身份验证请求的条件，则可以将CAS配置为 ，向用户呈现所有选择，从而允许他们选择在给定时间最有意义的提供程序。 该方法 是对提供者进行排名的替代策略。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 可信设备/浏览器

作为任何多因素身份验证流程的一部分，CAS都可以本地提供受信任的设备/浏览器功能。 尽管某些提供程序也倾向于支持此功能，但是现在将这种行为放到CAS中，直接为您提供对设备/浏览器检查方式的精确控制，如何记住后续请求的决策，以及如何允许委托管理这些受信任者决定管理员和最终用户。

[有关更多信息，请参阅本指南](Multifactor-TrustedDevice-Authentication.html)。

## 2FA vs.MFA

部署时，CAS中的多因素身份验证主要以两因素身份验证的形式出现。 但是，该框架的设计方式允许将其他提供程序的其他链接附加到现有的身份验证体验中。 如果您需要将多个因素一个接一个地串联在一起，则可能需要调整和扩展现有的身份验证工作流程才能交付用例。

## 设定值

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。
