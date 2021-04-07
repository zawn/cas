---
layout: 违约
title: CAS - 叠加安装
category: 安装
---

# 塞尔莱特容器配置

可部署 CAS 的多个容器选项。 [WAR覆盖](WAR-Overlay-Installation.html) 指南 描述了如何建立和部署CAS。

## 如何选择？

菜单上有各种各样的伺服容器和服务器。 选择标准概述如下：

- 选择您最熟悉的技术，并具有排除故障、调整和扩展以赢得胜利的技能和耐心。
- 选择一种不会强制 CAS 配置与群集中的任何单个服务器/节点绑定的技术，因为这将显示自动缩放问题和手动工作。
- 选择一种与您的网络和防火墙配置配合良好的技术，并且基于您的网络拓扑技术，该技术性能良好且足够可靠。
- 选择一种技术，显示有希望的结果下， *你的预期负荷*，运行 [性能和压力测试](../high_availability/High-Availability-Performance-Testing.html)。
- 选择一种不依赖于外部流程、系统和体力劳动的技术，是自力更生和自我控制的。

## 生产质量

此处展示的所有伺服容器，无论是否嵌入，都旨在做好生产准备。 这意味着 CAS 开箱即用的默认船舶，如果必要且默认，CAS 可能会在 被推翻，从而为您配置从开发到生产 当今平台上的一切。 就生产质量而言，使用嵌入式容器与外部容器几乎没有区别。

除非有具体、技术和合理的反对意见，否则选择嵌入式伺服容器几乎总是更好的选择。

## 嵌入式

请注意，CAS 本身会附带一些嵌入式容器，使平台尽可能 独立。 这些嵌入式容器是 CAS 软件不可分割的一部分，通常针对每个版本进行维护和 更新，并且肯定用于并可用于生产部署。 您 **不需要** ，但如果您愿意，可以将配置和部署到外部配置的容器中。 

<div class="alert alert-info"><strong>少做点事</strong><p>请记住，嵌入式 
容器的大部分（如果不是全部）可以通过 CAS 属性进行控制。 有关详细信息，请参阅本指南</a> <a href="../configuration/Configuration-Properties.html#embedded-apache-tomcat-container">。</p></div>

要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#embedded-container)查看本指南。

### 执行

CAS 网络应用程序一旦构建，可以通过以下命令与嵌入式容器部署到位：

```bash
爪哇 - 贾尔 / 路径 / 到 / 卡斯. 战争
```

此外，还可以将 CAS 作为完全可执行的 Web 应用程序运行：

```bash
• 奇莫德 +x / 路径 / 到 / 卡斯. 战争
/ 路径 / 到 / 卡斯. 战争
```

这是通过部署叠加的构建过程实现的，其中启动脚本 *在 Web 应用工件的开头插入* 。 如果您 希望查看和检查脚本，只需运行以下命令：

 ```bash
 #X是文件开头的行数
 头-n X/路径/到.cas.war
 ```

请注意，在大多数 Linux 和 OS X 分布中支持将 CAS 作为独立且完全可执行的 Web 应用程序运行。 其他平台（如 Windows）可能需要自定义配置。

### 阿帕奇·汤姆卡特

请注意，默认情况下，嵌入式容器尝试启用 HTTP2 协议。

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器 - webapp - 汤姆卡特</artifactId>
     <version>${cas.version}</version>
</dependency>
```

#### IPv4 配置

为了强制 Apache Tomcat 使用 IPv4，请将以下属性配置为系统属性，以便您的 *运行* 命令：

```bash
-贾瓦. net. 首选 Pv4 堆栈] 真实 
```

在外部容器的情况下，需要将相同的配置应用于您的 `$CATALINA_OPTS` 环境变量。

#### 更快的启动速度

[本指南](https://cwiki.apache.org/confluence/display/TOMCAT/HowTo+FasterStartUp) 就如何使 网络应用程序和 Apache Tomcat 作为一个整体更快地启动提出了几项建议。

#### 伐木

嵌入的 Apache Tomcat 容器目前无法在 INFO `以下显示任何日志消息` ，即使您的 CAS 日志 配置明确要求 `DEBUG` 或 `TRACE` 级数据。 请参阅此错误报告 [](https://github.com/spring-projects/spring-boot/issues/2923) 了解更多。

虽然变通办法和修复可能会在未来可用，但目前，您可以执行以下 更改，以从嵌入的 Apache Tomcat 获取 `DEBUG` 级别日志数据。 如果您正在对 Tomcat 内部组件（如阀门等）的行为 进行故障排除，则此特别有用。

- 设计一个 `记录。属性` 文件如下：

```properties
处理程序=java.util.logging.控制台
.level=所有
java.使用.登录.登录.控制台手。级别=精细
java.使用.登录.控制台汉德勒.格式=java.使用.登录。记录。
```

- 将`java.util.logging.comig.文件` 设置设计为系统/环境变量或命令行 参数，其值设置为 `记录` 。 启动和部署 CAS 时使用该设置。

例如：

```bash
java - jar / 路径 / 到 / 卡斯. 战争 - 贾瓦. 使用. 登录. 配置. 文件] / 路径 / 到 / 记录. 属性
```

### 码头

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器 - 网络应用程序 - 码头</artifactId>
     <version>${cas.version}</version>
</dependency>
```

### 退波

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-网络应用程序-底层</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 外部

CAS 部署可部署到任意数量的外部伺服容器中。 容器 **必须 最低 v4.0.0</code> `</strong> 支持。 在这些情况下，以下香草CAS网络应用程序
可用于 <a href="WAR-Overlay-Installation.html">战争覆盖</a> ：</p>

<pre><code class="xml"><dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器 - web 应用程序</artifactId>
     <version>${cas.version}</version>
</dependency>
`</pre>

虽然没有官方项目支持，但以下容器应与 CAS 部署兼容：

* [阿帕奇 · 汤姆卡特](http://tomcat.apache.org/) （至少需要阿帕奇 · 汤姆卡特 9 号）
* [杰博斯](http://www.jboss.org/)
* [野飞](http://wildfly.org/)
* [退波](http://undertow.io/)
* [码头](http://www.eclipse.org/jetty/) （至少需要码头 9.4）
* [玻璃鱼](http://glassfish.java.net/)
* [网圈](http://www.ibm.com/software/websphere/)

请记住，外部容器的配置是 **永远不会** 自动化的CAS以任何方式，这意味着你 负责升级，维护和所有其他配置的方式，如伐木，SSL等。 CAS 不为外部容器的配置或问题提供官方支持和故障排除指南等。 有关详细信息，请参阅服务器容器自己的文档。

注意 JBoss、野飞和 EAP，您可能需要添加一个 `jboss-脱衣结构.xml` 文件，以 `src/主/Webapp/WEB-INF` ，以便 CAS 能够正常启动。

```xml
<jboss-deployment-structure>
    <deployment>
        <dependencies>
            <module name="jdk.unsupported" />
        </dependencies>
    </deployment>
</jboss-deployment-structure>
```

### 配置

通过在覆盖中包括以下模块，支持外部容器：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器 - web 应用程序</artifactId>
     <version>${cas.version}</version>
</dependency>
```

### 不对称支持

如果使用了外部servlet容器，您可能需要确保它配置正确，以便在您发现相关错误且容器需要此时 支持异步请求。 这通常通过在容器的主要 `网络内设置真实</async-supported>` `<async-supported>来 
处理.xml`  
文件（即 对于阿帕奇汤姆卡特，这将是 `$CATALINA_HOME/康夫/网络.xml`）。

### 伐木

使用外部容器时，您可能需要确保默认情况下使用 CAS 船舶的记录配置文件被禁用，并转变为禁用 **特别** 日志配置和位置是否要通过 CAS 设置进行控制。 这是必要的，因为在 CAS 本身有机会通过设置控制记录之前，在外部伺服容器内初始化 CAS Web 应用程序上下文往往会过早地从类路径中初始化日志配置。

要禁用 CAS 自己的日志，请在 `src/主/资源` 下定义 `日志4j2.xml` 并在其中放入以下内容：

```xml
<？xml 版本="1.0"编码="UTF-8"？>
<Configuration>
    <Appenders>
        <Console name="console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d %p [%c] - &lt;%m&gt;%n" />
        </Console>
    </Appenders>
    <Loggers>
        <AsyncRoot level="off">
            <AppenderRef ref="console"/>
        </AsyncRoot>
    </Loggers>
</Configuration>
```

上述配置将使日志初始化变得无意义，从而允许通过 CAS 设置定义日志的位置和配置。

### 不同步记录

CAS 登录会自动插入运行时间应用上下文，并在指示容器关闭后清理 记录上下文。 然而，阿帕奇汤姆卡特，特别是 似乎默认忽略所有JAR文件命名 `日志4j*.jar`，这阻止了此功能的工作。 您可能需要更改 `` 属性，并将 `日志4j*.jar` 从 `罐中取出，` 属性。 如果 这样做，将阻止容器优雅地关闭，并导致记录器上下文线程挂起。

如果其他容器跳过扫描 Log4j JAR 文件，则可能需要在其他容器上进行类似处理。

## 码头工人

您可能也有兴趣通过 [多克](https://www.docker.com/)部署 CAS 。 有关详细信息，请参阅本指南</a>

。</p> 



## 系统服务

CAS 可以很容易地开始作为 Unix/Linux 服务使用 `init.d` 或 `系统`。 要了解更多 ，请 [请访问本指南](Configuring-Deployment-System-Service.html)。
