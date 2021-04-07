---
layout: 违约
title: 本地化 - 用户界面定制 - CAS
category: 用户界面
---

# 地方化
CAS Web 应用程序包括多个本地化消息文件：

- 英语（美国）
- 西班牙语
- 法语
- 俄语
- 荷兰语（荷兰语）
- 瑞典语（斯文斯克特）
- 意大利语（意大利语）
- 乌都语
- 中文（简版）
- 德语（德国）
- 日语
- 克罗地亚语
- 捷克语
- 斯洛文尼亚语
- 波兰语
- 葡萄牙语（巴西）
- 土耳其语
- 波斯语
- 阿拉伯语

为了"调用"UI 的特定语言， `/登录` 端点可以通过 `局域` 参数：

```html
https://cas.server.edu/login?locale=it
```

<div class="alert alert-warning"><strong>使用警告！</strong><p>请注意，并非所有语言都是完整和准确的整个 CAS 服务器版本，因为翻译完全依赖于社区贡献。
要获得准确完整的本地化消息列表，请始终参考英语捆绑包。</p></div>

## 配置

所有消息捆绑包都标记在 `messages_xx.属性` 文件 `src/主/资源`。 默认语言捆绑包用于 英语，因此称为 `消息`。 如果有任何自定义消息需要呈现到视图中， 也可以在 `custom_messages.属性` 文件下格式化。

如果在激活资源捆绑包中找不到代码，则将逐字使用代码本身。

要查看 CAS 物业的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#localization) 并 [本指南](../configuration/Configuration-Properties.html#message-bundles)。
