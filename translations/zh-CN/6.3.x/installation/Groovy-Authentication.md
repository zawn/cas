---
layout: 违约
title: CAS - 沟槽认证
category: 认证
---

# 沟槽身份验证

使用 Groovy 脚本验证和验证凭据。 证书验证、主要转换、 处理密码政策等相关事项的任务均由 Groovy 脚本承担全部责任。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>套机服务器支持通用</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#groovy-authentication)。

Groovy 脚本可设计为：

```groovy
进口组织.apereo.cas.认证.*
进口组织.apereo.cas.认证.*
进口组织.apereo.cas.认证.元数据.*

进口 javax.安全.auth.登录.*

验证（最终对象...args）{
    定义身份验证员=args[0]
    def凭据=args[1]
    除法服务管理器=args[2]
    def主要工厂=args[3]
    d def记录器=args[4]              

    /*
     *找出如何验证凭据。。。
     */
    如果（认证工作正确）=
        定义本金=主要工厂。创建原则（凭据.用户名）：
        返回新的默认验证汉德勒执行结果（认证手勒，
                新的基本信用元数据（凭据），
                本金，
                新的阵列列表<>（0）;
    =
    抛出新的失败记录除名（）：
=

定义支持信用（最终对象。。。args）{
    分级证书=args[0]
    def记录器=args[1]
    返回凭据！=空
}

防御支持信用类（最终对象。。。args）{
    def凭据Clazz=args[0]
    定义记录器=args[1]
    返回凭据Clazz==用户名密码信用.class
}
```
