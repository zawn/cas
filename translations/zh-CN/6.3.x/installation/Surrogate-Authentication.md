---
layout: 违约
title: CAS - 代理认证
category: 认证
---

# 代理身份验证

代理身份验证（模拟），有时被称为网络</em>的 *sudo，是代表其他用户进行身份验证的能力。 </p>

本案的两个角色是：

1. 凭据在身份验证后得到验证的主要管理员用户。
2. 代理用户，由管理员选择，CAS将在凭据验证后切换到该用户，并且是链接到单个登录会话的代理用户。

模拟的示例使用案例包括：

1. 代表用户登录应用程序以执行和进行更改。
2. 为代表其他用户的应用程序解决令人烦恼的身份验证体验。

代理身份验证通过在 WAR 叠加中包括以下依赖项而启用：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-代理-网络流</artifactId>
    <version>${cas.version}</version>
</dependency>
```

## 帐户存储

以下帐户存储可以配置并用于查找授权给特定用户的代理。

### 静态的

代理帐户可以在 CAS 配置中静态定义。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### 杰森

与上述类似，除了代理帐户可以在外部 JSON 文件中定义，该文件通过 CAS 配置指定路径。 JSON 文件的语法应与以下片段匹配：

```json
{
    "卡瑟"：["史密斯"，"班德森"]，
    "管理员"：["史密斯"，"汤姆汉克斯"]
}
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### 阿尔达普

通过在 WAR 叠加中包括以下依赖项，支持代孕身份验证的 LDAP 支持：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 代理 - 认证 - ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```

也可以从 LDAP 实例中检索代理帐户。 此类帐户预计将在 LDAP 中为主要用户定义的配置属性中找到，该属性的价值可能会根据您自己选择进一步缩小授权代理帐户列表的常规表达模式进行检查。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### 库奇德布

沙发数据库对代理身份验证的支持通过在 WAR 叠加中包括以下依赖项来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-代理-认证-沙发</artifactId>
    <version>${cas.version}</version>
</dependency>
```

代理帐户也可以从库奇德布实例中检索。 默认情况下，这采取代理/委托键/值对的形式。 被授权为代理人的用户可能会多次被列出，以授权他们访问多个帐户。 此外，CouchDb 代理支持可能配置为使用配置文件属性，其中包含用户可能代理的 `配置文件` 属性的委托人列表。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#surrogate-authentication)。

### 京城

JDBC 对代理身份验证的支持通过在 WAR 叠加中包括以下依赖项而实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-代理-认证-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

除了通常的数据库设置外，此模式还需要两个 SQL 查询的规范：决定资格的帐户和能够检索 可以冒充给定管理员用户的帐户列表。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jdbc-surrogate-accounts)。

### 休息

通过在 WAR 叠加中包括以下依赖项，支持代理身份验证：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>套机服务器支持-代理-认证-休息</artifactId>
    <version>${cas.version}</version>
</dependency>
```

| 方法   | 描述                   | 参数          | 响应          |
| ---- | -------------------- | ----------- | ----------- |
| `获取` | 委托人是否可以作为代理帐户进行身份验证。 | `代孕人`， `主要` | `202`       |
| `获取` | 账户本金列表有资格冒充。         | `主要`        | 用户名的JSON列表。 |

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-surrogate-accounts)。

### 习惯

如果您想要设计自己的帐户商店，您可以遵循以下方法：

```java
包组织. 阿佩雷奥. 卡斯. 自定义;

@Configuration（"我的代理配置"）
@EnableConfigurationProperties（案例配置.class）
公共类"我的代理配置"=

    @Bean
    公共代理服务（）=
      。。。
    •

}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。

## 帐户选择

代孕用户选择可以通过以下方式进行。

### 预选

在这种情况下，代理用户身份是事先知道的，并在登录时使用特殊语法提供给 CAS。 输入凭据时，应使用以下语法：

```bash
[surrogate-userid][separator][primary-userid]
```

例如，如果您</code> `套管，并且需要切换到 <code>jsmith` 作为代理用户，则提供给 CAS 的凭据 ID 将 `jsmith+casuser` 分离器 `+` ，并且可以通过 CAS 配置进行更改。 当然，您需要提供自己的密码。

### 图形用户界面

在这种情况下，代理用户身份 *事先不知道* ，并且您希望从预先填充的列表中选择帐户。 输入凭据时，应使用以下语法：

```bash
[separator][primary-userid]
```

例如，如果您</code> `套管，并且您需要找到您可能需要切换的代理帐户，则提供给 CAS 的凭据 ID 将 <code>+casuser` 分离器 `+` ，并且可以通过 CAS 配置进行更改。 当然，您需要提供自己的密码。

## 会话到期

可以为模拟会话分配一个特定的到期策略，该政策将控制代理会话可能持续多长时间。 这意味着，一旦到期政策规定，作为冒名顶替一部分而设立的SSO会议将理所当然地消失。 建议您缩短到期时间（即 30 分钟），以避免可能的安全问题。

<div class="alert alert-info"><strong>记得</strong><p>
指定给模拟会议的到期政策预计将比分配给非代理会议的正常</i> 到期政策
<i>短 <i></i> 。 换句话说，如果控制单个登录会话的通常到期策略设置为持续
2 小时，则代理会话到期的时间预计小于或等于 2 小时。
</p></div>

## 代理属性

在成功代理身份验证事件后，以下属性将传回应用程序，以便检测模拟会话：

| 属性      | 指示               |
| ------- | ---------------- |
| `代理可`   | 布利安， 以指示会话是否被冒充。 |
| `代孕原则`  | 经验证并充当模拟器的管理员用户。 |
| `代理使用者` | 冒充的代理用户。         |

## 代理访问策略

每个代理帐户存储都能够确定执行授权规则的冒名顶替者列表。 此外，您可以在每个服务级别上定义是否授权应用程序利用代理身份验证。 只有在建立身份验证和 SSO 会话是模拟策略时，代理访问策略才会激活。

有关可用选项，请参阅下文。

### 属性

决定主用户是否标记了足够的属性和权利，允许模拟执行。 在下面的示例中，只有在经过身份验证的主要用户具有包含 `管理员`值的 `给定` 的属性时，才允许代理访问应用程序匹配 `测试` 。

示例服务定义如下：

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务"："测试"，
  "名称"："测试"，
  "id"：1，
  "访问战略"：{
    "@class"："org.apereo.cas.服务。服务。代理注册服务访问"，
    "代理"：真实，
    "启用"：真实，
    "成功"：真实，
    "代理要求属性"：{
      "@class"："java.util.HashMap"，
      "给定名称"："java.util.HashSet"，"管理员"]][
    ]
  [
]
```

### 槽的

决定是否允许主要用户通过外部 Groovy 脚本进行模拟。 示例服务文件如下：


```json
•
  "@class"： "org. apereo. cas. 服务. 注册服务"，
  "服务 Id"： "测试"，
  "名称"： "测试"，
  "id"： 1，
  "访问战略"： [
    "@class"： "org. apereo. cas. 服务. 格劳维苏罗盖特注册服务访问服务" ，
    "凹槽脚本"： "文件：/ 等 / 卡斯 / 康普 / 代理. groovy"
  [

```

此组件的配置有资格使用 [弹簧表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。 Groovy 脚本本身可设计为：

```groovy
导入java.利用。*

定义对象运行（最终对象。。。args）{
    def本金=args[0]
    d主要属性=args[1]
    d记录器=args[2]

    logger.info（"检查 $principal的模拟身份验证。。。"）

    决定是否允许冒名顶替返回真实。。。
    如果 （主。 等于 （"卡苏瑟"） {
        return true
    }
    记录器。
    返回虚假
}
```

通过的参数如下：

| 参数     | 描述                                |
| ------ | --------------------------------- |
| `主要`   | 主要/主要用户ID。                        |
| `主要属性` | 为主要用户收集的主要属性。                     |
| `记录`   | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |

## 代理审计

默认情况下，审计日志中会跟踪代理身份验证事件：

```
[] 世卫组织：（主要用户： [casuser]，代孕用户： [testuser]）
：ST-1-u_R_SyXJJlENS0fBLwpecNE https://example.app.edu
行动：SERVICE_TICKET_CREATED
应用：CAS
时间：9月11日星期一 12：55：07 MST 2017
客户端 IP 地址： 127.0.0.1
服务器 IP 地址： 127.0.1
[]

```

此外，失败和成功事件也可以通过短信和/或电子邮件传达给相关方。 要了解有关可用选项的更多信息，请 [本指南](../notifications/SMS-Messaging-Configuration.html) 或 [本指南](../notifications/Sending-Email-Configuration.html)。

## 休息协议

该功能扩展了 [CAS REST API](../protocol/REST-Protocol.html) 通信模型以代理身份验证， 允许 REST 凭据指定替代项并代表其他用户进行身份验证。 要激活 CAS REST API 的代理认证 ，您需要选择以下选项之一：

- 使用以下语法格式化凭证用户名：

```bash
[surrogate-userid][separator][primary-userid]
```

- 传递包含代理使用 `X 代理-委托` 的特殊请求标题。
