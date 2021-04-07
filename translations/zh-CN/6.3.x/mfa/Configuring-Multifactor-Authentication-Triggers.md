---
layout: 违约
title: CAS - 多因素身份验证触发器
category: 多因素认证
---

# 多因素身份验证触发器

以下触发器可用于激活和指示 CAS 导航到多因素身份验证流。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

多因素身份验证触发器的执行顺序概述如下：

1. 适应的
2. 全球
3. 选择加入请求参数/标题
4. 休息端点
5. 格罗夫脚本
6. 每个应用程序的主要属性
7. 全球主要属性谓词
8. 全球主要属性
9. 全球身份验证属性
10. 应用
11. 石斑鱼
12. 实体 ID 请求参数
13. 其他

如果未找到激活和执行的适用配置，则每个触发器应正确尝试忽略身份验证请求。 另请注意，CAS 的各种模块</em> CAS 应用程序运行时间中显示并注入了自己的 *内部触发器，以便将特定于协议的身份验证请求（如 SAML2 或 OpenID Connect 提供的请求）转换为多因素身份验证流。</p>

<div class="alert alert-info"><strong>服务要求</strong><p>大多数多因素身份验证触发器要求提交给 CAS 的原始身份验证请求包含 <code>服务</code> 参数。 如果未能做到这一点，将导致初始成功身份验证尝试，随后携带相关参数的请求将提升身份验证上下文，并在以后触发多因子。 如果您需要测试特定触发器，请记住提供 <code>服务</code> 参数，以查看触发器在起作用。</p></div>

触发机制一般应完全无视多因素认证：它所关心的就是以一种非常通用的方式在链条中找到下一个事件。 这意味着，从技术上讲，可以将多个触发器组合在一起，每个触发器可能会在身份验证流中产生不同的事件。 在活动中，在选择了最终候选事件后，能够支持和响应生成的事件的适当组件和模块将接管并适当地路由身份验证流程。

## 全球

无论单个设置如何，都可以为所有应用程序和用户触发 MFA。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 每个应用程序

对于在 CAS 服务注册表内注册的特定应用程序，可以触发 MFA。

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）"，
  "id"：100，
  "名称"："测试"，
  "多因素政策"：{
    "@class"："org.apereo.cas.服务。默认注册服务多因素政策"，
    "多因素授权提供者"："java.uledhashSet"，"mfa-duo"]，
    "旁路"：虚假的，
    的"强制执行"：真正的
  =
}
```

政策定义接受以下字段

| 田          | 描述                                                                         |
| ---------- | -------------------------------------------------------------------------- |
| `多因素授权提供者` | 应触发此应用程序的多因素提供商 ID 集。                                                      |
| `脚本`       | 向脚本（无论是外部还是内部）路径，以动态触发多因素身份验证。                                             |
| `旁路可`      | 是否应 [应绕过多因素身份验证，](Configuring-Multifactor-Authentication-Bypass.html) 此服务。 |
| `强制执行`     | 多因素身份验证是否应该强制触发，即使没有 MFA 即可满足现有身份验证上下文。                                    |

### 每个应用程序的凹槽

您可以使用 Groovy 脚本确定注册服务的多因素认证策略：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）："/。
  "id"：100，
  "名称"："测试"，
  "多因素政策"：{
    "@class"："org.apereo.cas.服务。默认注册服务多因素政策"，
    "脚本"："file:///etc/cas/config/mfa-policy.groovy"
  }

```

脚本也可以直接嵌入到服务定义中，因此：

您可以使用 Groovy 脚本确定注册服务的多因素认证策略：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）："/。
  "id"：100，
  "名称"："测试"，
  "多因素政策"：{
    "@class"："org.apereo.cas.服务.默认注册服务多因素政策"，
    "脚本"："凹凸不忙 { ... }"
  }

```

脚本本身可以设计如下：

```groovy
定义运行（最终对象。。。args）{
    定义身份验证=args[0]
    def注册服务=args[1]
    d def http要求=args[2]
    定义服务=args[3]
    dex应用程序Contex =args[4]
    def记录器=args[5]

    伐木机。debug（"确定 ${registeredService} 和 ${authentication}的mfa提供商"）
    返回"mfa-duo"
}
```

通过的参数如下：

| 参数         | 描述                                |
| ---------- | --------------------------------- |
| `注册服务`     | 表示注册表中相应服务定义的对象。                  |
| `认证`       | 表示 `身份验证` 对象的对象。                  |
| `赫特普·雷奎斯特` | 表示 HTTP 服务器请求的对象。                 |
| `服务`       | 表示服务请求的对象，与此 http 请求关联。           |
| `应用康德信`    | 表示春季应用上下文的对象。                     |
| `记录`       | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |

脚本的预期结果 `无效` 以防多因子身份验证被此触发器跳过， 或应考虑激活的多因素提供商的标识符。

### 每个应用程序的凹槽（弃用）

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能被弃用，并计划在未来删除。</strong> 如果可以，请考虑使用此处概述的
替代方案，使用 Groovy 触发每个服务的多因素身份验证。</p>
</div>

此外，您可以使用 Groovy 脚本确定注册服务的多因素认证策略：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^（https|图片）："/。
  "id"：100，
  "名称"："测试"，
  "多因素政策"：{
    "@class"："org.apereo.cas.服务。Groovy注册服务多因素政策"，
    "groovyScript"："file:///etc/cas/config/mfa-policy.groovy"
  =

```

脚本本身可以通过在必要时覆盖所需的操作来设计：

```groovy
进口组织.apereo.cas.服务.*
进口 java.util.*

类 Groovy 多因素政策扩展默认注册服务多因素政策 [
    @Override
    集<String> 获得多因素授权提供者 （） {
        ...
    }

    @Override
    注册服务多因素政策失效模式获得假模式（）{
        ...
    [

    @Override
    字符串获取主属性名称触发器（）{
        ...
    }

    @Override
    字符串获得原则属性价值匹配（）{
        ...
    }

    @Override
    布尔是通过（）{
        ...
    }

    @Override
    布尔是强制执行（）{
        ...
    •
}
```

此组件的配置有资格使用 [弹簧表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。 请参阅 CAS API 文档，以了解有关操作和预期行为的更多情况。

## 全球主要属性

MFA 可以触发所有用户/主题，其特定属性与以下条件之一相匹配。

- 根据其价值与regex模式匹配的主要属性触发MFA。 **注意** ，只有在只有配置 **个 MFA 提供商** 的情况下，这种行为才适用，因为这样 CAS 才能知道下一步激活哪个提供商。

- 根据其价值 **完全** 与 MFA 提供商匹配的主要属性触发 MFA。 如果您配置了多个提供商，或者您能够灵活地将提供商 ID 分配给属性为值，则此选项更相关。

不用说，在此步骤之前，本金的属性需要得到解决。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

## 全球主要属性谓词

这是上述触发器的更通用的变体。 在应用程序运行时间中配置和可用的多个提供商的情况下，它可能很有用，并且您需要设计一个策略来动态决定应激活的提供商的请求。

该决定将交给一个 `谓词` 实施定义在 Groovy 脚本中，其位置被传授给 CAS。 脚本中 `测试` 功能的责任是确定被触发提供商的资格。 如果谓词通过返回 `真实` 多个提供商来确定多个提供商符合资格，则将选择按提供商订单排名的排序结果集中的第一个提供商进行响应。

Groovy 脚本谓词可以这样设计：

```groovy
进口组织.apereo.cas.认证.*
进口 java.util.功能.*
进口组织.apereo.cas.服务.*

类谓词example 实现谓词<MultifactorAuthenticationProvider> •

    定义服务


    除颤器记录器

    公共预示器（服务， 委托人，提供商，记录员）{
        此。服务=服务
        此。委托人=本本
        。提供商=
        此。logger=记录器
    }

    @Override
    布尔测试（最终多因素授权提供者p）^
        ...
    •
}
```

通过的参数如下：

| 参数    | 描述                                |
| ----- | --------------------------------- |
| `服务`  | 表示注册表中相应服务定义的对象。                  |
| `主要`  | 表示经过验证的主体的对象。                     |
| `供应商` | 收集 `多因素授权提供者`，从中进行选择。             |
| `记录`  | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

例如，以下谓词示例将开始测试每个多因素身份验证提供商，如果给定提供商 `mfa-duo` 只要可以联系到提供商，它就会接受它作为有效的触发器。

```groovy
导入组织.apereo.cas.认证.*
进口 java.util.功能.*
进口组织.apereo.cas.服务.*

类谓词example 实现谓词<MultifactorAuthenticationProvider> •

    定义服务

    提供商
    除颤器

    公共谓词（服务、委托人、提供商、提供商 记录器）{
        此。服务=服务
        此。本金=本主
        此。提供商=提供商
        此。logger=记录器
    }

    @Override
    布尔测试（最终多因素Au 如果（p.matches（"mfa-duo"）{
           logger.info（"供应商"）可用，则
        logger.info（"测试提供商{}"，p.getId）
        。 检查资格。。。"，p.getId（）
           （p.是可用的（此.服务））{
               logger.info（"提供商{}匹配。 好去！"，p.getId（）
               返回真实：
           =
           logger.info（"跳过提供商{}。 匹配失败。， p. getid （）
           返回错误： 
        =
        logger.info（"无法联系到提供商{}"，p.getId）
        返回虚假
    }
}
```

## 全球身份验证属性

对于 *身份验证事件/元数据* 已解决与以下条件之一匹配的特定属性 的所有用户/主题，可以触发 MFA：

- 根据 *身份验证属性（s）* 触发 MFA，其值与 regex 模式相匹配。 **注意** ，只有在只有配置 **个 MFA 提供商** 的情况下，这种行为才适用，因为这样 CAS 才能知道下一步激活哪个提供商。

- 根据 *认证属性触发 MFA，该属性* 其价值 **完全** 与 MFA 提供商相匹配。 如果您配置了多个提供商，或者您能够灵活地将 提供商 ID 分配给属性为值，则此选项就更相关了。

不用说，在此步骤之前，身份验证事件的属性需要已解决。 当基础身份验证引擎发出 CAS 信号以执行凭据的额外验证时，此触发 通常很有用。 CAS 可以捕获此信号作为身份验证事件元数据的一部分，然后可以触发 其他多因素身份验证事件。

此方案的一个示例是 RADIUS 服务器产生的"访问挑战响应"。

## 适应的

可以根据可能被视为非法的请求的具体性质触发 MFA。 例如， 您可能希望从特定 IP 模式或特定地理位置提交的所有请求 被迫通过 MFA。 CAS 能够适应传入请求 的各种属性，并将路由流量执行 MFA。 有关详细信息，请参阅本指南</a>

。</p> 



## 石斑鱼

MFA 可以由 [集团](https://incommon.org/software/grouper/) 组触发，这些组将分配给经过验证的委托人。 组由 CAS 收集，然后对所有可用/配置的 MFA 提供商进行交叉检查。 该组的比较因子 **必须** 在 CAS 中定义以激活此行为 并且它可以基于组名、显示名称等，其中 与提供商 ID 的成功匹配应激活所选的 MFA 提供商。



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持组</artifactId>
  <version>${cas.version}</version>
</dependency>
```


您还需要确保 `` 在类路径 上提供以下配置属性的属性：



```properties
格勒克莱恩. web 服务. url = http://192.168.99.100:32768/grouper-ws/servicesRest
格克莱恩. web 服务. 登录 = 班德森
格克莱恩. web 服务. 密码 = 密码
```




## 槽的

MFA 可以根据您自己设计的凹凸不定脚本的结果触发。 脚本的结果应确定CAS应尝试激活的MFA提供商ID。

凹槽脚本的轮廓如下所示：



```groovy
导入java.util.*

级样品格罗夫事件参考+
    def字符串运行（最终对象。。。args）{
        def服务=args[0]
        def注册服务=args[1]
        def认证=args[2]
        dftp要求=args[3]
        d伐木机=args[4]

        ...

        返回"姆法杜"
    }
}
```


通过的参数如下：

| 参数         | 描述                                |
| ---------- | --------------------------------- |
| `服务`       | 表示请求中提供的传入服务的对象（如果有的话）。           |
| `注册服务`     | 表示注册表中相应服务定义的对象。                  |
| `认证`       | 表示已建立的身份验证事件的对象，包含主体。             |
| `赫特普·雷奎斯特` | 代表 `的对象`。                         |
| `记录`       | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |


例如，如果请求的应用程序 `https://www.example.com` ，并且经过验证的委托人包含 `邮件` 属性，其值包含 `email@example.org`，则以下脚本会通过 Duo Security 触发多因素身份验证。



```groovy
导入java.util.*

类我的扩展脚本{
    def字符串运行（最终对象。。。args）{
        def服务=args[0]
        def注册服务=args[1]
        def身份验证=args[2]
        def http要求=args[3]
        def记录器=args[4]

        如果（service.id=="https://www.example.com"）{
            logger.info（"评估主要属性[{]" 身份验证.委托.属性）

            def 邮件 = 身份验证.委托.属性[邮件]
            （邮件包含（"email@example.org"））
                logger.info（"已找到具有价值的邮件属性[[}]，邮件）
                返回"mfa-duo"
            }
        =
        返回无效
    =

```




## 休息

MFA 可以根据您设计的远程 REST 端点的结果触发。 如果端点被配置， 中科院应发布 `POST`，将经过验证的用户名作为 `主id` 和 `服务Id` 作为服务 网址在请求的主体。

端点必须设计为接受/处理 `申请/json`。 成功 `200` 状态代码的事件时，响应主体预计将是 CAS 应激活的 MFA 提供商 ID。



## 选择加入请求参数/标题

MFA 可以触发特定身份验证请求，前提 向 CAS 的初始请求 `/登录` 端点包含指示所需 MFA 认证流程的参数/标题 。 参数/标题名称 可配置，但其值必须与上述可用 MFA 提供商的身份验证提供商 ID 相匹配。

根据请求参数触发身份验证流的示例请求是：



```bash
https://.../cas/login?service&<PARAMETER_NAME>=<MFA_PROVIDER_ID>
```


同样的策略也适用于基于请求/会话属性的触发器，这些触发器倾向于用于 ABI 和 CAS 组件之间的内部通信，尤其是在设计扩展时。



## 每个应用程序的主要属性

作为混合选项，MFA 可以触发在 CAS 服务注册表内注册的特定应用程序，前提是 经过验证的委托人具有与配置属性值匹配的属性值的属性。 属性 值可以是一个任意的regex模式。 请参阅下文了解如何配置 MFA 设置。



```json
•
  "@class"： "org. apereo. cas. 服务. 注册服务"，
  "服务id"："^（https|图片）："，
  "id"：100，
  "名称"："测试"，
  "多因素政策"：{
    "@class"："org.apereo.cas.服务.默认服务多因素政策"，
    "多因素授权提供者"："java.util.LinkedhashSet"，"mfa-duo"]]，
    "主要属性名称触发器"："成员"，
    "主要属性价值匹配"："教师|所有会员"
  =

```




## 实体 ID 请求参数

在将身份验证委托给 CAS 的情况下，通常通过 [Shibboleth 身份提供商](https://www.shibboleth.net/products/) ，实体 ID 可以作为请求参数 传递给 CAS，以被视为 CAS 注册服务。 这允许基于注册 的实体 ID 激活多因素身份验证策略，这允许一个人根据 CAS 服务注册 注册的实体 ID "激活多因素身份验证策略"（#Per应用程序）。 作为附带利益，实体 ID 可以利用所有其他 CAS 功能 例如访问策略和授权规则，因为这只是 CAS 已知的另一个服务定义。

要了解有关集成选项的更多了解，并了解如何从 Shibboleth 身份提供商处将身份验证委托给 CAS ，请 [](../integration/Shibboleth.html)查看本指南。

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 希博莱斯</artifactId>
  <version>${cas.version}</version>
</dependency>
```


`实体id` 参数可以这样传递：



```bash
https://.../cas/login?service=http://idp.example.org&实体
```




## 习惯

虽然对触发器的支持可能看起来很广泛，但总有一个边缘使用案例会让您根据一组特殊要求触发 MFA。 要了解如何设计自己的触发器， [请参阅本指南](Configuring-Multifactor-Authentication-CustomTriggers.html)。
