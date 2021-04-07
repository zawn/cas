---
layout: 默认
title: CAS-自定义多因素身份验证
category: 多因素身份验证
---

# 自定义多因素身份验证

要创建自己的自定义多因素身份验证提供程序，您将需要设计主要在一个唯一标识符下将自定义身份验证流注册到CAS webflow引擎中的组件。 稍后，您还需要考虑可以触发您的自定义多因素身份验证提供程序 [](Configuring-Multifactor-Authentication-Triggers.html)。

## 提供商ID

每个多因素提供者都分配有一个唯一的标识符，该标识符通常被映射或使其等于基础网络流。 唯一标识符可以是您选择的任意字符串，只要它保持区分和明智即可，这取决于用例，可以在其他系统和其他应用程序中用作触发器。

出于本指南的目的，我们选择 `mfa-custom` 作为我们的提供者ID。

## Webflow XML配置

流配置文件需要放置在 `src / main / resources / webflow / mfa-custom` 目录中，命名为 `mfa-custom.xml` 其轮廓如下所示：

```xml
<吗？xml版本=“ 1.0”编码=“ UTF-8”？>
<flow xmlns="http://www.springframework.org/schema/webflow"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/webflow http://www.springframework.org/schema/webflow/spring-webflow.xsd">

    <- 
        定义状态和动作... 
    ->
    <end-state id="success" />
</flow>
```

## 注册Webflow配置

定制提供程序本身是其自己的独立Web流，然后将其注册到主要身份验证流。

```java
公共类CustomAuthenticatorWebflowConfigurer扩展AbstractCasMultifactorWebflowConfigurer {
    public static final String MFA_EVENT_ID =“ mfa-custom”;

    / *
        基于父类定义适当的构造函数
        public CustomAuthenticatorWebflowConfigurer（...） {
        }
    * /  


    protected void doInitialize（）throws Exception {
        registerMultifactorProviderAuthenticationWebflow（getLoginFlow（），
                MFA_EVENT_ID，yourCustomMfaFlowDefinitionRegistry）;
    }
}
```

## 设计提供者

CAS中的多因素身份验证提供程序以 `MultifactorAuthenticationProvider` 实例的形式表示。 提供者的概述在下面简要显示，其大部分行为都被删除，以支持默认设置。

```java
公共类CustomMultifactorAuthenticationProvider扩展AbstractMultifactorAuthenticationProvider {
    private static final long serialVersionUID = 4789727148634156909L;
}
```

## 注册提供者

定制Webflow配置需要向CAS注册。 配置注册的概要示例如下：

```java
包org.example.cas;

@Configuration（“ CustomAuthenticatorSubsystemConfiguration”）
公共类CustomAuthenticatorSubsystemConfiguration {
...
    @Bean
    public FlowDefinitionRegistry customFlowRegistry（）{
        var builder = new FlowDefinitionRegistryBuilder（applicationContext，flowBuilderServices）;
        builder.setBasePath（“ classpath *：/ webflow”）;
        builder.addFlowLocationPattern（“ / mfa-custom / *-webflow.xml”）;
        返回builder.build（）;
    }

    @Bean
    public MultifactorAuthenticationProvider customAuthenticationProvider（）{
        var p = new CustomMultifactorAuthenticationProvider（）;
        p.setId（“ mfa-custom”）;
        返回p;
    }

    @Bean
    @DependsOn（“ defaultWebflowConfigurer”）
    公共CasWebflowConfigurer customWebflowConfigurer（）{
        返回新的CustomAuthenticatorWebflowConfigurer（...）;
    } 

    @Bean
    公共CasWebflowExecutionPlanConfigurer customWebflowExecutionPlanConfigurer（）{
        返回计划> plan.registerWebflowConfigurer（customWebflowConfigurer（））;
    }
...
}
```

不要忘记在CAS中注册配置类。 [有关更多详细信息，请参见本指南](../configuration/Configuration-Management-Extensions.html)

## 扳机

[受支持的选项](Configuring-Multifactor-Authentication-Triggers.html)任何一个来触发自定义身份验证Webflow。
