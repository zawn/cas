---
layout: 默认
title: CAS-密码策略执行
category: 验证
---

# 密码策略执行

密码策略实施尝试：

- 根据用户帐户状态检测许多方案，这些方案原本会阻止用户身份验证。
- 对帐户状态接近可配置的到期日期的用户发出警告，并将流重定向到外部身份管理系统。

## LDAP

以下情况是通过防止在一个通用的方式通过认证默认视为错误 正常CAS登录流程。 LPPE截取身份验证流，检测上述标准错误代码。 错误代码随后在CAS登录流程中转换为正确的消息，并允许用户采取适当的措施， 完全说明了问题的性质。

- `帐户被锁定`
- `帐户已禁用`
- `ACCOUNT_EXPIRED`
- `INVALID_LOGON_HOURS`
- `INVALID_WORKSTATION`
- `PASSWORD_MUST_CHANGE`
- `PASSWORD_EXPIRED`

LDAP错误到CAS的翻译工作都是 的处理 [ldaptive](http://www.ldaptive.org/docs/guide/authentication/accountstate)。 要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#ldap-authentication)。

### 帐户到期通知

当帐户即将到期时，LPPE也可以警告用户。 到期策略为 这是通过预先配置的LDAP属性确定的，并具有默认值。

## JDBC

某些数据库身份验证方案对通过CAS设置中定义的列名检测锁定/禁用/等帐户 要查看CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#database-authentication)。
