---
layout: 默认
title: CAS-ADFS集成
category: 验证
---

# 概述

CAS Server和ADFS之间的集成将用户身份验证从CAS Server 委派给ADFS，从而使CAS Server成为WS-Federation客户端。 从ADFS发出的声明可作为属性提供给CAS Server，以及扩展为CAS Client。

<div class="alert alert-info"><strong>记住</strong><p>此处描述的功能允许CAS将ADFS用作外部身份提供程序。 如果您想做相反的事情，允许ADFS成为CAS客户端并使用CAS作为身份提供者，则可以利用</a> <a href="../installation/Configuring-SAML2-Authentication.html">SAML2支持作为一个集成选项。</p></div>

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-wsfederation-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您可能还需要在 CAS Overlay中声明以下存储库，以便能够解决依赖关系：

```groovy
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://build.shibboleth.net/nexus/content/repositories/releases” 
    }
}
```

<div class="alert alert-info"><strong>JCE要求</strong><p>确保您已在CAS使用的Java环境中安装了正确的JCE bundle 
是安全的，特别是在您需要使用ADFS发行的加密有效负载的情况下。 
确保为您的Java版本选择正确的JCE版本。 <code>java -version</code> 命令检测Java版本。</p></div>

## WsFed配置

调整并提供ADFS实例的设置，并确保您已获得ADFS签名证书 并在运行时可解析的位置将其提供给CAS。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ws-fed-delegated-authentication)。

## 加密的断言

CAS能够自动解密ADFS发出的SAML断言。 为此，您首先需要生成一个私钥/公钥对

```bash
openssl genrsa -out private.key 1024
openssl rsa -pubout -in private.key -out public.key-通知PEM -outer DER
openssl pkcs8 -topk8-通知PER -outder DER -nocrypt -in private.key -out private .p8
openssl req -new -x509 -key private.key -out x509.pem -days 365

＃将X509证书转换为DER格式
openssl x509 -outform der -in x509.pem -out certificate.crt
```

配置CAS以引用密钥对，并在ADFS `certificate.crt` 文件进行加密。

## 修改ADFS声明

WsFed配置（可选）可以允许您处理来自ADFS的声明，但是在将它们插入CAS用户主体之前。 属性的操作是使用 *属性转换器* ，其中其逻辑可以在Groovy脚本内实现，并且其 路径通过设置传给CAS。

该脚本可以采用以下形式：

```groovy
import org.apereo.cas。*
import java.util。*
import org.apereo.cas.authentication。*

def Map run（final Object ... args）{
    def properties = args[0]
    def logger = args[1]
    logger.warn（“正在更改属性{}”，属性）
    return [upn：[“ CASUser”]]
}
```

传递给脚本的参数如下：

| 范围    | 描述                                 |
| ----- | ---------------------------------- |
| `属性`  | ADFS提供的属性的当前 `映射`                  |
| `记录器` | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

请注意，脚本 *的执行结果必须* 将属性收集到 `Map` ，其中属性名称（键）是简单的 `字符串` ，并将属性值转换为集合。

## 处理CAS注销

可选步骤 `casLogoutView.html` 可以修改为放置指向ADFS注销页面的链接。

```html
<a href="https://adfs.example.org/adfs/ls/?wa=wsignout1.0">注销</a>
```

或者，您可以简单地指示CAS在执行注销操作后重定向到上述端点。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logout)。

## 每项服务依赖方ID

为了为每个服务定义指定一个依赖方标识符，请调整服务 注册表以匹配以下内容：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“示例服务”，
  “ id”：100 ，
  “ properties”：{
    “ @class”：“ java.util.HashMap”，
    “ wsfed.relyingPartyIdentifier”：{
      “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceProperty”，
      “值“：[” java.util.HashSet“，[” custom-identifier“]]
    }
  }
}
```

## 故障排除

请注意CAS与ADFS服务器之间的时钟漂移问题。 响应的验证失败确实会出现在日志中，并且请求 ADFS，从而导致重定向循环。
