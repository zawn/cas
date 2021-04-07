---
layout: 违约
title: CAS - 审计配置
category: 日志 & 审计
---

# 审计

CAS 将 [Inspektr 框架](https://github.com/apereo/inspektr) 用于审计目的 和统计数据。 Inspektr项目允许对粗粒 执行路径进行非侵入性审计和记录，例如，使用 和春季管理 `@Aspect`式方面的注释执行弹簧管理的豆类方法执行。

CAS 服务器自动配置所有相关的 Inspektr 组件。   所有注入 Inspektr 类的可用配置选项都可以通过相关的 CAS 属性提供给部署人员。 请注意，中科院的审计记录管理功能支持同时处理多个审计记录目的地。 换句话说，您可以选择同时将审计记录路由到数据库和 REST 端点以及基于记录器的任意数量的目的地。

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#audits)。

## 行政终点

CAS 提供以下端点：

| 端点     | 描述                 |
| ------ | ------------------ |
| `审计日志` | 提供所有审计日志的 JSON 表示。 |

您可以通过在导航路径中添加持续时间语法来指定返回日志条目的间隔。 此间隔将从当前 执行查询的日期和时间中减去。 例如， `/执行器/审计日志/PT1H` 将只返回过去一小时的条目。

执行器端点还可以通过包含标准的POST方法接受 JSON 对象，以筛选日志条目。

可应用以下筛选器：

| 钥匙     | 价值                                                  |
| ------ | --------------------------------------------------- |
| `间隔`   | `PT1H`， `PT10M`， `P1D`                              |
| `行动性能` | `TICKET_GRANTING_TICKET_CREATED`， `SERVICE_TICK. *` |
| `客户地址` | `111.11.11.111`， `111.111. *`                       |
| `用户名`  | `卡瑟`， `卡斯`。                                         |
| `资源操作` | `ST-1.*`， `TGT-1-. *`                               |

每个筛选器 `间隔之外，` 可以接受与之匹配的常规表达式。

## 基于文件的审计

基于文件的审计日志显示在 [记录](../logging/Logging.html) 配置中定义的 `cas_audit.log` 文件中。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#audits)。

### 样本日志输出

```bash
世卫组织： org.apereo.cas.support.oauth.authentication.principal.OAuthCredentials@6cd7c975
提供凭据： org.apereo.cas.support.oauth.authentication.principal.OAuthCredentials@6cd7c975
行动： AUTHENTICATION_SUCCESS
申请： CAS
时间： 2013 年 8 月 26 日星期一 12：35：59 IST
客户 IP 地址： 172.16.5.181
服务器 IP 地址： 192.168.200.22

世卫组织：org.apereo.cas.support.oauth.authentication.principal.OAuthCredentials@6cd7c975
：TGT-9-qj2jZKQUmu1gQvXNf7tXQOJPOtROvOuvYAxybhZiVrdZ6pCUwW-cas01.example.org
行动：TICKET_GRANTING_TICKET_CREATED
申请：CAS
时间： 2013年8月26日 星期一 12：35：59 IST
客户 IP 地址： 172.16.5.181
服务器 IP 地址： 192.168.200.22
```

## 数据库审核

如果您打算使用数据库进行审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 审计 - jdbc</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要了解如何配置数据库驱动程序，请 [查看本指南](JDBC-Drivers.html)。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#database-audits)。

## 蒙古银行审计

如果您打算使用 MongoDb 数据库进行审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 审计 - 蒙古</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#mongodb-audits)。

## 雷迪斯审计

如果您打算使用 Redis 数据库进行审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-审计-重新</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#redis-audits)。

## 库奇德布审计

如果您打算使用 CouchDb 数据库进行审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 审计 - 沙发</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchdb-audits)。

## 沙发基地审核

如果您打算使用 Couchbase 数据库进行审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织. apereo. cas</groupId>
    <artifactId>卡斯服务器支持 - 审计 - 沙发基地</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#couchbase-audits)。

## 发电机数据库审计

如果您打算使用 DynamoDb 数据库进行审核功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-审计-动态</artifactId>
    <version>${cas.version}</version>
</dependency>
```

要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#dynamodb-audits)。

## 休息审计

审计事件也可能 `邮政`到您选择的终点。 要激活此功能，请在配置中启用以下模块：

```xml
<dependency>
    <groupId>组织.apereo.cas</groupId>
    <artifactId>卡-服务器-支持-审计-休息</artifactId>
    <version>${cas.version}</version>
</dependency>
```

HTTP 请求的主体是审计记录的 JSON 表示。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#rest-audits)。

## 审计事件

以下事件被跟踪并记录在审计日志中：

| 事件                                                  | 行动                      |
| --------------------------------------------------- | ----------------------- |
| `TICKET_GRANTING_TICKET`                            | `创建`， `NOT_CREATED`， `` |
| `PROXY_GRANTING_TICKET`                             | `创建`， `NOT_CREATED`， `` |
| `SERVICE_TICKET`                                    | `创造`， `NOT_CREATED`     |
| `PROXY_TICKET`                                      | `创造`， `NOT_CREATED`     |
| `认证`                                                | `成功`， `失败`              |
| `AUTHENTICATION_EVENT`                              | `引发`                    |
| `AUP_VERIFY`                                        | `引发`                    |
| `AUP_SUBMIT`                                        | `引发`                    |
| `EVALUATE_RISKY_AUTHENTICATION`                     | 不适用                     |
| `MITIGATE_RISKY_AUTHENTICATION`                     | 不适用                     |
| `MULTIFACTOR_AUTHENTICATION_BYPASS`                 | 不适用                     |
| `SAVE_SERVICE`                                      | `成功`， `失败`              |
| `SAVE_CONSENT`                                      | `成功`， `失败`              |
| `CHANGE_PASSWORD`                                   | `成功`， `失败`              |
| `DELETE_SERVICE`                                    | `成功`， `失败`              |
| `SAML2_RESPONSE`                                    | `创造了`， `失败`             |
| `SAML2_REQUEST`                                     | `创造了`， `失败`             |
| `OAUTH2_USER_PROFILE`                               | `创造了`， `失败`             |
| `OAUTH2_ACCESS_TOKEN_REQUEST`                       | `创造了`， `失败`             |
| `OAUTH2_ACCESS_TOKEN_RESPONSE`                      | `创造了`， `失败`             |
| `OAUTH2_CODE_RESPONSE`                              | `创造了`， `失败`             |
| `REST_API_TICKET_GRANTING_TICKET`                   | `创造了`， `失败`             |
| `REST_API_SERVICE_TICKET`                           | `创造了`， `失败`             |
| `SERVICE_ACCESS_ENFORCEMENT`                        | `引发`                    |
| `DELEGATED_CLIENT`                                  | `成功`， `失败`              |
| `SURROGATE_AUTHENTICATION_ELIGIBILITY_VERIFICATION` | `引发`                    |
| `SURROGATE_AUTHENTICATION_ELIGIBILITY_SELECTION`    | `引发`                    |
