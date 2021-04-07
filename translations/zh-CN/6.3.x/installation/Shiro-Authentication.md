---
layout: 默认
title: CAS-Shiro认证
category: 验证
---


# Shiro认证
[Apache Shiro](http://shiro.apache.org/)处理身份验证事件。


## 成分

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-shiro-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## Shiro配置

Apache Shiro支持检索和检查经过身份验证的 主题的角色和权限。 CAS公开了一个适度的配置，以强制角色和权限作为 部分，因此，如果角色和权限不存在，则身份验证可能会失败。 尽管默认情况下这些设置是可选的，但您可以 ，以检查其存在并进行报告。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#shiro-authentication)。

根据上面的示例，样本 `shiro.ini` 需要放置在类路径上：

```ini
[main]
cacheManager = org.apache.shiro.cache.MemoryConstrainedCacheManager
securityManager.cacheManager = $cacheManager

[users]
casuser = Mellon，admin

[roles]
admin = system，admin，staff，superuser：*
```

## 记录中

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="org.apache.shiro" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
