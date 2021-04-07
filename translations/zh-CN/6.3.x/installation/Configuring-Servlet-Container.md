---
layout: 默认
title: CAS-覆盖式安装
category: 安装
---

# Servlet容器配置

许多容器选项可用于部署CAS。 [WAR Overlay](WAR-Overlay-Installation.html) 指南 介绍了如何构建和部署CAS。

## 我该如何选择？

菜单上有各种各样的servlet容器和服务器。 选择标准概述如下：

- 选择一种您最熟悉的技术，并具有技巧和耐心来对故障进行排除，调整和扩展以取得胜利。
- 选择一种不会强制将CAS配置绑定到群集中任何单个服务器/节点的技术，因为这会带来自动扩展问题和手动工作。
- 选择一种适合您的网络和防火墙配置，并且根据您的网络拓扑具有足够性能和可靠性的技术。
- 选择一种技术，该技术在 *的预期负载*下会显示出令人鼓舞的结果，并已进行了 [性能和压力测试](../high_availability/High-Availability-Performance-Testing.html)。
- 选择一种尽可能不依赖外部流程，系统和人工工作，自力更生且自成体系的技术。

## 生产质量

此处介绍的所有servlet容器（嵌入式或其他方式）均旨在准备好生产。 这意味着，CAS出厂时提供了有用的默认值， ，如果有必要，并且默认情况下，CAS会为您配置 的所有内容。 就生产质量而言，使用嵌入式容器与使用外部容器几乎没有区别。

除非有特定的，技术上的和合理的反对意见，否则选择嵌入式servlet容器几乎总是更好的选择。

## 嵌入式的

请注意，CAS本身附带了许多嵌入式容器，这些容器可使平台尽可能独立地包含 。 这些嵌入式容器是CAS软件不可或缺的一部分， 更新，并且肯定可以用于生产部署中。 您 **** ，但如果需要，可以配置并部署到外部配置的容器。 

<div class="alert alert-info"><strong>少做</strong><p>
容器的大部分（如果不是全部）方面都可以通过CAS属性进行控制。 有关更多信息，请参见 <a href="../configuration/Configuration-Properties.html#embedded-apache-tomcat-container">本指南</a></p></div>

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#embedded-container)。

### 执行

一旦构建了CAS Web应用程序，就可以通过以下命令将其与嵌入式容器一起部署：

```bash
java -jar /path/to/cas.war
```

此外，还可以将CAS作为完全可执行的Web应用程序运行：

```bash
＃chmod + x /path/to/cas.war
/path/to/cas.war
```

这是通过部署覆盖的构建过程实现的，其中启动脚本为 *，在Web应用程序工件的开头插入了* 如果您 ，则只需运行以下命令：

 ```bash
 ＃X是从文件开头开始的行数
 head -n X /path/to.cas.war
 ```

请注意，大多数Linux和OS X发行版都支持将CAS作为独立且完全可执行的Web应用程序运行。 Windows等其他平台可能需要自定义配置。

### 阿帕奇雄猫

请注意，默认情况下，嵌入式容器会尝试启用HTTP2协议。

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-webapp-tomcat</artifactId>
     <version>${cas.version}</version>
</dependency>
```

#### IPv4配置

为了强制Apache Tomcat使用IPv4，请将以下内容配置为 *运行* 命令的系统属性：

```bash
-Djava.net.preferIPv4Stack = true 
```

如果是外部容器，则需要将相同类型的配置应用于您的 `$CATALINA_OPTS`

#### 更快的启动

[本指南](https://cwiki.apache.org/confluence/display/TOMCAT/HowTo+FasterStartUp) Web应用程序和整个Apache Tomcat更快地启动提供了一些建议。

#### 记录中

即使您的CAS日志 配置明确要求 `DEBUG` 或 `TRACE` 级数据，嵌入式Apache Tomcat容器当前也无法显示 `INFO` 请参阅 [此错误报告](https://github.com/spring-projects/spring-boot/issues/2923) 以了解更多信息。

尽管将来可能会出现变通方法和修补程序，但是您暂时可以执行以下 更改，以从嵌入式Apache Tomcat中 `DEBUG` 如果要对Tomcat的内部组件（例如阀门等） 进行故障诊断，这特别有用。

- 这样设计一个 `logging.properties` 文件：

```properties
handlers = java.util.logging.ConsoleHandler
.level =全部
java.util.logging.ConsoleHandler.level = FINER
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
```

- 将`java.util.logging.config.file` 设计为系统/环境变量或命令行 参数，其值设置为 `logging.properties` 路径。 启动和部署CAS时使用该设置。

例如：

```bash
java -jar /path/to/cas.war -Djava.util.logging.config.file = / path / to / logging.properties
```

### 码头

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-webapp-jetty</artifactId>
     <version>${cas.version}</version>
</dependency>
```

### 底拖

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-webapp-undertow</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 外部的

CAS部署可以部署到任意数量的外部servlet容器。 容器 **必须** 至少支持 servlet规范 `v4.0.0` 在这些情况下，可以在 [WAR叠加层](WAR-Overlay-Installation.html) 使用以下普通CAS Web应用程序 ：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-webapp</artifactId>
     <version>${cas.version}</version>
</dependency>
```

虽然没有官方项目支持，但以下容器应与CAS部署兼容：

* [Apache Tomcat](http://tomcat.apache.org/) （至少需要Apache Tomcat 9）
* [老板](http://www.jboss.org/)
* [野蝇](http://wildfly.org/)
* [底拖](http://undertow.io/)
* [码头](http://www.eclipse.org/jetty/) （至少需要9.4码头）
* [玻璃鱼](http://glassfish.java.net/)
* [的WebSphere](http://www.ibm.com/software/websphere/)

请记住，外部容器的配置永远不会 **** 操作，这意味着您 负责升级，维护和所有其他配置方式，例如日志记录，SSL等。 对于外部容器的配置或问题，CAS不提供官方支持和故障排除指南等，为 有关更多信息，请参阅servlet容器自己的文档。

对于JBoss，Wildfly和EAP，请注意，您需要 `jboss-deloyment-structure.xml` 文件添加到 `src / main / webapp / WEB-INF` 中，以使CAS正常启动。

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

通过在叠加层中包含以下模块来启用对外部容器的支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-webapp</artifactId>
     <version>${cas.version}</version>
</dependency>
```

### 异步支持

如果使用了外部servlet容器，则可能需要确保在收到相关错误且容器需 通常通过在容器的主 `web.xml`  
文件中 `<async-supported>true</async-supported>` 来处理该值为 对于Apache Tomcat，该 `$CATALINA_HOME/conf/web.xml`）。

### 记录中

使用外部容器时，如果要通过CAS设置控制日志配置和位置，则可能需要确保默认情况下CAS附带的日志记录配置文件已被禁用，并且变成了no-op **特别是** 这是必须的，因为在外部Servlet容器内进行CAS Web应用程序上下文的初始化会在CAS本身有机会通过设置控制日志记录之前，过早地初始化来自类路径的日志配置。

要禁用CAS自己的日志记录，请在 `src / main / resources` `log4j2.xml` ，并将以下内容放入其中：

```xml
<？xml版本=“ 1.0”编码=“ UTF-8”？>
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

上面的配置将使日志记录初始化变得毫无意义，从而允许通过CAS设置来定义日志的位置和配置。

### 异步记录

CAS日志自动将其自身插入到运行时应用程序上下文中，并在指示容器关闭 但是，Apache Tomcat特别是 似乎默认情况下会忽略所有名为 `log4j * .jar`JAR文件，这将阻止该功能正常工作。 您可能需要更改 `catalina.properties` 并从 `jarsToSkip` 属性中 `log4j * .jar` 失败为 将阻止容器正常关闭，并导致记录器上下文线程挂起。

如果其他容器跳过对Log4j JAR文件的扫描，则可能需要对它们进行类似的操作。

## 码头工人

您可能也有兴趣通过 [Docker](https://www.docker.com/)部署CAS。 有关更多信息，请参见 [本指南](Docker-Installation.html)

## 系统服务

`init.d` 或 `systemd`轻松将CAS作为Unix / Linux服务启动。 要了解 ，请 [访问此指南](Configuring-Deployment-System-Service.html)。
