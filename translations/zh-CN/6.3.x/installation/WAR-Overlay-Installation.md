---
layout: 默认
title: CAS-覆盖式安装
category: 安装
---

# WAR叠加安装

CAS安装是一个从根本上面向源的过程，我们建议您使用WAR overlay（1） 项目来组织自定义设置，例如组件配置和UI设计。 WAR覆盖构建的输出是一个 `cas.war` 文件，可以将其部署 到servlet容器（如 [Apache Tomcat](Configuring-Servlet-Container.html)。

## 要求

[请参阅本指南](../planning/Installation-Requirements.html) 了解更多信息。

## 什么是WAR重叠广告？

覆盖是一种与重复代码和/或资源作斗争的策略。 无需下载CAS代码库并从源代码进行构建，而是使用 叠加层，您可以下载项目本身提供的预构建的香草CAS Web应用程序服务器，并将特定行为覆盖/插入其中。 在构建时，构建安装过程将尝试首先下载提供的 二进制工件。 然后，该工具将在同一项目目录中找到可用的配置文件和设置 ，并将这些文件和设置合并到下载的工件中，以生成 个有益健康的存档（即 `cas.war`）。 覆盖的工件可能包括 资源，java类，图像，CSS和javascript文件。 为了使合并 进程成功执行，被覆盖的工件的位置和名称 在当地必须 **EXACTLY** 的比赛，通过该项目提供的的 最初下载的存档中。 `src / main / java` 文件夹中的Java代码 `src / main / resources` 资源将最终位于 `WEB-INF ` 文件夹中，并由 `WEB-INF \ lib`内的jar文件中，使用类加载器代替具有相同名称的资源。

毋庸置疑，尽管前期准备时间可能会有些复杂，但这种方法有很多优势：

1. 无需从源代码下载/构建。
2. 在大多数情况下，只需调整构建脚本以下载较新的CAS版本，升级就变得非常容易。
3. 而不是托管整个软件源代码，作为部署者，您 **仅** 将保留自己的本地自定义项，这使更改跟踪变得更加容易。
4. 同样，仅因为仅管理相关的更改（而不是整个软件），所以跟踪源代码控制存储库中的更改非常轻巧。

### 管理叠加

CAS的大部分（如果不是全部）方面都可以通过添加，删除或修改覆盖图中的文件来控制；通过添加将CAS API实现为Java源文件或依赖项引用的第三方组件 CAS的行为也是可能的，而且的确是常见的。

覆盖的处理过程可以归纳为以下步骤：

- 开始并构建提供的基本香草构建/部署。
- 从生成的构建中识别需要更改的工件。 这些工件通常由 `build` 目录中的build产生。 使用gradle `explodeWar` 任务。
- 将已标识的工件从上面标识的目录复制到 `src / main / resources` 目录。
1. 如果 `src / main / resources` 目录不存在，则创建它们。
2. 复制的路径和文件名 **必须与它们的构建副本完全匹配** ，否则更改将不会生效。 请参阅下表以了解如何将文件夹和文件从内部版本映射到 `src`。
- 更改后，请重新构建并重复该过程，以尽可能多地进行。
- 在构建的二进制工件中仔细检查您的更改，以确保覆盖过程正在运行。

<div class="alert alert-warning"><strong>准确无误</strong><p>不要复制构建产生的所有内容。 尝试将更改和自定义项的 
，仅获取您实际需要的内容。 确保部署环境保持整洁精确，否则将带来可怕的升级问题和痛苦的麻烦。</p></div>

## CAS WAR叠加

提供以下描述的CAS WAR叠加项目供参考和研究。

### CAS覆盖初始化

Apereo CAS Initializr是Apereo CAS生态系统中的一个相对较新的功能，它使您可以 迅速地部署CAS WAR Overlay项目，而您只需要快速启动即可。

要了解有关initializr的更多信息，请 [本指南](WAR-Overlay-Initializr.html)。

### CAS覆盖模板

您可以下载或克隆以下存储库，以开始使用CAS覆盖模板。

<div class="alert alert-info"><strong>回顾分行！</strong><p>以下存储库将您引向其 <code>主目录</code> 分支。
您应该始终确保您所在的分支与您要配置和部署的CAS版本匹配。 <code>master</code>
分支通常指向CAS服务器的最新稳定版本。 检查构建配置，并且如果不恰当的，
使用 <code>GIT中分支-a</code> 以查看可用的分支，然后 <code>GIT中结帐 [branch-name]</code> 至必要时进行切换。</p></div>

| 项目                                                          | 建立目录                          | 源目录                      |
| ----------------------------------------------------------- | ----------------------------- | ------------------------ |
| [CAS WAR叠加](https://github.com/apereo/cas-overlay-template) | `cas / build / cas-resources` | `src / main / resources` |

在 `CAS /建造/ CAS-资源` 文件解压缩 从 `cas.war！WEB-INF \ lib中\ CAS-服务器的web应用-资源-<version>的.jar` 经由 `gradle这个explodeWar` 中的覆盖。

构建覆盖的项目，你需要复制的目录和 文件 *，你需要自定义* 到源目录中生成目录。

WAR叠加层还提供了其他任务，以便在重新组装之前先爆炸二进制工件。 您可能需要自己手动执行该步骤，以了解需要将哪些文件/目录复制到源目录中。

注意：请勿在上述构建目录中进行 **NOT** 每次构建时，变更集将被清除，并将 设置为默认值。 将重叠的组件放在源目录 和/或其他指示的位置内，以免引起意外。

## CAS配置服务器覆盖

有关更多详细信息， [Maven WAR叠加层](https://github.com/apereo/cas-configserver-overlay)

要了解有关配置服务器的更多信息，请 [本指南](../configuration/Configuration-Server-Management.html)。

## Dockerized部署

有关更多信息，请参见 [本指南](Docker-Installation.html)

## Servlet容器

CAS可以部署到许多servlet容器中。 有关更多信息，请参见 [本指南](Configuring-Servlet-Container.html)

## 海关和第三方来源

通常，通过开发实现CAS API的Java组件来定制或扩展CAS的功能，或者 包含第三方源代码。 包括第三方来源是微不足道的；只需 `build.gradle` 文件中 相关的依赖项即可。 

<div class="alert alert-warning"><strong>停止编码</strong><p>
覆盖或修改CAS内部组件和类，除非绝对需要</i><i>除非万不得已，通常 
是误导性渎职。 尽可能避免进行自定义更改，以独自承担维护负担。 
避免携带。 您将冒险部署的稳定性和安全性。 如果增强 
情况有吸引力或适度，请回馈该项目。 停止编写代码，或将其归类。
</p></div>

为了包括自定义Java源代码，应该在覆盖项目源代码树 `src / main / java` 目录下包含 

    ├──SRC
    │├──主
    ││├──的java
    │││└──EDU
    │││└──SSO
    │││└──中间件
    │││└──CAS
    │││├──审计
    ││││├──CompactSlf4jAuditTrailManager.java
    │││├──认证
    ││││└──主要
    ││││└──UsernamePasswordCredentialsToPrincipalResolver.java
    │││ ├──服务
    ││││└──JsonServiceRegistryDao.java
    │││├──util的
    ││││└──X509Helper.java
    │││└──卷筒纸
    │││├──HelpController的.java
    │││├──流
    ││││├──AbstractForgottenCredentialAction.java
    │││└──util的
    │││├──ProtocolParameterAuthority.java

## 依赖管理

每个CAS版本都提供了它支持的精选依赖性列表。 依赖项中的任何一个提供版本，因为CAS分发正在为您管理它。 当您升级CAS本身时，这些依赖项也将以一致的方式进行升级。

精选的依赖项列表包含一个完善的第三方库列表。 该列表可作为标准物料清单（BOM）提供。 并非每个人都喜欢从BOM表继承。 您可能需要使用自己的公司标准父级，也可能只想明确声明所有配置。

为了利用CAS BOM中的 `org.apereo.cas：CAS-服务器支持-BOM`，经由摇篮，请 [使用本指南](https://plugins.gradle.org/plugin/io.spring.dependency-management) 并相应地配置的摇篮的构建。

<sub>（1）[WAR叠加层]（http://maven.apache.org/plugins/maven-war-plugin/overlays.html）</sub>
