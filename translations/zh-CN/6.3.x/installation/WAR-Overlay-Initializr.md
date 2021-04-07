---
layout: 违约
title: Cas - 战争覆盖初始
category: 安装
---

# 战争覆盖初始

[Apereo CAS 初始][initializr] 是 Apereo CAS 生态系统的一个相对较新的补充，它允许您作为部署者 ，以快速启动所需的操作在飞行中生成 CAS WAR 覆盖项目。

要从 CAS 部署开始，采用者通常 从 [基于 Gradle 的普通覆盖项目开始，](WAR-Overlay-Installation.html) 并以此为基准进行未来的修改。 虽然多年来，这是传统和推荐的 方法，但对于一个对 生态系统较陌生的相对新手用户来说，下载、修改和准备覆盖项目，以包括所有必要的 定制，也相当具有挑战性。 鉴于叠加项目的静态性质， 项目所有者和维护人员在不影响基线模板的情况下保持最新状态或提供 和自动化的其他增强功能也具有挑战性。

为了应对此类情况， [CAS WAR 叠加初始][initializr] 提供了在 CAS 部署所需的所有依赖项和模块中提取 的快速方法， 提供友好且程序化的卷曲友好型 API，以生成 覆盖结构和所需的构建文件。

处理项目生成 任务的基本框架基于 [春季初始](https://github.com/spring-io/initializr)。

## 概述

CAS 初始子可以根据部署所需的 请求的模块和依赖项动态生成启动项目。 此行为 可以根据该输入和随后的 条件根据用户的体验进行定制，以生成其他引用、文件、启动 模板，以及同一项目中的更多配置过程，使部署过程更加舒适。

CAS初始在这一点上主要是一个后端服务和一些API。 然而，可以想象，图形和现代用户界面 可以建立在可用的API之上，以帮助项目 生成任务，特别是对于项目新人。

管理和维护单独的叠加项目，并使它们与各种 CAS 版本保持 同步，可能是一项昂贵的维护任务。 CAS 初始化使项目开发人员能够自动化 维护任务，将所有内容保持在同一存储库 中，以实现更快、更准确的升级。

<div class="alert alert-info"><strong>注意</strong>
<p>请记住，CAS 初始在此时无法 
为 CAS 管理 Web 应用程序生成覆盖项目。 此 
功能将在未来的版本中制定出来。</p></div>

CAS 初始子由 CAS 项目本身内部使用，其 非常 *"吃你的杀死* 种动态生成 叠加项目的方式。 这些生成的项目用作 CAS 基础 Docker 图像发布到 Docker 中心，并用作 CAS CI 针对每个相关功能运行的 浏览器/UI 测试的基线。 CAS初始化使用自己来测试自己！

## 项目生成

[CAS初始][initializr] 可以使用卷发来生成CAS覆盖项目。 要访问 CAS 初始 ，可以使用以下策略。

### 希罗库
中科院项目提供了中科院 [希罗库][initializr]的运行实例。 要获得 从此实例开始，一个简单的方法可能是将以下功能包含在您的 bash 配置文件中：

```bash
函数 getcas（{
    卷曲-k https://casinit.herokuapp.com/starter.tgz-d依赖关系="$1"|焦油-xzvf-
    ls
}
```

这允许您使用：

```bash
格特卡斯二人组
```

…由 [双安全](../mfa/DuoSecurity-Authentication.html) 和 [开放ID连接](OAuth-OpenId-Authentication.html) 的CAS叠加项目。

<div class="alert alert-info"><strong>注意</strong>
<p>为了帮助降低部署成本，Heroku 实例已启用对 
限速请求的支持。 请注意，访问时可能会限制频繁请求。</p></div>

### 码头工人

如果在 Heroku 上不可用初始化器，您也可以通过 Docker 运行您自己的初始实例：

```bash
码头运行 -rm-p 8080：8080 阿佩雷奥/卡斯初始：${tag}
```

CAS 初始响应应在 `http://localhost:8080` 时提供，并将使用卷发响应 API 请求。 已发布的图片和标签 中科院初始 [可以在这里找到](https://hub.docker.com/r/apereo/cas-initializr/tags)。 每个标记的图像都对应于 CAS 服务器版本，用于 图像能够生成覆盖项目。

## CAS 模块

CAS 项目模块和可请求的依赖项必须通过 其标识符进行指定。 要查看此服务支持和 提供的所有依赖项的完整列表，您可以调用以下命令：

```bash
卷曲 https://casinit.herokuapp.com/dependencies
```

通常，依赖性标识符与 CAS 服务器依赖/模块工件名称匹配，而无需 `cas 服务器-` 前缀。 此外，某些依赖项可以指定别名作为简化请求的 捷径。 要查看受抚养人及其别名的完整列表，您可以使用：

```bash
卷曲 https://casinit.herokuapp.com/actuator/info
```

此外，CAS 初始子发布有关其功能的元数据，这是所有请求参数（依赖项、类型等） 可用选项 服务的客户使用该信息初始化所选选项和可用依赖项的树。

您可以使用适当的 `接受` 标题来获取根端点上的元数据：

```bash
卷曲-H'接受：应用程序/杰森'https://casinit.herokuapp.com
```

或使用HTTPie：

```bash
http https://casinit.herokuapp.com 接受：申请/json
```

[initializr]: https://casinit.herokuapp.com/

[initializr]: https://casinit.herokuapp.com/

[initializr]: https://casinit.herokuapp.com/

[initializr]: https://casinit.herokuapp.com/
