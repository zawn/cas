---
layout: 违约
title: CAS - 配置票务组件
category: 票务
---

# 票证注册复制加密

以下票证注册机构可以通过加密和签名票证来支持 的安全票证复制：

* [黑兹尔卡斯特](../ticketing/Hazelcast-Ticket-Registry.html)
* [埃卡奇](../ticketing/Ehcache-Ticket-Registry.html)
* [点燃](../ticketing/Ignite-Ticket-Registry.html)
* [库奇德布](../ticketing/CouchDb-Ticket-Registry.html)
* [梅卡奇德](../ticketing/Memcached-Ticket-Registry.html)
* [雷迪斯](../ticketing/Redis-Ticket-Registry.html)
* [蒙古德布](../ticketing/MongoDb-Ticket-Registry.html)

<div class="alert alert-info"><strong>默认行为</strong><p>默认情况下，当您使用上述票证注册时，
关闭加密。 它需要明确的配置才能使用。</p></div>

## 配置

每个票证注册表配置都支持需要由部署人员配置的密码组件。 用于密码的设置、算法和秘密密钥可以通过 CAS 设置进行控制。 请参阅分配给每个注册表的设置，以了解有关票证加密的更多信息。

此外， [点火](../ticketing/Ignite-Ticket-Registry.html) 可配置为使用 TLS 进行复制传输。
