---
layout: 违约
title: CAS - 海关服务注册处
category: 服务业
---

# 自定义服务注册表

如果您希望自行设计服务注册表的实现，则需要将您的实施注入 CAS：

```java
包组织. 阿佩雷奥. 卡斯. 支持;

@Configuration（"我的配置"）
@EnableConfigurationProperties（cas配置专业.class）
公共类My配置实施服务注册执行计划配置器{

  @Bean
  @RefreshScope
  公共服务注册道服务注册（）{
      。。。
  •

  @Override
  公共空白配置服务注册（最终服务注册执行计划）{
    计划。注册服务注册（服务注册）：
  }
}
```

至少，您的叠加将需要包括以下模块：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-核心服务</artifactId>
     <version>${cas.version}</version>
</dependency>
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。
