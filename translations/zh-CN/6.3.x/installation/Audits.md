---
layout: 默认
title: CAS-审核配置
category: 记录 & 审核
---

# 稽核

CAS使用 [Inspektr框架](https://github.com/apereo/inspektr) 进行审计 和统计。 和Spring管理的 `@Aspect`样式方面 粗粒度的执行路径进行非侵入式审核和日志记录，例如Spring管理的bean方法执行。

CAS服务器自动配置所有相关的Inspektr组件。   注入到Inspektr类的所有可用配置选项都可以通过相关的CAS属性提供给部署者。 请注意，CAS的审核记录管理功能支持同时处理多个审核记录目标。 换句话说，您可以选择将审核记录同时路由到数据库和REST端点以及任意数量的基于记录器的目的地。

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#audits)。

## 行政端点

CAS提供了以下端点：

| 终点         | 描述                 |
| ---------- | ------------------ |
| `auditLog` | 提供所有审核日志的JSON表示形式。 |

您可以通过向导航路径添加持续时间语法来指定要返回的日志条目的间隔。 执行查询时， 期和时间中减去此时间间隔。 例如， `/ actuator / auditLog / PT1H` 将仅返回过去一小时的条目。

执行器端点还可以通过POST方法接受JSON对象，该方法包含过滤日志条目所依据的条件。

可以应用以下过滤器：

| 钥匙                   | 价值                                                 |
| -------------------- | -------------------------------------------------- |
| `间隔`                 | `PT1H`， `PT10M`， `P1D`                             |
| `动作已执行`              | `TICKET_GRANTING_TICKET_CREATED`， `SERVICE_TICK。*` |
| `clientIpAddress`    | `111.111.111.111`， `111.111。*`                     |
| `用户名`                | `箱`箱， `箱*`                                         |
| `resourceOperatedOn` | `ST-1 *。`， `TGT-1 - *`                             |

`间隔` 以外的每个过滤器都可以接受要匹配的正则表达式。

## 基于文件的审核

基于文件的审核日志显示在 [Logging](../logging/Logging.html) 配置中 `cas_audit.log` 文件中。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#audits)。

### 样本日志输出

```bash
WHO：org.apereo.cas.support.oauth.authentication.principal.OAuthCredentials@6cd7c975
：提供的凭据：org.apereo.cas.support.oauth.authentication.principal.OAuthCredentials@6cd7c975
操作：AUTHENTICATION_SUCCESS
应用程序：CAS
时间：IST 2013年8月26日星期一12:35:59
客户IP地址：172.16.5.181
服务器IP地址：192.168.200.22

WHO：org.apereo.cas.support.oauth.authentication.principal.OAuthCredentials@6cd7c975
内容：TGT-9-qj2jZKQUmu1gQvXNf7tXQOJPOtROvOuvYAxybhZiVrdZ6pCUwW-cas01.example.org
操作：TICKET_GRANTING_TICKET_CREATED
应用程序：CAS
时机：IP 8月26日12:35:59 DDR ESS ESS 2013年
月18日：IP地址：ADDRESS DDR2地址：ADDRESS
服务器地址：ADDR 26 12:35:59 IST 2013 11 CLI。
```

## 数据库审核

如果打算将数据库用于审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序，请 [本指南](JDBC-Drivers.html)。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-audits)。

## MongoDb审核

如果打算将MongoDb数据库用于审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-mongo</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-audits)。

## Redis审核

如果打算将Redis数据库用于审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-redis</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-audits)。

## CouchDb审核

如果打算将CouchDb数据库用于审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-couchdb</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-audits)。

## Couchbase审核

如果打算将Couchbase数据库用于审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-couchbase</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-audits)。

## DynamoDb审核

如果打算将DynamoDb数据库用于审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-dynamodb</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-audits)。

## REST审核

审计事件也可能是您选择的端点的 `POST` 要激活此功能，请在您的配置中启用以下模块：

```xml
<dependency>
    <groupId>org.apereo.cas</groupId>
    <artifactId>cas-server-support-audit-rest</artifactId>
    <version>${cas.version}</version>
</dependency>
```

HTTP请求的主体是审核记录的JSON表示形式。 要查看CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-audits)。

## 审核事件

在审核日志中跟踪并记录了以下事件：

| 事件                                                  | 行动                                    |
| --------------------------------------------------- | ------------------------------------- |
| `TICKET_GRANTING_TICKET`                            | `CREATED`， `NOT_CREATED`， `DESTROYED` |
| `PROXY_GRANTING_TICKET`                             | `CREATED`， `NOT_CREATED`， `DESTROYED` |
| `SERVICE_TICKET`                                    | `CREATED`， `NOT_CREATED`              |
| `PROXY_TICKET`                                      | `CREATED`， `NOT_CREATED`              |
| `验证`                                                | `SUCCESS`， `FAILED`                   |
| `AUTHENTICATION_EVENT`                              | `已触发`                                 |
| `AUP_VERIFY`                                        | `已触发`                                 |
| `AUP_SUBMIT`                                        | `已触发`                                 |
| `EVALUATE_RISKY_AUTHENTICATION`                     | 不适用                                   |
| `MITIGATE_RISKY_AUTHENTICATION`                     | 不适用                                   |
| `MULTIFACTOR_AUTHENTICATION_BYPASS`                 | 不适用                                   |
| `SAVE_SERVICE`                                      | `SUCCESS`， `FAILURE`                  |
| `SAVE_CONSENT`                                      | `SUCCESS`， `FAILURE`                  |
| `更改密码`                                              | `SUCCESS`， `FAILURE`                  |
| `DELETE_SERVICE`                                    | `SUCCESS`， `FAILURE`                  |
| `SAML2_RESPONSE`                                    | `CREATED`， `FAILED`                   |
| `SAML2_REQUEST`                                     | `CREATED`， `FAILED`                   |
| `OAUTH2_USER_PROFILE`                               | `CREATED`， `FAILED`                   |
| `OAUTH2_ACCESS_TOKEN_REQUEST`                       | `CREATED`， `FAILED`                   |
| `OAUTH2_ACCESS_TOKEN_RESPONSE`                      | `CREATED`， `FAILED`                   |
| `OAUTH2_CODE_RESPONSE`                              | `CREATED`， `FAILED`                   |
| `REST_API_TICKET_GRANTING_TICKET`                   | `CREATED`， `FAILED`                   |
| `REST_API_SERVICE_TICKET`                           | `CREATED`， `FAILED`                   |
| `SERVICE_ACCESS_ENFORCEMENT`                        | `已触发`                                 |
| `DELEGATED_CLIENT`                                  | `SUCCESS`， `FAILURE`                  |
| `SURROGATE_AUTHENTICATION_ELIGIBILITY_VERIFICATION` | `已触发`                                 |
| `SURROGATE_AUTHENTICATION_ELIGIBILITY_SELECTION`    | `已触发`                                 |
