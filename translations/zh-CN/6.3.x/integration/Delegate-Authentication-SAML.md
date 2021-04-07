---
layout: 违约
title: CAS - 授权身份认证/SAML2 身份提供商
category: 认证
---

# 授权身份验证/SAML2

如果 CAS 配置为将身份验证委托给外部身份提供商，服务提供商 （CAS） 元数据以及身份提供商元数据在以下端点自动可用：

| 端点                         | 描述                                          |
| -------------------------- | ------------------------------------------- |
| `/sp/元数据`                  | 显示服务提供商 （CAS） 元数据。 如果定义只有一个SAML2 IDP，则效果良好。 |
| `/sp/idp/元数据`              | 显示身份提供商元数据。 如果定义只有一个SAML2 IDP，则效果良好。        |
| `/{clientName}/元数据`        | 显示所请求的客户名的服务提供商元数据。                         |
| `/sp/{clientName}/idp/元数据` | 显示所请求的客户名的身份提供商元数据。                         |

请注意，您可以使用 CAS 的多个外部身份提供商，其中每个集成都可以 使用一组不同的元数据和作为服务提供商的 CAS 密钥完成。 每个集成（称为客户端， ，因为 CAS 本身成为身份提供商的客户端）可以任选地命名。

请记住，当您访问上述端点或查看 CAS 登录屏幕时 ，会自动生成服务提供商 （CAS） 元数据。 这是必要的，因为今天，生成元数据需要 访问HTTP请求/响应。 如果元数据无法 解决，则返回 `406 - 不能接受` 的状态代码。

## 身份提供商发现服务

<div class="alert alert-info"><strong>注意</strong><p>使用身份提供商发现需要 
委托身份验证可用，因为该功能不能
作为独立发现服务使用。</p></div>

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普发现</artifactId>
    <version>${cas.version}</version>
</dependency>
```

身份提供商发现允许 CAS [嵌入并提供发现服务](https://wiki.shibboleth.net/confluence/display/EDS10/Embedded+Discovery+Service) 作为委托身份验证的一部分。 用于委托身份验证的 CAS 配置 中的 SAML2 身份提供商作为发现选项呈现。

CAS 还能够直接消耗多个 JSON 源 ，其中包含有关可用身份提供商的发现元数据。 发现 JSON 源 可能从 URL（即由 Shibboleth 服务提供商曝光）获取，也可以 直接用作具有以下结构的 JSON 文件：

```json
[{
 "实体ID"："https://idp.example.net/idp/saml"，
 "显示名称"：[
  "价值"："Example.net"，
  "lang"："en"
  }]，
 "描述"：[{
  "价值"："人民的身份提供者，
  "lang"："en"
  []，
 "Logos"：[
  "价值"："https://example.net/images/logo.png"，
  "高度"："90"，
  "宽度"："62"
  []
[]
```

要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#saml2-identity-provider-discovery)查看本指南。

提供以下端点：

| 端点            | 描述                      |
| ------------- | ----------------------- |
| `/idp/发现`     | 身份提供商发现着陆页。             |
| `/idp/发现/馈送`  | 身份提供商发现 JSON 源。         |
| `/idp/发现/重定向` | 返回终点，让 CAS 在选择后调用身份提供商。 |

申请可直接通过 `[cas-server-prefix]/idp/发现`调用发现服务。 发现服务也可能 使用发现协议通过 `[cas-server-prefix]/idp/发现？实体ID=[service-provider-entity-id]&返回=[cas-server-prefix]/idp/发现/重定向`。 其他参数可以作为 `返回` 网址的一部分，并且必须对它们进行编码。
