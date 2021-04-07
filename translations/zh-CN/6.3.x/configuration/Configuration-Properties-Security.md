---
layout: 默认
title: CAS-保护配置属性
category: 配置
---

# 配置安全

本文档介绍了如何检索和保护CAS配置和属性。

## 单机版

如果您在没有配置服务器的情况下以独立模式运行CAS，则 可以利用内置的 [Jasypt](http://www.jasypt.org/) 功能来解密敏感的CAS设置。

Jasypt提供了可用于执行加密，解密等的命令行工具。 为了使用这些工具，您应该下载Jasypt发行版。 解压缩后，您将在 `jasypt-$VERSION/ bin` 目录中找到 `bat | sh` 脚本，您可以将它们用于加密/解密操作 `（encrypt | decrypt）。（bat | sh）`。

加密的设置需要按以下方式放入CAS配置文件中：

```properties
cas.something.sensitive ={cas-cipher}FKSAJDFGYOS8F7GLHAKERGFHLSAJ
```

尝试解密设置时，还需要指示CAS使用正确的算法，解密密钥和其他相关参数 功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#configuration-security)。


## 春云

固定CAS设置和解密它们完全由处理 的 [弹簧云](https://github.com/spring-cloud/spring-cloud-config) 项目 为 [本指南中描述](Configuration-Server-Management.html)。

CAS配置服务器公开 `/ encrypt` 和 `/ decrypt` 端点以支持加密和解密值。 两个端点都接受 `POST` 负载；您可以使用 `/ encrypt` 来保护和加密设置，并将其放入CAS配置中。 CAS将在适当的时候自动解密。

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#configuration-security)。

<div class="alert alert-info"><strong>JCE要求</strong><p>要使用加密和解密
功能，您需要在JVM版本中安装完整强度的“ Java密码学扩展（JCE）无限强度管辖策略文件”
（如果默认情况下不存在）。</p></div>

要加密给定的设置，请使用：

```bash
curl -u casuser：梅隆https：//config.server.endpoint/encrypt -dsensitiveValue
```

然后，使用以下指定的方法将加密的设置复制到CAS配置中。

<div class="alert alert-info"><strong>URL编码</strong><p>小心 <code>curl</code>。
您可能必须使用 <code>--data-urlencode</code> 或设置一个明确的 <code>Content-Type：text / plain</code>
以解决特殊字符，例如 <code>+</code>。</p></div>

如果您希望手动加密和解密设置以确保功能正常，请使用：

```bash
导出ENCRYPTED =`curl -u casuser：Mellon https：//config.server.endpoint/encrypt -dsensitiveValue | python -c'import sys，urllib; print urllib.quote（sys.stdin.read（）。strip（））'`
echo $ENCRYPTED
curl -u casuser：Mellon https：//config.server.endpoint/decrypt- d $ENCRYPTED | python -c'导入sys，urllib;打印urllib.quote（sys.stdin.read（）。strip（））'
```

前缀为 `{cipher}` 属性在运行时由CAS配置服务器自动解密，例如：

```yml
cas
    某些东西
        敏感：“{cipher}FKSAJDFGYOS8F7GLHAKERGFHLSAJ”
```

或者：

```properties
＃请注意，该值周围没有引号！
cas.something.sensitive ={cipher}FKSAJDFGYOS8F7GLHAKERGFHLSAJ
```

## 金库

您还可以将敏感设置存储在 [Vault](https://www.vaultproject.io/)。 保险柜可以存储您现有的机密信息，也可以动态生成新的机密信息 以控制对第三方资源的访问或为您的基础结构提供限时凭证。 要了解有关Vault及其安装过程的更多信息，请访问项目网站。

一旦可以在CAS中访问和配置了保管库，就可以通过以下依赖项提供支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-configuration-cloud-vault</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](Configuration-Properties.html#vault)。

使用CAS，可以在应用程序服务器启动时获取机密。 CAS使用来自应用程序名称（即 `cas`）和活动配置文件的数据和设置 中应存储并稍后提取秘密的上下文路径。

这些上下文路径通常是：

```bash
/秘密/{application}/{profile}
/秘密/{application}
```

例如，您可以将以下CAS设置写入保险柜：

```bash
保管库写入密钥/ cas /本机 <setting-name>=<value>
```

CAS将在需要时执行与以下命令等效的命令，以在以后读取设置：

```bash
保管库读取机密/ cas /本机
```

可以在任何给定时间重新加载Vault中存储的所有设置和机密。 要了解有关CAS如何允许您重新加载配置更改的更多信息，请 [本指南](Configuration-Management-Reload.html)。 要了解有关CAS如何管理和配置文件的更多信息，请 [本指南](Configuration-Management.html)。

### 故障排除

要启用其他日志记录，请修改日志记录配置文件以添加以下内容：

```xml
<Logger name="org.springframework.cloud.vault" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
