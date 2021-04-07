---
layout: 违约
title: CAS - 无密码身份验证
category: 认证
---

# 无密码身份验证

无密码身份验证是 CAS 中的一种身份验证形式，其中密码采取可配置时间后过期的代币 形式。 使用此策略，用户需要一个标识符（即用户名），用于定位包含电子邮件和电话 号码等联系方式的用户记录 。 找到后，CAS 生成的令牌将通过配置的通知 策略（即电子邮件、短信等）发送给用户，然后用户 将令牌还给 CAS 以继续。 

<div class="alert alert-info"><strong>无魔法链接</strong><p>
目前，没有支持魔术链接，将删除的任务，提供令牌 
回到CAS允许用户自动进行。
此变体可能会在以后的版本中计算出来。</p></div>

为了成功实现此功能，需要建立配置，以联系持有符合无密码身份验证资格的用户记录的 帐户商店。 同样，CAS 必须配置以管理已发行的代币，以便执行在适当的数据存储中执行查找、 验证、过期或保存操作。

## 无密码变种

无密码身份验证也可以使用 [QR码身份验证](QRCode-Authentication.html)激活， 允许最终用户通过使用移动设备扫描QR码登录。

无密码身份验证也可以通过 [FIDO2 WebAuthn](../mfa/FIDO2-WebAuthn-Authentication.html) 实现，该允许用户在没有密码的情况下 验证其身份，并使用支持 FIDO2 的设备登录。

## 概述

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-无密码-网络流</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

## 账户商店

符合无密码身份验证条件的用户记录必须由 CAS 使用以下策略之一找到。 所有策略都可以 使用 CAS 设置进行配置，并根据配置值的存在进行激活。

### 简单

此策略提供链接到其联系方式的用户名的静态地图，如电子邮件或电话号码。 最好 用于测试和演示目的。 地图中的密钥被视为有资格进行身份验证的用户名，而该值可以是电子邮件 地址或电话号码，用于使用已发行的代币与用户联系。

### 蒙古德布

此策略允许用户在 MongoDb 中查找用户记录。 指定的 MongoDb 集合预计将携带类型 `无密码用户帐户` JSON 格式的对象。 要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持无密码蒙古</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 阿尔达普

此策略允许用户在 LDAP 目录中定位用户记录。 该记录预计将通过可配置属性携带用户的电话号码 或电子邮件地址。 要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持无密码 - ldap</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 杰森

此策略允许用户通过 JSON 资源查找用户记录，因此：

```json 
•
  "@class"："java.利用.LinkedhashMap"，
  "卡苏瑟"：{
    "@class"："org.apereo.cas.api.密码用户帐户"，
    "用户名"："用户名"："用户名"，
    "属性"：{
        "@class"："java.usil.LinkedhashMap"，
        "名称"："java.util.Arraylist"， ["价值"]]
    [，
    "多因素授权资格"："真实"，
    "授权授权资格"："真实"，
    "请求密码"：虚假
  =
}
```

### 槽的

此策略允许用户通过 Groovy 脚本查找用户记录。 脚本的主体可以定义为：

```groovy
导入组织.apereo.cas.api.*

def 运行 （对象[] args） [
    def 用户名 = args[0]
    def 记录器 = args[1]

    logger.info （"为用户 $username查找用户记录"）

    /*
     ...
     查找给定用户名的记录，并将结果返回 CAS。
     ...
    */

    def帐户=新的无密码用户帐户（）
    帐户。设置用户名（用户名）
    帐户。setEmail（"username@example.org"）
    帐户。 测试使用者）
    帐户.setPhone（"123-456-7890"） 
    帐户。 "。。。"） 
    帐户.集多因素授权资格（三国博利。FALSE）  
    帐户。设置要求密码（假）
    返回帐户
}
```

### 休息

此策略允许一个设计 REST 端点负责定位无密码用户记录。 成功执行终点  
将产生类似于以下响应机构：

```json
•
  "@class"："org.apereo.cas.api.无密码用户帐户"，
  "用户名"："用户名"，
  "电子邮件"："cas@example.org"，
  "电话"："123-456-7890"，
  "名称"："CASUser"，        
  "多因素授权资格"："FALSE"，  
  "授权认证资格"："FALSE"，  
  "请求密码"：虚假的，
  的"属性"：\"最后名称"："["，"。。。" •
}
```

### 习惯

您也可以使用以下豆定义定义自己的用户帐户商店，并通过实现 `无密码用户帐户商店`：

```java 
@Bean
公共无密码用户帐户存储无密码用户帐户商店（）{
    。。。
}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。

## 代币管理

以下策略定义了 CAS 如何管理发行的代币。

### 记忆

这是默认选项，其中代币使用具有可配置到期期的缓存保存在内存中。 不用说，此选项 不适合在 CAS 内部的聚类部署中，无法在 CAS 节点之间同步和复制令牌。

### 日本经济与经济、经济、经济

此策略允许使用关系数据库存储令牌并管理其到期策略。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持无密码 - jpa</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。

### 休息

此策略允许一个设计 REST 端点，负责完全管理令牌及其到期策略。 CAS 继续生成令牌，端点仅充当真实代币存储的外墙，以加密方式接收来自 CAS 的代币。

以下操作需要由终点支持：

| 赫特普方法 | 描述         | 参数          | 响应        |
| ----- | ---------- | ----------- | --------- |
| `获取`  | 为用户查找令牌。   | `用户名`       | 响应主体中的令牌。 |
| `删除`  | 删除用户的所有令牌。 | `用户名`       | 不适用       |
| `删除`  | 为用户删除单个令牌。 | `用户名`， `令牌` | 不适用       |
| `发布`  | 为用户保存令牌。   | `用户名`， `令牌` | 不适用       |

### 习惯

您也可以使用以下豆子定义定义您自己的代币管理商店，并通过实现 `无密码存储库`：

```java 
@Bean
公共无密码存储库无密码托肯存储库（）{
    。。。
}
```

[本指南](../configuration/Configuration-Management-Extensions.html) 了解有关如何将配置注册到 CAS 运行时间的更多信息。

### 消息传递 & 通知

可以通过短信、邮件等方式通知用户代币。 要了解有关可用选项的更多信息，请 [本指南](../notifications/SMS-Messaging-Configuration.html) 或 [本指南](../notifications/Sending-Email-Configuration.html)。

## 禁用无密码身份验证流

无密码身份验证可在每个用户的基础上有条件地禁用。 如果从帐户商店检索到的无密码帐户 带有用户，其 `请求Password` 设置为 `真实`，则无密码流（如上文所述代币生成等）将 禁用并跳过，转而支持更常见的 CAS 身份验证流，对用户进行密码挑战。 支持此行为可能取决于每个帐户商店的实现 。

## 多因素身份验证集成

无密码身份验证可以 与中科院多因素认证提供商</a>

集成。 在这种情况下， ，一旦 CAS 配置能够通过设置 或定位的无密码用户帐户 *符合多因素身份验证资格* 来支持此行为， CAS 将允许无密码身份验证跳过其自身 *预期的正常* 流（如上文所述的代币生成等），转而支持 CAS 中可用和定义的 多因素身份验证提供商。</p> 

这意味着，如果 [多因素身份验证提供商](../mfa/Configuring-Multifactor-Authentication.html) 被定义和激活，并定义 [多因子触发器](../mfa/Configuring-Multifactor-Authentication-Triggers.html) CAS 信号可用性和给定无密码用户的多因子流的资格，CAS 将跳过其正常的无密码身份验证流 ，转而支持请求的多因素身份验证提供商及其流。 如果没有多因素提供商 可用，或者如果没有触发器需要为已验证的无密码用户使用多因素身份验证，则无密码 身份验证流程将照常开始。

要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。



## 委托身份验证集成

无密码身份验证可与中科院授权认证</a>集成。 在这种情况下， 一旦 CAS 配置能够通过设置支持此行为，或者定位的无密码用户帐户被视为 *合格的授权身份验证* ， CAS 将允许无密码身份验证跳过其自己的 *预期的正常* 流（如上文所述的代币生成等），转而支持 CAS 中可能可用和定义的 授权身份验证。</p> 

这意味着，如果 [授权的身份验证提供商](../integration/Delegate-Authentication.html) 被定义和激活，CAS 将跳过其正常的无密码身份验证流 ，转而支持请求的多因素身份验证提供商及其流程。 如果没有授权的身份提供商 可用，无密码身份验证流程将照常启动。

无密码用户的委托身份验证提供商的选择 使用脚本进行处理。 脚本的定义如下：



```groovy
def运行（对象[]args）{
    定义无密码用户=args[0]
    def客户端=（集）args[1]
    d def http服务器要求=args[2]
    d def记录器=args[3]

    logger.info（"测试用户名 $passwordlessUser"）

    返回客户端[0]
}
```


通过的参数如下：

| 参数           | 描述                                |
| ------------ | --------------------------------- |
| `无密码用户`      | 表示 `无密码用户帐户的对象`。                  |
| `客户`         | 表示身份提供商配置集合的对象。                   |
| `赫特普·塞尔莱特要求` | 表示 http 请求的对象。                    |
| `记录`         | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |


脚本的结果可以 `无效` 跳过为用户委托的身份验证，也可以从可用的身份提供商 传递到脚本中进行选择。

要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#passwordless-authentication)。
