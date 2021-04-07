---
layout: 默认
title: CAS-多因素身份验证绕过
category: 多因素身份验证
---

# 多因素身份验证绕过

每个 [多因素提供者](Configuring-Multifactor-Authentication.html) 都配备了允许旁路的选项。 一旦选择了提供者 来满足身份验证请求，便会参考旁路规则以计算提供者是否应忽略该请求并有条件地跳过MFA。

## 默认绕过

CAS为可以通过CAS属性配置的 [多因素提供程序](Configuring-Multifactor-Authentication.html) 提供默认的绕过策略。  
在咨询任何其他已配置的旁路提供商之前，所有提供商都将参考此策略了解旁路事件。

绕过规则允许每个提供程序具有以下选项：

- **主体** 属性 **名称**跳过多因素身份验证。
- ... [和可选地]基于指定的 **主体** 属性 **值**跳过多因素身份验证。
- **身份验证** 属性 **名称**跳过多因素身份验证。
- ... [和可选地]基于指定的 **身份验证** 属性 **值**跳过多因素身份验证。
- 根据主要身份验证执行的方法/形式，跳过多因素身份验证。
- 跳过多因素身份验证，具体取决于http请求的属性，例如远程地址/主机和/或标头名称。

以下是一些示例：

- 触发MFA，除非主体携带 `隶属关系` 属性，其值为 `明矾` 或 `成员`。
- 触发MFA，除非委托人带有 `superAdmin` 属性。
- 触发MFA，除非主要身份验证方法为SPNEGO。
- 触发MFA，除非用于主要身份验证的凭据的类型为 `org.example.MyCredential`。

注意的是，除了上述选项，一些多因素认证提供商 也可以跳过和旁路在事件的认证请求，所述认证的主要不相当“限定” 对多因素认证。 请参阅每个特定提供者的文档以了解更多信息。

### 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#multifactor-authentication)。

请注意，如果给定提供者的 ，则票证验证请求将成功通过。 在这种情况下，不会将身份验证上下文传递回应用程序，并且 其他属性，以使应用程序知道绕过提供者的多因素身份验证。

### 每项服务绕过

可以通过CAS服务注册表对每个应用程序覆盖MFA绕过规则。 MFA时，这很有用，但有选择地需要排除其中的一些。 可以在CAS服务注册表中定义访问 绕过MFA的服务0：

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “multifactorPolicy”： {
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ multifactorAuthenticationProviders”：[“ java.util.LinkedHashSet”，[“ mfa-duo”]]，
    “ bypassEnabled”：“ true”
  }
}
```

### 每个主体属性绕过 & 服务

这与上面的选项相似，不同之处在于，如果经过身份验证的主体包含具有指定值

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “^（HTTPS | IMAPS）：//.*”，
  “ID”：100，
  “multifactorPolicy”： {
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceMultifactorPolicy”，
    “ bypassPrincipalAttributeName”：“ attributeForBypass”，
    “ bypassPrincipalAttributeValue”：“ ^ bypass-value-[A-Z]。+”，
    “ bypassEnabled”：“正确”
  }
}
```

## 其他旁路提供商

除了可配置的默认旁路规则之外，在计算默认旁路规则之后，还可以定义和执行以下旁路提供程序。

在默认规则确定应该绕过多因素身份验证的情况下，该链将被短路，并且将不咨询其他绕过提供者。

### 通过Groovy绕过

可以使用您自己设计的Groovy脚本确定多因素身份验证绕过。 脚本的结果（如果为 `` 表示应继续对请求的提供程序进行多因素 否则 `假` 指示应跳过和绕过此提供程序的多因素身份验证。

脚本的概要如下：

```groovy
import java.util。*

def布尔值运行（最终对象... args）{
    def身份验证= args[0]
    def主体= args[1]
    def已注册服务= args[2]
    def提供者= args[3]
    def logger = args[4]
    def httpRequest = args[5]

    //发生东西...

    返回false；
}
```

传递的参数如下：

| 范围            | 描述                                 |
| ------------- | ---------------------------------- |
| `验证`          | 表示已建立的身份验证事件的对象。                   |
| `主要的`         | 代表已验证主体的对象。                        |
| `服务`          | 代表注册表中相应服务定义的对象。                   |
| `提供者`         | 表示请求的多因素身份验证提供程序的对象。               |
| `记录器`         | 负责发布日志消息的对象，例如 `logger.info（...）`。 |
| `httpRequest` | 负责捕获http请求的对象。                     |

例如，如果请求请求的应用程序在CAS服务注册表中以名称 `MyApplication` 注册，则以下脚本将跳过多因素身份验证，并且仅在提供者为Duo Security且经过身份验证的主体包含名为 `` 的属性的情况下，该脚本才会跳过此过程。值包含 `真`。

```groovy
def boolean run（final Object ... args）{
    def身份验证= args[0]
    def主体= args[1]
    def服务= args[2]
    def提供者= args[3]
    def logger = args[4]
    def httpRequest = args[5]

    if （service.name ==“ MyApplication”）{
        logger.info（“评估主体属性 ${principal.attributes}”）

        def旁路= principal.attributes ['mustBypassMfa']
        if（bypass.contains（“ true”） && 提供者。 id ==“ mfa-duo”）{
            logger.info（“跳过主体 ${principal.id}跳过”）
            return false
        }
    }
    return true
}
```

### 通过REST绕过

可以使用您自己设计的REST API确定多因素身份验证绕过。 端点必须设计为通过 `GET` 请求 `application / json` 返回的状态码 `202` 表示 `ACCEPTED` 表示应继续对请求的提供者进行多因素身份验证。 否则，应跳过和绕过此提供程序的多因素身份验证。

传递了以下参数：

| 范围    | 描述                  |
| ----- | ------------------- |
| `主要的` | 经过身份验证的主体的标识符。      |
| `提供者` | 多因素身份验证提供程序的标识符。    |
| `服务`  | 注册表中已注册服务的标识符（如果有）。 |
