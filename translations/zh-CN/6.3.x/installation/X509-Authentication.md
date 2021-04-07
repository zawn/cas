---
layout: 默认
title: CAS-X.509身份验证
category: 验证
---

# X.509验证

CAS X.509身份验证组件提供了一种机制，用于在SSL / TLS握手过程为 X.509组件需要在CAS应用程序外部进行配置，因为 SSL握手发生在CAS应用程序所在的servlet层外部。 部署架构没有特别要求 证书之外，Apache反向代理，负载平衡器SSL终止） `javax.servlet.request.X509Certificate`的请求属性被servlet容器访问。 对于直接在servlet容器处 `Apache / mod_jk`时的配置，自然会发生这种情况。对于其他体系结构，可能需要做 附加工作。

可以将CAS配置为从在CAS前面运行的代理创建的标头中提取X509证书。

## 概述

证书作为SSL（也称为TLS）初始化的一部分进行交换，当任何浏览器连接到 `https` 网站时，证书就会发生初始化。 每个浏览器中预先安装了一定数量的公共CA证书。 假定：

- 您的组织已经能够生成和分发用户可以在其浏览器中安装的证书
- 该证书中的某个位置包含一个字段，该字段包含主体名称或可以轻松地映射为CAS可以使用的主体名称的

剩下的问题是确保浏览器，服务器和Java都准备好支持 这些机构证书，理想情况下，应 这些机构证书将是在浏览器连接到CAS时唯一交换的证书。

## 流动

当浏览器通过https：URL连接到CAS时，服务器通过发送自己的证书来标识自己。 浏览器必须已经安装了一个证书，该证书标识并信任颁发CAS Server证书的CA。 如果浏览器尚未准备好信任CAS服务器，则会弹出一条错误消息，指出该服务器不受信任。

服务器发送可标识其自身的证书后，然后可以发送愿意接受证书 理想情况下，此列表将仅包含一个名称。内部机构CA的名称 ，该机构颁发内部专用内部证书的证书，该证书内部包含具有CAS主体名称的字段。

用户可以从任意数量的CA将任意数量的证书安装到浏览器中。 如果这些证书中只有一个证书 来自服务器发送的可接受CA列表中命名的CA，则大多数浏览器将自动发送该 个证书而不询问，并且可以将其中一些证书配置为不询问何时只有一个证书可能的选择。 此 表示用户体验，其中在进行一些初始设置后CAS对用户透明，并且登录自动 然而，如果托管CAS服务器的列表，并符合多个发送多个CA名 浏览器上的一个证书，那么用户将得到提示，从列表中选择一个证书。 用户交互 破坏了CAS中证书的许多目的。

请注意，CAS不控制此交换。 它由基础服务器处理。 您可能无法将控件设置为 要求浏览器访问CAS时服务器仅出售一个CA名称。 因此，如果要在CAS中使用X.509证书（ ，则在选择托管环境时应考虑此要求。 理想的情况是选择一个服务器 ，该服务器可以使用VeriSign或InCommon之类的公用证书来标识自己，但要求 客户端证书仅由内部公司/校园机构颁发。

当CAS获得控制权时，浏览器可能已经提供了用户证书，并且用户证书中存储 CAS X.509身份验证机制检查该证书 并验证该证书是由受信任的机构颁发的。 然后，CAS ，以标识一个或多个可以将 转换为应用程序期望的主体标识符的字段。

虽然一个机构可以具有一个向员工，客户， 台机器，服务和设备颁发证书的证书颁发机构，但更常见的是，该机构只有一个“根”证书 颁发机构，而在整个机构中它仅颁发了少数几个证书证书。 这些证书中的每个 标识一个辅助证书颁发机构 ，该证书颁发机构1向（学生，职员，服务器等）颁发特定类别的证书。 可以将 以信任根颁发机构，也可以隐式信任它创建的 但是，这使CAS仅与该 最不可靠的辅助证书颁发机构一样安全。 在将来的某个时刻， 要求新级别证书的产品。 他将要求创建一个 证书颁发机构，将这些证书出售给运行此新产品的计算机。 然后，他 将把这个混乱的管理交给初级 程序员或顾问。 如果CAS信任由根创建的任何颁发机构颁发的任何证书，则 将信任由已获得控制意图的人伪造的欺诈性证书 旨在作为特殊目的的独立CA。 因此，最好将CAS配置为仅从 个人颁发凭据的二级CA 证书，而不是信任机构根CA。

## 配置

通过在WAR叠加中包含以下依赖关系来启用X.509支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-x509-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

X.509处理程序从技术上讲在Web服务器终止SSL连接 的实际SSL客户端身份验证过程之后</em> _ 由于SSL对等方可以配置为接受范围广泛的 证书，因此CAS X.509处理程序提供了许多属性，这些属性对 可接受的客户端证书设置了附加限制。</p>

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#x509-authentication)。

## Web服务器配置

X.509配置需要在CAS Web应用程序之外进行大量配置。 服务器SSL组件的配置因软件而异，不在本文档讨论范围之内。 对于SSL配置，我们提供了

* 为可选的客户端证书行为配置SSL组件通常可以提供更好的用户体验。 如果不存在证书，则要求客户端证书可防止SSL协商，从而避免了 用户友好的服务器端错误消息。
* 仅接受来自受信任的颁发者（通常是您的PKI内的颁发者）的证书。
* 在允许的发行者的证书链中指定所有证书。

### 嵌入式Web服务器

尽管此处的说明通常适用于外部服务器部署（例如Apache Tomcat），但 不是硬性要求。 X.509身份验证可以使用随CAS一起提供的嵌入式Apache Tomcat 容器来实现，并且可以根据使用情况和行为来极大地简化配置和自动化步骤 证书和信任存储 的配置以及客户端身份验证的行为和实施也可以直接由CAS管理。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#embedded-container)。

#### 可选（混合）身份验证

当使用 [嵌入式Apache Tomcat容器](Configuring-Servlet-Container.html)，可能需要 才能允许用户选择X.509身份验证或通常的CAS登录流程而无需首先提示。 在这种情况下，允许用户选择通过X.509选择登录流，这时浏览器将向 提示对话框提示您选择证书，然后将其传递到CAS进行操作。

通过公开嵌入式Apache Tomcat容器的专用端口（ 可能会强制要求X.509身份验证进行登录和访问）来实现此行为。 这样做应自动 登录选项，以触发X.509的浏览器。

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#x509-authentication)。

### 外部Apache Tomcat

这里所说的一切扩展了</a>Apache Tomcat参考。</p> 

`$CATALINA_HOME/conf/server.xml` 使用一个或多个 `<Connector>` 元素配置Tomcat服务器。 这些元素中的每个 定义了一个端口号，Tomcat将在该端口号上侦听请求。 支持SSL的连接器配置有一个或两个文件 ，这些文件表示X.509证书的集合。

- `keystoreFile` 是X.509证书的集合，Tomcat将使用其中的一个将其自身标识给 浏览器。 该证书包含运行Tomcat的服务器的DNS名称，HTTP客户端 将用作URL的服务器名称部分。 它可以使用包含多个文件 证书（在这种情况下Tomcat将使用别名“Tomcat的”或下存储的证书，如果 没有找到，将使用它发现也具有相关联的私人所述第一证书钥匙）。 但是， 是明智的做法，即使用仅包含一个主机 证书的文件，当然还要使用其私钥和父证书颁发机构的链。

- `truststoreFile` 是代表证书颁发机构的X.509证书的集合， 愿意从中接受用户证书。 由于 `keystoreFile` 包含颁发证书标识服务器的CA， 的 `truststoreFile` 和 `keystoreFile` 可以在一个CAS的配置相同的其中的URL（实际的端口），其使用X.509认证 不众所周知的交互式（用户名/密码形式）登录URL已广为人知，因此 是机构内部的CA。

如果您计划通过同一端口同时支持X.509和用户ID /密码验证，则一种策略是 公用（VeriSign，Thawte）证书放入 `keystoreFile`，然后仅将机构 内部 `truststoreFile中的CA证书`。 从逻辑上讲，所有的文档中， 证书颁发机构颁发证书到 服务器，浏览器信任是完全和逻辑上独立的证书颁发机构是问题的 证书给用户，然后在服务器信任。 Java将它们分开，Tomcat将它们分开，并且如果在SSL协商期间服务器请求来自CA的用户证书而不是颁发服务器自己的标识证书 在此配置中，服务器 并且强烈建议该浏览器仅发送可映射到主体名称 

<div class="alert alert-info"><strong>差不多好了</strong><p>如果以前配置的CAS没有 
X.509身份验证，则可能已经配置 <code>keystoreFile</code>
加载了标识此服务器的证书。 您需要添加的只是 <code>truststoreFile</code> 部分。</p></div>

配置的连接器将如下所示：



```xml
<！-在端口443上定义SSL HTTP / 1.1连接器>
<！-如果未指定truststoreFile，则将使用默认的java“ cacerts”信任库–>
<Connector port="443"
    maxHttpHeaderSize="8192"
    maxThreads="150"
    minSpareThreads="25"
    maxSpareThreads="75"
    enableLookups="false"
    disableUploadTimeout="true"
    acceptCount="100"
    scheme="https"
    secure="true"
    clientAuth="want"
    sslProtocol="TLS"
    keystoreFile="/path/to/keystore.jks"
    keystorePass="secret"
    truststoreFile="/path/to/myTrustStore.jks"
    truststorePass="secret" />
```


`clientAuth =“ want”` 告诉Tomcat请求浏览器提供用户证书（如果有）。 如果要强制使用用户证书为 `“ want”` 替换为 `“ true”`。 如果您指定 `“ want”` 并且浏览器没有证书，则CAS可能会将请求转发到登录表单。

使用Tomcat时，密钥库的 `JKS` 或 `PKCS12` 当同时使用 `PKCS12` 和JKS密钥库类型 `keystoreType` 和 `truststoreType` 属性指定每个密钥库的类型。

您可以使用以下命令导入机构证书颁发机构（颁发用户证书的证书）的证书：



```bash
＃创建一个空白的密钥库（如果需要从头开始）
＃keytool -genkey -keyalg RSA -alias“ selfsigned” -keystore myTrustStore.jks -storepass“ secret” -validity 360
＃keytool -delete -alias“ selfsigned” -keystore myTrustStore .jks

keytool-导入-alias myAlias -keystore /path/to/myTrustStore.jks -file certificateForInstitutionalCA.crt
```

