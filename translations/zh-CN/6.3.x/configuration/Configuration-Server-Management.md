---
layout: 违约
title: CAS - 配置服务器
category: 配置
---

# 配置服务器

当 CAS 部署通过部署管道从开发到测试并进入生产 您可以管理这些环境之间的配置，并确保应用程序 通过使用 [Spring云](https://github.com/spring-cloud/spring-cloud-config) 项目提供的外部配置服务器 迁移时拥有所需的一切运行。 作为替代方案， 您可能会决定以独立模式运行 CAS，从而消除外部配置服务器部署的需要， 但代价是丢失与云部署相关的功能和功能。

## 配置配置文件

CAS 服务器 Web 应用程序响应以下策略，这些策略决定了应如何使用设置。

### 独立

这是默认配置模式，表明 CAS **不需要连接到外部配置服务器 ** ，并将在嵌入式 *独立模式*运行。 打开此选项后，CAS 默认将尝试在设置名称 `cas.独立.配置目录` 下注明的给定目录中查找 的设置和属性，否则将返回使用 `/等/cas/配置` 作为配置目录。 您可以通过此处概述的方法 [指示 CAS 使用此设置](Configuration-Management.html#overview)。 还存在一个 `cas.独立.配置文件` 可用于以文件或类路径资源的形式直接向 CAS 提供属性集合。

与春云外部配置服务器类似，此目录的内容包括 `（cas|应用）。（yml|专业）` 可用于控制CAS行为的文件。 此外，请注意，CAS 可以监控此配置目录，以便自动拾取 更改，并根据需要刷新应用上下文。 请 [查看本指南](Configuration-Management-Reload.html#reload-strategy) 了解更多。

请注意，默认情况下，CAS 的所有设置和配置都通过嵌入式 `应用程序进行控制。 CAS 服务器中的属性` 文件 Web 应用程序。 还有一个嵌入式 `应用程序.yml` 文件，如果您希望将配置发货到 CAS 主 Web 应用程序内，并且不依赖外部化配置文件，则该文件允许您覆盖所有默认值。 如果你喜欢的属性比yaml，那么 `应用程序独立。属性` 将覆盖 `应用程序。属性` 以及。

外部配置文件中找到的设置现在和现在都能够覆盖 CAS 提供的默认值。 CAS 配置目录内 配置文件的命名遵循以下模式：

- `应用程序。（属性|毫升|yml）` 文件总是加载，如果发现。
- 位于 `属性内的设置|毫升|` 文件，其名称匹配 `spring.application.name` 值加载（即 `cas.属性`）注意： `spring.application.name` 默认 `CAS` 但小写名称也将加载。
- 位于 `属性|毫升|yml` 文件内的设置，其名称与 `spring.profile.` 值匹配，加载了主动（即 `ldap.属性`）。
- 在打包的 Web 应用程序（`应用程序-{profile}.属性|毫升|yaml`）之外的配置文件特定的应用程序属性 这允许您在需要时将设置拆分为多个属性文件，然后通过将它们的名称 分配到活动配置文件列表（即 `春天. 配置文件. 活动 = 独立， 测试， 舞台`）

配置文件按以下顺序加载，其中 `弹簧。配置文件.主动=独立、配置文件 1、配置文件 2`。 请注意，加载的最后一个配置文件将覆盖之前加载的配置文件中的任何重复属性：

1. `应用。（属性|毫升|亚毫升）`
2. （小写） `spring.application.name。（属性|毫升|毫升）`
3. `spring.application.name（属性|毫升|亚毫升）`
4. `应用程序独立。（属性|毫升|亚毫升）`
5. `独立。（属性|毫升|亚毫升）`
6. `应用程序配置文件1.（属性|毫升|亚毫升）`
7. `配置文件1.（属性|毫升|亚毫升）`
8. `应用程序配置文件2.（属性|毫升|亚毫升）`
9. `配置文件2.（属性|毫升|亚毫升）`

如果存在两个具有相同碱基名称和不同扩展的配置文件，则按 `属性`、 `yml` 、然后 `yaml` 然后 `凹凸不平的` （最后一个处理后的胜利存在重复属性）进行处理。 这些外部配置文件将覆盖位于类路径中的文件（例如，来自 CAS 叠加中 `src/主/资源` 的文件，这些文件最终 `WEB-INF/类`），但内部文件按照 [弹簧启动](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html) 规则加载，这些规则与此处描述的 CAS 独立配置规则不同（例如 <profile>。属性不会从类路径加载，但 `应用程序-<profile>。属性` ）。

<div class="alert alert-warning"><strong>记得</strong><p>建议您不要叠加或以其他方式
修改 <code>应用程序中内置的属性</code> 或 <code>引导属性</code> 文件。 这只会使您的部署复杂化和削弱。
相反，尽量遵守 CAS 违约和引导 CAS 通过默认，覆盖通过 <code>应用程序.yml</code>， <code>应用程序独立。 属性</code> 或
使用 <a href="Configuration-Management.html#overview">概述的策略</a>。 同样，请尝试指示 CAS 查找其外部的
配置文件。 过早的优化只会导致混乱。</p></div>

### 春云

CAS 能够使用外部和中央配置服务器来获取状态和设置。 配置服务器为 CAS（及其所有其他客户端）提供了一种非常抽象的方式，从各种源 （如文件系统、 `git` 或 `svn` 存储库、MongoDb 数据库、Vault 等）获取设置。 此解决方案的美妙之处在于，对于 CAS Web 应用服务器来说，设置来自何处并不重要，它对潜在的属性来源一无所知。 它 与配置服务器交谈，以定位设置并继续前进。

<div class="alert alert-info"><strong>配置安全性</strong><p>这是一个很好的策略，以确保配置设置
不会分散在各种部署环境中，从而导致更安全的部署。 配置服务器不需要
暴露在外部世界，它可以安全可靠地隐藏在防火墙后面，等等，只允许访问授权客户端
如CAS服务器Web应用程序。</p></div>

[春云项目](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)提供了全面的指南。

#### 覆盖

配置服务器本身，类似于CAS，可以通过它自己的 [WAR覆盖](https://github.com/apereo/cas-configserver-overlay)中的以下模块部署 ：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡斯服务器-网络应用程序-配置服务器</artifactId>
  <version>${cas.version}</version>
</dependency>
```

除了此处列出的 [策略](Configuration-Management.html#overview)，配置服务器 可以通过以下顺序和机制加载 CAS 设置和属性：

1. 包装网络应用程序（`应用程序-{profile}.属性|毫升`）以外的个人资料特定应用属性）
2. 包装在罐子内的配置文件特定应用属性（`应用程序 -{profile}。属性|毫升`）
3. 包装罐外的应用属性（`应用程序。属性|毫升`）。
4. 应用属性包装在您的罐子内（`应用程序。属性|毫升`）。

配置服务器的配置和行为也由它自己的 `src/主/资源/引导属性` 文件控制。 默认情况下，它运行在端口 `8888` 下，位于嵌入式 Apache Tomcat 服务器内部的 `/casconfigserver
` ，该服务器的端点受到基本身份验证 保护，其中默认凭据 `casuser` 以及 `src/主/资源/`应用程序中定义的自动生成密码。

此外，默认情况下，它运行在下面描述的 `原生` 配置文件下。

配置服务器保护并暴露了以下端点：

| 参数            | 描述                          |
| ------------- | --------------------------- |
| `/加密`         | 接受 `开机自检` 以加密 CAS 配置设置。     |
| `/解密`         | 接受解密 CAS 配置设置的 `开机自检` 。     |
| `/执行器/刷新`     | 接受 `POST` ，并尝试刷新配置服务器的内部状态。 |
| `/执行器/执行器`    | 接受 `获取` 并描述配置服务器的所有配置源。     |
| `/执行器/案例/默认值` | 描述配置服务器对 `默认` 设置配置文件的了解。    |
| `/执行器/案例/本地`  | 描述配置服务器对 `本机` 设置配置文件的了解。    |

一旦部署了配置服务器，并假设用于保护配置服务器的凭据与下面的示例匹配，您可以通过以下方式观察设置的集合：

```bash
卷曲 - u 卡苏瑟： 梅隆 https://config.server.url:8888/casconfigserver/cas/native
```

假设在配置中启用执行器端点，您还可以观察为配置服务器提供设置的属性源的集合：

```bash
卷曲 - u 卡苏瑟： 梅隆 https://config.server.url:8888/casconfigserver/actuator/env
```

<div class="alert alert-info"><strong>执行器端点</strong><p>
请记住，执行器端点通常以 <code>/执行器</code>为前缀。
</p></div>

#### 客户和消费者

要让 CAS 服务器 Web 应用程序（或任何其他客户端）与配置服务器交谈， 以下设置需要应用于 CAS 自己的 `src/主/资源/引导属性` 文件。 将 CAS 服务器 Web 应用程序配置为配置服务器 的客户端的属性必须在从配置服务器读取应用程序的其余部分之前读取， *引导* 阶段。

```properties
春天.云.配置.urittps：//卡苏特：Mellon@config.服务器.url：8888/卡康菲格服务器
春天.云.配置文件=原生
弹簧.云.配置文件启用=真
弹簧。
```

请记住，配置服务器提供从 `/{name}/{profile}/{label}` 到应用程序的属性源， 客户端应用中的默认绑定如下：

```bash
"名称"= ${spring.application.name}
"配置文件"= ${spring.profiles.active}
"标签"="主"
```

所有这些可以通过设置 `弹簧.cloud.comig.*` （其中 `*` 是"名称"、"配置文件"或"标签"）来覆盖它们。 "标签"可用于回滚到以前的配置版本：使用默认的 Config 服务器实施 它可以是 git 标签、分支名称或提交 ID。 标签也可以作为逗号分离列表提供， 在这种情况下，列表中的项目将逐一尝试，直到一个成功。 例如，当您可能想要将配置标签与分支对齐时， 分支的功能时，这可能会很有用， 但使其成为可选的（例如： `春天. 云. 配置. 标签 ] 我， 发展`） 。

要了解 CAS 如何允许您重新加载配置更改，请 [查看本指南](Configuration-Management-Reload.html)。

#### 配置 文件

存在各种配置文件，以确定配置服务器应如何检索属性和设置。

##### 本地

默认情况下，将服务器配置为加载 `cas。（属性|毫升）从外部位置` `/等/cas/配置`的文件。 服务器会不断监控此位置以检测外部变化。 请注意，此位置只需要 存在，并且不需要任何特殊权限或结构。 此 目录内的配置文件的名称需要与 `spring.application.name` 匹配（即 ``）。

如果你想使用额外的配置文件，他们需要有 表格 `应用程序-<profile>。（属性|毫升）`。 名为 `应用程序的文件。（属性|毫升）默认情况下将包括` 。 配置文件特定 文件可以通过使用 `spring.profile.包括` 配置选项激活， 通过 `src/主/资源/引导属性` 文件进行控制：

```properties
春天.配置文件.活动=原生
春天.云.配置.服务器.本地.搜索位置=文件//等/cas/配置
弹簧。
```

外部位置托管的外部 `。属性` 文件示例如下：

```properties
cas.server.name......
```

您也可以使用 `cas.yml` 文件来托管更改。

##### 违约

配置服务器还能够处理 `git` 或 `svn` 基于存储库的 CAS 配置。 此类存储库可以是部署的本地存储库，也可以以 GitHub/BitBucket 的形式在云上。 访问基于云的 存储库可以以用户名/密码的形式出现，也可以通过 SSH 进行访问，以便只要在 CAS 部署环境中配置适当的密钥，这与通常通过 SSH 访问 git 存储库的方式没有什么不同。

```properties
#春天. 配置文件. 活动 = 默认
# 春天. 云. 配置. 服务器. git. uri
.com= 配置.服务器.git.uri®文件：//${user.home}/配置
#春天.云.配置.服务器.用户名=
# 春天.云.配置.服务器。 git. 密码 –

[ 春天. 云. 配置. 服务器. svn. 基于]
[ 春天. 云. 配置. 服务器. svn. uri]
[ 春天. 云. 康夫 .服务器. svn. 用户名]
[ 春天. 云. 配置. 服务器. svn. 密码]
[ 春天. 云. 配置. 服务器. svn. 默认标签] 主干
```

不用说，存储库可以同时使用 YAML 和属性语法来托管配置文件。

<div class="alert alert-info"><strong>保留您需要的！</strong><p>同样，在上述所有战略中，
鼓励采用者只保留和维护其特定部署所需的属性。 获取所有 CAS 设置的副本并将其移动到外部位置
不必要的。 由外部配置位置或存储库
定义的设置能够覆盖 CAS
提供的默认值。</p></div>

##### 蒙古德布

该服务器还能够完全从 MongoDb 实例中定位属性。

支持通过战争叠加中的以下依赖提供：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-配置-云-蒙戈</artifactId>
     <version>${cas.version}</version>
</dependency>
```

请注意，要访问和审查 CAS 属性的集合， 您需要使用自己的原生模组为 MongoDB 配置和注入设置。

蒙古银行文件必须在蒙古</code>`收集中找到，如下所述文件：</p>

<pre><code class="json">•
    "id"："kfhf945jegnsd45sdg93452"，
    "名称"："设置名称"，
    "值"："设置值"
}
`</pre>

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#mongodb)。

##### 哈希公司保险库

CAS 还能够使用 [库](https://www.vaultproject.io/) 来 定位属性和设置。 [请查看本指南](Configuration-Properties-Security.html)。

##### 哈希公司领事

CAS 还能够使用 [领事](https://www.consul.io/) 来 定位属性和设置。 [请查看本指南](../installation/Service-Discovery-Guide-Consul.html)。

##### 阿帕奇动物园管理员

CAS还能够使用 [阿帕奇动物园管理员](https://zookeeper.apache.org/) 来定位属性和设置。

支持通过战争叠加中的以下依赖提供：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持配置 - 云 - 动物园管理员</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#zookeeper)。

您将需要将 CAS 设置映射到包含值的动物园管理员节点。 所有设置的父节点应 与提供给 CAS 的配置根值相匹配。 在根部下，您可以有 `cas`、 `cas、dev`、 `cas、本地`等 文件夹，其中 `开发` 和 `本地` 是 Spring 配置文件。

要在 Apache 动物园管理员中创建节点和值，请尝试以下命令 作为示例：

```bash
动物园管理员-客户端-服务器动物园管理员1：2181
创建/cas cas
创建/cas/配置cas
创建/cas/配置/cas cas
创建/cas/配置/cas/设置名棺材：：测试
```

在阿帕奇动物园管理员中创建节点和目录可能需要提供价值。 上述示例命令显示，在创建目录时提供 cas</code> `值。 请务必咨询官方的阿帕奇动物园管理员指南。 您可能不需要这样做。</p>

<p spaces-before="0">最后，在 CAS 属性中，新的 <code>设置"` 设置"可以用作参考。

```properties
#卡斯. 东西. 东西]${settingName}
```

...其中 `${settingName}` 获得阿帕奇动物园管理员节点的内容的价值 `cas/配置/cas/设置`。

##### 亚马逊 S3

CAS 还能够使用 [亚马逊 S3](https://aws.amazon.com/s3/) 来定位属性和设置。

支持通过战争叠加中的以下依赖提供：

 ```xml
 <dependency>
      <groupId>组织. apereo. cas</groupId>
      <artifactId>卡斯服务器支持配置 - 云 - aws - s3</artifactId>
      <version>${cas.version}</version>
 </dependency>
 ```

 有关相关设置，请参阅本指南</a>

。</p> 



##### 亚马逊秘密经理

CAS还能够使用 [亚马逊秘密经理](https://aws.amazon.com/secrets-manager/) 来定位属性和设置。

支持通过战争叠加中的以下依赖提供：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-配置-云-aws-秘密管理</artifactId>
     <version>${cas.version}</version>
</dependency>
```


有关相关设置，请参阅本指南</a> 。</p> 



##### 亚马逊系统管理器参数存储 （SSM）

CAS 还能够使用 [AWS 系统管理器参数存储](https://docs.aws.amazon.com/systems-manager/latest/userguide/systems-manager-parameter-store.html) 来定位属性和设置。

支持通过战争叠加中的以下依赖提供：



```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持配置 - 云 - aws - ssm</artifactId>
     <version>${cas.version}</version>
</dependency>
```


有关相关设置，请参阅本指南</a> 。</p> 



##### 迪纳莫德布

CAS 还能够使用 [DynamoDb](https://aws.amazon.com/dynamodb/) 来定位属性和设置。

支持通过战争叠加中的以下依赖提供：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-配置-云-动态</artifactId>
     <version>${cas.version}</version>
</dependency>
```


`代dbcas专业` 表由中科院自动创建，结构如下：



```json
•
    "id"："主键"，
    "名称"："设置名称"，
    "值"："设置值"
}
```


有关相关设置，请参阅本指南</a> 。</p> 



##### 蔚蓝钥匙沃特秘密

CAS 还能够使用微软 Azure 的"钥匙伏特密钥"来定位属性和设置。 支持通过战争叠加中的以下依赖提供：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>套机服务器支持-配置-云-蔚蓝-键-</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#azure-keyvault-secrets)。

**重要**：Azure 钥匙库中允许的名称模式 `^[0-9a-zA-Z-]+$`。 包含包含 `的 
的属性。名称中的` （即 `cas.一些.财产`），取代 `。` 与 `-` ，当您存储在Azure钥匙库设置（即 `一些财产`）。 该模块将为您处理转换。 

有关相关设置，请参阅本指南</a> 。</p> 



##### 京城

CAS 还能够使用关系数据库来定位属性和设置。

支持通过战争叠加中的以下依赖提供：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>套机服务器支持配置-云-jdbc</artifactId>
     <version>${cas.version}</version>
</dependency>
```


默认情况下，设置预计将在包含字段的 `CAS_SETTINGS_TABLE` 下找到： `id`、 `名称` 和 `值`。 要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#jdbc)。



##### 休息

CAS 还能够使用 REST API 定位属性和设置。

支持通过战争叠加中的以下依赖提供：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-配置-云-休息</artifactId>
     <version>${cas.version}</version>
</dependency>
```


REST 端点预计将在有效负载中生成 `地图` ，其中密钥是设置名称 ，值是设置值。 要查看此 功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#rest)。



#### CAS 服务器云配置

CAS 项目直接在此页面上提供的云配置模块也可以在 CAS 服务器覆盖 内逐字使用。 请记住，这些模块的主要目标是简单地从源中检索 设置和属性。 虽然它们在Spring云配置服务器内激活时主要和主要有用， 可以设置为荣誉配置文件等，但它们也可能直接用于 CAS 服务器覆盖内，以便在独立模式运行时从源 取取设置。 在这种情况下，无论格式或语法的 ，所有配置来源都将协同工作以检索设置，并且您当然可以根据需要进行混合和匹配。



#### 复合来源

在某些情况下，您可能希望从多个环境存储库提取配置数据。 要做到这一点，只需在配置服务器的应用程序属性或 YAML 文件中启用多个配置文件。 例如，如果您想从 Git 存储库以及 SVN 存储库提取配置数据，您将为配置服务器设置以下属性。



```yml
春季：
  配置文件：
    活动：git，svn
  云：
    配置：
      服务器：
        svn：
          uri：file:///path/to/svn/repo
          订单：2
        git：
          uri：file:///path/to/git/repo
          订单：1
```


除了每个指定 URI 的回购之外，您还可以指定</code> 属性的 `订单。 <code>订单` 属性允许您指定 所有存储库的优先顺序。 订单属性的数字值越低，其优先级就越 。 存储库的优先顺序将有助于解决包含相同属性值的存储库之间的任何潜在 冲突。



#### 财产覆盖

配置服务器具有"覆盖"功能，允许操作员提供配置属性， 使用正常更改事件和挂钩的应用程序无法意外更改的所有应用程序。 要声明覆盖，请添加名称值对的地图，以 `春天。云.配置.服务器.覆盖`。 

例如：



```yml
春季：
  云：
    配置：
      服务器：
        覆盖：
          foo：栏
```


这将导致 CAS 服务器（作为配置服务器的客户端）阅读 `foo=bar` 独立于其自己的配置。



## 保护设置

要了解如何通过加密保护 CAS 设置的敏感性，请 [](Configuration-Properties-Security.html)查看本指南。



## 重新加载更改

要了解 CAS 如何允许您重新加载配置更改，请 [查看本指南](Configuration-Management-Reload.html)。



## 聚类部署

CAS 使用 [春云总线](http://cloud.spring.io/spring-cloud-static/spring-cloud.html) 在分布式部署中管理配置。 春云总线将 分布式系统的节点与轻量级消息中间商连接。 然后，这可用于广播状态 更改（例如配置更改）或其他管理说明。

要了解如何通过加密保护 CAS 设置的敏感性，请 [](Configuration-Management-Clustered.html)查看本指南。
