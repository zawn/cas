---
layout: 默认
title: CAS-建立程序
category: 开发者
---

# 建立过程

本页记录了CAS开发人员/贡献者在本地构建CAS服务器应采取的步骤。

<div class="alert alert-warning"><strong>使用警告！</strong><p>
如果要部署和配置CAS，那么您将处于 <strong>WRONG PLACE</strong>！ 要在本地部署CAS，请使用项目文档中针对特定CAS版本所述的WAR Overlay方法。 克隆，下载和从源头建立CAS代码库是 <strong>ONLY</strong> ，如果你希望参与该项目的开发需要。
</p></div>

## 源结帐

以下shell命令可用于从资源库中获取源代码：

```bash
git clone-递归git@github.com：apereo / cas.git cas-server
```

或更快速的克隆：

```bash
git clone --recursive --depth = 1-单分支--branch = master git@github.com：apereo / cas.git cas-server
＃git fetch --unshallow
```

为了成功克隆，您需要在Github上为帐户 [SSH密钥](https://help.github.com/articles/working-with-ssh-key-passphrases/) 如果这不是一个选择，则可以通过 `https://github.com/apereo/cas.git``https` 下的CAS存储库。

您可能还需要更新链接到CAS存储库的子模块。 较新版本的Git将自动执行此操作，而 较旧的版本将要求您明确告诉git下载子模块的内容：

```bash
git子模块更新--init --recursive
```

## 建造

以下shell命令可用于构建源：

```bash
cd cas-server
git checkout master
```

完成后，您可以通过以下命令构建代码库：

```bash
./gradlew构建安装--parallel -x测试-x javadoc -x检查--build-cache --configure-on-demand
```

以下命令行布尔标志受构建支持，并且可以通过 `-D`以系统属性的形式传递：

|标记|说明 | ----------------------------------- + ----------- -------------------------------------------------- -------------- + | `enableRemoteDebugging`           |允许通过预定义的端口（即 `5000`）进行远程调试。 | `remoteDebugging暂停`          |设置为 `true` 将挂起JVM远程调试，直到调试器附加到正在运行的会话。 | `enableIncremental`               |启用Gradle的增量编译功能。 | `showStandardStreams`             |让构建输出日志发送到标准流。 （即控制台等） | `skipCheckstyle`                  |跳过运行Checkstyle检查。 | `skipSpotbugs`                    |跳过运行的Spotbugs检查。 | `skipVersionConflict`             |如果发现依赖项冲突，请使用最新版本，而不要使构建失败。 | `skipNestedConfigMetadataGen`     |跳过为嵌套属性和通用集合生成配置元数据的操作。 | `skipSonarqube`                   |忽略向Sonarqube报告结果。 | `skipErrorProneCompiler`          |跳过运行 `出错` 静态分析的编译器。 | `skipBootifulArtifact`            |不要应用Spring Boot插件来启动应用程序构件。 | `ignoreJavadocFailures`           |忽略javadoc故障，让构建继续进行。 | `ignoreFindbugsFailures`          |忽略Findbugs失败，让构建恢复。 | `ignoreTestFailures`              |忽略测试失败，让构建继续进行。 | `casModules`                      |逗号分隔的模块列表，不带 `cas-server- [support | core]` 前缀。

- 您可以使用 `-x <task>` 来完全跳过/忽略构建中的某个阶段。 （即 `-x试验`， `-x检查`）。
- 如果您不需要让Gradle为您解析/更新依赖关系和新模块版本，则可以 `--offline` 标志，这会使构建进行得更快。
- 使用Gradle守护程序也有很大帮助。 默认情况下应启用 [](https://docs.gradle.org/current/userguide/gradle_daemon.html)。
- 通过 `启用 <a href="https://docs.gradle.org/current/userguide/build_cache.html">Gradle的构建缓存</a> --build-cache` 也可以显着缩短构建时间。

## 任务

可用的构建任务可以使用命令 `./gradlew task`。

## IDE设定

可以使用任何支持Gradle的现代IDE进行CAS开发。

### IntelliJ IDEA

以下Gradle的IDEA设置也可能有用：

![图像](https://user-images.githubusercontent.com/1205228/71612835-5ea5ed80-2bbc-11ea-8f49-9746dc2b3a70.png)

此外，您可能需要自定义VM设置以确保开发环境可以加载和索引代码库：

```bash
-服务器
-Xms1g
-Xmx8g
-Xss16m
-XX：NewRatio = 3

-XX：ReservedCodeCacheSize = 240m
-XX：+ UseCompressedOops
-XX：SoftRefLRUPolicyMSPerMB = 50

-XX：+ UseParNewGC
-XX：ParallelGCThreads = 4
-XX：+ UseConcMarkSweepGC
-XX：ConcGCThreads = 4

-XX：+ CMSClassUnloadingEnabled
-XX：+ CMSParallelRemarkEnabled
-XX：
-XX：+ CMSScavengeBeforeRemark
-XX：+ UseCMSInitiatingOccupancyTownOnly

= 1
-XX：SurvivorRatio = 8
-XX：+ UseCodeCacheFlushing
-XX：+ AggressiveOpts
-XX：-TraceClassUnloading
-XX：+ AlwaysPreTouch
-XX：+ TieredCompilation

-Djava.net.preferIPv4Stack = true
Dsun.io.useCanonCaches = false
-Djsse.enableSNIExtension = true
-ea
-Xverify：无
```

#### 外挂程式

以下插件在开发过程中可能会很有用：

- [格纹风格](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea)
- [FindBugs](https://plugins.jetbrains.com/plugin/3847-findbugs-idea)
- [龙目岛](https://github.com/mplushnikov/lombok-intellij-plugin)

一旦安装了Lombok插件，您还需要确保已打开“注释处理”。 您可能需要重新启动IDEA才能使更改完全生效。

![图像](https://user-images.githubusercontent.com/1205228/35231112-287f625a-ffad-11e7-8c1a-af23ff33918d.png)

请注意，可以将CAS提供的Checkstyle规则导入到idea中，以自动执行多个 格式规则，这些规则特别与程序包的导入和布局有关。 导入后，规则 应该类似于以下屏幕截图：

![图像](https://user-images.githubusercontent.com/1205228/42846621-b99539fc-8a2e-11e8-8128-9344bda7224d.png)

#### 运行CAS

通过创建 大致匹配以下屏幕截图 *运行配置* ，可以直接从IDEA运行CAS Web应用程序：

![图像](https://user-images.githubusercontent.com/1205228/41805461-9ea25b76-765f-11e8-9a36-fa82d286cf09.png)

此设置允许开发人员 [嵌入式Servlet容器](Build-Process.html#embedded-containers) 应用程序。

### 蚀

对于Eclipse，执行以下命令：

```bash
cd cas-server
./gradlew eclipse
```

然后，使用“ General \ Existing Projects into Workspace” 将项目导入eclipse，然后从项目的“ Configure”上下文菜单中选择“ Add Gradle Nature”。

<div class="alert alert-warning"><strong>青年汽车</strong><p>我们对Eclipse及其对基于Gradle的 
项目的支持的经验并不理想。 虽然时间改变了一切和文档老去，很可能是你可能会遇到Eclipse如何设法问题 
解决摇篮依赖性和构建项目。 最后，欢迎您使用最适合自己的方法，因为最终目标 
是找到合适的工具来构建和为CAS做出贡献。</p></div>

## 测试模块

请 [请参阅第](Test-Process.html) 页，以了解有关测试过程和准则的更多信息。

## 嵌入式容器

CAS项目带有许多内置模块，这些模块已预先为服务器Web应用程序，管理Web应用程序等 这些模块 `webapp` 文件夹中。

### 配置SSL

`thekeystore` 文件必须包含为您的CAS服务器域颁发的SSL私钥/公钥。 您需要 `keytool` 命令来创建密钥库和证书。

以下命令可以作为示例：

```bash
keytool -genkey -alias cas -keyalg RSA -validity 999 \
    -keystore / etc / cas / thekeystore -ext san = dns：$REPLACE_WITH_FULL_MACHINE_NAME
```

请注意，validity参数允许您以天数指定证书应为 有效的时间。 时间段越长，您重新创建它的可能性就越小。 要重新创建它，您需要将 删除旧的，然后再次按照这些说明进行操作。 您可能还需要提供 *Subject Alternative Name* 字段，可以使用 `keytool` 通过 `-ext san = dns：$REPLACE_WITH_FULL_MACHINE_NAME`。

响应将如下所示：

```bash
输入密钥库密码：changeit
重新输入新密码：changeit
您的名字和姓氏是什么？
  [Unknown]：  $REPLACE_WITH_FULL_MACHINE_NAME （即mymachine.domain.edu）
组织单位的名称是什么？
  [Unknown]：测试
您的组织名称是什么？
  [Unknown]：测试
您所在的城市或地区的名称是什么？
  [Unknown]：测试
您所在州或省的名称是什么？
  [Unknown]：测试
本机的两个字母的国家/地区代码是什么？
  [Unknown]：美国
CN =$FULL_MACHINE_NAME，OU =测试，O =测试，L =测试，ST =测试，C = US是否正确？
  [no]：是
```

在您的 `/ etc / hosts` 文件中（在Windows上： `C：\ Windows \ System32 \ Drivers \ etc \ hosts`），您可能还需要添加以下条目：

```bash
127.0.0.1 mymachine.domain.edu
```

从密钥库导出的证书也需要导入到Java平台的全局密钥库中：

```bash
＃将证书导出到文件中
keytool -export -file /etc/cas/config/cas.crt -keystore / etc / cas / thekeystore -alias cas

＃将证书导入到全局密钥库中
sudo keytool -import -file /etc/cas/config/cas.crt -alias cas -keystore $JAVA_HOME/ lib / security / cacerts
```

...其中 `JAVA_HOME` 是您安装JDK的位置（即 `/ Library / Java / JavaVirtualMachines / jdk[version].jdk / Contents / Home`）。

在Windows上，应该授予控制台的管理权限，而不是 `sudo` ， `$JAVA_HOME/ lib / security / cacerts` 改为 `“%JAVA_HOME%/ lib / security / cacerts”`。

### 部署

执行以下命令：

```bash
cd webapp / cas-server-webapp-tomcat

../../gradlew build bootRun --parallel --offline --configure-on-demand --build-cache --stacktrace
```

响应将如下所示：

```bash
...
2017年5月26日19：10：46470 INFO [org.apereo.cas.web.CasWebApplication] - <Started CasWebApplication in 21.893 seconds (JVM running for 36.888)>
...
```

默认情况下，CAS可以通过 `https://mymachine.domain.edu:8443/cas`

### 远程调试

如果 指定 `enableRemoteDebugging` 参数，则将嵌入式容器实例预先配置为侦听端口 `5000` 对于外部容器 部署 [例如Apache Tomcat](https://wiki.apache.org/tomcat/FAQ)，以下示例 显示了需要在 `bin / startup.sh | bat` 文件中进行配置的内容：

```bash
导出JPDA_ADDRESS = 5000
导出JPDA_TRANSPORT = dt_socket
bin / catalina.sh jpda开始
```

完成后，在连接到该端口的IDE中创建一个远程调试器配置，您将可以进入代码。

![图像](https://cloud.githubusercontent.com/assets/1205228/26517058/d09a8288-4245-11e7-962e-004bfe174a0a.png)

## 手动子模块测试

请 [请参阅第](Test-Process.html) 页，以了解有关测试过程和准则的更多信息。

## 样本构建别名

以下是一些方便的构建别名的示例，这些别名可用于从项目中快速运行本地cas服务器，或者从项目中 安装依赖项以在cas-overlay中使用。

```bash
＃调整CAS别名CAS项目的位置的文件夹
别名CAS =“CD〜/工作区/ CAS”

＃试验直接从项目中科院而不是使用CAS重叠
别名BC ='清晰; cas; cd webapp / cas-server-webapp-tomcat; \
    ../../gradlew build bootRun-按需配置--build-cache --parallel \
    -x测试-x javadoc -x检查-DremoteDebuggingSuspend = false \
    -DenableRemoteDebugging = true --stacktrace \
    -DskipNestedConfigMetadataGen =真“

＃安装用于罐子利用CAS覆盖项目
别名BCI =”清晰; cas; \
    ./gradlew clean build install-按需配置\
    --build-cache --parallel \
    -x测试-x javadoc -x检查--stacktrace \
    -DskipNestedConfigMetadataGen = true \
    -DskipBootifulArtifact =真的'
```
