---
layout: 默认
title: CAS-属性发布同意书
category: 属性
---

# 属性同意

CAS提供了在属性释放时强制执行用户知情同意的功能。 实际上，这意味着在访问目标应用程序之前， 用户显示允许释放给应用程序的属性集合，并带有继续进行或拒绝释放这些属性的选项。 还存在其他选项，用于指示同意引擎应如何考虑属性释放策略中的基础更改。 如果在属性释放策略中未检测到任何更改，还可以为用户提供设置提醒的功能。

对存储在已配置存储库中的同意属性记录进行签名和加密。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-consent-webflow</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 行政端点

CAS提供了以下端点：

| 终点                 | 描述                                                                                                                                                |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------- |
| `attributeConsent` | 管理和控制 [属性同意决定](Attribute-Release-Consent.html)。 `GET` 操作将生成所有同意决策的列表。 甲 `DELETE` 与记录密钥ID操作将尝试移除和撤销注册的装置（即， `attributeConsent /{principal}/{id}`）。 |


## 属性选择

默认情况下，所有标记为释放的属性均符合许可条件。 要控制此过程，您可以定义一个同意策略，该策略指示执行同意属性选择的标准。

分配给每个服务的策略包括以下功能：

| 场地                      | 描述                            |
| ----------------------- | ----------------------------- |
| `excludeedAttributes`   | 从同意中排除指示的属性。                  |
| `includeOnlyAttributes` | 强制将指定的属性包括在内，前提是已解决属性。        |
| `地位`                    | 控制是否应激活对此服务的同意。 有关激活规则，请参见下文。 |

定义示例如下：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “描述”：“示例”，
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ReturnAllAttributeReleasePolicy”，
    “ consentPolicy”：{
      “ @class”：“ org.apereo.cas.services.consent。 DefaultRegisteredServiceConsentPolicy“，
      ” excludedAttributes“：[” java.util.LinkedHashSet“，[” test“]]，
      ” includeOnlyAttributes“：[” java.util.LinkedHashSet“，[” test“]]，
      ”状态“： “假”
    }
  }
}
```

## 激活

可以在全局级别和每个服务级别上控制属性同意激活。 默认情况下，启用 属性同意的全局激活规则，并禁用服务定义的同意策略规则。 的同意策略 `状态` 字段来覆盖全局规则，该字段接受以下值：

| 价值     | 描述                  |
| ------ | ------------------- |
| `错误的`  | 禁用同意策略，将覆盖全局配置。     |
| `真的`   | 启用了同意策略，将覆盖全局配置。    |
| `不明确的` | 未定义同意策略，将决策委派给全局配置。 |

注意，属性同意策略也可以链接在一起以组成多个策略。 每个策略都可以单独禁用或启用，并且 将用于确定属性同意的激活和选择。 属性同意策略的样本链如下：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “样品”，
  “名称”： “样品”，
  “ID”：100，
  “描述”：“示例”，
  “ attributeReleasePolicy”：{
    “ @class”：“ org.apereo.cas.services.ChainingAttributeReleasePolicy”，
    “策略”：[“ java.util.ArrayList”，
      [
        {
          “ @class” ：“” org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy“，
          ” allowedAttributes“：[” java.util.ArrayList“，[” cn“，” mail“，” sn“]]，
          ” consentPolicy“：{
            ” @class“：” org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy“，
            ” includeOnlyAttributes“：[” java.util.LinkedHashSet“，[” cn“]]，
            ” status“：” TRUE“
          }
        }，
        {
          “ @class”：“ org.apereo.cas.services.ReturnAllowedAttributeReleasePolicy”，
          “ allowedAttributes”：[“ java.util.ArrayList”，[“ displayName”]]，
          “ consentPolicy”：{
            “ @class”：“ org.apereo.cas.services.consent.DefaultRegisteredServiceConsentPolicy”，
            “ includeOnlyAttributes”：[“ java.util.LinkedHashSet”，[“ displayName”]]，
            “ sta tus”：“假”
          }
        }
      ]
    ]
  }
}
```

### 通过Groovy激活

可以使用外部Groovy脚本替换默认的同意激活策略，以确定请求 是否符合同意的条件。 脚本的路径是通过CAS配置属性定义的。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#attribute-consent)。

脚本本身可以这样设计：

```groovy
import org.apereo.cas.util.model.TriStateBoolean

def run（Object [] args）{
    def acceptEngine = args[0]
    def casProperties = args[1]
    def service = args[2]
    defregisteredService = args[3]
    def身份验证= args[4]
    def requestContext = args[5]
    def logger = args[6]

    logger.debug（“ ${registeredService.name}激活同意”）
    返回true;
}
```

以下参数传递到脚本：

| 范围               | 描述                                    |
| ---------------- | ------------------------------------- |
| `同意引擎`           | 对 `ConsentEngine` 对象的引用。              |
| `casProperties`  | 对从属性源加载的CAS配置属性的引用。                   |
| `服务`             | 代表请求应用程序的 `服务`                        |
| `已注册的服务`         | `RegisteredService` 对象代表注册表中的服务定义。    |
| `验证`             | 表示活动身份验证事务的 `身份验证`                    |
| `requestContext` | 表示Spring Webflow `RequestContext`的对象。 |
| `记录器`            | 负责发布日志消息的对象，例如 `logger.info（...）`。    |

该脚本应返回 `true` 或 `false` 以确定是否需要同意。

## 贮存

可以使用以下选项之一存储并记住用户的同意决定。

### JSON格式

这是默认选项，对于演示和测试目的最有用。 同意决策均 ，该静态JSON资源通过设置将其路径告知CAS。

记录示例如下：

```json
{
   “ID”：1000，
   “主要”： “casuser”，
   “服务”： “https://google.com”，
   “createdDate”：[2017年，7，10，14，10，17] ，
   “选项”：“ ATTRIBUTE_NAME”，
   “提醒”：14、6
   “ reminderTimeUnit”：“ DAYS”，
   “属性”：“ ...”
}
```

以下字段可用：

| 场地       | 描述                                                                  |
| -------- | ------------------------------------------------------------------- |
| `ID`     | 现有记录的有效数值。                                                          |
| `主要的`    | 经过身份验证的用户ID。                                                        |
| `服务`     | 属性将要释放到的目标应用程序URL。                                                  |
| `创建日期`   | 决策记录的日期/时间。                                                         |
| `选项`     | 指示如何确定此应用程序的属性更改。 （即 `ATTRIBUTE_NAME`， `ATTRIBUTE_VALUE`， `ALWAYS`） |
| `提醒`     | 指示在未找到更改的情况下将提醒用户再次同意的期限。                                           |
| `提醒时间单位` | 提醒时间单元（即 `个月`， `天`， `小时`，等）。                                        |
| `属性`     | 此应用程序的属性名称的Base64，经过签名和加密。                                          |

`选项` 有效值包括：

| 场地                | 描述                                                           |
| ----------------- | ------------------------------------------------------------ |
| `ATTRIBUTE_NAME`  | 例如，在从发行包中添加或删除属性的情况下，如果任何属性名称发生更改，请征得同意。 如果更改现有属性的值，则忽略“同意”。 |
| `ATTRIBUTE_VALUE` | 与上面相同，不同之处在于属性值也要考虑并在更改后触发同意。                                |
| `总是`              | 无论更改或上下文如何，请始终征求同意。                                          |

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#json-attribute-consent)。

### Groovy

同意操作可以通过Groovy脚本处理，该脚本的路径通过设置传给CAS。

该脚本可以设计为：

```groovy
import java.util。*
import org.apereo.cas.consent。*

def设置<ConsentDecision> read（final Object ... args）{
    def acceptDecisions = args[0]
    def logger = args[1]
...
    返回null；
}

def布尔型写（最终对象... args）{
    def acceptDecision = args[0]
    def logger = args[1]
...
    返回true；
}

def布尔型delete（final Object ... args）{
    def DecisionId = args[0]
    def logger = args[1]
...
    返回true；
}

def布尔型deleteAll（final Object ... args）{
    def主体= args[0]
    def logger = args[1]
...
    返回true；
}
```


### JDBC

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-consent-jdbc</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jpa-attribute-consent)。

### MongoDb

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-consent-mongo</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-attribute-consent)。

### 雷迪斯

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-consent-redis</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-attribute-consent)。

### CouchDb

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-consent-couchdb</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-attribute-consent)。


### 休息

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持同意休息</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-attribute-consent)。

端点必须设计为接受/处理 `application / json`。

| 手术        | 方法   | 数据                            | 预期回应                |
| --------- | ---- | ----------------------------- | ------------------- |
| 找到同意决定    | `得到` | `服务`， `主要` 作为标题               | `200`。 同意决定对象在体内。   |
| 找到用户的同意决定 | `得到` | `主体` 作为标题                     | `200`。 同意决定在体内成为对象。 |
| 找到所有同意决定  | `得到` | 不适用                           | `200`。 同意决定在体内成为对象。 |
| 商店同意决定    | `邮政` | 体内同意决定对象                      | `200`。              |
| 删除同意决定    | `删除` | `/<decisionId>` 附加到URL的 | `200`。              |
| 删除同意决定    | `删除` | `主体` 作为头                      | `200`。              |

传输中的同意决策对象将并且必须与上面的JSON结构匹配。


### LDAP

同意决定可以存储在LDAP用户对象上。 这些决策将序列化为JSON，并一一存储在多值字符串属性中。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-consent-ldap</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ldap-attribute-consent)。


### 风俗

您还可以将自己的用于属性同意管理的实现注入到CAS中，该实现本身将处理存储同意决策等。 为此，您将需要设计一个与以下内容大致匹配的配置类：

```java
包org.apereo.cas.consent;

@Configuration（“ MyConfiguration”）
@EnableConfigurationProperties（CasConfigurationProperties.class）
公共类MyConfiguration {

    @Bean
    公共ConsentRepository acceptRepository（）{
...
    }
}
```

[请参阅本指南](../configuration/Configuration-Management-Extensions.html) 以了解有关如何将配置注册到CAS运行时的更多信息。
