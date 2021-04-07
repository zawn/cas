---
layout: 默认
title: 本地化-用户界面自定义-CAS
category: 用户界面
---

# 本土化
CAS Web应用程序包括许多本地化的消息文件：

- 英文（美国）
- 西班牙语
- 法语
- 俄语
- 荷兰语（荷兰）
- 瑞典文（Svenskt）
- 义大利文（Italiano）
- 乌尔都语
- 简体中文）
- 德文（Deutsch）
- 日本人
- 克罗地亚语
- 捷克文
- 斯洛文尼亚文
- 抛光
- 葡萄牙语（巴西）
- 土耳其
- 波斯语
- 阿拉伯

为了“调用” UI的特定语言，可以向 `/ login` 端点传递 `locale` 参数，如下所示：

```html
https://cas.server.edu/login?locale=it
```

<div class="alert alert-warning"><strong>使用警告！</strong><p>请注意，由于翻译完全取决于社区的贡献，因此并非所有语言在CAS服务器版本中都是完整而准确的。
有关本地化消息的准确和完整列表，请始终参考英语捆绑包。</p></div>

## 配置

所有消息束都标记在 `src / main / resources``messages_xx.properties` 文件下。 默认的语言包是 英语语言，因此被称为 `messages.properties`。 如果有任何需要显示在视图中的自定义消息，则它们也可以设置为 `custom_messages.properties` 文件，

如果在激活的资源包中找不到代码，则将逐字使用代码本身。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#localization) 和 [本指南](../configuration/Configuration-Properties.html#message-bundles)。
