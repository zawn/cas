---
layout: 违约
title: 中科院 - ADFS 集成
category: 认证
---

# 概述

CAS 服务器和 ADFS 之间的集成将用户身份验证从 CAS 服务器 委托给 ADFS，使 CAS 服务器成为 WS-联邦客户端。 ADFS 发布的索赔可作为 CAS 服务器的属性以及 CAS 客户端提供。

<div class="alert alert-info"><strong>记得</strong><p>此处描述的功能允许 CAS 将 ADFS 用作外部身份提供商。 如果您希望做相反的事情，允许 ADFS 成为 CAS 客户端，并使用 CAS 作为身份提供商，您可以利用 CAS</a> 中的 <a href="../installation/Configuring-SAML2-Authentication.html">SAML2 支持作为一个集成选项。</p></div>

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-服务器-网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您可能需要在您的 CAS 叠加中申报以下存储库，以能够解决依赖关系：

```groovy
存储库{
    maven{ 
        马文康顿{发布（）=
        网址"https://build.shibboleth.net/nexus/content/repositories/releases" 
    }
}
```

<div class="alert alert-info"><strong>JCE 要求</strong><p>确保您在您的 Java 环境中安装适当的 JCE 捆绑包 
是安全的，特别是如果您需要使用 ADFS 发布的加密有效载荷。 
请务必为您的Java版本选择正确的JCE版本。 爪哇版本可以通过 <code>爪哇版本</code> 命令检测到。</p></div>

## WsFed 配置

调整和提供 ADFS 实例的设置，并确保您已获得 ADFS 签名证书，并在运行时可以解决的位置提供给 CAS。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ws-fed-delegated-authentication)。

## 加密断言

CAS 能够自动解密 ADFS 发布的 SAML 断言。 要做到这一点， 您首先需要生成私钥/公共密钥对：

```bash
打开sl genrsa-出私人.key 1024
打开sl rsa-在私人.key-出公共.key-通知PEM-超越DER
打开sl pkcs8-topk8-通知PER-超越DER-诺克里普特-在私人.key-出私人 .p8
打开 sl req -新 -x509 -关键私有.key -出 x509.pem - 天 365

# 将 X509 证书转换为 DER 格式
打开 sl x509 - 外形 de - in x509.pem - 出证书. crt
```

配置 CAS 以引用密钥对换，并配置 ADFS 中 的依赖方信任设置，以使用 `证书。crt` 文件进行加密。

## 修改 ADFS 索赔

WsFed 配置可任选地允许您操作来自 ADFS 的索赔，但在它们插入 CAS 用户本金之前。 属性的操作使用 *属性突变器进行* 其逻辑可以在 Groovy 脚本中实现，其 路径通过设置传授给 CAS。

脚本可能采取以下形式：

```groovy
进口组织.apereo.cas.*
进口 java.util.*
进口组织.apereo.cas.认证.*

def 地图运行（最终对象...args）{
    定义属性=args[0]
    def记录器=args[1]
    记录器。警告（"变异属性[}"，属性）
    返回[upn：["CASUser"]]
]
```

传递给脚本的参数如下：

| 参数   | 描述                                |
| ---- | --------------------------------- |
| `属性` | 当前 `地图` ADFS 提供的属性。               |
| `记录` | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |

请注意，脚本的执行结果 *必须* 确保将属性收集到 `映射` 中，其中属性名称、密钥是简单的 `字符串` 属性值转换为集合。

## 处理 CAS 注销

可选步骤，即 `casLogoutView.html` 可进行修改，以放置指向ADFS注销页面的链接。

```html
<a href="https://adfs.example.org/adfs/ls/?wa=wsignout1.0">注销</a>
```

或者，您也可以简单地指示 CAS 在执行注销操作后重定向到上述端点。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#logout)。

## 每个服务依赖方 ID

为了指定每个服务定义的依赖方标识符，请调整您的服务 注册表以匹配以下情况：

```json
•
  "@class"："org.apereo.cas.services.注册服务"，
  "服务id"："^https://.+"，
  "名称"："样本服务"，
  "id"：100，
  "属性"：{
    "@class"："java.il.哈希马普"，
    "wsfed.依赖方标识符"：{
      "@class"："org. apereo.cas.服务.默认注册服务专业"，
      "值"："java.使用。哈希集"，"自定义标识符"]
    }
  }
}
```

## 故障 排除

注意 CAS 和 ADFS 服务器之间的时钟漂移问题。 响应的验证失败确实显示在日志中，请求再次被路由回 ADFS，从而导致重定向循环。
