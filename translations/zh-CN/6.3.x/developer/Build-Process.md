---
layout: 违约
title: CAS - 构建过程
category: 开发 人员
---

# 构建过程

本页记录了 CAS 开发人员/贡献者在本地构建 CAS 服务器时应采取的步骤。

<div class="alert alert-warning"><strong>使用警告！</strong><p>
如果你要部署和配置CAS，你是在 <strong>错误的地方</strong>！ 要在本地部署 CAS，请使用项目文档中描述的 WAR 叠加方法，用于特定的 CAS 版本。 从源头上克隆、下载和构建 CAS 代码库 <strong>只有您希望为项目发展做出贡献时才需要</strong> 。
</p></div>

## 源结帐

以下空壳命令可用于从存储库中获取源：

```bash
git 克隆 - 递归 git@github.com：阿佩雷奥/卡斯.git 套件服务器
```

或更快的克隆：

```bash
git 克隆 - 递归 - 深度+1 - 单分支 - 分支+主 git@github.com：apereo/cas.git 套件服务器
# git 取 - 解开
```

要获得成功的克隆，您需要 [为 Github 上的帐户设置 SSH 密钥](https://help.github.com/articles/working-with-ssh-key-passphrases/) 。 如果这不是一个选项，您可以通过 `https://github.com/apereo/cas.git`</code> `https下克隆CAS存储库。</p>

<p spaces-before="0">您可能需要更新链接到 CAS 存储库的子模块。 较新版本的 Git 将自动做到这一点， 
但较旧的版本将要求您明确告诉 git 下载子模块的内容：</p>

<pre><code class="bash">git子模块更新-init-递归
`</pre>

## 建

以下空壳命令可用于构建源：

```bash
cd套管服务器
git结帐主
```

完成后，您可以通过以下命令构建代码库：

```bash
。/抓图构建安装-平行-x测试-x贾瓦多克-x检查-构建缓存-按需配置
```

以下命令线布尔标志由构建支持，可通过 `-D`以系统属性的形式传递：

|国旗|描述 |--------------------------------------------------------------------------------------------------------------+ | `启用重新检测`           |允许通过预先定义的端口进行远程调试（即 `5000`）。 | `远程窃听暂停`          |设置为 `真正的` 暂停JVM远程调试，直到调试器连接到运行会话。 | `启用`               |启用 Gradle 的增量编译功能。 | `显示标准流`             |让已发送到标准流的生成输出日志。 （即控制台等） | `跳过检查风格`                  |跳过运行检查型检查。 | `跳过斑点虫`                    |跳过运行斑点虫检查。 | `跳过反向`             |如果发现依赖性冲突，请使用最新版本，而不是使构建失败。 | `跳过康菲格梅塔达根`     |跳过为嵌套属性和通用集合生成配置元数据。 | `跳过索纳库贝`                   |忽略向声纳库贝报告结果。 | `跳过埃罗普罗内公司`          |跳过运行 `容易出错的` 静态分析编译器。 | `跳过布蒂富尔事实`            |不要将"弹簧启动"插件应用于启动应用工件。 | `忽略贾瓦多克费卢斯`           |忽略javadoc故障，让生成恢复。 | `忽略芬德布格斯费卢斯`          |忽略查找虫故障，让生成恢复。 | `忽略测试费卢斯`              |忽略测试失败，让生成恢复。 | `种模式`                      |comma 分离了没有 `cas-server-[支持|核心]` 前缀的模块列表。

- 您可以使用 `-x <task>` 完全跳过/忽略构建中的一个阶段。 （即 `-x测试`， `-x检查`）。
- 如果您不需要让 Gradle 为您解析/更新依赖项和新模块版本，您可以利用 `- 当您构建时脱机` 标志，这往往会使构建速度更快。
- 使用格栅道蒙也是一个很大的帮助。 默认情况下，应启用 [](https://docs.gradle.org/current/userguide/gradle_daemon.html)。
- 通过 `实现 <a href="https://docs.gradle.org/current/userguide/build_cache.html">Gradle 的构建缓存</a> - 构建缓存` 也可以显著提高构建时间。

## 任务

可用的构建任务可以使用命令 `。/`任务找到。

## IDE 设置

CAS 开发可以使用任何支持 Gradle 的现代 IDE 进行。

### 英特利杰·伊德基德

格拉德尔的以下 IDEA 设置可能也很有用：

![图像](https://user-images.githubusercontent.com/1205228/71612835-5ea5ed80-2bbc-11ea-8f49-9746dc2b3a70.png)

此外，您可能需要自定义 VM 设置，以确保开发环境可以加载和索引代码库：

```bash
-服务器
-Xms1g
-Xmx8g
- Xss16m
- XX：新拉蒂奥+3

- XX：保留代码缓存=2.4亿
- XX：+使用压缩的Oops
-XX：软参考政策+50

XX：+使用ParNewGC
-XX：平行GC阅读=4
-XX：+使用康马克推特GC
-XX：康克GC阅读=4

-XX：+CMS类不可加载
-XX：+CMS帕拉勒马克可
-XX：CMS启动占用 65
- XX：+CMS清理前标记
-XX：+使用CMS启动占用仅

-XX：最大收缩保持=1
-XX：幸存者拉蒂奥=8
-XX：+使用代码缓存冲刷
-XX：+侵略性
-XX：-跟踪类卸载
-XX：+始终超前触摸
-XX：+分层组合

-贾瓦.net.优选IPv4Stack=真实
-Dsun.io.useCanonCaches=假
-Djse.启用SNI扩展=真实
-
-Xverify：无
```

#### 插件

在开发过程中，以下插件可能证明是有用的：

- [检查型](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea)
- [查找布格斯](https://plugins.jetbrains.com/plugin/3847-findbugs-idea)
- [隆博克](https://github.com/mplushnikov/lombok-intellij-plugin)

安装 Lombok 插件后，您还需要确保注释处理已打开。 您可能需要重新启动IDEA才能使更改生效。

![图像](https://user-images.githubusercontent.com/1205228/35231112-287f625a-ffad-11e7-8c1a-af23ff33918d.png)

请注意，CAS 提供的检查式规则可以导入到构想中，以自动化一些与包装进口和布局相关的 格式规则。 导入后， 的规则应看起来像下面的屏幕截图：

![图像](https://user-images.githubusercontent.com/1205228/42846621-b99539fc-8a2e-11e8-8128-9344bda7224d.png)

#### 运行案例

可以通过 创建大致匹配以下屏幕截图的 *运行配置* ，从 IDEA 直接运行 CAS 网络应用程序：

![图像](https://user-images.githubusercontent.com/1205228/41805461-9ea25b76-765f-11e8-9a36-fa82d286cf09.png)

此设置允许开发人员通过嵌入式</a>嵌入式伺服容器运行 CAS 网络 应用程序。</p> 



### 日蚀

对于 Eclipse，执行以下命令：



```bash
cd套管服务器
。/格劳日食
```


然后，使用"一般现有项目进入工作空间"将项目导入日食， 从项目的"配置"上下文菜单中选择"添加 Gradle 自然"。

<div class="alert alert-warning"><strong>基督教青年会</strong><p>我们在 Eclipse 及其对基于 Gradle 的 
项目的支持方面体验并不理想。 当时间改变一切，文档变老时，您可能会遇到 Eclipse 如何设法 
解决 Gradle 依赖关系和构建项目的问题。 最后，欢迎您使用最适合您的作品作为最终目标 
是找到合适的手性，以构建和贡献 CAS。</p></div>

## 测试模块

请 [](Test-Process.html) 查看此页面，了解有关测试过程和指南的更多内容。



## 嵌入式容器

CAS 项目配备了一些内置模块，这些模块预先配置了嵌入式伺服容器，如 Apache Tomcat、码头等，用于服务器 Web 应用程序、管理 Web 应用程序等。 这些模块位于 CAS 项目的 `webapp` 文件夹中。



### 配置 SSL

</code> 文件的关键商店 `必须包括为您的 CAS 服务器域签发的 SSL 私钥/公共密钥。 您 
将需要使用 JDK 的 <code>钥匙` 命令来创建钥匙店和证书。 

下列命令可作为示例：



```bash
钥匙凳-基因-阿利亚斯-凯萨尔格RSA-有效性999=
    -钥匙店/等/卡/钥匙店-外圣恩：$REPLACE_WITH_FULL_MACHINE_NAME
```


请注意，有效性参数允许您在几天内指定证书应 有效期。 时间越长，重新创建它的可能性就越小。 要重新创建它，您需要 删除旧说明，然后再次按照这些说明操作。 您可能还需要提供 *主题替代名称* 字段，这可以通过 `-ext san=dns：$REPLACE_WITH_FULL_MACHINE_NAME`完成 `键凳` 。

响应将看起来像这样：



```bash
输入钥匙店密码：更改
重新输入新密码：更改
您的姓名和姓氏是什么？
  [Unknown]：  $REPLACE_WITH_FULL_MACHINE_NAME （即 mymachine.domain.edu）
你的组织单位叫什么名字？
  [Unknown]：测试
您的组织名称是什么？
  [Unknown]：测试
您的城市或地区名称是什么？
  [Unknown]：测试
贵州或省的名称是什么？
  [Unknown]：测试
这个单位的两个字母的国家代码是什么？
  [Unknown]：美国
CN=$FULL_MACHINE_NAME、OU+测试、O+测试、L+测试、ST+测试、C=US是否正确？
  [no]：是的
```


在您的 `/等/主机` 文件（在 Windows 上： `C：\Windows\System32\驱动程序等主机`），您可能还需要添加以下条目：



```bash
127.0.1 mymachine.domain.edu
```


从您的钥匙店导出的证书还需要导入 Java 平台的全球钥匙店：



```bash
• 将证书导入文件
钥匙凳 - 出口文件/等/卡/配置/cas.crt - 钥匙店 /等/cas/钥匙店 - 别名 cas

• 将证书导入全球钥匙店
sudo 钥匙凳 - 进口 - 文件 /等/cas/config/cas.crt - 别名 cas - 钥匙店 $JAVA_HOME/lib/安全/卡塞特
```


...其中 `JAVA_HOME` 是您安装JDK的地方（即 `/图书馆/Java/爪哇虚拟机械/jdk[version].jdk/内容/家庭`）。

在 Windows 上，管理权应授予控制台，而不是 `苏多`， `$JAVA_HOME/lib/安全/` 应改为 `"%JAVA_HOME%/lib/安全/卡塞茨"` 。



### 部署

执行以下命令：



```bash
cd网络应用程序/卡斯服务器-网络应用程序-汤姆卡特

。。/../抓图生成启动运行-平行-离线--按需配置-生成缓存-堆栈跟踪
```


响应将看起来像这样：



```bash
...
2017-05-26 19：10：46，470 INFO [org.apereo.cas.web.CasWebApplication] - <Started CasWebApplication in 21.893 seconds (JVM running for 36.888)>
...
```


默认情况下，CAS 将在 `https://mymachine.domain.edu:8443/cas`



### 远程调试

嵌入式容器实例预先配置，可收听港口 `5000` 上的调试请求，前提是您 指定 `启用重新调试` 参数。 对于外部容器 部署， [如 Apache Tomcat](https://wiki.apache.org/tomcat/FAQ)，下面的示例 显示了 `箱/启动.sh|` 文件中需要配置的内容：



```bash
出口JPDA_ADDRESS=5000
出口JPDA_TRANSPORT=dt_socket
bin/卡塔利娜.sh jpda启动
```


完成后，在 IDE 中创建一个远程调试器配置，该配置连接到此端口，您将能够步入代码。

![图像](https://cloud.githubusercontent.com/assets/1205228/26517058/d09a8288-4245-11e7-962e-004bfe174a0a.png)



## 手动子模块测试

请 [](Test-Process.html) 查看此页面，了解有关测试过程和指南的更多内容。



## 示例生成别名

下面是一些方便的构建别名的例子，用于从项目中快速运行本地 cas 服务器，或 从项目中安装依赖项以用于 cas 覆盖。



```bash
• 将 cas 别名调整到 cas 项目文件夹
别名 cas='cd~/工作空间/cas'

# 测试套装直接从项目中，而不是使用 CAS 叠加
别名 bc=clear 的位置：卡斯;cd网络应用程序/卡斯服务器-网络应用程序-汤姆卡特：\
    。/../抓斗生成启动运行-按需配置-构建缓存-平行=
    -x测试-x贾瓦多克-x检查-演示重复=假=
    -可拒绝 主题窃听=真实-堆栈跟踪-
    -斯基普内斯德·康菲格·梅塔达根]真'

#安装罐子用于CAS叠加项目
别名bci='clear：卡斯;•
    ./抓地力清洁构建安装-按需配置-
    -构建缓存-平行-
    -x测试-xjavadoc-x检查-堆栈跟踪]
    -DskipNested康菲格梅塔达根=真实=真实=
    -DskipBooful事实=真实"
```
