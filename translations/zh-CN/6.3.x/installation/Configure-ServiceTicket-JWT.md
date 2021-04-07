---
layout: 违约
title: 中科院 - JWT 服务门票
category: 票务
---

# JWT 服务门票

[JSON 网络代币](http://jwt.io/) 是一种开放的行业标准 RFC 7519 方法，用于安全地代表双方之间的索赔。 CAS 也可以允许完全创建已签名/加密的 JWT，并以服务票的形式将其传回应用程序。

JWT 完全自成一体，包含经过验证的本金以及 JWT 索赔形式的所有授权属性。

<div class="alert alert-info"><strong>JCE 要求</strong><p>确保您的 Java 
环境中安装了适当的 JCE 捆绑包，这是 CAS 使用的，特别是如果您需要使用特定的签名/加密算法和方法。 请务必为您的Java版本选择 
正确的JCE版本。 爪哇版本可以通过 <code>爪哇版本</code> 命令检测到。</p></div>

## 概述

基于 JWT 的服务票是根据《 [CAS 协议》](../protocol/CAS-Protocol.html)定义的相同语义向应用程序签发的。 CAS通过其 `/登录` 端点收到认证请求后，将有条件地通过请求的 http 方法，以 `票` 参数的形式，将 `JWT` 服务票发回 申请。

默认情况下，所有 JWT 均由 CAS 根据部署期间生成和控制的密钥进行签名和加密。 这些密钥可以 与客户端应用程序交换，以拆开 JWT 和访问权限。

## 流程图

<a href="../images/cas_flow_jwt_diagram.png" target="_blank"><img src="../images/cas_flow_jwt_diagram.png" alt="CAS 网络流 JWT 图表" title="CAS 网络流 JWT 图表" /></a>

请注意，根据上述图表，默认情况下，JWT 请求在内部导致 CAS 生成申请的 `ST` ，然后立即进行验证， 以便访问与 CAS 服务注册表中与申请注册记录相关的每个策略的经过验证的本金和属性。 此响应将转换为 `JWT` 然后传递到客户端应用程序。

也就是说，收到服务票（`ST`）和验证服务票的责任全部由中科院内部承担和处理。 该应用程序只需要学习如何破译和拆开JWT</code> 的最终 `，并确保其有效性。</p>

<p spaces-before="0">生成的 <code>JWT` 的到期时间受作为验证事件的一部分返回的断言的长度控制。 如果未指定 断言有效期长度，则到期时间由 SSO 会话的长度控制，该会话定义为 CAS 服务器 SSO 到期政策的一部分。 

<div class="alert alert-warning"><strong>未打开ID连接</strong><p>请记住，您只是收到一张 JWT 形式的机票， 
从而消除客户验证正常服务机票的需要。 票证由 CAS 内部验证，您作为客户端 
只负责验证 JWT 本身。 不要混淆此与开放ID连接。 虽然 JWT，令牌本身不是 ID 令牌，但无法刷新 
，并且必须在您认为其过期后再次获得。 如果您需要更多，请考虑使用开放ID连接协议。 
请注意，验证 JWT 的责任被推到客户端</b> <b>，而不是 CAS 服务器本身。</p></div>

## 行政终点

CAS 提供以下端点：

| 端点           | 描述                     |
| ------------ | ---------------------- |
| `jwt代卡签名签名键` | 暴露签名的公钥，接受可选的 `服务` 参数。 |

## 配置

JWT 支持通过在战争覆盖中包括以下依赖关系而启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡-服务器-支持-代币票</artifactId>
     <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#jwt-tickets)。

### 注册客户端

在 CAS 服务注册表中发出信号，以生成服务票的 JWT：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务id"："^https://.*"，
  "名称"："示例"，
  "id"：10，
  "属性"：{
    "@class"："java.util.哈希马普"，
    "jwtAs服务卡"：{
      "@class"："或
      "价值"："java.利用"，"哈希塞特"，"真实"，
    [
  ]
}
```

### 按服务配置密钥

默认情况下，用于编码 JWT 的签名和加密密钥是 CAS 服务器的全球密钥，可以通过 CAS 设置进行定义。 也可以 以每项服务的方式覆盖全球密钥，允许每个应用程序使用自己的签名和加密密钥集。 为此，在注册表中配置 服务定义，以匹配以下情况：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："@https://.*"
  "名称"："示例"，
  "id"：10，
  "属性"：{
    "@class"："java.util.HashMap"，
    "jwtas服务卡"：{
      "@class"："org.apereo.cas.服务。默认注册服务专业"，
      "值"："java.ul.利用"，"哈希塞特"，"真实"]]
    }，
    "jwtAs服务卡签名钥匙"：{
       "@class"： "org. apereo. cas. 服务. 默认注册服务专业"，
       "价值"： [ "java. util. 哈希塞特"， [ "..." []]
    }，
    "jwtAs服务卡加密钥匙"：{
         "@class"："org.apereo.cas.服务。默认注册服务"，
         "值"："java.util.哈希集"，["。。。" []]
    }，
    "jwtAs服务网卡"：{
         "@class"："org.apereo.cas.服务.服务.默认注册服务"，
         "价值"："java.util.HashSet"，"ENCRYPT_AND_SIGN"]]
    =
  [
]
```

提供以下密码策略类型：

| 类型                 | 描述             |
| ------------------ | -------------- |
| `ENCRYPT_AND_SIGN` | 默认策略;加密值，然后签名。 |
| `SIGN_AND_ENCRYPT` | 签署值，然后加密。      |


## JWT 验证 - AES

下面 *个代码片段* 示例演示了如何验证和分析 CAS 生产的 JWT ，该是通过 `AES`创建的：

```java
导入组织. apache. 共享. 编解码码. 二进制. base64;
进口组织.jose4j.jwe.杰森网络加密;
进口组织.jose4j.jwk.杰森网络钥匙;
进口组织.jose4j.jws.杰森网络签名;
进口组织.jose4j.钥匙。艾斯基：

进口爪哇. nio. 查塞特. 标准集;
进口爪哇安全。

...

最后字符串签名键="。。。"：
最后的字符串加密键="。。。"：

最后的关键=新的艾斯基（签名键。获取字节（StandardCharsets.UTF_8）：新钥匙（签名键。获取字节（StandardCharsets.UTF_8））

最后的杰森网络签名jws=新的杰森网络签名（）：
jws.集组合（安全Jwt）：
jws.设置键（键）：
如果 （！ jws. 验证签名） [
    抛出新的例外 （"Jwt 验证失败"）;
=

最终字节 [] 解码字节 = base64. 解码 Base64 （jws. get 编码加载 （）. get 字节 （StandardCharsets.UTF_8）：
最后的字符串解码加载=新的字符串（解码字节，StandardCharsets.UTF_8）：

最后的杰森网加密jwe=新的杰森网加密（）：
最后的 JsonWebKey jsonWebkey = JsonWebkey. 工厂
    . newjwk （"\n" + "\"kty\"： "八"，\n"\"k\"：\"+ 加密键 + "\"\n"+ "}"）;

jwe. 集计算 （解码的支付负载）;
jwe.设置键（新艾斯基（杰森网络钥匙。获取键。获取编码）：
系统. out. 打印 （jwe. 获取解释性字串）：
```
