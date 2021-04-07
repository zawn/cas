---
layout: 违约
title: CAS - 码头安装
category: 安装
---

# 码头安装

每次发布 CAS 软件时，码头工人图像都会被标记并 推至 [码头中心](https://hub.docker.com/r/apereo/cas/)上的 Apereo CAS 存储库。 图像可以通过以下命令向下拉：

```bash
多克拉阿佩雷奥/卡斯：v[A.B.C]
```

...其中 `[A.B.C]` 表示映射到CAS服务器版本的图像标记。

## 概述

拼凑的 CAS 部署是由 Docker 包裹的现有 [CAS 叠加项目](WAR-Overlay-Installation.html) 。 覆盖项目已经包括一个嵌入式容器，以处理 CAS 的部署。 叠加项目还包括一个嵌入式构建工具，以便 CAS 的构建和部署不需要单独的步骤来下载和配置选项。

在 Docker 中心托管的 Docker 图像 *大部分是* 打算 用作快速启动和演示。 您还可以将它们用作 基础图像，将自定义添加到图像中。 图像 是由现有的 [中科院覆盖](WAR-Overlay-Installation.html)。
