---
layout: 违约
title: CAS - 服务发现
category: 高可用性
---

# 服务发现

服务发现是基于微服务的架构的关键原则之一。 本指南旨在描述内置的 CAS 支持选项，可用于定位节点以用于负载平衡和故障转移。

## 尤里卡服务器发现服务

[尤里卡](https://github.com/Netflix/eureka) 是基于RESE的服务，主要用于定位服务，以平衡负载和中端服务器故障转移的目的。 CAS 提供支持尤里卡的服务发现服务器，该服务器基于 [春云 Netflix](http://cloud.spring.io/spring-cloud-netflix) ，并通过 [春云](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)启动。

[有关详细信息，请参阅本指南](Service-Discovery-Guide-Eureka.html) 。

## 领事服务器发现服务

[HashiCorp 领事](https://www.consul.io) 具有多个组件，但总的来说，它是发现和配置基础设施中服务的工具。 它提供了关键功能，如服务发现，健康检查等。

[有关详细信息，请参阅本指南](Service-Discovery-Guide-Consul.html) 。
