---
layout: 默认
title: CAS-CAS协议规范
category: 通讯协定
---

<a name="headTop"></p>
<h1 spaces-before="0">
  <em x-id="3">CAS协议3.0规范</em>
</h1>
<h1 spaces-before="0">
  <strong x-id="1">作者，版本</strong>
</h1>

<p spaces-before="0">
  作者：德鲁·马祖雷克（Drew Mazurek）
</p>

<p spaces-before="0">
  贡献者：
</p>

<ul>
  <li>
    苏珊·布拉姆霍尔（Susan Bramhall）
  </li>
  <li>
    霍华德·吉尔伯特
  </li>
  <li>
    安迪·纽曼（Andy Newman）
  </li>
  <li>
    安德鲁·佩特罗
  </li>
  <li>
    罗伯特·奥施瓦尔德[CAS 3.0]
  </li>
  <li>
    米沙·莫耶德（Misagh Moayyed）
  </li>
</ul>

<p spaces-before="0">
  版本：3.0.3
</p>

<p spaces-before="0">
  发布日期：2017-12-01
</p>

<p spaces-before="0">
  耶鲁大学2005年版权 &copy;
</p>

<p spaces-before="0">
  Apereo，Inc.版权所有 &copy;
</p>

<p spaces-before="0">

<a name="head1"></p>
<h1 spaces-before="0">
  <strong x-id="1">1。 介绍</strong>
</h1>

<p spaces-before="0">
  这是CAS 1.0、2.0和3.0协议的正式规范。
</p>

<p spaces-before="0">
  中央身份验证服务（CAS）是Web的 它允许用户访问多个应用程序，同时 应用 凭据（例如，用户ID和密码）一次。
</p>

<p spaces-before="0">


<a name="head1.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">1.1。 约定 & 定义</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    本文档中的关键字“必须”，“不得”，“必须”，“应”，“应不”，“应”， “应不”，“建议”，“可以”和“可选”如RFC 2119 [<a href="#1">1</a>]中
  </p>
  
  <ul>
    <li>
      <p spaces-before="0">
        “客户端”是指最终用户和/或Web浏览器。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “ CAS客户端”是指与Web 应用程序集成并通过CAS协议与CAS服务器进行交互的软件组件。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “服务器”是指中央身份验证服务服务器。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “服务”是指客户端尝试访问的应用程序。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “后端服务”是指服务试图代表客户端 这也可以称为“目标服务”。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “ SSO”是指单点登录。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “ SLO”是指单一注销。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        “&lt;LF&gt;”是空换行（ASCII值0x0a）。
      </p>
    </li>
  </ul>
  
  <p spaces-before="0">

<a name="head1.2"></p> 
    
    <p spaces-before="0">
      <strong x-id="1">1.2参考实现</strong>
    </p>
<hr />
    
    <p spaces-before="0">
      Apereo CAS服务器[<a href="#8">8</a> CAS协议规范的官方参考实现。
    </p>
    
    <p spaces-before="0">
      Apereo CAS Server 4.x和更高版本支持CAS协议3.0规范。
    </p>
    
    <p spaces-before="0">

<a name="head2"></p>
<h1 spaces-before="0">
  <strong x-id="1">2。 CAS URI</strong>
</h1>

<p spaces-before="0">
  CAS是基于HTTP [<a href="#2">2</a>]，[<a href="#3">3</a>]的协议，要求它的 组件中的每一个都可以通过特定的URI进行访问。 本节将讨论每个URI
</p>

<table spaces-before="0">
  <tr>
    <th>
      URI
    </th>
    
    <th>
      描述
    </th>
  </tr>
  
  <tr>
    <td>
      <code>/登录</code>
    </td>
    
    <td>
      凭证请求者/接受者
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/登出</code>
    </td>
    
    <td>
      销毁CAS会话（注销）
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/证实</code>
    </td>
    
    <td>
      服务票证验证
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/ serviceValidate</code>
    </td>
    
    <td>
      服务票证验证[CAS 2.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/ proxyValidate</code>
    </td>
    
    <td>
      服务/代理票证验证[CAS 2.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/代理人</code>
    </td>
    
    <td>
      代理票务服务[CAS 2.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/ p3 / serviceValidate</code>
    </td>
    
    <td>
      服务票证验证[CAS 3.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/ p3 / proxyValidate</code>
    </td>
    
    <td>
      服务/代理票证验证[CAS 3.0]
    </td>
  </tr>
</table>

<p spaces-before="0">

<a name="head2.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.1。 /登录为凭证请求者</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/ login</code> URI具有两种行为：作为凭据请求者，而作为 作为凭据接受者。 它通过充当凭据 接受者来响应凭据，否则充当凭据请求者。
  </p>
  
  <p spaces-before="0">
    如果客户端已经与CAS建立了单点登录会话，则 Web浏览器向CAS提供一个安全cookie，其中包含一个标识 的票证授予票证的字符串。 此cookie称为授予票证的cookie。 如果授予票据的cookie键指向有效的授予票据的票证，则 中的所有其他条件 有关授予票证的cookie的更多信息，请参见第 <a href="#head3.6">3.6</a>
  </p>
  
  <p spaces-before="0">

<a name="head2.1.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.1。 参数</strong>
</h3>

<p spaces-before="0">
  以下HTTP请求参数可以传递给 <code>/ login</code> 而 作为凭证请求者。 它们都是区分大小写的，并且全部 必须由 <code>/ login</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [OPTIONAL] 正在尝试访问的应用程序的标识符。 在几乎所有情况下，这都是 应用程序的URL。 作为HTTP请求参数，此URL值必须按照<a href="#4">4</a>]的2.2节中的描述 如果 <code>服务</code> 并且尚不存在单个登录会话 ，则CAS应该从用户请求凭据以启动单个 登录会话。 如果 <code>服务</code> 并且单点登录 会话已经存在，则CAS应该显示一条消息，通知客户端 它已经登录。
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注意：强烈建议通过 <code>服务</code> url，以便只有授权和已知的 客户端应用程序才能使用CAS服务器。 保持服务管理工具的开放状态以允许对 可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议客户端应用程序 安全协议（例如 <code>https</code> ，以进一步加强身份验证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>更新</code> [OPTIONAL] 如果设置了此参数，将绕过 在这种情况下，无论是否存在与CAS的单点登录会话，CAS都将要求客户端出示凭据 此 参数与 <code>网关</code> 参数不兼容。 将服务 重定向到 <code>/ login</code> URI，并将登录表单视图发布到 <code>/ login</code> URI不应同时设置 <code>renew</code> 和 <code>gateway</code> 请求参数。 如果两者都设置，则行为是不确定的。 建议如果设置了 <code>更新</code> 实现将忽略 <code>网关</code> 建议将 <code>renew</code> 参数设置为0时，其值为“ true”。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>网关</code> [OPTIONAL] 如果设置了此参数，CAS将不会向客户端 询问凭据。 如果客户端具有与CAS预先存在的单点登录会话 非交互方式（即信任身份验证）建立单点登录会话 客户端重定向到指定的URL通过 <code>service</code> 参数，附加一个有效的 service票证。 （CAS也可以插入一个咨询页面，通知 客户端已经进行了CAS认证。） 如果客户端不为 则与CAS进行单次登录会话，并且 ，则CAS必须将客户端重定向至由 <code>服务</code> 参数指定的URL，且不附加“ ticket”参数URL。 如果未指定 <code>服务</code> ，并且设置了 <code>网关</code> 未定义。 建议在这种情况下，CAS请求 凭据，就像未指定任何参数一样。 此参数与 <code>renew</code> 参数 如果两个都设置 则行为是不确定的。 建议将 <code>网关</code> 参数设置为 “真”。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>方法</code> [可选，CAS 3.0]-发送响应时要使用 <code>方法</code> 虽然可以将本机HTTP重定向（<code>GET</code>）用作默认方法，但是需要 <code>POST</code> 应用程序可以使用此参数来指示 方法类型。 还可以指定 <code>HEADER</code> 以指示CAS最终响应（例如 <code>服务</code> 和 <code>票证</code>应以HTTP响应标头的形式 它是由CAS服务器执行，以确定 是否 <code>POST</code> 或 <code>HEADER</code> 响应被支撑。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.1.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.2。 / login的URL示例</strong>
</h3>

<p spaces-before="0">
  简单的登录示例：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?service=http%3A%2F%2Fwww.example.org%2F服务</code>
</p>

<p spaces-before="0">
  不要提示输入用户名/密码：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?service=http%3A%2F%2Fwww.example.org%2F服务&网关= true</code>
</p>

<p spaces-before="0">
  总是提示输入用户名/密码：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?service=http%3A%2F%2Fwww.example.org%2F服务&更新= true</code>
</p>

<p spaces-before="0">
  使用POST响应而不是重定向：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?method=POST&服务= http%3A%2F%2Fwww.example.org%2F服务</code>
</p>

<p spaces-before="0">

<a name="head2.1.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.3。 用户名/密码认证的响应</strong>
</h3>

<p spaces-before="0">
  当 <code>/ login</code> 充当凭据请求者时，响应将根据请求的凭据类型 在大多数情况下，CAS将以 响应，显示一个登录屏幕，要求输入用户名和密码。 该页面必须 包含带有参数“用户名”，“密码”和“ lt”的表格。 格式 MAY还可以包含参数“ warn”。 如果 <code>服务</code> 被指定为 <code>/ login</code>，则 <code>服务</code> 也必须是该形式的参数，包含最初传递给 <code>/ login</code> 。 这些参数将在第 <a href="#head2.2.1">2.2.1</a>节中详细讨论。 必须通过HTTP POST方法将 <code>/ login</code> ，然后它将 作为凭证接受器，如第 <a href="#head2.2">2.2</a>节所述。
</p>

<p spaces-before="0">

<a name="head2.1.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.4。 信任认证响应</strong>
</h3>

<p spaces-before="0">
  信任身份验证可考虑将 请求的任意方面作为身份验证的基础。 和实施的特定身份验证机制的后勤功能， 身份验证的适当用户体验将是高度特定于部署者的。
</p>

<p spaces-before="0">
  当 <code>/ login</code> 充当信任身份验证的凭据请求者时，其 行为将由它将接收的凭据类型决定。 如果 ，则CAS可以透明地将用户重定向到 服务。 或者，CAS可以显示警告，提示凭据为 并允许客户端确认要使用这些凭据。 建议CAS实现允许部署者选择首选 行为，这是 如果凭据无效或不存在，建议 CAS向客户端显示身份验证失败的原因，并可能 提供其他身份验证方式（例如，用户名/密码 身份验证）。
</p>

<p spaces-before="0">

<a name="head2.1.5"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.5。 单点登录身份验证的响应</strong>
</h3>

<p spaces-before="0">
  如果客户端已经与CAS建立了单点登录会话，则 <code>/ login</code> 呈现其HTTP会话cookie，并且将按照第 <a href="#head2.2.4">节2.2.4</a> 。 但是，如果设置了 <code>renew</code> 参数 <a href="#head2.1.3">2.1.3</a> 或 <a href="#head2.1.4">2.1.4</a>节中的说明处理该行为。
</p>

<p spaces-before="0">

<a name="head2.2"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.2。 /登录为凭证接受者</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    将一组接受的凭据传递给 <code>/ login</code>， <code>/ login</code> 充当 凭据接受器，并且其行为在本节中定义。
  </p>
  
  <p spaces-before="0">

<a name="head2.2.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.1。 所有认证类型共有的参数</strong>
</h3>

<p spaces-before="0">
  下列HTTP请求参数可以在其作为凭证接受者 <code>/ login</code> 它们都区分大小写，并且都必须由 <code>/ login</code> 。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [OPTIONAL] 客户端尝试访问 的应用程序的URL。 作为HTTP请求参数，必须将该URL值URL编码为<a href="#4">4</a>]的2.2节中所述的 成功进行身份验证后，CAS必须将 第 <a href="#head2.2.4">2.2.4</a>节中将 。 如果CAS Server在 非开放模式下运行（允许使用CAS Server的服务URL在CAS Server中注册为 ），则如果未经授权的服务URL是，则CAS Server务必拒绝操作并打印 呈现。
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注意：强烈建议通过 <code>服务</code> url，以便只有授权和已知的 客户端应用程序才能使用CAS服务器。 保持服务管理工具的开放状态以允许对 可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议客户端应用程序 安全协议（例如 <code>https</code> ，以进一步加强身份验证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>警告</code> [OPTIONAL] 如果设置了此参数，则单点登录不得为 透明。 必须先提示客户端，然后再将其认证为 另一个服务。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>方法</code> [OPTIONAL] 发送响应时要使用的 <code>方法</code> 有关详细信息，请参见 第 <a href="#head2.1.1">节2.1.1</a>
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.2.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.2。 用户名/密码认证的参数</strong>
</h3>

<p spaces-before="0">
  除了在节规定的可选参数 <a href="#head2.2.1">2.2.1</a>， 以下HTTP请求的参数必须被传递到 <code>/登录</code> 而这是 充当用于用户名/密码验证的认证受体。 它们均为 均区分大小写。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>username</code> [REQUIRED] 尝试登录的客户端的用户名
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>password</code> [REQUIRED] 尝试登录的客户端的密码
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>lt</code> [OPTIONAL] 登录票。 这是第 <a href="#head2.1.3">节2.1.3</a>讨论的登录表单 一部分。 登录票本身在第 <a href="#head3.5">3.5</a> 。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>RememberMe</code> [OPTIONAL，CAS 3.0]-如果设置了此参数，则CAS服务器可能会创建 Remember-Me支持）。 个长期票证授予票证取决于CAS服务器配置。
    </p>
  </li>
</ul>

<blockquote spaces-before="0">
  <p spaces-before="0">
    的长期票证授予票证（记住我）时，必须考虑安全因素。 示例包括共享的计算机使用情况。 在CAS客户端系统上，处理“记住我”的登录名 有关详细信息，请参见第 <a href="#head4.1">4.1</a>
  </p>
</blockquote>

<p spaces-before="0">

<a name="head2.2.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.3。 信任认证的参数</strong>
</h3>

<p spaces-before="0">
  没有用于信任身份验证的REQUIRED HTTP请求参数。 信任 认证可以基于HTTP请求的任何方面。
</p>

<p spaces-before="0">

<a name="head2.2.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.4。 回复</strong>
</h3>

<p spaces-before="0">
  <code>/登录</code> 作为凭据接受者 时，必须由0 /登录1提供以下响应之一。
</p>

<ul>
  <li>
    <p spaces-before="0">
      成功登录：将客户端重定向到 <code>service</code> 参数指定的URL，其方式不会导致将客户端的凭证 转发到 <code>service</code>。 这种重定向必须导致客户端发出 GET请求到 <code>服务</code>。 该请求务必包含有效的服务 票证，该票证是作为HTTP请求参数“ ticket”传递的。 有关更多信息，请参见 <a href="#head_appdx_b">附录 B</a> 如果未指定 <code>服务</code> CAS必须显示一条消息，通知客户端它已经成功 启动了一次登录会话。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      登录失败：作为凭据请求者 <code>/登录</code> 它建议 在此情况下，所述CAS服务器显示错误消息给用户 描述为什么登录失败（例如错误的密码，被锁定的帐户等）， 和如果合适的话，提供一个机会，用户尝试 再次登录。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.3"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.3。 /登出</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/ logout</code> 破坏客户端的单点登录CAS会话。 票证授予 cookie（第 <a href="#head3.6">3.6</a>节）被破坏，随后对 <code>/ login</code> 请求将不会 获得服务票证，直到用户再次提供主凭证（并且 从而建立了新的单点登录会话）。
  </p>
  
  <p spaces-before="0">

<a name="head2.3.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.3.1。 参数</strong>
</h3>

<p spaces-before="0">
  可以将以下HTTP请求参数指定为 <code>/ logout</code>。 区分大小写 ，应该由 <code>/ logout</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [可选，CAS 3.0]-如果指定 <code>服务</code> 参数，则CAS服务器执行注销后 浏览器可能会自动重定向到 <code>服务</code> 是否实际通过 CAS服务器进行重定向取决于服务器配置。 作为HTTP请求参数， <code>服务</code> 值必须按照<a href="#4">4</a>]的2.2节中的描述进行
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注意：强烈建议通过 <code>服务</code> url，以便只有授权和已知的 客户端应用程序才能使用CAS服务器。 保持服务管理工具的开放状态以允许对 可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议客户端应用程序 安全协议（例如 <code>https</code> ，以进一步加强身份验证客户端。
      </p>
    </blockquote>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注： <code>URL</code> 前CAS 2.0规范定义的参数是 不是在CAS 3.0有效的参数了。 CAS服务器必须忽略给定的 <code>url</code> 参数。 如上所述，CAS客户端可以提供 <code>服务</code> 参数，因为 可以确保在非开放模式下操作时针对注册的服务 有关详细信息，请参见 <a href="#head2.3.2">2.3.2</a>
      </p>
    </blockquote>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.3.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.3.2。 回复</strong>
</h3>

<p spaces-before="0">
  [CAS 1.0，CAS 2.0] <code>/ logout</code> 必须显示一个页面，指出用户已经 注销。 如果实现了“ url”请求参数，则 <code>/ logout</code> 也应该 提供指向所提供URL的链接，如第 <a href="#head2.3.1">节2.3.1</a>。
</p>

<p spaces-before="0">
  [CAS 3.0] <code>/登出</code> 必须显示一个页面，指出如果未提供 <code>service</code> 参数 如果提供了 <code>服务</code> 请求参数，则CAS服务器在成功注销后将重定向到给定的服务URL
</p>

<blockquote spaces-before="0">
  <p spaces-before="0">
    注意：当CAS Server在非开放模式下运行时（允许 服务URL），CAS服务器必须确保仅 注册的 [service] 参数服务URL进行重定向。 在 <code>URL</code> 前CAS 2.0规范定义的参数是 不是在CAS 3.0有效的参数了。 CAS服务器必须忽略给定的 <code>url</code> 参数。
  </p>
</blockquote>

<p spaces-before="0">

<a name="head2.3.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.3.3单一注销</strong>
</h3>

<p spaces-before="0">
  CAS服务器可以支持单一注销（SLO）。 SLO意味着用户不仅从CAS服务器注销，还从所有访问的CAS客户端 应用程序 如果CAS服务器支持SLO，则每当获得票证授予票证时 提供给CAS的所有服务URL发送包含注销XML文档（请参阅 <a href="#head_appdx_c">附录 C</a> 由用户明确过期（例如，在注销期间）。 不支持SLO POST请求的CAS客户端必须忽略这些请求。 SLO请求也可以在TGT空闲超时后由CAS服务器发起。
</p>

<p spaces-before="0">



<a name="head2.3.3.1"></p>

<h4 spaces-before="0">
  <strong x-id="1">2.3.3.1服务器行为</strong>
</h4>

<p spaces-before="0">
  CAS服务器应忽略对CAS客户端应用程序服务URL的 请求上可能发生的所有错误。 这样可以确保发送POST请求时出现的任何错误 都不会影响CAS Server的性能和 可用性（“即发即弃”）。
</p>

<p spaces-before="0">



<a name="head2.3.3.2"></p>

<h4 spaces-before="0">
  <strong x-id="1">2.3.3.2客户行为</strong>
</h4>

<p spaces-before="0">
  处理注销POST请求数据取决于CAS客户端。 它建议 登出从由服务票证id标识的应用程序的用户发送 在SLO POST请求。 如果客户端支持SLO POST请求处理，则客户端应返回HTTP成功 状态代码。
</p>

<p spaces-before="0">



<a name="head2.4"></p>

<h2 spaces-before="0">
  <strong x-id="1">2.4。 /验证[CAS 1.0]</strong>
</h2>

<p spaces-before="0">
  <code>/ validate</code> 检查服务票证的有效性。 <code>/ validate</code> 是CAS 1.0协议的一部分，因此不处理代理身份验证。 当将代理票证传递给 <code>/ validate</code>时，CAS必须 。
</p>

<p spaces-before="0">



<a name="head2.4.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.4.1。 参数</strong>
</h3>

<p spaces-before="0">
  可以将以下HTTP请求参数指定为 <code>/ validate</code>。 它们 并且必须全部由 <code>/ validate</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>service</code> [REQUIRED] 的服务的标识符，如第2.2.1节所述。 作为HTTP请求参数， <code>服务</code> 值必须按照<a href="#4">4</a>]的2.2节中的描述进行
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注意：强烈建议通过 <code>服务</code> url，以便只有授权和已知的 客户端应用程序才能使用CAS服务器。 保持服务管理工具的开放状态以允许对 可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议客户端应用程序 安全协议（例如 <code>https</code> ，以进一步加强身份验证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>票</code> [REQUIRED] -颁发的服务票证 <code>/登录</code>。 服务票为3.1节中所述
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>renew</code> [OPTIONAL] 如果设置了此参数， 主要凭证的表示中发出了服务票证 登录会话发出的，则它将失败。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head2.4.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.4.2。 回复</strong>
</h3>

<p spaces-before="0">
  <code>/ validate</code> 将返回以下两个响应之一：
</p>

<p spaces-before="0">
  票证验证成功：
</p>

<p spaces-before="0">
  是&lt;LF&gt;用户名&lt;LF&gt;
</p>

<p spaces-before="0">
  在票证验证失败时：
</p>

<p spaces-before="0">
  否&lt;LF&gt;
</p>

<p spaces-before="0">



<a name="head2.4.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.4.3。 / validate的网址示例</strong>
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/validate?service=http%3A%2F%2Fwww.example.org%2Fservice&ticket = ST-1856339-aA5Yuvrxzpv8Tau1cYQ7</code>
</p>

<p spaces-before="0">
  确保通过提供主要凭证来发行服务票证：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/validate?service=http%3A%2F%2Fwww.example.org%2Fservice&ticket = ST-1856339-aA5Yuvrxzpv8Tau1cYQ7&renew = true</code>
</p>

<p spaces-before="0">



<a name="head2.5"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.5。 / serviceValidate [CAS 2.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/ serviceValidate</code> 检查服务票证的有效性并返回XML片段响应。 <code>/ serviceValidate</code> 还必须在请求时生成并颁发代理授予票证。 <code>/ serviceValidate</code> 如果接收到代理票证，则不得返回成功的身份验证。 建议如果 <code>/ serviceValidate</code> 收到代理票证，则XML 响应中的错误消息应该解释验证失败，因为代理票证已通过 传递给 <code>/ serviceValidate</code>。
  </p>
  
  <p spaces-before="0">



<a name="head2.5.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.1。 参数</strong>
</h3>

<p spaces-before="0">
  可以将以下HTTP请求参数指定为 <code>/ serviceValidate</code>。 它们 区分大小写，并且必须全部由 <code>/ serviceValidate</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>service</code> [REQUIRED] 的服务的标识符，如第 <a href="#head2.2.1">节2.2.1</a>。 作为HTTP请求参数， <code>服务</code> 值必须按照<a href="#4">4</a>]的2.2节中的描述进行
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注意：强烈建议通过 <code>服务</code> url，以便只有授权和已知的 客户端应用程序才能使用CAS服务器。 保持服务管理工具的开放状态以允许对 可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议客户端应用程序 安全协议（例如 <code>https</code> ，以进一步加强身份验证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>票</code> [REQUIRED] -颁发的服务票证 <code>/登录</code>。 服务票为 如第 <a href="#head3.1">节3.1</a>。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>pgtUrl</code> [OPTIONAL] 代理回调的URL。 <a href="#head2.5.4">2.5.4</a>节中讨论。 作为HTTP请求的参数中，“pgtUrl”值必须是URL编码为 在RFC 1738 [第2.2节描述的<a href="#4">4</a>]。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>renew</code> [OPTIONAL] 如果设置了此参数， 主要凭证的表示中发出了服务票证 登录会话发出的，则它将失败。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>格式</code> [OPTIONAL] 如果设置了此参数，则必须基于参数值生成 支持的值为 <code>XML</code> 和 <code>JSON</code>。 如果未设置此参数，则将使用默认的 <code>XML</code> 格式。 如果CAS服务器不支持该参数值，则 ，如第 <a href="#head2.5.3">2.5.3</a>节中所述。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.5.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.2。 回复</strong>
</h3>

<p spaces-before="0">
  <code>/ serviceValidate</code> 如所描述的将返回一个XML格式的CAS serviceResponse 在XML模式 <a href="#head_appdx_a">附录A</a>。 以下是示例响应：
</p>

<p spaces-before="0">
  <strong x-id="1">票证验证成功：</strong>
</p>

<pre><code class="xml">&lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
 &lt;cas:authenticationSuccess&gt;
  &lt;cas:user&gt;用户名&lt;/cas:user&gt;
  &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d ...&lt;/cas:proxyGrantingTicket&gt;
 &lt;/cas:authenticationSuccess&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  “ serviceResponse”：{
    “ authenticationSuccess”：{
      “ user”：“用户名”，
      “ proxyGrantingTicket”：“ PGTIOU-84678-8a9d ...”
    }
  }
}
</code></pre>

<p spaces-before="0">
  <strong x-id="1">在票证验证失败时：</strong>
</p>

<pre><code class="xml">&lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
 &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
    无法识别票证ST-1856339-aA5Yuvrxzpv8Tau1cYQ7
  &lt;/cas:authenticationFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  “ serviceResponse”：{
    “ authenticationFailure”：{
      “ code”：“ INVALID_TICKET”，
      “ description”：“未识别票号ST-1856339-aA5Yuvrxzpv8Tau1cYQ7”
    }
  }
}
</code></pre>

<p spaces-before="0">
  有关代理响应，请参见第 <a href="#head2.6.2">2.6.2</a>节。
</p>

<p spaces-before="0">

<a name="head2.5.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.3。 错误代码</strong>
</h3>

<p spaces-before="0">
  以下值可以用作认证 故障响应的“代码”属性。 服务器必须实现的最小错误代码集。 实现可能包括其他实现。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>INVALID_REQUEST</code> 并非所有必需的请求参数都存在
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_TICKET_SPEC</code> 无法满足验证规范的要求
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>UNAUTHORIZED_SERVICE_PROXY</code> 该服务无权执行代理身份验证
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_PROXY_CALLBACK</code> 指定的代理回调无效。 为代理身份验证指定的凭据
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_TICKET</code> 所提供的票证无效，或者该票证不是 来自初始登录，验证时设置了 <code>更新</code> XML响应 <code>&lt;cas:authenticationFailure&gt;</code> 块的主体 的确切细节。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_SERVICE</code> 提供的票证有效，但指定的服务 与与票证关联的服务不匹配。 CAS必须 ，并禁止以后对该票证进行验证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INTERNAL_ERROR</code> 票证验证期间发生内部错误
    </p>
  </li>
</ul>

<p spaces-before="0">
  对于所有错误代码，建议CAS提供更详细的消息 作为XML响应 <code>&lt;cas:authenticationFailure&gt;</code> 块的主体。
</p>

<p spaces-before="0">

<a name="head2.5.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.4。 代理回调</strong>
</h3>

<p spaces-before="0">
  如果服务希望将客户端的身份验证代理到后端服务，则该服务 必须获取代理授权票证（PGT）。 通过代理回调URL将该票证的获取处理为 该URL将唯一且安全地标识代理客户端身份验证 后端服务可以 再决定是否要接受基础上进行代理凭据 服务标识回调URL。
</p>

<p spaces-before="0">
  代理回调机制的工作方式如下：
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      正在请求代理授予票证（PGT）的服务在初始服务票证或代理票证验证为 “ pgtUrl”指定为 <code>/ serviceValidate</code> （或 <code>/ proxyValidate</code>）。 CAS将连接到该服务以验证服务身份的服务的回调URL为 这个 URL必须是HTTPS，并且CAS必须评估端点以建立对等信任。 至少建立信任涉及使用PKIX和使用容器信任将 回调URL的证书的签名，链和到期窗口。 代理授予票证或相应的 代理授予票证IOU的生成可能由于代理回调URL不满足最低 安全性要求而失败，例如无法建立对等方之间的信任或端点的 如果发生故障，将不会发出授权代理票证，并且 <a href="#head2.5.2">部分2.5.2</a> 服务响应不得包含 <code>&lt;proxyGrantingTicket&gt;</code> 块。 此时，将 代理授权票证，并且服务票证验证将失败 否则，该过程将正常进行到步骤2。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      CAS使用HTTP GET请求将HTTP请求参数 <code>pgtId</code> 和 <code>pgtIou</code> 传递到pgtUrl端点。 这些实体分别在第 <a href="#head3.3">3.3</a> 节和第 <a href="#head3.4">3.4</a>节中讨论。 如果代理回调URL指定了任何参数，则必须保留 CAS还必须通过验证来自GET请求的响应HTTP状态代码为 如果 代理服务无法通过身份验证，或者端点以不可接受的状态 代码进行响应，则代理身份验证务必失败，CAS务必以适当的错误代码 响应，如第 <a href="#head2.5.3">节2.5.3</a>。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      如果HTTP GET返回的200（OK）的HTTP状态代码，以CAS必须响应 的 <code>/ serviceValidate</code> （或 <code>/ proxyValidate</code>）与服务响应请求 （第 <a href="#head2.5.2">2.5.2</a>），其含有的Proxy-在 <code>&lt;cas:proxyGrantingTicket&gt;</code> 块中授予票证IOU（第 <a href="#head3.4">3.4</a>节） 如果HTTP GET返回 状态代码，则CAS必须响应 <code>/ serviceValidate</code> （或 <code>/ proxyValidate</code>）请求，并提供服务响应，其中 不得包含 <code>&lt;cas:proxyGrantingTicket&gt;</code> 块。 CAS可以遵循 <code>pgtUrl</code>发出的 重定向。 然而，识别回调URL 在验证时提供 <code>&lt;proxy&gt;</code> 块必须，这是相同的URL 最初传递到 <code>/ serviceValidate</code> （或 <code>/ proxyValidate</code>）作为 <code>pgtUrl</code> 参数。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      响应中接收到代理授权票证IOU，并且从代理回调收到代理授权票证和代理授权票证IOU ，将使用代理授权票证IOU关联 代理-授予带有验证响应的票证。 该服务将 然后使用代理授予票证用于获取代理票作为 部分所述 <a href="#head2.7">2.7</a>。
    </p>
  </li>
</ol>

<p spaces-before="0">



<a name="head2.5.5"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.5。 属性[CAS 3.0]</strong>
</h3>

<p spaces-before="0">
  [CAS 3.0]响应文件可能包括可选的 <cas:attributes> 用于其他身份验证和/或用户属性的元素。 有关详细信息，请参见 <a href="#head_appdx_a">附录A</a>
</p>

<p spaces-before="0">

<a name="head2.5.6"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.6。 / serviceValidate的URL示例</strong>
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/serviceValidate?service=http%3A%2F%2Fwww.example.org%2Fservice&ticket = ST-1856339-aA5Yuvrxzpv8Tau1cYQ7</code>
</p>

<p spaces-before="0">
  确保通过提供主要凭证来发行服务票证：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/serviceValidate?service=http%3A%2F%2Fwww.example.org%2Fservice&ticket = ST-1856339-aA5Yuvrxzpv8Tau1cYQ7&renew = true</code>
</p>

<p spaces-before="0">
  传递用于代理的回调URL：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/serviceValidate?service=http%3A%2F%2Fwww.example.org%2Fservice&ticket = ST-1856339-aA5Yuvrxzpv8Tau1cYQ7&pgtUrl = https：//www.example.org%2Fservice%2FproxyCallback</code>
</p>

<p spaces-before="0">



<a name="head2.5.7"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.7具有自定义属性的示例响应</strong>
</h3>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
    &lt;cas:authenticationSuccess&gt;
      &lt;cas:user&gt;用户名&lt;/cas:user&gt;
      &lt;cas:attributes&gt;
        &lt;cas:firstname&gt;John&lt;/cas:firstname&gt;
        &lt;cas:lastname&gt;Doe&lt;/cas:lastname&gt;
        &lt;cas:title&gt;先生&lt;/cas:title&gt;
        &lt;cas:email&gt;jdoe@example.org&lt;/cas:email&gt;
        &lt;cas:affiliation&gt;员工&lt;/cas:affiliation&gt;
        &lt;cas:affiliation&gt;教师&lt;/cas:affiliation&gt;
      &lt;/cas:attributes&gt;
      &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d ...&lt;/cas:proxyGrantingTicket&gt;
    &lt;/cas:authenticationSuccess&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  “ serviceResponse”：{
    “ authenticationSuccess”：{
      “ user”：“用户名”，
      “ proxyGrantingTicket”：“ PGTIOU-84678-8a9d ...”，
      “代理”：[“ https：// proxy1 / pgtUrl“，” https：// proxy2 / pgtUrl“]，
      ”属性“：{
        ” firstName“：” John“，
        ” affiliation“：[” staff“，” faculty“]，
        ” title“： “先生”，
        “电子邮件”：“ jdoe@example.orgmailto：jdoe@example.org”，
        “姓氏”：“ Doe”
      }
    }
  }
}
</code></pre>

<p spaces-before="0">

<a name="head2.6"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.6。 / proxyValidate [CAS 2.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/ proxyValidate</code> <code>/ serviceValidate</code> 和 相同的验证任务，另外还要验证代理票证。 <code>/ proxyValidate</code> 必须能够 验证服务票证和代理票证。 有关详细信息，请参见第 <a href="#head2.5.4">2.5.4</a>
  </p>
  
  <p spaces-before="0">



<a name="head2.6.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.1。 参数</strong>
</h3>

<p spaces-before="0">
  <code>/ proxyValidate</code> <code>/ serviceValidate</code>具有相同的参数要求。 参见 第 <a href="#head2.5.1">节2.5.1</a>。
</p>

<p spaces-before="0">



<a name="head2.6.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.2。 回复</strong>
</h3>

<p spaces-before="0">
  <code>/ proxyValidate</code> 中所描述将返回一个XML格式的CAS serviceResponse 在XML模式 <a href="#head_appdx_a">附录A</a>。 以下是示例响应：
</p>

<p spaces-before="0">
  票证验证成功的响应：
</p>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
    &lt;cas:authenticationSuccess&gt;
      &lt;cas:user&gt;用户名&lt;/cas:user&gt;
      &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d ...&lt;/cas:proxyGrantingTicket&gt;
      &lt;cas:proxies&gt;
        &lt;cas:proxy&gt;https：// proxy2 / pgtUrl&lt;/cas:proxy&gt;
        &lt;cas:proxy&gt;https：// proxy1 / pgtUrl&lt;/cas:proxy&gt;
      &lt;/cas:proxies&gt;
    &lt;/cas:authenticationSuccess&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  “ serviceResponse”：{
    “ authenticationSuccess”：{
      “ user”：“用户名”，
      “ proxyGrantingTicket”：“ PGTIOU-84678-8a9d ...”，
      “代理”：[“ https：// proxy1 / pgtUrl“，” https：// proxy2 / pgtUrl“]
    }
  }
}
</code></pre>


<blockquote spaces-before="0">
  <p spaces-before="0">
    注意：当验证通过多个代理进行时，遍历代理的顺序 必须反映在 <cas:proxies> 堵塞。 最近访问的代理必须是列出的第一个代理，并且在添加新代理后，所有其他代理都必须向下移 在 上面的例子中，服务通过识别 <a href="https://proxy1/pgtUrl" x-nc="1">的https：// PROXY1 / pgtUrl</a> 是 第一次访问，且该服务代理认证到服务 通过标识 <a href="https://proxy2/pgtUrl" x-nc="1">的https：// Proxy2发出/ pgtUrl</a>。
  </p>
</blockquote>

<p spaces-before="0">
  对票证验证失败的响应：
</p>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
      &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
         票证PT-1856376-1HMgO86Z2ZKeByc5XdYD无法识别
      &lt;/cas:authenticationFailure&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  “ serviceResponse”：{
    “ authenticationFailure”：{
      “ code”：“ INVALID_TICKET”，
      “ description”：“未识别票PT-1856339-aA5Yuvrxzpv8Tau1cYQ7”
    }
  }
}
</code></pre>

<p spaces-before="0">

<a name="head2.6.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.3错误代码</strong>
</h3>

<p spaces-before="0">
  参见第 <a href="#head2.5.3">节2.5.3</a>
</p>

<p spaces-before="0">



<a name="head2.6.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.4 / proxyValidate的URL示例</strong>
</h3>

<p spaces-before="0">
  <code>/ proxyValidate</code> <code>/ serviceValidate</code>相同的参数。 请参见第 <a href="#head2.5.5">2.5.5</a> 节中的使用示例，用“ proxyValidate”代替 “ serviceValidate”。
</p>

<p spaces-before="0">



<a name="head2.7"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.7。 /代理[CAS 2.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/ proxy</code> 为已获得代理授权 票证的服务提供代理票证，并将代理身份验证到后端服务。
  </p>
  
  <p spaces-before="0">


<a name="head2.7.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.1。 参数</strong>
</h3>

<p spaces-before="0">
  必须将以下HTTP请求参数指定为 <code>/ proxy</code>。 它们都区分大小写
</p>

<ul>
  <li>
    <code>pgt</code> [REQUIRED] 服务票证或代理票证验证期间获取的代理票证。
  </li>
  <li>
    <code>targetService</code> [REQUIRED] 后端服务的服务标识符。 请注意，并非所有后端服务都是Web服务，因此此服务标识符 并不总是URL。 但是，在验证代理票证 ，此处指定的服务标识符 必须与为 <code>/ proxyValidate</code> <code>service</code>
  </li>
</ul>

<p spaces-before="0">


<a name="head2.7.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.2。 回复</strong>
</h3>

<p spaces-before="0">
  <code>/ proxy</code> 将返回XML格式的CAS serviceResponse文档，如 <a href="#head_appdx_a">附录A</a> 模式所述。 以下是示例响应：
</p>

<p spaces-before="0">
  对请求成功的响应：
</p>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
      &lt;cas:proxySuccess&gt;
          &lt;cas:proxyTicket&gt;PT-1856392-b98xZrQN4p90ASrw96c8&lt;/cas:proxyTicket&gt;
      &lt;/cas:proxySuccess&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<p spaces-before="0">
  对请求失败的响应：
</p>

<pre><code class="xml">&lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
      &lt;cas:proxyFailure code="INVALID_REQUEST"&gt;
          'PGT'和'targetService'参数都需要
      &lt;/cas:proxyFailure&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  “ serviceResponse”：{
    “ authenticationFailure”：{
      “ code”：“ INVALID_REQUEST”，
      “ description”：“'pgt'和'targetService'参数都是必需的”
    }
  }
}
</code></pre>

<p spaces-before="0">

<a name="head2.7.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.3。 错误代码</strong>
</h3>

<p spaces-before="0">
  以下值可以用作认证 故障响应 <code>代码</code> 服务器必须实现的最小错误代码集。 实现可能包括其他实现。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>INVALID_REQUEST</code> 并非所有必需的请求参数都存在
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>UNAUTHORIZED_SERVICE</code> 未授权服务执行代理请求
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INTERNAL_ERROR</code> 票证验证期间发生内部错误
    </p>
  </li>
</ul>

<p spaces-before="0">
  对于所有错误代码，建议CAS提供更详细的消息 作为正文。 <cas:authenticationFailure> XML响应的块。
</p>

<p spaces-before="0">



<a name="head2.7.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.4。 / proxy的网址示例</strong>
</h3>

<p spaces-before="0">
  简单的代理请求：
</p>

<p spaces-before="0">
  <code>https：// server / cas / proxy？targetService = http%3A%2F%2Fwww.service.com&pgt = PGT-490649-W81Y9Sa2vTM7hda7xNTkezTbVge4CUsybAr</code>
</p>

<p spaces-before="0">


<a name="head3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.4服务票证生命周期的影响</strong>
</h3>

<p spaces-before="0">
  CAS Server实现可以在生成代理票证时更新父服务票证（ST）的生存期。
</p>

<p spaces-before="0">
  <strong x-id="1">2.8。 / p3 / serviceValidate [CAS 3.0]</strong>
</p>
<hr />

<p spaces-before="0">
  <code>/ p3 / serviceValidate</code> <code>/ serviceValidate</code> 和 相同的验证任务，并另外在CAS响应中返回用户属性。 有关详细信息，请参见 第 <a href="#head2.5">2.5</a> 节和第 <a href="#head2.5.7">2.5.7</a>
</p>

<p spaces-before="0">

<a name="head2.8.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.8.1。 参数</strong>
</h3>

<p spaces-before="0">
  <code>/ p3 / serviceValidate</code> <code>/ serviceValidate</code>具有相同的参数要求。 参见 第 <a href="#head2.5.1">节2.5.1</a>。
</p>

<p spaces-before="0">
  <strong x-id="1">2.9。 / p3 / proxyValidate [CAS 3.0]</strong>
</p>
<hr />

<p spaces-before="0">
  <code>/ p3 / proxyValidate</code> <code>/ p3 / serviceValidate</code> 和 相同的验证任务，另外还要验证代理票证。 参见第 <a href="#head2.5">2.8</a>节。
</p>

<p spaces-before="0">

<a name="head2.8.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.9.1。 参数</strong>
</h3>

<p spaces-before="0">
  <code>/ p3 / proxyValidate</code> <code>/ p3 / serviceValidate</code>具有相同的参数要求。 参见 第 <a href="#head2.8.1">节2.8.1</a>。
</p>
<h1 spaces-before="0">
  <strong x-id="1">3。 CAS实体</strong>
</h1>

<p spaces-before="0">

<a name="head3.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.1。 服务票</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    服务票证是一个不透明的字符串，客户端将其用作凭证 来获取对服务的访问权。 服务票据从CAS一个时获得的 凭证的客户端的呈现和服务标识符，以 <code>/登录</code> 为 部分所述 <a href="#head2.2">2.2</a>。
  </p>
  
  <p spaces-before="0">

<a name="head3.1.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.1.1。 服务票属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      服务凭单仅对生成时 到 <code>/登录</code> 的服务标识符有效。 服务标识符不应为服务票证的
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务票仅对一次票确认尝试有效。 无论验证是否成功，CAS都必须随后使票证无效， 导致该票证的所有未来验证尝试均失败。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      CAS应当在未验证的服务票证发出后 如果服务出示了一张过期的服务票证以进行 验证，则CAS必须以验证失败响应作为响应。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议验证响应包括描述性消息 说明验证失败的原因。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      到期之前有效的持续时间不超过5分钟。 本地安全和CAS使用注意事项 可以确定未经验证的服务票证的最佳使用寿命。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务票证必须包含足够的安全随机数据，以便票证为 不可猜测。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务票必须以 <code>ST-</code>字符开头。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务必须能够接受最长32个字符的服务票证。 长度为256个字符的服务票证。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.2"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.2。 代理票</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    代理票证是一个不透明的字符串，服务将其用作凭证来代表客户端获得对后端服务的 代理票获得 从CAS时有效的代理授予票证的服务的介绍（第 <a href="#head3.3">3.3</a>），和用于向所述后端服务的服务标识 它连接。
  </p>
  
  <p spaces-before="0">



<a name="head3.2.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.2.1。 代理票证属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      代理票证仅在生成时 <code>/ proxy</code> 的服务标识符有效。 服务标识符不应该是 代理票证的一部分。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理凭单仅对一次凭单验证尝试有效。 无论验证是否成功，CAS都必须随后使 无效，导致同一票证的所有将来的验证尝试均失败
    </p>
  </li>
  <li>
    <p spaces-before="0">
      CAS应该在未验证的代理票证发布后的合理时间内 过期。 如果服务提供了一个 过期的代理票证进行验证，则CAS必须以验证失败响应作为响应。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议验证响应包括描述性消息 说明验证失败的原因。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      过期之前有效的持续时间不超过5分钟。 本地安全性和CAS使用注意事项 可以确定未验证的代理票证的最佳寿命。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理票必须包含足够的安全随机数据，以使票不能
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代用券应以 <code>PT-</code>字符开头。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      后端服务必须能够接受最多32个字符（长度
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议后端服务支持最多256 字符的代理票证。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.3"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.3。 授权代理票</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    代理授予票证（PGT）是一个不透明的字符串，服务使用该字符串获取 代理票证，以代表客户端获取对后端服务的访问。 或代理票证后，可以从CAS获得代理票证。 授予代理票证的发行在第 <a href="#head2.5.4">2.5.4</a>节中有完整描述。
  </p>
  
  <p spaces-before="0">



<a name="head3.3.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.3.1。 代理授权票证属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      服务可以使用授权代理票证来获取多个代理 票证。 授予代理的票证不是一次性使用的票证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      的客户端注销CAS时，必须授予代理授权的凭单。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予代理授权的票证必须包含足够的安全随机数据，以使在合理的时间内通过蛮力 攻击
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予代理的票证应以字符 <code>PGT-</code>开头。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务必须能够处理长达64个 字符的授权代理票证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议服务支持最多 256个字符的授权代理票证。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.4"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.4。 代理授权票IOU</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    代理授权凭单IOU是一个不透明字符串，它放置在由 <code>/ serviceValidate</code> 和 <code>/ proxyValidate</code> ，用于将服务 凭单或代理凭单验证与特定的代理授权凭单相关联。 有关此过程的完整说明，请参见 第 <a href="#head2.5.4">节2.5.4</a>
  </p>
  
  <p spaces-before="0">



<a name="head3.4.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.4.1。 授予代理票证的IOU属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      授予代理票证的IOU不应包含对其 相关代理票证的任何引用。 给定一个特定的PGTIOU， 合理的时间内通过算法方法导出其对应的PGT的可能性
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予代理的票证IOU必须包含足够的安全随机数据，以确保在合理的时间内通过蛮力 攻击
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予代理凭单的IOU应该以字符 <code>PGTIOU-</code>开头。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务必须能够处理最多64个字符的PGTIOU。 支持服务的PGTIOU的长度
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.5"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.5。 登录票</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    登录票证是一个 <em x-id="3">可选</em> 字符串，可以由 <code>/ login</code> 作为凭据请求者 并作为用户名/密码 身份验证的凭据接受者传递给 <code>/ login</code> 其目的是防止由于Web浏览器中的
  </p>
  
  <p spaces-before="0">


<a name="head3.5.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.5.1。 登录票证属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      <code>/登录</code> 发出的登录票必须是概率唯一的。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      登录票仅对一次身份验证尝试有效。 无论身份验证成功与否（ ，CAS都必须使登录 凭单无效，从而导致以后所有以该登录凭单的
    </p>
  </li>
  <li>
    <p spaces-before="0">
      登录凭单应以字符 <code>LT-</code>开头。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head3.6"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.6。 购票饼干</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    授予票证的cookie是CAS在 建立单一登录会话<a href="#5">5</a> 此cookie会为 的登录状态，并且在有效期间，客户端可以代替 主要凭据将其提供给CAS。 服务可以在登录通过停用单个的 <code>续订</code> 参数在章节描述 <a href="#head2.1.1">2.1.1</a>， <a href="#head2.4.1">2.4.1</a>， 和 <a href="#head2.5.1">2.5.1</a>。
  </p>
  
  <p spaces-before="0">



<a name="head3.6.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.6.1。 授予票证的Cookie属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      如果未激活相应TGT的长期支持（<a href="#head4.1.1">4.1.1</a>） ，则票证cookie应当设置为在客户端的
    </p>
  </li>
  <li>
    <p spaces-before="0">
      CAS应将cookie路径设置为尽可能严格。 例如， ，如果CAS服务器的路径/ CAS下成立，cookie路径应设置 至/ CAS。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      票证cookie的值应包含足够的安全随机数据 以使票证cookie在合理的时间段内不可猜测。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予票务的cookie的名称应以字符 <code>TGC-</code>开头。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予票证的cookie的值应遵循与授予票证的 票相同的规则。 通常，授予票证的cookie的值可以包含授予票证的 票证本身，作为已认证的单点登录会话的表示。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head3.7"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.7。 票证和授予票证的Cookie字符集</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    除上述要求外，所有CAS车票和的值 票证授予饼干必须从该组只包含字符 <code>{A-Z, a-z, 0-9}</code>， 和连字符 <code>-</code>。
  </p>
  
  <p spaces-before="0">


<a name="head3.8"></p> 
    
    <p spaces-before="0">
      <strong x-id="1">3.8。 购票票</strong>
    </p>
<hr />
    
    <p spaces-before="0">
      票证授予票证（TGT）是由CAS <code>/登录</code>时通过成功的身份验证事件后发出。 该票证可能与票证授予cookie绑定在一起，该cookie代表 状态，具有有效期，并充当 发行服务票证，代理授予 票证的基础和基线。
    </p>
    
    <p spaces-before="0">

<a name="head3.8.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.8.1。 授予机票的机票属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      服务可以使用授予票证的票证获取多个服务 票证。 授予票证的票证不是一次性使用的票证，并且与有效期和到期策略
    </p>
  </li>
  <li>
    <p spaces-before="0">
      当正在管理 的客户端注销CAS时，授予票证的票证必须过期。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予票证的票证必须包含足够的安全随机数据，以确保在合理的时间内通过蛮力 攻击
    </p>
  </li>
  <li>
    <p spaces-before="0">
      授予票务的票证应以字符 <code>TGT-</code>。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议将票证授予票证与其他外部资源共享 漏洞与票证授予cookie 绑定在一起并表示身份验证会话，以将安全性1漏洞最小化。
    </p>
  </li>
</ul>

<p spaces-before="0">


<a name="head4"></p>
<h1 spaces-before="0">
  <strong x-id="1">4， 可选功能</strong>
</h1>

<p spaces-before="0">

<a name="head4.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">4.1长期门票-记住我[CAS 3.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    CAS Server可能支持长期票证授予票证（称为 “记住我”功能）。 如果此功能是由CAS服务器支持，它 可以作为执行重复性，非交互式重新登录到CAS服务器 只要在CAS服务器的长期票据发放票据未过期 和浏览器TGC Cookie有效。
  </p>
  
  <p spaces-before="0">


<a name="head4.1.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.1.1启用“记住我”（登录页面）</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      CAS服务器必须在登录页面上提供一个复选框，以允许使用“记住我 功能。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      默认情况下，必须取消选中该复选框。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      是否启用“记住我”作为登录名，必须由用户选择。 参见第 <a href="#head2.2.2">节2.2.2</a>。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head4.1.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.1.2安全隐患</strong>
</h3>

<p spaces-before="0">
  启用“记住我”可能会带来安全隐患。 由于CAS身份验证 存在有效的长期TGT票证并且浏览器 呈现的CAS cookie有效时，用户不会以交互方式登录，因此必须对CAS格外小心客户端以正确处理“记住我” 登录。 它必须是CAS客户负责决定 是否及何时记住，我CAS登录可能会被特殊处理。 参见 <a href="#head4.1.3">4.1.3</a>。
</p>

<p spaces-before="0">



<a name="head4.1.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.1.3 CAS验证响应属性</strong>
</h3>

<p spaces-before="0">
  由于只有CAS客户端必须决定如何处理“记住我”登录名（请参阅 <a href="#head4.2.1">4.2.1</a>），因此CAS服务器必须提供有关 记住我登录到CAS客户端的信息。 必须由所有被提供此信息 由CAS服务器支持的票验证方法（见第 <a href="#head2.5">2.5</a>， <a href="#head2.6">2.6</a> 和 <a href="#head2.8">2.8</a>）在这种情况下。
</p>

<ul>
  <li>
    <p spaces-before="0">
      在serviceValidate XML响应中（请参阅 <a href="#head_appdx_a">附录A</a>）， <code>longTermAuthenticationRequestTokenUsed</code> 属性 记住我登录。 另外， <code>isFromNewLogin</code> 属性可以用来决定它是否具有安全性 含义。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      在SAML验证响应中，“记住我”必须由 <code>longTermAuthenticationRequestTokenUsed</code> 属性指示。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head4.1.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.1.4 CAS客户要求</strong>
</h3>

<p spaces-before="0">
  如果CAS客户端需要处理特殊的“记住我”登录名（例如，在记住的登录名上拒绝访问CAS客户端应用程序的 客户端不得使用 <code>/ validate</code> CAS验证URL，因为此URL 在验证响应文档中不支持CAS属性。
</p>


<h3 spaces-before="0">
  <strong x-id="1">4.1.5长期票证cookie属性</strong>
</h3>

<p spaces-before="0">
  当CAS服务器创建了一个长期TGT时，按 <a href="#head3.6.1">3.6.1</a> 一定不会在客户端的浏览器会话结束时到期。 取而代之的是，票证授予cookie将在定义的长期TGT票证生命周期内到期。
</p>

<p spaces-before="0">
  长期票证授予票证的生命周期值定义取决于CAS Server实施者。 长期票证授予票证的寿命不得超过3个月。
</p>

<p spaces-before="0">


<a name="head4.2"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">4.2 / samlValidate [CAS 3.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/ samlValidate</code> 通过HTTP POST提供 文档检查服务票证的有效性。 必须返回SAML（安全访问标记语言）[<a href="#7">7</a>] 1.1 响应文档。 这允许释放 信息（属性）。 安全断言 标记语言（SAML）描述了一种文档和协议框架，通过该文档和协议框架， 交换 安全断言（例如有关身份验证先验行为的断言）。
  </p>
  
  <p spaces-before="0">



<a name="head4.2.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.1参数</strong>
</h3>

<p spaces-before="0">
  必须将以下HTTP请求参数指定为 <code>/ samlValidate</code>。 它们 都区分大小写。
</p>

<ul>
  <li>
    <code>目标</code> [REQUIRED] 后端服务的URL编码的服务标识符。 <a href="#4">[4]</a>2.2节中所述，作为HTTP请求参数，此URL值必须为URL编码的 。 此处指定的服务标识符 必须与提供给 <code>/ login</code><code>service</code> 参数匹配。 参见 第 <a href="#head2.1.1">章2.1.1</a>。 <code>TARGET</code> 服务将使用HTTPS。 SAML 属性不得发布到非SSL站点。
  </li>
</ul>

<p spaces-before="0">



<a name="head4.2.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.2 HTTP请求方法和主体</strong>
</h3>

<p spaces-before="0">
  对/ samlValidate的请求必须是HTTP POST请求。 请求主体必须是文档类型为“ text / xml”
</p>

<p spaces-before="0">



<a name="head4.2.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.3 SAML请求值</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      <code>RequestID</code> [REQUIRED] 请求的唯一标识符
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>IssueInstant</code> [REQUIRED] 请求的时间戳
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>samlp：AssertionArtifact</code> [REQUIRED] 在登录时 参数获得的有效CAS服务凭单。 参见第 <a href="#head2.2.4">2.2.4节</a>。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head4.2.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.4 / samlValidate POST请求的示例</strong>
</h3>

<pre><code class="bash">POST / cas / samlValidate？TARGET =
主机：cas.example.com
内容长度：491
内容类型：text / xml
</code></pre>

<pre><code class="xml">&lt;SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"&gt;
    &lt;SOAP-ENV:Header/&gt;
    &lt;SOAP-ENV:Body&gt;
        &lt;samlp:Request xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" MajorVersion="1" MinorVersion="1" RequestID="_192.168.16.51.1024506224022" IssueInstant="2002-06-19T17:03:44.022Z"&gt;
            &lt;samlp:AssertionArtifact&gt;ST-1-u4hrm3td92cLxpCvrjylcas.example.com&lt;/samlp:AssertionArtifact&gt;
        &lt;/samlp:Request&gt;
    &lt;/SOAP-ENV:Body&gt;
&lt;/SOAP-ENV:Envelope&gt;
</code></pre>

<p spaces-before="0">


<a name="head4.2.5"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.5 SAML响应</strong>
</h3>

<p spaces-before="0">
  CAS服务器对 <code>/ samlValidate</code> 请求的响应。 必须是SAML 1.1响应。
</p>

<p spaces-before="0">
  示例SAML 1.1验证响应：
</p>

<pre><code class="xml">
&lt;SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"&gt;
  &lt;SOAP-ENV:Header /&gt;
  &lt;SOAP-ENV:Body&gt;
    &lt;Response xmlns="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion"
    xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" IssueInstant="2008-12-10T14:12:14.817Z"
    MajorVersion="1" MinorVersion="1" Recipient="https://eiger.iad.vt.edu/dat/home.do"
    ResponseID="_5c94b5431c540365e5a70b2874b75996"&gt;
      &lt;Status&gt;
        &lt;StatusCode Value="samlp:Success"&gt;
        &lt;/StatusCode&gt;
      &lt;/Status&gt;
      &lt;Assertion xmlns="urn:oasis:names:tc:SAML:1.0:assertion" AssertionID="_e5c23ff7a3889e12fa01802a47331653"
      IssueInstant="2008-12-10T14:12:14.817Z" Issuer="localhost" MajorVersion="1"
      MinorVersion="1"&gt;
        &lt;Conditions NotBefore="2008-12-10T14:12:14.817Z" NotOnOrAfter="2008-12-10T14:12:44.817Z"&gt;
          &lt;AudienceRestrictionCondition&gt;
            &lt;Audience&gt;
              https://some-service.example.com/app/
            &lt;/Audience&gt;
          &lt;/AudienceRestrictionCondition&gt;
        &lt;/Conditions&gt;
        &lt;AttributeStatement&gt;
          &lt;Subject&gt;
            &lt;NameIdentifier&gt;johnq&lt;/NameIdentifier&gt;
            &lt;SubjectConfirmation&gt;
              &lt;ConfirmationMethod&gt;
                urn:oasis:names:tc:SAML:1.0:cm:artifact
              &lt;/ConfirmationMethod&gt;
            &lt;/SubjectConfirmation&gt;
          &lt;/Subject&gt;
          &lt;Attribute AttributeName="uid" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;12345&lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
          &lt;Attribute AttributeName="groupMembership" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;
              uugid=middleware.staff,ou=Groups,dc=vt,dc=edu
            &lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
          &lt;Attribute AttributeName="eduPersonAffiliation" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;staff&lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
          &lt;Attribute AttributeName="accountState" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;ACTIVE&lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
        &lt;/AttributeStatement&gt;
        &lt;AuthenticationStatement AuthenticationInstant="2008-12-10T14:12:14.741Z"
        AuthenticationMethod="urn:oasis:names:tc:SAML:1.0:am:password"&gt;
          &lt;Subject&gt;
            &lt;NameIdentifier&gt;johnq&lt;/NameIdentifier&gt;
            &lt;SubjectConfirmation&gt;
              &lt;ConfirmationMethod&gt;
                urn:oasis:names:tc:SAML:1.0:cm:artifact
              &lt;/ConfirmationMethod&gt;
            &lt;/SubjectConfirmation&gt;
          &lt;/Subject&gt;
        &lt;/AuthenticationStatement&gt;
      &lt;/Assertion&gt;
    &lt;/Response&gt;
  &lt;/SOAP-ENV:Body&gt;
&lt;/SOAP-ENV:Envelope&gt;
</code></pre>

<p spaces-before="0">


<a name="head4.2.5.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.5.1 SAML CAS响应属性</strong>
</h3>

<p spaces-before="0">
  SAML响应中可能会提供以下其他属性：
</p>

<ul>
  <li>
    <code>longTermAuthenticationRequestTokenUsed</code> -如果长期票证授予 机票（记住-ME）由CAS服务器支持的（见第 <a href="#head4.1">4.1</a>）中， SAML响应必须包含该属性，以指示记住登录 到CAS客户端。
  </li>
</ul>

<p spaces-before="0">


<a name="head_appdx_a"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录A：CAS响应XML模式</strong>
</h1>

<pre><code class="xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:cas="http://www.yale.edu/tp/cas"
           targetNamespace="http://www.yale.edu/tp/cas"
           elementFormDefault="qualified"&gt;
  &lt;xs:annotation&gt;
    &lt;xs:documentation&gt;
      The following is the schema for the Central Authentication Service (CAS) version 3.0 protocol response.&lt;br /&gt;
      This covers the responses for the following endpoints: /serviceValidate, /proxyValidate, /p3/serviceValidate, /p3/proxyValidate, /proxy.&lt;br /&gt;
      This specification is subject to change.&lt;br /&gt;

      Schema version: 3.0.3&lt;br /&gt;

      History:&lt;br /&gt;
      3.0   initial version for CAS 3.0 protocol spec &lt;br /&gt;
      3.0.3 fixed attributes memberOf / xs:any clash, added documentation.&lt;br /&gt;
    &lt;/xs:documentation&gt;
  &lt;/xs:annotation&gt;
  &lt;xs:element name="serviceResponse" type="cas:ServiceResponseType"&gt;
    &lt;xs:annotation&gt;
      &lt;xs:documentation&gt;The service Response.&lt;/xs:documentation&gt;
    &lt;/xs:annotation&gt;
  &lt;/xs:element&gt;
  &lt;xs:complexType name="ServiceResponseType"&gt;
    &lt;xs:choice&gt;
      &lt;xs:element name="authenticationSuccess" type="cas:AuthenticationSuccessType"/&gt;
      &lt;xs:element name="authenticationFailure" type="cas:AuthenticationFailureType"/&gt;
      &lt;xs:element name="proxySuccess" type="cas:ProxySuccessType"/&gt;
      &lt;xs:element name="proxyFailure" type="cas:ProxyFailureType"/&gt;
    &lt;/xs:choice&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="AuthenticationSuccessType"&gt;
    &lt;xs:sequence&gt;
      &lt;xs:element name="user" type="xs:string"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;The username which authenticated successfully.&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="attributes" type="cas:AttributesType" minOccurs="0"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;Optional attributes.&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="proxyGrantingTicket" type="xs:string" minOccurs="0"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;Optional PGT.&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="proxies" type="cas:ProxiesType" minOccurs="0"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;Optional type of proxies.&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
    &lt;/xs:sequence&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="ProxiesType"&gt;
    &lt;xs:sequence&gt;
      &lt;xs:element name="proxy" type="xs:string" maxOccurs="unbounded"/&gt;
    &lt;/xs:sequence&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="AuthenticationFailureType"&gt;
    &lt;xs:simpleContent&gt;
      &lt;xs:extension base="xs:string"&gt;
        &lt;xs:attribute name="code" type="xs:string" use="required"&gt;
          &lt;xs:annotation&gt;
            &lt;xs:documentation&gt;The error code on authentication failure.&lt;/xs:documentation&gt;
          &lt;/xs:annotation&gt;
        &lt;/xs:attribute&gt;
      &lt;/xs:extension&gt;
    &lt;/xs:simpleContent&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="ProxySuccessType"&gt;
    &lt;xs:sequence&gt;
      &lt;xs:element name="proxyTicket" type="xs:string"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;The PT.&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
    &lt;/xs:sequence&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="ProxyFailureType"&gt;
    &lt;xs:simpleContent&gt;
      &lt;xs:extension base="xs:string"&gt;
        &lt;xs:attribute name="code" type="xs:string" use="required"&gt;
          &lt;xs:annotation&gt;
            &lt;xs:documentation&gt;The error code on proxy failure.&lt;/xs:documentation&gt;
          &lt;/xs:annotation&gt;
        &lt;/xs:attribute&gt;
      &lt;/xs:extension&gt;
    &lt;/xs:simpleContent&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="AttributesType"&gt;
    &lt;xs:sequence&gt;
      &lt;xs:element name="authenticationDate" type="xs:dateTime" minOccurs="1" maxOccurs="1"/&gt;
      &lt;xs:element name="longTermAuthenticationRequestTokenUsed" type="xs:boolean" minOccurs="1" maxOccurs="1"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;true if a long-term (Remember-Me) token was used&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="isFromNewLogin" type="xs:boolean" minOccurs="1" maxOccurs="1"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;true if this was from a new, interactive login. 如果登录来自非交互式登录（例如，Remember-Me），则此值为false或可以省略。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:any minOccurs="0" maxOccurs="unbounded" processContents="lax"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;任何用户特定的属性元素。 可以包含memberOf或任何其他元素。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:any&gt;
    &lt;/xs:sequence&gt;
  &lt;/xs:complexType&gt;
&lt;/xs:schema&gt;
</code></pre>

<blockquote spaces-before="0">
  <p spaces-before="0">
    注意：由于用户属性可以由CAS Server实现者扩展（请参阅 <xs:any> 模式定义），建议使用以下格式
  </p>
</blockquote>

<pre><code class="xml">&lt;cas:attributes&gt;
...
    &lt;cas:[attribute-name]&gt;值&lt;/cas:[attribute-name]&gt;
&lt;/cas:attributes&gt;
</code></pre>

<blockquote spaces-before="0">
  <p spaces-before="0">
    具有自定义属性的示例响应：
  </p>
</blockquote>

<pre><code class="xml">&lt;cas:attributes&gt;
    &lt;cas:authenticationDate&gt;2015-11-12T09：30：10Z&lt;/cas:authenticationDate&gt;
    &lt;cas:longTermAuthenticationRequestTokenUsed&gt;是&lt;/cas:longTermAuthenticationRequestTokenUsed&gt;
    &lt;cas:isFromNewLogin&gt;是&lt;/cas:isFromNewLogin&gt;
    &lt;cas:myAttribute&gt;myValue&lt;/cas:myAttribute&gt;
&lt;/cas:attributes&gt;
</code></pre>

<p spaces-before="0">

<a name="head_appdx_b"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录B：安全重定向</strong>
</h1>

<p spaces-before="0">
  成功登录后，必须谨慎处理将客户端从CAS安全重定向到其最终 目的地的操作。 在大多数情况下，客户端已通过POST请求向CAS服务器 根据此规范， CAS服务器必须使用GET请求将用户转发到应用程序。
</p>

<p spaces-before="0">
  HTTP / 1.1 RFC [<a href="#3">3</a>]提供的响应代码为303：请参见“其他”，其中 提供了所需的行为：通过POST 请求接收数据的脚本可以通过303重定向将浏览器转发到另一个浏览器。通过GET请求获得的URL 但是，并非所有浏览器都正确
</p>

<p spaces-before="0">
  因此，推荐的重定向方法是JavaScript。 包含 <code>window.location.href</code> 页面按以下方式可以正常运行：
</p>

<pre><code class="html"> &lt;html&gt;
    &lt;head&gt;
        &lt;title&gt;Yale中央身份验证服务&lt;/title&gt;
        &lt;script&gt;
            window.location.href =“ https：//portal.yale.edu/Login？ticket = ST -...”
mce_href =“ https：// portal。 yale.edu/Login?ticket=ST -...“;`
       &lt;/script&gt;
    &lt;/head&gt;
    &lt;body&gt;
        &lt;noscript&gt;
            &lt;p&gt;CAS登录成功。&lt;/p&gt;
            &lt;p&gt;  单击 &lt;a xhref="https://portal.yale.edu/Login?ticket=ST-..."
mce_href="https://portal.yale.edu/Login?ticket=ST-..."&gt;这里&lt;/a&gt;
            以访问您请求的服务。&lt;br /&gt;  &lt;/p&gt;
        &lt;/noscript&gt;
    &lt;/body&gt;
 &lt;/html&gt;
</code></pre>

<p spaces-before="0">
  此外，CAS应该通过设置所有 个与缓存相关的标头来禁用浏览器缓存：
</p>

<ul>
  <li>
    <p spaces-before="0">
      语用说明：无快取
    </p>
  </li>
  <li>
    <p spaces-before="0">
      缓存控制：无存储
    </p>
  </li>
  <li>
    <p spaces-before="0">
      过期：[RFC 1123 [<a href="#6">6</a>]日期等于或早于现在]
    </p>
  </li>
</ul>

<p spaces-before="0">
  登录票证的引入消除了CAS接受由浏览器缓存和重播的 但是， 包含一个错误，在该错误中，通过使用“后退 按钮，Safari可能被强制向要尝试访问 CAS可以通过不阻止这种行为 自动重定向，如果它检测到远程浏览器是这一个 Safari浏览器的早期版本。 而是，CAS应显示一个页面，指出登录 成功，并提供指向所请求服务的链接。 客户端必须为 然后手动单击以继续。
</p>

<p spaces-before="0">



<a name="head_appdx_c"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录C：注销XML文档</strong>
</h1>

<p spaces-before="0">
  当CAS服务器支持SLO时，它将回调到系统中注册 SAML注销请求XML文档发送POST请求：
</p>

<pre><code class="xml">  &lt;samlp:LogoutRequest xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol"
     ID="[RANDOM ID]" Version="2.0" IssueInstant="[CURRENT DATE/TIME]"&gt;
    &lt;saml:NameID xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion"&gt;
      @ NOT_USED @
    &lt;/saml:NameID&gt;
    &lt;samlp:SessionIndex&gt;[会话标识符]&lt;/samlp:SessionIndex&gt;
  &lt;/samlp:LogoutRequest&gt;`
</code></pre>

<p spaces-before="0">


<a name="head_appdx_d"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录D：参考</strong>
</h1>

<p spaces-before="0">
  <a name="1">[1] Bradner，S.，“用于RFC中以指示要求 级别的 <a href="http://www.ietf.org/rfc/rfc2119.txt">RFC 2119</a>，哈佛大学，1997年3月 </p> 
  
  <p spaces-before="0">
    <a name="2">[2] 伯纳斯李，T.，调遣，R.，Frystyk，H。， “超文本传输 协议- HTTP / 1.0”， <a href="http://www.ietf.org/rfc/rfc1945.txt">RFC 1945</a>， MIT / LCS，UC Irvine的MIT / LCS，1996年5月。</p> 
    
    <p spaces-before="0">
      <a name="3">[3] Fielding，R.，Gettys，J.，Mogul，J.，Frystyk，H.，Masinter，L.， Leach，P.，Berners-Lee，T.，“超文本传输协议-HTTP / 1.1”， <a href="http://www.ietf.org/rfc/rfc2068.txt">RFC 2068</a>，UC Irvine，Compaq / W3C，Compaq， W3C / MIT，Xerox，Microsoft，W3C / MIT，1999年6月。</p> 
      
      <p spaces-before="0">
        <a name="4">[4] 伯纳斯李，T.，Masinter，L.，和MaCahill，M.， “统一 资源定位器（URL）”， <a href="http://www.ietf.org/rfc/rfc1738.txt">RFC 1738</a>， CERN，施乐公司，明尼苏达州，1994年12月的大学。</p> 
        
        <p spaces-before="0">
          <a name="5">[5] Kristol，D.，Montulli，L。，“ HTTP状态管理机制”， <a href="http://www.ietf.org/rfc/rfc2965.txt">RFC 2965</a>，Bell实验室/ Lucent Technologies，Epinions.com，Inc.，2000年10月。</p> 
          
          <p spaces-before="0">
            <a name="6">[6] R. Braden，“ Internet主机的要求-应用程序和 支持”， <a href="http://www.ietf.org/rfc/rfc1123.txt">RFC 1123</a>，Internet 工程任务组，1989年10月。</p> 
            
            <p spaces-before="0">
              <a name="7">[7] OASIS SAML 1.1标准，saml.xml.org，2009年12月。</p> 
              
              <p spaces-before="0">
                <a name="8">[8] <a href="http://www.Apereo.org/cas">Apereo CAS Server</a> 参考 实施</p> 
                
                <p spaces-before="0">


<a name="head_appdx_e"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录E：CAS许可证</strong>
</h1>

<p spaces-before="0">
  根据一项或多项贡献者许可协议授权给Apereo。 版权拥有权的其他信息，请参见随本作品分发 版本2.0（“许可证”）向您授予此文件的许可； 许可，否则您不得使用此文件。  您可以在以下位置获得许可证的副本：
</p>

<p spaces-before="0">
  http://www.apache.org/licenses/LICENSE-2.0
</p>

<p spaces-before="0">
  除非适用法律要求或书面同意，软件分发 的许可证上的分布“AS IS”的基础，没有担保或 任何形式的条件，明示或暗示的保证。  有关许可下的 特定语言的管理权限和限制，请参阅许可。
</p>

<p spaces-before="0">


<a name="head_appdx_f"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录F：YALE许可证</strong>
</h1>

<p spaces-before="0">
  耶鲁大学（c）2000-2005版权所有。
</p>

<p spaces-before="0">
  本软件按“原样”提供，不提供任何明示或暗示的担保，其中 包括但不限于）适销性的默示担保和 特定目的的适用性。 在任何情况下 耶鲁大学或其员工对任何直接的，间接的，偶然的， 特殊，或继发性损害（包括，但不限于，THE 获得替代品或服务的成本;使用损失，数据或 利润；或业务中断），无论是基于合同，严格责任还是侵权（包括过失 或其他原因）造成的 建议提前进行此类损坏。
</p>

<p spaces-before="0">
  再分配和使用该软件源代码或二进制形式的，有或 ，而不修改，都允许，只要满足下列条件， 满足：
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      任何再分配必须包括上述版权声明和免责声明 和任何相关文档在此条件列表以及，如果可行的话， 再分布的软件。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      任何再分配必须包括确认，“本产品包含 软件由耶鲁大学开发的，”任何相关文件中，如果 是可行的，在引入的软件。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      名称“ Yale”和“ Yale University”不得用于认可或 推广从该软件派生的产品。
    </p>
  </li>
</ol>

<p spaces-before="0">



<a name="head_appdx_g"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录G：对本文档的更改</strong>
</h1>

<p spaces-before="0">
  2005年5月4日：v1.0-CAS 1.0和CAS 2.0的初始版本，版权所有©2005，耶鲁大学
</p>

<p spaces-before="0">
  2012年3月2日：v1.0.1-修复了“ noscropt”拼写错误。 apetro per amazurek以 归功于ASU的Faraz Khan抓住了错字。
</p>

<p spaces-before="0">
  2013年4月：v3.0-CAS 3.0协议，Apereo版权，Apache许可证2.0
</p>

<p spaces-before="0">
  2014年1月：v3.0.1-属性出现
</p>

<p spaces-before="0">
  2015年9月：v3.0.2-格式参数
</p>

<p spaces-before="0">
  2017年12月：v3.0.3-修复了ServiceValidate XSD模式
</p>
