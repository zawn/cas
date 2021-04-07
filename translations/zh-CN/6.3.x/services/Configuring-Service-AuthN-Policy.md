---
layout: 默认
title: CAS-服务认证策略
category: 服务
---

# 服务认证策略

可以向注册表中的每个注册应用程序分配一个身份验证策略，该策略指示 应该如何验证和执行身份验证事务。 身份验证策略 有时可能会覆盖在CAS身份验证引擎中全局找到的 以增强身份验证流程。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，  
    ” requiredAuthenticationHandlers“：[” java.util.TreeSet“，[” AuthNHandlerName“]]，
    “ excludedAuthenticationHandlers”：[“ java.util.TreeSet”，[]]
  }
}
```

以下字段可以分配给策略：

| 范围                               | 描述                                                                              |
| -------------------------------- | ------------------------------------------------------------------------------- |
| `requiredAuthenticationHandlers` | 在CAS中可用并配置的所需身份验证处理程序的一组标识符/名称。 这些名称可用于强制服务定义，以仅在将身份验证请求提交给CAS时才使用带有该名称的身份验证策略。 |
| `excludeAuthenticationHandlers`  | 排除的身份验证处理程序的一组标识符/名称。 这些名称可用于将服务定义强制为 *排除* 并在将身份验证请求提交给CAS时取消某些身份验证处理程序的资格。     |

请注意，虽然CAS中的所有身份验证方法都被赋予了默认名称，但大多数（如果不是全部）方法都可以通过CAS设置来分配名称。

## 认证策略标准

身份验证策略标准也可以分配给每个应用程序定义，它们应该覆盖为部署定义的全局策略。 这类政策应紧跟之后那些 [可以全局定义](../installation/Configuring-Authentication-Components.html#authentication-policy)，是完全任选的，并且可以是下列类型之一：

### 允许的

映射到 `必需` [身份验证策略](../configuration/Configuration-Properties.html#required)。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^（https | imaps）：//.*”，
  “ name”：“ Example”，
  “ id “：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” requiredAuthenticationHandlers“：[” java.util.TreeSet“，[” JSON“]]，
    “ criteria”：{
      “ @class”：“ org.apereo.cas.services.AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria”
    }
  }
}
```

### 排除在外

启用身份验证策略条件，以按其名称排除和取消指定的身份验证处理程序的资格。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^（https | imaps）：//.*”，
  “ name”：“ Example”，
  “ id “：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” excludedAuthenticationHandlers“：[” java.util.TreeSet“，[” JSON“]]，
    “ criteria”：{
      “ @class”：“ org.apereo.cas.services.ExcludedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria”
    }
  }
}
```

### 任何

映射到 `Any` [身份验证策略](../configuration/Configuration-Properties.html#authentication-policy)。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” criteria“：{
      ” @class“：” org.apereo.cas.services。 AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria“，
      ” tryAll“：true
    }
  }
}
```

### 全部

映射到 `全部` [身份验证策略](../configuration/Configuration-Properties.html#authentication-policy)。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” criteria“：{
      ” @class“：” org.apereo.cas.services。 AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria“
    }
  }
}
```

### 没有预防

映射到 `Not Prevented` [身份验证策略](../configuration/Configuration-Properties.html#authentication-policy)。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” criteria“：{
      ” @class“：” org.apereo.cas.services。 NotPreventedRegisteredServiceAuthenticationPolicyCriteria“
    }
  }
}
```

### Groovy

映射到 `Groovy` [身份验证策略](../configuration/Configuration-Properties.html#authentication-policy)。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” criteria“：{
      ” @class“：” org.apereo.cas.services。 GroovyRegisteredServiceAuthenticationPolicyCriteria“，
      ” script“：” ...“
    }
  }
}
```

`脚本` 属性可以是嵌入式Groovy脚本或对外部文件的引用。

### 休息

 映射到 `Rest` [身份验证策略](../configuration/Configuration-Properties.html#authentication-policy)。

```json
{
  “@class”： “org.apereo.cas.services.RegexRegisteredService”，
  “服务Id”： “https://app.example.org/.+”，
  “名称”：为“ExampleApp”，
  “ id“：
  ” authenticationPolicy“：{
    ” @class“：” org.apereo.cas.services.DefaultRegisteredServiceAuthenticationPolicy“，
    ” criteria“：{
      ” @class“：” org.apereo.cas.services。 RestfulRegisteredServiceAuthenticationPolicyCriteria“，
      ” url“：” ...“，
      ” basicAuthUsername“：” ...“，
      ” basicAuthPassword“：” ...“
    }
  }
}
```
