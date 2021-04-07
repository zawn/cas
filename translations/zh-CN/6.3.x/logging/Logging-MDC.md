---
layout: 默认
title: CAS-MDC日志记录配置
category: 记录 & 审核
---

# 映射诊断上下文

为了唯一标记每个请求，CAS将上下文 信息放入 `MDC`，即Mapped Diagnostic Context的缩写。 有效地，此 转换为可用于日志记录上下文的许多特殊变量，这些特殊变量 可以传达有关请求或身份验证事件的性质的其他信息。

| 多变的             | 描述            |
| --------------- | ------------- |
| `remoteAddress` | HTTP请求的远端地址。  |
| `remoteUser`    | HTTP请求的远程用户。  |
| `服务器名称`         | HTTP请求的服务器名称。 |
| `服务器端口`         | HTTP请求的服务器端口。 |
| `地区`            | HTTP请求的语言环境。  |
| `内容类型`          | HTTP请求的内容类型。  |
| `contextPath`   | HTTP请求的上下文路径。 |
| `localAddress`  | HTTP请求的本地地址。  |
| `localPort`     | HTTP请求的本地端口。  |
| `远程端口`          | HTTP请求的远端端口。  |
| `pathInfo`      | HTTP请求的路径信息。  |
| `协议`            | HTTP请求的协议。    |
| `authType`      | HTTP请求的认证类型。  |
| `方法`            | HTTP请求的方法。    |
| `请求参数`          | HTTP请求的查询字符串。 |
| `requestUri`    | HTTP请求的请求URI。 |
| `方案`            | HTTP请求的方案。    |
| `时区`            | HTTP请求的时区。    |
| `主要的`           | CAS认证的主体ID。   |

此外，所有可用的请求属性，标头和参数都作为变量公开。

以上变量可用于日志记录模式：

- 单独使用 `%X` 来包含所有变量。
- 使用 `%X{key}` 包含指定的变量。

```xml
<Console name="console" target="SYSTEM_OUT">
    <PatternLayout pattern="%X{locale} %d %p [%c] - &lt;%m&gt;%n"/>
</Console>
```