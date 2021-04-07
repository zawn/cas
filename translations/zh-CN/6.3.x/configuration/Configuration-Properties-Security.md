---
layout: 违约
title: CAS - 保护配置属性
category: 配置
---

# 配置安全性

本文档描述了如何检索和安全 CAS 配置和属性。

## 独立

如果您在没有配置服务器的情况下以独立模式运行 CAS， 您可以利用内置 [Jasypt](http://www.jasypt.org/) 功能解密敏感的 CAS 设置。

Jasypt 提供用于执行加密、解密等的命令线工具。 为了使用这些工具，您应该下载 Jasypt 分发。 解压缩后，您将找到一个 `jasypt-$VERSION/bin` 目录，其中 `一些蝙蝠|sh` 脚本，可用于加密/解密操作 `（加密|熟练）。（蝙蝠|什）`。

需要将加密设置放入 CAS 配置文件中，如下所述：

```properties
cas.东西.敏感={cas-cipher}福克萨伊夫吉奥斯8F7格哈克尔法哈萨伊
```

在尝试解密设置时，您还需要指示 CAS 使用适当的算法、解密密钥和其他相关参数 。 要查看此 功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#configuration-security)。


## 春云

保护 CAS 设置并解密它们完全由 [春云](https://github.com/spring-cloud/spring-cloud-config) 项目 处理，如本指南</a>中所述。</p> 

CAS 配置服务器会暴露 `/加密` 和 `/解密` 端点以支持加密和解密值。 两个端点都接受 `POST` 有效载荷;您可以使用 `/加密` 来保护和加密设置，并将它们置于 CAS 配置中。 CAS 将在适当的时候自动解密。

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#configuration-security)。

<div class="alert alert-info"><strong>JCE 要求</strong><p>要使用加密和解密
功能，您需要在 JVM 版本中安装全强度的"Java 加密扩展 （JCE） 无限强度管辖政策文件"
（如果默认不存在）。</p></div>

要加密给定设置，请使用：



```bash
卷曲 - u 卡苏瑟： 梅隆 https://config.server.endpoint/encrypt - d 敏感值
```


然后，使用下面指定的方法将加密设置复制到 CAS 配置中。

<div class="alert alert-info"><strong>网址编码</strong><p>小心 <code>卷曲</code>。
您可能需要使用 <code>- 数据-</code> 或设置一个明确的 <code>内容类型：文本/纯</code>
，以解释特殊字符，如 <code>+</code>。</p></div>

如果您希望手动加密和解密设置以确保功能是理智的，请使用：



```bash
出口加密-冰壶-u卡苏瑟：梅隆 https://config.server.endpoint/encrypt-d敏感价值|巨蛇 - c '进口 sys， urllib; 打印 urllib. 报价 （sys. stdin. read. 条纹） '
回声 $ENCRYPTED
卷曲 - u 卡苏瑟： 梅隆 https://config.server.endpoint/decrypt - d $ENCRYPTED |巨蛇 -c '进口 sys， 尿布; 打印尿布里布. 报价 （sys. stdin. 阅读） 。
```


与 `{cipher}` 的前缀属性在运行时由 CAS 配置服务器自动解密，例如：



```yml
cas
    一些
        敏感的东西："{cipher}FKSAJDFGYOS8F7格尔克弗尔萨伊"
```


艺术



```properties
#注意，没有围绕该值的报价！
cas.东西.敏感={cipher}福克萨伊夫吉奥斯8F7格哈克尔法哈萨伊
```




## 库

您还可以将敏感设置存储在 [库](https://www.vaultproject.io/)内。 Vault 可以存储您现有的机密，也可以动态生成新的机密 以控制对第三方资源的访问，或为您的基础设施提供限时的凭据。 要了解有关 Vault 及其安装过程的更多信息，请访问项目网站。

一旦保险库在 CAS 内访问和配置，则通过以下依赖提供支持：



```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>的卡-服务器-支持-配置-云-库</artifactId>
     <version>${cas.version}</version>
</dependency>
```


要查看此功能的 CAS 属性的相关列表，请 [查看本指南](Configuration-Properties.html#vault)。

在 CAS 中，在应用程序服务器启动时会收集机密。 CAS 使用从应用程序名称 的数据和设置（即 `cas`）和活动配置文件，以确定 中应存储和稍后获取哪些秘密的上下文路径。

这些上下文路径通常是：



```bash
/秘密/{application}/{profile}
/秘密/{application}
```


例如，您可以将以下 CAS 设置写入保险库：



```bash
金库写秘密/卡斯/原生 <setting-name>=<value>
```


CAS 将在需要时执行相当于以下命令的命令，以在以后阅读设置：



```bash
金库读取秘密/cas/原生
```


存储在 Vault 中的所有设置和机密可在任何给定时间重新加载。 要了解有关 CAS 如何允许您重新加载配置更改的更多内容，请 [查看本指南](Configuration-Management-Reload.html)。 要详细了解 CAS 如何管理和配置配置，请 [查看本指南](Configuration-Management.html)。



### 故障 排除

要启用其他日志，请修改记录配置文件以添加以下：



```xml
<Logger name="org.springframework.cloud.vault" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
```
