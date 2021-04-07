---
layout: 违约
title: CAS - 密码策略执行
category: 认证
---

# 密码政策执行

密码政策执行尝试：

- 检测一些场景，否则会根据用户帐户状态阻止用户身份验证。
- 警告帐户状态接近可配置到期日期的用户，并将流量重定向到外部身份管理系统。

## 阿尔达普

以下情景默认被视为错误，通过 正常的 CAS 登录流以通用方式阻止身份验证。 LPPE 拦截身份验证流程，检测上述标准错误代码。 然后，错误代码在 CAS 登录流中转换为适当的消息，并允许用户采取适当操作， 充分解释问题的性质。

- `ACCOUNT_LOCKED`
- `ACCOUNT_DISABLED`
- `ACCOUNT_EXPIRED`
- `INVALID_LOGON_HOURS`
- `INVALID_WORKSTATION`
- `PASSWORD_MUST_CHANGE`
- `PASSWORD_EXPIRED`

将LDAP错误翻译成CAS工作流程，都是由 [](http://www.ldaptive.org/docs/guide/authentication/accountstate) 处理的。 要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#ldap-authentication)查看本指南。

### 账户到期通知

LPPE 还能够在帐户即将过期时警告用户。 到期策略 通过预配置的具有默认值的 LDAP 属性确定。

## 京城

某些数量的数据库身份验证计划对于通过 CAS 设置中定义的列名检测锁定/禁用/等帐户 的支持有限。 要查看 CAS 物业的相关列表，请 [](../configuration/Configuration-Properties.html#database-authentication)查看本指南。
