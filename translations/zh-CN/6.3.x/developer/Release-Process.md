---
layout: 违约
title: CAS - 发布过程
category: 开发 人员
---

# CAS 发布过程

此页面记录了发布工程师削减 CAS 服务器版本应采取的步骤。

## 索纳型设置

您需要注册一个 [Sonatype 帐户](https://central.sonatype.org/pages/ossrh-guide.html) ，并且必须要求 授权通过创建 JIRA 向 `组织.apereo` 包发布版本。 一旦你有，你可能会被要求有一个 目前的项目成员 *担保* 给你。

## GPG 设置

您需要 [生成自己的 PGP 签名，](https://blog.sonatype.com/2010/01/how-to-generate-pgp-signatures-with-maven/) 在上传到中央存储库之前签署发布神器。 要创建 OpenPGP 签名，您需要生成密钥对。 您需要向构建提供关键信息，这意味着三件事：

- 公共密钥 ID（密钥 ID 的最后 8 个符号。 您可以使用 `gpg-K` 来获取它
- 包含私钥的秘密钥匙圈文件的绝对路径。 由于 gpg 2.1，您需要导出带有命令的密钥：

```bash
gpg --钥匙分离.gpg--出口秘密钥匙 > ~/。gnupg/隔离.gpg
```

- 用于保护私钥的密码。

上述设置将需要放置在 `~/。格栅/格栅。属性` 文件：

```properties
签名.keyId=7A24P9QB
签名.密码=P@美元$w0rd
签名。
```

有关使用 Gradle 签名插件如何签名的其他说明 [此处可用](https://docs.gradle.org/current/userguide/signing_plugin.html)

## 环境设置

- 加载您的SSH密钥，并确保此SSH密钥也引用在GitHub中。
- 如有必要，调整 `$GRADLE_OPTS` 以初始化 JVM 堆大小。
- 加载您的 `~/。gradle/gradle.属性` 文件，</em>以下 *示例：</li> </ul>

```properties
组织. 格拉德尔. 戴蒙 = 假
组织. 格拉德尔. 平行 = 假
```

- 查看 CAS 项目： `git 克隆 git@github.com：apereo/cas.git 套件服务器`
- 确保你有 [最新版本的JDK 11](https://openjdk.java.net/projects/jdk/11/) 安装通过 `java版本`。

## 准备发布

应用以下步骤来准备释放环境。 有一些变化需要考虑，这取决于是否应创建 新的发布分支。

### 创建分支

```bash
• 将 $BRANCH 替换为 CAS 版本（即 5.3.x）
git 结帐-b $BRANCH
```

<div class="alert alert-warning"><strong>记得</strong><p>您应该仅针对主要或次要 
版本（即 <code>4.2</code>， <code>5.0</code>）。
如果您即将发布的版本已经存在远程跟踪分支，则应</code> 该分支 <code>git 结帐， 
跳过此步骤，然后移动到下一个部分以构建和发布。</p></div>

### 吉图布行动

<div class="alert alert-warning"><strong>记得</strong><p>当创建新分支时，您应该仅针对主要或次要 
版本这样做。</p></div>

- 更改 `.github/工作流/cas-build.yml` 触发并 *仅* 构建新创建的发布分支。 扫描文件以确保所有引用指向新创建的发布分支。
- 检查 `ci` 文件夹下的所有 CI 外壳脚本，以确保没有任何指向 `开发` 或 `主`。 这尤其适用于 CAS 文档如何发布到 `gh 页` 分支。
- 禁用CI中报告新依赖版本、使用翻新更新依赖项、发布Docker图像等的工作。

不要忘记提交所有更改并推动更改上游，创建一个新的远程分支来跟踪版本。

## 执行版本

- 在该项目的 `语法.属性`，将项目版本更改为发布版本，并删除 `-SNAPSHOT`。 （即 `6.0.0-RC1`）。
- 使用以下命令构建和发布项目：

```bash
。/release.sh
```

## 最终发布

- 为已发布的版本创建一个标签，提交更改并将标签推至上游存储库。 （即 `v5.0.0-RC1`）。

您还应切换回主要开发分支（即 `掌握`），并遵循以下步骤：

- 在该项目的 `gradle.属性`中，将项目版本更改为下一个</em> 开发版本（即 * `5.0.0-快照`）。</li>
- 将更改推至上游存储库。</ul>

## 家政

<div class="alert alert-info"><strong>记得</strong><p>更新发布描述时，请尽量保持 
一致，并遵循与之前版本相同的布局。</p></div>

- 在 GitHub 上发布项目的 RC 版本时，将发布标记标记为预发布。

## 更新叠加

更新以下叠加项目，指向新发布的 CAS 版本。 您可能需要将当前 `主` 分支 转移到下面每个叠加项目的维护分支，特别是如果/处理主要/次要版本 ，以及如果此处的发布过程有您创建了一个新的分支。

- [卡斯战争覆盖](https://github.com/apereo/cas-overlay-template)

## 更新文档

<div class="alert alert-warning"><strong>记得</strong><p>当创建新分支时，您应该仅针对主要或次要版本这样做。</p></div>

- 配置文档，</a>指出 `当前` 到最新可用版本

。</li> 
  
  - 配置文档，将新版本包含在</a>可用版本列表中。</li> 
  
  - [更新文档](https://github.com/apereo/cas/edit/gh-pages/Older-Versions.md/) 并添加新发布的版本。
- 如有必要，请更新项目的 [`README.md` 页面](https://github.com/apereo/cas/blob/master/README.md) 列出新版本。
- 更新 [构建过程](Build-Process.html) 包括有关如何构建新版本的任何所需信息。
- 更新 [发布说明](../release_notes/Overview.html) 并删除所有以前的条目。</ul> 



## 更新维护策略

更新 [维护策略](https://github.com/apereo/cas/edit/gh-pages/developer/Maintenance-Policy.md/) 以记录发布时间表和 EOL 时间表。 此任务仅在处理主要或次要版本时才相关。



## 更新演示

（可选）今天，一些 CAS 演示在 Heroku 上运行，并在代码库内的专用 分支中进行跟踪。 在相关时，获取通行证并更新每个通行证。
