---
layout: 默认
title: CAS-Webflow装饰
category: Webflow管理
---

# Webflow装饰品

有时您可能需要修改CAS登录Web流程，以包括通常从外部资源和终结点获取的其他数据，这些数据也可能被认为是敏感的，并且可能需要凭据才能访问。 示例包括在CAS登录屏幕上显示公告或其他类型的动态数据。 您当然可以将Webflow</a>

，以在登录流中包括其他状态和操作，以调用端点和数据源以获取数据。 一个更简单的选择是让CAS通过接触您的REST端点等自动装饰登录Web流程，同时注意内部Web流程的配置。 随着CAS开始呈现登录视图，同时保留了将来装饰Webflow的其他部分的权利，此类装饰器将特别受到要求。</p> 

请注意，装饰器仅将数据注入到webflow上下文中，以后该数据可用于CAS登录视图等等。 一旦数据可用，您仍然有责任使用该数据在适当的视图中正确显示它并正确设置样式。



## Groovy装饰器

Groovy登录装饰器允许使用外部Groovy脚本将数据注入Webflow上下文，该脚本可能采用以下形式：



```groovy
def run（Object [] args）{
     def requestContext = args[0]
     def applicationContext = args[1]
     def logger = args[2]

     logger.info（“装饰Webflow ...”）
     requestContext.flowScope.put（“ decoration”，...）
 }
```


传递的参数如下：

| 范围                   | 描述                                                |
| -------------------- | ------------------------------------------------- |
| `requestContext`     | Spring Webflow `RequestContext` 承载各种类型的作用域作为数据容器。 |
| `applicationContext` | Spring应用程序上下文。                                    |
| `记录器`                | 用于在需要时发出日志消息的Logger对象。                            |


要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow-login-decorations)。



## REST装饰器

RESTful登录装饰器允许用户通过访问外部REST API将数据注入到Webflow上下文中。 如果端点以 `200` 状态码响应，则CAS会将响应主体解析为JSON对象，并将结果填充到 `装饰`下的 `flowScope` 容器中。 请记住，数据塞进一个Webflow **MUST** 可序列化的，如果你打算沿着复杂的对象类型和花哨的数据结构传递，你需要确保他们能够安全，并最终转化为一个简单的 `字节[]`。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spring-webflow-login-decorations)。
