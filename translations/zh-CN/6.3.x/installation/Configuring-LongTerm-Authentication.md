---
layout: 默认
title: CAS-长期认证
category: SSO & SLO
---

# 长期认证

此功能也称为 *记住我*，将SSO会话的时间延长到典型的小时数 这样用户无需登录CAS就可以花费数天或数周的时间。 有关与长期身份验证有关的安全问题的讨论，请参见 [安全指南](../planning/Security-Guide.html)

## 政策和部署注意事项

尽管用户可以选择建立长期身份验证会话，但是根据安全策略 部署人员必须通过权衡便利性和安全风险来确定长期身份验证会话的长度

长期身份验证会话的使用大大增加了将授予票证的票证在票证注册表中存储 丢失与长期SSO会话对应的授予票证的票证将需要 的用户重新向CAS进行身份验证。 这需要长期的验证会话安全策略必须不 自然过期将强制规定把票之前被终止 注册表组件，它提供了耐久存，如 [JPA票登记](../ticketing/JPA-Ticket-Registry.html)。

## 配置

调整你的到期策略，这样记得，我的身份验证请求 通过长期超时过期策略处理，以及其他请求 经由CAS默认SSO会话过期策略处理。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#ticket-granting-cookie) 和 [本指南](../configuration/Configuration-Properties.html#remember-me)。
