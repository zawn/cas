---
layout: 默认
title: CAS-监控 & 统计
category: 监控 & 统计
---

# 监控/统计

用于监视和诊断CAS服务器内部配置的执行器端点通常在端点 `/执行器` 。

## Spring Boot端点

以下端点由 [Spring Boot执行器](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)保护并可用：

| 终点            | 描述                                                               |
| ------------- | ---------------------------------------------------------------- |
| `自动配置`        | 描述如何自动配置CAS应用程序上下文。                                              |
| `豆子`          | 显示所有CAS应用程序上下文 **内部** Spring Bean。                               |
| `条件`          | 显示在配置和自动配置类上评估的条件以及它们匹配或不匹配的原因。                                  |
| `configprops` | **内部** 配置属性的列表。                                                  |
| `线程转储`        | 为正在运行的CAS服务器生成线程转储。                                              |
| `环保`          | 产生所有应用程序属性的集合。                                                   |
| `健康`          | 报告由各种监视器生成的系统的总体健康状况。                                            |
| `信息`          | CAS版本信息和其他系统特征。                                                  |
| `指标`          | 运行时指标和统计信息。                                                      |
| `httptrace`   | 显示HTTP跟踪信息（默认情况下，最近100个HTTP请求-响应交换）。                             |
| `映射`          | 描述CAS如何映射和处理请求。                                                  |
| `计划任务`        | 在CAS中显示计划的任务。                                                    |
| `关掉`          | `POST`关闭应用程序。 默认禁用。                                              |
| `重新开始`        | `POST`重新启动应用程序。 默认禁用。                                            |
| `刷新`          | `POST` 刷新应用程序配置，以使组件重新加载并识别新值。                                   |
| `堆转储`         | 返回GZip压缩的hprof堆转储文件。                                             |
| `乔洛基亚`        | 配置Jolokia并将其包含在CAS中时，将通过HTTP公开JMX bean。                          |
| `日志文件`        | `logging.file` 或 `logging.path` 属性并支持HTTP `Range` 标头，则返回日志文件的内容。 |
| `普罗米修斯`       | 以Prometheus服务器可以抓取的格式公开指标。                                       |

<div class="alert alert-info"><strong>暴露的端点</strong><p>
注意，默认情况下暴露在纸幅的唯一端点是 <code>信息</code>， <code>状态</code>， <code>健康</code> 和 <code>configurationMetadata</code>。
其他端点需要显式 <strong>，然后在CAS设置中</strong>
</p></div>

由Spring提供的引导驱动器终端也可以直观地管理和监控 通过 [春天启动管理服务器](Configuring-Monitoring-Administration.html)。
<div class="alert alert-info"><strong>获取健康信息</strong><p>请注意， <code>/ status</code> 端点大多数保留为 
作为旧式端点。 如果希望详细获取每个监视器的运行状况，我们建议 <code>/ actuator / health</code> 终结点。</p></div>

## CAS端点

可以通过在WAR叠加中包括以下依赖项来输入默认的CAS执行器端点集：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas服务器支持报告</artifactId>
    <version>${cas.version}</version>
</dependency>
```

请注意，某些CAS功能提供了它们自己的执行器端点，并且只有在启用该功能并在运行时将其提供给CAS后 有关更多信息，请参阅特定功能的 文档页面，以了解有关管理端点等的更多信息。

## 指标

度量标准可让您深入了解正在运行的CAS软件，并提供衡量关键组件行为的方法。 有关更多信息，请参见 [本指南](Configuring-Metrics.html)

导航到 `/ actuator / metrics` 显示可用仪表名称的列表。 您可以通过提供其名称作为选择器来深入查看有关 `/actuator/metrics/jvm.memory.max`。  您在此处使用的名称应与 匹配，而不是与已针对其出厂的监视系统进行标准化命名约定的名称匹配。

您还可以在URL末尾添加任意数量的 `tag = KEY：VALUE` 查询参数，以在仪表上 `/actuator/metrics/jvm.memory.max?tag=area:nonheap`

报告的测量值是与仪表名称和已应用的任何标签相匹配的所有仪表的统计信息的总和。 因此，在上面的示例中，返回的“值”统计量是 “压缩类空间”和“元空间”区域的最大内存占用量的总和。 如果您只想查看“ Metaspace”的最大大小，则可以添加 ，即 `tag = id：Metaspace` `/actuator/metrics/jvm.memory.max?tag=area:nonheap&tag = id：Metaspace`。

## 安全

一旦端点被启用，暴露，都提供了端点的安全处理 由 [的Spring Security](https://spring.io/projects/spring-security)。 保护和访问，以便您可以分别确定每个端点的特定安全级别和身份验证方法。

如果将CAS配置为 *NOT* 强制执行端点安全规则，那么所有端点都被视为敏感端点，并且需要身份验证，通常通过具有CAS设置中定义的主凭据的基本身份验证

如果将CAS配置为强制执行端点安全性规则，则可以使用允许通过授权IP地址， 基本凭据，角色和属性等进行访问的特定安全性规则标记每个端点。

身份验证凭据通常通过CAS设置进行控制。 对于基本身份验证，默认用户名是 `casuser`。 如果未在CAS设置中定义密码 则密码0可能会在启动时自动生成并显示在CAS日志中。 还可以将其他来源定义为 ，以通过JAAS，LDAP，JDBC等对请求进行身份验证。

根据访问方法和 `内容类型` （即基于Web的访问与基于命令行的访问）， `curl` 和family在标头中提供 输入基于Web的登录表单。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#actuator-management-endpoints)。

### 故障排除

要启用其他日志记录，请配置log4j配置文件以添加以下 级：

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

## 分布式跟踪

通过在WAR覆盖中包含以下依赖关系，可以支持对请求的分布式跟踪：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-sleuth</artifactId>
     <version>${cas.version}</version>
</dependency>
```

![图像](https://cloud.githubusercontent.com/assets/1205228/24955152/8798ad9c-1f97-11e7-8b9d-fccc3c306c42.png)

对于大多数用户， [Sleuth](https://cloud.spring.io/spring-cloud-sleuth/) 应该是不可见的，并且应该自动检测与外部系统的

跟踪数据将自动捕获并传递给 [Zipkin](https://github.com/openzipkin/zipkin)，这有助于 收集解决延迟问题所需的时序数据。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#sleuth-distributed-tracing)。

### 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：

```xml
 <Logger name="org.springframework.cloud" level="debug" additivity="false">
    <AppenderRef ref="casConsole"/>
    <AppenderRef ref="casFile"/>
</Logger>
```

