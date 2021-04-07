---
layout: 违约
title: CAS - 配置多因素身份验证自定义触发器
category: 多因素认证
---

# 多因素身份验证自定义触发器

要创建自己的自定义多因素身份验证触发器，您需要设计能够解决 CAS 身份验证链中事件的组件。 触发器（即事件解决器）的工作是检查一组条件和要求，并向 CAS 提供事件 ID，以指示身份验证流程的下一步。

例如，典型的自定义触发器可能是：

- 激活由 `mfa-duo` 识别的 MFA 提供商，如果客户端浏览器的 IP 地址与模式匹配 `123.+`。

请注意：

- 你真的没有做任何事情 *自定义* 本身。 所有内置的 CAS 触发器在尝试解决下一个事件时都以完全相同的方式运行。
- 如下所示，事件分辨率机制完全无视多因素认证：它所关心的就是以一种非常通用的方式在链条中找到下一个事件。 当然，我们的定制实施希望通过提供商与某种形式的 MFA 进行下一个事件处理，但在理论上，我们本可以解决下一个事件， `你好世界`。

## 要求

您需要对覆盖中的以下模块进行编译时间访问：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-核心网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

这些模块默认情况下与 CAS 一起发货，您应在构建配置中</code> 范围内 `编译` 或 `来标记它们。</p>

<h2 spaces-before="0">设计触发器</h2>

<p spaces-before="0">以下示例显示了自定义事件解决器的合理轮廓：</p>

<pre><code class="java">包组织. 阿佩雷奥. 卡斯. 自定义. mfa;

公共类示例多因素验证程序实现多因素验证触发器 =

    @Autowired
    私人案例配置配置项目 casProperties：

   @Override
   公共可选<MultifactorAuthenticationProvider> 已激活（最终身份验证、
                                                                  最终注册服务注册服务、
                                                                  最终 Http 服务要求 http 服务要求、
                                                                  最终服务服务） =

       返回可选.空（）;
   =

`</pre>

## 注册触发器

然后需要注册事件解析器触发器。 [请参阅本指南](../configuration/Configuration-Management-Extensions.html) 了解更多详细信息。

以下示例显示了自定义事件解决器的合理轮廓：

```java
包组织. 阿佩雷奥. 卡斯. 自定义. 配置;

@Configuration("SomethingConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class SomethingConfiguration {

    @Autowired
    @Qualifier("initialAuthenticationAttemptWebflowEventResolver")
    private CasDelegatingWebflowEventResolver initialEventResolver;

    @Bean
    public MultifactorAuthenticationTrigger exampleMultifactorAuthenticationTrigger() {
        return new ExampleMultifactorAuthenticationTrigger();
    }

    @Bean
    @RefreshScope
    public CasWebflowEventResolver exampleMultifactorAuthenticationWebflowEventResolver() {
        val r = new DefaultMultifactorAuthenticationProviderEventResolver(
            authenticationSystemSupport.getObject(),
            centralAuthenticationService.getObject(),
            servicesManager.getObject(),
            ticketRegistrySupport.getObject(),
            warnCookieGenerator.getObject(),
            authenticationRequestServiceSelectionStrategies.getObject(),
            multifactorAuthenticationProviderSelector.getObject(),
            exampleMultifactorAuthenticationTrigger());
        this.initialAuthenticationAttemptWebflowEventResolver.addDelegate(r);
        return r;
    }
}
```

不要忘记在 CAS 注册配置类。 [请参阅本指南](../configuration/Configuration-Management-Extensions.html) 了解更多详细信息。
