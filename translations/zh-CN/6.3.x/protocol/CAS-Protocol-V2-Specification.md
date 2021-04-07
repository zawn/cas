---
layout: 默认
title: CAS-CAS协议2.0规范
category: 通讯协定
---

<a name="headTop"></p>
<h1 spaces-before="0">
  <em x-id="3">CAS协议2.0规范</em>
</h1>

<p spaces-before="0">
  作者：Drew Mazurek 撰稿人： Susan Bramhall Howard Gilbert Andy Newman Andrew Petro 版本：1.0
</p>

<p spaces-before="0">
  发行日期：2005年5月4日 版权所有©2005，耶鲁大学
</p>

<h1 spaces-before="0">
  1。 介绍
</h1>

<p spaces-before="0">
  这是CAS 1.0和2.0协议的正式规范。 它可能随时更改。
</p>

<h2 spaces-before="0">
  1.1。 约定 & 定义
</h2>

<p spaces-before="0">
  本文档中的关键字“必须”，“不得”，“必须”，“应”，“应禁止”，“应”，“不应”，“建议”，“可以”， 和“可选”按照RFC 2119[1]描述进行解释。
</p>

<ul>
  <li>
    “客户端”是指最终用户和/或Web浏览器。
  </li>
  <li>
    “服务器”是指中央身份验证服务服务器。
  </li>
  <li>
    “服务”是指客户端尝试访问的应用程序。
  </li>
  <li>
    <p spaces-before="0">
      “后端服务”是指服务试图代表客户端访问的应用程序。 也可以 称为“目标服务”。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <LF> 是空换行符（ASCII值0x0a）。
    </p>
  </li>
</ul>

<h1 spaces-before="0">
  2。 CAS URI
</h1>

<p spaces-before="0">
  CAS是基于HTTP [2,3]的协议，要求它的每个组件都可以通过特定的URI进行访问。 本节将讨论每个URI。
</p>

<h2 spaces-before="0">
  2.1。 <em x-id="3">/登录</em> 作为凭据请求者
</h2>

<p spaces-before="0">
  <em x-id="3">/ login</em> URI有两种行为：作为凭据请求者和凭据接受者。 它通过充当凭据接受者来对凭据
</p>

<p spaces-before="0">
  如果客户端已经与CAS建立了单点登录会话，则Web浏览器将向CAS提供一个安全的 cookie，该cookie包含一个标识票证授予票证的字符串。 此cookie称为授予票证的cookie。 如果授予票证的cookie密钥指向有效的授予票证的票证，则只要满足本规范中的 有关授予票证的cookie的更多信息，请参见第3.6节。
</p>

<h3 spaces-before="0">
  2.1.1。 参数
</h3>

<p spaces-before="0">
  当以下HTTP请求参数充当凭据请求者时， <em x-id="3">/ login</em> 它们都是区分大小写的，并且都必须由 <em x-id="3">/ login</em>处理。
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      <code>服务 [OPTIONAL]</code> 客户端尝试访问的应用程序的标识符。 在几乎所有情况下，这将是应用程序的URL 请注意，作为HTTP请求参数，必须按照 [4]第2.2节 描述，对该URL值进行URL编码。 如果未指定服务且单点登录会话尚不存在，则CAS应该 要求用户提供凭据以启动单点登录会话。 如果未指定服务，并且 会话，则CAS应该显示一条消息，通知客户端它已经登录。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>更新 [OPTIONAL]</code> 如果设置了此参数，将跳过单点登录。 在这种情况下，无论是否存在与CAS的单点登录会话，CAS都将要求客户端 该参数与“ gateway”参数 重定向到 <em x-id="3">/ login</em> URI的服务和登录到 <em x-id="3">/ login</em> URI 登录表单视图不应同时设置“ renew”和“ gateway”请求参数。 如果两者都设置，则行为是不确定的。 建议将 设置为“更新”，CAS实现将忽略“网关”参数。 建议将更新参数 设置为“ true”。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>网关 [OPTIONAL]</code> 如果设置了此参数，CAS将不会要求客户端提供凭据。 如果客户端 单点登录会话，或者可以通过非交互方式 （即信任身份验证）建立单点登录会话，则CAS可以将客户端重定向到由“服务”参数，附加有效的 服务票证。 （CAS也可以插入一个咨询页面，通知客户已经进行了CAS认证。） 如果客户端没有与CAS的单点登录会话，并且无法建立非交互式身份验证，则CAS 必须将客户端重定向到由“服务”参数指定的URL，并且没有附加“票证”参数网址。 如果未指定“服务”参数并且设置了“网关”，则CAS的行为未定义。 建议在 ，则CAS请求凭据，就像未指定任何参数一样。 该参数与“更新” 参数不兼容。 如果两者都设置，则行为是不确定的。 建议将网关参数设置为“ true”。
    </p>
  </li>
</ol>

<h3 spaces-before="0">
  2.1.2。 URL的示例 <em x-id="3">/登录</em>
</h3>

<p spaces-before="0">
  简单的登录示例：
</p>

<pre><code>https：// server / cas / login？service = http%3A%2F%2Fwww.service.com
</code></pre>

<p spaces-before="0">
  不要提示输入用户名/密码：
</p>

<pre><code>https：// server / cas / login？service = http%3A%2F%2Fwww.service.com&网关= true
</code></pre>

<p spaces-before="0">
  总是提示输入用户名/密码：
</p>

<pre><code>https：// server / cas / login？service = http%3A%2F%2Fwww.service.com&renew = true
</code></pre>

<h3 spaces-before="0">
  2.1.3。 用户名/密码认证的响应
</h3>

<p spaces-before="0">
  当 <em x-id="3">/ login</em> 充当凭据请求者时，响应将根据其请求的凭据类型而有所不同。 在大多数情况下，CAS会通过显示要求输入用户名和密码的登录屏幕进行响应。 该页面必须包含一个带有参数“用户名”，“密码”和“ lt” 该表格还可以包含参数“ warn”。 如果将“ service”指定为 <em x-id="3">/ login</em> ，则“ service”也必须是格式的参数，包含最初传递给 <em x-id="3">/ login</em>。 在第2.2.1节中将详细讨论这 表单必须通过HTTP POST方法提交给 <em x-id="3">/ login</em> ， 将充当凭据接受器，如2.2节所述。
</p>

<h3 spaces-before="0">
  2.1.4。 信任认证响应
</h3>

<p spaces-before="0">
  信任身份验证可考虑将请求的任意方面作为身份验证的基础。 和所实施的特定身份验证机制的后勤功能，用于信任身份验证的适当用户体验将是高度特定于部署者的。
</p>

<p spaces-before="0">
  当 <em x-id="3">/ login</em> 充当信任身份验证的凭据请求者时，其行为将由它将接收 如果凭据有效，则CAS可以透明地将用户重定向到服务。 或者，CAS可以显示一个警告，提示已提供凭据，并允许客户端确认要使用 那些凭据。 建议CAS实现允许部署者选择首选行为。 如果 凭据无效或不存在，建议CAS向客户端显示身份验证失败的原因 并可能向用户显示其他身份验证方式（例如，用户名/密码身份验证）。
</p>

<h3 spaces-before="0">
  2.1.5。 单点登录身份验证的响应
</h3>

<p spaces-before="0">
  如果客户端已经建立了与CAS的单点登录会话，则客户端将向 <em x-id="3">/ login</em> cookie，并将按照第2.2.4节中的方式处理行为。 但是，如果设置了“ renew”参数，则该行为将按2.1.3或2.1.4节处理为
</p>

<h2 spaces-before="0">
  2.2。 <em x-id="3">/登录</em> 作为凭据接受者
</h2>

<p spaces-before="0">
  将一组接受的凭据传递给 <em x-id="3">/ login</em>，它充当凭据接受者，其行为在本节的
</p>

<h3 spaces-before="0">
  2.2.1。 所有认证类型共有的参数
</h3>

<p spaces-before="0">
  当以下HTTP请求参数充当凭据接受器时， <em x-id="3">/ login</em> 它们都是 区分大小写，并且都必须由 <em x-id="3">/ login</em>处理。
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      <code>服务 [OPTIONAL]</code> 客户端尝试访问的应用程序的URL。 成功进行身份验证后，CAS必须将客户端重定向到该URL 这将在第2.2.4节中详细讨论。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>警告 [OPTIONAL]</code> 如果设置了此参数，则单点登录绝不能透明。 认证到另一个服务之前提示客户端。
    </p>
  </li>
</ol>

<h3 spaces-before="0">
  2.2.2。 用户名/密码认证的参数
</h3>

<p spaces-before="0">
  除了在2.2.1节中指定的可选参数外，以下HTTP请求参数在充当用户名/密码身份验证的凭据接受器时，还 <em x-id="3">/ login</em> 它们都是区分大小写的。
</p>

<ol start="1">
  <li>
    <code>username [REQUIRED]</code> 尝试登录的客户端的用户名
  </li>
  
  <li>
    <code>password [REQUIRED]</code> 尝试登录的客户端的密码
  </li>
  
  <li>
    <code>lt [REQUIRED]</code> 登录票。 这是第2.1.3节中讨论的登录表单的一部分。 登录票证本身将在3.5节中讨论。
  </li>
</ol>

<h3 spaces-before="0">
  2.2.3。 信任认证的参数
</h3>

<p spaces-before="0">
  没有用于信任身份验证的REQUIRED HTTP请求参数。 信任认证可以基于HTTP请求的
</p>

<h3 spaces-before="0">
  2.2.4。 回复
</h3>

<p spaces-before="0">
  / login作为凭据接受者运行时，必须由/ login提供以下响应之一。
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      成功登录：客户端重定向到“服务”参数的方式指定的URL不会导致 客户端的凭证转发给服务。 这种重定向必须导致客户端向服务 该请求必须包括有效的服务票证，该服务票证作为HTTP请求参数“ ticket”传递。 有关更多信息，请参见附录B。 如果未指定“服务”，则CAS必须显示一条消息，通知客户端 已成功启动了单次登录会话。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      登录失败：以凭据请求者身份返回/ login。 正是在这种情况下，建议将CAS服务器显示 的错误信息会显示来描述为什么登录失败（如密码错误，锁定帐号等）的用户，如果 适当，提供一个机会，让用户尝试再次登录。
    </p>
  </li>
</ol>

<h2 spaces-before="0">
  2.3。 <em x-id="3">/登出</em>
</h2>

<p spaces-before="0">
  <em x-id="3">/ logout</em> 破坏客户端的单点登录CAS会话。 票证授予cookie（第3.6节）被破坏，并且 <em x-id="3">/登录</em> 后续请求将不会获得服务票证，直到用户再次提供主凭证 （从而建立新的单点登录会话）为止。
</p>

<h3 spaces-before="0">
  2.3.1。 参数
</h3>

<p spaces-before="0">
  可以将以下HTTP请求参数指定为 <em x-id="3">/ logout</em>。 这是区分大小写的，应该由 <em x-id="3">/ logout</em>处理。
</p>

<ol start="1">
  <li>
    url [OPTIONAL] 如果指定了“ url”，则“ url”指定的URL应该在具有描述性文字的注销页面上。 例如，“您刚刚注销的应用程序提供了一个链接，希望您遵循该链接。 请点击此处 访问http://www.go-back.edu。”
  </li>
</ol>

<h3 spaces-before="0">
  2.3.2。 回复
</h3>

<p spaces-before="0">
  <em x-id="3">/ logout</em> 必须显示一个页面，指出用户已经注销。 如果实现了“ url”请求参数，则 <em x-id="3">/ logout</em> 也应提供指向所提供URL的链接，如第2.3.1节所述。
</p>

<h2 spaces-before="0">
  2.4。 <em x-id="3">/验证</em> [CAS 1.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/ validate</em> 检查服务票证的有效性。/validate是CAS 1.0协议的一部分，因此不处理代理 身份验证。 <em x-id="3">/ validate</em>时，CAS必须以票证验证失败响应来响应。
</p>

<h3 spaces-before="0">
  2.4.1。 参数
</h3>

<p spaces-before="0">
  可以将以下HTTP请求参数指定为 <em x-id="3">/ validate</em>。 它们区分大小写，并且必须全部通过 <em x-id="3">/ validate</em> 。
</p>

<ol start="1">
  <li>
    <code>service [REQUIRED]</code> 为其发出票证的服务的标识符，如第2.2.1节所述。
  </li>
  
  <li>
    <code>票 [REQUIRED]</code> / login发行的服务票。 服务票在第3.1节中进行了描述。
  </li>
  
  <li>
    <code>renew [OPTIONAL]</code> 如果设置了此参数，则仅当从用户的主要凭据的表示中 如果票是从单个登录会话发出的，它将失败。
  </li>
</ol>

<h3 spaces-before="0">
  2.4.2。 回复
</h3>

<p spaces-before="0">
  <em x-id="3">/ validate</em> 将返回以下两个响应之一：
</p>

<p spaces-before="0">
  票证验证成功：
</p>

<pre><code>是&lt;LF&gt;
用户名&lt;LF&gt;
</code></pre>

<p spaces-before="0">
  在票证验证失败时：
</p>

<pre><code>否&lt;LF&gt;
&lt;LF&gt;
</code></pre>

<h3 spaces-before="0">
  2.4.3。 URL示例 <em x-id="3">/ validate</em>
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<pre><code>https：// server / cas / validate？service = http%3A%2F%2Fwww.service.com&票= ...
</code></pre>

<p spaces-before="0">
  确保通过提供主要凭证来发行服务票证：
</p>

<pre><code>https：// server / cas / validate？service = http%3A%2F%2Fwww.service.com&票= ...
</code></pre>

<h2 spaces-before="0">
  2.5。 <em x-id="3">/ serviceValidate</em> [CAS 2.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/ serviceValidate</em> 检查服务票证的有效性并返回XML片段响应。 <em x-id="3">/ serviceValidate</em> 必须在请求时还生成并颁发代理授予票证。 如果/ serviceValidate收到代理票证，则绝不能返回成功的 建议如果/ serviceValidate接收到代理票证，则 错误消息应该说明验证失败，因为将代理票证传递给 <em x-id="3">/ serviceValidate</em>。
</p>

<h3 spaces-before="0">
  2.5.1。 参数
</h3>

<p spaces-before="0">
  可以将以下HTTP请求参数指定为 <em x-id="3">/ serviceValidate</em>。 它们是大小写敏感的，必须全部 通过处理 <em x-id="3">/ serviceValidate</em>。
</p>

<ol start="1">
  <li>
    <code>service [REQUIRED]</code> 为其发出票证的服务的标识符，如第2.2.1节所述。
  </li>
  
  <li>
    <code>票 [REQUIRED]</code> / login发行的服务票。 服务票在第3.1节中进行了描述。
  </li>
  
  <li>
    <code>pgtUrl [OPTIONAL]</code> 代理回调的URL。 在第2.5.4节中讨论。
  </li>
  
  <li>
    <code>renew [OPTIONAL]</code> 如果设置了此参数，则仅当服务票证是根据用户的主要凭证的表示发行的时，票证验证才会成功。 如果票是从单个登录会话发出的，它将失败。
  </li>
</ol>

<h3 spaces-before="0">
  2.5.2。 回复
</h3>

<p spaces-before="0">
  <em x-id="3">/ serviceValidate</em> 将返回XML格式的CAS serviceResponse，如附录A中的XML模式中所述 以下是示例响应：
</p>

<p spaces-before="0">
  票证验证成功：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
    &lt;cas:authenticationSuccess&gt;
        &lt;cas:user&gt;用户名&lt;/cas:user&gt;
            &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d ...
        &lt;/cas:proxyGrantingTicket&gt;
    &lt;/cas:authenticationSuccess&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<p spaces-before="0">
  在票证验证失败时：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
    &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
        无法识别票证ST-1856339-aA5Yuvrxzpv8Tau1cYQ7
    &lt;/cas:authenticationFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<h3 spaces-before="0">
  2.5.3。 错误代码
</h3>

<p spaces-before="0">
  以下值可以用作认证失败响应的“代码”属性。 以下是所有CAS服务器必须实现的最小 实现可能包括其他实现。
</p>

<ol start="1">
  <li>
    <code>INVALID_REQUEST</code> 并非所有必需的请求参数都存在
  </li>
  
  <li>
    <p spaces-before="0">
      <code>INVALID_TICKET</code> 提供的票证无效，或者该票证不是来自初始登录，并且在验证时设置了 的身体 <cas:authenticationFailure> XML响应的块应该描述确切的 细节。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>INVALID_SERVICE</code> 提供的票证有效，但是指定的服务与与票证 CAS必须使该票证失效，并禁止将来对该票证进行验证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>INTERNAL_ERROR</code> 票证验证期间发生内部错误
    </p>
  </li>
</ol>

<p spaces-before="0">
  对于所有错误代码，建议CAS提供更详细的消息作为XML响应 <code>&lt;cas:authenticationFailure&gt;</code>
</p>

<h3 spaces-before="0">
  2.5.4。 代理回调
</h3>

<p spaces-before="0">
  如果服务希望将客户端的身份验证代理到后端服务，则它必须获取授予代理的凭单。 通过代理回调URL处理此票证的获取。 该URL将唯一且安全地标识代理客户端身份验证 然后，后端服务可以根据后端服务的标识回调URL
</p>

<p spaces-before="0">
  代理回调机制的工作方式如下：
</p>

<p spaces-before="0">
  请求代理授予票证的服务在初始服务票证或代理票证验证时将 HTTP请求参数“ pgtUrl”设置为 <em x-id="3">/ serviceValidate</em> （或 <em x-id="3">/ proxyValidate</em>）。 这是CAS 将连接到的服务的回调URL，以验证服务的身份。 该URL必须是HTTPS，并且CAS必须验证SSL证书是否为 有效且其名称与服务名称匹配。 如果证书未通过验证，则不会 的授权代理票证，并且第2.5.2节中所述的CAS服务响应 <code>&lt;proxyGrantingTicket&gt;</code> 块。 在此 点，将停止发放授权代理票证，但服务票证验证将继续，并根据情况 如果证书验证成功，则按照步骤2进行授权代理票证的发行。 CAS使用HTTP GET请求将HTTP请求参数“ pgtId”和“ pgtIou”传递给pgtUrl。 这些实体分别在第3.3节和第3.4节中讨论
</p>

<p spaces-before="0">
  如果HTTP GET返回HTTP状态代码200（确定），则CAS必须使用服务响应（第2.5.2节）响应 <em x-id="3">/ serviceValidate</em> （或 <em x-id="3">/ proxyValidate</em>） （第3.4节）位于 <code>&lt;cas:proxyGrantingTicket&gt;</code> 块内。 如果HTTP GET返回除HTTP 3xx重定向以外的任何其他状态代码，则CAS必须 响应 <em x-id="3">/ serviceValidate</em> （或 <em x-id="3">/ proxyValidate</em>）请求，并提供一个服务响应，该响应 <code>&lt;cas:proxyGrantingTicket&gt;</code> 块。 CAS可以遵循pgtUrl发出的任何HTTP重定向。 但是，验证后在 <code>&lt;proxy&gt;</code> 块中提供的标识回 URL必须与最初作为“ pgtUrl”参数 <em x-id="3">/ serviceValidate</em> （或 <em x-id="3">/ proxyValidate</em>
</p>

<p spaces-before="0">
  该服务已在CAS响应中接收到代理授权票证IOU，并且 代理授权票证IOU，将使用代理授权票证IOU来关联代理授权 张带有验证响应的票证。 然后，该服务将使用代理授予票证来获取2.7节中所述
</p>

<h3 spaces-before="0">
  2.5.5。 URL示例 <em x-id="3">/ serviceValidate</em>
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<pre><code>https：// server / cas / serviceValidate？service = http%3A%2F%2Fwww.service.com&...
</code></pre>

<p spaces-before="0">
  确保通过提供主要凭证来发行服务票证：
</p>

<p spaces-before="0">
  https：// server / cas / serviceValidate？service = http%3A%2F%2Fwww.service.com&... ST-1856339-aA5Yuvrxzpv8Tau1cYQ7&renew = true
</p>

<p spaces-before="0">
  传递用于代理的回调URL：
</p>

<p spaces-before="0">
  https：// server / cas / serviceValidate？service = http%3A%2F%2Fwww.service.com&...
</p>

<h2 spaces-before="0">
  2.6。 <em x-id="3">/ proxyValidate</em> [CAS 2.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/ proxyValidate</em> 必须执行与/ serviceValidate相同的验证任务，并另外验证代理票证。 <em x-id="3">/ proxyValidate</em> 必须能够验证服务票证和代理票证。
</p>

<h3 spaces-before="0">
  2.6.1。 参数
</h3>

<p spaces-before="0">
  <em x-id="3">/ proxyValidate</em> <em x-id="3">/ serviceValidate</em>具有相同的参数要求。 请参阅第2.5.1节。
</p>

<h3 spaces-before="0">
  2.6.2。 回复
</h3>

<p spaces-before="0">
  <em x-id="3">/ proxyValidate</em> 将返回XML格式的CAS serviceResponse，如附录A中的XML模式中所述 以下是示例响应：
</p>

<p spaces-before="0">
  票证验证成功：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
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

<p spaces-before="0">
  请注意，当认证通过多个代理进行时，代理遍历的顺序 必须反映在代理中。 <cas:proxies> 堵塞。 最近访问的代理必须是列出的第一个代理， 其他代理必须在添加新代理后向下移动。 在上面的示例中，首先访问了由 <em x-id="3">https：// proxy1 / pgtUrl</em> 标识的服务，并且该服务代理了对由 <em x-id="3">https：// proxy2 / pgtUrl</em>标识的服务的身份验证。
</p>

<p spaces-before="0">
  在票证验证失败时：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
  &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
      票证PT-1856376-1HMgO86Z2ZKeByc5XdYD无法识别
  &lt;/cas:authenticationFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<h3 spaces-before="0">
  2.6.3 URL示例 <em x-id="3">/ proxyValidate</em>
</h3>

<p spaces-before="0">
  <em x-id="3">/ proxyValidate</em> <em x-id="3">/ serviceValidate</em>相同的参数。 有关使用示例，请参见第2.5.5节，将 “ proxyValidate”替换为“ serviceValidate”。
</p>

<h2 spaces-before="0">
  2.7。 <em x-id="3">/代理</em> [CAS 2.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/ proxy</em> 为已获得代理授权票证的服务提供代理票证，并将代理 身份验证到后端服务。
</p>

<h3 spaces-before="0">
  2.7.1。 参数
</h3>

<p spaces-before="0">
  必须将以下HTTP请求参数指定给/ proxy。 它们都区分大小写。
</p>

<ol start="1">
  <li>
    <code>pgt [REQUIRED]</code> 服务在服务票证或代理票证验证期间获取的代理票证
  </li>
  
  <li>
    <code>targetService [REQUIRED]</code> 后端服务的服务标识符。 请注意，并非所有后端服务都是 Web服务，因此此服务标识符将不会始终是URL。 但是，在验证代理票证后，此处指定的服务标识符必须 匹配为/ proxyValidate指定的“服务”参数。
  </li>
</ol>

<h3 spaces-before="0">
  2.7.2。 回复
</h3>

<p spaces-before="0">
  <em x-id="3">/ proxy</em> 将返回XML格式的CAS serviceResponse，如附录A中的XML模式中所述 以下是示例响应：
</p>

<p spaces-before="0">
  根据请求成功：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
    &lt;cas:proxySuccess&gt;
        &lt;cas:proxyTicket&gt;PT-1856392-b98xZrQN4p90ASrw96c8&lt;/cas:proxyTicket&gt;
    &lt;/cas:proxySuccess&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<p spaces-before="0">
  应要求失败：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
  &lt;cas:proxyFailure code="INVALID_REQUEST"&gt;
      'PGT'和'targetService'参数都需要
  &lt;/cas:proxyFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<h3 spaces-before="0">
  2.7.3。 错误代码
</h3>

<p spaces-before="0">
  以下值可以用作认证失败响应的“代码”属性。 以下是所有CAS服务器必须实现的最小错误代码集 实现可能包括其他实现。
</p>

<ol start="1">
  <li>
    <code>INVALID_REQUEST</code> 并非所有必需的请求参数都存在
  </li>
  
  <li>
    <code>BAD_PGT</code> 提供的pgt无效
  </li>
  
  <li>
    <code>INTERNAL_ERROR</code> 票证验证期间发生内部错误
  </li>
</ol>

<p spaces-before="0">
  对于所有错误代码，建议CAS提供更详细的消息作为XML响应 <code>&lt;cas:authenticationFailure&gt;</code>
</p>

<h3 spaces-before="0">
  2.7.4。 URL示例 <em x-id="3">/代理</em>
</h3>

<p spaces-before="0">
  简单的代理请求：
</p>

<pre><code>https：// server / cas / proxy？targetService = http%3A%2F%2Fwww.service.com&pgt = ......
</code></pre>

<h1 spaces-before="0">
  3。 CAS实体
</h1>
<h2 spaces-before="0">
  3.1。 服务票
</h2>

<p spaces-before="0">
  服务票证是一个不透明的字符串，客户端将其用作获取对服务的访问的凭据。 如2.2节所述，在 <em x-id="3">/ login</em> 提供凭据和服务标识符后，可以从CAS获得服务凭单。
</p>

<h3 spaces-before="0">
  3.1.1。 服务票属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      服务凭单仅对生成时在/ login中指定的服务标识符有效。 服务标识符不应成为服务凭单的一部分。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      服务票仅对一次票确认尝试有效。 无论验证是否成功， 然后会使该票证失效，从而导致该票证的所有将来的验证尝试均失败。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      CAS应在未批准的服务票签发后的合理时间内将其过期。 如果服务 为验证提供了过期的服务票证，则CAS必须以验证失败响应作为响应。 建议验证响应中包含一条描述性消息，说明验证失败的原因。 建议服务票证在过期之前的有效期限不超过五分钟。 本地安全性和CAS使用注意事项可以确定未经验证的服务票证的最佳使用寿命。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      服务票证必须包含足够的安全随机数据，以使票证不可猜测。
    </p>
  </li>
  
  <li>
    服务票必须以字符“ ST-”开头。
  </li>
  
  <li>
    服务必须能够接受最长32个字符的服务票证。 建议服务 支持最多256个字符的服务票证。
  </li>
</ol>

<h2 spaces-before="0">
  3.2。 代理票
</h2>

<p spaces-before="0">
  代理票证是一个不透明的字符串，服务将其用作凭证以代表客户端 代理票证是在服务提供有效的代理授予票证 （第3.3节）以及与其连接的后端服务的服务标识符后从CAS获得的。
</p>

<h3 spaces-before="0">
  3.2.1。 代理票证属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      代理票证仅对生成时指定给/ proxy的服务标识符有效。 服务标识符不应该是代理凭单的一部分。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理凭单仅对一次凭单验证尝试有效。 无论验证是否成功， 然后会使该票证失效，从而导致该票证的所有将来的验证尝试均失败。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      CAS应该在未验证的代理票证签发后的合理时间内过期。 如果服务提供了用于验证过期的代理凭单，则CAS必须以验证失败响应作为响应。 建议验证响应中包含一条描述性消息，说明验证失败的原因。 建议代理票证过期之前的有效期限不超过五分钟。 本地安全性和CAS使用注意事项可以确定未验证的代理票证的最佳寿命。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理凭单必须包含足够的安全随机数据，以使凭单不可猜测。
    </p>
  </li>
  
  <li>
    代理票应以字符“ PT-”开头。
  </li>
  
  <li>
    代理票必须以字符“ ST-”或“ PT-”开头。
  </li>
  
  <li>
    后端服务必须能够接受最多32个字符的代理票证。 建议后端服务支持最多256个字符的代理票证。
  </li>
</ol>

<h2 spaces-before="0">
  3.3。 授权代理票
</h2>

<p spaces-before="0">
  授予代理的票证是一个不透明的字符串，服务使用它来获取代理票证，以代表客户端 服务票证或代理票证后，可从CAS获得代理票证。 授予代理票证的票证在第2.5.4节中有完整描述。
</p>

<h3 spaces-before="0">
  3.3.1。 代理授权票证属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      服务可以使用授权代理票证来获取多个代理票证。 授予代理的票证不是一次性使用的票证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      当代理身份验证的客户端从CAS中注销时，必须授予代理授权的票证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      授予代理的票证必须包含足够的安全随机数据，以使票证在合理的 时间内无法通过蛮力攻击进行猜测。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      授予代理的票证应以字符“ PGT-”开头。
    </p>
  </li>
  
  <li>
    服务必须能够处理最长64个字符的授权代理票证。 建议服务支持最长256个字符的授权代理票证。
  </li>
</ol>

<h2 spaces-before="0">
  3.4。 代理授权票IOU
</h2>

<p spaces-before="0">
  授予代理票证的IOU是一个不透明的字符串，它位于由 <em x-id="3">/ serviceValidate</em> 和 <em x-id="3">/ proxyValidate</em> 提供的响应中，用于将服务票证或代理票证的验证与特定的代理授予票证 有关此过程的完整说明，请参见第2.5.4节。
</p>

<h3 spaces-before="0">
  3.4.1。 授予代理票证的IOU属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      授予代理票证的借条不应包含对其关联的代理票证的任何引用。 给定一个特定的PGTIOU，一定不能在 合理的时间内通过算法方法导出其对应的PGT。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      授予代理的票证IOU必须包含足够的安全随机数据，以确保在 合理的时间内通过蛮力攻击无法猜测到票证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      授予代理票务的借条应以字符“ PGTIOU-”开头。
    </p>
  </li>
  
  <li>
    服务必须能够处理最多64个字符的PGTIOU。 建议服务支持最长256个字符的PGTIOU。
  </li>
</ol>

<h2 spaces-before="0">
  3.5。 登录票
</h2>

<p spaces-before="0">
  登录票证是一个字符串，由字符串 <em x-id="3">/ login</em> 作为凭据请求者提供，并作为 <em x-id="3">/ login</em> ，用于用户名/密码身份验证。 其目的是防止由于Web浏览器中的错误而
</p>

<h3 spaces-before="0">
  3.5.1。 登录票证属性
</h3>

<ol start="1">
  <li>
    / login发行的登录票必须是概率唯一的。
  </li>
  
  <li>
    <p spaces-before="0">
      登录票仅对一次身份验证尝试有效。 无论身份验证是否成功， 登录凭单的该实例进行的所有将来的身份验证尝试都将失败。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      登录票应以字符“ LT-”开头。
    </p>
  </li>
</ol>

<h2 spaces-before="0">
  3.6。 购票饼干
</h2>

<p spaces-before="0">
  授予票证的cookie是CAS在建立单点登录会话时设置[5] 该cookie维护客户端的登录状态，并且在有效期间，客户端可以将其提交给CAS代替主要凭证的 2.1.1、2.4.1和2.5.1节中所述的“更新”参数选择退出单点登录。
</p>

<h3 spaces-before="0">
  3.6.1。 授予票证的Cookie属性
</h3>

<ol start="1">
  <li>
    授予票证的cookie必须设置为在客户端的浏览器会话结束时到期。
  </li>
  
  <li>
    <p spaces-before="0">
      CAS必须将Cookie路径设置为尽可能严格。 例如，如果将CAS服务器设置在 路径/ cas下，则cookie路径必须设置为/ cas。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      票证cookie的值必须包含足够的安全随机数据，以使票证cookie在合理的时间段内不可猜测
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      授予票务的cookie的值应以字符“ TGC-”开头。
    </p>
  </li>
</ol>

<h2 spaces-before="0">
  3.7。 票证和授予票证的Cookie字符集
</h2>

<p spaces-before="0">
  除上述要求外，所有CAS票和票证授予cookie的值只能包含 从字符的集合{包括AZ，az，0-9和连字符- '}。
</p>

<h1 spaces-before="0">
  附录A：CAS响应XML模式
</h1>

<pre><code><!--
       The following is the schema for the Yale Central Authentication
       Service (CAS) version 2.0 protocol response.  This covers the responses
       for the following servlets:
         /serviceValidate
         /proxyValidate
         /proxy
       This specification is subject to change.
       Author:  Drew Mazurek
       Version: $Id: cas2.xsd,v 1.1 2005/02/14 16:19:06 dmazurek Exp $
    -->
&lt;xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:cas="http://www.yale.edu/tp/cas"
           targetNamespace="http://www.yale.edu/tp/cas"
           elementFormDefault="qualified" attributeFormDefault="unqualified"&gt;
    &lt;xs:element name="serviceResponse" type="cas:ServiceResponseType"/&gt;
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
            &lt;xs:element name="user" type="xs:string"/&gt;
            &lt;xs:element name="proxyGrantingTicket" type="xs:string" minOccurs="0"/&gt;
            &lt;xs:element name="proxies" type="cas:ProxiesType" minOccurs="0"/&gt;
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
                &lt;xs:attribute name="code" type="xs:string" use="required"/&gt;
            &lt;/xs:extension&gt;
        &lt;/xs:simpleContent&gt;
    &lt;/xs:complexType&gt;
    &lt;xs:complexType name="ProxySuccessType"&gt;
        &lt;xs:sequence&gt;
            &lt;xs:element name="proxyTicket" type="xs:string"/&gt;
        &lt;/xs:sequence&gt;
    &lt;/xs:complexType&gt;
    &lt;xs:complexType name="ProxyFailureType"&gt;
        &lt;xs:simpleContent&gt;
            &lt;xs:extension base="xs:string"&gt;
                &lt;xs:attribute name="code" type="xs:string" use="required"/&gt;
            &lt;/xs:extension&gt;
        &lt;/xs:simpleContent&gt;
    &lt;/xs:complexType&gt;
&lt;/xs:schema&gt;
</code></pre>

<h1 spaces-before="0">
  附录B：安全重定向
</h1>

<p spaces-before="0">
  成功登录后，必须谨慎处理将客户端从CAS重定向到其最终目的地的安全操作。 在大多数情况下，客户端已通过POST请求将凭据发送到CAS服务器。 根据此规范，CAS 服务器随后必须使用GET请求将用户转发到应用程序。
</p>

<p spaces-before="0">
  HTTP / 1.1 RFC[3] 提供的响应代码为303：请参见其他，以提供所需的行为： 通过POST请求接收数据的脚本可以通过303重定向，将浏览器转发到另外 URL（通过GET）要求。 但是，并非所有浏览器都正确实现了此行为。
</p>

<p spaces-before="0">
  因此，推荐的重定向方法是JavaScript。 方式包含window.location.href的页面可以正常执行：
</p>

<pre><code>&lt;html&gt;
    &lt;head&gt;
        &lt;title&gt;Yale中央身份验证服务&lt;/title&gt;
        &lt;script&gt;
            window.location.href =“ https：//portal.yale.edu/Login？ticket = ST -...” mce_href =“ https：//portal.yale .edu / Login？ticket = ST -...“；
       &lt;/script&gt;
    &lt;/head&gt;
    &lt;body&gt;
        &lt;noscript&gt;
            &lt;p&gt;CAS登录成功。&lt;/p&gt;
            &lt;p&gt;  单击 &lt;a xhref="https://portal.yale.edu/Login?ticket=ST-..." mce_href="https://portal.yale.edu/Login?ticket=ST-..."&gt;这里&lt;/a&gt;
            以访问您请求的服务。&lt;br /&gt;  &lt;/p&gt;
        &lt;/noscropt&gt;
    &lt;/body&gt;
&lt;/html&gt;
</code></pre>

<p spaces-before="0">
  此外，CAS应该通过设置所有与缓存相关的所有标头来禁用浏览器缓存：
</p>

<pre><code>语法：无缓存
缓存控制：无存储
过期：[RFC 1123[6] 日期等于或早于现在]
</code></pre>

<p spaces-before="0">
  登录票证的引入消除了CAS接受浏览器 但是，Apple的Safari浏览器的早期版本存在一个错误，即通过使用“后退”按钮， Safari向其尝试访问的服务提供客户端凭据。 如果CAS Safari的这些早期版本之一，则可以通过不自动重定向 而是，CAS应显示一个表明登录成功的页面，并提供指向所请求服务的链接。 然后，客户端必须手动单击以继续。
</p>

<p spaces-before="0">
  附录C：参考文献 [1] Bradner，S.，“用于RFC中以指示需求水平的关键词”，RFC 2119，哈佛大学，1997年3月。 [2] Berners-Lee，T.，Fielding，R.，Frystyk，H。，“超文本传输协议-HTTP / 1.0”，RFC 1945，MIT / LCS，加州大学欧文分校，MIT / LCS，1996年5月。 [3] Fielding，R.，Gettys，J.，Mogul，J.，Frystyk，H.，Masinter，L.，Leach，P.，Berners-Lee，T.，“超文本传输协议-HTTP / 1.1”，RFC 2068 ，加州大学欧文分校，Compaq / W3C，Compaq，W3C / MIT，施乐，微软，W3C / MIT，1999年6月。 [4] T. Berners-Lee，L。Masinter和M. MaCahill，“统一资源定位符（URL）”，RFC 1738，CERN，施乐公司，明尼苏达大学，1994年12月。 [5] Kristol，D.，Montulli，L。，“ HTTP状态管理机制”，RFC 2965，贝尔实验室/朗讯科技公司，Epinions.com，Inc.，2000年10月。 [6] Braden，R。，“ Internet主机的要求-应用程序和支持”，RFC 1123，Internet工程任务组，1989年10月。
</p>

<h1 spaces-before="0">
  附录D：CAS许可证
</h1>

<p spaces-before="0">
  耶鲁大学（c）2000-2005版权所有。 本软件按“原样”提供，任何明示或暗示的担保，包括但不限于对特定目的的适销性和适用性的 在 大学或其员工均不对任何直接，间接，偶发，特殊，特殊或后果性损害 （包括但不限于，替代商品或服务的采购成本；使用损失，或利润； 或业务中断），无论是基于合同，严格责任还是侵权 （包括疏忽或其他方式），无论是出于任何责任，都基于上述任何责任，无论是出于使用本软件的目的，还是出于上述原因， 可能性中的一种。
</p>

<p spaces-before="0">
  再分配和使用该软件源代码或二进制形式，有或没有修饰的，是允许的，提供 的是，满足以下条件：
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      文档以及可能的情况下在重新分发的软件中包括上述版权声明和免责声明以及此条件列表。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      任何重新分发都必须在任何相关文档中（如果可行的话）在重新分发的软件中包含确认：“该产品包括由耶鲁大学开发的软件” \\
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      不得使用名称“ Yale”和“ Yale University”来认可或促销从该软件派生的产品。
    </p>
  </li>
</ol>

<h1 spaces-before="0">
  附录E：对本文档的更改
</h1>

<p spaces-before="0">
  2005年5月4日：v1.0-初始版本
</p>
