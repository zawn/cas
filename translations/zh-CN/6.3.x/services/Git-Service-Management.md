---
layout: 违约
title: CAS - Git 服务注册处
category: 服务业
---

# 吉特服务注册处

此注册表读取远程或本地 git 存储库的服务定义。 服务定义文件预计将 [JSON](JSON-Service-Management.html) 或 [YAML](YAML-Service-Management.html) 文件。 存储库的内容按定义的间隔拉取，并致力于对服务定义进行更改，并 推至定义的遥控器。

支持通过在覆盖中添加以下模块来实现：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-git-服务-注册</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#git-service-registry)。

<div class="alert alert-warning"><strong>无干扰！</strong><p>
小心不要手动修改本地服务器上克隆的 git 存储库目录的状态。 这样做，您可能会 
干扰 CAS 自身的服务管理流程，最终可能会损坏 git 存储库的状态。
</p></div>

服务注册表还能够自动检测更改，因为它会定期从定义的遥控器中提取更改。 它将监控更改以识别 文件添加、删除和更新，并将自动刷新 CAS，以便更改可能立即发生。
