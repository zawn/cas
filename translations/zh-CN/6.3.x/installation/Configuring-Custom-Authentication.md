---
layout: 默认
title: CAS-设计认证策略
category: 验证
---

# 自定义身份验证策略

尽管CAS对各种系统的身份验证支持较为全面和复杂，但常见的部署用例是设计自定义身份验证方案的任务。 本文描述了在CAS中设计和注册自定义身份验证策略（即 `AuthenticationHandler`

本指南实际上是为熟悉Spring，Spring Boot和Spring Webflow的中级开发人员准备的。 这是 *NOT* 通过复制/粘贴逐字使用的教程。 相反，它是开发人员根据特殊要求扩展CAS的秘诀。

## 概述

总体任务可以归类为：

1. 设计身份验证处理程序。
2. 向CAS身份验证引擎注册身份验证处理程序。
3. 让CAS识别身份验证配置。

## 设计

第一步是为身份验证处理程序本身定义框架。 这是核心主要组件，其工作是声明对给定类型的凭证的支持，然后才尝试对其进行验证并产生成功的结果。 所有处理程序都从其扩展的核心父组件是 `AuthenticationHandler` 接口。

假设此处使用的凭据类型处理传统的用户名和密码（如 `UsernamePasswordCredential` 所示），则为自定义身份验证处理程序定义的更合适的框架可能类似于以下示例：

```java
包com.example.cas;

公共类MyAuthenticationHandler扩展AbstractUsernamePasswordAuthenticationHandler {
...
    受保护的AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal（
        最终UsernamePasswordCredential凭据，
        最终String originalPassword）{

        if（everythingLooksGood（））{
            return createHandlerResult（credential，
                this.principalFactory.createPrincipal（username），null）;
        }
        抛出新的FailedLoginException（“对不起，您失败了！”）;
    }
...
}
```

### 审查

- 身份验证处理程序具有产生完全解析的主体以及属性的能力。 如果您能够从与原始用户/本金帐户存储相同的位置检索属性， `主体` 对象必须能够在构造时在其中携带所有这些属性和声明。

- 最后一个参数 `null`实际上是警告的集合，这些警告最终被应用到身份验证链中并有条件地显示给用户。 此类警告的示例包括临近到期日期的密码状态等。

- 身份验证处理程序还可以通过引发许多特定的异常来阻止身份验证。 抛出的更常见异常是 `FailedLoginException` 以记录身份验证失败。 可能引发其他特定异常以指示帐户状态本身异常，例如 `AccountDisabledException`。

- 如有必要，也可以将其他各种组件（例如 `PrincipalNameTransformer`s， `PasswordEncoder`s）注入到我们的处理程序中，尽管为简单起见，在本文中现在略过了这些组件。

## 登记

设计处理程序后，需要在CAS中注册处理程序并将其放入身份验证引擎中。 这是通过神奇的 `@Configuration` 类实现的，在您批准后，它们会在运行时自动拾取， 的工作是了解如何动态修改应用程序上下文。

```java
包com.example.cas;

@Configuration（“ MyAuthenticationEventExecutionPlanConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyAuthenticationEventExecutionPlanConfiguration
                    实现AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    私有CasConfigurationProperties casProperties;

    @Bean
    public AuthenticationHandler myAuthenticationHandler（）{
        var handler = new MyAuthenticationHandler（）;
        / *
            通过调用各种设置方法来配置处理程序。
            请注意，您还可以完全访问已解析的CAS设置的集合。
            请注意，每个身份验证处理程序可以有选择地符合“ order”
            以及唯一名称的条件。
        * /
        返回h;
    }

    @Override
    public void configureAuthenticationExecutionPlan（final AuthenticationEventExecutionPlan plan）{
        if（feelingGoodOnAMondayMorning（））{
            plan.registerAuthenticationHandler（myAuthenticationHandler（））;
        }
    }
}
```


现在，我们已经正确创建了处理程序并将其注册到CAS身份验证机制，我们只需要确保CAS能够获取我们的特殊配置即可。 为此，请创建一个 `src / main / resources / META-INF / spring.factories` 文件，并在其中引用配置类，如下所示：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration = \
    com.example.cas.MyAuthenticationEventExecutionPlanConfiguration
```

要了解更多关于注册策略， [请参阅本指南](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html)。

在运行时，CAS将尝试自动检测所有将自身广告为 `组件和bean AuthenticationEventExecutionPlanConfigurers`。 然后调用每个检测到的组件以注册其自己的身份验证执行计划。 最终，此操作的结果将生成现成的身份验证处理程序集合，可以按定义的给定顺序（如果有）由CAS调用。
