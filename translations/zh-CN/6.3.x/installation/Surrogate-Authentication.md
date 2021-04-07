---
layout: 默认
title: CAS-代理身份验证
category: 验证
---

# 代理身份验证

代理身份验证（模拟），有时也称为Web</em>*sudo，是代表另一个用户进行身份验证的功能。 </p>

在这种情况下，两个参与者是：

1. 身份验证时已验证其凭据的主要admin用户。
2. 由管理员选择的代理用户，在凭据验证后，CAS将切换到该代理用户，并且该代理用户已链接到单点登录会话。

模仿的用例示例包括：

1. 代表用户登录到应用程序以执行和进行更改。
2. 代表另一个用户对应用程序进行繁琐的身份验证体验进行故障排除。

通过在WAR叠加中包括以下依赖项来启用代理身份验证：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-surrogate-webflow</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 帐户存储

可以配置以下帐户存储，并将其用于查找为特定用户授权的代理。

### 静止的

可以在CAS配置中静态定义代理帐户。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### JSON格式

与上述类似，不同之处在于可以在外部JSON文件中定义代理帐户，该外部JSON文件的路径是通过CAS配置指定的。 JSON文件的语法应与以下代码段匹配：

```json
{
    “ casuser”：[“ jsmith”，“ banderson”]，
    “ adminuser”：[“ jsmith”，“ tomhanks”]
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### LDAP

通过在WAR覆盖中包含以下依赖关系来启用对代理身份验证的LDAP支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-surrogate-authentication-ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```

代理帐户也可以从LDAP实例中检索。 可以在为LDAP中的主要用户定义的配置属性中找到此类帐户，可以根据您自己选择的正则表达式模式检查其值，以进一步缩小授权代理帐户的范围。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### CouchDb

通过在WAR覆盖中包含以下依赖项，可以启用CouchDb对代理身份验证的支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-surrogate-authentication-couchdb</artifactId>
    <version>${cas.version}</version>
</dependency>
```

代理帐户也可以从CouchDb实例中检索。 默认情况下，采用代理/主键/值对的形式。 可以多次列出被授权为代理的用户，以授权他们访问多个帐户。 此外，CouchDb代理支持可以配置为使用配置文件属性，该属性包含用户可以使用 `profileBased` 属性代理的主体列表。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### JDBC

通过在WAR覆盖中包括以下依赖项，启用了对代理身份验证的JDBC支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-surrogate-authentication-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

除了通常的数据库设置之外，此模式还需要指定两个SQL查询；一个确定资格，另一个可以检索 可以模拟给定admin用户的帐户列表。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jdbc-surrogate-accounts)。

### 休息

通过在WAR覆盖中包含以下依赖项，可以实现对代理身份验证的REST支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-surrogate-authentication-rest</artifactId>
    <version>${cas.version}</version>
</dependency>
```

| 方法   | 描述                   | 参数）        | 回复          |
| ---- | -------------------- | ---------- | ----------- |
| `得到` | 委托人是否可以作为代理帐户进行身份验证。 | `替代`， `主要` | `202`       |
| `得到` | 可以模拟的帐户主体清单。         | `主要的`      | 用户名的JSON列表。 |

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-surrogate-accounts)。

### 风俗

如果您希望设计自己的帐户存储，则可以采用以下方法：

```java
包org.apereo.cas.custom;

@Configuration（“ mySurrogateConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MySurrogateConfiguration {

    @Bean
    public SurrogateAuthenticationService surrogateAuthenticationService（）{
...
    }

}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

## 账户选择

代理用户选择可以通过以下方式进行。

### 预选

在这种情况下，代理用户身份是事先已知的，并在登录时使用特殊语法提供给CAS。 输入凭据时，应使用以下语法：

```bash
[surrogate-userid][separator][primary-userid]
```

例如，如果您是 `casuser` 并且您需要切换为 `jsmith` 作为代理用户，那么提供给CAS的凭据ID将是 `jsmith + casuser` ，其中分隔符是 `+` 并且可以通过CAS进行更改配置。 您当然需要提供自己的密码。

### 图形用户界面

在这种情况下，代理用户身份为 *而不是* 事先已知，并且您希望从预先填充的列表中选择帐户。 输入凭据时，应使用以下语法：

```bash
[separator][primary-userid]
```

例如，如果您是 `casuser` 并且需要找到可能要切换到的代理帐户，则提供给CAS的凭据ID将是 `+ casuser` ，其中分隔符是 `+` ，可以通过以下方式更改CAS配置。 您当然需要提供自己的密码。

## 会话期满

可以为模拟会话分配特定的到期策略，该策略将控制代理会话可以持续多长时间。 这意味着，一旦到期政策要求，作为模拟的一部分而建立的SSO会话将正确地消失。 建议您将到期时间保持较短（即30分钟），以避免可能的安全问题。

<div class="alert alert-info"><strong>记住</strong><p>
预期分配给模拟会话的到期策略
配给非代理会话的 <i>正常</i> 到期策略4短</i> <i> 换句话说，如果将控制单点登录会话的通常的过期策略设置为持续
2小时，则替代会话的过期时间应为小于或等于2小时的时间段。
</p></div>

## 代理属性

在成功的代理身份验证事件后，以下属性将被传递回应用程序，以检测模拟会话：

| 属性                 | 指示                     |
| ------------------ | ---------------------- |
| `surrogateEnabled` | 指示会话是否被假冒的布尔值。         |
| `代理校长`             | 管理员用户，其凭据已通过验证，并充当模仿者。 |
| `surrogateUser`    | 被模拟的代理用户。              |

## 代理访问策略

每个代理帐户存储区都能够确定模拟对象列表以强制执行授权规则。 此外，您可以在每个服务级别上定义是否授权应用程序使用代理身份验证。 仅当建立身份验证和SSO会话是模拟之一时，才激活代理访问策略。

请参阅下面的可用选项。

### 属性

确定主要用户是否被标记了足够的属性和权利以允许模拟执行。 在下面的示例中，仅当经过身份验证的主要用户携带属性 `namedName` 包含值 `Administrator``testId` 的应用程序。

示例服务定义如下：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.SurrogateRegisteredServiceAccessStrategy”，
    “ surrogateEnabled”：true，
    “ enabled”：true，
    “ ssoEnabled”：true，
    “ surrogateRequiredAttributes”：{
      “ @class” ：“ java.util.HashMap”，
      “ givenName”：[“ java.util.HashSet”，[“管理员”]]
    }
  }
}
```

### Groovy

确定是否允许主要用户通过外部Groovy脚本进行模拟。 样本服务文件如下：


```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.GroovySurrogateRegisteredServiceAccessStrategy”，
    “ groovyScript”：“文件：/etc/cas/config/surrogate.groovy”
  }
}
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。 Groovy 脚本本身可以设计为：

```groovy
import java.util。*

def对象运行（最终对象... args）{
    def主体= args[0]
    def主体[1]
    def logger = args[2]

    logger.info（“正在检查 $principal模拟身份验证。 ..“）

    //通过返回true来决定是否允许模拟...
    if（principal.equals（“ casuser”）） {
        return true
    }
    logger.warn（“不允许用户进行模拟！”）
    返回false
}
```

传递的参数如下：

| 范围                    | 描述                                 |
| --------------------- | ---------------------------------- |
| `主要的`                 | 主要/主要用户ID。                         |
| `PrincipalAttributes` | 为主要用户收集的主要属性。                      |
| `记录器`                 | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

## 代理审核

默认情况下，代理身份验证事件在审核日志中进行跟踪：

```
================================================== ===========
世界卫生组织：（主要用户： [casuser]，代理用户： [testuser]）
：https://example.app.edu的ST-1-u_R_SyXJJlENS0fBLwpecNE
操作：SERVICE_TICKET_CREATED
应用程序： CAS
时间：2017年9月11日星期一9:55:07
客户IP地址：127.0.0.1
服务器IP地址：127.0.0.1
=================== ==========================================
```

另外，失败和成功事件也可以通过SMS和/或电子邮件消息传达给相关方。 要了解更多有关可用选项，请 [参阅本指南](../notifications/SMS-Messaging-Configuration.html) 或 [本指南](../notifications/Sending-Email-Configuration.html)。

## REST协议

该功能扩展了 [CAS REST API](../protocol/REST-Protocol.html) 通信模型以替代身份验证， 允许REST凭证指定替代并代表另一个用户进行身份验证。 要为CAS REST API激活代理身份验证 ，您将需要选择以下选项之一：

- 使用以下语法设置凭据用户名的格式：

```bash
[surrogate-userid][separator][primary-userid]
```

- 传递包含代理用户标识的特殊请求标头 `X-Surrogate-Principal`
