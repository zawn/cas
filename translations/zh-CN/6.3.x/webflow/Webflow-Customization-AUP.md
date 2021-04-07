---
layout: 违约
title: CAS - 网络流可接受的使用策略
category: 网络流管理
---

# 可接受的使用策略

CAS 也称为 *使用条款* 或 *EULA*，它提供了允许用户在进入应用程序之前接受使用策略的能力。 此功能的生产级部署将需要修改流程，以便检索 和/或接受策略将通过外部存储机制（如 LDAP 或 JDBC）进行处理。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-上-网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

通过修改 `src/main/resources/templates/casAcceptableUsagePolicyView.html`来定制策略。 请参阅本指南 [](../ux/User-Interface-Customization.html) 了解有关用户界面定制的更多了解。 请注意，此处的视图应完全访问已解决的本金和属性，如果您希望动态更改页面以显示不同的文本，则 等。

<div class="alert alert-info"><strong>网流序列</strong><p>请记住，可接受的使用策略在 CAS 已建立身份验证本金的成功认证事件后执行
，因为 
策略记录与已识别的用户记录紧密相关联。 在认证事件之前实施此功能
需要对 CAS 网络流进行相当繁重的修改，以及存储和记住决策的替代方法，
如 Cookie 或浏览器存储等。</p></div>

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

## 每种服务

可按服务禁用和跳过可接受的使用策略：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："https://app.example.org"，
  "名称"："示例"，
  "id"： 1，
  "可接受的使用政策"：
  =
    "@class"："org.apereo.cas.服务.默认注册服务可接受使用政策"，
    "启用"：真实、
    "消息代码"："示例代码"，
    "文本"："示例文本"
  =

```

分配给每个服务的策略包括以下功能：

| 田      | 描述                             |
| ------ | ------------------------------ |
| `启用`   | 控制此服务的政策是否为活动/非活动。 默认值 `真实`。   |
| `消息代码` | 链接到带有实际策略文本的 CAS 语言捆绑包的政策语言代码。 |
| `发短信`  | 应显示此应用程序的政策文本。                 |

## 存储机制

使用策略用户决策通过以下方式存储和记住。

在几乎所有存储策略中，CAS 允许部署器 通过 CAS 单值 `布尔` 属性检测当前用户的政策选择。 属性必须使用 [CAS属性解析策略](../integration/Attribute-Resolution.html)来解决。 如果该属性包含 `虚假`值，CAS 将尝试 要求政策接受。 接受保单后，结果将存储回存储。

### 违约

默认情况下，记住用户选择的任务默认保留在内存中，并在 容器重新启动和/或聚类部署时丢失。 此选项仅在开发、测试 和演示过程中有用，根本不适合生产。

默认存储机制的范围可以从 GLOBAL 的默认值（上文所述）调整为 身份验证，这将导致用户在每次身份验证事件期间必须同意该策略。 当 CAS 根据现有门票授予 票 cookie 授予访问权限时，用户将不必同意该策略。

### 槽的

或者，CAS 可以配置为使用 Groovy 脚本来验证政策和存储结果的状态 。 脚本应匹配以下内容：

```groovy
进口组织.apereo.cas.认证.
进口组织.apereo.cas.认证.*
进口组织.apereo.cas.
进口组织.apereo.cas.aup.*
进口组织.弹簧框架 .webflow.执行.*

定义验证（对象[]args）{
    定义请求机密=args[0]
    定义凭据=args[1]
    def应用程序机密=args[2]
    def本金=args[3]
    去记录器=args[4]
    ...
    如果（政策接受）{
        返回可接受的使用政策状态。接受（本金）
    =
    返回可接受的使用政策状态。拒绝（原则）
=

提交（对象[] args）{
     def请求机密=args[0]
     除卡凭据=args[1]
     def应用程序本金=args[2]
     def本金=args[3]
     定义记录器=args[4]
     ...
     返回真实
=

/*
    实施特殊回调功能
    作为覆盖项，将"可接受使用策略" 
    对象返回 CAS，以重新用于可接受的使用策略流
    。
*/
德获取（对象[]args）{
    定义请求通信=args[0]
    定义凭据=args[1]
    def应用程序本金=args[2]
    def本金=args[3]
    d伐木机=args[4]

    ...    

    返回可接受的使用周期。建设者（）
            .默认Text（"你好， 世界"）
            .代码（可接受的使用周期。CODE）
            。构建（）：
}
```

通过的参数如下：

| 参数      | 描述                                |
| ------- | --------------------------------- |
| `请求康德信` | 代表春季网络流的对象 `请求信`。                 |
| `凭据`    | 表示认证的对象 `凭据`。                     |
| `应用康德信` | 代表春季 `应用信的对象`。                    |
| `主要`    | 表示经过验证的 `主体`的对象。                  |
| `记录`    | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |

### 阿尔达普

或者，CAS 可以配置为使用 LDAP 作为存储机制。 接受保单后，结果将存储回 LDAP，并通过相同的属性 记住。 支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - aup - ldap</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-1)。

### 蒙古德布

CAS 可以配置为使用 MongoDb 实例作为存储机制。 在接受保单后，采用者应提供保存 决策的集合名称，并假定文档包含 `用户名` 列以及与定义的 AUP 属性名称匹配的用户名。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 阿普 - 蒙戈</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 雷迪斯

CAS 可以配置为使用 Redis 实例作为存储机制。 决策映射为 CAS 用户名和指定 AUP 属性名称的组合。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 阿普 - 雷迪斯</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 库奇德布

CAS 可以配置为使用沙发数据库实例作为存储机制。 在接受保单后，采用者应提供保存 决策的集合名称，并假定文档包含 `用户名` 列以及与定义的 AUP 属性名称匹配的用户名。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 阿普 - 库奇德布</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 沙发基地

CAS 可以配置为使用沙发底座实例作为存储机制。 接受保单后， 决定将保存在具有 `用户名` 列和 AUP 属性名称的文件中，该文档具有决策结果。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - aup - 沙发基地</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 京城

CAS 可以配置为使用数据库作为存储机制。 接受保单后，采用者应提供保留 决定的表名，并假定该表包含 `用户名` 列以及与定义的 AUP 属性名称匹配的名称。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - aup - jdbc</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 休息

CAS 可配置为使用 REST API 作为存储机制。 接受保单后， 联系 API，传递已接受保单的 `用户名` 参数。 预期响应状态代码 `200`。

此外，中科院将援引 `${endpoint}/政策` 的API终点来获取适当的政策条款。 通过 `用户名` 和 `本地` 参数联系 API，预期响应状态代码 `200`。 产出机构 响应预计将是 `可接受使用政策周期` 实例：

```json
•
  "@class"："org.apereo.cas.aup.可接受使用策略"，
  "代码"："屏幕.aup.政策图.key"，
  "默认文本"："默认策略文本"
}
```

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 上休息</artifactId>
  <version>${cas.version}</version>
</dependency>
```


要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。

### 习惯

如果您希望设计自己的存储机制，您可以采用以下方法：

```java
包组织. 阿佩雷奥. 卡斯. 自定义;

@Configuration（"我的使用政策配置"）
@EnableConfigurationProperties（配置.class）
公共类的"使用政策配置"=

    @Bean
    公共可接受的使用政策配置可接受的使用政策配置（）=
      。。。
    •

}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。

## 政策条款

上述存储选项也可用于获取可接受的使用策略 ，并将其传递给适当的视图，以显示和接受根据属性 `超政策`。 策略术语可以引用 CAS 语言捆绑包中找到的特定消息代码， 也可以包含用于逐字显示的默认策略文本。

除非存储选项覆盖并专门化此能力，否则 获取策略条款的默认行为基于 CAS 属性中定义的单个值属性，该属性通常可能表示用户状态或会员资格。 属性值附加到 `屏幕的语言代码.aup.政策策略` ，然后允许 CAS 从语言捆绑包中查找特定 策略文本。 如果 CAS 语言捆绑包中没有此类密钥，则将显示在同一语言密钥下找到的默认策略文本 。

当然，必须从相关来源为已解决的经过验证的委托人提供定义属性。

例如，如果策略术语属性定义为 `状态` 与 `开发人员`值，则携带策略文本的预期语言 代码将 `</p>`&lt;p&gt;屏幕。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptable-usage-policy)。
