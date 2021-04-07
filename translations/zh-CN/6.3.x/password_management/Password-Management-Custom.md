---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理-自定义

您也可以将自己的密码管理实现注入CAS，该实现本身将处理帐户更新和检索。 为此，您将需要设计一个与以下内容大致匹配的配置类：

```java
软件包org.apereo.cas.pm;

@Configuration（“ MyPasswordConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
public class MyPasswordConfiguration {

    @Bean
    public PasswordManagementService passwordChangeService（）{
...
    }
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。