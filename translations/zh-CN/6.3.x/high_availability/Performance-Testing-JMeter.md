---
layout: 默认
title: CAS-JMeter性能测试
category: 高可用性
---

# JMeter性能测试

Apache JMeter是一个出色的性能测试工具，在Java社区中大量使用。

## 安装JMeter

* Linux和Mac：
  * 下载JMeter二进制文件。
    * [http://jmeter.apache.org/download_jmeter.cgi](http://jmeter.apache.org/download_jmeter.cgi)
  * 将apache-jmeter-*。tgz解压缩到您的首选位置
  * 运行 `bin / jmeter.sh`
    * 注意：Mac用户还可以使用流行的HomeBrew软件包管理器来安装JMeter。
* 视窗：
  * 这是有关Windows安装程序的教程。
    * [https://toolsqa.com/jmeter/download-and-installation-jmeter/](http://toolsqa.com/jmeter/download-and-installation-jmeter/)

## 测试脚本样例

在下面，您将找到针对三种最流行的CAS实现形式的三个通用的可运行登录脚本。 请随时编辑和使用以满足您的需求。

尽管脚本支持不同的登录方法，但它们确实具有一些共同的特征。

## 通用设置选项卡

* _用户定义的变量_
  * _ThreadCount_ 线程数（同类用户）。  推荐从100位用户左右开始。
  * _持续时间_ 测试应运行多长时间。  通常，线程（用户）越多，持续时间应越长
  * _RampUpPeriod_ 提升到完整的线程数集需要多长时间
* _线程组_ （或测试）：
  * _循环计数_ 循环数，或更准确地说，通过测试的用户数。
    * 计数将与将通过测试的总用户数相关联
    * _永远_ 复选框将遍历文件，直到手动停止或直到到达“用户定义的变量”页面上的
* _CSV获取用户/密码_：
  * 包含测试用户凭据的文件的名称和位置
  * `User，Password`的格式，“ User”，“逗号”和“ Password”之间不能有空格

**剧本**

可以从 [这里](https://github.com/apereo/cas/raw/master/etc/loadtests/)下载脚本。

* **_CAS_CAS.jmx_**
  * 使用标准CAS登录过程进行CAS的香草安装
  * 无需SP（服务提供商）
  * 用户定义的变量：
    * _IdPHost_ -CAS实例的URL
    * _CasSP_ -SP（服务提供商）URL，但不必处于活动状态
  * 测试片段：
    * _GET-CAS登录页面_ 典型的CAS登录访问登录页面
    * _POST-登录凭据_ 凭据从用户文件发布到CAS实例
    * _GET-使用Service Ticket_ 用户信息-使用用户登录时CAS生成的Service Ticket获取用户信息
      * 在断言下，可能需要更新预期的用户结果
    * _GET-用户注销_ 通过CAS注销从CAS会话注销用户
* **_CAS_Oauth.jmx_**
  * CAS支持OAuth登录过程
  * 活动SP是可选的
  * 脚本反映了使用OAuth的最常见方式，即授权码方法
  * _用户定义的变量_：
    * _IdPHost_ -CAS实例的URL
    * _CasSP_ -SP（服务提供商）URL，但不必处于活动状态
    * _SpClientId_ -CAS服务文件中SP的clientId
    * _SpRedirectUri_ -SP中的端点，将用于接收“授权码”
    * _SpState_ 使用的CSRF令牌
    * _SpClientName_ 用于身份验证的OAuth调用类型
    * _SpResponseType_ 使用的OAuth方法，在这种情况下为“代码”，代表“授权代码”
    * _SpClientSecret_ -SP和CAS之间共享的秘密短语或单词
  * _测试片段_：
    * _验证服务提供商_ 验证SP的URL是否正确（可选，可以禁用）
    * _开始CAS登录过程_ 使用所有参数设置访问OAuth的CAS登录页面
    * _1a-1d_ 发布用户的登录凭据，然后重定向以获取访问令牌中的代码
      * 测试时由于编码问题而分为几个过程
    * _GET-具有访问令牌的用户配置文件_ 调用CAS以获取具有访问令牌的用户信息
      * 在断言下，可能需要更新预期的用户结果
    * _GET-用户注销_ 通过CAS注销从CAS会话注销用户
* **_CAS_SAML2.jmx_**
  * CAS支持SAML2登录过程
  * 需要一个活动的SP！
    * 对于此测试，使用SimpleSAMLphp
  * _用户定义的变量_：
    * _CasSP_ 使用SAML的已注册CAS SP的域
    * _ProviderId_ -SP的元数据中声明的SAML EntityID
  * _测试片段_：
    * _进入SP以进行CAS登录_ 受SAML2保护的SP页面将重定向到CAS登录端点
    * _POST-登录用户_ 凭据从用户文件发布到CAS SAML2登录中
    * _POST-CAS授权给SP_ 从CAS发送响应到SP以进行处理并最终请求用户信息
      * 可能需要更新断言才能成功返回用户信息
    * _GET-用户注销_ 通过CAS注销从CAS会话注销用户

## 运行测试脚本

将测试脚本保存到系统后。 您可以在JMeter GUI中运行，也可以通过命令行运行。 他强烈建议将该GUI用于 对脚本进行故障排除以使其在您的环境中正常工作。 然后，当您实际开始进行 负载测试时，可以通过命令行进行。

要激活JMeter GUI，请从命令行键入：

```bash
提示$ / usr / local / bin / jmeter
```

此路径应对应于您选择安装Jmeter的位置。

通过命令行启动JMeter的简单示例：

```bash
提示$ / usr / local / bin / jmeter -n -t your_script.jmx
```

`-n` 在非GUI模式下运行JMeter。 `-t` 到.jmx测试文件的路径。

[Jmeter站点](http://jmeter.apache.org/usermanual/get-started.html#non_gui)上找到更多示例。
