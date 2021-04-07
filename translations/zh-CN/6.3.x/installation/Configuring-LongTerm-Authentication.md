---
layout: 违约
title: 中科院 - 长期认证
category: 索 & 斯洛
---

# 长期认证

此功能也称为 *记住我*，将 SSO 会话的长度延长到通常的小时数 以便用户无需登录 CAS 即可使用数天或数周。 请参阅 [安全指南](../planning/Security-Guide.html) ，讨论与长期认证相关的安全问题。

## 政策和部署考虑

虽然用户可以选择建立长期身份验证会话，但根据安全策略，通过 配置确定会话的持续时间。 部署人员必须通过权衡便利性和安全风险来确定 的长期认证会话的长度。

长期认证会话的使用大大延长了票证授予票 存储在票证注册表中的时间长度。 丢失与长期 SSO 会话相对应的出票票证需要 用户重新验证 CAS。 要求长期认证会议不得在自然到期前终止 的安全策略将要求提供持久存储的票证 注册表组件，例如 [JPA 票证注册处](../ticketing/JPA-Ticket-Registry.html)。

## 配置

调整您的过期策略，以便通过长期超时到期策略 处理记住我的身份验证请求，并通过 CAS 默认 SSO 会话到期策略处理 的其他请求。

要查看 CAS 属性的相关列表，请 [](../configuration/Configuration-Properties.html#ticket-granting-cookie) 查看本指南，并 [本指南](../configuration/Configuration-Properties.html#remember-me)。
