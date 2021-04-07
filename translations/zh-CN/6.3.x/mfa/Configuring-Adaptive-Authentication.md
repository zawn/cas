---
layout: 默认
title: CAS-自适应身份验证
category: 多因素身份验证
---

# 自适应身份验证

CAS中的自适应身份验证允许您基于客户端浏览器和/或设备的 配置后，将为您提供阻止来自某些浏览器代理提交的特定位置的 例如，您可能认为从 `英国伦敦` 提交的身份验证请求为 可疑，或者您可能希望阻止从Internet Explorer提交的请求等。

还可以将自适应身份验证配置为根据特定的日期和时间触发多因素。 例如，您可能希望在选定的日期或当前时间在晚上11点之后或早上6点之前触发多因素。 可以将每个规则块分配给mfa提供程序，其中规则的成功匹配允许执行多因素触发器。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#adaptive-authentication)。

要启用自适应身份验证，您将需要允许CAS对身份验证请求进行地理定位。 要了解更多信息，请 [参见本指南](../installation/GeoTracking-Authentication-Requests.html)

## IP智能

CAS为您提供了检查客户端IP地址并决定是否应授予访问权限的功能。 对于检测bot，代理或VPN流量并保护您的部署免受欺诈，自动攻击，爬网程序等侵害，这可能是有用的

IP地址检查的结果可以禁止并请求该请求，允许其通过，也 以指示IP地址出现问题的可能性。 如果结果是排名分数，则将其与 进行比较，以确定请求是否可以继续进行。

可以在CAS设置中将禁止的IP地址定义为模式，也可以使用下面列出的策略对其进行检查。

### 休息

在 `GET` 请求下，客户端IP地址作为标头 `clientIpAddress` 预期结果状态代码如下：

| 代码           | 描述                                                |
| ------------ | ------------------------------------------------- |
| `401`， `403` | IP地址被禁止，该请求将被拒绝。                                  |
| `200`， `202` | 允许使用IP地址，请求可以继续进行。                                |
| 所有其他人        | 响应主体的分数应介于 `1` 和 `0`（`1 =禁止` 和 `0 =允许`），表明IP地址可疑。 |

### Groovy

可以使用Groovy脚本检查客户端IP地址，该脚本的轮廓应符合以下条件：

```groovy
导入org.apereo.cas.authentication.principal。*
导入org.apereo.cas.authentication。*
导入org.apereo.cas.util。*
导入org.apereo.cas.authentication.adaptive.intel。*

def run（Object [] args）{
    def requestContext = args[0]
    def clientIpAddress = args[1]
    def logger = args[2]
    logger.info（“提供的客户端IP地址为 ${clientIpAddress}”）

    if（ipAddressIsRejected（））
        return IPAddressIntelligenceResponse.banned（）

    返回IPAddressIntelligenceResponse.allows（）
}
```

### BlackDot IP英特尔

请 [查看此链接](https://getipintel.net/) 以获取更多信息。 对于大量查询，需要有效的订阅。

<div class="alert alert-warning"><strong>使用警告！</strong><p>这是一项免费服务，主要对开发，测试和演示有用。 此服务的生产部署 
需要预订，该预订可以处理预期的查询计数和负载。</p></div>

请注意，必须在联系人字段中使用经常检查的有效电子邮件，否则该服务可能会被禁用，恕不另行通知。 此外， **DO NOT** 每天超过 超过500个查询 & 每分钟15个查询。 有关更多信息，请参见 [FAQ](https://getipintel.net/#FAQ)

# 基于风险的认证

CAS能够跟踪和检查身份验证请求中的可疑行为。 要了解更多信息，请 [参见本指南](../installation/Configuring-RiskBased-Authentication.html)。
