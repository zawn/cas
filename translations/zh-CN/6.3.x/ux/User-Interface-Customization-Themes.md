---
layout: 默认
title: 动态主题-用户界面自定义-CAS
category: 用户界面
---

# 动态主题

通过引入 [Service Management应用程序](../services/Service-Management.html)，部署人员现在能够基于 种不同的服务切换主题。 例如，您可能想为职员应用程序和学生应用程序使用不同的登录屏幕（不同的样式），或者要为白天和黑夜 本文档可以帮助您完成基本设置以实现此目的。

## 静态主题

CAS被配置为基于服务注册表中给定注册服务 `主题` 通过此方法激活的主题仍将保留CAS的默认视图，但会将装饰（例如CSS和Javascript）应用于视图。 视图的物理结构无法通过此方法修改。

### 配置

- 将 `[theme_name].properties` `src / main / resources` 文件夹的根目录中。 该文件的内容应与以下内容匹配：

```properties 
＃主题CSS文件的路径
cas.standard.css.file = / themes /[theme_name]/css/cas.css

＃主题JS文件的路径
cas.standard.js.file = / themes /[theme_name]/ js / cas。 js

＃通过通用布局显示主题徽标的路径
＃cas.logo.file = / images / logo.png     

＃确定是否应显示抽屉菜单
＃cas.drawer-menu.enabled = true                    

＃主题各种标题/标题中使用的名称
＃cas.theme.name = Example主题

＃主题收藏图标文件的路径。
＃cas.favicon.file = / themes / example / images / favicon.ico

＃启用并显示通知菜单
＃cas.notifications-menu.enabled = true

＃启用并显示抽屉菜单
＃cas.drawer- menu.enabled = true
```

- 创建目录 `src / main / resources / static / themes /[theme_name]`。 将特定于主题的 `cas.css` 和 `cas.js` `css` 和 `js`的适当目录中。
- `theme` 属性下为服务定义指定 `[theme_name]`

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//www.example.org”，
  “ name”：“ MyTheme”，
  “ theme” ：“[theme_name]”，
  “ id”：1000
}
```

值可以使用 [Spring Expression Language](../configuration/Configuration-Spring-Expressions.html) 语法。

## 主题观点

CAS还可以利用服务的关联主题来有选择地选择将使用哪些UI视图集来生成 标准视图（`casLoginView.html`等）。 为目标的主题页面集在结构上完全不同，以至于使用简单主题来扩大默认视图不可行，则此功能特别有用。 在这种情况下，可能需要新的视图页面。

默认情况下，与特定主题关联的视图应该位于： `src / main / resources / templates /<theme-id>`。 请注意，CAS 视图和基于主题的视图都可以在Web应用程序上下文之外进行外部化。 外部化后，可以通过CAS属性在以主题命名 例如，如果CAS视图的外部路径为 `/ etc / cas / templates` 主题 `示例` 视图模板文件可能位于 `/ etc / cas / templates / sample /`。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#views)。

### 配置

- 将 `[theme_name].properties` `src / main / resources` 文件夹的根目录中。 该文件的内容应与以下内容匹配：

```properties
cas.standard.css.file = / themes /[theme_name]/css/cas.css
cas.standard.js.file = / themes /[theme_name]/js/cas.js
```

- `src / main / resources / templates /<theme-id>`）将默认的视图页面集克隆到新目录中。
- `theme` 属性下为服务定义指定主题名称。

## 主题集

CAS提供了一个呈现许多现成主题的模块。 的意图是说明常见问题并提供常见用例，并尝试使大部分配置自动化。

通过在WAR叠加中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas服务器支持主题集合</artifactId>
     <version>${cas.version}</version>
</dependency>
```

此模块提供了以下主题，可以将其分配给服务定义：

| 主题   | 描述                             |
| ---- | ------------------------------ |
| `例子` | 结合了定制的CSS，Javascript和视图的参考示例主题 |

上面的主题集合还可以作为参考示例，说明如何使用 自定义CSS，Javascript以及相关的视图和片段来定义主题。

## Groovy主题

如果定义了多个主题，则可能需要动态确定给定服务定义的主题。 为此，您可以通过自己设计的Groovy脚本来计算最终主题名称。 分配给服务定义的主题需要指向脚本的位置：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//www.example.org”，
  “ name”：“ MyTheme”，
  “ theme” ：“ file：///etc/cas/config/themes.groovy”，
  “ id”：1000
}
```

脚本本身可以设计为：

```groovy
import java.util。*

def字符串运行（最终对象... args）{
    def服务= args[0]
    def已注册服务= args[1]
    def queryStrings = args[2]
    def标头= args[3]
    def logger = args[4]

    //确定主题...

    返回null
}
```

返回 `null` 或空白将使CAS切换到默认主题。 以下参数可以传递给Groovy脚本：

| 范围             | 描述                                 |
| -------------- | ---------------------------------- |
| `服务`           | 代表请求服务的对象。                         |
| `已注册的服务`       | 代表注册表中匹配的注册服务的对象。                  |
| `queryStrings` | 在请求中找到的所有查询字符串的文本表示形式（如果有）。        |
| `标头`           | `映射` 及其在请求中找到的值（如果有）。              |
| `记录器`          | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

## RESTful主题

与上述选项有些类似，您可以通过自己设计的REST端点计算最终主题名称。 分配给服务定义的主题需要指向REST API的位置。 端点必须设计为通过 `GET` 请求 `application / json` 返回的状态码 `200` 允许CAS读取响应的主体以确定主题名称。 空的响应正文将使CAS切换为默认主题。

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//www.example.org”，
  “ name”：“ MyTheme”，
  “ theme” ：“ https://themes.example.org”，
  “ id”：1000
}
```

以下参数可以传递给Groovy脚本：

| 范围   | 描述        |
| ---- | --------- |
| `服务` | 请求服务的标识符。 |
