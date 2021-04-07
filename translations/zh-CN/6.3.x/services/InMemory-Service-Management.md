---
layout: 默认
title: CAS-InMemory服务注册表
category: 服务
---

# InMemory服务注册表

这是一个内存中的服务管理工具，该工具源于通过Spring Bean连线的注册Bean。

```java
@Configuration（“ myConfiguration”）
公共类MyConfiguration {

  @Bean
  公共列表inMemoryRegisteredServices（）{
      最终列表服务=新的ArrayList<>（）;
      最后的RegexRegisteredService服务=新的RegexRegisteredService（）;
...
      services.add（service）;
      退货服务；
  }
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

给定注册的服务作为Spring bean定义注入到上下文中，您将需要查阅项目的javadocs 以了解有关CAS服务API的更多信息，以及如何将各种其他组件注入服务定义。 

<div class="alert alert-info"><strong>警告</strong><p>
此组件是 <strong>NOT</strong> ，因为它不保留数据，因此适合与服务管理Webapp一起使用。
另一方面，对于硬编码配置具有权威性且足以满足
服务注册表数据的部署，如果仅存在少量与CAS集成的应用程序，则将不使用UI，这是完全可以接受的。
</p></div>

## 自动初始化

在启动和配置允许的情况下，注册表可以根据CAS可用的默认JSON服务定义自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)