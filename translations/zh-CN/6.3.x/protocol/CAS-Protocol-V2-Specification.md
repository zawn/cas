---
layout: 违约
title: 中科院 - CAS 协议 2.0 规范
category: 协议
---

<a name="headTop"></p>
<h1 spaces-before="0">
  <em x-id="3">CAS 协议 2.0 规范</em>
</h1>

<p spaces-before="0">
  作者：德鲁·马祖雷克 撰稿人： 苏珊·布拉姆霍尔 霍华德·吉尔伯特 安迪·纽曼 安德鲁·彼得罗 版：1.0
</p>

<p spaces-before="0">
  发布日期：2005年5月4日 版权所有©2005年耶鲁大学
</p>

<h1 spaces-before="0">
  1. 介绍
</h1>

<p spaces-before="0">
  这是 CAS 1.0 和 2.0 协议的官方规范。 它可能会发生变化。
</p>

<h2 spaces-before="0">
  1.1. 公约 & 定义
</h2>

<p spaces-before="0">
  本文件中的关键词"必须"、"必须"、"必须"、"应"、"不应"、"建议"、"可能"、" "和"可选"将解释为 RFC 2119[1]中所述。
</p>

<ul>
  <li>
    "客户端"是指最终用户和/或 Web 浏览器。
  </li>
  <li>
    "服务器"是指中央身份验证服务服务器。
  </li>
  <li>
    "服务"是指客户端正在尝试访问的应用程序。
  </li>
  <li>
    <p spaces-before="0">
      "后端服务"是指服务试图代表客户访问的应用程序。 这也可以 称为"目标服务"。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <LF> 是一个裸线馈送（ASCII值0x0a）。
    </p>
  </li>
</ul>

<h1 spaces-before="0">
  2. 卡斯URI
</h1>

<p spaces-before="0">
  CAS 是基于 HTTP[2，3] 的协议，要求其每个组件都可以通过特定的 UURI 访问。 本节将讨论每个URI。
</p>

<h2 spaces-before="0">
  2.1. <em x-id="3">/登录</em> 作为凭据请求器
</h2>

<p spaces-before="0">
  <em x-id="3">/登录</em> URI 具有两种行为：作为凭据请求者和凭据接受者。 它通过充当凭据接受者 以其他方式作为凭据请求者来响应凭据。
</p>

<p spaces-before="0">
  如果客户端已与 CAS 建立了单个登录会话，Web 浏览器会向 CAS 提交一个安全 cookie，其中包含识别出票证的字符串。 这块饼干叫做赠票饼干。 如果有效赠票票的赠票饼干密钥，CAS 可以签发服务票证，前提是符合此规范中的所有 其他条件。 有关赠票饼干的更多信息，请参阅第 3.6 节。
</p>

<h3 spaces-before="0">
  2.1.1. 参数
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可在充当凭据请求器时传递给 <em x-id="3">/登录</em> 。 它们都对案件敏感，并且都必须通过 <em x-id="3">/登录</em>来处理。
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      <code>服务 [OPTIONAL]</code> - 客户端正在尝试访问的应用程序的标识符。 在几乎所有情况下，这将 应用程序的 URL。 请注意，作为 HTTP 请求参数，此 URL 值必须按照 RFC 1738 [4]第 2.2 节中描述的 URL 编码。 如果未指定服务且尚未存在单个登录会话，CAS 应 请求用户凭据启动单个登录会话。 如果没有指定服务，并且已经存在单个登录 会话，CAS 应显示一条消息，通知客户端它已登录。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>续订 [OPTIONAL]</code> - 如果设置此参数，将绕过单个登录。 在这种情况下，CAS 将要求客户 出示凭据，而不管是否与 CAS 存在单一的签到会话。 此参数与"网关"参数不兼容 。 重定向到 <em x-id="3">/登录</em> URI 的服务和登录表单视图发布到 <em x-id="3">/登录</em> URI 不应同时设置"续订"和"网关"请求参数。 如果两者都设置，则行为是不确定的。 建议 ，如果设置"续订"，CAS 实施将忽略"网关"参数。 建议当更新参数 设置时，其价值为"真实"。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>网关 [OPTIONAL]</code> - 如果设置此参数，CAS 不会向客户索要凭据。 如果客户端具有与 CAS 的预先存在的 单个登录会话，或者是否可以通过非交互式方式 （即信任身份验证）建立单个登录会话，CAS 可能会将客户端重定向到"服务"参数指定的 URL，从而附加有效的 服务票证。 （CAS 还可以插话一个咨询页面，告知客户 CAS 认证已经进行。 如果客户端没有与 CAS 的单一登录会话，并且无法建立非交互式身份验证，CAS 必须将客户端重定向到"服务"参数指定的 URL，并且没有附加到 URL 的"票证"参数。 未指定"服务"参数并设置"网关"，则 CAS 的行为未定义。 建议在本案例 ，CAS 请求凭据，好像没有指定任何参数。 此参数与"续订" 参数不兼容。 如果两者都设置，则行为是不确定的。 建议在设置网关参数时，其值为"真实"。
    </p>
  </li>
</ol>

<h3 spaces-before="0">
  2.1.2. <em x-id="3">/登录</em>的 URL 示例
</h3>

<p spaces-before="0">
  简单的登录示例：
</p>

<pre><code>https://server/cas/login?service=http%3A%2F%2Fwww.service.com
</code></pre>

<p spaces-before="0">
  不要提示用户名/密码：
</p>

<pre><code>https://server/cas/login?service=http%3A%2F%2Fwww.service.com&网关=真实
</code></pre>

<p spaces-before="0">
  始终提示用户名/密码：
</p>

<pre><code>https://server/cas/login?service=http%3A%2F%2Fwww.service.com&更新=真实
</code></pre>

<h3 spaces-before="0">
  2.1.3. 用户名/密码身份验证响应
</h3>

<p spaces-before="0">
  当 <em x-id="3">/登录</em> 充当凭据请求者时，响应将因请求的凭据类型而异。 在大多数情况下，CAS 会通过显示要求用户名和密码的登录屏幕来响应。 此页面必须包含带有参数"用户名"、"密码"和"lt"的表单 。 表单可能还包括参数"警告"。 如果"服务" 指定为 <em x-id="3">/登录</em>，"服务"也必须是表单的参数，包含最初传递到 <em x-id="3">/登录</em>的值。 第2.2.1节详细讨论了这些 参数。 表格必须通过HTTP POST方法提交给 <em x-id="3">/登录</em> 然后作为凭据接受者，在第2.2节讨论。
</p>

<h3 spaces-before="0">
  2.1.4. 信任身份验证响应
</h3>

<p spaces-before="0">
  信任身份验证可考虑请求的任意方面，作为身份验证的基础。 考虑到当地政策 以及实施的特定认证机制的后勤工作，信任认证的适当用户体验将高度针对具体部署。
</p>

<p spaces-before="0">
  当 <em x-id="3">/登录</em> 作为信任身份验证的凭据请求者时，其行为将取决于它将收到的 凭据类型。 如果凭据有效，CAS 可能会透明地将用户重定向到服务。 或者，CAS 可能会显示提交凭据的警告，并允许客户端确认它希望使用这些凭据 。 建议 CAS 实施允许部署人员选择首选行为。 如果 凭据无效或不存在，建议 CAS 向客户端显示身份验证失败的原因， 并可能向用户提供替代身份验证方法（例如用户名/密码身份验证）。
</p>

<h3 spaces-before="0">
  2.1.5. 单个登录身份验证响应
</h3>

<p spaces-before="0">
  如果客户端已与 CAS 建立了单一的登录会话，则客户端将 Cookie 提交其 HTTP 会话，以 <em x-id="3">/登录</em> 并且行为将像第 2.2.4 节一样处理。 但是，如果设置"更新"参数，行为将 像第 2.1.3 节或第 2.1.4 节那样处理。
</p>

<h2 spaces-before="0">
  2.2. <em x-id="3">/登录</em> 作为凭据接受者
</h2>

<p spaces-before="0">
  当一组接受的凭据传递给 <em x-id="3">/登录</em>时，它将作为凭据接受者，其行为 本节中定义。
</p>

<h3 spaces-before="0">
  2.2.1. 所有类型的身份验证常见的参数
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可在充当凭据接受者时传递给 <em x-id="3">/登录</em> 。 他们都 案件敏感，他们必须由 <em x-id="3">/登录</em>处理。
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      <code>服务 [OPTIONAL]</code> - 客户端尝试访问的应用程序的 URL。 CAS 必须在成功认证后将客户端重定向到此 URL 。 第2.2.4节详细讨论了这个问题。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>警告 [OPTIONAL]</code> - 如果设置此参数，单个登录必须不透明。 在 对其他服务进行身份验证之前，必须提示客户端。
    </p>
  </li>
</ol>

<h3 spaces-before="0">
  2.2.2. 用户名/密码身份验证参数
</h3>

<p spaces-before="0">
  除了第 2.2.1 节中指定的可选参数外，以下 HTTP 请求参数必须传递给 <em x-id="3">/登录</em> ，同时它充当用户名/密码身份验证的凭据接受者。 它们都对个案敏感。
</p>

<ol start="1">
  <li>
    <code>用户名 [REQUIRED]</code> - 尝试登录的客户端的用户名
  </li>
  
  <li>
    <code>密码 [REQUIRED]</code> - 正在尝试登录的客户端的密码
  </li>
  
  <li>
    <code> [REQUIRED]</code> -登录票。 这是作为第 2.1.3 节中讨论的登录表的一部分提供的。 登录票证本身在第 3.5 节中讨论。
  </li>
</ol>

<h3 spaces-before="0">
  2.2.3. 信任身份验证参数
</h3>

<p spaces-before="0">
  信任身份验证没有所需的 HTTP 请求参数。 信任认证可能基于 HTTP 请求的任何方面 。
</p>

<h3 spaces-before="0">
  2.2.4. 响应
</h3>

<p spaces-before="0">
  当/登录作为凭据接受者运行时，必须提供以下响应之一。
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      成功登录：将客户端重定向到"服务"参数指定的 URL，不会导致 将客户的凭据转发到服务。 此重定向必须导致客户端向服务 发出 GET 请求。 请求必须包括一张有效的服务机票，作为 HTTP 请求参数"机票"传递。 有关更多信息，请参阅附录 B。 如果没有指定"服务"，CAS 必须显示一条消息，通知客户端， 它已成功启动了单个登录会话。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      登录失败：作为凭据请求器返回/登录。 在这种情况下，建议 CAS 服务器显示 向用户显示一条错误消息，描述登录失败的原因（例如密码错误、锁定帐户等），如果 适当，则为用户提供再次尝试登录的机会。
    </p>
  </li>
</ol>

<h2 spaces-before="0">
  2.3. <em x-id="3">/注销</em>
</h2>

<p spaces-before="0">
  <em x-id="3">/注销</em> 会破坏客户端的单个登录 CAS 会话。 出票 Cookie（第 3.6 节）被销毁，随后 <em x-id="3">/登录</em> 请求，直到用户再次 （从而建立新的单一登录会话）提交主要凭据，才能获得服务票证。
</p>

<h3 spaces-before="0">
  2.3.1. 参数
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可指定为 <em x-id="3">/注销</em>。 这是案件敏感，应由 <em x-id="3">/注销</em>处理。
</p>

<ol start="1">
  <li>
    网址 [OPTIONAL] - 如果指定了"url"，则"url"指定的 URL 应在具有描述性文本的注销页面上。 例如，"您刚刚注销的应用程序提供了它希望您关注的链接。 请点击这里 访问 http://www.go-back.edu"。
  </li>
</ol>

<h3 spaces-before="0">
  2.3.2. 响应
</h3>

<p spaces-before="0">
  <em x-id="3">/注销</em> 必须显示一个页面，说明用户已注销。 如果实施"url"请求参数， <em x-id="3">/注销</em> 还应提供指向第 2.3.1 节中描述的提供的 URL 的链接。
</p>

<h2 spaces-before="0">
  2.4. <em x-id="3">/验证</em> [CAS 1.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/验证</em> 检查服务票证的有效性。 /验证是 CAS 1.0 协议的一部分，因此不处理代理 认证。 当代理票证传递给 <em x-id="3">/验证</em>时，CAS 必须以票证验证失败响应来响应。
</p>

<h3 spaces-before="0">
  2.4.1. 参数
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可指定为 <em x-id="3">/验证</em>。 它们对案件敏感，必须全部由 <em x-id="3">/验证</em>处理 。
</p>

<ol start="1">
  <li>
    <code>服务 [REQUIRED]</code> - 如第 2.2.1 节所述，签发机票的服务标识符。
  </li>
  
  <li>
    <code>票 [REQUIRED]</code> -/登录签发的服务票。 服务票在第 3.1 节中描述。
  </li>
  
  <li>
    <code>续订 [OPTIONAL]</code> - 如果设置此参数，只有当服务票证 用户主要凭据的演示中签发时，票证验证才会成功。 如果从单个登录会话中签发的票证将失败。
  </li>
</ol>

<h3 spaces-before="0">
  2.4.2. 响应
</h3>

<p spaces-before="0">
  <em x-id="3">/验证</em> 将返回以下两个答复之一：
</p>

<p spaces-before="0">
  关于票证验证成功：
</p>

<pre><code>是的&lt;LF&gt;
用户名&lt;LF&gt;
</code></pre>

<p spaces-before="0">
  关于票证验证失败：
</p>

<pre><code>没有&lt;LF&gt;
&lt;LF&gt;
</code></pre>

<h3 spaces-before="0">
  2.4.3. <em x-id="3">/验证</em>的 URL 示例
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<pre><code>https://server/cas/validate?service=http%3A%2F%2Fwww.service.com&票
</code></pre>

<p spaces-before="0">
  确保通过出示主要凭据签发服务票：
</p>

<pre><code>https://server/cas/validate?service=http%3A%2F%2Fwww.service.com&票
</code></pre>

<h2 spaces-before="0">
  2.5. <em x-id="3">/服务验证</em> [CAS 2.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/服务验证</em> 检查服务票证的有效性并返回 XML 片段响应。 <em x-id="3">/服务验证</em> 还必须生成和发出代理授予票，当要求。 /服务验证不得返回成功的 认证，如果它收到代理票证。 建议如果/服务验证收到代理票证，XML 响应中的 错误消息应解释验证失败，因为代理票证已传递到 <em x-id="3">/服务验证</em>。
</p>

<h3 spaces-before="0">
  2.5.1. 参数
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可指定为 <em x-id="3">/服务验证</em>。 它们对案件敏感，必须全部由 <em x-id="3">/服务验证</em> 处理。
</p>

<ol start="1">
  <li>
    <code>服务 [REQUIRED]</code> - 如第 2.2.1 节所述，签发机票的服务标识符。
  </li>
  
  <li>
    <code>票 [REQUIRED]</code> -/登录签发的服务票。 服务票在第 3.1 节中描述。
  </li>
  
  <li>
    <code>pgturl [OPTIONAL]</code> - 代理回调的 Url 。 在第2.5.4节中讨论。
  </li>
  
  <li>
    <code>续订 [OPTIONAL]</code> - 如果设置此参数，只有从用户的主要凭据演示中签发服务票证，票证验证才会成功。 如果从单个登录会话中签发的票证将失败。
  </li>
</ol>

<h3 spaces-before="0">
  2.5.2. 响应
</h3>

<p spaces-before="0">
  <em x-id="3">/服务验证</em> 将返回 XML 格式的 CAS 服务响应（如附录 A 中的 XML 示例中所述。下面 示例响应：
</p>

<p spaces-before="0">
  关于票证验证成功：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
    &lt;cas:authenticationSuccess&gt;
        &lt;cas:user&gt;用户名&lt;/cas:user&gt;
            &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d.。。
        &lt;/cas:proxyGrantingTicket&gt;
    &lt;/cas:authenticationSuccess&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<p spaces-before="0">
  关于票证验证失败：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
    &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
        票ST-1856339-aA5尤夫尔克斯pv8陶1cYQ7未识别
    &lt;/cas:authenticationFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<h3 spaces-before="0">
  2.5.3. 错误代码
</h3>

<p spaces-before="0">
  以下值可用作身份验证失败响应的"代码"属性。 以下是所有 CAS 服务器必须实施的最小错误代码集 。 实施可能包括其他实施。
</p>

<ol start="1">
  <li>
    <code>INVALID_REQUEST</code> - 并非所有所需的请求参数都存在
  </li>
  
  <li>
    <p spaces-before="0">
      <code>INVALID_TICKET</code> - 提供的机票无效，或者机票并非来自初始登录，"续订" 已设置为验证。 身体的 <cas:authenticationFailure> XML响应的块应描述确切 细节。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>INVALID_SERVICE</code> - 提供的机票有效，但指定的服务与 票相关的服务不匹配。 CAS 必须使机票失效，并不允许将来验证该票证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>INTERNAL_ERROR</code> - 票证验证过程中发生了内部错误
    </p>
  </li>
</ol>

<p spaces-before="0">
  对于所有错误代码，建议 CAS 提供更详细的消息，作为 XML 响应 <code>&lt;cas:authenticationFailure&gt;</code> 块的主体。
</p>

<h3 spaces-before="0">
  2.5.4. 代理回调
</h3>

<p spaces-before="0">
  如果服务希望将客户端身份验证代理到后端服务，则必须获得代理授予票证。 此票证的获取通过代理回调 URL 处理。 此 URL 将独特且安全地识别代理客户身份验证的后端 服务。 然后，后端服务可以根据后端服务的识别回调 URL 决定是否接受 凭据。
</p>

<p spaces-before="0">
  代理回调机制的工作原理如下：
</p>

<p spaces-before="0">
  请求代理授权票的服务在初始服务票证或代理票证验证时指定了 HTTP 请求参数"pgtUrl"以 <em x-id="3">/服务验证</em> （或 <em x-id="3">/代理验证</em>）。 这是 CAS 将连接到的服务的回调 URL，以验证服务的身份。 此 URL 必须是 HTTPS，CAS 必须验证 SSL 证书是否 有效，并且其名称与服务的名称相符。 如果证书未通过验证，则不会 签发代理授权票证，CAS 服务响应（如第 2.5.2 节所述）不得包含 <code>&lt;proxyGrantingTicket&gt;</code> 块。 在此 点，代理授权机票的发行将停止，但服务票证验证将继续，并酌情返回成功或 失败。 如果证书验证成功，则代理授权票证的发放将按照第 2 步进行。 CAS使用HTTP GET请求将HTTP请求参数"pgtId"和"pgtIou"传递到pgtUrl。 这些实体 分别在第3.3和3.4节中讨论。
</p>

<p spaces-before="0">
  如果 HTTP GET 返回了 200 （OK） 的 HTTP 状态代码，CAS 必须响应 <em x-id="3">/服务验证</em> （或 <em x-id="3">/代理验证</em>） 请求，并提供包含 <code>&lt;cas:proxyGrantingTicket&gt;</code> 区块内包含代理授予票证 IOU（第 3.4 节）的服务响应（第 2.5.2 节）。 如果 HTTP GET 返回任何其他状态代码，但 HTTP 3xx 重定向除外，CAS 必须 响应 <em x-id="3">/服务验证</em> （或 <em x-id="3">/代理验证</em>）请求，并回复不得包含 <code>&lt;cas:proxyGrantingTicket&gt;</code> 块的服务响应。 CAS 可能会遵循 pgturl 发布的任何 Http 重定向。 但是，在 <code>&lt;proxy&gt;</code> 块验证时提供的识别回调 URL 必须与最初传递给 <em x-id="3">/服务验证</em> （或 <em x-id="3">/代理验证</em>）作为"pgtUrl"参数的 URL 相同。
</p>

<p spaces-before="0">
  该服务在 CAS 响应中收到代理授予票证 IOU，以及代理授予票证和代理回调中的 代理授予票证 IOU 后，将使用代理授予票证 IOU 将代理授予票证 IOU 与验证响应关联代理授予 票证。 然后，该服务将使用代理授予票购买代理 票，如第 2.7 节所述。
</p>

<h3 spaces-before="0">
  2.5.5. <em x-id="3">/服务验证</em>的 URL 示例
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<pre><code>https://server/cas/serviceValidate?service=http%3A%2F%2Fwww.service.com&...
</code></pre>

<p spaces-before="0">
  确保通过出示主要凭据签发服务票：
</p>

<p spaces-before="0">
  https://server/cas/serviceValidate?service=http%3A%2F%2Fwww.service.com&... ST-1856339-aA5尤夫尔克斯pv8陶1cYQ7&续订=真实
</p>

<p spaces-before="0">
  通过回调网址进行代理：
</p>

<p spaces-before="0">
  https://server/cas/serviceValidate?service=http%3A%2F%2Fwww.service.com&...
</p>

<h2 spaces-before="0">
  2.6. <em x-id="3">/代理验证</em> [CAS 2.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/代理验证</em> 必须执行与/服务验证相同的验证任务，并额外验证代理票证。 <em x-id="3">/代理验证</em> 必须能够同时验证服务票证和代理票证。
</p>

<h3 spaces-before="0">
  2.6.1. 参数
</h3>

<p spaces-before="0">
  <em x-id="3">/代理验证</em> 具有与 <em x-id="3">/服务验证</em>相同的参数要求。 请参阅第 2.5.1 节。
</p>

<h3 spaces-before="0">
  2.6.2. 响应
</h3>

<p spaces-before="0">
  <em x-id="3">/代理验证</em> 将返回附录 A 中的 XML 示例中描述的 XML 格式 CAS 服务响应，下面 示例响应：
</p>

<p spaces-before="0">
  关于票证验证成功：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
  &lt;cas:authenticationSuccess&gt;
      &lt;cas:user&gt;用户名&lt;/cas:user&gt;
      &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d.&lt;/cas:proxyGrantingTicket&gt;
      &lt;cas:proxies&gt;
          &lt;cas:proxy&gt;https://proxy2/pgtUrl&lt;/cas:proxy&gt;
          &lt;cas:proxy&gt;https://proxy1/pgtUrl&lt;/cas:proxy&gt;
      &lt;/cas:proxies&gt;
  &lt;/cas:authenticationSuccess&gt;
&lt;/cas:serviceResponse&gt;。。
</code></pre>

<p spaces-before="0">
  请注意，当身份验证通过多个代理时，代理的穿越顺序 必须反映在 <cas:proxies> 块。 最近访问的代理必须是列出的第一个代理，并且当添加新的代理时，其他代理的所有 都必须向下移动。 在上述示例中，首先访问了 <em x-id="3">https://proxy1/pgtUrl</em> 识别的服务，该服务与 <em x-id="3">https://proxy2/pgtUrl</em>识别的服务进行了近似认证。
</p>

<p spaces-before="0">
  关于票证验证失败：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
  &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
      票PT-1856376-1HMgO86Z2ZKeBcc5XdYD未识别
  &lt;/cas:authenticationFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<h3 spaces-before="0">
  2.6.3 <em x-id="3">/代理验证</em>的 URL 示例
</h3>

<p spaces-before="0">
  <em x-id="3">/代理验证</em> 接受与 <em x-id="3">/服务验证</em>相同的参数。 有关使用示例，请参阅第 2.5.5 节，将 "代理验证"替换为"服务验证"。
</p>

<h2 spaces-before="0">
  2.7. <em x-id="3">/代理</em> [CAS 2.0]
</h2>

<p spaces-before="0">
  <em x-id="3">/代理</em> 为已获得代理授予票证的服务提供代理票证，并将 身份验证代理到后端服务。
</p>

<h3 spaces-before="0">
  2.7.1. 参数
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数必须指定为/代理。 它们都对病例敏感。
</p>

<ol start="1">
  <li>
    <code>pgt [REQUIRED]</code> - 服务在服务票证或代理票证验证期间获得的代理授予票证
  </li>
  
  <li>
    <code>目标服务 [REQUIRED]</code> - 后端服务的服务标识符。 请注意，并非所有后端服务都 网络服务，因此此服务标识符并不总是 URL。 但是，此处指定的服务标识符必须 匹配代理票证验证后指定给/代理验证的"服务"参数。
  </li>
</ol>

<h3 spaces-before="0">
  2.7.2. 响应
</h3>

<p spaces-before="0">
  <em x-id="3">/代理</em> 将返回 XML 格式的 CAS 服务响应，如附录 A 中的 XML 示例中所述。下面 示例响应：
</p>

<p spaces-before="0">
  应请求成功：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
    &lt;cas:proxySuccess&gt;
        &lt;cas:proxyTicket&gt;PT-1856392-b98xZrQN4p90Asrw96c8&lt;/cas:proxyTicket&gt;
    &lt;/cas:proxySuccess&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<p spaces-before="0">
  请求失败：
</p>

<pre><code>&lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
  &lt;cas:proxyFailure code="INVALID_REQUEST"&gt;
      "pgt"和"目标服务"参数都需要
  &lt;/cas:proxyFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<h3 spaces-before="0">
  2.7.3. 错误代码
</h3>

<p spaces-before="0">
  以下值可用作身份验证失败响应的"代码"属性。 以下是所有 CAS 服务器必须实施的最小错误代码集 。 实施可能包括其他实施。
</p>

<ol start="1">
  <li>
    <code>INVALID_REQUEST</code> - 并非所有所需的请求参数都存在
  </li>
  
  <li>
    <code>BAD_PGT</code> -提供的pgt无效
  </li>
  
  <li>
    <code>INTERNAL_ERROR</code> - 票证验证过程中发生了内部错误
  </li>
</ol>

<p spaces-before="0">
  对于所有错误代码，建议 CAS 提供更详细的消息，作为 XML 响应 <code>&lt;cas:authenticationFailure&gt;</code> 块的主体。
</p>

<h3 spaces-before="0">
  2.7.4. <em x-id="3">/代理</em>的网址示例
</h3>

<p spaces-before="0">
  简单的代理请求：
</p>

<pre><code>https://server/cas/proxy?targetService=http%3A%2F%2Fwww.service.com&格特。。。
</code></pre>

<h1 spaces-before="0">
  3. 中科院实体
</h1>
<h2 spaces-before="0">
  3.1. 服务票
</h2>

<p spaces-before="0">
  服务票证是客户用作访问服务的凭据的不透明字符串。 服务票是在客户出示凭据和服务标识符后从 CAS 获得的， <em x-id="3">/登录</em> 如第 2.2 节所述。
</p>

<h3 spaces-before="0">
  3.1.1. 服务票属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      服务票证仅适用于生成时指定为/登录的服务标识符。 服务标识符不应是服务票的一部分。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      服务票必须仅适用于一次机票验证尝试。 无论验证是否成功， CAS 必须使票证失效，导致该票证的所有未来验证尝试失败。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      CAS 应在未验证的服务票签发后的合理时间内过期。 如果服务 显示验证过期的服务票证，CAS 必须以验证失败响应进行响应。 建议验证响应包括解释验证失败原因的描述性消息。 建议服务票在到期前的有效期不超过五分钟。 当地安全和 CAS 使用考虑可能决定未验证服务票的最佳使用寿命。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      服务票必须包含足够的安全随机数据，以便票证不可猜测。
    </p>
  </li>
  
  <li>
    服务票必须从字符"ST-"开头。
  </li>
  
  <li>
    服务必须能够接受长达32个字符的服务票证。 建议服务 长度高达 256 个字符的支持服务票。
  </li>
</ol>

<h2 spaces-before="0">
  3.2. 代理票证
</h2>

<p spaces-before="0">
  代理票证是一个不透明的字符串，服务用作凭据，以 代表客户端访问后端服务。 代理票证是在服务提供有效的代理授权票证 （第 3.3 节）以及其连接的后端服务的服务标识符后从 CAS 获得的。
</p>

<h3 spaces-before="0">
  3.2.1. 代理票证属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      代理票证仅适用于生成时指定给/代理的服务标识符。 服务标识符不应是代理票证的一部分。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理票证仅适用于一次票证验证尝试。 无论验证是否成功， CAS 必须使票证失效，导致该票证的所有未来验证尝试失败。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      CAS 应在未验证的代理票签发后的合理时间内过期。 如果服务用于验证过期的代理票证，CAS 必须以验证失败响应进行响应。 建议验证响应包括解释验证失败原因的描述性消息。 建议代理票证在过期前的有效期不超过五分钟。 本地安全和 CAS 使用考虑可能决定未验证代理票的最佳使用寿命。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理票证必须包含足够的安全随机数据，以便票证不可猜测。
    </p>
  </li>
  
  <li>
    代理票应该从字符"PT-"开始。
  </li>
  
  <li>
    代理票必须从字符"ST-"或"PT-"开始。
  </li>
  
  <li>
    后端服务必须能够接受长度高达 32 个字符的代理票证。 建议后端服务支持长度高达 256 个字符的代理票证。
  </li>
</ol>

<h2 spaces-before="0">
  3.3. 代理授予票证
</h2>

<p spaces-before="0">
  代理授予票证是服务用于获取代理票证的不透明字符串，用于获取代表客户端访问后端服务 的代理票证。 代理授权票是在验证 服务票或代理票证后从 CAS 获得的。 代理授权票的发放完全在第 2.5.4 节中描述。
</p>

<h3 spaces-before="0">
  3.3.1. 代理授予票证属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      代理授予的门票可能被服务用于获取多个代理票证。 代理赠与票不是一次性使用门票。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理授权票证必须在认证被接近的客户注销 CAS 时过期。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理授予的门票必须包含足够的安全随机数据，以便通过蛮力攻击在合理 段时间内无法猜出票证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理授权票应该从字符"PGT-"开始。
    </p>
  </li>
  
  <li>
    服务必须能够处理长达 64 个字符的代理授予票证。 建议服务支持长度高达 256 个字符的代理授予票证。
  </li>
</ol>

<h2 spaces-before="0">
  3.4. 代理授予票 IOU
</h2>

<p spaces-before="0">
  代理授予票证 IOU 是一个不透明的字符串，放置在 <em x-id="3">/服务验证</em> 提供的响应中， <em x-id="3">/代理验证</em> 用于将服务票证或代理票证验证与特定代理授予 票证关联。 有关此过程的完整描述，请参阅第 2.5.4 节。
</p>

<h3 spaces-before="0">
  3.4.1. 代理授予票证 IOU 属性
</h3>

<ol start="1">
  <li>
    <p spaces-before="0">
      代理授予票证 IOUs 不应包含对其关联的代理授予票证的任何引用。 鉴于特定的 PGTIOU，一定不可能在 合理的时间内通过算法方法得出相应的 PGT。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理授予票证 IO 必须包含足够的安全随机数据，以便通过蛮力攻击在 合理的时间内无法猜测票证。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      代理授予票证 IOU 应从字符"PGTIOU-"开头。
    </p>
  </li>
  
  <li>
    服务必须能够处理长度高达 64 个字符的 PGTIO。 建议服务支持长度高达 256 个字符的 PGTIO。
  </li>
</ol>

<h2 spaces-before="0">
  3.5. 登录票证
</h2>

<p spaces-before="0">
  登录票证是由 <em x-id="3">/登录</em> 作为凭据请求者提供的字符串，并传递给 <em x-id="3">/登录</em> 作为用户名/密码身份验证的 凭据接受者。 其目的是防止因 Web 浏览器中的错误而重播 凭据。
</p>

<h3 spaces-before="0">
  3.5.1. 登录票证属性
</h3>

<ol start="1">
  <li>
    /登录签发的登录票必须具有概率上的独特性。
  </li>
  
  <li>
    <p spaces-before="0">
      登录票证必须仅适用于一次身份验证尝试。 无论认证是否成功，CAS 必须使登录票无效，导致该 登录票证的所有未来身份验证尝试失败。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      登录票应该从字符"LT-"开始。
    </p>
  </li>
</ol>

<h2 spaces-before="0">
  3.6. 赠票饼干
</h2>

<p spaces-before="0">
  赠票饼干是中科院在建立单一签到会话时设置的 Http 饼干[5] 。 此 Cookie 保持客户端的登录状态，虽然它有效，但客户端可以将其提交给 CAS 以代替主要凭据 。 服务可以通过第 2.1.1、2.4.1 和 2.5.1 节中描述的"续订"参数选择退出单个登录。
</p>

<h3 spaces-before="0">
  3.6.1. 赠票饼干属性
</h3>

<ol start="1">
  <li>
    赠票 Cookie 必须在客户端的浏览器会话结束时设置为过期。
  </li>
  
  <li>
    <p spaces-before="0">
      CAS必须设置饼干路径尽可能限制。 例如，如果 CAS 服务器设置在 路径/cas 下，则必须将 Cookie 路径设置为/cas。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      赠票 Cookie 的价值必须包含足够的安全随机数据，以便在合理的时间内 无法猜出赠票 Cookie。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      赠票饼干的价值应从字符"TGC-"开始。
    </p>
  </li>
</ol>

<h2 spaces-before="0">
  3.7. 票证和票授予饼干字符集
</h2>

<p spaces-before="0">
  除上述要求外，所有 CAS 门票和赠票 Cookie 的价值必须仅包含集 {A-Z、a-z、0-9 和连字符？-'}的 字符。
</p>

<h1 spaces-before="0">
  附录 A： CAS 响应 XML 模式
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
  附录 B：安全重定向
</h1>

<p spaces-before="0">
  成功登录后，必须小心处理将客户端从 CAS 安全地重定向到其最终目的地。 在大多数情况下，客户端已通过 POST 请求向 CAS 服务器发送凭据。 根据此规范，CAS 服务器必须向用户转发 GET 请求的应用程序。
</p>

<p spaces-before="0">
  HTTP/1.1 RFC[3] 提供 303 的响应代码：见其他，它提供了所需的行为： 通过 POST 请求接收数据的脚本可以通过 303 重定向将浏览器通过 GET 请求转发到另一个 URL。 但是，并非所有浏览器都正确实现了此行为。
</p>

<p spaces-before="0">
  因此，建议的重定向方法是 Java 脚本。 包含窗口.位置.href 的页面以以下 方式充分执行：
</p>

<pre><code>&lt;html&gt;
    &lt;head&gt;
        &lt;title&gt;耶鲁中央认证服务&lt;/title&gt;
        &lt;script&gt;
            窗口. 位置. href] "https://portal.yale.edu/Login?ticket=ST-..."mce_href="https://portal.yale.edu/Login?ticket=ST-..."：
       &lt;/script&gt;
    &lt;/head&gt;
    &lt;body&gt;
        &lt;noscript&gt;
            &lt;p&gt;CAS登录成功。&lt;/p&gt;
            &lt;p&gt;  点击此处 &lt;a xhref="https://portal.yale.edu/Login?ticket=ST-..." mce_href="https://portal.yale.edu/Login?ticket=ST-..."&gt;&lt;/a&gt;
            访问您请求的服务。&lt;br /&gt;  &lt;/p&gt;
        &lt;/noscropt&gt;
    &lt;/body&gt;
&lt;/html&gt;
</code></pre>

<p spaces-before="0">
  此外，CAS 应通过设置所有与缓存相关的所有标题来禁用浏览器缓存：
</p>

<pre><code>Pragma：无缓存
缓存控制：无存储
过期：[RFC 1123[6] 日期等于或之前]
</code></pre>

<p spaces-before="0">
  登录票证的引入消除了 CAS 接受通过浏览器缓存和重播 的凭据的可能性。 然而，苹果的Safari浏览器的早期版本包含一个错误，通过使用后退按钮， Safari可能被迫将客户的凭据呈现给它试图访问的服务。 CAS 可以防止 这种行为，如果它检测到远程浏览器是 Safari 的早期版本之一，则不会自动重定向。 相反，CAS 应显示一个表示登录成功的页面，并提供指向所请求的服务的链接。 然后，客户端必须手动单击才能继续。
</p>

<p spaces-before="0">
  附录C：引用 [1] 布拉德纳，S.，"关键词用于RFC，以指示要求水平"，RFC 2119，哈佛大学，1997年3月。 [2] 伯纳斯-李，T.，菲尔丁，R.，弗莱斯蒂克，H.，"超字转移协议 - HTTP/1.0"，RFC 1945，麻省理工学院/LCS，加州大学欧文分校，麻省理工学院/LCS，1996年5月。 [3] 菲尔丁， R.， 盖蒂斯， J.， 莫卧儿，J.，弗莱斯蒂克，H.，马辛特，洛杉矶，利奇，P.，伯纳斯-李，T.，"超字转移协议 - HTTP/1.1"，RFC 2068，加州大学欧文分校，康柏/W3C，康柏，W3C/麻省理工学院，施乐，微软，W3C/麻省理工学院，1999年6月。 [4] 伯纳斯-李，T.，马辛特，洛杉矶和马卡希尔，M.，"统一资源定位器（URL）"，RFC 1738，欧洲核子研究中心，施乐公司，明尼苏达大学，1994年12月。 [5] 克里斯托尔，D.，蒙图利，洛杉矶，"HTTP国家管理机制"，RFC 2965，贝尔实验室/朗讯技术，Epinions.com，公司，2000年10月。 [6] 布雷登，R.，"互联网主机的要求 - 应用和支持"，RFC 1123，互联网工程工作队，1989年10月。
</p>

<h1 spaces-before="0">
  附录 D： CAS 许可证
</h1>

<p spaces-before="0">
  版权所有 （c） 2000-2005 耶鲁大学。 本软件提供"按是"，任何明示或暗示的保修，包括（但不限于）为特定目的的商户性和健身的隐含 保证，均被明确否认。 耶鲁大学 大学或其员工在 （包括但不限于购买替代产品或服务的费用）的任何直接、间接、偶然、特殊、示范性或相应的损害，都不应承担任何责任。使用损失、数据或利润; 或业务中断）然而，无论在合同中、严格责任或侵权 （包括疏忽或其他方面）在使用本软件时以任何方式发生的原因和任何责任理论，即使事先告知此类损害 可能性。
</p>

<p spaces-before="0">
  允许以源或二进制形式重新分配和使用此软件，无论是否修改，只要 满足以下条件：
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      任何再分配必须包括上述版权通知和免责声明，以及任何相关 文档中的此条件列表，如果可行，则包含在重新分配的软件中。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      任何再分配都必须包括确认，"本产品包括耶鲁大学开发的软件"，\\ 在任何相关文档中，如果可行的话，在再分配的软件中。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      不得使用"耶鲁大学"和"耶鲁大学"来认可或推广源自此软件的产品。
    </p>
  </li>
</ol>

<h1 spaces-before="0">
  附录E：本文件的更改
</h1>

<p spaces-before="0">
  2005年5月4日：v1.0 - 初始版本
</p>
