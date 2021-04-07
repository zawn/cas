---
layout: 默认
title: CAS-OS服务部署
category: 安装
---

# 操作系统服务部署

`init.d` 或 `systemd`轻松将CAS作为Unix / Linux服务启动。 也可以通过外部守护程序将Windows支持设置为 请注意，以下大多数策略（如果不是全部）都尝试通过配置为 [ servlet容器运行CAS，在此进行解释](Configuring-Servlet-Container.html#embedded)。

## `init.d` 服务

如果CAS是作为完全可执行的Web应用程序</a>和

运行的，则可以将其用作 `init.d` 服务。 简单地 `符号链接` 所述的Web应用程序文件到 `的init.d` 以支持标准的 `开始`， `停止`， `重启` 和 `状态` 的命令。</p> 

CAS中内置的配置允许它与OS系统配置进行交互，如下所示：

- 以拥有jar文件的用户身份启动服务
- `/var/run/cas/cas.pid`跟踪CAS Web应用程序的PID
- 将控制台日志写入 `/var/log/cas.log`

要将CAS作为 `init.d` 服务安装，只需创建一个符号链接：



```bash
sudo ln -s /path/to/cas.war /etc/init.d/cas
服务cas开始
```


您还可以标记应用程序以使用标准操作系统工具自动启动。 例如，在Debian上：



```bash
update-rc.d myapp默认为 <priority>
```




### 安全

当以 `root`执行时，就像使用 `root` 启动 `init.d` 服务时一样，CAS默认 可执行脚本将以拥有Web应用程序文件的用户身份运行Web应用程序。 您不应该 **永远** `根` 身份运行CAS，因此Web应用程序文件永远不应该由 `根`拥有。 而是创建一个特定用户以运行 CAS，并使用 `chown` 使其成为文件的所有者。 例如：



```bash
chown bootapp：bootapp /path/to/cas.war
```


您还可以采取措施来防止修改CAS Web应用程序文件。 首先，将 的权限配置为使其无法写入，并且只能由其所有者读取或执行：



```bash
chmod 500 /path/to/cas.war
```


此外，你也应该采取措施，如果CAS Web应用程序或将损失降到 即正在运行它的帐户被泄露。 如果攻击者确实获得了访问权限，则他们可以使Web应用程序 文件可写并更改其内容。 一种防止这种情况的方法是使用 `chattr`使其不可变：



```bash
须藤chattr + i /path/to/cas.war
```


这将防止任何用户（包括 `root`）修改文件。



## `systemd` 服务

要将CAS作为 `systemd` 服务安装，请使用以下示例创建一个名为 `cas.service` 的脚本，并将其放在 `/ etc / systemd / system` 目录中：



```ini
[Unit]
说明= CAS
之后= syslog.target

[Service]
用户= bootapp
ExecStart = / path / to / cas.war
SuccessExitStatus = 143

[Install]
WantedBy =多用户目标
```

<div class="alert alert-info"><strong>没那么快</strong><p>记住改变 <code>说明</code>， <code>用户</code> 和 <code>ExecStart</code> 字段进行部署。</p></div>

运行CAS Web应用程序，PID文件和控制台日志文件的用户由 `systemd` 本身管理，因此必须使用 `service` 脚本中的适当字段进行配置。 有关更多详细信息，请查阅 [服务单元配置手册第](https://www.freedesktop.org/software/systemd/man/systemd.service.html)

要将应用程序标记为在系统引导时自动启动，请使用以下命令：



```bash
systemctl启用cas.service
```


有关更多详细信息，请参考 `man systemctl`



## 暴发户

[Upstart](http://upstart.ubuntu.com/) 是一个基于事件的服务管理器，它是System V init的潜在替代品，它提供了对不同守护程序行为的更多控制。 使用Ubuntu时，您可能已经安装并配置了它（检查是否有任何作业的名称以 `/ etc / init``cas` 开头。

我们创建一个作业 `cas.conf` 以启动CAS Web应用程序：



```bash
＃放在/ home /{user}/.config/cas
说明“ CAS Web应用程序”
＃如果突然停止，尝试重新启动服务
重新生成
exec java -jar /path/to/cas.war
```


现在运行 `启动cas` ，您的服务将启动。 新贵提供了许多就业的配置选项，你可以找到 [大多在这里](http://upstart.ubuntu.com/cookbook/)。



## Windows服务



### Windows服务包装器

[winsw](https://github.com/kohsuke/winsw)将CAS作为Windows服务启动。 

Winsw提供了编程方式来 `安装/卸载/启动/停止` 服务。 另外，它可以用于在Windows下将任何类型的可执行文件作为服务运行。

下载Winsw二进制文件后，定义我们的Windows服务 `cas.xml`



```xml
<service>
    <id>cas</id>
    <name>CAS</name>
    <description>CAS Web应用程序。</description>
    <executable>java</executable>
    <arguments>-Xmx2048m -jar“路径\to \ cas.war”</arguments>
    <logmode>旋转</logmode>
</service>
```


最后，必须将 `winsw.exe` 重命名为 `cas.exe` 以便其名称与 `cas.xml` 配置文件匹配。 之后，您可以像这样安装服务：



```bash
cas.exe安装
```


类似地，可以使用 `卸载`， `开始`， `停止`等

请参考本示例</a> 以了解更多信息。</p> 



### 其他

[Apache Commons Daemon项目](http://commons.apache.org/daemon/index.html)[Procrun](http://commons.apache.org/proper/commons-daemon/procrun.html) 将CAS Web应用程序作为Windows服务启动。 Procrun是一组应用程序，允许Windows用户将Java应用程序包装为Windows服务。 可以将此类服务设置为在计算机启动时自动启动，并且将在没有任何用户登录的情况下继续运行。
