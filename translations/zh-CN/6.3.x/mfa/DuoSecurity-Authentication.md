---
layout: 默认
title: CAS-Duo安全认证
category: 多因素身份验证
---

# 双重安全认证

[Duo Security](https://www.duo.com) 是一个两步验证服务，它为访问机构和个人数据

Duo提供了几种用于验证用户身份的选项：

- 移动推送通知和对智能手机的一键式身份验证（需要免费的Duo Mobile应用）
- 在智能手机上生成的一次性代码
- 由Duo生成并通过短信发送到手机的一次性代码
- 从那里打来的电话会提示您确认登录请求

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-duo</artifactId>
     <version>${cas.version}</version>
</dependency>
```

您可能需要将以下存储库添加到WAR叠加层中：

```groovy
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://dl.bintray.com/uniconiam/maven” 
    }
}
```

## 行政端点

CAS提供了以下端点：

| 终点                              | 描述                                                       |
| ------------------------------- | -------------------------------------------------------- |
| `多平`                            | 对Duo Security API进行Ping检查服务可用性。 可以接受可选的 `providerId` 参数。 |
| `duoAccountStatus /${username}` | 获取所请求用户名的帐户状态。 可以接受可选的 `providerId` 参数。                  |

## 多个实例

CAS对Duo Security的多因素身份验证支持允许为 多个Duo提供程序配置不同的ID（每个ID为 ，这些ID可以通过不同的配置连接到单独的Duo Security实例。 此行为允许将更敏感的应用程序 连接到具有更严格和安全的身份验证策略的Duo实例。

为了使此行为起作用，需要将您自己选择的单独的唯一ID分配给每个Duo Security 提供程序。 每个提供程序实例都已在CAS中注册，并在必要时 如果只有一个Duo实例可用，则不需要定义提供者ID。

## 用户帐号状态

如果用户未在Duo Security中注册或通过直接旁路被允许，则 CAS将向Duo Security查询用户帐户的先验知识，以了解 用户是否已注册或配置为直接旁路。 如果将帐户配置为直接绕过，或者 用户帐户，但是新用户注册策略允许用户跳过注册，则CAS将 Duo Security，并且不会挑战用户，并且还会报告 **NOT** 将启用了多因素的 身份验证上下文返回给应用程序。

<div class="alert alert-warning"><strong>青年汽车</strong><p>在最近与Duo Security的对话中，结果 
表示API行为已更改（出于安全原因），在该情况下，API行为可能不再准确 
报告了帐户状态。 这意味着即使上述条件成立，CAS仍可以继续 
路由到从API接收到资格状态的Duo Security。 据报道Duo Security为 
正在进行修复，以更安全的方式还原API行为。 同时，YMMV。</p></div>

## 健康状况

CAS可以根据需要与Duo Security联系，以便使用Duo Security的 `ping` API查询服务的健康状态。 [CAS监视端点](../monitoring/Monitoring-Statistics.html)提供的 `运行状况` 端点 记录和报告操作结果。 当然，整个Duo身份验证流程中的相同结果也可用于确定失败模式。

## 通用提示

[Duo OIDC Auth API](https://duo.com/docs/oauthapi)的Duo Multifactor Authentication的变体。 这是 是基于OIDC标准的API，用于向CAS添加强大的两因素身份验证。 此选项不再在CAS控制和拥有的iFrame中 而是，提示现在托管在Duo的服务器上，并通过浏览器重定向显示。 来自Duo Security的 响应将作为浏览器重定向传递给CAS，CAS将开始协商并交换该响应，以 为佳，其中包含多因素身份验证用户配置文件详细信息的JWT。

该选项仅需要集成密钥，私有密钥和API主机名的设置。 要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#duosecurity)。

## 非浏览器MFA

CAS的Duo Security模块还能够支持 [个基于非浏览器的多因素身份验证](https://duo.com/docs/authapi) 请求。 为了触发此行为，应用程序（例如 `curl`，REST API等）需要指定特殊的 `Content-Type` 以向CAS发出从非基于Web的环境提交请求的信号。 多因素身份验证请求是在 `自动` 模式下提交给Duo Security</a>

，实际上可以将 转换为Duo建议的最适合用户设备的带外因素（推送或电话）。</p> 

为了成功完成身份验证流程，CAS还必须配置 ，该方法能够支持非基于Web的环境 例如 [基本身份验证](../installation/Basic-Authentication.html)。

`curl` 的示例，该示例 基本身份验证，同时将请求内容类型标识为 `application / cas`来尝试向服务进行身份验证。 假定 服务在CAS中配置了特殊的多因素策略，该策略也强制流 也通过Duo Security。



```bash
curl --location --header“内容类型：application / cas” https://apps.example.org/myapp -L -u casuser：Mellon
```




## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#duosecurity)。



## 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下 级：



```xml
...
<Logger name="com.duosecurity" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```


您还应该使用NTP来确保CAS服务器的时间正确。 此外，CAS通常在TCP端口443上 每个Duo Security不建议使用使用目标IP地址或IP地址范围的规则将防火墙限制出站访问 防火墙配置可能会随着时间的推移而改变，以保持我们服务的高可用性。
