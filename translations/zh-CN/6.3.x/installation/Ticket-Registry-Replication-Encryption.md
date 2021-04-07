---
layout: 默认
title: CAS-配置票证组件
category: 售票处
---

# 票证注册表复制加密

以下票证注册表通过对票证进行加密和签名，可以支持安全票证复制

* [淡褐色](../ticketing/Hazelcast-Ticket-Registry.html)
* [高速缓存](../ticketing/Ehcache-Ticket-Registry.html)
* [点燃](../ticketing/Ignite-Ticket-Registry.html)
* [CouchDb](../ticketing/CouchDb-Ticket-Registry.html)
* [记忆快取](../ticketing/Memcached-Ticket-Registry.html)
* [雷迪斯](../ticketing/Redis-Ticket-Registry.html)
* [MongoDb](../ticketing/MongoDb-Ticket-Registry.html)

<div class="alert alert-info"><strong>默认行为</strong><p>使用上述票证注册表时，默认情况下，加密功能为
 在使用它之前，需要进行明确的配置。</p></div>

## 配置

每个票证注册表配置都支持需要由部署者配置的密码组件。 可以通过CAS设置来控制用于密码的设置，算法和秘密密钥。 请参阅为每个注册表分配的设置，以了解有关票证加密的更多信息。

此外， [Ignite](../ticketing/Ignite-Ticket-Registry.html) 配置为使用TLS进行复制传输。
