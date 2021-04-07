---
layout: 默认
title: CAS-接受身份验证
category: 多因素身份验证
---

# 接受身份验证

[Acceptto](https://www.acceptto.com) 端到端基于风险的multiFactor身份验证来保护您的员工身份。

首先访问 [Acceptto文档](https://www.acceptto.com/acceptto-mfa-rest-api/)。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-acceptto-mfa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

该集成增加了对多因素身份验证和QR无密码身份验证的支持。

## 与DBFP集成

集成能够处理与DBFP的集成，并将设置一个名为 `jwt` 的cookie，该cookie会传递给Acceptto API。 该参数包含服务器用来评估身份验证请求风险的值，包括浏览器指纹，用户的IP地址和用户浏览器的GPS位置。 服务器将此数据与用户行为数据的历史记录进行比较，以检测异常。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptto)。
