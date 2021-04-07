---
layout: 违约
title: 中科院 - JMX 集成
category: 集成
---

# JMX 集成

CAS 中的 JMX 支持为您提供了轻松、透明地将 CAS 部署集成到 JMX 基础设施中的功能。 这些功能 设计为无需将 CAS 组件耦合到弹簧或 JMX 接口和类即可工作。 CAS 组件无需注意任一 JMX，以便 利用春季 JMX 功能。 使用由 CAS 管理的资源提供的 JMX 操作可以通过爪哇的 `jconsole` 工具等完成。

以下 *包装* 组件注册到 JMX 基础设施中，并位于定义明确的 CAS 功能之上， 概念或组件，以提供远程操作或洞察力：

- 用于 CAS 服务管理设施的 JMX 管理资源
- 用于 CAS 票证注册表的 JMX 管理资源

其他包装和组件将被制定出来，并反复添加。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - jmx</artifactId>
  <version>${cas.version}</version>
</dependency>
```
