---
layout: 违约
title: CAS - 双安全认证
category: 多因素认证
---

# 双安全认证

[二重奏安全](https://www.duo.com) 是一个两步验证服务，为访问机构和个人数据提供了 额外的安全。

Duo 提供多个选项来验证用户身份验证：

- 移动推送通知和智能手机身份的单按钮验证（需要免费的 Duo 移动应用程序）
- 在智能手机上生成的一次性代码
- Duo 生成的一次性代码，并通过短信发送到手机
- 从该电话将提示您验证登录请求

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡 - 服务器支持 - 杜</artifactId>
     <version>${cas.version}</version>
</dependency>
```

您可能需要将以下存储库添加到 WAR 叠加：

```groovy
存储库{
    马文{ 
        马文康森特{发布（）=
        网址"https://dl.bintray.com/uniconiam/maven" 
    }
}
```

## 行政终点

CAS 提供以下端点：

| 端点                  | 描述                                    |
| ------------------- | ------------------------------------- |
| `双平`                | 平双安全API，以检查服务可用性。 可以接受可选的 `提供商Id` 参数。 |
| `双帐户状态/${username}` | 获取所请求用户名的帐户状态。 可以接受可选的 `提供商Id` 参数。    |

## 多个实例

CAS 对 Duo 安全性的多因素身份验证支持允许 多个 Duo 提供商配置每个 可连接到具有不同配置的单独的 Duo 安全实例的不同 id。 此行为允许将更敏感的应用程序 连接到具有更严格和安全身份验证策略的 Duo 实例。

要使此行为正常工作，需要将您自己选择的单独 ID 分配给每个 Duo 安全 提供商。 每个提供商实例均在 CAS 注册，并在必要时在身份验证 流中激活。 如果只有一个可用的单个二重奏实例，则无需定义提供商 ID。

## 用户帐户状态

如果用户未注册 Duo Security 或允许通过直接旁路通过， CAS 将查询 Duo 安全，以了解用户帐户的 apori，了解用户是否已注册或配置为直接旁路 。 如果该帐户被配置为直接旁路或 用户帐户尚未注册，但新用户注册策略允许用户跳过注册，CAS 将完全绕过 Duo Security，并且不会对用户提出质疑，并且 **不会** 将支持多因素的 身份验证上下文报告回应用程序。

<div class="alert alert-warning"><strong>基督教青年会</strong><p>在最近与 Duo Security 的对话中， 
事实证明 API 行为（出于安全原因）发生了变化，可能不再准确 
报告回账帐户状态。 这意味着，即使上述条件成立，CAS 也可以继续将用户 
从 API 获得资格状态的 Duo Security 进行路由。 据报道，Duo 安全部门正在 
修复工作，以更安全的方式恢复 API 行为。 同时，YMMV。</p></div>

## 健康状况

中科院可根据需要联系双安，使用双安 `平` API查询服务的健康状况。 手术结果使用 [中科院监测终端](../monitoring/Monitoring-Statistics.html)提供的 `健康` 端点 进行记录和报告。 当然，在整个 Duo 身份验证流程中，相同的结果也用于确定故障模式。

## 通用提示

通用提示是二重多因子身份验证的一种变体，它使用 [二重奏OIDC Auth API](https://duo.com/docs/oauthapi)。 这 OIDC 基于标准的 API，用于为 CAS 添加强大的双重身份验证。 此选项不再在 CAS 控制和拥有的 iFrame 中显示双提示 。 相反，该提示现在托管在 Duo 的服务器上，并通过浏览器重定向显示。 Duo Security 的 响应将作为浏览器重定向传递给 CAS，CAS 将开始谈判和交换该响应，以 包含多因素身份验证用户配置文件详细信息的 JWT。

此选项仅需要集成密钥、密钥和 API 主机名的设置。 要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#duosecurity)。

## 非浏览器 MFA

CAS 的 Duo 安全模块还能够支持基于 [基于浏览器的多因素身份验证](https://duo.com/docs/authapi) 请求。 为了触发此行为，应用程序（即 `卷发`、REST ABI 等）需要指定一个特殊的 `内容类型` ，以向 CAS 发出请求来自非基于 Web 的环境中的信号。 多因素身份验证请求 [以 `自动` 模式提交给 Duo 安全](https://duo.com/docs/authapi#/auth) ，该模式可以有效地 转化为 Duo 推荐的带外因子（推送或电话），以最适合用户的设备。

为了顺利完成认证流程，中科院还必须配置一种初级认证 方法， 如 [基础认证](../installation/Basic-Authentication.html)等支持非基于 Web 的环境。

下面是一个使用 `卷发` 示例，该尝试通过首先进行 基本身份验证来验证服务，同时将请求内容类型识别为 `应用程序/cas`。 据推测，下面的 服务配置在中科院与一个特殊的多因素政策，迫使流动 通过双安全以及。

```bash
卷曲 - 位置 - 标题 "内容类型： 应用程序 / cas" https://apps.example.org/myapp - L - u 卡苏瑟： 梅隆
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#duosecurity)。

## 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下 级别：

```xml
...
<Logger name="com.duosecurity" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

您还应使用 NTP 来确保 CAS 服务器的时间正确。 此外，CAS 通常会在 TCP 端口 443 上与 Duo 的服务进行通信。 不建议使用目的地 IP 地址或 IP 地址范围使用规则访问 Duo 服务的防火墙配置， ，因为这些配置可能会随着时间的推移而改变，以保持我们的服务的高可用性。
