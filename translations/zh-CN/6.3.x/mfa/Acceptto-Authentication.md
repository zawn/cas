---
layout: 违约
title: CAS - 接受认证
category: 多因素认证
---

# 接受身份验证

通过 [接受](https://www.acceptto.com) 基于风险的多因素身份验证，确保您的员工身份。

首先参观 [接受文件](https://www.acceptto.com/acceptto-mfa-rest-api/)。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织. apereo. cas</groupId>
     <artifactId>卡斯服务器支持 - 接受 - mfa</artifactId>
     <version>${cas.version}</version>
</dependency>
```

集成为多因素身份验证和 QR 无密码身份验证增加了支持。

## 与 DBFP 集成

集成能够处理与 DBFP 的集成，并将设置一个名为 `jwt` 的曲奇饼，该饼干将传递给接受 API。 此参数包含服务器用于评估身份验证请求风险的值，包括浏览器指纹、用户 IP 地址和用户浏览器的 GPS 位置。 服务器将此数据与用户行为数据的历史记录进行比较，以检测异常情况。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#acceptto)。
