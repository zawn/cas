---
layout: 默认
title: CAS-多因素身份验证触发器
category: 多因素身份验证
---

# 多因素身份验证触发器

以下触发器可用于激活并指示CAS导航到多因素身份验证流程。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

下面概述了多因素身份验证触发器的执行顺序：

1. 适应性强
2. 全球的
3. 选择加入请求参数/标题
4. REST端点
5. Groovy脚本
6. 每个应用程序的主要属性
7. 全局主体属性谓词
8. 全局主体属性
9. 全局认证属性
10. 应用领域
11. 石斑鱼
12. 实体ID请求参数
13. 其他

如果未找到激活和执行的适用配置，则每个触发器应正确尝试忽略身份验证请求。 还要注意，各种CAS模块都提供了自己的 *内部触发器* 并将它们注入CAS应用程序运行时，以便将协议特定的身份验证请求（例如SAML2或OpenID Connect提出的请求）转换为多因素身份验证流。

<div class="alert alert-info"><strong>服务要求</strong><p>大多数多因素身份验证触发器均要求提交给CAS的原始身份验证请求包含 <code>服务</code> 参数。 否则，将导致初始成功的身份验证尝试，在此之后，带有相关参数的后续请求将提升身份验证上下文并稍后触发多因素。 如果需要测试特定的触发器，请记住 <code>service</code> 参数，以查看该触发器的作用。</p></div>

一般而言，触发机制应完全不考虑多因素身份验证；它关心的只是以一种非常通用的方式查找链中的下一个事件。 这意味着在技术上可以组合多个触发器，每个触发器可能在身份验证流程中产生不同的事件。 如果选择了最终的候选事件，则能够支持并响应所产生事件的适当组件和模块将接管并适当路由验证流程。

## 全球的

无论单独的设置如何，均可为所有应用程序和用户触发MFA。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 每个应用

可以为在CAS服务注册表中注册的特定应用程序触发MFA。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “名称”： “ test”，
  “ multifactorPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ multifactorAuthenticationProviders”：[“ java.util.LinkedHashSet”，[“ mfa-duo”]] ，
    “ bypassEnabled”：否，
    “ forceExecution”：正确
  }
}
```

策略定义接受以下字段

| 场地                                   | 描述                                                                       |
| ------------------------------------ | ------------------------------------------------------------------------ |
| `multifactorAuthenticationProviders` | 应为此应用程序触发的多因素提供程序ID的集合。                                                  |
| `脚本`                                 | 脚本的路径，无论是外部脚本还是内部脚本，以动态触发多因素身份验证。                                        |
| `BypassEnabled`                      | 多因素身份验证是否应为 [绕过此服务的](Configuring-Multifactor-Authentication-Bypass.html) |
| `强制执行`                               | 即使不使用MFA即可满足现有身份验证上下文，是否也应强制触发多因素身份验证。                                   |

### 每个应用程序的Groovy

您可以使用Groovy脚本确定注册服务的多因素身份验证策略：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “名称”： “ test”，
  “ multifactorPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ script”：“ file：///etc/cas/config/mfa-policy.groovy “
  }
}
```

该脚本也可以直接嵌入服务定义中，如下所示：

您可以使用Groovy脚本确定注册服务的多因素身份验证策略：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “名称”： “ test”，
  “ multifactorPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ script”：“ groovy { ... }”
  }
}
```

脚本本身可以设计如下：

```groovy
def run（final Object ... args）{
    def身份验证= args[0]
    def已注册服务= args[1]
    def httpRequest = args[2]
    def service = args[3]
    def applicationContext = args[4]
    def logger = args[5]

    logger。 debug（“确定 ${registeredService} 和 ${authentication}mfa提供程序”）
    返回“ mfa-duo”
}
```

传递的参数如下：

| 范围                   | 描述                                 |
| -------------------- | ---------------------------------- |
| `已注册的服务`             | 代表注册表中相应服务定义的对象。                   |
| `验证`                 | 表示 `验证` 对象的对象。                     |
| `httpRequest`        | 表示HTTP Servlet请求的对象。               |
| `服务`                 | 表示服务请求的对象，与此http请求相关联。             |
| `applicationContext` | 表示Spring应用程序上下文的对象。                |
| `记录器`                | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

该脚本的预期结果为 `null` （如果该触发器将跳过多因素身份验证， 或应考虑激活的多因素提供者的标识符。

### 每个应用程序的Groovy（已弃用）

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>不推荐使用此功能，并计划在将来将其删除。</strong> 如果可以，请考虑使用
替代方案，以使用Groovy触发每个服务的多因素身份验证。</p>
</div>

此外，您可以使用Groovy脚本确定注册服务的多因素身份验证策略：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “名称”： “ test”，
  “ multifactorPolicy”：{
    “ @class”：“ org.apereo.cas.services.GroovyRegisteredServiceMultifactorPolicy”，
    “ groovyScript”：“ file：///etc/cas/config/mfa-policy.groovy “
  }
}
```

通过在必要时覆盖所需的操作，可以将脚本本身设计为：

```groovy
import org.apereo.cas.services。*
import java.util。*

类GroovyMultifactorPolicy扩展DefaultRegisteredServiceMultifactorPolicy {

    Set<String> getMultifactorAuthenticationProviders（）{
...
    }

    @Override
    RegisteredServiceMultifactorPolicyFailureModes getFailureMode（）{
...
    }


    字符串getPrincipalAttributeNameTrigger（）{
...
    }


    字符串getPrincipalAttributeValueToMatch（）{
...
    }


    boolean isBypassEnabled（）{
...
    }


    boolean isForceExecution（）{
...
    }
}
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。 请参阅CAS API文档以了解有关操作和预期行为的更多信息。

## 全局主体属性

可以为所有具有与以下条件之一匹配的特定属性的用户/主题触发MFA。

- 根据其值与正则表达式模式匹配的主体属性触发MFA。 **注意** 该行为仅在仅 **个MFA提供程序** 情况下适用，因为这将使CAS 知道接下来要激活的提供程序。

- 根据主要属性（其值 **恰好为** 与MFA提供者匹配）触发MFA。 如果您配置了多个提供程序，或者可以灵活地将提供程序ID分配给属性作为值，则此选项更相关。

不用说，在此步骤之前，必须已经为主体解析了属性。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 全局主体属性谓词

这是上述触发器的更通用的变体。 如果在应用程序运行时中配置了多个提供程序并且可用，并且您需要设计一种策略来动态决定应为该请求激活的提供程序，则这可能会很有用。

该决策将移交给 `谓词` 实现，该脚本的位置已告知CAS。 `test` 函数的职责是确定要触发的提供者的资格。 `真` 来确定多个提供者为合格，则将选择按提供者顺序排序的排序结果集中的第一个提供者进行响应。

Groovy脚本谓词可以这样设计：

```groovy
import org.apereo.cas.authentication。*
import java.util.function。*
import org.apereo.cas.services。*

class PredicateExample实现谓词<MultifactorAuthenticationProvider> {

    def服务
    def主体
    def提供者
    def记录器

    公共PredicateExample（服务，委托人，提供者，记录器）{
        this.service =服务
        this.principal =委托人
        this.providers =提供者
        this.logger =记录器
    }

    @Override
    布尔测试（最终MultifactorAuthenticationProvider p）{
...
    }
}
```

传递的参数如下：

| 范围    | 描述                                              |
| ----- | ----------------------------------------------- |
| `服务`  | 代表注册表中相应服务定义的对象。                                |
| `主要的` | 代表已验证主体的对象。                                     |
| `提供者` | `MultifactorAuthenticationProvider`的集合，应从中进行选择。 |
| `记录器` | 负责发布日志消息的对象，例如 `logger.info（...）`。              |


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

例如，下面的谓词示例将开始测试每个多因素身份验证提供程序，并且如果给定提供程序为 `mfa-duo` ，则只要可以到达提供程序，它将接受它作为有效触发器。

```groovy
import org.apereo.cas.authentication。*
import java.util.function。*
import org.apereo.cas.services。*

class PredicateExample实现谓词<MultifactorAuthenticationProvider> {

    def服务
    def主体
    def提供者
    def记录器

    公共PredicateExample（服务，委托人，提供者，记录器）{
        this.service =服务
        this.principal =委托人
        this.providers =提供者
        this.logger =记录器
    }

    @Override
    布尔测试（最终MultifactorAuthenticationProvider p）{
        logger.info（“测试提供程序{}”，p.getId（））
        if（p.matches（“ mfa-duo”））{
           logger.info（“提供程序{}可用。 检查资格...”，如果（p.isAvailable（this.service））{
               logger.info（“ Provider {}相匹配，则p.getId（））为
 很好！！，p.getId（））
               返回true；
           }
           logger.info（“跳过提供程序{}。 匹配失败。“，p.getId（））
           返回false; 
        }
        logger.info（”无法访问提供者{}“，p.getId（））
        返回false
    }
}
```

## 全局认证属性

*身份验证事件/元数据* 解决了与以下条件之一匹配的特定属性 所有用户/主题触发MFA：

- *身份验证属性* 触发MFA，该属性的值与正则表达式模式匹配。 **注意** 该行为仅在仅 **个MFA提供程序** 情况下适用，因为这将使CAS 知道接下来要激活的提供程序。

- *身份验证属性* 触发MFA，该属性的值 **恰好** 与MFA提供者匹配。 如果您配置了多个提供程序，或者可以灵活地将 提供程序ID分配给属性作为值，则此选项更相关。

不用说，在此步骤之前，必须已针对身份验证事件解析属性。 当基础身份验证引擎向CAS发出信号以执行凭据的其他验证时，该触发器 CAS可以将该信号捕获为属性，该属性是身份验证事件元数据的一部分，然后可以触发 其他多因素身份验证事件。

这种情况的一个示例是RADIUS服务器产生的“访问挑战响应”。

## 适应性强

可以基于被认为是非法的请求的特定性质来触发MFA。 例如， 从特定IP模式或从特定地理位置 提交的所有请求都强制通过MFA。 CAS能够使自己适应传入请求 各种属性，并将路由该流以执行MFA。 有关更多信息，请参见 [本指南](Configuring-Adaptive-Authentication.html)

## 石斑鱼

可以通过将已分配身份验证的主体 [Grouper](https://incommon.org/software/grouper/) CAS收集组，然后对照所有可用/配置的MFA提供程序进行交叉检查。 该组的比较因子 **MUST** 在CAS被定义为激活此行为 ，它可以基于组的名称，显示名称等，其中 对一个提供者ID成功匹配应当激活所选择的MFA提供商。

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您还需要确保 `classer.client.properties` 在具有以下已配置属性的类路径

```properties
grouperClient.webService.url = http://192.168.99.100:32768/grouper-ws/servicesRest
grouperClient.webService.login = banderson
grouperClient.webService.password =密码
```

## Groovy

可以根据您自己设计的groovy脚本的结果来触发MFA。 脚本的结果应确定CAS应尝试激活的MFA提供者ID。

groovy脚本的概要如下所示：

```groovy
导入java.util。*

类SampleGroovyEventResolver {
    def字符串运行（最终对象... args）{
        def服务= args[0]
        def已注册服务= args[1]
        def身份验证= args[2]
        def httpRequest = args[3]
        def记录器= args[4]

...

        返回“ mfa-duo”
    }
}
```

传递的参数如下：

| 范围            | 描述                                 |
| ------------- | ---------------------------------- |
| `服务`          | 表示请求中提供的传入服务的对象（如果有）。              |
| `已注册的服务`      | 代表注册表中相应服务定义的对象。                   |
| `验证`          | 代表已建立的身份验证事件的对象，包含主体。              |
| `httpRequest` | `HttpServletRequest`的对象。           |
| `记录器`         | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

`https://www.example.com` 并且经过身份验证的主体包含 `mail` 属性（其值包含 `email@example.org`，则以下脚本将通过Duo Security触发多因素身份验证。 。

```groovy
import java.util。*

class MyExampleScript {
    def字符串运行（最终对象... args）{
        def服务= args[0]
        def已注册服务= args[1]
        def身份验证= args[2]
        def httpRequest = args[3]
        def记录器= args[4]

        if（service.id ==“ https://www.example.com”）{
            logger.info（“评估主体属性[{}]”，authentication.principal.attributes）

            def邮件=身份验证.principal.attributes ['mail']
            如果（mail.contains（“ email@example.org”））{
                logger.info（“找到的值为[{}]的邮件属性”，mail）
                返回“ MFA-二重奏“
            }
        }
        返回null
    }
}
```

## 休息

可以基于设计的远程REST端点的结果来触发MFA。 如果配置了端点， CAS出具 `POST`，提供认证的用户名作为 `principalId` 和 `服务Id` 为服务 在请求的主体中的URL。

端点必须设计为接受/处理 `application / json`。 如果成功发送了 `200` 状态代码，则响应的主体为 ，预计将是CAS应该激活的MFA提供者ID。

## 选择加入请求参数/标题

MFA可以触发对特定认证请求，提供 的初始请求到CAS `/登录` 端点包含参数/报头 ，其指示所要求的MFA认证流。 参数/标头名称 是可配置的，但其值必须与上述可用MFA提供程序

基于请求参数触发身份验证流的示例请求为：

```bash
https：//.../cas/login？service = ...&<PARAMETER_NAME>=<MFA_PROVIDER_ID>
```

相同的策略也适用于基于请求/会话属性的触发器，特别是在设计扩展时，这些触发器通常用于API和CAS组件之间的内部通信。

## 每个应用程序的主要属性

作为混合选项，可以为在CAS服务注册表中注册的特定应用程序触发MFA，前提 ，其属性与配置的属性值匹配。 属性 值可以是任意的正则表达式模式。 请参阅下面的内容以了解如何配置MFA设置。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “名称”： “ test”，
  “ multifactorPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ multifactorAuthenticationProviders”：[“ java.util.LinkedHashSet”，[“ mfa-duo”]] ，
    “ principalAttributeNameTrigger”：“ memberOf”，
    “ principalAttributeValueToMatch”：“ faculty | allMfaMembers”
  }
}
```

## 实体ID请求参数

在将身份验证委派给CAS的情况下（最常见的情况是通过 [Shibboleth身份提供者](https://www.shibboleth.net/products/) ，实体ID可以作为 传递给CAS，以被视为CAS注册服务。 的实体ID激活多因素身份验证策略。这允许一个人基于在CAS服务注册表中 的实体ID [激活多因素身份验证策略]（＃Per Application）。 附带的好处是，实体ID可以利用所有其他CAS功能 优势，例如访问策略和授权规则，这仅仅是因为它只是CAS已知的另一种服务定义。

要了解有关集成选项的更多信息并了解如何将Shibboleth身份提供商的身份 [参见本指南](../integration/Shibboleth.html)。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-shibboleth</artifactId>
  <version>${cas.version}</version>
</dependency>
```

在 `ENTITYID` 参数可以这样进行传递：

```bash
https：//.../cas/login？service = http：//idp.example.org&entityId = the-entity-id-passed
```

## 风俗

尽管对触发器的支持似乎很广泛，但总有一些边缘用例可以让您基于一组特殊要求来触发MFA。 要了解如何设计自己的触发器，请参阅本指南</a>。</p>
