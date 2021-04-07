---
layout: 默认
title: CAS-指标
category: 监控 & 统计
---

# CAS指标

适用时，通过Spring Boot的CAS将注册以下核心指标：

JVM指标，报告以下方面的利用率：

- 各种内存和缓冲池
- 与垃圾收集有关的统计数据
- 线程利用率
- 加载/卸载的类数
- CPU指标
- 文件描述符指标
- Logback指标：记录每个级别记录到Logback的事件数
- 正常运行时间指标：报告正常运行时间的量度和代表应用程序绝对启动时间的固定量度
- Tomcat指标
- Spring集成指标

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-metrics</artifactId>
     <version>${cas.version}</version>
</dependency>
```

通过自动配置，可以使用 `jdbc`的度量 进行检测。 数据源检测产生的量规代表 当前活动连接，最大允许连接数和最小允许连接数。 这些仪表中的每一个都有一个以 `jdbc`开头的名称。 度量标准还通过基于Bean名称计算 `DataSource` 同样，特定于Hikari的指标以 `hikaricp` 前缀公开。 每个度量标准均以池名称标记。

通过自动配置，可以在启动时使用前缀为缓存的指标来检测所有可用的缓存。 高速缓存检测针对一组基本指标进行了标准化。 还提供其他特定于缓存的指标。

`Rabbitmq`的度量的可用RabbitMQ连接工厂的检测。

使用CAS执行器管理端点可以访问和查询CAS度量。 导航到端点将显示可用仪表名称的列表。 您可以通过提供特定名称作为选择器来深入查看有关特定计量器的信息。

[请参阅本指南](Monitoring-Statistics.html) 了解更多信息。

## 行政端点

CAS提供了以下端点：

| 终点     | 描述                             |
| ------ | ------------------------------ |
| `统计数据` | 公开有关票证，内存，服务器可用性和正常运行时间等的统计数据。 |

## 自定义指标

要注册自定义指标，请将 `` 注入到您的组件中，如以下示例所示：

```java
公共类Dictionary {
    private final List<String> 单词= new CopyOnWriteArrayList<>（）;

    字典（最终的MeterRegistry注册中心）{
        Registry.gaugeCollectionSize（“ dictionary.size”，Tags.empty（），this.words）;
    }
}
```

如果你发现你一再仪器跨组件或应用程序套件的指标， 您可以封装这套房在 `MeterBinder` 执行。 默认情况下，所有 `MeterBinder` 将自动绑定到Spring管理的 `MeterRegistry`。

# 自定义指标

如果需要将自定义应用于特定的仪表实例，则可以使用 `io.micrometer.core.instrument.config.MeterFilter` 界面。 默认情况下，所有 `MeterFilter` bean都将自动应用于千分尺 `MeterRegistry.Config`。

`com.example`开头的仪表ID，如果要将 `mytag.region` 标签 `mytag.area` ，则可以执行以下操作：

```java
@Bean
public MeterFilter namedRegionTagMeterFilter（）{
    返回MeterFilter.renameTag（“ com.example”，“ mytag.region”，“ mytag.area”）;
}
```

## 贮存

CAS指标可以路由到各种类型的数据库以进行存储和分析。 提供以下选项：

- 简单（在内存中）
- 石墨
- 神经节
- JMX
- 阿特拉斯
- 信号效果
- 统计
- InfluxDb
- 普罗米修斯
- 波前
- 新遗物
- CloudWatch
- ...

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#metrics)。
