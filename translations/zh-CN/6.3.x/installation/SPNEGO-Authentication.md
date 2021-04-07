---
layout: 默认
title: CAS-SPNEGO认证
category: 验证
---

# SPNEGO认证

[SPNEGO](http://en.wikipedia.org/wiki/SPNEGO) 是一种身份验证技术，主要用于向在Active Directory域凭据下运行的Windows上运行的浏览器 涉及三个参与者：客户端，CAS服务器和Active Directory域控制器/ KDC。

1. 客户端将CAS：HTTP `GET` 发送到CAS的CAS保护页面
2. CAS响应：HTTP `401` 拒绝访问 `WWW身份验证：协商`
3. 客户端发送票证请求：Kerberos（`KRB_TGS_REQ`）为 `HTTP/cas.example.com@REALM`
4. Kerberos KDC响应：Kerberos（`KRB_TGS_REP`）授予 `HTTP/cas.example.com@REALM`
5. 客户端发送CAS：HTTP `GET` 授权：与SPNEGO令牌协商
6. CAS响应：HTTP `200` -OK `WWW-Authenticate` w / SPNEGO响应+请求的页面。

当没有CAS SSO会话时，以上交互仅针对第一个请求发生。 一旦CAS授予了票证授予票证，SPNEGO流程将不会再次发生，直到CAS 票证到期为止。

## 要求

* 客户端登录到Windows Active Directory域。
* 支持的浏览器。
* CAS对AD域控制器运行MIT Kerberos。

<div class="alert alert-info"><strong>JCE要求</strong><p>确保您已在CAS使用的Java环境中安装了正确的JCE软件包是安全的，特别是在您需要使用ADFS发行的加密有效负载的情况下。 确保为您的Java版本选择正确的JCE版本。 <code>java -version</code> 命令检测Java版本。</p></div>

<div class="alert alert-info"><strong>大型Kerberos票证</strong><p>如果组织用户拥有较大的kerberos票证，这很可能是由于成为大量组的成员所致 <code></code> 值，以允许将票证传递到CAS Server应用程序。</p></div>

## 成分

通过在WAR叠加中包含以下依赖项来启用SPNEGO支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-spnego-webflow</artifactId>
  <version>${cas.version}</version>
</dependency>
```

您可能还需要在CAS覆盖中声明以下存储库，以便能够解决依赖关系：

```groovy
存储库{
    maven { 
        mavenContent {releasesOnly（）}
        url“ https://dl.bintray.com/uniconiam/maven” 
    }
}
```

## 配置

需要执行以下步骤才能打开SPNEGO功能。

### 创建SPN帐户

为服务主体名称（SPN）创建一个Active Directory帐户并记录用户名。 下一步将覆盖密码。

### 创建密钥表文件

keytab文件可在CAS服务器和密钥分发中心（KDC）之间建立信任链接；在这种情况下，Active Directory 域控制器充当KDC的角色。 [`ktpass` 工具](http://technet.microsoft.com/en-us/library/cc753771.aspx) 用于生成包含加密密钥的密钥表文件 管理员的身份从Active Directory域控制器执行命令（域管理员的成员将无法 `ktpass`）。

例子：

```bash
C：\ Users \ administrator.DOMAIN>ktpass / out myspnaccount.keytab / princ HTTP/cas.example.com@REALM / pass * / mapuser domain-account@YOUR.REALM / ptype KRB5_NT_PRINCIPAL / crypto RC4-HMAC-NT
定位域控制器：DC.YOUR.REALM
成功将HTTP / cas.example.com映射到domaine-account。
输入HTTP / cas.example.com
再次输入密码以确认：
密码设置成功！
密钥已创建。
输出密钥表到myspnaccount.keytab：
密钥表版本：0x502
密钥大小69 HTTP/cas.example.com@REALM ptype 1（KRB5_NT_PRINCIPAL）vno 3 etype 0x17（RC4-HMAC）密钥长度16
（0x00112233445566778899aabbccddeeff）
```

使用 `ktpass` 需要Active Directory管理员权限。 如果不是这样，则可以使用 `%JAVA_HOME%\ bin \ ktab.exe` 中的 `ktab.exe` ，JDK提供

```bash
%JAVA_HOME%\ bin \ ktab.exe -a service_xxx -n 0 -k cas.keytab
```

`-k` 指定关键选项卡输出文件名，而 `-n 0` 指定KNVO编号（如果可用），并且已找到该用户帐户。 此值可能与用户帐户上的 `msDS-KeyVersionNumber`

还要注意，如果更改了密码，则必须在更改密码后重新生成keytab文件。

### 测试SPN帐户

在CAS服务器主机上安装和配置MIT KerberosV。 以下示例 `krb5.conf` 文件可以用作参考

```ini
[logging]
 默认= FILE：/var/log/krb5libs.log
 kdc = FILE：/var/log/krb5kdc.log
 admin_server = FILE：/var/log/kadmind.log

[libdefaults]
 ticket_lifetime = 24000
 default_realm =您.REALM.HERE
 default_keytab_name = /home/cas/kerberos/myspnaccount.keytab
 dns_lookup_realm =假
 dns_lookup_kdc =假
 default_tkt_enctypes = RC4-HMAC
 default_tgs_enctypes = RC4-HMAC

[realms]
 YOUR.REALM.HERE = {
  KDC = your.kdc.your.realm.here:88
 }

[domain_realm]
 .your.realm.here = YOUR.REALM.HERE
 your.realm.here = YOUR.REALM.HERE
```

请务必注意，将 `myspnaccount.keytab` 声明为默认密钥表，否则CAS可能无法 找到它，并且将引发类似于以下内容的异常

```bash
KrbException：无效的参数（400）-无法找到适当类型的密钥来用HMAC解密AP REP -RC4
```

然后验证您是否能够 **读取** 密钥表文件：

```bash
klist -k
密钥表名称：FILE：/home/cas/kerberos/myspnaccount.keytab
KVNO主体
---- ----------------------- --------------------------------------------------
   3 HTTP/cas.example.com@REALM
```

然后，验证您是否可以使用</strong> keytab文件 **</p>

```bash
kinit -k HTTP/cas.example.com@REALM
klist
票证缓存：FILE：/ tmp / krb5cc_999
默认主体：HTTP/cas.example.com@REALM

有效启动Expires Service主体
12/08/2016 10 ：52：00 12/08/2016 20:52:00 krbtgt / REALM @ REALM
    续订至12/08/2016 20:52:00
```

### 浏览器配置

* Internet Explorer-启用 `Integrated Windows Authentication` 并将CAS服务器URL添加到 `Local Intranet` 区域。
* Firefox-将 `about：config` `network.negotiate-auth.trusted-uris` 配置参数设置为CAS服务器 URL，例如 `https://cas.example.com`。

### 认证配置

确保至少在CAS配置中指定了JCIFS服务主体。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。 要查看处理NTLM身份验证的CAS属性的相关列表，请 [本指南](../configuration/Configuration-Properties.html#ntlm-authentication)。

您可以提供一个JAAS `login.conf` 文件：

```bash
jcifs.spnego.initiate {
   com.sun.security.auth.module.Krb5LoginModule必需storeKey = true useKeyTab = true keyTab =“ / home / cas / kerberos / myspnaccount.keytab”;
};
jcifs.spnego.accept {
   com.sun.security.auth.module.Krb5LoginModule必需storeKey = true useKeyTab = true keyTab =“ / home / cas / kerberos / myspnaccount.keytab”;
};
```

## 客户选择策略

CAS提供了一组尝试激活SPNEGO部件的有条件流动， 的情况下，部署者需要一个可配置的方式来决定SPNEGO是否应该被施加到 当前认证/浏览器请求。 可用的状态为 `评估客户` ，这将尝试启动SPNEGO身份验证 或正常恢复，具体取决于下面选择的客户端操作策略。

### 通过远程IP

检查请求的远程IP地址是否与预定义模式匹配。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。

### 按主机名

检查请求的远程主机名是否与预定义模式匹配。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。

### 通过LDAP属性

检查LDAP实例中的远程主机名，以找到一个预定义的属性，该属性的仅存在 将允许Webflow恢复到SPNEGO。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#spnego-authentication)。

## 记录中

要启用其他日志记录，请配置log4j配置文件以添加以下级别：

```xml
...
<Logger name="jcifs.spnego" level="debug" additivity="false">
    <AppenderRef ref="console"/>
    <AppenderRef ref="file"/>
</Logger>
...
```

## 故障排除

- 未在GSS-API级别上指定失败

```bash
引起原因：GSSException：在GSS-API级别上未指定的失败（机制级别：无效的参数（400）-找不到用于解密AP REP的适当类型的密钥-HMAC SHA1-96的AES256 CTS模式）在sun.security.jgss处为
        krb5.Krb5Context.acceptSecContext（未知来源）
        在sun.security.jgss.GSSContextImpl.acceptSecContext（未知来源）
        在sun.security.jgss.GSSContextImpl.acceptSecContext（未知来源）
        ... 280更多
原因：KrbException ：无效的参数（400） -无法找到适当类型的密钥来解密 
AP REP - AES256 CTS模式HMAC SHA1-96
        在sun.security.krb5.KrbApReq.authenticate（未知来源）
        在sun.security.krb5。 KrbApReq。<init>（未知源）
        位于sun.security.jgss.krb5.InitSecContextToken。<init>（来源不明）
        ... 283更多
```

您很有可能使用了错误的 `.keytab` 文件路径。 密钥表文件中的KVNO必须与Active Directory中存储的KVNO相同。 Active Directory在每次执行ktpass时都会提高 `msDS-KeyVersionNumber`。

其他可能的原因包括：

1. CAS配置中的服务主体与密钥表中的服务主体不同。 （参数 `/ ktpass的`
2. Active Directory与票证一起发送 `enctype` 没有密钥。 （从 `ktpass` `/ crypto` ，并在 `krb5.conf / permitted_enctypes + default_tkt_enctypes`）。
3. 从该票证的KVNO比（PARAM在密钥表的KVNO不同 `/ KVNO` 从 `的ktpass`）。
