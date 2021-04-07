---
layout: 默认
title: CAS-Webflow定制
category: Webflow管理
---

# Webflow定制

CAS使用 [Spring Webflow](http://projects.spring.io/spring-webflow) 来执行 *脚本* 的登录和注销协议处理。 Spring Web Flow建立在Spring MVC的基础上，并允许实现Web应用程序的“流”。 流程封装了一系列 ，这些步骤可指导用户完成某些业务任务。 它跨越多个HTTP请求，具有状态，处理 事务数据，可重用，并且本质上可能是动态的并且可以长时间运行。 每个流可能包含许多其他设置以及以下主要元素：

- 动作：描述可执行任务并返回结果的组件
- 过渡：将流量从一种状态路由到另一种状态；过渡可能是整个流程的全局过渡。
- 视图：描述返回到客户端的表示层的组件
- 决策：有条件地路由到其他流程区域并可以做出合理决策的组件

Spring Web Flow为CAS提供了一种可插拔的体系结构，其中可以将自定义操作，视图和决策注入到 流中，以说明其他用例和流程。 请注意， <strong>，则必须对Webflow的内部和注入策略</strong>具有一定程度的理解。 本文档的目的不是描述Spring Web Flow，而是仅演示CAS如何使用该框架执行协议和业务逻辑执行的各个方面。

## Webflow会话

有关更多信息，请参见 [本指南](Webflow-Customization-Sessions.html)

## Webflow自动配置

当大多数CAS模块声明为依赖项时，它们会尝试自动配置CAS Webflow以适应其需求。 实际上，这意味着CAS采用者将不再需要手动调整CAS webflow配置，即 ，模块将自动处理所有必需的更改。 虽然这是默认的行为，有可能是 ，你可能需要手动处理所有这些变化。 为此，您将需要禁用Webflow

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow)。

<div class="alert alert-warning"><strong>Achtung，liebe Leser！</strong><p>仅在绝对必要的情况下/如果 

很小的修改或修饰。 如果不仔细地对Webflow进行大量修改，则
可能会使您的部署和将来的升级复杂化。 如果合理，请考虑为
建议，并直接对其进行维护。</p></div>

默认情况下，CAS配置为将更改热加载到Spring Webflow配置。

## 扩展Webflow

如果您想了解如何修改和扩展CAS认证的流程， [请参阅本指南](Webflow-Customization-Extensions.html)。

## 行政端点

CAS提供了以下端点：

| 终点              | 描述                                                                           |
| --------------- | ---------------------------------------------------------------------------- |
| `springWebflow` | 提供CAS身份验证Webflow的JSON表示形式。 端点可以接受 `flowId` 参数作为 `GET` 操作的一部分，以仅显示请求的流id的流主体。 |

## Webflow装饰品

学习如何动态地从外部数据源和端点获取和显示数据，并通过 [阅读本指南](Webflow-Customization-Extensions.html)将它们传递到Webflow。

## 身份验证所需的服务

默认情况下，如果初始身份验证请求未标识目标应用程序为 在某些情况下，在没有登录 到特定服务的情况下登录CAS的功能可能被认为是功能不足，因为在实践中，很少有用户和机构 准备了解，品牌化和支持充其量只是附带的用例在到CAS日志记录的 份在任何CAS-依赖的服务没有建立一个记录SSO会话的。

这样，CAS可选地允许采用者在没有显示目标应用程序 下不必费心提示凭据，而是在用户直接访问CAS而未指定服务时显示消息。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。

## 可接受的使用政策

CAS提供了允许用户在继续应用程序之前接受使用策略的能力。 有关更多信息，请参见 [本指南](Webflow-Customization-AUP.html)

## 自定义错误

有关更多信息，请参见 [本指南](Webflow-Customization-Exceptions.html)

## 自定义设置

所有Webflow组件和CAS视图都可以访问从各种配置源定义的整个CAS设置包。 `casProperties` 扩展和修改任何CAS视图或webflow组件，以访问特定设置。 请记住，此语法仅允许访问CAS拥有</em> *设置，并以其自己的前缀表示。</p>

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#custom-settings)。

## 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：

```xml
<Logger name="org.springframework.webflow" level="debug" additivity="false">
    <AppenderRef ref="casConsole"/>
    <AppenderRef ref="casFile"/>
</Logger>
```

