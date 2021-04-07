---
layout: 默认
title: CAS-配置服务器
category: 配置
---

# 配置服务器

随着您的CAS部署从开发到测试以及进入生产 整个部署管道，您可以管理这些环境之间的配置，并确保应用程序 具有通过使用提供的外部配置服务器 迁移时所需要运行的所有功能。由 [Spring Cloud](https://github.com/spring-cloud/spring-cloud-config) 项目提供。 或者， 以独立模式运行CAS，从而无需外部配置服务器部署，而 则可能会丢失与云部署相关的功能，但代价是。

## 配置配置文件

CAS服务器Web应用程序响应以下指示应如何使用设置的策略。

### 单机版

这是默认配置模式，它指示CAS不需要 **NOT** 需要连接到外部配置服务器 并且将以嵌入式 *独立模式*。 启用此选项后，CAS默认情况下将尝试 `cas.standalone.configuration-directory` 下指示的给定目录内 ，否则退回到使用 `/ etc / cas / config` 作为配置目录。 您可以指示CAS通过这些方法来使用此设置 [这里概述](Configuration-Management.html#overview)。 还存在一个 `cas.standalone.configuration-file` ，该文件可用于以文件或类路径资源的形式直接将属性集合提供给CAS。

与Spring Cloud外部配置服务器类似，此目录的内容包括 `（cas | application）。（yml | properties）` 可用于控制CAS行为的文件。 另外，请注意，CAS可以监视此配置目录，以自动提取更改 并根据需要刷新应用程序上下文。 请 [本指南](Configuration-Management-Reload.html#reload-strategy) 以了解更多信息。

请注意，默认情况下，所有CAS设置和配置都是通过CAS服务器 Web应用程序中 `application.properties` 还有一个嵌入式 `application.yml` 文件，如果您希望将配置发送到主CAS Web应用程序中而不依赖于外部化的配置文件，则可以覆盖所有默认设置。 如果您更喜欢yaml的属性，那么 `application-standalone.properties` 也会覆盖 `application.properties`。

在外部配置文件中找到的设置可以并且将能够覆盖CAS提供的默认设置。 CAS配置目录中的配置文件 的命名遵循以下模式：

- 如果找到 `应用程序。（properties | yml | yaml）总是加载`
- 加载位于 `properties | yml | yaml` 文件中的设置，这些文件的名称与 `spring.application.name` 的值匹配（即 `cas.properties`）注： `spring.application.name` 默认为大写 `CAS` 但小写名称也将被加载。
- `spring.profiles.active` 的值匹配的 `properties | yml | yaml` 文件内的设置（即 `ldap.properties`）。
- 打包的Web应用程序之外的特定于配置文件的应用程序属性（`application-{profile}.properties | yml | yaml`） 这样，您可以根据需要将设置拆分为多个属性文件，然后通过将其名称分配为 来定位它们活动配置文件的列表（即 `spring.profiles.active = standalone，testldap，stagingMfa`）

配置文件按以下顺序加载，其中 `spring.profiles.active = standalone，profile1，profile2`。 请注意，最后加载的配置文件将覆盖较早加载的配置文件中的所有重复属性：

1. `应用程序。（属性| yml | yaml）`
2. （小写） `spring.application.name。（属性| yml | yaml）`
3. `spring.application.name。（properties | yml | yaml）`
4. `独立应用程序。（properties | yml | yaml）`
5. `独立的。（属性| yml | yaml）`
6. `application-profile1。（属性| yml | yaml）`
7. `profile1。（属性| yml | yaml）`
8. `application-profile2。（属性| yml | yaml）`
9. `profile2。（属性| yml | yaml）`

如果存在与相同的基本名称和不同的扩展两个配置文件，它们在的顺序处理 `属性`， `YML` ，然后 `YAML` ，然后 `常规` （最后一个经处理的胜，其中重复的属性存在）。 这些外部配置文件将覆盖位于类路径中的文件（例如，CAS覆盖中 `src / main / resources` `WEB-INF / classs`），但是内部文件是按 [spring boot](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html) 与此处描述的CAS独立配置规则不同的规则（例如， <profile>.properties不会从类路径加载，但是 `应用程序<profile>.properties` 会加载）。

<div class="alert alert-warning"><strong>记住</strong><p>建议您不要覆盖或以其他方式
修改内置的 <code>application.properties</code> 或 <code>bootstrap.properties</code> 文件。 这只会使部署复杂化并削弱其性能。
代替试图通过以符合通过默认设置，覆盖所述默认CAS和自举CAS尽可能 <code>application.yml</code>， <code>application-standalone.properties</code> 或
使用 <a href="Configuration-Management.html#overview">级概述的策略</a>。 同样，尝试指示CAS在其自身外部
 过早的优化只会导致混乱。</p></div>

### 春云

CAS能够使用外部和中央配置服务器来获取状态和设置。 配置服务器为CAS（及其所有其他客户端）提供了一种非常抽象的方式，可以从多种 资源中获取设置，例如文件系统， `git` 或 `svn` 存储库，MongoDb数据库，Vault等。 此解决方案的优点在于，对于CAS Web应用程序服务器而言，设置的来源无关紧要，并且不了解基础属性源。 它 以查找设置并继续。

<div class="alert alert-info"><strong>配置安全</strong><p>这是一个非常好的策略，可确保配置设置
不会分散在各种部署环境中，从而实现更安全的部署。 配置服务器不必
，它可以安全地隐藏在防火墙等后面，从而仅允许访问授权客户端
例如CAS服务器Web应用程序）。</p></div>

[Spring Cloud项目](https://cloud.spring.io/spring-cloud-config/spring-cloud-config.html)提供了完整的综合指南。

#### 覆盖

类似于CAS的配置服务器本身，可以通过以下模块在其自己的 [WAR覆盖](https://github.com/apereo/cas-configserver-overlay) ：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-webapp-config-server</artifactId>
  <version>${cas.version}</version>
</dependency>
```

除了此处概述 [](Configuration-Management.html#overview)，配置服务器 还可通过以下顺序和机制来加载CAS设置和属性：

1. 打包的Web应用程序之外的特定于配置文件的应用程序属性（`application-{profile}.properties | yml`）
2. 打包在jar中的特定于配置文件的应用程序属性（`application-{profile}.properties | yml`）
3. 打包的jar之外的应用程序属性（`application.properties | yml`）。
4. 打包在jar中的应用程序属性（`application.properties | yml`）。

配置服务器的配置和行为也由其自己的 `src / main / resources / bootstrap.properties` 文件控制。 默认情况下，它运行在端口 `8888` 的 `/ casconfigserver` 内部 的嵌入式Apache Tomcat服务器中，该端点的端点受基本身份验证 保护，其中默认凭据为 `casuser` ，自动生成的密码定义为 `src / main /resources/application.properties`。

此外，默认情况下，它在以下所述的 `本机` 配置文件下运行。

以下端点由配置服务器保护和公开：

| 范围                           | 描述                         |
| ---------------------------- | -------------------------- |
| `/加密`                        | 接受 `POST` 来加密CAS配置设置。      |
| `/解密`                        | 接受 `POST` 来解密CAS配置设置。      |
| `/执行器/刷新`                    | 接受 `POST` 并尝试刷新配置服务器的内部状态。 |
| `/执行器/环境`                    | 接受 `GET` 并描述配置服务器的所有配置源。   |
| `/ actuator / cas / default` | 描述配置服务器对 `默认` 设置配置文件的了解。   |
| `/执行器/ cas /本机`              | 描述配置服务器对 `本机` 设置配置文件的了解。   |

部署完配置服务器并假定用于保护配置服务器的凭据与下面的示例匹配后，您可以通过以下方法观察设置的收集：

```bash
curl -u casuser：梅隆https：//config.server.url：8888 / casconfigserver / cas / native
```

假设在配置中启用了执行器端点，您还可以观察到为配置服务器提供设置的属性源的集合：

```bash
curl -u casuser：梅隆https：//config.server.url：8888 / casconfigserver / actuator / env
```

<div class="alert alert-info"><strong>执行器端点</strong><p>
请记住，执行器端点通常以 <code>/作动器</code>为前缀。
</p></div>

#### 客户和消费者

要使CAS服务器Web应用程序（或与此有关的任何其他客户端）与配置服务器通信， 需要将以下设置应用于CAS自己的 `src / main / resources / bootstrap.properties` 文件。 *引导* 阶段从配置服务器读取应用程序的其余配置之前，先读取将CAS服务器Web应用程序配置为配置服务器

```properties
spring.cloud.config.uri = https：// casuser：Mellon@config.server.url：8888 / casconfigserver
spring.cloud.config.profile =本地
spring.cloud.config.enabled = true
spring.profiles。 active =默认
```

请记住，配置服务器从供应源属性 `/{name}/{profile}/{label}` 至应用， 其中在所述客户端应用程序的缺省绑定如下：

```bash
“名称” = ${spring.application.name}
“配置文件” = ${spring.profiles.active}
“标签” =“主”
```

`spring.cloud.config。*` （其中 `*` 是“名称”，“配置文件”或“标签”），可以覆盖所有参数。 “标签”对于回滚到以前的配置版本很有用。使用默认的Config Server实现 它可以是git标签，分支名称或提交ID。 标签也可以以逗号分隔的列表的形式提供，如果为 在这种情况下，列表中的项将一一尝试，直到成功为止。 这在使用功能 分支时可能很有用，例如，当您可能希望将配置标签与分支 对齐，但使其可选时（例如 `spring.cloud.config.label = myfeature，develop`）。

要了解更多有关CAS如何允许您重新加载配置更改的信息，请 [本指南](Configuration-Management-Reload.html)。

#### 个人资料

存在各种配置文件，以确定配置服务器应如何检索属性和设置。

##### 本国的

默认情况下，服务器配置为从外部位置 `/ etc / cas / config``cas。（properties | yml）` 文件。 服务器会不断监视此位置，以检测外部更改。 请注意，此位置只需要 ，就不需要任何特殊的权限或结构。 目录中的配置文件的名称必须与 `spring.application.name` （即 `cas.properties`）匹配。

如果要使用其他配置文件，则它们必须具有 形式 `application-<profile>（properties | yml）`。 默认情况下将包含一个名为 `application。（properties | yml）` 可以使用 `spring.profiles.include` 配置选项来激活特定于配置文件的 文件，通过 `src / main / resources / bootstrap.properties` 文件来

```properties
spring.profiles.active =本机
spring.cloud.config.server.native.searchLocations = file：/// etc / cas / config
spring.profiles.include = profile1，profile2
```

由外部位置托管的外部 `.properties`

```properties
cas.server.name = ...
```

您可能还可以使用 `cas.yml` 文件来托管更改。

##### 默认

配置服务器还能够处理托管CAS配置的基于 `git` 或 `svn` 这样的存储库可以是部署本地的，也可以是GitHub / BitBucket形式的云。 对 个基于云的存储库的访问既可以采用用户名/密码的形式，也可以通过SSH进行访问，只要在 CAS部署环境中配置了适当的密钥，这实际上与正常情况下通过git存储库的访问方式没有什么不同SSH。

```properties
＃spring.profiles.active =默认
＃spring.cloud.config.server.git.uri = https：//github.com/repoName/config
＃spring.cloud.config.server.git.uri =文件：/ /${user.home}/ config
＃spring.cloud.config.server.git.username =
＃spring.cloud.config.server.git.password =

＃spring.cloud.config.server.svn.basedir =
＃spring。 cloud.config.server.svn.uri =
＃spring.cloud.config.server.svn.username =
＃spring.cloud.config.server.svn.password =
＃spring.cloud.config.server.svn。 default-label = trunk
```

不用说，存储库可以同时使用YAML和属性语法来托管配置文件。

<div class="alert alert-info"><strong>保留您所需要的！</strong><p>同样，在上述所有策略中，
鼓励采用者仅保留和维护其特定部署所需的属性。 获取所有CAS设置的副本并将其移动到外部位置
这是不必要的。 默认情况下，由外部配置位置或存储库定义的设置为

</p></div>

##### MongoDb

该服务器还能够完全从MongoDb实例定位属性。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-mongo</artifactId>
     <version>${cas.version}</version>
</dependency>
```

请注意，要访问和查看CAS属性的集合，则 您将需要使用自己的MongoDB本机工具来配置和注入设置。

必须在集合 `MongoDbProperty`找到MongoDb文档，如下所示：

```json
{
    “ id”：“ kfhf945jegnsd45sdg93452”，
    “ name”：“ the-setting-name”，
    “ value”：“ the-setting-value”
}
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#mongodb)。

##### HashiCorp保险库

CAS还能够使用 [Vault](https://www.vaultproject.io/) 到 定位属性和设置。 [请阅读本指南](Configuration-Properties-Security.html)。

##### HashiCorp领事

CAS还可以使用 [Consul](https://www.consul.io/) 到 定位属性和设置。 [请阅读本指南](../installation/Service-Discovery-Guide-Consul.html)。

##### 阿帕奇ZooKeeper

CAS还能够使用 [Apache ZooKeeper](https://zookeeper.apache.org/) 来查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-zookeeper</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#zookeeper)。

您将需要将CAS设置映射到包含值的ZooKeeper的节点。 所有设置的父节点应该 匹配提供给CAS的配置根值。 在根，你可以有文件夹，例如 为 `CAS`， `CAS，偏差`， `CAS，本地`等，其中 `dev的` 和 `本地` 是弹簧的配置文件。

要在Apache ZooKeeper中创建节点和值，请尝试以下命令 作为示例：

```bash
zookeeper-client -server zookeeper1：2181
创建/ cas cas
创建/ cas / config cas
创建/ cas / config / cas cas
创建/ cas / config / cas / settingName casuser :: Test
```

在Apache ZooKeeper中创建节点和目录可能需要提供一个值。 上面的示例命令显示，创建目录时提供的 值为 `cas` 请始终与官方的Apache ZooKeeper指南联系。 您可能不需要执行该步骤。

最后，在你的CAS特性，新的 `settingName` 设置可作为参考。

```properties
＃cas.something.something =${settingName}
```

...其中 `${settingName}` 获取Apache ZooKeeper节点 `cas / config / cas / settingName`的内容的值。

##### 亚马逊S3

CAS还可以使用 [Amazon S3](https://aws.amazon.com/s3/) 来查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

 ```xml
 <dependency>
      <groupId>org.apereo.cas</groupId>
      <artifactId>cas-server-support-configuration-cloud-aws-s3</artifactId>
      <version>${cas.version}</version>
 </dependency>
 ```

 有关设置，请参见本指南的 [](Configuration-Properties.html#amazon-s3)

##### 亚马逊秘密经理

CAS还能够使用 [Amazon Secret Manager](https://aws.amazon.com/secrets-manager/) 来查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-aws-secretsmanager</artifactId>
     <version>${cas.version}</version>
</dependency>
```

有关设置，请参见本指南的 [](Configuration-Properties.html#amazon-secrets-manager)

##### Amazon Systems Manager参数存储（SSM）

CAS还能够使用 [AWS Systems Manager参数存储](https://docs.aws.amazon.com/systems-manager/latest/userguide/systems-manager-parameter-store.html) 来查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-aws-ssm</artifactId>
     <version>${cas.version}</version>
</dependency>
```

有关设置，请参见本指南的 [](Configuration-Properties.html#amazon-parameter-store)

##### DynamoDb

CAS还可以使用 [DynamoDb](https://aws.amazon.com/dynamodb/) 来查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持配置cloud-dynamodb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

CAS将自动创建具有以下结构的 `DynamoDbCasProperties`

```json
{
    “ id”：“主键”，
    “ name”：“ the-setting-name”，
    “ value”：“ the-setting-value”
}
```

有关设置，请参见本指南的 [](Configuration-Properties.html#dynamodb)

##### Azure KeyVault的秘密

CAS还可以使用Microsoft Azure的KeyVault Secrets来查找属性和设置。 通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-azure-keyvault</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#azure-keyvault-secrets)。

**重要事项**：Azure Key Vault中允许的名称模式为 `^[0-9a-zA-Z-]+ $`。 属性，其中包含 `。名称中的` `cas.some.property`），替换为 `。` 与 `-` 时存储在天青密钥保管库的设置（即 `CAS-一些属性`）。 该模块将为您处理转换。

有关设置，请参见本指南的 [](Configuration-Properties.html#azure-keyvault-secrets)

##### JDBC

CAS还能够使用关系数据库来查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-jdbc</artifactId>
     <version>${cas.version}</version>
</dependency>
```

默认情况下，预期的设置下一个被发现 `CAS_SETTINGS_TABLE` 包含字段： `ID`， `名` 和 `的值`。 要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#jdbc)。

##### 休息

CAS还能够使用REST API查找属性和设置。

通过WAR叠加中的以下依赖关系提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-rest</artifactId>
     <version>${cas.version}</version>
</dependency>
```

REST端点预计将 `映射` ，其中键为设置名称 ，值为设置值。 功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#rest)。

#### CAS服务器云配置

直接由CAS项目在此页面上上面提供的云配置模块也可以逐字地在 CAS服务器覆盖内使用。 请记住，这些模块的主要目的是简单地从源中 虽然它们在Spring Cloud Configuration服务器中激活时最主要且最有用，并且 设置为接受配置文件等，但是它们也可以在CAS服务器内部直接使用，从而可以在独立运行时 模式。 在这种情况下，所有 的配置源都将一起工作以检索设置，并且您当然可以根据需要混合和匹配。

#### 复合源

在某些情况下，您可能希望从多个环境存储库中提取配置数据。 为此，只需在配置服务器的应用程序属性或YAML文件中启用多个配置文件。 例如，如果要从Git存储库和SVN 存储库中提取配置数据，则可以为配置服务器设置以下属性。

```yml
春季：
  配置文件：
    活动：git，svn
  云：
    配置：
      服务器：
        svn：
          uri：file：/// path / to / svn / repo
          顺序：2
        git：
          uri：file： /// path / to / git / repo
          顺序：1
```

除了每个回购库指定一个URI外，您还可以指定一个 `阶` 属性。 `order` 属性使您可以为所有存储库 订单属性的数值越低，优先级越高 存储库的优先级顺序将有助于解决包含相同属性值的存储库之间的

#### 物业替代

配置服务器具有“替代”功能，该功能使操作员可以使用正常更改事件和挂钩向所有应用程序无法意外更改的应用程序 要声明覆盖，请将名称/值对的映射添加到 `spring.cloud.config.server.overrides`。

例如：

```yml
春季：
  云：
    配置：
      服务器：
        覆盖：
          foo：酒吧
```

这将导致CAS服务器（作为配置服务器的客户端）独立于其自己的配置 `foo = bar`

## 保护设置

要了解如何CAS敏感的设置都可以通过加密加以保护， [请参阅本指南](Configuration-Properties-Security.html)。

## 重新加载更改

要了解更多有关CAS如何允许您重新加载配置更改的信息，请 [本指南](Configuration-Management-Reload.html)。

## 集群部署

CAS使用 [Spring Cloud Bus](http://cloud.spring.io/spring-cloud-static/spring-cloud.html) 来管理分布式部署中的配置。 Spring Cloud Bus通过轻量级消息代理 然后可以将其用于广播状态 更改（例如，配置更改）或其他管理指令。

要了解如何CAS敏感的设置都可以通过加密加以保护， [请参阅本指南](Configuration-Management-Clustered.html)。
