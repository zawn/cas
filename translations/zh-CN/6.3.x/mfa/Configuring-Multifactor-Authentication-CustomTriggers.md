---
layout: 默认
title: CAS-配置多因素身份验证自定义触发器
category: 多因素身份验证
---

# 多因素身份验证自定义触发器

要创建自己的自定义多因素身份验证触发器，您将需要设计一个能够解析CAS身份验证链中事件的组件。 触发器（即事件解析器）的工作是检查一组条件和要求，并将事件ID提供给CAS，以指示身份验证流程中的下一步。

例如，典型的自定义触发器可能是：

- 如果客户端浏览器的IP地址与模式 `123. +``mfa-duo` 标识的MFA提供程序。

注意：

- 您实际上并没有做任何 *自定义*。 所有内置的CAS触发器尝试解决下一个事件时，其行为均相同。
- 正如您将在下面看到的，事件解决机制完全不考虑多因素身份验证；它关心的只是以一种非常通用的方式查找链中的下一个事件。 我们的自定义实现当然希望通过提供程序使下一个事件处理某种形式的MFA，但是从理论上讲，我们可以将下一个事件解析为 `hello-world`。

## 要求

您将需要对覆盖中的以下模块进行编译时访问：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-core-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

这些是默认情况下随CAS一起提供的模块，您应在构建配置中 `编译` 或 `提供的`

## 设计触发器

下面的示例演示了自定义事件解析器的合理概述：

```java
包org.apereo.cas.custom.mfa;

公共类ExampleMultifactorAuthenticationTrigger实现MultifactorAuthenticationTrigger {

    @Autowired
    私有CasConfigurationProperties casProperties;

   @Override
   public Optional<MultifactorAuthenticationProvider> isActivated（最终身份验证身份验证，
                                                                  最终RegisteredService的registeredService，
                                                                  最终HttpServletRequest的httpServletRequest，
                                                                  最终的Service的服务）{

       return Optional.empty（）;
   }
}
```

## 注册触发器

然后需要注册事件解析器触发器。 [有关更多详细信息，请参见本指南](../configuration/Configuration-Management-Extensions.html)

下面的示例演示了自定义事件解析器的合理概述：

```java
包org.apereo.cas.custom.config;

@Configuration（“ SomethingConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类SomethingConfiguration {

    @Autowired
    @Qualifier（“ initialAuthenticationAttemptWebflowEventResolver”）
    私人CasDelegatingWebflowEventResolver initialEventResolver;

    @Bean
    public MultifactorAuthenticationTrigger exampleMultifactorAuthenticationTrigger（）{
        返回新的ExampleMultifactorAuthenticationTrigger（）;
    }

    @Bean
    @RefreshScope
    公共CasWebflowEventResolver exampleMultifactorAuthenticationWebflowEventResolver（）{
        val r = new DefaultMultifactorAuthenticationProviderEventResolver（
            authenticationSystemSupport.getObject（），
            centralAuthenticationService.getObject（），
            servicesManager.getObject（），
            ticketRegistrySupport.getObject（），
            warnCookieGenerator.getObject（），
            authenticationRequestServiceSelectionStrategies.getObject（），
            multifactorAuthenticationProviderSelector.getObject（），
            exampleMultifactorAuthenticationTrigger（））;
        this.initialAuthenticationAttemptWebflowEventResolver.addDelegate（r）;
        return r;
    }
}
```

不要忘记在CAS中注册配置类。 [有关更多详细信息，请参见本指南](../configuration/Configuration-Management-Extensions.html)
