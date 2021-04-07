---
layout: 默认
title: CAS-Git服务注册中心
category: 服务
---

# Git服务注册中心

该注册表从远程或本地git存储库读取服务定义。 服务定义文件应为 （ [JSON](JSON-Service-Management.html) 或 [YAML](YAML-Service-Management.html) 文件）。 将按定义的时间间隔提取存储库的内容，并提交对服务定义的更改，并将 推送到已定义的远程服务器。

通过将以下模块添加到叠加层来启用支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-git-service-registry</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#git-service-registry)。

<div class="alert alert-warning"><strong>无干扰！</strong><p>
注意不要手动修改在本地服务器上克隆的git信息库目录的状态。 这样做会冒 
干扰CAS自己的服务管理流程的风险，并最终可能破坏git存储库的状态。
</p></div>

服务注册表还能够自动检测到更改，因为它将定期从定义的遥控器中提取更改。 它将监视更改以识别 文件的添加，删除和更新，并将自动刷新CAS，因此更改可能立即发生。
