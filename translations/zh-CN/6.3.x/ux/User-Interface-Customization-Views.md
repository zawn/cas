---
layout: 默认
title: 视图-用户界面自定义-CAS
category: 用户界面
---

# 观看次数

在CAS Web应用程序内部的 模板文件夹 `WEB-INF \ lib \ cas-server-support-thymeleaf-<cas.version>.jar` 将需要自定义的所有视图添加到CAS覆盖项目中 `src / main / resources / templates`  
通过在CAS覆盖项目中的 `src / main / resources` 下的相同位置放置文件，可以覆盖在该模块中找到的所有文件。 覆盖图的Gradle构建脚本的任务可帮助将资源 从CAS Web应用程序获取到CAS覆盖图中的正确位置。

如果通过CAS设置 ，则视图也可以有条件地在Web应用程序外部进行外部化。 如果在外部路径中找不到视图模板文件，则CAS附带的默认模板将用作后备文件。

也可以使用CAS设置中的外部URL查找视图，该URL负责在响应 此URL端点将在其请求中接收可用的请求标头以及以下标头：

| 标头          |
| ----------- |
| `所有者`       |
| `模板`        |
| `资源`        |
| `主题`（如果有）   |
| `语言环境`（如果有） |

成功获得 `200` 状态结果后，响应主体应包含将由CAS呈现的视图。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#views)。

## 胸腺

CAS将 [Thymeleaf](https://www.thymeleaf.org) 用于其标记呈现引擎。 每个模板都由 `layout.html` 模板文件装饰，该文件提供了模板内容的布局结构。 为多个模板之间的重用而优化的各个组件存储在 `src / main / resources / templates / fragments` 文件夹中，并由 `src / main / resources / templates`的模板引用。

有关其用法和语法的更多信息，请参见 [Thymeleaf文档](https://www.thymeleaf.org/)

## 访问应用程序之前的警告

CAS具有在重定向到服务之前警告用户的能力。 这样，无论何时应用程序使用CAS登录用户，都可以使用户知道。 （如果他们不选择警告，则在访问成功依赖现有CAS单点登录会话的应用程序时，他们可能看不到任何CAS屏幕。） 一些CAS采用者在CAS登录视图中删除了“警告”复选框，并且不提供这种插页式建议，即正在进行单点登录。

```html
...
<input id="warn"
       name="warn"
       value="true"
       tabindex="3"
       th:accesskey="#{screen.welcome.label.warn.accesskey}"
       type="checkbox" />
<label for="warn" th:utext="#{screen.welcome.label.warn}"/>
...
```

## 自定义字段

CAS允许通过包含要由用户填充的其他字段来动态扩展登录表单。 使用设置将这样的字段教给CAS，然后绑定到身份验证流，并提供给所有希望使用这些字段强加附加过程和规则的

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#views)。
