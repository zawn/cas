---
layout: 默认
title: CAS-身份验证中断
category: Webflow管理
---

# 认证中断

CAS具有暂停和中断身份验证流以访问外部服务和资源的能力，可以查询状态和设置，以便 可以指示CAS应如何管理和控制SSO会话。 中断服务能够向用户呈现通知消息，提供重定向到外部服务的选项等。 常见用例涉及 *公告板* ，以向选定的用户显示消息和公告，然后可选地要求受众在CAS能够履行身份验证请求并建立会话之前完成某些任务。

<div class="alert alert-info"><strong>中断顺序</strong><p>
请注意，中断操作通常在主身份验证事件之后执行，这意味着CAS已识别出已身份验证的用户，并且通过扩展使该用户可用于该中断。
</p></div>

在中断流程中，CAS目前无法返回到充当中断服务的外部资源来存储，跟踪或记住用户的决定。 换句话说，我们只处理 `CRUD``R` （即读）。 如今的功能仅以只读模式处理查询状态和读取结果。 中断服务本身是必需的，并被鼓励将观众重定向到外部资源，在该处执行操作将重置中断状态，从而使CAS可以释放以后继续前进的权限，而不必再次中断身份验证流程。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-interrupt-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-interrupt)。

## 中断有效负载

最终，每个中断策略的任务都是产生包含以下设置的响应：

| 场地                         | 描述                                                |
| -------------------------- | ------------------------------------------------- |
| `信息`                       | 在屏幕上显示公告消息。                                       |
| `链接`                       | 要在屏幕上显示的链接图，其中key是链接文本，值是目的地。                     |
| `打断`                       | `true / false` 指示CAS是否应中断认证流程。                    |
| `堵塞`                       | `true / false` 指示CAS是否应完全阻止身份验证流。                 |
| `ssoEnabled`               | `是/` 指示CAS是否应允许认证而不建立SSO。                         |
| `自动重定向`                    | `true / false` 指示CAS是否应自动重定向到第一个提供的链接。            |
| `autoRedirectAfterSeconds` | 指示CAS是否应在配置的秒数后自动重定向。 默认值为 `-1`，这意味着不应执行延迟的重定向功能。 |

<div class="alert alert-info"><strong>我们可以SSO进入链接吗？</strong><p>
<code>链接</code> 的集合只是链接，并不以任何方式与CAS身份验证序列绑定，这意味着它们不会激活状态，在该序列中过渡或查看以触发CAS生成票证，执行某些操作等。 这个集合中的任何链接就是这样。只是一个链接。 如果链接指向与CAS集成的应用程序，则通过链接访问这些应用程序将再次提示用户输入凭据，特别是在尚未建立单点登录的情况下。 请记住，中断通知通常在身份验证步骤之后并且在创建任何单点登录会话之前执行。</p></div>

## 中断策略

中断查询可以通过以下方式执行：

### JSON格式

此策略可访问静态JSON资源，其中包含链接到各种中断策略的用户名映射。 此选项在开发，测试和演示期间最有用。

```json
{
  “ casuser”：{
    “ message”：“公告消息 <strong>到这里</strong>。”，
    “链接”：{
      “ Go to Location1”：“ https://www.location1.com”，
      “ Go到Location2”：“ https://www.location2.com”
    }，
    “ block”：false，
    “ ssoEnabled”：false，
    “ interrupt”：true，
    “ autoRedirect”：false，
    “ autoRedirectAfterSeconds” ：-1，
    “的数据”：{
      “field1的”：[ “VALUE1”， “值2”]，
      “FIELD2”：[ “值3”， “值4”]
    }
  }
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-interrupt-json)。

### 正则表达式属性

这种策略允许在CAS设置中定义将与属性名称和值匹配的正则表达式模式。 如果在CAS检查身份验证和主体属性的集合时产生了成功的匹配，则身份验证流 将被中断。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-interrupt-regex-attributes)。

### Groovy

该策略可用于Groovy资源，该资源的工作是在给定提供的用户名和一定数量的其他参数的情况下，动态计算是否应中断身份验证流。

该脚本可以定义为：

```groovy
import org.apereo.cas.interrupt.InterruptResponse

def run（final Object ... args）{
    def主体= args[0]
    def属性= args[1]
    def服务= args[2]
    defregisteredService = args[3]
    def requestContext = args[4]
    def logger = args[5]

...
    def块=
    def ssoEnabled =真

    返回新的InterruptResponse（“消息”，[link1：“ google.com”，link2：“ yahoo.com”]，块，ssoEnabled）
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-interrupt-groovy)。

以下参数传递到脚本：

| 范围               | 描述                                                 |
| ---------------- | -------------------------------------------------- |
| `主要的`            | 经过身份验证的主体。                                         |
| `属性`             | 类型 `映射<String, Object>` 映射，既包含主体属性又包含身份验证属性。 |
| `服务`             | 代表请求应用程序的 `服务`                                     |
| `已注册的服务`         | `RegisteredService` 对象代表注册表中的服务定义。                 |
| `requestContext` | 表示Spring Webflow `RequestContext`的对象。              |
| `记录器`            | 负责发布日志消息的对象，例如 `logger.info（...）`。                 |

### 休息

此策略可扩展到REST端点资源，该资源的工作是在给定以下参数的情况下动态计算是否应中断身份验证流：

| 范围       | 描述                  |
| -------- | ------------------- |
| `用户名`    | 经过身份验证的主体ID。        |
| `服务`     | 发出请求的应用程序的标识符（URL）。 |
| `已注册的服务` | 匹配的注册服务的标识符在注册表中找到。 |

`200`的成功操作中，期望响应主体包含JSON有效负载，其语法和结构与上述内容相同。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#authentication-interrupt-rest)。

### 风俗

如果您希望设计自己的中断策略来进行查询，则可以设计组件来确定：

```java
包org.apereo.cas.support.interrupt;

@Configuration（“ myInterruptConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyInterruptConfiguration {
    @Bean
    公共InterruptInquirer interruptInquirer（）{
...
    }

    @Bean
    公共InterruptInquiryExecutionPlanConfigurer myInterruptInquiryExecutionPlanConfigurer（）{
        返回计划> {
            plan.registerInterruptInquirer（interruptInquirer（））;
        };
    }
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

## 跳过中断

可以基于每个服务禁用中断通知。 以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“示例服务”，
  “ id”：100 ，
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ skipInterrupt”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “ values”： [“ java.util.HashSet”，[“ true”]]
    }
  }
}
```
