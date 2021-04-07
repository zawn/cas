---
layout: 默认
title: CAS-Web Flow可接受的使用策略
category: Webflow管理
---

# 可接受的使用政策

也称为 *使用条款* 或 *EULA*，CAS提供了允许用户在继续应用程序之前接受使用策略的能力。 此功能的生产级部署将需要对流进行修改，以便将通过诸如LDAP或JDBC之类的外部存储机制来处理

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

通过修改 `src / main / resources / templates / casAcceptableUsagePolicyView.html`定义策略。 见 [本指南](../ux/User-Interface-Customization.html) 更多地了解用户接口的定制。 请注意，此处的视图应具有对已解析的主体和属性的完全访问权限；如果您希望动态更改页面以显示不同的文本，则 

<div class="alert alert-info"><strong>Webflow序列</strong><p>请记住，在CAS已建立身份验证主体的成功身份验证事件之后，可接受的使用策略将执行
 
策略记录与所标识的用户记录紧密相关。 在身份验证事件
之前实现此功能将需要对CAS webflow进行相当大的修改，以及存储和记忆决策
替代方法，例如cookie或浏览器存储等。</p></div>

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

## 每项服务

可以基于每种服务禁用和跳过可接受的使用策略：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ https://app.example.org”，
  “ name”：“ Example”，
  “ id”：
  “ acceptableUsagePolicy”：
  {
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAcceptableUsagePolicy”，
    “ enabled”：true，
    “ messageCode”：“ example.code”，
    “ text”： “示例文字”
  }
}
```

分配给每个服务的策略包括以下功能：

| 场地            | 描述                         |
| ------------- | -------------------------- |
| `已启用`         | 控制此服务的策略是否有效。 默认值为 `true`。 |
| `messageCode` | 链接到包含实际策略文本的CAS语言包的策略语言代码。 |
| `文本`          | 应为此应用程序显示的策略文本。            |

## 储存机制

使用策略用户决策通过以下方式存储和记忆。

在几乎所有存储策略中，CAS都允许部署者 `布尔` 属性来检测当前用户的策略选择。 必须使用 [CAS属性解析策略](../integration/Attribute-Resolution.html)解析属性。 如果属性包含的值为 `false`，则CAS将尝试 请求策略接受。 接受策略后，结果将存储回存储中。

### 默认

默认情况下，记住用户选择的任务默认情况下保留在内存中，并且在 容器重新启动时和/或在群集部署中将丢失。 此选项仅在开发，测试 和演示期间有用，而根本不适合生产。

可以将默认存储机制的范围从GLOBAL（如上所述）的默认值调整为 AUTHENTICATION，这将导致用户在每次身份验证事件期间必须同意该策略。 票证cookie的现有票证授予访问权限时，用户将不必同意该策略。

### Groovy

或者，可以将CAS配置为使用Groovy脚本来验证 并存储结果。 该脚本应符合以下条件：

```groovy
导入org.apereo.cas.authentication.principal。*
导入org.apereo.cas.authentication。*
导入org.apereo.cas.util。*
导入org.apereo.cas.aup。*
导入org.springframework .webflow.execution。*

def verify（Object [] args）{
    def requestContext = args[0]
    def凭证= args[1]
    def applicationContext = args[2]
    def主体= args[3]
    def logger = args[4]
...
    if（policyAccepted（））{
        返回AcceptableUsagePolicyStatus.accepted（principal）
    }
    返回AcceptableUsagePolicyStatus.denied（principal）
}

def commit（Object [] args）{
     def requestContext = args[0]
     def凭证= args[1]
     def applicationContext = args[2]
     def主体= args[3]
     def logger = args[4]
...
     return true
}

/ *
    了特殊的回调函数
    作为重写，以 
    的“ AcceptableUsagePolicyTerms”对象返回给CAS以重新
    可接受的使用策略流。
* /
def fetch（Object [] args）{
    def requestContext = args[0]
    def凭据= args[1]
    def applicationContext = args[2]
    def主体= args[3]
    def logger = args[4]

    ...    

    返回AcceptableUsagePolicyTerms。 builder（）
            .defaultText（“ Hello，World”）
            .code（AcceptableUsagePolicyTerms.CODE）
            .build（）;
}
```

传递的参数如下：

| 范围                   | 描述                                    |
| -------------------- | ------------------------------------- |
| `requestContext`     | 表示Spring Webflow `RequestContext`的对象。 |
| `凭据`                 | 表示认证 `凭证`的对象。                         |
| `applicationContext` | 表示Spring `ApplicationContext`的对象。     |
| `主要的`                | 表示已验证的 `主体`的对象。                       |
| `记录器`                | 负责发布日志消息的对象，例如 `logger.info（...）`。    |

### LDAP

或者，可以将CAS配置为使用LDAP作为存储机制。 接受该策略后，结果将存储回LDAP，并通过同一属性记住 通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-ldap</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-1)。

### MongoDb

可以将CAS配置为使用MongoDb实例作为存储机制。 接受该策略后，采用者应提供一个集合名称，其中 决策，并且假定该文档包含 `用户名` 列以及与定义的AUP属性名匹配的一个。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-mongo</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 雷迪斯

可以将CAS配置为使用Redis实例作为存储机制。 决策映射到CAS用户名和指定的AUP属性名称的组合。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-redis</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### CouchDb

可以将CAS配置为使用CouchDb实例作为存储机制。 接受该策略后，采用者应提供一个集合名称，其中 决策，并且假定该文档包含 `用户名` 列以及与定义的AUP属性名匹配的一个。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-couchdb</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### Couchbase

可以将CAS配置为使用Couchbase实例作为存储机制。 接受该策略后，将 决策保存在文档中，该文档包含 `用户名` 列和AUP属性名称以及决策结果。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-couchbase</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### JDBC

可以将CAS配置为使用数据库作为存储机制。 接受该策略后，采用者应提供一个表名，其中 决策，并假定该表包含 `用户名` 列以及与定义的AUP属性名匹配的一个。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-jdbc</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 休息

可以将CAS配置为使用REST API作为存储机制。 接受该策略后， `` 接受该策略的用户名2参数来联系API的 预期的响应状态代码是 `200`。

`${endpoint}/ policy` 处的API端点以获取适当的策略条款。 `用户名` 和 `语言环境` 参数联系该API，并且预期的响应状态代码为 `200`。 响应 输出主体应为 `AcceptableUsagePolicyTerms` 的实例，如下所示：

```json
{
  “ @class”：“ org.apereo.cas.aup.AcceptableUsagePolicyTerms”，
  “ code”：“ screen.aup.policyterms.some.key”，
  “ defaultText”：“默认策略文本”
}
```

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-aup-rest</artifactId>
  <version>${cas.version}</version>
</dependency>
```


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 风俗

如果您希望设计自己的存储机制，则可以采用以下方法：

```java
包org.apereo.cas.custom;

@Configuration（“ myUsagePolicyConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
public class MyUsagePolicyConfiguration {

    @Bean
    public AcceptableUsagePolicyRepository acceptUsagePolicyRepository（）{
...
    }

}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。

## 政策条款

上面概述的存储选项也可用于获取可接受的使用策略 ，并将其传递到适当的视图，以在属性 `aupPolicy`下显示和接受。 策略条款可以引用在CAS语言束中找到的特定消息代码， 或可以包含用于逐字显示的默认策略文本。

除非存储选项覆盖并专门化此功能，否则获取策略项 默认行为将基于CAS属性中定义的单值属性，该属性通常可能指示用户状态或成员身份。 该属性值被附加到语言代码 `screen.aup.policyterms` ，然后允许CAS从语言包中 如果在CAS语言包中没有这样的密钥，则将显示在同一语言密钥下找到

当然，对于来自相关来源的已解析的经过身份验证的主体，必须具有定义的属性。

例如，如果将策略条款属性定义为 `状态` ，值为 `开发人员`，则用于承载策略文本的预期语言 `screen.aup.policyterms.developer =<p>针对开发人员的策略</p>`。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。
