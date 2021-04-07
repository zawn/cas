---
layout: 默认
title: CAS-JAAS认证
category: 验证
---

# JAAS认证

[JAAS](https://docs.oracle.com/javase/9/security/java-authentication-and-authorization-service-jaas1.htm) 是Java标准 身份验证和授权API。 JAAS通过外部化的纯文本配置文件进行配置。 将JAAS与CAS一起使用可以修改身份验证过程，而不必重建和重新部署CAS 并且可以进行PAM样式的多模块“堆叠”身份验证。

## 配置

JAAS组件在CAS核心模块中提供，不需要使用其他依赖项。 JAAS处理程序将委托给内置的JAAS子系统，以根据JAAS配置文件中

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jaas-authentication)。

## JAAS配置文件

缺省的JAAS配置文件位于 `$JRE_HOME/lib/security/java.security`。 重要的是要注意 ，JAAS配置适用于整个JVM。 通过将 `java.security.auth.login.config` 系统属性设置为备用文件路径（即 `file：/etc/cas/config/jaas.config`可以有效地更改JAAS配置文件的路径 ）。

提供了一个示例JAAS配置文件以供参考。

```
/ **
  * JAAS的登录配置，领域名称定义为CAS。
  * /
CAS {
  org.sample.jaas.login.SampleLoginModule足够
    debug = FALSE;
};
```

## 登录模块

以下登录模块可用于CAS：

### 日本国家发展研究院

该模块会提示输入用户名和密码，然后根据在JNDI下配置的目录服务中存储的密码来验证密码。

```
CAS {
  com.sun.security.auth.module.JndiLoginModule足够

    。provider.url= name_service_url
    debug = FALSE;
};
```

值 `name_service_url` 指定目录服务和此模块可以在其中访问相关用户和组信息的路径。 因为此模块仅执行一级搜索来找到相关的用户信息，所以URL必须指向目录和目录服务中存储用户和组信息的上一级目录。

有关所有其他选项的列表和更全面的文档，请参阅 [本指南](http://docs.oracle.com/javase/8/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/JndiLoginModule.html) 以获取更多信息。

### 的Kerberos

该模块使用Kerberos协议对用户进行身份验证。 模块的配置条目具有几个选项，这些选项控制 身份验证过程以及对 `Subject`的专用凭据集的添加。 无论这些选项如何， `Subject`的主体集和私有凭证集。 调用commit时，将 `KerberosPrincipal` 添加到 `Subject`的主体集，并将KerberosTicket添加到 `Subject`的专用凭据。

如果模块的配置条目将选项 `storeKey` 设置为true，则 `KerberosKey` 添加到 主题的专用凭据中。 `KerberosKey`，主体的密钥可以从密钥表中获取，也可以从用户的密码中获取。

该模块还识别 `doNotPrompt` 选项。 如果设置为true，则不会提示用户输入密码。 用户可以通过使用配置条目中 `ticketCache` 来指定票证缓存的位置。 用户可以通过使用配置条目中 `keyTab`

主体名称可以在配置条目通过使用选项来指定 `本金`。 主体名称可以是 ，可以是简单用户名，也可以是服务名称，例如 `host / mission.eng.sun.com`。 主体也可以使用系统属性 `sun.security.krb5.principal` 。 登录期间检查此属性。 如果未设置此属性， 然后使用配置中的主体名称。 在没有设置主体属性并且主体 条目也不存在的情况下，系统会提示用户输入名称。 设置该条目的属性并将 `useTicketCache` 设置为 true时，仅使用属于此主体的TGT。

请注意， `-Djava.security.krb5.conf = / etc / krb5.conf``krb5.conf` 提供给JVM进行Kerberos身份验证。

```
CAS {
  com.sun.security.auth.module.Krb5LoginModule足够
    refreshKrb5Config = TRUE / FALSE
    useTicketCache = TRUE / FALSE
    ticketCache = ...
    renewTGT = TRUE / FALSE
    useKeyTab = TRUE / FALSE
    doNotPrompt = TRUE / FALSE
    keyTab = TRUE / FALSE
    storeKey = TRUE / FALSE
    主体= ...
    debug = FALSE;
};
```

有关所有其他选项的列表和更全面的文档，请参阅 [本指南](http://docs.oracle.com/javase/8/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/Krb5LoginModule.html) 以获取更多信息。

### UNIX系统

这个模块导入用户的Unix主要信息（`UnixPrincipal`， `UnixNumericUserPrincipal`，和 `UnixNumericGroupPrincipal`）和同事将它们与当前 `主体`。

```
CAS {
  com.sun.security.auth.module.UnixLoginModule足够
    debug = FALSE;
};
```

有关所有其他选项的列表和更全面的文档，请参阅 [本指南](http://docs.oracle.com/javase/8/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/UnixLoginModule.html) 以获取更多信息。

### 新台币

该模块将用户的NT安全性信息呈现为 `主体`的数量，并将它们与 `主体`关联。

```
CAS {
  com.sun.security.auth.module.NTLoginModule足够
    debugNative = TRUE
    debug = FALSE;
};
```

有关所有其他选项的列表和更全面的文档，请参阅 [本指南](http://docs.oracle.com/javase/8/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/NTLoginModule.html) 以获取更多信息。

### LDAP

此模块执行基于LDAP的身份验证。 对照存储在LDAP目录中的相应用户凭证来验证用户名和密码。 如果身份验证成功，则使用用户的专有名称创建 `LdapPrincipal` 并使用用户的用户名创建 `UserPrincipal` `Subject`关联。

有关所有其他选项的列表和更全面的文档，请参阅 [本指南](http://docs.oracle.com/javase/8/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/LdapLoginModule.html) 以获取更多信息。

该模块以三种模式之一运行。 通过指定一组特定的选项来选择一种模式：

#### 首先搜索

在搜索优先模式下，将搜索LDAP目录以确定用户的专有名称，然后尝试进行身份验证。 使用提供的用户名和指定的搜索过滤器来执行（匿名）搜索。 如果成功，则尝试使用用户的专有名称和提供的密码进行身份验证。 要启用此模式，请设置 `userFilter` 选项，并省略 `authIdentity` 选项。 如果事先不知道用户的专有名称，请使用 `搜索优先`

下面的示例标识了LDAP服务器，并指定了用户条目的 `uid` 和 `objectClass` 属性。 它还指定应基于用户的 `employeeNumber` 属性创建一个标识。

```
CAS {
  com.sun.security.auth.module.LdapLoginModule必需
    userProvider =“ ldap：// ldap-svr / ou = people，dc = example，dc = com”
    userFilter =“（&（uid ={USERNAME}） （objectClass = inetOrgPerson））“
    authzIdentity =”{EMPLOYEENUMBER}“
    debug = true;
};
```

#### 认证优先

在 `认证优先` 模式下，尝试使用提供的用户名和密码进行认证，然后搜索LDAP目录。 如果身份验证成功，则使用提供的用户名和指定的搜索过滤器进行搜索。 要启用此模式，请设置 `authIdentity` 和 `userFilter` 选项。 访问已配置为禁止匿名搜索的LDAP目录时，请使用 `认证优先`

下面的示例要求动态定位LDAP服务器，直接使用提供的用户名执行身份验证，但不使用SSL保护，并通过三个命名属性之一及其 `objectClass` 属性来定位用户的条目。

```
CAS {
  com.sun.security.auth.module.LdapLoginModule必需
    userProvider =“ ldap：/// cn = users，dc = example，dc = com”
    authIdentity =“{USERNAME}”
    userFilter =“（&（| （samAccountName ={USERNAME}）（userPrincipalName ={USERNAME}）（cn ={USERNAME}））（objectClass = user））“
    useSSL = false
    debug = true;
};
```

#### 仅认证

在 `身份验证` 模式下，尝试使用提供的用户名和密码进行身份验证。 由于用户的专有名称是已知的，因此不会搜索LDAP目录。 要启用此模式，请将authIdentity选项设置为有效的专有名称，并省略userFilter选项。 事先知道用户的专有名称时，请使用仅身份验证模式。

下面的示例标识了其他LDAP服务器，它指定了用于身份验证的专有名称和用于授权的固定身份。 不执行目录搜索。

```
CAS {
  com.sun.security.auth.module.LdapLoginModule必需
    userProvider =“ ldap：// ldap-svr1 ldap：// ldap-svr2”
    authIdentity =“ cn ={USERNAME}，ou = people，dc = example， dc = com“
    authzIdentity =” staff“
    debug = true;
};
```

### 适应性

Ldaptive提供了 [登录模块，用于针对LDAP的](http://www.ldaptive.org/docs/guide/jaas.html) 每个模块都接受与ldaptive代码库中对象上的setter对应的属性。 如果您希望设置一个特定的配置选项，可以将其作为设置器使用，则很有可能在模块上被接受。 任何未知的选项都将作为通用属性传递给提供程序。

为了利用Ldaptive提供的登录模块，必须存在以下依赖项并将其添加到叠加层中：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-ldap-core</artifactId>
    <version>${cas.version}</version>
</dependency>
```

### 密钥库

此模块提示输入密钥存储别名，并使用别名的主体和凭据填充主题。 在主题的主体中的别名凭据中存储第一个证书的主题可分辨名称的 `X500Principal` ，在主题的公共凭据中存储别名的证书路径，并在别名的证书路径中存储 `X500PrivateCredential` 其证书是第一个证书），以及其私有密钥是主题的私有证书中别名的私有密钥。

```
CAS {
  com.sun.security.auth.module.KeyStoreLoginModule足够
    keyStoreURL = ...
    keyStoreType =
    keyStoreProvider = ...
    keyStoreAlias = ...
    keyStorePasswordURL = ...
    privateKeyPasswordURL = ...
    保护= ...
    debug = FALSE;
};
```

有关所有其他选项的列表和更全面的文档，请参阅 [本指南](http://docs.oracle.com/javase/8/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/KeyStoreLoginModule.html) 以获取更多信息。

## 部署

如果您的部署策略，最终采用了 [嵌入容器](Configuring-Servlet-Container.html#embedded)， ，你可以沿着一个系统属性为这样的形式JAAS配置文件的位置传递：

```bash
java -Djava.security.auth.login.config =文件：/etc/cas/config/jaas.config -jar ...
```

或者，您可以在CAS设置中将登录配置类型激活为 `JavaLoginConfig` ，并直接在设置中直接在其中

有关配置管理的更多信息，请 [本指南](../configuration/Configuration-Management.html)。
