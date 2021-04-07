---
layout: 默认
title: CAS-故障排除指南
category: 安装
---

# 故障排除指南

这里收集了一些常见的问题和答案。 请注意更新，因为随着时间/发展的发展，更新可能会增加。

## 查看日志

如果已配置适当的日志级别，则CAS服务器日志是确定问题根本原因的最佳资源。 具体来说，您要确保在日志配置中打开 `org.apereo` `DEBUG`

```xml
<Logger name="org.apereo" level="trace" additivity="false" includeLocation="true">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```

应用更改后，请重新启动服务器环境并观察日志文件，以更好地了解CAS行为 有关更多信息，请 [本指南](../logging/Logging.html) ，了解如何使用CAS配置日志。

请注意，以上配置块仅解决了CAS组件的日志记录行为。而不是CAS所依赖的 请查阅log4j配置，并为每个相关组件 `DEBUG` 这些通常是您进行诊断和故障排除的最佳数据源。

如果你选择的容器是 [的Apache Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/logging.html)， ，你可能也想看看你的 `catalina.out的` 和 `本地主机，XYZ.log` 日志文件，以了解更多的问题根源。

## 部署问题； X配置问题。 你能帮我吗？

[研究这个](#review-logs)。

## 如何调整/扩展MongoDb，MySQL，Spring Webflow等？

使用的外部组件的调优和配置方面的问题，并且需要实现CAS默认设置以外的更高级用例，那么最好由负责该组件开发的社区来解决 和支持。 通常， 应该始终选择最熟悉的技术，否则，请向Spring Webflow，MongoDb，Hazelcast等论坛中的

此类别中在其他地方最好回答的典型问题是：

- 如何为Apache Tomcat，Jetty等配置SSL？
- 如何在Spring Webflow中将变量从一个流传递到下一个流？
- 如何调校榛树广播群集？
- 为提高MongoDb的可用性建议的策略是什么？

## 使用 `SNAPSHOT` 版本

在某些情况下，您可能会了解到可以修复与CAS部署相关的缺陷或行为，并且建议您升级到当前可用的 `SNAPSHOT` 版本。 根据您对安装</a>选择，您将需要在部署配置中找到设置，并构建描述 *当前* CAS版本的脚本，并将其升级到下一个 `SNAPSHOT`。 生成脚本还应具有有关如何在README文件等中 `SNAPSHOT` </p> 

要找出 `SNAPSHOT` 版本，您可以查看发布时间表或CAS代码库的相应分支。 例如，如果您已经部署了CAS `2.0.4` ，并且发布计划显示下一个发行版针对 `2.0.5`，那么可用的 `SNAPSHOT` 发行版将是 `2.0.5-SNAPSHOT`。 您还可以查看分配给问题/拉动请求的里程碑设置，并确定 `SNAPSHOT` 版本。 `SNAPSHOT` 发行版始终使用 `-SNAPSHOT`后缀。 如果为问题分配的里程碑是例如 `1.2.5-RC1`，那么 `SNAPSHOT` 发行版将是 `1.2.5-RC1-SNAPSHOT`。 



## 在负载均衡器/代理后面配置SSL

[servlet容器](Configuring-Servlet-Container.html) 例如Apache Tomcat）中运行CAS，该服务器位于某种代理（例如haproxy，Apache httpd等）之后，其中代理正在处理SSL终止。 与用户的连接通过 `https`进行保护，而代理服务器和CAS服务之间的连接仅为 `http`。 

使用此设置，CAS登录屏幕可能仍会警告您有关不安全的连接。 CAS中没有设置可以让您控制/调整此设置，因为它完全由容器本身控制。 CAS唯一关心的是传入连接请求是否将自身标识为安全连接。 因此，要删除警告，您将需要查看容器的配置和文档，以了解如何确保代理与CAS之间的连接安全。 

对于 [Apache Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/config/http.html) `secure = true` 属性来调整与代理通信的连接器。



## 应用程序X“将您重定向了太多次”

“太多重定向”错误通常是由服务票证验证失败事件 是由应用程序配置错误引起的。 票证验证失败可能是由票证过期或无法识别，与SSL相关的 问题等引起的。 检查您的CAS日志，您将找到原因。



## 不接收属性

如果您的客户端应用程序未接收到属性，则需要确保：

1. 客户端正在使用能够发布属性 [CAS协议](../protocol/CAS-Protocol.html)
2. 基于＃1的客户端正在命中适当的端点以进行服务票证验证（即 `/ p3 / serviceValidate`）。
3. CAS服务器本身是 [，正确解析和获取属性](../integration/Attribute-Resolution.html)
4. 准许CAS服务器向其服务注册表中的特定客户端应用程序 [释放属性](../integration/Attribute-Release.html)

请 [本指南](../services/Service-Management.html) 以更好地了解CAS服务注册表。



## 申请未获授权

当您的CAS服务注册表中找不到请求的应用程序/服务URL时，您可能会遇到此错误。 当将 身份验证请求提交到CAS `登录` 终结点时，目标应用程序被指示为url参数， 以确定是否允许该应用程序使用CAS。 如果未找到该URL，则会显示 由于注册表中的服务定义具有通过url模式定义的能力，因此 完全可能是注册表中用于服务定义的模式配置不正确，并且不会为请求的应用程序url 

请 [本指南](../services/Service-Management.html) 以更好地了解CAS服务注册表。



## 无效/过期的CAS票证

尝试使用其过期策略指示票证 已过期的CAS票证时，您可能会遇到 `INVAILD_TICKET` 中科院日志应详细进一步解释如果票据被认为是过期了，但用于诊断目的， 你可能要调整 [票到期策略配置](../ticketing/Configuring-Ticket-Expiration-Policy.html) 删除并解决此错误。

此外，如果票证本身无法在CAS票证注册表中定位，则该票证也被视为无效。 您将需要 来观察所使用的票证，并将其与票证注册表中存在的值进行比较，以确保提供的票证ID有效。  



## 堆内存不足错误



```bash
java.lang.OutOfMemoryError：GC开销超过上限
        在java.util.Arrays.copyOfRange（Arrays.java:3658）
        在java.lang.StringBuffer.toString（StringBuffer.java:671）
        在 
```


您很可能会遇到此错误，很可能是使用了基于缓存的票证注册表（例如EhCache），其逐出策略 未正确配置。 对象和门票缓存在注册表存储后端内往往留连不是围绕长 ，他们应该或驱逐策略是不是做一个足够好的工作，由中科院到期可能会被标示干净未使用的机票。 

要进行故障排除，您可以将JVM配置为在退出之前执行堆转储，您应该立即进行设置，以便在下次发生时将一些附加信息 以下系统属性可以解决问题：



```bash
-XX：+ HeapDumpOnOutOfMemoryError -XX：HeapDumpPath =“ / path / to / jvm-dump.hprof” 
```


另外，请确保您的容器配置为具有足够的可用内存。 对于Apache Tomcat，可以配置以下设置作为环境变量：



```bash
CATALINA_OPTS = -Xms1000m -Xmx2000m
```


[JVisualVM](https://visualvm.github.io/)类的文件来配置服务器。 这将帮助您了解内存 

您可能还考虑使用JMap工具或 [YourKit Java profiler](http://www.yourkit.com/java/profiler/) 进行定期堆转储，并使用某些分析工具进行脱机分析。 

最后，查看票证注册表的驱逐策略，并确保确定对象生存期的值适合您的环境。 



## SSL & 证书



### PKIX路径构建失败



```bash
2009年9月28日下午4:13:26 org.apereo.cas.client.validation.AbstractCasProtocolUrlBasedTicketValidator restoreResponseFromServer
严重：javax.net.ssl.SSLHandshakeException：
sun.security.validator.ValidatorException：PKIX路径构建失败：
sun。 security.provider.certpath.SunCertPathBuilderException：无法找到请求的目标的有效证书路径
javax.net.ssl.SSLHandshakeException：
sun.security.validator.ValidatorException：PKIX路径构建失败：
sun.security.provider.certpath.SunCertPathBuilderException ：无法找到有效的认证路径要求的目标
      在com.sun.net.ssl.internal.ssl.Alerts.getSSLException（来源不明）
      在com.sun.net.ssl.internal.ssl.SSLSocketImpl.fatal（未知来源）
      com.sun.net.ssl.internal.ssl.Handshaker.fatalSE（未知源）
      在com.sun处
      net.ssl.internal.ssl.ClientHandshaker.serverCertificate（未知来源）
```


PKIX路径构建错误是最常见的SSL错误。 这里的问题是CAS客户端不信任 CAS服务器提供的证书。最常见的原因是在CAS服务器上 *自签名证书* 要解决此错误，请将CAS服务器 证书导入到CAS客户端的系统信任库中。 如果证书是由您自己的PKI颁发的，则最好将PKI的根证书导入到CAS客户端信任库中。 

默认情况下，Java系统信任库位于 `$JAVA_HOME/ jre / lib / security / cacerts`。 要导入的证书 **必须** 是DER编码的文件。 如果证书文件的内容是二进制的，则可能是DER编码的；否则，它可能是DER编码的。 `--- BEGIN CERTIFICATE ---`开头，则表示该文件是PEM编码的，需要转换为DER编码。 



```bash
keytool -import -keystore $JAVA_HOME/ jre / lib / security / cacerts -file tmp / cert.der -alias certName
```


如果您的计算机上安装了多个Java版本，请确保应用程序/ Web服务器指向正确的JDK / JRE版本 （证书已正确导出到的版本）。经验证的证书是 `JAVA_HOME` 可能与服务器使用的证书不同。




### 没有主题替代名称



```bash
javax.net.ssl.SSLHandshakeException：java.security.cert.CertificateException：没有主题替代名称
```


这是主机名/ SSL证书CN不匹配。 当将颁发给localhost的自签名证书放在通过IP地址访问 应该注意的是，生成带有IP地址的通用名称的证书，例如 `CN = 192.168.1.1，OU = Middleware，dc = vt，dc = edu`，在大多数情况下，如果客户端建立连接，将无法正常工作是Java。



### HTTPS主机名错误



```bash
java.lang.RuntimeException：java.io.IOException：HTTPS主机名错误：应该为 <eiger.iad.vt.edu>
    org.apereo.cas.client.validation.Saml11TicketValidator.retrieveResponseFromServer（Saml11TicketValidator.java:203）
    org.apereo.cas.client.validation .AbstractUrlBasedTicketValidator.validate（AbstractUrlBasedTicketValidator.java:185）
    org.apereo.cas.client.validation.AbstractTicketValidationFilter.doFilter
```


当CAS客户端票证验证器尝试联系CAS服务器并获得一个证书，该证书的 CN与CAS服务器的标准主机名不匹配时，通常会发生上述错误。 这种不匹配有一些常见的根本原因：

- CAS客户端配置错误
- 复杂的多层服务器环境（例如集群式CAS服务器）
- 主机名太宽，无法使用通配符证书

还值得检查您的CAS服务器用于SSL加密的证书是否与客户端检查的证书匹配。 



### 找不到与X匹配的名称



```bash
由以下原因引起：java.security.cert.CertificateException：在cas.server上找不到与cas.server匹配的名称
    在sun.security.util.HostnameChecker.matchDNS（未知源）〜[？：1.8.0_77]在sun.security.util.HostnameChecker处为

```


与上述相同。



### 通配符证书

Java对通配符证书的支持仅限于与通配符在同一域中的主机。 例如，与证书 `CN = .vt.edu` 匹配主机 **`a.vt.edu`** 和 **`b.vt.edu`**，但 *不是* **`abvt.edu`**。



### 无法识别的名称错误



```bash
javax.net.ssl.SSLProtocolException：握手警报：无法识别的名称
```


上面的错误主要发生在Oracle JDK CAS Server安装中。 在JDK中，默认情况下启用SNI（服务器名称指示）。 当HTTPD Server 没有发送回正确的服务器名称时，JDK HTTP Connection将拒绝连接，并引发上述异常。

您必须确保HTTPD服务器发送回正确的主机名。 例如 在Apache HTTPD中，必须在SSL虚拟主机中设置ServerAlias：



```bash
ServerName your.ssl-server.name
ServerAlias your.ssl-server.name
```


或者，可以通过将以下标志添加到CAS服务器的应用程序服务器配置的Java选项中来禁用JDK中的SNI检测：


```bash
-Djsse.enableSNIExtension = false
```




### 当其他一切都失败了

如果您已经阅读，理解并尝试了此页面上的所有故障排除提示，并且仍然遇到问题， ，并将其附加到 CAS邮件列表的发布中。 设置以下系统属性 `javax.net.debug = ssl`，会将SSL跟踪写入 STDOUT。 下面是如何在Tomcat Servlet容器中执行此操作的示例。

示例 `setenv.sh` Tomcat脚本如下：



```bash
＃取消注释自定义SSL密钥库的下4行
＃所有已部署的应用程序使用的
＃KEYSTORE =“$HOME/path/to/custom.keystore”
＃CATALINA_OPTS =$CATALINA_OPTS“ -Djavax.net.ssl.keyStore =$KEYSTORE”
＃CATALINA_OPTS =$CATALINA_OPTS“ -Djavax.net.ssl.keyStoreType = BKS”
＃CATALINA_OPTS =$CATALINA_OPTS“ -Djavax.net.ssl.keyStorePassword = changeit”

＃取消注释接下来的4行以允许自定义SSL信任库
＃所有已部署的应用程序
＃TRUSTSTORE =“$HOME/path/to/custom.truststore”
＃CATALINA_OPTS =$CATALINA_OPTS“ -Djavax.net.ssl.trustStore =$TRUSTSTORE”
＃CATALINA_OPTS =$CATALINA_OPTS“ -Djavax.net.ssl.trustStoreType = BKS“
＃CATALINA_OPTS =$CATALINA_OPTS” -Djavax.net.ssl.trustStorePassword = changeit“

＃取消注释下一行以在catalina.out中打印SSL调试跟踪
＃CATALINA_OPTS =$CATALINA_OPTS” -Djavax.net.debug = ssl“

出口CATALINA_OPTS
```
