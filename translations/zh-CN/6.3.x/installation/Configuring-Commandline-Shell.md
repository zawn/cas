---
layout: 默认
title: CAS-配置命令行外壳
category: 安装
---

# CAS命令行外壳

CAS命令行外壳提供了查询CAS服务器以获取有关可用设置/模块的帮助以及 其他实用程序功能的功能。

要调用和使用该实用程序，请执行：

```bash
Java的罐子/路径/到/ CAS-服务器支撑壳-$casVersion的.jar
```

... `$casVersion` 是已部署的CAS版本。

接下来显示的界面将指导您完成可用的参数和查询方法。 您将学习如何启动交互式shell并动态查询CAS引擎。

<div class="alert alert-info"><strong>JCE要求</strong><p>
Java环境中安装了正确的JCE捆绑包，特别是在需要使用特定的签名/加密算法和方法的情况下。 
确保为您的Java版本选择正确的JCE版本。 <code>java -version</code> 命令检测Java版本。</p></div>

请注意，“ [WAR覆盖”部署策略](WAR-Overlay-Installation.html) 应该已经配备了这 功能。 您不必做任何特别的事情即可与shell进行交互。 有关如何调用和使用Shell的更多信息，请参见相关的
