---
layout: 默认
title: CAS通用属性概述
category: 配置
---

# CAS的共同属性

本文档介绍了一些建议和配置选项，这些建议和配置选项适用于一部分CAS模块和功能，并且在其中很常见。 要查看CAS属性的完整列表，请 [查看本指南](Configuration-Properties.html)。

## 什么是 `${configurationKey}`？

许多CAS *sub* 设置是通用的，适用于许多模块和功能。 例如，在处理数据库身份验证时，有许多与数据库相关的模块，这些模块拥有用于定义数据库驱动程序的单独设置。 这些设置通常定义为 `cas.authn.feature1.database-driver = xyz` 和 `cas.authn.feature2.database-driver = abc`。 该页面没有复制共享的和通用的 `数据库驱动程序` `${configurationKey}`下引用特定功能时仅收集功能和模块之间的通用CAS设置。 因此， `feature1` 或 `feature2` `${configurationKey}.database-driver` 下找到与数据库相关的通用设置（例如 `database-driver`），其中 `${configurationKey}` 可能是 `cas .authn.feature1` 或 `cas.authn.feature2` 具体取决于手边的功能。 要从通用设置块继承的每个功能的注释和文档应始终公布 `${configurationKey}`的适当值。

## 命名约定

- 由CAS平台直接控制的设置和属性始终始终以前缀 `cas`开头。 所有其他设置均为 并通过其他基础框架提供给CAS，并且可能具有自己的架构和语法。 **区分时要小心**

- CAS和/或CAS依赖的框架拒绝无法识别的属性。 这意味着，如果您以某种方式拼写错误的属性定义或不遵循点符号语法，等等， ，并且它控制的功能可能永远不会以您想要的方式被激活。

## 索引设置

能够接受多个值的CAS设置通常以索引记录，例如 `cas.some.setting[0]= value`。 索引 `[0]` 旨在由采用者递增，以允许使用不同的多个配置块：

```properties
＃cas.some.setting[0]= value1
＃cas.some.setting[1]= value2
```

## 信任但要验证

如果不确定给定的CAS设置的含义，请毫不犹豫地将其 **NOT** 查看代码库或更佳的代码， [提出问题](/cas/Mailing-Lists.html) 以阐明预期的行为。

<div class="alert alert-info"><strong>把事情简单化</strong><p>如果您不知道或无法确定设置的用途，则不需要它。</p></div>

## 时间单位

随着时间单位应对所有CAS设置，除非另有说明， 应支持对度量单位全面清晰的时间语法：

```bash
“ PT20S”-解析为“ 20秒”
“ PT15M”-解析为“ 15分钟”
“ PT10H”-解析为“ 10小时”
“ P2D”-解析为“ 2天”
“ P2DT3H4M “-解析为“ 2天3小时4分钟”
```

仍然支持本机数字语法，尽管 以了解确切的度量单位。

## 作业调度

许多CAS组件都具有计划后台作业以清理令牌，删除记录等的功能。 可以使用以下设置来控制调度程序的行为：

```properties
＃ ${configurationKey}.schedule.start-delay = PT10S
＃ ${configurationKey}.schedule.repeat-interval = PT60S
＃ ${configurationKey}.schedule.enabled = true
```

## 身份验证限制

CAS中的某些功能（例如 [OAuth](../installation/OAuth-OpenId-Authentication.html) 或 [REST API](../protocol/REST-Protocol.html)，除了在登录流和身份验证尝试期间应用 通用身份验证限制功能之外，还可以限制对特定端点的请求。

为了完全提供此功能，期望打开 [身份验证限制](../installation/Configuring-Authentication-Throttling.html)

## 认证凭证选择

允许许多身份验证处理程序确定它们是否可以对提供的凭据 进行操作，因此可以在身份验证处理程序选择阶段进行尝试和测试。 凭据标准 可以是以下选项之一：

- 根据凭证标识符测试的正则表达式模式
- 您自己设计的完全合格的类名称，类似于以下示例：

```java
导入java.util.function.Predicate;
import org.apereo.cas.authentication.Credential;

公共类PredicateExample实现谓词<Credential> {
    @Override
    公共布尔测试（最终凭据凭据）{
        //检查凭据并返回true / false
    }
}
```

- 类似于以下示例的外部Groovy脚本的路径：

```groovy
import org.apereo.cas.authentication.Credential
import java.util.function.Predicate

类PredicateExample实现谓词<Credential> {
    @Override
    布尔测试（最终凭据）{
        //测试并返回结果
    }
}
```

## 密码编码

CAS的某些方面（例如身份验证处理）支持 密码编码的配置。 大多数选项基于Spring Security对密码编码</a>支持。</p> 

*配置密钥*，以下与CAS中的密码编码支持有关的选项同样适用于许多CAS组件（身份验证处理程序等）：



```properties
＃ ${configurationKey}.password-encoder.type = NONE | DEFAULT | STANDARD | BCRYPT | SCRYPT | PBKDF2
＃ ${configurationKey}.password-encoder.character-encoding =
＃ ${configurationKey}.password-encoder.encoding-algorithm =
＃ ${configurationKey}.password- encoder.secret =
＃ ${configurationKey}.password-encoder.strength = 16
```


支持以下选项：

| 类型                            | 描述                                                                                                                                                                               |
| ----------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `没有任何`                        | 没有密码编码（即纯文本）发生。                                                                                                                                                                  |
| `默认`                          | 使用CAS `DefaultPasswordEncoder` `characterEncoding` 和 `encodingAlgorithm`消息摘要算法。                                                                                                  |
| `BCRYPT`                      | 根据提供的 `强度` 和可选的 `秘密`使用 `BCryptPasswordEncoder`。                                                                                                                                  |
| `加密`                          | 使用 `SCryptPasswordEncoder`。                                                                                                                                                      |
| `PBKDF2`                      | 根据提供的 `强度` 和可选的 `秘密`使用 `Pbkdf2PasswordEncoder`。                                                                                                                                  |
| `标准`                          | 根据提供的 `密码` 使用 `StandardPasswordEncoder`。                                                                                                                                         |
| `社保局`                         | 使用 `LdapShaPasswordEncoder` 支持Ldap SHA和SSHA（盐腌SHA）。 这些值是基于base-64编码的，并在编码的哈希值之前添加 `{SHA}` （或 `{SSHA}`                                                                            |
| `GLIBC_CRYPT`                 | 使用 `GlibcCryptPasswordEncoder` 基于该 [`编码算法`](https://commons.apache.org/proper/commons-codec/archives/1.10/apidocs/org/apache/commons/codec/digest/Crypt.html)， `强度` 提供和可选的 `秘密`。 |
| `org.example.MyEncoder`       | 您自己选择的 `PasswordEncoder`                                                                                                                                                         |
| `文件：///path/to/script.groovy` | Groovy脚本的路径，该脚本负责处理密码编码操作。                                                                                                                                                       |


如果您打算设计自己的密码编码器或编写脚本来这样做，则 可能还需要确保覆盖层在运行时 `org.springframework.security:spring-security-core` 确保提供</code> 或 `compileOnly` 才能将工件标记为 `，以避免冲突。</p>

<p spaces-before="0">如果您需要设计自己的密码编码方案，其中将类型指定为标准Java类名，则该类的结构将为
 类似于以下内容：</p>

<pre><code class="java">包org.example.cas;

import org.springframework.security.crypto.codec。*;
导入org.springframework.security.crypto.password。*;

公共类MyEncoder扩展AbstractPasswordEncoder {
    @Override
    受保护的byte []编码（CharSequence rawPassword，byte [] salt）{
        返回...
    }
}
`</pre> 

如果您需要设计自己的密码编码方案，在该方案中将类型指定为Groovy脚本的路径，则脚本的结构将类似于以下 



```groovy
import java.util。*

def byte [] run（final Object ... args）{
    def rawPassword = args[0]
    def generateSalt = args[1]
    def logger = args[2]
    def casApplicationContext = args[3]

    logger.debug（ “编码密码...”）
    返回 ...
}

def布尔匹配（最终对象... args）{
    def rawPassword = args[0]
    def encodingPassword = args[1]
    def logger = args[2]
    def casApplicationContext = args[3]

   logger.debug（“是否匹配？” ）;
   回...
```




## 身份验证主体转换

可以配置通常处理用户名密码凭证 身份验证处理程序，以在执行身份验证序列之前转换用户ID。 可以使用以下选项：

| 类型     | 描述         |
| ------ | ---------- |
| `没有任何` | 不要应用任何转换。  |
| `大写`   | 将用户名转换为大写。 |
| `小写`   | 将用户名转换为小写。 |


作为主体转换的一部分的身份验证处理程序也可以被提供到Groovy脚本的路径，以转换提供的用户名。 脚本的大纲可以采用以下形式：



```groovy
def字符串运行（最终对象... args）{
    def提供的用户名= args[0]
    def记录器= args[1]
    返回ProvidedUsername.concat（“ SomethingElse”）
}
```


*配置密钥*，以下与CAS中的主体转换支持有关的选项同样适用于许多CAS组件（身份验证处理程序等）：



```properties
＃ ${configurationKey}.principal-transformation.pattern =（。+）@ example.org
＃ ${configurationKey}.principal-transformation.groovy.location = file：///etc/cas/config/principal.groovy
＃ ${configurationKey}.principal-transformation .suffix =
＃ ${configurationKey}.principal-transformation.case-conversion = NONE |大写|小写
＃ ${configurationKey}.principal-transformation.prefix =
```




## Cookie属性

以下公共属性在CAS中配置cookie生成器支持。



```properties
＃ ${configurationKey}.path =
＃ ${configurationKey}.max-age = -1
＃ ${configurationKey}.domain =
＃ ${configurationKey}.name =
＃ ${configurationKey}.secure = true
＃ ${configurationKey}.http-only = true
＃ ${configurationKey}.same-site -policy = none | lax | strict
＃ ${configurationKey}.comment = CAS Cookie
```




## Cassandra配置

与Cassandra相关的控件属性，CAS尝试建立连接，运行查询等时 



```properties
＃ ${configurationKey}.keyspace =
＃ ${configurationKey}.contact-points = localhost：9042
＃ ${configurationKey}.local-dc =
＃ ${configurationKey}.consistency-level = ANY |一个|两个|三个| QUORUM | LOCAL_QUORUM | ALL | EACH_QUORUM | LOCAL_SERIAL | SERIAL | LOCAL_ONE
＃ ${configurationKey}.serial-consistency-level = ANY |一个|两个|三个| QUORUM | LOCAL_QUORUM | ALL | EACH_QUORUM | LOCAL_SERIAL | SERIAL | LOCAL_ONE
＃ ${configurationKey}.timeout = PT5S
```




## 休眠 & JDBC

控制与Hibernate相关的全局属性，当CAS尝试使用和利用数据库资源时 ，连接和查询为 



```properties
＃cas.jdbc.show-sql = true
＃cas.jdbc.gen-ddl = true
＃cas.jdbc.case-insensitive = false
＃cas.jdbc.physical-table-names。{table-name}={new-table-name}
```




### 数据库设置

*配置密钥*，与CAS中的JPA / JDBC支持相关的以下选项同样适用于许多CAS组件（票证登记处等）：



```properties
＃ ${configurationKey}.user = sa
＃ ${configurationKey}.password =
＃ ${configurationKey}.driver-class = org.hsqldb.jdbcDriver
＃ ${configurationKey}.url = jdbc：hsqldb：mem：cas-hsql-database
＃ ${configurationKey}.dialect = org。 hibernate.dialect.HSQLDialect

＃ ${configurationKey}.fail-fast-timeout = 1
＃ ${configurationKey}.isolation-level-name = ISOLATION_READ_COMMITTED 
＃ ${configurationKey}.health-query =
＃ ${configurationKey}.isolate-internal-queries = false
＃ ${configurationKey}。泄漏阈值= 10
＃ ${configurationKey}.propagation-behaviorName = PROPAGATION_REQUIRED
＃ ${configurationKey}.batchSize = 1
＃ ${configurationKey}.default-catalog =
＃ ${configurationKey}.default-schema =
＃ ${configurationKey}.ddl-auto = create-drop
＃ ${configurationKey}.physical-naming-strategy-class-name = org.apereo.cas.hibernate.CasHibernatePhysicalNamingStrategy

＃ ${configurationKey}.autocommit = false
＃ ${configurationKey}.idle-timeout = 5000

＃ ${configurationKey}.data-source-name =
＃ ${configurationKey}.data-source-roxy = false

＃休眠特定的属性（即“ hibernate.globally_quoted_identifiers”）
＃ ${configurationKey}.properties.property-name = propertyValue

＃ ${configurationKey}.pool.suspension = false
＃ ${configurationKey}.pool.mi- size = 6
＃ ${configurationKey}.pool.max-size = 18
＃ ${configurationKey}.pool.max-wait = 2000
＃ ${configurationKey}.pool.timeout-millis = 1000
```




### 基于容器的JDBC连接

如果您打算与CAS一起使用容器管理的JDBC连接（即 JPA票务/服务注册，等等） 则可以设置 `数据源的名称` 上的任何需要一个数据库中的配置项的属性 连接。 使用容器配置的数据源时，将不使用许多与池相关的参数。 如果指定了 `数据源名称` ，但JNDI查找失败，则将使用配置的 （或默认）CAS池参数创建数据源。

如果在尝试使用容器数据源时遇到类加载错误，则可以尝试将 设置 `data-source-proxy` 设置为true，这将以 方式包装容器数据源，以解决错误。

`data-source-name` 属性可以是数据源的JNDI名称，也可以是带有 `java：/ comp / env /`前缀的资源名称。 如果它是资源名称，那么您需要在 `web.xml` 中添加一个条目，您可以将其添加到 CAS覆盖中。 它应包含这样的条目：



```xml
<resource-ref>
    <res-ref-name>jdbc / casDataSource</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>容器</res-auth>
</resource-ref>
```


在Apache Tomcat中，可以在 `context.xml`像这样定义容器数据源：



```xml
<Resource name="jdbc/casDataSource"
    auth="Container"
    type="javax.sql.DataSource"
    driverClassName="org.postgresql.Driver"
    url="jdbc:postgresql://casdb.example.com:5432/xyz_db"
    username="cas"
    password="xyz"
    testWhileIdle="true"
    testOnBorrow="true"
    testOnReturn="false"
    validationQuery="select 1"
    validationInterval="30000"
    timeBetweenEvictionRunsMillis="30000"
    factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
    minIdle="0"
    maxIdle="5"
    initialSize="0"
    maxActive="20"
    maxWait="10000" />
```


在Jetty中，可以使用 `jetty.xml` 或 `jetty-env.xml` 文件将池放入JNDI中，如下所示：



```xml
<？xml版本=“ 1.0”？>
<！DOCTYPE配置PUBLIC“-// Jetty // Configure // EN”“ http://www.eclipse.org/jetty/configure_9_3.dtd”>

<Configure class="org.eclipse.jetty.webapp.WebAppContext">
<New id="datasource.cas" class="org.eclipse.jetty.plus.jndi.Resource">
    <Arg></Arg> <！-空范围arg是JVM作用域>
    <Arg>jdbc / casDataSource</Arg> <！-与web.xml中的资源匹配的名称>
    <Arg>
        <New class="org.apache.commons.dbcp.BasicDataSource">
            <Set name="driverClassName">oracle.jdbc.OracleDriver</Set>
            <Set name="url">jdbc：oracle：thin：@ / /casdb.example.com:1521/ntrs“</Set>
            <Set name="username">cas</Set>
            <Set name="password">xyz</Set>
            <Set name="validationQuery">从对偶中选择假人</Set>
            <Set name="testOnBorrow">true</Set>
            <Set name="testOnReturn">false</Set>
            <Set name="testWhileIdle">false</Set>
            <Set name="defaultAutoCommit">false</Set>
            <Set name="initialSize">0</Set>
            <Set name="maxActive">15</Set>
            <Set name="minIdle">0</Set>
            <Set name="maxIdle">5</Set>
            <Set name="maxWait">2000</Set>
        </New>
    </Arg>
</New>
</Configure>
```




## 签名 & 加密

CAS中的许多组件都接受签名和加密密钥。 在大多数情况下，如果未提供密钥，CAS将自动生成 如果您希望手动并事先创建签名和加密密钥，请遵循以下说明。

请注意，如果要求您为密钥创建一个具有一定大小 [JWK](https://tools.ietf.org/html/rfc7517) 以下命令集来生成令牌：



```bash
wget https://raw.githubusercontent.com/apereo/cas/master/etc/jwk-gen.jar
java -jar jwk-gen.jar -t oct -s [size]
```


结果将类似于：



```json
{
  “ kty”：“ oct”，
  “ kid”：“ ...”，
  “ k”：“ ...”
}
```


`k` 的生成值需要分配给相关的CAS设置。 请注意，CAS通过使用高级加密标准（`AES`）算法 生成的密钥， 项电子数据加密规范。 美国国家标准技术研究所。



### 设定值

*配置密钥*，以下加密选项同样适用于相关的CAS 组件（票证注册表等）：



```properties
＃ ${configurationKey}.crypto.signing.key =
＃ ${configurationKey}.crypto.signing.key-size =

＃ ${configurationKey}.crypto.encryption.key =
＃ ${configurationKey}.crypto.encryption.key-size =

＃ ${configurationKey}.crypto.alg = AES
＃ ${configurationKey}.crypto.enabled = false   

＃ ${configurationKey}.crypto.strategyType = ENCRYPT_AND_SIGN | SIGN_AND_ENCRYPT
```


可以使用以下密码策略类型：

| 类型                 | 描述             |
| ------------------ | -------------- |
| `ENCRYPT_AND_SIGN` | 默认策略；加密值，然后签名。 |
| `SIGN_AND_ENCRYPT` | 签名值，然后加密。      |




### RSA密钥

某些功能（例如产生 [JWT作为CAS票证](../installation/Configure-ServiceTicket-JWT.html) 可能使您可以将 `RSA` 算法与公用/专用密钥对一起使用来进行签名和加密。 在CAS编码的有效负载的使用者是局外人和客户端应用程序的情况下，此行为可能会被证明是有用的，该局外人和客户端应用程序无需直接和可见地访问签名秘密，并且相对于公钥，以验证有效载荷的真实性并对其进行解码。 在CAS本身既是有效载荷的产生者又是使用者的情况下，此特定选项没有什么意义。

<div class="alert alert-info"><strong>记住</strong><p>签名和加密选项不是互斥的。 尽管这很荒谬，但CAS完全有可能使用 <code>AES</code> 密钥进行签名，使用 <code>RSA</code> 密钥进行加密，反之亦然。</p></div>

为了启用用于签名有效负载的RSA功能，您将需要通过以下示例命令生成私有/公共密钥对：



```bash
openssl genrsa -out private.key 2048
openssl rsa -pubout -in private.key -out public.key -inform PEM -outder DER
```


需要在CAS属性中为相关功能的签名密钥配置私钥路径（即 `file：///path/to/private.key` 公钥需要与客户端应用程序和有效负载的使用者共享，以便验证有效负载签名。



```properties
＃cas.xyz.crypto.signing.key = file：///etc/cas/config/private.key
```

<div class="alert alert-info"><strong>按键大小</strong><p>请记住，RSA密钥大小必须至少为 <code>2048</code> 及以上。 CAS不接受较小的密钥大小，这将导致运行时错误。 做出明智的选择。</p></div>

为了启用RSA功能来加密有效负载，您将需要执行与上述操作相反的操作。 客户端应用程序将为您提供一个公共密钥，该公共密钥将用于加密有效负载，并且需要在CAS属性中为加密密钥配置 `file：///path/to/public.key`相关功能。 提交有效负载后，客户端应使用自己的私钥对有效负载进行解码并将其解压缩。



```properties
＃cas.xyz.crypto.encryption.key = file：///etc/cas/config/public.key
```




## 人员目录主要解析

*配置密钥*，当CAS尝试解析和构建经过身份验证的主体时，以下与Person Directory支持有关的选项：



```properties
＃ ${configurationKey}.principal-attribute = uid，sAMAccountName等
＃ ${configurationKey}.return-null = false
＃ ${configurationKey}.principal-resolution-failure-fatal = false
＃ ${configurationKey}.use-existing-principal-id = false
＃ ${configurationKey}.attribute-resolution-enabled = true
＃ ${configurationKey}.active-attribute-repository-ids = StubRepository等
```




## Git配置

*配置键*的情况下，以下与CAS中Git集成支持有关的选项在CAS尝试连接和拉/推更改时：



```properties
＃ ${configurationKey}.git.repository-url = https：//github.com/repository
＃ ${configurationKey}.git.branches-to-clone = master
＃ ${configurationKey}.git.active-branch = master
＃ ${configurationKey}.git.sign- commits = false
＃ ${configurationKey}.git.username =
＃ ${configurationKey}.git.password =
＃ ${configurationKey}.git.clone-directory.location = file：/ tmp / cas-service-registry
＃ ${configurationKey}.git.push-changes = false
＃ ${configurationKey}.git.private-key-passphrase =
＃ ${configurationKey}.git.private-key.location = file：/tmp/privkey.pem
＃ ${configurationKey}.git.ssh-session-password =
＃ ${configurationKey}。 git.timeout = PT10S
＃ ${configurationKey}.git.strict-host-key-checking = true
＃ ${configurationKey}.git.clear-existing-identities = false
```




## InfluxDb配置

*配置键*与CAS中的InfluxDb支持有关的以下选项同样适用于许多CAS组件：



```properties
＃ ${configurationKey}.url = http：// localhost：8086
＃ ${configurationKey}.username = root
＃ ${configurationKey}.password = root
＃ ${configurationKey}.retention-policy = autogen
＃ ${configurationKey}.drop-database = false
＃ ${configurationKey}.points- to-flush = 100
＃ ${configurationKey}.batch-interval = PT5S
＃ ${configurationKey}.consistency-level = ALL
```




## Apache Kafka配置

*配置键*与CAS中的Kafka支持有关的以下选项同样适用于许多CAS组件：



```properties
＃ ${configurationKey}.bootstrap-address =本地主机：9092
```




### Apache Kafka主题配置

*配置键*与CAS中的Kafka支持有关的以下选项同样适用于许多CAS组件：



```properties
＃ ${configurationKey}.name =
＃ ${configurationKey}.partitions = 1
＃ ${configurationKey}.replicas = 1
＃ ${configurationKey}.compression-type = gzip
＃ ${configurationKey}.config.key = value
```




## Hazelcast配置

*配置键*与CAS中的Hazelcast支持相关的以下选项同样适用于许多CAS组件：



```properties
＃ ${configurationKey}.cluster.members = 123.456.789.000,123.456.789.001
＃ ${configurationKey}.cluster.instance-name = localhost
＃ ${configurationKey}.cluster.port = 5701

＃ ${configurationKey}.license-key =
＃ ${configurationKey}.enable-compression =
＃ ${configurationKey}.enable-management-center-scripting = true
```


*配置键*，下面列出了更高级的Hazelcast配置设置：



```properties
＃ ${configurationKey}.cluster.tcpip-enabled = true

＃ ${configurationKey}.cluster.partition-member-group-type = HOST_AWARE | CUSTOM | PER_MEMBER | ZONE_AWARE | SPI
＃ ${configurationKey}.cluster.map-merge-policy = PUT_IF_ABSENT | HIGHER_HITS | DISCARD | PASS_THROUGH | EXPIRATION_TIME | LATEST_UPDATE | LATEST_ACCESS

＃ ${configurationKey}.cluster.eviction-policy = LRU
＃ ${configurationKey}.cluster.max-no-heartbeat-seconds = 300
＃ ${configurationKey}.cluster.logging-type = slf4j
＃ ${configurationKey}.cluster .port-auto-increment = true
＃ ${configurationKey}.cluster.max-size = 85
＃ ${configurationKey}.cluster.backup-count = 1
＃ ${configurationKey}.cluster.async-backup-count = 0
＃ ${configurationKey}.cluster.max -size-policy = USED_HEAP_PERCENTAGE
＃ ${configurationKey}.cluster.timeout = 5

＃ ${configurationKey}.cluster.local-address =
＃ ${configurationKey}.cluster.public-address =

＃ ${configurationKey}.cluster.outbound-ports[0]= 45000
```




### 静态WAN复制



```properties
＃ ${configurationKey}.cluster.wan-replication.enabled = false
＃ ${configurationKey}.cluster.wan-replication.replication-name = CAS

＃ ${configurationKey}.cluster.wan-replication.targets[0].endpoints = 1.2.3.4,4.5.6.7
＃ ${configurationKey}.cluster.wan-replication.targets[0].publisher-className = com.hazelcast.enterprise.wan.replication.WanBatchReplication
＃ ${configurationKey}.cluster.wan-replication.targets[0].queue-full-behavior = THROW_EXCEPTION
＃ ${configurationKey}.cluster.wan-replication.targets[0].acknowledge-type = ACK_ON_OPERATION_COMPLETE
＃ ${configurationKey}.cluster.wan-replication.targets[0].queue-capacity = 10000
＃ ${configurationKey}.cluster.wan-replication.targets[0].batch-size = 500
＃ ${configurationKey}.cluster.wan-replication.targets[0].snapshot-enabled = false
＃ ${configurationKey}.cluster.wan-replication.targets[0].batch-maximum-delay-milliseconds = 1000
＃ ${configurationKey}.cluster.wan-replication .targets[0].response-timeout-毫秒= 60000
＃ ${configurationKey}.cluster.wan-replication.targets[0].executor-thread-count = 2

＃ ${configurationKey}.cluster.wan-replication.targets[0].consistency-check-strategy = NONE | MERKLE_TREES
＃ ${configurationKey}.cluster.wan-replicat ion.targets[0].cluster-name =
＃ ${configurationKey}.cluster.wan-replication.targets[0].publisher-id =
＃ ${configurationKey}.cluster.wan-replication.targets[0].properties =
```




### 组播发现



```properties
＃ ${configurationKey}.cluster.multicast-trusted-interfaces =
＃ ${configurationKey}.cluster.multicast-enabled = false
＃ ${configurationKey}.cluster.multicast-port =
＃ ${configurationKey}.cluster.multicast-group =
＃ ${configurationKey}.cluster.multicast-超时= 2
＃ ${configurationKey}.cluster.multicast生存时间= 32
```




### AWS EC2发现



```properties
＃ ${configurationKey}.cluster.discovery.enabled = true

＃ ${configurationKey}.cluster.discovery.aws.access-ley =
＃ ${configurationKey}.cluster.discovery.aws.secret-ley =

＃ ${configurationKey}.cluster.discovery.aws.iam-角色=

＃ ${configurationKey}.cluster.discovery.aws.region = us-east-1
＃ ${configurationKey}.cluster.discovery.aws.host-header =
＃ ${configurationKey}.cluster.discovery.aws.security-group-name =
＃ ${configurationKey}.cluster.discovery.aws.tag-key =
＃ ${configurationKey}.cluster.discovery.aws.tag-value =
＃ ${configurationKey}.cluster.discovery.aws.port = -1
＃ ${configurationKey}.cluster.discovery.aws .connection-timeout-seconds = 5
```




### Apache jclouds发现



```properties
＃ ${configurationKey}.cluster.discovery.enabled = true

＃ ${configurationKey}.cluster.discovery.jclouds.provider =
＃ ${configurationKey}.cluster.discovery.jclouds.identity =
＃ ${configurationKey}.cluster.discovery.jclouds.credential =
＃ ${configurationKey}。 cluster.discovery.jclouds.endpoint =
＃ ${configurationKey}.cluster.discovery.jclouds.zones =
＃ ${configurationKey}.cluster.discovery.jclouds.regions =
＃ ${configurationKey}.cluster.discovery.jclouds.tag-keys =
＃ ${configurationKey}。 cluster.discovery.jclouds.tag-values =
＃ ${configurationKey}.cluster.discovery.jclouds.group =
＃ ${configurationKey}.cluster.discovery.jclouds.port = -1
＃ ${configurationKey}.cluster.discovery.jclouds.role-name =
＃ ${configurationKey}.cluster.discovery.jclouds.credential-path =
```




### Kubernetes发现



```properties
＃ ${configurationKey}.cluster.discovery.enabled = true

＃ ${configurationKey}.service-dns =
＃ ${configurationKey}.service-dns-timeout = -1
＃ ${configurationKey}.service-name =
＃ ${configurationKey}.service-label-name =
＃ ${configurationKey}.service-label-value =
＃ ${configurationKey}.cluster.discovery.kubernetes.namespace =
＃ ${configurationKey}.resolve-not-ready-addresses = false
＃ ${configurationKey}.cluster.discovery.kubernetes.kubernetes-master =
＃ ${configurationKey}.api-token =
```




### Docker Swarm发现



```properties
＃ ${configurationKey}.cluster.discovery.enabled = true

＃ ${configurationKey}.cluster.discovery.docker-swarm.dns-provider.enabled = true
＃ ${configurationKey}.cluster.discovery.docker-swarm.dns-provider.service-name =
＃ ${configurationKey}.cluster.discovery.docker-swarm.dns-provider.service-port = 5701
＃ ${configurationKey}.cluster.discovery.docker-swarm.dns-provider.peer-services = service-a，service-b等

＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.enabled = true
＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.group-name =
＃ ${configurationKey}.cluster.discovery.docker-swarm。 member-provider.group-password =
＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.docker-network-names =
＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.docker-service-名称=
＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.docker-service-labels =
＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.swarm-mgr-uri =
＃ ${configurationKey}。 cluster.discovery.docker-swarm.member-provider.skip-verify-ssl = false
＃ ${configurationKey}.cluster.discovery.docker-swarm.member-provider.hazelcast-peer-port = 5701
```




### Microsoft Azure发现



```properties
＃ ${configurationKey}.cluster.discovery.enabled = true

＃ ${configurationKey}.cluster.discovery.azure.subscription-id =
＃ ${configurationKey}.cluster.discovery.azure.client-id =
＃ ${configurationKey}.cluster.discovery.azure.client- secret =
＃ ${configurationKey}.cluster.discovery.azure.tenant-id =
＃ ${configurationKey}.cluster.discovery.azure.cluster-id =
＃ ${configurationKey}.cluster.discovery.azure.group-name =
```




## RADIUS配置

给定组件 *配置密钥*，与CAS中RADIUS支持相关的以下选项同样适用于许多CAS组件（身份验证等） 。

`服务器` 参数定义了已认证服务的标识值（CAS服务器），主要是 `server.protocol` `客户端`标识的RADIUS服务器进行通信。

`客户端` 参数定义用于连接RADIUS服务器的值。 当设置了 `failoverOnException` 时，参数 `client.inetAddress` 可以包含更多用逗号分隔的地址来定义故障转移服务器 



```properties
＃ ${configurationKey}.server.nas-port-id = -1
＃ ${configurationKey}.server.nas-real-port = -1
＃ ${configurationKey}.server.protocol = EAP_MSCHAPv2
＃ ${configurationKey}.server.retries = 3
＃ ${configurationKey}.server .nas-port-type = -1
＃ ${configurationKey}.server.nas-port = -1
＃ ${configurationKey}.server.nas-ip-address =
＃ ${configurationKey}.server.nas-ipv6-address =
＃ ${configurationKey}.server .nas-identifier = -1
＃ ${configurationKey}.client.authentication-port = 1812
＃ ${configurationKey}.client.shared-secret = N0Sh @ ar3d$ecReT
＃ ${configurationKey}.client.socket-timeout = 0
＃ ${configurationKey}.client.inet -address = localhost
＃ ${configurationKey}.client.accounting-port = 1813
＃ ${configurationKey}.failover-on-exception = false
＃ ${configurationKey}.failover-on-authentication-failure = false
```




## CouchDb配置

*配置密钥*，以下与CAS中的CouchDb支持有关的选项同样适用于许多CAS组件（票证登记处等）：



```properties
＃ ${configurationKey}.couch-db.url = http：// localhost：5984
＃ ${configurationKey}.couch-db.username =
＃ ${configurationKey}.couch-db.password =
＃ ${configurationKey}.couch-db.socket-timeout = 10000
＃ ${configurationKey}.couch-db.connection-timeout = 1000
＃ ${configurationKey}.couch-db.drop-collection = false
＃ ${configurationKey}.couch-db.max-connections = 20
＃ ${configurationKey}.couch-db.enable-ssl =
＃ ${configurationKey}.couch-db.relaxed-ssl-settings =
＃ ${configurationKey}.couch-db.caching = false
＃ ${configurationKey}.couch-db.max-cache-entries = 1000
＃ ${configurationKey}.couch-db.max- object-size-bytes = 8192
＃ ${configurationKey}.couch-db.use-expect-continue = true
＃ ${configurationKey}.couch-db.cleanup-idle-connections = true
＃ ${configurationKey}.couch-db.create-if-not -exists = true
＃ ${configurationKey}.couch-db.proxy-host =
＃ ${configurationKey}.couch-db.proxy-port = -1

＃默认值基于功能名称。
＃ ${configurationKey}.couch-db.db-name =

＃对于一些无法进行更新的功能，这些功能会自动解决。
＃ ${configurationKey}.couch-db.retries = 5

＃根据手头的功能，CAS可能异步执行某些操作。
＃ ${configurationKey}.couch-db.asynchronous = true
```




## MongoDb配置

*配置键*，以下与CAS中对MongoDb的支持有关的选项同样适用于许多CAS组件（票证登记处等）：



```properties
＃ ${configurationKey}.mongo.host =本地主机
＃ ${configurationKey}.mongo.client-uri =本地主机
＃ ${configurationKey}.mongo.port = 27017
＃ ${configurationKey}.mongo.drop-collection = false
＃ ${configurationKey}.mongo.socket-keep-alive = false
＃ ${configurationKey}.mongo.password =

＃ ${configurationKey}.mongo.collection = cas-service-registry

＃ ${configurationKey}.mongo.database-name = cas-mongo-database
＃ ${configurationKey}.mongo.timeout = 5000
＃ ${configurationKey}.mongo.user-id =
＃ ${configurationKey}.mongo.write-concern = NORMAL
＃ ${configurationKey}.mongo.read-concern =可用
＃ ${configurationKey}.mongo.read-preference = PRIMARY
＃ ${configurationKey}.mongo.authentication-database-名称=
＃ ${configurationKey}.mongo.replica-set =
＃ ${configurationKey}.mongo.ssl-enabled = false
＃ ${configurationKey}.mongo.retry-writes = false

＃ ${configurationKey}.mongo.pool.life-time = 60000
＃ ${configurationKey}.mongo.pool.idle-time = 30000
＃ ${configurationKey}.mongo.pool.max-wait-time = 60000
＃ ${configurationKey}.mongo.pool.max-size = 10
＃ ${configurationKey}.mongo.pool.min-size = 1
＃ ${configurationKey}.mongo.pool.per-host = 10
```




## DynamoDb配置

*配置键*，与CAS中的DynamoDb支持有关的以下选项同样适用于许多CAS组件（票证登记处等）：



```properties
＃ ${configurationKey}.dynamo-db.drop-tables-on-startup = false
＃ ${configurationKey}.dynamo-db.prevent-table-creation-on-startup = false
＃ ${configurationKey}.dynamo-db.local-instance = false
```


此功能的AWS设置在此处 [](#amazon-integration-settings)。



## RESTful集成

以下选项与CAS中的功能相关，这些功能提供REST支持以获取和更新数据。 *配置键*，这些设置将同样适用：



```properties
＃ ${configurationKey}.method = GET | POST
＃ ${configurationKey}.order = 0
＃ ${configurationKey}.case-insensitive = false
＃ ${configurationKey}.basic-auth-username = uid
＃ ${configurationKey}.basic-auth-password =密码
＃ ${configurationKey}。 headers.key = value
＃ ${configurationKey}.url = https：//rest.somewhere.org/attributes
```




## Redis配置

*配置密钥*的情况下，与CAS中的Redis支持相关的以下选项同样适用于许多CAS组件（机票登记册等）：



```properties
＃ ${configurationKey}.redis.host = localhost
＃ ${configurationKey}.redis.database = 0
＃ ${configurationKey}.redis.port = 6380
＃ ${configurationKey}.redis.password =
＃ ${configurationKey}.redis.timeout = 2000
＃ ${configurationKey}.redis.use -ssl = false
＃ ${configurationKey}.redis.read-from =主
```




### Redis池配置



```properties
＃ ${configurationKey}.redis.pool.enabled = false
＃ ${configurationKey}.redis.pool.max-active = 20
＃ ${configurationKey}.redis.pool.max-idle = 8
＃ ${configurationKey}.redis.pool.min-idle = 0
＃ ${configurationKey}.redis.pool.max-active = 8
＃ ${configurationKey}.redis.pool.max-wait = -1
＃ ${configurationKey}.redis.pool.num-tests-per-eviction-run = 0
＃ ${configurationKey}.redis .pool.soft-min-evictable-idle-time-millis = 0
＃ ${configurationKey}.redis.pool.min-evictable-idle-time-millis = 0
＃ ${configurationKey}.redis.pool.lifo = true
＃ ${configurationKey}。 redis.pool.fairness = false
＃ ${configurationKey}.redis.pool.test-on-create = false
＃ ${configurationKey}.redis.pool.test-on-borrow = false
＃ ${configurationKey}.redis.pool.test-on-turn = false
＃ ${configurationKey}.redis.pool.test-while-idle = false
```




### Redis前哨配置



```properties
＃ ${configurationKey}.redis.sentinel.master = mymaster
＃ ${configurationKey}.redis.sentinel.node[0]=本地主机：26377
＃ ${configurationKey}.redis.sentinel.node[1]=本地主机：26378
＃ ${configurationKey}.redis.sentinel.node[2]=本地主机：26379
```




### Redis集群配置



```properties
＃ ${configurationKey}.redis.cluster.password =
＃ ${configurationKey}.redis.cluster.max-redirects = 0
＃ ${configurationKey}.redis.cluster.nodes[0].host =
＃ ${configurationKey}.redis.cluster.nodes[0].port =
＃ ${configurationKey}redis.cluster.nodes[0].replica-of =
＃ ${configurationKey}.redis.cluster.nodes[0].id =
＃ ${configurationKey}.redis.cluster.nodes[0].name =
＃ ${configurationKey}.redis.cluster.nodes[0]。类型=主|从
```




## DDL配置

请注意，Hibernate的DDL设置的默认值为 `create-drop` ，这可能不适用于生产环境。 将值设置为 `validate` 可能更可取，但是可以使用以下任何选项：

| 类型     | 描述                 |
| ------ | ------------------ |
| `证实`   | 验证架构，但不对数据库进行任何更改。 |
| `更新`   | 更新架构。              |
| `创造`   | 创建架构，销毁先前的数据。      |
| `创建放置` | 在会话结束时删除架构。        |
| `没有任何` | 没做什么。              |


请注意，在任何模式已更改为 `的版本迁移过程中，CAS启动后，create-drop` 都会导致 对于票据之类的瞬态数据，这可能 ，但在诸如审计表的情况下，重要数据可能会丢失。 使用 `更新`，而安全 数据，被证实会导致无效的数据库状态。 `验证` 或 `无` 设置 可能是生产使用的唯一安全选项。

有关事务级别和传播行为的配置的详细信息， 请参阅 [本指南](http://docs.spring.io/spring-framework/docs/current/javadoc-api/)。



## SAML2服务提供商集成

为每个服务提供商定义的设置仅尝试自动创建 和 [SAML服务定义](../installation/Configuring-SAML2-Authentication.html#saml-services) ，仅此而已。 如果您发现 适用的设置，则最好退回本机配置策略， SAML服务提供者，这取决于您选择的服务注册表。

每个SAML服务提供商都支持以下设置：

| 姓名                  | 描述                                                                          |
| ------------------- | --------------------------------------------------------------------------- |
| `元数据`               | 服务提供商的元数据位置（即URL，路径等）                                                       |
| `名称`                | 在服务注册表中注册的服务提供商的名称。                                                         |
| `描述`                | 在服务注册表中注册的服务提供商的描述。                                                         |
| `nameIdAttribute`   | 为该服务提供者生成名称ID时使用的属性。                                                        |
| `nameIdFormat`      | 强制的NameID格式标识符（即 `urn：oasis：names：tc：SAML：1.1：nameid-format：emailAddress`）。 |
| `属性`                | 释放给服务提供商的属性，可以虚拟地进行映射和重命名。                                                  |
| `signatureLocation` | 签名位置以验证元数据。                                                                 |
| `实体编号`              | 此服务提供商允许的实体ID列表。                                                            |
| `signRespons`       | 指明是否应签署答复。 默认值为 `true`。                                                     |
| `标志断言`              | 指出是否应该签署断言。 默认值为 `false`。                                                   |


激活服务提供商的自动配置所需的唯一必需设置是元数据的存在和定义。 所有其他设置都是可选的。 

*配置密钥*，以下选项同样适用于SAML2服务提供者集成：



```properties
＃ ${configurationKey}.metadata = / etc / cas / saml / dropbox.xml
＃ ${configurationKey}.name = SP名称
＃ ${configurationKey}.description = SP集成
＃ ${configurationKey}.name-id-attribute = mail
＃ ${configurationKey}.name-id-格式=
＃ ${configurationKey}.signature-location =
＃ ${configurationKey}.attributes =
＃ ${configurationKey}.entity-ids =
＃ ${configurationKey}.sign-responses =
＃ ${configurationKey}.sign-assertions =
```




## 多因素身份验证提供程序

*配置密钥*所有可配置的多因素身份验证提供者都具有这些可用的基本属性：



```properties
＃ ${configurationKey}.rank =
＃ ${configurationKey}.id =
＃ ${configurationKey}.name =
＃ ${configurationKey}.failure-mode =未定义
```




## 多因素身份验证绕过

*配置密钥*的情况下，以下旁路选项同样适用于多因素身份验证提供者：



```properties
＃ ${configurationKey}.bypass.principal-attribute-name = bypass |跳过
＃ ${configurationKey}.bypass.principal-attribute-value = true | enabled。+

＃ ${configurationKey}.bypass.authentication-attribute-name = bypass |跳过
＃ ${configurationKey}。 bypass.authentication属性值=允许+ |。启用+。

＃ ${configurationKey}.bypass.authentication处理程序名= AcceptUsers +。
＃ ${configurationKey}.bypass.authentication-方法名= LdapAuthentication +。

＃ ${configurationKey}.bypass .credential-class-type = UsernamePassword。+

＃ ${configurationKey}.bypass.http-request-remote-address = 127. + | example。*
＃ ${configurationKey}.bypass.http-request-headers = header-X-。+ | header-Y-。+

＃ ${configurationKey}.bypass.groovy.location = file：/etc/cas/config/mfa-bypass.groovy
```


如果通过REST确定了多因素身份验证旁路，则 RESTful设置可用 [在这里](#restful-integrations) 在配置密钥 `${configurationKey}.bypass.rest`。



## Couchbase集成设置

*配置密钥*，在将CAS配置为与Couchbase集成（即票证注册表等）时，以下选项是共享的并适用：



```properties
＃ ${configurationKey}.addresses[0]= localhost
＃ ${configurationKey}.cluster-username =
＃ ${configurationKey}.cluster-password = 

＃ ${configurationKey}.bucket = testbucket    

＃ ${configurationKey}.connection-timeout = PT60S
＃ ${configurationKey}.search-timeout = PT30S
＃ ${configurationKey}.query-timeout = PT30S
＃ ${configurationKey}.view-timeout = PT30S
＃ ${configurationKey}.kv-timeout = PT30S 
＃ ${configurationKey}.max-http-connections = PT30S
＃ ${configurationKey}.idle-connection-timeout = PT30S
＃ ${configurationKey}.query-threshold = PT30S
＃ ${configurationKey}.scan-consistency = NOT_BOUNDED | REQUEST_PLUS
```




## Amazon集成设置

给定提供者的 *配置密钥* Amazon Web Service功能集成时，以下选项是共享的并适用：



```properties
＃ ${configurationKey}.credential-access-key =
＃ ${configurationKey}.credential-secret-key =

＃ ${configurationKey}.endpoint = http：// localhost：8000
＃ ${configurationKey}.region = US_WEST_2 | US_EAST_2 | EU_WEST_2 |<REGION-NAME>
＃ ${configurationKey}.local-address =
＃ ${configurationKey}.retry-mode = STANDARD | LEGACY

＃ ${configurationKey}.proxy-host =
＃ ${configurationKey}.proxy-password =
＃ ${configurationKey}.proxy-username =

＃ ${configurationKey}.read-容量= 10
＃ ${configurationKey}写入容量= 10
＃ ${configurationKey}连接超时= 5000
＃ ${configurationKey}套接字超时= 5000
＃ ${configurationKey}.use-reaper = false

＃ ${configurationKey}客户端执行超时= 10000
＃ ${configurationKey}.max-connections = 10
```




## Memcached集成设置

*配置密钥*，在将CAS配置为与memcached集成（即票证注册表等）时，以下选项是共享的并适用：



```properties
＃ ${configurationKey}.memcached.servers = localhost：11211
＃ ${configurationKey}.memcached.locator-type = ARRAY_MOD
＃ ${configurationKey}.memcached.failure-mode =重新分配
＃ ${configurationKey}.memcached.hash-algorithm = FNV1_64_HASH
＃ ${configurationKey}.memcached.protocol = TEXT
＃ ${configurationKey}.memcached.should-optimize = false
＃ ${configurationKey}.memcached.daemon = true
＃ ${configurationKey}.memcached.max-reconnect-delay = -1
＃ ${configurationKey}.memcached.use-nagle-algorithm = false
＃ ${configurationKey}.memcached.shutdown-timeout-seconds = -1
＃ ${configurationKey}.memcached.op-timeout = -1
＃ ${configurationKey}.memcached.timeout-exception-threshold = 2
＃ ${configurationKey}.memcached.max-total = 20
＃ ${configurationKey}.memcached.max-idle = 8
＃ ${configurationKey}.memcached.min-idle = 0

＃ ${configurationKey}.memcached.transcoder = KRYO | SERIAL | WHALIN | WHALINV1
＃ ${configurationKey}.memcached.transcoder-compression-threshold = 16384
＃ ${configurationKey}.memcached.kryo-自动重置= false
＃ ${configurationKey}.memcached.kryo-by-references = false
＃ ${configurationKey}.memcached.kryo-registration-required = false
```




## 密码政策设定

下列选项是共享的，当CAS被配置为与账户来源和验证策略集成应用，鉴于提供商的支持密码策略实施和检测， *配置关键*。 请注意，某些设置仅在基础帐户源是LDAP时才适用，并且仅在CAS中配置的身份验证策略能够接受和识别它们的情况下才考虑在内： 



```properties
＃ ${configurationKey}.type = GENERIC | AD | FreeIPA | EDirectory

＃ ${configurationKey}.enabled = true
＃ ${configurationKey}.policy-attributes.account-locked = javax.security.auth.login.AccountLockedException
＃ ${configurationKey}.login-failures = 5
＃ ${configurationKey}.warning-attribute-value =
＃ ${configurationKey}.warning-attribute-name =
＃ ${configurationKey}.display-warning-on-match = true
＃ ${configurationKey}.warn-all = true
＃ ${configurationKey}.warning-days = 30
＃ ${configurationKey}.account-state-handling-enabled = true

＃org.ldaptive.auth.AuthenticationResponseHandler`的实现
＃ ${configurationKey}.custom-policy-class = com.example.MyAuthenticationResponseHandler

＃ ${configurationKey}.strategy = DEFAULT | GROOVY | REJECT_RESULT_CODE
＃ ${configurationKey}.groovy.location = file：/etc/cas/config/password-policy.groovy
```




#### 密码政策策略

密码策略策略类型概述如下。 该策略评估从LDAP等接收到的身份验证响应，并允许对其进行预先审查，以进一步检查帐户状态，消息和警告是否符合进一步调查的条件。

| 选项                   | 描述                                                                                        |
| -------------------- | ----------------------------------------------------------------------------------------- |
| `默认`                 | 照原样接受身份验证响应，并处理帐户状态（如果有）。                                                                 |
| `格罗维`                | 作为Groovy脚本的一部分动态检查身份验证响应。 处理帐户状态更改和警告的责任完全委托给脚本。                                          |
| `REJECT_RESULT_CODE` | `DEFAULT` 的扩展，其中仅当配置中未拒绝认证响应的结果代码时，才处理帐户状态。 默认情况下， `INVALID_CREDENTIALS（49）` 阻止CAS处理帐户状态。 |


如果要将密码策略策略移交给Groovy脚本，则脚本的概述可能如下：



```groovy
导入java.util。*
导入org.ldaptive.auth。*
导入org.apereo.cas。*
导入org.apereo.cas.authentication。*
导入org.apereo.cas.authentication.support。*

def清单<MessageDescriptor> run（final Object ... args）{
    def响应= args[0]
    def配置= args[1];
    def logger =参数[2]
    def applicationContext = args[3]

    logger.info（“通过$ {configuration.getAccountStateHandler（）}处理密码策略[{}]”，响应）

    def accountStateHandler = configuration.getAccountStateHandler（）
    return accountStateHandler .handle（响应，配置）
}
```


传递的参数如下：

| 范围    | 描述                                                    |
| ----- | ----------------------------------------------------- |
| `回复`  | `org.ldaptive.auth.AuthenticationResponse`的LDAP身份验证响应 |
| `配置`  | 带有定义的帐户状态处理程序的LDAP密码策略配置。                             |
| `记录器` | 负责发布日志消息的对象，例如 `logger.info（...）`。                    |




## 电子邮件通知

要了解更多有关这个话题， [请参阅本指南](../notifications/Sending-Email-Configuration.html)。

*配置密钥*，在将CAS配置为发送电子邮件通知时，以下选项是共享的并适用：



```properties
＃ ${configurationKey}.mail.from =
＃ ${configurationKey}.mail.text =
＃ ${configurationKey}.mail.subject =
＃ ${configurationKey}.mail.cc =
＃ ${configurationKey}.mail.bcc =
＃ ${configurationKey}.mail.reply-to =
＃ ${configurationKey}.mail.validate-addresses = false
＃ ${configurationKey}.mail.html = false

＃ ${configurationKey}.mail.attribute-name = mail
```


还可能需要定义以下设置来描述邮件服务器设置：



```properties
＃spring.mail.host =
＃spring.mail.port =
＃spring.mail.username =
＃spring.mail.password =
＃spring.mail.properties.mail.smtp.auth = true
＃spring。 mail.properties.mail.smtp.starttls.enable = true
```




## 短信通知

*配置密钥*，在将CAS配置为发送SMS通知时，以下选项是共享的并适用：



```properties
＃ ${configurationKey}.sms.from =
＃ ${configurationKey}.sms.text =
＃ ${configurationKey}.sms.attribute-name =电话
```


您还需要确保定义了能够发送SMS消息的提供程序。 要了解有关该 主题的 [本指南](../notifications/SMS-Messaging-Configuration.html)。



## Webflow自动配置

Webflow的控制方面，与Webflow状态，过渡和执行顺序的自动配置有关。



```properties
＃ ${configurationKey}.order =
```




## 委派的身份验证设置

以下选项是共享的，申请时CAS被配置为委托认证 到外部提供商例如Yahoo，给出的提供者的 *配置键*：



```properties
＃ ${configurationKey}.id =
＃ ${configurationKey}.secret =
＃ ${configurationKey}.client-name =我的提供者
＃ ${configurationKey}.auto-redirect = false
＃ ${configurationKey}.css-class =
＃ ${configurationKey}.principal-attribute-id =
＃ ${configurationKey}.enabled = true
＃ ${configurationKey}.callback-url-type = PATH_PARAMETER | QUERY_PARAMETER | NONE

```


回调URL解析支持以下类型：

| 类型                | 描述                            |
| ----------------- | ----------------------------- |
| `PATH_PARAMETER`  | 构造回调URL时，客户端名称将作为路径参数添加到url中。 |
| `QUERY_PARAMETER` | 构造回调URL时，客户端名称将作为查询参数添加到URL中。 |
| `没有任何`            | 没有客户端名称添加到该URL。               |




### 委托身份验证OpenID Connect设置

在将CAS配置为将身份验证 委派给外部OpenID Connect提供程序（例如Azure AD）的情况下，给定了提供程序的 *配置密钥*，则共享和应用以下选项：



```properties
＃ ${configurationKey}.discovery-uri =
＃ ${configurationKey}.logout-url =
＃ ${configurationKey}.max-clock-skew =
＃ ${configurationKey}.scope =
＃ ${configurationKey}.use-nonce = false
＃ ${configurationKey}.disable-nonce = false
＃ ${configurationKey}.preferred-jws-algorithm =
＃ ${configurationKey}.response-mode =
＃ ${configurationKey}.response-type =
＃ ${configurationKey}.custom-params.param1 = value1
＃ ${configurationKey}.read-timeout = PT5S
＃ ${configurationKey}-timeout = PT5S
＃ ${configurationKey}.expire-session-with-token = false
＃ ${configurationKey}.token-expiration-advance = 0
```




## LDAP连接设置

下列选项适用于给定的供应商的LDAP服务器（即验证，属性分辨率等）集成功能 *配置重点*：



```properties
＃ ${configurationKey}.ldap-url = ldaps：//ldap1.example.edu ldaps：//ldap2.example.edu
＃ ${configurationKey}.bind-dn = cn =-目录-manager，dc = example，dc = org
＃ ${configurationKey}.bind-credential =密码

＃ ${configurationKey}.pool-passivator = NONE | BIND
＃ ${configurationKey}.connection-strategy =
＃ ${configurationKey}.connect-timeout = PT5S
＃ ${configurationKey}.trust-certificates =
＃ ${configurationKey}.trust-store =
＃ ${configurationKey}.trust-store-password =
＃ ${configurationKey}.trust-store-type = JKS | JCEKS | PKCS12
＃ ${configurationKey}.keystore =
＃ ${configurationKey}.keystore-password =
＃ ${configurationKey}.keystore-type = JKS | JCEKS | PKCS12
＃ ${configurationKey}.disable-pooling = false
＃ ${configurationKey}.min-pool-size = 3
＃ ${configurationKey}.max-pool-size = 10
＃ ${configurationKey}.validate-on-checkout = true
＃ ${configurationKey}.validate-定期= true
＃ ${configurationKey}.validate-period = PT5M
＃ ${configurationKey}.validate-timeout = PT5S
＃ ${configurationKey}.fail-fast = true
＃ ${configurationKey}.idle-time = PT10M
＃ ${configurationKey}.prune-period = PT2H
＃ ${configurationKey}.block-wait-time = PT3S

＃ ${configurationKey}.use-start-tls = false
＃ ${configurationKey}.response-timeout = PT5S
＃ ${configurationKey}.allow-multiple-dns = false
＃ ${configurationKey}.allow-multiple-entries = false
＃ ${configurationKey}.follow-referrals = false
＃ ${configurationKey}.binary-attributes = objectGUID，someOtherAttribute
＃ ${configurationKey}.name =
```




### 连接初始化

可以使用以下参数初始化注入到LDAP连接池中的LDAP连接配置：

| 行为                                  | 描述                       |
| ----------------------------------- | ------------------------ |
| `指定binddn`/`bindCredential` 提供      | 初始化连接时，请使用提供的凭据进行绑定。     |
| `指定binddn`/`bindCredential` 设定为 `*` | 使用快速绑定策略来初始化池。           |
| `指定binddn`/`bindCredential` 设置为空白   | 跳过连接初始化；匿名执行操作。          |
| 提供了SASL机制                           | 初始化连接时，请使用给定的SASL机制进行绑定。 |




### 钝化剂

将对象重新签入LDAP连接池时，可以使用以下选项来钝化对象：

| 类型     | 描述                                                |
| ------ | ------------------------------------------------- |
| `没有任何` | 没有钝化发生。                                           |
| `绑定`   | 通过对连接执行绑定操作来钝化连接的默认行为。 建立与LDAP的连接时，此选项需要绑定凭证的可用性。 |




#### 为什么要使用钝化剂？

`DIRECT` 或 `AUTHENTICATED` 类型进行身份验证并且LDAP被锁定以不允许匿名绑定/搜索时，您可能会收到意外的LDAP故障。 如果池中与给定的LDAP连接进行的第二次尝试均与失败的登录尝试位于同一连接上，则该尝试将失败，并且常规连接验证器也会类似地失败。 当连接返回到池时，它仍可能包含上一次尝试的主体和凭据。 在下一次尝试使用该连接的绑定之前，验证器将尝试再次验证连接，但是失败，因为它不再尝试使用配置的绑定凭据，而是尝试使用上一步中使用的任何用户DN。 给定验证失败，连接将关闭，CAS默认情况下将拒绝访问。 钝化器尝试使用配置的绑定凭证重新连接到LDAP，从而有效地将连接重置为每个绑定请求后的连接。

*<Operation exception encountered, reopening connection>* 消息类型的日志中看到错误，则通常表明这是由CAS建立和创建的连接池的验证超时大于LDAP服务器中配置的超时，或更可能是，在LDAP服务器前面的负载均衡器中。 您可以调整LDAP服务器会话的连接超时，也可以教导CAS使用等于或小于LDAP服务器会话超时的有效期。



### 连接策略

如果提供多个URL作为LDAP URL，则这将描述如何处理每个URL。

| 提供者              | 描述                                      |
| ---------------- | --------------------------------------- |
| `ACTIVE_PASSIVE` | 除非失败，第一个LDAP将用于每个请求，然后将使用下一个。           |
| `ROUND_ROBIN`    | 对于每个新连接，将使用列表中的下一个URL。                  |
| `随机的`            | 对于每个新连接，将选择一个随机的LDAP URL。               |
| `DNS_SRV`        | 将使用基于已配置/给定LDAP URL的DNS SRV记录的LDAP URL。 |




### LDAP SASL机制



```properties
＃ ${configurationKey}.sasl-mechanism = GSSAPI | DIGEST_MD5 | CRAM_MD5 | EXTERNAL
＃ ${configurationKey}.sasl-realm = EXAMPLE.COM
＃ ${configurationKey}.sasl-authorization-id =
＃ ${configurationKey}.sasl-mutual-auth =
＃ ${configurationKey}.sasl保护质量=
＃ ${configurationKey}.sasl-security-strength =

```




### LDAP连接验证器

以下LDAP验证器可用于测试连接运行状况：

| 类型     | 描述                                      |
| ------ | --------------------------------------- |
| `没有任何` | 没有验证发生。                                 |
| `搜索`   | 通过执行搜索操作来验证连接是否健康。 如果搜索结果大小大于零，则认为验证成功。 |
| `比较`   | 通过执行比较操作来验证连接是否健康。                      |




```properties
＃ ${configurationKey}.validator.type = NONE | SEARCH | COMPARE
＃ ${configurationKey}.validator.base-dn =
＃ ${configurationKey}.validator.search-filter =（object-class = *）
＃ ${configurationKey}.validator.scope = OBJECT | ONELEVEL | SUBTREE
＃ ${configurationKey}.validator.attribute-name = objectClass
＃ ${configurationKey}.validator.attribute-value = top
＃ ${configurationKey}.validator.dn =

```




### LDAP SSL主机名验证

以下LDAP验证器可用于测试连接运行状况：

| 类型   | 描述                              |
| ---- | ------------------------------- |
| `默认` | 默认选项，用于启用和强制进行LDAP SSL配置的主机名验证。 |
| `任何` | 跳过并忽略LDAP SSL配置的主机名验证。          |




```properties
＃${configurationKey}.hostname-verifier = DEFAULT | ANY
```




### LDAP SSL信任管理器

信任管理器负责管理在进行LDAP信任决策时使用的信任材料 并负责确定是否应接受对等方提供的凭据。

| 类型   | 描述                  |
| ---- | ------------------- |
| `默认` | 启用并强制使用默认的JVM信任管理器。 |
| `任何` | 信任任何客户端或服务器。        |




```properties
＃${configurationKey}.trust-manager = DEFAULT | ANY
```




### LDAP类型

CAS中的许多组件/功能使您可以 `类型` ，特别是在CAS需要更新LDAP中的属性等（例如，同意，密码管理等）的情况下。 相关设置为：



```properties
＃${configurationKey}.type = AD | FreeIPA | EDirectory |通用
```


支持以下类型：

| 类型        | 描述                       |
| --------- | ------------------------ |
| `广告`      | 活动目录。                    |
| `FreeIPA` | FreeIPA目录服务器。            |
| `电子目录`    | NetIQ eDirectory。        |
| `通用的`     | 所有其他目录服务器（例如，OpenLDAP等）。 |




### LDAP验证/搜索设置

除了上述常见的LDAP连接设置外，在某些情况下，CAS仅需要 身份验证即可获取帐户或属性集，或者通常执行搜索查询。 给定提供者的 *配置密钥*，以下选项适用：

**注：** 未指定足够的性能如 `型`， `LDAPURL`等就会停用LDAP完全静默。



```properties
＃ ${configurationKey}.type = AD |已授权|直接|匿名|

＃ ${configurationKey}.base-dn = dc = example，dc = org
＃ ${configurationKey}.subtree-search = true
＃ ${configurationKey}.search-filter = cn ={user}
＃ ${configurationKey}.page-size = 0

＃ ${configurationKey}.enhance-with-entry-resolver = true
＃ ${configurationKey}.deref-aliases = Never | SEARCHING | FINDING | ALWAYS
＃ ${configurationKey}.dn-format = uid =%s，ou = people， dc = example，dc = org
＃ ${configurationKey}.principal-attribute-password =密码

```


支持以下身份验证类型：

| 类型    | 描述                                                                                                                                                                         |
| ----- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `广告`  | Active Directory-用户通常使用DN格式 `sAMAccountName`                                                                                                                               |
| `认证的` | 管理员绑定/搜索身份验证类型。 如果 `principalAttributePassword` 是空的，则用户简单绑定完成验证凭据。 否则，给定的属性与给定相比 `principalAttributePassword` 使用 `SHA` 的它加密值。                                              |
| `直接的` | 从格式字符串计算用户DN并执行简单绑定。 当不需要搜索来计算绑定操作所需的DN时，这是相关的。 当所有用户都位于目录的单个分支下时，此选项很有用，例如 `ou = Users，dc = example，dc = org`，或者CAS登录表单上提供的用户名是DN的一部分，例如 `uid =%s，ou =用户，dc =示例，dc = org` |
| `匿名的` | 除了 `bindDn` 和 `bindCredential` `AUTHENTICATED` 类似的语义来初始化连接。 如果 `principalAttributePassword` 是空的，则用户简单绑定完成验证凭据。 否则，给定的属性与给定相比 `principalAttributePassword` 使用 `SHA` 的它加密值。  |




### LDAP搜索条目处理程序



```properties
＃ ${configurationKey}.search-entry-handlers[0].type =

＃ ${configurationKey}.search-entry-handlers[0].case-change.dn-case-change = NONE | LOWER | UPPER
＃ ${configurationKey}.search-entry-handlers[0]-change.attribute-name-case-change = NONE | LOWER | UPPER
＃ ${configurationKey}项处理程序[0].case-change.attribute-value-case-change = NONE | LOWER | UPPER
＃ ${configurationKey}.search-条目处理程序[0].case-change.attribute-names =

＃ ${configurationKey}.search-entry-handlers[0].dn-attribute.dn-attribute-name = entryDN
＃ ${configurationKey}.search-entry-handlers[0].dn-attribute。 add-if-exists = false

＃ ${configurationKey}.search-entry-handlers[0].primary-group-id.group-filter =（&（object-class = group）（object-sid ={0}））
＃ ${configurationKey}.search条目处理程序[0].primary-group-id.base-dn =

＃ ${configurationKey}条目处理程序[0].merge-attribute.merge-attribute-name =
＃ ${configurationKey}条目处理程序[0].merge- attribute.attribute-names =

＃ ${configurationKey}.search-entry-handlers[0].recursive.search-attribute =
＃ ${configurationKey}.search-entry-handlers[0].recursive.merge-attributes =

```


支持以下类型：

| 类型                   | 描述                                          |
| -------------------- | ------------------------------------------- |
| `CASE_CHANGE`        | 提供了修改搜索条目DN，属性名称和属性值的大小写的能力。                |
| `DN_ATTRIBUTE_ENTRY` | 将条目DN作为属性添加到结果集中。 提供RFC 5020的客户端实现。         |
| `合并`                 | 将一个或多个属性的值合并为一个属性。                          |
| `OBJECT_GUID`        | 处理 `objectGUID` 属性的获取和转换。                   |
| `OBJECT_SID`         | 处理 `objectSid` 属性的获取和转换。                    |
| `PRIMARY_GROUP`      | 构造主要组SID，然后搜索该组并将其DN放入原始搜索条目的'memberOf'属性中。 |
| `RANGE_ENTRY`        | 通过执行其他搜索，重写从Active Directory返回的属性以包括所有值。    |
| `RECURSIVE_ENTRY`    | 这将基于提供的属性进行递归搜索，并将这些结果合并到原始条目中。             |




### LDAP多个基本DN

在某些情况下，单个LDAP树的不同部分可能被视为base-dns。 而不是重复 对于每个单独的基本-DN LDAP配置块，每个条目可以被指定并使用特殊的分界符接合在一起。 使用所有base-dn和DN解析程序的组合按定义的顺序检索用户DN。 如果找到 则DN解析将失败。 否则，返回找到的第一个DN。



```properties
＃ ${configurationKey}.base-dn = subtreeA，dc = example，dc = net | subtreeC，dc = example，dc = net
```
