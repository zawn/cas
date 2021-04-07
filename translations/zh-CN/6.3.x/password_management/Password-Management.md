---
layout: 违约
title: CAS - 密码管理
category: 密码管理
---

# 密码管理

如果身份验证因密码政策被拒绝而失败，CAS 能够拦截该请求 ，并允许用户更新已到位的帐户密码。 CAS 的密码管理功能相当温和，如果功能不足以满足您的政策，您可以始终将 CAS 重定向到使用完全负责管理帐户密码和关联流量的单独独立应用程序。

CAS 还可以允许用户自愿重置其密码。 忘记帐户密码 的人可能会在其注册的电子邮件地址和/或电话中收到带有基于时间的到期政策的安全链接。 链接将允许用户提供他/她预先定义的安全问题的答案，如果成功完成， 将允许用户下一次重置其密码并再次登录。 您还可以指定接受密码的模式。

默认情况下，用户成功更改密码后，将重定向到登录屏幕 输入新密码并登录。 CAS 还可以配置为在成功更改后 用户自动登录。 此行为可以通过 CAS 设置进行更改。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - pm - 网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

<div class="alert alert-info"><strong>亚格尼</strong><p>您不需要在配置和叠加中明确包含此模块
。 这只是为了教你它的存在。</p></div>

## 配置

要了解有关可用通知选项的更多信息，请 [本指南](../notifications/SMS-Messaging-Configuration.html) 或本指南</a>

。 </p> 

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#password-management)。



## 重新整合

密码重置尝试可以保护和集成 [谷歌重述](https://developers.google.com/recaptcha)。 这就要求存在用于基本集成的 RECAPTCHA 设置，并指示密码管理流程通过重新CAPTCHA 打开和验证请求。 要查看 CAS 属性的相关列表，请 [](../configuration/Configuration-Properties.html#google-recaptcha-integration) 查看本指南，并 [本指南](../configuration/Configuration-Properties.html#password-management)。



## 密码历史记录

CAS 允许跟踪和存储回收密码的策略。 回收的密码保存在用户帐户的存储中， 在密码更新后进行有效性检查。 

启用密码历史记录功能后，密码可以通过 Groovy 或内存后端在历史记录中进行跟踪。 特定的 存储选项也可能为密码历史记录提供自己的支持。



### 槽的

密码历史记录跟踪，一旦启用，可以传递到外部 Groovy 脚本：



```groovy
def存在（对象[]args）{
    def请求=args[0]
    定义记录器=args[1]
    返回虚假
}

def存储（对象[]args）{
    def 请求=args[0]
    def记录器=args[1]
    返回真实
}

d取取所有（对象[]args）{
    def记录器=args[0]
    返回[]
=

d取（对象[]args）{
    def用户名=args[0]
    def记录器=args[1]
    返回[]
=   

def删除（对象]]ar ）{ 
    def用户名=args[0]
    dd伐木机=args[1]
=

d去除所有（对象[]args）{ 
    定义记录器=args[0]
}
```


`请求` 参数封装了 `密码更改请求` 对象，带有 `个用户名` ，</code> 字段 密码。</p>

<h2 spaces-before="0">杰森存储</h2>

<p spaces-before="0">帐户和密码可能存储在静态适度的 JSON 资源中。 此选项在开发过程中最有用， 
用于演示目的。 要了解更多，请 <a href="Password-Management-JSON.html">本指南</a>。</p>

<h2 spaces-before="0">格罗夫存储</h2>

<p spaces-before="0">帐户和密码可以通过 Groovy 脚本进行处理和计算。 要了解更多，请 
 <a href="Password-Management-Groovy.html">本指南</a>。</p>

<h2 spaces-before="0">LDAP 存储</h2>

<p spaces-before="0">帐户密码和安全问题可能存储在 LDAP 服务器中。 要了解更多，请 
 <a href="Password-Management-LDAP.html">本指南</a>。</p>

<h2 spaces-before="0">京打存储</h2>

<p spaces-before="0">帐户密码和安全问题可以存储在关系数据库中。 要了解更多，请 
 <a href="Password-Management-JDBC.html">本指南</a>。</p>

<h2 spaces-before="0">休息存储</h2>

<p spaces-before="0">帐户密码和安全问题也可以使用 REST API 进行管理。 要了解更多 
请 <a href="Password-Management-REST.html">本指南</a>。</p>

<h2 spaces-before="0">习惯</h2>

<p spaces-before="0">要设计自己的密码管理存储选项和策略，请 
 <a href="Password-Management-Custom.html">本指南</a>。</p>
