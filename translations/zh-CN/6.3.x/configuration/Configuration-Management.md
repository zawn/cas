---
layout: 默认
title: CAS-配置管理
category: 配置
---

# 配置管理

[Spring Cloud](https://github.com/spring-cloud/spring-cloud-config) 项目可以完全自动处理CAS的核心基础，这些配置在多个CAS节点之间 下面列出的策略 提供了一种非常灵活而强大的方法来管理生产部署的CAS配置，方法是 允许CAS采用方 **仅** 跟踪其特定部署问题所需的设置 并由其他人员处理默认的CAS配置。

以下策略可用于完全扩展CAS配置模型。

<div class="alert alert-info"><strong>是YAML还是属性？</strong><p>在以下任何使用的策略中，CAS配置都允许使用
 通常， 
都无关紧要，但是在将Unicode字符串用作属性值时，它确实很重要。 Spring使用ISO-8859-1编码加载属性
 YAML文件使用UTF-8编码加载。 如果要设置Unicode
值，请尝试使用YAML配置文件。</p></div>

## 概述

CAS允许您外部化配置，因此您可以在 不同的环境中使用同一CAS实例。 您可以使用属性文件，YAML文件，环境变量和 命令行参数来外部化配置。

CAS使用一个非常特殊的顺序，该顺序旨在合理地覆盖值。 按以下顺序考虑传递给CAS Web应用程序

1. 命令行参数，从 `-` （例如 `--server.port = 9000`）
2. 从 `SPRING_APPLICATION_JSON` 属性（嵌入在环境变量/系统属性中的内联JSON）
3. `java：comp / env`JNDI属性。
4. Java系统属性。
5. 操作系统环境变量。
6. 由配置服务器</a> 和配置文件

指示的配置文件（即 `application.properties | yml`</li> </ol>

<div class="alert alert-info"><strong>管理配置</strong><p>为了管理
CAS配置，您应该配置
到 <a href="../monitoring/Monitoring-Statistics.html">CAS管理面板的访问权限。</a></p></div>

   
   

## 配置服务器

CAS提供了一个内置配置服务器，该服务器负责引导配置 环境并在分布式系统中加载外部化设置。 您可能有一个中央 位置，可以管理所有环境中CAS节点的外部属性。 要了解有关如何管理CAS配置的更多信息，请 [本指南](Configuration-Server-Management.html)。



## 扩展CAS配置

要了解有关如何扩展和定制CAS配置的更多信息，请 [本指南](Configuration-Management-Extensions.html)。



## 自动配置策略

要查看CAS属性的完整列表，请 [本指南](Configuration-Properties.html#configuration-storage)。

请注意，在大多数情况下（即使不是全部），CAS也会尝试根据声明 和特定于功能的专用模块来自动配置上下文。 通常，这应该使部署者 免于通过XML配置文件手动按摩应用程序上下文。

这个想法是双重的：

- 通过在叠加层中声明适当的模块，声明您打算使用给定的CAS功能。
- （可选）配置适当的属性和设置。

CAS将自动负责将适当的Bean和其他组件注入运行时应用程序上下文， 取决于模块的存在和/或部署者配置的设置。

<div class="alert alert-info"><strong>没有XML</strong><p>同样，整个点
的自动配置策略是确保部署者不在的XML文件的海中游泳
配置和豆类等。 CAS应该照顾好这一切。 如果您发现一个实例，其中
，则认为该实例为“错误”，并提出功能请求。</p></div>


