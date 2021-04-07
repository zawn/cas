---
layout: 默认
title: CAS-Groovy身份验证
category: 验证
---

# Groovy身份验证

使用Groovy脚本验证和认证凭据。 凭证验证，主体转换， 处理密码策略以及所有其他相关事项的任务是Groovy脚本的唯一责任。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-generic</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#groovy-authentication)。

Groovy脚本可以设计为：

```groovy
导入org.apereo.cas.authentication。*
导入org.apereo.cas.authentication.credential。*
导入org.apereo.cas.authentication.metadata。*

导入javax.security.auth.login。*

def身份验证（最终对象... args）{
    def authenticationHandler = args[0]
    def凭据= args[1]
    def servicesManager = args[2]
    defPrincipalFactory = args[3]
    def logger = args[4]              

    / *
     *弄清楚如何验证凭据...
     * /
    如果（authenticationWorksCorrectly（））{
        def主体= principalFactory.createPrincipal（credential.username）;
        返回新的DefaultAuthenticationHandlerExecutionResult（authenticationHandler，
                新的BasicCredentialMetaData（credential），
                主体，
                新的ArrayList<>（0））;
    }
    抛出新的FailedLoginException（）;
}

defsupportsCredential（final Object ... args）{
    def凭据= args[0]
    def logger = args[1]
    返回凭据！= null
}

def supportCredentialClass（final Object ... args）{
    def credentialClazz = args[0]
    def logger = args[1]
    return credentialClazz == UsernamePasswordCredential.class
}
```
