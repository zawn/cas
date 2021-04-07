---
layout: 默认
title: CAS-Web流扩展
category: Webflow管理
---

# 扩展CAS Webflow

本指南的目的是更好地描述CAS如何利用Spring Webflow适应各种身份验证流。 请记住，这是 **NOT** 来教一个Spring Webflow本身在内部如何工作。 如果您想了解更多有关Spring Webflow的知识并了解动作，状态，决策和范围的内部，请参见本指南</a>

。</p> 

默认情况下，CAS在以下核心Webflow配置文件上运行：

| 流动   | 地点                                                      |
| ---- | ------------------------------------------------------- |
| `登录` | `src / main / resources / webflow / login-webflow.xml`  |
| `登出` | `src / main / resources / webflow / logout-webflow.xml` |


上面的流配置文件提供了CAS核心需要的最小结构，以处理登录和注销流。 重要的是要注意，在运行时，取决于CAS配置和功能模块的存在，许多其他动作和状态会动态注入到这些流中的任何一个中。 还要注意，每个功能模块本身可能会动态呈现其他自认为在运行时自动选择的子流配置文件。

因此，实际上，您在上面看到的不一定是您所能获得的全部。

<div class="alert alert-warning"><strong>快乐地生活</strong><p>最好 <strong>AVOID</strong> 覆盖/修改流配置文件。 流配置文件不被视为公共API，不会被编译，并且在大多数情况下都不是向后兼容的候选文件。 CAS尝试在适当的情况下动态地自动执行所有Webflow更改。 远离手动更改只会使您将来的升级更加容易。 仅在非常高级的情况下才这样做，并且一定要知道您在做什么！</p></div>

## 修改Webflow

在一般情况下，您可能能够 [覆盖和修改](../installation/WAR-Overlay-Installation.html) 核心流配置文件以添加或覆盖所需的行为。 同样，在将这些更改引入部署环境之前，请仔细考虑。 避免尽可能多地对Webflow进行临时更改，并考虑一下您所考虑的更改可能更适合作为对CAS项目本身的直接贡献，因此您可以利用其配置，而仅使用 *NOT* 对其进行维护。

要了解如何引入新的动作和状态在Spring的Webflow，请 [请参阅本指南](http://projects.spring.io/spring-webflow/)。

<div class="alert alert-info"><strong>说出来</strong><p>如果您发现Webflow自动配置策略无法按广告发布的方式发生故障，请与项目社区进行讨论，并提交可纠正错误的补丁程序或以适当的增强功能添加所需的行为。 避免一次性更改，并在更改所属位置进行更改。</p></div>

在更高级的情况下，您可能需要深入研究并有条件地更改核心CAS行为，则需要利用CAS API进行更改。 直接使用CAS API确实会带来一些好处，但要付出一定的代价：

- 所有更改都限于Java（Groovy，Kotlin，Clojure等）。
- 您具有Java的全部功能，可以动态和有条件地扩展Spring Webflow。
- 您所做的更改都是独立的。
- 现在，更改已成为CAS API的一部分，并且将进行编译。 升级中的重大更改（如有）应在构建时立即注意到。



### 爪哇

这是动态更改Webflow内部的最传统但功能最强大的方法。 系统将要求您编写可自动配置Web流程的组件，并将其自身注入正在运行的CAS应用程序上下文中，以便仅在运行时执行。

叠加层至少需要包括以下模块：



```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-core-webflow</artifactId>
     <version>${cas.version}</version>
</dependency>
```




#### 设计

设计动态Webflow配置代理，该代理使用以下形式来更改Webflow：



```java
公共类SomethingWebflowConfigurer扩展AbstractCasWebflowConfigurer {
     public SomethingWebflowConfigurer（FlowBuilderServices flowBuilderServices，
                                       FlowDefinitionRegistry flowDefinitionRegistry，
                                       ApplicationContext applicationContext，
                                       CasConfigurationProperties casProperties）{
        超级（flowBuilderServices，flowDefinitionRegistry，applicationContext，casProperties）;
     }


    受保护的void doInitialize（）引发异常{
        final Flow flow = super.getLoginFlow（）;
        //发生魔术；呼叫“超级”以查看您可以访问和更改的流程。
    }
}
```




#### 登记

然后，您需要将新设计的组件注册到CAS应用程序运行时中：



```java
包org.example.something;

@Configuration（“ somethingConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类SomethingConfiguration实现CasWebflowExecutionPlanConfigurer {

    @Autowired
    私有CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier（“ loginFlowRegistry”）
    私有FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Autowired
    私有的ApplicationContext applicationContext;

    @Autowired
    私有FlowBuilderServices flowBuilderServices；

    @ConditionalOnMissingBean（name =“ somethingWebflowConfigurer”）
    @Bean
    public CasWebflowConfigurer somethingWebflowConfigurer（）{
        返回新的SomethingWebflowConfigurer（flowBuilderServices，
                    loginFlowDefinitionRegistry，applicationContext，casProperties）;
    }

    @Override
    public void configureWebflowExecutionPlan（最终CasWebflowExecutionPlan计划）{
        plan.registerWebflowConfigurer（somethingWebflowConfigurer（））;
    }
}
```


配置类需要在 `src / main / resources / META-INF / spring.factories` 文件中向CAS注册：



```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration = org.example.something.SomethingConfiguration
```


有关更多信息，请参见 [本指南](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html)



### Groovy

您可以配置CAS以通过Groovy脚本更改和自动配置Webflow。 这是一个不太复杂的选项，您可以适度访问允许更改Webflow的CAS API。 但是，由于CAS是在运行时提供的，所以配置和脚手架以及所需依存关系的配置更加容易。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow-groovy-auto-configuration)。

<div class="alert alert-warning"><strong>停止编码</strong><p>请记住，此处提供的，特别是作为Groovy脚本的一部分执行的API通常被认为是CAS内部的实现。 可以毫不犹豫地添加或删除它们，这意味着更改可能会破坏您的部署并在运行时升级。 请记住，与Java类不同，在构建CAS时不会静态编译脚本，而实际上在打开服务器并进行部署时，您只会观察到失败。 因此，有充分的理由选择此选项，并确保在进入代码之前已经考虑了所有更改。</p></div>

随后的示例Groovy脚本旨在查找CAS登录流以及该流中预定义的特定状态。 如果找到，则将自定义动作插入该状态以在CAS进入流程中的状态后立即执行。 尽管这是一个比较适度的示例，但请注意，脚本具有添加/删除动作，状态，过渡，添加/删除子流等的功能。



```groovy
导入java.util。*

导入org.apereo.cas。*
导入org.apereo.cas.web。*
导入org.apereo.cas.web.support。*
导入org.apereo.cas.web.flow 。*

import org.springframework.webflow。*
import org.springframework.webflow.engine。*
import org.springframework.webflow.execution。*

def Object run（final Object ... args）{
    def webflow = args[0]
    def springApplicationContext = args[1]
    def logger = args[2]

    logger.info（“正在配置webflow上下文...”）

    def loginFlow = webflow.getLoginFlow（）
    如果（webflow.containsFlowState（loginFlow，CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM））{
        logger.info（“发现登录表单的

        def state = webflow.getState（loginFlow，CasWebflowConstants。 STATE_ID_INIT_LOGIN_FORM，ActionState.class）
        logger.info（“状态ID为{}”，state.id）

        state.getEntryActionList（）。add（{requestContext->
            def flowScope = requestContext.flowScope
            def httpRequest = WebUtils。 getHttpServletRequestFromExternalWebflowContext（requestContext）

            logger.info（“动作作为 ${state.id}一部分执行。 东西发生了...“）
            返回新事件（此，“成功”）
        }）

        logger.info（“将动作添加到 ${state.id}的输入动作列表中”）
    }

    返回true
}
```


传递的参数如下：

| 范围                         | 描述                                       |
| -------------------------- | ---------------------------------------- |
| `网络流`                      | 该对象表示Spring Webflow API之上的外观。            |
| `springApplicationContext` | Spring应用程序上下文。                           |
| `记录器`                      | 用于发布日志消息的Logger对象，例如 `logger.info（...）`。 |
