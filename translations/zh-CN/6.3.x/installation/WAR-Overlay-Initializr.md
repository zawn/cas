---
layout: 默认
title: CAS-WAR叠加初始化
category: 安装
---

# WAR Overlay Initializr

[Apereo CAS Initializr][initializr] 是Apereo CAS生态系统中的一个相对较新的功能，它允许 作为部署者即时生成CAS WAR Overlay项目，而您只需要快速启动即可。

为了开始进行CAS部署，采用者通常从 开始，从 [开始是一个简单的基于Gradle的覆盖项目](WAR-Overlay-Installation.html) 并将其用作将来修改的基准。 尽管多年来一直是传统的推荐使用 生态系统的相对较新的用户而言，下载，修改和准备覆盖项目以包括所有必需的 自定义项也可能是一个挑战。 考虑到叠加项目的静态性质，对于 项目的所有者和维护者来说，使其保持最新状态或提供其他增强功能 和自动化而不影响基线模板也可能是一个挑战。

为了解决这种情况， [CAS WAR Overlay Initializr][initializr] 提供了一种快速的方法来提取 CAS部署所需的所有依赖项和模块，而 提供了友好且编程的curl-friendly API来生成 覆盖结构和所需的构建文件。

处理项目生成 任务 [Spring Initializr](https://github.com/spring-io/initializr)。

## 概述

请求的模块和部署所需的依赖关系来动态生成启动项目。 可以根据该输入和 ，以在同一项目中生成其他引用，文件，开始的 模板等，以使部署过程更加舒适。

此时的CAS Initializr主要是一个后端服务和一些API。 但是，可以想象可以在可用的API之上构建图形化的现代用户界面 生成任务，特别是对于项目新手。

管理和维护一个单独的覆盖项目，并使它们 同步可能是一项昂贵的维护任务。 CAS Initializr允许项目开发人员自动执行 维护任务，将所有内容都保留在同一存储库 以实现更快，更准确的升级。

<div class="alert alert-info"><strong>笔记</strong>
<p>请记住，CAS Initializr在这个时间点是不是能 
产生了CAS管理Web应用程序的覆盖项目。 将来的版本中将解决此 
</p></div>

CAS Initializr由CAS项目本身内部使用，以 非常 *Eat What Your Kill的* 种方式动态生成 覆盖项目。 这些生成的项目用作发布到Docker Hub的 Docker映像，并用作CAS CI针对每个相关功能运行 CAS Initializr会自我测试！

## 项目生成

[CAS Initializr][initializr] 来生成CAS覆盖项目。 要访问 CAS Initializr，可以使用以下策略。

### 赫鲁库
[Heroku][initializr]上运行CAS Initializr的实例。 要从 ，一个简单的方法可能是在bash配置文件中包含以下函数：

```bash
函数getcas（）{
    curl -k https://casinit.herokuapp.com/starter.tgz -d依赖性=“ $ 1” | tar -xzvf-
    ls
}
```

这使您可以使用以下方法生成CAS覆盖项目：

```bash
格特卡斯二人组，oidc
```

…这产生具有多因素认证制备的CAS覆盖项目 由 [Duo网络安全](../mfa/DuoSecurity-Authentication.html) 和 为支持 [ID连接](OAuth-OpenId-Authentication.html)。

<div class="alert alert-info"><strong>笔记</strong>
<p>为了降低部署成本，Heroku实例启用了对 
个限速请求的支持。 请注意，经常访问的请求可能会受到限制。</p></div>

### 码头工人

如果在Heroku上没有Initializr，则还可以通过Docker运行自己的Initializr实例：

```bash
泊坞窗运行--rm -p 8080：8080 apereo / cas-initializr：${tag}
```

CAS Initializr应该在 `http：// localhost：8080` 处可用，并将使用curl响应API 在此处</a>可以找到CAS Initializr发布图像和标签为 。 每个标记的图像都对应于 的CAS服务器版本，该图像能够生成覆盖项目。</p> 



## CAS模块

可以请求的CAS项目模块和依赖项必须通过其标识符 要查看此服务支持的所有依赖项的完整列表以及 ，可以调用以下命令：



```bash
卷曲https://casinit.herokuapp.com/dependencies
```


通常，依赖项标识符与CAS服务器依赖项/模块工件名称匹配，但前缀 不为 `cas-server-` 此外，可以为某些依赖项分配别名作为 快捷方式，以简化请求。 要查看依赖项及其别名的完整列表，可以使用：



```bash
卷曲https://casinit.herokuapp.com/actuator/info
```


此外，CAS Initializr会发布有关其功能的元数据，即所有请求参数（依赖项，类型等） 服务的客户端使用该信息来初始化选择选项和可用依赖关系树。

`Accept` 标头在根端点上获取元数据：



```bash
curl -H'接受：application / json'https://casinit.herokuapp.com
```


或使用HTTPie：



```bash
http https://casinit.herokuapp.com接受：application / json
```

[initializr]: https://casinit.herokuapp.com/

[initializr]: https://casinit.herokuapp.com/

[initializr]: https://casinit.herokuapp.com/

[initializr]: https://casinit.herokuapp.com/
