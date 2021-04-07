---
layout: 默认
title: CAS-服务发现
category: 高可用性
---

# 服务发现

服务发现是基于微服务的体系结构的关键原则之一。 本指南旨在描述内置的CAS支持的选项，这些选项可用于定位节点以实现负载平衡和故障转移。

## 尤里卡服务器发现服务

[Eureka](https://github.com/Netflix/eureka) 是基于REST的服务，主要用于查找服务，以实现负载均衡和中间层服务器的故障转移。 CAS提供了一个基于Eureka的服务发现服务器，该服务器基于 [Spring Cloud Netflix](http://cloud.spring.io/spring-cloud-netflix) 并通过 [Spring Cloud](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)引导。

[有关更多信息，请参见本指南](Service-Discovery-Guide-Eureka.html)

## 领事服务器发现服务

[HashiCorp Consul](https://www.consul.io) 具有多个组件，但总体而言，它是用于发现和配置基础结构中的服务的工具。 它提供了关键功能，例如服务发现，运行状况检查等。

[有关更多信息，请参见本指南](Service-Discovery-Guide-Consul.html)
