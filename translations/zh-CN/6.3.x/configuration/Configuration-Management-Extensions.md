---
layout: 默认
title: CAS-配置扩展
category: 配置
---

# 扩展CAS配置

作为 [Spring Boot](https://github.com/spring-projects/spring-boot) 应用程序的核心，设计和扩展CAS配置组件主要归结为 [，以下指南](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html) 在本文档中简要介绍了其中的某些方面。

## 配置组件

这是推荐的方法，用于创建其他Spring Bean，覆盖现有的bean并将您自己的自定义行为注入CAS应用程序运行时。

考虑到CAS采用了Spring Boot，大多数（如果不是全部）旧的XML配置都转换为 `@Configuration` 组件。 这些是由每个相关模块声明的类，这些类在运行时自动拾取，其作用是声明和配置Bean并将它们注册到应用程序上下文中。 另一种思考的方式是，用 `@Configuration` 装饰的组件是旧XML配置文件的松散等效项，这些文件高度组织化，其中 `<bean>` 标记被转换为标记为 `@Bean` Java方法并进行动态配置。

### 设计

要设计自己的配置类，请从以下示例中汲取灵感：

```java
包org.apereo.cas.custom.config;

@Configuration（“ SomethingConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类SomethingConfiguration {

    @Autowired
    私有CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier（“ someOtherBeanId”）
    私有SomeBean someOtherBeanId;

    @RefreshScope
    @Bean
    公共MyBean myBean（）{
        return new MyBean（）;
    }
} 
```

- `@Bean` 定义也可以用 `@RefreshScope` 标记，以在由于外部属性更改而刷新CAS上下文时变为可自动重载。
- `@Configuration` 类分配一个带有 `@Order（1984）` 的订单，这会将它们放置在一个有序队列中，等待以该顺序加载。
- 更明确地讲， `@Configuration` `@Configuration` 组件之前/之后准确地加载， `@AutoConfigureBefore` 或 `@AutoConfigureAfter` 注释。

### 登记

如何获取 `@Configuration` 组件？ 每个CAS模块按照Spring Boot</a>

声明其配置组件集：</p> 

- 创建一个 `src / main / resources / META-INF / spring.factories` 文件
- 将以下内容添加到文件中：



```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration = org.apereo.cas.custom.config.SomethingConfiguration
```




### 覆写

如果您需要重写CAS提供的bean的定义并将其完全替换为您自己的bean，该怎么办？

这是 `@Conditional` 组件可以提供帮助的地方。 CAS中的大多数组件/ bean定义都以 `@Conditional` 标记的某种形式注册，该标记指示引导过程忽略它们的创建（如果 *已经定义了具有相同ID* 这意味着您可以创建自己的配置类，对其进行注册并设计一个 `@Bean` 定义，仅是为了使上下文可以利用您的上下文，而不是默认情况下CAS附带的上下文。



### 排除项目

您可以控制自动配置类的列表，以将其排除在 `cas.properties` 文件中：



```properties
spring.autoconfigure.exclude = org.apereo.cas.custom.config.SomethingConfiguration
```




## CAS特性

CAS提供的设置</a> 的 集合都封装在 `CasConfigurationProperties` 组件内。 这是一个父类，它将整个CAS平台的所有元素组合在一起，并以非常类型安全的方式将值绑定到内部的相关字段。 [配置绑定](Configuration-Server-Management.html) 通常是通过实际配置类上的 `@EnableConfigurationProperties（CasConfigurationProperties.class）` </p>

<div class="alert alert-info"><strong>前缀表示法</strong><p>请注意，所有CAS提供的设置都专门以前缀 <code>cas</code>开头。 CAS依赖的其他框架和程序包可能会提供自己的配置命名方案。 注意区别。</p></div>

如果您希望自己设计并扩展CAS配置文件，则可以肯定地采用相同的方法，并带有 `@EnableConfigurationProperties` 注释或使用良好的 `@Value`。
