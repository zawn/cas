---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理-REST

查找用户的电子邮件和安全问题以及管理 和密码更新等任务被委派给用户定义的其余端点。

通过在WAR叠加中包含以下依赖项来启用REST支持：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-pm-rest</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-password-management)。

| 终点       | 方法   | 标头                  | 预期回应                |
| -------- | ---- | ------------------- | ------------------- |
| 获取电子邮件地址 | `得到` | `用户名`               | `200`。 正文中的电子邮件地址。  |
| 获取电话号码   | `得到` | `用户名`               | `200`。 体内的电话号码。     |
| 获取安全问题   | `得到` | `用户名`               | `200`。 安全问题在体内映射。   |
| 更新密码     | `邮政` | `的用户名`， `密码`， `旧密码` | `200`。 `正确/错误` 在体内。 |
