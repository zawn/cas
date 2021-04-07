---
layout: default
title: CAS特性
category: 配置
---

# CAS特性

[中的配置文件中指定各种属性，也可以在 中将其指定为命令行开关](Configuration-Management.html#overview)。 本节提供了常用CAS属性的列表，以及使用它们的基础模块的 

<div class="alert alert-warning"><strong>有选择性</strong><p>
本部分仅作为指南。 做 <strong>NOT</strong> 
个设置集合复制/粘贴到您的CAS配置中；而是只选择您需要的属性。 
，否则请勿启用设置 
保留为 <i>参考</i>即可将设置复制到您的配置中。 所有这些想法都会导致升级麻烦，维护噩梦和过早老化。</p></div>

以下属性列表由CAS控制并提供给CAS。 对于大多数用例，每个块对应 ，该特定的CAS模块应包含在构建 和部署过程中准备的最终CAS分发中。

<div class="alert alert-info"><strong>亚尼</strong><p>请注意，对于几乎所有用例，声明和配置以下所列的
 你不应该以
明确地按摩CAS XML配置文件来设计的认证处理，
创建属性释放政策等 CAS在运行时将为您自动配置所有必需的更改。</p></div>

## 命名约定

可以很轻松地指定属性名称。 为 实例 `cas.someProperty`， `cas.some属性`， `cas.some_property` 都是有效名称。 尽管CAS接受所有形式，但有 特定组件（在CAS和其他使用的框架中）其运行时的激活以 属性值为条件，其中该属性必须在kebab case的CAS配置中指定。 这个 既适用于CAS拥有的属性，也可以通过外部库或框架（例如Spring Boot等）

> 如果可能，属性应以小写的kebab格式存储，例如cas.property-name = value。

## 一般的

许多CAS配置选项同样适用于许多模块和功能。 要了解并记下这些选项，请 [本指南](Configuration-Properties-Common.html)。

## 验证

配置属性会在CAS启动时自动进行验证，以报告配置绑定问题，如果配置模式无法识别或验证已定义的CAS设置 验证过程 默认情况下处于启用状态，可以在启动时使用特殊的 *系统属性* `SKIP_CONFIG_VALIDATION` 来跳过，该属性应设置为 `true`。

[Configuration Metadata](Configuration-Metadata-Repository.html) 处理其他验证过程，并在启动时由Spring Boot及其系列自动应用属性迁移。

## 自定义设置

以下设置可用于通过任意配置键和值来扩展CAS：

```properties
＃cas.custom.properties。[property-name]=[property-value]
```

## 配置存储

本节概述了可用于存储CAS配置和设置的策略。

### 单机版

这是默认配置模式，它指示CAS不需要与 ，而将在嵌入式独立模式下运行。

#### 按目录

默认情况下，CAS将尝试在设置名称 `cas.standalone.configuration-directory` 的给定目录内查找设置和属性，否则退回使用：

1. `/ etc / cas / config`
2. `/ opt / cas / config`
3. `/ var / cas / config`

CAS还可以加载Groovy文件以加载设置。 该文件应该在上述匹配的 目录中找到，并且应命名为 `${cas-application-name}.groovy`，例如 `cas.groovy`。 该脚本能够将活动配置文件的条件设置和适用于所有环境和配置文件的通用设置组合到一个位置，其结构类似于以下示例：

```groovy
//设置可以按单个配置文件进行过滤
配置文件{
    独立{
        cas.some.setting =“ value”
    }
}

// //适用于所有配置文件和环境
cas.common.setting =“ value”
```

#### 按文件

还存在一个 `cas.standalone.configuration-file` ，该文件可用于以文件或类路径资源的形式将 的集合直接提供给CAS。 这在以下情况下特别有用：将裸CAS服务器部署到云中而不使用 ，而为此额外配置服务器或外部目录，并且部署者希望避免覆盖嵌入式配置文件。


### 春云

CAS配置运行时将加载以下设置，它将引导整个CAS运行上下文为 它们将被放置在配置服务器本身 `src / main / resources / bootstrap.properties` 有关更多信息，请参见 [本指南](Configuration-Server-Management.html)

由Spring Cloud支持的配置服务器支持以下配置文件。

### 本国的

从外部属性/ yaml配置文件加载设置。

```properties
＃spring.profiles.active =本机

＃CAS应该监视的配置目录，以查找设置。
＃spring.cloud.config.server.native.search-locations = file：/// etc / cas / config
```

### Git存储库

允许CAS Spring Cloud配置服务器从内部/外部Git存储库加载设置。 然后，这允许CAS成为配置服务器的客户端，并在需要时通过HTTP消耗设置。

```properties
＃spring.profiles.active = default

＃包含CAS设置的git存储库的位置。
＃该位置可以指向HTTP / SSH /目录。
＃spring.cloud.config.server.git.uri = https：//github.com/repoName/config
＃spring.cloud.config.server.git.uri = file：//${user.home}/ config

＃使用的凭据验证git请求，特别是使用HTTPS时为
 如果通过SSH连接到存储库，请记住使用
＃在SSH代理中注册您的公共密钥，就像通常在任何其他公共存储库中

＃spring.cloud.config.server.git.username =
＃spring.cloud.config.server.git.password =
```

上面的配置也适用于基于在线git的存储库，例如Github，BitBucket等。

### 领事

允许CAS Spring Cloud配置服务器从 [HashiCorp的Consul](../installation/Service-Discovery-Guide-Consul.html)加载设置。

```properties
＃spring.cloud.consul.config.enabled = true
＃spring.cloud.consul.config.prefix =配置
＃spring.cloud.consul.config.default-context = apps
＃spring.cloud.consul.config。 profile-separator = ::

＃spring.cloud.consul.config.watch.delay = 1000
＃spring.cloud.consul.config.watch.enabled = false
```

### 金库

允许CAS Spring Cloud配置服务器从 [HashiCorp的Vault](Configuration-Properties-Security.html)加载设置。

```properties
＃spring.cloud.vault.host = 127.0.0.1
＃spring.cloud.vault.port = 8200
＃spring.cloud.vault.connection-timeout = 3000
＃spring.cloud.vault.read-timeout = 5000
＃spring.cloud.vault.enabled = true
＃spring.cloud.vault.fail-fast = true
＃spring.cloud.vault.scheme = http
```

#### 令牌认证

令牌是Vault中身份验证的核心方法。 令牌认证要求提供静态令牌。

```properties
＃spring.cloud.vault.authentication =令牌
＃spring.cloud.vault.token = 1305dd6a-a754-f145-3563-2fa90b0773b7
```

#### AppID验证

保管箱支持由两个难以猜测的令牌组成的AppId身份验证。 AppId默认为 `spring.application.name` ，它是静态配置的。 第二个令牌是UserId，它是由应用程序确定的部分，通常与运行时环境有关。 Spring Cloud Vault Config支持IP地址，Mac地址和静态 UserId（例如，通过系统属性提供）。 IP和Mac地址表示为十六进制编码的SHA256哈希。

使用IP地址：

```bash
出口IP_ADDRESS =`echo -n 192.168.99.1 | sha256sum`
```

```properties
＃spring.cloud.vault.authentication = APPID
＃spring.cloud.vault.app-id.user-id =$IP_ADDRESS
```

使用MAC地址：

```bash
输出 $MAC_ADDRESS=`echo -n ABCDEFGH | sha256sum`
```

```properties
＃spring.cloud.vault.authentication = APPID
＃spring.cloud.vault.app-id.user-id =$MAC_ADDRESS
＃spring.cloud.vault.app-id.network-interface = eth0
```

#### Kubernetes身份验证

Kubernetes身份验证机制允许使用Kubernetes服务帐户令牌与Vault进行身份验证。 身份验证基于角色，并且角色绑定到服务帐户名称和名称空间。

```properties
＃spring.cloud.vault.authentication = KUBERNETES
＃spring.cloud.vault.kubernetes.role = my-dev-role
＃spring.cloud.vault.kubernetes.service-account-token-file：/ var / run /秘密/kubernetes.io/serviceaccount/令牌
```

#### 通用后端v1

```properties
＃spring.cloud.vault.generic.enabled = true
＃spring.cloud.vault.generic.backend =秘密
```

#### KV后端v2

```properties
＃spring.cloud.vault.kv.enabled = true
＃spring.cloud.vault.kv.backend =秘密
```

### MongoDb

允许CAS Spring Cloud配置服务器从MongoDb实例加载设置。

```properties
＃cas.spring.cloud.mongo.uri = mongodb：// casuser：Mellon@ds135522.mlab.com：35522 / jasigcas
```

### Azure KeyVault的秘密

允许CAS Spring Cloud配置服务器从Microsoft Azure的KeyVault实例加载设置。

```properties
＃azure.keyvault.enabled = true
＃azure.keyvault.uri =在此处放置您的Azure-keyvault-uri
＃azure.keyvault.client-id =在此处放置您的Azure-client-id
＃ azure.keyvault.client-key =在这里输入您的azure-client-key-here
＃azure.keyvault.token-acquire-timeout-seconds = 60
```

### 动物园管理员

允许CAS Spring Cloud配置服务器从Apache ZooKeeper实例加载设置。

```properties
＃spring.cloud.zookeeper.connect-string =本地主机：2181
＃spring.cloud.zookeeper.enabled = true
＃spring.cloud.zookeeper.config.enabled = true
＃spring.cloud.zookeeper.max-retries = 10
＃spring.cloud.zookeeper.config.root = cas / config
```

### 亚马逊秘密经理

此功能的常见AWS设置在配置键 `cas.spring.cloud.aws.secrets-manager`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 可用。

### 亚马逊参数存储

此功能的常见AWS设置在配置键 `cas.spring.cloud.aws.ssm`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 可用。

### 亚马逊S3

可以使用概述为 [策略（此处为](Configuration-Management.html#overview) 传递以下设置，以使CAS建立连接， 使用配置键 `cas.spring.cloud.aws.s3`传递。

```properties
＃ ${configuration-key}.bucket-name = cas-properties
```

此功能的常见AWS设置在配置键 `cas.spring.cloud.aws.s3`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 可用。

### DynamoDb

在配置键 `cas.spring.cloud.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) ，可以使用此功能的常用AWS设置。

### JDBC

允许CAS Spring Cloud配置服务器从RDBMS实例加载设置。 在配置键 `cas.spring.cloud.jdbc`下，此功能 [此处](Configuration-Properties-Common.html#database-settings) 可用。

```properties
＃cas.spring.cloud.jdbc.sql =从CAS_SETTINGS_TABLE中选择ID，名称，值
```

### 休息

允许CAS Spring Cloud配置服务器从REST API加载设置。

```properties
＃cas.spring.cloud.rest.url =
＃cas.spring.cloud.rest.basic-auth-用户名=
＃cas.spring.cloud.rest.basic-auth-password =
＃cas.spring.cloud .rest.method =
＃cas.spring.cloud.rest.headers = Header1：Value1; Header2：Value2
```

## 配置安全

要了解更多有关敏感CAS设置怎么能 担保， [请查看本指南](Configuration-Properties-Security.html)。

### 单机版

```properties
＃cas.standalone.configuration-security.alg = PBEWithMD5AndTripleDES
＃cas.standalone.configuration-security.provider = BC
＃cas.standalone.configuration-security.iterations =
＃cas.standalone.configuration-security.psw =
```

上述设置可使用任何的被传递到CAS [策略概要这里](Configuration-Management.html#overview)， 虽然它可能是更安全的将它们传递给CAS如任一命令行或系统属性。

### 春云

如果使用了Spring Cloud配置服务器，则通过Spring Cloud加密和解密配置。

```properties
＃spring.cloud.config.server.encrypt.enabled = true

＃crypto.keyStore.location = file：///etc/cas/casconfigserver.jks
＃crypto.keyStore.password = keystorePassword
＃crypto.keyStore.alias
＃crypto.keyStore.secret =更改我
```

## 云配置总线

CAS使用Spring Cloud Bus来管理分布式部署中的配置。 Spring Cloud Bus通过轻量级消息代理

```properties
＃spring.cloud.bus.enabled = false
＃spring.cloud.bus.refresh.enabled = true
＃spring.cloud.bus.env.enabled = true
＃spring.cloud.bus.destination = CasCloudBus
＃spring .cloud.bus.ack.enabled = true
```

要了解更多有关这个话题， [请参阅本指南](Configuration-Management.html)。

### 兔子MQ

通过 [RabbitMQ](http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_rabbitmq_binder) 中的其他节点广播CAS配置更新。

```properties
＃spring.rabbitmq.host =
＃spring.rabbitmq.port =
＃spring.rabbitmq.username =
＃spring.rabbitmq.password =

＃或以上全部在一行
＃spring.rabbitmq.addresses =
```

### 卡夫卡

通过 [Kafka](http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_apache_kafka_binder)将CAS配置更新广播到群集 中的其他节点。

```properties
＃spring.cloud.stream.bindings.output.content-type = application / json
＃spring.cloud.stream.kafka.binder.zkNodes = ...
＃spring.cloud.stream.kafka.binder.brokers = ...
```

## 嵌入式容器

以下属性与CAS附带的嵌入式容器有关。

```properties
server.servlet.context-path = / cas
server.port = 8443
server.ssl.key-store = file：/ etc / cas / thekeystore
server.ssl.key-store-password = changeit
server.ssl。 key-password = changeit

＃server.ssl.enabled = true
＃server.ssl.ciphers =
＃server.ssl.key-alias =
＃server.ssl.key-store-provider =
＃server.ssl。 key-store-type =
＃server.ssl.protocol =

＃server.max-http-header-size = 2097152
＃server.use-forward-headers = true
＃server.connection-timeout = 20000
```

### X.509客户端身份验证

```properties
＃server.ssl.trust-store =
＃server.ssl.trust-store-password =
＃server.ssl.trust-store-provider =
＃server.ssl.trust-store-type =
＃server.ssl .client-auth = NEED | NONE | WANT
```

客户端身份验证类型支持以下值：

| 类型     | 描述                   |
| ------ | -------------------- |
| `需要`   | 客户端身份验证是必需的，也是必需的。   |
| `没有任何` | 不需要客户端身份验证。          |
| `想`    | 客户端身份验证是必需的，但不是强制性的。 |

### 嵌入式码头集装箱

以下设置会影响嵌入式Jetty容器的运行时行为。

```properties
＃server.jetty.acceptors = -1       

＃server.jetty.accesslog.append = false
＃server.jetty.accesslog.custom-format =
＃server.jetty.accesslog.enabled = false
＃server.jetty.accesslog .file-date-format =
＃server.jetty.accesslog.filename =
＃server.jetty.accesslog.format =
＃server.jetty.accesslog.ignore-paths =
＃server.jetty.accesslog.retention-period = 31

＃server.jetty.connection-idle-timeout =
＃server.jetty.max-http-form-post-size = 200000B
＃server.jetty.max-threads = 200
＃server.jetty.min-线程= 8
＃server.jetty.selectors = -1
＃server.jetty.thread-idle-timeout = -1
```

### 嵌入式Apache Tomcat容器

以下设置会影响嵌入式Apache Tomcat容器的运行时行为。

```properties
＃server.tomcat.basedir = build / tomcat

＃server.tomcat.accesslog.enabled = true
＃server.tomcat.accesslog.pattern =%t %a “%r” %s （%D ms）
＃server.tomcat.accesslog.suffix = .log

＃server.tomcat.max-http-post-size = 20971520
＃server.tomcat.max-threads = 5
＃server.tomcat.port-header = X转发端口
＃server.tomcat。 protocol-header = X-Forwarded-Proto
＃server.tomcat.protocol-header-https-value = https
＃server.tomcat.remote-ip-header = X-FORWARDED-FOR
＃server.tomcat.uri-encoding = UTF-8

＃cas.server.tomcat.server-name = Apereo CAS
```

#### HTTP代理

如果您决定在嵌入式Tomcat容器中和非安全端口 上运行没有任何SSL配置的CAS，但希望自定义链接到正在运行的端口（即 `8080`）的连接器配置，请执行以下设置可申请：

```properties
＃cas.server.tomcat.http-proxy.enabled = true
＃cas.server.tomcat.http-proxy.secure = true
＃cas.server.tomcat.http-proxy.protocol = AJP / 1.3
＃cas。 server.tomcat.http-proxy.scheme = https
＃cas.server.tomcat.http-proxy.redirect-port =
＃cas.server.tomcat.http-proxy.proxy-port =
＃cas.server.tomcat .http-proxy.attributes.attribute-name = attributeValue
```

#### HTTP

除了链接到 `server.port` 设置 之外，还为嵌入式Tomcat容器启用HTTP连接。

```properties
＃cas.server.tomcat.http.port = 8080
＃cas.server.tomcat.http.protocol = org.apache.coyote.http11.Http11NioProtocol
＃cas.server.tomcat.http.enabled = true
＃cas。 server.tomcat.http.attributes.attribute-name = attributeValue
```

#### AJP

为嵌入式Tomcat容器启用AJP连接，

```properties
＃cas.server.tomcat.ajp.secure = false
＃cas.server.tomcat.ajp.enabled = false
＃cas.server.tomcat.ajp.proxy-port = -1
＃cas.server.tomcat.ajp .protocol = AJP / 1.3
＃cas.server.tomcat.ajp.async-timeout = 5000
＃cas.server.tomcat.ajp.scheme = http
＃cas.server.tomcat.ajp.max-post-size = 20971520
＃cas.server.tomcat.ajp.port = 8009
＃cas.server.tomcat.ajp.enable-lookups = false
＃cas.server.tomcat.ajp.redirect-port = -1
＃cas.server .tomcat.ajp.allow-trace = false
＃cas.server.tomcat.ajp.attributes.attribute-name = attributeValue
```

#### SSL阀

Tomcat SSLValve是一种通过HTTP标头从在Tomcat前面运行的SSL代理（例如HAProxy或BigIP F5） 如果启用此，请确保您的代理是保证 ，这头不与客户端（例如浏览器）发起。

```properties
＃cas.server.tomcat.ssl-valve.enabled = false
＃cas.server.tomcat.ssl-valve.ssl-client-cert-header = ssl_client_cert
＃cas.server.tomcat.ssl-valve.ssl-cipher -header = ssl_cipher
＃cas.server.tomcat.ssl-valve.ssl-session-id-header = ssl_session_id
＃cas.server.tomcat.ssl-valve.ssl-cipher-user-key-size-header = ssl_cipher_usekeysize
```

HAProxy配置示例（代码段）：使用可选的cert配置SSL前端，重定向到cas，如果提供了cert，则将其放在标头中。

```
前端web-vip
  绑定192.168.2.10:443 ssl crt /var/lib/haproxy/certs/www.example.com.pem ca文件/var/lib/haproxy/certs/ca.pem验证可选
  模式http
  acl www-cert ssl_fc_sni如果 { www.example.com }
  acl空路径路径/
  http-请求重定向位置/ cas /如果空路径www-cert
  http-请求del-header ssl_client_cert除非 { ssl_fc_has_crt }
  http-request set-header ssl_client_cert- --- BEGIN \ CERTIFICATE ----- \％[ssl_c_der，base64] \ ----- END \ CERTIFICATE ----- \如果 { ssl_fc_has_crt }
  acl cas-path path_beg -i / cas
  reqadd X-Forwarded -Proto：\ https
  use_backend cas-pool如果cas-path

后端cas-pool
  选项httpclose
  选项forwardfor
  cookie SERVERID-cas插入间接nocache
  服务器cas-1 192.168.2.10:8080检查cookie cas-1
```

#### 延长检修阀

为嵌入式Tomcat容器启用 [扩展访问日志](https://tomcat.apache.org/tomcat-9.0-doc/config/valve.html#Extended_Access_Log_Valve)

```properties
＃cas.server.tomcat.ext-access-log.enabled = false
＃cas.server.tomcat.ext-access-log.pattern = c-ip s-ip cs-uri sc状态时间x-线程名xH（安全）xH（remoteUser）
＃cas.server.tomcat.ext-access-log.suffix = .log
＃cas.server.tomcat.ext-access-log.prefix = localhost_access_extended
＃cas.server.tomcat.ext -access-log.directory =
```

#### 改写阀

为嵌入式Tomcat容器启用 [重写阀](https://tomcat.apache.org/tomcat-9.0-doc/rewrite.html)

```properties
＃cas.server.tomcat.rewrite-valve.location = classpath：//容器/tomcat/rewrite.config
```

#### 基本认证

为嵌入式Apache Tomcat启用基本身份验证。

```properties
＃cas.server.tomcat.basic-authn.enabled = true
＃cas.server.tomcat.basic-authn.security-roles[0]= admin
＃cas.server.tomcat.basic-authn.auth-roles[0]= admin
＃cas.server.tomcat.basic-authn.patterns[0]= / *
```

#### Apache可移植运行时（APR）

Tomcat可以使用 [Apache Portable Runtime](https://tomcat.apache.org/tomcat-9.0-doc/apr.html) 提供出色的 可扩展性，性能以及与本机服务器技术的更好集成。

```properties
＃cas.server.tomcat.apr.enabled = false

＃cas.server.tomcat.apr.ssl-protocol =
＃cas.server.tomcat.apr.ssl-verify-depth = 10
＃cas.server.tomcat.apr.ssl-verify-depth = 10 .apr.ssl-verify-client = require
＃cas.server.tomcat.apr.ssl-cipher-suite =
＃cas.server.tomcat.apr.ssl-disable-compression = false
＃cas.server.tomcat .apr.ssl-honor-cipher-order = false

＃cas.server.tomcat.apr.ssl-certificate-chain-file =
＃cas.server.tomcat.apr.ssl-ca-certificate-file =
＃ cas.server.tomcat.apr.ssl-certificate-key-file =
＃cas.server.tomcat.apr.ssl-certificate-file =
```

启用APR需要以下JVM系统属性，该属性指示APR库二进制文件的位置（即 `usr / local / opt / tomcat-native / lib`）：

```bash
-Djava.library.path = / path / to / tomcat-native / lib
```

可以为APR连接器分配SSLHostConfig元素，如下所示：

```properties
＃cas.server.tomcat.apr.ssl-host-config.enabled = false
＃cas.server.tomcat.apr.ssl-host-config.revocation-enabled = false
＃cas.server.tomcat.apr.ssl -host-config.ca-certificate-file = false
＃cas.server.tomcat.apr.ssl-host-config.host-name =
＃cas.server.tomcat.apr.ssl-host-config.ssl-协议=
＃cas.server.tomcat.apr.ssl-host-config.protocols =全部
＃cas.server.tomcat.apr.ssl-host-config.insecure-
＃cas.server.tomcat。 apr.ssl-host-config.certificate-verification-depth = 10

＃cas.server.tomcat.apr.ssl-host-config.certificates[0].certificate-file =
＃cas.server.tomcat.apr.ssl- host-config.certificates[0].certificate-key-file =
＃cas.server.tomcat.apr.ssl-host-config.certificates[0].certificate-key-password =
＃cas.server.tomcat.apr.ssl- host-config.certificates[0].certificate-chain-file =
＃cas.server.tomcat.apr.ssl-host-config.certificates[0].type =未定义
```

#### 连接器IO

```properties
＃cas.server.tomcat.socket.app-read-buf-size = 0
＃cas.server.tomcat.socket.app-write-buf-size = 0
＃cas.server.tomcat.socket.buffer-pool = 0
＃cas.server.tomcat.socket.performance-connection-time = -1
＃cas.server.tomcat.socket.performance-latency = -1
＃cas.server.tomcat.socket.performance-bandwidth = -1
```

#### 会话群集 & 复制

启用内存中会话复制以复制Web应用程序会话增量。

| 聚类类型 | 描述                                                                                                                                                         |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `默认` | `clusterMembers`通过静态定义的集群成员发现集群成员。 [使用McastService](http://tomcat.apache.org/tomcat-9.0-doc/cluster-howto.html)                                            |
| `云`  | 适用于通过访问Kubernetes API或对Kubernetes服务的成员进行DNS查找而发现成员的Kubernetes。 [文档](https://cwiki.apache.org/confluence/display/TOMCAT/ClusteringCloud) 尚不完善，请参阅代码以获取详细信息。 |

| 会员提供者        | 描述                                                                                                                                                                                                                                                                                                                                                                                         |
| ------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `Kubernetes` | 使用 [Kubernetes API](https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/membership/cloud/KubernetesMembershipProvider.java) 查找部署中的其他Pod。 通过容器中设置的环境变量中的信息来发现和访问API。 KUBERNETES_NAMESPACE环境变量用于查询命名空间中的Pod，它将将该命名空间中的其他Pod视为潜在的群集成员，但是可以使用KUBERNETES_LABELS环境变量（用作 [标签选择器](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#api)对其进行过滤。 |
| `域名系统`       | 使用 [DNS查找](https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/membership/cloud/DNSMembershipProvider.java) 查找由DNS_MEMBERSHIP_SERVICE_NAME环境变量指定的DNS名称后面的群集成员的地址。 可在Kubernetes中使用，但不依赖Kubernetes。                                                                                                                                                               |
| `会员资格提供者` 班  | 使用您选择 [成员资格提供程序实现](https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/MembershipProvider.java)                                                                                                                                                                                                                                                                   |

大多数设置适用于 `DEFAULT` 集群类型，如果多播发现不起作用，则需要通过 `clusterMembers` `cloudMembershipProvider` 设置适用于 `CLOUD` 类型。

```properties
＃cas.server.tomcat.clustering.enabled = false
＃cas.server.tomcat.clustering.clustering-type = DEFAULT | CLOUD
＃cas.server.tomcat.clustering.cluster-members = ip-address：port：index
＃cas.server.tomcat.clustering.cloud-membership-provider = kubernetes | dns | [MembershipProvider impl类名]（https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/ tribes / MembershipProvider.java）
＃cas.server.tomcat.clustering.expire-sessions-on-shutdown = false
＃cas.server.tomcat.clustering.channel-send-options = 8

＃cas.server.tomcat。 clustering.receiver-port = 4000
＃cas.server.tomcat.clustering.receiver-timeout = 5000
＃cas.server.tomcat.clustering.receiver-max-threads = 6
＃cas.server.tomcat.clustering.receiver -address =自动
＃cas.server.tomcat.clustering.receiver-auto-bind = 100

＃cas.server.tomcat.clustering.membership-port = 45564
＃cas.server.tomcat.clustering.membership-address = 228.0.0.4
＃cas.server.tomcat.clustering.membership-frequency = 500
＃cas.server.tomcat.clustering。 Membership-drop-time = 3000
＃cas.server.tomcat.clustering.membership-recovery-enabled = true
＃cas.server.tomcat.clustering.membership-local-loopback-disabled = false
＃cas.server.tomcat .clustering.membership-recovery-counter = 10

＃cas.server.tomcat.clustering.manager-type = DELTA | BACKUP
```

## CAS服务器

识别CAS服务器。 `名称` 和 `前缀` 始终是必需的设置。

CAS主机将自动附加到CAS生成的票证ID。 如果未指定，CAS将自动检测并使用其中一个。

```properties
＃cas.server.name = https：//cas.example.org：8443
＃cas.server.prefix = https：//cas.example.org：8443 / cas 
＃cas.server.scope = example.org
＃cas.host.name =
```

## 会话复制

控制某些CAS功能（例如OAuth或OpenID Connect）的会话复制方面， 允许将会话和身份验证配置文件数据作为cookie与客户端一起保留。

常见的cookie属性在这里 [下](Configuration-Properties-Common.html#cookie-properties) 下配置密钥 `cas.session-replication.cookie`。

```properties
＃cas.session-replication.auto-configure-cookie-path = true
```

## CAS横幅

启动时，CAS将显示横幅以及一些诊断信息。 为了跳过此步骤并进行总结，请将系统属性设置为 `-DCAS_BANNER_SKIP = true`。

### 更新检查

CAS也可以有条件地配置为作为横幅的一部分报告是否有更新的CAS版本可用于升级。 默认情况下，此检查是关闭的，并且可以使用 `-DCAS_UPDATE_CHECK_ENABLED = true`的系统属性来启用。

## 执行器管理端点

`/执行器` 端点的访问控制和设置，该端点提供管理功能和对CAS软件的监督。

要了解更多有关这个话题， [请参阅本指南](../monitoring/Monitoring-Statistics.html)。

```properties
＃management.endpoints.by-default-true = true
＃management.endpoints.web.base-path = / actuator

＃management.endpoints.web.exposure.include =信息，运行状况，状态，配置元数据
＃management.endpoints .web.exposure.exclude =

＃management.endpoints.jmx.exposure.exclude = *
＃management.endpoints.jmx.exposure.include =
```

### 基本身份验证安全

可以通过以下设置来定义基本身份验证的凭据：

```properties
＃spring.security.user.name = casuser
＃spring.security.user.password =
＃spring.security.user.roles =
```

### JAAS身份验证安全性

可以通过以下设置配置用于端点安全性的JAAS身份验证：

```properties
＃cas.monitor.endpoints.jaas.refresh-configuration-on-startup = true
＃cas.monitor.endpoints.jaas.login-config = file：/etc/cas/config/jaas.conf
＃cas.monitor。 endpoints.jaas.login-context-name = CAS
```

### LDAP验证安全性

此功能的共享LDAP设置在配置键 `cas.monitor.endpoints.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。

可以通过以下设置另外配置用于端点安全性的LDAP身份验证：

```properties
＃cas.monitor.endpoints.ldap.ldap-authz.role属性= uugid
＃cas.monitor.endpoints.ldap.ldap-authz.role前缀= ROLE_
＃cas.monitor.endpoints.ldap.ldap-AuthZ的.allow-multiple-results = false
＃cas.monitor.endpoints.ldap.ldap-authz.group-attribute =
＃cas.monitor.endpoints.ldap.ldap-authz.group-prefix =
＃cas.monitor。 endpoints.ldap.ldap-authz.group-filter =
＃cas.monitor.endpoints.ldap.ldap-authz.group-base-dn =
＃cas.monitor.endpoints.ldap.ldap-authz.base-dn =
＃cas.monitor.endpoints.ldap.ldap-authz.search-filter =
```

### JDBC身份验证安全性

此功能的共享数据库设置在配置键 `cas.monitor.endpoints.jdbc`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。

可以通过以下设置另外配置用于端点安全性的JDBC身份验证：

```properties
＃cas.monitor.endpoints.jdbc.role-prefix =
＃cas.monitor.endpoints.jdbc.query =
```

此功能的密码编码设置在配置键 `cas.monitor.endpoints.jdbc`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

### 启用端点

要确定某个端点是否可用，所有端点的计算顺序如下：

1. 在CAS设置中查阅了单个端点的 `启用` `info`

```properties
＃management.endpoint。<endpoint-name>.enabled = true
```

2. 如果未定义，请从CAS设置中咨询全局端点安全性。
3. 如果未定义，则查询CAS中端点的默认内置设置，默认情况下 `false`

</a>列出了许多可用的端点ID。</p> 

端点也可以映射到自定义的任意端点。 例如，为了重新映射 `健康` 端点 `健康检查`， 指定以下设置：



```properties
＃management.endpoints.web.path-mapping.health = healthcheck
```




### 健康终点

`management.endpoint.health.show-details` 通过以下条件将 `运行状况` 终结点配置为显示详细信息：

| 网址    | 描述                                                       |
| ----- | -------------------------------------------------------- |
| `绝不`  | 永远不要显示健康监视器的详细信息。                                        |
| `总是`  | 始终显示健康监视器的详细信息。                                          |
| `授权时` | 详细信息仅显示给授权用户。 `management.endpoint.health.roles`来配置授权角色。 |




```properties
＃management.endpoint.health.show-details =从不
```


`运行状况` 端点的结果和详细信息由许多运行状况指示器组件生成，这些组件可以监视不同的系统，例如LDAP连接 池，数据库连接等。 此类运行状况指示器默认情况下处于关闭状态，并且可以通过以下设置单独控制和打开：



```properties
＃management.health。<name>.enabled = true
＃management.health.defaults.enabled = false 
```


如果存在适当的CAS功能，则可以使用以下健康指示器名称：

| 健康指标                                           | 描述                                 |
| ---------------------------------------------- | ---------------------------------- |
| `memoryHealthIndicator`                        | 报告有关CAS JVM内存使用情况等的健康状况。           |
| `systemHealthIndicator`                        | 报告CAS服务器系统的运行状况（负载，正常运行时间，堆，CPU等）。 |
| `sessionHealthIndicator`                       | 报告有关CAS票证和SSO会话使用情况的健康状态。          |
| `duoSecurityHealthIndicator`                   | 报告有关Duo Security API的运行状况。         |
| `ehcacheHealthIndicator`                       | 报告有关Ehcache缓存的运行状况。                |
| `hazelcastHealthIndicator`                     | 报告有关Hazelcast缓存的运行状况。              |
| `dataSourceHealthIndicator`                    | 返回有关JDBC连接的运行状况的报告。                |
| `pooledLdapConnectionFactoryHealthIndicator`   | 报告LDAP连接池的运行状况。                    |
| `memcachedHealthIndicator`                     | 报告有关Memcached连接的运行状况。              |
| `mongoHealthIndicator`                         | 报告MongoDb连接的健康状况。                  |
| `samlRegisteredServiceMetadataHealthIndicator` | 报告有关SAML2服务提供商元数据源的运行状况。           |




### 端点安全

CAS激活的全局端点安全性配置可以在配置键 `cas.monitor.endpoints.endpoint下进行控制。<endpoint-name>`。 `defaults`  的特殊终结点，可以用作控制所有终结点安全性的快捷方式。 可以通过特殊的登录表单来允许通过Web访问端点 ，该表单的访问和状态可以通过以下方式控制：



```properties
＃cas.monitor.endpoints.form-login-enabled = false
```


请注意，必须先启用任何单个端点，然后才能应用任何安全性。 使用以下设置来控制所有端点的安全性：



```properties
＃ ${configuration-key}.required-roles[0]=
＃ ${configuration-key}.required-authorities[0]=
＃ ${configuration-key}.required-ip-addresss[0]=
＃ ${configuration-key}[0]= PERMIT | ANONYMOUS | DENY | AUTHENTICATED | ROLE | AUTHORITY | IP_ADDRESS
```


每个端点均允许使用以下访问级别：

| 类型     | 描述                      |
| ------ | ----------------------- |
| `允许`   | 允许对端点的开放访问。             |
| `匿名的`  | 允许匿名访问端点。               |
| `否定`   | 默认。 阻止对端点的访问。           |
| `认证的`  | 要求对端点进行身份验证访问。          |
| `角色`   | 要求对端点进行身份验证的访问以及角色要求。   |
| `权威`   | 要求对端点进行经过身份验证的访问以及权限要求。 |
| `IP地址` | 要求使用IP地址集合对端点进行身份验证访问。  |




### Spring Boot管理服务器

要了解更多有关这个话题， [请参阅本指南](../monitoring/Configuring-Monitoring-Administration.html)。



```properties
＃spring.boot.admin.client.url = https：//bootadmin.example.org：8444
＃spring.boot.admin.client.instance.service-base-url =${cas.server.prefix}
＃spring.boot.admin.client .instance.name = Apereo CAS
＃如果通过基本认证保护Spring Boot Admin端点：
＃spring.boot.admin.client.username =
＃spring.boot.admin.client.password =
＃如果CAS端点通过基本
＃spring.boot.admin.client.instance.metadata.user.name =
＃spring.boot.admin.client.instance.metadata.user.password =
```




### Java旋律

要了解更多有关这个话题， [请参阅本指南](../monitoring/Configuring-Monitoring-JavaMelody.html)。



```properties
＃javamelody.enabled = true
＃javamelody.excluded-datasources =一个，两个等
＃javamelody.spring-monitoring-enabled = true
＃javamelody.init-parameters.log = true
＃javamelody.init-parameters.url -exclude图案=（/ webjars /.* | / CSS /.* | /图像/.* | /字体/.* | / JS /.*）
＃javamelody.init-parameters.monitoring路径= /监视

＃通过IP正则表达式模式控制访问
＃javamelody.init-parameters.allowed-addr-pattern =。+
＃通过Basic AuthN控制访问
＃javamelody.init-parameters.authorized-users = admin：pwd
```




## 网络应用会议

控制CAS Web应用程序会话行为 （由基础Servlet容器引擎处理）。



```properties
＃server.servlet.session.timeout = PT30S
＃server.servlet.session.cookie.http-only = true
＃server.servlet.session.tracking-modes = COOKIE
```




## 观看次数

控制CAS应如何对待视图和其他UI元素。

要了解更多有关这个话题， [请参阅本指南](../ux/User-Interface-Customization-Views.html)。



```properties
＃spring.thymeleaf.encoding = UTF-8

＃控制视图是否应由CAS缓存。
＃启用后，临时查看的机会不会自动为
＃CAS直到重新启动为止。 小增量性能
＃有望得到改善。
＃spring.thymeleaf.cache = true

＃指示CAS在以下位置定位视图。
＃此位置可以外部
＃cas Web应用程序之外的目录。
＃spring.thymeleaf.prefix = classpath：/ templates /

＃定义如果身份验证请求中未提供任何服务，CAS可以重定向到的默认URL。
＃cas.view.default-redirect-url = https：//www.github.com

＃CAS视图可能位于
＃Web应用程序上下文
＃可以处理通过Thymeleaf。
＃cas.view.template-prefixes[0]=文件：/// etc / cas / templates
```




## 自定义登录字段



```properties
＃cas.view.custom-login-form-fields。[field-name].message-bundle-key =
＃cas.view.custom-login-form-fields。[field-name].required = true
＃cas.view.custom-login-form-fields。[field-name].converter =
```




### CAS v1



```properties
＃cas.view.cas1.attribute-renderer-type = DEFAULT | VALUES_PER_LINE
```




### CAS v2



```properties
＃cas.view.cas2.v3-forward-compatible = false
＃cas.view.cas2.success =协议/2.0/casServiceValidation成功
＃cas.view.cas2.failure =协议/2.0/casServiceValidationFailure
＃cas.view。 cas2.proxy.success =协议/2.0/casProxySuccessView
＃cas.view.cas2.proxy.failure =协议/2.0/casProxyFailureView
```




### CAS v3



```properties
＃cas.view.cas3.success =协议/3.0/casServiceValidationSuccess
＃cas.view.cas3.failure =协议/3.0/casServiceValidationFailure
＃cas.view.cas3.attribute-renderer-type = DEFAULT | INLINE
```




### 宁静的景色

通过REST控制CAS视图的分辨率。 此功能的RESTful设置为 可用 [此处](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.view.rest`。



## 记录中

控制CAS日志记录配置的位置和其他设置。 要了解更多有关这个话题， [请参阅本指南](../logging/Logging.html)。



```properties
＃logging.config =文件：/etc/cas/log4j2.xml
＃server.servlet.context-parameters.is-log4j-auto-initialization-disabled = true

＃cas.logging.mdc-enabled = true

＃控制通过属性记录日志级别
＃logging.level.org.apereo.cas = DEBUG
```


要禁用日志清理，请使用系统属性 `CAS_TICKET_ID_SANITIZE_SKIP = true`启动容器。



## AspectJ配置



```properties
＃spring.aop.auto = true
＃spring.aop.proxy-target-class = true
```




## 认证属性

除非主要的身份验证方案另有说明，否则由 [Person Directory](../integration/Attribute-Resolution.html) 某个组件）从多个属性源中检索到的一组身份验证属性，该属性为 

如果定义了多个属性存储库源，则将它们添加到列表 并缓存和合并其结果。



```properties
＃cas.authn.attribute-repository.expiration-time = 30
＃cas.authn.attribute-repository.expiration-time-unit = MINUTES
＃cas.authn.attribute-repository.maximum-cache-size = 10000
＃ cas.authn.attribute-repository.merger = REPLACE | ADD | MULTIVALUED | NONE
＃cas.authn.attribute-repository.aggregation = MERGE | CASCADE
```

<div class="alert alert-info"><strong>记住这一点</strong><p>请注意，在某些情况下，
CAS身份验证能够在同一身份验证请求中从身份验证源检索和解析属性，这将
消除了配置单独的属性存储库的需要，特别是在身份验证和属性源都相同的情况下。
当源不同或需要处理更高级的属性
分辨率用例（例如级联，合并等）
<a href="../installation/Configuring-Principal-Resolution.html">有关更多信息，请参见本指南</a></p></div>

所有源的属性都在各自的块中定义。 CAS并不关心属性的源所有者。 它会在可以找到它们的地方找到它们，否则它将继续前进。 这意味着可以通过一个源解析某些数量的属性，而可以通过另一个源解析其余的属性 。 如果来源之间存在共同点，则合并应决定最终结果和行为。

用简单的英语讲的故事是：

- 我有一堆属性，希望对经过身份验证的主体进行解析。
- 我有一堆从中获取所述属性的资源。
- 想办法。

请注意，属性存储库源（如果已定义）将以特定顺序执行。 在可能发生属性合并时要考虑到这一点很重要。 默认情况下，执行顺序（定义时）为以下顺序，但可以针对每个源进行调整：

1. LDAP
2. JDBC
3. JSON格式
4. Groovy
5. [Internet2石斑鱼](http://www.internet2.edu/products-services/trust-identity/grouper/)
6. 休息
7. 脚本
8. 存根/静态

请注意，如果 *显式* 属性映射，则CAS可以从属性存储库源中检索 上的所有允许的属性，并将其提供给主体。 另一方面，如果定义了显式属性映射 ，则仅检索 *映射属性*



### 多映射属性

可以允许对属性进行虚拟重命名和重新映射。 例如，以下定义尝试 抓取属性 `uid` 并将其重命名为 `userId`：



```properties
＃cas.authn.attribute-repository。[type-placeholder].attributes.uid =用户ID
```




### 合并策略

当从多个来源中找到相同的属性时，可以使用以下合并策略来解决冲突：

| 类型     | 描述                          |
| ------ | --------------------------- |
| `代替`   | 覆盖现有的属性值（如果有）。              |
| `添加`   | 保留现有属性值（如果有），并忽略解析链中后续来源的值。 |
| `多值`   | 将所有值组合到一个属性中，从本质上创建一个多值属性。  |
| `没有任何` | 不合并属性，仅使用在身份验证期间检索到的属性。     |




### 整合策略

当定义了多个属性存储库源来获取数据时，可以使用以下聚合策略来解析和合并属性 

| 类型   | 描述                             |
| ---- | ------------------------------ |
| `合并` | 默认。 按顺序查询多个存储库，然后将结果合并到单个结果集中。 |
| `级联` | 同上；每个查询的结果将向下传递到下一个属性存储库源。     |




### 存根

需要映射到硬编码值的静态属性属于此处。



```properties
＃cas.authn.attribute-repository.stub.id =

＃cas.authn.attribute-repository.stub.attributes.uid = uid
＃cas.authn.attribute-repository.stub.attributes.displayName = displayName
＃cas .authn.attribute-repository.stub.attributes.cn = commonName
＃cas.authn.attribute-repository.stub.attributes.affiliation = groupMembership
```




### LDAP

如果你希望直接地和单独地检索从LDAP源属性，此LDAP设置 特征可用 [这里](Configuration-Properties-Common.html#ldap-connection-settings) 下 配置键 `cas.authn.attribute-repository.ldap[0]`。



```properties
＃cas.authn.attribute-repository.ldap[0].id =
＃cas.authn.attribute-repository.ldap[0].order = 0

＃cas.authn.attribute-repository.ldap[0].attributes.uid = uid
＃ cas.authn.attribute-repository.ldap[0].attributes.display-name = displayName
＃cas.authn.attribute-repository.ldap[0].attributes.cn = commonName
＃cas.authn.attribute-repository.ldap[0].attributes .affiliation = groupMembership
```


要获取并解析带有标签/选项的属性，请考虑对映射的属性进行如下标记：



```properties
＃cas.authn.attribute-repository.ldap[0].attributes.affiliation =隶属关系;
```




### Groovy

如果您希望直接并分别从Groovy脚本中检索属性，则 与以下设置相关：



```properties
＃cas.authn.attribute-repository.groovy[0].location = file：/etc/cas/attributes.groovy
＃cas.authn.attribute-repository.groovy[0].case-insensitive = false
＃cas.authn.attribute- repository.groovy[0].order = 0
＃cas.authn.attribute-repository.groovy[0].id =
```


Groovy脚本可以设计为：



```groovy
import java.util。*

def Map<String, List<Object>> run（final Object ... args）{
    def用户名= args[0]
    def属性= args[1]
    def logger = args[2]
    def属性= args[3]
    def appContext = args[4]

    logger.debug（“ [{}]：收到的uid是[{}]”，this.class.simpleName，uid）
    return [用户名：[uid]，喜欢：[“ cheese”，“ food”]，id ：[1234,2,3,4,5]，另一个：“属性”]
}
```




### JSON格式

如果您希望直接和单独地从静态JSON源中检索属性，则 与以下设置相关：



```properties
＃cas.authn.attribute-repository.json[0].location = file：//etc/cas/attribute-repository.json
＃cas.authn.attribute-repository.json[0].order = 0
＃cas.authn.attribute -repository.json[0].id =
```


该文件的格式可以是：



```json
{
    “ user1”：{
        “ firstName”：[“ Json1”]，
        “ lastName”：[“ One”]
    }，
    “ user2”：{
        “ firstName”：[“ Json2”]，
        “ eduPersonAffiliation“：[”员工“，”学生“]
    }
}
```




### 休息

从REST端点检索属性。 此功能的RESTful设置 可用 [这里](Configuration-Properties-Common.html#restful-integrations) 在 下配置密钥 `cas.authn.attribute-repository.rest[0]`。



```properties
＃cas.authn.attribute-repository.rest[0].order = 0
＃cas.authn.attribute-repository.rest[0].id =
＃cas.authn.attribute-repository.rest[0].case-insensitive = false
```


`用户名`下的请求参数的形式传递。 预期响应 为JSON映射，如下所示：



```json
{
  “名称”： “JohnSmith对”，
  “年龄”：29，
  “消息”：[ “MSG 1”， “MSG 2”， “MSG 3”]
}
```




### Python / Javascript / Groovy

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能已弃用，并计划在将来删除。</strong></p>
</div>

与Groovy选项类似，但用途更多，该选项利用Java的本机 脚本API调用Groovy，Python或Javascript脚本引擎来编译预定义的脚本来解析属性。 以下设置是相关的：



```properties
＃cas.authn.attribute-repository.script[0].location = file：/etc/cas/script.groovy
＃cas.authn.attribute-repository.script[0].order = 0
＃cas.authn.attribute-repository。脚本[0].id =
＃cas.authn.attribute-repository.script[0].case-insensitive = false
＃cas.authn.attribute-repository.script[0].engine-name = js | groovy | python
```


尽管CAS本身应支持Javascript和Groovy，但Python脚本可能需要 来调整CAS配置，以包括 [Python模块](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jython-standalone%22)。

Groovy脚本可以定义为：



```groovy
import java.util。*

映射<String, List<Object>> 运行（最终对象... args）{
    def uid = args[0]
    def logger = args[1]

    logger.debug（“使用UID：{ ，uid）
    return [username：[uid]，likes：[“ cheese”，“ food”]，id：[1234,2,3,4,5]，另一个：“ attribute”]
}
```


Javascript脚本可以定义为：



```javascript
函数run（uid，logger）{
    print（“事情正在发生”）
    logger.warn（“使用UID调用的Java语言：{}”，uid）;

    //如果要回调到Java，这是这样做的一种方法
    var javaObj = new JavaImporter（org.yourorgname.yourpackagename）;
    with（javaObj）{
        var objFromJava = JavaClassInPackage.someStaticMethod（uid）;
    }

    var map = {};
    map [“ attr_from_java”] = objFromJava.getSomething（）;
    map [“ username”] = uid;
    map [“ likes”] =“奶酪”;
    map [“ id”] = [1234,2,3,4,5];
    map [“ another”] =“属性”;

    返回图；
}
```




### JDBC

从JDBC源检索属性。 此功能 数据库设置可用 [在这里](Configuration-Properties-Common.html#database-settings) 下 配置密钥 `cas.authn.attribute-repository.jdbc[0]`。



```properties
＃cas.authn.attribute-repository.jdbc[0].attributes.uid = uid
＃cas.authn.attribute-repository.jdbc[0].attributes.display-name = displayName
＃cas.authn.attribute-repository.jdbc[0]。 attributes.cn = commonName
＃cas.authn.attribute-repository.jdbc[0].attributes.affiliation = groupMembership

＃cas.authn.attribute-repository.jdbc[0].single-row = true
＃cas.authn.attribute-repository .jdbc[0].order = 0
＃cas.authn.attribute-repository.jdbc[0].id =
＃cas.authn.attribute-repository.jdbc[0].require-all-attributes = true
＃cas.authn.attribute- repository.jdbc[0].case-canonicalization = NONE | LOWER | UPPER
＃cas.authn.attribute-repository.jdbc[0].query-type = OR | AND
＃cas.authn.attribute-repository.jdbc[0]不区分大小写-query-attributes =用户名

＃仅在将多个行映射到一个用户时使用
＃cas.authn.attribute-repository.jdbc[0].column-mappings.column-attr-name1 = columnAttrValue1
＃cas.authn .attribute-repository.jdbc[0].column-mappings.column-attr-name2 = colu mnAttrValue2
＃cas.authn.attribute-repository.jdbc[0].column-mappings.column-attr-name3 = columnAttrValue3

＃cas.authn.attribute-repository.jdbc[0].sql = SELECT *从表WHERE {0}
＃cas。 authn.attribute-repository.jdbc[0].username = uid
```




### 石斑鱼

对于给定的CAS主体， [到Grouper实例](https://incommon.org/software/grouper/) 读取所有组， `grouperGroups` 多值属性下的CAS属性。 要了解更多有关这个话题， [请参阅本指南](../integration/Attribute-Resolution.html)。



```properties
＃cas.authn.attribute-repository.grouper[0].enabled = true
＃cas.authn.attribute-repository.grouper[0].id =
＃cas.authn.attribute-repository.grouper[0].order = 0
```


您还需要确保 `grouper.client.properties` 在类路径上可用（即 `src / main / resources`） 具有以下配置的属性：



```properties
＃grouperClient.webService.url = http://192.168.99.100:32768/grouper-ws/servicesRest
＃grouperClient.webService.login = banderson
＃grouperClient.webService.password =密码
```




### Couchbase

此选项将从Couchbase数据库中获取给定CAS主体的属性。 要 了解有关此主题的更多信息， [查看本指南](../installation/Couchbase-Authentication.html)。 此功能的数据库设置在配置键 `cas.authn.attribute-repository.couchbase`[此处](Configuration-Properties-Common.html#couchbase-integration-settings) 可用。



```properties
＃cas.authn.attribute-repository.couchbase.usernameAttribute =用户名
＃cas.authn.attribute-repository.couchbase.order = 0
＃cas.authn.attribute-repository.couchbase.id =
```




### 雷迪斯

此选项将从Redis数据库中获取给定CAS主体的属性。 

要了解更多有关这个话题， [请参阅本指南](../installation/Redis-Authentication.html)。

此功能的常用配置设置在配置键 `cas.authn.attribute-repository.redis`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



```properties
＃cas.authn.attribute-repository.redis.order = 0
＃cas.authn.attribute-repository.redis.id =
```




### Microsoft Azure活动目录

此选项将使用Microsoft Graph API从Microsoft Azure Active Directory中获取属性。

可以使用以下设置：



```properties
＃cas.authn.attribute-repository.azure-active-directory[0].client-id =
＃cas.authn.attribute-repository.azure-active-directory[0].client-secret =
＃cas.authn.attribute-repository .azure活动目录[0].client-secret =
＃cas.authn.attribute-repository.azure活动目录[0].tenant =

＃cas.authn.attribute-repository.azure活动目录[0].id =
＃cas.authn.attribute-repository.azure-active-directory[0].order = 0
＃cas.authn.attribute-repository.azure-active-directory[0].case-insensitive = false
＃cas.authn.attribute- repository.azure-active-directory[0].resource =
＃cas.authn.attribute-repository.azure-active-directory[0].scope =
＃cas.authn.attribute-repository.azure-active-directory[0].grant-type =
＃cas.authn.attribute-repository.azure-active-directory[0].api-base-url =
＃cas.authn.attribute-repository.azure-active-directory[0].attributes =
＃cas.authn.attribute -repository.azure-active-directory[0].domain =
＃cas.authn.attribute-repository.azure-activ电子目录[0].logging-level =
```




### Shibboleth集成

要了解更多有关这个话题， [请参阅本指南](../integration/Shibboleth.html)。



```properties
＃cas.authn.shib-idp.server-url = https：//idp.example.org
```




### 默认捆绑

如果您希望将默认属性捆绑发布给所有应用程序， 并且您不想在每个服务定义中都复制相同的属性，为 则以下设置是相关的：



```properties
＃cas.authn.attribute-repository.default-attribute-to-release = cn，givenName，uid，隶属关系
```


要了解更多有关这个话题， [请参阅本指南](../integration/Attribute-Release.html)。



### 协议属性

主要属性外，是否还应包括和释放规范中定义的协议属性。 释放启用协议属性时，将释放所有身份验证属性。 如果希望限制释放哪些身份验证属性，则可以使用以下设置更全面地控制身份验证属性。

协议/身份验证属性也可以在每个服务 基础上有条件地释放。 要了解更多有关这个话题， [请参阅本指南](../integration/Attribute-Release.html)。



```properties
＃cas.authn.authentication-attribute-release.only-release = authenticationDate，isFromNewLogin
＃cas.authn.authentication-attribute-release.never-release =
＃cas.authn.authentication-attribute-release.enabled = true
```




## 主要决议

如果放置了单独的解析器，则控制默认情况下应如何构造最终主体。 此功能的主要分辨率 和人员目录设置为 可用 [在这里](Configuration-Properties-Common.html#person-directory-principal-resolution) 在配置键 `cas.person-directory`。



## 属性定义

属性定义存储库允许在属性解析和发布期间使用特殊修饰 



```properties
＃cas.person-directory.attribute-definition-store.json.location = file：/etc/cas/config/attribute-defns.json
```




## 认证引擎

在执行之前和之后，控制CAS验证引擎的内部工作。



```properties
cas.authn.core.groovy-authentication-resolution.location = file：/etc/cas/config/GroovyAuthentication.groovy
cas.authn.core.groovy-authentication-resolution.order = 0

cas.authn.core.service -authentication-resolution.order = 0
```




### 认证预处理



#### Groovy



```properties
＃cas.authn.core.engine.groovy-pre-processor.location = file：/etc/cas/config/GroovyPreProcessor.groovy
```


脚本本身可以设计为：



```groovy
def run（Object [] args）{
    def事务= args[0]
    def logger = args[1]
    true
}

def support（Object [] args）{
    def凭据= args[0]
    def logger = args[1]
    true
}
```




### 认证后处理



#### Groovy



```properties
＃cas.authn.core.engine.groovy-post-processor.location = file：/etc/cas/config/GroovyPostProcessor.groovy
```


脚本本身可以设计为：



```groovy
def run（Object [] args）{
    def builder = args[0]
    def transaction = args[1]
    def logger = args[2]
    true
}

def support（Object [] args）{
    def凭证= args[0]
    def logger = args[1]
    true
}
```




## 认证政策

要了解更多有关这个话题， [请参阅本指南](../installation/Configuring-Authentication-Components.html#authentication-policy)。

当CAS尝试出售和验证票证时应用的全局身份验证策略。



```properties
＃cas.authn.policy.required-handler-authentication-policy-enabled = false
```




### 任何

满意是否有任何处理程序成功。 支持tryAll标志以避免短路 并尝试每个处理程序，即使一个先前的处理成功了也是如此。



```properties
＃cas.authn.policy.any.try-all = false
＃cas.authn.policy.any.enabled = true
```




### 全部

当且仅当所有给定的凭据都通过了成功验证时，才满足。 在CAS中新增了对多个凭据的支持，并且此处理程序 仅在多因素身份验证情况下才可接受。



```properties
＃cas.authn.policy.all.enabled = true
```




### 来源选择

允许CAS根据凭据来源选择身份验证处理程序。 与每个身份验证处理程序相反，这允许身份验证引擎将验证凭据 的任务限制在选定的源或帐户存储库中。



```properties
＃cas.authn.policy.source-selection-enabled = true
```




### 唯一校长

当且仅当发出请求的委托人尚未通过CAS进行身份验证时，才满足。 否则，身份验证事件将被阻止，从而阻止多次登录。

<div class="alert alert-warning"><strong>使用警告</strong><p>激活这一政策并不是没有代价，
为CAS需要查询机票注册表和所有的门票目前确定当前用户是否已经建立的任何地方验证会话。 这肯定会给部署增加性能负担。 小心使用。</p></div>

```properties
＃cas.authn.policy.unique-principal.enabled = true
```




### 没有预防

当且仅当验证事件未被 `PreventedException`阻止时才满足。



```properties
＃cas.authn.policy.not-prevented.enabled = true
```




### 必需的

当且仅当指定的处理程序成功验证其凭据时才满足。



```properties
＃cas.authn.policy.req.try-all = false
＃cas.authn.policy.req.handler-name = handlerName
＃cas.authn.policy.req.enabled = true
```




### Groovy

执行常规脚本以检测身份验证策略。



```properties
＃cas.authn.policy.groovy[0].script = file：/etc/cas/config/account.groovy
```


该脚本可以设计为：



```groovy
导入java.util。*
导入org.apereo.cas.authentication.exceptions。*
导入javax.security.auth.login。*

def异常运行（最终对象... args）{
    def主体= args[0]
    def logger = args[1]

    if（conditionYouMayDesign（）== true）{
        返回新的AccountDisabledException（）
    }
    返回null；
}
```




### 休息

`POST` 与REST端点联系以检测身份验证策略。 消息正文包含经过CAS身份验证的主体，可以使用 来检查帐户状态和策略。

此功能的RESTful设置为 可用 [此处](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.authn.policy.rest[0]`。

REST端点的响应代码按以下方式转换：

| 代码           | 结果                                        |
| ------------ | ----------------------------------------- |
| `200`        | 成功认证。                                     |
| `403`， `405` | 产生一个 `AccountDisabledException`           |
| `404`        | 产生一个 `AccountNotFoundException`           |
| `423`        | 产生一个 `AccountLockedException`             |
| `412`        | 产生一个 `AccountExpiredException`            |
| `428`        | 产生一个 `AccountPasswordMustChangeException` |
| 其他           | 产生一个 `FailedLoginException`               |




## 身份验证限制

CAS提供了一种限制登录失败尝试的功能，以支持密码猜测和相关的滥用情况。 要了解更多有关这个话题， [请参阅本指南](../installation/Configuring-Authentication-Throttling.html)。



```properties
＃cas.authn.throttle.username-参数=用户名
＃cas.authn.throttle.app-code = CAS

＃cas.authn.throttle.failure.threshold = 100
＃cas.authn.throttle.failure.code = AUTHENTICATION_FAILED
＃cas.authn.throttle.failure.range-seconds = 60
```


此功能的计划程序设置在配置键 `cas.authn.throttle`[这里](Configuration-Properties-Common.html#job-scheduling) 是可用的。



### Bucket4j

使用限速和令牌桶处理容量规划和系统过载保护。



```properties
＃cas.authn.throttle.bucket4j。秒数范围= 60
＃cas.authn.throttle.bucket4j.capacity = 120
＃cas.authn.throttle.bucket4j.blocking = true
＃cas.authn.throttle。 bucket4j.overdraft = 0
```




### MongoDb

此功能的通用配置设置在配置键 `cas.audit`[这里](Configuration-Properties-Common.html#mongodb-configuration) 是可用的。 此功能使用与CAS MongoDb审核工具相同的数据源。 



### 雷迪斯

此功能的通用配置设置在配置键 `cas.audit`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。 此功能使用CAS Redis审核工具使用的相同数据源。



### 淡褐色

使用分布式Hazelcast映射记录受限制的身份验证尝试。 此功能的Hazelcast设置在配置键 `cas.authn.throttle.hazelcast`[这里](Configuration-Properties-Common.html#hazelcast-configuration) 是可用的。



### 数据库

查询CAS审核工具使用的数据源，以防止来自 相同IP地址的特定用户名的连续登录尝试失败。 

此功能的数据库设置在配置键 `cas.authn.throttle.jdbc`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



```properties
＃cas.authn.throttle.jdbc.audit-query =从COM_AUDIT_TRAIL \
选择AUD_DATE \在AUD_CLIENT_IP =？ AND AUD_USER =吗？ \
＃AND AUD_ACTION =？ AND APPLIC_CD =？ \
＃AND AUD_DATE >=？ 按AUD_DATE DESC排序
```




### CouchDb

查询CAS审核工具使用的数据源，以防止来自 相同IP地址的特定用户名的连续登录尝试失败。 此功能的CouchDb设置在配置键 `cas.authn.throttle`[这里](Configuration-Properties-Common.html#couchdb-configuration)。 使用此功能时，审核工具应处于同步模式。



## 自适应身份验证

控制CAS身份验证应如何使其适应传入的客户端请求。 要了解更多有关这个话题， [请参阅本指南](../mfa/Configuring-Adaptive-Authentication.html)。



```properties
＃cas.authn.adaptive.reject-countries =美国+
＃cas.authn.adaptive.reject-browsers = Gecko。+
＃cas.authn.adaptive.reject-ip-addresses = 127. +

＃cas。 authn.adaptive.require-multifactor.mfa-duo = 127. + | United。+ | Gecko。+
```


自适应身份验证还可以对特定时间做出反应，以触发多因素身份验证。



```properties
＃cas.authn.adaptive.require-timed-multifactor[0].provider-id = mfa-duo
＃cas.authn.adaptive.require-timed-multifactor[0].on-or-hour-hour = 20
＃cas.authn .adaptive.require-timed-[0]每小时或之前= 7
＃cas.authn.adaptive.require-timed-multifactor[0].on-days =周六，周日
```




### IP地址智能

通过以下策略检查客户端IP地址。



#### REST自适应身份验证

此功能的RESTful设置在配置键 `cas.authn.adaptive.ip-intel.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



#### Groovy自适应身份验证



```properties
＃cas.authn.adaptive.ip-intel.groovy.location =文件：/etc/cas/config/GroovyIPAddressIntelligenceService.groovy
```




#### BlackDot自适应身份验证



```properties
＃cas.authn.adaptive.ip-intel.black-dot.url = http：//check.getipintel.net/check.php？ip =%s
＃cas.authn.adaptive.ip-intel.black-dot。 email-address =
＃cas.authn.adaptive.ip-intel.black-dot.mode = DYNA_LIST
```




## 代理身份验证

代表另一个用户进行身份验证。 要了解更多有关这个话题， [请参阅本指南](../installation/Surrogate-Authentication.html)。



```properties
＃cas.authn.surrogate.separator = +
＃cas.authn.surrogate.tgt。秒杀时间= 30
```


主要分辨率和人员目录设置在此处 [](Configuration-Properties-Common.html#person-directory-principal-resolution) 在配置键 `cas.authn.surrogate.principal`下可用。



### 静态代理帐户



```properties
＃cas.authn.surrogate.simple.surrogates.casuser = jsmith，jsmith2
＃cas.authn.surrogate.simple.surrogates.casuser2 = jsmith4，jsmith2
```




### JSON代理帐户



```properties
＃cas.authn.surrogate.json.location = file：/etc/cas/config/surrogates.json
```




### LDAP代理帐户

此功能的LDAP设置在配置项 `cas.authn.surrogate.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



```properties
＃cas.authn.surrogate.ldap.surrogate-search-filter =（&（主要={user}）（memberOf = cn = edu：example：cas：something：{user}，dc = example，dc = edu））
＃cas。 authn.surrogate.ldap.member-attribute-name = memberOf
＃cas.authn.surrogate.ldap.member-attribute-value-regex = cn = edu：example：cas：something：（[[^，] +）,. +
```




### CouchDb代理帐户

此功能的设置可用 [在这里](Configuration-Properties-Common.html#couchdb-configuration) 在配置键 `cas.authn.surrogate`。 代理可以存储为委托人档案的一部分，也可以存储为一系列委托人/代理人对。 默认值为键/值对。



```properties
＃cas.authn.surrogate.ldap.surrogate-search-filter =（&（主要={user}）（memberOf = cn = edu：example：cas：something：{user}，dc = example，dc = edu））
＃cas。 authn.surrogate.ldap.member-attribute-name = memberOf
＃cas.authn.surrogate.ldap.member-attribute-value-regex = cn = edu：example：cas：something：（[[^，] +）,. +
```




### JDBC代理帐户

此功能的数据库设置在配置键 `cas.authn.surrogate.jdbc`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



```properties
＃cas.authn.surrogate.jdbc.surrogate-search-query =从替代WHERE用户名=中选择COUNT（*）。
＃cas.authn.surrogate.jdbc.surrogate-account-query = SELECT surrogate_user AS surrogateAccount FROM surrogate WHERE username =？
```




### REST代理帐户

此功能的RESTful设置在配置键 `cas.authn.surrogate.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



### 通知事项

此功能的电子邮件通知设置在配置键 `cas.authn.surrogate`[这里](Configuration-Properties-Common.html#email-notifications) 是可用的。 此功能的SMS通知设置为 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 在配置键 `cas.authn.surrogate`。



## QR认证

尝试通过移动设备通过QR码登录。 要了解有关该 主题的 [本指南](../installation/QRCode-Authentication.html)。



```properties   
＃配置浏览器客户端允许的Origin标头值。
＃cas.authn.qr.allowed-origins = *
```




### JSON设备存储库

尝试通过移动设备通过QR码登录。 要了解有关该 主题的 [本指南](../installation/QRCode-Authentication.html)。



```properties
＃cas.authn.qr.json.location = file：/etc/cas/config/qrdevices.json
```




## 基于风险的认证

评估可疑身份验证请求并采取措施。 要了解 个关于该主题的更多信息， [查看本指南](../installation/Configuring-RiskBased-Authentication.html)。



```properties
＃cas.authn.adaptive.risk.threshold = 0.6
＃cas.authn.adaptive.risk.days-recent-history = 30

＃cas.authn.adaptive.risk.ip.enabled = false

＃cas。 authn.adaptive.risk.agent.enabled = false

＃cas.authn.adaptive.risk.geo-location.enabled = false

＃cas.authn.adaptive.risk.date-time.enabled = false
＃cas.authn .adaptive.risk.date-time.window-in-hours = 2

＃cas.authn.adaptive.risk.response.block-attempt = false

＃cas.authn.adaptive.risk.response.mfa-provider =
＃cas.authn.adaptive.risk.response.risky-authentication-attribute = triggeredRiskBasedAuthentication
```


此功能的电子邮件通知设置在配置键 `cas.authn.adaptive.risk.response`[这里](Configuration-Properties-Common.html#email-notifications) 是可用的。 此功能的SMS通知设置为 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 在配置键 `cas.authn.adaptive.risk.response`。



## 无密码认证

要了解更多有关这个话题， [请参阅本指南](../installation/Passwordless-Authentication.html)。



### 帐户商店



```properties   
＃cas.authn.passwordless.multifactor-authentication-activated = false
＃cas.authn.passwordless.delegated-authentication-activated = false
```




#### 简单帐户存储



```properties
＃cas.authn.passwordless.accounts.simple.casuser=cas@example.org
```




#### Groovy帐户存储



```properties
＃cas.authn.passwordless.accounts.groovy.location = file：/etc/cas/config/pwdless.groovy
```




#### JSON帐户存储



```properties
＃cas.authn.passwordless.accounts.json.location = file：/etc/cas/config/pwdless-accounts.json
```




#### RESTful帐户存储

此功能的RESTful设置在配置项 `cas.authn.passwordless.accounts.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



#### LDAP帐户存储

此功能的LDAP设置在配置键 `cas.authn.passwordless.accounts.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



#### MongoDb帐户存储

此功能的MongoDb设置在配置键 `cas.authn.passwordless.accounts.mongo`[这里](Configuration-Properties-Common.html#mongodb-configuration) 是可用的。



### 代币管理



```properties
＃cas.authn.passwordless.accounts.expire-in-seconds = 180
```


此功能的RESTful设置在配置键 `cas.authn.passwordless.tokens.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 。 签名密钥和加密 密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 对此功能进行签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 下 配置密钥 `cas.authn.passwordless.tokens`。

此功能的电子邮件通知设置在配置键 `cas.authn.passwordless.tokens`[这里](Configuration-Properties-Common.html#email-notifications) 是可用的。 此功能的SMS通知设置为 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 在配置键 `cas.authn.passwordless.tokens`。

此功能的数据库设置可用 [在这里](Configuration-Properties-Common.html#database-settings) 下 在配置密钥 `cas.authn.passwordless.tokens.jpa`。 此功能的计划程序设置在配置键 `cas.authn.passwordless.tokens.jpa.cleaner`[这里](Configuration-Properties-Common.html#job-scheduling) 是可用的。



## 电子邮件提交

电子邮件通知设置可用 [在这里](Configuration-Properties-Common.html#email-notifications)。



## 短信服务

要了解更多有关这个话题， [请参阅本指南](../notifications/SMS-Messaging-Configuration.html)。



### Groovy

使用Groovy脚本发送文本消息。



```properties
＃cas.sms-provider.groovy.location = file：/etc/cas/config/SmsSender.groovy
```




### 休息

使用RESTful API发送短信。 此功能的RESTful设置为 可用 [此处](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.sms-provider.rest`。



### 特威里奥

使用Twilio发送短信。



```properties
＃cas.sms-provider.twilio.account-id =
＃cas.sms-provider.twilio.token =
```




### TextMagic

使用TextMagic发送短信。



```properties
＃cas.sms-provider.text-magic.username =
＃cas.sms-provider.text-magic.token =
＃cas.sms-provider.text-magic.debugging = false
＃cas.sms-provider。 text-magic.password =
＃cas.sms-provider.text-magic.read-timeout = 5000
＃cas.sms-provider.text-magic.connection-timeout = 5000
＃cas.sms-provider.text- magic.write-timeout = 5000
＃cas.sms-provider.text-magic.verifying-ssl = true
＃cas.sms-provider.text-magic.api-key =
＃cas.sms-provider.text- magic.api-key-prefix =
```




### Clickatell

使用Clickatell发送短信。



```properties
＃cas.sms-provider.clickatell.server-url = https：//platform.clickatell.com/messages
＃cas.sms-provider.clickatell.token =
```




### Nexmo

使用Nexmo发送短信。 Nexmo至少需要apiSecret或signatureSecret字段集。



```properties
＃cas.sms-provider.nexmo.api-token =
＃cas.sms-provider.nexmo.api-secret =
＃cas.sms-provider.nexmo.signature-secret =
```




### 亚马逊SNS

使用Amazon SNS发送短信。



```properties
＃cas.sms-provider.sns.sender-id =
＃cas.sms-provider.sns.max-price =
＃cas.sms-provider.sns.sms-type =交易
```


此功能的AWS设置在配置键 `cas.sms-provider.sns`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



## Google Cloud Firebase消息传递

要了解更多有关这个话题， [请参阅本指南](../notifications/Notifications-Configuration.html)。



```properties
＃cas.google-firebase-messaging.service-account-key.location = / path / to / account-key.json“，
＃cas.google-firebase-messaging.database-url = https：// cassso-123456 .firebaseio.com”，
＃cas.google-firebase-messaging.registration-token-attribute-name = registrationToken
＃cas.google-firebase-messaging.scopes = https：//www.googleapis.com/auth/firebase .messaging
```




## 地理跟踪

要了解更多有关这个话题， [请参阅本指南](../installation/GeoTracking-Authentication-Requests.html)。



### GoogleMaps GeoTracking

用于对身份验证事件进行地理配置文件。



```properties
＃cas.google-maps.api-key =
＃cas.google-maps.client-id =
＃cas.google-maps.client-secret =
＃cas.google-maps.connect-timeout = 3000
＃ cas.google-maps.google-apps-engine = false
```




### Maxmind GeoTracking

用于对身份验证事件进行地理配置文件。



```properties
＃cas.maxmind.city-database =文件：/etc/cas/maxmind/GeoLite2-City.mmdb
＃cas.maxmind.country-database =文件：/etc/cas/maxmind/GeoLite2-Country.mmdb
```




## Cassandra身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Cassandra-Authentication.html)。



```properties
＃cas.authn.cassandra.username-attribute =
＃cas.authn.cassandra.password-attribute =
＃cas.authn.cassandra.table-name =
＃cas.authn.cassandra.username =
＃cas.authn .cassandra.password =
＃cas.authn.cassandra.query = SELECT *从 %s 哪里 %s=？ 允许
＃cas.authn.cassandra.name =
＃cas.authn.cassandra.order =
```


此功能的常规Cassandra设置在配置键 `cas.authn.cassandra`[这里](Configuration-Properties-Common.html#cassandra-configuration) 可用。

此功能的主要转换设置在配置键 `cas.authn.cassandra`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 

此功能的密码编码设置在配置键 `cas.authn.cassandra`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



## 摘要式身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Digest-Authentication.html)。



```properties
＃cas.authn.digest.users.casuser = 3530292c24102bac7ced2022e5f1036a
＃cas.authn.digest.users.anotheruser = 7530292c24102bac7ced2022e5f1036b
＃cas.authn.digest.realm = CAS
＃cas.authn.digest.name =
authn.digest.order =
＃cas.authn.digest.authentication-method = auth
```




## 半径认证

要了解更多有关这个话题， [请参阅本指南](../mfa/RADIUS-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.radius`[此处为](Configuration-Properties-Common.html#authentication-principal-transformation) 可用。

此功能的密码编码设置在配置密钥 `cas.authn.radius`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的半径设置在配置项 `cas.authn.radius`[此处为](Configuration-Properties-Common.html#radius-configuration) 可用。



```properties
＃cas.authn.radius.name =
```




## 文件认证

要了解更多有关这个话题， [请参阅本指南](../installation/Permissive-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.file`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置密钥 `cas.authn.file`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.file.separator = ::
＃cas.authn.file.filename = file：///路径/到/用户/文件
＃cas.authn.file.name =
```




## Groovy身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Groovy-Authentication.html)。



```properties
＃cas.authn.groovy.order = 0
＃cas.authn.groovy.name =
```




## JSON验证

要了解更多有关这个话题， [请参阅本指南](../installation/Permissive-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.json`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置键 `cas.authn.json`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的密码策略设置在配置键 `cas.authn.json.passwordPolicy`[这里](Configuration-Properties-Common.html#password-policy-settings) 是可用的。



```properties
＃cas.authn.json.location = file：///path/to/users/file.json
＃cas.authn.json.name =
```




## 拒绝用户身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Reject-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.reject`[此处为](Configuration-Properties-Common.html#authentication-principal-transformation) 可用。

此功能的密码编码设置在配置键 `cas.authn.reject`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.reject.users =用户1，用户2
＃cas.authn.reject.name =
```




## 数据库认证

要了解更多有关这个话题， [请参阅本指南](../installation/Database-Authentication.html)。



### 查询数据库身份验证

通过将用户密码（可以用密码编码器编码） 与可配置数据库查询确定的记录密码进行比较，从而对用户进行身份验证。

此功能的数据库设置在 [在此处](Configuration-Properties-Common.html#database-settings) 在配置键 `cas.authn.jdbc.query[0]`下可用。

此功能的主要转换设置在配置键 `cas.authn.jdbc.query[0]`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置键 `cas.authn.jdbc.query[0]`[这里](Configuration-Properties-Common.html#password-encoding)。



```properties
＃cas.authn.jdbc.query[0].credential-criteria =
＃cas.authn.jdbc.query[0].name =
＃cas.authn.jdbc.query[0].order = 0

＃cas.authn.jdbc.query[0].sql = SELECT * FROM表WHERE name =？
＃cas.authn.jdbc.query[0].field-password =密码
＃cas.authn.jdbc.query[0].field-expired =
＃cas.authn.jdbc.query[0].field-disabled =
＃cas.authn .jdbc.query[0].principal-attribute-list = sn，cn：commonName，givenName
```




### 搜索数据库身份验证

通过查询用户名和密码来搜索用户记录；如果找到至少一个结果，则对用户进行身份验证。

此功能的数据库设置在 [在此处](Configuration-Properties-Common.html#database-settings) 在配置键 `cas.authn.jdbc.search[0]`下可用。

此功能的主要转换设置在配置键 `cas.authn.jdbc.search[0]`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置键 `cas.authn.jdbc.search[0]`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.jdbc.search[0].field-user =
＃cas.authn.jdbc.search[0].table-users =
＃cas.authn.jdbc.search[0].field-password =
＃cas.authn。 jdbc.search[0].credential-criteria =
＃cas.authn.jdbc.search[0].name =
＃cas.authn.jdbc.search[0].order = 0
```




### 绑定数据库身份验证

通过尝试使用用户名和（哈希）密码创建数据库连接来对用户进行身份验证。

此功能的数据库设置在配置键 `cas.authn.jdbc.bind[0]`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。

此功能的主要转换设置在配置键 `cas.authn.jdbc.bind[0]`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置密钥 `cas.authn.jdbc.bind[0]`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.jdbc.bind[0].credential-criteria =
＃cas.authn.jdbc.bind[0].name =
＃cas.authn.jdbc.bind[0].order = 0
```




### 编码数据库身份验证

JDBC查询处理程序，它将为用户拉回密码和私有盐值，并使用公共盐值 假设所有内容都在同一数据库表中。 支持 次迭代以及私有盐的设置。

此密码编码方法将专用盐和公用盐组合在一起，在散列之前，专用盐和公用盐都附加在密码中。 如果使用了多次迭代，则第一次迭代的字节码哈希将重新哈希，而不会增加盐值。 将最终的哈希 与数据库值进行比较之前，将其转换为十六进制。

此功能的数据库设置在 [在此处](Configuration-Properties-Common.html#database-settings) 在配置键 `cas.authn.jdbc.encode[0]`下可用。

此功能的主要转换设置在配置键 `cas.authn.jdbc.encode[0]`[这里](Configuration-Properties-Common.html#authentication-principal-transformation)。

此功能的密码编码设置在配置键 `cas.authn.jdbc.encode[0]`[这里](Configuration-Properties-Common.html#password-encoding)。



```properties
＃cas.authn.jdbc.encode[0].number-of-iterations = 0
＃cas.authn.jdbc.encode[0].number-of-iterations-field-name = numIterations
＃cas.authn.jdbc.encode[0]。 salt-field-name =盐
＃cas.authn.jdbc.encode[0].static-salt =
＃cas.authn.jdbc.encode[0].sql =
＃cas.authn.jdbc.encode[0].algorithm-name =
＃cas.authn.jdbc.encode[0].password-field-name =密码
＃cas.authn.jdbc.encode[0].expired-field-name =
＃cas.authn.jdbc.encode[0].disabled-field-名称=

＃cas.authn.jdbc.encode[0].credential-criteria =
＃cas.authn.jdbc.encode[0].name =
＃cas.authn.jdbc.encode[0].order = 0
```




## CouchDb验证

要了解更多有关这个话题， [请参阅本指南](../installation/CouchDb-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.couch-db`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 此功能的密码编码设置在配置密钥 `cas.authn.couch-db`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的常规配置设置在配置键 `cas.authn`[这里](Configuration-Properties-Common.html#couchdb-configuration) 是可用的。



```properties
＃cas.authn.couch-db.attributes =
＃cas.authn.couch-db.username-attribute =用户名
＃cas.authn.couch-db.password-attribute =密码
＃cas.authn.couch-db .name =
＃cas.authn.couch-db.order =
```




## Redis认证

要了解更多有关这个话题， [请参阅本指南](../installation/Redis-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.redis`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 此功能的密码编码设置在配置键 `cas.authn.redis`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的通用配置设置在配置键 `cas.authn`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



```properties
＃cas.authn.redis.name =
＃cas.authn.redis.order =
```




## MongoDb验证

要了解更多有关这个话题， [请参阅本指南](../installation/MongoDb-Authentication.html)。 

此功能的主要转换设置在配置键 `cas.authn.mongo`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 此功能的密码编码设置在配置密钥 `cas.authn.mongo`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的常规配置设置在配置键 `cas.authn`[这里](Configuration-Properties-Common.html#mongodb-configuration) 是可用的。



```properties
＃cas.authn.mongo.attributes =
＃cas.authn.mongo.username-attribute =用户名
＃cas.authn.mongo.password-attribute =密码
＃cas.authn.mongo.principal-id-attribute =
＃cas.authn.mongo.name =
```




## LDAP验证

CAS针对LDAP目录（例如Active Directory或OpenLDAP）对用户名/密码进行身份验证。 目录架构众多，我们为四种常见情况提供配置。

请注意，CAS将根据以下指定的设置在内部自动创建适当的组件 如果要针对多个LDAP 服务器进行身份验证，请增加索引并为下一个LDAP服务器指定设置。

**注意：** 作为LDAP身份验证的一部分检索的属性与 [其他属性存储库源](#authentication-attributes)检索的 合并（如果有的话）。 作为LDAP身份验证的一部分直接检索的属性胜过所有其他属性。

要了解更多有关这个话题， [请参阅本指南](../installation/LDAP-Authentication.html)。 此功能的LDAP设置在配置键 `cas.authn.ldap[0]`[这里](Configuration-Properties-Common.html#ldap-connection-settings)。



```properties
＃定义要从LDAP检索的属性，作为同一身份验证事务的一部分
＃左侧的大小指示源，而右侧的大小指示可选的重命名/重映射
＃属性定义。 相同的属性名称可以多次映射到
＃个不同的属性名称。

＃cas.authn.ldap[0].principal-attribute-list = sn，cn：commonName，givenName，eduPersonTargettedId：SOME_IDENTIFIER

＃cas.authn.ldap[0].collect-dn-attribute = false
＃cas.authn.ldap[0]。主体dn属性名称= principalLdapDn
＃cas.authn.ldap[0].allow-multiple-principal-属性值= true
＃cas.authn.ldap[0].allow-missing-principal-属性值= true
＃cas.authn.ldap[0].credential-criteria =
```


要获取并解析带有标签/选项的属性，请考虑对映射的属性进行如下标记：



```properties
＃cas.authn.ldap[0].principal-attribute-list = homePostalAddress：homePostalAddress;
```




### LDAP密码政策

此功能的LDAP密码策略设置在配置键 `cas.authn.ldap[0].passwordPolicy`[此处](Configuration-Properties-Common.html#password-policy-settings) 可用。



### LDAP密码编码 & 主体转换

此功能的主要转换设置在配置键 `cas.authn.ldap[0]`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置密钥 `cas.authn.ldap[0]`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



## REST认证

这允许CAS服务器通过 `POST`到达远程REST端点。 要了解更多有关这个话题， [请参阅本指南](../installation/Rest-Authentication.html)。 此功能的密码编码设置在配置密钥 `cas.authn.rest`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.rest.uri = https：// ...
＃cas.authn.rest.name =
＃cas.authn.rest.charset = US-ASCII
```




## Google Apps身份验证

通过CAS对Google Apps服务和应用程序进行身份验证。 要了解更多有关这个话题， [请参阅本指南](../integration/Google-Apps-Integration.html)。



```properties
＃cas.google-apps.public-key-location =文件：/etc/cas/public.key
＃cas.google-apps.key-algorithm = RSA
＃cas.google-apps.private-key-location =文件：/etc/cas/private.key
```




## OpenID验证

允许CAS成为OpenID身份验证提供程序。 要了解更多有关这个话题， [请参阅本指南](../protocol/OpenID-Protocol.html)。

主要分辨率和人员目录设置 [在此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 在配置键 `cas.authn.openid.principal`下可用。



```properties
＃cas.authn.openid.enforce-rp-id = false
＃cas.authn.openid.name =
＃cas.authn.openid.order =
```




## SPNEGO认证

要了解更多有关这个话题， [请参阅本指南](../installation/SPNEGO-Authentication.html)。

此功能的主要分辨率和人员目录设置在此处 [（在配置键 `cas.authn.spnego.principal`](Configuration-Properties-Common.html#person-directory-principal-resolution) 可用。



```properties
＃cas.authn.spnego.mixed-mode-authentication = false
＃cas.authn.spnego.supported-browsers = MSIE，Trident，Firefox，AppleWebKit
＃cas.authn.spnego.send401-on-authentication-failure = true
＃cas.authn.spnego.ntlm-allowed = true
＃cas.authn.spnego.principal-with-domain-name = false
＃cas.authn.spnego.name =
＃cas.authn.spnego.ntlm =错误的
```




### Webflow配置

此功能的Webflow自动配置设置在配置项 `cas.authn.spnego.webflow`[此处](Configuration-Properties-Common.html#webflow-auto-configuration) 可用。



### 系统设置



```properties
＃cas.authn.spnego.system.kerberos-conf =
＃cas.authn.spnego.system.login-conf =
＃cas.authn.spnego.system.kerberos-realm = EXAMPLE.COM
＃cas.authn。 spnego.system.kerberos-debug = true
＃cas.authn.spnego.system.use-subject-creds-only = false
＃cas.authn.spnego.system.kerberos-kdc = 172.10.1.10
```




### Spnego身份验证设置



```properties
＃cas.authn.spnego.properties[0].cache-policy = 600
＃cas.authn.spnego.properties[0].jcifs-domain-controller =
＃cas.authn.spnego.properties[0].jcifs-domain =
＃cas .authn.spnego.properties[0].jcifs-password =
＃cas.authn.spnego.properties[0].jcifs-username =
＃cas.authn.spnego.properties[0].jcifs-service-password =
＃cas.authn。 spnego.properties[0].timeout = 300000
＃cas.authn.spnego.properties[0].jcifs-service-principal = HTTP / cas.example.com @ EXAMPLE.COM
＃cas.authn.spnego.properties[0].jcifs-netbios -wins =
```




### SPNEGO客户选择策略



```properties
＃cas.authn.spnego.host-name-client-action-strategy = hostnameSpnegoClientAction
```




### SPNEGO客户端选择主机名



```properties
＃cas.authn.spnego.alternative-remote-host-attribute = alternateRemoteHeader
＃cas.authn.spnego.ips-to-check-pattern = 127. +
＃cas.authn.spnego.dns-timeout = 2000
＃ cas.authn.spnego.host-name-pattern-string =。+
```




### SPNEGO LDAP集成

此功能的LDAP设置在配置项 `cas.authn.spnego.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



```properties
＃cas.authn.spnego.spnego-attribute-name = distinguishedName
```




### NTLM身份验证



```properties
＃cas.authn.ntlm.include-pattern =
＃cas.authn.ntlm.load-balance = true
＃cas.authn.ntlm.domain-controller =
＃cas.authn.ntlm.name =
＃cas。 authn.ntlm.order =
＃cas.authn.ntlm.enabled = false
```




## JAAS认证

要了解更多有关这个话题， [请参阅本指南](../installation/JAAS-Authentication.html)。 此功能的主要转换设置在配置键 `cas.authn.jaas[0]`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 此功能的密码编码设置在配置键 `cas.authn.jaas[0]`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.jaas[0].realm = CAS
＃cas.authn.jaas[0].kerberos-kdc-system-property =
＃cas.authn.jaas[0].kerberos-realm-system-property
＃cas.authn。 jaas[0].name =
＃cas.authn.jaas[0].order =
＃cas.authn.jaas[0].credential-criteria =
＃cas.authn.jaas[0].login-config-type = JavaLoginConfig
＃cas.authn .jaas[0].login-configuration-file = / path / to / jaas.con
```


主要分辨率和人员目录设置可用 [在这里](Configuration-Properties-Common.html#person-directory-principal-resolution) 在配置键 `cas.authn.jaas[0].principal`。

此功能的密码策略设置在配置键 `cas.authn.jaas[0].password-policy`[这里](Configuration-Properties-Common.html#password-policy-settings) 是可用的。




## GUA认证

要了解更多有关这个话题， [请参阅本指南](../installation/GUA-Authentication.html)。



### LDAP储存库

此功能的LDAP设置在配置键 `cas.authn.gua.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



```properties
＃cas.authn.gua.ldap.image-attribute = userImageIdentifier
```




### 静态资源库



```properties
＃cas.authn.gua.simple。[username1]=文件：/path/to/image.jpg
＃cas.authn.gua.simple。[username2]=文件：/path/to/image.jpg
```




## JWT /令牌认证

要了解更多有关这个话题， [请参阅本指南](../installation/JWT-Authentication.html)。 此功能的主要转换设置在配置键 `cas.authn.token`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。



```properties
＃cas.authn.token.name =
```




### Webflow配置

此功能的Webflow自动配置设置可用 [在这里](Configuration-Properties-Common.html#webflow-auto-configuration) 在 下配置键 `cas.authn.token.webflow`。



### 智威汤逊门票

允许将通过各种协议通道的CAS票证创建为JWT。 有关更多信息，请参见 [本指南](../installation/Configure-ServiceTicket-JWT.html) 或 [本指南](../protocol/REST-Protocol.html)



```properties
＃cas.authn.token.crypto.encryption-enabled = true
＃cas.authn.token.crypto.signing-enabled = true
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.authn.token`。



## Couchbase身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Couchbase-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.couchbase`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置键 `cas.authn.couchbase`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的数据库设置在配置键 `cas.authn.couchbase`[这里](Configuration-Properties-Common.html#couchbase-integration-settings) 是可用的。



```properties
＃cas.authn.couchbase.username-attribute =用户名
＃cas.authn.couchbase.password-attribute = psw

＃cas.authn.couchbase.name =
＃cas.authn.couchbase.order =
```




## Amazon Cloud Directory身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/AWS-CloudDirectory-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.cloud-directory`[这里](Configuration-Properties-Common.html#authentication-principal-transformation)。 此功能的密码编码设置在配置密钥 `cas.authn.cloud-directory`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的AWS设置在配置键 `cas.authn.cloud-directory`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 。



```properties
＃cas.authn.cloud-directory.directory-arn =
＃cas.authn.cloud-directory.schema-arn =
＃cas.authn.cloud-directory.facet-name =

＃cas.authn.cloud-directory .username-attribute-name =
＃cas.authn.cloud-directory.password-attribute-name =
＃cas.authn.cloud-directory.username-index-path =

＃cas.authn.cloud-directory.name =
＃cas.authn.cloud-directory.order =
```




## Amazon Cognito身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/AWS-Cognito-Authentication.html)。

此功能的主要转换设置在配置项 `cas.authn.cognito`[此处为](Configuration-Properties-Common.html#authentication-principal-transformation) 可用。 此功能的密码编码设置在配置键 `cas.authn.cognito`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。

此功能的AWS设置在配置键 `cas.authn.cognito`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



```properties
＃cas.authn.cognito.name =
＃cas.authn.cognito.order =

＃cas.authn.cognito.client-id =
＃cas.authn.cognito.user-pool-id =

＃cas.authn .cognito.mapped-attributes.given_name = givenName
＃cas.authn.cognito.mapped-attributes。[custom \：netid] = netid
```




## Okta身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Okta-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.okta`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 此功能的密码编码设置在配置密钥 `cas.authn.okta`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.okta.name =
＃cas.authn.okta.order =  
＃cas.authn.okta.credential-criteria =

＃cas.authn.okta.organization-url =     

＃cas.authn.okta .connection-timeout = 5000
＃cas.authn.okta.proxy-username =
＃cas.authn.okta.proxy-password =
＃cas.authn.okta.proxy-host =
＃cas.authn.okta。 proxy-port =
```




## Microsoft Azure Active Directory身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Azure-ActiveDirectory-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.azure-active-directory`[这里](Configuration-Properties-Common.html#authentication-principal-transformation)。 此功能的密码编码设置在配置密钥 `cas.authn.azure-active-directory`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.azure-active-directory.name =
＃cas.authn.azure-active-directory.order =
＃cas.authn.azure-active-directory.credential-criteria =

＃cas.authn.azure -active-directory.client-id =
＃cas.authn.azure-active-directory.login-url = https：//login.microsoftonline.com/common/
＃cas.authn.azure-active-directory.resource = https：//graph.microsoft.com/
```




## SOAP认证

要了解更多有关这个话题， [请参阅本指南](../installation/SOAP-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.soap`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。 此功能的密码编码设置在配置密钥 `cas.authn.soap`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.soap.name =
＃cas.authn.soap.order =
＃cas.authn.soap.url =
```




## 远程地址认证

要了解更多有关这个话题， [请参阅本指南](../installation/Remote-Address-Authentication.html)。



```properties
＃cas.authn.remote-address.ip-address-range =
＃cas.authn.remote-address.name =
＃cas.authn.remote-address.order =
```





## 接受用户身份验证

<div class="alert alert-warning"><strong>默认凭证</strong><p>为了测试在CAS缺省的认证方案，
使用 <strong>casuser</strong> 和 <strong>梅隆</strong> 分别作为用户名和密码。 这些都是自动
通过静态认证处理程序配置，以及 <strong>MUST</strong> 从配置中移除
在生产之前的推出。</p></div>

此功能的主要转换设置在配置键 `cas.authn.accept`[此处为](Configuration-Properties-Common.html#authentication-principal-transformation) 可用。 此功能的密码策略设置在配置键 `cas.authn.accept.passwordPolicy`[这里](Configuration-Properties-Common.html#password-policy-settings) 是可用的。 此功能的密码编码设置在配置密钥 `cas.authn.accept`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.accept.users =
＃cas.authn.accept.name =
＃cas.authn.accept.enabled = true
＃cas.authn.accept.credential-criteria =
```




## X509认证

要了解更多有关这个话题， [请参阅本指南](../installation/X509-Authentication.html)。



### Webflow配置

此功能的Webflow自动配置设置可用 [在这里](Configuration-Properties-Common.html#webflow-auto-configuration) 在 下配置键 `cas.authn.x509.webflow`。



```properties
＃cas.authn.x509.webflow.port = 8446
＃cas.authn.x509.webflow.client-auth =想要
```




### 主要决议

X.509主体解析可以对以下主体类型起作用：

| 类型                 | 描述                                                                                                                     |
| ------------------ | ---------------------------------------------------------------------------------------------------------------------- |
| `序列号`              | <strong>基数</strong>（范围为2到36）的序列号来解析主体。 如果 <code>基数</code> 是 <code>16</code>，则序列号可以用前导零填充到偶数个数字。 |
| `SERIAL_NO_DN`     | 通过序列号和发行人dn解析主体。                                                                                                       |
| `主题`               | 通过从证书主题DN中提取一个或多个属性值并将它们与中间定界符组合来解析主体。                                                                                 |
| `SUBJECT_ALT_NAME` | 通过主题备用名称扩展名解析主体。 （类型：otherName）                                                                                        |
| `SUBJECT_DN`       | 默认类型；通过证书的主题dn解析主体。                                                                                                    |
| `CN_EDIPI`         | 通过通用名称中的电子数据交换个人标识符（EDIPI）解析主体。                                                                                        |
| `RFC822_EMAIL`     | 通过 [RFC822名称](https://tools.ietf.org/html/rfc5280#section-4.2.1.6) （也称为电子邮件地址）类型来解析主体。                                 |


为 `CN_EDIPI`，`SUBJECT_ALT_NAME`，和 `RFC822_EMAIL` 主要解析器，因为并非所有的证书具有这些属性， 可能，以便具有从用作主要的证书不同的属性指定以下属性。  
如果未指定替代属性，则主体将为null，CAS将使身份验证失败或使用其他身份验证器。



```properties
＃cas.authn.x509.alternate-principal-attribute = subjectDn | sigAlgOid | subjectX500Principal | x509Rfc822Email | x509subjectUPN
```




### CRL提取/吊销

CAS为证书吊销检查提供了灵活的策略引擎。 之所以出现此功能，是因为JSSE内置的吊销机制 

可用策略涵盖以下事件：

- CRL到期
- CRL不可用

无论哪种情况，都可以使用以下选项：

| 类型    | 描述                                      |
| ----- | --------------------------------------- |
| `允许`  | 允许进行身份验证。                               |
| `否定`  | 拒绝身份验证并阻止。                              |
| `临界点` | 适用于CRL到期，请限制请求，从而允许在阈值时间段之前但之后不允许到期的数据。 |


吊销证书检查可以通过以下方式之一进行：

| 类型      | 描述                                                                                       |
| ------- | ---------------------------------------------------------------------------------------- |
| `没有任何`  | 不执行撤销。                                                                                   |
| `证书撤销表` | `cRLDistributionPoints` 扩展字段中提到的CRL URI。 可以使用缓存来防止针对CRL端点的过多IO；如果高速缓存中不存在或已过期，则将获取CRL数据。 |
| `资源来源`  | 在固定位置托管的CRL。 定期获取CRL并进行缓存。                                                               |


要获取CRL，可以使用以下选项：

| 类型     | 描述                                     |
| ------ | -------------------------------------- |
| `资源来源` | 默认情况下，所有吊销检查都使用固定资源来从指定位置获取CRL资源。      |
| `LDAP` | 如果CRL资源位置是LDAP URI，则可以从预配置的属性中获取CRL资源。 |




```properties
＃cas.authn.x509.crl-expired-policy = DENY | ALLOW | THRESHOLD
＃cas.authn.x509.crl-unavailable-policy = DENY | ALLOW | THRESHOLD
＃cas.authn.x509.crl-resource-expired -policy = DENY | ALLOW | THRESHOLD
＃cas.authn.x509.crl-resource-unavailable-policy = DENY | ALLOW | THRESHOLD

＃cas.authn.x509.revocation-checker = NONE | CRL | RESOURCE
＃cas。 authn.x509.crl-fetcher = RESOURCE | LDAP

＃cas.authn.x509.crl-resources[0]=文件：/ ...

＃cas.authn.x509.cache-max-elements-in-memory = 1000
＃cas.authn.x509.cache-disk-overflow = false
＃cas.authn.x509.cache-disk-size = 100MB
＃ cas.authn.x509.cache-eternal = false
＃cas.authn.x509.cache-to-live-seconds = 7200

＃cas.authn.x509.check-key-usage = false
＃cas.authn .x509.revocation-policy-threshold = 172800

＃cas.authn.x509.reg-ex-subject-dn-pattern =。+
＃cas.authn.x509.reg-ex-trusted-issuer-dn-pattern = 。+

＃cas.authn.x509.name =
＃cas.authn.x509.order =

＃cas.authn.x509.principal-descriptor =
＃cas.authn.x509.max-path-length = 1
＃cas.authn.x509.throw-on-fetch-failure = false

＃cas.authn.x509.check-all = false
＃cas.authn.x509.require-key-usage = false
＃cas.authn。 x509.refresh-interval-seconds = 3600
＃cas.authn.x509.max-path-length-allow-unspecified = false

＃SUBJECT_DN
＃cas.authn.x509.subject-dn.format = [DEFAULT，RFC1779， RFC2253，[规范]
```


| 类型        | 描述                                                                                                                                                                                                                                       |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `默认`      | 调用certificate.getSubjectDN（）方法以实现向后兼容，但该方法为 [“已降级”](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/cert/X509Certificate.html#getIssuerDN())。                                                                 |
| `RFC1779` | 调用 [X500Principal.getName（“ RFC1779”）](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/security/auth/x500/X500Principal.html#getName()) ，它发出带有RFC 1779中定义的属性关键字（CN，L，ST，O，OU，C，STREET）的主题DN。 任何其他属性类型都作为OID发出。        |
| `RFC2253` | 调用 [X500Principal.getName（“ RFC2253”）](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/security/auth/x500/X500Principal.html#getName()) ，它发出带有RFC 2253中定义的属性关键字（CN，L，ST，O，OU，C，STREET，DC，UID）的主题DN。 任何其他属性类型都作为OID发出。 |
| `典范`      | 调用X500Principal.getName（“ CANONICAL”，它发出以RFC 2253开头的主题DN，并应用 [javadoc](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/security/auth/x500/X500Principal.html#getName())描述的其他规范化。                                      |




```properties

＃cas.authn.x509.serial-no-dn.serial-number-prefix = SERIALNUMBER =
＃cas.authn.x509.serial-no-dn.value-delimiter =，

＃SERIAL_NO
＃cas。 authn.x509.serial-no.principal-sn-radix = 10
＃cas.authn.x509.serial-no.principal-hex-sn-zero-padding = false

＃SUBJECT_ALT_NAME
＃cas.authn.x509.subject -alt-name.alternate-principal-attribute = [sigAlgOid | subjectDn | subjectX500Principal | x509Rfc822Email]

＃CN_EDIPI 
＃cas.authn.x509.cn-edipi.alternate-principal-attribute = [sigAlgOid | RjectfPrincipal | Subjectcn | 822 [x509subjectUPN]

＃RFC822_EMAIL 
＃cas.authn.x509.rfc822-email.alternate-principal-attribute = [sigAlgOid | subjectDn | subjectX500Principal | x509subjectUPN]
```




### X509证书提取

这些设置可用于打开CAS并将其配置为 以从HTTP请求标头上的base64编码证书 提取X509证书（由CAS前面的代理放置）。 如果将其设置为true，则重要的是 ，并且代理必须确保标头 永远不会来自浏览器。



```properties
＃cas.authn.x509.extract-cert = false
＃cas.authn.x509.ssl-header-name = ssl_client_cert
```


证书的特定解析逻辑 Apache HTTPD，Nginx，Haproxy，BigIP F5等设置的标头一起使用。



### X509主要分辨率



```properties
＃cas.authn.x509.principal-type = SERIAL_NO | SERIAL_NO_DN | SUBJECT | SUBJECT_ALT_NAME | SUBJECT_DN
```


此功能的主要分辨率和人员目录设置在此处 [（在配置键 `cas.authn.x509.principal`](Configuration-Properties-Common.html#person-directory-principal-resolution)。



### X509 LDAP集成

对于X509特征LDAP设置（如果取从LDAP CRL所使用的）是可用 [这里](Configuration-Properties-Common.html#ldap-connection-settings) 所述配置密钥下 `cas.authn.x509.ldap`。

请参阅LDAP属性存储库 [此处](Configuration-Properties.html#ldap) 以使用从X509证书中提取的主体获取其他LDAP属性。 



## Syncope身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Syncope-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.syncope`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置密钥 `cas.authn.syncope`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.syncope.domain = Master
＃cas.authn.syncope.url = https：//idm.instance.org/syncope
＃cas.authn.syncope.name =
```




## Shiro认证

要了解更多有关这个话题， [请参阅本指南](../installation/Shiro-Authentication.html)。

此功能的主要转换设置在配置键 `cas.authn.shiro`[这里](Configuration-Properties-Common.html#authentication-principal-transformation) 是可用的。

此功能的密码编码设置在配置密钥 `cas.authn.shiro`[这里](Configuration-Properties-Common.html#password-encoding) 是可用的。



```properties
＃cas.authn.shiro.required-permissions = value1，value2，...
＃cas.authn.shiro.required-roles = value1，value2，...
＃cas.authn.shiro.location = classpath：shiro.ini
＃cas.authn.shiro.name =
```




## 可信认证

要了解更多有关这个话题， [请参阅本指南](../installation/Trusted-Authentication.html)。 此功能的主要分辨率和人员目录设置在此处 [（在配置键 `cas.authn.trusted`](Configuration-Properties-Common.html#person-directory-principal-resolution) 可用。



```properties
＃cas.authn.trusted.name =
＃cas.authn.trusted.order =
＃cas.authn.trusted.remote-principal-header =
```




## WS-Fed委托认证

要了解更多有关这个话题， [请参阅本指南](../integration/ADFS-Integration.html)。



### 属性类型

为了构造最终的经过身份验证的主体，可以在收集主体属性时 

| 类型            | 描述                            |
| ------------- | ----------------------------- |
| `中国科学院`       | 使用CAS自己的属性解析机制和存储库提供的属性。      |
| `世界可持续发展经济论坛` | 使用委托的WS-Fed实例提供的属性。           |
| `两个都`         | 结合以上两个选项，其中CAS属性存储库优先于WS-Fed。 |




```properties
＃cas.authn.wsfed[0].identity-provider-url = https：//adfs.example.org/adfs/ls/
＃cas.authn.wsfed[0].identity-provider-identifier = https：//adfs.example .org / adfs / services / trust
＃cas.authn.wsfed[0].relying-party-identifier = urn：cas：localhost
＃cas.authn.wsfed[0].signing-certificate-resources = classpath：adfs-signing.crt
＃cas.authn.wsfed[0].identity-attribute =

＃cas.authn.wsfed[0].attributes-type = WSFED
＃cas.authn.wsfed[0].tolerance = 10000
＃cas.authn.wsfed[0].attribute -resolver-enabled = true
＃cas.authn.wsfed[0].auto-redirect = true
＃cas.authn.wsfed[0].name =
＃cas.authn.wsfed[0].attribute-mutator-script.location = file： /etc/cas/config/wsfed-attr.groovy

＃cas.authn.wsfed[0].principal.principal-attribute =
＃cas.authn.wsfed[0].principal.return-null = false

＃使用的私有/公共密钥对解密断言（如果有）。
＃cas.authn.wsfed[0].encryption-private-key = classpath：private.key
＃cas.authn.wsfed[0].encryption-certificate = classpath：certificate.crt
＃cas.authn.wsfed[0].encryption-private-密钥密码=无
```




### 签名 & 加密

签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 此功能的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在 `cas.authn.wsfed[0].cookie`。



## 多因素身份验证

要了解更多有关这个话题， [请参阅本指南](../mfa/Configuring-Multifactor-Authentication.html)。



```properties
＃描述在无法到达提供者的情况下的全局失败模式
＃cas.authn.mfa.global-failure-mode = CLOSED

＃设计选择用来传递身份验证上下文的属性
＃cas.authn.mfa.authentication-context- attribute = authnContextClass

＃识别非浏览器MFA请求的请求内容类型
＃cas.authn.mfa.content-type = application / cas
```




### 多因素身份验证：全局触发器



```properties
＃全局激活所有的MFA，而不考虑其他设置
＃cas.authn.mfa.global-provider-id = mfa-duo
```




### 多因素身份验证：身份验证属性触发器



```properties
＃根据身份验证元数据属性全局激活MFA
＃cas.authn.mfa.global-authentication-attribute-name-triggers = customAttributeName
＃cas.authn.mfa.global-authentication-attribute-value-regex = customRegexValue
```




### 多因素身份验证：主体属性触发器



```properties
＃根据主要属性全局激活MFA
＃cas.authn.mfa.global-principal-attribute-name-triggers = memberOf，eduPersonPrimaryAffiliation

＃指定使用单个提供程序时触发多因素的正则表达式模式。
＃与多个多因素提供程序一起使用时注释掉设置
＃cas.authn.mfa.global-principal-attribute-value-regex = faculty | staffy | staff

＃基于主体属性和基于Groovy的谓词全局激活MFA
＃cas .authn.mfa.global-principal-attribute-predicate = file：/etc/cas/PredicateExample.groovy
```




### 多因素身份验证：REST API触发器

此功能的RESTful设置可用 [在这里](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.authn.mfa.rest`。



### 多因素身份验证：Groovy触发器



```properties
＃根据Groovy脚本激活MFA
＃cas.authn.mfa.groovyScript = file：/etc/cas/mfaGroovyTrigger.groovy
```




### 多因素身份验证：Internet2石斑鱼触发器



```properties
＃根据Internet2的Grouper
激活MFA＃cas.authn.mfa.grouper-group-field = NAME | EXTENSION | DISPLAY_NAME | DISPLAY_EXTENSION
```




### 多因素身份验证：Http请求触发器



```properties
＃cas.authn.mfa.request-parameter = authn_method
＃cas.authn.mfa.request-header = authn_method
＃cas.authn.mfa.session-attribute = authn_method
```




### 多因素身份验证：提供商选择



```properties
＃如果通过Groovy脚本解析了多个，则选择MFA提供程序
＃cas.authn.mfa.provider-selector-groovy-script = file：/etc/cas/mfaGroovySelector.groovy

＃启用提供程序选择菜单比一个
cas.authn.mfa.provider-selection-enabled = true
```




### 多因素可信设备/浏览器

要了解更多有关这个话题， [请参阅本指南](../mfa/Multifactor-TrustedDevice-Authentication.html)。



```properties
＃cas.authn.mfa.trusted.authentication-context-attribute = isFromTrustedMultifactorAuthentication
＃cas.authn.mfa.trusted.device-registration-enabled = true
＃cas.authn.mfa.trusted.key-generator-type = DEFAULT |遗产
```


以下策略可用于生成可信设备记录的密钥：

| 类型   | 描述                                |
| ---- | --------------------------------- |
| `默认` | 使用用户名，设备名称和设备指纹的组合来生成设备密钥。        |
| `遗产` | 不推荐使用。 使用用户名，记录日期和设备指纹的组合来生成设备密钥。 |




#### 签名 & 加密

签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 此功能的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.authn.mfa.trusted`。



#### JSON存储



```properties
＃cas.authn.mfa.trusted.json.location = file：/etc/cas/config/trusted-dev.json
```




#### JDBC存储

此功能的数据库设置在配置键 `cas.authn.mfa.trusted.jpa`[此处](Configuration-Properties-Common.html#database-settings) 可用。



#### CouchDb储存

此功能的配置设置在 [此处为](Configuration-Properties-Common.html#couchdb-configuration) 配置键 `cas.authn.mfa.trusted`下可用。



#### MongoDb储存

此功能的配置设置在 [此处为](Configuration-Properties-Common.html#mongodb-configuration) 配置键 `cas.authn.mfa.trusted`下可用。



#### DynamoDb存储

此功能的通用配置设置在配置项 `cas.authn.mfa.trusted`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 是可用的。

此功能的AWS设置在配置键 `cas.authn.mfa.trusted.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



```properties
＃cas.authn.mfa.trusted.dynamo-db.tableName = DynamoDbCasMfaTrustRecords
```




#### REST存储



```properties
＃cas.authn.mfa.trusted.rest.endpoint = https：//api.example.org/trustedBrowser
```




#### 可信设备指纹



```properties
＃cas.authn.mfa.trusted.device-fingerprint.componentSeparator = @  

＃cas.authn.mfa.trusted.device-fingerprint.cookie.enabled = true
＃cas.authn.mfa.trusted.device-fingerprint.cookie .order = 1

＃cas.authn.mfa.trusted.device-fingerprint.client-ip.enabled = true
＃cas.authn.mfa.trusted.device-fingerprint.client-ip.order = 2

＃cas。 authn.mfa.trusted.device-fingerprint.geolocation.enabled = false
＃cas.authn.mfa.trusted.device-fingerprint.geolocation.order = 4
```


该装置指纹饼干组件可以与普通Cookie属性被配置发现 [这里](Configuration-Properties-Common.html#cookie-properties) 所述配置密钥下 `cas.authn.mfa.trusted.device-fingerprint.cookie`。 默认cookie名称设置为 `MFATRUSTED` ，默认maxAge设置为 `2592000`。

设备指纹Cookie组件支持签名 & 加密。 签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 此功能的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.authn.mfa.trusted.device-fingerprint.cookie`。



#### 清洁工

计划在后台运行一个更清洁的进程，以清理过期的和过时的票证。 本节控制该过程的行为方式。 此功能的调度器设置 可用 [这里](Configuration-Properties-Common.html#job-scheduling) 所述配置密钥下 `cas.authn.mfa.trusted.cleaner`。




### 简单的多因素身份验证

要了解更多有关这个话题， [请参阅本指南](../mfa/Simple-Multifactor-Authentication.html)。



```properties
＃cas.authn.mfa.simple.name =
＃cas.authn.mfa.simple.order =
＃cas.authn.mfa.simple.time-to-kill-in-seconds = 30
＃cas.authn。 mfa.simple.token-length = 6
```


此功能的电子邮件通知设置在配置键 `cas.authn.mfa.simple`[这里](Configuration-Properties-Common.html#email-notifications) 是可用的。 此功能的SMS通知设置为 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 在配置键 `cas.authn.mfa.simple`。

此提供多因素认证旁路设置可用 [这里](Configuration-Properties-Common.html#multifactor-authentication-bypass) 的配置密钥下 `cas.authn.mfa.simple`。



### Google身份验证器

要了解更多有关这个话题， [请参阅本指南](../mfa/GoogleAuthenticator-Authentication.html)。



```properties
＃cas.authn.mfa.gauth.issuer =
＃cas.authn.mfa.gauth.label =

＃cas.authn.mfa.gauth.window-size = 3
＃cas.authn.mfa.gauth.code- digits = 6
＃cas.authn.mfa.gauth.time-step-size = 30
＃cas.authn.mfa.gauth.rank = 0
＃cas.authn.mfa.gauth.trusted-device-enabled = false
＃cas.authn.mfa.gauth.multiple-device-registration-enabled = false

＃cas.authn.mfa.gauth.name =
＃cas.authn.mfa.gauth.order =
```


此提供程序的多因素身份验证绕过设置在配置项 `cas.authn.mfa.gauth`下的此处 [ ](Configuration-Properties-Common.html#multifactor-authentication-bypass)可用。 此功能的调度程序设置在配置键 `cas.authn.mfa.gauth.cleaner`[这里](Configuration-Properties-Common.html#job-scheduling) 是可用的。



#### 签名 & 加密

签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。  该功能的签名 & 可用 [这里是配置密钥 `cas.authn.mfa.gauth`](Configuration-Properties-Common.html#signing--encryption)。



#### Google身份验证器CouchDb

此功能的配置设置可用 [在这里](Configuration-Properties-Common.html#couchdb-configuration) 在配置键 `cas.authn.mfa.gauth`。  



#### Google身份验证器JSON



```properties
＃cas.authn.mfa.gauth.json.location = file：/somewhere.json
```




#### Google Authenticator Rest

此功能的RESTful设置在配置项 `cas.authn.mfa.gauth.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。

此外，可以使用以下设置通过REST管理令牌：



```properties
＃cas.authn.mfa.gauth.rest.token-url = https：//somewhere.gauth.com
```




#### Google Authenticator MongoDb

此功能的配置设置可用 [在这里](Configuration-Properties-Common.html#mongodb-configuration) 在配置键 `cas.authn.mfa.gauth`。  此功能还可以使用以下设置：



```properties
＃cas.authn.mfa.gauth.mongo.token-collection = MongoDbGoogleAuthenticatorTokenRepository
```




#### Google身份验证器LDAP

此功能的LDAP设置在 [在此处](Configuration-Properties-Common.html#ldap-connection-settings) 在配置键 `cas.authn.mfa.gauth.ldap`下可用。 

此功能还可以使用以下设置：



```properties
＃cas.authn.mfa.gauth.ldap.account-attribute-name = gauthRecord
```




#### Google Authenticator Redis

此功能的配置设置在配置项 `cas.authn.mfa.gauth`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。  




#### Google Authenticator JPA

此功能的数据库设置在配置键 `cas.authn.mfa.gauth.jpa`[此处](Configuration-Properties-Common.html#database-settings) 可用。



### YubiKey

要了解更多有关这个话题， [请参阅本指南](../mfa/YubiKey-Authentication.html)。



```properties
＃cas.authn.mfa.yubikey.client-id =
＃cas.authn.mfa.yubikey.secret-key =
＃cas.authn.mfa.yubikey.rank = 0
＃cas.authn.mfa.yubikey。 api-urls =
＃cas.authn.mfa.yubikey.trusted-device-enabled = false
＃cas.authn.mfa.yubikey.multiple-device-registration-enabled = false

＃cas.authn.mfa.yubikey。名称=
＃cas.authn.mfa.yubikey.order =
```


此提供程序的多因素身份验证绕过设置为 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 在配置密钥 `cas.authn.mfa.yubikey`。



#### YubiKey REST设备商店

此功能的RESTful设置在配置项 `cas.authn.mfa.yubikey.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



#### YubiKey JSON设备商店



```properties
＃cas.authn.mfa.yubikey.json-file = file：/etc/cas/deviceRegistrations.json
```




#### YubiKey允许的设备存储



```properties
＃cas.authn.mfa.yubikey.allowed-devices.uid1 = yubikeyPublicId1
＃cas.authn.mfa.yubikey.allowed-devices.uid2 = yubikeyPublicId2
```




#### YubiKey注册记录加密和签名



```properties
＃cas.authn.mfa.yubikey.crypto.enabled = true
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.authn.mfa.yubikey`。



### YubiKey JPA设备商店

此功能的数据库设置在配置键 `cas.authn.mfa.yubikey.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



### YubiKey CouchDb设备商店

此功能的配置设置可用 [在这里](Configuration-Properties-Common.html#couchdb-configuration) 在配置键 `cas.authn.mfa.yubikey`。



### YubiKey MongoDb设备商店

此功能的配置设置可用 [在这里](Configuration-Properties-Common.html#mongodb-configuration) 在配置键 `cas.authn.mfa.yubikey`。



### YubiKey DynamoDb设备商店

此功能的配置设置可用 [在这里](Configuration-Properties-Common.html#dynamodb-configuration) 在配置键 `cas.authn.mfa.yubikey`。



### YubiKey Redis设备商店

此功能的常规配置设置在配置键 `cas.authn.mfa.yubikey`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



### 半径OTP

要了解更多有关这个话题， [请参阅本指南](../mfa/RADIUS-Authentication.html)。



```properties
＃cas.authn.mfa.radius.rank = 0
＃cas.authn.mfa.radius.trusted-device-enabled = false
＃cas.authn.mfa.radius.allowed-authentication-attempts = -1
＃cas .authn.mfa.radius.name =
＃cas.authn.mfa.radius.order =
```


此功能的半径设置在配置键 `cas.authn.mfa.radius`[这里](Configuration-Properties-Common.html#radius-configuration) 是可用的。

此提供程序的多因素身份验证绕过设置在配置项 `cas.authn.mfa.radius`[此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 可用。



### 双重安全

要了解更多有关这个话题， [请参阅本指南](../mfa/DuoSecurity-Authentication.html)。



```properties
＃cas.authn.mfa.duo[0].duo-secret-key =
＃cas.authn.mfa.duo[0].rank = 0
＃cas.authn.mfa.duo[0].duo-application-key =
＃cas .authn.mfa.duo[0].duo-integration-key =
＃cas.authn.mfa.duo[0].duo-api-host =
＃cas.authn.mfa.duo[0].trusted-device-enabled = false
＃cas.authn.mfa.duo[0].id = mfa-duo
＃cas.authn.mfa.duo[0].registration-url = https：//registration.example.org/duo-enrollment
＃cas.authn.mfa .duo[0].name =
＃cas.authn.mfa.duo[0].order =
```


此提供程序的多因素身份验证绕过设置为 可用 [这里](Configuration-Properties-Common.html#multifactor-authentication-bypass) 在 以下3配置密钥 `cas.authn.mfa.duo[0]`。




#### Web SDK

`duo-application-key` 生成的必需字符串，至少40个字符长，并且对Duo保密。 您可以使用以下命令在Python中生成随机字符串：



```python
import os，hashlib
打印hashlib.sha1（os.urandom（32））。hexdigest（）
```




#### 通用提示

通用提示不再需要您生成和使用应用程序密钥值。 取而代之的是，它需要一个 *客户端ID* 和 *的客户端秘密*， ，其是已知的并且使用集成密钥和秘密密钥的配置设置教导CAS。 将CAS注册为受保护的应用程序时，需要从Duo Security获取集成密钥， 



### FIDO2 WebAuthn

要了解更多有关这个话题， [请参阅本指南](../mfa/FIDO2-WebAuthn-Authentication.html)。



```properties
＃cas.authn.mfa.web-authn.allowed-origins =
＃cas.authn.mfa.web-authn.application-id =
＃cas.authn.mfa.web-authn.relying-party-name = CAS WebAuthn 
＃cas.authn.mfa.web-authn.relying-party-id =

＃cas.authn.mfa.web-authn.display-name-attribute = displayName
＃cas.authn.mfa.web-authn。 allow-primary-authentication = false

＃cas.authn.mfa.web-authn.allow-unrequested-extensions = false
＃cas.authn.mfa.web-authn.allow-untrusted-attestation = false
＃cas.authn .mfa.web-authn.validate-signature-counter = true
＃cas.authn.mfa.web-authn.attestation-conveyance-preference = DIRECT | INDIRECT | NONE
＃cas.authn.mfa.web-authn.trusted -device-metadata.location =

＃cas.authn.mfa.web-authn.trusted-device-enabled = false

＃cas.authn.mfa.web-authn.expire-devices = 30
＃cas.authn.mfa .web-authn.expire-devices-time-unit =天
```


此提供程序的多因素身份验证绕过设置为 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 在配置密钥 `cas.authn.mfa.web-authn`。

签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 在配置密钥 `cas.authn.mfa.web-authn`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以使用此功能的签名 & 加密设置。                                                      



### FIDO2 WebAuthn清洁程序

此功能的调度程序设置为 可用 [这里](Configuration-Properties-Common.html#job-scheduling) 在配置键 `cas.authn.mfa.web-authn.cleaner`。



### FIDO2 WebAuthn JSON



```properties
＃cas.authn.mfa.web-authn.json.location = file：///etc/cas/config/devices.json
```




### FIDO2 WebAuthn MongoDb

此功能的常用配置设置为 可用 [此处](Configuration-Properties-Common.html#mongodb-configuration) 在配置键 `cas.authn.mfa.web-authn`。



### FIDO2 WebAuthn LDAP

此功能的常用配置设置为 可用 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 在配置键 `cas.authn.mfa.web-authn.ldap`。



```properties
＃cas.authn.mfa.web-authn.ldap.account-attribute-name = casWebAuthnRecord
```




### FIDO2 WebAuthn JPA

此功能的数据库设置在配置键 `cas.authn.mfa.web-authn.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



### FIDO2 WebAuthn Redis

此功能的常用配置设置在配置键 `cas.authn.mfa.web-authn`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



### FIDO2 WebAuthn DynamoDb

此功能的常用配置设置在配置键 `cas.authn.mfa.web-authn`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 是可用的。

该功能的AWS设置在配置键 `cas.authn.mfa.web-authn.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



### FIDO U2F

要了解更多有关这个话题， [请参阅本指南](../mfa/FIDO-U2F-Authentication.html)。



```properties
＃cas.authn.mfa.u2f.rank = 0
＃cas.authn.mfa.u2f.name =
＃cas.authn.mfa.u2f.order =

＃U2F设备注册请求到期：
＃cas.authn .mfa.u2f.expire-registrations = 30
＃cas.authn.mfa.u2f.expire-registrations-time-unit = SECONDS

＃自注册以来U2F设备的到期时间，与上次使用的时间无关：
＃cas.authn。 mfa.u2f.expire-devices = 30
＃cas.authn.mfa.u2f.expire-devices-time-unit = DAYS
```


此提供程序的多因素身份验证绕过设置为 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 在配置密钥 `cas.authn.mfa.u2f`。 签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 在配置密钥 `cas.authn.mfa.u2f`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以使用此功能的签名 & 加密设置。



### FIDO U2F JSON



```properties
＃cas.authn.mfa.u2f.json.location = file：///etc/cas/config/u2fdevices.json
```




### FIDO U2F清洁剂

此功能的计划程序设置为 可用 [这里](Configuration-Properties-Common.html#job-scheduling) 在配置键 `cas.authn.mfa.u2f.cleaner`。



### FIDO U2F CouchDb

此功能的常用配置设置为 可用 [此处](Configuration-Properties-Common.html#couchdb-configuration) 在配置键 `cas.authn.mfa.u2f`。



### FIDO U2F MongoDb

此功能的常用配置设置为 可用 [此处](Configuration-Properties-Common.html#mongodb-configuration) 在配置键 `cas.authn.mfa.u2f`。



### FIDO U2F DynamoDb

此功能的常用配置设置在配置键 `cas.authn.mfa.u2f`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 是可用的。

此功能的AWS设置在配置键 `cas.authn.mfa.u2f.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



### FIDO U2F Redis

此功能的常用配置设置在配置键 `cas.authn.mfa.u2f`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



### FIDO U2F JPA

此功能的数据库设置在配置键 `cas.authn.mfa.u2f.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



### FIDO U2F休息

此功能的RESTful设置在配置键 `cas.authn.mfa.u2f.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



### FIDO U2F Groovy



```properties
＃cas.authn.mfa.u2f.groovy.location = file：/etc/cas/config/fido.groovy
```




### 旋转安全

要了解更多有关这个话题， [请参阅本指南](../mfa/SwivelSecure-Authentication.html)。



```properties
＃cas.authn.mfa.swivel.swivel-turing-image-url = https：//turing.example.edu/TURingImage
＃cas.authn.mfa.swivel.swivel-url = https：//swivel.example。组织/ pinsafe
＃cas.authn.mfa.swivel.shared-secret=Th3Sh@r3d$ecret
＃cas.authn.mfa.swivel.ignore-SSL-错误=假
＃cas.authn.mfa.swivel.rank = 0
＃cas.authn.mfa.swivel.name =
＃cas.authn.mfa.swivel.order =
```


此提供程序的多因素身份验证绕过设置在配置密钥 `cas.authn.mfa.swivel`[这里](Configuration-Properties-Common.html#multifactor-authentication-bypass) 是可用的。




### Authy

要了解更多有关这个话题， [请参阅本指南](../mfa/AuthyAuthenticator-Authentication.html)。



```properties
＃cas.authn.mfa.authy.api-key =
＃cas.authn.mfa.authy.api-url =
＃cas.authn.mfa.authy.phone-attribute =电话
＃cas.authn.mfa。 authy.mail-attribute = mail
＃cas.authn.mfa.authy.country-code = 1
＃cas.authn.mfa.authy.force-verification = true
＃cas.authn.mfa.authy.trusted-device -enabled = false
＃cas.authn.mfa.authy.name =
＃cas.authn.mfa.authy.order =
```


此提供程序的多因素身份验证绕过设置为 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 在配置密钥 `cas.authn.mfa.authy`。




### 接受

要了解更多有关这个话题， [请参阅本指南](../mfa/Acceptto-Authentication.html)。



```properties
＃cas.authn.mfa.acceptto.application-id =
＃cas.authn.mfa.acceptto.secret =
＃cas.authn.mfa.acceptto.organization-id =
＃cas.authn.mfa.acceptto.organization-id -secret =

＃cas.authn.mfa.acceptto.authn-selection-url = https：//mfa.acceptto.com/mfa/index
＃cas.authn.mfa.acceptto.api-url = https：// mfa.acceptto.com/api/v9/
＃cas.authn.mfa.acceptto.message =您要通过CAS登录吗？
＃cas.authn.mfa.acceptto.timeout = 120
＃cas.authn.mfa.acceptto.email-attribute = mail    
＃cas.authn.mfa.acceptto.group-attribute =    

＃cas.authn.mfa.acceptto .registration-api-url = https：//mfa.acceptto.com/api/integration/v1/mfa/authenticate
＃cas.authn.mfa.acceptto.registration-api-public-key = file：/ path / to /publickey.pem

＃cas.authn.mfa.acceptto.order =
＃cas.authn.mfa.acceptto.order =
＃cas.authn.mfa.acceptto.rank = 0
```


此提供程序的多因素身份验证绕过设置在配置项 `cas.authn.mfa.acceptto`[此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 可用。



## SAML核心

控制CAS中的核心SAML功能。



```properties
＃cas.saml-core.ticketid-saml2 = false
＃cas.saml-core.skew-allowance = 5
＃cas.saml-core.issue-length = 30
＃cas.saml-core.attribute-namespace = http://www.ja-sig.org/products/cas/
＃cas.saml-core.issuer = localhost
＃cas.saml-core.security-manager = org.apache.xerces.util.SecurityManager
```




## SAML IDP

允许CAS成为SAML2身份提供者。

要了解更多有关这个话题， [请参阅本指南](../installation/Configuring-SAML2-Authentication.html)。



```properties
＃cas.authn.saml-idp.entity-id = https：//cas.example.org/idp
＃cas.authn.saml-idp.replicate-sessions = false

＃cas.authn.saml-idp.authentication -context-class-mappings[0]=缸：绿洲：名称：tc：SAML：2.0：ac：classes：SomeClassName->mfa-duo
＃cas.authn.saml-idp.authentication-context-class-mappings[1]= https ：//refeds.org/profile/mfa->mfa-gauth

＃cas.authn.saml-idp.attribute-friendly-names[0]= urn：oid：1.3.6.1.4.1.5923.1.1.1.6->eduPersonPrincipalName

＃cas.authn.saml-idp.attribute-query-profile-enabled = true
```




### 属性名称格式

单个属性的名称格式可以映射为多种预定义格式，也可以映射为您自己选择的自定义格式。 要在最终SAML响应中进行编码的给定属性可以包含以下任何一种名称格式：

| 类型                  | 描述                                                          |
| ------------------- | ----------------------------------------------------------- |
| `基本的`               | 将属性映射到 `urn：oasis：names：tc：SAML：2.0：attrname-format：basic`。 |
| `乌里`                | 将属性映射到 `urn：oasis：names：tc：SAML：2.0：attrname-format：uri`。   |
| `未指定`               | 将属性映射到 `urn：oasis：names：tc：SAML：2.0：attrname-format：basic`。 |
| `urn：my：own：format` | 将属性映射到 `urn：my：own：format`。                                 |




### SAML元数据



```properties
＃cas.authn.saml-idp.metadata.location =文件：/ etc / cas / saml
＃cas.authn.saml-idp.metadata.metadata-backup-location =

＃cas.authn.saml-idp.metadata .cache-expiration-minutes = 30
＃cas.authn.saml-idp.metadata.fail-fast = true
＃cas.authn.saml-idp.metadata.private-key-alg-name = RSA
＃cas。 authn.saml-idp.metadata.require-valid-metadata = true
＃cas.authn.saml-idp.metadata.force-metadata-refresh = true

＃cas.authn.saml-idp.metadata.basic-authn-用户名=
＃cas.authn.saml-idp.metadata.basic-authn-密码=
＃cas.authn.saml-idp.metadata.supported-content-types =
```




#### SAML元数据JPA

此功能的数据库设置在配置键 `cas.authn.saml-idp.metadata.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



```properties
＃cas.authn.saml-idp.metadata.jpa.idp-metadata-enabled = true
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签名 & 此功能的加密设置可用 [在此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.authn.saml-idp.metadata.jpa`。



#### SAML元数据CouchDb

此功能的通用配置设置在配置键 `cas.authn.saml-idp.metadata`[这里](Configuration-Properties-Common.html#couchdb-configuration) 是可用的。



```properties
＃cas.authn.saml-idp.metadata.couch-db.idp-metadata-enabled = true
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签名 & 加密 可以使用此功能的设置 [在这里](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.authn.saml-idp.metadata.mongo`。



#### SAML元数据Git



```properties
＃cas.authn.saml-idp.metadata.git.idp-metadata-enabled = true
```


此功能的通用配置设置在配置键 `cas.authn.saml-idp.metadata`[这里](Configuration-Properties-Common.html#git-configuration) 是可用的。

签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签名 & 此功能的加密设置可用 [在这里](Configuration-Properties-Common.html#signing--encryption) 在 配置密钥 `cas.authn.saml-idp.metadata.git`。



#### SAML元数据MongoDb

此功能的常用配置设置为 [此处](Configuration-Properties-Common.html#mongodb-configuration) 为配置键 `cas.authn.saml-idp.metadata`。



```properties
＃cas.authn.saml-idp.metadata.mongo.idp-metadata-collection = saml-idp-metadata
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签名 & 此功能的加密设置可用 [在这里](Configuration-Properties-Common.html#signing--encryption) 在 配置密钥 `cas.authn.saml-idp.metadata.mongo`。



#### SAML元数据REST

此功能的RESTful设置在配置键 `cas.authn.saml-idp.metadata.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



```properties
＃cas.authn.saml-idp.metadata.rest.idp-metadata-enabled = true
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签名 & 此功能的加密设置可用 [在这里](Configuration-Properties-Common.html#signing--encryption) 在 配置密钥 `cas.authn.saml-idp.metadata.rest`。



#### SAML元数据Amazon S3

在配置键 `cas.authn.saml-idp.metadata.amazon-s3`下，此功能的常用AWS设置可用 [这里](Configuration-Properties-Common.html#amazon-integration-settings) 可用。

签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 签名 & 加密 此功能的设置可用 [在此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.authn.saml-idp.metadata.amazon-s3`。



```properties
＃cas.authn.saml-idp.metadata.amazon-s3.bucket-name = saml-sp-bucket
＃cas.authn.saml-idp.metadata.mongo.idp-metadata-bucket-name = saml-idp-桶
```




### SAML注销



```properties
＃cas.authn.saml-idp.logout.force-signed-logout-requests = true
＃cas.authn.saml-idp.logout.single-logout-callbacks-disabled = false
＃cas.authn.saml-idp .logout.sign-logout-response = false
＃cas.authn.saml-idp.logout.send-logout-response = true
＃cas.authn.saml-idp.logout.logout-response-binding =
```




### SAML算法 & 安全性



```properties
＃cas.authn.saml-idp.algs.override-signature-canonicalization-algorithm =
＃cas.authn.saml-idp.algs.override-data-encryption-algorithms =
＃cas.authn.saml-idp.algs .override-key-encryption-algorithms =
＃cas.authn.saml-idp.algs.override-blocked-encryption-algorithms =
＃cas.authn.saml-idp.algs.override-allowed-algorithms =
＃cas .authn.saml-idp.algs.override-signature-reference-digest-methods =
＃cas.authn.saml-idp.algs.override-signature-algorithms =
＃cas.authn.saml-idp.algs.override -blocked-signature-signing-algorithms =
＃cas.authn.saml-idp.algs.override-allowed-signature-signing-algorithms =
```




### SAML回应



```properties
＃cas.authn.saml-idp.response.default-authentication-context-class =
＃cas.authn.saml-idp.response.default-属性名称格式= uri
＃cas.authn.saml-idp。 response.sign-error = false
＃cas.authn.saml-idp.response.signing-credential-type = X509 | BASIC
＃cas.authn.saml-idp.response.attribute-name-formats = attributeName->基本| uri |未指定|自定义格式等，...
```




### SAML票证



```properties
＃cas.authn.saml-idp.ticket.saml-artifacts-cache-storage-name = samlArtifactsCache
＃cas.authn.saml-idp.ticket.saml-attribute-query-cache-storage-name = samlAttributeQueryCache
```




### SAML配置文件



```properties
＃cas.authn.saml-idp.profile.slo.url-decode-redirect-request = false
＃cas.authn.saml-idp.profile.sso.url-decode-redirect-request = false
＃cas.authn .saml-idp.profile.sso-post-simple-sign.url-decode-redirect-request = false
```




## SAML SP

允许CAS注册并启用许多内置的SAML服务提供商集成。 要了解更多有关这个话题， [请参阅本指南](../integration/Configuring-SAML-SP-Integrations.html)。

<div class="alert alert-warning"><strong>记住</strong><p>此处列出的SAML2服务提供商集成只是尝试根据已知的和文档化的集成准则以及供应商所拥有的服务提供商提供的配方来自动执行CAS配置。 这些配方会随着时间的推移而改变和破坏CAS。</p></div>

所有SAML2服务提供商的配置设置均为 [，此处](Configuration-Properties-Common.html#saml2-service-provider-integrations)可用。

| 服务提供者                | 配置键                            | 属性                                                                                                                                              |
| -------------------- | ------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| GitLab               | `cas.saml-sp.gitlab`           | `姓氏`，`first_name的`，`名`                                                                                                                          |
| 希普查                  | `cas.saml-sp.hipchat`          | `last_name`，`first_name`，`title`                                                                                                                |
| 投寄箱                  | `cas.saml-sp.dropbox`          | `邮件`                                                                                                                                            |
| 雅典                   | `cas.saml-sp.openAthens`       | `电子邮件`， `eduPersonPrincipalName`                                                                                                                |
| 艾格尼特                 | `cas.saml-sp.egnyte`           | 不适用                                                                                                                                             |
| 永桥                   | `cas.saml-sp.ever-bridge`      | 不适用                                                                                                                                             |
| 简单                   | `cas.saml-sp。简单性`              | 不适用                                                                                                                                             |
| 应用程式动态               | `cas.saml-sp.app-dynamics`     | `User.OpenIDName`， `User.email`， `User.fullName`， `AccessControl的`， `组-成员`                                                                      |
| 优佳                   | `cas.saml-sp.yuja`             | 不适用                                                                                                                                             |
| 简单                   | `cas.saml-sp。简单性`              | 不适用                                                                                                                                             |
| 新遗物                  | `cas.saml-sp.new-relic`        | 不适用                                                                                                                                             |
| 阳光州教育 & 研究计算联盟       | `cas.saml-sp.sserca`           | 不适用                                                                                                                                             |
| 彻威尔                  | `cas.saml-sp.cherWell`         | 不适用                                                                                                                                             |
| 财务管理信息系统             | `cas.saml-sp.famis`            | 不适用                                                                                                                                             |
| 拜恩德                  | `cas.saml-sp.bynder`           | 不适用                                                                                                                                             |
| 网络顾问                 | `cas.saml-sp.webAdvisor`       | `uid`                                                                                                                                           |
| Adobe Creative Cloud | `cas.saml-sp.adobe-cloud`      | `的firstName`， `的lastName`， `电子邮件`                                                                                                               |
| 保护人类                 | `cas.saml-sp.sans-sth`         | `的firstName`， `的lastName`， `scopedUserId`， `部`， `参考`， `的电子邮件`                                                                                   |
| 简易IEP                | `cas.saml-sp.easy-iep`         | `员工ID`                                                                                                                                          |
| 无限校园                 | `cas.saml-sp。无限校园`             | `员工ID`                                                                                                                                          |
| 松弛                   | `cas.saml-sp.slack`            | `User.Email`， `User.Username`， `first_name的`， `姓氏`， `EMPLOYEEID`                                                                                |
| Zendesk              | `cas.saml-sp.zendesk`          | `组织` `标签` `电话`， `角色`， `电子邮件`                                                                                                                    |
| 加特纳                  | `cas.saml-sp.gartner`          | `瓮：OID：2.5.4.42`， `瓮：OID：2.5.4.4`， `瓮：OID：0.9.2342.19200300.100.1.3`                                                                            |
| 弧形地理信息系统             | `cas.saml-sp.arcGIS`           | `arcNameId`， `邮件`， `给定名称`                                                                                                                       |
| 利益焦点                 | `cas.saml-sp。受益重点`             | `BenefitFocusUniqueId`                                                                                                                          |
| Office365            | `cas.saml-sp.office365`        | `IDPEmail`， `ImmutableID`                                                                                                                       |
| 安全管理                 | `cas.saml-sp.sa-manage`        | `邮件`                                                                                                                                            |
| 销售队伍                 | `cas.saml-sp.salesforce`       | `eduPersonPrincipalName`                                                                                                                        |
| 工作日                  | `cas.saml-sp。工作日`              | 不适用                                                                                                                                             |
| 学术作品                 | `cas.saml-sp.academic-works`   | `显示名称`                                                                                                                                          |
| 飞涨                   | `cas.saml-sp.zoom`             | `邮件`， `SN`， `给定名称`                                                                                                                              |
| 印象笔记                 | `cas.saml-sp.evernote`         | `电子邮件`                                                                                                                                          |
| 画面                   | `cas.saml-sp.tableau`          | `用户名`                                                                                                                                           |
| 朝体                   | `cas.saml-sp.asana`            | `电子邮件`                                                                                                                                          |
| 盒子                   | `cas.saml-sp.box`              | `电子邮件`， `的firstName`， `的lastName`                                                                                                               |
| 立即服务                 | `cas.saml-sp.service-now`      | `eduPersonPrincipalName`                                                                                                                        |
| 净合作伙伴                | `cas.saml-sp.net-partner`      | `学生卡`                                                                                                                                           |
| Webex                | `cas.saml-sp.webex`            | `的firstName`， `的lastName`                                                                                                                       |
| 不常见                  | `cas.saml-sp.in-common`        | `eduPersonPrincipalName`                                                                                                                        |
| 亚马逊                  | `cas.saml-sp.amazon`           | `awsRoles`， `awsRoleSessionName`                                                                                                                |
| 一致的解决方案              | `cas.saml-sp.concur-solutions` | `电子邮件`                                                                                                                                          |
| 到处投票                 | `cas.saml-sp.poll-无处不在`        | `电子邮件`                                                                                                                                          |
| DocuSign             | `cas.saml-sp.docuSign`         | `电子邮件`， `给定名称`， `姓`， `employeeNumber`                                                                                                           |
| SafariOnline         | `cas.saml-sp.safari-在线`        | `电子邮件`， `给定名称`， `姓`， `employeeNumber`，`eduPersonAffiliation`                                                                                    |
| 黑波特                  | `cas.saml-sp.black-baud`       | `电子邮件`， `eduPersonPrincipalName`                                                                                                                |
| 给予校园                 | `cas.saml-sp.give-campus`      | `电子邮件`， `给定名称`， `姓`， `的displayName`                                                                                                             |
| 经线                   | `cas.saml-sp。经线`               | `电子邮件`， `给定名称`， `eduPersonPrincipalName`， `姓`， `eduPersonScopedAffiliation`， `employeeNumber`                                                   |
| 火箭聊天                 | `cas.saml-sp。火箭聊天`             | `电子邮件`， `CN`， `的用户名`                                                                                                                            |
| Arms软件               | `cas.saml-sp.arms软件`           | `电子邮件`， `UID`， `eduPersonPrincipalName`                                                                                                         |
| 顶帽                   | `cas.saml-sp.top-hat`          | `电子邮件`， `eduPersonPrincipalName`                                                                                                                |
| 学术健康计划               | `cas.saml-sp。学术健康计划`           | `电子邮件`， `给定名称`， `姓`， `studentId`                                                                                                                |
| 合流                   | `cas.saml-sp。汇合`               | `电子邮件`， `给定名称`， `姓`， `的uid`， `的displayName`                                                                                                     |
| 吉拉                   | `cas.saml-sp.jira`             | `电子邮件`， `给定名称`， `姓`， `的uid`， `的displayName`                                                                                                     |
| 崩溃计划                 | `cas.saml-sp。崩溃计划`             | `电子邮件`， `给定名称`， `姓`                                                                                                                             |
| 艾玛                   | `cas.saml-sp.emma`             | `电子邮件`， `给定名称`， `姓`                                                                                                                             |
| 定性                   | `cas.saml-sp.qualtrics`        | `电子邮件`， `给定名称`， `姓`， `employeeNumber`， `eduPersonPrincipalName`                                                                                 |
| NeoGov               | `cas.saml-sp.neoGov`           | `电子邮件`， `ImmutableID`                                                                                                                           |
| 津布拉                  | `cas.saml-sp.zimbra`           | `电子邮件`                                                                                                                                          |
| PagerDuty            | `cas.saml-sp.pager-duty`       | `电子邮件`                                                                                                                                          |
| 颅骨咖啡馆                | `cas.saml-sp.cranium-cafe`     | `电子邮件`， `eduPersonPrincipalName`， `的displayName`， `eduPersonScopedAffiliation`， `studentId`                                                     |
| CCC中央                | `cas.saml-sp.cccco`            | `电子邮件`， `eduPersonPrincipalName`， `的displayName`， `eduPersonScopedAffiliation`， `的uid`， `给定名称`， `COMMONNAME`， `姓`， `eduPersonPrimaryffiliation` |


**注**：对于InCommon和其他元数据聚合，可以将多个实体ID指定为 过滤 [InCommon元数据](https://spaces.internet2.edu/display/InCFederation/Metadata+Aggregates)。 EntityIds 可以是正则表达式模式并且被映射到 CAS” `服务Id` 在注册表字段。 签名位置必须是用于签名元数据的公共密钥。



## OpenID连接

允许CAS成为OpenID Connect提供程序（OP）。 要了解更多有关这个话题， [请参阅本指南](../installation/OIDC-Authentication.html)。



```properties
＃cas.authn.oidc.issuer = http：// localhost：8080 / cas / oidc
＃cas.authn.oidc.skew = 5

＃cas.authn.oidc.dynamic-client-registration-mode =打开|受保护

＃cas.authn.oidc.subject-types =公共，成对
＃cas.authn.oidc.scopes = openid，配置文件，电子邮件，地址，电话，离线访问
＃cas.authn.oidc.claims = sub，名称， preferred_username，family_name，\
＃给定名称，中间名，给定名称，配置文件，\
＃图片，昵称，网站，区域信息，区域设置，updated_at，生日，\
＃电子邮件，电子邮件验证，电话号码，电话号码已验证

oidc.response-types-supported =代码，令牌，id_token令牌
＃cas.authn.oidc.introspection-supported-authentication-methods = client_secret_basic
＃cas.authn.oidc.claim-types-supported = normal
＃cas。 authn.oidc.grant类型支持= authorization_code，密码，client_credentials，refresh_token
＃cas.authn.oidc.token端点-AUTH-方法支持= client_secret_basic，client_secret_post，private_key_jwt，client_secret_jwt
＃cas.authn.oidc。挑战代码e-methods-supported = plain，S256

＃cas.authn.oidc.id-token-signing-alg-values-supported = none，RS256，RS384，RS512，PS256，PS384，PS512，ES256，ES384，ES512，HS256 ，HS384，HS512
＃cas.authn.oidc.id-token-encryption-alg-values-supported = RSA1_5，RSA-OAEP，RSA-OAEP-256，A128KW，A192KW，A256KW，\
    A128GCMKW，A192GCMKW，A256GCMKW， ECDH-ES，ECDH-ES + A128KW，ECDH-ES + A192KW，ECDH-ES + A256KW
＃cas.authn.oidc.id-token-encryption-encoding-values-supported = A128CBC-HS256，A192CBC-HS384，A256CBC -HS512，A128GCM，A192GCM，A256GCM

＃cas.authn.oidc.user-info-signing-alg-values-supported = none，RS256，RS384，RS512，PS256，PS384，PS512，ES256，ES384，ES512，HS256， HS384，HS512
＃cas.authn.oidc.user-info-encryption-alg-values-supported = RSA1_5，RSA-OAEP，RSA-OAEP-256，A128KW，A192KW，A256KW，\
    A128GCMKW，A192GCMKW，A256GCMKW，ECDH -ES，ECDH-ES + A128KW，ECDH-ES + A192KW，ECDH-ES + A256KW
＃cas.authn.oidc.user-info-encryption-encoding-values-supported = A128CBC-HS256，A192CBC-HS384，A256CBC- HS512，A128GCM，A192GCM，A256GCM
```




### OpenID Connect JWKS



```properties
＃cas.authn.oidc.jwks.jwks-in-minutes = 60
＃cas.authn.oidc.jwks.jwks-key-size = 2048
＃cas.authn.oidc.jwks.jwks-type = RSA | EC
```




#### 基于文件的JWKS

将OpenID Connect的JSON Web密钥集作为静态文件资源进行管理。



```properties 
＃cas.authn.oidc.jwks.jwks-file = file：/etc/cas/config/keystore.jwks
```




#### 基于REST的JWKS

接触到外部REST API，以获取JSON Web密钥集。 预期的响应代码为 `200` ，其中响应主体应包含JSON Web密钥集的内容。

此功能的RESTful设置在配置项 `cas.authn.oidc.jwks.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



### OpenID Connect范围 & 声明



```properties
＃定义自定义范围和声明
＃cas.authn.oidc.user-defined-scopes.scope1 = cn，givenName，照片，customAttribute
＃cas.authn.oidc.user-defined-scopes.scope2 = cn，givenName，照片，customAttribute2

＃将固定的声明映射到CAS属性
＃cas.authn.oidc.claims-map.given_name =自定义名称
＃cas.authn.oidc.claims-map.preferred_username = global-user-attribute
```




### OpenID Connect WebFinger

WebFinger是Internet工程任务组IETF指定的协议，该协议允许 发现有关URI标识的人和事物的信息。[1] 有关某人 可能会通过“ acct：” URI（例如，看起来像电子邮件地址的URI）发现。



#### WebFinger UserInfo通过Groovy



```properties
＃cas.authn.oidc.webfinger.userInfo.groovy.location = classpath：/webfinger.groovy
```




#### 通过REST的WebFinger UserInfo

此功能的RESTful设置在配置键 `cas.authn.oidc.webfinger.user-info.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



### OpenID Connect注销

可以通过以下属性定义支持的注销通道：



```properties
＃cas.authn.oidc.logout.backchannel-logout-supported = true
＃cas.authn.oidc.logout.frontchannel-logout-supported = true
```




## Pac4j委托AuthN

充当代理，并将身份验证委托给外部身份提供者。 要了解更多有关这个话题， [请参阅本指南](../integration/Delegate-Authentication.html)。



```properties
＃cas.authn.pac4j.typed-id-used = false
＃cas.authn.pac4j.principal-attribute-id =
＃cas.authn.pac4j.name =
＃cas.authn.pac4j.order =
＃ cas.authn.pac4j.lazy-init = true
＃cas.authn.pac4j.replicate-sessions = true
```




### 基于REST的配置

可以使用外部REST端点将用于委派身份验证的身份提供程序提供给CAS。 

此功能的RESTful设置为 可用 [此处](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.authn.pac4j.rest`。



### 默认配置

下列外部身份提供者在下面列出的配置项下 [公共设置](Configuration-Properties-Common.html#delegated-authentication-settings) 

| 身份提供者        | 配置键                             |
| ------------ | ------------------------------- |
| 推特           | `cas.authn.pac4j.twitter`       |
| 贝宝           | `cas.authn.pac4j.paypal`        |
| WordPress的   | `cas.authn.pac4j.wordpress`     |
| 雅虎           | `cas.authn.pac4j.yahoo`         |
| 兽人           | `cas.authn.pac4j.orcid`         |
| 投寄箱          | `cas.authn.pac4j.dropbox`       |
| 的GitHub      | `cas.authn.pac4j.github`        |
| 四方           | `cas.authn.pac4j.foursquare`    |
| WindowsLive的 | `cas.authn.pac4j.windows-live`  |
| 谷歌           | `cas.authn.pac4j.google`        |
| HiOrg服务器     | `cas.authn.pac4j.hi-org-server` |


有关其他身份提供者（例如CAS，SAML2等），请参见下文。



### 调配

向身份存储供应并创建已建立的用户配置文件。



#### Groovy



```properties
＃cas.authn.pac4j.provisioning.groovy.location =文件：/etc/cas/config/Provisioner.groovy
```




#### 休息

此功能的RESTful设置在配置项 `cas.authn.pac4j.provisioning.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



### 的GitHub

将身份验证委派给GitHub时， [公共设置块](Configuration-Properties-Common.html#delegated-authentication-settings) 



```properties
＃cas.authn.pac4j.github.scope = user | read：user | user：email | ...
```


默认范围是 `用户`，即 `对GitHub用户帐户的读/写`

有关可能的范围的完整列表，请 [参见此链接](https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/)。 



### 谷歌

将身份验证委托给Google时， [公共设置块](Configuration-Properties-Common.html#delegated-authentication-settings)



```properties
＃cas.authn.pac4j.google.scope = EMAIL | PROFILE | EMAIL_AND_PROFILE
```




### 中国科学院

将身份验证委托给外部CAS服务器。



```properties
＃cas.authn.pac4j.cas[0].login-url =
＃cas.authn.pac4j.cas[0].protocol =
```




### OAuth20

将身份验证委托给通用OAuth2服务器。 这种常见的设置 身份提供者可用 [这里](Configuration-Properties-Common.html#delegated-authentication-settings) 所述配置密钥下 `cas.authn.pac4j.oauth2[0]`。



```properties
＃cas.authn.pac4j.oauth2[0].auth-url =
＃cas.authn.pac4j.oauth2[0].token-url =
＃cas.authn.pac4j.oauth2[0].profile-url =
＃cas.authn。 pac4j.oauth2[0].profile-path =
＃cas.authn.pac4j.oauth2[0].scope =
＃cas.authn.pac4j.oauth2[0].profile-verb = GET | POST
＃cas.authn.pac4j.oauth2[0].response-type = code
＃cas.authn.pac4j.oauth2[0].profile-attrs.attr1 =配置文件路径
＃cas.authn.pac4j.oauth2[0].custom-params.param1 = value1
```




### OpenID连接

将身份验证委托给外部OpenID Connect服务器。

在配置密钥 `cas.authn.pac4j.oidc[0]`下，此身份提供者的通用设置为 [此处为](Configuration-Properties-Common.html#delegated-authentication-settings) 。



#### 谷歌

在配置密钥 `cas.authn.pac4j.oidc[0].google`下，此身份提供者的常规设置可用 [这里](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 可用。



#### Azure AD

在配置密钥 `cas.authn.pac4j.oidc[0].azure`下，此身份提供者的通用设置为 [此处为](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 。

以下设置专门适用于此提供程序：



```properties
＃cas.authn.pac4j.oidc[0].azure.tenant =租户名称
```




#### 按键披风

此身份提供者的常用设置为 可用 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 在配置密钥 `cas.authn.pac4j.oidc[0].keycloak`。



```properties
＃cas.authn.pac4j.oidc[0].keycloak.realm =
＃cas.authn.pac4j.oidc[0].keycloak.base-uri =
```




#### 苹果登录

此身份提供者的常用设置为 可用 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 在配置键 `cas.authn.pac4j.oidc[0]`。



```properties
＃cas.authn.pac4j.oidc[0].apple.private-key =
＃cas.authn.pac4j.oidc[0].apple.private-key-id =
＃cas.authn.pac4j.oidc[0].apple.team- id =
＃cas.authn.pac4j.oidc[0].apple.timeout = PT30S
```




#### 通用的

在配置密钥 `cas.authn.pac4j.oidc[0].generic`下，此身份提供者的通用设置为 [此处为](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 。



### SAML2

将身份验证委派给外部SAML2 IdP。



```properties
# cas.authn.pac4j.saml[0].keystore-password=
# cas.authn.pac4j.saml[0].private-key-password=
# cas.authn.pac4j.saml[0].keystore-path=
# cas.authn.pac4j.saml[0].keystore-alias=

# cas.authn.pac4j.saml[0].service-provider-entity-id=
# cas.authn.pac4j.saml[0].service-provider-metadata-path=

# cas.authn.pac4j.saml[0].certificate-name-to-append=

# cas.authn.pac4j.saml[0].maximum-authentication-lifetime=3600
# cas.authn.pac4j.saml[0].maximum-authentication-lifetime=300
# cas.authn.pac4j.saml[0].destination-binding=urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect

# cas.authn.pac4j.saml[0].identity-provider-metadata-path=

# cas.authn.pac4j.saml[0].authn-context-class-ref[0]=
# cas.authn.pac4j.saml[0].authn-context-comparison-type=
# cas.authn.pac4j.saml[0].name-id-policy-format=
# cas.authn.pac4j.saml[0].force-auth=false
# cas.authn.pac4j.saml[0].passive=false

# cas.authn.pac4j.saml[0].wants-assertions-signed=
# cas.authn.pac4j.saml[0].wants-responses-signed=
# cas.authn.pac4j.saml[0].all-signature-validation-disabled=false
# cas.authn.pac4j.saml[0].sign-service-provider-metadata=false
# cas.authn.pac4j.saml[0].principal-id-attribute=eduPersonPrincipalName
# cas.authn.pac4j.saml[0].use-name-qualifier=true
# cas.authn.pac4j.saml[0].attribute-consuming-service-index=
# cas.authn.pac4j.saml[0].assertion-consumer-service-index=-1
# cas.authn.pac4j.saml[0].provider-name=
# cas.authn.pac4j.saml[0].name-id-policy-allow-create=TRUE|FALSE|UNDEFINED


# cas.authn.pac4j.saml[0].sign-authn-request=false
# cas.authn.pac4j.saml[0].sign-service-provider-logout-request=false
# cas.authn.pac4j.saml[0].black-listed-signature-signing-algorithms[0]=
# cas.authn.pac4j.saml[0].signature-algorithms[0]=
# cas.authn.pac4j.saml[0].signature-reference-digest-methods[0]=
# cas.authn.pac4j.saml[0].signature-canonicalization-algorithm=

# cas.authn.pac4j.saml[0].requested-attributes[0].name=
# cas.authn.pac4j.saml[0].requested-attributes[0].friendly-name=
# cas.authn.pac4j.saml[0].requested-attributes[0].name-format=urn:oasis:names:tc:SAML:2.0:attrname-format:uri
# cas.authn.pac4j.saml[0].requested-attributes[0].required=false

# cas.authn.pac4j.saml[0].mapped-attributes[0].name=urn:oid:2.5.4.42
# cas.authn.pac4j.saml[0].mapped-attributes[0].mapped-as=displayName

# cas.authn.pac4j.saml[0].message-store-factory=org.pac4j.saml.store.EmptyStoreFactory
```


访问CAS登录屏幕后，检查生成的元数据，以确保 端口和端点。 最后，与委派的IdP共享CAS SP元数据，并将CAS注册为授权的依赖方。



#### SAML2身份提供者发现



```properties
cas.authn.pac4j.saml-discovery.resource[0].location = file：/etc/cas/config/json-feed.json
```




### Facebook

将身份验证委托给Facebook。 此身份提供者的通用设置在配置密钥 `cas.authn.pac4j.facebook`[这里](Configuration-Properties-Common.html#delegated-authentication-settings) 是可用的。



```properties
＃cas.authn.pac4j.facebook.fields =
＃cas.authn.pac4j.facebook.scope =
```




### HiOrg服务器

将身份验证委托给HiOrg Server。 在配置密钥 `cas.authn.pac4j.hi-org-server`[这里](Configuration-Properties-Common.html#delegated-authentication-settings) ，可以使用此身份提供程序的通用设置。



```properties
＃cas.authn.pac4j.hi-org-server.scope = eigenedaten
```




### 领英

将身份验证委托给LinkedIn。 此身份提供者的通用设置在配置键 `cas.authn.pac4j.linkedin`[这里](Configuration-Properties-Common.html#delegated-authentication-settings) 是可用的。



```properties
＃cas.authn.pac4j.linked-in.scope =
```




### 推特

将身份验证委托给Twitter。  在配置密钥 `cas.authn.pac4j.twitter`[这里](Configuration-Properties-Common.html#delegated-authentication-settings) ，可以使用此身份提供程序的通用设置。



```properties
＃cas.authn.pac4j.twitter.include-email = false
```




## WS联合会

允许CAS充当身份提供者和安全令牌服务 以支持WS-Federation协议。

要了解有关此主题的更多信息，请参阅本指南 [](../protocol/WS-Federation-Protocol.html)



```properties
＃cas.authn.wsfed-idp.idp.realm = urn：org：apereo：cas：ws：idp：realm-CAS
＃cas.authn.wsfed-idp.idp.realm-name = CAS

＃cas.authn .wsfed-idp.sts.signing-keystore-file = / etc / cas / config / ststrust.jks
＃cas.authn.wsfed-idp.sts.signing-keystore-password = storepass
＃cas.authn.wsfed- idp.sts.encryption-keystore-file = / etc / cas / config / stsencrypt.jks
＃cas.authn.wsfed-idp.sts.encryption-keystore-password = storepass

＃cas.authn.wsfed-idp.sts .subject-name-id-format =未指定
＃cas.authn.wsfed-idp.sts.subject-name-qualifier = http：//cxf.apache.org/sts
＃cas.authn.wsfed-idp.sts .encrypt-tokens = true
＃cas.authn.wsfed-idp.sts.sign-tokens = true

＃cas.authn.wsfed-idp.sts.conditions-accept-client-lifetime = true
＃cas.authn。 wsfed-idp.sts.conditions-fail-lifetime-
＃cas.authn.wsfed-idp.sts.conditions-未来生存时间= PT60S
＃cas.authn.wsfed-idp.sts。 conditions-lifetime = PT30M
＃cas.authn.wsfed-idp.sts.conditions-max-lifetime = PT12H

＃cas.authn.wsfed-idp.sts.realm.keystore- file = / etc / cas / config / stscasrealm.jks
＃cas.authn.wsfed-idp.sts.realm.keystore-password = storepass
＃cas.authn.wsfed-idp.sts.realm.keystore-alias = realmcas
＃cas.authn.wsfed-idp.sts.realm.key-password = cas
＃cas.authn.wsfed-idp.sts.realm.issuer = CAS
```




### 签名 & 加密

签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。  这些用于确保IdP和STS之间的身份验证请求的安全。 签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.authn.wsfed-idp.sts`。



## OAuth2

允许CAS充当OAuth2提供者。 在这里，您可以控制CAS发行的各种令牌应持续多长时间，依此类推。

在配置密钥 `cas.authn.oauth`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以使用此功能的签名 & 加密设置。

要了解更多有关这个话题， [请参阅本指南](../installation/OAuth-OpenId-Authentication.html)。



```properties
＃cas.authn.oauth.replicate-sessions = false 
＃cas.authn.oauth.grants.resource-owner.require-service-header = true
＃cas.authn.oauth.user-profile-view-type = NESTED |平
```




### 刷新令牌



```properties
＃cas.authn.oauth.refresh-token.time-to-kill-in-seconds = 2592000
```




### 代号



```properties
＃cas.authn.oauth.code。秒杀时间= 30
＃cas.authn.oauth.code。使用次数= 1
```




### 访问令牌



```properties
＃cas.authn.oauth.access-token.time-to-kill-seconds = 7200
＃cas.authn.oauth.access-token.max-time-to-live-seconds = 28800
```




### 设备令牌



```
＃cas.authn.oauth.device-token.time-to-kill-in-seconds = 2592000
＃cas.authn.oauth.device-token.refresh-interval = PT15S 
```




### 设备用户代码



```
＃cas.authn.oauth.device-user-code.time-to-kill-in-seconds = 2592000
＃cas.authn.oauth.device-user-code.user-code-length = 8
```




### OAuth2 JWT访问令牌



```properties
＃cas.authn.oauth.access-token.create-as-jwt = false
＃cas.authn.oauth.access-token.crypto.encryption-enabled = true
＃cas.authn.oauth.access-token.crypto .signing-enabled = true
```


签名密钥和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 在配置密钥 `cas.authn.oauth.access-token`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以使用此功能的签名 & 加密设置。



### OAuth2 UMA

要了解更多有关这个话题， [请参阅本指南](../installation/OAuth-OpenId-Authentication.html)。



```properties
＃cas.authn.uma.issuer = http：// localhost：8080 / cas

＃cas.authn.uma.requesting-party-token.max-to-live-in-seconds = PT3M
＃cas.authn .uma.requesting-party-token.jwks-file = file：/etc/cas/config/uma-keystore.jwks

＃cas.authn.uma.permission-ticket.max-time-to-seconds = PT3M
```




#### OAuth2 UMA JPA

此功能的数据库设置在配置键 `cas.authn.uma.resource-set.jpa`[此处](Configuration-Properties-Common.html#database-settings) 可用。



## 本土化

要了解更多有关这个话题， [请参阅本指南](../ux/User-Interface-Customization-Localization.html)。



```properties
＃cas.locale.param-name =语言环境
＃cas.locale.default-value = zh-CN
```


如果用户更改语言，则CAS将创建一个特殊的cookie，以包含所选的语言。 此功能的Cookie [此处](Configuration-Properties-Common.html#cookie-properties) 在配置键 `cas.locale.cookie`。



## 全球SSO行为

要了解更多有关这个话题， [请参阅本指南](../installation/Configuring-SSO.html)。



```properties
＃cas.sso.allow-missing-service-parameter = true
＃cas.sso.create-sso-cookie-on-
＃cas.sso.proxy-authn-enabled = true
＃cas。 sso.renew-authn-enabled = true
＃cas.sso.sso-enabled = true
＃cas.sso.required-service-pattern =
```




## 警告Cookie

当访问CAS受保护的服务时要警告用户时，由CAS创建。 此功能的Cookie设置在配置键 `cas.warning-cookie`[此处为](Configuration-Properties-Common.html#cookie-properties) 可用。



```properties
＃cas.warningCookie.auto-configure-cookie-path = true
```




## 票务授予Cookie

此功能的Cookie设置在配置项 `cas.tgc`[这里](Configuration-Properties-Common.html#cookie-properties) 是可用的。



```properties
＃cas.tgc.pin-to-session = true
＃cas.tgc.remember-me-max-age = P14D
＃cas.tgc.auto-configure-cookie-path = true
```




### 签名 & 加密

签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.tgc`。



## 登出

控制与CAS注销功能相关的各种设置。 要了解更多有关这个话题， [请参阅本指南](../installation/Logout-Single-Signout.html)。



```properties
＃cas.logout.follow-service-redirects = false
＃cas.logout.redirect-parameter = service
＃cas.logout.redirect-url = https：//www.github.com
＃cas.logout.confirm- logout = false
＃cas.logout.remove-descendant-tickets = false
```




## 单次登出

要了解更多有关这个话题， [请参阅本指南](../installation/Logout-Single-Signout.html)。



```properties
＃cas.slo.disabled = false
＃cas.slo.asynchronous = true
```




## 清除通行证

捕获并缓存用户凭证，并有选择地将其释放到受信任的应用程序。 要了解更多有关这个话题， [请参阅本指南](../integration/ClearPass.html)。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认情况下，ClearPass是关闭的。
想想 <strong>非常仔细地</strong> 开启此功能之前，因为这 <strong>MUST</strong> 是
中得到一个集成的工作不得已...也许甚至没有然后。</p></div>

```properties
＃cas.clearpass.cache-credential = false
```


签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.clearpass`。



## 讯息包

要了解更多有关这个话题， [请参阅本指南](../ux/User-Interface-Customization-Localization.html)。 baseName是消息捆绑包的基本名称，表示以.properties或_xx.properties结尾的文件，其中xx是国家/地区区域代码。 commonNames实际上不是消息束，但它们是合并在一起的属性文件，并且包含仅当在消息束中找不到它们时才使用的键。 列表中较晚文件中的密钥将优先于较早文件中的密钥。



```properties
＃cas.message-bundle.encoding = UTF-8
＃cas.message-bundle.fallback-system-locale = false
＃cas.message-bundle.cache-seconds = 180
＃cas.message-bundle.use- code-message = true
＃cas.message-bundle.base-names = classpath：custom_messages，classpath：messages
＃cas.message-bundle.common-names = classpath：/common_messages.properties，文件：/ etc / cas / config / common_messages.properties
```




## 稽核

控制审核消息的格式。 要了解更多有关这个话题， [请参阅本指南](../installation/Audits.html)。



```properties 
＃cas.audit.enabled = true
＃cas.audit.ignore-audit-failures = false
＃cas.audit.app-code = CAS
＃cas.audit.number-of-days-in-history = 30
＃cas.audit.include-validation-assertion = false
＃cas.audit.alternate-server-addr-header-name =
＃cas.audit.alternate-client-addr-header-name = X-Forwarded-For
＃cas.audit.use-server-host-address = false  

＃cas.audit.supported-actions = *
＃cas.audit.excluded-actions =
```




### Slf4j审核

将审核日志路由到Slf4j日志记录系统，后者又可以将审核日志存储在文件或日志记录系统支持的 

记录器名称固定为 `org.apereo.inspektr.audit.support`。



```xml
<Logger name="org.apereo.inspektr.audit.support" level="info">
    <！-将审核数据路由到日志框架支持的任意数量的附加程序。 >
</Logger>
```

<div class="alert alert-info"><strong></strong><p>给定CAS，日志系统
和可能将数据推送到各种系统的任意数量的日志附加程序之间的抽象层，路由到Slf4j日志的审核记录不能为
</p></div>

```properties
＃cas.audit.slf4j.audit-format = DEFAULT | JSON
＃cas.audit.slf4j.singleline-separator = |
＃cas.audit.slf4j.use-单行= false
＃cas.audit.slf4j.enabled = true
```




### MongoDb审核

将审核日志存储在MongoDb数据库中。

此功能的常规配置设置在配置键 `cas.audit` [这里](Configuration-Properties-Common.html#mongodb-configuration)。



```properties
＃cas.audit.mongo.asynchronous = true
```




### Redis审核

将审核日志存储在Redis数据库中。

此功能的常规配置设置在配置键 `cas.audit` [这里](Configuration-Properties-Common.html#redis-configuration)。



```properties
＃cas.audit.redis.asynchronous = true
```




### CouchDb审核

将审核日志存储在CouchDb数据库中。

此功能的常规配置设置在配置键 `cas.audit`[这里](Configuration-Properties-Common.html#couchdb-configuration) 是可用的。



### Couchbase审核

将审核日志存储在Couchbase数据库中。

此功能的数据库设置在配置键 `cas.audit.couchbase`[这里](Configuration-Properties-Common.html#couchbase-integration-settings) 是可用的。



```properties
＃cas.audit.couchbase.asynchronous = true
```




### DynamoDb审核

将审核日志存储在DynamoDb数据库中。

此功能的常规配置设置在配置键 `cas.audit`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 是可用的。

此功能的AWS设置在配置键 `cas.audit.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



```properties
＃cas.audit.dynamo-db.asynchronous = true
```




### 数据库审核

将审核日志存储在数据库中。 此功能的数据库设置在配置键 `cas.audit.jdbc`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



```properties
＃cas.audit.jdbc.asynchronous = true
＃cas.audit.jdbc.max-age-days = 180
＃cas.audit.jdbc.column-length = 100
＃cas.audit.jdbc.select-sql- query-template =
＃cas.audit.jdbc.date-formatter-pattern =
```


此功能的计划程序设置在配置键 `cas.audit.jdbc`[这里](Configuration-Properties-Common.html#job-scheduling) 是可用的。



### REST审核

将审核日志存储在数据库中。 此功能的RESTful设置为 可用 [此处](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.audit.rest`。



```properties
＃cas.audit.rest.asynchronous = true
```




## 侦查分布式跟踪

要了解更多有关这个话题， [请参阅本指南](../monitoring/Monitoring-Statistics.html#distributed-tracing)。



```properties
＃spring.sleuth.sampler.percentage = 0.5
＃spring.sleuth.enabled = true

＃spring.zipkin.enabled = true
＃spring.zipkin.base-url = http：// localhost：9411 /
```




## 监控方式

要了解更多有关这个话题， [请参阅本指南](../monitoring/Monitoring-Statistics.html)。



### 门票授予门票

确定CAS应如何监控TGT的生成。



```properties
＃cas.monitor.tgt.warn.threshold = 10
＃cas.monitor.tgt.warn.eviction-threshold = 0
```




### 服务票

确定CAS应如何监视ST的生成。



```properties
＃cas.monitor.st.warn.threshold = 10
＃cas.monitor.st.warn.evictionThreshold = 0
```



### 加载

确定CAS应如何监视CAS服务器的系统负载。  



```properties
＃cas.monitor.load.warn.threshold = 25
```




### 缓存监视器

确定CAS应如何监视各种缓存存储服务的内部状态。



```properties
＃cas.monitor.warn.threshold = 10
＃cas.monitor.warn.eviction-threshold = 0
```




### Memcached监视器

确定CAS应如何监视内存缓存连接池的内部状态。 此注册表的集成设置在配置项 `cas.monitor.memcached`[这里](Configuration-Properties-Common.html#memcached-integration-settings) 是可用的。



### MongoDb显示器

确定CAS应如何监视MongoDb实例的内部状态。  
此功能的通用配置设置可用 [在此处](Configuration-Properties-Common.html#mongodb-configuration) 在配置键 `cas.monitor`。



### 数据库监控

确定CAS应如何监视用于身份验证或属性检索的JDBC连接的内部状态（使用 此功能的数据库设置为 可用 [此处](Configuration-Properties-Common.html#database-settings) 在配置键 `cas.monitor.jdbc`。



```properties
＃cas.monitor.jdbc.validation-query =选择1
＃cas.monitor.jdbc.max-wait = 5000
```




### LDAP服务器监控

决定CAS如何监视用于身份验证等的LDAP服务器  
此功能的LDAP设置为 可用 [在这里](Configuration-Properties-Common.html#ldap-connection-settings) 在配置键 `cas.monitor.ldap[0]`。 池大小的默认值为零，以防止ldap池初始化失败而影响服务器启动。

以下属性特定于ldap监视器，并配置将在LDAP监视器连接池上ping 



```properties
＃cas.monitor.ldap[0].max-wait = 5000
＃cas.monitor.ldap[0].pool.min-size = 0
＃cas.monitor.ldap[0].pool.max-size = 18
＃cas.monitor .ldap[0].pool.enabled = true
```




### 记忆

确定CAS应如何监视运行时可用的JVM内存的内部状态。



```properties
＃cas.monitor.free-mem-threshold = 10
```




## 主题

要了解更多有关这个话题， [请参阅本指南](../ux/User-Interface-Customization-Themes.html)。



```properties
＃cas.theme.param-name =主题
＃cas.theme.default-theme-name = cas-theme-default
```




## 大事记

确定CAS应如何跟踪身份验证事件。 要了解更多有关这个话题， [请参阅本指南](../installation/Configuring-Authentication-Events.html)。



```properties
cas.events.enabled = true

＃是否应打开并从浏览器请求地理位置跟踪。
＃cas.events.track-geolocation = false

＃控制CAS是否应监视配置文件和自动刷新上下文。
＃cas.events.track-configuration-modifications = true
```




### InfluxDb活动

确定CAS如何在InfluxDb实例中存储身份验证事件。 此功能的通用 配置设置在配置键 `cas.events.influx-db`[这里](Configuration-Properties-Common.html#influxdb-configuration)。



### CouchDb活动

确定CAS应如何在CouchDb实例中存储身份验证事件。 此功能的通用 [在这里](Configuration-Properties-Common.html#couchdb-configuration) 在配置键 `cas.events.couch-db`。



### 数据库事件

确定CAS应如何在数据库实例内部存储身份验证事件。 数据库 设置此功能可用 [这里](Configuration-Properties-Common.html#database-settings) 所述配置密钥下 `cas.events.jpa`。



### MongoDb活动

确定CAS如何在MongoDb实例中存储身份验证事件。 此功能的通用 [在这里](Configuration-Properties-Common.html#mongodb-configuration) 在配置键 `cas.events`。



### DynamoDb活动

确定CAS如何在DynamoDb实例中存储身份验证事件。

此功能的通用配置设置在配置键 `cas.events`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 提供。

此功能的AWS设置在配置键 `cas.evnts.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



```properties
＃cas.events.dynamo-db.table-name = DynamoDbCasEvents
```




## Http Web请求

控制CAS如何响应和验证传入的HTTP请求。



```properties
＃cas.http-web-request.header.enabled = true

＃cas.http-web-request.header.xframe = true
＃cas.http-web-request.header.xframe-options = DENY

＃cas。 http-web-request.header.xss = true
＃cas.http-web-request.header.xss-options = 1;模式=块

＃cas.http-web-request.header.hsts = true
＃cas.http-web-request.header.xcontent = true
＃cas.http-web-request.header.cache = true
＃ cas.http-web-request.header.content-security-policy =

＃cas.http-web-request.cors.enabled = false
＃cas.http-web-request.cors.allow-credentials = false
＃ cas.http-web-request.cors.allow-origins[0]=
＃cas.http-web-request.cors.allow-methods[0]= *
＃cas.http-web-request.cors.allow-headers[0]= *
＃cas.http-web-request.cors.max-age = 3600
＃cas.http-web-request.cors.exposed-headers[0]=

＃cas.http-web-request.web.force-encoding = true
＃cas.http-web-request.web.encoding = UTF-8

＃cas.http-web-request.allow-multi-value-parameters = false
＃cas.http-web-request.only- post-params =用户名，密码
＃cas.http-web-request.params-to-check =票，服务，续订，网关，警告，方法，目标，SAMLart，pgtUrl，pgt，pgtId，pgtIou，targetService，entityId ，令牌
＃cas.http-web-request.pattern-to-block =
＃cas.http-web-request.characters-to-for bid = none

＃cas.http-web-request.custom-headers.header-name1 = headerValue1
＃cas.http-web-request.custom-headers.header-name2 = headerValue2

＃server.servlet.encoding.charset = UTF-8
＃server.servlet.encoding.enabled = true
＃server.servlet.encoding.force = true
```




## Http客户端

控制CAS如何尝试通过其自己的Http客户端 当对票证验证事件和/或单次注销 时，最常使用此方法。

如果要将本地证书导入到CAS运行环境中， 本地信任库，以提高跨环境配置的可移植性。



```properties
＃cas.http-client.connection-timeout = 5000
＃cas.http-client.async-timeout = 5000
＃cas.http-client.read-timeout = 5000 

＃cas.http-client.proxy-host =
＃cas.http-client.proxy-port = 0 

＃cas.http-client.host-name-verifier = NONE | DEFAULT
＃cas.http-client.allow-local-logout-urls = false
＃cas .http-client.authority-validation-reg-ex =
＃cas.http-client.authority-validation-reg-ex-case-sensitive = true
＃cas.http-client.default-headers =

＃cas。 http-client.truststore.psw =
＃cas.http-client.truststore.file = classpath：/truststore.jks
＃cas.http-client.truststore.type =
```




### 主机名验证

默认选项可用于验证主机名：

| 类型     | 描述         |
| ------ | ---------- |
| `没有任何` | 忽略主机名验证。   |
| `默认`   | 强制执行主机名验证。 |




## 服务注册

请参阅 [本指南](../services/Service-Management.html) 以了解更多信息。



```properties
＃cas.service-registry.watcher-enabled = true

＃根据默认JSON服务定义自动初始化注册表
＃cas.service-registry.init-from-json = false

＃cas.service-registry.management-type = DEFAULT | DOMAIN
＃cas.service-registry.cache = PT5M
＃cas.service-registry.cache-size = 1000
＃cas.service-registry.cache-capacity = 1000
```


此功能的计划程序设置可用 [在这里](Configuration-Properties-Common.html#job-scheduling) 在 下配置键 `cas.service-registry`。



### 服务注册中心通知

此功能的电子邮件通知设置在配置项 `cas.service-registry`[这里](Configuration-Properties-Common.html#email-notifications) 是可用的。 此功能的SMS通知设置为 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 在配置键 `cas.service-registry`。



### JSON服务注册表

如果基础服务注册表使用本地系统资源 来查找JSON服务定义，请决定应如何找到这些资源。



```properties
＃cas.service-registry.json.location = classpath：/ services
```


要了解更多有关这个话题， [请参阅本指南](../services/JSON-Service-Management.html)。



### YAML服务注册表

如果基础服务注册表使用本地系统资源 来定位YAML服务定义，请决定应如何找到这些资源。



```properties
＃cas.service-registry.yaml.location = classpath：/服务
```


要了解更多有关这个话题， [请参阅本指南](../services/YAML-Service-Management.html)。



### Git服务注册中心

与git仓库一起使用，以获取和管理服务注册表定义。

此功能的常规配置设置在配置键 `cas.service-registry`[这里](Configuration-Properties-Common.html#git-configuration) 是可用的。



```properties
＃cas.service-registry.git.group-by-type = true
＃cas.service-registry.git.root-directory =
```


要了解更多有关这个话题， [请参阅本指南](../services/Git-Service-Management.html)。



### RESTful服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/REST-Service-Management.html)。



```properties
＃cas.service-registry.rest.url = https：//example.api.org
＃cas.service-registry.rest.basic-auth-username =
＃cas.service-registry.rest.basic-auth-密码=
```




### CouchDb服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/CouchDb-Service-Management.html)。 此功能的通用配置设置在配置键 `cas.service-registry`[这里](Configuration-Properties-Common.html#couchdb-configuration) 是可用的。



### Redis服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/Redis-Service-Management.html)。 此功能的通用配置设置在配置键 `cas.service-registry`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



### CosmosDb服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/CosmosDb-Service-Management.html)。



```properties
＃cas.service-registry.cosmos-db.uri =
＃cas.service-registry.cosmos-db.key =
＃cas.service-registry.cosmos-db.database =
＃cas.service-registry.cosmos -db.collection =
＃cas.service-registry.cosmos-db.throughput = 10000
＃cas.service-registry.cosmos-db.drop-collection = true
＃cas.service-registry.cosmos-db.consistency -level =会话
```




### Amazon S3服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/AmazonS3-Service-Management.html)。

此功能的AWS设置在配置键 `cas.service-registry.amazon-s3`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



### DynamoDb服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/DynamoDb-Service-Management.html)。 此功能的常规配置设置在配置键 `cas.service-registry`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 是可用的。 此功能的AWS设置在配置键 `cas.service-registry.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



```properties
＃cas.service-registry.dynamo-db.table-name = DynamoDbCasServices
```




### Cassandra服务注册表

要了解更多有关这个话题， [请参阅本指南](../services/Cassandra-Service-Management.html)。

此功能的常规Cassandra设置在配置项 `cas.service-registry.cassandra`[这里](Configuration-Properties-Common.html#cassandra-configuration) 处可用。



### MongoDb服务注册中心

将CAS服务定义存储在MongoDb实例中。 要了解更多有关这个话题， [请参阅本指南](../services/MongoDb-Service-Management.html)。 此功能的通用配置设置在配置键 `cas.service-registry`[这里](Configuration-Properties-Common.html#mongodb-configuration) 是可用的。



### LDAP服务注册表

控制应如何在LDAP实例中找到CAS服务。 要了解更多有关这个话题， [请参阅本指南](../services/LDAP-Service-Management.html)。  此功能的LDAP设置在配置项 `cas.service-registry.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



```properties
＃cas.service-registry.ldap.service-definition-attribute = description
＃cas.service-registry.ldap.id-attribute = uid
＃cas.service-registry.ldap.object-class = casRegisteredService
＃cas。 service-registry.ldap.search-filter =（%s={0}）
＃cas.service-registry.ldap.load-filter =（objectClass =%s）
```




### Couchbase服务注册表

控制应如何在Couchbase实例中找到CAS服务。 要了解更多有关这个话题， [请参阅本指南](../services/Couchbase-Service-Management.html)。 此功能的数据库设置在配置键 `cas.service-registry.couchbase`[这里](Configuration-Properties-Common.html#couchbase-integration-settings) 是可用的。



### 数据库服务注册表

控制应如何在数据库实例内找到CAS服务。 要了解更多有关这个话题， [请参阅本指南](../services/JPA-Service-Management.html)。 此功能的数据库设置在配置键 `cas.service-registry.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



### 缓存服务注册表

服务高速缓存持续时间指定了条目的固定持续时间，该条目在创建或更新后会自动从高速缓存中删除。



### 缓存大小服务注册表

服务缓存大小指定缓存可能包含的最大条目数。



### Cach容量服务注册中心

服务缓存容量设置内部数据结构的最小总大小。



## 服务注册表复制

控制应如何在CAS群集之间复制CAS服务定义文件。 要了解有关此主题的更多信息，请参阅本指南 [](../services/Configuring-Service-Replication.html)

可以按照以下选项配置复制模式：

| 类型               | 描述                             |
| ---------------- | ------------------------------ |
| `ACTIVE_ACTIVE`  | 所有CAS节点都会同步定义的副本，并将其保留在本地。     |
| `ACTIVE_PASSIVE` | 默认。 一个主节点保留定义，并将更改流式传输到其他被动节点。 |




```properties
＃cas.service-registry.stream.enabled = true
＃cas.service-registry.stream.replication-mode = ACTIVE_ACTIVE | ACTIVE_PASSIVE
```




## 服务注册表复制Hazelcast

控制应如何在由分布式Hazelcast缓存支持的CAS群集之间复制CAS服务定义文件。 要了解更多有关这个话题， [请参阅本指南](../services/Configuring-Service-Replication.html)。

此功能的Hazelcast设置可用 [在这里](Configuration-Properties-Common.html#hazelcast-configuration) 在 下配置键 `cas.service-registry.stream.hazelcast.config`。



```properties
＃cas.service-registry.stream.hazelcast.duration = PT1M
```




## 服务注册表复制Kafka

控制应如何在由Apache Kafka支持的CAS集群之间复制CAS服务定义文件。 要了解更多有关这个话题， [请参阅本指南](../services/Configuring-Service-Replication.html)。

此功能的Kafka通用设置可用 [在这里](Configuration-Properties-Common.html#apache-kafka-configuration) 在 下配置键 `cas.service-registry.stream.kafka`。 此功能的Kafka主题设置为 可用 [此处](Configuration-Properties-Common.html#apache-kafka-configuration) 在配置键 `cas.service-registry.stream.kafka.topic`。



## 票务登记处

要了解更多有关这个话题， [请参阅本指南](../ticketing/Configuring-Ticketing-Components.html)。



### 签名 & 加密

加密密钥必须是大小为 `16`随机生成的字符串。 签名密钥 [是大小为 `512`的JWK](Configuration-Properties-Common.html#signing--encryption)。



### 清洁工

计划在后台运行一个更清洁的进程，以清理过期的和过时的票证。 本节控制该过程的行为方式。 此功能的计划程序设置为 可用 [这里](Configuration-Properties-Common.html#job-scheduling) 在配置键 `cas.ticket.registry.cleaner`。



### JPA票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/JPA-Ticket-Registry.html)。 该功能的数据库设置在配置键 `cas.ticket.registry.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



```properties
＃cas.ticket.registry.jpa.ticket-lock-type = NONE
＃cas.ticket.registry.jpa.jpa-locking-timeout = 3600
```


签约 & 此注册表加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 的配置项下 `cas.ticket.registry.jpa`。



### CouchDb票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/CouchDb-Ticket-Registry.html)。 此功能的数据库设置在配置项 `cas.ticket.registry.couch-db`[这里](Configuration-Properties-Common.html#couchdb-configuration) 是可用的。



### Couchbase票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/Couchbase-Ticket-Registry.html)。 此功能的数据库设置在配置键 `cas.ticket.registry.couchbase`[这里](Configuration-Properties-Common.html#couchbase-integration-settings) 是可用的。

签约 & 此注册表加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 的配置项下 `cas.ticket.registry.couchbase`。



### Hazelcast票务登记处

要了解更多有关这个话题， [请参阅本指南](../ticketing/Hazelcast-Ticket-Registry.html)。

此功能的常用Hazelcast设置在配置键 `cas.ticket.registry.hazelcast`[这里](Configuration-Properties-Common.html#hazelcast-configuration) 可用。



```properties
＃cas.ticket.registry.hazelcast.page-size = 500
```


此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.ticket.registry.hazelcast`。



### 卡桑德拉门票登记处

要了解更多有关这个话题， [请参阅本指南](../ticketing/Cassandra-Ticket-Registry.html)。

此功能的常规Cassandra设置在配置键 `cas.ticket.registry.cassandra`[此处](Configuration-Properties-Common.html#cassandra-configuration) 可用。

此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.ticket.registry.cassandra`。



```properties
＃cas.ticket.registry.cassandra.drop-tables-on-startup = false
```




### Infinispan票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/Infinispan-Ticket-Registry.html)。



```properties
＃cas.ticket.registry.infinispan.cache-name =
＃cas.ticket.registry.infinispan.config-location = / infinispan.xml
```


此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.ticket.registry.infinispan`。



### InMemory票务注册表

这通常是默认的票证注册表实例，其中票证 保留在运行时环境内存中。



```properties
＃使支持映射可缓存
＃cas.ticket.registry.in-memory.cache = true

＃cas.ticket.registry.in-memory.load-factor = 1
＃cas.ticket.registry.in- memory.concurrency = 20
＃cas.ticket.registry.in-memory.initial-capacity = 1000
```


此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.ticket.registry.in-memory`。



### JMS票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/Messaging-JMS-Ticket-Registry.html)。

在配置密钥 `cas.ticket.registry.jms`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以使用此注册表的Signing & 加密设置。



```properties
＃cas.ticket.registry.jms.id =
```




#### JMS票务注册表ActiveMQ



```properties
＃spring.activemq.broker-url = tcp：//192.168.1.210：9876
＃spring.activemq.user = admin
＃spring.activemq.password =秘密
＃spring.activemq.pool.enabled = true
＃spring .activemq.pool.max-connections = 50
＃spring.activemq.packages.trust-all = false
＃spring.activemq.packages.trusted = org.apereo.cas
```




#### JMS票务注册表Artemis



```properties
＃spring.artemis.mode =本机
＃spring.artemis.host = 192.168.1.210
＃spring.artemis.port = 9876
＃spring.artemis.user = admin
＃spring.artemis.password =秘密
```




#### JMS票务注册表JNDI



```properties
＃spring.jms.jndi-name = java：/ MyConnectionFactory
```




### Ehcache票务注册表

这些属性适用于使用Ehcache库的2.x版的模块。 要了解更多有关这个话题， [请参阅本指南](../ticketing/Ehcache-Ticket-Registry.html)。



```properties
＃cas.ticket.registry.ehcache.replicate-updates-via-copy = true
＃cas.ticket.registry.ehcache.cache-manager-name = ticketRegistryCacheManager
＃cas.ticket.registry.ehcache.replicate-puts = true
＃cas.ticket.registry.ehcache.replicate-updates = true
＃cas.ticket.registry.ehcache.memory-store-eviction-policy = LRU
＃cas.ticket.registry.ehcache.config-location = classpath： /ehcache-replicated.xml
＃cas.ticket.registry.ehcache.maximum-batch-size = 100
＃cas.ticket.registry.ehcache.shared = false
＃cas.ticket.registry.ehcache.replication-interval = 10000
＃cas.ticket.registry.ehcache.cache-live-live = 2147483647
＃cas.ticket.registry.ehcache.disk-expiry-thread-interval-seconds = 0 =
＃cas.ticket.registry.ehcache .replicate-removals = true
＃cas.ticket.registry.ehcache.max-chunk-size = 5000000
＃cas.ticket.registry.ehcache.max-elements-on-disk = 0
＃cas.ticket.registry。 ehcache.max-elements-in-cache = 0
＃cas.ticket.registry.ehcache.max-elements-in-memory = 10000
＃cas.ticket.registry.eh cache.eternal = false
＃cas.ticket.registry.ehcache.loader-async = true
＃cas.ticket.registry.ehcache.replicate-puts-via-copy = true
＃cas.ticket.registry.ehcache.cache -time-to-idle = 0
＃cas.ticket.registry.ehcache.persistence = LOCALTEMPSWAP | NONE | LOCALRESTARTABLE | DISTRIBUTED
＃cas.ticket.registry.ehcache.synchronous-writes =

＃systemprops允许映射属性在处理configLocation config之前将其设置为系统属性。
${key}
在ehcache XML配置中引用＃cas.ticket.registry.ehcache.systemprops.key1 = value1
＃cas.ticket.registry.ehcache.systemprops.key2 = value2
```


该注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.ticket.registry.ehcache`。



### Ehcache 3票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/Ehcache-Ticket-Registry.html)。



```properties
＃cas.ticket.registry.ehcache3.enabled = true
＃cas.ticket.registry.ehcache3.max-elements-in-memory = 10000
＃cas.ticket.registry.ehcache3.per-cache-size-on-disk = 20MB
＃cas.ticket.registry.ehcache3.eternal = false
＃cas.ticket.registry.ehcache3.enable-statistics = true
＃cas.ticket.registry.ehcache3.enable-management = true
＃cas.ticket .registry.ehcache3.terracotta-cluster-uri =
＃cas.ticket.registry.ehcache3.default-server-resource = main
＃cas.ticket.registry.ehcache3.resource-pool-name = cas-ticket-pool
＃cas.ticket.registry.ehcache3.resource-pool-size = 15MB
＃cas.ticket.registry.ehcache3.root-directory = / tmp / cas / ehcache3
＃cas.ticket.registry.ehcache3.persist-on- disk = true
＃cas.ticket.registry.ehcache3.cluster-connection-timeout = 150
＃cas.ticket.registry.ehcache3.cluster-read-write-timeout = 5
＃cas.ticket.registry.ehcache3.clustered -cache-consistency =强
```


Terracota群集URI没有默认值，但格式为 `terracotta：//host1.company.org：9410，host2.company.org：9410 / cas-application`

在配置密钥 `cas.ticket.registry.ehcache3`下，此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption)。



### 点燃票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/Ignite-Ticket-Registry.html)。



```properties
＃cas.ticket.registry.ignite.key-algorithm =
＃cas.ticket.registry.ignite.protocol =
＃cas.ticket.registry.ignite.trust-store-password =
＃cas.ticket.registry.ignite .key-store-type =
＃cas.ticket.registry.ignite.key-store-file-path =
＃cas.ticket.registry.ignite.key-store-password =
＃cas.ticket.registry.ignite .trust-store-type =
＃cas.ticket.registry.ignite.ignite-address[0]= localhost：47500
＃cas.ticket.registry.ignite.ignite-address[1]=
＃cas.ticket.registry.ignite。信任存储文件路径=
＃cas.ticket.registry.ignite.ack-timeout = 2000
＃cas.ticket.registry.ignite.join-timeout = 1000
＃cas.ticket.registry.ignite.local-地址=
＃cas.ticket.registry.ignite.local-port = -1
＃cas.ticket.registry.ignite.network-timeout = 5000
＃cas.ticket.registry.ignite.socket-timeout = 5000
＃ cas.ticket.registry.ignite.thread-priority = 10
＃cas.ticket.registry.ignite.force-server-mode = false
＃cas.ticket.registry.ignite.client-mode = false

＃cas.ticket .registry.ignite.tickets -cache.write-synchronization-mode = FULL_SYNC
＃cas.ticket.registry.ignite.tickets-cache.atomicity-mode = TRANSACTIONAL
＃cas.ticket.registry.ignite.tickets-cache.cache-mode = REPLICATED
```


此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.ticket.registry.ignite`。



### Memcached票务注册表

要了解更多有关这个话题， [请阅读本指南](../ticketing/Memcached-Ticket-Registry.html)此注册表.Integration设置可用 [这里](Configuration-Properties-Common.html#memcached-integration-settings) 的配置项下 `cas.ticket.registry.memcached`。

此注册表的签名 & [此处](Configuration-Properties-Common.html#signing--encryption) 在配置项 `cas.ticket.registry.memcached`。



### DynamoDb票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/DynamoDb-Ticket-Registry.html)。 

此功能的常用配置设置在配置键 `cas.ticket.registry`[这里](Configuration-Properties-Common.html#dynamodb-configuration) 是可用的。 

在配置密钥 `cas.ticket.registry.dynamo-db`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以为此注册表签名 & 加密设置。

此功能的AWS设置在配置键 `cas.ticket.registry.dynamo-db`[这里](Configuration-Properties-Common.html#amazon-integration-settings) 是可用的。



```properties
＃cas.ticket.registry.dynamo-db.service-tickets-table-name = serviceTicketsTable
＃cas.ticket.registry.dynamo-db.proxy-tickets-table-name = proxyTicketsTable
＃cas.ticket.registry.dynamo -db.ticket-granting-tickets-table-name = ticketGrantingTicketsTable
＃cas.ticket.registry.dynamo-db.proxy-granting-tickets-table-name = proxyGrantingTicketsTable
＃cas.ticket.registry.dynamo-db.transient -session-tickets-table-name = transientSessionTicketsTable
```




### MongoDb票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/MongoDb-Ticket-Registry.html)。 在配置密钥 `cas.ticket.registry.mongo`下，这里 [](Configuration-Properties-Common.html#signing--encryption) 可以使用此注册表的Signing & 加密设置。  此功能的通用配置设置在配置键 `cas.ticket.registry`[这里](Configuration-Properties-Common.html#mongodb-configuration) 是可用的。



### Redis票务注册表

要了解更多有关这个话题， [请参阅本指南](../ticketing/Redis-Ticket-Registry.html)。 此功能的常用配置设置在配置键 `cas.ticket.registry`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。 该注册表的签名 & 可用 [这里是配置密钥 `cas.ticket.registry.redis`](Configuration-Properties-Common.html#signing--encryption)。



## 协议票据安全

控制在呼出时与客户端应用程序共享时， 保护由CAS服务器发行的票证。 签名和加密 密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 签署 & 这个加密设置 特征是可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.ticket`。



## 服务票证行为

控制服务票证的到期策略以及适用于ST的其他属性。



```properties
＃cas.ticket.st。最大长度= 20

＃cas.ticket.st。使用次数= 1
＃cas.ticket.st。秒杀时间= 10
```




## 代理授予票证行为



```properties
＃cas.ticket.pgt.max-length = 50
```




## 代理票行为



```properties
＃cas.ticket.pt。秒杀时间= 10
＃cas.ticket.pt。使用次数= 1
```





## 临时会话票证行为



```properties
＃cas.ticket.tst。秒杀时间= 300
```




## 票证授予票证行为



```properties
＃cas.ticket.tgt.only-track-most-recent-session = true
＃cas.ticket.tgt.max-length = 50
```




## TGT过期政策

票证到期策略在以下情况下被激活：

- 如果默认策略的超时值都设置为零或更少，则CAS应确保票证为 *从不* 已过期。
- 禁用策略需要将其所有超时设置都设置为等于或小于零的值。
- 如果未确定票证过期策略，CAS将确保票证始终 *始终视为*

<div class="alert alert-info"><strong>保留您所需要的！</strong><p>建议您仅保留和维护特定策略所需的 
 保留所有 
字段的副本或保留副本作为参考，同时将其注释掉，则为 <strong>UNNECESSARY</strong> 该策略最终将导致 
较差的升级，从而增加打破更改和混乱的部署的机会。</p></div>

票证过期策略按以下顺序激活：

1. 如果并且相应地配置了默认策略的设置，票证将永不过期。
2. 超时
3. 默认
4. 节流超时
5. 硬超时
6. 票总是立即过期。



### 默认

提供困难的时间以及滑动窗口。



```properties
＃设置为负值以使票证永不过期
＃cas.ticket.tgt。最大生存时间（秒）= 28800
＃cas.ticket.tgt。生存时间（秒）= 7200
```




### 记住账号



```properties
＃cas.ticket.tgt.remember-me.enabled = true
＃cas.ticket.tgt.remember-me.time-to-kill-in-seconds = 28800
```




### 超时

应用于TGT的到期策略提供了最近使用的到期策略，类似于Web服务器会话超时。



```properties
＃cas.ticket.tgt.timeout.max-live-in-seconds = 28800
```




### 节流超时

受限制的超时策略通过节流的概念扩展了超时策略，在节流的概念中，票证最多每N秒可以使用一次。



```properties
＃cas.ticket.tgt.throttledTimeout.timeToKillInSeconds = 28800
＃cas.ticket.tgt.throttledTimeout.timeInBetweenUsesInSeconds = 5
```




### 硬超时

硬超时策略提供了有限的票证生命周期，从创建时间起算。



```properties
＃cas.ticket.tgt.hard-timeout。秒杀时间= 28800
```




## Google reCAPTCHA集成

在CAS登录页面上显示Google的reCAPTCHA小部件。



```properties
＃cas.google-recaptcha.enabled = true
＃cas.google-recaptcha.verify-url = https：//www.google.com/recaptcha/api/siteverify
＃cas.google-recaptcha.site-key =
＃cas.google-recaptcha.secret =
＃cas.google-recaptcha.invisible =
＃cas.google-recaptcha.position = bottomright
```




## 谷歌分析

要了解更多有关这个话题， [请参阅本指南](../integration/Configuring-Google-Analytics.html)。



```properties
＃cas.googleAnalytics.googleAnalyticsTrackingId =
```




### Google Analytics Cookie

适用于此功能的常用cookie设置为 [，此处](Configuration-Properties-Common.html#cookie-properties) 位于配置键 `cas.google-analytics.cookie`。



```properties
cas.google-analytics.cookie.attribute-name =
cas.google-analytics.cookie.attribute-value-pattern =。+
```




## Spring Webflow

和所有其他与Webflow相关的设置来管理Spring Webflow的会话状态。

要了解更多有关这个话题， [请参阅本指南](../webflow/Webflow-Customization.html)。



```properties
＃cas.webflow.always-pause-redirect = false
＃cas.webflow.refresh = true
＃cas.webflow.redirect-same-state = false
＃cas.webflow.autoconfigure = true
＃cas.webflow.base -path =
```




### Spring Webflow登录装饰



#### Groovy



```properties
＃cas.webflow.login-decorator.groovy.location = file：/etc/cas/config/LoginDecorator.groovy
```




#### 休息

此功能的RESTful设置可用 [在这里](Configuration-Properties-Common.html#restful-integrations) 在配置键 `cas.webflow.login-decorator.rest`。



### Spring Webflow自动配置

控制CAS如何动态更改和配置Spring Webflow上下文的选项。 要了解更多有关这个话题， [请参阅本指南](../webflow/Webflow-Customization-Extensions.html)。



```properties
＃cas.webflow.autoconfigure = true
```




#### Spring Webflow Groovy自动配置

通过自定义的Groovy脚本控制Spring Webflow上下文。



```properties
＃cas.webflow.groovy.location = file：/etc/cas/config/custom-webflow.groovy
```




### Spring Webflow会话管理

要了解更多有关这个话题， [请参阅本指南](../webflow/Webflow-Customization-Sessions.html)。



```properties
＃cas.webflow.session.lock-timeout = 30
＃cas.webflow.session.compress = false
＃cas.webflow.session.max-conversations = 5

＃启用服务器端会话管理
＃cas.webflow。 session.storage = false
```


签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。



#### Spring Webflow客户端会话

加密密钥必须是大小为 `16`随机生成的字符串。 签名密钥 [是大小为 `512`的JWK](Configuration-Properties-Common.html#signing--encryption)。

签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.webflow`。



#### Spring Webflow Hazelcast服务器端会话



```properties
＃cas.webflow.session.hz-location = classpath：/hazelcast.xml
```




#### Spring Webflow MongoDb服务器端会话



```properties
＃spring.data.mongodb.host = mongo-srv
＃spring.data.mongodb.port = 27018
＃spring.data.mongodb.database = prod
```




#### Spring Webflow Redis服务器端会话



```properties
＃spring.session.store-type = redis
＃spring.redis.host = localhost
＃spring.redis.password =秘密
＃spring.redis.port = 6379
```




#### Spring Webflow JDBC服务器端会话



```properties
＃spring.session.store-type = jdbc
＃spring.session.jdbc.initialize-schema =嵌入
＃spring.session.jdbc.schema = classpath：org / springframework / session / jdbc / schema-@@ platform @@ .sql
＃spring.session.jdbc.table-name = SPRING_SESSION

＃spring.datasource.url = jdbc：hsqldb：mem：cas-sessions
＃spring.datasource.username = sa
＃spring.datasource.password =
```




### 身份验证异常

在CAS Webflow中映射自定义身份验证例外，并将其链接到消息捆绑中定义的自定义消息。

要了解更多有关这个话题， [请参阅本指南](../webflow/Webflow-Customization-Exceptions.html)。



```properties
＃cas.authn.errors.exceptions = value1，value2，...
```




### 认证中断

中断身份验证流程以联系外部服务。 要了解更多有关这个话题， [请参阅本指南](../webflow/Webflow-Customization-Interrupt.html)。



#### 身份验证中断JSON



```properties
＃cas.interrupt.json.location = file：/etc/cas/config/interrupt.json
```




#### 身份验证中断正则表达式属性



```properties
＃cas.interrupt.attribute-name =属性名称模式
＃cas.interrupt.attribute-value =属性值模式
```




#### 身份验证中断Groovy



```properties
＃cas.interrupt.groovy.location = file：/etc/cas/config/interrupt.groovy
```




#### 身份验证中断REST

此功能的RESTful设置在配置项 `cas.interrupt.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。




### 可接受的使用政策

确定CAS应如何尝试确定是否接受AUP。 要了解更多有关这个话题， [请参阅本指南](../webflow/Webflow-Customization-AUP.html)。



```properties
＃cas.acceptable-usage-policy.aup-attribute-name = aupAccepted
＃cas.acceptable-usage-policy.aup-policy-terms-attribute-name =会员
```




#### 默认



```properties
＃cas.acceptable-usage-policy.in-memory.scope =全球|认证
```


支持以下范围：

| 范围    | 描述                        |
| ----- | ------------------------- |
| `全球的` | 将决策存储在全局内存映射中（在服务器生命周期内）。 |
| `验证`  | 存储决策，以便在通过凭据进行身份验证时提示用户。  |




#### Groovy



```properties
＃cas.acceptable-usage-policy.groovy.location = file：/etc/cas/config/aup.groovy
```




#### 休息

此功能的RESTful设置在配置项 `cas.acceptable-usage-policy.rest`[这里](Configuration-Properties-Common.html#restful-integrations) 是可用的。



#### JDBC

如果AUP是通过JDBC控制的，则决定应如何在数据库实例内部记住选择。 此功能的数据库设置在配置键 `cas.acceptable-usage-policy.jdbc`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



```properties
＃cas.acceptable-usage-policy.jdbc.table-name = usage_policies_table
＃cas.acceptable-usage-policy.jdbc.aup-column =
＃cas.acceptable-usage-policy.jdbc.principal-id-column =用户名
＃cas.acceptable-usage-policy.jdbc.principal-id-attribute =
＃cas.acceptable-usage-policy.jdbc.sql-update = UPDATE %s SET %s= true在哪里 %s=？
```




#### CouchDb

此功能的常用配置设置为 [此处](Configuration-Properties-Common.html#couchdb-configuration) 在 下，配置键 `cas.acceptable-usage-policy`。 此功能使用 `异步` 设置。



#### Couchbase

此功能的数据库设置 配置键 `cas.acceptable-usage-policy.couchbase`下的 [这里](Configuration-Properties-Common.html#couchbase-integration-settings) 是可用的。



#### MongoDb

此功能的常用配置设置为 [此处](Configuration-Properties-Common.html#mongodb-configuration) 在 下，配置键 `cas.acceptable-usage-policy`。



#### 雷迪斯

此功能的常规配置设置在配置键 `cas.acceptable-usage-policy`[这里](Configuration-Properties-Common.html#redis-configuration) 是可用的。



#### LDAP

如果AUP是通过LDAP控制的，请决定应如何在LDAP实例内部记住选择。 此功能的LDAP设置在配置键 `cas.acceptable-usage-policy.ldap[0]`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



#### 禁用可接受的使用策略

允许禁用可接受的使用策略网络流-需要重新启动。



```properties
cas.acceptable-usage-policy.enabled = true
```




## REST API

要了解更多有关这个话题， [请参阅本指南](../protocol/REST-Protocol.html)。



```properties
＃cas.rest.attribute-name =
＃cas.rest.attribute-value =
＃cas.rest.header-auth =
＃cas.rest.body-auth =
＃cas.rest.tls-client-auth =
```




## 指标

要了解更多有关这个话题， [请参阅本指南](../monitoring/Monitoring-Statistics.html)。



### 阿特拉斯

默认情况下，指标会导出到本地计算机上运行的Atlas。 可以使用以下命令提供要使用的Atlas服务器的位置：



```properties
＃management.metrics.export.atlas.uri = http：//atlas.example.com：7101 / api / v1 / publish
```




### 数据狗

Datadog注册表会定期将指标推送到 `datadoghq` 要将指标导出到Datadog，必须提供您的API密钥：



```properties
＃management.metrics.export.datadog.api-key = YOUR_KEY
```


您还可以更改将度量标准发送到Datadog的时间间隔：



```properties
＃management.metrics.export.datadog.step = 30秒
```




### 神经节

默认情况下，指标会导出到在本地计算机上运行的Ganglia。 可以使用以下命令提供要使用的Ganglia服务器主机和端口：



```properties
＃management.metrics.export.ganglia.host = ganglia.example.com
＃management.metrics.export.ganglia.port = 9649
```




### 石墨

默认情况下，指标会导出到在本地计算机上运行的Graphite。 可以使用以下方式提供要使用的Graphite服务器主机和端口：



```properties
＃management.metrics.export.graphite.host = graphite.example.com
＃management.metrics.export.graphite.port = 9004
```




### InfluxDb

默认情况下，指标会导出到本地计算机上运行的Influx。 可使用以下命令提供要使用的Influx服务器的位置：



```properties
＃management.metrics.export.influx.uri = http：//influx.example.com：8086
```



### JMX

千分尺提供了到JMX的层次结构映射，主要是作为一种便宜且可移植的方式在本地查看指标。



### 新遗物

New Relic注册表会定期将指标推送到New Relic。 要将指标导出到New Relic，必须提供您的API密钥和帐户ID：



```properties
＃management.metrics.export.newrelic.api-key = YOUR_KEY
＃management.metrics.export.newrelic.account-id = YOUR_ACCOUNT_ID
```


您还可以更改将度量标准发送到“ New Relic”的时间间隔：



```properties
＃management.metrics.export.newrelic.step = 30s
```




### 普罗米修斯

Prometheus希望抓取或轮询单个应用程序实例以获取指标。 Spring Boot在 `/ actuator / prometheus` 提供了一个执行器端点 ，以适当格式显示Prometheus抓取。

这是一个示例 `scrape_config` 添加到 `prometheus.yml`：



```yaml
scrape_configs：
  - JOB_NAME： '春'
    metrics_path： '/致动器/普罗米修斯'
    static_configs：
      -目标：[ 'HOST：PORT']
```




### 信号交换

SignalFx注册表会定期将指标推送到SignalFx。 要将指标导出到SignalFx，必须提供您的访问令牌：



```properties
＃management.metrics.export.signalfx.access-token = YOUR_ACCESS_TOKEN
```


您还可以更改将度量标准发送到SignalFx的时间间隔：



```properties
＃management.metrics.export.signalfx.step = 30s
```


千分尺附带一个简单的内存后端，如果未配置其他注册表，该后端将自动用作后备。 这使您可以查看在度量标准端点中收集了哪些度量标准。

使用任何其他可用后端时，内存后端都会自行禁用。 您还可以显式禁用它：



```properties
＃management.metrics.export.simple.enabled = false
```




### 统计数据

StatsD注册表急切地通过UDP将度量标准推送到StatsD代理。 默认情况下， 量度导出到在本地计算机上运行的StatsD代理。 可以使用以下命令提供要使用的StatsD代理主机和端口：



```properties
＃management.metrics.export.statsd.host = statsd.example.com
＃management.metrics.export.statsd.port = 9125
```


您还可以更改要使用的StatsD线路协议（默认为Datadog）：



```properties
＃management.metrics.export.statsd.flavor = etsy
```




### 波前

Wavefront注册表会定期将指标推送到Wavefront。 如果要直接将指标导出到 Wavefront，则必须提供API令牌：



```properties
＃management.metrics.export.wavefront.api-token = YOUR_API_TOKEN
```


或者，您可以使用在您的环境中设置的Wavefront边车或内部代理，该代理将 指标数据转发到Wavefront API主机：



```properties
＃management.metrics.export.uri = proxy：// localhost：2878
```


您还可以更改将度量标准发送到Wavefront的时间间隔：



```properties
＃management.metrics.export.wavefront.step = 30s
```




## SAML元数据UI

控制在CAS正在为外部SAML2 IdP处理身份验证的情况下，如何在主要的CAS登录页面 

要了解更多有关这个话题， [请参阅本指南](../integration/Shibboleth.html)。



```properties
＃cas.saml-metadata-ui.require-valid-metadata = true
＃cas.saml-metadata-ui.resources = classpath：/ sp-metadata：:classpath:/pub.key,http://md.incommon.org /InCommon/InCommon-metadata.xml：:classpath:/inc-md-pub.key
＃cas.saml-元数据ui.max-有效性= 0
＃cas.saml-元数据ui.require签名根=假
＃cas.saml-metadata-ui.parameter = entityId
```


此功能的计划程序设置在配置键 `cas.saml-metadata-ui`[这里](Configuration-Properties-Common.html#job-scheduling) 是可用的。



## 尤里卡服务发现

要了解更多有关这个话题， [请参阅本指南](../installation/Service-Discovery-Guide-Eureka.html)。



```properties
＃eureka.client.service-url.default-zone = $ {EUREKA_SERVER_HOST：http：// localhost：8761} /
＃eureka.client.enabled = true
＃eureka.instance.status-page-url =${cas.server.prefix}/ actuator / info
＃eureka.instance.health-check-url =${cas.server.prefix}/ actuator / health
＃eureka.instance.home-page-url =${cas.server.prefix}/
＃eureka.client.healthcheck.enabled = true

＃春天。 cloud.config.discovery.enabled = false
```




## 领事服务发现

要了解更多有关这个话题， [请参阅本指南](../installation/Service-Discovery-Guide-Consul.html)。



```properties
＃spring.cloud.consul.port = 8500
＃spring.cloud.consul.enabled = true
＃spring.cloud.consul.host = localhost

＃spring.cloud.consul.discovery.health-check-path =<health-endpoint-url>
＃spring.cloud.consul.discovery.health-check-interval = 15s
＃spring.cloud.consul.discovery.instance-id =${spring.application.name}：${random.value}

＃spring.cloud.consul.discovery.heartbeat.enabled = true
＃ spring.cloud.consul.discovery.heartbeat.ttl-value = 60
＃spring.cloud.consul.discovery.heartbeat.ttl-unit = s
```




## 调配



### 信息管理系统

通过SCIM设置经过身份验证的CAS主体。 要了解更多有关这个话题， [请参阅本指南](../integration/SCIM-Integration.html)。



```properties
＃cas.scim.version = 2
＃cas.scim.target =
＃cas.scim.oauthToken =
＃cas.scim.username =
＃cas.scim.password =
```




## 属性同意

CAS提供了在属性释放时强制执行用户知情同意的功能。 要了解更多有关这个话题， [请参阅本指南](../integration/Attribute-Release-Consent.html)。



```properties
＃cas.consent.reminder = 30
＃cas.consent.reminder-time-unit =小时|天数|月
＃cas.consent.enabled = true
＃cas.consent.active = true

＃cas.consent.activation -strategy-groovy-script.location =
```


此功能的签名 & [在这里](Configuration-Properties-Common.html#signing--encryption) 在配置密钥 `cas.consent`。 签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。



### Webflow配置

此功能的Webflow自动配置设置可用 [在这里](Configuration-Properties-Common.html#webflow-auto-configuration) 在 下配置键 `cas.consent.webflow`。



### JSON属性同意



```properties
＃cas.consent.json.location = file：/etc/cas/config/consent.json
```




### Groovy属性同意



```properties
＃cas.consent.groovy.location =文件：/etc/cas/config/consent.groovy
```




### JPA属性同意

此功能的数据库设置在配置键 `cas.consent.jpa`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。



### LDAP属性同意

此功能的LDAP设置在配置项 `cas.consent.ldap`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 是可用的。



```properties
＃cas.consent.ldap.consent-attribute-name = casConsentDecision
```




### MongoDb属性同意

此功能的常规配置设置在 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置键 `cas.consent`下可用。



### Redis属性同意

此功能的常规配置设置在 [此处](Configuration-Properties-Common.html#redis-configuration) 配置键 `cas.consent`下可用。



### CouchDb属性同意

此功能的常规配置设置在 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置键 `cas.consent`下可用。



### REST属性同意



```properties
＃cas.consent.rest.endpoint = https：//api.example.org/trustedBrowser
```




## Apache Fortress身份验证

要了解更多有关这个话题， [请参阅本指南](../installation/Configuring-Fortress-Authentication.html)。



```properties
＃cas.authn.fortress.rbaccontext = HOME
```




## CAS客户

配置与Java CAS客户端相关的设置，这些设置配置为处理入站票证验证操作等。



```properties
＃cas.client.prefix = https：//sso.example.org/cas
＃cas.client.validator-type = CAS10 | CAS20 | CAS30 | JSON
```




## 密码同步

允许用户将帐户密码同步到各种目标位置。 要了解有关该 主题的 [本指南](../installation/Password-Synchronization.html)。



### LDAP密码同步

此功能的常用LDAP设置可用 [在这里](Configuration-Properties-Common.html#ldap-connection-settings) 下 在配置密钥 `cas.authn.passwordSync.ldap[0]`。



```properties
＃cas.authn.password-sync.enabled = true
＃cas.authn.password-sync.ldap[0].enabled = false
```




## 密码管理

允许用户就地更新其帐户密码等。 要了解更多有关这个话题， [请参阅本指南](../installation/Password-Policy-Enforcement.html)。



```properties
＃cas.authn.pm.enabled = true
＃cas.authn.pm.captcha-enabled = false

＃最少8个字符和最多10个字符，至少1个大写字母，1个小写字母，1个数字和1个特殊字符
＃cas .authn.pm.policy-pattern = ^（？=。*[a-z]）（？=。*[A-Z]）（？=。* \\ d）（？=。* [$ @ $！％*？&]）[ A-Za-z \\ d $ @ $！％*？&]{8,10}

＃cas.authn.pm.reset.expirationMinutes = 1
＃cas.authn.pm.reset.security-questions-enabled = true

＃密码管理令牌将包含客户端IP地址还是服务器IP地址。
＃cas.authn.pm.reset.include-server-ip-address = true
＃cas.authn.pm.reset.include-client-ip-address = true

＃成功更改密码后自动登录
＃cas。 authn.pm.auto-login = false
```


此功能的常用电子邮件通知设置在配置键 `cas.authn.pm.reset`[这里](Configuration-Properties-Common.html#email-notifications) 是可用的。 此功能的SMS通知设置为 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 在配置键 `cas.authn.pm.reset`。

签名和加密密钥 [都是大小为 `512` 和 `256`](Configuration-Properties-Common.html#signing--encryption)。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 签署 & 这个特性加密设置可用 [这里](Configuration-Properties-Common.html#signing--encryption) 所述配置密钥下 `cas.authn.pm.reset`。



### Webflow配置

此功能的Webflow自动配置设置可用 [在这里](Configuration-Properties-Common.html#webflow-auto-configuration) 在 下配置键 `cas.authn.pm.webflow`。



### 密码记录

要了解更多有关这个话题， [请参阅本指南](../installation/Password-Policy-Enforcement.html)。



```properties
＃cas.authn.pm.history.enabled = false

＃cas.authn.pm.history.groovy.location = classpath：PasswordHistory.groovy
```




### JSON密码管理



```properties
＃cas.authn.pm.json.location = classpath：jsonResourcePassword.json
```




### Groovy密码管理



```properties
＃cas.authn.pm.groovy.location = classpath：PasswordManagementService.groovy
```




### LDAP密码管理

此功能的通用LDAP设置在配置键 `cas.authn.pm.ldap[0]`[这里](Configuration-Properties-Common.html#ldap-connection-settings) 可用。



```properties
＃cas.authn.pm.ldap[0].type = AD | GENERIC | EDirectory | FreeIPA
＃cas.authn.pm.ldap[0].username-attribute = uid

＃应该获取以指示安全性问题和答案的属性
＃ cas.authn.pm.ldap[0].security-questions-attributes.attr-question1 = attrAnswer1
＃cas.authn.pm.ldap[0].security-questions-attributes.attr-question2 = attrAnswer2
＃cas.authn.pm。 ldap[0].security-questions-attributes.attr-question3 = attrAnswer3
```




### JDBC密码管理

此功能的通用数据库设置在配置键 `cas.authn.pm.jdbc`[这里](Configuration-Properties-Common.html#database-settings) 是可用的。 在配置密钥 `cas.authn.pm.jdbc`[这里](Configuration-Properties-Common.html#password-encoding) ，可以使用 功能的通用密码编码设置。



```properties
＃预期将返回下面指示的两个字段
＃cas.authn.pm.jdbc.sql-security-questions = SELECT问题，从表WHERE user =中回答？

＃cas.authn.pm.jdbc.sql-find-email =从表中选择电子邮件，用户=？
＃cas.authn.pm.jdbc.sql-find-phone =从表中选择电话，用户=？
＃cas.authn.pm.jdbc.sql-find-user =从表中选择用户WHERE email =？
＃cas.authn.pm.jdbc.sql-change-password =更新表SET密码=？ 用户在哪里？
```




### REST密码管理



```properties
＃cas.authn.pm.rest.endpoint-url-email =
＃cas.authn.pm.rest.endpoint-url-phone =
＃cas.authn.pm.rest.endpoint-url-user =
＃cas .authn.pm.rest.endpoint-url-security-questions =
＃cas.authn.pm.rest.endpoint-url-change =
＃cas.authn.pm.rest.endpoint-username =
＃cas.authn .pm.rest.endpoint-password =
```
