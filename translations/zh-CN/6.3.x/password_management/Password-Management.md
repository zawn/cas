---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理

如果由于拒绝密码策略而导致身份验证失败，则CAS能够截获 ，并允许用户就地更新帐户密码。 CAS的密码管理功能相当适度，或者，如果该功能不足以提供您的策略，则您可以始终将CAS重定向为使用单独的独立应用程序，该应用程序完全负责管理帐户密码和关联的流程。

CAS还可以允许用户自愿重设密码。 那些忘记了帐户密码 可能会在其注册的电子邮件地址和/或电话上收到带有基于时间的过期策略的安全链接。 链接 将允许用户提供对其预先定义的安全性问题的答案，如果成功完成，则链接 将允许用户接下来重置其密码并再次登录。 您也可以为接受的密码指定一种模式。

默认情况下，用户成功更改密码后，他们将被重定向到登录屏幕 以输入其新密码并登录。 也可以将CAS配置为成功更改 可以通过CAS设置更改此行为。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-pm-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

<div class="alert alert-info"><strong>亚尼</strong><p>您无需在配置和覆盖中明确包含此模块
 这只是要告诉您它的存在。</p></div>

## 配置

要了解有关可用通知选项的更多信息，请 [请参阅本指南](../notifications/SMS-Messaging-Configuration.html) 或 [本指南](../notifications/Sending-Email-Configuration.html)。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#password-management)。

## reCAPTCHA整合

密码重置尝试可以受到保护，并与 [Google reCAPTCHA](https://developers.google.com/recaptcha)集成在一起。 这要求存在用于基本集成的reCAPTCHA设置，并指示密码管理流程打开并通过reCAPTCHA验证请求。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#google-recaptcha-integration) 和 [本指南](../configuration/Configuration-Properties.html#password-management)。

## 密码记录

CAS允许采取策略来跟踪和存储回收的密码。 回收的密码将保留在该用户帐户的存储器中，并且在密码更新时检查

一旦启用了密码历史记录功能，就可以通过Groovy或内存后端在历史记录中跟踪密码。 特定的 存储选项也可能提供其自己的密码历史记录支持。

### Groovy

启用了密码历史记录跟踪后，可以将其移交给外部Groovy脚本，如下所示：

```groovy
def exist（Object [] args）{
    def request = args[0]
    def logger = args[1]
    return false
}

def store（Object [] args）{
    def request = args[0]
    def logger = args[1]
    return true
}

def fetchAll（Object [] args）{
    def logger = args[0]
    return []
}

def fetch（Object [] args）{
    def username = args[0]
    def logger = args[1]
    return [ ]
}   

def remove（Object [] args）{ 
    def用户名= args[0]
    def logger = args[1]
}

def removeAll（Object [] args）{ 
    def logger = args[0]
}
```

`request` 参数封装了一个 `PasswordChangeRequest` 对象， `用户名` 和 `password` 字段。

## JSON存储

帐户和密码可以存储在静态适度的JSON资源中。 此选项在开发过程中最有用，对于演示目的， 要了解更多信息，请 [参见本指南](Password-Management-JSON.html)。

## Groovy存储

帐户和密码可以通过Groovy脚本进行处理和计算。 要了解更多信息，请 [请参阅本指南](Password-Management-Groovy.html)。

## LDAP储存

帐户密码和安全性问题可以存储在LDAP服务器中。 要了解更多信息，请 [请参阅本指南](Password-Management-LDAP.html)。

## JDBC存储

帐户密码和安全性问题可以存储在关系数据库中。 要了解更多信息，请 [请参阅本指南](Password-Management-JDBC.html)。

## REST存储

帐户密码和安全性问题也可以使用REST API进行管理。 要了解更多 请 [参阅本指南](Password-Management-REST.html)。

## 风俗

要设计自己的密码管理存储选项和策略，请 [请参阅本指南](Password-Management-Custom.html)。
