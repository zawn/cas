---
layout: 默认
title: CAS-具有SAML2身份提供者的委托身份验证
category: 验证
---

# 带SAML2的委派身份验证

如果将CAS配置为将身份验证委派给外部身份提供者，则服务提供者（CAS） 元数据以及身份提供者元数据将在以下端点上自动变为可用：

| 终点                             | 描述                                       |
| ------------------------------ | ---------------------------------------- |
| `/ sp /元数据`                    | 显示服务提供商（CAS）元数据。 如果仅定义一个SAML2 IdP，则效果很好。 |
| `/ sp / idp /元数据`              | 显示身份提供者元数据。 如果仅定义一个SAML2 IdP，则效果很好。      |
| `/ sp /{clientName}/元数据`       | 显示请求的客户端名称的服务提供商元数据。                     |
| `/ sp /{clientName}/ idp /元数据` | 显示所请求的客户端名称的身份提供者元数据。                    |

请注意，您可以在CAS上使用多个外部身份提供程序，其中每个集成都可以使用0的不同元数据和键集（作为服务提供程序的CAS） 可以为每个集成（称为客户端，因为CAS本身成为身份提供者

端点或查看CAS登录屏幕，服务提供商（CAS）元数据就会自动生成。 这是必需的，因为今天生成元数据需要对HTTP请求/响应的 在事件的元数据不能 解决，状态代码 `406 -不接受` 被返回。

## 身份提供商发现服务

<div class="alert alert-info"><strong>笔记</strong><p>使用身份提供者发现需要 
委托身份验证可用的功能不能单独使用
作为一个独立的搜索服务。</p></div>

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-saml-idp-discovery</artifactId>
    <version>${cas.version}</version>
</dependency>
```

身份提供者发现允许CAS [并提供发现服务](https://wiki.shibboleth.net/confluence/display/EDS10/Embedded+Discovery+Service) 作为委派身份验证的一部分。 提供了用于委派身份验证 中已配置的SAML2身份提供程序，作为发现选项。

CAS还能够直接使用多个JSON feed ，其中包含有关可用身份提供者的发现元数据。 可以从URL提取发现JSON feed （即由Shibboleth服务提供商公开），也可以 直接用作具有以下结构的JSON文件：

```json
[{
 “ENTITYID”： “https://idp.example.net/idp/saml”，
 “DisplayNames”：[{
  “值”： “Example.net”，
  “郎”： “EN”
  }]，
 “描述”：[{
  “值”：“一个由人民为人民提供的身份提供者。”，
  “ lang”：“ en”
  }]，
 “徽标”：[{
  “值“： ”https://example.net/images/logo.png“，
  ”高度“： ”90“，
  ”宽“： ”62“
  }]
}]
```

要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#saml2-identity-provider-discovery)。

以下端点可用：

| 终点              | 描述                    |
| --------------- | --------------------- |
| `/ idp /发现`     | 身份提供者发现登录页面。          |
| `/ idp /发现/提要`  | 身份提供者发现JSON提要。        |
| `/ idp /发现/重定向` | 返回端点以让CAS在选择后调用身份提供者。 |

应用程序可以通过 `[cas-server-prefix]/ idp / discovery`直接调用发现服务。 发现服务可能也 可以使用发现协议通过调用 `[cas-server-prefix]/ IDP /发现？ENTITYID =[service-provider-entity-id]&返回=[cas-server-prefix]/ IDP /发现/重定向`。 其他参数可能会作为 `return` url的一部分包括在内，并且必须全部进行编码。
