---
layout: 违约
title: CAS - 配置管理
category: 配置
---

# 配置管理

中科院负责多个 CAS 节点的配置管理、设置和更改复制 的核心基础全部通过 [春云](https://github.com/spring-cloud/spring-cloud-config) 项目自动处理。 下面列出的策略 通过 允许 CAS 采用者 **仅** 跟踪其特定部署问题所需的设置 ，并留下所有其他由默认 CAS 配置处理的方法，从而提供了一种非常灵活和强大的方法来管理 CAS 配置。

以下策略可用于全面扩展 CAS 配置模型。

<div class="alert alert-info"><strong>雅姆还是属性？</strong><p>CAS 配置允许在以下任何策略中同时使用
YAML 和属性语法。 使用哪种语法 
通常并不重要，但当使用 Unicode 字符串作为属性值时，它确实很重要。 使用 ISO-8859-1 编码加载文件
弹簧加载属性。 YAML 文件加载了 UTF-8 编码。 如果您正在设置Unicode
值，请尝试使用YAML配置文件。</p></div>

## 概述

CAS 允许您将配置外部化，以便您可以在 不同的环境中处理相同的 CAS 实例。 您可以使用属性文件、YAML 文件、环境变量和 命令行参数来外部化配置。

CAS 使用非常特殊的顺序，旨在允许合理覆盖值。 传递给 CAS 网络应用程序 的属性按以下顺序考虑：

1. 命令行参数，从 `开始 -` （例如 `-- 服务器.port=9000`）
2. 来自 `SPRING_APPLICATION_JSON` 的属性（嵌入在环境可变/系统属性中的内联 JSON）
3. JNDI属性从 `爪哇：公司/env`。
4. 爪哇系统属性。
5. 操作系统环境变量。
6. 配置文件（即 `应用程序。属性| <a href="#configuration-server">配置服务器</a> 和配置文件指示的|毫升`）。

<div class="alert alert-info"><strong>管理配置</strong><p>为了管理 CAS 配置
，您应该将访问
配置到 <a href="../monitoring/Monitoring-Statistics.html">CAS 管理面板。</a></p></div>

## 配置服务器

CAS 提供内置配置服务器，负责在分布式系统中启动配置 环境和加载外部设置。 您可能有一个中央 位置来管理所有环境中 CAS 节点的外部属性。 要了解有关如何管理 CAS 配置的更多内容，请 [](Configuration-Server-Management.html)查看本指南。

## 扩展 CAS 配置

要了解有关如何扩展和定制 CAS 配置的更多内容，请 [查看本指南](Configuration-Management-Extensions.html)。

## 自动配置策略

要查看 CAS 属性的完整列表，请 [查看本指南](Configuration-Properties.html#configuration-storage)。

请注意，CAS 在大多数（如果不是所有情况下）将尝试根据声明 和特定功能专用模块的存在自动配置上下文。 这通常应减轻部署器 通过 XML 配置文件手动按摩应用程序上下文。

这个想法是双重的：

- 通过在覆盖中声明适当的模块来声明您对给定 CAS 功能的意图。
- 可选地配置适当的属性和设置。

CAS 将根据部署人员配置的模块和/或其设置，自动将适当的豆类和其他组件注入运行时间应用上下文中， 。

<div class="alert alert-info"><strong>无XML</strong><p>同样，
自动配置策略的全部要点是确保部署人员不会在 XML 文件
配置豆类等海洋中游泳。 中科院应该处理好这一切。 如果您发现此索赔
不持有的实例，请考虑该"错误"并提交功能请求。</p></div>


