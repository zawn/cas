---
layout: 默认
title: CAS-属性发布
category: 属性
---

# 属性发布

属性返回到范围服务，并通过两步过程：

* [属性分辨率](Attribute-Resolution.html)：在建立所述主，的时间完成 *通常* 通过 `PrincipalResolver` ，其中属性是从各种来源的解析组件。
* 属性发布：采用者必须为服务明确配置属性发布，以便在验证响应中将解析的属性发布到服务。

<div class="alert alert-info"><strong>服务管理</strong><p>属性发布也可以通过
<a href="../services/Service-Management.html">服务管理工具</a>进行配置。</p></div>

## 主体ID属性

确定受CAS保护的应用程序应如何接收经过身份验证的用户ID。 有关更多信息，请参见 [本指南](Attribute-Release-PrincipalId.html)

## 属性发布政策

确定CAS应该如何向应用程序释放属性。 有关更多信息，请参见 [本指南](Attribute-Release-Policies.html)

## 属性同意

提供强制执行用户同意以释放属性的功能。 有关更多信息，请参见 [本指南](Attribute-Release-Consent.html)

## 缓存属性

控制应如何缓存CAS解析的属性。 有关更多信息，请参见 [本指南](Attribute-Release-Caching.html)

## 加密属性

默认情况下，CAS支持加密某些属性的功能，例如 票证和凭据。 属性编码器 的默认实现将使用每个服务密钥对加密敏感属性。 请参阅 [本指南](../services/Service-Management.html) 以了解更多信息。

## 属性定义

CAS属性可以用其他元数据修饰，以后可以根据 要求和与目标应用程序集成的性质来使用这些元数据。 要了解 ，请 [请参阅本指南](Attribute-Definitions.html)。
