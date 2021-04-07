---
layout: 违约
title: CSS & 爪哇脚本 - 用户界面自定义 - CAS
category: 用户界面
---

# CSS

默认样式都包含在位于 `src/主/资源/静态/css/cas.css`的单个文件中。 此位置设置为 `个 cas 主题默认。 属性`。 CAS默认使用 [Material.io](https://material.io/) 库和设计规范作为用户体验的基础。

如果您想创建自己的 `css/自定义.css文件`，例如，您将需要更新该文件中的 `cas.标准.css.文件` 密钥。

```bash
cas.标准.css.文件=/css/卡.css
卡.js.文件=/js/cas.js
```

## 响应式设计

CSS 媒体查询为 CAS 带来了响应式设计功能，使采用者能够将 集中在所有适当设备和平台的一个主题上。 这些查询在 相同的 `.css` 文件中定义。 他们关注推特引导断点和网格。

# 爪哇脚本

如果您需要添加一些 JavaScript，请随时附加 `src/主/资源/静态/js/cas.js`。

例如，您还可以创建自己的 `自定义.js` 文件，并从 `脚本内调用它.html` 类似：

```html
<script type="text/javascript" src="/js/custom.js"></script>
```

如果您正在开发每个服务的主题，每个主题还能够根据 `cas.标准.js.文件` 设置指定自定义 `cas.js` 文件。

最重要的是，CAS 会自动使用以下 Javascript 库：

* [杰奎里](https://jquery.com/)
* [网格/弹性公用设施的引导](https://getbootstrap.com/docs/4.5/getting-started/contents/#css-files)
* [Material.io](https://material.io/)

## 脚本加载

CAS 提供回调功能，允许在脚本加载完成时通知采用者，这将是执行/加载实际页面内依赖于 JQuery 的其他 Javascript 相关功能的安全时间。

```javascript
功能jquery已准备好（）{
    //自定义爪哇脚本任务现在可以通过JQuery执行。。。
}
```

## 检查卡普洛克

在输入凭据密码时，当 CAPSLOCK 密钥打开时，CAS 将显示简短的警告。 此检查由 `.js` 文件执行。

## 浏览器曲奇支持

CAS 要遵守单个登录会话，浏览器必须支持并接受 Cookie。 如果浏览器关闭了对 Cookie 的支持，CAS 将通知 用户。 此行为通过 `.js` 文件进行控制。

## 保存锚碎片

锚点/片段可能会在重定向中丢失，因为表单柱的服务器端处理程序忽略客户端锚，除非附在表单POST url 中。 如果您希望 CAS 认证的应用程序能够在书签时使用锚/片段，则需要这样做。 CAS 在默认情况下进行配置，以在指定时保留锚定片段。 你没有什么可做的了。

### 爪哇脚本/CSS库的网络照片

CAS 应用程序在 CAS Webapp 中包装第三方静态资源，而不是引用 CDN 链接，以便 CAS 可以部署在互联网接入有限的 网络上。

第三方静态资源被包装在"WebJAR"罐中，并通过servlet `3.0` 功能 提供，该功能将 `META-INF/资源下的任何文件夹合并到web应用程序罐中` 与应用程序的 Web 根部。

对于修改 CAS 的开发人员，如果添加或修改第三方库，则步骤是：

- 将 WebJAR 依赖性添加到 `依赖项中。在 <code>的 ex.library.webjars` 部分中添加</code> 。
- 将依赖性版本添加到 `语法.属性` ，并将其用于 ``依赖中。
- 添加每个资源（例如.js或 css）</code> `核心/cas-服务器-核心 web/src/主/资源/cas_common_messages.属性的条目。</li>
<li>参考 <code>gradle.` 在 URL 中的版本，它将在构建时间进行过滤）。

<div class="alert alert-info"><strong>资源缓存</strong><p>当版本号更改和资源升级时，构建尝试再次重建所有相关模块。 如果您确实需要强制移除缓存的工件并重新重新包装应用程序，请在 <code>核心/cas-服务器-web</code> 模块内运行构建的 <code>清洁</code> 任务。</p></div>

例如：

```properties
网络贾尔斯.zxcvbn.js/网络贾尔斯/zxcvbn/迪斯特/兹克斯克夫本.js
```

然后参考相关视图（即 HTML 页面）中 `cas_common_messages.属性` 的条目，其中条目 `webjars.zxcvbn.js`：

```html
<script type="text/javascript" th:src="@{#{webjars.zxcvbn.js}}"></script>
```

#### 构建网络贾尔斯

您可以在 http://webjars.org 搜索网络聊天。 有三种口味的WebJAR，你可以阅读，但NPM和鲍尔类型可以自动创建为任何版本（如果他们不存在），只要有一个NPM或鲍尔包的网络资源，你想使用。 单击"添加 Webjar"按钮并按照说明操作。 如果在叠加中自定义 UI，则部署人员可以将 Webjars 添加为其叠加项目的依赖项，并直接在 html 文件中或通过在叠加项目 `src\r` 文件夹中添加 `common_messages.属性` 文件来引用资源的网址。
