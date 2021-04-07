---
layout: 违约
title: 中科院 - 中科院协议规范
category: 协议
---

<a name="headTop"></p>
<h1 spaces-before="0">
  <em x-id="3">CAS 协议 3.0 规范</em>
</h1>
<h1 spaces-before="0">
  <strong x-id="1">作者， 版本</strong>
</h1>

<p spaces-before="0">
  作者：德鲁·马祖雷克
</p>

<p spaces-before="0">
  贡献：
</p>

<ul>
  <li>
    苏珊·布拉姆霍尔
  </li>
  <li>
    霍华德·吉尔伯特
  </li>
  <li>
    安迪·纽曼
  </li>
  <li>
    安德鲁·彼得罗
  </li>
  <li>
    罗伯特·奥施瓦尔德 [CAS 3.0]
  </li>
  <li>
    米萨格·莫耶德
  </li>
</ul>

<p spaces-before="0">
  版本： 3.0.3
</p>

<p spaces-before="0">
  发布日期： 2017-12-01
</p>

<p spaces-before="0">
  版权所有 &copy; 2005年耶鲁大学
</p>

<p spaces-before="0">
  版权所有 &copy; 2017年，阿佩雷奥公司。
</p>

<p spaces-before="0">

<a name="head1"></p>
<h1 spaces-before="0">
  <strong x-id="1">1. 介绍</strong>
</h1>

<p spaces-before="0">
  这是 CAS 1.0 、2.0 和 3.0 协议的官方规范。
</p>

<p spaces-before="0">
  中央身份验证服务 （CAS） 是一个单一登录/单签入协议 。 它允许用户访问多个应用程序，同时仅向中央 CAS 服务器 应用程序提供其 凭据（如使用和密码）一次。
</p>

<p spaces-before="0">


<a name="head1.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">1.1. 公约 & 定义</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    本文件中的关键词"必须"、"必须"、"必须"、"必须"、"应"、"不应"、" "、"建议"、"可能"和"可选"将 解释为 RFC 2119[<a href="#1">1</a>]中所述。
  </p>
  
  <ul>
    <li>
      <p spaces-before="0">
        "客户端"是指最终用户和/或 Web 浏览器。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "CAS 客户端"是指与 Web 应用程序集成并通过 CAS 协议与 CAS 服务器交互的软件组件。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "服务器"是指中央身份验证服务服务器。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "服务"是指客户端正在尝试访问的应用程序。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "后端服务"是指服务试图代表客户访问 的应用程序。 这也可以称为"目标服务"。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "SSO"是指单个登录。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "SLO"是指单一注销。
      </p>
    </li>
    <li>
      <p spaces-before="0">
        "&lt;LF&gt;"是一个裸线饲料（ASCII值0x0a）。
      </p>
    </li>
  </ul>
  
  <p spaces-before="0">

<a name="head1.2"></p> 
    
    <p spaces-before="0">
      <strong x-id="1">1.2 参考实施</strong>
    </p>
<hr />
    
    <p spaces-before="0">
      Apereo CAS 服务器 （<a href="#8">8</a>） 是《 CAS 协议规范》的官方参考实施。
    </p>
    
    <p spaces-before="0">
      Apereo CAS 服务器 4.x 及以上支持 CAS 协议 3.0 规范。
    </p>
    
    <p spaces-before="0">

<a name="head2"></p>
<h1 spaces-before="0">
  <strong x-id="1">2. 卡斯URI</strong>
</h1>

<p spaces-before="0">
  CAS 是一个基于 HTTP<a href="#2">2</a>[，]<a href="#3">基于 3</a>的基于该协议，要求其每个 组件都可以通过特定的 UURI 访问。 本节将讨论 每个URI：
</p>

<table spaces-before="0">
  <tr>
    <th>
      乌里
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
      证书请求者/接受者
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/注销</code>
    </td>
    
    <td>
      销毁 CAS 会话（注销）
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/验证</code>
    </td>
    
    <td>
      服务票证验证
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/服务验证</code>
    </td>
    
    <td>
      服务票证验证 [CAS 2.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/代理验证</code>
    </td>
    
    <td>
      服务/代理票证验证 [CAS 2.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/代理</code>
    </td>
    
    <td>
      代理票务服务 [CAS 2.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/p3/服务验证</code>
    </td>
    
    <td>
      服务票证验证 [CAS 3.0]
    </td>
  </tr>
  
  <tr>
    <td>
      <code>/p3/代理验证</code>
    </td>
    
    <td>
      服务/代理票证验证 [CAS 3.0]
    </td>
  </tr>
</table>

<p spaces-before="0">

<a name="head2.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.1. /登录作为凭据请求器</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/登录</code> URI 具有两种行为：作为凭据请求者和 凭据接受者。 它通过充当证书 接受者来响应凭据，并以其他方式充当证书请求者。
  </p>
  
  <p spaces-before="0">
    如果客户端已与 CAS 建立了单个登录会话， Web 浏览器会向 CAS 呈现一个包含 票证识别字符串的安全 Cookie。 这块饼干叫做赠票饼干。 如果有效赠票机票的赠票饼干密钥，CAS MAY 签发服务票证，前提是符合本规范 中的所有其他条件。 有关赠票饼干的更多信息，请参阅第 <a href="#head3.6">节 3.6</a> 。
  </p>
  
  <p spaces-before="0">

<a name="head2.1.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.1. 参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可在作为凭据请求人 时传递给 <code>/登录</code> 。 他们都是案件敏感，他们都 必须由 <code>/登录</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [OPTIONAL] - 客户端 尝试访问的应用程序的标识符。 在几乎所有情况下，这将是 应用程序的 URL。 作为 HTTP 请求参数，此 URL 值必须 RFC 3986 第 2.2 节中描述的 URL 编码 [<a href="#4">4</a>] 。 如果未指定 <code>服务</code> ，并且尚未 存在单个登录会话，CAS 应请求用户凭据启动单个 登录会话。 如果未指定 <code>服务</code> 并且已经存在单个登录 会话，CAS 应显示通知客户端 已登录的消息。
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注：强烈建议通过 服务管理工具过滤所有 <code>服务</code> 网址，以便只有授权和已知 客户端应用程序才能使用 CAS 服务器。 让服务管理工具打开，以便 所有应用程序的宽大访问可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议 客户端应用程序只允许 <code>https 等安全协议</code> ，以进一步加强认证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>续订</code> [OPTIONAL] - 如果设置此参数，将 绕过单个登录。 在这种情况下，CAS 将要求客户提交凭据 无论是否与 CAS 存在单个登录会话。 此 参数与 <code>网关</code> 参数不兼容。 重定向到 <code>/登录</code> URI 和登录表单视图以发布到 <code>/登录</code> URI 的服务不应同时设置 <code>续订</code> 和 <code>网关</code> 请求参数。 如果两者都设置，则行为是不确定的。 建议中科院 实施在设置 <code>更新</code> 时忽略</code> 参数 <code>网关。
  建议当 &lt;code>续订</code> 参数时，其值设置为"真实"。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>网关</code> [OPTIONAL] - 如果设置此参数，CAS 不会向客户端 索要凭据。 如果客户端与 CAS 有预先存在的单个登录会话 ，或者是否可以通过 非交互式方式（即信任身份验证）建立单个登录会话，CAS 可能会将 客户端重定向到 <code>服务</code> 参数指定的 URL，并附加有效的 服务票证。 （CAS 还可插话一个咨询页面，告知 客户已进行 CAS 认证。 如果客户端未 与 CAS 进行单次登录会话，并且无法建立非交互式身份验证 ，CAS 必须将客户端重定向到由 <code>服务</code> 参数指定的 URL，而 URL 中不附加"票证"参数。 未指定 <code>服务</code> 参数，设置 <code>网关</code> ，则未确定中科院的行为 。 建议在这种情况下，CAS 请求 凭据，就好像没有指定参数一样。 此参数与续订</code> 参数 <code>不兼容
  。 如果两者都是
  设置的，则行为是不确定的。 建议在设置 &lt;code>网关</code> 参数时，其值 为"真实"。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>方法</code> [选择， CAS 3.0] - 发送响应时</code> 使用 <code>方法。
  虽然原生 HTTP 重定向 （&lt;code>GET</code>） 可以用作默认方法，但需要 <code>POST</code> 响应的 应用程序可以使用此参数来指示方法类型 。 还可以指定 <code>HEADER</code> 方法，以指示 CAS 的最终响应，如 <code>服务</code> 和 <code>票务</code>应以 HTTP 响应标题的形式 退回。  由 CAS 服务器实施来确定 是否支持 <code>POST</code> 或 <code>HEADER</code> 响应。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.1.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.2. 网址/登录示例</strong>
</h3>

<p spaces-before="0">
  简单的登录示例：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?service=http%3A%2F%2Fwww.example.org%2F服务</code>
</p>

<p spaces-before="0">
  不要提示用户名/密码：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?service=http%3A%2F%2Fwww.example.org%2F服务&网关]真实</code>
</p>

<p spaces-before="0">
  始终提示用户名/密码：
</p>

<p spaces-before="0">
  <code>续订&https://cas.example.org/cas/login?service=http%3A%2F%2Fwww.example.org%2F服务=真实</code>
</p>

<p spaces-before="0">
  使用POST响应而不是重定向：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/login?method=POST&服务=http%3A%2F%2Fwww.example.org%2F服务</code>
</p>

<p spaces-before="0">

<a name="head2.1.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.3. 用户名/密码身份验证响应</strong>
</h3>

<p spaces-before="0">
  当 <code>/登录</code> 作为凭据请求者的行为时，响应将因 请求的凭据类型而异。 在大多数情况下，CAS 会通过 显示要求用户名和密码的登录屏幕来响应。 此页面必须 包括一个具有参数的表单，"用户名"、"密码"和"lt"。 表格可能还包括参数"警告"。 如果 <code>服务</code> 指定为 <code>/登录</code>， <code>服务</code> 也必须是表单的参数，包含最初 传递给 <code>/登录</code>的值。 这些参数在第 <a href="#head2.2.1">节22.1</a>中详细讨论。 表格必须通过HTTP POST方法提交给 <code>/登录</code> 然后 作为凭据接受者，在22</a><a href="#head2.2">讨论。</p> 
  
  <p spaces-before="0">

<a name="head2.1.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.4. 信任身份验证响应</strong>
</h3>

<p spaces-before="0">
  信任身份验证将考虑 请求的任意方面作为身份验证的基础。 考虑到当地政策 和所实施的特定认证机制的后勤工作，信任 认证的适当用户体验将高度针对具体部署。
</p>

<p spaces-before="0">
  当 <code>/登录</code> 作为信任身份验证的凭据请求者时，其 行为将取决于它将收到的凭据类型。 如果凭据 有效，CAS 可能会透明地将用户重定向到 服务。 或者，CAS MAY 会显示 提交凭据的警告，并允许客户端确认它希望使用这些凭据。 建议 CAS 实施允许部署人员选择首选 行为。 如果凭据无效或不存在，建议 CAS 向客户端显示身份验证失败的原因，并可能 用户提供替代身份验证手段（例如用户名/密码 身份验证）。
</p>

<p spaces-before="0">

<a name="head2.1.5"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.1.5. 单个登录身份验证响应</strong>
</h3>

<p spaces-before="0">
  如果客户端已与 CAS 建立了单个登录会话，则 客户端将向 <code>/登录</code> 提交其 HTTP 会话 cookie，并且行为将 像第 22.4</a>节 <a href="#head2.2.4">处理。 但是，如果设置 <code>更新</code> 参数 ，则行为将像第 21.3 节 <a href="#head2.1.3">2.13</a> 或 21.4</a> <a href="#head2.1.4">处理。</p> 
  
  <p spaces-before="0">

<a name="head2.2"></p> 
    
    <p spaces-before="0">
      <strong x-id="1">2.2. /登录作为凭据接受者</strong>
    </p>
<hr />
    
    <p spaces-before="0">
      当一组接受的凭据传递给 <code>/登录</code>时， <code>/登录</code> 充当 凭据接受者，其行为在本节中定义。
    </p>
    
    <p spaces-before="0">

<a name="head2.2.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.1. 所有类型的身份验证常见的参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可在作为凭据接受者 时传递给 <code>/登录</code> 。 他们都是案件敏感，他们都必须 处理 <code>/登录</code>。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [OPTIONAL] - 客户端尝试 访问的应用程序的 URL。 作为 HTTP 请求参数，此 URL 值必须按照 RFC 1738 第 2.2 节[<a href="#4">4</a>] 中描述的 进行 URL 编码。 CAS必须在成功认证后将 客户端重定向到此网址。 第224</a>节 <a href="#head2.2.4">详细讨论 。 如果 CAS 服务器在 非开放模式下运行（允许使用 CAS 服务器的服务网址 CAS 服务器内注册），则 CAS 服务器必须拒绝操作，如果出现未经授权的服务 URL，则必须拒绝操作并打印出 有意义的消息。</p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注：强烈建议通过 服务管理工具过滤所有 <code>服务</code> 网址，以便只有授权和已知 客户端应用程序才能使用 CAS 服务器。 让服务管理工具打开，以便 所有应用程序的宽大访问可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议 客户端应用程序只允许 <code>https 等安全协议</code> ，以进一步加强认证客户端。
      </p>
    </blockquote></li> 
    
    <li>
      <p spaces-before="0">
        <code>警告</code> [OPTIONAL] - 如果设置此参数，单个登录不得 透明。 在对客户端进行身份验证以 其他服务之前，必须提示客户端。
      </p>
    </li>
    
    <li>
      <p spaces-before="0">
        </code> [OPTIONAL] <code>方法 - 发送响应时</code> 使用 <code>方法。 详情请参阅
  节 &lt;a href="#head2.1.1">2.1.1&lt;/a>&lt;/p>&lt;/li>
&lt;/ul>

&lt;p spaces-before="0">&lt;a name="head2.2.2">&lt;/p>

&lt;h3 spaces-before="0">&lt;strong x-id="1">2.2.2. 用户名/密码身份验证参数&lt;/strong>&lt;/h3>

&lt;p spaces-before="0">除了第 &lt;a href="#head2.2.1">22.1&lt;/a>节中指定的可选参数外，
以下 HTTP 请求参数必须传递给 &lt;code>/登录</code> ，同时 作为用户名/密码身份验证的凭据接受者。 他们 所有案件敏感。
      </p>
      <ul>
        <li>
          <p spaces-before="0">
            <code>用户名</code> [REQUIRED] - 尝试登录的客户端的用户名
          </p>
        </li>
        <li>
          <p spaces-before="0">
            <code>密码</code> [REQUIRED] - 正在尝试登录的客户端的密码
          </p>
        </li>
        <li>
          <p spaces-before="0">
            <code></code> [OPTIONAL] -登录票。 这是作为登录表格的一部分 在21.3</a><a href="#head2.1.3">节讨论。 登录票证本身 <a href="#head3.5">3.5</a>节讨论。</p></li> 
            
            <li>
              <p spaces-before="0">
                <code>记住我</code> [选择， CAS 3.0] - 如果设置此参数， CAS 服务器可能会创建长期 票务授予票证 （称为 记住 - 我支持）。 是否支持 长期门票发放票证，都取决于 CAS 服务器配置。
              </p>
            </li></ul>

<blockquote spaces-before="0">
  <p spaces-before="0">
    注意：当长期门票发放（记住我）得到CAS服务器 支持时，必须考虑安全考虑因素。 这对于 示例包括共享计算机的使用情况。 在 CAS 客户端系统中，处理"记住-我"登录不同可能是 必要的。 详情请参阅第 <a href="#head4.1">节 4.1</a> 。
  </p>
</blockquote>

<p spaces-before="0">

<a name="head2.2.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.3. 信任身份验证参数</strong>
</h3>

<p spaces-before="0">
  信任身份验证没有所需的 HTTP 请求参数。 信任 身份验证可能基于 HTTP 请求的任何方面。
</p>

<p spaces-before="0">

<a name="head2.2.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.2.4. 响应</strong>
</h3>

<p spaces-before="0">
  当 <code>/登录</code> 作为凭据接受者 操作时，必须提供以下响应之一。
</p>

<ul>
  <li>
    <p spaces-before="0">
      成功登录：将客户端重定向到 <code>服务</code> 参数指定的 URL，不会导致客户的凭据 转发到 <code>服务</code>。 此重定向必须导致客户 向 <code>服务</code>发出 GET 请求。 请求必须包括有效的服务 票，作为HTTP请求参数"票证"传递。 有关更多信息，请参阅附录 B</a> <a href="#head_appdx_b">。 如果未指定 <code>服务</code> ，CAS 必须 显示一条消息，通知客户端，它已成功 启动了单个登录会话。</p></li> 
      
      <li>
        <p spaces-before="0">
          登录失败：返回到 <code>/登录</code> 作为凭据请求器。 在这种情况下，建议 CAS 服务器 向用户显示错误消息， 描述登录失败的原因（例如密码错误、锁定帐户等）， 并酌情为用户提供再次尝试 登录的机会。
        </p>
      </li></ul> 
      
      <p spaces-before="0">

<a name="head2.3"></p> 
        
        <p spaces-before="0">
          <strong x-id="1">2.3. /注销</strong>
        </p>
<hr />
        
        <p spaces-before="0">
          <code>/注销</code> 会破坏客户端的单个登录 CAS 会话。 赠票 Cookie（第 3.6</a>节 <a href="#head3.6">）被销毁，随后 <code>/登录</code> 的请求在用户再次出示主要凭据（并因此 从而建立新的单一登录会话）之前不会 获得服务票证。</p> 
          
          <p spaces-before="0">

<a name="head2.3.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.3.1. 参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可指定为 <code>/注销</code>。 情况 敏感，应由 <code>/注销</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [OPTIONAL， CAS 3.0] - 如果指定了 <code>服务</code> 参数，则 浏览器可能会在 CAS 服务器执行注销后自动重定向到 <code>服务</code> 指定的 URL。 如果通过 CAS 服务器进行重定向，则实际执行取决于服务器配置。 作为 HTTP 请求参数， <code>服务</code> 值必须编码为 RFC 1738 [<a href="#4">4</a>]第 2.2 节中描述的 。
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注：强烈建议通过 服务管理工具过滤所有 <code>服务</code> 网址，以便只有授权和已知 客户端应用程序才能使用 CAS 服务器。 让服务管理工具打开，以便 所有应用程序的宽大访问可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议 客户端应用程序只允许 <code>https 等安全协议</code> ，以进一步加强认证客户端。
      </p>
    </blockquote>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注：原CAS 2.0规范中定义的 <code>网址</code> 参数 不再是CAS 3.0的有效参数。 CAS服务器必须忽略给定的 <code>网址</code> 参数。 CAS 客户端可以提供上述 <code>服务</code> 参数， ，因为这样可以确保参数在非开放模式下运行时对注册服务 网址进行验证。 详情请参阅 <a href="#head2.3.2">2.32</a> 。
      </p>
    </blockquote>
  </li>
</ul>

<p spaces-before="0">

<a name="head2.3.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.3.2. 响应</strong>
</h3>

<p spaces-before="0">
  [CAS 1.0，CAS 2.0] <code>/注销</code> 必须显示一个页面，说明用户已 注销。 如果实施"url"请求参数， <code>/注销</code> 还应 提供与第 <a href="#head2.3.1">2.3.1</a>节中描述的提供的 URL 的链接。
</p>

<p spaces-before="0">
  [CAS 3.0] <code>/注销</code> 必须显示一个页面，说明如果没有提供 <code>服务</code> 参数，则用户已 注销。 如果提供具有 编码 URL 值的 <code>服务</code> 请求参数，CAS 服务器在成功登录后 重定向到给定服务 URL。
</p>

<blockquote spaces-before="0">
  <p spaces-before="0">
    注：当 CAS 服务器在非开放模式下运行时（允许的服务网址 CAS 服务器内注册），CAS 服务器必须确保只接受 注册 [service] 参数服务网址进行重定向。 原 CAS 2.0 规范中定义的 <code>网址</code> 参数 不再是 CAS 3.0 中的有效参数。 CAS服务器必须忽略给定的 <code>网址</code> 参数。
  </p>
</blockquote>

<p spaces-before="0">

<a name="head2.3.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.3.3 单个注销</strong>
</h3>

<p spaces-before="0">
  CAS 服务器可能支持单一注销 （SLO）。 SLO 意味着用户不仅从 CAS 服务器获得 ，而且从所有访问过的 CAS 客户端 应用程序中获取。 如果 SLO 得到 CAS 服务器的支持，CAS 服务器必须向本 CAS 会话期间提供给 CAS 的所有服务网址发送包含登录 XML 文档（见 <a href="#head_appdx_c">附录 C</a>）的 HTTP POST 请求， 当用户明确过期时（例如在注销期间）。 不支持 SLO POST 请求的 CAS 客户端必须忽略这些请求。 SLO 请求也可能由 CAS 服务器在 TGT 怠速超时时启动。
</p>

<p spaces-before="0">



<a name="head2.3.3.1"></p>

<h4 spaces-before="0">
  <strong x-id="1">2.3.3.1 服务器行为</strong>
</h4>

<p spaces-before="0">
  CAS 服务器应忽略单个注销帖子上可能出现的所有错误， 向 CAS 客户端应用服务网址请求。 这可确保发送 POST 请求时 的任何错误不会干扰 CAS 服务器性能和 可用性（"开火和忘记"）。
</p>

<p spaces-before="0">



<a name="head2.3.3.2"></p>

<h4 spaces-before="0">
  <strong x-id="1">2.3.3.2 客户行为</strong>
</h4>

<p spaces-before="0">
  处理注销后请求数据由 CAS 客户端决定。 建议 用户从 SLO POST 请求中 发送的服务票证 ID 标识的应用程序中注销用户。 如果客户端支持 SLO POST 请求处理，则客户端应返回 HTTP 成功 状态代码。
</p>

<p spaces-before="0">



<a name="head2.4"></p>

<h2 spaces-before="0">
  <strong x-id="1">2.4. /验证 [CAS 1.0]</strong>
</h2>

<p spaces-before="0">
  <code>/验证</code> 检查服务票的有效性。 <code>/验证</code> 是 CAS 1.0 协议的一部分，因此不处理代理身份验证。 当代理票证传递给 <code>/验证</code>时，CAS 必须以票证验证失败响应 。
</p>

<p spaces-before="0">



<a name="head2.4.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.4.1. 参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可指定为 <code>/验证</code>。 它们 案件敏感，必须全部由 <code>/验证</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [REQUIRED] - 签发机票的服务标识符，如第 2.2.1 节所述。 作为 HTTP 请求参数， <code>服务</code> 值必须编码为 RFC 1738 [<a href="#4">4</a>]第 2.2 节中描述的 。
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注：强烈建议通过 服务管理工具过滤所有 <code>服务</code> 网址，以便只有授权和已知 客户端应用程序才能使用 CAS 服务器。 让服务管理工具打开，以便 所有应用程序的宽大访问可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议 客户端应用程序只允许 <code>https 等安全协议</code> ，以进一步加强认证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>票</code> [REQUIRED] - <code>/登录</code>签发的服务票。 服务票 第 3.1 节中描述的。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>续订</code> [OPTIONAL] - 如果设置此参数，只有在用户 主要凭据的演示中签发服务票证时，票证验证才会成功。 如果从单个 签到会话发出罚单，则将失败。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head2.4.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.4.2. 响应</strong>
</h3>

<p spaces-before="0">
  <code>/验证</code> 将返回以下两个答复之一：
</p>

<p spaces-before="0">
  关于票证验证成功：
</p>

<p spaces-before="0">
  是的<LF>用户名<LF>
</p>

<p spaces-before="0">
  关于票证验证失败：
</p>

<p spaces-before="0">
  没有<LF>
</p>

<p spaces-before="0">



<a name="head2.4.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.4.3. URL 示例/验证</strong>
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/validate?service=http%3A%2F%2Fwww.example.org%2F服务&票=ST-1856339-aA5尤夫尔克斯pv8陶1cYQ7</code>
</p>

<p spaces-before="0">
  确保通过出示主要凭据签发服务票：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/validate?service=http%3A%2F%2Fwww.example.org%2F服务&票=ST-1856339-aA5Yuvrxzpv8陶1cYQ7&续订=真实</code>
</p>

<p spaces-before="0">



<a name="head2.5"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.5. /服务验证 [CAS 2.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/服务验证</code> 检查服务票证的有效性并返回 XML 片段响应。 <code>/服务验证</code> 也必须生成和发出代理授予票，当要求。 <code>/服务验证</code> 如果收到代理票证，不得返回成功的身份验证。 建议如果 <code>/服务验证</code> 收到代理票证，XML 响应中的错误消息应说明验证失败，因为代理票证 传递给 <code>/服务验证</code>。
  </p>
  
  <p spaces-before="0">



<a name="head2.5.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.1. 参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数可指定为 <code>/服务验证</code>。 他们 案件敏感，必须全部由 <code>/服务验证</code>处理。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>服务</code> [REQUIRED] - 签发罚单的服务的标识符，如第 <a href="#head2.2.1">221</a>条所述。 作为 HTTP 请求参数， <code>服务</code> 值必须编码为 RFC 1738 [<a href="#4">4</a>]第 2.2 节中描述的 。
    </p>
<blockquote spaces-before="4">
      <p spaces-before="0">
        注：强烈建议通过 服务管理工具过滤所有 <code>服务</code> 网址，以便只有授权和已知 客户端应用程序才能使用 CAS 服务器。 让服务管理工具打开，以便 所有应用程序的宽大访问可能会增加服务攻击 和其他安全漏洞的风险。 此外，建议 客户端应用程序只允许 <code>https 等安全协议</code> ，以进一步加强认证客户端。
      </p>
    </blockquote>
  </li>
  
  <li>
    <p spaces-before="0">
      <code>票</code> [REQUIRED] - <code>/登录</code>签发的服务票。 服务票 <a href="#head3.1">310</a>节中描述。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>pgturl</code> [OPTIONAL] - 代理回调的 Url 。 讨论第 <a href="#head2.5.4">节254</a>。 作为 HTTP 请求参数，"pgtUrl"值必须按照 RFC 1738 [<a href="#4">4</a>]第 2.2 节中描述的 进行 URL 编码。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>续订</code> [OPTIONAL] - 如果设置此参数，只有在用户 主要凭据的演示中签发服务票证时，票证验证才会成功。 如果从单个 签到会话发出罚单，则将失败。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>格式</code> [OPTIONAL] - 如果设置此参数，必须根据参数值生成票证验证响应 。 支持的值 <code>XML</code> 和 <code>JSON</code>。 如果不设置此参数，将使用默认 <code>XML</code> 格式。 如果 CAS 服务器不支持参数值，则必须返回 的错误代码，如第 25.3</a>节 <a href="#head2.5.3">所述。</p></li> </ul> 
      
      <p spaces-before="0">

<a name="head2.5.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.2. 响应</strong>
</h3>

<p spaces-before="0">
  <code>/服务验证</code> 将返回 XML 格式的 CAS 服务响应，如 <a href="#head_appdx_a">附录 A</a>中的 XML 模式中描述的 。 以下是示例响应：
</p>

<p spaces-before="0">
  <strong x-id="1">关于票证验证成功：</strong>
</p>

<pre><code class="xml">&lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
 &lt;cas:authenticationSuccess&gt;
  &lt;cas:user&gt;用户名&lt;/cas:user&gt;
  &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d.&lt;/cas:proxyGrantingTicket&gt;
 &lt;/cas:authenticationSuccess&gt;
&lt;/cas:serviceResponse&gt;。。
</code></pre>

<pre><code class="json">•
  "服务响应"：{
    "身份验证成功"：{
      "用户名"："用户名"，
      "代理代理代号"："PGTIOU-84678-8a9d.。"
    [
  }
}
</code></pre>

<p spaces-before="0">
  <strong x-id="1">关于票证验证失败：</strong>
</p>

<pre><code class="xml">&lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
 &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
    票ST-1856339-aA5尤夫尔克斯pv8陶1cYQ7未识别
  &lt;/cas:authenticationFailure&gt;
&lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">•
  "服务响应"：{
    "认证错误"：{
      "代码"："INVALID_TICKET"，
      "描述"："票ST-1856339-aA5Yuvrxzzpv8Tau1cYQ7未识别"
    =
  =
}
</code></pre>

<p spaces-before="0">
  有关代理响应，请参阅第 <a href="#head2.6.2">节 2.62</a>。
</p>

<p spaces-before="0">

<a name="head2.5.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.3. 错误代码</strong>
</h3>

<p spaces-before="0">
  以下值可用作身份验证 故障响应的"代码"属性。 以下是所有 CAS 服务器必须实施的最小错误代码集。 实施可能包括其他实施。
</p>

<ul>
  <li>
    <p spaces-before="0">
      <code>INVALID_REQUEST</code> - 并非所有所需的请求参数都存在
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_TICKET_SPEC</code> - 不符合验证规范的要求
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>UNAUTHORIZED_SERVICE_PROXY</code> - 该服务未授权执行代理身份验证
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_PROXY_CALLBACK</code> - 指定的代理回调无效。 代理身份验证指定 的凭据不符合安全要求
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_TICKET</code> - 提供的机票无效，或者机票 来自初始登录， <code>续订</code> 已设置为验证。 XML 响应 <code>&lt;cas:authenticationFailure&gt;</code> 块 主体应描述 确切细节。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INVALID_SERVICE</code> - 提供的机票有效，但指定的服务 与机票相关的服务不匹配。 CAS必须 机票无效，并不允许将来对同一张票进行验证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>INTERNAL_ERROR</code> - 票证验证过程中发生了内部错误
    </p>
  </li>
</ul>

<p spaces-before="0">
  对于所有错误代码，建议 CAS 提供更详细的消息 作为 XML 响应 <code>&lt;cas:authenticationFailure&gt;</code> 块的主体。
</p>

<p spaces-before="0">

<a name="head2.5.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.4. 代理回调</strong>
</h3>

<p spaces-before="0">
  如果服务希望将客户端身份验证代理到后端服务，则 必须获得代理授予票证 （PGT）。 此票证的获取 通过代理回调 URL 处理。 此 URL 将独特且安全地识别代理客户身份验证的 服务。 后端服务可以 然后根据代理 识别回调 URL 的服务决定是否接受凭据。
</p>

<p spaces-before="0">
  代理回调机制的工作原理如下：
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      请求代理授权票证 （PGT） 的服务在 初始服务票证或代理票证验证时指定了 HTTP 请求参数 "pgtUrl" <code>/服务验证</code> （或 <code>/代理验证</code>）。 这是一个回调网址， CAS 将连接到该服务以验证服务的身份。 此 URL 必须是 HTTPS，CAS 必须评估终点以建立同行信任。 建立信任至少涉及使用 PKIX 和使用容器信任来 验证 回调网址证书的签名、链条和到期窗口。 代理授权票证的生成或相应的 代理授予票证 IOU 可能会由于代理回调网址未能满足最低 安全要求（如未能在同行之间建立信任或端点 无响应等而失败。 如有故障，将不签发代理授权罚单，CAS 服务响应，如第 2.5.2 节所述， <a href="#head2.5.2">2.5.2</a> 不得包含 <code>&lt;proxyGrantingTicket&gt;</code> 块。 此时， 代理授权机票的签发将停止，服务票证验证将 失败。 否则，该过程将正常进行到第 2 步。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      CAS 使用 HTTP GET 请求将 pgtid</code> <code>HTTP 请求参数传递到 pgturl 端点，并将
 &lt;code>pgtiou</code> 到 pgtUrl 端点。 这些实体分别 <a href="#head3.3">330</a> 和340</a> <a href="#head3.4">讨论。 如果代理回调网址指定了任何参数，则必须保留这些参数 。 CAS 还必须通过验证 GET 请求的响应 HTTP 状态代码 来确保端点可到达，具体步骤#3。 如果 代理服务未能进行身份验证或端点以不可接受的状态 代码响应，代理身份验证必须失败，CAS 必须以适当的错误代码 响应，如第 <a href="#head2.5.3">25.3</a>节所述。</p></li> 
      
      <li>
        <p spaces-before="0">
          如果HTTP GET返回200（OK）的HTTP状态代码，CAS必须响应 <code>/服务验证</code> （或 <code>/代理验证</code>）请求 服务响应 （第25.2 <a href="#head2.5.2">节</a>），包含 <code>&lt;cas:proxyGrantingTicket&gt;</code> 区块内 的代理授权票证IOU（第3.4 <a href="#head3.4"></a>节）。 如果 HTTP GET 返回任何其他状态代码 ，但 HTTP 3xx 重定向除外，CAS 必须响应 <code>/服务验证</code> （或 <code>/代理验证</code>）请求， 必须包含 <code>&lt;cas:proxyGrantingTicket&gt;</code> 块的服务响应。 CAS 可遵循 <code>pgturl</code>发布的任何 HTTP 重定向。 但是，在 <code>&lt;proxy&gt;</code> 块验证时提供的识别回调 URL 必须与最初 传递给 <code>/服务验证</code> （或 <code>/代理验证</code>）作为 <code>pgtUrl</code> 参数的 URL 相同。
        </p>
      </li>
      
      <li>
        <p spaces-before="0">
          该服务在 CAS 响应中收到代理授予票证 IOU，以及代理授予票证和代理授予票证 IOU 从代理回调中获取，将使用代理授予票证 IOU 将代理授予票证 与验证响应关联。 该服务将 然后使用代理授权票购买代理票证，如 <a href="#head2.7">270</a>节所述 。
        </p>
      </li></ol> 
      
      <p spaces-before="0">



<a name="head2.5.5"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.5. 属性 [CAS 3.0]</strong>
</h3>

<p spaces-before="0">
  [CAS 3.0]回复文档可能包括可选文件 <cas:attributes> 用于其他身份验证和/或用户属性的元素。 详情请参阅附录 A</a> <a href="#head_appdx_a">。</p> 
  
  <p spaces-before="0">

<a name="head2.5.6"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.6. 网址示例/服务验证</strong>
</h3>

<p spaces-before="0">
  简单的验证尝试：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/serviceValidate?service=http%3A%2F%2Fwww.example.org%2F服务&票=ST-1856339-aA5尤夫尔克斯pv8Tau1cYQ7</code>
</p>

<p spaces-before="0">
  确保通过出示主要凭据签发服务票：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/serviceValidate?service=http%3A%2F%2Fwww.example.org%2F服务&票=ST-1856339-aA5Yuvrxzpv8陶1cYQ7&续订=真实</code>
</p>

<p spaces-before="0">
  通过回调网址进行代理：
</p>

<p spaces-before="0">
  <code>https://cas.example.org/cas/serviceValidate?service=http%3A%2F%2Fwww.example.org%2F服务&票=ST-1856339-aA5Yuvrxzpv8Tau1cYQ7&pgtUrl=：//www.示例.org%2F服务%2F代理回电</code>
</p>

<p spaces-before="0">



<a name="head2.5.7"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.5.7 带自定义属性的示例响应</strong>
</h3>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
    &lt;cas:authenticationSuccess&gt;
      &lt;cas:user&gt;用户名&lt;/cas:user&gt;
      &lt;cas:attributes&gt;
        &lt;cas:firstname&gt;约翰·&lt;/cas:firstname&gt;
        &lt;cas:lastname&gt;·多伊&lt;/cas:lastname&gt;
        &lt;cas:title&gt;&lt;/cas:title&gt;
        &lt;cas:email&gt;jdoe@example.org&lt;/cas:email&gt;
        &lt;cas:affiliation&gt;&lt;/cas:affiliation&gt;
        &lt;cas:affiliation&gt;教职员工&lt;/cas:affiliation&gt;
      &lt;/cas:attributes&gt;
      &lt;cas:proxyGrantingTicket&gt;PGTIOU-84678-8a9d.&lt;/cas:proxyGrantingTicket&gt;
    &lt;/cas:authenticationSuccess&gt;
  &lt;/cas:serviceResponse&gt;。。
</code></pre>

<pre><code class="json">•
  "服务响应"：{
    "身份验证成功"：{
      "用户"："用户名"，
      "代理代号"："PGTIOU-84678-8a9d.。。"，
      "代理"："https://proxy1/pgtUrl"，"https://proxy2/pgtUrl"，
      "属性"：{
        "第一个名字"："约翰"，
        "从属关系"："员工"、"教师"，
        "头衔"："先生"，
        "电子邮件"："jdoe@example.orgmailto：jdoe@example.org"，
        "姓氏"："Doe"
      [
    ]
  [
]
</code></pre>

<p spaces-before="0">

<a name="head2.6"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.6. /代理验证 [CAS 2.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/代理验证</code> 必须执行与 <code>/服务验证</code> 相同的验证任务，并 额外验证代理票证。 <code>/代理验证</code> 必须能够 验证服务票证和代理票证。 详情请参阅第 <a href="#head2.5.4">节 2.54</a> 。
  </p>
  
  <p spaces-before="0">



<a name="head2.6.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.1. 参数</strong>
</h3>

<p spaces-before="0">
  <code>/代理验证</code> 具有与 <code>/服务验证</code>相同的参数要求。 见 节 <a href="#head2.5.1">25.1</a>。
</p>

<p spaces-before="0">



<a name="head2.6.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.2. 响应</strong>
</h3>

<p spaces-before="0">
  <code>/代理验证</code> 将返回 XML 格式的 CAS 服务响应，如 <a href="#head_appdx_a">附录 A</a>中的 XML 模式 所述。 以下是示例响应：
</p>

<p spaces-before="0">
  对票证验证成功率的响应：
</p>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
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

<pre><code class="json">•
  "服务响应"：{
    "身份验证成功"：{
      "用户名"："用户名"，
      "代理代号"："PGTIOU-84678-8a9d.。"，
      "代理"："https://proxy1/pgtUrl"、"https://proxy2/pgtUrl"=
    [
  ]
}
</code></pre>


<blockquote spaces-before="0">
  <p spaces-before="0">
    注：当身份验证通过多个代理时， 必须反映在 <cas:proxies> 块。 最近访问的代理必须是第一个列出的代理， 所有其他代理必须随着新代理的添加而向下移动。 在上述 中， <a href="https://proxy1/pgtUrl" x-nc="1">https://proxy1/pgtUrl</a> 识别的服务 首先访问，该服务与 <a href="https://proxy2/pgtUrl" x-nc="1">https://proxy2/pgtUrl</a>识别的服务 近似认证。
  </p>
</blockquote>

<p spaces-before="0">
  对票证验证失败的响应：
</p>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'&gt;
      &lt;cas:authenticationFailure code="INVALID_TICKET"&gt;
         票PT-1856376-1HMgO86Z2ZKeBcc5XdYD未识别
      &lt;/cas:authenticationFailure&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">•
  "服务响应"：{
    "认证错误"：{
      "代码"："INVALID_TICKET"，
      "描述"："票PT-1856339-aA5Yuvrxzzpv8Tau1cYQ7不识别"
    =
  =
}
</code></pre>

<p spaces-before="0">

<a name="head2.6.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.3 错误代码</strong>
</h3>

<p spaces-before="0">
  见第 <a href="#head2.5.3">节 2.53</a>
</p>

<p spaces-before="0">



<a name="head2.6.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.6.4 URL 示例/代理验证</strong>
</h3>

<p spaces-before="0">
  <code>/代理验证</code> 接受与 <code>/服务验证</code>相同的参数。 请参阅第 <a href="#head2.5.5">节 25.5</a> ，以用作使用示例，将"代理验证"替换为 "服务验证"。
</p>

<p spaces-before="0">



<a name="head2.7"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">2.7. /代理 [CAS 2.0]</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    <code>/代理</code> 为已获得代理授予 票的服务提供代理票证，并将代理身份验证到后端服务。
  </p>
  
  <p spaces-before="0">


<a name="head2.7.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.1. 参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数必须指定为 <code>/代理</code>。 它们都 个案敏感。
</p>

<ul>
  <li>
    <code>pgt</code> [REQUIRED] - 服务在 服务票证或代理票证验证期间获得的代理授予票证。
  </li>
  <li>
    <code>目标服务</code> [REQUIRED] - 后端服务的服务标识符。 请注意，并非所有后端服务都是 Web 服务，因此此服务标识符 并不总是 URL。 但是，此处指定的服务标识符 必须匹配 <code>服务</code> 参数，该参数在代理票证验证 后指定 <code>/代理验证</code> 。
  </li>
</ul>

<p spaces-before="0">


<a name="head2.7.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.2. 响应</strong>
</h3>

<p spaces-before="0">
  <code>/代理</code> 将返回 XML 格式的 CAS 服务响应文档，如 <a href="#head_appdx_a">附录 A</a>中的 XML 示式中所述。 以下是示例响应：
</p>

<p spaces-before="0">
  请求成功响应：
</p>

<pre><code class="xml">  &lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
      &lt;cas:proxySuccess&gt;
          &lt;cas:proxyTicket&gt;PT-1856392-b98xZrQN4p90Asrw96c8&lt;/cas:proxyTicket&gt;
      &lt;/cas:proxySuccess&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<p spaces-before="0">
  对请求失败的响应：
</p>

<pre><code class="xml">&lt;cas:serviceResponse xmlns:cas="http://www.yale.edu/tp/cas"&gt;
      &lt;cas:proxyFailure code="INVALID_REQUEST"&gt;
          "pgt"和"目标服务"参数都需要
      &lt;/cas:proxyFailure&gt;
  &lt;/cas:serviceResponse&gt;
</code></pre>

<pre><code class="json">{
  "服务响应"：{
    "认证错误"：{
      "代码"："INVALID_REQUEST"，
      "描述"："需要"
    "
  }
}
</code></pre>

<p spaces-before="0">

<a name="head2.7.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.3. 错误代码</strong>
</h3>

<p spaces-before="0">
  以下值可用作身份验证 故障响应</code> 属性 <code>代码。 以下是所有 CAS
服务器必须实施的最小错误代码集。 实施可能包括其他实施。&lt;/p>

&lt;ul spaces="0" level="0" marker="-">
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  &lt;code>INVALID_REQUEST</code> - 并非所有所需的请求参数都存在
</p></li> 

<li>
  <p spaces-before="0">
    <code>UNAUTHORIZED_SERVICE</code> - 服务未经授权执行代理请求
  </p>
</li>

<li>
  <p spaces-before="0">
    <code>INTERNAL_ERROR</code> - 票证验证过程中发生了内部错误
  </p>
</li></ul> 

<p spaces-before="0">
  对于所有错误代码，建议 CAS 提供更详细的消息， 作为 <cas:authenticationFailure> 阻止XML响应。
</p>

<p spaces-before="0">



<a name="head2.7.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.4. 网址示例/代理</strong>
</h3>

<p spaces-before="0">
  简单的代理请求：
</p>

<p spaces-before="0">
  <code>https://server/cas/proxy?targetService=http%3A%2F%2Fwww.service.com&pgt=PGT-490649-W81Y9Sa2vTM7赫达7xNTkezTbg4库西巴</code>
</p>

<p spaces-before="0">


<a name="head3"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.7.4 服务票生命周期影响</strong>
</h3>

<p spaces-before="0">
  CAS 服务器实施可能会在代理票务生成时更新母公司服务票证 （ST） 寿命。
</p>

<p spaces-before="0">
  <strong x-id="1">2.8. /p3/服务验证 [CAS 3.0]</strong>
</p>
<hr />

<p spaces-before="0">
  <code>/p3/服务验证</code> 必须执行与 <code>/服务验证</code> 相同的验证任务，并在 CAS 响应中 额外返回用户属性。 详情请参阅 节 2.5</a> <a href="#head2.5">节和第 <a href="#head2.5.7">节 2.57</a> 。</p> 
  
  <p spaces-before="0">

<a name="head2.8.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.8.1. 参数</strong>
</h3>

<p spaces-before="0">
  <code>/p3/服务验证</code> 具有与 <code>/服务验证</code>相同的参数要求。 见 节 <a href="#head2.5.1">25.1</a>。
</p>

<p spaces-before="0">
  <strong x-id="1">2.9. /p3/代理验证 [CAS 3.0]</strong>
</p>
<hr />

<p spaces-before="0">
  <code>/p3/代理验证</code> 必须执行与 <code>/p3/服务验证</code> 相同的验证任务，并 额外验证代理票证。 见第 <a href="#head2.5">节2.8</a>。
</p>

<p spaces-before="0">

<a name="head2.8.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">2.9.1. 参数</strong>
</h3>

<p spaces-before="0">
  <code>/p3/代理验证</code> 具有与 <code>/p3/服务验证</code>相同的参数要求。 见 节 <a href="#head2.8.1">28.1</a>。
</p>
<h1 spaces-before="0">
  <strong x-id="1">3. 中科院实体</strong>
</h1>

<p spaces-before="0">

<a name="head3.1"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.1. 服务票</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    服务票证是客户用作获得服务的凭据 的不透明字符串。 服务票是根据 客户出示凭据和 <code>/登录</code> 的服务标识符（如第 22</a><a href="#head2.2">节中描述 ）从 CAS 获得的。</p> 
    
    <p spaces-before="0">

<a name="head3.1.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.1.1. 服务票属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      服务票证仅适用于生成时指定 <code>/登录</code> 的服务标识符。 服务标识符不应 服务票的一部分。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务票必须仅适用于一次机票验证尝试。 无论验证是否成功，CAS 都必须使票证失效， 导致该票证的所有未来验证尝试失败。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      CAS 应在合理的时间内到期未验证的服务票， 签发后到期。 如果服务提供过期的服务票证进行 验证，CAS 必须回复验证失败响应。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议验证响应包括描述性消息 解释验证失败的原因。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议服务票的有效期在服务票到期前有效 不超过五分钟。 当地安全和 CAS 使用考虑 可能决定未验证服务票的最佳使用寿命。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务票必须包含足够的安全随机数据，以便票证 不可猜测。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务票必须从字符开始， <code>ST-</code>。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务必须能够接受长达32个字符的服务票证。 建议服务支持长达 256 个字符的服务票， 长度。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.2"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.2. 代理票证</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    代理票证是一种不透明的字符串，服务用作凭据，用于代表客户获取 访问后端服务。 代理机票 服务出示有效代理赠与票证（第 <a href="#head3.3">节 3.3</a>）和 其连接的后端服务标识符后，从 CAS 获得代理票证。
  </p>
  
  <p spaces-before="0">



<a name="head3.2.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.2.1. 代理票证属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      代理票证仅适用于生成时指定为 <code>/代理</code> 的服务标识符。 服务标识符不应是 代理票证的一部分。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理票证仅适用于一次票证验证尝试。 无论验证是否成功，CAS 都必须 票证无效，导致该票证的所有未来验证尝试 失败。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      CAS 应在发行后 的合理期限内到期未验证的代理票证。 如果服务提供验证 过期代理票证，CAS 必须以验证失败响应进行响应。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议验证响应包括描述性消息 解释验证失败的原因。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议代理票证到期前的有效期 不超过五分钟。 本地安全和 CAS 使用考虑 可能决定未验证代理票的最佳使用寿命。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理票证必须包含足够的安全随机数据，以便票证 无法猜测。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理票应该从字符开始， <code>PT-</code>。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      后端服务必须能够接受长达 32 个字符 的代理票证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      建议后端服务支持长度高达 256 字符的代理票证。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.3"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.3. 代理授予票证</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    代理授予票证 （PGT） 是服务用于获取 代理票证以代表客户获取后端服务的不透明字符串。 代理赠与票是在验证服务票证 或代理票证后从 CAS 获得的。 代理发票全款 <a href="#head2.5.4">254</a>。
  </p>
  
  <p spaces-before="0">



<a name="head3.3.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.3.1. 代理授予票证属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      代理授予的门票可能被服务用于获取多个代理 票证。 代理赠与票不是一次性使用门票。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      当身份验证 的客户从 CAS 中近注销时，代理授权票必须过期。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理授权票必须包含足够的安全随机数据，以便通过蛮力 攻击在合理的时间内无法猜测 票证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理赠票应从PGT-</code><code>字符开始。&lt;/p>&lt;/li>
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  服务必须能够处理长达 64
  字符的代理授予票证。&lt;/p>&lt;/li>
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  建议服务支持长度高达 256 个字符的
  代理授予票证。&lt;/p>&lt;/li>
&lt;/ul>

&lt;p spaces-before="0">&lt;a name="head3.4">&lt;/p>

&lt;p spaces-before="0">&lt;strong x-id="1">3.4. 代理授予票 IOU&lt;/strong>&lt;/p>

&lt;hr marker="----------------------------------" />

&lt;p spaces-before="0">代理授予票证 IOU 是一个不透明的字符串，它位于 &lt;code>/服务验证</code> 提供的响应 中， <code>/代理验证</code> 用于将服务 票证或代理票证验证与特定的代理授予票证关联。 有关此过程的完整描述，请参阅 节 2.5.4 <a href="#head2.5.4">2.5.4</a> 。
    </p>
    <p spaces-before="0">



<a name="head3.4.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.4.1. 代理授予票证 IOU 属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      代理授予票证 IOUs 不应包含对其 关联代理授予票证的任何引用。 鉴于特定的 PGTIOU，在 合理的时间段内，不得 可能通过算法方法推导相应的 PGT。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理授予票证 IOUs 必须包含足够的安全随机数据，以便通过蛮力 攻击在合理的时间内无法猜测 票证。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      代理授权票证 IOU 应从字符开始， <code>PGTIOU-</code>。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      服务必须能够处理长度高达 64 个字符的 PGTIO。 建议 服务支持长达 256 个字符的 PGTIO， 长度。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head3.5"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.5. 登录票证</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    登录票证是可选 <em x-id="3"></em> 字符串，可由 <code>/登录</code> 作为凭据请求者 提供，并传递给 <code>/登录</code> ，作为用户名/密码 身份验证的凭据接受者。 其目的是防止因 web 浏览器中的 错误而重播凭据。
  </p>
  
  <p spaces-before="0">


<a name="head3.5.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.5.1. 登录票证属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      由 <code>/登录</code> 签发的登录票必须是概率上独一无二的。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      登录票证必须仅适用于一次身份验证尝试。 无论是否 认证是否成功，CAS 都必须使登录 票证无效，导致该登录票证 的所有未来身份验证尝试失败。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      登录门票应从 <code>中尉</code>的字符开始。
    </p>
  </li>
</ul>

<p spaces-before="0">

<a name="head3.6"></p> 
  
  <p spaces-before="0">
    <strong x-id="1">3.6. 赠票饼干</strong>
  </p>
<hr />
  
  <p spaces-before="0">
    赠票饼干是中科院在 设立单一签到会话时设定的HTTP饼干[<a href="#5">5</a>]。 此 Cookie 保持客户端 登录状态，虽然它有效，但客户端可以将其提交给 CAS 以代替 主要凭据。 服务可以通过 <code>续订</code> 参数选择退出单一登录，该参数 <a href="#head2.1.1">21.1</a>、 <a href="#head2.4.1">24.1</a>、 和 <a href="#head2.5.1">25.1</a>。
  </p>
  
  <p spaces-before="0">



<a name="head3.6.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.6.1. 赠票饼干属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      如果长期支持不活跃（<a href="#head4.1.1">4.1.1</a> ），则赠票 Cookie 应设置为在客户端的 浏览器会话结束时过期（ 4.1.1 ）。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      中科院将饼干路径设置为尽可能严格的限制。 例如， 如果 CAS 服务器设置在路径/cas 下，则将 将 Cookie 路径设置为/cas。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      赠票饼干的价值应包含足够的安全随机数据 ，以便在合理的时间内无法猜测出赠票饼干。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      赠票饼干的名称应以TGC-</code><code>字符开头。&lt;/p>&lt;/li>
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  赠票饼干的价值应遵循与赠票
  相同的规则。 通常，赠票 Cookie 的价值可能包含票证授予
  票本身作为经过验证的单个登录会话的表示。&lt;/p>&lt;/li>
&lt;/ul>

&lt;p spaces-before="0">&lt;a name="head3.7">&lt;/p>

&lt;p spaces-before="0">&lt;strong x-id="1">3.7. 票证和票授予饼干字符集&lt;/strong>&lt;/p>

&lt;hr marker="--------------------------------------------------------" />

&lt;p spaces-before="0">除上述要求外，所有CAS门票和
赠票饼干的价值必须只包含 &lt;code>{A-Z, a-z, 0-9}</code>、 和连字符 <code>-</code>的字符。
    </p>
    <p spaces-before="0">


<a name="head3.8"></p> 
      
      <p spaces-before="0">
        <strong x-id="1">3.8. 赠票票</strong>
      </p>
<hr />
      
      <p spaces-before="0">
        出票票证 （TGT） 是由 CAS 服务器生成的不透明字符串，该字符串是在 <code>/登录</code>成功后颁发的。 此票可以与代表单个签到会话的 状态的赠票 Cookie 挂钩，有效期为服务票、代理授予 票等的 基础和基线。
      </p>
      
      <p spaces-before="0">

<a name="head3.8.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">3.8.1. 出票属性</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      发票服务可用于获取多种服务 门票。 赠票不是一次性门票， 与有效期和到期政策相关联。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      当身份验证 正在管理的客户注销 CAS 时，出票票必须过期。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      出票必须包含足够的安全随机数据，以便通过暴力 攻击在合理的时间内无法猜测 票。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      出票应从TGT-</code><code>字符开始。&lt;/p>&lt;/li>
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  建议在与其他外部资源共享
  时对出票票进行加密，以最大限度地减少安全
  漏洞，因为它们与赠票 Cookie
  相关，并代表身份验证会话。&lt;/p>&lt;/li>
&lt;/ul>

&lt;p spaces-before="0">&lt;a name="head4">&lt;/p>

&lt;h1 spaces-before="0" marker="========================">&lt;strong x-id="1">4. 可选功能&lt;/strong>&lt;/h1>

&lt;p spaces-before="0">&lt;a name="head4.1">&lt;/p>

&lt;p spaces-before="0">&lt;strong x-id="1">4.1 长期门票 - 记住我 [CAS 3.0]&lt;/strong>&lt;/p>

&lt;hr marker="-------------------------------------------------" />

&lt;p spaces-before="0">CAS服务器可能支持长期门票发放（称为
"记住我"功能）。 如果此功能得到 CAS 服务器的支持，则只要 CAS 服务器中的长期票证未过期且浏览器 TGC Cookie 有效，则
可以
对 CAS 服务器进行定期
、非交互式重新登录。&lt;/p>

&lt;p spaces-before="0">&lt;a name="head4.1.1">&lt;/p>

&lt;h3 spaces-before="0">&lt;strong x-id="1">4.1.1 启用记忆-我（登录页）&lt;/strong>&lt;/h3>

&lt;ul spaces="0" level="0" marker="-">
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  CAS服务器必须在登录页面上提供一个复选框，以便记住我
功能。&lt;/p>&lt;/li>
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  默认情况下，复选框必须未选中。&lt;/p>&lt;/li>
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  它必须是用户选择启用记住-我的登录与否。
  见第 &lt;a href="#head2.2.2">节22.2&lt;/a>。&lt;/p>&lt;/li>
&lt;/ul>

&lt;p spaces-before="0">&lt;a name="head4.1.2">&lt;/p>

&lt;h3 spaces-before="0">&lt;strong x-id="1">4.1.2 安全影响&lt;/strong>&lt;/h3>

&lt;p spaces-before="0">启用记忆-我可能有安全隐患。 由于 CAS 身份验证
受限于浏览器，并且当
有效长期 TGT 票证且浏览器
提供的 CAS Cookie 有效时，用户无法进行交互式登录，因此 CAS 客户端必须特别小心，以正确处理记住-我
登录。 必须由 CAS 客户负责决定
是否以及何时可以特别处理记住我 CAS 登录。 见 &lt;a href="#head4.1.3">41.3&lt;/a>。&lt;/p>

&lt;p spaces-before="0">&lt;a name="head4.1.3">&lt;/p>

&lt;h3 spaces-before="0">&lt;strong x-id="1">4.1.3 CAS 验证响应属性&lt;/strong>&lt;/h3>

&lt;p spaces-before="0">由于只有 CAS 客户必须决定如何处理记住-我登录（见
&lt;a href="#head4.2.1">4.2.1&lt;/a>），CAS 服务器必须向 CAS 客户端提供有关
记住-Me 登录的信息。 在这种情况下，此信息必须由 CAS 服务器支持的所有
票证验证方法提供（见第
&lt;a href="#head2.5">节 2.5&lt;/a>、 &lt;a href="#head2.6">2.6&lt;/a> 和 &lt;a href="#head2.8">2.8&lt;/a>）。&lt;/p>

&lt;ul spaces="0" level="0" marker="-">
&lt;li marker="-" level="0" spaces="0" spaces-after-marker="2">&lt;p spaces-before="0">  在服务中验证 XML 响应（见附录 A&lt;/a>&lt;a href="#head_appdx_a">），
记住-Me 登录必须由
&lt;code>长期授权要求使用</code> 属性表示。 此外， <code>是来自新登录</code> 属性可用于决定这是否具有安全 影响。
    </p>
  </li>
  <li>
    <p spaces-before="0">
      在 SAML 验证响应中，记住-我必须由 <code>长期验证要求使用</code> 属性来表示。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head4.1.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.1.4 CAS 客户端要求</strong>
</h3>

<p spaces-before="0">
  如果 CAS 客户需要处理"记住-我"登录特别（例如，在记住的登录号上拒绝访问 CAS 客户端应用程序的 敏感区域），CAS 客户端不得使用 <code>/验证</code> CAS 验证 URL，因为此 URL 不会在验证响应文档中 支持 CAS 属性。
</p>


<h3 spaces-before="0">
  <strong x-id="1">4.1.5 长期出票饼干属性</strong>
</h3>

<p spaces-before="0">
  当 CAS 服务器创建长期 TGT 时，票证授予 cookie 不得在客户端浏览器会话结束时过期，该会话在 36.1</a><a href="#head3.6.1">定义。 相反，赠票饼干应在定义的长期 TGT 票期届满。</p> 
  
  <p spaces-before="0">
    长期票证的终身价值定义由 CAS 服务器实施者决定。 长期门票发放期不得超过3个月。
  </p>
  
  <p spaces-before="0">


<a name="head4.2"></p> 
    
    <p spaces-before="0">
      <strong x-id="1">4.2/萨姆瓦尔特 [CAS 3.0]</strong>
    </p>
<hr />
    
    <p spaces-before="0">
      <code>/</code> 通过SAML 1.1请求 HTTP POST提供的文件检查服务票的有效性。 必须返回 SAML（安全访问标记语言）[<a href="#7">7</a>] 1.1 响应文档。 这允许发布经过验证的 NetID 的其他 信息（属性）。 标记语言 （SAML） 的安全断言描述了一个文档和协议框架，通过该框架，可以 交换 安全断言（如有关先前身份验证行为的断言）。
    </p>
    
    <p spaces-before="0">



<a name="head4.2.1"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.1 参数</strong>
</h3>

<p spaces-before="0">
  以下 HTTP 请求参数必须指定为 <code>/</code>。 他们 都是对病例敏感的。
</p>

<ul>
  <li>
    <code>目标</code> [REQUIRED] - 后端服务的 URL 编码服务标识符。 请注意，作为 HTTP 请求参数，此 URL 值必须 RFC 1738<a href="#4">[4]</a>第 2.2 节中描述的 URL 编码。 此处指定的服务标识符 必须与提供给 <code>/登录</code><code>服务</code> 参数相匹配。 见 节 <a href="#head2.1.1">2.1.1</a>。 <code>目标</code> 服务应使用HTTPS。 SAML 属性不得发布到非 SSL 站点。
  </li>
</ul>

<p spaces-before="0">



<a name="head4.2.2"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.2 HTTP 请求方法和主体</strong>
</h3>

<p spaces-before="0">
  请求/请求必须是HTTP开机自检请求。 请求机构必须是有效 SAML 1.0 或 1.1 请求 XML 文件类型的"文本/xml"。
</p>

<p spaces-before="0">



<a name="head4.2.3"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.3 SAML 请求值</strong>
</h3>

<ul>
  <li>
    <p spaces-before="0">
      <code>请求ID</code> [REQUIRED] - 请求的唯一标识符
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>问题安装</code> [REQUIRED] - 请求的时戳
    </p>
  </li>
  <li>
    <p spaces-before="0">
      <code>samlp： 断言事实</code> [REQUIRED] - 在登录时作为响应 参数获得的有效 CAS 服务票证。 见第 <a href="#head2.2.4">节22.4</a>。
    </p>
  </li>
</ul>

<p spaces-before="0">



<a name="head4.2.4"></p>

<h3 spaces-before="0">
  <strong x-id="1">4.2.4 /山姆验证后请求示例</strong>
</h3>

<pre><code class="bash">开机自检/卡斯/萨姆尔验证？目标=
主机：cas.example.com
内容长度：491
内容类型：文本/xml
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
  <strong x-id="1">4.2.5 萨姆尔响应</strong>
</h3>

<p spaces-before="0">
  CAS服务器对 <code>/</code> 请求的响应。 必须是SAML 1.1响应。
</p>

<p spaces-before="0">
  示例 SAML 1.1 验证响应：
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
            &lt;NameIdentifier&gt;约翰&lt;/NameIdentifier&gt;
            &lt;SubjectConfirmation&gt;
              &lt;ConfirmationMethod&gt;
                骨灰盒：绿洲：名称：tc：SAML：1.0：cm：文物
              &lt;/ConfirmationMethod&gt;
            &lt;/SubjectConfirmation&gt;
          &lt;/Subject&gt;
          &lt;Attribute AttributeName="uid" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;12345&lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
          &lt;Attribute AttributeName="groupMembership" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;
              uugid= 中间件. 工作人员，ou=组，dc=vt，dc=edu
            &lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
          &lt;Attribute AttributeName="eduPersonAffiliation" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;工作人员&lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
          &lt;Attribute AttributeName="accountState" AttributeNamespace="http://www.ja-sig.org/products/cas/"&gt;
            &lt;AttributeValue&gt;活跃&lt;/AttributeValue&gt;
          &lt;/Attribute&gt;
        &lt;/AttributeStatement&gt;
        &lt;AuthenticationStatement AuthenticationInstant="2008-12-10T14:12:14.741Z"
        AuthenticationMethod="urn:oasis:names:tc:SAML:1.0:am:password"&gt;
          &lt;Subject&gt;
            &lt;NameIdentifier&gt;johnq&lt;/NameIdentifier&gt;
            &lt;SubjectConfirmation&gt;
              &lt;ConfirmationMethod&gt;
                骨灰盒：绿洲：名称：tc：SAML：1.0：cm：文物
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
  <strong x-id="1">4.2.5.1 SAML CAS 响应属性</strong>
</h3>

<p spaces-before="0">
  可以在 SAML 响应中提供以下其他属性：
</p>

<ul>
  <li>
    <code>长期授权委托使用</code> - 如果长期票证授予 票（记住-我）由 CAS 服务器支持（见第 <a href="#head4.1">节 4.1</a>）， SAML 响应必须包含此属性，以表示 CAS 客户端的记住登录。
  </li>
</ul>

<p spaces-before="0">


<a name="head_appdx_a"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 A： CAS 响应 XML 模式</strong>
</h1>

<pre><code class="xml">&lt;？xml 版本="1.0"编码="UTF-8"？&gt;
&lt;xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:cas="http://www.yale.edu/tp/cas"
           targetNamespace="http://www.yale.edu/tp/cas"
           elementFormDefault="qualified"&gt;
  &lt;xs:annotation&gt;
    &lt;xs:documentation&gt;
      以下是中央认证服务 （CAS） 版本 3.0 协议响应的模式。&lt;br /&gt;
      这包括以下端点的响应：/服务验证、/代理验证、/p3/服务验证、/p3/代理验证、/代理。&lt;br /&gt;
      此规范可能会发生变化。&lt;br /&gt;

      Schema 版本： 3.0.3&lt;br /&gt;

      历史： CAS 3.0 协议规格的初始版本&lt;br /&gt;
      3.0 初始版本 &lt;br /&gt;
      3.0.3 固定属性成员/xs：任何冲突， 添加文档。&lt;br /&gt;
    &lt;/xs:documentation&gt;
  &lt;/xs:annotation&gt;
  &lt;xs:element name="serviceResponse" type="cas:ServiceResponseType"&gt;
    &lt;xs:annotation&gt;
      &lt;xs:documentation&gt;服务响应。&lt;/xs:documentation&gt;
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
          &lt;xs:documentation&gt;成功验证的用户名。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="attributes" type="cas:AttributesType" minOccurs="0"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;可选属性。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="proxyGrantingTicket" type="xs:string" minOccurs="0"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;可选的PGT。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="proxies" type="cas:ProxiesType" minOccurs="0"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;可选类型的代理。&lt;/xs:documentation&gt;
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
            &lt;xs:documentation&gt;身份验证失败的错误代码。&lt;/xs:documentation&gt;
          &lt;/xs:annotation&gt;
        &lt;/xs:attribute&gt;
      &lt;/xs:extension&gt;
    &lt;/xs:simpleContent&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="ProxySuccessType"&gt;
    &lt;xs:sequence&gt;
      &lt;xs:element name="proxyTicket" type="xs:string"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;PT.&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
    &lt;/xs:sequence&gt;
  &lt;/xs:complexType&gt;
  &lt;xs:complexType name="ProxyFailureType"&gt;
    &lt;xs:simpleContent&gt;
      &lt;xs:extension base="xs:string"&gt;
        &lt;xs:attribute name="code" type="xs:string" use="required"&gt;
          &lt;xs:annotation&gt;
            &lt;xs:documentation&gt;代理故障的错误代码。如果使用长期（记住-我）令牌&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:element name="isFromNewLogin" type="xs:boolean" minOccurs="1" maxOccurs="1"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;真实，如果这是从一个新的交互式登录，&lt;/xs:documentation&gt;
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
          &lt;xs:documentation&gt;正确的。 如果登录来自非交互式登录（例如记住-我），此值为错误或可能被省略。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:element&gt;
      &lt;xs:any minOccurs="0" maxOccurs="unbounded" processContents="lax"&gt;
        &lt;xs:annotation&gt;
          &lt;xs:documentation&gt;任何用户特定属性元素。 可能包含成员Of或任何其他元素。&lt;/xs:documentation&gt;
        &lt;/xs:annotation&gt;
      &lt;/xs:any&gt;
    &lt;/xs:sequence&gt;
  &lt;/xs:complexType&gt;
&lt;/xs:schema&gt;
</code></pre>

<blockquote spaces-before="0">
  <p spaces-before="0">
    注：CAS服务器实施者可以扩展用户属性（参见 <xs:any> 模式定义），建议使用以下格式形成自定义属性 ：
  </p>
</blockquote>

<pre><code class="xml">&lt;cas:attributes&gt;
    ...
    &lt;cas:[attribute-name]&gt;价值&lt;/cas:[attribute-name]&gt;
&lt;/cas:attributes&gt;
</code></pre>

<blockquote spaces-before="0">
  <p spaces-before="0">
    具有自定义属性的示例响应：
  </p>
</blockquote>

<pre><code class="xml">&lt;cas:attributes&gt;
    &lt;cas:authenticationDate&gt;2015-11-12T09：30：10Z&lt;/cas:authenticationDate&gt;
    &lt;cas:longTermAuthenticationRequestTokenUsed&gt;真实&lt;/cas:longTermAuthenticationRequestTokenUsed&gt;
    &lt;cas:isFromNewLogin&gt;真实&lt;/cas:isFromNewLogin&gt;
    &lt;cas:myAttribute&gt;我的价值&lt;/cas:myAttribute&gt;
&lt;/cas:attributes&gt;
</code></pre>

<p spaces-before="0">

<a name="head_appdx_b"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 B：安全重定向</strong>
</h1>

<p spaces-before="0">
  成功登录后，必须小心处理将客户端从 CAS 安全地重定向到其最终 目的地。 在大多数情况下，客户端已通过 POST 请求向 CAS 服务器发送了 凭据。 根据此规范， CAS 服务器必须向用户提交 GET 请求的应用程序。
</p>

<p spaces-before="0">
  HTTP/1.1 RFC[<a href="#3">3</a>] 提供了 303 的响应代码：见其他响应代码， 提供所需的行为：通过POST 请求接收数据的脚本可以通过 303 重定向将浏览器转发到另一个网址， 通过 GET 请求。 但是，并非所有浏览器都正确地实现了这种行为 。
</p>

<p spaces-before="0">
  因此，推荐的重定向方法是 Java 脚本。 包含 <code>窗口的页面.位置.href 以以下方式</code> 充分执行：
</p>

<pre><code class="html"> &lt;html&gt;
    &lt;head&gt;
        &lt;title&gt;耶鲁大学中央认证服务&lt;/title&gt;
        &lt;script&gt;
            窗口.位置.hrefé"https://portal.yale.edu/Login?ticket=ST-..."
mce_href="https://portal.yale.edu/Login?ticket=ST-..."："
       &lt;/script&gt;
    &lt;/head&gt;
    &lt;body&gt;
        &lt;noscript&gt;
            &lt;p&gt;CAS登录成功。&lt;/p&gt;
            &lt;p&gt;  点击此处 &lt;a xhref="https://portal.yale.edu/Login?ticket=ST-..."
mce_href="https://portal.yale.edu/Login?ticket=ST-..."&gt;&lt;/a&gt;
            访问您请求的服务。&lt;br /&gt;  &lt;/p&gt;
        &lt;/noscript&gt;
    &lt;/body&gt;
 &lt;/html&gt;
</code></pre>

<p spaces-before="0">
  此外，CAS 应通过设置所有与缓存相关的 标题来禁用浏览器缓存：
</p>

<ul>
  <li>
    <p spaces-before="0">
      普拉格玛：无缓存
    </p>
  </li>
  <li>
    <p spaces-before="0">
      缓存控制：无存储
    </p>
  </li>
  <li>
    <p spaces-before="0">
      过期：[RFC 1123]<a href="#6">6</a>]日期等于现在或之前]
    </p>
  </li>
</ul>

<p spaces-before="0">
  登录票证的引入消除了 CAS 接受浏览器缓存和重播 凭据的可能性。 然而，苹果Safari浏览器的早期版本 包含一个错误，通过使用后 按钮，Safari可能会被迫将客户的凭据呈现给它试图访问的 服务。 CAS 可以通过不 自动重定向来防止此类行为，如果它检测到远程浏览器是 Safari 的早期版本之一 。 相反，CAS 应显示一个页面，说明登录 成功，并提供指向所请求服务的链接。 客户端必须 然后手动单击才能继续。
</p>

<p spaces-before="0">



<a name="head_appdx_c"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 C： 注销 XML 文档</strong>
</h1>

<p spaces-before="0">
  当 SLO 得到 CAS 服务器的支持时，它将回拨到在系统中注册的每项 服务，并在 SAML 注销请求 XML 文档之后向 发送 POST 请求：
</p>

<pre><code class="xml">  &lt;samlp:LogoutRequest xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol"
     ID="[RANDOM ID]" Version="2.0" IssueInstant="[CURRENT DATE/TIME]"&gt;
    &lt;saml:NameID xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion"&gt;
      @NOT_USED@
    &lt;/saml:NameID&gt;
    &lt;samlp:SessionIndex&gt;[会话识别器]&lt;/samlp:SessionIndex&gt;
  &lt;/samlp:LogoutRequest&gt;'
</code></pre>

<p spaces-before="0">


<a name="head_appdx_d"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 D： 参考</strong>
</h1>

<p spaces-before="0">
  <a name="1">1997年3月，哈佛大学</a>RFC2119 <a href="http://www.ietf.org/rfc/rfc2119.txt">[1] ，"在RFC中使用的关键词来表示要求 水平"。</p> 
  
  <p spaces-before="0">
    <a name="2">[2] 伯纳斯-李，T.，菲尔丁，R.，弗莱斯蒂克，H.，"超字转移 协议 - HTTP/1.0"， <a href="http://www.ietf.org/rfc/rfc1945.txt">RFC 1945</a>， 麻省理工学院/LCS，加州大学欧文分校，麻省理工学院/LCS，1996年5月。</p> 
    
    <p spaces-before="0">
      <a name="3">[3] 菲尔丁， R.， 盖蒂斯， J. ， 莫古尔， J.， 弗莱斯蒂克， H. ， 马辛特， 洛杉矶， 利奇， P.， 伯纳斯-李， T.， "超字转移协议 - HTTP/1.1"， <a href="http://www.ietf.org/rfc/rfc2068.txt">RFC 2068</a>， 加州大学欧文分校， 康柏/W3C， 康柏， W3C/麻省理工学院， 施乐， 微软， W3C/麻省理工学院， 1999年6月.</p> 
      
      <p spaces-before="0">
        <a name="4">[4] 伯纳斯-李，T.，马辛特，洛杉矶，和马卡希尔，M.，"统一 资源定位器（URL）， <a href="http://www.ietf.org/rfc/rfc1738.txt">RFC 1738</a>， 欧洲核子研究中心，施乐公司，明尼苏达大学，1994年12月。</p> 
        
        <p spaces-before="0">
          <a name="5">[5] 克里斯托尔，华盛顿特区，蒙图利，"HTTP国家管理机制"， <a href="http://www.ietf.org/rfc/rfc2965.txt">RFC 2965</a>，贝尔实验室/朗讯 技术，Epinions.com，公司，2000年10月。</p> 
          
          <p spaces-before="0">
            <a name="6">[6] 布雷登，R.，"互联网主机的要求 - 应用程序和 支持"， <a href="http://www.ietf.org/rfc/rfc1123.txt">RFC 1123</a>，互联网 工程工作队，1989年10月。</p> 
            
            <p spaces-before="0">
              <a name="7">[7] OASIS SAML 1.1 标准，saml.xml.org，2009 年 12 月。</p> 
              
              <p spaces-before="0">
                <a name="8">[8] <a href="http://www.Apereo.org/cas">阿佩雷奥CAS服务器</a> 实施参考</p> 
                
                <p spaces-before="0">


<a name="head_appdx_e"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 E： CAS 许可证</strong>
</h1>

<p spaces-before="0">
  根据一项或多个贡献者许可协议授权给 Apereo。 有关版权所有权 ，请参阅与本作品一起分发的 通知文件。 Apereo 根据 Apache 许可证（ 版本 2.0（"许可证"）向您授权此文件;您不得使用此文件，除非符合许可证 。  您可以在以下地点获得许可证的副本：
</p>

<p spaces-before="0">
  http://www.apache.org/licenses/LICENSE-2.0
</p>

<p spaces-before="0">
  除非适用法律要求或以书面形式同意，否则根据许可证分发 的软件将以"AS IS"为基础分发，无需保证或 任何形式的明示或暗示条件。  请参阅《许可证》下 特定语言管理权限和限制的许可证。
</p>

<p spaces-before="0">


<a name="head_appdx_f"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 F： 耶鲁许可证</strong>
</h1>

<p spaces-before="0">
  版权所有 （c） 2000-2005 耶鲁大学。
</p>

<p spaces-before="0">
  本软件提供"按是"，任何明示或暗示的保修， 包括，但不限于，为特定目的的商户性和 健身的隐含保证，明确不被否认。 在任何情况下， 耶鲁大学或其员工应承担任何直接、间接、偶然、 特殊、示范性或间接损害（包括但不限于购买替代产品或服务的 成本：使用损失、数据或 利润;或业务中断）然而，无论在合同、严格责任或侵权（包括疏忽 或其他方面）在使用本软件的任何方式中引起和基于任何 责任理论的原因和理论，即使 事先告知此类损害的可能性。
</p>

<p spaces-before="0">
  允许以源或二进制形式重新分配和使用此软件，或未经修改 ，但 满足以下条件：
</p>

<ol start="1">
  <li>
    <p spaces-before="0">
      任何再分配都必须包括上述版权通知和免责声明 以及任何相关文档中的此条件列表，如果可行， 重新分配的软件中。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      任何再分配必须在任何相关文档中包括"本产品包括耶鲁大学开发的 软件"，如果 可行，则包含在再分配软件中。
    </p>
  </li>
  
  <li>
    <p spaces-before="0">
      不得使用"耶鲁大学"和"耶鲁大学"来认可或 推广源自此软件的产品。
    </p>
  </li>
</ol>

<p spaces-before="0">



<a name="head_appdx_g"></p>
<h1 spaces-before="0">
  <strong x-id="1">附录 G： 此文档的更改</strong>
</h1>

<p spaces-before="0">
  2005年5月4日：v1.0 - 首次发布为CAS 1.0和CAS 2.0，版权©2005年耶鲁大学
</p>

<p spaces-before="0">
  2012 年 3 月 2 日： v1.0.1 - 固定的 "无作物" 拼写错误。 阿佩罗每惊奇与信贷 法拉兹汗在ASU赶上打字错误。
</p>

<p spaces-before="0">
  2013 年 4 月： v3.0 - CAS 3.0 协议、Apereo 版权、阿帕奇许可证 2.0
</p>

<p spaces-before="0">
  2014 年 1 月： v3.0.1 - 属性发生
</p>

<p spaces-before="0">
  2015 年 9 月： v3.0.2 - 格式参数
</p>

<p spaces-before="0">
  2017 年 12 月： v3.0.3 - 固定服务验证 XSD 模式
</p>
