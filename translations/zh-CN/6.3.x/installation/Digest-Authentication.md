---
layout: 违约
title: 中科院 - 文摘认证
category: 认证
---

# 文摘身份验证

文摘身份验证是 CAS 可用于与用户的 网络浏览器协商凭据的商定方法之一。 这可用于在发送敏感信息之前确认用户的身份。 它将哈希功能应用于用户名和密码，然后再通过网络发送。 从技术上讲，消化身份验证是MD5加密 的应用，它与使用无值来防止重播攻击有关。 它使用 HTTP 协议。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-消化-身份验证</artifactId>
  <version>${cas.version}</version>
</dependency>
```

有关消化认证工作原理的其他信息，请 [](https://en.wikipedia.org/wiki/Digest_access_authentication)查看本指南。

## 配置

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#digest-authentication)。

## 凭据管理

默认情况下，CAS 尝试根据客户端在身份验证请求中报告的内容对计算的哈希值进行交叉检查。 为了取得成功，CAS 将需要访问保存 MD5 凭据表示的数据存储。 当然，商店 需要将哈希值保持在最低水平。

默认情况下，CAS 使用其属性文件来存放哈希的凭据。 此模块 的实际生产级部署将需要提供自己的数据存储库，提供作为身份验证帐户的哈希值集合。

## 客户请求

以下片段演示了给定 Java 客户端如何使用 CAS 消化认证， 通过 Apache 的 HttpClient 库进行：

```java
最终的HttpHost目标=新的HttpHost（"本地酒店"，8080，"http"）：

最后的凭据提供者信条提供者=新的基本信用提供者（）：
信用提供者.设置信用（
        新的AuthScope（目标.getHostname（），目标.getPort（）），
        个新的用户名密码信用（"卡苏特"，"梅隆"）：

最后的可关闭的HtpClient=HttpClients.自定义（）
        .集差异信用提供器（信用提供者）
        。

尝试{
    HttpGet=新的HttpGet（"http://localhost:8080/cas/login"）;

    //创建自动缓存实例
    最终的自动缓存身份验证=新的基本身份验证（）;

    //生成文摘方案对象， 初始化它，并将其添加到本地身份验证缓存
    最后的文摘化学摘要=新的文摘化学（）：
    文摘Auth.覆盖帕拉默特（"领域"， "CAS"）;
    身份验证。put（目标，摘要）;

    //将身份验证添加到执行上下文中
    最终的HttpClient康特本地文摘= .创建（）;
    本地通信。设置自动缓存（身份验证）;

    系统。out.打印（"执行请求"+httpget.获取请求行（）="目标"+目标）;
    尝试（可关闭的响应响应=httpclient.执行（目标， httpget，本地文本））{
        系统.out.println（响应.获取状态线）：
        系统.out.打印（实体使用.到斯特林（响应。获取））：
    }
}最后{
    httpclient.关闭（）;
}
```
