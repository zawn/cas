---
layout: 默认
title: CAS-OAuth用户管理的访问协议
category: 通讯协定
---

# 用户管理的访问协议

用户管理的访问（UMA）是一种轻量级的访问控制协议，该协议定义了集中式工作流程，以允许实体（用户或公司） 管理对其资源的访问。

要了解有关UMA的更多信息，请 [阅读规范](https://docs.kantarainitiative.org/uma/rec-uma-core.html)。

## 配置

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-oauth-uma</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看此功能的CAS属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2-uma)。

## 资源储存

默认情况下，资源定义保留在内存库中。

CAS还提供了由关系数据库 支持的替代实现，以跟踪和管理此类定义。 在CAS属性中激活了存储库选择。

通过在WAR叠加中包含以下依赖项来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-oauth-uma-jpa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 终点

### 请求方令牌

`/oauth2.0/umaJwks` 发出 `GET` 请求，以检索签名公钥。

### 政策规定

#### 创造

发出 `POST` 请求到 `/oauth2.0/${resourceId}/策略` 与有效载荷体为：

```json
{
  “权限”：[{
    “主题”：“ casuser”，
    “作用域”：[“读”，“写”]，
    “声明”：{
        “ givenName”：“ CAS”
      }
    } ]
}
```

#### 删除

发出 `DELETE` 请求为 `/oauth2.0/${resourceId}/ policy /${policyId}`

#### 更新

发出一个 `PUT` 请求，作为 `/oauth2.0/${resourceId}/ policy /${policyId}` ，有效载荷主体作为一个匹配 `POST` 方法的请求。

#### 找

- 发出 `GET` 请求作为 `/oauth2.0/${resourceId}/ policy /` 以获取资源的所有策略定义。
- 发出 `GET` 请求作为 `/oauth2.0/${resourceId}/ policy /${policyId}` 来获取资源的特定策略定义。

### 资源

与资源相关的操作在端点 `/oauth2.0/resourceSet`处处理。

#### 创造

预期的 `POST` 有效负载主体为：

```json
{
  “ uri”：“ ...”，
  “ type”：“ ...”，
  “ name”：“ ...”，
  “ icon_uri”：“ ...”，
  “ resource_scopes”： [“读取”，“写入”]
}
```

#### 删除

发出 `DELETE` 请求为 `${resourceSetEndpoint}/${resourceId}`

#### 更新

发出 `PUT` 请求为 `${resourceSetEndpoint}/${resourceId}` 与有效载荷体作为一个匹配 `POST` 方法。

#### 找

- 发出 `GET` 请求为 `${resourceSetEndpoint}/${resourceId}` 抓取特定资源的定义。
- 发出一个 `GET` 请求作为 `${resourceSetEndpoint}` 来获取所有资源定义。

### 许可票

`/oauth2.0/permission` 发出一个 `POST` 请求，有效载荷主体为：

```json
{
    “权利要求书”：{ “给定名称”： “CAS”}，
    “RESOURCE_ID”：100，
    “resource_scopes”：[ “读”]
}
```

### 索赔收集

使用以下查询参数向 `/oauth2.0/rqpClaims` 发出 `GET`

- `client_id`
- `redirect_uri`
- `票`
- `状态` （可选）

### 发现

UMA发现可通过 `/oauth2.0/.well-known/uma-configuration``GET` 获得。

### 授权

`/oauth2.0/rptAuthzRequest` 发出一个 `POST` 请求，有效载荷主体为：

```json
{
    “ ticket”：“ ...”，
    “ rpt”：“ ...”，
    “ grant_type”：“ ur：ietf：params：oauth：grant-type：uma-ticket”，
    “ claim_token” ：“ ...”，
    “ claim_token_format”：“ ...”
}
```
