---
layout: 违约
title: CAS - 发布说明
category: 规划
---

# RC1 发布说明

我们强烈建议您利用释放候选人，因为他们出来。 等待一个 `GA` 发布只会设置 你不愉快的惊喜。 一个 `的GA` 只是 [一个标签，没有更多的](https://apereo.github.io/2017/03/08/the-myth-of-ga-rel/)。 请注意，CAS 版本 *严格* 基于时间的版本：它们不是根据特定的基准、统计或功能完成来安排的。 为了获得 对特定版本的信心，强烈建议您尽早开始尝试发布候选和/或后续快照。

## 阿佩雷奥会员

如果您受益于 Apereo CAS 的免费和开源软件，我们邀请您 [加入 Apereo 基金会](https://www.apereo.org/content/apereo-membership) ，并以最适合您部署的能力为项目提供财政支持。 请注意，所有发展活动都 *几乎完全* 自愿进行的，没有附加任何期望、承诺或条件。 拥有更好的资金手段来维持工程活动，将使开发人员社区能够分配 *专门和投入的* 时间进行长期支持、维护和发布规划，特别是在及时解决关键和安全问题方面。 资金将确保支持你所依赖的软件，你获得优势，并说的方式Apeo，和CAS项目，运行和运行。 如果您认为 CAS 部署是身份和访问管理生态系统的关键部分，则这是一个可行的选择。

## 参与

- 今天开始您的CAS部署。 尝试功能， [分享反馈](/cas/Mailing-Lists.html)。
- 更妙的是， [](/cas/developer/Contributor-Guidelines.html)提供补丁。
- 建议并应用文档改进。

## 资源

- [发布时间表](https://github.com/apereo/cas/milestones)
- [发布策略](/cas/developer/Release-Policy.html)

## 覆盖

在 [CAS战争覆盖](../installation/WAR-Overlay-Installation.html)`` ，调整下列设置：

```properties
卡斯.版本=6.3.0-RC1
```

<div class="alert alert-info">
  <strong>系统要求</strong><br/>本版本的最低系统/平台要求没有更改。
</div>

## 值得注意的新 &

### 弹簧启动 2.3

中科院已切换到春靴 `2.3.x.释放`。 CAS 叠加也已更新，以与此更改同步。 虽然 这被归类为弹簧启动功能/小版本，但鉴于该框架在 CAS 中的重要使用，升级的影响可能更加明显。

下列设置受升级影响，应进行调整以匹配以下内容：

```properties
服务器.tomcat.线程.分钟备用=10
服务器.tomcat.线程.max=200

服务器.服务器.编码.编码.字符集=UTF-8
服务器. servlet.en 编码.启用]真实
服务器.servlet.编码.force=真实

管理.端点.健康.状态.订单=警告、向下、OUT_OF_SERVICE、未知、向上

服务器.tomcat.连接超时=PT2 0S
服务器.tomcat.max-http 格式后大小=2097152

服务器.tomcat.remoteip.端口头=X 转发端口
服务器.tomcat.远程 ip.协议-标题=X转发-原
服务器.tomcat.远程.协议-标题-https-价值=
服务器.tomcat.远程-ip-头=X-转发-FOR
```

升级过程中未发现兼容性问题，配置名称空间对 CAS 基本未受影响。 也就是说，请怀疑和核实。

### 通过代码库进行测试覆盖

目前，CAS 在代码库中所有模块的测试覆盖率已达到 82%</code> `，并且还在继续攀升。 
，还应用了其他验证规则，以失败所有低于此阈值的拉取请求。 随着取得进展，这一领域将得到密切监测和改进，
有望在最终大会发布前达到至少85%` `。 当然，这不会是最终版本的拦截器。</p>

<h3 spaces-before="0">重新分配群集支持</h3>

<p spaces-before="0">重新分配支持和配置名称空间现在能够支持与 Redis 群集的连接。</p>

<h3 spaces-before="0">用于 CAS 事件的发电机数据库存储</h3>

<p spaces-before="0"><a href="../installation/Configuring-Authentication-Events.html">CAS 事件</a> 现在可以存储在 DynamoDb 实例中。</p>

<h3 spaces-before="0">沙发基地可接受使用策略</h3>

<p spaces-before="0"><a href="../webflow/Webflow-Customization-AUP.html">可接受的使用策略</a> 决策现在可以通过 Couchbase 数据库进行管理和跟踪。</p>

<h3 spaces-before="0">通过 Git 存储库的 SAML2 元数据</h3>

<p spaces-before="0"><a href="../installation/Configuring-SAML2-DynamicMetadata.html">SAML2 元数据</a> 文物现在可以从 Git 存储库中提取和提取。 此功能支持服务和身份提供商工件。</p>

<h3 spaces-before="0">多因素身份验证网络流</h3>

<p spaces-before="0">多因素身份验证提供商的 Webflow 定义（即 Google 身份验证器、Authy 等）现在通过 Webflow 自动配置而不是静态 XML 定义在运行时动态 
构建。 这允许更好的灵活性，以及测试覆盖，当涉及到自定义。</p>

<h3 spaces-before="0">U2F 多因素身份验证受信任的设备</h3>

<p spaces-before="0">支持 <a href="../mfa/Multifactor-TrustedDevice-Authentication.html">多因素身份验证受信任的设备/浏览器</a> 现已扩展 
，还包括 <a href="../mfa/FIDO-U2F-Authentication.html">U2F</a>。 此外，还 
提出一些新的行政执行器端点，以报告注册设备或删除/取消注册设备。</p>

<h3 spaces-before="0">身份验证执行器端点</h3>

<p spaces-before="0">一些新的 <a href="../installation/Configuring-Authentication-Components.html">行政执行器端点</a> 
提交，以汇报注册认证处理程序和政策。</p>

<h3 spaces-before="0">用于 U2F 多因素身份验证的发电机数据库存储</h3>

<p spaces-before="0"><a href="../mfa/FIDO-U2F-Authentication.html">U2F 多因素身份验证</a> 设备现在可以存储在 DynamoDb 实例中。</p>

<h3 spaces-before="0">格拉德尔远程构建缓存</h3>

<p spaces-before="0">CAS Gradle 构建现在连接到远程构建缓存服务器，以最大限度地提高连续集成构建的性能。</p>

<p spaces-before="0"><img src="https://user-images.githubusercontent.com/1205228/84562682-9d46f300-ad6b-11ea-8ed8-3042a3facbec.png" alt="图像" /></p>

<h3 spaces-before="0">谷歌身份验证器帐户注册</h3>

<p spaces-before="0">在完成帐户注册流程之前，已增强了多因素身份验证的 Google 身份验证器，以请求代币。 一旦所提供的令牌得到验证，该帐户将在 CAS 注册，并准备后续的多因素认证。</p>

<p spaces-before="0"><img src="https://user-images.githubusercontent.com/1205228/86023135-83323380-ba40-11ea-8d16-4fe8ff560c99.png" alt="图像" /></p>

<h3 spaces-before="0">阿帕奇 J 仪性能测试</h3>

<p spaces-before="0"><a href="../high_availability/Performance-Testing-JMeter.html">阿帕奇JMeter性能测试</a> ，船舶与中科院现在 
添加到 <a href="https://github.com/apereo/cas/actions">GitHub行动</a>。 此时，只有 <em x-id="3">CAS</em> 变种进行了测试， 
一旦 CAS 运行时上下文（即 WAR 叠加）可以 
模块选择菜单按需动态构建。 目标是确保 JMeter 测试工件 
和脚本是可维护和管理的，从一个 CAS 版本到下一个 CAS 版本。</p>

<h3 spaces-before="0">谷歌火基云消息</h3>

<p spaces-before="0">初步支持是基于 <a href="../notifications/Notifications-Configuration.html">谷歌火基云消息</a>的通知。 此功能的第一个消费者
是 <a href="../mfa/Simple-Multifactor-Authentication.html">简单的多因素身份验证</a> 模块。</p>

<h3 spaces-before="0">通过阿帕奇·卡夫卡进行服务注册复制</h3>

<p spaces-before="0">如果 CAS 服务定义不是通过集中存储在全球管理的，则当部署多个节点时，定义需要保持 
在聚类中的所有 CAS 节点同步。 如果您不想求助于外部手件和流程，或者如果您的 
部署的原生选项没有那么有吸引力，您可以利用 CAS 自己的模具 <a href="../services/Configuring-Service-Replication.html">由 Apache Kafka</a> 支持，该 
分布式缓存用于在群集中广播服务定义文件。</p>

<h3 spaces-before="0">谷歌身份验证器多台设备</h3>

<div class="alert alert-warning">
  <strong>小心！</strong><br />这可能是一个突破性的变化。 对管理用户设备记录的基础数据模型和存储库实现进行了修改，以处理每个用户的设备集合。 这确实会影响数据库或文件系统计划和 API 调用，其中需要收集而不是单个结果。
</div>

<p spaces-before="0">谷歌多因素身份验证器现在允许接受和注册多个设备。 当找到多个注册记录时，必须在注册时分配用于设备选择菜单的帐户或设备的名称。 在通过 REST 验证 Google 身份验证器令牌时，如果用户帐户具有多个注册设备，则必须指定帐户标识符。 此外，请注意，允许每个用户使用多个设备是通过 CAS 设置控制的，默认情况下会禁用，以保持行为与之前版本的兼容性。</p>

<table spaces-before="0">
<thead>
<tr>
  <th></th>
  <th></th>
</tr>
</thead>
<tbody>
<tr>
  <td><img src="https://user-images.githubusercontent.com/1205228/85271898-ad0fb700-b490-11ea-9f69-60ae4aa59bd2.png" alt="图像" /></td>
  <td><img src="https://user-images.githubusercontent.com/1205228/85271811-8a7d9e00-b490-11ea-9d49-5689f7f539f2.png" alt="图像" /></td>
</tr>
</tbody>
</table>

<h3 spaces-before="0">尤比基设备的发电机数据库存储</h3>

<p spaces-before="0"><a href="../mfa/YubiKey-Authentication.html">YubiKey 设备</a> 现在可以存储在 DynamoDb 实例中。</p>

<h3 spaces-before="0">斯瓦格集成</h3>

<p spaces-before="0"><a href="../integration/Swagger-Integration.html">斯瓦格集成</a> 可以通过 <a href="https://springdoc.org/">斯普林多克</a>升级为使用斯瓦格v2。</p>

<h2 spaces-before="0">其他内容</h2>

<ul>
<li>已更正映射到外部 Groovy 脚本的属性定义，以更有利于资源的方式处理缓存。</li>
<li>服务定义的管理现在将搜索操作委托给服务注册表，而不是在内部过滤匹配项，同时利用缓存层尽可能提高性能。</li>
<li>非授权/OIDC <code>代币` 代码的生成现在已经过适当审核。 此外，为非授权/OIDC 功能</code> 标志的 `将恢复到活动主体 ID。</li>
<li>由 <a href="../installation/Syncope-Authentication.html">Apache 同步</a> 支持的身份验证策略已增强，无需依赖 Apache 同步模块，允许集成与所有 Apache 同步版本配合工作。 进一步的改进，以确保配置可以符合重新加载的要求和 <code>@RefreshScope`等。</li>
- 多因素 & 授权身份验证的无密码帐户的资格已切换到 `三国` 类型，以便根据全球设置进行审查时更容易覆盖和未定义状态。
- 在使用 Git 集成时，用于提交操作的用户名和电子邮件属性现在通过本地、全球和系统 git 配置解决，然后再返回到默认的 CAS 控制值。
- 服务管理 `发现ServiceBy（）` 操作现在直接委托给服务注册表，中间有一个适度的缓存层，以尽可能提高和保留性能。
- 测试改进，以减少启动运行时上下文所需的重复配置类数量。
- 现在，使用从钥匙店提取的算法可以正确签名 OpenID 连接 ID 代币， `是` 字段应正确反映 CAS 配置中的发行人。
- [蝗灾性能测试](../high_availability/Performance-Testing-Locust.html) 现已升级为使用蝗虫 `110`。
- OAuth 或 OpenID 连接的 ID 令牌或用户信息有效负载生成现在已硬化，以防止在发现设置中未定义时没有</code> 算法 。</li>
</ul>

<h2 spaces-before="0">库升级</h2>

<ul>
<li>错误提示编译器</li>
<li>未绑定 LDAP SDK</li>
<li>弹簧启动</li>
<li>春云</li>
<li>春季数据</li>
<li>弹簧启动管理</li>
<li>雨云</li>
<li>斯瓦格</li>
<li>斯瓦格</li>
<li>亚马逊 SDK</li>
<li>阿帕奇·汤姆卡特</li>
<li>帕克4j</li>
<li>特维利奥</li>
<li>主动MQ</li>
<li>弹力城堡</li>
<li>斯瓦格</li>
<li>滴维扎德</li>
<li>阿帕奇策展人</li>
<li>蝗</li>
<li>开放销售</li>
<li>奥希</li>
<li>沙发基地驱动程序</li>
<li>蒙古德布司机</li>
<li>尼姆布斯· 奥多克</li>
</ul>
