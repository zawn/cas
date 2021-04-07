---
layout: 违约
title: CAS - 配置票务组件
category: 票务
---

# 票务

有两个核心可配置票务组件：

* `票务登记` - 提供耐用的门票存储。
* `到期政策` - 为票证到期语义提供政策框架。

## 票务登记处

部署环境和技术专长通常决定票务注册</code> 组件的特定 。 建议在 HA 部署时采用缓存支持的实施，而默认的内存内存注册表可能适用于小型部署。</p>

<p spaces-before="0">要查看 CAS 属性的相关列表，请 <a href="../configuration/Configuration-Properties.html#ticket-registry">查看本指南</a>。</p>

<h3 spaces-before="0">如何选择？</h3>

<p spaces-before="0">菜单上有各种各样的票务登记簿。 选择标准概述如下：</p>

<ul>
<li>选择您最熟悉的技术，并具有排除故障、调整和扩展以赢得胜利的技能和耐心。</li>
<li>选择一种不会强制 CAS 配置与群集中的任何单个服务器/节点绑定的技术，因为这将显示自动缩放问题和手动工作。</li>
<li>选择一种与您的网络和防火墙配置配合良好的技术，并且基于您的网络拓扑技术，该技术性能良好且足够可靠。</li>
<li>选择一种技术，显示有希望的结果下， <em x-id="3">你的预期负荷</em>，运行 <a href="../high_availability/High-Availability-Performance-Testing.html">性能和压力测试</a>。</li>
<li>选择一种不依赖于外部过程和系统的技术，是自力更生和自我遏制的。</li>
</ul>

<p spaces-before="0">以上只是概述了您可能希望考虑的建议和准则。 每个选项都呈现各种优点和缺点，最终，您必须决定哪些缺点或优势为您提供最佳体验。</p>

<h3 spaces-before="0">基于缓存的机票注册</h3>

<p spaces-before="0">基于缓存的票务登记处提供高性能的解决方案，用于高可用性
部署的门票存储。 提供以下缓存技术的组件：</p>

<ul>
<li><a href="Default-Ticket-Registry.html">违约</a></li>
<li><a href="Hazelcast-Ticket-Registry.html">黑兹尔卡斯特</a></li>
<li><a href="Ehcache-Ticket-Registry.html">埃卡奇</a></li>
<li><a href="Ignite-Ticket-Registry.html">点燃</a></li>
<li><a href="Memcached-Ticket-Registry.html">梅卡奇德</a></li>
<li><a href="Infinispan-Ticket-Registry.html">英菲尼斯潘</a></li>
</ul>

<h3 spaces-before="0">基于消息的票务注册</h3>

<ul>
<li><a href="Messaging-JMS-Ticket-Registry.html">JMS</a></li>
</ul>

<h3 spaces-before="0">RDBMS 票务登记处</h3>

<p spaces-before="0">基于 RDBMS 的票务登记处在多个 CAS 节点之间提供分布式票务商店。
提供以下缓存技术的组件：</p>

<ul>
<li><a href="JPA-Ticket-Registry.html">日本经济与经济、经济、经济</a></li>
</ul>

<h3 spaces-before="0">诺斯QL票务登记处</h3>

<p spaces-before="0">CAS还为各种其他数据库提供支持，包括雷迪斯、蒙戈德和阿帕奇
卡桑德拉，用于存储门票和坚持：</p>

<ul>
<li><a href="Infinispan-Ticket-Registry.html">英菲尼斯潘</a></li>
<li><a href="Couchbase-Ticket-Registry.html">沙发基地</a></li>
<li><a href="Redis-Ticket-Registry.html">雷迪斯</a></li>
<li><a href="CouchDb-Ticket-Registry.html">库奇德布</a></li>
<li><a href="MongoDb-Ticket-Registry.html">蒙古德布</a></li>
<li><a href="DynamoDb-Ticket-Registry.html">迪纳莫德布</a></li>
</ul>

<h3 spaces-before="0">安全缓存复制</h3>

<p spaces-before="0">一些基于缓存的票证注册机构支持在线路上安全复制票证数据，
以便对门票进行加密，并在复制尝试中签名，以防止嗅探和窃听。
<a href="../installation/Ticket-Registry-Replication-Encryption.html">有关详细信息，请参阅本指南</a> 。</p>

<h2 spaces-before="0">票证到期政策</h2>

<p spaces-before="0">CAS 支持一个可插入且可扩展的政策框架，以控制
门票赠与票 （TGT） 和服务票 （ST） 的到期政策。
<a href="Configuring-Ticket-Expiration-Policy.html">有关如何配置到期保单的详细信息，请参阅本指南</a> 。</p>
