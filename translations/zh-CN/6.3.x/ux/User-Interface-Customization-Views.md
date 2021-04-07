---
layout: 违约
title: 视图 - 用户界面自定义 - CAS
category: 用户界面
---

# 视图

这些视图位于 模板文件夹中 `WEB-INF-lib-cas 服务器支持-百里香叶-<cas.version>.jar` 中的 CAS Web 应用程序中。 将任何需要自定义的视图添加到 CAS 叠加项目中</code> 文件夹 `src/主/资源/模板。<br x-id="2" />
该模块中发现的任何文件都可以通过将它们放在 CAS 叠加项目中
<code>src/主/资源` 下的同一位置来覆盖。 覆盖的 Gradle 构建脚本具有帮助将资源从 CAS Web 应用程序 到 CAS 叠加的正确位置的任务。

视图也可以有条件地和单独地在 Web 应用程序之外外部外部化，前提是通过 CAS 设置 外部路径。 如果在外部化路径上找不到视图模板文件，则使用 CAS 的默认文件将用作回退。

也可以在 CAS 设置中使用外部 URL 找到视图，该 URL 负责在响应 生成全视图主体。 此 URL 端点将接收可用的请求标题及其请求中的以下标题：

| 页眉        |
| --------- |
| `所有者`     |
| `模板`      |
| `资源`      |
| `主题`，如果可用 |
| `地区`，如果可用 |

在成功 `200` 状态结果后，响应机构预计将包含中科院将呈现的观点。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#views)。

## 蒂梅利夫

CAS使用 [百里香叶](https://www.thymeleaf.org) 进行加价渲染发动机。 每个模板都由 `布局.html` 模板文件进行装饰，该文件为模板的内容提供了布局结构。 在多个模板中优化用于重复使用的单个组件存储在 `src/主/资源/模板/片段` 文件夹中，并在</code>`src/主/资源/模板中引用模板。</p>

<p spaces-before="0">有关提梅叶文档 <a href="https://www.thymeleaf.org/">，请参阅</a> 有关其使用和语法的更多信息。</p>

<h2 spaces-before="0">访问应用程序前的警告</h2>

<p spaces-before="0">CAS 能够在重定向到服务之前警告用户。 这样，每当应用程序使用 CAS 登录时，用户都可以随时了解。
（如果他们不选择警告，在访问成功依赖于现有 CAS 单个登录会话的应用程序时，他们可能看不到任何 CAS 屏幕。
一些 CAS 采用者删除 CAS 登录视图中的"警告"复选框，并且不提供单个登录正在发生的插座建议。</p>

<pre><code class="html">...
<input id="warn"
       name="warn"
       value="true"
       tabindex="3"
       th:accesskey="#{screen.welcome.label.warn.accesskey}"
       type="checkbox" />
<label for="warn" th:utext="#{screen.welcome.label.warn}"/>
...
`</pre>

## 自定义字段

CAS 允许通过包含由用户填充的其他字段来动态扩展登录表单。 这些字段使用设置向 CAS 传授，然后受身份验证流程约束，并提供给所有希望使用该字段实施额外流程和规则的 认证处理程序。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#views)。
