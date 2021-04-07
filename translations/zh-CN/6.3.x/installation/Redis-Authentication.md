---
layout: 违约
title: CAS - 重新认证
---

# 重新验证

使用雷迪斯</a>

验证和验证凭据。</p> 

支持通过在 WAR 叠加中包括以下依赖性来启用：



```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-重新验证</artifactId>
  <version>${cas.version}</version>
</dependency>
```


用户帐户映射到 `用户名` 字段作为密钥。 用户帐户记录将包含以下字段：

| 田    | 描述                                                                      |
| ---- | ----------------------------------------------------------------------- |
| `密码` | 用户密码与适用的编码，如果有的话。                                                       |
| `地位` | `MUST_CHANGE_PASSWORD`， ``之一， `锁`， `残疾`， `过期`， MUST_CHANGE_PASSWORD 。 |
| `属性` | 用户属性建模为 `地图<String, List<Object>>`。                         |


要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#redis-authentication)查看本指南。



## 重新分配主要属性

如果主要属性需要从 雷迪斯数据库中提取，而不一定对 Redis 的凭据进行身份验证，也可以使用上述依赖性。 

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis)。
