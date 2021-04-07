---
layout: 违约
title: CAS - SPNEGO 认证
category: 认证
---

# 斯内戈认证

[SPNEGO](http://en.wikipedia.org/wiki/SPNEGO) 是一种身份验证技术，主要用于为运行在"活动目录"域名证书下运行的 Windows 上的浏览器提供 透明的 CAS 认证。 涉及三个角色：客户端、CAS 服务器和活动目录域控制器/KDC。

1. 客户发送CAS：HTTP `获取` 到CAS的cas保护页面
2. 中科院回应： HTTP `401` - 访问被拒绝 `W 验证： 谈判`
3. 客户发送机票请求：Kerberos（`KRB_TGS_REQ`）请求 `HTTP/cas.示例.com@REALM`
4. 克贝罗斯 Kdc 回应： 克贝罗斯 （`KRB_TGS_REP`） 授予 `Http / cas. 示例的门票.com @ realm`
5. 客户发送 CAS： Http `获得` 授权： 与 SPNEGO 代币进行谈判
6. CAS 回复： HTTP `200` - 确定 `WWW 认证` w/SPNEGO 响应 + 请求页面。

只有在没有 CAS SSO 会话时，才会发生上述交互。 一旦 CAS 授予了票证，SPNEGO 流程将不会再发生，直到 CAS 票过期。

## 要求

* 客户端已登录到窗动目录域。
* 支持的浏览器。
* CAS 正在对 AD 域控制器运行麻省理工学院角膜。

<div class="alert alert-info"><strong>JCE 要求</strong><p>确保在 CAS 使用的 Java 环境中安装适当的 JCE 捆绑包是安全的，特别是如果您需要使用 ADFS 发布的加密有效载荷。 请务必为您的Java版本选择正确的JCE版本。 爪哇版本可以通过 <code>爪哇版本</code> 命令检测到。</p></div>

<div class="alert alert-info"><strong>大型克贝罗斯门票</strong><p>如果组织用户拥有大型 kerberos 票证，可能是由于是大量组的成员造成的，Tomcat 连接器将需要将 <code>最大HttpHeaderSize</code> 值从默认金额中增加，以便将票证传递到 CAS 服务器应用程序。</p></div>

## 组件

SPNEGO 支持通过在战争叠加中包括以下依赖性来实现：

```xml
<dependency>
  <groupId>组织.apereo.cas</groupId>
  <artifactId>卡-服务器-支持-斯涅戈-网络流</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您可能还需要在 CAS 叠加中申报以下存储库，才能解决依赖关系：

```groovy
存储库{
    马文{ 
        马文康森特{发布（）=
        网址"https://dl.bintray.com/uniconiam/maven" 
    }
}
```

## 配置

打开 SPNEGO 功能需要以下步骤。

### 创建 SPN 帐户

为服务主体名称 （SPN） 创建活动目录帐户并记录用户名。 密码将在下一步被覆盖。

### 创建键盘文件

密钥塔文件支持 CAS 服务器和密钥分发中心 （KDC） 之间的信任链接：活跃目录 域控制器在此上下文中充当 KDC 的角色。 [`ktpass` 工具](http://technet.microsoft.com/en-us/library/cc753771.aspx) 用于生成包含加密密钥的键盘文件， 。 请务必以 管理员的身份执行活动目录域控制器的命令（域管理员成员将无法成功使用 `ktpass` ）。

例：

```bash
C：\用户/管理员。域>ktpass / 出我的帐户. 键盘 / princ Http / cas. 示例.com @ Realm / 通过 * / 地图用户域名 - account@YOUR. realm / ptype KRB5_NT_PRINCIPAL / 加密 Rc4 - hmac - nt
目标域控制器： Dc. your 。realm
成功映射了 HTTP/cas.示例.com域名帐户。
键入 HTTP/cas.示例的密码.com：
再次键入密码以确认：成功设置
密码！
已创建密钥。
向 myspnaccount.keytab 输出密钥塔：
键塔版本： 0x502
键大小 69 HTTP/cas.示例.com@REALM ptype 1 （KRB5_NT_PRINCIPAL） vno 3 词型0x17 （RC4-HMAC） 键长 16
（0x00112233445566778899aabbccddeeff）
```

使用 `ktpass` 需要主动目录管理权限。 如果这不是一个选项，您可以使用 `ktab.exe` 从 `%JAVA_HOME%-bin\ktab.exe` 由JDK提供：

```bash
%JAVA_HOME%- 宾克塔布.exe - service_xxx - n 0 - k 卡斯克. 凯塔布
```

`-k` 指定关键选项卡输出文件名，并 `-n 0` 指定 KNVO 号码（如果可用）并找到用户帐户。 此值可能与用户帐户上的 `msDS-密钥转换编号` 相匹配。

另请注意，密钥塔文件必须在密码更改后（如果有的话）再生。

### 测试 SPN 帐户

在 CAS 服务器主机上安装和配置麻省理工学院 Kerberos V。 以下示例 `krb5.` 文件可 用作参考。

```ini
[logging]
 默认值 = 文件：瓦尔/日志/krb5libs.log
 kdc = 文件：/瓦尔/日志/krb5kdc.log
 admin_server = 文件：/瓦尔/日志/卡德明德.log

[libdefaults]
 ticket_lifetime = 24000
 default_realm = 您的。领域。这里
 default_keytab_name = /家 / 卡斯 / 克贝罗斯 / 我的帐户.
 dns_lookup_realm = 假
 dns_lookup_kdc = 假
 default_tkt_enctypes = rc4 - hmac
 default_tgs_enctypes = rc4 - hmac

[realms]
 你。领域。这里={
  kdc=你的.kdc.你的.领域。这里：88
 }

[domain_realm]
 。你的。领域。这里=你的。领域。这里
 你的。领域。这里=你的。领域。这里
```

需要注意的是， `myspnaccount.` 被宣布为默认密钥塔，否则 CAS 可能无法 找到它，并将提出类似

```bash
Krb 例外： 无效参数 （400） - 找不到适当的类型键来解密 Ap Rep - rc4 与 Hmac '
```

然后验证您是否能够 **读取** 键盘文件：

```bash
klist-k
键塔名称：文件：/家/卡斯/克贝罗斯/我的帐户.钥匙塔
KVNO校长
---- --------------------------------------------------------------------------
   3 HTTP/cas.示例.com@REALM
```

然后验证您是否能够 **使用** 键盘文件：

```bash
kinit -k HTTP/cas.示例.com@Realm
单
机票缓存：文件：/tmp/krb5cc_999
默认本金：HTTP/cas.示例.com@Realm

有效启动过期服务本金
12/08// 2016 10：52：00 12/08/2016 20：52：00 krbtgt/REALM@REALM
    续订至2016年8月12日 20：52：00
```

### 浏览器配置

* 互联网浏览器 - 启用 `集成视窗身份验证` ，并将 CAS 服务器 URL 添加到 `本地内联网` 区。
* Firefox - 将 `网络.谈判-auth.信任-uris` 配置参数设置在 `中，包括：将` 配置到 CAS 服务器 URL 中，例如 `https://cas.example.com`.

### 身份验证配置

确保您至少在 CAS 配置中指定了 JCIFS 服务负责人。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。 要查看处理NTLM认证的CAS属性的相关列表，请 [](../configuration/Configuration-Properties.html#ntlm-authentication)查看本指南。

您可以提供JAAS `登录。` 文件：

```bash
jcifs. spnego. 启动 [
   com. sun. 安全. auth. 模块. krb5 洛金模块所需商店钥匙] 真实使用 Keytab = 真正的钥匙塔]"/ 家庭 / 卡斯 / 克贝罗斯 / 我的帐户. 键盘";
[：
jcifs. spnego. 接受 [
   com. sun. 安全. auth. 模块. krb5Login 模块要求商店钥匙= 真实使用钥匙塔= 真正的钥匙塔] "/ 家庭 / 卡斯 / 克贝罗斯 / 我的斯普纳克计数. 键盘";
[;
```

## 客户选择策略

CAS 提供了一组有条件地激活 SPNEGO 流的组件， ，以防部署人员需要一种可配置的方法来决定 SPNEGO 是否应用于当前身份验证/浏览器请求 。 网络流 可用的状态 `评估"客户要求"` ，该将尝试 启动 SPNEGO 身份验证或正常恢复，具体取决于下面选择的客户端操作策略。

### 通过远程 IP

检查请求的远程 IP 地址是否与前定义模式匹配。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。

### 由主机名

检查请求的远程主机名是否与前定义模式匹配。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。

### 按 LDAP 属性

检查远程主机名的 LDAP 实例，以找到其仅存在 允许网络流恢复到 SPNEGO 的预定义属性。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。

## 伐木

要启用其他记录，请配置 log4j 配置文件以添加以下级别：

```xml
...
<Logger name="jcifs.spnego" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 故障 排除

- GSS-API 级别未指明的失败

```bash
原因： GSS 除例： GSS-API 级别未指明故障（机制级别：无效参数 （400） - 无法找到解密 AP REP 的适当类型的键 - 带有 HMAC SHA1-96 的 AES256 CTS 模式）
        在太阳下。 5Context.接受Sec康德信（未知来源）
        在太阳。安全.jgs.GS康德信请.接受Sec康德信（未知来源）
        在太阳。安全.jgs.GSS康德信特.接受Sec康德信（未知来源）
        ...280 更多
原因： KrbException： 无效参数 （400） - 找不到适当的类型键解密 
AP REP - AES256 CTS 模式与 HMAC SHA1-96
        在太阳.安全.krb5.KrbApReq.身份验证 （未知来源）
        在太阳.安全.krb5.krbApReq.<init>（未知来源）
        在太阳. 安全. jgss. krb5. Initsec 康德信托肯。<init>（未知来源）
        ...283更多
```

很可能您 `.keytab` 文件的错误路径。 密钥塔文件中的 KVNO 必须与存储在活动目录中的 KVNO 相同。 活动目录正在提高KVNO与ktpass的每一次执行，作为其 `msDS-关键版本数量`的一部分。

其他可能的原因包括：

1. CAS 配置中的服务主体与钥匙塔中的服务主体不相同。 （参数 `/普林克` 从克特帕斯）
2. 活动目录发送的 `封装` 没有密钥。 （参数 `/加密` 从 `ktpass` ，并设置在 `krb5.com/permitted_enctypes+default_tkt_enctypes`）。
3. 票上的KVNO不同于钥匙塔中的KVNO（参数 `/kvno` 从 `ktpass`）。
