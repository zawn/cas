---
layout: 违约
title: 中科院 - 监测 & 统计
category: 监测 & 统计
---

# 监测/统计

用于监控和诊断 CAS 服务器内部配置的执行器端点通常 暴露在端点 `/执行器`上。

## 弹簧启动端点

下列端点由 [春靴执行器](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)固定和提供：

| 端点       | 描述                                                     |
| -------- | ------------------------------------------------------ |
| `自动侦察`   | 描述 CAS 应用程序上下文是如何自动配置的。                                |
| `豆`      | 显示所有 CAS 应用上下文 **内部** 弹簧豆。                             |
| `条件`     | 显示在配置和自动配置类上评估的条件，以及它们匹配或不匹配的原因。                       |
| `配置程序`   | **内部** 配置属性列表。                                         |
| `螺纹凹槽`   | 为正在运行的 CAS 服务器生成线程转储。                                  |
| `恩夫`     | 生成所有应用属性的集合。                                           |
| `健康`     | 报告系统的总体健康状况，由各种监测员制作。                                  |
| `信息`     | CAS 版本信息和其他系统特征。                                       |
| `指标`     | 运行时间指标和统计数据。                                           |
| `赫特特拉克`  | 显示 HTTP 跟踪信息（默认情况下，最后 100 个 HTTP 请求响应交换）。              |
| `映射`     | 描述 CAS 如何映射和处理请求。                                      |
| `预定任务`   | 在 CAS 中显示预定任务。                                         |
| `关闭`     | 通过 `邮政`关闭应用程序。 默认情况下已禁用。                               |
| `重新启动`   | 通过 `邮政`重新启动应用程序。 默认情况下已禁用。                             |
| `刷新`     | 通过 `POST` 刷新应用程序配置，让组件重新加载并识别新值。                       |
| `堆堆`     | 返回一个GZip压缩的hprof堆转储文件。                                 |
| `乔洛基亚`   | 当乔洛基亚被配置并包含在 CAS 中时，将 JMX 豆暴露在 HTTP 上。                 |
| `日志文件`   | 如果 `记录.文件` 或 `日志，则返回日志文件的内容。 路径` 属性设置为支持 HTTP `范围` 标题。 |
| `普罗 米修斯` | 以普罗米修斯服务器可以刮掉的格式显示指标。                                  |

<div class="alert alert-info"><strong>暴露的端点</strong><p>
请注意，默认情况下，在网络上暴露的唯一端点是 <code>信息</code>， <code>状态</code>， <code>健康</code> 和 <code>配置Metadata</code>。
其他端点需要明确 <strong>启用，然后在 CAS 设置中的 Web</strong> 上暴露，以便访问。
</p></div>

春靴提供的执行器端点也可以通过 [春靴管理服务器](Configuring-Monitoring-Administration.html)进行目视管理和监控 。
<div class="alert alert-info"><strong>获取健康信息</strong><p>请注意， <code>/状态</code> 端点大多保留 
作为传统端点。 如果您希望详细了解每个监视器的健康状况，我们建议 <code>/执行器/健康</code> 终点。</p></div>

## CAS 终点

CAS 执行器端点的默认集可以通过在 WAR 叠加中包含以下依赖项来上交：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>的案例服务器支持报告</artifactId>
    <version>${cas.version}</version>
</dependency>
```

请注意，某些 CAS 功能提供自己的执行器端点，并且这些端点只有在功能打开并在运行时间提供给 CAS 后才会成为活动 。 欲了解更多信息，请参阅特定的 文档页面，了解有关功能，以了解有关管理端点等的更多信息。

## 指标

指标允许深入了解运行中的 CAS 软件，并提供测量关键组件行为的方法。 有关详细信息，请参阅本指南</a>。</p> 

导航到 `/执行器/指标` 显示可用仪表名称列表。 您可以通过提供其作为选择器的名称来向下钻取有关 特定仪表的信息，例如 `/执行器/指标/jvm.内存.max`.  此处使用的名称应与代码中使用的名称 匹配，而不是在代码已为其发送到的监控系统进行命名约定规范化后匹配名称。

您还可以添加任意数量的 `标签=KEY：` 查询参数添加到 URL 的末端，以便在仪表上 进行尺寸钻孔，例如 `/执行器/指标/jvm.内存.max标签=区域：非堆`

所报告的测量值是与仪表名称和已应用的任何标签匹配的所有仪表的统计数据的总和。 因此，在上面的示例中，返回的"值"统计是堆中"代码缓存"、" 压缩类空间"和"元空间"区域的最大内存足迹的总和。 如果你只是想看到"元空间"的最大尺寸， 你可以添加一个额外的 `标签=id：元空间`，即。 `/执行器/指标/jvm.内存.max标签=区域：非堆&标签=id：元空间`。



## 安全

一旦端点启用并暴露，所有提供的端点的安全性由 [春季安全](https://spring.io/projects/spring-security) 处理。 保护和访问每个端点 通过 CAS 设置单独控制，以便您可以独立决定每个端点的特定安全级别和身份验证方法。

如果 CAS 被配置为 *而不是* 执行端点安全规则，则所有端点都被视为敏感，需要身份验证，通常通过基本身份验证处理 ，并在 CAS 设置中定义主凭据。 

如果 CAS 被配置以执行端点安全规则，则每个端点可能会标记特定的安全规则，允许通过授权的 IP 地址访问， 基本凭据、角色和属性等。 

身份验证凭据通常通过 CAS 设置进行控制。 对于基本身份验证，默认用户名 `卡苏`。 如果在 CAS 设置中未定义密码，则 密码可以在启动时自动生成并显示在 CAS 日志中。 也可以定义其他来源 ，以便通过 JAAS、LDAP、JDBC 等对请求进行身份验证。

根据访问方法和呼叫者与 CAS 之间协商的 `内容类型` （即基于 Web 的与命令线访问）， 凭据可以通过 `卷曲` 和家庭以标题形式提供，也可以输入基于 Web 的登录表单。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#actuator-management-endpoints)。



### 故障 排除

要启用其他记录，请配置 log4j 配置文件以添加以下 级别：



```xml
...
<Logger name="org.pac4j" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
<Logger name="org.springframework.security" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```




## 分布式追踪

支持对请求的分布式跟踪，通过在 WAR 叠加中包括以下依赖性来实现：



```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 侦探</artifactId>
     <version>${cas.version}</version>
</dependency>
```


![图像](https://cloud.githubusercontent.com/assets/1205228/24955152/8798ad9c-1f97-11e7-8b9d-fccc3c306c42.png)

对于大多数用户来说 [侦探](https://cloud.spring.io/spring-cloud-sleuth/) 应该是不可见的，所有与外部系统 交互都应该自动进行仪器化。

跟踪数据会自动捕获并传递给 [Zipkin](https://github.com/openzipkin/zipkin)，这有助于 收集排除延迟问题所需的时间数据。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#sleuth-distributed-tracing)。



### 故障 排除

要启用其他日志，请修改记录配置文件以添加以下：



```xml
 <Logger name="org.springframework.cloud" level="debug" additivity="false">
    <AppenderRef ref="casConsole"/>
    <AppenderRef ref="casFile"/>
</Logger>
```

