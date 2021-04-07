---
layout: 违约
title: CAS - 密码管理
category: 密码管理
---

# 密码管理 - 休息

定位用户的电子邮件和安全问题以及管理 和密码更新等任务将委托给用户定义的休息端点。

通过在战争覆盖中包括以下依赖项来支持 REST 支持：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持下午休息</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-password-management)。

| 端点       | 方法   | 头                  | 预期响应               |
| -------- | ---- | ------------------ | ------------------ |
| 获取电子邮件地址 | `获取` | `用户名`              | `200`。 电子邮件地址在身体。  |
| 获取电话号码   | `获取` | `用户名`              | `200`。 体内的电话号码。    |
| 获取安全问题   | `获取` | `用户名`              | `200`。 身体中的安全问题映射。 |
| 更新密码     | `发布` | `用户名`， `密码`， `老密码` | `200`。 `真假` 在体内。   |
