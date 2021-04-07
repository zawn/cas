---
layout: 违约
title: CAS - 密码管理
category: 密码管理
---

# 密码管理 - 格罗夫

帐户和密码可以使用自定义的 Groovy 脚本进行确定和处理。 脚本的大纲可能与以下内容相匹配：

```groovy
def更改（对象[]args）{
    def凭据=args[0]
    定义密码更改bean=args[1]
    def记录器=args[2]
    返回真实
}

def查找电子邮件（对象[] args）{
    def用户名=args[0]
    def记录器=args[1]
    返回"cas@example.org"
}

找到手机（对象[]args）{
    def用户名 args[0]
    def记录器=args[1]
    返回"1234567890"
=

d找到使用器（对象[]args）{
    def电子邮件=args[0]
    def记录器=args[1]
    返回"卡苏斯"
=

d得到安全问题（对象[]args）{
    def用户名=args[0]
    定义记录器=args[1]
    返回[安全问题1："安全回答1"]
}
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#groovy-password-management)。
