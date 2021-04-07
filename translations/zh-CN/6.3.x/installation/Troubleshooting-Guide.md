---
layout: 违约
title: CAS - 故障排除指南
category: 安装
---

# 故障排除指南

这里收集了一些常见的问题和答案。 请留意更新，因为随着时间/开发的进行，这种情况可能会增加。

## 查看日志

CAS服务器日志是确定问题根源的最佳资源，前提是您已配置了相应的日志级别。 具体来说，您希望确保在日志配置中的 `组织.apereo` 包上打开 `DEBUG` 级别：

```xml
<Logger name="org.apereo" level="trace" additivity="false" includeLocation="true">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```

应用更改时，重新启动服务器环境并观察日志文件，以便更好地了解 CAS 行为 。 欲了解更多信息，请 [查看本指南](../logging/Logging.html) 如何配置日志与CAS。

请注意，上述配置块仅涉及 CAS 组件的记录行为：而不是那些中科院所依赖的 。 请咨询 log4j 配置，并打开每个相关组件的适当 `DEBUG` 日志。 这些通常是诊断和故障排除的最佳数据源。

如果您选择的容器 [Apache Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/logging.html)， 您可能还需要查看您的 `卡塔利娜.log` `` 。

## 部署问题;X 配置问题。 你能帮忙吗？

[研究这个](#review-logs)。

## 如何调整/扩展蒙古银行、MySQL、春季网络流等？

如果您对 CAS 使用的外部组件的调整和配置有疑问，并且您需要实现 CAS 默认提供的其他更高级的使用案例，则您的问题最好由负责该组件开发和支持的社区 解决。 一般来说， ，你应该总是选择一种你最熟悉的技术，或者以其他方式，拍摄一个问题， 春天的网络流，MongoDb，黑兹尔卡斯特等论坛，让专家审查和建议的想法。

这一类的典型问题在其他地方得到最好的回答是：

- 如何为阿帕奇·汤姆卡特、码头等配置SSL？
- 如何在春季网络流中将变量从一个流传递到下一个流程？
- 如何调整淡褐色星团？
- 建议的策略是什么，使蒙古数据库高度可用？

## 使用 `快照` 版本

在某些情况下，您可能会了解到 CAS 部署相关的缺陷或行为可用修复程序，并可能建议您升级到当前可用 `SNAPSHOT` 发布。 根据您 [安装](WAR-Overlay-Installation.html)选择，您需要在部署配置中找到设置，并构建描述您当前 ** CAS 版本的脚本，并将其颠簸到下一个 `SNAPSHOT`。 生成脚本还应包含有关如何在 README 文件中获取和构建快照 `快照` 版本等的其他说明。

要了解 SNAPSHOT</code> 版本适用于您的部署 `，您可以查看发布时间表或 CAS 代码库的相应分支。 例如，如果您已部署 CAS <code>2.04` 并且发布时间表显示下一个版本的目标是 `2.0.5`，则可用 `的 SNAPSHOT` 版本 `2.0.5-SNAPSHOT`。 您还可以查看分配给问题/拉取请求的里程碑设置，并确定 `SNAPSHOT` 发布。 `快照` 版本总是后缀与 `- 快照`。 如果问题指定的里程碑是 `1.2.5-RC1`，则 `SNAPSHOT` 版本将在</code>`1.2.5-RC1-SNAPSHOT。 </p>

<h2 spaces-before="0">在负载平衡器/代理后面配置 SSL</h2>

<p spaces-before="0">您可能在 <a href="Configuring-Servlet-Container.html">的伺服容器</a> （如 Apache Tomcat）后面运行 CAS，例如代理正在处理 SSL 终止的某种代理（如哈普罗西、阿帕奇 httpd 等）。 连接到用户是通过 <code>https`，但那些代理和CAS服务之间的连接只是 `http`。

使用此设置，CAS 登录屏幕仍可能警告您不安全的连接。 CAS 中没有允许您控制/调整此设置的设置，因为这完全由容器本身控制。 CAS 关心的都是传入的连接请求是否标识为安全连接。 因此，要删除警告，您需要查看容器的配置和文档，看看如何在代理和 CAS 之间保护连接。

对于 [Apache Tomcat](https://tomcat.apache.org/tomcat-9.0-doc/config/http.html)，您也许能够调整与代理通信的连接器，该连接器具有 `安全=真正的` 属性。

## 应用程序X"重定向您太多次"

"重定向"错误通常由服务票证验证失败事件引起，通常 由应用程序配置错误引起。 机票验证失败可能是由过期或未识别的机票、与 SSL 相关的 问题等引起的。 检查您的 CAS 日志，您就会找到原因。

## 未接收属性

如果您的客户申请未接收属性，则需要确保：

1. 客户端正在使用能够发布属性的 [CAS 协议](../protocol/CAS-Protocol.html) 版本。
2. 客户端基于#1，正在达到服务票验证的适当终点（即 `/p3/服务验证`）。
3. CAS 服务器本身 [正确解析和检索属性](../integration/Attribute-Resolution.html) 。
4. CAS 服务器有权在其服务注册表内 [发布](../integration/Attribute-Release.html) 特定客户端应用程序的属性。

请 [本指南](../services/Service-Management.html) 查看，以便更好地了解 CAS 服务注册表。

## 未授权的申请

当 CAS 服务注册表中找不到请求的应用程序/服务 url 时，您可能会遇到此错误。 当向 CAS 提交 认证请求 `登录` 端点时，目的地申请被指示为网址参数， 将在 CAS 服务注册表上进行检查，以确定是否允许应用 CAS。 如果找不到网址，此 消息将显示回来。 由于注册表中的服务定义能够由网址模式定义，因此 完全有可能服务定义注册表中的模式配置错误，并且不会为所请求的应用网址生成成功的匹配 。

请 [本指南](../services/Service-Management.html) 查看，以便更好地了解 CAS 服务注册表。

## 无效/过期的 CAS 门票

在尝试使用 CAS 机票时，可能会遇到 `INVAILD_TICKET` 相关错误，该机票的过期政策规定机票 已过期。 CAS 日志应进一步详细解释机票是否被视为过期，但出于诊断目的， 您可能需要调整 [票证到期策略配置](../ticketing/Configuring-Ticket-Expiration-Policy.html) 以消除和排除此错误。

此外，如果机票本身不能位于 CAS 机票注册表中，则机票也被视为无效。 您需要 来观察所使用的机票，并将其与机票注册表中存在的值进行比较，以确保所提供的机票 ID 有效。

## 出堆内存错误

```bash
java.lang.出记忆器：GC间接费用限制超过
        在java.it.Arrays.copyOfRange（阵列.java：3658）
        在java.lang.斯特林布弗.托斯特林（斯特林布弗.java：671）
 
```

当使用基于缓存的机票注册表（如 EhCache）时，可能会遇到此错误，其驱逐策略 配置不正确。 对象和门票缓存在注册表存储后端往往停留的时间超过 他们应该或驱逐政策没有做足够的工作，以清理未使用的门票，可能标记为过期的CAS。

要排除故障，您可以在退出之前配置 JVM 执行堆转储，因此您必须立即设置该转储，以便下次发生时 一些附加信息。 以下系统属性应执行该技巧：

```bash
-XX：+堆杜姆蓬莫里埃罗尔-XX：堆泵路径]"/路径/到/jvm-转储.hprof" 
```

还要确保您的容器配置为有足够的内存可用。 对于 Apache Tomcat，可以配置以下设置为环境变量：

```bash
CATALINA_OPTS-Xms1000m -Xmx2000m
```

你会想配置文件你的服务器与类似 [JVisualVM](https://visualvm.github.io/)。 这将有助于你看到你的记忆 到底发生了什么。

您也可以考虑使用 JMap 工具定期堆放，或 [您的Kit Java 探查器](http://www.yourkit.com/java/profiler/) ，然后使用某些分析工具离线分析。

最后，查看机票注册表的驱逐政策，并确保确定对象寿命的值适合您的环境。

## SSL & 证书

### PKIX 路径构建失败

```bash
9月28日， 2009 4：13：26 下午组织.apereo.cas.客户端.验证.抽象卡普罗托科尔基于代客验证器检索响应服务器
严重： javax.net.sl.SSL 手摇除：
太阳.安全.验证器 Pkix 路径构建失败：
太阳. 安全. 提供商. 证明路径. 太阳路径构建器例外： 找不到有效的认证路径到请求的目标
javax. net. ssl. sl. sl 手摇除：
太阳. 安全. 验证器. 验证器例外： Pkix 路径构建失败：
太阳。 提供商. certpath. 太阳证书路径建设者例外： 无法在 com. sun. net. ssl. 内部. ssl. Alerts. getsl 例外 （未知来源）
      在 com. sun. net. ssl. slsocket. 致命 （未知来源）找到请求的目标
      
      的有效认证路径 在 com. sun. net. sl. 内部. 握手. 致命 （未知来源）
      在 com. sun. net. ssl. 内部. sl. 握手. 致命 （未知来源）
      在 com. sun. net. sl. 内部. 客户手摇. 服务器认证 （未知来源）
```

PKIX路径构建错误是最常见的SSL错误。 这里的问题是，CAS 客户端不信任 CAS 服务器提供的证书：最常见的这种情况是由于在 CAS 服务器上使用 *自签名证书* 。 要解决此错误，将 CAS 服务器 证书导入 CAS 客户端的系统信任商店。 如果证书由您自己的 PKI 颁发，最好将 PKI 的根证书导入 CAS 客户信任商店。

默认情况下，Java 系统信托商店处于 `$JAVA_HOME/jre/lib/安全/卡塞茨`。 要导入的证书 **必须** 为 DER 编码文件。 如果证书文件的内容是二进制的，则可能是 DER 编码的：如果文件以文本 `---BEGIN证书---`开头，则该文本是 PEM 编码的，需要转换为 DER 编码。

```bash
钥匙凳 - 进口 - 钥匙店 $JAVA_HOME/jre / lib / 安全 / 卡塞茨 - 文件 tmp / 证书. der - 别名证书
```

如果您的计算机上安装了多个 java 版本，请确保应用/Web 服务器指向正确的 JDK/JRE 版本 （证书已正确导出的版本）在生成自验证证书时发生的一个常见错误是， `JAVA_HOME` 可能与服务器使用的版本不同。


### 无主题替代名称

```bash
javax.net.ssl.SSL手摇除了：爪哇.安全.证书。证书例外：不存在任何主题替代名称
```

这是主机名/SSL 证书 CN 不匹配。 当通过 IP 地址访问 的机器上放置向本地收件人签发的自签名证书时，通常会发生这种情况。 应当指出，生成具有通用名称的 IP 地址的证书，例如 `CN=192.168.1.1，OU=中间件，直流，直流+edu`，在大多数情况下，客户端连接是 Java 不起作用。

### HTTPS主机名错误

```bash
java. lang. 运行时间例外： java. io. io. Io 例外： Https 主机名错误： 应该是 <eiger.iad.vt.edu>
    组织. apereo. cas. 客户端. 验证. Saml11 滑石车验证器. 从服务器检索响应 （Saml11 代号验证器.java： 20 3）
    组织. apereo. cas. 客户端. 验证. 抽象基于彩票验证器. 验证器 （抽象基础彩票验证器.java： 185）
    组织. apereo. cas. 客户端. 验证. 抽象纸质板验证过滤器. do 过滤器
```

当 CAS 客户端票证验证器尝试联系 CAS 服务器并出示其 CN 与 CAS 服务器完全合格的主机名称不匹配的证书时，上述错误最为常见。 这种不匹配有几个共同的根本原因：

- CAS 客户端配置错误
- 复杂的多层服务器环境（例如聚类 CAS 服务器）
- 主机名称太宽，无法提供通配符证书的范围

还值得检查您的 CAS 服务器用于 SSL 加密的证书是否与客户端正在检查的证书相匹配。

### 未找到与X匹配的名称

```bash
原因： java. security. cert. 证书例外： 没有在太阳上找到
    的名称匹配 cas. 服务器. 安全. 利用. 霍斯纳梅切克. 匹配 Dns （未知来源） [？： 1.8.0_77]
    在阳光下。
```

与上文相同。

### 通配符证书

Java 对通配符证书的支持仅限于与通配符完全相同的域的主机。 例如，带有 `CN=.vt.edu` 比赛的证书 **`a.vt.edu`** 和 **`b.vt.edu`**，但 *不* **`a.b.vt.edu`**。

### 未识别的名称错误

```bash
贾瓦克斯.net.ssl.SSL普罗托科尔例外：握手警报：unrecognized_name
```

上述错误主要发生在甲骨文 JDK CAS 服务器安装中。 在 JDK 中，默认情况下启用了 SNI（服务器名称指示）。 当 HTTPD 服务器 不发送正确的服务器名称时，JDK HTTP 连接拒绝连接，并抛出上述例外。

您必须确保您的 HTTPD 服务器将返回正确的主机名。 例如 在阿帕奇HTTPD中，您必须在SSL vhost中设置服务器：

```bash
服务器名 your.ssl-server.name
服务器 your.ssl-server.name
```

或者，您可以通过在 CAS 服务器的应用程序服务器配置的 Java 选项中添加此标志来禁用 JDK 中的 SNI 检测：
```bash
-吉塞.使斯尼扩展=假
```

### 当其他一切失败时

如果您已阅读、理解并尝试过此页面上的所有故障排除提示，并且继续出现问题，请 执行 SSL 跟踪并将其附加到 CAS 邮件列表的帖子中。 当设置以下系统属性时，SSL 跟踪被写入 STDOUT， `javax.net.debug=ssl`。 在 Tomcat 伺服容器中如何做到这一点的示例如下。

汤姆卡特脚本 `setenv.sh` 示例如下：

```bash
• 取消对自定义 SSL 密钥存储
的下 4 行 # 所有已部署的应用程序使用
# KEYSTORE] "$HOME/路径/到/自定义.钥匙店"
# CATALINA_OPTS]$CATALINA_OPTS" -Djavax.net.sl.keyStore]$KEYSTORE"
# CATALINA_OPTS=$CATALINA_OPTS

"-Djavax.net.ssl.keyStore 类型=BKS"
# CATALINA_OPTS]$CATALINA_OPTS" -Djavax.net.sl.钥匙存储密码=更改它"

# 取消对下 4 行的调整，允许自定义 SSL 信任商店
# 所有部署的应用程序
使用 # 信任商店="$HOME/路径/到/定制.信托商店"
CATALINA_OPTS=$CATALINA_OPTS"-贾瓦克斯.net.ssl.信托商店=$TRUSTSTORE"
CATALINA_OPTS=$CATALINA_OPTS"-贾瓦克斯.net.sl.信托商店类型=BKS"
CATALINA_OPTS=$CATALINA_OPTS"-Djavax.net.ssl.trustStorePassword= changeit"

# 未承诺在卡塔利娜打印 SSL 调试跟踪的下一行. 出
# CATALINA_OPTS=$CATALINA_OPTS" -Djavax.net.debug=ssl" 出口CATALINA_OPTS
```
