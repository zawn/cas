---
layout: 违约
title: CAS - 配置扩展
category: 配置
---

# 扩展 CAS 配置

作为 [春靴](https://github.com/spring-projects/spring-boot) 应用的核心，CAS配置组件的设计和扩展在很大程度上归结为 [以下指南](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html) 其中某些方面在本文档中进行了简要强调。

## 配置组件

这是建议的方法，以创建额外的春豆，覆盖现有的，并注入自己的自定义行为到CAS应用程序运行时间。

鉴于 CAS 采用的弹簧启动，大多数（如果不是全部）旧的 XML 配置都转换为 `@Configuration` 组件。 这些是每个相关模块宣布的类，在运行时自动拾取，其工作是声明和配置豆类并将其注册到应用程序上下文中。 另一种思维方式是，装饰有 `@Configuration` 的组件是旧 XML 配置文件的松散等价物，这些配置文件组织严密，其中 `<bean>` 标签被翻译成带有 `@Bean` 标记并动态配置的 java 方法。

### 设计

要设计自己的配置类，请从以下示例中获得灵感：

```java
包组织. 阿佩雷奥. 卡斯. 自定义. 配置;

@Configuration（"某物配置"）
@EnableConfigurationProperties（cas配置.class）
公共类某物配置=

    @Autowired
    私人案例配置配置项目：

    @Autowired
    @Qualifier（"另一些豆子"）
    私人一些豆一些其他豆;

    @RefreshScope
    @Bean
    公共我的豆我的豆（）{
        返回新的MyBean（）;
    }
} 
```

- 当 CAS 上下文因外部属性更改而刷新时， `@Bean` 定义也可以标记为可自动重载的 `@RefreshScope` 。
- `@Configuration` 类可以分配一个订单与 `@Order（1984年）` 这将使他们在一个有序的队列等待按该顺序加载。
- 更明确的是， `@Configuration` 类也可以完全加载之前/之后的另一个 `@Configuration` 组件与 `@AutoConfigureBefore` 或 `@AutoConfigureAfter` 注释。

### 注册

如何拾取 `@Configuration` 组件？ 每个 CAS 模块根据春靴</a>

制定的准则声明其配置组件集：</p> 

- 创建 `src/主/资源/META-INF/弹簧。工厂` 文件
- 在文件中添加以下几个：



```properties
组织. 弹簧框架. 靴子. 自动配置. 启用自动配置 \ org. apereo. cas. 自定义. 配置. 东西配置
```




### 重写

如果您需要推翻 CAS 提供的豆子的定义，并完全用您自己的豆子替换它，该怎么办？

这就是 `@Conditional` 组件的帮助。 CAS 中的大多数组件/豆定义都以某种形式的 `@Conditional` 标记进行注册，这些标签指示引导过程忽略其创建，如果 *具有相同 id* 的豆定义， 则已定义。 这意味着您可以创建自己的配置类，注册它和设计一个 `@Bean` 定义，只有上下文使用您的，而不是默认情况下与CAS的船舶。



### 排除

您可以控制自动配置类的列表，将其排除在 `cas.属性` 文件中：



```properties
弹簧. 自动配置. 排除\ org. apereo. cas. 自定义. 配置. 某物配置
```




## 中科院物业

CAS 提供的设置</a> 的 集合都封装在 `Cas 配置配置` 组件中。 这是一个母类，将整个 CAS 平台的所有元素聚集在一起，以非常安全的方式将值绑定到内部的相关字段。 [配置绑定](Configuration-Server-Management.html) 通常通过实际配置类别上的 `@EnableConfigurationProperties（Cas配置.class.class）` 完成。 </p>

<div class="alert alert-info"><strong>前缀符号</strong><p>请注意，所有 CAS 提供的设置仅以前缀 <code>cas</code>开头。 CAS 所依赖的其他框架和包可能会显示它们自己的配置命名方案。 注意区别。</p></div>

如果你想设计自己的和扩展的CAS配置文件，你一定可以遵循相同的方法与 `@EnableConfigurationProperties` 注释或使用良好的ol' `@Value`。
