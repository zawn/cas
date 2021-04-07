---
layout: 默认
title: CAS-配置主体分辨率
category: 配置
---

# 概述
主体解析将身份验证凭据中的信息转换为安全主体 ，该主体通常包含其他 元数据属性（即，用户详细信息，例如从属关系，组成员身份，电子邮件，显示名称）。

CAS主体包含一个唯一的标识符，通过该标识符，所有请求 服务都将知道已认证的用户。 主体还包含可选的 [属性，可以将这些属性](../integration/Attribute-Release.html) 释放给服务以支持授权和个性化。 主体解析是凭据身份验证后发生

默认情况下，CAS `AuthenticationHandler` 组件提供基本的主体解析机制。 例如， `LdapAuthenticationHandler` 组件支持获取属性并从 LDAP查询中设置主体ID属性。 在所有情况下，都从与提供身份验证的存储库相同的存储库中解析主体。

在许多情况下，有必要通过一种方式执行身份验证，并通过另一种方式解析主体。 `PrincipalResolver` 组件提供了此功能。 这种混合匹配策略 的常见用例是X.509身份验证。 通常将证书存储在LDAP目录中，并将目录查询为 从目录属性中解析主体ID和属性。 `X509CertificateAuthenticationHandler` 可以 与基于LDAP的主体解析器组合以适应这种情况。

## 配置

CAS使用人员目录库针对多个数据 源提供灵活的主体解析服务。 `PersonDirectoryPrincipalResolver` 的关键 `IPersonAttributeDao` 对象的定义。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#principal-resolution)。

## PrincipalResolver与AuthenticationHandler

在前者提供足够功能的任何情况下，应优先使用 `AuthenticationHandler` 组件提供的主要解析机制 `PrincipalResolver` 如果由身份验证处理程序 解析的委托人就足够了，那么 `null` 值来代替解析器bean id。

