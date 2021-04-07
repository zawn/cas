---
layout: 默认
title: CAS-REST身份验证
category: 验证
---

# REST认证

<div class="alert alert-warning"><strong>小心</strong><p>本文档描述了
如何将身份验证请求委派和提交给远程REST端点。 它与本机CAS REST API无关，

<a href="../protocol/REST-Protocol.html">在此处记录</a>。</p></div>

通过在WAR覆盖中包含以下依赖项来启用REST身份验证：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持休息认证</artifactId>
    <version>${cas.version}</version>
</dependency>
```

`POST` 到达远程REST端点，以进行凭据验证。 `授权` 标头传递，该标头的值为 `Basic XYZ` ，其中XYZ是 Base64编码版本。

返回的响应必须随附一个 `200` 状态代码，其中主体应包含 `id` 和 `属性` 字段，后者是可选的， 代表CAS的已验证主体：

```json
{“ @class”：“ org.apereo.cas.authentication.principal.SimplePrincipal”，“ id”：“ casuser”，“ attributes”：{}}
```

来自REST端点的预期响应按以下方式映射到CAS：

| 代码    | 结果                                        |
| ----- | ----------------------------------------- |
| `200` | 成功认证。                                     |
| `403` | 产生一个 `AccountDisabledException`           |
| `404` | 产生一个 `AccountNotFoundException`           |
| `423` | 产生一个 `AccountLockedException`             |
| `412` | 产生一个 `AccountExpiredException`            |
| `428` | 产生一个 `AccountPasswordMustChangeException` |
| 其他    | 产生一个 `FailedLoginException`               |

## 警示语

远程REST端点可以使用自定义标头将警告发送回CAS服务器。 如果身份验证成功，则登录后将直接向用户显示这些警告。

### `X-CAS警告`

对于 `X-CAS-Warning` 标头，都会向用户显示一条相应的消息。 [本地消息](../ux/User-Interface-Customization-Localization.html) 的关键字，也可以是消息本身。

### `X-CAS-PasswordExpirationDate`

如果此标头出现在响应中并且包含 `RFC1123日期` 则会显示一条特殊消息 以警告用户密码即将过期。 如果配置了 [密码管理提供程序](../password_management/Password-Management.html) ，则 用户将能够直接更改密码。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-authentication)。
