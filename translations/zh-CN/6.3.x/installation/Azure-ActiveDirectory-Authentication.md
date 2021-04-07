---
layout: 违约
title: CAS - 微软 Azure 活动目录认证
category: 认证
---

# 微软 Azure 活动目录认证

[Azure 活动目录 （Azure AD）](https://docs.microsoft.com/en-us/azure/active-directory/fundamentals/active-directory-whatis) 是微软基于云的身份和访问管理服务。 此处描述的功能允许用户使用 Azure 活动目录作为帐户存储进行身份验证，并使用 Microsoft Graph 可选地提取用户属性。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 祖雷达 - 身份验证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#microsoft-azure-active-directory-authentication)。

## 主要属性

如果主要属性需要从 Azure 活动目录中提取，而无需 必须验证凭据，也可以使用上述依赖性。 要查看 CAS 属性的相关列表，请[查看本指南]。配置/配置属性.html#微软-蔚蓝-主动目录。
