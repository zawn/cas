---
layout: 默认
title: 概述-用户界面自定义-CAS
category: 用户界面
---

# 概述

标记CAS用户界面（UI）涉及到编辑CSS样式表，以及少量相对简单的HTML包含文件的集合，这些文件也称为视图。 （可选）您可能还希望修改显示的文本和/或在这些视图上

# 浏览器支持

CAS用户界面应适当且舒适地适合所有主要的浏览器供应商：

* 谷歌浏览器
* 火狐浏览器
* 苹果Safari
* 微软边缘
* Internet Explorer（仅v11）

请注意，某些较旧版本的IE，尤其是IE 9及更低版本，可能会给安装正确的UI配置带来额外的困难。 用于用户界面的库（Bootstrap和Material.io）不支持Internet Explorer 9或更低版本。

<div class="alert alert-info"><strong>支持的浏览器</strong><p>此处列出的受支持的浏览器参考默认的CAS用户界面。 可以使用覆盖，主题等来实现自定义以支持其他浏览器。</p></div>

## IE浏览器

为了指示CAS以兼容模式呈现UI，将以下内容自动添加到相关的UI组件：

```html
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
```

# CSS & Javascript

有关更多信息，请参见 [本指南](User-Interface-Customization-CSSJS.html)

# 观看次数

有关更多信息，请参见 [本指南](User-Interface-Customization-Views.html)

# 本土化

有关更多信息，请参见 [本指南](User-Interface-Customization-Localization.html)

# 主题

有关更多信息，请参见 [本指南](User-Interface-Customization-Themes.html)
