---
layout: 违约
title: CAS - 操作系统服务部署
category: 安装
---

# 操作系统服务部署

CAS 可以很容易地开始作为 Unix/Linux 服务使用 `init.d` 或 `系统`。 ，还可通过外部护蒙提供 Windows 支持。 请注意，以下大多数（如果不是全部）策略都试图通过嵌入式 服务器容器运行 CAS，其配置 [此处解释](Configuring-Servlet-Container.html#embedded)。

## `` 服务

如果CAS是作为一个完全可执行的web应用程序</a>

建立和运行的，那么 就可以用作 `it.d` 服务。 只需 `` 网络应用程序文件 `` 支持标准 `启动`， `停止`， `重新启动` ，并 `状态` 命令。</p> 

CAS 内置的配置允许它与操作系统配置进行交互：

- 作为拥有罐子文件的用户开始服务
- 使用 `/var/运行/cas/cas.pid`跟踪CAS Web应用程序的PID
- 将控制台日志写到 `/瓦尔/日志/cas.log`

将 CAS 安装为 `init.d` 服务只需创建一个符号链接：



```bash
苏多 ln / 路径 / 到 / 卡斯战争 / 等 / init. d / cas
服务卡开始
```


您还可以标记应用程序，以便使用您的标准操作系统工具自动启动。 例如，在德比安：



```bash
更新-rc.d我的应用程序默认 <priority>
```




### 安全

当执行为 `根`时，如使用 `根` 启动 `init.d` 服务时，CAS 默认 可执行脚本将作为拥有 Web 应用程序文件的用户运行 Web 应用程序。 您 **切勿** 将CAS作为 `根` 运行，因此网络应用文件绝不应归 `根`所有。 相反，创建一个特定的用户来运行 CAS，并使用 `` 使其成为文件的所有者。 例如：



```bash
乔恩启动应用程序： 启动应用程序 / 路径 / 到 / cas. 战争
```


您还可以采取措施防止 CAS 网络应用程序文件的修改。 首先， 其权限进行配置，使其无法书写，只能由其所有者读取或执行：



```bash
奇莫德 500 /路径 / 到 / 卡斯. 战争
```


此外，如果 CAS 网络应用程序或 运行该帐户的帐户遭到破坏，您还应采取措施限制损坏。 如果攻击者确实获得了访问权限，他们可以使 Web 应用程序 文件可令状并更改其内容。 防止这种情况的一种方法是使用 `喋喋不休的`：



```bash
苏多聊天 +i / 路径 / 到 / 卡斯. 战争
```


这将阻止任何用户，包括 `根`，修改文件。



## `系统化` 服务

将 CAS 安装为 `系统` 服务，则使用以下示例创建名为 `cas.服务` 的脚本，并将其放在 `/等/系统/系统` 目录中：



```ini
[Unit]
描述=CAS
后记录.目标

[Service]
用户=启动应用程序
执行启动=/路径/到/cas.战争
成功exitstatus=143

[Install]
通缉比多用户。目标
```

<div class="alert alert-info"><strong>没那么快</strong><p>请记住更改 <code>描述</code>， <code>用户</code> 和 <code>执行启动</code> 字段进行部署。</p></div>

运行 CAS Web 应用程序、PID 文件和控制台日志文件的用户由 `系统` 本身进行管理，因此必须在 `服务` 脚本中使用适当的字段进行配置。 请咨询 [服务单元配置人员页面](https://www.freedesktop.org/software/systemd/man/systemd.service.html) 了解更多详细信息。

要标记在系统启动时自动启动的应用程序，请使用以下命令：



```bash
系统启用案例服务
```


有关更多详细信息，请参阅 `个人系统` 。



## 暴发户

[暴发户](http://upstart.ubuntu.com/) 是一个基于事件的服务经理，一个潜在的替代系统V init，提供更多控制不同护身符的行为。 使用 Ubuntu 时，您可能已经安装并配置了它（检查是否有以 `/等/init`中</code> 开 `cas 开开的名称的工作）。</p>

<p spaces-before="0">我们 <code>cas.com` 创建一个开始 CAS 网络应用程序的工作：



```bash
#放置在/家庭/{user}/。配置/cas
描述"CAS网络应用程序"
#尝试服务重新启动，如果突然停止
重生
执行java-jar/路径/到/cas.war
```


现在运行 `开始cas` 和您的服务将开始。 暴发户提供了许多工作配置选项，你可以找到 [他们中的大多数在这里](http://upstart.ubuntu.com/cookbook/)。



## 视窗服务



### 窗口服务包装

CAS可能开始作为窗口服务使用 [winw](https://github.com/kohsuke/winsw)。 

Winsw 提供程序化手段， `安装/卸载/启动/停止` 服务。 此外，它可用于在 Windows 下运行任何类型的可执行服务。

下载了 Winsw 二进制文件后，定义我们 Windows 服务的 `cas.xml` 配置文件应看起来像这样：



```xml
<service>
    <id>cas</id>
    <name>中科院</name>
    <description>CAS网络应用。</description>
    <executable>爪哇</executable>
    <arguments>-Xmx2048m-jar"路径\to\cas.war"</arguments>
    <logmode>旋转</logmode>
</service>
```


最后，你必须重命名 `winw.exe` `cas.exe` 使其名称与 `cas.xml` 配置文件匹配。 此后，您可以这样安装服务：



```bash
卡斯.exe安装
```


同样，您可以使用卸载 ``， `开始`， `停止`等。

请参阅此示例 [](https://github.com/snicoll-scratches/spring-boot-daemon) 了解更多。



### 别人

CAS网络应用也可以开始作为Windows服务使用 [宝润](http://commons.apache.org/proper/commons-daemon/procrun.html) [阿帕奇公地戴蒙项目](http://commons.apache.org/daemon/index.html)。 宝洁是一组应用程序，允许 Windows 用户将 Java 应用程序包装为 Windows 服务。 当机器启动时，此类服务可能会自动启动，并且将继续运行，而无需登录任何用户。
