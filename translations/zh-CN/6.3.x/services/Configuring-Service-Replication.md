---
layout: 违约
title: CAS - 配置服务复制
category: 服务业
---

# 配置服务复制

如果 CAS 服务定义不是通过 [集中存储存储](Service-Management.html)在全球管理的，则部署多个节点时，需要在整个群集中的所有 CAS 节点中保持 定义同步。 当此类定义的管理策略是将它们存储在磁盘上，以 每个节点（如 [JSON](JSON-Service-Management.html) 或 [YAML](YAML-Service-Management.html)）文件时， 以下机制可用于将文件从一个主机复制到另一个主机。

## 本地

背景任务可以与 `rsync` 等一起安排，以便将文件从主机复制到另一个主机。 当然，该作业需要定期运行，以确保配置保持同步。 这是最简单的选择，因为CAS完全不知道背景中的额外过程。

在 Linux 机器上，可以安装 `rsync` ：

```bash
#yum安装rsync（基于红帽子的系统）
#容易安装rsync（基于Debian的系统）
```

例如，此命令将同步目录 `/等/cas/服务，` 从本地计算机到远程服务器：

```bash
rsync - avz /等/cas/服务root@192.168.0.101：/等/cas/服务
```

上述命令的完全相反，可以这样进行：

```bash
rsync - avzh root@192.168.0.100：等/卡斯/服务/等/cas/服务
```

- 要执行传输操作，请使用 `ssh - 进度` 标志。
- 要在模拟模式下测试命令执行，请使用 `-干运行` 标志。

## 黑兹尔卡斯特

如果您不想使用外部手件和流程，或者如果您的 部署的原生选项没有那么吸引人，您可以利用 CAS 自己的模具，通过 Hazelcast 提供 分布式缓存，以便在群集中广播服务定义文件，并根据需要 每个节点添加/删除/更新。 当 CAS 加载服务定义时，事件会广播到群集中的所有 CAS 节点，以接收更改并保持定义同步。

支持通过在叠加中包括以下依赖关系而启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持服务 - 注册表流 - 哈泽尔卡斯特</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry-replication-hazelcast)。

## 阿帕奇·卡夫卡

如果您不想使用外部手件和流程，或者如果您的 部署的原生选项没有那么吸引人，您可以利用 CAS 自己的模具，通过 Apache Kafka 提供 分布式缓存，以便在群集中广播服务定义文件，并根据需要 每个节点添加/删除/更新。 当 CAS 加载服务定义时，事件会广播到群集中的所有 CAS 节点，以接收更改并保持定义同步。

支持通过在叠加中包括以下依赖关系而启用：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 服务 - 注册 - 流 - 卡夫卡</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry-replication-kafka)。

## 复制模式

当 CAS 被配置为在主动模式下复制服务定义时，您需要确保服务注册表调度器经过仔细调整，以避免意外和覆盖。 同样，如果 CAS 设置为监视更改，则需要对 CAS 服务注册表目录的临时动态更改进行相同的检查和验证。 复制和计划延迟可能会迫使一个节点覆盖对另一个节点的更改。

例如，考虑以下情景：CAS 群集中有两个节点，CAS1 设置用于监视节点 N1 上的 `/等/cas/服务` 的变化，CAS2 正在监视节点 N2 上的 `/等/cas/服务` 目录。 启动时，N1 和 N2 都尝试引导彼此的服务定义副本，以确保所有副本都正确同步。

现在，让我们考虑一个文件是 `/等/cas/服务/App-100.json` 从N2删除。 在从 N2 广播更改到 N1 的时间中，N2 的服务注册表调度器可能也会唤醒并尝试通过从分布式缓存同步其服务定义文件副本来恢复世界状态，这意味着 N2 将从 N1 中获取已删除服务的副本，并将删除的文件还原。 这种情况通常表现为当服务注册表调度器设置为非常积极的超时，并且主要可以通过放松重新加载操作以运行在长调度器上（如每 2 小时）来避免。 或者，您可能决定运行主动被动设置，仅让一个主节点产生和广播更改和其他奴隶/被动节点，并且仅在需要时使用更改。
