---
layout: 违约
title: CAS - 休息认证
category: 认证
---

# 休息认证

<div class="alert alert-warning"><strong>小心</strong><p>此文档描述了
如何将身份验证请求委托并提交到远程 REST 端点。 它与本地的CAS REST API无关
，其配置和警告
<a href="../protocol/REST-Protocol.html">记录在这里</a>。</p></div>

通过在战争覆盖中包括以下依赖项来启用 REST 身份验证：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>套机服务器支持-休息-认证</artifactId>
    <version>${cas.version}</version>
</dependency>
```

这允许 CAS 服务器通过 `邮政` 到达远程 REST 端口，以验证凭据。 凭据通过 `授权` 标题传递，其值 `基本 XYZ` 其中 XYZ 是 Base64 编码版本的凭据。

返回的响应必须附有一个 `200` 状态代码，其中主体应包含 `ID` 和 `属性` 字段，后者是可选的， 代表 CAS 的认证本金：

```json
{"@class"："org.apereo.cas.认证.校长.简单原则"，"id"："卡苏瑟"，"属性"：{}
```

从 REST 端点的预期响应映射到 CAS 如下：

| 法典    | 结果              |
| ----- | --------------- |
| `200` | 成功的身份验证。        |
| `403` | 生成 `帐户禁用例外`     |
| `404` | 生成 `帐户未发现例外`    |
| `423` | 生成 `帐户锁定例外`     |
| `412` | 生成 `帐户初始`       |
| `428` | 生成 `帐户密码必须改变例外` |
| 其他    | 生成 `失败日志初始`     |

## 警告

远程 REST 端点可以使用自定义标题将警告发回 CAS 服务器。 如果身份验证成功，这些警告将在登录后直接显示给用户。

### `X-CAS 警告`

对于响应中显示的每个 `X-CAS 警告` 标题，将向用户显示相应的消息。 标题值可以是 [本地化消息](../ux/User-Interface-Customization-Localization.html) 的关键，也可以是消息本身的关键。

### `X-CAS-密码清除日`

如果此标题存在于响应中，并包含 `RFC1123 日期` 将显示一条特殊消息 ，以警告用户有关过期密码的信息。 如果配置了 [密码管理提供商](../password_management/Password-Management.html) ， 用户将能够直接更改密码。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-authentication)。
