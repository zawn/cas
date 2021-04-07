---
layout: 默认
title: CAS-配置服务复制
category: 服务
---

# 配置服务复制

[集中存储](Service-Management.html)全局管理CAS服务定义，则当部署多个节点时，需要在集群中的所有CAS节点中保持 当此类定义的管理策略是将它们存储在每个节点 [JSON](JSON-Service-Management.html) 或 [YAML](YAML-Service-Management.html)）文件 机制将文件从一台主机复制到另一台主机。

## 本国的

`rsync` 任务计划后台任务，以将文件从主机复制到另一个主机。 当然，该作业需要定期运行以确保配置保持同步。 这是最简单的选择，因为CAS完全不了解后台的额外处理。

在Linux机器上， `rsync` 可能安装为：

```bash
＃yum install rsync（在基于Red Hat的系统上）
＃apt-get install rsync（在基于Debian的系统上）
```

例如，此命令会将目录 `/ etc / cas / services` 从本地计算机同步到远程服务器：

```bash
rsync -avz / etc / cas / services root@192.168.0.101：/ etc / cas / services
```

与上述命令完全相反的是：

```bash
rsync -avzh root@192.168.0.100：/ etc / cas / services / etc / cas / services
```

- 要通过ssh执行传输操作，请使用 `ssh --progress` 标志。
- 要在模拟模式下测试命令执行，请使用 `--dry-run` 标志。

## 淡褐色

如果您不想使用外部工具和流程，或者如果 部署的本机选项不那么吸引人，则可以利用CAS自己的工具，该工具 分布式缓存，以在整个服务器上广播服务定义文件。集群，并根据需要添加/删除/更新 当服务定义由CAS加载时，事件会广播到 CAS节点，以获取更改并保持定义同步。

通过在叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-service-registry-stream-hazelcast</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry-replication-hazelcast)。

## 阿帕奇·卡夫卡（Apache Kafka）

如果您不想使用外部工具和流程，或者如果 部署的本机选项不那么吸引人，则可以利用CAS自己的工具，该工具 分布式缓存，以在整个服务器上广播服务定义文件。集群，并根据需要添加/删除/更新 当服务定义由CAS加载时，事件会广播到 CAS节点，以获取更改并保持定义同步。

通过在叠加层中包含以下依赖项来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-service-registry-stream-kafka</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry-replication-kafka)。

## 复制方式

当将CAS配置为以双活模式复制服务定义时，您将需要确保对服务注册表调度程序进行了仔细调整，以避免出现意外情况和覆盖。 同样，如果将CAS设置为监视更改，则需要进行相同类型的检查并验证对CAS服务注册表目录的临时动态更改。 复制和计划上的延迟可能会迫使一个节点覆盖对另一节点的更改。

例如，考虑以下情形：CAS群集中有两个节点，其中CAS1设置为监视 `/ etc / cas / services` 更改，而CAS2监视节点 `/ etc / cas / services` 目录N2 N1和N2在启动时都尝试自举彼此的服务定义副本，以确保正确同步所有服务定义。

现在，让我们考虑一个文件为 `/etc/cas/services/App-100.json` 从N2中删除。 从N2到N1广播更改时，N2的服务注册表调度程序也可能会唤醒，并尝试通过从分布式缓存中同步其服务定义文件的副本来尝试恢复世界的状态，这意味着N2将从N1获取已删除服务的副本，并将恢复已删除的文件。 当服务注册表调度程序设置为非常激进的超时时，通常会出现这种情况，并且可以通过放宽重新加载操作以使其在较长的调度程序上（例如每2小时运行一次）来避免这种情况。 或者，您可以决定运行主动-被动设置，以仅使一个主节点产生并广播更改，而仅使其他从/被动节点产生并广播更改，并且仅在需要时使用更改。
