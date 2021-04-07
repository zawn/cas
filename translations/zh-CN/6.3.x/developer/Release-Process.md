---
layout: 默认
title: CAS-发布流程
category: 开发者
---

# CAS发布过程

本页记录了发行工程师应采取的削减CAS服务器发行版的步骤。

## 声纳类型设置

您将需要注册一个 [Sonatype帐户](https://central.sonatype.org/pages/ossrh-guide.html) 并且必须要求 被授权通过创建JIRA将发布发布到 `org.apereo` 拥有之后，系统可能会要求您为 当前项目成员之一提供 *凭证*。

## GPG设定

您需要 [生成自己的PGP签名](https://blog.sonatype.com/2010/01/how-to-generate-pgp-signatures-with-maven/) 以在将发布工件上载到中央存储库之前对发布工件进行签名。 为了创建OpenPGP签名，您将需要生成一个密钥对。 您需要向构建提供关键信息，这意味着三件事：

- 公钥ID（keyId的后8个符号。 您可以使用 `gpg -K` 来获取它
- 包含您的私钥的密钥环文件的绝对路径。 从gpg 2.1开始，您需要使用以下命令导出密钥：

```bash
GPG --keyring secring.gpg --export秘密密钥 > 〜/ .gnupg / secring.gpg
```

- 用于保护您的私钥的密码短语。

上述设置将需要被放置到 `〜/ .gradle / gradle.properties` 文件：

```properties
signing.keyId = 7A24P9QB
$w0rd
signing.secretKeyRingFile = / Users / example / .gnupg / secring.gpg
```

如何文物的其他注意事项使用的摇篮签署 签署插件是 [可以在这里](https://docs.gradle.org/current/userguide/signing_plugin.html)

## 环境设定

- 加载您的SSH密钥，并确保GitHub中也引用了该SSH密钥。
- 如有必要，请调整 `$GRADLE_OPTS` 来初始化JVM堆大小。
- 加载 `〜/ .gradle / gradle.properties` 与下列文件 *作为一个例子*：

```properties
org.gradle.daemon = false
org.gradle.parallel = false
```

- 检出CAS项目： `git clone git@github.com：apereo / cas.git cas-server`
- 确保您已通过 `java -version`[最新版本的JDK 11](https://openjdk.java.net/projects/jdk/11/)。

## 准备发布

应用以下步骤准备发布环境。 有考虑到这取决于是否有一些变化 新版本的分支应该被创建。

### 创建分支

```bash
＃将 $BRANCH 替换为CAS版本（即5.3.x）
git checkout -b $BRANCH
```

<div class="alert alert-warning"><strong>记住</strong><p>您应该只为主要或次要做到这一点 
版本（即 <code>4.2.x版</code>， <code>5.0.x版本</code>）。
如果已经存在要发布的版本的远程跟踪分支，则应 <code>git checkout</code> 该分支， 
跳过此步骤，继续进行下一部分以进行构建和发布。</p></div>

### GitHub动作

<div class="alert alert-warning"><strong>记住</strong><p>创建新分支后，仅应在主要或次要 
</p></div>

- 将 `.github / workflows / cas-build.yml更改为` 来触发，仅 ** 建立新创建的发行分支。 扫描文件，以确保所有引用都指向新创建的发行分支。
- `ci` 文件夹下的所有CI shell脚本，确保没有东西指向 `development` 或 `master`。 这尤其适用于将CAS文档发布到 `gh-pages` 分支的方式。
- 在CI中禁用报告新依赖项版本的作业，使用Renovate更新依赖项，发布Docker映像等。

不要忘记提交所有更改并将更改推送到上游，从而创建一个新的远程分支来跟踪发布。

## 执行发布

- 在项目的 `gradle.properties`，将项目版本更改为发行版，然后删除 `-SNAPSHOT`。 （即 `6.0.0-RC1`）。
- 使用以下命令生成和发布项目：

```bash
./release.sh
```

## 最终发布

- 为发布的版本创建标签，提交更改，然后将标签推送到上游存储库。 （即 `v5.0.0-RC1`）。

您还应该切换回主开发分支（即 `master`），并按照以下步骤操作：

- 在项目的 `gradle.properties`，将项目版本更改为 *next* 开发版本（即 `5.0.0-SNAPSHOT`）。
- 将您的更改推送到上游存储库。

## 家政

<div class="alert alert-info"><strong>记住</strong><p>更新发行说明时，请尝试使 
保持一致，并遵循与先前发行版相同的布局。</p></div>

- 在GitHub上发布项目的RC版本时，将release标签标记为预发布。

## 更新叠加层

更新以下覆盖项目，以指向新发布的CAS版本。 对于下面的每个重叠项目，您可能需要将当前的 `主` 分支 移到维护分支，尤其是在处理主要/次要发行版 以及此处的发行过程中是否创建了新分支的情况下。

- [CAS WAR叠加](https://github.com/apereo/cas-overlay-template)

## 更新文件

<div class="alert alert-warning"><strong>记住</strong><p>创建新分支后，仅应针对主要版本或次要版本执行此操作。</p></div>

- 配置文档以将 `当前` 指向此处的最新可用版本 [](https://github.com/apereo/cas/blob/gh-pages/current/index.html)。
- 配置文档以将新版本包括在 [可用版本](https://github.com/apereo/cas/blob/gh-pages/_layouts/default.html)。
- [更新文档](https://github.com/apereo/cas/edit/gh-pages/Older-Versions.md/) 并添加新发布的版本。
- 如有必要，更新项目的 [`README.md` 第](https://github.com/apereo/cas/blob/master/README.md) 页以列出新版本。
- 将构建过程</a> 更新为

，以包含有关如何构建新发行版的任何所需信息。</li> 
  
  - 更新 [发行说明](../release_notes/Overview.html) 并删除所有以前的条目。</ul> 



## 更新维护政策

更新 [维护策略](https://github.com/apereo/cas/edit/gh-pages/developer/Maintenance-Policy.md/) 以记下发布时间表和EOL时间线。 仅当处理主要或次要版本时，此任务才有意义。



## 更新演示

（可选）如今，许多CAS演示都在Heroku上运行，并且在代码库内的 进行一次通行证，并在相关时进行更新。
