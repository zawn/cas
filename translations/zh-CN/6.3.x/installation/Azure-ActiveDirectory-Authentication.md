---
layout: 默认
title: CAS-Microsoft Azure Active Directory身份验证
category: 验证
---

# Microsoft Azure Active Directory身份验证

[Azure Active Directory（Azure AD）](https://docs.microsoft.com/en-us/azure/active-directory/fundamentals/active-directory-whatis) 是Microsoft的基于云的身份和访问管理服务。 此处描述的功能允许使用 Azure Active Directory作为帐户存储来验证凭据，并可以选择使用Microsoft Graph获取用户属性。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-azuread-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#microsoft-azure-active-directory-authentication)。

## 主要属性

如果需要从Azure Active Directory中获取主体属性，而无需对 进行身份验证，则可以使用上述依赖项。 要查看CAS属性的相关列表，请[阅读本指南]（.. configuration / Configuration-Properties.html＃microsoft-azure-active-directory。
