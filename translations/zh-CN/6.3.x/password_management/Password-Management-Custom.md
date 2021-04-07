---
layout: 违约
title: CAS - 密码管理
category: 密码管理
---

# 密码管理 - 自定义

您也可以将自己的密码管理实施注入 CAS，该 CAS 本身将处理帐户更新和检索。 为此，您需要设计大致匹配以下配置类：

```java
包装 org.apereo.cas.pm;

@Configuration（"我的密码配置"）
@EnableConfigurationProperties（配置.class）
公共类MyPassword配置=

    @Bean
    公共密码管理服务密码更改服务（）{
        。。。
    •
}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。