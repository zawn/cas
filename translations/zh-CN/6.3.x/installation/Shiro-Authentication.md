---
layout: 违约
title: 中科院 - 希罗认证
category: 认证
---


# 希罗认证
中科院支持处理认证事件通过 [阿帕奇希罗](http://shiro.apache.org/)。


## 组件

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套服务器支持-希罗认证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 希罗配置

阿帕奇·希罗支持检索和检查经过验证的 对象的角色和权限。 CAS 暴露了作为身份验证 一部分执行角色和权限的适度配置，因此在它们缺席的情况下，身份验证可能会失败。 默认情况下，这些设置是可选的，但您可以配置角色和/或权限， 给定的身份验证处理程序检查其存在并报告。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#shiro-authentication)。

样本 `shiro.ini` 需要根据上述示例放置在类路径上：

```ini
[main]
缓存管理器 = org.apache.shiro.cache.记忆控制管理器
安全管理器. 缓存管理器 = $cacheManager

[users]
卡塞管理员 = 梅隆，管理员

[roles]
管理员 = 系统、管理员、工作人员、超级使用者：*
```

## 伐木

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="org.apache.shiro" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```
