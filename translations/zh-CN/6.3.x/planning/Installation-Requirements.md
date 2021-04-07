---
layout: 默认
title: CAS-安装要求
category: 安装
---

# 安装要求

根据配置组件的选择，可能会有其他要求，例如LDAP目录， 数据库和缓存基础结构。 具有明确的硬件和软件依赖性的组件的部署者来说，要求应该是显而易见的。 在任何情况下，附加要求都为 并不明显，有关组件配置的讨论应提及系统，软件，硬件和其他 要求。

## 爪哇

CAS的核心是基于Java的Web应用程序。 在部署之前，您需要先安装 [JDK](https://openjdk.java.net/projects/jdk/11/) `11` 。

<div class="alert alert-danger"><strong>Oracle JDK许可证</strong><p>
Oracle已更新提供Oracle JDK的许可条款。 针对Oracle Java SE的新Oracle技术网络许可协议与提供先前版本的JDK的许可有很大不同。 <b>在下载和使用本产品之前，请仔细阅读</b></p></div>

许可证的关键部分如下：

> 您不得：将本程序用于开发，测试，制作原型和演示应用程序之外的任何数据处理或任何商业，生产或内部业务目的。

除非您打算为此付费，否则请执行 **NOT** **改用OpenJDK构建。**

## Servlet容器

没有正式支持CAS的servlet容器，但 [Apache Tomcat](http://tomcat.apache.org/) 是最常用的 对特定servlet容器的支持取决于社区成员的专业知识。

有关更多信息，请参见 [本指南](../installation/Configuring-Servlet-Container.html)

## 制作工具

WAR叠加层为 [提供](../installation/WAR-Overlay-Installation.html) ，以提供简单，灵活的 部署解决方案。 尽管它当然需要很高的前期学习成本，但从长远来看，它可以带来许多好处。 

<div class="alert alert-info"><strong>少做</strong><p>
您 <b></b> 在安装之前需要先安装Gradle。 它是自动提供给您的。
</p></div>

## Git（可选）

虽然不是严格的要求，强烈建议您有 [的Git](https://git-scm.com/downloads) 安装用于CAS的部署， 和管理所有CAS文物，配置文件，构建脚本和设置源控制库中。

## 操作系统

尽管基于Linux的安装通常比Windows更普遍，但对操作系统没有特别的偏好。

## 互联网连接

任何基于Maven / Gradle的项目的构建阶段通常都需要Internet连接，包括 建议的WAR覆盖图。 构建过程通过搜索包含本地下载和安装的工件（大多数情况下为jar文件）的

## 硬件

社区上的传闻似乎表明，CAS部署至少应在具有8GB内存 如果日志保存在服务器本身上，则还需要足够的磁盘空间（最好是SSD）来容纳CAS生成的日志。

请记住，以上要求是 *建议*。 根据您的部署和请求量，您可能会完全满意（或多或少为 从最低限度开始，并准备根据需要调整和增强容量。
