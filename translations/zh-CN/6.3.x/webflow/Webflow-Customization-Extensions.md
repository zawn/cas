---
layout: 违约
title: CAS - 网络流扩展
category: 网络流管理
---

# 扩展 CAS 网络流

本指南的目的是更好地描述中科院如何利用春季网络流来适应各种认证流程。 请记住，这是 **不是** 教一个人如何春天网络流本身在内部工作。 如果你想了解更多关于春天网络流和了解行动的内部，状态，决定和范围，请 [看到本指南](http://projects.spring.io/spring-webflow/)。

CAS 默认在以下核心 Webflow 配置文件上运行：

| 流    | 位置                       |
| ---- | ------------------------ |
| `登录` | `src/主/资源/网络流/登录网络流.xml` |
| `注销` | `src/主/资源/网络流/注销网络流.xml` |

上述流配置文件为 CAS 处理登录和注销流的核心需求提供最小结构。 需要注意的是，在运行时，根据 CAS 配置和功能模块的存在，将许多其他操作和状态动态注入其中任何一个流中。 另请注意，每个功能模块本身可能会动态呈现在运行时自动拾取的其他有意见的子流配置文件。

所以，事实上，你上面看到的不一定是你得到的全部。

<div class="alert alert-warning"><strong>快乐地生活</strong><p>最好手动 <strong></strong> 叠加/修改流量配置文件。 流配置文件不被视为公共 ABI，未编译，在大多数情况下不是向后兼容的候选文件。 CAS 尝试在适当情况下动态自动实现所有 Web 流更改。 远离手动更改只会使您未来的升级更加容易。 只有在非常先进的情况下才这样做，并确保知道您在做什么！</p></div>

## 修改网络流

在适度的琐碎情况下，您可能能够 [叠加并修改](../installation/WAR-Overlay-Installation.html) 核心流配置文件以添加或覆盖所需的行为。 再次，在将这些更改引入您的部署环境之前，请仔细考虑。 避免对 Webflow 进行尽可能的临时更改，并考虑您心目中的变化如何更适合作为对 CAS 项目本身的直接贡献，以便您可以充分利用其配置， *而不是* 其维护。

要了解如何将新动作和状态引入春季网络流，请 [](http://projects.spring.io/spring-webflow/)本指南。

<div class="alert alert-info"><strong>直言不讳</strong><p>如果您发现 Webflow 自动配置策略未能按广告宣传交付的东西被破坏，请与项目社区讨论，并提交一个修补程序来更正错误或添加所需的行为作为适度的增强。 避免一次性更改，并在更改所在位置进行更改。</p></div>

在更高级的情况下，您可能需要进行深度潜水，并有条件地改变 CAS 的核心行为，您需要利用 CAS ABI 进行更改。 直接使用 CAS ABI 确实会以一定成本提供以下优势：

- 更改范围均为 Java（格罗夫、科特林、克洛朱尔等）。
- 您拥有爪哇的全部力量，能够动态和有条件地增强春季网络流。
- 您的更改都是自成一体的。
- 更改现在是 CAS ABI 的一部分，并将进行汇编。 升级时的中断更改（如果有的话）应在构建时间立即引起注意。

### 爪哇岛

这是最传统但最强大的动态更改 Webflow 内部的方法。 您将被要求编写自动配置 Webflow 的组件，并将自己注入运行中的 CAS 应用程序上下文中，以便在运行时执行。

至少，您的叠加将需要包括以下模块：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-核心网络流</artifactId>
     <version>${cas.version}</version>
</dependency>
```

#### 设计

使用以下形式设计更改 Webflow 的动态网络流配置代理：

```java
公共类某些Webflow配置器扩展了文摘CasWebflow配置器=
     公共的东西网络流配置器（流量建设者服务流量建设者服务，
                                       流定义注册流定义注册，
                                       应用程序功能应用程序Context，
                                       Cas配置项目casProperties）{
        超级（流量建设者服务，流量定义注册，应用程序文本，casProperties）;
     =

    @Override
    受保护的空虚做启动（）抛出异常{
        最终流量=超级
        。呼叫"超级"，查看您可以访问的内容并更改流量。
    •
}
```

#### 注册

然后，您需要将新设计的组件注册到 CAS 应用程序运行时间中：

```java
包组织。示例。

@Configuration（"某物配置"）
@EnableConfigurationProperties（cas配置专业.class）
公共类某物配置实现CasWebflow执行计划配置器=

    @Autowired
    私人案例配置配置项目案例：

    @Autowired
    @Qualifier（"登录流程注册"）
    私人流程定义注册登录流程定义注册：

    @Autowired
    私人应用功能应用程序Context：

    @Autowired
    私人流量建设者服务流程建设服务：

    @ConditionalOnMissingBean（名称="东西网络流配置器"）
    @Bean
    公共CasWebflow配置器的东西蜘蛛网配置器（）{
        返回新的东西Webflow配置器（流量建设者服务，
                    登录花源定义注册，应用程序Conte
    =

    @Override
    公共空文配置网络流执行计划（最终卡斯网络流执行计划）{
        计划。注册网络流配置器（某些网络流配置器）：
    =
}
```

配置类需要在 `src/主/资源/META-INF/Spring.工厂` 文件中注册：

```properties
组织. 弹簧框架. 启动. 自动配置. 启用自动配置 = 组织. 示例. 东西. 东西配置
```

有关详细信息，请参阅本指南</a>

。</p> 



### 槽的

您可以配置 CAS 以通过 Groovy 脚本更改和自动配置 Webflow。 这是不太详细的选项，您可以适度访问 CAS ABI，允许您更改 Webflow。 但是，叠加和所需依赖项的配置和脚手架更容易，因为所有配置和脚手架均由 CAS 在运行时提供。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow-groovy-auto-configuration)。

<div class="alert alert-warning"><strong>停止编码</strong><p>请记住，此处提供的专门作为 Groovy 脚本的一部分执行的 ABI 主要被视为 CAS 内部的实施。 它们可能会毫不犹豫地添加或删除，这意味着更改可能会在运行时中断您的部署和升级。 请记住，与 Java 类不同，当您构建 CAS 时，脚本不会静态编译，并且您只能在实际打开服务器并部署时观察到故障。 因此，请有充分的理由选择此选项，并确保在进入代码之前，您已经考虑过更改。</p></div>

示例 Groovy 脚本如下，旨在定位 CAS 登录流和流中预先定义的特定状态。 如果发现，当 CAS 在流程中输入该状态时，将自定义操作插入状态以执行。 虽然这是一个相当温和的例子，但请注意，脚本能够添加/删除操作、状态、过渡、添加/删除子流等。



```groovy
进口 java.util.*

进口组织. apereo. cas.*
进口组织. apereo. cas. web. *
进口组织. apereo. cas. web. 支持. *
进口组织. apereo. cas. web.flow.*

进口组织.弹簧框架.webflow.*
进口组织.弹簧框架.webflow.引擎.*
进口组织.弹簧框架.webflow.执行.*

定义对象运行（最终对象...args）{
    除网流=args[0]
    定义弹簧应用功能=args[1]
    d def记录器=args[2]

    logger.info（"配置网络流上下文。。。"）

    def登录流=webflow.getLoginFlow（）
    （webflow.包含流状态（登录流，CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM））{
        logger.info（"初始化登录表单的已找到状态"）

        dd状态=Webflow. get状态（登录流，CasWebflowConstants.STATE_ID_INIT_LOGIN_FORM， 行动状态.class）
        logger.info（"状态 ID 是 [}"，state.id）

        状态
            >
            。 作为 ${state.id}的一部分，网络流（请求通量）

            logger.info（"行动执行"）。 事情发生了。。。"）
            返回新事件（此"成功"）
        }）

        logger.info（"在 ${state.id}的参赛动作列表中添加操作"）
    }

    返回真实
}
```


通过的参数如下：

| 参数        | 描述                              |
| --------- | ------------------------------- |
| `网络流`     | 代表春季网络流 ABI 顶部的门面的对象。           |
| `春季申请康德特` | 春季应用上下文。                        |
| `记录`      | 用于发布日志消息（如 `logger.info）`的记录对象。 |
