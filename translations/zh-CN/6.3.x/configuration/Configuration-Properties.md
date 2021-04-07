---
layout: 违约
title: 中科院物业
category: 配置
---

# 中科院物业

CAS 中可以指定各种属性 [配置文件内部或作为命令 线路开关](Configuration-Management.html#overview)。 本节提供了一个常见的 CAS 属性列表，并 使用它们的基础模块的引用。

<div class="alert alert-warning"><strong>选择性</strong><p>
此部分仅用作指南。 <strong>不要</strong> 将整个 
集合的设置复制/粘贴到您的 CAS 配置中：而只选择你需要的属性。 除非您 
某些目的，并且不将设置复制到您的配置中，否则不要启用设置，而应将其 
保留为 <i>参考</i>。 所有这些想法导致升级头痛，维护噩梦和过早老化。</p></div>

下列属性列表由 CAS 控制并提供给 CAS。 对于大多数使用案例，每个块都与特定的 CAS 模块 对应，该模块预计将包含在构建 和部署过程中准备的 CAS 最终分布中。

<div class="alert alert-info"><strong>亚格尼</strong><p>请注意，对于几乎所有使用案例，
 申报和配置下面列出的属性就足够了。 您不必
明确按摩 CAS XML 配置文件来设计身份验证处理程序、
创建属性释放策略等。 CAS 在运行时将为您自动配置所有所需的更改。</p></div>

## 命名公约

财产名称可以非常宽松地指定。 对于 个实例 `cas.一些财产`， `cas.一些财产`， `cas.some_property` 都是有效的名字。 虽然 CAS 接受所有形式，但某些组件（在 CAS 和其他使用的框架中） ，其运行时间的激活以 属性值为条件，其中需要使用烤肉串盒在 CAS 配置中指定此属性。 对于 CAS 拥有的属性以及可能通过外部库或框架（如春靴等） 提交给系统的属性，这一 都是正确的。

> 如果可能，属性应以小写烤肉串格式存储，例如 cas.属性名称=值。

## 常规

许多 CAS 配置选项同样适用于多个模块和功能。 要了解和 注意这些选项，请 [本指南](Configuration-Properties-Common.html)。

## 验证

配置属性在 CAS 启动器上自动验证，以报告具有配置绑定性的问题， 特别是如果配置模式无法识别或验证定义的 CAS 设置。 默认情况下， 的验证过程是开启的，可以使用特殊的 *系统属性* `SKIP_CONFIG_VALIDATION` 在启动时跳过，该  SKIP_CONFIG_VALIDATION  应设置为 `真正的`。

其他验证过程也通过 [配置元数据](Configuration-Metadata-Repository.html) 和属性迁移处理，这些迁移在启动时由 Spring Boot 和家庭自动应用。

## 自定义设置

以下设置可用于使用任意配置密钥和值扩展 CAS：

```properties
#卡斯. 自定义. 属性。[property-name]=[property-value]
```

## 配置存储

本节概述了可用于存储 CAS 配置和设置的策略。

### 独立

这是默认配置模式，表明 CAS 不需要 外部配置服务器连接，并将以嵌入式独立模式运行。

#### 按目录

CAS 默认将尝试在设置名称 `cas.独立.配置目录` 下 指示的给定目录内查找设置和属性，否则将返回使用：

1. `/等/卡斯/配置`
2. `/选择/卡斯/配置`
3. `/瓦尔/卡斯/康菲`

CAS 还能够加载用于加载设置的 Groovy 文件。 该文件预计将在上述匹配的 目录中找到，并应命名为 `${cas-application-name}.groovy`，如 `cas.groovy`。 该脚本能够将适用于所有环境和配置文件的活动配置文件和常见设置的有条件设置组合到一个位置，其结构类似于以下示例：

```groovy
设置可以由个人配置文件
配置文件（
    独立）
        cas.some.设置="值"
    [
]

//这适用于 cas.common
的所有配置文件和环境。
```

#### 按文件

还有一个 `cas.独立.配置文件` 可用于直接以文件或分类资源的形式向 CAS 属性集合。 在云中部署裸露的 CAS 服务器时，这特别有用，无需 配置服务器或外部目录的额外仪式，并且部署人员希望避免覆盖嵌入式配置文件。


### 春云

以下设置将由 CAS 配置运行时间加载，该运行时间将引导 整个 CAS 运行上下文。 它们将被放入配置服务器本身的 `src/主/资源/引导属性` 。 有关详细信息，请参阅本指南</a>。</p> 

由春云支持的配置服务器支持以下配置文件。



### 本地

加载来自外部属性/yaml 配置文件的设置。



```properties
#Spring.配置文件.活动=原生

#CAS 应监控以定位设置的配置目录。
• 春季.云.配置.服务器.本地搜索位置
```




### 吉特存储库

允许 CAS 弹簧云配置服务器从内部/外部 Git 存储库加载设置。 然后，这允许 CAS 成为配置服务器的客户端，在需要时通过 HTTP 消耗设置。



```properties
#弹簧.配置文件.活动=默认

= 包含 CAS 设置的 git 存储库的位置。
• 位置可以指向 HTTP/SSH/目录。
•春天.云.康菲格.服务器.git.uri=https：//github.com/重新名/配置
# 春天.云.配置.服务器.${user.home}/配置

# 用于验证 git 请求的凭据，特别是
# 使用 HTTPS 时。 如果通过SSH连接到存储库，请记住
#向SSH代理注册您的公共密钥，就像您的正常情况一样，
#与任何其他公共存储库。
#春天. 云. 配置. 服务器. git. 用户名]
# 春天. 云. 配置. 服务器. git. 密码]
```


上述配置也适用于基于在线 git 的存储库，如吉图布、BitBucket 等。



### 领事

允许 CAS 春季云配置服务器加载来自哈希公司领事</a>设置。</p> 



```properties
#春天. 云. 领事. 配置. 启用] 真实
# 春天. 云. 领事. 配置
# 春天. 云. 领事. 配置. 默认上下文 ] 应用程序
# 春天。 云. 领事. 配置. 配置文件分离器]：

# 春天. 云. 领事. 配置. 手表. 延迟 = 10
# 春天. 云. 领事. 配置文件. 手表. 启用 = 假
```




### 库

允许 CAS 春季云配置服务器加载来自哈希公司保险库</a>设置。</p> 



```properties
# 春天. 云. vault. 主机 = 127.0.1
# 春天. 云. vault. port= 8200
# 春天. 云. vault. 连接超时 = 3000
#春天. 云. vault. 阅读超时 # 5000
# 春天. 云. vault. 启用] 真正的
# 春天. 云. vault. 失败快 ] 真正的
# 春天. 云. vault. 计划
```




#### 令牌身份验证

代币是保险库内身份验证的核心方法。 代币身份验证需要提供静态令牌。



```properties
•春天.云.vault.认证]令牌
#春天.云.vault.令牌=1305dd6a-a754-f145-3563-2fa90b0773b7
```




#### 应用身份验证

Vault 支持 AppId 身份验证，该身份验证由两个难以猜测的令牌组成。 AppId 默认为静态配置的 `spring.application.name` 。 第二个令牌是 UserId，该部分由应用程序决定，通常与运行时间环境相关。 春季云库配置支持 IP 地址、Mac 地址和静态 UserId（例如通过系统属性提供）。 IP 和 Mac 地址表示为六角编码的 SHA256 哈希。

使用 IP 地址：



```bash
出口IP_ADDRESS++++-n 192.168.99.1 |剃须刀256苏姆'
```




```properties
#春天. 云. vault. 认证 ] appid
# spring.cloud.vault.app id. 用户 id =$IP_ADDRESS
```


使用 MAC 地址：



```bash
出口 $MAC_ADDRESS='回声-n ABCDEFGH|剃须刀256苏姆'
```




```properties
•春天.云.vault.认证]APPID
# spring.cloud.vault.app-id.用户-id]$MAC_ADDRESS
= spring.cloud.vault.app-id.网络接口=eth0
```




#### 库贝内茨身份验证

库伯涅茨身份验证机制允许使用库伯内特服务帐户代币对保险库进行身份验证。 身份验证基于角色，该角色受限于服务帐户名称和名称空间。



```properties
•春天.云.vault.认证]库贝内特斯
#春天.云.vault.库贝内茨.角色=我的开发角色
#春天.云.vault.库贝内茨.服务帐户-令牌文件：/var/运行/秘密/库伯内特斯.io/服务帐户/令牌
```




#### 通用后端 v1



```properties
#春天. 云. vault. 通用. 启用] 真正的
# 春天. 云. vault. 通用. 后端 / 秘密
```




#### KV 后端 v2



```properties
#春天. 云. vault. kv. 启用] 真正的
# 春天. 云. vault. kv. 后端 - 秘密
```




### 蒙古德布

允许 CAS 春季云配置服务器加载来自 MongoDb 实例的设置。



```properties
#卡斯.春天.云.蒙戈.乌里·蒙戈德布：//卡苏瑟：Mellon@ds135522.mlab.com：35522/贾西格卡斯
```




### 蔚蓝钥匙沃特秘密

允许 CAS 弹簧云配置服务器加载来自微软 Azure 的 KeyVault 实例的设置。



```properties
[azure. keyvault. 启用] 真正的
[ azure. keyvault. uri] 把你的 azure - 钥匙 - 乌里 - 这里
= azure. keyvault. 客户 id = 把你的 a 祖尔 - 客户 id - 这里
# azure. keyvault. 客户键 - 把你的蔚蓝 - 客户键 - 这里
# azure. keyvault. 代币收购超时秒 + 60
```




### 动物园管理员

允许 CAS 春季云配置服务器加载来自阿帕奇动物园管理员实例的设置。



```properties
#春天. 云. zookeeper. 连接字符串] 本地主
# 春天. 云. zookeeper. 启用] 真正的
# 春天. 云. zookeeper. 配置. 启用] 真实
# 春天. 云. 云. 动物园管理员.max # 10
# 春天. 云. zookeeper.
```




### 亚马逊秘密经理

此功能的常见 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.spring.云.aws.秘密管理器`。



### 亚马逊参数商店

此功能的常见 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.spring.云.aws.ssm`。



### 亚马逊 S3

以下设置可使用此处概述的策略 [](Configuration-Management.html#overview) 进行传递，以便 CAS 建立连接， 使用配置密钥 `cas.spring.cloud.aws.s3`。



```properties
# ${configuration-key}.桶名称= 卡斯属性
```


此功能的常见 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.spring.云.aws.s3`。



### 迪纳莫德布

此功能的常见 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.spring.云.dynamo-db`。 



### 京城

允许 CAS 弹簧云配置服务器加载 RDBMS 实例中的设置。 此功能的数据库设置 可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.spring.云.jdbc`。



```properties
#cas. 春天. 云. jdbc.sql = 选择 id， 名称， 价值从CAS_SETTINGS_TABLE
```




### 休息

允许 CAS 春季云配置服务器加载来自 REST API 的设置。



```properties
#cas.spring. 云. rest. url]
# cas. 春天. 云. rest. 基本身份验证 - 用户名]
# cas. 春天. 云. rest. 基本身份验证密码 ]
# cas. 春天. 云. rest. 方法]
# cas. 春天. 云. rest. 标题 1： 价值 1;标题2：价值2
```




## 配置安全性

要了解 CAS 设置 如何安全，请 [](Configuration-Properties-Security.html)查看本指南。



### 独立



```properties
#cas. 独立. 配置安全. alg = Pbe 与 5 和三重
# 卡斯. 独立. 配置安全. 提供商 = bc
# cas. 独立. 配置安全. 迭代 =
# cas. 独立. 配置安全. pw ]
```


上述设置可以使用此处的任何 [策略大纲传递给 CAS](Configuration-Management.html#overview)， 尽管将它们作为命令线或系统属性传递给 CAS 可能更安全。



### 春云

如果使用春云配置服务器，则通过春云加密和解密配置。



```properties
#spring.cloud.配置.服务器.加密.启用]真实

#加密.钥匙存储.位置文件//等/cas/卡斯康菲格服务器.jks
# 加密.密钥存储.密码=密钥存储密码
# 加密
.
```




## 云配置总线

CAS 使用春云总线在分布式部署中管理配置。 春云总线将 分布式系统的节点与轻量级消息中间商连接。



```properties
#春天. cloud. bus. 启用] 假
# 春天. 云. bus. 刷新. 启用] 真实
[ 春天. 云. 云. 巴士. 启用] 真实
[ 春天. 云. bus. 目的地] 卡斯云巴士
# 春天. 云. bus. ack. 启用] 真实
```


要了解有关此主题的更多内容，请 [](Configuration-Management.html)查看本指南。



### 兔子

通过 [兔子MQ](http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_rabbitmq_binder)向集群中的其他节点 广播CAS配置更新。



```properties
[春天. 兔子. 主机]
[ 春天. 兔子. port]
[ 春天. 兔子. 用户名]
[ 春天.

. ] 或以上所有行
[ 春天. 兔子. 地址]
```




### 卡 夫 卡

通过 [卡夫卡](http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_apache_kafka_binder)向集群中的其他节点 广播 CAS 配置更新。



```properties
#春天. 云. 流. 绑定. 输出. 内容类型 / 应用程序 / 杰森
# 春天. 云. 流. 卡夫卡. 宾德. zkNodes]...
#春天. 云. 流. 卡夫卡. 宾德. 经纪人]...
```




## 嵌入式容器

下列属性与与 CAS 一起运输的嵌入式容器相关。



```properties
服务器.服务器.服务.上下文路径]/cas
服务器.port=8443
服务器。ssl.key存储/文件/cas/钥匙店
服务器。ssl.key
存储-密码=更改服务器.sl.key-密码=更改

#服务器.sl.启用=真实
#服务器.sl.密码=
#服务器
.key.key商店提供商
.key商店类型+
服务器.sl.协议

#服务器.max-http-标题大小=2097152
#服务器.使用-前方标题=真实
#服务器.连接超时=20000
```




### X.509 客户身份验证



```properties
#服务器.sl.信任商店]
# 服务器.ssl.信任商店-密码]
# 服务器.sl.信任商店提供商]
# 服务器. sl.信任商店类型 ]
# 服务器. sl. 客户端 - 身份验证需要|没有|要
```


客户端身份验证类型支持以下值：

| 类型   | 描述                   |
| ---- | -------------------- |
| `需要` | 客户端身份验证是必要和强制性的。     |
| `没有` | 不需要客户身份验证。           |
| `要`  | 客户端身份验证是需要的，但不是强制性的。 |




### 嵌入式码头容器

以下设置会影响嵌入式码头容器的运行时间行为。



```properties
*服务器.jetty.接受者]-1       

# 服务器.jetty.访问日志.附录=假
# 服务器.jetty.访问日志.自定义格式]
# 服务器. jetty. 访问日志. 启用= 假
# 服务器. 码头 .访问日.文件日期格式]
[ 服务器. jetty. 访问日" 文件名]
[ 服务器. jetty. 访问日" 格式]
# 服务器. jetty. 访问日志. 忽略路径]
# 服务器。 码头.访问日志.保留期=31

#服务器.jetty.连接-空闲-超时=
#服务器.max-http 格式后大小=200000B
#服务器.jet .max线=200
#服务器.jetty.min 线程=8
# 服务器.jetty.选择器=-1
# 服务器.jetty.线程-空闲-超时=-1
```




### 嵌入式阿帕奇汤姆卡特容器

以下设置会影响嵌入式阿帕奇 Tomcat 容器的运行时间行为。



```properties
#服务器. tomcat. 基于构建 / tomcat

# 服务器. tomcat. 访问日志启用] 真实
# 服务器. tomcat. 访问日志. 模式]%t %a "%r" %s （%D 毫秒）
.log

.log 服务器.汤姆卡特.max-http后大小=20971520
#服务器.tomcat.max线程=5
# 服务器. tomcat. 端口标题 ] X 转发端口
# 服务器. tomcat. 协议标题 # X 转发 - 普罗托
# 服务器. tomcat. 协议标题 - https - 价值 = https
# 服务器. tomcat. 远程 ip 标题 = X 转发 -
# 服务器. tomcat. uri 编码 = Utf - 8

# cas. 服务器. tomcat. 服务器名称 ] 阿佩雷奥卡斯
```




#### 赫特普代理

如果您决定在嵌入的 Tomcat 容器和非安全端口上运行 CAS，无需任何 SSL 配置， 但希望自定义与运行端口相连的连接器配置（即 `8080`），以下设置如下：



```properties
#cas. 服务器. tomcat. http - 代理. 启用] 真实
# cas. 服务器. tomcat. http 代理. 安全] 真实
# 卡斯. 服务器.
. -代理. 计划] https
# cas. 服务器. tomcat. http - 代理. 重定向端口]
# cas. 服务器. tomcat. http - 代理. 代理端口]
# cas. 服务器. tomcat. http 代理. 属性名称 = 属性价值
```




#### 赫特普

除了链接到 `服务器.port` 设置的配置 外，还可启用嵌入式 Tomcat 容器的 HTTP 连接。



```properties
#卡斯. 服务器. 汤姆卡特. http. port = 8080
# 卡斯. 服务器. 汤姆卡特. http. 协议 . org. apache. 土狼. http11. htt p11Nio 普罗托科尔
# cas. 服务器. tomcat. http. 启用] 真实
# cas. 服务器. tomcat. http. 属性. 属性名称 = 属性价值
```




#### 阿杰普

启用嵌入式汤姆卡特容器的 AJP 连接，



```properties
#cas. 服务器. tomcat. ajp. 安全 = 假
# 卡斯. 服务器. tomcat. ajp. 启用 ] 假
# 卡斯. 服务器. 汤姆卡特. ajp. 代理端口 = - 1
# 卡斯. 服务器. 汤姆卡特. ajp. 协议=AJP/1.3
# 卡斯.服务器.汤姆卡特.ajp.不同步超时=5000
# 卡斯.服务器.汤姆卡特.ajp.计划]http
# 卡斯.服务器.tom 卡特. ajp.max 后大小 = 20971520
# 卡斯. 服务器. tomcat. ajp. port = 8009
# 卡斯. 服务器. tomcat. ajp. 启用查找 = 假
# cas. 服务器. tomcat. ajp. 重定向端口] - 1
# 卡斯. 服务器. 汤姆卡特. ajp. 允许跟踪 = 假
# cas. 服务器. tomcat. ajp. 属性. 属性名称
```




#### SSL 阀门

Tomcat SSLValve 是一种从 SSL 代理（如 HAProxy 或 BigIP F5）获得客户端证书的方法， 通过 HTTP 标头在 Tomcat 前面运行。 如果启用此功能，请确保代理 确保此标题不会源自客户端（例如浏览器）。



```properties
#cas. 服务器. tomcat. ssl - 阀门. 启用 = 假
# 卡斯. 服务器. 汤姆卡特. sl - 阀门. sl - 客户端 - 证书 - 标题 ] ssl_client_cert
# 卡斯. 服务器. 汤姆卡特. ssl - 阀门. sl - 密码 - 标题 [ssl_cipher
] 卡斯. 服务器. tomcat. ssl - 阀门. sl - 会话 - id - 头 ] ssl_session_id
# 卡斯. 服务器. 汤姆卡特. sl - 阀门. sl - 密码 - 用户键大小 - ssl_cipher_usekeysize
```


示例 HAProxy 配置 （片段）： 配置 SSL 前端与证书可选， 重定向到 cas， 如果证明提供， 把它放在头。



```
前端网络 vip
  绑定 192.168.2.10：443 ssl crt / var / lib / 哈普罗西 / 证书 / www. 示例.com. pem ca - 文件/var/lib/哈普罗西/证书/ca.pem验证可选的
  模式http
  如果 ssl_fc_sni { www.example.com }
  al空路径/
  htt 请求重定向位置/cas/如果空路径w-cert
  http请求的转头ssl_client_cert除非 { ssl_fc_has_crt }
  http请求设置标题ssl_client_cert -----BEGIN\证书-----\% [ssl_c_der，base64]\-----end\证书-----\如果 { ssl_fc_has_crt }
  个acl卡斯卡路径path_beg-i/cas
  重新qadd X转发-原：\https
  use_backend cas池，如果cas-path

后端套管池
  选项httpclose
  选项转发
  曲奇服务器服务器cas插入间接非缓存
  服务器cas-1 192.168.2.10：8080检查cookie cas-1
```




#### 扩展访问日志阀

启用嵌入式 Tomcat 容器的 [扩展访问日志](https://tomcat.apache.org/tomcat-9.0-doc/config/valve.html#Extended_Access_Log_Valve) 。



```properties
#cas. 服务器. tomcat. ext 访问日志. 启用 = 假
# cas. 服务器. tomcat. ext 访问日志. 模式 = c - ip s - ip cs - uri sc 状态时间 x - 线程名 x - h （安全） x - h （远程 [
] 卡斯. 服务器. tomcat. ext 访问日志. 后缀 = .log
# cas. 服务器. tomcat. ext 访问日志. 前缀 ] localhost_access_extended
# cas. 服务器. tomcat. ext 访问日志. 目录]
```




#### 重写阀门

启用嵌入式 Tomcat 容器的 [重写阀](https://tomcat.apache.org/tomcat-9.0-doc/rewrite.html) 。



```properties
#cas.服务器.汤姆卡特.重写阀门.位置=类路径：//容器/汤姆卡特/重写.配置
```




#### 基本身份验证

启用嵌入式阿帕奇汤姆卡特的基本身份验证。



```properties
#cas. 服务器. tomcat. 基本 - authn. 启用] 真正的
# cas. 服务器.[0]. 基本 - authn. 安全角色  = 管理
# cas. 服务器.[0]. auth - 角色 [ 管理员
# 卡斯. 服务器. tomcat. 基本 - 奥森. 模式[0]= / *
```




#### 阿帕奇便携式运行时间 （APR）

Tomcat 可以使用阿帕奇便携式运行时间</a> ，提供卓越的 可扩展性、性能和更好地集成本地服务器技术。</p> 



```properties
#cas. 服务器. tomcat. 4 月启用了 ] 假

# 卡斯. 服务器. 汤姆卡特. apr. ssl 协议]
# 卡斯. 服务器. 汤姆卡特. a4. sl 验证深度 = 10
# cas .服务器. tomcat. a4. sl 验证 - 客户端] 需要
# cas. 服务器. tomcat. a4. sl - 密码 - 套件 ]
# cas. 服务器. 汤姆卡特. apr. sl - 禁用 - 压缩=假
#cas.服务器.tomcat.a4.ssl-荣誉密码订单=假

#卡斯.服务器.tomcat.a4.ssl证书链文件]
#cas.服务器 .汤姆卡特. 4 月 ssl - ca - 证书文件]
# cas. 服务器. tomcat. a4. sl - 证书键文件]
# 卡斯. 服务器. 汤姆卡特. apr. sl - 证书文件]
```


启用 APR 需要以下 JVM 系统属性，该属性指示 APR 库二进制文件的位置（即 `/本地/选择/猫本地/利伯`）：



```bash
-贾瓦.图书馆.路径=/路径/到/汤姆卡特本地/图书馆
```


APR 连接器可以分配一个 SSL 主机Config元素：



```properties
#cas. 服务器. tomcat. a4. sl - 主机 - 配置. 启用] 假
# cas. 服务器. tomcat. ap. ssl - 主机 - 配置. 撤销启用 = 假
# cas. 服务器. tomcat. a4. sl - 主机 - config.ca - 证书文件 ] 假
# 卡斯. 服务器. tomcat. 配置. 主机名称 ]
# cas. 服务器. tomcat. a4. ssl - 主机 - 配置. sl 协议]
# cas. 服务器.

。

#cas.server..[0].证书文件
.cas.服务器.tomcat.[0].证书密钥文件]
#cas. 服务器. tomcat. a4. sl - 主机配置. 证书[0]. 证书键密码]
# cas. 服务器. tomcat. a4. sl - 主机配置. 证书[0]. 证书链文件]
# cas. 服务器. tomcat. a4. sl - 主机 - 配置. 证书[0]。
```




#### 连接器 IO



```properties
# cas.server.tomcat.socket.app 读 buf 大小 = 0
# cas.server.tomcat.socket.app 写 buf 大小 = 0
# cas. 服务器. tomcat. 插座. 缓冲池 = 0
# cas. 服务器. tomcat. 插座.性能-连接-时间=-1
#cas.服务器.tomcat.插座.性能延迟=-1
# cas.服务器.tomcat.插座.性能带宽=-1
```




#### 会话聚类 & 复制

启用内存会话复制以复制 Web 应用会话三角洲。 

| 聚类类型 | 描述                                                                                                                                        |
| ---- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| `违约` | 通过多播发现发现群集成员，并可选择地使用 `聚类成员`静态定义的群集成员。 [简单与姆卡斯特服务](http://tomcat.apache.org/tomcat-9.0-doc/cluster-howto.html)                             |
| `云`  | 用于库伯涅茨，通过访问库伯涅茨 API 或对库伯涅特斯服务的成员进行 DNS 查找来发现成员。 [文档](https://cwiki.apache.org/confluence/display/TOMCAT/ClusteringCloud) 目前是轻的，请参阅详细信息代码。 |


| 会员提供商     | 描述                                                                                                                                                                                                                                                                                                                                                                           |
| --------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `库贝内茨`    | 使用 [库伯涅茨 API](https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/membership/cloud/KubernetesMembershipProvider.java) 在部署中查找其他吊舱。 通过容器中设置的环境变量中的信息发现和访问 API。 KUBERNETES_NAMESPACE环境变量用于查询名称空间中的吊舱，它将将该命名空间中的其他吊舱视为潜在的群集成员，但可以使用用作 [标签选择器](https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#api)的KUBERNETES_LABELS环境变量进行筛选。 |
| `登斯`      | 使用 [DNS 查找](https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/membership/cloud/DNSMembershipProvider.java) 查找由DNS_MEMBERSHIP_SERVICE_NAME环境变量指定的 DNS 名称后面的群集成员的地址。 在库贝内茨工作， 但不依赖库伯内特斯。                                                                                                                                                          |
| `会员提供者` 类 | 使用您选择的 [会员提供商实施](https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/MembershipProvider.java) 。                                                                                                                                                                                                                                                     |


大多数设置适用于 `默认` 聚类类型，该类型要求成员通过 `聚类成员进行定义，如果多播发现不起作用，则` 。 `云提供器` 设置适用于云</code> 类型的 `。</p>

<pre><code class="properties">#cas.服务器.汤姆卡特.集群.启用=假
#卡斯.服务器.汤姆卡特.集群.集群类型=默认|云
# cas. 服务器. tomcat. 集群. 集群成员 = ip 地址： 端口： 索引
• cas.server.tomcat.clustering.cloud 会员提供商 = 库伯内特|dns|[会员提供者内联类名]（https://github.com/apache/tomcat/blob/master/java/org/apache/catalina/tribes/MembershipProvider.java）
# cas. 服务器. tomcat. 集群. 失效会话关闭 = 假
# cas. 服务器. tomcat. 集群. 频道发送选项 = 8

# cas. 服务器. tomcat. 集群. 接收器端口 = 40
# cas. 服务器. tomcat. 集群. 接收器时间 出 [5000
# cas. 服务器. tomcat. 集群. 接收器 - 最大线程] 6
# cas. 服务器. tomcat. 集群. 接收器地址 = 自动
# cas. 服务器. tomcat. 集群. 接收器 - 自动绑定 = 100

# cas. 服务器. tomcat. 集群。 会员端口=45564
#cas.服务器.tomcat.聚类.会员地址=228.0.0.4
# cas.服务器.comcat.集群.会员频率=500
# cas.服务器.tomcat.集群.会员-下降时间=3000
#cas. 服务器. tomcat. 集群. 会员恢复启用] 真正的
# cas. 服务器. tomcat. 集群. 会员本地循环禁用 = 假
# cas. 服务器. tomcat. 集群. 会员恢复计数器 = 10

# cas. 服务器. tomcat. 集群. 经理类型 = 德尔塔|备份
`</pre> 



## CAS服务器

识别 CAS 服务器。 `名称` 和 `前缀` 始终是必需的设置。

CAS主机自动附加到CAS生成的票证ID。 如果未指定，CAS 会自动检测并使用一个。



```properties
#cas.服务器.名称=_/cas.示例.org：8443
# cas.服务器.前缀//cas.示例.org：8443/cas 
# cas.服务器.示例.org
= cas.host.name
```




## 会话复制

某些 CAS 功能的会话复制的控制方面，如 OAuth 或 OpenID 连接， 允许会话和身份验证配置文件数据作为 Cookie 与客户端一起保存。

`cas. 会话复制. cookie`的配置密钥下 </a> 此处 常见的 cookie 属性。</p> 



```properties
#cas. 会话复制. 自动配置 - 曲奇路径] 真实
```




## 卡斯横幅

启动时，CAS 将显示横幅以及一些诊断信息。 为了跳过这一步骤并进行总结，设置系统属性 `-DCAS_BANNER_SKIP=真实`。



### 更新检查

CAS 也可能有条件地配置报告，作为横幅的一部分，是否可用于升级的较新的 CAS 版本。 此检查在默认情况下已关闭，并且可能以 `-DCAS_UPDATE_CHECK_ENABLED=真实`的系统属性启用。



## 执行器管理端点

以下属性描述了 CAS `/执行器` 端点的访问控制和设置，该端点为 CAS 软件提供行政功能和监督。

要了解有关此主题的更多内容，请 [](../monitoring/Monitoring-Statistics.html)查看本指南。



```properties
#管理. 端点. 默认启用] 真实
# 管理. 端点. web. 基础路径= / 执行器

# 管理. 端点. web. 曝光. 包括信息， 健康， 状态， 配置 阿达达塔
# 管理. 端点. web. 曝光. 排除]

# 管理. 端点. jmx. 曝光. 排除 ]
[ 管理. 端点. jmx. 曝光. 包括]
```




### 基本身份验证安全性

基本身份验证凭据可以通过以下设置进行定义：



```properties
#春天. 安全. 用户. 名称 ] 卡瑟
# 春天. 安全. 用户. 密码]
# 春天. 安全. 用户. 角色
```




### JAAS 身份验证安全

端点安全的 JAAS 身份验证可以通过以下设置进行配置：



```properties
#cas.监视器.端点.jaas.在启动时刷新配置]真实
# cas.监视器.终点.jaas.登录配置文件/等/cas/配置/jaas.
# cas.监视器.端点.jaas.登录上下文名称[CAS
```




### LDAP 身份验证安全性

此功能的共享 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.监视器.端点.ldap`。

端点安全的 LDAP 身份验证可以通过以下设置进行附加配置：



```properties
#cas. 监视器. 端点. ldap - authz. 角色属性 = 乌吉德
# 卡斯. 监视器. 端点. ldap. ldap - authz. 角色前缀 ] ROLE_
# 卡斯. 监视器. 结束 点. ldap. ldap - authz. 允许多重结果 = 假
# cas. 监视器. 端点. ldap. ldap - authz. 组属性 =
# cas. 监视器. 端点. ldap .ldap - authz. 组前缀]
# 卡斯. 监视器. 端点. ldap. ldap - authz. 组过滤器]
# cas. 监视器. 端点. ldap - authz .组基 dn]
# cas. 监视器. 端点. ldap. ldap - authz. 基地 - dn]
# 卡斯. 监视器. 端点. ldap. ldap - authz. 搜索过滤器]
```




### JDBC 身份验证安全性

此功能的共享数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥 `cas.监视器.端点.jdbc`下提供。

可以通过以下设置额外配置端点安全的 JDBC 身份验证：



```properties
#卡斯. 监视器. 端点. jdbc. 角色前缀]
# 卡斯. 监视器. 端点. jdbc. 查询]
```


此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.监视器.端点.jdbc`。



### 启用端点

要确定端点是否可用，所有端点的计算顺序如下：

1. `启用了单个端点的` 设置（即 `信息`）请参阅 CAS 设置，如下所示：



```properties
#管理。终点。<endpoint-name>。启用=真实
```


2. 如果未定义，则从 CAS 设置中咨询全球端点安全性。
3. 如果未定义，则会咨询 CAS 端点的默认内置设置，这在默认情况下通常 `虚假` 。

一些可用的端点ids [应列在此处](../monitoring/Monitoring-Statistics.html)。

端点也可以映射到自定义任意端点。 例如，要将 `健康` 终点重新 `健康检查`， 指定以下设置：



```properties
#管理.端点.web.路径映射.健康=健康检查
```




### 健康终点

`健康` 端点也可以配置为使用 `管理.端点.健康.显示详细信息，通过以下条件` ：

| 网址    | 描述                                      |
| ----- | --------------------------------------- |
| `从不`  | 切勿显示健康监测器的详细信息。                         |
| `总是`  | 始终显示健康监测器的详细信息。                         |
| `授权时` | 详细信息仅显示给授权用户。 授权角色可以使用 `管理。端点.健康.角色`配置。 |




```properties
#管理.终点.健康.显示细节]从不
```


`健康` 端点的结果和详细信息由一些健康指标组件产生，这些组件可以监控不同的系统，如 LDAP 连接 池、数据库连接等。 默认情况下，此类健康指标将被关闭，并且可以通过以下设置单独控制并打开：



```properties
#管理。健康。<name>.启用]真实
#管理.健康.默认启用=错误 
```


鉴于 CAS 具有相应的功能，可提供以下健康指标名称：

| 健康指标               | 描述                                   |
| ------------------ | ------------------------------------ |
| `记忆健康指示器`          | 关于CAS JVM内存使用健康状况的报告等。               |
| `系统健康指示器`          | 关于 CAS 服务器系统健康状况的报告。（负载、工作时间、堆、CPU等） |
| `会话健康指示器`          | 关于 CAS 门票和 SSO 会话使用的健康状况的报告。         |
| `双安全健康指示器`         | 关于双安全 ABI 健康状况的报告。                   |
| `埃卡切健康指示器`         | 关于埃卡奇缓存健康状况的报告。                      |
| `榛子健康指示器`          | 关于黑兹尔卡斯特缓存健康状况的报告。                   |
| `数据来源健康指示器`        | 关于 JDBC 连接的健康状况的报告。                  |
| `汇集的Ldap连接工厂健康指示器` | 关于LDAP连接池健康状况的报告。                    |
| `医疗医疗指示器`          | 报告关于 Memcached 连接的健康状况。              |
| `蒙戈健康指示器`          | 关于蒙古数据库连接健康状况的报告。                    |
| `萨姆注册服务梅塔达塔健康指示器`  | 关于 SAML2 服务提供商元数据源健康状况的报告。           |




### 端点安全

CAS 激活的全球端点安全配置可控制在配置密钥 `cas.监视器.端点.端点。<endpoint-name>`. 有一个名为 `默认`  的特殊端点，如果在 CAS 设置中未定义，则用作控制所有端点安全的快捷方式。 可以通过一个特殊的登录表单访问网络上的端点 ，其访问和存在可以通过：



```properties
#cas.监视器.端点。支持形式登录=错误
```


请注意，在应用任何安全性之前，必须首先启用任何单个端点。 使用以下设置控制所有端点的安全性：



```properties
# ${configuration-key}.
[0]的角色=# ${configuration-key}.必要权威[0]=
= ${configuration-key}.必用 ${configuration-key}地址[0]=
.访问[0]=许可|匿名|拒绝|认证|角色|权威|IP_ADDRESS
```


每个端点允许以下访问级别：

| 类型           | 描述                       |
| ------------ | ------------------------ |
| `许可证`        | 允许打开访问端点。                |
| `匿名`         | 允许匿名访问终点。                |
| `否认`         | 违约。 阻止对终点的访问。            |
| `认证`         | 需要经过身份验证的端点访问。           |
| `角色`         | 需要通过身份验证访问端点以及角色要求。      |
| `柄`          | 需要经过身份验证的端点访问以及权威要求。     |
| `IP_ADDRESS` | 需要使用 IP 地址集合对端点进行身份验证访问。 |




### 弹簧启动管理服务器

要了解有关此主题的更多内容，请 [](../monitoring/Configuring-Monitoring-Administration.html)查看本指南。



```properties
•春天.靴子.管理员.客户.url=https：//靴子管理员.示例.org：8444
# 春天.靴子.管理员.客户端.实例.服务-基础 url]${cas.server.prefix}
# 弹簧.启动.admin.case.名称=Apereo CAS
# 万一春季启动管理员端点受到保护 通过基本的 authn：
[ 春天. boot. admin. 客户端. 用户名]
[ 春天. boot. admin. admin. 客户端. 密码]
] 万一 Cas 端点通过基本的 authn 受到保护：
= spring.boot.admin.client.instance.metadata.user.name ]
• 弹簧. boot. 管理员.
```




### 爪哇梅洛迪

要了解有关此主题的更多内容，请 [](../monitoring/Configuring-Monitoring-JavaMelody.html)查看本指南。



```properties
#javamelody. 启用] 真正的
# 贾瓦梅洛迪. 排除数据来源 = 一，二， 等
# javamelody. 春天监控启用 ] 真正的
# 贾瓦梅洛迪. init 参数 .log]真正的
#贾瓦梅洛迪.init参数.url-排除模式]（/网络贾尔斯/。*|/css/。*|/图像/。 |/字体/。*|/js/。*）
# javamelody.init 参数.监控路径/监控

# 通过 IP 常规表达模式控制访问
# javamelody. init 参数. 允许添加器模式].
# 通过基本 Authn
控制访问 # javamelody. init - 参数. 授权用户 = 管理员： pwd
```




## 网络应用会话

控制 CAS 网络应用会话行为 ，因为它由底层伺服容器发动机处理。



```properties
#服务器.服务器.会话.超时=PT30S
#服务器.服务器.服务.会话.cookie.http仅限真实
#服务器.服务器.服务器.会话.跟踪模式=COOKIE
```




## 视图

控制 CAS 应如何对待视图和其他 UI 元素。

要了解有关此主题的更多内容，请 [](../ux/User-Interface-Customization-Views.html)查看本指南。



```properties
#春天.百里香叶.编码=UTF-8

#控制视图是否应由CAS缓存。
#打开时，临时查看的机会不会自动
#在重新启动之前由CAS拾取。 预计会有小的增量性能
#改进。
#春天.百里香叶.缓存]真正的

#指示CAS在下面的位置定位视图。
#此位置可以外部化到目录外部
#cas 网络应用程序。
#spring.百里香叶.前缀/类路径/模板/

定义默认网址，如果身份验证请求中没有提供服务，CAS 可能会将其重定向到该网址。
#cas.view.默认重定向 url=/www.github.com

# CAS 视图可能位于 web 应用上下文之外
之外的以下路径，此外还有指定
的前缀 ， 以上通过 Thymeleaf 处理。
#cas.视图.模板前缀[0]=file:///etc/cas/templates
```




## 自定义登录字段



```properties
#cas.视图。自定义登录形式字段。[field-name].消息捆绑键=
#cas.view.自定义登录表单字段。[field-name]。要求]真正的
#cas.view.自定义登录形式字段。[field-name]。转换器=
```




### CAS v1



```properties
#cas. view. cas1. 属性渲染器类型 = 默认|VALUES_PER_LINE
```




### 卡斯 v2



```properties
#cas.view.cas2.v3 向前兼容=假
# cas.view.cas2.成功=协议/2.0/卡斯服务验证成功
# cas.view.cas2.失败=协议/2.0/casServ 验证失败
# cas. view. cas2. 代理. 成功= 协议 / 2.0 / 卡斯普罗西成功视图
# cas. view. cas2. 代理. 失败 = 协议 / 2.0 / 卡斯普罗西失败视图
```




### CAS v3



```properties
#cas.view.cas3.成功=协议/3.0/卡斯服务验证成功
#cas.view.cas3.失败=协议/3.0/卡斯服务验证失败
# cas.view.cas3.属性渲染器类型=默认|内嵌
```




### 宁静的视图

通过 REST 控制 CAS 视图的解决。 此功能的 RESTful 设置 可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.view.rest`。



## 伐木

控制 CAS 测井配置的位置和其他设置。 要了解有关此主题的更多内容，请 [](../logging/Logging.html)查看本指南。



```properties
• 登录.配置文件/cas/log4j2.xml
# 服务器.服务器.服务.上下文-parameters.is-log4j-自动初始化禁用=真实

#cas.log.mdc 启用=真实

= 通过属性控制日志水平
# 登录.level.org.apereo.cas=DEBUG
```


要禁用日志消毒，请使用系统属性 `CAS_TICKET_ID_SANITIZE_SKIP]真实`启动容器。



## 方J配置



```properties
#春天. aop. 自动] 真正的
# 春天. aop. 代理目标类] 真实
```




## 身份验证属性

由主要解决过程检索到的一组身份验证属性 ，通常通过 [人员目录的某些组件](../integration/Attribute-Resolution.html) 从多个属性源检索，除非具体身份验证方案另有说明。

如果定义了多个属性存储库源，则它们 添加到列表中，并且其结果被缓存和合并。



```properties
#cas.authn.属性存储库.到期时间=30
# cas.authn.属性存储库.到期时间单位=分钟
# cas.authn.属性存储库.最大缓存大小=10000
# cas.authn.属性存储库.合并=替换|添加|多重价值|没有
# 卡斯. 奥森. 属性存储库. 聚合 = 合并|级 联
```

<div class="alert alert-info"><strong>记住这一点</strong><p>请注意，在某些情况下，
CAS 身份验证能够在同一身份验证请求中检索和解决身份验证源中的属性，这将
无需配置单独的属性存储库，特别是当身份验证和属性源相同时。
当来源不同时，或者当需要处理更高级的属性
分辨率时，应需要使用单独的存储库，例如级联、合并等，
<a href="../installation/Configuring-Principal-Resolution.html">请参阅本指南</a> 了解更多信息。</p></div>

所有源的属性均在各自的单个块中定义。 CAS不关心属性的源所有者。 它找到他们的地方，他们可以找到，否则，它继续前进。 这意味着某些数量的属性可以通过一个源来解决， 的其余属性可以通过另一个来源来解决。 如果各来源之间有共性，合并应决定最终结果和行为。

纯英语的故事是：

- 我有一堆属性，我想解决的认证本金。
- 我有一堆来源，其中说属性被检索。
- 弄清楚。

请注意，属性存储库源（如果/定义时）以特定顺序执行。 在进行属性合并时，必须考虑这一点。 默认情况下，执行顺序（定义时）如下，但可以按来源进行调整：

1. 阿尔达普
2. 京城
3. 杰森
4. 槽的
5. [互联网2 组](http://www.internet2.edu/products-services/trust-identity/grouper/)
6. 休息
7. 脚本
8. 存根/静态

请注意，如果没有定义 *明确的* 属性映射，则 CAS 可以从属性存储库源检索记录 上的所有允许属性，并提供给委托人。 另一方面， 如果定义了显式属性映射，则只检索 *映射* 属性。



### 多映射属性

属性可能允许几乎重命名和重新映射。 例如，以下定义尝试 从属性源中获取属性 `uid` ，并将其重命名为 `用户Id`：



```properties
#卡斯. 奥森. 属性存储库。[type-placeholder].属性.uid=用户ID
```




### 合并策略

当从多个来源找到相同的属性时，以下合并策略可用于解决冲突：

| 类型     | 描述                          |
| ------ | --------------------------- |
| `取代`   | 覆盖现有属性值（如果有的话）。             |
| `加`    | 保留现有属性值（如果有）并忽略分辨率链中后续源中的值。 |
| `多重价值` | 将所有值合并到单个属性中，本质上是创建一个多值属性。  |
| `没有`   | 不要合并属性，只使用身份验证期间检索的属性。      |




### 聚合策略

当定义多个属性存储源以获取数据时，可使用以下聚合策略来解决和合并 属性：

| 类型    | 描述                            |
| ----- | ----------------------------- |
| `合并`  | 违约。 按顺序查询多个存储库，并将结果合并到单个结果集中。 |
| `级 联` | 与上文相同：每个查询的结果将传递到下一个属性存储库源。   |




### 存根

需要映射到硬编码值的静态属性属于此处。



```properties
#cas.authn.attribute-repository.stub.id]

# cas. authn. 属性存储库. stub. 属性. uid = uid
# 卡斯. 奥森. 属性存储库. 存根. 属性. 显示名 = 显示娜
# cas. authn. 属性存储库. stub. 属性. cn = 共同名
# cas. authn. 属性存储库. stub. 属性. 属性. 关联 = 组成员
```




### 阿尔达普

如果您希望直接和单独地从 LDAP 源检索属性，则此 功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 以下 配置密钥 `cas.authn.属性存储库.ldap[0]`。



```properties
#cas. authn. 属性存储库. ldap[0]. id]
# 卡斯. authn. 属性存储库. ldap[0]. 订单 = 0

# cas. authn. 属性存储库. ldap[0]. 属性. uid = uid
# 卡斯. .属性存储库.ldap[0].属性.显示名称=显示名称
# cas.authn. 属性存储库.ldap[0].属性.cn=共同名称
# cas.authn.属性存储库.ldap[0].属性.关联=组
```


要获取和解决带有标记/选项的属性，请考虑将映射属性标记为：



```properties
#卡斯. 奥森. 属性存储库. ldap[0]. 属性. 从属关系：
```




### 槽的

如果您希望直接和单独检索 Groovy 脚本中的属性，则 以下设置相关：



```properties
#cas.authn.属性存储库.沟[0].位置=文件/cas/属性.凹槽
#cas.authn.属性存储库.沟[0]。 敏感=虚假
#cas.authn.属性存储库.沟[0].订单=0
#卡斯.奥森.属性存储库.沟[0].id=
```


Groovy 脚本可设计为：



```groovy
导入java.util.*

d地图<String, List<Object>> 运行（最终对象。。。args）{
    def用户名=args[0]
    dex属性=args[1]
    d def记录器=args[2]
    d属性=args[3]
[4]

    帐户。 记录器。debug（[}]：收到的uid是[{}]， 这个.class.简单名称，uid）
    返回[用户名：[uid]，喜欢：["奶酪"，"食物"]，id：[1234，2，3，4，5]，另一个："属性"]
]
```




### 杰森

如果您希望直接和单独地从静态 JSON 源检索属性，则 以下设置相关：



```properties
#cas.authn.属性存储库.json[0].位置/文件/等/卡斯/属性存储库.json
# cas.authn.属性存储库.json[0].订单=0
# cas.奥森.属性存储库.json[0].id]
```


文件的格式可能是：



```json
{
    "用户1"：{
        "第一个名字"：["Json1"]
        "姓氏"：["一"]
    [，
    "用户2"：{
        "第一个名字"：["Json2"]，
        "员工"，"学生"]
    [
]
```




### 休息

从RESE端点检索属性。 此功能 的 RESTful 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥 `cas.authn.属性存储库.rest[0]`。



```properties
#cas. authn. 属性存储库. rest[0]. 订单 = 0
# 卡斯. authn. 属性存储库. 休息[0]. id]
# cas. authn. 属性存储库. rest[0].
```


身份验证用户 ID 以请求参数的形式 `用户名`下传递。 预计响应 为 JSON 地图：



```json
{
  "名字"："约翰史密斯"，
  "年龄"：29岁，
  "消息"：["msg 1"，"msg 2"，"msg 3"]
}
```




### Python/爪哇脚本/格罗夫

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能被弃用，并计划在将来删除。</strong></p>
</div>

类似于 Groovy 选项，但更通用，此选项利用 Java 的原生 脚本 API 调用 Groovy、Python 或 Javascript 脚本引擎来编译预先定义的脚本以解决属性问题。 下列设置相关：



```properties
#cas.authn.属性存储库.脚本[0].位置=文件/等/cas/脚本.沟
# cas.authn.属性存储库.脚本[0].订单=0
# cas.authn.属性存储库. 脚本[0].id]
# cas. authn. 属性存储库. 脚本[0]. 案例麻木不仁 = 虚假
# cas. authn. 属性存储库. 脚本[0]. 发动机名称 = js|贪婪|巨蛇
```


虽然 Javascript 和 Groovy 应由 CAS 提供本地支持，但 Python 脚本可能需要 来按摩 CAS 配置，以将 [Python 模块](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jython-standalone%22)。

凹槽脚本可定义为：



```groovy
导入java.util.*

地图<String, List<Object>> 运行（最终对象。。。args）{
    dufid=args[0]
    def记录器=args[1]

    记录器.debug（"Groovy事情发生得很好与UID：{}"，uid）
    返回[用户名：[uid]，喜欢：["奶酪"， "食物"]，ID：[1234，2，3，4，5]，另一个："属性"]
}
```


爪哇脚本可定义为：



```javascript
函数运行（uid，记录器）{
    打印（"事情正在正常发生"）
    记录器

    。 这是这样做的一种方式
    瓦尔贾瓦奥比=新的爪哇岛（组织.yourorgname.你的包装名称）;
    与（javaObj）{
        瓦尔奥比弗罗姆雅娃=JavaClassIn包装。一些统计记事机（uid）;
    }

    瓦尔地图={}：
    地图["attr_from_java"]=objFromJava.get东西（）;
    地图["用户名"]=uid;
    地图["赞"]="奶酪"：
    地图["id"]=[1234，2，3，4，5];
    地图["另一个"]="属性";

    返回地图;
}
```




### 京城

从 JDBC 源检索属性。 此功能的数据库设置 可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥 `cas.authn.属性存储库.jdbc[0]`下。



```properties
#cas. authn. 属性存储库. jdbc[0]. 属性. uid = uid
# 卡斯. authn. 属性存储库. jdbc[0]. 属性. 显示名称 = 显示名称
# 卡斯. 奥森。 属性存储库. jdbc[0]. 属性. cn = 共同名
# 卡斯. authn. 属性存储库. jdbc[0]. 属性. 关联= 组成员

# 卡斯. 奥森. 属性 - 存储库. jdbc[0]. 单行= 真实
# 卡斯. 奥森. 属性存储库. jdbc[0]. 订单 = 0
# 卡斯. 奥森. 属性存储库. jdbc[0]. id •
# 卡斯. authn. 属性存储库. jdbc[0]. 要求所有属性] 真实
# 卡斯. 奥森. 属性存储库. jdbc[0]. 案例规范化] 无|更低|上
# 卡斯. 奥森. 属性存储库. jdbc[0]. 查询类型] or|和
#cas.authn.属性存储库.jdbc[0].case-麻木不仁查询属性=用户名

#仅当有许多行的映射到一个用户
时使用 # cas. authn. 属性存储库. jdbc[0]. 列映射. 列 - attr 名称 1] 列 AtrValue1
# 卡斯. 奥森. 属性存储库. jdbc[0][0].列映射.列-attr-名称2]列"价值2"
#cas.authn.属性存储库.jdbc.列映射。列-attr-名称3=列-属性-值 3

# 卡斯. 奥森. 属性存储库. jdbc[0].sql = 选择 * 从表中 {0}
# 卡斯. 奥森. 属性存储库. jdbc[0]. 用户名 = uid
```




### 石斑鱼

此选项读取了 [给定 CAS 本金](https://incommon.org/software/grouper/) 的 Grouper 实例中的所有组，并将它们 作为 `集团` 多价值属性下的 CAS 属性。 要了解有关此主题的更多内容，请 [](../integration/Attribute-Resolution.html)查看本指南。



```properties
#cas. authn. 属性存储库. 组[0]. 启用] 真实
# 卡斯. authn. 属性存储库.[0]. id]
# cas. authn. 属性存储库. 组[0].
```


您还需要确保 `群组.客户端.属性` 可在类路径上提供（即 `src/主/资源`） 下列配置属性：



```properties
#格勒克莱恩特. web 服务. url = http://192.168.99.100:32768/grouper-ws/servicesRest
# 格特克莱恩. web 服务. 登录 » 班德森
# 格特克莱恩. web 服务. 密码 = 密码
```




### 沙发基地

此选项将从 Couchbase 数据库中获取给定 CAS 负责人的属性。 要 了解更多关于这个主题， [请](../installation/Couchbase-Authentication.html)查看本指南。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchbase-integration-settings) 配置密钥下 `cas.authn.属性存储库.沙发库`。



```properties
#cas. authn. 属性存储库. 沙发库. 用户名属性 = 用户名
# cas. authn. 属性存储库. 沙发库. 订单 = 0
# cas.authn.attribute-repository.couchbase.id]
```




### 雷迪斯

此选项将从 Redis 数据库中获取给定 CAS 负责人的属性。 

要了解有关此主题的更多内容，请 [](../installation/Redis-Authentication.html)查看本指南。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥 `cas.authn.属性存储库.reis`下提供。



```properties
#卡斯. 奥森. 属性存储库. reis. 订单 = 0
= cas.authn.attribute-repository.redis.id]
```




### 微软 Azure 活动目录

此选项将使用微软图形 API 从微软 Azure 活动目录获取属性。

可用以下设置：



```properties
# cas.authn.attribute-repository.azure-active-directory[0].client-id=
# cas.authn.attribute-repository.azure-active-directory[0].client-secret=
# cas.authn.attribute-repository.azure-active-directory[0].client-secret=
# cas.authn.attribute-repository.azure-active-directory[0].tenant=

# cas.authn.attribute-repository.azure-active-directory[0].id=
# cas.authn.attribute-repository.azure-active-directory[0].order=0
# cas.authn.attribute-repository.azure-active-directory[0].case-insensitive=false
# cas.authn.attribute-repository.azure-active-directory[0].resource=
# cas.authn.attribute-repository.azure-active-directory[0].scope=
# cas.authn.attribute-repository.azure-active-directory[0].grant-type=
# cas.authn.attribute-repository.azure-active-directory[0].api-base-url=
# cas.authn.attribute-repository.azure-active-directory[0].attributes=
# cas.authn.attribute-repository.azure-active-directory[0].domain=
# cas.authn.attribute-repository.azure-active-directory[0].logging-level=
```




### 希博莱斯集成

要了解有关此主题的更多内容，请 [](../integration/Shibboleth.html)查看本指南。



```properties
* 卡斯. 奥森. 希布 - 伊德普. 服务器 - 乌尔普斯
```




### 默认捆绑包

如果您希望向所有应用程序（ ）发布默认属性捆绑包，并且您不想根据每个服务定义重复相同的属性， 则以下设置是相关的：



```properties
#cas.authn.属性存储库.默认属性到释放\cn，给定名、uid、从属关系
```


要了解有关此主题的更多内容，请 [](../integration/Attribute-Release.html)查看本指南。



### 协议属性

定义 CAS 是否应除 主要属性之外，还包括和发布规范中定义的协议属性。 默认情况下，当协议属性启用 版本时，将释放所有身份验证属性。 如果您希望限制哪些身份验证属性被释放，您可以使用以下设置在全球范围内更全面地控制身份验证属性。

协议/身份验证属性也可以有条件地在每个服务 的基础上发布。 要了解有关此主题的更多内容，请 [](../integration/Attribute-Release.html)查看本指南。



```properties
#cas.authn.认证属性发布.仅释放=身份验证日，来自新登录
#cas.authn.认证属性发布.从不发布=
# cas.authn.身份验证属性释放.启用=真实
```




## 主要决议

如果放置了单独的解算器，则控制默认情况下应如何构建最终本金。 此功能的主要分辨率 和人员目录设置 [此处 可用，](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥 `cas.人员目录`下。



## 属性定义

属性定义存储允许一个人描述关于必要属性的元数据， 在属性分辨率和发布过程中需要考虑特殊装饰。



```properties
#cas.人员目录.属性定义-商店.json.位置=文件/等/卡斯/配置/属性-定义.json
```




## 身份验证引擎

在执行前后控制 CAS 认证引擎的内部工作。



```properties
cas.authn. core. 凹槽认证分辨率. 位置= 文件/等/cas/配置/沟槽验证. 沟
cas. authn. 核心. 凹槽认证分辨率. 订单 = 0

cas. authn. 核心. 服务认证分辨率.
```




### 身份验证预处理



#### 槽的



```properties
#cas.authn. 核. 发动机. 格罗夫预处理器. 位置： 文件/ 等/ 卡斯 / 配置 / 沟前处理器. 沟
```


脚本本身可设计为：



```groovy
def运行（对象[]args）{
    def交易=args[0]
    def记录器=args[1]
    真正的
}

def支持（对象[]args）{
    def凭据=args[0]
    d记录器=args[1]
    真正的
}
```




### 处理后身份验证



#### 槽的



```properties
# cas. authn. core. 发动机. 格罗夫后处理器. 位置： 文件： 等 / 卡斯 / 康菲 / 格罗夫后处理器. groovy
```


脚本本身可设计为：



```groovy
def运行（对象[]args）{
    def构建器=args[0]
    def事务=args[1]
    def记录器=args[2]
    真正的
=

def支持（对象[]args）{
    def凭据=args[0]
    def记录器=args[1]
    真正的
}
```




## 身份验证策略

要了解有关此主题的更多内容，请 [](../installation/Configuring-Authentication-Components.html#authentication-policy)查看本指南。

当 CAS 尝试自动售货和验证门票时应用的全球认证策略。



```properties
#cas.authn.政策.要求-处理程序-认证-支持策略=虚假
```




### 任何

满意，如果任何处理成功。 支持尝试所有标志以避免短路 ，并尝试每个处理程序，即使一个之前成功。



```properties
#卡斯. 奥森. 政策. any. 尝试所有 = 假
# 卡斯. 奥森. 政策. 任。
```




### 都

如果并且仅当所有给定的凭据都成功认证时，才满意。 在 CAS 中，对多个凭据的支持是新的，此处理器 只有在多重身份验证的情况下才能接受。



```properties
#卡斯. 奥森. 政策. 所有. 启用] 真实
```




### 来源选择

允许 CAS 根据凭据源选择身份验证处理程序。 这允许身份验证引擎将验证凭据 的任务限制到选定的源或帐户存储库，而不是每个身份验证处理程序。



```properties
#卡斯. 奥森. 政策. 来源选择启用= 真实
```




### 独特的校长

如果且仅当请求本金尚未通过 CAS 认证时，才感到满意。 否则，身份验证事件将受阻，从而阻止多个登录。

<div class="alert alert-warning"><strong>使用情况警告</strong><p>激活此策略并非没有成本，
，因为 CAS 需要查询机票注册表和所有在场的门票，以确定当前用户是否在任何地方建立了身份验证会话。 这肯定会给部署增加性能负担。 小心使用。</p></div>

```properties
#卡斯. 奥森. 政策. 独特校长. 启用] 真实
```




### 未阻止

满意，如果只有当认证事件不被 `阻止例外`。



```properties
#卡斯. 奥森. 政策. 未预防. 启用] 真实
```




### 必填

如果且仅当指定处理程序成功验证其凭据时，才满意。



```properties
#卡斯. 奥森. 政策. req. 尝试所有 = 假
# 卡斯. 奥森. 政策. req. 处理者名称 [ 处理者姓名
] 卡斯. 奥森. 政策. req. 启用] 真实
```




### 槽的

执行凹凸不合的脚本以检测身份验证策略。



```properties
#卡斯.奥森.政策.格罗夫[0].脚本文件/等/卡斯/配置/帐户。
```


脚本可设计为：



```groovy
进口 java. util.*
进口组织. apereo. cas. 认证. 例外. *
进口 javax. 安全. auth. 登录. *

除外运行 （最终对象...args）{
    def本金=args[0]
    def记录器=args[1]

    如果（条件你可能设计（）===真实）{
        返回新的帐户禁用除（）
    =
    返回无效：
}
```




### 休息

通过 `邮政` 联系 REST 端点以检测身份验证策略。 消息主体包含 CAS 认证的本金，可用于 检查帐户状态和策略。

此功能的 RESTful 设置 可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.政策.rest[0]`。

来自 REST 端点的响应代码被翻译为：

| 法典           | 结果              |
| ------------ | --------------- |
| `200`        | 成功的身份验证。        |
| `403`， `405` | 生成 `帐户禁用例外`     |
| `404`        | 生成 `帐户未发现例外`    |
| `423`        | 生成 `帐户锁定例外`     |
| `412`        | 生成 `帐户初始`       |
| `428`        | 生成 `帐户密码必须改变例外` |
| 其他           | 生成 `失败日志初始`     |




## 身份验证限制

CAS 提供了一个设施，用于限制失败的登录尝试，以支持密码猜测和相关滥用情景。 要了解有关此主题的更多内容，请 [](../installation/Configuring-Authentication-Throttling.html)查看本指南。



```properties
#cas.authn.throttle.用户名-参数=用户名
=cas.authn.throttle.app 代码=CAS

# cas.authn.throttle.故障. 故障. 阈值=100
# cas.authn.油门.故障.代码= AUTHENTICATION_FAILED
# cas.authn.
```


此功能的调度器设置可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥 `cas.authn.油门`下提供。



### 桶4j

使用限速器和令牌存储桶处理容量规划和系统超载保护。



```properties
#cas. authn. 油门. 桶 4j. 范围在几秒钟内 ] 60
# 卡斯. 奥森. 油门.
. 容量 = 120  # 卡斯. 奥森. 油门. 桶 4j. 阻止] 真正的
# 卡斯. 奥森. 油门. 桶 4j. 透支 = 0
```




### 蒙古德布

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.audit`。 此功能使用 CAS 蒙古数据库审计设施使用的相同数据源。 



### 雷迪斯

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.audit`。 此功能使用 CAS 雷迪斯审计设施使用的相同数据源。



### 黑兹尔卡斯特

使用分布式黑兹尔卡斯特地图记录节流身份验证尝试。 此功能的黑兹尔卡斯特设置可 [此处](Configuration-Properties-Common.html#hazelcast-configuration) 配置键下 `cas.authn.throttle.哈泽尔卡斯特`。



### 数据库

查询 CAS 审计设施用于防止来自同一 IP 地址的特定用户名的连续失败登录尝试 的数据源。 

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.throttle.jdbc`。



```properties
#卡斯. 奥森. 节流. jdbc. 审计查询 ] 从COM_AUDIT_TRAIL选择AUD_DATE [
] AUD_CLIENT_IP在哪里？ AUD_USER=？ \
#和AUD_ACTION=？ APPLIC_CD=？ \
#和AUD_DATE >=？ 由AUD_DATE德斯克订购
```




### 库奇德布

查询 CAS 审计设施用于防止来自同一 IP 地址的特定用户名的连续失败登录尝试 的数据源。 此功能的 CouchDb 设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥 `cas.authn.油门`下提供。 使用此功能时，审核设施应处于同步模式。



## 自适应身份验证

控制 CAS 身份验证应如何适应传入的客户请求。 要了解有关此主题的更多内容，请 [](../mfa/Configuring-Adaptive-Authentication.html)查看本指南。



```properties
#cas. authn. 自适应. 拒绝国家]
# cas. authn. 自适应. 拒绝浏览器] 壁虎.
# cas. authn. 自适应. 拒绝 - ip 地址 = 127.

# cas. authn. 自适应. 要求 - 多因素. mfa - duo = 127. |美联航|壁虎。
```


自适应身份验证也可以对特定时间做出反应，以触发多因素身份验证。



```properties
#cas. authn. 自适应. 要求定时多因素[0]. 提供商 - id = mfa - duo
# cas. authn. 自适应. 要求定时多因素[0]. 小时或下班后 = 20
# cas. authn. 自适应. 要求定时多因素[0][0]
。
```




### IP 地址智能

通过以下策略检查客户端 IP 地址。



#### 休息自适应身份验证

此功能的 RESTful 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.自适应.ip-英特尔.rest`。



#### 沟槽自适应身份验证



```properties
•卡斯.奥森.自适应.ip-英特尔.格罗维.位置]文件/等/卡斯/配置/格罗夫伊普地址智能服务。
```




#### 黑点自适应身份验证



```properties
*cas.authn.自适应.ip-英特尔.黑点.url=__检查.盖特平特尔.网络/检查.php？ip]%s
#cas.authn.自适应.ip-英特尔.黑 dot.email 地址]
#cas.authn.自适应.ip-英特尔.黑点.DYNA_LIST
```




## 代理身份验证

代表其他用户进行身份验证。 要了解有关此主题的更多内容，请 [](../installation/Surrogate-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. 苏罗盖特. 分离器]
# 卡斯. 奥森. 苏尔罗盖特. tgt. 秒内杀人时间 = 30
```


此功能 的主要分辨率和人员目录设置可 [此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥 `cas.authn.surrogate.`。



### 静态代理帐户



```properties
#卡斯. 奥森. 苏罗盖特. 简单. 代理. 卡苏瑟 - jsmith， jsmith2
# 卡斯. 奥森. 苏罗盖特. 简单. 代理. 卡苏瑟 2 ] jsmith4， jsmith2
```




### 杰森代理账户



```properties
* 卡斯. 奥森. 苏罗盖特. json. 位置[ 文件/ 等 / 卡斯 / 配置 / 代理. json
```




### LDAP 代理账户

此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.surrogate.ldap`。



```properties
#cas.authn.surrogate.ldap.代理搜索过滤器]（&（校长={user}）（成员Ofof=cn=edu：示例：cas：东西：{user}，dc=示例，直流编辑））
cas.authn. surrogate. ldap. 成员属性名称]
成员 # 卡斯. 奥森. surrogate. ldap. 成员属性价值 - 雷格克斯 cn = edu： 示例： 卡斯： 某事。
```




### 库奇德布代理账户

此功能的设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥 `cas.authn.surrogate`下提供。 代孕可以作为委托人配置文件的一部分存储，也可以作为一系列委托人/代理对存储。 默认值是密钥/值对。



```properties
#cas.authn.surrogate.ldap.代理搜索过滤器]（&（校长={user}）（成员Ofof=cn=edu：示例：cas：东西：{user}，dc=示例，直流编辑））
cas.authn. surrogate. ldap. 成员属性名称]
成员 # 卡斯. 奥森. surrogate. ldap. 成员属性价值 - 雷格克斯 cn = edu： 示例： 卡斯： 某事。
```




### 京交所代理账户

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.surrogate.jdbc`。



```properties
#cas.奥森.代理.jdbc.代理搜索查询=选择计数（*）从代理用户名=？
#cas.authn.代理.jdbc.代理帐户查询=选择surrogate_user作为代理帐户从代理用户名=？
```




### 休息代理账户

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.surrogate.rest`。



### 通知

此功能的电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications) 配置密钥 `cas.authn.surrogate`。 此功能的短信通知设置 可 [此处](Configuration-Properties-Common.html#sms-notifications) 配置密钥 `cas.authn.surrogate`下提供。



## QR 身份验证

尝试通过移动设备通过QR代码登录。 要了解有关此 主题的更多内容，请 [](../installation/QRCode-Authentication.html)查看本指南。



```properties   
*配置允许浏览器客户端的"原始"标题值。
*卡斯.奥森.qr.允许的起源**
```




### 杰森设备存储库

尝试通过移动设备通过QR代码登录。 要了解有关此 主题的更多内容，请 [](../installation/QRCode-Authentication.html)查看本指南。



```properties
# 卡斯. 奥森. qr. json. 位置： 文件/ 等 / 卡斯 / 康菲格 / qrdevices. json
```




## 基于风险的身份验证

评估可疑身份验证请求并采取行动。 要了解 更多关于这个主题，请 [](../installation/Configuring-RiskBased-Authentication.html)查看本指南。



```properties
#cas. authn. 自适应. 风险. 阈值 = 0.6
# 卡斯. authn. 自适应. 风险. 最近历史中的天 # 30

# 卡斯. 奥森. 自适应. 风险 . ip. 启用] 假

# cas. authn. 自适应. 风险. 代理. 启用 = 假

# 卡斯. authn. 自适应. 风险. 地理位置. 启用 = 假

# 卡斯. 奥森。 自适应. 风险. 日期时间. 启用] 假
# cas. authn. 自适应. 风险. 日期时间. 小时窗口 = 2

# cas. authn. 自适应. 风险. 响应. 阻止尝试] 假

# cas. authn. 自适应. 风险. 响应. mfa 提供商]
# 卡斯. authn. 自适应. 风险. 响应. 风险认证属性 = 触发风险基础验证
```


此功能的电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications) 配置密钥下 `cas.authn.自适应.风险.响应`。 此功能的 SMS 通知设置 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 配置密钥下 `cas.authn.自适应.风险.响应`。



## 无密码身份验证

要了解有关此主题的更多内容，请 [](../installation/Passwordless-Authentication.html)查看本指南。



### 账户商店



```properties   
#cas.authn.无密码.多因素身份验证激活=虚假
#cas.authn.无密码.授权认证激活=错误
```




#### 简单帐户商店



```properties
#cas.authn.passwordless.accounts.simple.casuser=cas@example.org
```




#### 格罗夫帐户商店



```properties
#卡斯. authn. 无密码. 帐户. 格罗维. 位置 [ 文件] 等 / 卡斯 / 康菲格 / 无密码. 格劳维
```




#### 杰森帐户商店



```properties
#卡斯. 奥森. 无密码. 帐户. json. 位置[ 文件/ cas / 配置 / 无密码帐户. json
```




#### 可重复的帐户存储

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.无密码.帐户.rest`。



#### LDAP 帐户商店

此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.无密码.帐户.ldap`。



#### 蒙哥德银行帐户商店

MongoDb 此功能的设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.authn.无密码.帐户.mongo`。



### 代币管理



```properties
#卡斯. 奥森. 无密码. 帐户. 秒内过期 = 180
```


此功能的 RESTful 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.无密码.令牌.rest`。 签名密钥和加密 密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 在 配置密钥 `cas.authn.无密码.令牌`下，可 [此处](Configuration-Properties-Common.html#signing--encryption) 签名 & 加密设置。

此功能的电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications) 配置密钥下 `cas.authn.无密码.令牌`。 此功能的短信通知设置 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 配置密钥 `cas.authn.密码.令牌`。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥 `cas.authn.无密码.令牌.jpa`下。 此功能的调度器设置可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥 `cas.authn.无密码.令牌.jpa.清洁工`下提供。



## 电子邮件提交

电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications)。



## 短信消息

要了解有关此主题的更多内容，请 [](../notifications/SMS-Messaging-Configuration.html)查看本指南。



### 槽的

使用 Groovy 脚本发送短信。



```properties
• 卡斯. 短信提供商. groovy. 位置： 文件/ 等 / 卡斯 / 康菲 / 短信发送者. 格劳维
```




### 休息

使用回复API发送短信。 此功能的 RESTFUL 设置 可 [此处](Configuration-Properties-Common.html#restful-integrations) 以下配置密钥 `cas.sms 提供商.rest`。



### 特维利奥

使用Twilio发送短信。



```properties
#卡斯. 短信提供商. twilio. 帐户 - id]
# 卡斯. 短信提供商. twilio. 令牌]
```




### 文本魔术

使用文本魔术发送短信。



```properties
#cas. 短信提供商. 文本魔法. 用户名]
# cas. 短信提供商. 文本魔法. 令牌]
# cas. 短信提供商. 文本 - 魔术. 调试 = 假
# cas. 短信提供商. 文本 - 魔术. 密码]
# cas. 短信提供商. 文本魔法. 阅读超时 = 50
# cas. sms 提供商 .文本 - 魔术. 连接超时 # 5000
# cas. sms - 提供商. 文本 - 魔术. 写超时 = 50
# cas. 短信提供商. 文本 - 魔术. 验证 - ssl= 真实
# cas. 短信提供商. 文本 - 魔术. api - key]
# cas. 短信提供商. 文本 - 魔术.
```




### 点击

使用点击发送短信。



```properties
# cas. 短信提供商. 点击. 服务器 - url = https：//平台. 点击.com / 消息
# 卡斯. 短信提供商. clickatell. 令牌]
```




### 内克斯莫

使用 Nexmo 发送短信。 Nexmo至少需要一个秘密或签名秘密字段集。



```properties
#cas. 短信提供商. nexmo. api 代币]
# 卡斯. 短信提供商. nexmo. api 秘密 ]
# 卡斯. sms - 提供商. nexmo. 签名秘密]
```




### 亚马逊 SNS

使用亚马逊SNS发送短信。



```properties
#cas. 短信提供商. sns. 发送者 - id]
[ 卡斯. 短信提供商. sns.max价格]
[ 卡斯. sms - 提供商. sns. ss 类型] 交易
```


此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥 `cas.sms 提供商.sns`下提供。



## 谷歌云火基消息

要了解有关此主题的更多内容，请 [](../notifications/Notifications-Configuration.html)查看本指南。



```properties
#cas.谷歌-火力基地-消息.服务-帐户-键.位置=/路径/到/帐户键.json"
#cas.google-火基-消息.数据库-url=https：//卡索-123456.firebaseio.com"，
# cas.google-火基-消息.注册-代币属性-名称-注册-
# cas.google-火基-消息..com 范围
```




## 地理跟踪

要了解有关此主题的更多内容，请 [](../installation/GeoTracking-Authentication-Requests.html)查看本指南。



### 谷歌图片地理跟踪

用于地理剖析身份验证事件。



```properties
#cas. google 地图. api 键]
# cas. google 地图. 客户 id=
# cas. google 地图. 客户 - 秘密 =
# cas. google - 地图. 连接超时 = 30
# cas. google 地图. google - 应用程序 - 引擎 = 虚假
```




### 马克斯明德地理跟踪

用于地理剖析身份验证事件。



```properties
#cas.最大明德.城市-数据库]文件/等/卡斯/最大分钟/地理信息2-城市.mmdb
#卡斯.最大明德.国家数据库/文件/等/卡斯/最大分钟/地理信息2-国家.mmdb
```




## 卡桑德拉身份验证

要了解有关此主题的更多内容，请 [](../installation/Cassandra-Authentication.html)查看本指南。



```properties
[卡斯. 奥森. 卡桑德拉. 用户名属性]
[ 卡斯. 奥森. 卡桑德拉. 密码属性]
[ 卡斯. 奥森. 卡桑德拉. 表名称]
# 卡斯。 奥森. 卡桑德拉. 用户名]
[ 卡斯. 奥森. 卡桑德拉. 密码]
[ 卡斯. 奥森. 卡桑德拉. 查询] 选择 * 从 %s %s 的地方 ] ？ 允许过滤
[ cas.authn.cassandra.name]
[ 卡斯. 奥森. 卡桑德拉. 秩序]
```


此功能的常见卡桑德拉设置可 [此处](Configuration-Properties-Common.html#cassandra-configuration) 配置密钥下 `cas.authn.卡桑德拉`。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.cassandra`。 

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.cassandra`。



## 文摘身份验证

要了解有关此主题的更多内容，请 [](../installation/Digest-Authentication.html)查看本指南。



```properties
*cas.authn.digest.用户.用户.卡苏瑟=3530292c24102bac7ced2022e5f1036a
#cas.authn.文摘.用户.另一个用户=7530292c2410 2bac7ced2022e5f1036b
# 卡斯. authn.
. cas.authn.digest.name]
# 卡斯. authn. 文摘. 订单]
# 卡斯. 奥森. 文摘. 认证方法 = 身份验证
```




## 半径认证

要了解有关此主题的更多内容，请 [](../mfa/RADIUS-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.radius`下提供。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.radius`。

此功能的半径设置可 [此处](Configuration-Properties-Common.html#radius-configuration) 配置密钥下 `cas.authn.radius`。



```properties
•cas.authn.radius.name+
```




## 文件认证

要了解有关此主题的更多内容，请 [](../installation/Permissive-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.文件`下提供。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.文件`。



```properties
#cas.authn.文件.分离器]：
# 卡斯.奥森.文件.文件/路径/到/用户/文件
# cas.authn.file.name]
```




## 沟槽身份验证

要了解有关此主题的更多内容，请 [](../installation/Groovy-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. 格劳维. 订单 = 0
= cas.authn.groovy.name]
```




## 杰森认证

要了解有关此主题的更多内容，请 [](../installation/Permissive-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.json`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.json`。

此功能的密码策略设置可 [此处](Configuration-Properties-Common.html#password-policy-settings) 配置密钥下 `cas.authn.json.密码政策`。



```properties
#卡斯.奥森.杰森.位置|文件/路径/到/用户/文件.json
cas.authn.json.name]
```




## 拒绝用户身份验证

要了解有关此主题的更多内容，请 [](../installation/Reject-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn. 拒绝`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn. 拒绝`。



```properties
#卡斯.奥森.拒绝.用户=用户1，用户2
=cas.authn.reject.name]
```




## 数据库认证

要了解有关此主题的更多内容，请 [](../installation/Database-Authentication.html)查看本指南。



### 查询数据库身份验证

通过将用户密码（可使用密码编码器进行编码）与可配置的数据库查询确定的记录中的密码进行比较 ，对用户进行身份验证。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.jdbc.查询[0]`。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.jdbc.查询[0]`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.jdbc.查询[0]`下提供。



```properties
#cas. authn. jdbc. 查询[0]. 证书标准]
# 卡斯. 奥森. jdbc. 查询[0]. 名称]
# 卡斯. 奥森. jdbc. 查询[0]. 订单 [ 0

# 卡斯. 奥森. jdbc. 查询[0].sql = 选择 * 从表名]
#cas.奥森.jdbc.查询[0].字段密码=密码
#卡斯.奥森.jdbc.查询[0].字段过期=
#cas.authn. jdbc. 查询[0]. 字段禁用 =
# cas. 奥森. jdbc. 查询[0]. 主要属性列表 \ sn， cn： 通用名， 给定名
```




### 搜索数据库身份验证

通过查询用户名和密码来搜索用户记录;如果发现至少一个结果，则对用户进行身份验证。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.jdbc.搜索[0]`。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.jdbc.搜索[0]`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.jdbc.搜索[0]`。



```properties
#cas. authn. jdbc. 搜索[0]. 现场用户]
# 卡斯. 奥森. jdbc. 搜索[0]. 表用户 ]
# 卡斯. 奥森. jdbc. 搜索[0]. 现场密码]
# cas. authn. jdbc. 搜索[0]. 证书标准]
# 卡斯. 奥森. jdbc. 搜索[0]. 名称]
# 卡斯. 奥森. jdbc. 搜索[0]. 订单 = 0
```




### 绑定数据库身份验证

通过尝试使用用户名和（哈希德）密码创建数据库连接来验证用户身份。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.jdbc.bind[0]`。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.jdbc.bind[0]`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.jdbc.bind[0]`。



```properties
#卡斯. 奥森. jdbc. bind[0]. 认证标准]
# 卡斯. 奥森. jdbc. 宾德[0]. 名称]
# 卡斯. 奥森. jdbc. 宾德[0].
```




### 编码数据库身份验证

JDBC 查询处理程序，该处理程序将提取用户的密码和私有盐值，并使用公共盐值验证编码 密码。 假设所有内容都在同一数据库表内。 支持 次数迭代以及私人盐的设置。

此密码编码方法结合了私盐和公盐，在哈希之前先将其预置于密码上。 如果使用多个迭代，则在没有盐值的情况下重述第一次迭代的副编码散布。 在将其与数据库值进行比较之前，将最终的哈希 转换为六角形。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥 `cas.authn.jdbc.编码[0]`下提供。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.jdbc.编码[0]`下提供。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.jdbc.编码[0]`。



```properties
#cas. authn. jdbc. 编码[0]. 迭代数 = 0
# 卡斯. authn. jdbc. 编码[0]. 迭代编号 - 字段名称 ] 数字
# 卡斯. 奥森. jdbc. 编码[0].盐场名称=盐
#cas.authn.jdbc.编码[0].静态盐=
#卡斯.奥森.jdbc.编码[0].sql]
#卡斯.奥森.jdbc.编码[0].算法- 名称=
# cas. authn. jdbc. 编码[0]. 密码字段名称 ] 密码
# cas. authn. jdbc. 编码[0]. 过期字段名称]
# 卡斯. 奥森. jdbc.[0]. 禁用 字段名称=

# cas. authn. jdbc. 编码[0]. 证书标准]
# 卡斯. 奥森. jdbc. 编码[0]. 名称]
# 卡斯. 奥森. jdbc. 编码[0].
```




## 沙发数据库身份验证

要了解有关此主题的更多内容，请 [](../installation/CouchDb-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.沙发-db`下提供。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.沙发-db`。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥 `cas.authn`下提供。



```properties
#cas. authn. 沙发 - db. 属性]
[ 卡斯. 奥森. 库奇 - 德布. 用户名属性] 用户名
[ 卡斯. 奥森. 沙发 - db. 密码属性] 密码
[ cas.authn.couch-db.name]
# 卡斯. 奥森. 沙发 - db. 订单]
```




## 重新验证

要了解有关此主题的更多内容，请 [](../installation/Redis-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.redis`。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.redis`下提供。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.authn`。



```properties
#cas.authn.redis.name]
# 卡斯. 奥森. 雷迪斯. 秩序]
```




## 蒙哥德布认证

要了解有关此主题的更多内容，请 [](../installation/MongoDb-Authentication.html)查看本指南。 

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.mongo`。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.mongo`。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.authn`下提供。



```properties
#cas. authn. mongo. 属性]
# 卡斯. authn. mongo. 用户名
# 卡斯. 奥森. mongo. 密码属性 = 密码
# cas. authn. mongo.
# cas.authn.mongo.name
```




## LDAP 身份验证

CAS 根据 LDAP 目录（如活动目录或 OpenLDAP）验证用户名/密码。 有许多目录架构，我们为四个常见案例提供配置。

请注意，CAS 将根据下面指定的设置在内部 自动创建相应的组件。 如果您希望对多个LDAP 服务器进行身份验证，则增量索引并指定下一台LDAP服务器的设置。

**注意：作为 LDAP 身份验证的一部分检索到的** 属性与从其他属性存储源 [检索 的所有属性合并](#authentication-attributes)（如果有的话）。 作为LDAP身份验证的一部分直接检索的属性胜过所有其他属性。

要了解有关此主题的更多内容，请 [](../installation/LDAP-Authentication.html)查看本指南。 此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.ldap[0]`。



```properties
# 定义要从 LDAP 中检索的属性，作为同一身份验证交易的一部分
* 左侧大小注释源，而右侧大小表示属性定义的可选重命名/重新映射
# 。 允许多次映射同一属性名称以
#不同的属性名称。

#cas. authn. ldap[0]. 主要属性列表 \ sn， cn： 共同名， 给定名， 爱德华 · 塔格迪德： SOME_IDENTIFIER

= 卡斯. 奥森. ldap[0]. 收集 - dn 属性 = 假
# 卡斯. 奥森. ldap[0]. 名称 =校长LdapDn
# cas.authn.ldap[0].允许多重主属性值=真实
# cas.authn.ldap[0]. 允许缺失 - 委托属性值 = 真实
# cas. authn. ldap[0].
```


要获取和解决带有标记/选项的属性，请考虑将映射属性标记为：



```properties
#卡斯. 奥森. ldap[0]. 主要属性列表 ] 家庭后地址： 家庭后地址;
```




### LDAP 密码策略

此功能的 LDAP 密码策略设置可 [此处](Configuration-Properties-Common.html#password-policy-settings) 配置密钥下 `cas.authn.ldap[0].密码政策`。



### LDAP 密码编码 & 主转换

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.ldap[0]`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.ldap[0]`。



## 休息认证

这允许 CAS 服务器通过 `邮政`到达远程 REST 端口。 要了解有关此主题的更多内容，请 [](../installation/Rest-Authentication.html)查看本指南。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.rest`。



```properties
# 卡斯. 奥森. 休息. 乌里普斯
#cas.authn.rest.name]
# 卡斯. 奥森. 休息. 查塞特 ] 美国 - 阿西
```




## 谷歌应用身份验证

通过CAS验证到谷歌应用程序服务和应用程序。 要了解有关此主题的更多内容，请 [](../integration/Google-Apps-Integration.html)查看本指南。



```properties
#cas.google-apps.公共密钥位置/文件/等/公共.key
# cas.谷歌应用程序.key算法=RSA
# cas.google-应用程序.私钥位置/文件/等/cas/私人.key
```




## 开放式身份验证

允许 CAS 成为开放式身份验证提供商。 要了解有关此主题的更多内容，请 [](../protocol/OpenID-Protocol.html)查看本指南。

此功能 的主要分辨率和人员目录设置可 [此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥 `cas.authn.openid.`。



```properties
#卡斯. 奥森. 开放. 强制 - rp - id = 假
= cas.authn.openid.name ]
# 卡斯. 奥森. openid. 订单
```




## 斯内戈认证

要了解有关此主题的更多内容，请 [](../installation/SPNEGO-Authentication.html)查看本指南。

此功能的主要分辨率和人员目录设置可 [此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥下 `cas.authn.spnego.`。



```properties
#cas. authn. spnego. 混合模式认证 = 假
# 卡斯. 奥森. 斯普内戈. 支持浏览器 » Msie， 三叉星， 火狐， 苹果网基特
# 卡斯. 奥森. 斯普内戈. 发送 401 在认证 - 失败[真正的
]cas.authn.斯普内戈.ntlm允许=真正的
[卡斯.奥森.斯普内戈.具有域名的校长]假
=cas.authn.spnego.name]
#卡斯.奥森.斯普内戈.ntlm=假
```




### 网流配置

此功能的网流自动配置设置可 [此处](Configuration-Properties-Common.html#webflow-auto-configuration) 配置密钥下 `cas.authn.spnego.网络流`。



### 系统设置



```properties
[卡斯. 奥森. 斯普内戈. 系统. 克贝罗斯 - 康普]
# 卡斯. 奥森. 斯普内戈. 系统. 登录 - 康普
# 卡斯. 奥森. 斯普内戈. 系统. 克贝罗斯 - 领域 [ 示例.COM
] 卡斯. 奥森. 斯普内戈 .系统. 克贝罗斯 - 德布格] 真正的
# 卡斯. 奥森. 斯普内戈. 系统. 仅使用主题 - 信用 - 假
# cas. authn. 斯普内戈. 系统. 克贝罗斯 - kdc = 172.10.1.10
```




### 斯涅戈身份验证设置



```properties
#cas. authn. spnego. 属性[0]. 缓存政策 = 600
# 卡斯. 奥森. 斯普内戈. 属性[0]. jcifs - 域控制器]
# 卡斯. 奥森. 斯普内戈 属性[0].jcifs域名]
#卡斯.奥森.斯普内戈.属性[0].jcifs-密码=
#卡斯.奥森.斯普内戈.属性[0].jcifs-用户名]
# cas. authn. spnego. 属性[0]. jcifs - 服务 - 密码]
# 卡斯. 奥森. 斯普内戈. 属性[0]. 超时 = 300000
# 卡斯。 奥森. 斯普内戈. 属性[0]. jcifs - 服务 - 校长 = Http / cas. 示例.com @ 示例.COM
# 卡斯. 奥森. 斯普内戈. 属性[0]. jcifs - netbios - wins ]
```




### 斯普内戈客户选择策略



```properties
#卡斯. 奥森. 斯普内戈. 主机名称 - 客户端 - 行动 - 战略 ] 主机名称斯普内戈 · 克莱恩行动
```




### SPNEGO 客户选择主机名



```properties
#cas. authn. spnego. 替代远程主机属性 = 备用雷莫特头
# 卡斯. 奥森. 斯普内戈. ips 到检查模式 [ 127.
] 卡斯. 奥森. 斯普内戈. dns 超时 = 20
# 卡斯. authn. spnego. 主机名称模式 - 字符串。
```




### 斯内戈 LDAP 集成

此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.spnego.ldap`。



```properties
# 卡斯. 奥森. 斯普内戈. 斯普内戈属性名称 ] 与众不同的名字
```




### NTLM 身份验证



```properties
#cas. authn. ntlm. 包括模式]
[ 卡斯. 奥森. ntlm. 负载平衡] 真实
[ 卡斯. 奥森. ntlm. 域控制器]
[ cas.authn.ntlm.name]
# 卡斯. 奥森. ntlm. 秩序]
[ 卡斯. 奥森. ntlm. 启用] 假
```




## 日本航空认证

要了解有关此主题的更多内容，请 [](../installation/JAAS-Authentication.html)查看本指南。 此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.jaas[0]`下提供。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.jaas[0]`。



```properties
#cas. authn. jaas[0]. 领域 [ 卡斯
] 卡斯. 奥森. jaas[0]. 克贝罗斯 - kdc 系统属性]
# 卡斯. 奥森. jaas[0]. kerberos - 领域 - 系统属性
# 卡斯. 奥森. jaas[0]. name]
[0]. 命令]
# cas. authn. jaas[0]. 认证标准 ]
# 卡斯. 奥森. jaas[0]. 登录配置类型 = 贾瓦洛金康菲格
# 卡斯. authn. jaas[0]. 登录配置配置文件 [ / 路径 / 到 / jaas. con
```


此功能 的主要分辨率和人员目录设置可 [此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥 `cas.authn.jaas[0].`。

此功能的密码策略设置可 [此处](Configuration-Properties-Common.html#password-policy-settings) 配置密钥 `cas.authn.jaas[0].密码策略`下提供。




## 瓜认证

要了解有关此主题的更多内容，请 [](../installation/GUA-Authentication.html)查看本指南。



### LDAP 存储库

此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.gua.ldap`。



```properties
#卡斯.奥森.瓜.ldap.图像属性=用户图像标识符
```




### 静态资源存储库



```properties
#卡斯. 奥森. 瓜. 简单。[username1]=文件/路径/图像.jpg
[username2]=文件：路径/到/图像.jpg
```




## JWT/令牌身份验证

要了解有关此主题的更多内容，请 [](../installation/JWT-Authentication.html)查看本指南。 此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.令牌`下提供。



```properties
•cas.authn.token.name=
```




### 网流配置

此功能的Webflow自动配置设置可 [此处](Configuration-Properties-Common.html#webflow-auto-configuration) 以下 配置密钥 `cas.authn.令牌.网络流`。



### JWT 门票

允许通过各种协议渠道创建 CAS 门票，以创建 JWT。 有关详细信息，请参阅本指南 [](../installation/Configure-ServiceTicket-JWT.html) 或 [本指南](../protocol/REST-Protocol.html) 。



```properties
#cas. authn. 令牌. 加密. 加密启用] 真实
# 卡斯. 奥森. 令牌. 加密. 签名启用= 真实
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.令牌`下，可 & 此功能的加密设置进行签名。



## 沙发底座验证

要了解有关此主题的更多内容，请 [](../installation/Couchbase-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.沙发基地`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn. 沙发基地`。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchbase-integration-settings) 配置密钥下 `cas.authn.沙发基地`。



```properties
#cas. authn. 沙发基地. 用户名属性 [ 用户名
# 卡斯. 奥森. 沙发基地. 密码属性 [ psw

] cas.authn.couchbase.name]
# 卡斯. 奥森. 库奇基地.
```




## 亚马逊云目录认证

要了解有关此主题的更多内容，请 [](../installation/AWS-CloudDirectory-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.cloud 目录`下提供。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.cloud 目录`下提供。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥 `cas.authn.cloud 目录`下提供。



```properties
•cas.authn.cloud 目录.目录-arn]
• cas.authn.cloud 目录.
• cas.authn.cloud 目录.方面名称•

• cas.authn.cloud 目录.用户名属性 名称=
= cas.authn.cloud 目录. 密码属性名称 =
# cas.authn.cloud 目录. 用户名索引路径]

# cas.authn.cloud-directory.name =
# cas.authn.cloud 目录.
```




## 亚马逊认知认证

要了解有关此主题的更多内容，请 [](../installation/AWS-Cognito-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.cognito`下提供。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.认知`下提供。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.authn.认知`。



```properties
#cas.authn.cognito.name]
[ 卡斯. 奥森. 科尼托. 订单]

# 卡斯. 奥森. 科尼托. 客户 - id]
# 卡斯. 奥森. 科尼托 .用户池 - id ]

# 卡斯. 奥森. 科格尼托. 映射 - attributes.given_name ] 给定名
# cas. authn. 认知. 映射属性。[自定义]：网=
```




## 冈田认证

要了解有关此主题的更多内容，请 [](../installation/Okta-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.okta`。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.okta`。



```properties
#cas.authn.okta.name]
# 卡斯. 奥森. okta. 秩序]  
# 卡斯. 奥森. okta. 认证标准 ]

# 卡斯. 奥森. okta. 组织 - url =     

# 卡斯. 奥森. okta. 连接超时 5000
# cas. authn. okta. 代理用户名]
# 卡斯. 奥森. okta. 代理密码]
# 卡斯. 奥森. okta. 代理主机]
# 卡斯. 奥森. okta. 代理端口
```




## 微软 Azure 活动目录认证

要了解有关此主题的更多内容，请 [](../installation/Azure-ActiveDirectory-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.azure 活动目录`下提供。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.azure 活动目录`下提供。



```properties
#cas.authn.azure-active-directory.name]
# cas. authn. azure 活动目录. 订单 =
# cas. authn. azure 活动目录. 认证标准 =

# cas. authn. azure 活动目录. 客户 - id]
# cas.authn.azure-主动目录.登录 url=#/登录.微软在线.com/公共/
# cas.authn.azure 活动目录. 资源=https：//图表.微软.com/
```




## 肥皂认证

要了解有关此主题的更多内容，请 [](../installation/SOAP-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.肥皂`下提供。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.soap`。



```properties
#cas.authn.soap.name]
# 卡斯. 奥森. 肥皂. 秩序]
# 卡斯. 奥森. 肥皂. url]
```




## 远程地址身份验证

要了解有关此主题的更多内容，请 [](../installation/Remote-Address-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. 远程地址. ip 地址范围]
= cas.authn.remote-address.name =
# 卡斯. 奥森. 远程地址. 订单
```





## 接受用户身份验证

<div class="alert alert-warning"><strong>默认凭据</strong><p>为了测试 CAS 中的默认身份验证方案，
分别使用 <strong>卡瑟</strong> 和 <strong>梅隆</strong> 作为用户名和密码。 这些通过静态身份验证处理器自动
配置， <strong>必须在生产推出前从配置
中删除</strong> 。</p></div>

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥 `cas.authn.接受`。 此功能的密码策略设置可 [此处](Configuration-Properties-Common.html#password-policy-settings) 配置密钥下 `cas.authn.接受.密码政策`。 此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.接受`。



```properties
[卡斯. 奥森. 接受. 用户]
[ cas.authn.accept.name]
[ 卡斯. 奥森. 接受. 启用] 真正的
[ 卡斯. 奥森. 接受. 证书标准]
```




## X509 身份验证

要了解有关此主题的更多内容，请 [](../installation/X509-Authentication.html)查看本指南。



### 网流配置

此功能的 Webflow 自动配置设置可 [此处](Configuration-Properties-Common.html#webflow-auto-configuration) 配置密钥 `cas.authn.x509.webflow`下提供。



```properties
#卡斯. authn. x509. 网络流. port = 8446
# 卡斯. 奥森. x509. 网络流. 客户端身份 = 想要
```




### 主要决议

X.509 主要决议可根据以下主要类型采取行动：

| 类型                 | 描述                                                                                                                         |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------- |
| `SERIAL_NO`        | 用可配置的 <strong>半径</strong>（从 2 到 36）按序列号解析本金。 如果 <code>半径</code> <code>16</code>，则序列号可以填充领先的零，甚至数字数。 |
| `SERIAL_NO_DN`     | 按序列号和发行人 dn 解决本金问题。                                                                                                        |
| `主题`               | 通过从证书主体 DN 中提取一个或多个属性值并将其与干预划界器相结合来解决委托人问题。                                                                                |
| `SUBJECT_ALT_NAME` | 通过主题替代名称扩展解决本金问题。 （类型：其他名）                                                                                                 |
| `SUBJECT_DN`       | 默认类型：通过证书的主题dn解决本金问题。                                                                                                      |
| `CN_EDIPI`         | 通过电子数据交换个人标识符 （EDIPI） 从通用名称解决本金问题。                                                                                         |
| `RFC822_EMAIL`     | 通过 [RFC822 名称](https://tools.ietf.org/html/rfc5280#section-4.2.1.6) （又名电子邮件地址）类型的主题替代名称字段解决本金问题。                           |


对于 `CN_EDIPI`，`SUBJECT_ALT_NAME`， `RFC822_EMAIL` 主要解决者，因为并非所有的证书都有这些属性， 您可以指定以下属性，以便具有与用作本金的证书不同的属性。  
如果没有指定替代属性，则本金将无效，CAS 将无法进行身份验证或使用不同的身份验证器。



```properties
#cas.authn.x509.交替主体属性=主题Dn|主题|主题X500原则|x509Rfc822电子邮件|x509主题UPN
```




### CRL 取回/撤销

CAS 为证书撤销检查提供了灵活的政策引擎。 该设施的产生是由于JSSE内置的撤销机制中缺乏可配置性 。

可用政策涵盖以下事件：

- CRL 到期
- CRL 不可用

无论哪种情况，都提供以下选项：

| 类型   | 描述                                       |
| ---- | ---------------------------------------- |
| `允许` | 允许进行身份验证。                                |
| `否认` | 拒绝身份验证并阻止。                               |
| `门槛` | 适用于 CRL 到期，限制允许过期数据长达阈值时间的请求，但不允许在阈值期间内。 |


撤销证书检查可以通过以下方式之一进行：

| 类型    | 描述                                                                              |
| ----- | ------------------------------------------------------------------------------- |
| `没有`  | 不执行撤销。                                                                          |
| `CRL` | 证书中提到的 CRL URI `cRL 分配点` 扩展字段。 可使用缓存防止过度使用 IO 对抗 CRL 端点;如果缓存中不存在或过期，则提取 CRL 数据。 |
| `资源`  | 在固定位置托管的CRL。 CRL 以周期性间隔取取并缓存。                                                   |


要提取 CRL，可用以下选项：

| 类型     | 描述                                      |
| ------ | --------------------------------------- |
| `资源`   | 默认情况下，所有撤销检查都使用固定资源从指定位置获取 CRL 资源。      |
| `阿尔达普` | 如果 CRL 资源位置为 LDAP URI，可以从预配置属性获取 CRL 资源 |




```properties
#卡斯. 奥森. x509. crl 过期政策 = 拒绝|允许|门槛
# 卡斯. 奥森. x509. crl 不可用政策 = 拒绝|允许|门槛
# 卡斯. authn. x509. crl 资源过期政策 = 拒绝|允许|门槛
# 卡斯. authn. x509. crl 资源不可用政策 = 拒绝|允许|门槛

# 卡斯. authn. x509. 撤销检查器= 无|CRL|资源
# 卡斯. 奥森. x509. cr - 取件人 = 资源|LDAP

# 卡斯. 奥森. x509. crl 资源[0]= 文件：

# cas.authn.x509.cache-max-elements-in-memory=1000
# cas.authn.x509.cache-disk-overflow=false
# cas.authn.x509.cache-disk-size=100MB
# cas.authn.x509.cache-eternal=false
# cas.authn.x509.cache-time-to-live-seconds=7200

# cas.authn.x509.check-key-usage=false
# cas.authn.x509.revocation-policy-threshold=172800

# cas.authn.x509.reg-ex-subject-dn-pattern=.+
# cas.authn.x509.reg-ex-trusted-issuer-dn-pattern=.+

# cas.authn.x509.name=
# cas.authn.x509.order=

# cas.authn.x509.principal-descriptor=
# cas.authn.x509.max-path-length=1
# cas.authn.x509.throw-on-fetch-failure=false

# cas.authn.x509.check-all=false
# cas.authn.x509.require-key-usage=false
# cas.authn.x509.refresh-interval-seconds=3600
# cas.authn.x509.max-path-length-allow-unspecified=false

# SUBJECT_DN
# cas.authn.x509.subject-dn.format=[DEFAULT,RFC1779,RFC2253,CANONICAL]
```


| 类型        | 描述                                                                                                                                                                                                                               |
| --------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `违约`      | 呼叫证书.获取对象DN（）方法的向后兼容性，但该方法 ["诋毁"](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/security/cert/X509Certificate.html#getIssuerDN())。                                                                           |
| `RFC1779` | 呼叫 [X500Prinal.getname（"RFC1779"）](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/security/auth/x500/X500Principal.html#getName()) 该主题 DN 与 RFC 1779 中定义的属性关键字（CN、L、ST、O、OU、C、STREET）。 任何其他属性类型都作为OID发出。     |
| `RFC2253` | 呼叫 [X500Prinal.getname（"RFC2253"）](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/security/auth/x500/X500Principal.html#getName()) 它发出带有 RFC 2253 定义的属性关键字的主题 DN（CN、L、ST、O、OU、C、街、直流、UID）。 任何其他属性类型都作为OID发出。 |
| `规范`      | 调用 X500Prinincipinal.getname（"规范"，它发出的主题 DN，以 RFC 2253 开头，并应用 [javadoc](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/security/auth/x500/X500Principal.html#getName())中描述的其他规范。                             |




```properties
# SERIAL_NO_DN
# 卡斯. authn. x509. 串行 - 无 dn. 序列号前缀 ] 序列号 ]
# 卡斯. 奥森. x509. 串行 - 无 dn. 价值划界器]，

# SERIAL_NO
# cas. authn. x509. 序列号. 校长 - s - n - radix = 10
# 卡斯. authn. x509. 序列 - 否. 校长 - 赫克斯 - s - n - 零填充 ] 假

# SUBJECT_ALT_NAME
# cas. authn. x509. 主题 - alt - 名称. 交替 - 主要属性] [西加尔吉德|主题|主题 X500 原则|x509Rfc822 电子邮件]

# CN_EDIPI 
• cas.authn.x509.cn - edipi. 交替 - 校长属性] [西加尔吉|主题| 主题 X500 原则|x509Rfc822 电子邮件|x509 主题+

# RFC822_EMAIL 
# cas. authn. x509. rfc822 - 电子邮件. 交替 - 校长属性] [西加尔吉德|主题| 主题 X500pin|x509 主题+
```




### X509 证书提取

这些设置可用于打开和配置 CAS 以 从基础 64 编码证书 在 HTTP 请求标题（由 CAS 前面的代理放置）中提取 X509 证书。 如果这被设置为真实，则用户不能绕过代理 ，并且代理确保标题 永远不会来自浏览器。



```properties
#卡斯. authn. x509. 提取证书 = 假
# 卡斯. 奥森. x509. sl 标题名称 = ssl_client_cert
```


证书的具体解析逻辑 与 Tomcat SSLValve 兼容，后者可与 阿帕奇 HTTPD、Nginx、哈普罗西、BigIP F5 等设置的标题配合使用。



### X509 主要分辨率



```properties
#卡斯. 奥森. x509. 校长类型 = SERIAL_NO|SERIAL_NO_DN|主题|SUBJECT_ALT_NAME|SUBJECT_DN
```


此功能的主要分辨率和人员目录设置可 [此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥 `cas.authn.x509.`。



### X509 LDAP 集成

X509 功能的 LDAP 设置（如果从 LDAP 提取 CR）可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.x509.ldap`。

请参阅此处 [LDAP 属性存储库，](Configuration-Properties.html#ldap) 使用从 X509 证书中提取的委托人获取其他 LDAP 属性。 



## 同步身份验证

要了解有关此主题的更多内容，请 [](../installation/Syncope-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.同步`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥 `cas.authn.同步`下提供。



```properties
#卡斯.奥森.同步.域名]大师
#卡斯.奥森.同步.url=赫特普斯：//idm.实例.org/同步
# cas.authn.syncope.name]
```




## 希罗认证

要了解有关此主题的更多内容，请 [](../installation/Shiro-Authentication.html)查看本指南。

此功能的主要转换设置可 [此处](Configuration-Properties-Common.html#authentication-principal-transformation) 配置密钥下 `cas.authn.shiro`。

此功能的密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.shiro`。



```properties
#卡斯. 奥森. 希罗. 要求权限 = 价值 1， 价值 2,...
#卡斯. 奥森. 希罗. 需要角色 = 价值 1， 价值 2,...
#卡斯. 奥森. 希罗. 位置 ] 类路径： 希罗.ini
# cas.authn.shiro.name]
```




## 可信身份验证

要了解有关此主题的更多内容，请 [](../installation/Trusted-Authentication.html)查看本指南。 此功能的主要分辨率和人员目录设置可 [此处](Configuration-Properties-Common.html#person-directory-principal-resolution) 配置密钥 `cas.authn.信任`下提供。



```properties
#cas.authn.trusted.name]
# 卡斯. 奥森. 信任. 秩序]
# 卡斯. 奥森. 信任. 远程校长头]
```




## WS-美联储委托认证

要了解有关此主题的更多内容，请 [](../integration/ADFS-Integration.html)查看本指南。



### 属性类型

为了构建最终认证本金，CAS 在收集主要属性时可配置以下 策略：

| 类型      | 描述                            |
| ------- | ----------------------------- |
| `中国科学院` | 使用 CAS 自己的属性分辨率机制和存储库提供的属性。   |
| `已提供`   | 使用委托的 WS-Fed 实例提供的属性。         |
| `双`     | 结合上述两种选项，CAS 属性存储库优先于 WS-Fed。 |




```properties
# cas.authn.wsfed[0].identity-provider-url=https://adfs.example.org/adfs/ls/
# cas.authn.wsfed[0].identity-provider-identifier=https://adfs.example.org/adfs/services/trust
# cas.authn.wsfed[0].relying-party-identifier=urn:cas:localhost
# cas.authn.wsfed[0].signing-certificate-resources=classpath:adfs-signing.crt
# cas.authn.wsfed[0].identity-attribute=upn

# cas.authn.wsfed[0].attributes-type=WSFED
# cas.authn.wsfed[0].tolerance=10000
# cas.authn.wsfed[0].attribute-resolver-enabled=true
# cas.authn.wsfed[0].auto-redirect=true
# cas.authn.wsfed[0].name=
# cas.authn.wsfed[0].attribute-mutator-script.location=file:/etc/cas/config/wsfed-attr.groovy

# cas.authn.wsfed[0].principal.principal-attribute=
# cas.authn.wsfed[0].principal.return-null=false

# Private/Public keypair used to decrypt assertions，如果有的话。
#cas. authn. wsfed[0]. 加密 - 私密密钥 ] 类路径： 私人.key
# 卡斯. 奥森. wsfed[0]. 加密 - 证书 - 类路径： 证书. crt
# cas. authn. wsfed[0]. 加密 - 私密密钥 = 无
```




### 签署 & 加密

[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 `cas.authn.wsfed[0].cookie`下，可 [此处](Configuration-Properties-Common.html#signing--encryption) 签署此功能的 & 加密设置。



## 多因素认证

要了解有关此主题的更多内容，请 [](../mfa/Configuring-Multifactor-Authentication.html)查看本指南。



```properties
#描述无法
无法联系到提供商的全球故障模式 # cas.authn.mfa.全球故障模式] 关闭

# 设计选择的属性来传达身份验证上下文
# cas.authn. mfa. 身份验证上下文属性 = 奥森康德

# 识别非浏览器 MFA 请求内容类型
# cas. authn. mfa. 内容类型 = 应用程序 / cas
```




### 多因素身份验证：全球触发器



```properties
•为所有人激活全球 MFA，无论其他设置如何
# 卡斯. authn. mfa. 全球提供商 - id = mfa - duo
```




### 多因素身份验证：身份验证属性触发器



```properties
• 基于身份验证元数据属性
激活 MFA 全球认证-属性-名称触发器]自定义归因名称
# cas.authn.mfa.全球认证-属性-值-注册=自定义注册价值
```




### 多因素身份验证：主要属性触发器



```properties
#根据主要属性
激活 MFA 全球 #cas.authn.mfa.全球-主要属性-名称触发器]成员 Of，编辑个人主要隶属关系

= 指定常规表达模式，在与单个提供商合作时触发多因素。
#
与多个多因素提供商合作时注释设置 # cas.authn.mfa.全球-校长属性-价值-注册]教师|斯塔夫

# 激活全球 MFA 基于主要属性和基于凹槽的谓词
# cas. authn. mfa. 全球 - 本金属性 - 谓词= 文件：
```




### 多因素身份验证：REST API 触发器

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.mfa.rest`。



### 多因素身份验证：凹槽触发器



```properties
• 激活基于格罗夫脚本的 Mfa
# 卡斯. 奥森. mfa. 格劳维脚本] 文件： 等 / 卡 / 姆法格鲁维旅行. groovy
```




### 多因素身份验证：Internet2 分组触发器



```properties
• 基于 Internet2 的分组
激活 Mfa # 卡斯. 奥森. mfa. 组字段 = 名称|扩展|DISPLAY_NAME|DISPLAY_EXTENSION
```




### 多因素身份验证：Http 请求触发器



```properties
#卡斯. 奥森. mfa. 请求参数 ] authn_method
[ 卡斯. 奥森. mfa. 请求标题] authn_method
# 卡斯. 奥森. mfa. 会话属性 = authn_method
```




### 多因素身份验证：提供商选择



```properties
• 选择 MFA 提供商，如果解决了多个问题，则通过 Groovy 脚本
# cas.authn.mfa.提供商-选择器-凹槽-脚本/文件：/等/cas/mfaGroovySelector.groovy

# 启用提供商选择菜单，如果解决了多个
cas.authn.mfa.提供商选择启用
```




### 多因素受信任的设备/浏览器

要了解有关此主题的更多内容，请 [](../mfa/Multifactor-TrustedDevice-Authentication.html)查看本指南。



```properties
#cas.authn.mfa.信任.认证-上下文属性]是来自受信任的多因素验证
# cas.authn.mfa.信任.设备注册启用=真实
# cas.authn.mfa.信任.key发电机类型=默认|遗产
```


以下策略可用于生成可信设备记录的密钥：

| 类型   | 描述                              |
| ---- | ------------------------------- |
| `违约` | 使用用户名、设备名称和设备指纹的组合来生成设备密钥。      |
| `遗产` | 荒废的。 使用用户名、记录日期和设备指纹的组合来生成设备密钥。 |




#### 签署 & 加密

[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.mfa.信任`下，可在此处签名 & 加密设置。



#### 杰森存储



```properties
#卡斯. 奥森. mfa. 信任. json. 位置 [ 文件] 等 / 卡斯 / 康菲格 / 信任开发. json
```




#### 京打存储

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.mfa.trusted.jpa`。



#### 沙发数据库存储

此功能的配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.authn.mfa. 信任`。



#### 蒙古数据库存储

此功能的配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.authn.mfa. 信任`。



#### 迪纳莫德布存储

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥下 `cas.authn.mfa.信任`。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.authn.mfa.信任.dynamo-db`。



```properties
#卡斯. 奥森. mfa. 信任. 迪纳莫 - 德布. 表名 = 迪纳莫德布卡斯法信托记录
```




#### 休息存储



```properties
#卡斯. 奥森. mfa. 信任. rest. 终点
```




#### 受信任的设备指纹



```properties
#cas. authn. mfa. trusted. 设备指纹. 组件分离器]  

# 卡斯. 奥森. mfa. 信任. 设备指纹. cookie. 启用] 真正的
# 卡斯. 奥森. mfa. 信任. 设备 - 指纹. cookie. 订单 = 1

# 卡斯. authn. mfa. 信任. 设备 - 指纹. 客户 - ip. 启用] 真实
# cas. authn. mfa. 信任. 设备 - 指纹. 客户 - ip. 订单 = 2

# 卡斯. authn. mfa. 信任. 设备指纹. 地理位置. 启用 = 假
# cas. authn. mfa. 信任. 设备 - 指纹. 地理位置. 订单 = 4
```


设备指纹饼干组件可以配置与常见的饼干属性发现 [这里](Configuration-Properties-Common.html#cookie-properties) 配置键下配置键 `cas.authn.mfa.trusted.设备指纹.cookie`。 默认 Cookie 名称设置为 `MFATRUSTED` ，默认最大值设置为 2592000</code>`。</p>

<p spaces-before="0">设备指纹曲奇组件支持 & 加密签名。 <a href="Configuration-Properties-Common.html#signing--encryption">的签名和加密密钥都是 JWK</a> 大小 <code>512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.mfa.trusted.设备指纹.cookie`下，可在此处签名 & 加密设置。



#### 清洁工

计划在后台运行一个更清洁的过程，以清理过期和过时的门票。 此部分控制该过程应如何表现。 此功能的调度器设置 可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥下 `cas.authn.mfa.trusted.清洁`。




### 简单的多因素身份验证

要了解有关此主题的更多内容，请 [](../mfa/Simple-Multifactor-Authentication.html)查看本指南。



```properties
#cas.authn.mfa.simple.name]
# 卡斯. 奥森. mfa. 简单. 订单]
# 卡斯. 奥森. mfa. 简单. 秒内杀人时间 = 30
# 卡斯. 奥森. mfa. 简单. 令牌长度 = 6
```


此功能的电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications) 配置密钥下 `cas.authn.mfa.简单`。 此功能的 SMS 通知设置 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 配置密钥下 `cas.authn.mfa.简单`。

此提供商的多因素身份验证旁路设置可 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥下 `cas.authn.mfa.简单`。



### 谷歌身份验证器

要了解有关此主题的更多内容，请 [](../mfa/GoogleAuthenticator-Authentication.html)查看本指南。



```properties
[卡斯. 奥森. mfa. gauth. 发行人]
[ 卡斯. 奥森. mfa. 高斯. 标签]

[ 卡斯. 奥森. mfa. 高斯. 窗口大小 ] 3
# 卡斯. 奥森. mfa.
. [30
] cas. authn. mfa. gauth. rank = 0
# 卡斯. authn. mfa. gauth. 信任设备启用 = 假
# cas. authn. mfa. gauth. 多设备注册启用 = 假

# cas.authn.mfa.gauth.name]
# 卡斯. authn. mfa.
```


此提供商的多因素身份验证旁路设置可 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥下 `cas.authn.mfa.gauth`。 此功能的调度器设置可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥下 `cas.authn.mfa.gauth.清洁`。



#### 签署 & 加密

[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。  `cas.authn.mfa.gauth`的配置密钥下， [此处](Configuration-Properties-Common.html#signing--encryption) 可签署此功能的 & 加密设置。



#### 谷歌身份验证器沙发数据库

此功能的配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.authn.mfa.gauth`。  



#### 谷歌身份验证器杰森



```properties
#卡斯. 奥森. mfa. 高斯. json. 位置 [ 文件] 某处. json
```




#### 谷歌身份验证器休息

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.mfa.gauth.rest`。

此外，代币可以通过 REST 使用以下设置进行管理：



```properties
#卡斯. 奥森. mfa. 高斯. rest. 令牌 - 乌尔普斯/ 某处. 高斯.com
```




#### 谷歌身份验证器蒙哥德布

此功能的配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.authn.mfa.gauth`。  此功能还可提供以下设置：



```properties
#卡斯. 奥森. mfa. 高斯. 蒙戈. 令牌收藏 » 蒙古数据库授权人托肯存储库
```




#### 谷歌身份验证器LDAP

此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.mfa.gauth.ldap`。 

此功能还可提供以下设置：



```properties
#卡斯. 奥森. mfa. 高斯. ldap. 帐户属性名称 ] 高斯记录
```




#### 谷歌身份验证器雷迪斯

此功能的配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.authn.mfa.gauth`。  




#### 谷歌身份验证器 JPA

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.mfa.gauth.jpa`。



### 尤比基

要了解有关此主题的更多内容，请 [](../mfa/YubiKey-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. mfa. yubikey. 客户 - id]
[ 卡斯. 奥森. mfa. yubikey. 秘密钥匙]
· 卡斯. 奥森. mfa. yubikey. 排名 = 0
# 卡斯. 奥森. mfa. 尤比基. api - urls •
• 卡斯. 奥森. mfa. yubikey. 信任设备启用 = 假
# 卡斯. authn. mfa. yubikey. 多设备注册启用 = 假

= cas.authn.mfa.yubikey.name ]
# 卡斯. 奥森. mfa. yubikey.
```


此提供商的多因素身份验证旁路设置 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥下 `cas.authn.mfa.yubikey`。



#### 尤比基休息设备商店

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.mfa.yubikey.rest`。



#### 尤比基杰森设备商店



```properties
#卡斯.奥森.姆法.尤比基.杰森档案[文件]等/卡斯/设备注册.json
```




#### 尤比基允许设备商店



```properties
#卡斯. 奥森. mfa. yubikey. 允许设备. uid1 ] 尤比基 · 普布利西德 1
# 卡斯. 奥森. mfa. yubikey. 允许设备. uid2 ] 尤比基 · 普布利奇 2
```




#### 尤比基注册记录加密和签名



```properties
#卡斯. 奥森. mfa. 尤比基. 加密. 启用] 真实
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 签署此功能的 & 加密设置 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.mfa.yubikey`下提供。



### 尤比基 JPA 设备商店

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.mfa.yubikey.jpa`。



### 尤比基沙发数据库设备商店

此功能的配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.authn.mfa.yubikey`。



### 尤比基蒙戈德布设备商店

此功能的配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.authn.mfa.yubikey`。



### 尤比基迪纳莫德布设备商店

此功能的配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥下 `cas.authn.mfa.yubikey`。



### 尤比基雷迪斯设备商店

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.authn.mfa.yubikey`。



### 半径OTP

要了解有关此主题的更多内容，请 [](../mfa/RADIUS-Authentication.html)查看本指南。



```properties
#cas. authn. mfa. radius. 排名 = 0
# 卡斯. 奥森. mfa. radius. 信任设备启用 = 假
# 卡斯. authn. mfa. radius. 允许认证尝试 ] - 1
= cas.authn.mfa.radius.name =
# 卡斯. authn. mfa. 半径.
```


此功能的半径设置可 [此处](Configuration-Properties-Common.html#radius-configuration) 配置密钥 `cas.authn.mfa.radius`下提供。

此提供商的多因素身份验证旁路设置可 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥下 `cas.authn.mfa.半径`。



### 双安全

要了解有关此主题的更多内容，请 [](../mfa/DuoSecurity-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. mfa. duo[0]. duo 秘密钥匙]
# 卡斯. 奥森. mfa. duo[0]. 排名 + 0
# 卡斯. 奥森. mfa. duo[0]。 双应用键 ]
[ 卡斯. 奥森. mfa. duo[0]. duo 集成键]
[ 卡斯. 奥森. mfa. duo[0]. 杜 - 阿皮 - 主机]
# 卡斯 . 奥森. mfa. duo[0]. 支持信任设备 = 假
# 卡斯. 奥森. mfa. duo[0]. id _ mfa - duo
# 卡斯. 奥森. mfa. duo[0]。 注册- url=_/注册.示例.org/双注册
# 卡斯.奥森.mfa.duo[0].名称]
# 卡斯.奥森.mfa.duo[0].订单
```


此提供商的多因素身份验证旁路设置 可 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 下 配置密钥 `cas.authn.mfa.duo[0]`。




#### 网络SDK

`双应用程序键` 是一个必需的字符串，至少40个字符长，你 生成和保密的二重奏。 您可以在Python中生成随机字符串，其中：



```python
导入操作系统、哈希利布
打印哈希利布.sha1（os.urandom（32）。
```




#### 通用提示

通用提示不再要求您生成和使用应用密钥值。 相反，它需要一个 *客户端ID* 和 *客户端秘密*， 这是已知的，并教CAS使用集成密钥和秘密键配置设置。 当您将 CAS 注册为受保护的应用程序时，您需要从 Duo 安全处获取集成密钥、 密钥和 API 主机名。 



### FIDO2网络授权

要了解有关此主题的更多内容，请 [](../mfa/FIDO2-WebAuthn-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. mfa. web - authn. 允许起源]
# 卡斯. 奥森. mfa. web - authn. 应用程序 - id ]
# 卡斯. 奥森. mfa. web - authn. 依靠党名 [ 卡斯 WebAuthn 
# 卡斯. authn. mfa. web - authn. 依靠党 id]

# 卡斯. 奥森. mfa. web - authn. 显示名称属性 - 显示名称
# 卡斯. 奥森。 mfa. web - authn. 允许初级认证 = 假

# cas. authn. mfa. web - authn. 允许未要求的扩展 = 虚假
# 卡斯. authn. mfa. 网络 - 奥森. 允许 - 不特鲁 证明= 假
# 卡斯. authn. mfa. web - authn. 验证签名计数器] 真实
# 卡斯. authn. mfa. web - authn. 证明 - 运输偏好 = 直接|间接|没有
# 卡斯. authn. mfa. web - authn. 信任设备 - 元数据. 位置]

# 卡斯. authn. mfa. web - authn. 信任设备启用 = 虚假

# 卡斯. 奥森. mfa. web - authn. 失效设备 = 30
# 卡斯. 奥森. mfa. web - 奥森. 过期设备 - 时间单位 = 天
```


此提供商的多因素身份验证旁路设置 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥下 `cas.authn.mfa.web-authn`。

签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.mfa.web-authn`下，可 & 此功能的加密设置进行签名。                                                      



### FIDO2 网络自动清洁剂

此功能的调度器设置 [此处 可用，](Configuration-Properties-Common.html#job-scheduling) 配置密钥 `cas.authn.mfa.web-authn.清洁`。



### 菲多 2 网络授权 Json



```properties
* 卡斯. 奥森. mfa. 网络 - 奥森. json. 位置： 文件/ 等 / 卡斯 / 配置 / 设备. json
```




### FIDO2 网络授权蒙古数据库

此功能的常见配置设置 可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.authn.mfa.web-authn`。



### FIDO2 网络授权 LDAP

此功能的常见配置设置 可用 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.mfa.web-authn.ldap`。



```properties
# 卡斯. 奥森. mfa. 网络 - 奥森. ldap. 帐户属性名称 ] 卡斯韦布帐户记录
```




### 菲多 2 网络授权 Jpa

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.mfa.web-authn.jpa`。



### FIDO2 网络授权雷迪斯

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.authn.mfa.web-authn`。



### FIDO2网络授权发电机数据库

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥下 `cas.authn.mfa.web-authn`。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.authn.mfa.web-authn. dynamo-db`。



### 菲多U2F

要了解有关此主题的更多内容，请 [](../mfa/FIDO-U2F-Authentication.html)查看本指南。



```properties
#cas.authn.mfa.u2f.排名=0
= cas.authn.mfa.u2f.name=
# 卡斯.奥森.mfa.u2f.订单=

• U2F 设备注册请求到期：
#cas.authn.mfa.u2f.过期注册=30
# 卡斯.奥森.mfa.u2f.过期注册时间单位=秒

# U2F 设备自注册以来的过期， 独立于上次使用的时间：
# 卡斯. 奥森. mfa. u2f. 过期设备 = 30
# 卡斯. 奥森. mfa. u2f. 过期设备 - 时间单位 = 天
```


此提供商的多因素身份验证旁路设置 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥 `cas.authn.mfa.u2f`。 签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 </code>`配置密钥</a>
此处 <a href="Configuration-Properties-Common.html#signing--encryption">可签署此功能的 & 加密设置。</p>

<h3 spaces-before="0">菲多 U2f 杰森</h3>

<pre><code class="properties">* 卡斯. 奥森. mfa. u2f. json. 位置 [ 文件...
`</pre> 



### 菲多U2F清洁剂

此功能的调度器设置 可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥下 `cas.authn.mfa.u2f.清洁`。



### 菲多U2F沙发数据库

此功能的常见配置设置 可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.authn.mfa.u2f`。



### 菲多 U2f 蒙古德布

此功能的常见配置设置 可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥下 `cas.authn.mfa.u2f`。



### 菲多U2F发电机数据库

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥下 `cas.authn.mfa.u2f`。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.authn.mfa.u2f.dynamo-db`。



### 菲多 U2f 雷迪斯

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.authn.mfa.u2f`。



### 菲多 U2f Jpa

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.mfa.u2f.jpa`。



### 菲多U2F休息

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.mfa.u2f. rest`。



### 菲多 U2f 格罗夫



```properties
# 卡斯. 奥森. mfa. u2f. 格劳维. 位置： 文件： 等 / 卡斯 / 康菲格 / 菲多. 格劳维
```




### 旋转安全

要了解有关此主题的更多内容，请 [](../mfa/SwivelSecure-Authentication.html)查看本指南。



```properties
#cas. authn. mfa. 旋转. 旋转 - 图- 图像 - url

= 乌森. mfa. sfa. 旋转. 共享秘密 ] Th3Sh@r3d$ecret
[ 卡斯. 奥森. mfa. 旋转. 忽略 - ssl 错误] 假
# 卡斯. 奥森. mfa. swivel. 排名 = 0
[ cas.authn.mfa.swivel.name]
# 卡斯. 奥森. mfa. 旋转. 订单
```


此提供商的多因素身份验证旁路设置可 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥 `cas.authn.mfa.旋转`下提供。




### 奥蒂

要了解有关此主题的更多内容，请 [](../mfa/AuthyAuthenticator-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. mfa. authy. api - key]
[ 卡斯. 奥森. mfa. authy. api - url]
[ 卡斯. 奥森. mfa. authy. 电话属性 ] 电话
[ 卡斯. 奥森. mfa. authy. 邮件属性] 邮件
# 卡斯。 奥森. mfa. authy. 国家代码 = 1
[ 卡斯. 奥森. mfa. authy. 强制验证] 真实
[ 卡斯. authn. mfa. authy. 信任设备启用] 假
[ cas.authn.mfa.authy.name]
[ 卡斯. 奥森. mfa. authy. 秩序]
```


此提供商的多因素身份验证旁路设置 可用 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥下 `cas.authn.mfa.authy`。




### 接受托托

要了解有关此主题的更多内容，请 [](../mfa/Acceptto-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. mfa. 接受. 申请 - id]
# 卡斯. 奥森. mfa. 接受. 秘密]
# 卡斯. 奥森. mfa. 接受组织 - id]
[ 卡斯. 奥森. mfa. 接受托. 组织秘密]

# 卡斯. 奥森. mfa. 接受 选择 url=/mfa.接受.com/mfa/索引
# 卡斯.奥森.mfa.接受托普斯托.阿皮-乌拉普斯//mfa.接受.com/api/v9/
# 卡斯.奥森.mfa.接受.消息]您想通过 CAS 登录吗？
#cas. authn. mfa. 接受. 超时 = 120
= cas.authn.mfa.acceptto.email 属性 [ 邮件    
# 卡斯. 奥森. mfa. 接受. 组属性]    

# 卡斯. 奥森. mfa. 接受. 注册 - api - url.com= 集成/v1/mfa/认证
[ 卡斯. authn. mfa. 接受注册 - 阿皮 - 公共钥匙] 文件： 路径 / 到 / 公共钥匙. pem

[ cas.authn.mfa.acceptto.name]
[ 卡斯. 奥森. mfa. 接受. 订单]
# 卡斯. 奥森. mfa. 接受
```


此提供商的多因素身份验证旁路设置可 [此处](Configuration-Properties-Common.html#multifactor-authentication-bypass) 配置密钥 `cas.authn.mfa.接受`。



## 萨姆尔核心

控制 CAS 内的核心 SAML 功能。



```properties
#cas. saml - core. 票务 - 萨姆 2] 假
# 卡斯. 萨姆 - 核心. 偏斜津贴 = 5
# 卡斯. 萨姆 - 核心. 问题长度 = 30
# 卡斯. 萨姆尔核心. 属性 -名称空间=http：//www.ja-sig.org/products/cas/
#卡斯.萨姆-核心.发行人=本地主机
# cas.saml-core.安全-管理器=组织.apache.xerces.利用.安全管理器
```




## 萨姆尔·伊德普

允许 CAS 成为 SAML2 身份提供商。

要了解有关此主题的更多内容，请 [](../installation/Configuring-SAML2-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 实体 - id


= 认证语境类映射[0]=骨灰盒：绿洲：名称：tc：SAML：2.0：ac：类：一些类名称 ->mfa-duo
# cas.authn. saml-idp. 身份验证上下文类映射[1]=https://refeds.org/profile/mfa ->mfa - 高斯

# cas. authn. saml - idp. 属性友好名称[0][ 骨灰盒： oid： 1.3.6. 1.4.1.5923.1.1.1.6->爱德华 · 普林格纳

· 卡斯. authn. saml - idp. 属性查询配置文件启用 = 真实
```




### 属性名称格式

单个属性的名称格式可以映射到许多预先定义的格式，或您自己选择的自定义格式。 在最终 SAML 响应中编码的给定属性可能包含以下任何名称格式：

| 类型              | 描述                                        |
| --------------- | ----------------------------------------- |
| `基本`            | 映射到 `骨灰盒的属性：绿洲：名称：tc：SAML：2.0：阿特纳梅格式：基本`。 |
| `乌里`            | 映射到 `骨灰盒的属性：绿洲：名称：tc：SAML：2.0：阿特纳梅格式：乌里`。 |
| `未指定`           | 映射到 `骨灰盒的属性：绿洲：名称：tc：SAML：2.0：阿特纳梅格式：基本`。 |
| `骨灰盒：我的：自己的：格式` | 将属性映射到 `骨灰盒：我的：格式`。                       |




### 萨姆尔元数据



```properties
#卡斯. authn. 萨姆尔 - 伊德普. 元数据. 位置 [ 文件] 等 / 卡斯 / 萨姆尔
# 卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. 元数据备份位置]

# 卡斯. 奥森. 萨姆 - 伊德普. 梅特 数据. 缓存 - 到期分钟 = 30
# cas. authn. saml - idp. 元数据. 失败 - 快 - 真
# 卡斯. authn. 萨姆尔 - idp. 元数据. 私人键 - 阿尔格 - 名称 ] Rsa
# cas. authn. saml - idp. 元数据. 要求有效元数据] 真实
# 卡斯. authn. saml - idp. 元数据. 强制元数据刷新 ] 真实

# 卡斯. 奥森. 萨姆尔 - idp. 元数据. 基本 - 奥森 - 用户名]
[ 卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. 基本 - 奥森密码]
[ 卡斯. 奥森. 萨姆 - 伊德普. 元数据. 支持内容类型]
```




#### 萨姆尔元数据 JPA

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.saml-idp.元数据.jpa`。



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. jpa. idp - 元数据启用] 真实
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 签署此功能的 & 加密设置可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥下 `cas.authn.saml-idp.元数据.jpa`。



#### 萨姆尔元数据沙发数据库

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥 `cas.authn.saml-idp.元数据`下提供。



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. 沙发 - db. idp - 元数据启用 ] 真实
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.saml-idp.元数据.mongo`下，可 & 此功能的加密 设置进行签名。



#### 萨姆尔元数据吉特



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. git. idp - 元数据启用 ] 真实
```


此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#git-configuration) 配置密钥 `cas.authn.saml-idp.元数据`下提供。

签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.saml-idp.元数据.git`下，可在此处签名 & 加密设置。



#### 萨姆尔元数据蒙哥德布

此功能的常用配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.authn.saml-idp.元数据`下提供。



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. 蒙戈. idp - 元数据收集 - 萨姆 - 伊德普 - 元数据
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 配置密钥 `cas.authn.saml-idp.元数据.mongo`下，可 [此处](Configuration-Properties-Common.html#signing--encryption) 此功能签署 & 加密设置。



#### 萨姆尔元数据休息

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.saml-idp.元数据.rest`。



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. rest. idp - 元数据启用] 真实
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.saml-idp.元数据.rest`下，可在此处签名 & 加密设置。



#### 萨姆尔元数据亚马逊S3

此功能的常见 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.authn.saml-idp.元数据.amazon-s3`。

签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.saml-idp.元数据.amazon-s3`下，可此处签名 & 加密 设置。



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 元数据. 亚马逊 - s3. 桶名 [ 萨姆 - 斯普桶
# 卡斯. 奥森. 萨姆 - 伊德普. 元数据. 蒙戈. idp - 元数据 - 桶名称 - 萨姆 - 伊德普桶
```




### 萨姆尔注销



```properties
#cas. authn. saml - idp. logout. 强制签名 - 注销请求] 真正的
[ 卡斯. 奥森. 萨姆尔 - 伊普. logout. 单注销回拨 - 禁用] 假
# 卡斯. 奥森. 萨姆尔 - idp. logout. 登录 - 回复 ] 虚假
# cas. authn. 萨姆尔 - 伊德普. logout. 发送 - 注销 - 响应 ] 真实
[ 卡斯. 奥森. 萨姆 - 伊普. logout. 注销 - 响应绑定]
```




### SAML 算法 & 安全性



```properties
#卡斯. 奥森. 萨姆尔 - 伊德普. 阿尔格斯. 覆盖签名 - 规范 - 算法]
# 卡斯. 奥森. 萨姆尔 - 伊德普. 阿尔格斯. 覆盖数据加密算法]
# 卡斯. 奥森。 萨姆 - 伊德普. algs. 覆盖键加密算法]
# 卡斯. 奥森. 萨姆尔 - 伊德普. 阿尔格斯. 覆盖阻止加密算法]
# 卡斯. 奥森. 萨姆尔 - 伊德普。 藻类. 覆盖允许的算法]
# 卡斯. 奥森. 萨姆尔 - 伊德普. 阿尔格斯. 覆盖签名参考 - 消化方法]
# 卡斯. 奥森. 萨姆尔 - 伊德普. 阿尔格斯. 覆盖签名 - 算法=
# 卡斯. authn. saml - idp. algs. 覆盖阻止签名签名算法]
# 卡斯. 奥森. 萨姆尔 - 伊德普. algs. 覆盖允许签名签名算法]
```




### 萨姆尔响应



```properties
#cas. authn. saml - idp. 响应. 默认身份验证上下文类]
# 卡斯. 奥森. 萨姆尔 - 伊德普. 响应. 默认属性 - 名称格式 = uri
#卡斯. 奥森. 萨姆尔 - 伊德普. 响应. 符号错误 [ 假
] 卡斯. 奥森. 萨姆尔 - 伊德普. 响应. 签名 - 证书类型 = X509|基本
# cas. authn. saml - idp. 响应. 属性名称格式 - 属性名称 ->基本|uri|未指定|习惯格式等,...
```




### 萨姆尔门票



```properties
#卡斯. 奥森. 萨姆尔 - 伊普. 票. 萨姆尔 - 文物 - 缓存存储名称 ] 萨姆尔事实缓存
# 卡斯. 奥森. 萨姆 - 伊普. 票. 萨姆属性 - 查询 - 缓存存储 - 名称 - 萨姆尔属性
```




### 萨姆尔配置文件



```properties
#cas. authn. saml - idp. 配置文件. slo. url - 解码 - 重定向请求 = 虚假
# cas. authn. saml - idp. 配置文件. sso. url - 解码 - 重定向请求 = 假
# cas. authn. saml - idp. 配置文件.
```




## 萨姆尔·斯普

允许 CAS 注册并启用一些内置的 SAML 服务提供商集成。 要了解有关此主题的更多内容，请 [](../integration/Configuring-SAML-SP-Integrations.html)查看本指南。

<div class="alert alert-warning"><strong>记得</strong><p>此处列出的 SAML2 服务提供商集成只是尝试根据供应商拥有的服务提供商提供的已知和有记录的集成指南和配方实现 CAS 配置自动化。 随着时间的推移，这些食谱可以改变和打破CAS。</p></div>

所有 SAML2 服务提供商的配置设置 [可在此处](Configuration-Properties-Common.html#saml2-service-provider-integrations)。

| 服务提供商          | 配置密钥                        | 属性                                                                 |
| -------------- | --------------------------- | ------------------------------------------------------------------ |
| 吉特拉布           | `卡斯. 萨姆尔 - 斯普 . 吉特拉布`       | `last_name`，`first_name`，`名字`                                      |
| 希普查特           | `卡斯. 萨姆 - 斯普 · 希普查特`        | `first_name``last_name``冠军`                                        |
| 滴盒             | `卡斯. 萨姆 - 斯普 . 滴盒`          | `邮件`                                                               |
| 开放马拉松          | `卡斯. 萨姆 - 斯普 - 打开马拉松`       | `电子邮件`， `爱德华·佩尔森`                                                  |
| 埃格尼特           | `卡斯. 萨姆尔 - 斯普 · 埃格尼特`       | 不适用                                                                |
| 埃弗布里奇          | `卡斯. 萨姆 - 斯普 - 永远桥`         | 不适用                                                                |
| 单纯             | `卡斯. 萨姆 - 斯普 . 简单`          | 不适用                                                                |
| 应用动态           | `卡斯. 萨姆尔 - sp.app 动态`       | `用户。开放ID`， `User.email`， `用户。全名`， `访问控制`， `团体会员`                   |
| 尤贾             | `卡斯. 萨姆尔 - 斯普 · 尤贾`         | 不适用                                                                |
| 单纯             | `卡斯. 萨姆 - 斯普 . 简单`          | 不适用                                                                |
| 新遗物            | `卡斯. 萨姆尔 - sp. 新遗物`         | 不适用                                                                |
| 阳光州教育 & 研究计算联盟 | `卡斯. 萨姆尔 - 斯塞尔卡`            | 不适用                                                                |
| 切尔韦尔           | `卡斯. 萨姆 - 斯普 · 切尔韦尔`        | 不适用                                                                |
| 法米斯            | `卡斯.萨姆尔-斯普·法米斯`             | 不适用                                                                |
| 拜恩德            | `卡斯. 萨姆 - 斯普 · 比恩德`         | 不适用                                                                |
| 网络顾问           | `卡斯. 萨姆 - 斯普 . 韦伯顾问`        | `乌伊德`                                                              |
| Adobe 创意云      | `卡斯. 萨姆 - 斯普 - 阿多贝 - 云`     | `第一个名字`， `最后一个名字`， `电子邮件`                                          |
| 保护人类           | `卡斯. 萨姆 - 斯普 - 桑斯 - 斯`      | `第一个名字`， `上一个名字`， `范围userId`， `部门`， `参考`， `电子邮件`                   |
| 简易IEP          | `卡斯. 萨姆 - 斯普 . 易 - 伊普`      | `员工Id`                                                             |
| 无限校园           | `卡斯. 萨姆 - 斯普无限校园`           | `员工Id`                                                             |
| 松弛             | `卡斯. 萨姆 - 斯莱克`              | `User.Email`， `用户名`， `first_name`， `last_name`， `员工id`             |
| 曾德克            | `卡斯. 萨姆尔 - 斯普 · 曾德斯克`       | `组织`， `标签`， `电话`， `角色`， `电子邮件`                                     |
| 加特纳            | `卡斯. 萨姆尔 - 斯普 · 加特纳`        | `骨灰盒：oid：2.5.4.42`， `骨灰盒：2.5.4.4`， `骨灰盒：0.9.2342.19200300.100.1.3` |
| 弧形地理信息系统       | `卡斯. 萨姆 - 斯普 · 阿克吉斯`        | ` <code>邮件`</code> ``                                           |
| 利益聚焦           | `卡斯. 萨姆尔 - sp. 利益焦点`        | `利益焦点统一`                                                           |
| 办公室365         | `卡斯. 萨姆尔 - 斯普办公室 365`       | `国际开发署`， `不可改变`                                                    |
| 萨管理            | `卡斯. 萨姆尔 - sp.sa 管理`        | `邮件`                                                               |
| 销售队伍           | `卡斯. 萨姆 - 斯普销售部队`           | `埃杜佩尔森原则名`                                                         |
| 工作日            | `卡斯.萨姆尔-斯普工作日`              | 不适用                                                                |
| 学术著作           | `卡斯. 萨姆尔 - sp. 学术作品`        | `显示名`                                                              |
| 缩放             | `卡斯. 萨姆斯普. 缩放`              | `邮件`， `sn`， `给名`                                                   |
| 埃弗诺特           | `卡斯. 萨姆 - 斯普 · 埃弗诺特`        | `电子邮件`                                                             |
| 画面             | `卡斯. 萨姆尔 - 斯普 · 表`          | `用户名`                                                              |
| 阿萨纳            | `卡斯. 萨姆尔 - 斯普 · 阿萨纳`        | `电子邮件`                                                             |
| 箱              | `卡斯. 萨姆 - 斯普盒`              | `电子邮件`， `第一个名字`， `最后一个名字`                                          |
| 立即服务           | `卡斯. 萨姆 - 斯普服务 - 现在`        | `埃杜佩尔森原则名`                                                         |
| 净合作伙伴          | `卡斯. 萨姆尔 - sp.net 合作伙伴`     | `学生ID`                                                             |
| 韦韦克斯           | `卡斯. 萨姆尔 - 斯普 . 韦伯克斯`       | `第一个名字`， `最后一个名字`                                                  |
| 不一起            | `卡斯. 萨姆尔 - sp.in - 共同`      | `埃杜佩尔森原则名`                                                         |
| 亚马逊河           | `卡斯. 萨姆尔 - 斯普 . 亚马逊`        | ``， `奥斯罗尔西翁`                                                       |
| 同意解决方案         | `卡斯. 萨姆 - 斯普 - 康库尔解决方案`     | `电子邮件`                                                             |
| 投票每个的地方        | `卡斯. 萨姆 - 斯普 - 波尔无处不在`      | `电子邮件`                                                             |
| 多库西            | `卡斯. 萨姆 - 斯普 . 多库符号`        | `电子邮件`， `给 <code>姓`的</code>， `员工编号`                             |
| 野生动物园在线        | `卡斯. 萨姆尔 - sp. 野生动物园在线`     | `电子邮件`， ` <code>姓`、 `雇员`、`人`</code>                             |
| 布莱克博德          | `卡斯. 萨姆 - 斯普 . 布莱克 - 鲍德`    | `电子邮件`， `爱德华·佩尔森`                                                  |
| 给坎普斯           | `卡斯. 萨姆 - 斯普 - 给校园`         | `电子邮件`， ` <code>姓`的</code>， `显示`                                |
| 扭曲线            | `卡斯. 萨姆 - 斯普 - 扭曲线`         | `电子邮件`， ` <code>名`， `姓`</code>，姓</code>， ` <code>名员工`        |
| 火箭聊天           | `卡斯. 萨姆 - sp. 火箭聊天`         | `电子邮件`， `cn`， `用户名`                                                |
| 武器软件           | `卡斯. 萨姆 - 斯普 - 武器软件`        | `电子邮件`， `乌伊德·`， `爱德华·佩尔森`                                          |
| 托普哈特           | `卡斯. 萨姆尔 - sp.top 帽子`       | `电子邮件`， `爱德华·佩尔森`                                                  |
| 学术健康计划         | `卡斯. 萨姆 - sp. 学术 - 健康计划`    | `电子邮件`， `给`， `姓`， `学生id`                                           |
| 合流             | `卡斯. 萨姆 - 斯普汇合`             | `电子邮件`， ` <code>姓`的</code>， ``， `显示`                            |
| 吉拉             | `卡斯. 萨姆尔 - 斯普 · 吉拉`         | `电子邮件`， ` <code>姓`的</code>， ``， `显示`                            |
| 崩溃计划           | `卡斯. 萨姆 - 斯普 - 崩溃计划`        | `电子邮件`， ``， `姓`                                                    |
| 艾玛             | `卡斯. 萨姆尔 - 斯普 · 艾玛`         | `电子邮件`， ``， `姓`                                                    |
| 夸夸其包           | `卡斯. 萨姆 - 斯普 . 夸夸其来`        | `电子邮件`， `给 <code>姓`的</code>， `雇员`， `爱德华·佩尔森`                    |
| 尼奥戈夫           | `卡斯. 萨姆尔 - 斯普 . 尼奥戈夫`       | `电子邮件`， `不可变`                                                      |
| 津布拉            | `卡斯. 萨姆尔 - 斯普 · 齐姆布拉`       | `电子邮件`                                                             |
| 寻呼机杜蒂          | `卡斯. 萨姆 - 斯普 . 寻呼机职责`       | `电子邮件`                                                             |
| 克拉尼姆咖啡馆        | `卡斯. 萨姆尔 - 斯普 - 克拉尼姆 - 咖啡馆` | `电子邮件`， `爱德华`， `显示名`， `爱德华斯科普达`， `学生id`                            |
| C 中心           | `卡斯. 萨姆尔 - 斯普科`             | `电子邮件`， `爱德华`， `显示名`， `爱德华斯科普达`， ``， `姓`， `姓`， `姓`， `教宗`           |


**注意**：对于 InCommon 和其他元数据聚合，可以指定多个实体 ID 来 [InCommon 元数据](https://spaces.internet2.edu/display/InCFederation/Metadata+Aggregates) 筛选。 实体ids 可以是常规的表达模式，并映射到注册表中 CAS `服务ID` 字段。 签名位置必须是用于签署元数据的公钥。



## 打开ID连接

允许 CAS 成为开放式连接提供商 （OP）。 要了解有关此主题的更多内容，请 [](../installation/OIDC-Authentication.html)查看本指南。



```properties
#cas.authn.oidc.发行人=本地主机：8080/卡斯/奥德克
# 卡斯.奥森.奥特克.偏斜+5

# cas.authn.oidc.动态客户端-注册模式]打开|保护

# cas. authn. oidc. 主题类型= 公共， 对
# cas. authn. oidc. 范围= 开放， 配置文件， 电子邮件， 地址， 电话， offline_access
# cas. authn. oidc. 索赔® 子， 名称， preferred_username， family_name， [
] given_name， middle_name， given_name， 配置文件， •
• 图片、昵称、网站、区域信息、区域、updated_at、出生日、
• 电子邮件、email_verified、phone_number、phone_number_verified、地址

。 响应类型支持=代码、令牌、id_token令牌
# cas.authn.oidc.反省支持的身份验证方法=client_secret_basic
# cas.authn.oidc.索赔类型支持+ 正常
# cas. authn. oidc. 授予类型支持 = authorization_code， 密码， client_credentials， refresh_token
# 卡斯. authn. oidc. 令牌端点 - 身份验证方法支持 = client_secret_basic， client_secret_post private_key_jwt，client_secret_jwt
#cas.authn.oidc.代码挑战方法支持+平原，S256

# cas.authn.oidc.id 令牌签名-藻类值支持=无，RS256,RS384,RS512,PS256,PS384,PS512,ES256,ES384,ES512,HS256,HS384,HS512
# cas.authn.oidc.id-token-encryption-alg-values-supported=RSA1_5,RSA-OAEP,RSA-OAEP-256,A128KW,A192KW,A256KW,\
    A128GCMKW,A192GCMKW,A256GCMKW,ECDH-ES,ECDH-ES+A128KW,ECDH-ES+A192KW,ECDH-ES+A256KW
# cas.authn.oidc.id-token-encryption-encoding-values-supported=A128CBC-HS256,A192CBC-HS384,A256CBC-HS512,A128GCM,A192GCM,A256GCM

# cas.authn.oidc.user-info-signing-alg-values-supported=none,RS256,RS384,RS512,PS256,PS384,PS512,ES256,ES384,ES512,HS256,HS384,HS512
# cas.authn.oidc.user-info-encryption-alg-values-supported=RSA1_5,RSA-OAEP,RSA-OAEP-256,A128KW,A192KW,A256KW,\
    A128GCMKW,A192GCMKW,A256GCMKW,ECDH-ES,ECDH-ES+A128KW,ECDH-ES+A192KW,ECDH-ES+A256KW
# cas.authn.oidc.user-info-encryption-encoding-values-supported=A128CBC-HS256,A192CBC-HS384,A256CBC-HS512,A128GCM,A192GCM,A256GCM
```




### 开放式连接 JWKS



```properties
#cas. 奥森. oidc. jwks. jwks. jwks - 缓存在几分钟内 ] 60
# 卡斯. 奥森. oidc. jwks. jwks. jwks 键大小 = 2048
# 卡斯. 奥特恩. oidc. jwks. jwks 类型 = Rsa |电子商务
```




#### 基于文件的 JWKS

将开放ID连接的JSON网络密钥集管理为静态文件资源。



```properties 
# 卡斯. 奥森. oidc. jwks. jwks 文件 ： 文件： 等 / 卡斯 / 配置 / 钥匙店. jwks
```




#### 基于休息的 JWKS

联系外部 REST API 以请求 JSON 网络密钥集。 预期响应代码 `200` 响应机构应包含 JSON Web 密钥集的内容。

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.oidc.jwks.rest`。



### 开放ID连接范围 & 索赔



```properties
# 定义自定义范围和索赔
# cas. authn. oidc. 用户定义范围. 范围 1] cn， 给定名， 照片， 自定义归因
# cas. authn. oidc. 用户定义范围. 范围 2] cn， 给定名， 照片， 自定义属性 2

# 地图固定索赔的 Cas 属性
# cas. authn. oidc. 索赔 - map.given_name - 自定义名称
# cas. authn. oidc. 索赔 - map.preferred_username = 全球用户属性
```




### 打开ID连接网络手指

WebFinger 是互联网工程工作队 IETF 指定的协议，允许 发现有关 URI 识别的人和事物的信息。[1] 有关 的人的信息可能会通过"acct"URI（例如，这是一个看起来像电子邮件地址的 URI）来发现。



#### 网络手指用户信息通过格罗夫



```properties
# 卡斯. 奥森. oidc. 韦芬格. 用户信息. 格劳维. 位置= 类路径： 韦伯. 格劳维
```




#### 网络手指用户信息通过休息

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.oidc.webfinger.用户信息.rest`。



### 打开ID连接注销

支持的注销通道可以通过以下属性进行定义：



```properties
#cas. 奥森. oidc. logout. 后通道 - 注销支持] 真正的
# 卡斯. 奥森. oidc. logout. 前通道 - 注销支持] 真实
```




## Pac4j 委托奥思恩

充当代理，并将身份验证委托给外部身份提供商。 要了解有关此主题的更多内容，请 [](../integration/Delegate-Authentication.html)查看本指南。



```properties
#cas. authn. pac4j. 键入 - id 使用 ] 假
# 卡斯. 奥森. pac4j. 校长属性 - id ]
] cas.authn.pac4j.name ]
[ 卡斯. 奥森. pac4j. 秩序]
[ 卡斯. 奥森. pac4j. 懒惰 - 伊特] 真正的
[ 卡斯. 奥森. pac4j. 复制会话] 真实
```




### 基于休息的配置

授权身份验证的身份提供商可以使用外部 REST 端点提供给 CAS。 

此功能的 RESTful 设置 可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.pac4j.rest`。



### 默认配置

以下外部身份提供商在下面列出的配置密钥下共享</a> 常见设置块：</p> 

| 身份提供商  | 配置密钥                       |
| ------ | -------------------------- |
| 唽      | `卡斯. 奥森. pac4j. 推特`        |
| PayPal | `卡斯. 奥森. pac4j. PayPal`    |
| 文字新闻   | `卡斯. 奥森. pac4j. 文字出版社`     |
| 雅虎     | `卡斯. 奥森. pac4j. 雅虎`        |
| 奥西德    | `卡斯. 奥森. pac4j. 奥西德`       |
| 滴盒     | `卡斯. 奥森. pac4j. 下拉框`       |
| 吉图布    | `卡斯. 奥森. pac4j. 吉图布`       |
| 直爽     | `卡斯. 奥森. pac4j. 四方`        |
| 窗户生活   | `卡斯. 奥森. pac4j. 窗户 - 生活`   |
| 谷歌     | `卡斯. 奥森. pac4j. 谷歌`        |
| 高服务器   | `卡斯. 奥森. pac4j. 希 - 组织服务器` |


有关 CAS、SAML2 等其他身份提供商，请参阅下文。



### 供应

向身份商店提供并创建已建立的用户配置文件。



#### 槽的



```properties
#卡斯. 奥森. pac4j. 供应. 格罗维. 位置： 文件： 等 / 卡斯 / 配置 / 提供者. 格罗维
```




#### 休息

此功能的 RESTful 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.authn.pac4j.提供.rest`。



### 吉图布

除了</a>设置的 常见块外，在将身份验证委托给 GitHub 时，还支持以下 属性：</p> 



```properties
#cas.奥森.pac4j.吉图布.范围=用户|阅读：用户|用户：电子邮件|...
```


默认范围 `用户`，即 `读/写` 访问 GitHub 用户帐户。

有关可能范围的完整列表，请 [](https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/)查看此链接。 



### 谷歌

除了</a> 设置的 常见块外，在将身份验证委托给 Google 时，还支持以下属性：</p> 



```properties
#卡斯. 奥森. pac4j. 谷歌. 范围 = 电子邮件|配置文件|EMAIL_AND_PROFILE
```




### 中国科学院

将身份验证委托给外部 CAS 服务器。



```properties
#卡斯. 奥森. pac4j. cas[0]. 登录 url]
# 卡斯. 奥森. pac4j. 卡斯[0]. 协议]
```




### 非航空20

将身份验证委托给通用的OAuth2服务器。 此 身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-settings) 配置密钥下 `cas.authn.pac4j.oauth2[0]`。



```properties
#cas. authn. pac4j. auauth2[0]. auth - url]
# 卡斯. 奥森. pac4j. auauth2[0]. 令牌 - 乌尔 ]
# 卡斯. 奥森. pac4j. auauth2[0]. 配置文件 - url]
. 卡斯. 奥森. pac4j. oauth2[0]. 配置文件路径]
# 卡斯. 奥森. pac4j. oauth2[0]. 范围 ]
] 卡斯. 奥森. pac4j. oauth2[0]. 配置文件 - 动词] get|开机自检
# 卡斯. 奥森. pac4j.[0]. 响应类型 = 代码
# 卡斯. 奥森. pac4j. oauth2[0]. 配置文件 -attrs. attr1] 路径到简介
# 卡斯. 奥森. pac4j.[0]. 自定义参数. param1 ] 价值 1
```




### 打开ID连接

将身份验证委托给外部开放ID连接服务器。

此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-settings) 配置密钥下 `cas.authn.pac4j.oidc[0]`。



#### 谷歌

此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 配置密钥下 `cas.authn.pac4j.oidc[0].google`。



#### 蔚蓝广告

此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 配置密钥 `cas.authn.pac4j.oidc[0].azure`下提供。

以下设置特别适用于此提供商：



```properties
#卡斯. 奥森. pac4j. oidc[0]. azure. 租户 + 租户名称
```




#### 钥匙洛克

此身份提供商的常见设置 可 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 配置密钥下 `cas.authn.pac4j.oidc[0].keycloak`。



```properties
#卡斯. 奥森. pac4j. oidc[0]. keycloak. 领域]
# 卡斯. 奥森. pac4j. oidc[0]. keycloak. 基地 - 乌里
```




#### 苹果签名

此身份提供商的常见设置 可 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 配置密钥 `cas.authn.pac4j oidc[0].apple`下提供。



```properties
#卡斯. 奥森. pac4j. oidc[0]. 苹果. 私人钥匙]
# 卡斯. 奥森. pac4j. oidc[0]. 苹果. 私人钥匙 - id ]
#卡斯. 奥森. pac4j. oidc[0]. 苹果. 团队 id]
# 卡斯. 奥森. pac4j.[0]. 苹果. 超时 = Pt30s
```




#### 通用

此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-openid-connect-settings) 配置密钥 `cas.authn.pac4j.oidc[0].通用`下提供。



### 萨姆尔2

将身份验证委托给外部 SAML2 IDP。



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
# cas.authn.pac4j.saml[0].全签名验证禁用=假
#cas.authn.pac4j.saml[0].符号服务提供商-元数据=假
# cas.authn.pac4[0]. 校长 - id - 属性 ] 爱德华森原则名称
# 卡斯. authn. pac4j. saml[0]. 使用名称 - 限定符= 真实
# 卡斯. authn. pac4j. saml[0]. 属性消耗 - 服务指数]
# 卡斯. 奥森. pac4j. saml[0]. 断言 - 消费者 - 服务指数 ] - 1

[0][0]|.错误|UNDEFINED


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


访问 CAS 登录屏幕后检查生成的元数据，以确保正确调整所有 端口和端点。 最后，与委托的 IdP 共享 CAS SP 元数据，并将 CAS 注册为授权依赖方。



#### SAML2 身份提供商发现



```properties
cas.authn.pac4j.萨姆尔发现.资源[0].位置/文件/等/卡斯/配置/杰森-饲料.json
```




### 脸谱网

将身份验证委托给 Facebook。 此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-settings) 配置密钥下 `cas.authn.pac4j.脸谱`。



```properties
#卡斯. 奥森. pac4j. 脸谱. 菲尔兹]
# 卡斯. 奥森. pac4j. 脸谱. 范围
```




### 高奥格服务器

将身份验证委托给高尔格服务器。 此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-settings) 配置密钥 `cas.authn.pac4j.hi-org 服务器`下提供。



```properties
#卡斯. 奥森. pac4j. hi - org 服务器. 范围 = 艾格内达滕
```




### LinkedIn

将身份验证委托给LinkedIn。 此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-settings) 配置密钥 `cas.authn.pac4j.LinkedIn`下提供。



```properties
#卡斯. 奥森. pac4j. 链接 - 范围]
```




### 唽

将身份验证委托给推特。  此身份提供商的常见设置可 [此处](Configuration-Properties-Common.html#delegated-authentication-settings) 配置密钥下 `cas.authn.pac4j.twitter`。



```properties
#卡斯. 奥森. pac4j. 推特. 包括电子邮件 = 假
```




## WS 联合会

允许 CAS 充当身份提供商和安全令牌服务 以支持 WS-联邦协议。

要了解有关此主题的更多内容，请 [请查看本指南](../protocol/WS-Federation-Protocol.html)



```properties
# cas.authn.wsfed-idp.idp.realm=urn:org:apereo:cas:ws:idp:realm-CAS
# cas.authn.wsfed-idp.idp.realm-name=CAS

# cas.authn.wsfed-idp.sts.signing-keystore-file=/etc/cas/config/ststrust.jks
# cas.authn.wsfed-idp.sts.signing-keystore-password=storepass
# cas.authn.wsfed-idp.sts.encryption-keystore-file=/etc/cas/config/stsencrypt.jks
# cas.authn.wsfed-idp.sts.encryption-keystore-password=storepass

# cas.authn.wsfed-idp.sts.subject-name-id-format=unspecified
# cas.authn.wsfed-idp.sts.subject-name-qualifier=http://cxf.apache.org/sts
# cas.authn.wsfed-idp.sts.encrypt-tokens=true
# cas.authn.wsfed-idp.sts.sign-tokens=true

# cas.authn.wsfed-idp.sts.conditions-accept-client-lifetime=true
# cas.authn.wsfed-idp.sts.conditions-fail-lifetime-exceedance=false
# cas.authn.wsfed-idp.sts.conditions-future-time-to-live=PT60S
# cas.authn.wsfed-idp.sts.conditions-lifetime=PT30M
# cas.authn.wsfed-idp.sts.conditions-max-lifetime=PT12H

# cas.authn.wsfed-idp.sts.realm.keystore-file=/etc/cas/config/stscasrealm.jks
# cas.authn.wsfed-idp.sts.realm.keystore-password=storepass
# cas.authn.wsfed-idp.sts.realm.keystore-alias=realmcas
# cas.authn.wsfed-idp.sts.realm.key-password=cas
# cas.authn.wsfed-idp.sts.realm.issuer=CAS
```




### 签署 & 加密

[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。  这些发挥作用是为了确保 IDP 和 STS 之间的身份验证请求。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.wsfed-idp.sts`下，可此处签名 & 加密设置。



## 非统组织2

允许 CAS 充当 OAuth2 提供商。 在这里，您可以控制 CAS 发行的各种代币应持续多长时间等。

[此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.oauth`下，可 & 此功能的加密设置进行签名。

要了解有关此主题的更多内容，请 [](../installation/OAuth-OpenId-Authentication.html)查看本指南。



```properties
#cas. authn. oauth. 复制会话 = 假 
# 卡斯. 奥森. 授权. 资源所有者. 要求服务标题] 真实
# 卡斯. 奥森. oauth. 用户配置文件视图类型 ] NESTED|平
```




### 刷新令牌



```properties
#卡斯. 奥森. 奥纳特. 刷新令牌. 秒内杀人时间] 2592000
```




### 代码



```properties
#卡斯. 奥森. oauth. 代码. 秒内杀人时间 ] 30
# 卡斯. 奥森. 奥特. 代码. 使用次数 = 1
```




### 访问令牌



```properties
#cas. authn. oauth. 访问令牌. 秒内杀人时间 ] 7200
# 卡斯. 奥森. oauth. 访问令牌.max秒内生活时间 = 28800
```




### 设备代币



```
#cas. authn. oauth. 设备代币. 秒内杀人时间 = 2592000
# 卡斯. 奥森. oauth. 设备代币. 刷新间隔 = Pt15s 
```




### 设备用户代码



```
#cas.authn.oauth.设备-用户代码.秒内杀戮时间=2592000
# cas.authn.oauth.设备-用户代码.用户代码长度=8
```




### 非授权2 JWT访问令牌



```properties
#cas. authn. oauth. 访问令牌. 创建为 jwt= 假
# 卡斯. authn. aauth. 访问令牌. 加密. 加密启用= 真实
# cas. authn. oauth. 访问 - 令牌. 加密。
```


签名密钥和加密密钥 [都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.oauth.访问令牌`下，可在此处签名 & 加密设置。



### 非统组织2 UMA

要了解有关此主题的更多内容，请 [](../installation/OAuth-OpenId-Authentication.html)查看本指南。



```properties
#cas.authn.uma.发行人=本地主机：8080/cas

# cas.authn.uma.请求方代币，.max秒内生活时间=PT3M
# cas.a uthn. uma. 请求党 - 令牌. jwks - 文件 / 等 / 卡斯 / 康比格 / 乌马钥匙店. jwks

# 卡斯. authn. uma. 许可票.max 时间到住秒 [ Pt3m
```




#### OAuth2 乌马 Jpa

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.uma.资源集.jpa`。



## 地方化

要了解有关此主题的更多内容，请 [](../ux/User-Interface-Customization-Localization.html)查看本指南。



```properties
#cas.地区名称=
#cas.本地.默认值
```


如果用户更改了语言，CAS 将创建一个特殊的 Cookie 来包含所选语言。 此功能的曲奇 设置可 [此处](Configuration-Properties-Common.html#cookie-properties) 配置密钥 `cas.locale.cookie`下提供。



## 全球 SSO 行为

要了解有关此主题的更多内容，请 [](../installation/Configuring-SSO.html)查看本指南。



```properties
#cas.sso.允许-缺失-服务-参数]真正的
#cas.s.创建-苏-曲奇-续订-奥森]真正的
=cas.s.代理-奥森 启用=真正的
#cas.sso.更新-奥森启用]真正的
#cas.sso.启用=真正的
#cas.sso.所需的服务模式]
```




## 警告曲奇

如果用户在访问 CAS 保护服务时受到警告，则由 CAS 创建。 此功能的曲奇设置可 [此处](Configuration-Properties-Common.html#cookie-properties) 配置密钥 `cas.警告-cookie`下提供。



```properties
#cas. 警告库克. 自动配置 - 饼干路径] 真实
```




## 门票赠与曲奇

此功能的曲奇设置可 [此处](Configuration-Properties-Common.html#cookie-properties) 配置密钥 `cas.tgc`下提供。



```properties
#cas. tgc. 针到会话] 真正的
# cas. tgc. 记住我最大年龄 = P14d
# cas. tgc. 自动配置 - 曲奇路径] 真实
```




### 签署 & 加密

[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 [此处可在配置密钥 `cas.tgc`下](Configuration-Properties-Common.html#signing--encryption) 签署此功能的 & 加密设置。



## 注销

控制与 CAS 注销功能相关的各种设置。 要了解有关此主题的更多内容，请 [](../installation/Logout-Single-Signout.html)查看本指南。



```properties
#cas.logout. 后续服务重定向= 假
# cas.logout. 重定向参数
服务 # cas. logout. 重定向 url
.com
=
```




## 单一注销

要了解有关此主题的更多内容，请 [](../installation/Logout-Single-Signout.html)查看本指南。



```properties
#卡斯. slo. 禁用 = 假
# 卡斯. slo. 异步= 真实
```




## 清除通道

捕获和缓存用户凭据，并可选地将其发布到受信任的应用程序。 要了解有关此主题的更多内容，请 [](../integration/ClearPass.html)查看本指南。

<div class="alert alert-warning"><strong>使用警告！</strong><p>默认情况下，清除通道已关闭。
在打开此功能之前，请仔细考虑 <strong>非常</strong> ，因为它 <strong>必须</strong>
集成工作的最后手段。。。也许甚至不是那样</p></div>

```properties
# 卡斯. 清除通行证. 缓存凭据 = 错误
```


[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 此功能的签名 & 加密设置可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.clearpass`下提供。



## 消息捆绑包

要了解有关此主题的更多内容，请 [](../ux/User-Interface-Customization-Localization.html)查看本指南。 基本名称是代表文件的消息捆绑包基名，这些文件以。属性或_xx属性结尾，其中 xx 是国家/地区代码。 通用名实际上不是消息捆绑包，而是合并在一起的属性文件，并且包含仅在消息捆绑包中找不到时才使用的密钥。 与之前文件中的密钥优先于之前文件中的密钥，将优先于列表中较后文件中的密钥。



```properties
#cas.消息捆绑.编码]UTF-8
#cas.消息捆绑包.回退系统-本地=假
#cas.消息捆绑.缓存秒=180
# cas.消息捆绑.使用代码- 消息=真实
#cas.消息捆绑.基地名称=类路径：custom_messages，类路径：消息
# cas.消息捆绑.通用名称=类路径：common_messages.属性、文件等/cas/配置/common_messages. 属性
```




## 审计

控制审核消息的格式。 要了解有关此主题的更多内容，请 [](../installation/Audits.html)查看本指南。



```properties 
#cas.audit.启用]真实
#cas.audit.忽略-审计-失败=虚假
#cas.audit.app 代码=CAS
#cas.audit.
30天]cas.audit.包括验证-确认=虚假
#cas.audit.交替 - 服务器 - 加载器 - 加载器 - 标题名称]
# cas. audit. 交替客户端 - 标题名称 ] X 转发 -
# cas. audit. 使用 - 服务器 - 主机 - 地址 [ 假  

# cas. audit. 支持行动]
# cas. audit. 排除行动]
```




### 斯尔夫 4j 审计

将审核日志路由到 Slf4j 记录系统，该系统可能反过来将审核日志存储在文件或记录系统支持的任何其他 目标中。

记录器名称固定在 `组织.apereo.因斯佩克特.审计.支持`。



```xml
<Logger name="org.apereo.inspektr.audit.support" level="info"><!--将审计数据路由到由记录框架支持的任何数量的应用者。 --></Logger>
```

<div class="alert alert-info"><strong></strong><p>由于 CAS、
记录系统和可能将数据推入各种系统的任何数量的日志应用器之间的抽象层，路由到 Slf4j 日志的审计记录无法
能够读取审计数据。</p></div>

```properties
#卡斯. 审计. slf4j. 审计格式 = 默认|杰森 ·
# 卡斯. 审计. slf4j. 单线分离器 ] |
# 卡斯. audit. slf4j. 使用单行 = 假
# 卡斯. 审计. slf4j. 启用] 真实
```




### 蒙古银行审计

将审核日志存储在蒙古数据库中。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.audit`下提供。



```properties
#卡斯. 审计. 蒙戈. 异步] 真实
```




### 雷迪斯审计

将审核日志存储在雷迪斯数据库中。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥 `cas.audit`下提供。



```properties
#卡斯. 审计. 雷迪斯. 异步] 真实
```




### 库奇德布审计

将审核日志存储在CouchDb数据库中。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.audit`。



### 沙发基地审核

将审核日志存储在沙发库数据库中。

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchbase-integration-settings) 配置密钥下 `cas.audit.沙发基地`。



```properties
#卡斯. 审计. 沙发基地. 异步] 真实
```




### 发电机数据库审计

将审核日志存储在 DynamoDb 数据库中。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥下 `cas.audit`。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.audit.dynamo-db`。



```properties
#卡斯. 审计. 发电机 - db. 异步] 真实
```




### 数据库审核

将审核日志存储在数据库中。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.audit.jdbc`。



```properties
#cas. audit. jdbc. 异步] 真实
# cas. audit. jdbc .max岁日 = 180
# 卡斯. audit. jdbc. 列长度 = 100
# cas. audit. jdbc. 选择 sql 查询模板 ]
# 卡斯. audit. jdbc. 日期格式模式]
```


此功能的调度器设置可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥下 `cas.audit.jdbc`。



### 休息审计

将审核日志存储在数据库中。 此功能的 RESTFUL 设置 可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.audit.rest`。



```properties
#卡斯. 审计. 休息. 异步] 真实
```




## 侦探分布式追踪

要了解有关此主题的更多内容，请 [](../monitoring/Monitoring-Statistics.html#distributed-tracing)查看本指南。



```properties
#春天. sleuth. 采样器. 百分比 = 0.5
[ 春天. sluth. 启用] 真正的

] 春天. zipkin. 启用] 真正的
[ 春天. zipkin. 基地 - url] / 本地主机： 9411 /
```




## 监测

要了解有关此主题的更多内容，请 [](../monitoring/Monitoring-Statistics.html)查看本指南。



### 门票赠与门票

决定中科院应该如何监控 TGT 的生成。



```properties
#cas. 监视器. tgt. 警告. 阈值 = 10
# 卡斯. 监视器. tgt. 警告. 驱逐阈值 = 0
```




### 服务门票

决定 CAS 应如何监控 ST 的生成。



```properties
#卡斯. 监视器. st. 警告. 阈值 = 10
# 卡斯. 监视器. st. 警告. 驱逐保持 = 0
```



### 负荷

决定 CAS 应如何监控 CAS 服务器的系统负载。  



```properties
#卡斯. 监视器. 负载. 警告. 阈值 = 25
```




### 缓存监视器

决定中科院应如何监控各种缓存存储服务的内部状态。



```properties
#卡斯. 监视器. 警告. 阈值 = 10
# 卡斯. 监视器. 警告. 驱逐阈值 = 0
```




### 梅卡奇监视器

决定 CAS 应如何监控 memcach 连接池的内部状态。 此注册表的集成设置可 [此处](Configuration-Properties-Common.html#memcached-integration-settings) 配置密钥 `cas.Monitor.`下提供。



### 蒙古数据库监视器

决定 CAS 应如何监控 MongoDb 实例的内部状态。  
此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.监视器`下提供。



### 数据库监控

决定 CAS 应如何监控用于身份验证或属性检索 的 JDBC 连接的内部状态。 此功能的数据库设置 可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥 `cas.monitor.jdbc`下提供。



```properties
#cas. 监视器. jdbc. 验证查询 = 选择 1
# cas. 监视器. jdbc .max - 等待 = 5000
```




### LDAP 服务器监控

决定CAS应该如何监控它用于身份验证的LDAP服务器等，  
LDAP设置 可用 [在这里](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.monitor.ldap[0]`。 池大小的默认值为零，以防止失败的ldap池初始化以影响服务器启动。

以下属性是特定于ldap监视器和配置线程池 ，将在LDAP监视器连接池上ping。



```properties
#cas.monitor.ldap[0].max-wait=5000
# cas.监视器.[0].池.min 大小=0
# cas.监视器.[0].池.max大小=18
# cas.监视器.[0]..pool.启用]真实
```




### 记忆

决定 CAS 应如何监控运行时可用的 JVM 内存的内部状态。



```properties
#cas.监视器.自由模值阈值=10
```




## 主题

要了解有关此主题的更多内容，请 [](../ux/User-Interface-Customization-Themes.html)查看本指南。



```properties
#cas. 主题. 参数名称 ] 主题
# 卡斯. 主题. 默认主题名称 - 卡 - 主题 - 默认
```




## 事件

决定 CAS 应如何跟踪身份验证事件。 要了解有关此主题的更多内容，请 [](../installation/Configuring-Authentication-Events.html)查看本指南。



```properties
cas.事件启用=真实

#是否应打开并请求浏览器进行地理位置跟踪。
#cas.事件.跟踪地理位置=虚假

#控制CAS是否应该监控配置文件和自动刷新上下文。
#cas.事件.跟踪配置修改]真实
```




### 影响数据库事件

决定 CAS 应如何将身份验证事件存储在"影响数据库"实例中。 此功能的常见 配置设置可 [此处](Configuration-Properties-Common.html#influxdb-configuration) 配置密钥下 `cas.events`.



### 沙发节活动

决定 CAS 应如何将身份验证事件存储在 CouchDb 实例中。 此功能的常见 配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.events.沙发-db`。



### 数据库事件

决定 CAS 应如何在数据库实例中存储身份验证事件。 此功能的数据库 设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥 `cas.events.`下提供。



### 蒙哥德布活动

决定 CAS 应如何将身份验证事件存储在 MongoDb 实例中。 此功能的常见 配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.事件`下提供。



### 迪纳莫德布事件

决定 CAS 应如何将身份验证事件存储在 DynamoDb 实例中。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥 `cas.事件`下提供。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.evnts.dynamo-db`。



```properties
#卡斯. 事件. 迪纳莫 - 德布. 表名称 = 迪纳莫德布卡斯事件
```




## 赫特普网络请求

控制 CAS 应如何响应和验证传入的 HTTP 请求。



```properties
#cas. http - web 请求. 标题. 启用] 真实

# cas. http - web 请求. header. x 帧] 真实
# cas. http - web 请求. head。 x 帧选项 = 拒绝

# cas. http - web 请求. header. xs= 真实
# cas. http - web 请求. header. xs - 选项 = 1;mode=block

# cas.http-web-request.header.hsts=true
# cas.http-web-request.header.xcontent=true
# cas.http-web-request.header.cache=true
# cas.http-web-request.header.content-security-policy=

# cas.http-web-request.cors.enabled=false
# cas.http-web-request.cors.allow-credentials=false
# cas.http-web-request.cors.allow-origins[0]=
# cas.http-web-request.cors.allow-methods[0]=*
# cas.http-web-request.cors.allow-headers[0]=*
# cas.http-web-request.cors.max-age=3600
# cas.http-web-request.cors.exposed-headers[0]=

# cas.http-web-request.web.force-encoding=true
# cas.http-web-request.web.encoding=UTF-8

# cas.http-web-request.allow-multi-value-parameters=false
# cas.http-web-request.only-post-params=username,password
# cas.http-web-request.params-to-check=ticket,service,renew,gateway,warn,method,target,SAMLart,pgtUrl,pgt,pgtId,pgtIou,targetService,entityId,token
# cas.http-web-request.pattern-to-block=
# cas.http-web-request.characters-to-forbid=none

# cas.http-web-request.custom-headers.header-name1=headerValue1
# cas.http-web-request.custom-headers.header-name2=headerValue2

# server.servlet.encoding.charset=UTF-8
# server.servlet.encoding.enabled=true
# server.servlet.encoding.force=true
```




## 赫特普客户端

控制 CAS 应如何尝试通过自己的 Http 客户端联系网络上的资源 。 这是最常用的，当响应 票证验证事件和/或单一的注销。

如果将本地证书导入 CAS 运行环境，则由 CAS 提供 本地信托商店，以提高跨环境配置的便携性。



```properties
#cas.http-客户端.连接超时=5000
# cas.http-客户端.不同步超时=5000
# cas.http-客户端. 读取超时=50 00 

[ cas. http - 客户端. 代理主机]
# 卡斯. http - 客户端口 = 0 

# cas. http - 客户端. 主机名称验证器] 无|默认
# cas. http - 客户端. 允许本地 - 注销 - 乌尔斯 ] 假
# cas. http - 客户端. 权威验证 - 注册 -
# cas. http - 客户端. 权威 - 验证 - 注册 - 前案例敏感] 真正的
# cas 。 http - 客户端. 默认标题]

# cas. http - 客户. 信托商店. psw] 更改它
# cas. http - 客户端. 信任商店. 文件 [ 类路径： 信托商店. jks
# cas. http - 客户端. 信任商店. 类型]
```




### 主机名验证

默认选项可用于主机名验证：

| 类型   | 描述       |
| ---- | -------- |
| `没有` | 忽略主机名验证。 |
| `违约` | 执行主机名验证。 |




## 服务注册处

请参阅本指南 [](../services/Service-Management.html) 了解更多。



```properties
#cas.服务注册表启用]真实

# 从默认的 JSON 服务定义自动初始化注册表
# cas.服务注册表|

.域
# cas. 服务注册. 缓存 = Pt5m
# cas. 服务注册表. 缓存大小 = 10
# cas. 服务注册. 缓存容量 = 1000
```


此功能的调度器设置可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥 `cas.服务注册`下提供。



### 服务注册表通知

此功能的电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications) 配置密钥下 `cas.服务注册`。 此功能的 SMS 通知设置 可用 [此处](Configuration-Properties-Common.html#sms-notifications) 配置密钥 `cas.服务注册`下。



### 杰森服务注册处

如果基础服务注册表使用本地系统资源 定位 JSON 服务定义，请决定如何找到这些资源。



```properties
•卡斯.服务注册.json.位置=类路径：服务
```


要了解有关此主题的更多内容，请 [](../services/JSON-Service-Management.html)查看本指南。



### 雅姆服务注册处

如果基础服务注册表使用本地系统资源 定位 YAML 服务定义，请决定如何查找这些资源。



```properties
• 卡斯.服务注册.yaml.位置=类路径：服务
```


要了解有关此主题的更多内容，请 [](../services/YAML-Service-Management.html)查看本指南。



### 吉特服务注册处

与 git 存储库配合使用，以获取和管理服务注册表定义。

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#git-configuration) 配置密钥 `cas.服务注册`下提供。



```properties
#cas.服务注册.git.按类型分组]真正的
#卡斯.服务注册.git.根目录]
```


要了解有关此主题的更多内容，请 [](../services/Git-Service-Management.html)查看本指南。



### 服务注册处

要了解有关此主题的更多内容，请 [](../services/REST-Service-Management.html)查看本指南。



```properties
#cas.服务-注册.rest.url=
#卡斯.服务-注册.rest.基本-身份验证-用户名]
#cas.服务-注册.rest.基本-身份验证-密码]
```




### 库奇德布服务注册处

要了解有关此主题的更多内容，请 [](../services/CouchDb-Service-Management.html)查看本指南。 此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥 `cas.服务注册`下提供。



### 重新分配服务注册表

要了解有关此主题的更多内容，请 [](../services/Redis-Service-Management.html)查看本指南。 此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥 `cas.服务注册`下提供。



### 宇宙数据库服务注册处

要了解有关此主题的更多内容，请 [](../services/CosmosDb-Service-Management.html)查看本指南。



```properties
#卡斯. 服务注册. 宇宙 - db. uri]
# 卡斯. 服务注册. 宇宙 - db .key ]
[ 卡斯. 服务注册. 宇宙 - db. 数据库]
# 卡斯. 服务注册. 宇宙 - db. 收集]
# cas. 服务注册. 宇宙 - db. 吞吐量 = 10000
# 卡斯. 服务 - 注册. 宇宙 - db. 滴收集] 真实
# 卡斯. 服务 - 注册. 宇宙 - db. 一致性级别 [ 会话
```




### 亚马逊 S3 服务注册表

要了解有关此主题的更多内容，请 [](../services/AmazonS3-Service-Management.html)查看本指南。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥 `cas.服务注册.amazon-s3`下提供。



### 迪纳莫德布服务注册处

要了解有关此主题的更多内容，请 [](../services/DynamoDb-Service-Management.html)查看本指南。 此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥 `cas.服务注册`下提供。 此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥下 `cas.服务注册.dynamo-db`。



```properties
#cas. 服务注册. 迪纳莫 - db. 表名称 = 迪纳莫德布斯服务
```




### 卡桑德拉服务注册处

要了解有关此主题的更多内容，请 [](../services/Cassandra-Service-Management.html)查看本指南。

此功能的常见卡桑德拉设置可 [此处](Configuration-Properties-Common.html#cassandra-configuration) 配置密钥下 `cas.服务注册.卡桑德拉`。



### 蒙哥德布服务注册处

将 CAS 服务定义存储在 MongoDb 实例中。 要了解有关此主题的更多内容，请 [](../services/MongoDb-Service-Management.html)查看本指南。 此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.服务注册`下提供。



### LDAP 服务注册表

控制如何在LDAP实例中找到CAS服务。 要了解有关此主题的更多内容，请 [](../services/LDAP-Service-Management.html)查看本指南。  此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥 `cas.服务注册`。



```properties
#cas.服务-注册.ldap.服务定义-属性=描述
#cas.服务-registry.ldap.id 属性=uid
#cas.服务-注册.ldap.对象类=卡注册服务
# cas. 服务注册. ldap. 搜索过滤器] （%s={0}）
# cas. 服务注册. ldap. 负载过滤器] （对象类 =%s）
```




### 沙发基地服务注册处

控制如何在沙发基地实例中找到 CAS 服务。 要了解有关此主题的更多内容，请 [](../services/Couchbase-Service-Management.html)查看本指南。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchbase-integration-settings) 配置密钥下 `cas.服务注册.沙发基地`。



### 数据库服务注册表

控制如何在数据库实例中找到 CAS 服务。 要了解有关此主题的更多内容，请 [](../services/JPA-Service-Management.html)查看本指南。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.服务注册.jpa`。



### 缓存服务注册表

服务缓存持续时间指定了创建或更新后自动从缓存中删除条目的固定持续时间。



### 缓存大小服务注册表

服务缓存大小指定缓存可能包含的最大条目数。



### 卡奇容量服务注册表

服务缓存容量为内部数据结构设定了最小总大小。



## 服务注册表复制

控制如何在 CAS 群集中复制 CAS 服务定义文件。 要了解有关此主题的更多内容，请 [请查看本指南](../services/Configuring-Service-Replication.html)

可根据以下选项配置复制模式：

| 类型               | 描述                         |
| ---------------- | -------------------------- |
| `ACTIVE_ACTIVE`  | 所有 CAS 节点都会同步定义副本并将其保留在本地。 |
| `ACTIVE_PASSIVE` | 违约。 一个主节点保持定义和流更改到其他被动节点。  |




```properties
#cas.服务注册表.流。启用]真实
#cas.服务注册表.流.复制模式=ACTIVE_ACTIVE|ACTIVE_PASSIVE
```




## 服务注册表复制黑兹尔卡斯特

控制如何在由分布式黑兹尔卡斯特缓存支持的 CAS 群集中复制 CAS 服务定义文件。 要了解有关此主题的更多内容，请 [](../services/Configuring-Service-Replication.html)查看本指南。

此功能的黑兹尔卡斯特设置可 [此处](Configuration-Properties-Common.html#hazelcast-configuration) 下 配置密钥 `cas.服务注册.流.哈泽尔卡斯特.配置`。



```properties
#卡斯. 服务注册. 流. 哈泽尔卡斯特. 持续时间 = Pt1m
```




## 服务注册复制卡夫卡

控制如何在由阿帕奇·卡夫卡支持的CAS群中复制CAS服务定义文件。 要了解有关此主题的更多内容，请 [](../services/Configuring-Service-Replication.html)查看本指南。

Kafka 此功能的常见设置可 [此处提供，](Configuration-Properties-Common.html#apache-kafka-configuration) 在配置密钥 `cas.服务注册.stream.kafka`下 。 此功能的 Kafka 主题设置 可用， [此处](Configuration-Properties-Common.html#apache-kafka-configuration) 配置密钥 `cas.服务注册.stream.kafka.主题`下。



## 票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Configuring-Ticketing-Components.html)查看本指南。



### 签署 & 加密

加密密钥必须是随机生成的大小字符串 `16`。 签约的关键 [是 `512`大小的JWK](Configuration-Properties-Common.html#signing--encryption) 。



### 清洁工

计划在后台运行一个更清洁的过程，以清理过期和过时的门票。 此部分控制该过程应如何表现。 此功能的调度器设置 可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥下 `cas.票务.注册.清洁`。



### JPA 票务注册处

要了解有关此主题的更多内容，请 [](../ticketing/JPA-Ticket-Registry.html)查看本指南。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.票务.注册.jpa`。



```properties
#cas. 票务. 注册. jpa. 票锁类型 ] 无
# 卡斯. 票务. 注册. jpa. jpa 锁定超时 = 3600
```


</a> 配置密钥 `cas.ticket.`，可在此处 此处签名 & 加密设置。</p> 



### 库奇德布票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/CouchDb-Ticket-Registry.html)查看本指南。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥下 `cas.票务.注册.沙发-db`。



### 沙发基地票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Couchbase-Ticket-Registry.html)查看本指南。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchbase-integration-settings) 配置密钥下 `cas.票务.注册.沙发基地`。

[此处可](Configuration-Properties-Common.html#signing--encryption) 以下配置密钥 `cas.票务.注册处.沙发基地`中签 & 加密设置。



### 黑兹尔卡斯特票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Hazelcast-Ticket-Registry.html)查看本指南。

此功能的常见黑兹尔卡斯特设置可 [此处](Configuration-Properties-Common.html#hazelcast-configuration) 配置密钥下 `cas.票务.注册.哈泽尔卡斯特`。



```properties
• cas.ticket.registry.hazelcast.page 尺寸=500
```


[此处](Configuration-Properties-Common.html#signing--encryption) 以下配置密钥 `cas.ticket.`，可 & 此注册表的加密设置进行签名。



### 卡桑德拉票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Cassandra-Ticket-Registry.html)查看本指南。

此功能的常见卡桑德拉设置可 [此处](Configuration-Properties-Common.html#cassandra-configuration) 配置密钥下 `cas.票务.卡桑德拉.卡桑德拉`。

[此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.ticket. & 注册表.卡桑德拉`下，可在此处签名加密设置。



```properties
#cas. 票务. 注册. 卡桑德拉. 启动时投递表 = 错误
```




### 英菲尼斯潘票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Infinispan-Ticket-Registry.html)查看本指南。



```properties
#cas. 票务. 注册. 英菲尼斯潘. 缓存名称]
# 卡斯. 票务. 注册. 英菲尼斯潘. 配置位置 = / 英菲尼斯潘.xml
```


[此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.ticket.`下，可此处签名 & 加密设置。



### 记忆票务登记处

这通常是默认的票证注册表实例，其中票证 保存在运行时间环境内存中。



```properties
• 启用后台地图可缓存
# cas.ticket.registry.in 内存.缓存] 真实

# cas.ticket.registry.in 内存. 负载因子 =1
= cas.ticket.registry.in 内存. 并发=20
= cas.ticket.registry.in 内存. 初始容量=1000
```


此注册表的登录 & 加密设置可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.ticket.registry.in 内存`下。



### JMS 票务注册处

要了解有关此主题的更多内容，请 [](../ticketing/Messaging-JMS-Ticket-Registry.html)查看本指南。

[此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.票务.`. & 注册加密设置。



```properties
•cas.ticket.registry.jms.id+
```




#### JMS 票务注册表活动MQ



```properties
#春天. activemq. 经纪人 - url_tcp：//192.168.1.210：9876
[ 春天. activemq. 用户= 管理员
# 春天. activemq.密码 = 秘密
# 春天。 activemq. pool. 启用] 真正的
# 弹簧. activemq. 池.max连接 = 50
# 春天. 活动mq. 包. 信任 - 所有 = 假
# 春天. 活动. 包. 信任] org. apereo. cas
```




#### JMS 票务注册处 阿特米斯



```properties
#春天. artemis. mode] 本地
# 春天. artemis. 主机 = 192.168.1.210
# 春天. 阿特米斯. 波特 = 9876
# 春天. artemis. 用户 # 管理员
# 春天.
```




#### JMS 票务注册处 JNDI



```properties
# 春天. jms. jndi 名称 = java： 我的连接工厂
```




### 埃卡奇票务登记处

这些属性用于使用 Ehcache 库版本 2.x 的模块。 要了解有关此主题的更多内容，请 [](../ticketing/Ehcache-Ticket-Registry.html)查看本指南。



```properties
# cas.ticket.registry.ehcache.replicate-updates-via-copy=true
# cas.ticket.registry.ehcache.cache-manager-name=ticketRegistryCacheManager
# cas.ticket.registry.ehcache.replicate-puts=true
# cas.ticket.registry.ehcache.replicate-updates=true
# cas.ticket.registry.ehcache.memory-store-eviction-policy=LRU
# cas.ticket.registry.ehcache.config-location=classpath:/ehcache-replicated.xml
# cas.ticket.registry.ehcache.maximum-batch-size=100
# cas.ticket.registry.ehcache.shared=false
# cas.ticket.registry.ehcache.replication-interval=10000
# cas.ticket.registry.ehcache.cache-time-to-live=2147483647
# cas.ticket.registry.ehcache.disk-expiry-thread-interval-seconds=0
# cas.ticket.registry.ehcache.replicate-removals=true
# cas.ticket.registry.ehcache.max-chunk-size=5000000
# cas.ticket.registry.ehcache.max-elements-on-disk=0
# cas.ticket.registry.ehcache.max-elements-in-cache=0
# cas.ticket.registry.ehcache.max-elements-in-memory=10000
# cas.ticket.registry.ehcache.eternal=false
# cas.ticket.registry.ehcache.loader-async=true
# cas.ticket.registry.ehcache.replicate-puts-via-copy=true
# cas.ticket.registry.ehcache.cache-time-to-idle=0
# cas.ticket.registry.ehcache.persistence=LOCALTEMPSWAP|没有|本地启动|分布式
#cas.票证.注册表.ehcache.同步写入=

系统计划允许在处理配置配置之前将属性地图设置为系统属性。
• 这些属性可通过 ${key}
# cas. 票务. 注册. ehcaache. 系统props. 键 1 = 值 1
# cas. 票务. 注册. ehcache. 系统props. 键 2 = 值 1 中引用
```


[此处可](Configuration-Properties-Common.html#signing--encryption) 以下配置密钥 `cas.票务.注册处.`中签名 & 加密设置。



### 埃卡奇 3 票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Ehcache-Ticket-Registry.html)查看本指南。



```properties
#cas. 票务. 注册. ehcaache3. 启用] 真实
# cas. 票务. 注册. .max元素内存 = 10000
# cas. 票务. 注册. ehcaache3. 每缓存大小在磁盘上 = 20mb
# cas. 票务. 注册. ehcaache3. 永恒 =假
# cas. 票务. 注册. 注册. ehcaache3. 启用统计] 真实
# cas. 票务. 注册. 注册. ehcaache3. 启用管理] 真实
# cas. 票务. 注册. ehcaache3.
# 卡斯. 票务. 注册. ehcaache3. 默认服务器资源 ] 主要
# cas. 票务. 注册. ehcaache3. 资源池名称 ] 卡 - 票池
# cas. 票务. 注册表.
# 卡什. 票务. 注册.
# 卡斯. 票务. 注册。 ehcaache3. 坚持在磁盘上] 真实
# cas. 票证. 注册. ehcaache3. 集群连接超时 = 150
# cas. 票务. 注册.
. 集群读写超时 = 5  # cas. 票务. 注册. ehcaache3. 集群 - 缓存 - 一致性] 强
```


Terracota 集群 URI 没有默认值，但格式 `赤土：/主机1.company.org：9410，host2.company.org：9410/cas 应用`

</code>，在</a> 配置密钥 `cas.ticket.下，可 <a href="Configuration-Properties-Common.html#signing--encryption">此处签署此注册表的 & 加密设置。</p>

<h3 spaces-before="0">点火票务登记处</h3>

<p spaces-before="0">要了解有关此主题的更多内容，请 <a href="../ticketing/Ignite-Ticket-Registry.html"></a>查看本指南。</p>

<pre><code class="properties"># cas.ticket.registry.ignite.key-algorithm=
# cas.ticket.registry.ignite.protocol=
# cas.ticket.registry.ignite.trust-store-password=
# cas.ticket.registry.ignite.key-store-type=
# cas.ticket.registry.ignite.key-store-file-path=
# cas.ticket.registry.ignite.key-store-password=
# cas.ticket.registry.ignite.trust-store-type=
# cas.ticket.registry.ignite.ignite-address[0]=localhost:47500
# cas.ticket.registry.ignite.ignite-address[1]=
# cas.ticket.registry.ignite.trust-store-file-path=
# cas.ticket.registry.ignite.ack-timeout=2000
# cas.ticket.registry.ignite.join-timeout=1000
# cas.ticket.registry.ignite.local-address=
# cas.ticket.registry.ignite.local-port=-1
# cas.ticket.registry.ignite.network-timeout=5000
# cas.ticket.registry.ignite.socket-timeout=5000
# cas.ticket.registry.ignite.thread-priority=10
# cas.ticket.registry.ignite.force-server-mode=false
# cas.ticket.registry.ignite.client-mode=false

# cas.ticket.registry.ignite.tickets-cache.write-synchronization-mode=FULL_SYNC
# cas.ticket.registry.ignite.tickets-cache.atomicity-mode=TRANSACTIONAL
# cas.ticket.registry.ignite.tickets-cache.cache-mode=REPLICATED
`</pre> 

[此处可](Configuration-Properties-Common.html#signing--encryption) 以下配置密钥 `cas.ticket.`. & 注册加密设置。



### 被没收的机票注册处

要了解有关此主题的更多内容，请 [](../ticketing/Memcached-Ticket-Registry.html)查看本指南。此注册表的集成设置可 [此处](Configuration-Properties-Common.html#memcached-integration-settings) 配置密钥 `cas.票务.注册表.memcached`。

[此处](Configuration-Properties-Common.html#signing--encryption) 以下配置密钥 `cas.ticket.`.memcached，可 & 此注册表的加密设置进行签名。



### 迪纳莫德布票务注册处

要了解有关此主题的更多内容，请 [](../ticketing/DynamoDb-Ticket-Registry.html)查看本指南。 

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#dynamodb-configuration) 配置密钥 `cas.ticket.注册`下提供。 

[此处可](Configuration-Properties-Common.html#signing--encryption) 以下配置密钥 `cas.票务.注册表.dynamo-db`下，可在此处签名 & 加密设置。

此功能的 AWS 设置可 [此处](Configuration-Properties-Common.html#amazon-integration-settings) 配置密钥 `cas.票务.注册.dynamo-db`下提供。



```properties
#cas. 票务. 注册. dynamo - db. 服务 - 门票 - 表名称] 服务票务表
# cas. 票务. 注册. dynamo - db. 代理票 - 表名称 - 代理票务表表
# cas. 票务. 注册. dynamo - db. 票务 - 赠票 - 表名称 • 票务票务表
# cas. 票务. 注册. dynamo - db. 代理授予票 - 表名称 ] 代理授权票务表表
# cas. 票务. 注册. dynamo - db. 瞬时会话 - 票务 - 表名称 = 瞬态票证表表
```




### 蒙古银行票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/MongoDb-Ticket-Registry.html)查看本指南。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.ticket. .`，可在此处签名 & 加密设置。  此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.ticket.注册表`下提供。



### 雷迪斯票务登记处

要了解有关此主题的更多内容，请 [](../ticketing/Redis-Ticket-Registry.html)查看本指南。 此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥 `cas.ticket.注册`下提供。 在此注册表的 & 加密设置 可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.ticket.`.



## 协议票务安全

控制 CAS 服务器签发的门票是否应通过签名和加密 与即将离出呼叫的客户端应用程序共享时进行安全保护。 [的 密钥的签名和加密均为 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 256</code>`。
加密算法设置为 <code>AES_128_CBC_HMAC_SHA_256`。 此 功能的签名 & 加密设置可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.ticket`下提供。



## 服务票行为

控制服务票的到期政策，以及适用于 ST 的其他属性。



```properties
# cas. 票.max长度 = 20

# 卡斯. 票. st. 使用次数 = 1
# 卡斯. 票. st. 秒内杀人时间 = 10
```




## 代理授予门票行为



```properties
# 卡斯. 票. pgt .max长度 + 50
```




## 代理票证行为



```properties
#cas. 票. pt. 秒内杀人时间] 10
# 卡斯. 票. pt. 使用次数 = 1
```





## 瞬时会话票证行为



```properties
#卡斯. 票. tst. 秒内杀人时间 = 300
```




## 门票赠与行为



```properties
#cas. 票. tgt. 仅跟踪最新会话 ] 真正的
# 卡斯. 票. tgt .max长度 = 50
```




## TGT 到期政策

票证到期政策在以下条件下激活：

- 如果默认保单的超时值都设置为零或更少，CAS 应确保门票 *永远不会* 被视为过期。
- 禁用策略需要将其所有超时设置设置为等值或小于零值。
- 如未确定票证到期政策，中科院应确保机票 *始终* 视为过期。

<div class="alert alert-info"><strong>保留您需要的！</strong><p>我们鼓励您只保留和维护特定策略所需的 
属性和设置。 这是 <strong>不必要的</strong> ，抓住所有 
字段的副本或保留副本作为参考，而让他们发表评论。 这一战略最终将导致 
糟糕的升级增加了打破变化的机会，并导致混乱的部署。</p></div>

票证到期政策按下列顺序激活：

1. 如果相应地配置默认保单的设置，则票证永远不会过期。
2. 超时
3. 违约
4. 节流超时
5. 硬超时
6. 门票总是会立即过期。



### 违约

提供一个困难的时间以及一个滑动窗口。



```properties
• 设置为负值，永远不会过期的门票
# cas.票务.tgt.max秒内生活时间 = 28800
# cas.票务. tgt. 秒内杀死时间 = 7200
```




### 记住我



```properties
#cas. 票. tgt. 记住我. 启用] 真正的
# 卡斯. 票. tgt. 记住我. 秒内杀人时间 = 28800
```




### 超时

适用于 TGT 的到期策略提供了最近使用的过期策略，类似于 Web 服务器会话超时。



```properties
#cas.票务.tgt.超时.max秒内直播+28800
```




### 节流超时

节流超时策略扩展了超时策略，其概念是限制每 N 秒最多可使用票证的位置。



```properties
#cas. 票. tgt. 节流超时. 时间到基尔秒 = 28800
# cas. 票. tgt. 限制超时. 时间在两次之间
```




### 硬超时

硬超时策略提供了从创建时间衡量的有限票证寿命。



```properties
#卡斯. 票. tgt. 硬超时. 秒内杀人时间 = 28800
```




## 谷歌重新整合

在 CAS 登录页面上显示谷歌的收复小部件。



```properties
#cas.谷歌-回顾.启用]真实
#卡斯.谷歌-回顾.验证-url=：/www.谷歌.com/回顾/api/网站验证
# cas .谷歌 - recaptcha.site 键]
[ 卡斯. 谷歌 - 回顾. 秘密]
[ 卡斯. 谷歌 - 重新捕获"
[ 卡斯. 谷歌 - 雷普查. 位置] 下右
```




## 谷歌分析

要了解有关此主题的更多内容，请 [](../integration/Configuring-Google-Analytics.html)查看本指南。



```properties
#cas.谷歌分析.谷歌分析跟踪ID=
```




### 谷歌分析曲奇

适用于此功能的常见 Cookie 设置 [此处可用，](Configuration-Properties-Common.html#cookie-properties) 配置密钥下的配置密钥 `cas.google 分析.cookie`。



```properties
cas.谷歌分析.曲奇.属性名称=
卡斯.谷歌分析.曲奇.属性价值模式]。
```




## 春季网络流

控制Spring网络流的会话状态应如何由CAS、 和所有其他与网络流相关的设置进行管理。

要了解有关此主题的更多内容，请 [](../webflow/Webflow-Customization.html)查看本指南。



```properties
#cas. webflow. 始终暂停重定向 = 假
# cas. webflow. 刷新] 真实
# cas. webflow. 重定向同状态 = 错误
# cas. webflow. 自动配置] 真实
# cas. web 流. 基础路径
```




### 春季网络流登录装饰



#### 槽的



```properties
# cas. 网络流. 登录装饰. groovy. 位置 = 文件/ 等/ 卡 / 配置 / 登录装饰器. groovy
```




#### 休息

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥 `cas.webflow. 登录装饰. rest`下提供。



### 春季网络流自动配置

控制"春季网络流"上下文如何由 CAS 动态更改和配置的选项。 要了解有关此主题的更多内容，请 [](../webflow/Webflow-Customization-Extensions.html)查看本指南。



```properties
#cas.网络流.自动配置=真实
```




#### 春季网络流沟自动配置

通过自定义 Groovy 脚本控制春季网络流上下文。



```properties
#cas.网络流.格劳维.位置/文件/等/卡/配置/自定义网络流。
```




### 春季网络流会话管理

要了解有关此主题的更多了解， [请参阅本指南](../webflow/Webflow-Customization-Sessions.html)。



```properties
#cas.webflow.会话.锁定超时=30
# cas.webflow.会话.压缩=假
# cas.webflow. 会话.max对话 = 5

# 启用服务器端会话管理
# cas.webflow. 会话。
```


[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。



#### 春季网络流客户端会话

加密密钥必须是随机生成的大小字符串 `16`。 签约的关键 [是 `512`大小的JWK](Configuration-Properties-Common.html#signing--encryption) 。

此功能的签名 & 加密设置可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.webflow`下提供。



#### 春季网络流淡褐色广播服务器侧会话



```properties
# 卡斯. 网络流. 会话. hz 位置 = 类路径： 淡褐色广播.xml
```




#### 春季网络流蒙古数据库服务器侧会话



```properties
#春天. data. 蒙古德布. 主机 \ 蒙古 - 斯尔夫
# 春天. data. 蒙古德布. port = 27018
# 春天. 数据. 蒙古 - 斯尔夫
```




#### 春季网络流重新分配服务器端会话



```properties
#spring.session.store 类型= 重新
# 春天. redis. 主机] 本地主机
# 春天. redis. 密码 = 秘密
# 春天. redis. port = 6379
```




#### 春季网络流 JDBC 服务器端会话



```properties
• spring.session.store 类型= jdbc
• 春季. 会话. jdbc. 初始化 - 模式嵌入式
• 春季. 会话. jdbc. schema] 类路径： 组织 / 弹簧框架/ 会话 / jdbc / 模式 - @@platform@@.sql

[ 春天. 会话. jdbc. 表名称] SPRING_SESSION

[ 春天. 数据来源. url= jdbc： hsqldb： mem： 卡斯会话
] 春天. 数据源. 用户名] sa  # 春天. 数据源. 密码]
```




### 身份验证例外

映射 CAS 网络流中的自定义身份验证异常，并将它们链接到消息捆绑包中定义的自定义消息。

要了解有关此主题的更多内容，请 [](../webflow/Webflow-Customization-Exceptions.html)查看本指南。



```properties
#卡斯.奥森.错误.例外=价值1，价值2,...
```




### 身份验证中断

中断身份验证流程以联系外部服务。 要了解有关此主题的更多内容，请 [](../webflow/Webflow-Customization-Interrupt.html)查看本指南。



#### 身份验证中断 JSON



```properties
# 卡斯. 中断. json. 位置： 文件/ 等 / 卡斯 / 配置 / 中断. json
```




#### 身份验证中断Regex属性



```properties
#cas.中断.属性名称=属性名称模式
# cas.中断.属性值=属性值模式
```




#### 身份验证中断格罗夫



```properties
#cas.中断.格罗维.位置/文件/等/卡/配置/中断。
```




#### 身份验证中断休息

此功能的 RESTFUL 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥 `cas.`下提供。




### 可接受的使用策略

决定 CAS 应如何尝试确定是否接受 AUP。 要了解有关此主题的更多内容，请 [](../webflow/Webflow-Customization-AUP.html)查看本指南。



```properties
#cas.可接受的使用-政策.上属性名称=接受
#cas.可接受的使用政策。aup-政策条款-属性-名称=会员资格
```




#### 违约



```properties
#cas. 可接受的使用 - policy.in 内存. 范围 = 全球|认证
```


支持以下示波器：

| 范围   | 描述                        |
| ---- | ------------------------- |
| `全球` | 将决策存储在全球内存地图中（服务器寿命）。     |
| `认证` | 存储决策，以便用户通过凭据进行身份验证时获得提示。 |




#### 槽的



```properties
# cas. 可接受的使用政策. groovy. 位置= 文件/ 等 / 卡斯 / 配置 / aup. 格劳维
```




#### 休息

此功能的 RESTful 设置可 [此处](Configuration-Properties-Common.html#restful-integrations) 配置密钥下 `cas.可接受的使用策略。rest`。



#### 京城

如果 AUP 通过 JDBC 进行控制，请决定如何在数据库实例中记住选择。 此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.可接受的使用策略.jdbc`。



```properties
#cas. 可接受的使用政策. jdbc. 表名称 = usage_policies_table
# cas. 可接受的使用 - 政策. jdbc. aup 列]
# cas. 可接受的使用政策. jdbc. 校长 - id 列 = 用户名
# cas. 可接受使用 - 政策. jdbc. 校长 - id 属性]
# 卡斯. 可接受使用 - 政策. jdbc .sql更新 ] 更新 %s 设置 %s= 真实的地方 %s=？
```




#### 库奇德布

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 配置密钥 `cas.可接受的使用策略`下提供。 此功能使用 `异步` 设置。



#### 沙发基地

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#couchbase-integration-settings) 可根据 配置密钥 `cas.可接受的使用策略.沙发基地`。



#### 蒙古德布

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 配置密钥 `cas.可接受的使用策略`下提供。



#### 雷迪斯

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 配置密钥下 `cas.可接受的使用策略`。



#### 阿尔达普

如果 AUP 通过 LDAP 进行控制，则决定如何在 LDAP 实例中记住选择。 此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.可接受的使用策略.ldap[0]`。



#### 禁用可接受的使用策略

允许可接受的使用策略网络流被禁用 -需要重新启动。



```properties
cas.可接受的使用策略启用=真实
```




## 休息API

要了解有关此主题的更多内容，请 [](../protocol/REST-Protocol.html)查看本指南。



```properties
#cas. rest. 属性名称]
# cas. rest. 属性价值]
# 卡斯. rest. 头 - 身份=
# 卡斯. rest. 身体 - 身份 =
# cas. rest. tls - 客户端 - 身份验证]
```




## 指标

要了解有关此主题的更多内容，请 [](../monitoring/Monitoring-Statistics.html)查看本指南。



### 地图集

默认情况下，指标将导出到在本地机器上运行的 Atlas。 可使用阿特拉斯服务器的位置：



```properties
#管理.指标.出口.阿特拉斯.乌里普/阿特拉斯.示例.com：7101/api/v1/发布
```




### 数据狗

数据狗注册表会定期将指标推至 `数据多格` 。 要向数据狗导出指标，必须提供您的 API 密钥：



```properties
#管理.指标.出口.数据狗.api键=YOUR_KEY
```


您还可以更改向数据狗发送指标的间隔：



```properties
#管理.指标.出口.数据狗。步骤+30s
```




### 神经节

默认情况下，指标会导出到在本地机器上运行的 Ganglia。 甘利亚服务器主机和要使用的端口可使用：



```properties
#管理. 指标. 出口. 甘利亚. 主机 # 甘利亚. 例如.com
# 管理. 指标. 出口. 甘利亚. 港口 = 9649
```




### 石墨

默认情况下，指标将导出到本地机器上运行的石墨。 石墨服务器主机和要使用的端口可使用：



```properties
#管理.指标.出口.石墨.主机=石墨.com
#管理.指标.出口.石墨.port=9004
```




### 拉布斯德布

默认情况下，指标将导出到在本地机器上运行的"影响"。 使用中的"影响"服务器的位置可以使用：



```properties
#管理.指标.出口.市场.埃里普/埃斯塔普.例如.com：8086
```



### JMX

微米为 JMX 提供了分层映射，主要作为一种廉价且便携式的本地查看指标的方式。



### 新遗物

新的文物登记处定期向新遗物推进指标。 要向新遗物导出指标，必须提供您的 API 密钥和帐户 ID：



```properties
#管理.指标.出口.新文物.api-key=YOUR_KEY
#管理.指标.出口.新遗物.帐户-id=YOUR_ACCOUNT_ID
```


您还可以更改将指标发送到新遗物的间隔：



```properties
#管理.指标.出口.新文物.步骤+30s
```




### 普罗 米修斯

普罗米修斯希望刮或投票个别应用程序实例的指标。 弹簧靴提供执行器端点 可在 `/执行器/普罗米修斯` 呈现普罗米修斯刮与适当的格式。

下面是一个 `scrape_config` 添加到 `普罗米修斯.yml`的例子：



```yaml
scrape_configs：
  - job_name： "春天"
    metrics_path： "/ 执行器 / 普罗米修斯"
    static_configs：
      - 目标： [主机： PORT]]
```




### 信号福克斯

信号Fx注册表会定期将指标推至信号Fx。 要向 SignalFx 导出指标，必须提供您的访问令牌：



```properties
#管理.指标.出口.信号fx.访问令牌=YOUR_ACCESS_TOKEN
```


您还可以更改向 SignalFx 发送指标的间隔：



```properties
#管理.指标.出口.信号fx.步骤+30s
```


微米船与一个简单的，内存后端，自动用作回退，如果没有其他注册表配置。 这允许您查看在指标端点中收集的指标。

内存后端在使用任何其他可用后端时立即禁用本身。 您也可以明确禁用它：



```properties
#管理.指标.出口.简单.启用=错误
```




### 统计

统计D注册表急切地将 UDP 上的指标推给统计代理。 默认情况下， 指标将导出到本地机器上运行的 StatsD 代理。 使用统计代理主机和端口可以使用：



```properties
# 管理. 指标. 出口. 统计. 主机 \ 统计. 示例.com
# 管理. 指标. 出口. 统计. 港口 = 9125
```


您还可以更改要使用的统计D行协议（默认为数据狗）：



```properties
# 管理. 指标. 出口. 斯塔斯塔德. 味道
```




### 波

波前注册表会定期将指标推至波面。 如果您正在直接向 波前出口指标，则必须提供您的 API 令牌：



```properties
#管理.指标.出口.波面.阿皮-令牌]YOUR_API_TOKEN
```


或者，您可以使用在环境中设置的波前侧车或内部代理， 将指标数据转发到波前 API 主机：



```properties
#管理.指标.出口.uri=代理：//本地主：2878
```


您还可以更改将指标发送到波前方的间隔：



```properties
#管理.指标.出口.波前。步骤+30s
```




## 萨姆尔元数据 UI

如果 CAS 正在处理外部 SAML2 IdP 的身份验证，请控制如何在 的 CAS 登录主页上显示 SAML MDUI 元素。

要了解有关此主题的更多内容，请 [](../integration/Shibboleth.html)查看本指南。



```properties
# cas. saml - 元数据 - ui. 要求有效元数据] 真实
# 卡斯. 萨姆 - 元数据 - ui. 资源 = 类路径：:classpath:/ 酒吧.key， http：/ / md. comcom. org / Incommon / 通信元数据.xml:classpath:：  / inc - md - pub .key
# cas. saml - 元数据 - ui .max有效性 = 0
# cas. saml - 元数据 - ui. 要求签名 - 根 = 假
# cas. saml - 元数据 - ui. 参数 = 实体
```


此功能的调度器设置可 [此处](Configuration-Properties-Common.html#job-scheduling) 配置密钥下 `cas.saml-元数据-ui`。



## 尤里卡服务发现

要了解有关此主题的更多内容，请 [](../installation/Service-Discovery-Guide-Eureka.html)查看本指南。



```properties
•尤里卡.客户.服务-url.默认区=EUREKA_SERVER_HOST：htt：//本地主机：8761]/尤里卡/
=尤里卡.客户端启用]真实
=尤里卡.实例.状态页面网址=${cas.server.prefix}/执行器/信息
# 尤里卡. 实例. 健康检查 url]${cas.server.prefix}/ 执行器 / 健康
# 尤里卡. 实例. 家庭页面 url]${cas.server.prefix}/
[ 尤里卡. 客户. 健康检查. 启用] 真实

# 春天. 云. comig. 发现. 启用] 假
```




## 领事服务发现

要了解有关此主题的更多内容，请 [](../installation/Service-Discovery-Guide-Consul.html)查看本指南。



```properties
#春天. 云. 领事. port= 8500
# 春天. 云. 领事. 启用] 真正的
# 春天. 云. 领事. 主机 ] 本地主机

# 春天. 云. 发现. 健康检查路径]<health-endpoint-url>
# 春天. 云. 领事. 发现. 健康检查间隔 # 15s${spring.application.name}${random.value}


#春天. 云. 领事. 发现. 心跳. 启用] 真实
# 春天. 云. 领事. 发现. 心跳. ttl 值 = 60
# 春天. 云. 领事. 发现. 心跳. ttl - 单位
```




## 供应



### 斯金

通过SCIM提供经过认证的CAS本金。 要了解有关此主题的更多内容，请 [](../integration/SCIM-Integration.html)查看本指南。



```properties
#cas. scim. 版本] 2
# 卡斯. scim. target]
# 卡斯. scim. 奥特托肯 ]
[ 卡斯. scim. 用户名]
[ 卡斯. scim. 密码]
```




## 属性同意

CAS 提供在属性发布时强制执行用户知情同意的能力。 要了解有关此主题的更多内容，请 [](../integration/Attribute-Release-Consent.html)查看本指南。



```properties
#cas. 同意. 提醒 ] 30
[ 卡斯. 同意. 提醒时间单位] 小时|天|月
# cas. 同意. 启用] 真正的
# cas. 同意. active = 真实

# cas. 同意. 激活策略 - 沟脚本. 位置
```


此功能的签名 & 加密设置可 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.同意`下提供。 [的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。



### 网流配置

此功能的Webflow自动配置设置可 [此处](Configuration-Properties-Common.html#webflow-auto-configuration) 配置密钥 `cas.同意.webflow`。



### 杰森属性同意



```properties
*卡斯.同意.json.位置*文件/等/卡斯/配置/同意。
```




### 格罗夫属性同意



```properties
* 卡斯. 同意. 格罗维. 位置： 文件/ 等 / 卡斯 / 康菲 / 同意。
```




### JPA 属性同意

此功能的数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.同意.jpa`。



### LDAP 属性同意

此功能的 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.同意.ldap`。



```properties
#卡斯. 同意. ldap. 同意属性名称 ] 卡斯决定
```




### 蒙哥德银行属性同意书

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#mongodb-configuration) 以下配置密钥 `cas.同意`。



### 重新分配属性同意

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#redis-configuration) 以下配置密钥 `cas.同意`。



### 库奇德布属性同意

此功能的常见配置设置可 [此处](Configuration-Properties-Common.html#couchdb-configuration) 以下配置密钥 `cas.同意`。



### 休息属性同意



```properties
*卡斯.同意.rest.终点=/api.示例.org/受信任的浏览器
```




## 阿帕奇要塞认证

要了解有关此主题的更多内容，请 [](../installation/Configuring-Fortress-Authentication.html)查看本指南。



```properties
#卡斯. 奥森. 堡垒. 巴科特斯特] 家
```




## CAS 客户端

配置与爪哇 CAS 客户端相关的设置，以处理入站机票验证操作等。



```properties
#cas.客户端.前缀/sso.示例.org/cas
# 卡斯.客户端.验证器类型=CAS10|CAS20|CAS30|杰森
```




## 密码同步

允许用户将帐户密码同步到就地的各种目的地。 要了解有关此 主题的更多内容，请 [](../installation/Password-Synchronization.html)查看本指南。



### LDAP 密码同步

此功能的常见 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥 `cas.authn.密码同步.ldap[0]`下。



```properties
#cas. authn. 密码同步. 启用] 真实
# 卡斯. 奥森. 密码同步. ldap[0]. 启用 = 假
```




## 密码管理

允许用户就地更新其帐户密码等。 要了解有关此主题的更多内容，请 [](../installation/Password-Policy-Enforcement.html)查看本指南。



```properties
#cas.authn.pm.启用]真实
#cas.authn.pm.启用卡普查=假

= 最小 8 和最大 10 个字符至少 1 个上写字母表， 1 小写字母表， 1 个数字和 1 个特殊字符
[ cas. authn. pm. 政策模式] [[a-z]][A-Z]（？&[）[阿扎兹]？&={8,10}

# cas.authn.pm.重置. 过期分钟 = 1
# cas.authn.pm.重置安全问题启用=真实

# 密码管理令牌是否包含客户端或服务器 IP 地址。
#cas.authn. pm. 重置. 包括服务器 - ip 地址] 真实
# cas. authn. pm. 重置. 包括客户端 - ip 地址] 真实

# 成功更改密码后自动登录
# cas. authn. pm.
```


此功能的常见电子邮件通知设置可 [此处](Configuration-Properties-Common.html#email-notifications) 配置密钥下 `cas.authn.pm.重置`。 此功能的短信通知设置 可 [此处](Configuration-Properties-Common.html#sms-notifications) 配置密钥下 `cas.authn.pm.重置`。

[的签名和加密密钥都是 JWK](Configuration-Properties-Common.html#signing--encryption) 大小 `512` 和 `256`。 加密算法设置为 `AES_128_CBC_HMAC_SHA_256`。 [此处](Configuration-Properties-Common.html#signing--encryption) 配置密钥 `cas.authn.pm.重置`下，可在此处签署此功能的 & 加密设置。



### 网流配置

此功能的网流自动配置设置可 [此处](Configuration-Properties-Common.html#webflow-auto-configuration) 配置密钥 `cas.authn.pm.webflow`下 。



### 密码历史记录

要了解有关此主题的更多内容，请 [](../installation/Password-Policy-Enforcement.html)查看本指南。



```properties
#cas. authn. pm. 历史. 启用] 假

# 卡斯. 奥森. 下午. 历史. 格罗维. 位置 ] 类路径： 密码历史. groovy
```




### 杰森密码管理



```properties
#卡斯. 奥森. 下午. json. 位置 ] 类路径： 杰森资源密码. json
```




### 沟槽密码管理



```properties
#卡斯. 奥森. pm. 格罗维. 位置 ] 类路径： 密码管理服务. 沟
```




### LDAP 密码管理

此功能的常见 LDAP 设置可 [此处](Configuration-Properties-Common.html#ldap-connection-settings) 配置密钥下 `cas.authn.pm.ldap[0]`。



```properties
#卡斯. 奥森. 下午. ldap[0]. 类型= ad|通用|电子编导|Freeipa
# cas. authn. pm. ldap[0]. 用户名属性 = uid

# 应该提取的属性， 以指示安全问题和答案
# cas. authn. pm. ldap[0]. 安全问题属性. atr - 问题 1] 在
1 [ 卡斯. authn. pm. ldap[0]. 安全问题属性. attr 问题 2] attranswer2
# 卡斯. 奥森. pm. ldap[0]. 安全问题属性. attr 问题 3] attranswer3
```




### JDBC 密码管理

此功能的常见数据库设置可 [此处](Configuration-Properties-Common.html#database-settings) 配置密钥下 `cas.authn.pm.jdbc`。 此 功能的常见密码编码设置可 [此处](Configuration-Properties-Common.html#password-encoding) 配置密钥下 `cas.authn.pm.jdbc`。



```properties
•下面注明的两个字段预计将
返回 #cas.authn.pm.jdbc .sql安全问题=选择问题，从表中回答用户在哪里？？

#cas.authn.pm.jdbc.sql查找电子邮件]从用户所在的位置选择来自表的电子邮件。？
#cas.奥森.pm.jdbc.sql查找电话=从用户所在位置的表中选择手机？？
#cas.authn.pm.jdbc.sql查找用户=从表中选择用户，电子邮件在哪里？？
#卡斯. 奥森. pm. jdbc .sql更改密码 [ 更新表设置密码]？ 用户在哪里？？
```




### 休息密码管理



```properties
#cas. authn. pm. rest. 端点 - url 电子邮件]
[ 卡斯. 奥森. 下午. rest. 端点 - url - 电话]
[ 卡斯. 奥森. 下午. rest. 端点 - 乌尔用户]
# 卡斯. 奥森. 下午休息 .端点 - url 安全问题]
# cas. authn. pm. rest. 端点 - url 更改]
# 卡斯. 奥森. pm. rest. 端点 - 用户名 ]
# cas. 奥森. pm. rest. 端点 - 密码
```
