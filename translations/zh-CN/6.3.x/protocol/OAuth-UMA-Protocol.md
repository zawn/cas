---
layout: 违约
title: CAS - 非授权用户管理访问协议
category: 协议
---

# 用户管理的访问协议

用户管理访问 （UMA） 是一种轻量级的访问控制协议，它定义了集中的工作流，允许实体（用户或公司） 管理对其资源的访问。

要了解有关 UMA 的更多内容，请 [阅读规范](https://docs.kantarainitiative.org/uma/rec-uma-core.html)。

## 配置

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 奥阿西 - 乌马</artifactId>
  <version>${cas.version}</version>
</dependency>
```

要查看此功能的 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#oauth2-uma)。

## 资源存储

默认情况下，资源定义保存在内存存储库中。

CAS 还提供由关系数据库 支持的另一种实施，以跟踪和管理此类定义。 存储库选择在 CAS 属性中激活。

支持通过在 WAR 叠加中包括以下依赖性来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 奥阿伊特 - 乌马 - 杰帕</artifactId>
  <version>${cas.version}</version>
</dependency>
```

## 端点

### 请求方令牌

发出 `获取` 请求， `/非授权2.0/umaJwks` 检索签名的公共密钥。

### 政策

#### 创造

向有效载荷主体 `/非授权 2.0/${resourceId}/政策` 发出 `POST` 请求：

```json
[
  "权限"：[[
    "主题"："卡瑟"，
    "范围"：["读"，"写"]，
    "索赔"：{
        "给定名称"："CAS"
      }
    }]
}
```

#### 删除

发布 `删除` 请求， `/非授权2.0/${resourceId}/政策/${policyId}`

#### 更新

发布 `将` 请求作为 `/非授权 2.0/${resourceId}/策略/${policyId}` ，有效载荷主体与 `POST` 方法相匹配。

#### 找到

- 发布 `获取` 请求， `/非授权2.0/${resourceId}/政策/` 获取资源的所有策略定义。
- 发布 `获取` 请求， `/非授权2.0/${resourceId}/政策/${policyId}` 获取资源的具体政策定义。

### 资源

资源相关操作在端点 `/非授权 2.0/资源集`处理。

#### 创造

预期 `开机自检` 有效载荷主体是：

```json
{
  "uri"："。。。"，
  "类型"："。。。"，
  "名称"："。。。"，
  "icon_uri"："......"，
  "resource_scopes"："读"，"写"]
}
```

#### 删除

发布 `删除` 请求， `${resourceSetEndpoint}/${resourceId}`

#### 更新

发布 `将` 请求作为有效载荷主体的 `${resourceSetEndpoint}/${resourceId}` ，作为匹配 `POST` 方法的请求。

#### 找到

- 发布 `获取` 请求，作为获取特定资源定义的 `${resourceSetEndpoint}/${resourceId}` 。
- 发布 `获取` 请求，作为获取所有资源定义的 `${resourceSetEndpoint}` 。

### 许可门票

向有效载荷主体 `/非授权 2.0/许可` 发出 `POST` 请求：：

```json
{
    "索赔"：{"给定名称"："CAS"}，
    "resource_id"：100，
    "resource_scopes"：["阅读"]
}
```

### 索赔收集

发出 `获取` 请求，以以下查询参数</code> `/非授权2.0/rqpClaims：</p>

<ul>
<li><code>client_id`</li>
- `redirect_uri`
- `票`
- `州` （可选）</ul>

### 发现

UMA 发现可通过 `获取` 在 `/oauth2.0/。众所周知/uma 配置`。

### 授权

向有效载荷主体 `/非授权2.0/rptAuthz请求` 发出 `post` 请求：

```json
\
    "票"： "。。。"，
    "rpt"： "。。。"，
    "grant_type"： urn： ietf： params： 授权： 授予类型： 乌马票"，
    "claim_token"： "。。。"，
    "claim_token_format"： "。。。"
}
```
