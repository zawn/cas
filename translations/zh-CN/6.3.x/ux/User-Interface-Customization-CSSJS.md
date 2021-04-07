---
layout: 默认
title: CSS & JavaScript-用户界面自定义-CAS
category: 用户界面
---

# 的CSS

默认样式全部包含在一个文件中，该文件位于 `src / main / resources / static / css / cas.css`。 该位置在 `cas-theme-default.properties`设置。 默认情况下，CAS使用 [Material.io](https://material.io/) 库和设计规范作为其用户体验的基础。

例如，如果要创建自己的 `css / custom.css文件`，则需要更新该文件中的 `cas.standard.css.file`

```bash
cas.standard.css.file = / css / cas.css
cas.standard.js.file = / js / cas.js
```

## 响应式设计

CSS媒体查询为CAS带来了响应式设计功能，使采用者可以将 集中在所有适当设备和平台的一个主题上。 这些查询在 相同的 `cas.css` 文件中定义。 他们遵循Twitter Bootstrap断点和网格。

# Java脚本

如果需要添加一些JavaScript，请随时添加 `src / main / resources / static / js / cas.js`。

例如，您还可以创建自己的 `custom.js` 文件，并从 `scripts.html` 调用它，如下所示：

```html
<script type="text/javascript" src="/js/custom.js"></script>
```

如果您要为每个服务开发主题，则每个主题还可以在 `cas.standard.js.file` 设置 `cas.js`

最重要的是，CAS自动使用以下Javascript库：

* [jQuery查询](https://jquery.com/)
* [网格/弹性实用程序的引导程序](https://getbootstrap.com/docs/4.5/getting-started/contents/#css-files)
* [材料](https://material.io/)

## 脚本加载

CAS提供了一个回调函数，该脚本允许在脚本加载完成时通知采用者，并且这是安全的时间来执行/加载依赖于实际页面内JQuery的其他与Javascript相关的功能。

```javascript
function jqueryReady（）{
    //自定义Javascript任务现在可以通过JQuery执行...
}
```

## 检查CAPSLOCK

在输入凭据密码期间打开CAPSLOCK键时，CAS将显示简短警告。 此检查由 `cas.js` 文件强制执行。

## 浏览器Cookie支持

为了使CAS能够执行一次登录会话，浏览器必须支持并接受Cookie。 如果浏览器关闭了对Cookie的支持，CAS将通知 此行为是通过 `cas.js` 文件控制的。

## 保留锚碎片

锚点/片段可能会因重定向而丢失，因为表单发布的服务器端处理程序将忽略客户端锚点，除非将其附加到表单POST URL上。 如果您希望经过CAS身份验证的应用程序能够在添加书签时使用锚点/片段，则需要这样做。 默认情况下，CAS被配置为在指定的时间和地点保留锚点片段。 您无所事事。

### 用于Javascript / CSS库的WebJAR

CAS应用程序将第三方静态资源打包在CAS Web应用程序内，而不是引用CDN链接，因此CAS可以部署在Internet访问受限的

第三方静态资源打包在“ WebJAR” jar文件中，并通过servlet `3.0` 功能 `META-INF /资源` 下的任何文件夹与应用程序的Web根合并。

对于修改CAS的开发人员，如果添加或修改第三方库，则步骤为：

- 添加WebJAR依赖性 `dependencies.gradle` 在 `ext.library.webjars` 部分。
- 依赖版本添加到 `gradle.properties` ，并在使用 `dependency.gradle`。
- 将每个资源（例如js或css）的条目添加到 `core / cas-server-core-web / src / main / resources / cas_common_messages.properties`
- `gradle.properties` 的版本，它将在构建时被过滤掉。

<div class="alert alert-info"><strong>资源缓存</strong><p>版本号更改和资源升级后，该构建尝试再次重建所有相关模块。 如果确实需要强制删除缓存的工件并重新打包应用程序，请在 <code>core / cas-server-core-web</code> 模块 <code>clean</code></p></div>

例如：

```properties
webjars.zxcvbn.js = / webjars / zxcvbn / dist / zxcvbn.js
```

然后在相关视图（即HTML页面）中 `cas_common_messages.properties` 中的条目， `webjars.zxcvbn.js`：

```html
<script type="text/javascript" th:src="@{#{webjars.zxcvbn.js}}"></script>
```

#### 构建WebJAR

您可以在http://webjars.org上搜索webjar。 您可以阅读三种WebJAR，但是只要存在要用于Web资源的NPM或Bower包，就可以为任何版本自动创建NPM和Bower类型（如果尚不存在）用。 点击“添加webjar”按钮，然后按照说明进行操作。 如果在覆盖中自定义UI，则部署者可以将webjar作为依赖项添加到其覆盖项目中，并直接在html文件中或通过将条目添加到覆盖项目 `<code>common_messages.properties` src \ main\resources</code> 文件夹。
