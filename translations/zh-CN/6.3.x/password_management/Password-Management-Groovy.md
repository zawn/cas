---
layout: 默认
title: CAS-密码管理
category: 密码管理
---

# 密码管理-Groovy

可以使用定制的Groovy脚本确定和处理帐户和密码。 脚本的轮廓可能与以下内容匹配：

```groovy
def change（Object [] args）{
    def凭据= args[0]
    def passwordChangeBean = args[1]
    def logger = args[2]
    return true
}

def findEmail（Object [] args）{
    def用户名= args[0]
    def logger = args[1]
    return“ cas@example.org”
}

def findPhone（Object [] args）{
    def username = args[0]
    def logger = args[1]
    return“ 1234567890”
}

def findUsername（Object [ ] args）{
    def email = args[0]
    def logger = args[1]
    return“ casuser”
}

def getSecurityQuestions（Object [] args）{
    def username = args[0]
    def logger = args[1]
    return [securityQuestion1： “ securityAnswer1”]
}
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#groovy-password-management)。
