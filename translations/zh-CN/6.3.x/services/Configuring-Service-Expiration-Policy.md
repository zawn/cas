---
layout: 默认
title: CAS-配置服务到期策略
category: 服务
---

# 配置服务到期策略

可以为在CAS中注册的应用程序分配可选的到期策略，该策略控制注册的生存期。 一旦该服务被视为过期，它将自动被禁用或从CAS注册表 删除，并且已定义并分配给该服务的 [相关联系人](Configuring-Service-Contacts.html)

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry-notifications)。

以下是一个示例JSON文件：

```json
{
  “ @class”：“ org.apereo.cas.services.RegexRegisteredService”，
  “ serviceId”：“ ^ https：//.+”，
  “ name”：“示例服务”，
  “ id”：100 ，
  “ expirationPolicy”：{
    “ @class”：“ org.apereo.cas.services.DefaultRegisteredServiceExpirationPolicy”，
    “ deleteWhenExpired”：true，
    “ notifyWhenDeleted”：false，
    “ notifyWhenExpired”：false，
    “ expirationDate” ：“ 2017-10-05”
  }
}
```

默认情况下，以下设置可用于到期策略：

| 场地                  | 描述                                                                                                                                                                                                                                                   |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `截止日期`              | 注册记录被视为过期的日期。 到期日期可指定 `15：2011-12-03T10 30`， `1980年9月24日下午4点30`， `2014年9月24日6:30 AM`， `2013年9月24日18 ：45`， `2017年9月24日` 或 `2017年10月25日` 格式。                                                                                                           |
| `deleteWhenExpired` | 当 `真`，是否以及何时到期去除CAS服务注册表中的应用。 否则，应用程序记录将被标记为禁用。                                                                                                                                                                                                      |
| `notifyWhenDeleted` | 假设已定义具有电子邮件地址或电话号码的有效联系人，并且CAS配置为发送 [电子邮件](../notifications/Sending-Email-Configuration.html) 或 [SMS通知](../notifications/SMS-Messaging-Configuration.html)，则通过电子邮件或文本通知 [联系人](Configuring-Service-Contacts.html) 该应用程序。 仅当应用程序已过期并且将要从注册表中删除时，才发送通知。 |
| `notifyWhenExpired` | 假设已定义具有电子邮件地址或电话号码的有效联系人，并且CAS配置为发送 [电子邮件](../notifications/Sending-Email-Configuration.html) 或 [SMS通知](../notifications/SMS-Messaging-Configuration.html)，则通过电子邮件或文本通知 [联系人](Configuring-Service-Contacts.html) 该应用程序。 仅在应用程序过期时发送通知。               |
