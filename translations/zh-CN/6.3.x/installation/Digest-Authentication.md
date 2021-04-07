---
layout: 默认
title: CAS-摘要身份验证
category: 验证
---

# 摘要式身份验证

摘要身份验证是CAS可以用来与用户的 Web浏览器协商凭据的公认方法之一。 这可以用于在发送敏感信息之前确认用户的身份。 在通过网络发送用户名和密码之前，它将哈希函数应用于用户名和密码。 从技术上讲，摘要身份验证是MD5密码 散列的一种应用，它使用nonce值来防止重放攻击。 它使用HTTP协议。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-digest-authentication</artifactId>
  <version>${cas.version}</version>
</dependency>
```

有关摘要式身份验证如何工作的其他信息，请 [本指南](https://en.wikipedia.org/wiki/Digest_access_authentication)。

## 配置

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#digest-authentication)。

## 凭证管理

默认情况下，CAS尝试对照客户端在身份验证请求中报告的内容对计算的哈希值进行交叉检查。 为了使此操作成功，CAS将需要访问保存凭据的MD5表示形式的数据存储。 存储区 需要将哈希值保持在最低限度。

默认情况下，CAS使用其属性文件来存储哈希凭证。 该模块的实际生产级别部署 将需要提供自己的数据存储，该存储提供哈希值的集合作为身份验证帐户。

## 客户要求

以下代码片段演示了给定的Java客户端如何通过Apache的HttpClient库

```java
最终的HttpHost目标=新的HttpHost（“ localhost”，8080，“ http”）;

final CredentialsProvider credsProvider = new BasicCredentialsProvider（）;
credsProvider.setCredentials（
        新的AuthScope（target.getHostName（），target.getPort（）），
        新的UsernamePasswordCredentials（“ casuser”，“ Mellon”）））;

最后的CloseableHttpClient httpclient = HttpClients.custom（）
        .setDefaultCredentialsProvider（credsProvider）
        .build（）;

试试{
    HttpGet httpget = new HttpGet（“ http：// localhost：8080 / cas / login”）;

    //创建AuthCache实例
    最后的AuthCache authCache = new BasicAuthCache（）;

    //生成DIGEST方案对象，对其进行初始化，并将其添加到本地auth缓存中
    。
    summaryAuth.overrideParamter（“ realm”，“ CAS”）;
    authCache.put（target，digestAuth）;

    //将AuthCache添加到执行上下文
    最后的HttpClientContext localContext = HttpClientContext.create（）;
    localContext.setAuthCache（authCache）;

    System.out.println（“执行请求” + httpget.getRequestLine（）+“到目标” + target）;
    试试（CloseableHttpResponse响应= httpclient.execute（target，httpget，localContext））{
        System.out.println（response.getStatusLine（））;
        System.out.println（EntityUtils.toString（response.getEntity（）））;
    }
}最后{
    httpclient.close（）;
}
```
