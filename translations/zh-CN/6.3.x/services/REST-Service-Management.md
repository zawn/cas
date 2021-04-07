---
layout: 默认
title: CAS-RESTful服务注册表
category: 服务
---

# RESTful服务注册表

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-rest-service-registry</artifactId>
     <version>${cas.version}</version>
</dependency>
```

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#restful-service-registry)。

| 手术       | 方法   | 身体                     | 回复                        |
| -------- | ---- | ---------------------- | ------------------------- |
| 保存       | `邮政` | `RegisteredService` 对象 | `RegisteredService` 对象    |
| 删除       | `删除` | `RegisteredService` 对象 | 没有任何                      |
| 加载       | `得到` | 没有任何                   | `RegisteredService` 对象的集合 |
| FindById | `得到` | 服务数字ID作为路径变量附加到端点URL   | `RegisteredService` 对象    |
| FindById | `得到` | 服务URL作为路径变量附加到端点URL    | `RegisteredService` 对象    |

预期所有操作都将返回 `200` 状态代码。 所有其他响应状态代码将强制CAS认为请求的操作无效。

## 自动初始化

在启动和配置允许的情况下，注册表可以从 默认JSON服务定义中自动进行初始化。 有关更多信息，请参见 [本指南](AutoInitialization-Service-Management.html)

## 执行

以下代码段演示了CAS通过Spring Boot期望的REST API *示例*

```java
@RestController
@RequestMapping（“ / services”）
公共类ServicesController {

    @DeleteMapping
    公共整数findByServiceId（@RequestBody final RegisteredService服务）{
        //找到服务...
        返回HttpStatus.SC_OK;
    }

    @PostMapping
    public RegisteredService save（@RequestBody final RegisteredService service）{
        //保存提供的服务...
        返回 ...;
    }

    @GetMapping（“ /{id}”）
    public RegisteredService findServiceById（@PathVariable（name =“ id”）最终字符串id）{
        if（NumberUtils.isParsable（id））{
            //通过数字内部定位服务标识符
            返回...
        }
         //通过服务标识符
        查找服务... return ...
    }

    @GetMapping
    public RegisteredService [] load（）{
        //加载服务...
        返回新的RegisteredService []{...}；
    }
}
```
