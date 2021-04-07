---
layout: 违约
title: CAS - 配置 SSO 会话
category: 索 & 斯洛
---

# SSO 会话

赠票饼干是中科院在建立单一签到会话时设置的 HTTP 饼干。 此 Cookie 为客户端保持登录 状态，虽然它有效，但客户端可以将其提交给 CAS 以代替主要凭据。 服务可以通过 `续订` 参数选择退出单一登录，或者 CAS 服务器可以根据服务注册表中为应用程序定义的政策有条件地选择退出服务 。 有关详细信息，请参阅 [CAS 协议](../protocol/CAS-Protocol.html) 。

Cookie 值链接到活动票证授予票证、启动请求的远程 IP 地址 以及提交请求的用户代理。 然后加密并签名最终的 Cookie 值。

这些键 **必须** 根据您的特定环境再生。 每个密钥 都是 JSON Web 令牌，每个用于加密和签名的算法都有定义长度。

如果密钥不是由部署器生成的，CAS 将尝试自动生成密钥，并将输出 每个受尊重密钥的结果。 部署人员必须尝试将生成的密钥复制到 CAS 属性文件中的相应 设置，尤其是在运行多节点 CAS 部署时。 否则将阻止 CAS 适当解密和加密 Cookie 值，并阻止成功的单次登录。

<div class="alert alert-info"><strong>SSO 会话</strong><p>可以审查当前主动 SSO 会话的集合，
并确定 CAS 本身是否通过 <a href="../monitoring/Monitoring-Statistics.html">CAS 管理面板保持活跃的 SSO 会话。</a></p></div>

## 行政终点

CAS 提供以下端点：

| 端点    | 描述                                                                                                                                                                                                             |
| ----- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `索塞斯` | 查看与 CAS 建立的当前单个登录会话，并远程管理每个会话。 `GET` 操作会生成当前 SSO 会话的列表，这些会话由提供 `类型的` 参数进行筛选，该参数的值 `所有`、 `PROXIED` 或直接</code>`。 <code>删除未指定票证 ID 的` 操作将尝试销毁所有 SSO 会话。 将网址中的出票标识符指定为占位符/选择器将尝试破坏该票证控制的会话。 （即 `索西翁/{ticket}`）。 |
| `索`   | 指示与浏览器会话和 SSO Cookie 关联的单个登录会话的当前状态。                                                                                                                                                                           |

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ticket-granting-cookie)。

饼干具有以下属性：

1. 它被标记为安全。
2. 根据容器支持，Cookie 将自动标记为仅限 http。
3. Cookie 值通过部署时需要生成的秘密密钥进行加密和签名。

如果密钥未定义，启动时 CAS 将注意到未定义密钥，它将自动为您适当生成密钥。 然后，您的 CAS 日志将显示以下片段：

```bash
警告 [...] - 未定义加密 <密钥。 中科院将自动生成加密密钥>
警告 [...] - <生成的加密密钥ABC的大小。。。。 生成的密钥必须添加到 CAS 设置中。>
警告 [...] - <签名的秘密密钥未定义。 中科院将自动生成签名密钥>
警告 [...] - <生成的签名密钥XYZ的大小。。。。 生成的密钥必须添加到 CAS 设置中。>
```

然后，您应该抓住每个生成的密钥进行加密和签名，并将它们放入每个设置的 CAS 属性中。 如果您希望手动生成密钥，您可以 [使用以下工具](https://github.com/mitreid-connect/json-web-key-generator)。

## 用于续订身份验证的曲奇生成

默认情况下，通过 [`续订` 请求参数](../protocol/CAS-Protocol.html) 或通过 [CAS 服务注册表 的服务特定设置](../services/Service-Management.html) 向用户挑战凭据的强制身份验证请求 始终会生成票证授予 cookie 。 这意味着，通过 CAS 登录 非 SSO 参与的应用程序可创建有效的 CAS 单个登录会话，该会话将在随后尝试对 SSO 参与申请进行身份验证 获得尊重。

饼干生成策略也可以根据每个应用程序进行自定义。 有关更多详情，请 [本指南](../services/Configuring-Service-SSO-Policy.html)。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。

## 全球禁用 SSO

通过 CAS 属性，可以在全球范围内禁用参加单次登录会话。 要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。

## 禁用服务 SSO 访问

每个应用程序都可以禁用现有单个签名会话。 例如， 以下服务每次都会受到挑战，无法提交凭据，从而不使用 SSO：

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："。。。"，
  "名称"："。。。"，
  "id"：1，
  "访问战略"：{
    "@class"："org.apereo.cas.服务"。默认注册服务访问"，
    "不可接受"：虚假
  }
}
```

## SSO 参与政策

单个登录参与策略也可以根据每个应用程序进行自定义。 有关更多详情，请 [本指南](../services/Configuring-Service-SSO-Policy.html)。

## SSO警告会话曲奇

CAS 在 CAS 登录页面上应用户要求建立 SSO 会话时设置的警告曲奇。 稍后，Cookie 用于在生成服务票证并授予访问服务应用程序之前向用户发出警告和提示。

要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#warning-cookie)查看本指南。

## 公共工作站

CAS 能够允许用户选择退出 SSO，通过在登录页面上指示 身份验证发生在公共工作站。 通过选择这样做，中科院将不履行随后的SSO会议 ，也不会产生旨在这样做的TGC。

```html
...
<input id="publicWorkstation"
       name="publicWorkstation"
       value="false" tabindex="4"
       type="checkbox" />
<label for="publicWorkstation" th:utext="#{screen.welcome.label.publicstation}"/>
...
```

## 默认服务

如果没有向 CAS 提交 `服务` ，您可以指定 CAS 将重定向到的默认 服务网址。 请注意，此默认服务，就像 所有其他服务一样，必须得到 CAS 的授权和注册。

要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#views)查看本指南。

## 所需服务

CAS 可配置为要求用户从应用程序中进行身份验证，然后 才能授予所有其他注册服务的访问权限。 一旦 CAS 在单个登录会话记录中找到所需 申请的记录，它将允许所有其他服务 身份验证尝试，直到单个登录会话被销毁。

此类验证检查可以关闭并跳过每个应用程序：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^https://www.example.com"，
  "名称"："示例"，
  "id"：1、
  "描述"："示例"，
  "属性"：{
    "@class"："java.util.哈希马普"，
    "跳过服务要求检查"：{
      "@class"："org. apereo.cas.服务.默认注册服务专业"，
      "价值"："java.利用"，"哈希集"，"真实"]
    }
  }
}
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#global-sso-behavior)。
