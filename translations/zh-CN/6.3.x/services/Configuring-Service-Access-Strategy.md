---
layout: 默认
title: CAS-配置服务访问策略
category: 服务
---

# 配置服务访问策略

注册服务的访问策略可提供对服务授权规则的细粒度控制。 它描述了是否允许该服务使用CAS服务器，是否允许参与 单次登录身份验证等。 此外，可以将其配置为要求在授予访问权限之前必须存在的 这种行为使您可以 各种属性，并定义将要制定的规则，并在来自应用程序的身份验证请求到达时对

## 默认策略

默认策略允许使用以下属性配置服务：

| 场地                     | 描述                                                                                                                                             |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| `已启用`                  | 用于切换条目是否处于活动状态的标志；禁用的条目会产生与不存在的条目等效的行为。                                                                                                        |
| `ssoEnabled`           | 设置为 `false` 可以强制用户对服务进行身份验证，而不管协议标志如何（例如 `renew = true`）。                                                                                      |
| `requiredAttributes`   | 必需的主要属性名称以及每个属性的值集的 `映射` 这些属性 **MUST** 可用于验证主体和解决之前CAS可以进行，提供从中科院的角度基于角色的访问控制的选项。 如果未提供必需的属性，则将完全忽略该检查。                                        |
| `requireAllAttributes` | 进行切换以控制所需属性的行为的标志。 默认值为 `true`，这意味着必须存在所有必需的属性名称。 否则，至少一个匹配的属性名称就足够了。 请注意，此标志仅控制必须显示哪些属性 **名称**。 如果属性名称满足CAS配置，则在下一步中，至少需要一个匹配的属性值，访问策略才能成功进行。 |
| `未经授权的重定向网址`           | 在不允许访问服务的情况下，用于重定向流的可选URL。                                                                                                                     |
| `不区分大小写`               | 指示是否应以不区分大小写的方式完成对必需属性值的匹配。 默认值为 `false`                                                                                                       |
| `rejectedAttributes`   | 拒绝的主要属性名称以及每个属性的值集的 `映射` 这些属性 **绝不能** ，以便可以授予访问权限。 如果未定义，则将完全忽略该检查。                                                                            |

<div class="alert alert-info"><strong>我们对大小写敏感吗？</strong><p>请注意，主体/必需属性 <strong>名称</strong> 比较区分大小写
 任何单独的属性名称都必须完全匹配。</p></div>

<div class="alert alert-info"><strong>发布的属性</strong><p>请注意，如果将CAS服务器配置为在发布时缓存属性，则还必须将所有必需的属性发布给依赖方。 <a href="../integration/Attribute-Release.html">有关属性释放和过滤器的更多信息，请参见本指南</a></p></div>

### 例子

以下示例演示了CAS的访问策略实施功能。

#### 禁用服务访问

服务不允许使用CAS：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ enabled”：false，
    “ ssoEnabled”：true
  }
}
```

#### 强制属性

要访问该服务，主体必须具有 `cn` 属性，其值为 `admin` **AND** a `namedName` 属性，其值为 `Administrator`：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ ssoEnabled”：true，
    “ requiredAttributes”：{
      “ @class”：“ java.util.HashMap “，
      ” cn“：[” java.util.HashSet“，[” admin“]]，
      ” givenName“：[” java.util.HashSet“，[” Administrator“]]
    }
  }
}
```

要访问该服务，主体必须具有 `cn` 属性，其值为 `admin` **或** a `namedName` 属性，其值为 `Administrator`：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ ssoEnabled”：true，
    “ requireAllAttributes”：false，
    “ requiredAttributes”：{
      “ @class” ：“ java.util.HashMap”，
      “ cn”：[“ java.util.HashSet”，[“ admin”]]，
      “ givenName”：[“ java.util.HashSet”，[“管理员”]]
    }
  }
}
```

访问服务，委托人必须具有 `CN` 属性，其值是任意的 `管理员`， `管理员` 或 `TheAdmin`。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ ssoEnabled”：true，
    “ requiredAttributes”：{
      “ @class”：“ java.util.HashMap “，
      ” cn“：[” java.util.HashSet“，[” admin“，” Admin“，” TheAdmin“]]
    }
  }
}
```

#### 静态未经授权的重定向URL

如果主体不为 ** 具有包含值 `超级用户``cn` 属性，则拒绝服务访问。 如果是这样，则 会将用户重定向到 `https://www.github.com`。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ unauthorizedRedirectUrl”：“ https://www.github.com”，
    “ requiredAttributes”：{
      “ @class”：“ java。 util.HashMap“，
      ” cn“：[” java.util.HashSet“，[”超级用户“]]
    }
  }
}
```

#### 动态未经授权的重定向URL

如果主体不为 ** 具有包含值 `超级用户``cn` 属性，则拒绝服务访问。 如果是这样，将根据指定的Groovy脚本的结果动态确定

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ unauthorizedRedirectUrl”：“ file：/etc/cas/config/unauthz-redirect-url.groovy”，
    “ requiredAttributes”：{
      “ @class“：” java.util.HashMap“，
      ” cn“：[” java.util.HashSet“，[”超级用户“]]
    }
  }
}
```

脚本本身将采用以下形式：

```groovy
导入org.apereo.cas。*
导入org.apereo.cas.web.support。*
导入java.util。*
导入java.net。*
导入org.apereo.cas.authentication。*

def URI运行（最终对象... args）{
    def registeredService = args[0]
    def requestContext = args[1]
    def applicationContext = args[2]
    def logger = args[3]

    logger.info（“重定向到某个地方，正在处理[{}]”，已注册服务。名称）
    / **
     *发生了...
     * /
    返回新的URI（“ https://www.github.com”）;
}
```

脚本提供了以下参数：

| 场地                   | 描述                                    |
| -------------------- | ------------------------------------- |
| `已注册的服务`             | 代表注册表中匹配的注册服务的对象。                     |
| `requestContext`     | 表示Spring Webflow `RequestContext`的对象。 |
| `applicationContext` | 表示Spring `ApplicationContext`的对象。     |
| `记录器`                | 负责发布日志消息的对象，例如 `logger.info（...）`。    |

#### 强制执行组合属性条件

访问服务，委托人必须具有 `CN` 属性，其值是任意的 `管理员`， `管理员` 或 `TheAdmin`， **OR** 的主体必须具有 `构件` 属性，其值是任意的 `管理员`， `ADMINGROUP` 或 `的工作人员`。


```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ requireAllAttributes”：false，
    “ ssoEnabled”：true，
    “ requiredAttributes”：{
      “ @class” ：“” java.util.HashMap“，
      ” cn“：[” java.util.HashSet“，[” admin“，” Admin“，” TheAdmin“]]，
      ”成员“：[” java.util.HashSet “，[” admins“，” adminGroup“，” staff“]]
    }
  }
}
```

#### 强制使用不具备的属性

访问服务，委托人必须具有 `CN` 属性，其值是任意的 `管理员`， `管理员` 或 `TheAdmin`， 或主体必须具有 `构件` 属性，其值是任意的 `管理员`， `adminGroup` 或 `员工`。 主体 也必须不具有其值与模式 `deny。+`相匹配的属性“ role”。


```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ testId”，
  “ name”：“ testId”，
  “ id”：
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ requireAllAttributes”：false，
    “ ssoEnabled”：true，
    “ requiredAttributes”：{
      “ @class” ：“” java.util.HashMap“，
      ” cn“：[” java.util.HashSet“，[” admin“，” Admin“，” TheAdmin“]]，
      ”成员“：[” java.util.HashSet “，[” admins“，” adminGroup“，” staff“]]
    }，
    ” rejectedAttributes“：{
      ” @class“：” java.util.HashMap“，
      ” role“：[” java.util。 HashSet”，[“ deny。+”]]
    }
  }
}
```

## 基于时间

基于时间的访问策略是默认设置的扩展，此外， 允许用户配置具有以下属性的服务：

| 场地                 | 描述                                                        |
| ------------------ | --------------------------------------------------------- |
| `startingDateTime` | 指示可以授予服务访问权的起始日期/时间。  （即 `2015-10-11T09：55：16.552-07：00`） |
| `结尾日期时间`           | 指示可以授予服务访问权的结束日期/时间。  （即 `2015-10-20T09：55：16.552-07：00`） |

服务访问权限仅在 `开始日期时间` 和 `结束日期时间`：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“ test”，
  “ id”：62，
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.services.TimeBasedRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ ssoEnabled”：true，
    “ unauthorizedRedirectUrl”：“ https：// www。 github.com“，
    ” startingDateTime“：” 2015-11-01T13：19：54.132-07：00“，
    ” endingDateTime“：” 2015-11-10T13：19：54.248-07：00“
  }
}
```

## 远端端点

此策略是默认策略的扩展，默认情况下， 允许您配置具有以下属性的服务：

| 场地                      | 描述                      |
| ----------------------- | ----------------------- |
| `EndpointUrl`           | 从CAS接收对身份验证的主体的授权请求的端点。 |
| `acceptedResponseCodes` | 逗号分隔的响应代码被认为已接受服务访问。    |

该策略的目的是确保远程端点可以通过 接受CAS认证主体作为 `GET` 请求的url参数的0来做出服务访问决策。 然后将端点返回的响应代码 与策略设置进行比较，如果找到匹配项，则授予访问权限。

基于响应代码授权服务访问的远程端点访问策略：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ id”：
  “ accessStrategy”：{
    “ @ class”：“ org.apereo.cas.services.RemoteEndpointServiceAccessStrategy”，
    “ endpointUrl”：“ https://somewhere.example.org”，
    “ acceptableResponseCodes”：“ 200,202”
  }
}
```

## Groovy

此策略委派给Groovy脚本，以在运行时动态决定CAS请求的访问规则：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ id”：
  “ accessStrategy”：{
    “ @类”：“ org.apereo.cas.services.GroovyRegisteredServiceAccessStrategy”，
    “ groovyScript”：“ file：///etc/cas/config/access-strategy.groovy”
  }
}
```

通过在必要时覆盖所需的操作，可以将脚本本身设计为：

```groovy
import org.apereo.cas.services。*
import java.util。*

类GroovyRegisteredAccessStrategy扩展DefaultRegisteredServiceAccessStrategy {

    boolean isServiceAccessAllowed（）{
...
    }


    boolean isServiceAccessAllowedForSso（）{
...
    }


    boolean doPrincipalAttributesAllowServiceAccess（字符串Principal，Map<String, Object> 属性）{
...
    }
}
```

该组件的配置符合使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法的条件。 请参阅CAS API文档以了解有关操作和预期行为的更多信息。

## 石斑鱼

通过在WAR覆盖中包括以下依赖项来启用石斑鱼访问策略：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-</artifactId>
  <version>${cas.version}</version>
</dependency>
```

此访问策略尝试为CAS主体 [Grouper](https://incommon.org/software/grouper/) 返回的组被收集为CAS属性，并针对服务访问所需的属性列表进行检查。

可以使用以下属性：

| 场地           | 描述                         | 价值观                                                        |
| ------------ | -------------------------- | ---------------------------------------------------------- |
| `groupField` | 将组转换为CAS属性时使用的Grouper组的属性。 | `NAME`， `EXTENSION`， `DISPLAY_NAME` 或 `DISPLAY_EXTENSION`。 |

您还需要确保 `grouper.client.properties` 在类路径上可用（即 `src / main / resources`） 具有以下配置的属性：

```properties
grouperClient.webService.url = http://grouper.example.com/grouper-ws/servicesRest
grouperClient.webService.login = banderson
grouperClient.webService.password =密码
```

基于群组的显示扩展名的群组访问策略：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“ test”，
  “ id”：62，
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.grouper.services.GrouperRegisteredServiceAccessStrategy”，
    “ enabled”：true，
    “ ssoEnabled”：true，
    “ requireAllAttributes”：true，
    “ requiredAttributes “：{
      ” @class“：” java.util.HashMap“，
      ” grouperAttributes“：[” java.util.HashSet“，[” faculty“]]
    }，
    ” groupField“：” DISPLAY_EXTENSION“
  }
}
```

尽管 `grouper.client.properties` 是一个硬性要求，必须提供，但始终可以将配置属性分配给策略 以覆盖默认值：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“ test”，
  “ id”：62，
  “ accessStrategy”：{
    “ @class”：“ org.apereo.cas.grouper.services.GrouperRegisteredServiceAccessStrategy”，
    “ configProperties”：{
      “ @class”：“ java.util.HashMap”，
      “ grouperClient。 webService.url”：“ http://grouper.example.com/grouper-ws/servicesRest”
    }，
    “ groupField”：“ DISPLAY_EXTENSION”
  }
}
```
