---
layout: 默认
title: CAS-QR码认证
category: 验证
---

# QR码认证

QR码身份验证是一种策略，允许用户 CAS服务器生成的QR码，并在成功验证其后登录。

QR码包含一个嵌入的特殊标识符，该标识符允许移动设备使用到CAS服务器的Web套接字 一旦建立，移动设备就可以从用户 收集凭证并将其提交给CAS以进行验证。 返回结果，期待作为 `JWT` 由CAS生成， 然后沿着传递给验证和登录成功网络套接字通道。 随后的登录尝试可以在完全 *密码* 以便移动设备可以继续将JWT 重新用于身份验证尝试，从而允许最终用户进行 *扫描并进行*。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-qrlogin</artifactId>
  <version>${cas.version}</version>
</dependency>
```


## 行政端点

CAS提供了以下端点：

| 终点                               | 描述                  |
| -------------------------------- | ------------------- |
| `qrDevices /{username}`          | `GET` 请求为用户获取设备。    |
| `qrDevices /{username}/{device}` | `POST` 请求向CAS注册设备。  |
| `qrDevices /{username}/{device}` | `DELETE` 请求为用户删除设备。 |

## 配置

要查看CAS 属性的相关列表，请 [查阅本指南](../configuration/Configuration-Properties.html#qr-authentication)。

## Web套接字通信

对于每个移动应用程序框架，连接到Web套接字连接的过程肯定会有所不同。 在较高级别上， `/ cas / qr-websocket` 端点建立与CAS服务器的Web套接字连接。 然后，必须将有效负载 `/ qr / accept` 路径，并且必须包含 `令牌` 字段，其中 携带预先认证的JWT。 有效负载标头必须指向从标头名称 `QR_AUTHENTICATION_CHANNEL_ID` 的通道ID，以及指向 ``下的授权设备标识符 的通道ID。

以下代码片段以示例方式演示了此过程：

```javascript 
let socket = new SockJS（'https://sso.example.org/cas/qr-websocket'）;
让stompClient = Stomp.over（socket）;
让有效负载= JSON.stringify（{'token': '...'}）;   
让channelId =“ ...”;      
让deviceId =“ ...”;
stompClient.send（ “/ QR /接受”， 
    {'QR_AUTHENTICATION_CHANNEL_ID': channelId, 
     'QR_AUTHENTICATION_DEVICE_ID': deviceId}， 
    有效载荷）;
```

以下代码段针对 [StompProtocolAndroid](https://github.com/NaikSoftware/StompProtocolAndroid)的Android平台演示了此过程：

```java
导入ua.naiksoftware.stomp。*;
导入ua.naiksoftware.stomp.dto。*;

字符串jwt =“ ...”;
JSONStringer jsonWebToken = new JSONStringer（）。object（）
  .key（“ token”）。value（jwt）.endObject（）;

字符串通道=“ ...”;      
字符串deviceId =“ ...”;
List<StompHeader> 标头= new ArrayList<>（）;
headers.add（new StompHeader（“ QR_AUTHENTICATION_CHANNEL_ID”，channel））;
headers.add（new StompHeader（“ QR_AUTHENTICATION_DEVICE_ID”，deviceId））;
headers.add（new StompHeader（StompHeader.DESTINATION，“ / qr / accept”））;

//用于ssl和localhost的wss：//10.0.2.2
StompClient客户端= Stomp.over（Stomp.ConnectionProvider.OKHTTP， 
  “ wss：//10.0.2.2：8443 / cas / qr-websocket / websocket”，null ，httpClient）；

client.connect（）;
StompMessage stompMessage = 
  新的StompMessage（StompCommand.SEND，标头，jsonWebToken.toString（））;
client.send（stompMessage）.subscribe（）;
```

## 获得JWT

移动设备应该请求用户凭据，然后使用 [REST协议](../protocol/REST-Protocol.html#jwt-ticket-granting-tickets) 到 服务器以获得JWT。 JWT请求还必须包含一个附加请求 参数 `QR_AUTHENTICATION_DEVICE_ID` ，它指示用户的授权设备标识符。

一旦接收到JWT，设备就可以缓存JWT并建立 *会话* 以供以后的代码重用。 应将JWT发送到CAS服务器的Web套接字通道，以进行验证和登录，如上所示。 生成的 JWT由CAS自动签名和加密，并且只能由CAS服务器解码。

## Web套接字通道

QR码包含一个嵌入的特殊标识符，该标识符允许移动设备使用到CAS服务器的Web套接字 移动设备必须能够以 扫描QR码以提取信道ID，以便在CAS与设备之间

## 移动设备授权

CAS使用专用的QR设备存储库授权并接受已注册的设备，该存储库可以 跟踪并将其链接到用户ID。 此类设备必须使用 外部注册机制或通过CAS提供的API向CAS注册。

默认情况下，所有设备都可以使用QR码进行身份验证。 可以使用以下概述的策略之一提供不同的设备存储库

### JSON格式

可以在单个JSON资源中管理和跟踪授权的设备，该设备通过设置将其路径告知CAS。 要查看 相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#qr-authentication)。

### 风俗

在下面提供适当的bean实现，以定义用于管理注册设备的自定义策略。

```java 
@Bean
public QRAuthenticationDeviceRepository qrAuthenticationDeviceRepository（）{
    返回...
}
```
