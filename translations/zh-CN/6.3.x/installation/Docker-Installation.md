---
layout: 默认
title: CAS-Docker安装
category: 安装
---

# Docker安装

在每次发布CAS软件时，都会标记 [Docker Hub](https://hub.docker.com/r/apereo/cas/)上的Apereo CAS存储库中。 可以通过以下命令下拉图像：

```bash
docker pull apereo / cas：v[A.B.C]
```

...其中 `[A.B.C]` 代表映射到CAS服务器版本的图像标签。

## 概述

Docker化的CAS部署是由Docker包装 [CAS覆盖项目](WAR-Overlay-Installation.html) 覆盖项目已经包含一个嵌入式容器来处理CAS的部署。 覆盖项目还包括一个嵌入式构建工具，因此CAS 构建和部署将不需要单独的步骤来下载和配置选择。

托管在Docker Hub上的Docker映像为 *多数为* 意在用作 快速入门和演示。 您也可以将它们用作 基本图像，以将自定义内容添加到图像中。 图像 是基于现有的 [CAS覆盖](WAR-Overlay-Installation.html)。
