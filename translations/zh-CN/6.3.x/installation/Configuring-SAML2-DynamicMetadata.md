---
layout: 违约
title: CAS - SAML2元数据管理
---

# SAML2 元数据管理

可以通过此处概述的策略，动态动态地管理服务提供商元数据。

## 行政终点

CAS 提供以下端点：

| 端点                | 描述                                                                                                                                                                                                                                                                                                                 |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `萨姆利德普注册服务梅塔达塔卡奇` | 管理和控制为 SAML 服务提供商保留元数据实例的缓存。 请注意，缓存特定于 CAS 服务器节点的 JVM 内存，它 **不** 分发或复制。 `GET` 操作使用 `服务Id` 和 `实体Id` 参数，为给定服务提供商生成元数据缓存副本。 `服务Id` 参数可能是注册服务的数字标识符或其名称。 如果服务定义表示元数据聚合（如 InCommon）， `实体id` 参数可用于精确定位和筛选聚合中的确切实体。 `删除` 操作将删除元数据缓存无效。 如果不提供参数，元数据缓存将完全失效。 `服务Id` 参数将强制CAS仅使该服务提供商的缓存元数据实例失效。 `服务Id` 参数可能是注册服务的数字标识符或其名称。 |

## 元数据查询协议

CAS 还支持 [动态元数据查询协议](https://spaces.internet2.edu/display/InCFederation/Metadata+Query+Protocol) 该协议是一种类似 REST 的 API，用于请求和接收任意元数据。 为了配置 CAS SAML 服务以从元数据查询服务器检索其元数据，必须配置元数据位置以指向查询服务器实例。

MDQ 可以使用以下片段进行配置，例如：

```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔服务。萨姆注册服务"，
  "服务ID"："实体-id-sp"，
  "名称"："SAML服务"，
  "id"：1000003，
  "评估"：10，
  "元数据定位"："https://mdq.server.org/entities/{0}"
}
```

...其中 `{0}` 作为要查询元数据的实体ID占位符。 占位符在运行时由 CAS 动态处理并更换。

## 休息

与动态元数据查询协议 （MDQ） 类似，SAML 服务提供商元数据也可以使用更传统的 REST 接口提取。 这是一个更简单的选项，不需要一个人部署一个合规的MDQ服务器，并提供使用任何编程语言或框架生成SP元数据的灵活性。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普 - 元数据休息</artifactId>
  <version>${cas.version}</version>
</dependency>
```

使用以下片段作为示例从 REST 端点获取元数据：

```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务。萨姆注册服务"，
  "服务ID"："实体-id-sp"，
  "名称"："SAML服务"，
  "id"：1000003，
  "评估"：10，
  "元数据定位"："rest://"
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>
上述注册记录中的元数据位置需要指定为 <code>rest://</code> 向 CAS 发出信号，即注册服务提供商的 SAML 元数据必须从 CAS 配置中定义的 REST 端点提取。
</p></div>

请求以 `实体Id` 为参数， `内容类型：应用程序/xml` 为标题提交到 REST 端点。 在 成功 `200 - 确定` 响应状态后，CAS 预计 HTTP 响应的主体将匹配以下片段：

```json
•  
   "id"：1000，
   "名称"："服务提供商的SAML元数据"，
   "值"："。。。。。。"，
   "签名"："。。。。。。"
}
```

要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-rest)查看本指南。

### 身份提供商元数据

属于 CAS 作为 SAML2 身份提供商的元数据神器也可以通过 REST ABI 进行管理和存储。 元数据、签名和 加密密钥等工件作为请求主体传递到以下结构中的外部 API 端点：

```json
\
    "签名认证"："。。。"，
    "签名键"："。。。"，
    "加密认证"："。。。"，
    "加密密钥"："。。。"，
    "元数据"："。。。"，
    "适用于"："CAS"
}
```

CAS 设置中定义的 URL 端点预计将在以 `/idp`结尾的路径上可用，CAS 会自动将其添加到 URL 端点中。 API 预计将在以下所有操作中成功 `200 - 确定` 响应状态：

| 方法   | 描述                                                                                 |
| ---- | ---------------------------------------------------------------------------------- |
| `获取` | 响应预计将生成 JSON 文档，概述上述键和元数据。 `适用于` 参数，以指示文档所有者和适用性，其中 `CAS` 表示 CAS 服务器是元数据和密钥的全球所有者。 |
| `发布` | 存储元数据和密钥以完成元数据生成过程。 请求主体包含 JSON 文档，其中概述了上述元数据和密钥。                                  |

请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。 有关 中科院物业，请 [本指南](../configuration/Configuration-Properties.html#saml-metadata-rest)。

#### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于服务定义的元数据文档需要调整适用于元数据 文档中的</code> 字段的 `，以便使用 <code>[service-name]-[service-numeric-identifier]` 格式携带服务定义的名称和数字标识符。

## 吉特

元数据文档也可以存储在 Git 存储库中并从 Git 存储库提取。 这可以特别用于避免在聚类中复制跨 CAS 节点的元数据文件，尤其是在需要处理多个双边 SAML 集成的情况下。 元数据文档以 XML 文件的形式存储，可选地，其签名证书预计将在存储库中同名的 `。pem` 文件中找到。 （即 `SP.xml`的证书可以在sp.pem</code>） `找到。</p>

<p spaces-before="0">支持通过在覆盖中包括以下模块来启用：</p>

<pre><code class="xml"><dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普 - 元数据 - git</artifactId>
  <version>${cas.version}</version>
</dependency>
`</pre>

然后，SAML 服务定义必须设计如下，以便 CAS 能够从 Git 存储库获取元数据文档：

```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务.萨姆注册服务"，
  "服务Id"："基于Git的元数据解析器"，
  "名称"："SAMLService"，
  "id"：1000003，
  "描述"："基于Git的元数据解析器"，
  "元数据定位"："git://"
}
```

给出上述定义，期望 git 存储库 包含一个 `SAMLService.xml` 文件，该文件也可以选择性地附有 `SAMLService.pem` 文件。

<div class="alert alert-info"><strong>元数据位置</strong><p>
上述注册记录中的元数据位置只需指定为 <code>git://</code> ，即可向 CAS 发出信号，即注册服务提供商 
SAML 元数据必须从 CAS 配置中定义的 Git 存储库提取。 
</p></div>

要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-git)查看本指南。

### 身份提供商元数据

作为 SAML2 身份提供商属于 CAS 的元数据工件也可以通过 Git 进行管理和存储。 元数据、签名和加密密钥等工件保存在存储库内不同目录位置的文件系统中，数据按需推入或从 git 存储库提取。

请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。 要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-git)查看本指南。

#### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于服务定义的元数据文档需要调整适用于元数据 文档中的</code> 字段 `，该文档用于构建元数据工件的目录路径。</p>

<h2 spaces-before="0">蒙古德布</h2>

<p spaces-before="0">元数据文档也可以存储在 MongoDb 实例中并从中提取。  这可以特别用于避免在聚类中复制跨 CAS 节点的元数据文件，尤其是在需要处理多个双边 SAML 集成的情况下。 元数据文档存储在单个预先定义的集合中并从通过设置传授给 CAS 的单个预定义集合中提取。  文件大纲如下：</p>

<table spaces-before="0">
<thead>
<tr>
  <th>田</th>
  <th>描述</th>
</tr>
</thead>
<tbody>
<tr>
  <td><code>ID`</td> 

</tr> 

</tbody> </table>

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普 - 元数据 - 蒙古</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后，SAML 服务定义必须设计如下，以便 CAS 能够从 MongoDb 实例中获取元数据文档：

```json
•
  "@class"： "组织. apereo. cas. 支持. saml. 服务. 萨姆注册服务"，
  "服务ID"："实体-id-sp"，
  "名称"："SAMLService"，
  "id"：1000003，
  "描述"："基于蒙古数据库的元数据解析器"，
  "元数据定位"："mongodb://"
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>
上述注册记录中的元数据位置只需指定为 <code>mongodb://</code> ，即可向 CAS 发出信号，即注册服务提供商 
SAML 元数据必须从 CAS 配置中定义的 MongoDb 数据源获取。 
</p></div>

要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-mongodb)查看本指南。

### 身份提供商元数据

属于 CAS 作为 SAML2 身份提供商的元数据神器也可以通过 MongoDb 进行管理和存储。 元数据、签名和加密密钥等文物保存在 MongoDb 收藏中，通过设置作为具有以下结构的单个文档向 CAS 传授：

```json
\
    "签名认证"："。。。"，
    "签名键"："。。。"，
    "加密认证"："。。。"，
    "加密密钥"："。。。"，
    "元数据"："。。。"，
    "适用于"："CAS"
}
```

请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。 要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-mongodb)查看本指南。

#### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于服务定义的元数据文档需要调整适用于元数据 文档中的</code> 字段的 `，以便使用 <code>[service-name]-[service-numeric-identifier]` 格式携带服务定义的名称和数字标识符。

## 日本经济与经济、经济、经济

元数据文档也可以存储在关系数据库实例中并从中提取。 这可以特别用于避免在聚类中复制跨 CAS 节点的元数据文件，尤其是在需要处理多个双边 SAML 集成的情况下。 元数据文档存储在单个预先定义的表中并从该表中提取（即 `萨姆尔梅塔达数据`）其连接信息通过设置传授给CAS，并自动生成。 表格的轮廓如下：

| 田    | 描述                    |
| ---- | --------------------- |
| `ID` | 记录的标识符。               |
| `名字` | 简要描述和命名元数据的索引字段。      |
| `价值` | 代表服务提供商元数据的 XML 文档。   |
| `签名` | 签名证书的内容，以验证元数据，如果有的话。 |

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普 - 元数据 - jpa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后，SAML 服务定义必须设计如下，以便 CAS 能够从数据库实例中获取元数据文档：

```json
•
  "@class"： "组织. apereo. cas. 支持. saml. 服务. 萨姆注册服务"，
  "服务ID"："基于实体的元数据解析器"，
  "名称"："SAMLService"，
  "id"：1000003，
  "描述"："基于关系db的元数据解析器"，
  "元数据定位"："jdbc://"

```

<div class="alert alert-info"><strong>元数据位置</strong><p>
上述注册记录中的元数据位置只需指定为 <code>jdbc://</code> ，即可向 CAS 发出信号，即注册服务提供商的 SAML 元数据必须从 CAS 配置中定义的 JDBC 数据源获取。 
</p></div>

要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-jpa)查看本指南。

### 身份提供商元数据

作为 SAML2 身份提供商属于 CAS 的元数据神器也可以通过 JPA 进行管理和存储。 元数据、签名和加密密钥等工件 保存在具有以下结构的数据库表中：

| 田      | 描述                           |
| ------ | ---------------------------- |
| `ID`   | 记录的标识符。                      |
| `签署认证` | 签名证书。                        |
| `签名键`  | 签名密钥。                        |
| `加密认证` | 加密证书。                        |
| `加密键`  | 加密密钥。                        |
| `元数据`  | SAML2 身份提供商元数据。              |
| `适用于`  | SAML2 身份提供商元数据的所有者（即 `中科院`）。 |

请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。 要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-jpa)查看本指南。

#### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于服务定义的元数据文档需要调整适用于元数据 文档中的</code> 列的 `，以便使用 <code>[service-name]-[service-numeric-identifier]` 格式携带服务定义的名称和数字标识符。

## 库奇德布

元数据文档也可以存储在 NoSQL 数据库中并从中提取。 这可以特别用于避免在聚类中复制跨 CAS 节点的元数据文件，尤其是在需要处理多个双边 SAML 集成的情况下。 元数据文档存储在单个预先定义的表中并从该表中提取（即 `萨姆尔梅塔达数据`）其连接信息通过设置传授给CAS，并自动生成。  数据库文档的大纲如下：

| 田    | 描述                    |
| ---- | --------------------- |
| `ID` | 记录的标识符。               |
| `名字` | 简要描述和命名元数据的索引字段。      |
| `价值` | 代表服务提供商元数据的 XML 文档。   |
| `签名` | 签名证书的内容，以验证元数据，如果有的话。 |

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普 - 元数据 - 沙发</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后，SAML 服务定义必须设计如下，以便 CAS 能够从 CouchDb 实例中获取元数据文档：

```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务。萨姆注册服务"，
  "服务Id"："基于实体的元数据解析器"，
  "名称"："SAMLService"，
  "id"：1000003，
  "描述"："基于关系db的元数据解析器"，
  "元数据定位"："couchdb://"，

```

<div class="alert alert-info"><strong>元数据位置</strong><p>
上述注册记录中的元数据位置只需指定为 <code>couchdb://</code> ，即可向 CAS 发出信号，即注册服务提供商的 SAML 元数据必须从 CAS 配置中定义的 CouchDb 提取。
</p></div>

请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。 要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-couchdb)查看本指南。

### 身份提供商元数据

作为 SAML2 身份提供商属于 CAS 的元数据神器也可以通过 CouchDb 进行管理和存储。 元数据、签名和加密密钥等工件保存在数据库 ，其中包含具有以下结构的文档：

| 田      | 描述                           |
| ------ | ---------------------------- |
| `ID`   | 记录的标识符。                      |
| `签署认证` | 签名证书。                        |
| `签名键`  | 签名密钥。                        |
| `加密认证` | 加密证书。                        |
| `加密键`  | 加密密钥。                        |
| `元数据`  | SAML2 身份提供商元数据。              |
| `适用于`  | SAML2 身份提供商元数据的所有者（即 `中科院`）。 |

请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。 要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-couchdb)查看本指南。

#### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于服务定义的元数据文档需要调整适用于元数据 文档中的</code> 字段的 `，以便使用 <code>[service-name]_[service-numeric-identifier]` 格式携带服务定义的名称和数字标识符。

## 槽的

SAML 服务定义的元数据位置可能指向外部 Groovy 脚本，允许脚本以编程方式 确定和构建元数据分辨率机制，以添加到现有解析器的集合中。

```json
•
  "@class"： "org. apereo. cas. 支持. saml. 服务. 萨姆注册服务"，
  "服务" ： "实体 - id - sp"，
  "名称"："SAMLService"，
  "id"：1000003，
  "描述"："基于凹槽的元数据解析器"，
  "元数据定位"："文件：/等/cas/配置/凹槽元数据。groovy"
。
```

脚本的大纲如下：

```groovy
进口 java. util.*
进口组织. apereo. cas. 支持. saml. *
进口组织. apereo. cas. 支持. saml. 服务. *
进口组织. 打开. saml. 元数据. 解决器. *

def 收集<MetadataResolver> 运行 （最终对象...args）{
    def注册服务=args[0]
    d def samlConfigBean=args[1]
[2]
    定义标准集=args[3]
    除号器=args[4]

    /*
     的东西发生在您构建相关元数据解析器实例的地方。
     完成后，只需将结果包装成集合并返回即可。
     CAS 将忽略空集合或空集合。
  */
  定义元数据雷索弗=...
   返回集合使用。包装（元数据恢复）
}
```

通过的参数如下：

| 参数        | 描述                                    |
| --------- | ------------------------------------- |
| `注册服务`    | 表示注册表中相应服务定义的对象。                      |
| `萨姆尔康菲格豆` | 代表 OpenSAML 配置类的对象，其中保留各种构建者和马歇尔工厂实例。 |
| `萨姆普罗佩里斯` | 负责捕获配置中定义的 CAS SAML IDP 属性的对象。        |
| `标准集`     | 负责捕获元数据解决方案标准的对象（如果有的话）。              |
| `记录`      | 负责发布日志消息的对象，如 `logger.info（。。。）`。     |

## 亚马逊 S3

元数据文档也可以存储在 MongoDb 实例中并从中提取。 这可以特别用于避免在聚类中复制 CAS 节点中的元数据文件，尤其是在需要 来处理多个双边 SAML 集成的情况下。 元数据文档存储并取自 通过设置教给 CAS 的单个预定义存储桶。

支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
  <groupId>组织. apereo. cas</groupId>
  <artifactId>卡斯服务器支持 - 萨姆尔 - 伊德普 - 元数据 - aws - s3</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后，SAML 服务定义必须设计如下，以便 CAS 能够从 MongoDb 实例中获取元数据文档：

```json
•
  "@class"："组织.apereo.cas.支持.萨姆尔.服务.萨姆注册服务"，
  "服务ID"："基于sp的实体id"，
  "名称"："SAML服务"，
  "id"：1000003，
  "描述"："亚马逊S3基元数据解析器"，
  "元数据定位"："aws3：//"
}
```

亚马逊 S3 对象元数据预计有以下参数：

| 参数   | 描述              |
| ---- | --------------- |
| `签名` | 元数据签名证书（如果有的话）。 |

<div class="alert alert-info"><strong>元数据位置</strong><p>
上述注册记录中的元数据位置只需指定为 <code>aws3：//</code> ，即可向 CAS 发出信号，即注册服务提供商 
的 SAML 元数据必须从 CAS 配置中定义的 Amazon S3 提取。 
</p></div>

要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-amazon-s3)查看本指南。

### 身份提供商元数据

属于 CAS 作为 SAML2 身份提供商的元数据工件也可以通过亚马逊 S3 存储桶进行管理和存储。 元数据、签名和加密密钥等工件 保存在具有以下结构的元数据的存储桶中：

| 田      | 描述                   |
| ------ | -------------------- |
| `ID`   | 记录的标识符。              |
| `签署认证` | 签名证书。                |
| `签名键`  | 签名密钥。                |
| `加密认证` | 加密证书。                |
| `加密键`  | 加密密钥。                |
| `适用于`  | 此元数据文档的所有者（即 `中科院`）。 |

实际对象的内容/主体预计将包含 SAML2 身份提供商元数据。 请注意，签名和加密密钥预计将使用 CAS 加密密钥进行加密和签名。

要查看 CAS 的相关属性，请 [](../configuration/Configuration-Properties.html#saml-metadata-amazon-s3)查看本指南。

#### 每种服务

身份提供商元数据、证书和密钥也可以在每个服务的基础上定义，以覆盖全球默认值。 适用于服务定义的元数据文档需要使用 `[service-name][service-numeric-identifier]` 格式放入名为 的特殊存储桶中。
