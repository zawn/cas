---
layout: 违约
title: CAS - 记忆服务注册处
category: 服务业
---

# 记忆服务注册处

这是一个内存服务管理工具种子从注册豆通过春豆连接。

```java
@Configuration（"我的配置"）
公共类"我的配置"=

  @Bean
  记忆注册服务中的公共列表（）{
      最终列表服务=新的阵列列表<>（）;
      最终注册服务=新的注册服务（）;
      。。。
      服务。添加（服务）：
      返程服务;
  }
}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。

鉴于注册服务被注入到"春豆"定义的上下文中，您需要咨询项目的 javadocs ，以了解有关 CAS 服务 API 的更多信息，以及如何在服务定义中注入各种其他组件。 

<div class="alert alert-info"><strong>警告</strong><p>
此组件 <strong>不适合在服务管理 Webapp 中使用</strong> ，因为它不保留数据。
另一方面，对于硬编码配置具有权威性且足以用于
服务注册表数据且仅存在少数与 CAS 集成的应用程序的部署而言，UI 是完全可以接受的。
</p></div>

## 自动初始化

在启动和配置允许的情况下，注册表能够自动从 CAS 可用的默认 JSON 服务定义中初始化。 有关详细信息，请参阅本指南</a> 。</p>