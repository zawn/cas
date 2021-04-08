---
layout: default
title: CAS - 叠加安装
category: 安装
---

# WAR 叠加安装

CAS安装是从根本上来说是面向源代码的过程，我们建议您使用WAR overlay（1）项目来组织自定义设置，例如组件配置和UI设计。 WAR覆盖最终会构建输出是一个 `cas.war` 文件，可以将其部署到servlet容器（如 [Apache Tomcat](Configuring-Servlet-Container.html)。

## 安装要求

[See this guide](../planning/Installation-Requirements.html) to learn more.

## 什么是WAR覆盖

覆盖是一种对抗重复代码和/或资源的策略。 与下载CAS代码库并从源代码进行构建不同，WAR叠加允许您下载项目本身提供的预构建的普通CAS web应用程序服务器，并覆盖/插入特定的行为。 在构建时，构建安装过程将首先尝试下载提供的二进制工件。 然后，该工具将在相同的项目目录中找到您的配置文件和可用的设置，并将它们合并到下载的工件中，以便生成一个完整的存档(即`cas.war`)。 覆盖的工件可能包括资源、java类、图像、CSS和javascript文件。 为了使合并进程成功执行，被覆盖的工件的位置和名称必须与该项目提供原始下载的存档位置和名称进行 **精确地** 的匹配，。 Java code in the overlay project's `src/main/java` folder and resources in `src/main/resources` will end up in the `WEB-INF\classes` folder of cas.war and they will be loaded by the classloader instead of resources with the same names in jar files inside `WEB-INF\lib`.

It goes without saying that while up-front ramp-up time could be slightly complicated, there are significant advantages to this approach:

1. There is no need to download/build from the source.
2. Upgrades are tremendously easier in most cases by simply adjusting the build script to download the newer CAS release.
3. Rather than hosting the entire software source code, as the deployer you **ONLY** keep your own local customizations which makes change tracking much easier.
4. Tracking changes inside a source control repository is very lightweight, again simply because only relevant changes (and not the entire software) is managed.

### Managing Overlays

Most if not all aspects of CAS can be controlled by adding, removing, or modifying files in the overlay; it's also possible and indeed common to customize the behavior of CAS by adding third-party components that implement CAS APIs as Java source files or dependency references.

The process of working with an overlay can be summarized in the following steps:

- Start with and build the provided basic vanilla build/deployment.
- Identify the artifacts from the produced build that need changes. These artifacts are generally produced by the build in the `build` directory for Gradle. Use the gradle `explodeWar` task.
- Copy the identified artifacts from the identified above directories over to the `src/main/resources` directory.
1. Create the `src/main/resources` directories, if they don't already exist.
2. Copied paths and file names **MUST EXACTLY MATCH** their build counterparts, or the change won't take effect. See the table below to understand how to map folders and files from the build to `src`.
- After changes, rebuild and repeat the process as many times as possible.
- Double check your changes inside the built binary artifact to make sure the overlay process is working.

<div class="alert alert-warning"><strong>Be Exact</strong><p>Do NOT copy everything produced by the build. Attempt to keep changes and customizations to a 
minimum and only grab what you actually need. Make sure the deployment environment is kept clean and precise, or you incur the risk of terrible upgrade issues and painful headaches.</p></div>

## CAS WAR Overlays

CAS WAR overlay projects described below are provided for reference and study.

### CAS Overlay Initializr

Apereo CAS Initializr is a relatively new addition to the Apereo CAS ecosystem that allows you as the deployer to generate CAS WAR Overlay projects on the fly with just what you need to start quickly.

To learn more about the initializr, please [review this guide](WAR-Overlay-Initializr.html).

### CAS Overlay Template

You can download or clone the repositories below to get started with a CAS overlay template.

<div class="alert alert-info"><strong>Review Branch!</strong><p>The below repositories point you towards their <code>master</code> branch.
You should always make sure the branch you are on matches the version of CAS you wish to configure and deploy. The <code>master</code>
branch typically points to the latest stable release of the CAS server. Check the build configuration and if inappropriate,
use <code>git branch -a</code> to see available branches, and then <code>git checkout [branch-name]</code> to switch if necessary.</p></div>

| 项目                                                                | Build Directory           | Source Directory     |
| ----------------------------------------------------------------- | ------------------------- | -------------------- |
| [CAS WAR Overlay](https://github.com/apereo/cas-overlay-template) | `cas/build/cas-resources` | `src/main/resources` |

The `cas/build/cas-resources` files are unzipped from `cas.war!WEB-INF\lib\cas-server-webapp-resources-<version>.jar` via `gradle explodeWar` in the overlay.

To construct the overlay project, you need to copy directories and files *that you need to customize* in the build directory over to the source directory.

The WAR overlay also provides additional tasks to explode the binary artifact first before re-assembling it again. You may need to do that step manually yourself to learn what files/directories need to be copied over to the source directory.

Note: Do **NOT** ever make changes in the above-noted build directory. The changeset will be cleaned out and set back to defaults every time you do a build. Put overlaid components inside the source directory and/or other instructed locations to avoid surprises.

## CAS Configuration Server Overlay

See this [Maven WAR overlay](https://github.com/apereo/cas-configserver-overlay) for more details.

To learn more about the configuration server, please [review this guide](../configuration/Configuration-Server-Management.html).

## Dockerized Deployment

有关更多信息，请参见 [本指南](Docker-Installation.html)

## Servlet Container

CAS can be deployed to a number of servlet containers. 有关更多信息，请参见 [本指南](Configuring-Servlet-Container.html)

## Custom and Third-Party Source

It is common to customize or extend the functionality of CAS by developing Java components that implement CAS APIs or to include third-party source by dependency references. Including third-party source is trivial; simply include the relevant dependency in the overlay `build.gradle` file. 

<div class="alert alert-warning"><strong>Stop Coding</strong><p>
Overlaying or modifying CAS internal components and classes, <i>unless ABSOLUTELY required</i>, should be a last resort and is generally 
considered a misguided malpractice. Where possible, avoid making custom changes to carry the maintenance burden solely on your own. 
Avoid carrying . You will risk the stability and security of your deployment. If the enhancement 
case is attractive or modest, contribute back to the project. Stop writing code, or rite it where it belongs.
</p></div>

In order to include custom Java source, it should be included under a `src/main/java` directory in the overlay project source tree.

    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── edu
    │   │   │       └── sso
    │   │   │           └── middleware
    │   │   │               └── cas
    │   │   │                   ├── audit
    │   │   │                   │   ├── CompactSlf4jAuditTrailManager.java
    │   │   │                   ├── authentication
    │   │   │                   │   └── principal
    │   │   │                   │       └── UsernamePasswordCredentialsToPrincipalResolver.java
    │   │   │                   ├── services
    │   │   │                   │   └── JsonServiceRegistryDao.java
    │   │   │                   ├── util
    │   │   │                   │   └── X509Helper.java
    │   │   │                   └── web
    │   │   │                       ├── HelpController.java
    │   │   │                       ├── flow
    │   │   │                       │   ├── AbstractForgottenCredentialAction.java
    │   │   │                       └── util
    │   │   │                           ├── ProtocolParameterAuthority.java

## Dependency Management

Each release of CAS provides a curated list of dependencies it supports. In practice, you do not need to provide a version for any of these dependencies in your build configuration as the CAS distribution is managing that for you. When you upgrade CAS itself, these dependencies will be upgraded as well in a consistent way.

The curated list of dependencies contains a refined list of third party libraries. The list is available as a standard Bills of Materials (BOM). Not everyone likes inheriting from the BOM. You may have your own corporate standard parent that you need to use, or you may just prefer to explicitly declare all your configuration.

To take advantage of the CAS BOM at `org.apereo.cas:cas-server-support-bom`, via Gradle, please [use this guide](https://plugins.gradle.org/plugin/io.spring.dependency-management) and configure the Gradle build accordingly.

<sub>(1) [WAR Overlays](http://maven.apache.org/plugins/maven-war-plugin/overlays.html)</sub>
