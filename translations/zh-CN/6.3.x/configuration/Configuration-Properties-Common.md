---
layout: 违约
title: 中科院普通物业概览
category: 配置
---

# 中科院公共属性

本文档描述了适用于 CAS 模块和功能的一些建议和配置选项，这些建议和配置选项在选择 CAS 模块和功能中很常见。 要查看 CAS 属性的完整列表，请 [查看本指南](Configuration-Properties.html)。

## 什么是 `${configurationKey}`？

许多 CAS *子* 设置是常见的，适用于许多模块和功能。 例如，在处理数据库身份验证时，有许多与数据库相关的模块拥有定义数据库驱动程序的单个设置。 这些设置通常被定义为 `cas.authn.功能1.数据库驱动程序=xyz` 和 `cas.authn.功能2.数据库驱动程序=abc`。 此页面没有重复共享的和常见的 `数据库驱动程序` 设置，而是尝试只收集跨功能和模块的常见 CAS 设置，同时参考路径 `${configurationKey}`下的特定功能。 因此， `功能 1` 或 `功能 2` 文档都可能允许在 `${configurationKey}下查找与数据库相关的常见设置（如 <code>数据库驱动程序`）。</code> .数据库驱动程序，其中 `${configurationKey}` 将 `cas.authn.功能1` 或 `cas.authn.功能2` 取决于手头的功能。 每个要从常见设置块中继承的功能的注释和文档应始终宣传 `${configurationKey}`的适当值。

## 命名公约

- 由 CAS 平台直接控制的设置和属性始终从前缀 `cas`开始。 所有其他设置都 控制，并通过其他基础框架提供给 CAS，并且可能有自己的语法和语法。 **小心** 与区别。

- 未识别的属性被 CAS 和/或 CAS 所依赖的框架拒绝。 这意味着，如果您以某种方式拼错了属性定义或未能遵守点符号语法等，则 CAS 完全拒绝您的设置 ，并且可能永远不会按照您的意图激活它控制的功能。

## 索引设置

能够接受多个值的 CAS 设置通常用索引进行记录，例如 `cas.some.设置[0]=价值`。 `[0]` 的索引将由采用者增量，以允许不同的多个配置块：

```properties
#cas.一些.设置[0]=价值1
#cas.一些。设置[1]=值2
```

## 信任但验证

如果您不确定给定 CAS 设置的含义，请 **不要毫不犹豫地打开它** 。 查看代码库或更好， [](/cas/Mailing-Lists.html) 提问以澄清预期的行为。

<div class="alert alert-info"><strong>保持简单</strong><p>如果您不知道或无法判断设置是什么，则不需要它。</p></div>

## 测量时间单位

处理时间单位的所有 CAS 设置（除非另有注明） 应支持持续时间语法，以充分明确测量单位：

```bash
"PT20S" - 解析为"20 秒"
"PT15M" - 解析为"15 分钟"
"PT10H" - 解析为"10 小时"
"P2D" - 解析为"2 天"
"P2DT3H4M" - 解析为"2 天" 3小时4分钟"
```

原生数字语法仍然得到支持，但您必须参考每个情况下 文档才能了解测量的确切单位。

## 工作安排

一些 CAS 组件能够安排背景作业来清理令牌、删除记录等。 调度器的行为可以使用以下设置进行控制：

```properties
# ${configurationKey}.计划.计划.启动延迟=PT10S
# ${configurationKey}.计划.计划.重复间隔=PT60S
# ${configurationKey}.计划。
```

## 身份验证限制

CAS 中的某些功能（如 [OAuth](../installation/OAuth-OpenId-Authentication.html) 或 REST API</a>，除了在登录流和身份验证尝试中应用的更 通用身份验证限制功能外，还允许您限制对特定端点的请求。</p> 

为了全面实现此功能，预计 [身份验证限制](../installation/Configuring-Authentication-Throttling.html) 已打开。



## 身份验证凭据选择

允许许多身份验证处理程序确定他们是否可以在提供的凭据 上操作，因此借给自己在身份验证处理程序选择阶段进行尝试和测试。 凭证标准可能是以下选项之一：

- 根据凭据标识符测试的常规表达模式
- 您自己设计的完全合格的类名称，看起来类似于以下示例：



```java
导入爪哇.利用。功能。预示：
进口组织. apereo. cas. 认证. 认证;

公共类谓词example实施谓词<Credential> ^
    @Override
    公共布尔测试（最终凭据证书）{
        //检查凭据并返回真/假
    }
}
```


- 通往外部 Groovy 脚本的路径，该脚本看起来类似于以下示例：



```groovy
导入组织.apereo.cas.认证.证书
导入 java.util.功能.谓词

类谓词执行谓词<Credential> •
    @Override
    布尔测试（最终证书凭据）{
        //测试和退货结果
    }
}
```




## 密码编码

CAS的某些方面，如 密码编码的身份验证处理支持配置。 大多数选项基于春季安全 [支持密码编码](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)。

鉴于组件的 *配置密钥*，CAS 中有关密码编码支持的以下选项同样适用于多个 CAS 组件（身份验证处理程序等）：



```properties
# ${configurationKey}.密码编码器.类型=无|默认|标准|布里普特|克里普特|PBKDF2
# ${configurationKey}.密码编码器.字符编码]
# ${configurationKey}.密码编码器.编码算法]
# ${configurationKey}.密码编码器.秘密]
# ${configurationKey}.密码编码器.强度=16
```


支持以下选项：

| 类型                              | 描述                                                                                                                                                                                                                                     |
| ------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `没有`                            | 不进行密码编码（即纯文本）。                                                                                                                                                                                                                         |
| `违约`                            | 使用中科院 `默认密码编码器` 。 对于通过 `字符编码` 和 `编码算法的消息消化算法`。                                                                                                                                                                                         |
| `布里普特`                          | 使用 `BCrypt密码编码器` 基于提供 `强度` 和可选 `秘密`。                                                                                                                                                                                                   |
| `克里普特`                          | 使用 `斯克里普特密码编码器`。                                                                                                                                                                                                                       |
| `PBKDF2`                        | 使用 `Pbkdf2密码编码器` 基于提供 `强度` 和可选 `秘密`。                                                                                                                                                                                                   |
| `标准`                            | 使用基于所提供 `秘密` 的 `标准密码编码器` 。                                                                                                                                                                                                             |
| `斯沙`                            | 使用 `的LdapSha密码编码器` 支持Ldap SHA和SSHA（盐沙）。 这些值是基础-64编码的，并且将标签 `{SHA}` （或 `{SSHA}`）预先贴在编码的哈希上。                                                                                                                                             |
| `GLIBC_CRYPT`                   | 使用基于 [`编码算法`](https://commons.apache.org/proper/commons-codec/archives/1.10/apidocs/org/apache/commons/codec/digest/Crypt.html)、 `强度` 和可选 `秘密`的GlibcCryptPassword编码器</code> `。</td>
</tr>
<tr>
  <td><code>组织。示例。我的编码器` | `密码编码器的实现` 您自己选择的。 |
| `file:///path/to/script.groovy` | 前往负责处理密码编码操作的 Groovy 脚本的路径。                                                                                                                                                                                                            |


如果您计划设计自己的密码编码器或编写脚本来设计密码编码器或编写脚本， 您可能还需要确保覆盖物能够访问 `组织.springframework.security：春季安全核心` 。 确保将工件标记为 `` 或 `编译仅` 以避免冲突。

如果您需要设计自己的密码编码方案，其中类型被指定为完全合格的 Java 类名称，则该类的结构将 类似于以下内容：



```java
包组织.示例.cas;

进口组织.弹簧框架.安全.加密.编解码*;
导入组织.弹簧框架.安全.加密.密码。*;

公共类MyEncoder扩展抽象密码编码器{
    @Override
    受保护的字节[]编码（字符字节原始密码，字节[]盐）{
        返回。。。
    •
}
```


如果您需要设计自己的密码编码方案，其中类型被指定为通往 Groovy 脚本的路径，则脚本的结构将类似于以下 ：



```groovy
导入java.利用。*

def拜特[]运行（最终对象。。。args）{
    def原始密码=args[0]
    def生成的alt=args[1]
    d记录器=args[2]
    d级联应用程序转换=args[3]

    记录器.debug（"编码密码。。。"）
    返回。。。
=

德布尔比赛（最终对象。。。args）{
    定义原始密码=args[0]
    定义编码密码=args[1]
    定义记录器=args[2]
    定义应用程序配置配置=args[3]

   记录器.debug（"匹配与否？"）;
   返回。。。
```




## 身份验证主体转换

通常处理用户名密码凭据 的身份验证处理程序可以在执行身份验证序列之前配置以转换用户 ID。 可使用以下选项：

| 类型   | 描述         |
| ---- | ---------- |
| `没有` | 不要应用任何转换。  |
| `大写` | 将用户名转换为大写。 |
| `小写` | 将用户名转换为小写。 |


作为主要转换的一部分，身份验证处理程序也可以提供一条通往 Groovy 脚本的路径，以转换所提供的用户名。 脚本的大纲可以采取以下形式：



```groovy
def字符串运行（最终对象。。。args）{
    def提供使用者=args[0]
    d记录器=args[1]
    返回提供使用者。concat（"东西"）
}
```


鉴于组件的 *配置密钥*，与 CAS 主要转换支持相关的以下选项同样适用于多个 CAS 组件（身份验证处理程序等）：



```properties
# ${configurationKey}.principal-transformation.pattern=(.+)@example.org
# ${configurationKey}. 校长转换. groovy.位置= 文件//等/cas/配置/委托. 凹槽
# ${configurationKey}.校长转换. 后缀=
# ${configurationKey}.委托转换. 案例转换= 无|上一个|下一个
# ${configurationKey}。校长转换.前缀=
```




## 饼干属性

以下常见属性在 CAS 中配置曲奇生成器支持。



```properties
# ${configurationKey}.path]
# ${configurationKey}.max岁 = -1
# ${configurationKey}.domain]
# ${configurationKey}. name]
# ${configurationKey}. secure = 真实
| # ${configurationKey}. http 仅限. 真实
= ${configurationKey}. 同一站点策略 = 无|拉克斯|限制
# ${configurationKey}. 评论] CAS 曲奇
```




## 卡桑德拉配置

当 CAS 尝试建立连接、运行查询等时，与卡桑德拉相关的控制属性 。



```properties
# ${configurationKey}.密钥空间]
= ${configurationKey}.联系点= 本地总部：9042
# ${configurationKey}.本地-直流=
= ${configurationKey}。一致性级别=任何|一|二|三|夸鲁姆|LOCAL_QUORUM|全部|EACH_QUORUM|LOCAL_SERIAL|串行|LOCAL_ONE
# ${configurationKey}.串行一致性级别=任何|一|二|三|夸鲁姆|LOCAL_QUORUM|全部|EACH_QUORUM|LOCAL_SERIAL|串行|LOCAL_ONE
# ${configurationKey}.超时=PT5S
```




## 冬眠 & Jdbc

当 CAS 尝试使用和利用数据库资源、 连接和查询时，控制与冬眠相关的全球属性 。



```properties
#cas. jdbc. 显示 - sql] 真正的
# 卡斯. jdbc. 根 - ddl] 真正的
# 卡斯. jdbc. 案件麻木不仁 = 假
# 卡斯. jdbc. 物理表名称。{table-name}={new-table-name}
```




### 数据库设置

鉴于组件的 *配置密钥*，与中科院 JPA/JDBC 支持相关的以下选项同样适用于多个 CAS 组件（票证注册等）：



```properties
# ${configurationKey}.user=sa
# ${configurationKey}.密码]
# ${configurationKey}.驱动类=org.hsqldb.jdbcdriver
# ${configurationKey}.url=jdbc：hsqldb：mem：卡斯-hsql-数据库
# ${configurationKey}.方言\org.hiber

# ${configurationKey}.故障快速超时 = 1
# ${configurationKey}.隔离级别名称=ISOLATION_READ_COMMITTED 
= ${configurationKey}.健康查询]
# ${configurationKey}.隔离内部查询=错误
= ${configurationKey}.泄漏阈值=10
# ${configurationKey}. 传播行为名称 = PROPAGATION_REQUIRED
= ${configurationKey}. batchsize = 1
= ${configurationKey}.默认目录]
# ${configurationKey}. 默认 -
# ${configurationKey}. ddl - 自动创建 - 下降
# ${configurationKey}. 物理命名战略类名称] 组织. apereo. cas. 休眠. 卡希伯内特物理命名战略

# ${configurationKey}. 自动承诺 = 虚假
# ${configurationKey}. 空闲超时 = 50

# ${configurationKey}. 数据源名称 ]
# ${configurationKey}. 数据源 - roxy = 假

# 冬眠特异性属性（即 "hibernate.globally_quoted_identifiers"）
# ${configurationKey}. 属性. 属性. 属性名称 = 财产价值

# ${configurationKey}. pool. 暂停 = 假
# ${configurationKey}. pool. mi 大小 = 6
 ${configurationKey}.pool.max大小=18
= ${configurationKey}.pool.max-wait=2000
# ${configurationKey}.pool.超时-毫秒=1000
```




### 基于集装箱的 JDBC 连接

如果您计划使用与CAS的集装箱管理的JDBC连接（即 JPA 票证/服务注册处等） 然后可以在任何需要数据库 连接的配置项目上设置 `数据源名称` 属性。 在使用配置的容器数据源时，将不使用许多与池相关的参数。 如果指定了 `数据源名称` 但 JNDI 查找失败，则将使用配置的 （或默认）CAS 池参数创建数据源。

如果您在尝试使用容器数据源时遇到分级加载错误，则可以尝试 将 `数据源代理` 设置设置为真实，从而将容器数据源包装成 一种可能解决错误的方法。

`数据源名称` 属性可以是数据源的 JNDI 名称，也可以是前缀 `java：/comp/env/`的资源名称。 如果它是一个资源名称，那么你需要一个条目在 `网络.xml` ，你可以添加到您的 CAS覆盖。 它应该包含这样的条目：



```xml
<resource-ref>
    <res-ref-name>jdbc / 卡斯数据来源</res-ref-name>
    <res-type>贾瓦克斯.sql. 数据来源</res-type>
    <res-auth>集装箱</res-auth>
</resource-ref>
```


在 Apache Tomcat 中，容器数据源可以在 `上下文中这样定义.xml`：



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


在码头，一个游泳池可以放在JNDI与 `码头.xml` 或 `码头.xml` 这样的文件：



```xml
<?xml version="1.0"?>
<!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN" "http://www.eclipse.org/jetty/configure_9_3.dtd">

<Configure class="org.eclipse.jetty.webapp.WebAppContext">
<New id="datasource.cas" class="org.eclipse.jetty.plus.jndi.Resource">
    <Arg></Arg> <!-- empty scope arg is JVM scope -->
    <Arg>jdbc/casDataSource</Arg> <!-- name that matches resource in web.xml-->
    <Arg>
        <New class="org.apache.commons.dbcp.BasicDataSource">
            <Set name="driverClassName">oracle.jdbc.OracleDriver</Set>
            <Set name="url">jdbc:oracle:thin:@//casdb.example.com:1521/ntrs"</Set>
            <Set name="username">cas</Set>
            <Set name="password">xyz</Set>
            <Set name="validationQuery">select dummy from dual</Set>
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




## 签署 & 加密

CAS 中的许多组件接受签名和加密密钥。 在大多数情况下，如果不提供密钥，CAS 将 自动生成它们。 如果您希望手动和事先创建签名和加密密钥，则以下说明适用。

请注意，如果您被要求为密钥创建一定大小的 [JWK](https://tools.ietf.org/html/rfc7517) ，则要使用以下命令集 生成令牌：



```bash
wget https://raw.githubusercontent.com/apereo/cas/master/etc/jwk-gen.jar
爪哇-贾尔-杰克根.jar-t八分之一-s [size]
```


结果类似于：



```json
{
  "kty"："八"，
  "孩子"："。。。"，
  "k"："。。。"
}
```


`k` 的生成值需要分配给相关的 CAS 设置。 请注意，通过上述算法 生成的密钥由 CAS 使用高级加密标准 （`AES`） 算法进行处理，该算法是美国建立的电子数据加密的 规范。 国家标准与技术研究所。



### 设置

鉴于组件的 *配置密钥*，以下加密选项同样适用于相关的 CAS 组件（票证注册等）：



```properties
# ${configurationKey}.crypto.签名.key]
# ${configurationKey}.加密. 签名.key大小=

# ${configurationKey}.crypto.加密.key
# ${configurationKey}.crypt ${configurationKey}.crypto.key.alg=AES


 ${configurationKey}.加密.启用=假   

 ${configurationKey}.加密.战略类型ENCRYPT_AND_SIGN|SIGN_AND_ENCRYPT
```


提供以下密码策略类型：

| 类型                 | 描述             |
| ------------------ | -------------- |
| `ENCRYPT_AND_SIGN` | 默认策略;加密值，然后签名。 |
| `SIGN_AND_ENCRYPT` | 签署值，然后加密。      |




### RSA 密钥

某些功能，如能够产生 [JWT 作为 CAS 票证](../installation/Configure-ServiceTicket-JWT.html) ，可能允许您使用 `RSA` 算法与公共/私人密钥对接进行签名和加密。 如果 CAS 编码有效载荷的消费者是局外人，并且客户端应用程序不需要直接和明显地访问签名秘密，并且可能只获得与公共密钥相对的半真半假，以验证有效载荷的真实性和解码，这种行为通常可能证明是有用的。 在 CAS 本身既是有效载荷的生产商又是有效载荷的消费者的情况下，此特定选项没有什么意义。

<div class="alert alert-info"><strong>记得</strong><p>签名和加密选项并非相互排斥。 虽然这相当荒谬，但CAS完全有可能使用 <code>AES</code> 密钥进行签名，并 <code>RSA</code> 密钥进行加密，反之亦然。</p></div>

为了启用 RSA 功能以签署有效负载，您需要通过以下示例命令生成私钥/公共密钥对：



```bash
打开sl genrsa-出私人.key 2048
打开sl rsa-酒吧-在私人.key-出公共.key-通知PEM-超越DER
```


私钥路径（即 `file:///path/to/private.key`）需要配置为相关功能的CAS属性的签名密钥。 公共密钥需要与有效载荷的客户应用程序和消费者共享，以验证有效载荷签名。



```properties
#cas.xyz.加密.签名.key文件：//等/卡斯/配置/私人.key
```

<div class="alert alert-info"><strong>密钥大小</strong><p>请记住，RSA 密钥大小至少需要 <code>2048</code> 及以上。 CAS 不接受较小的键大小，并会导致运行时间错误。 明智地选择。</p></div>

为了启用用于加密有效载荷的 RSA 功能，您需要基本上执行上述操作的反向操作。 客户端应用程序将为您提供一个公钥，用于加密有效载荷及其路径（即 `file:///path/to/public.key`）需要为CAS属性中的加密密钥进行相关功能的配置。 提交有效载荷后，客户端应使用自己的私钥解码有效载荷并拆开有效载荷。



```properties
#cas.xyz.加密.key文件//等/卡斯/配置/公共.key
```




## 人员目录主要决议

鉴于组件的 *配置密钥，在 CAS 尝试解决和构建经过验证的本金时，与"人员目录"支持相关的以下选项*：



```properties
# ${configurationKey}.主要属性=uid，帐户名等
= ${configurationKey}.返回-空=假
= ${configurationKey}.委托分辨率-故障-致命=虚假
# ${configurationKey}.使用-现有-主-id=虚假
=启用 ${configurationKey}.属性分辨率=真实
= ${configurationKey}.主动属性存储库-id=存根存储库等
```




## 吉特配置

鉴于组件的 *配置键，CAS 在尝试连接和拉/推更改时，与 Git 集成支持相关的以下选项*：



```properties
• ${configurationKey}. git. 存储库 - url= .com/存储库
# ${configurationKey}.git. 分支到克隆大师
# ${configurationKey}.git.主动分支/主
# ${configurationKey}.git.sign-提交] 假
= ${configurationKey}.git.用户名]
= ${configurationKey}.git.密码]
= ${configurationKey}.git.克隆-目录. 位置
# ${configurationKey}.git.推送更改 = 虚假
# ${configurationKey}.git.私人钥匙密码短语]
= ${configurationKey}.git.私钥. 位置. 文件/私人
# ${configurationKey}.git.ssh 会话-密码]
= ${configurationKey}.git.超时=PT10S
= ${configurationKey}.git.严格主机键检查=真实
= ${configurationKey}.git.清除存在的身份=虚假
```




## 影响数据库配置

鉴于该组件的 *配置密钥*，与CAS的Cassdb支持相关的以下选项同样适用于一些CAS组件：



```properties
# ${configurationKey}.url=/本地主机：8086
# ${configurationKey}。用户名 =根
# ${configurationKey}.密码=根
# ${configurationKey}.保留策略=自动原
# ${configurationKey}.drop-数据库=假
= ${configurationKey}.点对冲洗=100
= ${configurationKey}.批次间隔=PT5S
= ${configurationKey}。一致性级别
```




## 阿帕奇卡夫卡配置

鉴于组件的 *配置密钥*，与 CAS 中的 Kafka 支持相关的以下选项同样适用于多个 CAS 组件：



```properties
• ${configurationKey}.引导地址=本地酒店：9092
```




### 阿帕奇卡夫卡主题配置

鉴于组件的 *配置密钥*，与 CAS 中的 Kafka 支持相关的以下选项同样适用于多个 CAS 组件：



```properties
# ${configurationKey}.name]
# ${configurationKey}.分区 = 1
# ${configurationKey}.复制品= 1
# ${configurationKey}.压缩型 =
= ${configurationKey}.comig.key=值
```




## 黑兹尔卡斯特配置

鉴于组件的 *配置密钥*，与 CAS 中的黑兹尔卡斯特支持相关的以下选项同样适用于多个 CAS 组件：



```properties
# ${configurationKey}.集群.成员=123.456.789.000，123.456.789.001
# ${configurationKey}.集群.实例名称=本地
# ${configurationKey}.cluster.port=5701

# ${configurationKey}.许可键]
# ${configurationKey}.启用压缩 = 虚假
# ${configurationKey}.启用管理中心脚本=真实
```


鉴于组件的 *配置密钥*，下面列出了更高级的黑兹尔卡斯特配置设置：



```properties
# ${configurationKey}.集群. tcpip 启用= 真正的

= ${configurationKey}. 集群. 分区 - 成员组类型 = HOST_AWARE|定制|PER_MEMBER|ZONE_AWARE|SPI
# ${configurationKey}.集群.地图合并政策=PUT_IF_ABSENT|HIGHER_HITS|丢弃|PASS_THROUGH|EXPIRATION_TIME|LATEST_UPDATE|LATEST_ACCESS

# ${configurationKey}.集群.驱逐政策=LRU
# ${configurationKey}.集群.max无心跳秒=300
= ${configurationKey}.集群.记录类型 slf4j
# ${configurationKey}.集群.端口自动增量= 真实
# ${configurationKey}.集群.max大小=85
= ${configurationKey}.集群.备份计数 1
= ${configurationKey}.集群.
 ${configurationKey}${configurationKey}USED_HEAP_PERCENTAGE
.max.集群.超时=5

# ${configurationKey}.集群.本地地址=
= ${configurationKey}.集群.公共地址=

= ${configurationKey}.集群.出站端口[0]=45000
```




### 静态广域网复制



```properties
# ${configurationKey}.集群.wan 复制.启用=假
= ${configurationKey}.集群.wan 复制.复制名称=CAS

# ${configurationKey}.集群.wan 复制.目标[0].端点=1.2.3.4，4.5.6.7
= ${configurationKey}.集群.wan 复制.目标[0].发布商级名[com.哈泽尔卡斯特.企业.wan.复制.万.复制.万巴奇复制
] ${configurationKey}.集群.wan-复制.目标[0].队列全行为=THROW_EXCEPTION
= ${configurationKey}.集群.wan复制.目标[0].确认类型=ACK_ON_OPERATION_COMPLETE
= ${configurationKey}.集群.wan复制.目标[0].队列 容量=10000
= ${configurationKey}.集群.wan 复制.目标[0].批量大小=500
# ${configurationKey}.集群.wan 复制.目标[0].快照启用= 假
# ${configurationKey}.集群.wan 复制.目标[0].批量最大延迟毫秒=1000
# ${configurationKey}.cluster.wan 复制.目标[0].响应超时毫秒=60000
# ${configurationKey}.集群.wan 复制.目标[0].执行线程计数 =2

= ${configurationKey}.集群.wan 复制.目标[0]。一致性检查策略=无|MERKLE_TREES
# ${configurationKey}.集群.wan 复制.目标[0].集群名称=
# ${configurationKey}.集群.wan 复制. 目标[0].发布者 id=
= ${configurationKey}.集群.wan 复制.目标[0].属性
```




### 多播发现



```properties
# ${configurationKey}.集群.多广播信任界面]
= ${configurationKey}.集群.支持多播的假
= ${configurationKey}.集群.多播端口]
# ${configurationKey}.集群.多播组=
= ${configurationKey}.集群.多播超时=2
= ${configurationKey}.集群。多播直播时间=32
```




### AWS EC2 发现



```properties
# ${configurationKey}.集群.发现.启用]真正的

# ${configurationKey}.集群.发现.aws.访问-利]
= ${configurationKey}.集群.发现.aws.秘密-莱]


${configurationKey}# ${configurationKey}. 集群. 发现. aws. iam 角色 –

# ${configurationKey}. 集群. 发现. aws. 区域 = 我们 - 东 - 1
# ${configurationKey}. 集群. 发现。 aws. 主机标题 ]
# ${configurationKey}. 集群. 发现. aws. 安全组名]
# . 集群. 发现. aws. 标签键 ]
# ${configurationKey}。 集群.发现.aws.标签值=
= ${configurationKey}.集群.发现.aws.端口+-1= ${configurationKey}.集群.发现.aws.连接超时秒+5
```




### 阿帕奇云发现



```properties
# ${configurationKey}.集群.发现.启用]真实

# ${configurationKey}.集群.发现.云.提供商]
# ${configurationKey}.集群.发现.发现.jclou.身份=
= ${configurationKey}.集群.发现.jclou
. ${configurationKey}. 集群. 发现. jclouds. 端点]
# ${configurationKey}. 集群. 发现. jclouds. 区域]
# ${configurationKey}. 集群. 发现. jclouds. 区域]
# ${configurationKey}. 集群. 发现. jclouds. 标签键]
# ${configurationKey}. 集群. 发现. 发现. 标签值]
= ${configurationKey}. 集群. 发现. jclouds. 组 =
 ${configurationKey}.集群.发现.jclouds.port=-1
# ${configurationKey}.集群.发现.云.角色名称]
 ${configurationKey}.集群.发现.云.证书路径]
```




### 库贝内茨发现



```properties
# ${configurationKey}.集群.discovery.启用]真实

# ${configurationKey}.服务-dns]
# ${configurationKey}.服务-dns 超时=-1
# ${configurationKey}.服务名称=
# ${configurationKey}.服务标签名称=
# ${configurationKey}. 标签值=
# ${configurationKey}. 集群. 发现. 库贝内茨. 命名空间]
= ${configurationKey}. 解决不现成的地址 = 假
= ${configurationKey}. 集群. 发现. 库伯内特斯. 库伯内特斯 - 大师]
# ${configurationKey}. api - 令牌]
```




### 多克沼泽发现



```properties
# ${configurationKey}.集群.发现.启用]真正的

# ${configurationKey}.集群.发现.发现.docker-温暖.dns-提供商.启用]真实
= ${configurationKey}.集群.发现.docker-温暖 ${configurationKey}
.提供商. ${configurationKey}5701
 ${configurationKey}# ${configurationKey}. 集群. 发现. docker - 温暖. dns - 提供商. 点服务 = 服务 a， 服务 - b 等

# ${configurationKey}. 集群. 发现. docker - 温暖. 成员提供商. 启用] 真正的
# . 集群. 发现. docker - swarm. 成员提供商. 组名]
=  . 集群. 发现. -swarm. 成员提供商. 组密码]
# ${configurationKey}. 集群. 发现. docker - 温暖. 成员 - 提供商.
= ${configurationKey}. 集群. 发现. docker - swarm. 成员提供商. docker - 服务名称 =
# ${configurationKey}. 集群. 发现. docker - swarm. 成员提供商. [
] ${configurationKey}. 集群. 发现. docker - swarm. 成员提供商. 温暖 - mgr - uri]
# ${configurationKey}. 集群. 发现. docker - swarm. 成员提供商. 跳过验证 - ssl = 虚假
# ${configurationKey}. 集群. 发现. docker - swarm. 成员提供商. 哈泽尔卡斯特 - 同行端口 + 5701
```




### 微软蔚蓝发现



```properties
# ${configurationKey}.集群.发现.启用]真正的

# ${configurationKey}.集群.发现.azure.订阅-id]
= ${configurationKey}.集群.发现.发现.azure.客户端id=
= ${configurationKey}.集群.发现。 [客户-秘密]
= ${configurationKey}. 集群. 发现. azure. 租户 - id]
• ${configurationKey}. 集群. 发现. azure. 集群 id]
# ${configurationKey}. 集群. 发现. azure. 组名]
```




## 半径配置

鉴于该组件的 *配置密钥*，与中科院RADIUS支持相关的以下选项同样适用于 一些 CAS 组件（身份验证等）。

`服务器` 参数定义认证服务 （CAS 服务器） 的识别值，主要是 `服务器``` 。

`客户端` 参数定义连接RADIUS服务器的值。 参数 `客户端.inetAddress` 有可能包含更多由逗号分离的地址，以定义故障转移服务器在设置 `故障转移时` 。   



```properties
# ${configurationKey}.服务器. ${configurationKey}.服务器.
= ${configurationKey}.服务器.
 ${configurationKey}.服务器.EAP_MSCHAPv2

 ${configurationKey}.服务器.nas 端口类型=-1
# ${configurationKey}.服务器.服务器.nas 端口=-1
# ${configurationKey}.服务器.nas-ip 地址=
# ${configurationKey}.服务器.服务器.nas-ipv6 地址]
# ${configurationKey}.服务器.nas 标识符=-1
# ${configurationKey}.客户端口#1812
# ${configurationKey}.客户端。共享-秘密=N0Sh@ar3d$ecReT
# ${configurationKey}.客户端口=0
# ${configurationKey}.客户地址=本地主
# ${configurationKey}.客户端口=1813
# ${configurationKey}.异常故障转移=假
= ${configurationKey}.认证失败
```




## 沙发数据库配置

鉴于组件的 *配置密钥*，与 CouchDb 在 CAS 支持相关的以下选项同样适用于多个 CAS 组件（票证注册等）：



```properties
# ${configurationKey}. 沙发 - db. url= 本地主机： 5984
# ${configurationKey}. 沙发 - db. 用户名]
= ${configurationKey}. 沙发 - db. 密码 =
= ${configurationKey}. 沙发 - db. 插座超时 = 10
= ${configurationKey}. 沙发 - db. 连接超时 = 10
= ${configurationKey}. 沙发 - db. 滴收集 = 假
= ${configurationKey}. 沙发 - db .max连接 = 20
= ${configurationKey}. 沙发 - db. 启用 - ssl]
# ${configurationKey}. 沙发 - db. 放松 - sl 设置 ]
# ${configurationKey}. 沙发 - db. 缓存 = 假
# ${configurationKey}. 沙发 - db .max 缓存条目 = 10
= ${configurationKey}. 沙发 - db .max对象大小字节 = 8192
= ${configurationKey}. 沙发 - db. 使用 - 预期继续] 真实
# ${configurationKey}. 沙发 - db. 清理 - 空闲连接] 真正的
# ${configurationKey}. 沙发 - db. 创造- 如果不存在] 真正的
# ${configurationKey}. 沙发 - db. 代理主机]
# ${configurationKey}. 沙发 - db. 代理端口 = 1

# 默认基于功能名称。
# ${configurationKey}.沙发-db.db名称=

#对于无法自动解析更新冲突的少数功能。
# ${configurationKey}.沙发-db.retries=5

=根据手头的功能，CAS可能会同步执行一些操作。
* ${configurationKey}.沙发-db.异步=真实
```




## 蒙哥德布配置

鉴于该组件的 *配置密钥*，与 MongoDb 在 CAS 中的支持相关的以下选项同样适用于多个 CAS 组件（票证注册等）：



```properties
# ${configurationKey}.mongo.host=localhost
# ${configurationKey}.mongo.client-uri=localhost
# ${configurationKey}.mongo.port=27017
# ${configurationKey}.mongo.drop-collection=false
# ${configurationKey}.mongo.socket-keep-alive=false
# ${configurationKey}.mongo.password=

# ${configurationKey}.mongo.collection=cas-service-registry

# ${configurationKey}.mongo.database-name=cas-mongo-database
# ${configurationKey}.mongo.timeout=5000
# ${configurationKey}.mongo.user-id=
# ${configurationKey}.mongo.write-concern=NORMAL
# ${configurationKey}.mongo.read-concern=AVAILABLE
# ${configurationKey}.mongo.read-preference=PRIMARY
# ${configurationKey}.mongo.authentication-database-name=
# ${configurationKey}.mongo.replica-set=
# ${configurationKey}.mongo.ssl-enabled=false
# ${configurationKey}.mongo.retry-writes=false

# ${configurationKey}.mongo.pool.life-time=60000
# ${configurationKey}.mongo.pool.idle-time=30000
# ${configurationKey}.mongo.pool.max-wait-time=60000
# ${configurationKey}.mongo.pool.max-size=10
# ${configurationKey}.mongo.pool.min-size=1
# ${configurationKey}.mongo.pool.per-host=10
```




## 发电机数据库配置

鉴于组件的 *配置密钥*，与中科院 DynamoDb 支持相关的以下选项同样适用于多个 CAS 组件（票证注册等）：



```properties
# ${configurationKey}.dynamo-db.启动时投表=假
= ${configurationKey}.dynamo-db.预防表创建启动时=假
= ${configurationKey}.dynamo-db.本地实例=假
```


此功能的 AWS 设置可 [此处](#amazon-integration-settings)。



## 重新整合

以下选项与 CAS 中的功能相关，这些功能提供 REST 支持以获取和更新数据。 鉴于组件的 *配置密钥*，这些设置同样适用：



```properties
# ${configurationKey}.方法]获取|开机自检
# ${configurationKey}.顺序=0
= ${configurationKey}.case 麻木不仁 = 虚假
# ${configurationKey}.基本身份验证用户名 =uid
# ${configurationKey}.基本身份验证密码=密码
# ${configurationKey}.headers.key= 值
= ${configurationKey}.
```




## 雷迪斯配置

鉴于组件的 *配置密钥*，与 CAS 中的 Redis 支持相关的以下选项同样适用于多个 CAS 组件（票证注册等）：



```properties
# ${configurationKey}.雷迪斯.主机]本地主机
# ${configurationKey}.雷迪斯.数据库=0
= ${configurationKey}.redis.port=6380
# ${configurationKey}.redis. 密码=
= ${configurationKey}. redis. 超时 = 2000
# ${configurationKey}. redis. 使用 - ssl = 假
# ${configurationKey}. redis. 阅读 - 从] 大师
```




### 重新分配池配置



```properties
# ${configurationKey}. redis. pool. 启用= 假
# ${configurationKey}. redis. 池.max活动 = 20
# ${configurationKey}. redis. 池.max - 空闲 = 8
# ${configurationKey}. redis. pool. min - 空闲 = 0
# ${configurationKey}.redis.pool.max活动=8
= ${configurationKey}.redis.pool.max-wait=-1
# ${configurationKey}.雷迪斯.池.数字测试-每驱逐运行=0
= ${configurationKey}.redis.池 . 软敏可驱逐 - 可驱逐 - 空闲时间 - 毫 ] 0
= ${configurationKey}. redis. pool. 最小可驱逐 - 可被驱逐 - 空闲时间 - 毫秒 # 0
# ${configurationKey}. redis. pool.
# ${configurationKey}. redis. pool. 公平 = 虚假
# ${configurationKey}. redis. pool. 测试创造 = 假
# ${configurationKey}. redis. pool. 借试 = 假
# ${configurationKey}. redis. pool. 测试返回 = 假
= ${configurationKey}. redis. 池。
```




### 雷迪斯哨兵配置



```properties
# ${configurationKey}.雷迪斯.森蒂内尔.大师]我的大师
# ${configurationKey}.雷迪斯.森蒂内尔.节点[0]=本地主机：26377
# ${configurationKey}。 雷迪斯. sentinel. 节点[1]= 本地主
： 26378 # ${configurationKey}. redis. sentinel. 节点[2]= 本地主： 26379
```




### 重新组合配置



```properties
# ${configurationKey}.redis.集群.密码]
# ${configurationKey}.redis.集群.max重定向 =0
# ${configurationKey}.reis.集群. 节点[0].主机=
# ${configurationKey}.redis.集群.节点[0].port=
# ${configurationKey}。[0].
 ${configurationKey}.redis.集群.节点[0].id]
# ${configurationKey}.redis.集群.节点[0].名称=
# ${configurationKey}.redis.集群.节点[0].type=MASTER|奴隶
```




## DDL 配置

请注意，冬眠的 DDL 设置的默认值 `创建滴` 这可能不适合用于生产。 将值设置为 `验证` 可能更可取，但可以使用以下任何选项：

| 类型     | 描述                 |
| ------ | ------------------ |
| `驗證`   | 验证该模式，但不会对数据库进行更改。 |
| `更新`   | 更新该模式。             |
| `创造`   | 创建模式，销毁以前的数据。      |
| `创建-滴` | 在会话结束时删除该模式。       |
| `没有`   | 什么都不做              |


请注意，在版本迁移中，任何模式都已更改 `创建滴` 一旦 CAS 启动，将导致所有数据的丢失 。 对于像票证这样的瞬态数据，这可能 不是问题，但在审计表等情况下，重要数据可能会丢失。 使用 `更新`，虽然安全 的数据，被确认导致无效的数据库状态。 `验证` 或 `没有` 设置 可能是生产使用的唯一安全选项。

有关交易级别和传播行为配置的更多信息，请 本指南 [](http://docs.spring.io/spring-framework/docs/current/javadoc-api/)。



## SAML2 服务提供商集成

为每个服务提供商定义的设置只是尝试自动创建 [SAML 服务定义](../installation/Configuring-SAML2-Authentication.html#saml-services) 仅此而已。 如果您发现某些领域缺乏 适用设置，最好回到本地配置策略上，在 CAS 注册 SAML 服务提供商，这将取决于您选择的服务注册表。

每个 SAML 服务提供商支持以下设置：

| 名字       | 描述                                                  |
| -------- | --------------------------------------------------- |
| `元数据`    | 服务提供商元数据的位置（即 URL、路径等）                              |
| `名字`     | 在服务注册表中注册的服务提供商的名称。                                 |
| `描述`     | 在服务注册表中注册的服务提供商的描述。                                 |
| `名称伊德属性` | 为此服务提供商生成名称 ID 时要使用的属性。                             |
| `名称伊德法特` | 强制名称ID格式标识符（即 `骨灰盒：绿洲：名称：tc：SAML：1.1：名称格式：电子邮件地址`）。 |
| `属性`     | 要发布给服务提供商的属性，这些属性实际上可以映射并重命名。                       |
| `签名位置`   | 要验证元数据的签名位置。                                        |
| `实体ID`   | 允许此服务提供商的实体 ID 列表。                                  |
| `符号响应`   | 指示是否应签署回复。 默认值 `真实`。                                |
| `符号附录`   | 指示是否应签署断言。 违约 `虚假`。                                 |


激活服务提供商自动配置的唯一所需设置是元数据的存在和定义。 所有其他设置都是可选的。 

鉴于提供商的 *配置密钥，以下选项同样适用于 SAML2 服务提供商集成*：



```properties
# ${configurationKey}.元数据]/等/cas/saml/下拉框.xml
# ${configurationKey}.name=SP 名称
# ${configurationKey}.描述/SP 集成
# ${configurationKey}.名称-id-属性/邮件
# ${configurationKey}.name- id 格式 =
# ${configurationKey}. 签名位置]
= ${configurationKey}. 属性 =
= ${configurationKey}. 实体 - ids =
# ${configurationKey}. 符号响应 =
= ${configurationKey}.
```




## 多因素身份验证提供商

鉴于提供商的 *配置密钥*，所有可配置的多因素身份验证提供商都有这些基本属性：



```properties
# ${configurationKey}.rank]
• ${configurationKey}.id]
• ${configurationKey}。名称=
• ${configurationKey}.故障模式
```




## 多因素身份验证旁路

鉴于提供商的 *配置密钥，以下旁路选项同样适用于多因素身份验证提供商，*：



```properties
# ${configurationKey}.旁路.主要属性名称=旁路|跳过
= ${configurationKey}.旁路.委托属性值=真实|可用。]

= ${configurationKey}.旁路.旁路.认证-属性名称=旁路|跳过
# ${configurationKey}.旁路.认证-属性值=允许. |可

= ${configurationKey}.旁路.认证处理程序名称 ${configurationKey}
=接受使用者。 名称[Ldap授权.]

= ${configurationKey}.旁路.证书类类型=用户名密码 | ${configurationKey}

。
# ${configurationKey}. . . http 请求标题 = 标题 - x - |头 - y.

= ${configurationKey}. 旁路. groovy. 位置 [ 文件] 等 / cas / 配置 / mfa - 旁路. groovy
```


如果多因素身份验证旁路是通过 REST 确定的， 则此处 [可在配置键 `${configurationKey}. .`下](#restful-integrations) 提供 reSTful 设置。



## 沙发基地集成设置

当 CAS 配置为与 Couchbase 集成时（即票务注册表等），鉴于提供商的 *配置密钥*，将共享以下选项并实施：



```properties
# ${configurationKey}.地址[0]=本地
# ${configurationKey}.群集用户名]
# ${configurationKey}.集群密码] 

# ${configurationKey}.bucket= 测试桶    

# ${configurationKey}.连接超时 # PT60s
# ${configurationKey}.搜索超时 # PT30S
# ${configurationKey}.查询超时 = PT30
# ${configurationKey}. view 超时 # PT30s
# ${configurationKey}.kv 超时 # PT30s 
# ${configurationKey}.max - http 连接 = Pt30s
# ${configurationKey}.空闲连接超时 # PT30S
# ${configurationKey}.查询阈值 = PT30S
# ${configurationKey}.扫描一致性=NOT_BOUNDED|REQUEST_PLUS
```




## 亚马逊集成设置

鉴于提供商的 *配置密钥*，当 CAS 配置为集成各种 亚马逊 Web 服务功能时，将共享以下选项并应用：



```properties
# ${configurationKey}.凭据访问密钥]
= ${configurationKey}.凭据-秘密密钥]

= ${configurationKey}.终点=/本地主
：8000 = ${configurationKey}。区域US_WEST_2|US_EAST_2|EU_WEST_2|<REGION-NAME>
# ${configurationKey}.本地地址=
# ${configurationKey}.重试模式=标准|遗产

# ${configurationKey}.代理主机]
# ${configurationKey}.代理密码]
# ${configurationKey}.代理用户名]

# ${configurationKey}. 读取容量 = 10
# ${configurationKey}. 写容量 = 10
# ${configurationKey}.连接超时=5000
= ${configurationKey}.插座超时 =5000
= ${configurationKey}.使用-收割机=虚假

# ${configurationKey}.客户端执行超时 =10000
= ${configurationKey}.max连接=10
```




## 合并集成设置

鉴于提供商的 *配置密钥，当 CAS 配置为集成 memcached（即票务注册表等）时，将共享以下选项*并应用：



```properties
# ${configurationKey}.memcached.servers=本地主机：11211
# ${configurationKey}.memcached.定位器类型=ARRAY_MOD
= ${configurationKey}.memcached.故障模式= 重新分配
# ${configurationKey}.memcached FNV1_64_HASH
. ${configurationKey}.memcached.protocol] 文本
# ${configurationKey}. memcached. 应优化 = 虚假
# ${configurationKey}. memcached. daemon= 真实
# ${configurationKey}. memcached.max 重新连接延迟 = 1
# ${configurationKey}。 ${configurationKey}${configurationKey}. memcached. op 超时
+ 1

# ${configurationKey}. memcached. 超时阈值 = 2
 ${configurationKey}. memcached.max 总计 =20
= ${configurationKey}. memcached.max - 空闲 = 8
= ${configurationKey}. memcached. min - 懒惰 = 0

= ${configurationKey}. memcached. 转录机 = Kryo|串行|沃林|WHALINV1
# ${configurationKey}. memcached. 转录机压缩阈值 = 16384
# ${configurationKey}. memcached. kryo 自动重置 = 假
# ${configurationKey}. memcached. kryo 对象逐一引用 = 假
= ${configurationKey}. memcached.
```




## 密码策略设置

当 CAS 配置为与支持密码策略执行和检测的帐户源和身份验证策略集成时，会共享以下选项并应用，因为提供商的 *配置密钥*。 请注意，某些设置仅适用于基础帐户源为 LDAP，并且仅在 CAS 中配置的身份验证策略能够尊重和识别它们时才予以考虑： 



```properties
# ${configurationKey}.类型=通用|广告|自由|电子

# ${configurationKey}.启用=真实
# ${configurationKey}.政策属性.帐户锁定=javax.安全.auth.登录.帐户锁定例外
# ${configurationKey}.登录 失败=5
= ${configurationKey}.警告属性值=
= ${configurationKey}.警告属性名称=
= ${configurationKey}.匹配显示警告=真实
# ${configurationKey}.警告所有=真实
# ${configurationKey}.警告日=30
= ${configurationKey}.帐户状态处理启用=真实

=实施"org.ld" 身份验证响应汉德勒
# ${configurationKey}.定制政策类=com.示例.我授权响应手

# ${configurationKey}.策略=默认|格罗夫|REJECT_RESULT_CODE
# ${configurationKey}.groovy.位置=文件/cas/配置/密码政策。
```




#### 密码策略策略

密码策略策略类型概述如下。 该策略评估从 LDAP 等机构收到的身份验证响应，并允许提前查看，以便进一步检查帐户状态、消息和警告是否有资格进行进一步调查。

| 选择                   | 描述                                                                                     |
| -------------------- | -------------------------------------------------------------------------------------- |
| `违约`                 | 接受身份验证响应，并处理帐户状态（如果有的话）。                                                               |
| `槽的`                 | 动态检查身份验证响应，作为 Groovy 脚本的一部分。 处理帐户状态更改和警告的责任完全委托给脚本。                                    |
| `REJECT_RESULT_CODE` | 只有在配置中不拒绝身份验证响应的结果代码时，才处理帐户状态的 `默认` 的扩展。 默认情况下， `INVALID_CREDENTIALS（49）` 阻止CAS处理账户状态。 |


如果密码策略要分发到 Groovy 脚本，则脚本的大纲可能如下：



```groovy
进口 java.util.*
进口组织.ldaptive.auth.*
进口组织.apereo.cas.*
进口组织.apereo.cas.认证.*
进口组织.apereo.cas.认证.支持.*

<MessageDescriptor> 运行列表（最终对象...args） {
    def 响应 = args[0]
    def 配置 = args[1];
    def 记录器 = args[2]
    def 应用程序文本 = args[3]

    logger.info （"通过 ${配置处理密码策略 [[}] 响应）

    定义帐户状态手=配置。获取帐户状态手勒（）
    返回帐户状态汉德勒。句柄（响应，配置）
}
```


通过的参数如下：

| 参数   | 描述                                |
| ---- | --------------------------------- |
| `响应` | `组织.ldap.auth.身份验证响应`             |
| `配置` | 已定义帐户状态处理程序的 LDAP 密码策略配置。         |
| `记录` | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |




## 电子邮件通知

要了解有关此主题的更多内容，请 [](../notifications/Sending-Email-Configuration.html)查看本指南。

鉴于提供商的 *配置密钥，当 CAS 配置为发送电子邮件通知时，将共享以下选项并实施*：



```properties
# ${configurationKey}.mail.mail.
# ${configurationKey}.mail.文本]
# ${configurationKey}.mail. 主题=
# ${configurationKey}.mail.cc]
# ${configurationKey}. mail. bcc]
# ${configurationKey}.mail.mail.回复]
# ${configurationKey}.mail. mail. 验证地址 # 假
# ${configurationKey}. mail.html = 假

# ${configurationKey}. mail.
```


可能还需要定义以下设置来描述邮件服务器设置：



```properties
[春天. mail. host]
[ 春天. mail. port]
[ 春天. mail.
. mail. 密码]
[ 春天. mail. 属性. 邮件.
] 春天. 邮件. 属性. 邮件. smtp.
```




## 短信通知

鉴于提供商的 *配置密钥，当 CAS 配置为发送短信通知时，将共享以下选项并应用*：



```properties
# ${configurationKey}.sms.来自]
= ${configurationKey}.sms.文本=
= ${configurationKey}.sms.属性名称=电话
```


您还需要确保定义能够发送短信的提供商。 要了解有关此 主题的更多内容，请 [](../notifications/SMS-Messaging-Configuration.html)查看本指南。



## 网络流自动配置

与 Web 流状态的自动配置、过渡和执行顺序相关的 Web 流的控制方面。



```properties
* ${configurationKey}.订单
```




## 委托身份验证设置

当 CAS 配置为将身份验证 委托给雅虎等外部提供商时，会共享以下选项并适用，因为提供商的 *配置密钥*：



```properties
# ${configurationKey}.id]
# ${configurationKey}.秘密]
# ${configurationKey}.客户名] 我的提供商
# ${configurationKey}.自动重定向 = 虚假
# ${configurationKey}.css级=
# ${configurationKey}. 校长属性 - id =
# ${configurationKey}. 启用= 真实
= ${configurationKey}. 回调 url 类型 = PATH_PARAMETER|QUERY_PARAMETER|没有

```


以下类型支持回调 URL 分辨率：

| 类型                | 描述                           |
| ----------------- | ---------------------------- |
| `PATH_PARAMETER`  | 构建回调 URL 时，客户名会作为路径参数添加到网址中。 |
| `QUERY_PARAMETER` | 构建回调 URL 时，客户名将作为查询参数添加到网址中。 |
| `没有`              | 网址中未添加客户名。                   |




### 委托身份验证打开ID 连接设置

当 CAS 配置为将身份验证 委托给外部 OpenID 连接提供商（如 Azure AD）时，会共享以下选项并应用，因为提供商的 *配置密钥*：



```properties
# ${configurationKey}.发现-乌里]
= ${configurationKey}.logout-url=
= ${configurationKey}.max时钟偏斜=
= ${configurationKey}.scope=
# ${configurationKey}.使用-无-假=假
= ${configurationKey}.禁用-无名小卒=假
= ${configurationKey}.首选-jws-算法=
= ${configurationKey}.响应模式 =
= ${configurationKey}.响应类型 =
= ${configurationKey}.自定义参数. param1= 值 1
# ${configurationKey}. 读取超时 = PT5s
# ${configurationKey}.连接超时=PT5S
# ${configurationKey}.过期与代币 # 假
# ${configurationKey}.令牌到期提前=0
```




## LDAP 连接设置

鉴于提供商的 *配置密钥，以下选项适用于与 LDAP 服务器集成的功能（即身份验证、属性解析等），*：



```properties
• ${configurationKey}. ldap- url=ldaps：//ldap1. ldaps://ldap2.example.edu
= ${configurationKey}.bind-dn=cn=目录-经理，直流示例，dc=org
= ${configurationKey}.bind-凭据=密码

# ${configurationKey}.池钝器=无|BIND
# ${configurationKey}.连接策略]
# ${configurationKey}.连接超时 # PT5S
# ${configurationKey}.信任证书]
# ${configurationKey}. 信任商店 ]
# ${configurationKey}. 信任商店 - 密码 #
# ${configurationKey}. 信任商店类型 # Jks|杰克斯|PKCS12
# ${configurationKey}.钥匙店]
# ${configurationKey}.钥匙店密码 #
# ${configurationKey}.钥匙店类型 = JKS|杰克斯|PKCS12
# ${configurationKey}.禁用池= 假
# ${configurationKey}.min 池大小 =3
# ${configurationKey}.max池大小 = 10
# ${configurationKey}.验证结账=真实
# ${configurationKey}.定期验证]真实
= ${configurationKey}.验证期=PT5M
= ${configurationKey}.验证超时=PT5S
# ${configurationKey}.失败快=真实
 ${configurationKey}。 空闲时间=PT10M
= ${configurationKey}.修剪期=PT2H
= ${configurationKey}.块等待时间=PT3S

= ${configurationKey}.使用-启动-tls=虚假
= ${configurationKey}。响应- 超时 =PT5S
# ${configurationKey}.允许多 dns = 假
# ${configurationKey}. 允许多条目 = 假
= ${configurationKey}. 后续推荐 = 假
= ${configurationKey}. 二进制属性 = 对象指南，一些其他属性
# ${configurationKey}。名称=
```




### 连接初始化

注入LDAP连接池的LDAP连接配置可初始化，参数如下：

| 行为                    | 描述                         |
| --------------------- | -------------------------- |
| `绑定dn`/`绑定信用` 提供      | 在初始化连接时，使用所提供的凭据进行绑定。      |
| `绑定Dn`/`绑定信用` 设置为 `*` | 使用快速绑定策略初始化池。              |
| `绑定Dn`/`绑定信用` 设置为空白   | 跳过连接初始化：匿名执行操作。            |
| 提供 SASL 机制            | 在初始化连接时，使用给定的 SASL 机制进行绑定。 |




### 钝器

当对象被重新签回LDAP连接池时，以下选项可用于钝化对象：

| 类型   | 描述                                                |
| ---- | ------------------------------------------------- |
| `没有` | 不发生钝化。                                            |
| `捆`  | 默认行为通过在连接上执行绑定操作来钝化连接。 此选项要求在建立与 LDAP 的连接时提供绑定凭据。 |




#### 为什么是钝器？

当 CAS 被配置为使用 `直接` 进行身份验证时，可能会收到意外的 LDAP 故障，或 `` 类型进行身份验证，LDAP 被锁定，不允许匿名绑定/搜索。 如果池中的给定 LDAP 连接与失败的登录尝试处于同一连接上，则每次尝试都会失败，而常规连接验证器同样会失败。 当连接返回到池中时，它仍然可能包含上次尝试的本金和凭据。 在使用该连接的下一次绑定尝试之前，验证器尝试再次验证连接，但失败，因为它不再尝试使用配置的绑定凭据，而是尝试使用前一步骤中使用的任何用户 DN。 如果验证失败，连接将关闭，CAS 默认将拒绝访问。 钝化器尝试使用配置的绑定凭据重新连接到 LDAP，有效地重置连接到每次绑定请求后应连接的内容。

此外，如果您在日志中看到类似于 *<Operation exception encountered, reopening connection>* 类型的消息的错误，这通常表明 CAS 建立和创建的连接池的验证超时大于 LDAP 服务器中配置的超时，或者更有可能大于 LDAP 服务器前负载平衡器中的超时。 您可以调整 LDAP 服务器会话的超时以进行连接，也可以教 CAS 使用等于或小于 LDAP 服务器会话超时的有效期。



### 连接策略

如果将多个网址作为 LDAP 网址提供，这将描述如何处理每个 URL。

| 供应商              | 描述                                       |
| ---------------- | ---------------------------------------- |
| `ACTIVE_PASSIVE` | 第一个LDAP将用于每个请求，除非它失败，然后下一个将被使用。          |
| `ROUND_ROBIN`    | 对于每个新连接，将使用列表中的下一个网址。                    |
| `随机`             | 对于每个新连接，将选择随机 LDAP 网址。                   |
| `DNS_SRV`        | 将使用基于配置/给定 LDAP 网址的 DNS SRV 记录的 LDAP 网址。 |




### LDAP萨斯尔机制



```properties
• ${configurationKey}.萨斯尔机制=格萨皮|DIGEST_MD5|CRAM_MD5|外部
# ${configurationKey}.sasl 领域= 示例.COM
= ${configurationKey}. sasl 授权 id=
# ${configurationKey}. sasl - 相互身份验证]
• ${configurationKey}.sasl 质量保护]
• ${configurationKey}. sasl 安全强度=

```




### LDAP 连接验证器

以下 LDAP 验证器可用于测试连接健康状况：

| 类型   | 描述                                        |
| ---- | ----------------------------------------- |
| `没有` | 不进行验证。                                    |
| `搜索` | 通过执行搜索操作验证连接是否健康。 如果搜索结果大小大于零，则验证被认为是成功的。 |
| `比较` | 通过执行比较操作验证连接是否健康。                         |




```properties
# ${configurationKey}.验证器.类型=无|搜索|比较
# ${configurationKey}.验证器.验证器. base-dn]
# ${configurationKey}.验证器.搜索过滤器]（对象类=*）
# ${configurationKey}.验证器.范围=对象|一级|SUBTREE
# ${configurationKey}.验证器.验证器.属性名称=对象类
# ${configurationKey}.验证器.属性值 = 顶部
# ${configurationKey}.验证器.dn=

```




### LDAP SSL 主机名验证

以下 LDAP 验证器可用于测试连接健康状况：

| 类型   | 描述                            |
| ---- | ----------------------------- |
| `违约` | 启用和强制 LDAP SSL 配置的主机名验证的默认选项。 |
| `任何` | 跳过并忽略LDAP SSL配置的主机名验证。        |




```properties
•${configurationKey}。主机名验证器=默认|任何
```




### LDAP SSL 信托经理

信托经理负责管理 LDAP 信托决策时使用的信任材料， 以及决定是否应接受同行提供的凭据。

| 类型   | 描述                  |
| ---- | ------------------- |
| `违约` | 启用并强制默认的 JVM 信托管理器。 |
| `任何` | 信任任何客户端或服务器。        |




```properties
#${configurationKey}.信任经理=默认|任何
```




### LDAP 类型

CAS 中的许多组件/功能允许您明确指示 LDAP 服务器的 `类型` ，尤其是在 CAS 需要更新 LDAP 中的属性等的情况下（即同意、密码管理等）。 相关设置为：



```properties
#${configurationKey}.类型=广告|自由|电子编导|通用
```


支持以下类型：

| 类型     | 描述                   |
| ------ | -------------------- |
| `广告`   | 活动目录。                |
| `弗里帕`  | 免费目录服务器。             |
| `电子编导` | 网易电子编导。              |
| `通用`   | 所有其他目录服务器（即打开LDAP等）。 |




### LDAP 身份验证/搜索设置

除了上述常见的 LDAP 连接设置外，CAS 还需要执行对 LDAP 服务器进行 身份验证才能提取帐户或属性集，或执行搜索查询。 鉴于提供商的 *配置密钥*，以下选项适用：

**注：如果不能指定足够的属性，如 `型`、 `ldapUrl`等，** 只会默默地停用LDAP。



```properties
# ${configurationKey}.类型=广告|认证|直接|匿名

# ${configurationKey}. base - dn = dc = 示例， dc = 组织
# ${configurationKey}. 子树搜索 = 真实
= ${configurationKey}. 搜索过滤器 \ cn ]{user}
# ${configurationKey}.page 大小 =0

# ${configurationKey}. 增强与进入解决器] 真正的
# ${configurationKey}. deref - 别名 = 永远|搜索|查找|始终
# ${configurationKey}.dn 格式=%s，人，例如，直流组织
# ${configurationKey}。主要属性密码

```


支持以下身份验证类型：

| 类型   | 描述                                                                                                                                        |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| `广告` | 活动目录 - 用户使用 `sAM 帐户名` 进行身份验证，通常使用 DN 格式。                                                                                                  |
| `认证` | 管理器绑定/搜索身份验证类型。 如果 `主属性密码` 是空的，则用户将进行简单的绑定来验证凭据。 否则，给定属性将与使用 `SHA` 加密值的给定 `主要归因密码` 进行比较。                                                  |
| `直接` | 从格式字符串计算用户 DN 并执行简单的绑定。 当不需要搜索来计算绑定操作所需的 DN 时，这是相关的。 当所有用户都在目录中的单个分支下时，此选项是有用的，例如 `用户、例如，`或 CAS 登录表单上提供的用户名是 DN 的一部分，例如 `=%s，用户，直流，直流+组织` |
| `匿名` | 与 `认证` 类似的语义，除非没有 `绑定Dn` ，并且可以指定 `绑定信用` 来初始化连接。 如果 `主属性密码` 是空的，则用户将进行简单的绑定来验证凭据。 否则，给定属性将与使用 `SHA` 加密值的给定 `主要归因密码` 进行比较。                  |




### LDAP 搜索进入处理程序



```properties
# ${configurationKey}.搜索进入处理程序[0].类型=

# ${configurationKey}.搜索进入处理程序[0].case-change.dn 案例更改=无|更低|上
# ${configurationKey}.搜索进入处理程序[0]。案例更改.属性-名称-案例更改=无|更低|上
# ${configurationKey}.搜索进入处理程序[0]。案例更改.属性值案例更改=无|更低|上
# ${configurationKey}.搜索输入处理程序[0].case-change.属性名称]

= ${configurationKey}.搜索进入处理程序[0].dn 属性.dn 属性名称=条目 DN
# ${configurationKey}.search 进入处理程序[0].dn-属性.add-如果存在=假

= ${configurationKey}。搜索-输入处理程序[0]。主要组-id.组-过滤器=（&（对象类=组）（对象-sid={0}

# ${configurationKey}.搜索-输入-处理程序[0]。主要组-id.base-dn]

= ${configurationKey}.搜索-输入-处理程序[0].合并属性.合并属性-名称=
= ${configurationKey}.search- 条目处理程序[0].合并属性.属性名称]

# ${configurationKey}.搜索-输入处理程序[0].递归.搜索-属性]  = ${configurationKey}.搜索-输入-处理程序[0].递归.合并属性=

```


支持以下类型：

| 类型                   | 描述                                      |
| -------------------- | --------------------------------------- |
| `CASE_CHANGE`        | 提供修改搜索条目 DN、属性名称和属性值的情况的能力。             |
| `DN_ATTRIBUTE_ENTRY` | 将条目 DN 添加为结果集的属性。 提供 RFC 5020 的客户端实施。   |
| `合并`                 | 将一个或多个属性的值合并到单个属性中。                     |
| `OBJECT_GUID`        | 处理 `对象GUID` 属性提取和转换。                    |
| `OBJECT_SID`         | 处理 `对象` 属性提取和转换。                        |
| `PRIMARY_GROUP`      | 构建主组 SID，然后搜索该组，并将 DN 置于原始搜索条目的"成员"属性中。 |
| `RANGE_ENTRY`        | 重写从活动目录返回的属性，通过执行其他搜索来包含所有值。            |
| `RECURSIVE_ENTRY`    | 此基于提供的属性进行递归搜索，并将这些结果合并到原始条目中。          |




### LDAP多个基础数据库

可能有些情况下，单个 LDAP 树的不同部分可被视为碱基 dns。 每个条目可以使用特殊的标界字符指定并连接在一起，而不是复制每个基础 dn 的 LDAP 配置块 。 使用定义顺序中所有基础 dn 和 DN 解析器的组合检索用户 DN。 如果发现多个DN ，DN分辨率应失败。 否则，找到的第一个DN将返回。



```properties
# ${configurationKey}. 基地 - dn = 子树， 直流示例， 直流|净|子树， dc = 示例， 直流网
```
