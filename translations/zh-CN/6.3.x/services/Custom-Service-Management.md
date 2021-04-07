---
layout: 默认
title: CAS-海关服务登记处
category: 服务
---

# 海关服务登记处

如果希望设计自己的服务注册中心实现，则需要将实现注入到CAS中，如下所示：

```java
软件包org.apereo.cas.support;

@Configuration（“ myConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyConfiguration实现ServiceRegistryExecutionPlanConfigurer {

  @Bean
  @RefreshScope
  public ServiceRegistryDao serviceRegistry（）{
...
  }

  @Override
  public void configureServiceRegistry（final ServiceRegistryExecutionPlan plan）{
    plan.registerServiceRegistry（serviceRegistry（））;
  }
}
```

叠加层至少需要包括以下模块：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-core-services</artifactId>
     <version>${cas.version}</version>
</dependency>
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。
