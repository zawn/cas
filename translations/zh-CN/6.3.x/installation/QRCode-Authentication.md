---
layout: 违约
title: CAS - QR 代码认证
category: 认证
---

# QR 代码身份验证

QR 码身份验证是一种策略，允许用户扫描由 CAS 服务器生成的 QR 码，使用移动设备，并在成功验证后随后登录。

QR 码包含嵌入的特殊标识符，允许移动设备使用网络插座建立通信 通道到 CAS 服务器。 移动设备一经建立，可向用户 收集凭据，并提交中科院核实。 返回结果预期为中科院生成的 `JWT` ，然后 传递到 Web 插座通道进行验证并成功登录。 随后的登录尝试可以允许 完全 *无密码* 场景，以便移动设备可以继续重复使用 JWT 进行身份验证尝试，允许最终用户 *扫描并进行*。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - qrlogin</artifactId>
  <version>${cas.version}</version>
</dependency>
```


## 行政终点

CAS 提供以下端点：

| 端点                         | 描述                |
| -------------------------- | ----------------- |
| `qr设备/{username}`          | `获取` 请求，为用户获取设备。  |
| `qr设备/{username}/{device}` | `邮政` 向CAS注册设备的请求。 |
| `qr设备/{username}/{device}` | `删除删除用户设备` 请求。    |

## 配置

要查看中科院 物业的相关列表，请 [本指南](../configuration/Configuration-Properties.html#qr-authentication)。

## 网络插座通信

连接到 Web 插座连接的过程对于每个移动应用框架来说肯定各不相同。 在高层次上， 移动设备应通过端点 `/cas/qr 网络插` 座与 CAS 服务器建立网络插座连接。 然后，有效载荷必须作为地图发送到 `/qr/接受` 路径，并且必须包含 携带预验证 JWT 的 `令牌` 字段。 有效载荷标头必须指向标题名称 `QR_AUTHENTICATION_CHANNEL_ID` 下从 QR 码获得 的通道 ID，以及 `QR_AUTHENTICATION_DEVICE_ID`下授权设备标识符 。

以下代码片段以以下示例演示此过程：

```javascript 
让插座 = 新的袜子/ sso. 示例. org / cas / qr 网络插座'）;
让跺脚+跺脚。
让有效载荷=JSON.斯特林（{'token': '...'}）：   
让频道ID="。。。"：      
让设备ID="。。。"：
踩踏。发送（"/qr/接受"， 
    {'QR_AUTHENTICATION_CHANNEL_ID': channelId, 
     'QR_AUTHENTICATION_DEVICE_ID': deviceId}， 
    有效载荷：
```

下面的代码片段演示了基于 [步普罗托科尔安卓](https://github.com/NaikSoftware/StompProtocolAndroid) 安卓平台的过程：

```java
导入 ua. 奈克软件. 斯托普. *;
进口 ua. 奈克软件. 斯托普. dto.*;

字符串jwt="。。。"：
杰森斯特林格 jsonWebToken = 新的杰森斯特林格 （对象 ）
  .key （"令牌"） 。

字符串通道="。。。"：      
字符串设备ID="。。。"：
列表<StompHeader> 标题=新的阵列列表<>（）：
标题. add （新踩头 （"QR_AUTHENTICATION_CHANNEL_ID"， 频道）：
头。add（新跺脚头（"QR_AUTHENTICATION_DEVICE_ID"，设备ID））：
标题. add （新踩头 （踩头. 目的地， "/ qr / 接受"）：

//wss://10.0.2.2 为ssl和本地业主
跺脚客户端=跺脚。超过（跺脚。连接提供者。OKHTTP， 
  "wss://10.0.2.2:8443/cas/qr-websocket/websocket"，空，httClient）：

客户端。
跺脚运动= 
  新的跺脚信息（跺脚，发送，标题，jsonWebToken.托斯特林）：
客户端。发送（踩踏信息）。
```

## 获得 Jwt

移动设备应要求，然后使用 [REST 协议向 CAS 服务器提交用户凭据，](../protocol/REST-Protocol.html#jwt-ticket-granting-tickets) 获得 JWT。 JWT 请求还必须包含附加请求 参数 `QR_AUTHENTICATION_DEVICE_ID` ，该参数指示用户的授权设备标识符。

收到 JWT 后，设备可能会缓存 JWT，并建立一个 *会话* 供以后重复使用代码。 JWT 应发送到 CAS 服务器的 Web 插座通道进行验证和登录，如上所示。 生成的 JWT 由 CAS 自动签名和加密，只能由 CAS 服务器解码。

## 网络插座通道

QR 码包含嵌入的特殊标识符，允许移动设备使用网络插座在 CAS 服务器上建立 通信通道。 移动设备必须能够 扫描 QR 码以提取通道 ID，以便在 CAS 和设备之间建立 通信路线。

## 移动设备授权

注册设备由 CAS 授权并接受使用专用的 QR 设备存储库，该存储库能够 跟踪并将设备标识符链接到用户 ID。 此类设备必须使用 外部注册机制或通过可用的 CAS 提供的 ADI 在 CAS 注册。

默认情况下，所有设备都可以使用 QR 代码进行身份验证。 使用下面列出的策略之一，可以提供不同的设备存储库 实现。

### 杰森

授权设备可以在单个 JSON 资源内进行管理和跟踪，该资源通过设置向 CAS 传授路径。 有关中科院物业的 清单，请 [本指南](../configuration/Configuration-Properties.html#qr-authentication)。

### 习惯

提供以下适当的豆实施，以定义管理注册设备的自定义策略。

```java 
@Bean
公开的QR授权编辑器（）=
    返回。。。
}
```
