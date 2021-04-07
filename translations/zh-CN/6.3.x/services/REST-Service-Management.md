---
layout: 违约
title: CAS - 注册服务注册处
category: 服务业
---

# 服务注册处

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-休息-服务-注册</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#restful-service-registry)。

| 操作   | 方法   | 身体                     | 响应           |
| ---- | ---- | ---------------------- | ------------ |
| 救    | `发布` | `注册服务` 对象              | `注册服务` 对象    |
| 删除   | `删除` | `注册服务` 对象              | 没有           |
| 负荷   | `获取` | 没有                     | `注册服务` 对象的集合 |
| 查找比德 | `获取` | 附加到端点网址的服务数字 ID 作为路径变量 | `注册服务` 对象    |
| 查找比德 | `获取` | 附加到端点网址的服务网址为路径变量      | `注册服务` 对象    |

预计所有操作将返回 `200` 状态代码。 所有其他响应状态代码将强制 CAS 考虑请求的操作无效。

## 自动初始化

在启动和配置允许的情况下，注册表能够自动初始化自己从 默认的JSON服务定义提供给CAS。 有关详细信息，请参阅本指南</a>。</p> 



## 实现

以下代码片段演示了 CAS 预期通过春季启动实现 REST API</em> *示例：</p> 



```java
@RestController
@RequestMapping（"/服务"）
公共类服务控制器=

    @DeleteMapping
    公共整数查找服务（@RequestBody最终注册服务）{
        //定位服务。。。
        返回HttpStatus.SC_OK;
    =

    @PostMapping
    公共注册服务保存（@RequestBody最终注册服务）^
        //保存所提供的服务。。。
        返回...：
    =

    @GetMapping（"/{id}"）
    公共注册服务查找服务 ById（@PathVariable（名称 ="id"）最终字符串 ID） {
        （数字可用.可比较（id）） {
            //通过其数字内部标识符定位服务
            返回...
        •
         //通过服务标识符定位服务
        返回...
    [

    @GetMapping
    公共注册服务[]负载（）{
        //加载服务。。。
        返回新的注册服务 []{...}：
    }
}
```
