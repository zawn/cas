---
layout: 违约
title: CAS - 配置服务过期政策
category: 服务业
---

# 配置服务到期政策

在 CAS 注册的申请可以分配一个可选的过期政策，以控制注册的寿命。 一旦服务被视为过期，它将自动禁用或从 CAS 注册表中删除 ， [相关联系人](Configuring-Service-Contacts.html) 定义并分配给服务将通过电子邮件或短信通知。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#service-registry-notifications)。

示例 JSON 文件如下：

```json
•
  "@class"："组织.apereo.cas.服务.注册服务"，
  "服务id"："^https://.+"，
  "名称"："样本服务"，
  "id"：100，
  "到期政策"：{
    "@class"："org.apereo.cas.服务。默认注册服务探索政策"，
    "删除时删除"：真实，
    "通知时删除"：虚假，
    "通知时删除"：虚假，
    "到期日期"："2017-10-05"
  }
}
```

默认情况下，到期保单可设置以下设置：

通过电子邮件或短信通知 [联系人](Configuring-Service-Contacts.html) 申请，假设定义了具有电子邮件地址或电话号码的有效联系人，并配置CAS</a> 发送 [封电子邮件或](../notifications/Sending-Email-Configuration.html)短信通知。 通知仅在申请过期且即将从注册表中删除时发送。</td> </tr> 

通过电子邮件或短信通知 [联系人](Configuring-Service-Contacts.html) 申请，假设定义了具有电子邮件地址或电话号码的有效联系人，并配置CAS</a> 发送 [封电子邮件或](../notifications/Sending-Email-Configuration.html)短信通知。 通知仅在申请过期时发送。</td> </tr> </tbody> </table>
