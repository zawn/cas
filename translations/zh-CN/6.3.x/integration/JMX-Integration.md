---
layout: 默认
title: CAS-JMX集成
category: 一体化
---

# JMX整合

CAS中的JMX支持为您提供了轻松透明地将CAS部署集成到JMX基础结构中的功能。 这些功能被 ，无需将CAS组件耦合到Spring或JMX接口和类即可工作。 为了利用Spring JMX功能，CAS组件不需要知道任何一个JMX `jconsole` 工具来调用由CAS管理的资源提供的JMX操作。

以下 *包装器* 组件已注册到JMX基础结构中，并位于定义良好的CAS功能， 概念或组件之上，以提供远程操作或洞察力：

- 用于CAS服务管理工具的JMX托管资源
- 用于CAS票证注册表的JMX托管资源

额外的包装器和组件将被反复设计和添加。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-jmx</artifactId>
  <version>${cas.version}</version>
</dependency>
```
