---
layout: 默认
title: CAS-无密码身份验证
category: 验证
---

# 无密码认证

无密码身份验证是CAS中的一种身份验证形式，其中密码采用 形式，该令牌在可配置的时间段后过期。 使用此策略，要求用户提供标识符（即用户名），该标识符用于查找用户记录 ，该记录包含联系人形式，例如电子邮件和电话 号码。 找到后，将通过配置的通知 策略（即电子邮件，短信等） 将令牌提供回CAS以继续进行。 

<div class="alert alert-info"><strong>没有魔术链接</strong><p>
当前，不支持魔术链接，该魔术链接将取消将令牌 
提供回CAS的任务，从而使用户能够自动进行。
将来的发行版中可能会解决此变体。</p></div>

为了成功实现此功能，需要进行配置以联系 帐户存储，这些帐户存储包含有资格进行无密码身份验证的用户记录。 同样，必须将CAS配置为管理已发行的令牌，以便在适当的数据存储区中

## 无密码的变体

密码认证还可以使用激活 [QR代码验证](QRCode-Authentication.html)， 能够让最终用户登录通过使用移动设备扫描QR码。

也可以通过 [FIDO2 WebAuthn](../mfa/FIDO2-WebAuthn-Authentication.html) 来实现无密码身份验证，该身份使用户 无需密码即可验证其身份，并使用启用FIDO2的设备登录。

## 概述

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-passwordless-webflow</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

## 帐户商店

CAS必须使用以下策略之一找到符合无密码身份验证的用户记录。 可以使用CAS设置将所有策略配置为 ，并根据配置值的存在将其激活。

### 简单的

该策略提供了一个静态的用户名映射，该用户名链接到其联系方式，例如电子邮件或电话号码。 最好将 用于测试和演示目的。 映射中的密钥被视为有资格进行身份验证的用户名，而该值可以是电子邮件 地址或电话号码，该电子邮件地址或电话号码将用于与发出令牌的用户联系。

### MongoDb

这种策略允许人们在MongoDb中定位用户记录。 预期指定的MongoDb集合将以JSON格式 `PasswordlessUserAccount` 要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-passwordless-mongo</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### LDAP

这种策略允许人们在LDAP目录中定位用户记录。 预期该记录将通过可配置的属性携带用户的电话号码 要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-passwordless-ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### JSON格式

这种策略允许人们通过JSON资源来定位用户记录，例如：

```json 
{
  “ @class”：“ java.util.LinkedHashMap”，
  “ casuser”：{
    “ @class”：“ org.apereo.cas.api.PasswordlessUserAccount”，
    “ username”：“ casuser”，
    “属性“：{
        ” @class“：” java.util.LinkedHashMap“，
        ” name“：[” java.util.ArrayList“，[” value“]]
    }，
    ” multifactorAuthenticationEligible“：” TRUE“，
    “ delegatedAuthenticationEligible”：“ TRUE”，
    “ requestPassword”：false
  }
}
```

### Groovy

这一策略允许通过Groovy脚本定位用户记录。 脚本的主体可以这样定义：

```groovy
import org.apereo.cas.api。*

def run（Object [] args）{
    def用户名= args[0]
    def logger = args[1]

    logger.info（“查找用户 $username用户记录”）

    / *
...
     找到给定用户名的记录，然后将结果返回给CAS。
     ...
    * /

    def帐户=新的PasswordlessUserAccount（）
    account.setUsername（username）
    account.setEmail（“ username@example.org”）
    account.setName（“ TestUser”）
    account.setPhone（“ 123-456-7890 “） 
    account.setAttributes（Map.of（” ...“，List.of（” ...“，” ...“）） 
    account.setMultifactorAuthenticationEligible（TriStateBoolean.FALSE）  
    account.setRequestPassword（false）
    返回帐户
}
```

### 休息

该策略允许一个设计REST端点负责查找无密码用户记录。   
成功执行将产生类似于以下内容的响应主体：

```json
{
  “ @class”：“ org.apereo.cas.api.PasswordlessUserAccount”，
  “ username”：“ casuser”，
  “ email”：“ cas@example.org”，
  “ phone”：“ 123-456 -7890“，
  ” name“：” CASUser“，        
  ” multifactorAuthenticationEligible“：” FALSE“，  
  ” delegatedAuthenticationEligible“：” FALSE“，  
  ” requestPassword“：false，
  ” attributes“：{” lastName“：[” ...“，” ...“] }
}
```

### 风俗

您还可以使用以下bean定义并通过实现 `PasswordlessUserAccountStore`来定义自己的用户帐户存储：

```java 
@Bean
public PasswordlessUserAccountStore passwordlessUserAccountStore（）{
...
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

## 代币管理

以下策略定义了CAS如何管理发行的令牌。

### 记忆

这是默认选项，其中使用具有可配置有效期限的缓存将令牌保存在内存中。 不用说，此选项 在集群CAS部署内部不适用，因为没有办法在CAS节点之间同步和复制令牌。

### JPA

这种策略允许使用关系数据库存储令牌并管理其过期策略。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-passwordless-jpa</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

### 休息

此策略允许一个设计REST端点完全负责管理令牌及其过期策略。 CAS继续生成令牌，并且端点仅充当真实令牌存储的外观，以加密方式

端点需要支持以下操作：

| HTTP方法 | 描述          | 参数）          | 回复        |
| ------ | ----------- | ------------ | --------- |
| `得到`   | 找到用户的令牌。    | `用户名`        | 响应主体中的令牌。 |
| `删除`   | 删除该用户的所有令牌。 | `用户名`        | 不适用       |
| `删除`   | 为用户删除一个令牌。  | `的用户名`， `令牌` | 不适用       |
| `邮政`   | 为用户保存令牌。    | `的用户名`， `令牌` | 不适用       |

### 风俗

您还可以使用以下bean定义并通过实现 `PasswordlessTokenRepository`来定义自己的令牌管理存储：

```java 
@Bean
public PasswordlessTokenRepository passwordlessTokenRepository（）{
...
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

### 消息传送 & 通知

可以通过文本消息，邮件等向用户通知令牌。 要了解更多有关可用选项，请 [参阅本指南](../notifications/SMS-Messaging-Configuration.html) 或 [本指南](../notifications/Sending-Email-Configuration.html)。

## 禁用无密码认证流程

可以基于每个用户有条件地禁用无密码身份验证。 检索到的无密码帐户 `requestPassword` 设置为 `true`的用户，则无密码流程（即，如上面所描述的令牌生成等）将 并被跳过，而更常用CAS身份验证流程，要求用户输入密码。 对这种行为的支持可能取决于每个单独的帐户存储实现为

## 多因素身份验证集成

无密码身份验证可以与 [CAS多因素身份验证提供程序](../mfa/Configuring-Multifactor-Authentication.html) 。 在这种情况下， 一旦CAS配置被启用以支持通过设置这种行为 或位于密码的用户帐户被认为是 *合格* 为多因素认证， CAS将允许密码认证跳过其自身 *预期的正常* 流（即，作为上面描述的带有令牌生成等内容）支持 多因素身份验证提供程序，这些提供程序可以在CAS中使用并定义。

这意味着，如果定义并激活了 [多因素身份验证提供程序](../mfa/Configuring-Multifactor-Authentication.html) ，并且在给定的无密码用户的CAS信号可用性和多因素流的资格中 [多因素触发器](../mfa/Configuring-Multifactor-Authentication-Triggers.html) 其正常的无密码身份验证流，而倾向于请求的多因素身份验证提供程序及其流程。 如果没有可用的多因素提供者 ，或者如果没有触发器要求已验证的无密码用户使用多因素身份验证，则无密码 身份验证流程将照常开始。

要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

## 委托身份验证集成

无密码身份验证可以与 [CAS委托身份验证](../integration/Delegate-Authentication.html)集成在一起。 在这种情况下， 一旦CAS配置被启用以支持经由设置或位于密码的用户帐户此行为被认为是 *资格* 为委托身份验证， CAS将允许密码认证跳过其自己的 *预期的正常* 流（即，如所描述以上，以及令牌生成等），以支持 委托身份验证，这些身份验证可能在CAS中可用并定义。

这意味着，如果 [委派的身份验证提供程序](../integration/Delegate-Authentication.html) ，则CAS将跳过 其常规的无密码身份验证流程，而转向请求的多因素身份验证提供程序及其流程。 如果没有可用的委派身份提供者 ，则无密码身份验证流程将照常开始。

使用脚本将为无密码用户选择委派身份验证身份提供程序的操作处理为 脚本可以这样定义：

```groovy
def run（Object [] args）{
    def passwordlessUser = args[0]
    def客户端=（设置）args[1]
    def httpServletRequest = args[2]
    def logger = args[3]

    logger.info（“ Testing username $passwordlessUser”）

    返回客户端[0]
}
```

传递的参数如下：

| 范围                   | 描述                                 |
| -------------------- | ---------------------------------- |
| `无密码的用户`             | `PasswordlessUserAccount`的对象。      |
| `客户`                 | 代表身份提供者配置集合的对象。                    |
| `httpServletRequest` | 代表http请求的对象。                       |
| `记录器`                | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

脚本的结果可以是 `null` 以跳过用户的委托身份验证，也可以是从传递到脚本

要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。
