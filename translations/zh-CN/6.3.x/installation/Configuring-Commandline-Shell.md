---
layout: 违约
title: CAS - 配置指挥线壳
category: 安装
---

# 中科院指挥线壳牌

CAS 命令线外壳能够查询 CAS 服务器以获得有关可用设置/模块的帮助，并 各种其他实用功能。

要调用和与实用程序合作，执行：

```bash
爪哇 - jar / 路径 / 到 / 卡斯服务器支持壳 -$casVersion.jar
```

... `$casVersion` 不用说的是部署的CAS版本。

接下来呈现的界面将引导您使用可用的参数和查询方法。 您将学习如何启动到交互式外壳中，并动态查询 CAS 发动机。

<div class="alert alert-info"><strong>JCE 要求</strong><p>确保您的 
Java 环境中安装了适当的 JCE 捆绑包，该捆绑包由 CAS 使用，特别是如果您需要使用特定的签名/加密算法和方法。 
请务必为您的Java版本选择正确的JCE版本。 爪哇版本可以通过 <code>爪哇版本</code> 命令检测到。</p></div>

请注意， [WAR 叠加部署策略](WAR-Overlay-Installation.html) 应已配备此 功能。 您不必做任何特殊和额外的事情来与外壳交互。 有关 叠加文档，了解有关如何调用和处理外壳的更多信息。
