---
layout: 默认
title: CAS-SAML2元数据管理
---

# SAML2元数据管理

可以通过此处概述的策略动态地动态管理服务提供商的元数据。

## 行政端点

CAS提供了以下端点：

| 终点                                      | 描述                                                                                                                                                                                                                                                                                                                                                        |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `samlIdPRegisteredServiceMetadataCache` | 管理和控制用于保存SAML服务提供商的元数据实例的缓存。 请注意，缓存特定于CAS服务器节点的JVM内存，并且它是 **NOT** 分发或复制的。 甲 `GET` 操作产生元数据的高速缓存副本对于给定的服务提供者，使用 `服务Id` 和 `ENTITYID` 参数。 `serviceId` 参数可以是已注册服务或其名称的数字标识符。 如果服务定义表示元数据聚合（例如InCommon）， `entityId` 参数来查明和过滤聚合中的确切实体。 `DELETE` 操作将删除使元数据缓存无效。 如果未提供任何参数，则元数据缓存将完全无效。 `serviceId` 参数将强制CAS仅使该服务提供者的缓存的元数据实例无效。 `serviceId` 参数可以是已注册服务或其名称的数字标识符。 |

## 元数据查询协议

CAS还支持 [动态元数据查询协议](https://spaces.internet2.edu/display/InCFederation/Metadata+Query+Protocol) ，它是一种类似于REST的API，用于请求和接收任意元数据。 为了配置CAS SAML服务以从元数据查询服务器检索其元数据，必须将元数据位置配置为指向查询服务器实例。

可以使用以下代码段作为示例来配置MDQ：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “evaluationOrder”：10，
  “metadataLocation”： “https://mdq.server.org/entities/{0}”
}
```

...其中 `{0}` 用作要查询其元数据的entityID占位符。 占位符会动态处理，并在运行时由CAS替换。

## 休息

与动态元数据查询协议（MDQ）相似，也可以使用更传统的REST接口来获取SAML服务提供商元数据。 这是一种较简单的选项，不需要部署符合要求的MDQ服务器，并且可以灵活地使用任何编程语言或框架来生成SP元数据。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp-metadata-rest</artifactId>
  <version>${cas.version}</version>
</dependency>
```

以下面的代码段为例，从REST端点获取元数据：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “evaluationOrder”：10，
  “metadataLocation”： “休息：//”
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>
上面注册记录中的元数据位置需要指定为 <code>rest：//</code> 以向CAS发出信号，表明必须从CAS配置中定义的REST端点中获取注册服务提供商的SAML元数据。
</p></div>

请求被提交到REST端点，其中 `实体标识` 作为参数， `内容类型：application / xml` 作为标头。 在 成功 `200 -行` 响应状态，CAS预计HTTP响应的主体，以匹配以下片段：

```json
{  
   “ID”：1000，
   “名称”： “SAML元数据对于服务提供商”，
   “值”： “... ”
   “签名”：“ ...”
}
```

要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-rest)。

### 身份提供者元数据

属于CAS作为SAML2身份提供者的元数据工件也可以通过REST API进行管理和存储。 元数据，签名和 加密密钥等工件在以下结构中作为请求正文传递到外部API端点：

```json
{
    “ signingCertificate”：“ ...”，
    “ signingKey”：“ ...”，
    “ encryptionCertificate”：“ ...”，
    “ encryptionKey”：“ ...”，
    “ metadata”： “ ...”，
    “ applyTo”：“ CAS”
}
```

预期在CAS设置中定义的URL端点在以 `/ idp`结尾的路径上可用，该路径由CAS自动添加到URL端点上。 该API预期会在以下概述的所有操作上 `200-OK`

| 方法   | 描述                                                                                    |
| ---- | ------------------------------------------------------------------------------------- |
| `得到` | 预期响应将产生一个JSON文档，概述上述键和元数据。 `applyTo` 参数来指示文档所有者和适用性，其中值为 `CAS` 表示CAS服务器是元数据和密钥的全局所有者。 |
| `邮政` | 存储元数据和密钥以完成元数据生成过程。 请求主体包含JSON文档，该文档概述了如上所述的元数据和键。                                    |

请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。 要查看 CAS的相关属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-rest)。

#### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 元数据文档，这将是适用于服务定义需要调整 `appliesTo` 字段中的元数据 文件使用携带服务定义的名字和数字标识符 `[service-name]-[service-numeric-identifier]` 格式。

## 吉特

元数据文档也可以存储在Git存储库中或从中获取。 这可能特别用于避免在集群中的CAS节点之间复制元数据文件，特别是在需要处理多个双边SAML集成的情况下。 元数据文档存储为XML文件，并且可以选择在存储库中以相同名称在 `.pem` （即，可以在 `SP.pem``SP.xml`的证书）。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp-metadata-git</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后必须按以下方式设计SAML服务定义，以允许CAS从Git存储库中获取元数据文档：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “描述”： “基于GIT中-A元数据解析器”，
  “metadataLocation”： “GIT中：//”
}
```

给出上面的定义，期望的是git存储库 包含一个 `SAMLService.xml` 文件，该文件也可以有选择地还附带一个 `SAMLService.pem` 文件。

<div class="alert alert-info"><strong>元数据位置</strong><p>
只需将上面注册记录中的元数据位置指定为 <code>git：//</code> 即可向CAS发出信号，表明必须从CAS配置中定义的Git存储库中提取 
 
</p></div>

要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-git)。

### 身份提供者元数据

属于CAS作为SAML2身份提供者的元数据工件也可以通过Git进行管理和存储。 诸如元数据，签名和加密密钥之类的工件在存储库内不同目录位置的文件系统中保留，并且根据需要将数据推入或从git存储库中提取。

请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。 要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-git)。

#### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 适用于服务定义的元数据文档需要调整 `applyTo` 字段，该字段用于构造元数据工件的目录路径。

## MongoDb

元数据文档也可以存储在MongoDb实例中或从中获取。  这可能特别用于避免在集群中的CAS节点之间复制元数据文件，特别是在需要处理多个双边SAML集成的情况下。 元数据文档存储在单个预定义集合中并从中获取，该预定义集合通过设置传授给CAS。  该文件的概述如下：

| 场地   | 描述                    |
| ---- | --------------------- |
| `ID` | 记录的标识符。               |
| `名称` | 索引字段，用于简要描述和命名元数据。    |
| `价值` | 表示服务提供者元数据的XML文档。     |
| `签名` | 用于验证元数据的签名证书的内容（如果有）。 |

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp-metadata-mongo</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后必须按以下方式设计SAML服务定义，以允许CAS从MongoDb实例中获取元数据文档：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “描述”： “基于MongoDB的-A元数据解析器”，
  “metadataLocation”： “mongodb的：//”
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>
只需将上面注册记录中的元数据位置指定为 <code>mongodb：//</code> 即可向CAS发出信号，表明必须从CAS配置中定义的MongoDb数据源中提取 
 
</p></div>

要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-mongodb)。

### 身份提供者元数据

属于CAS作为SAML2身份提供者的元数据工件也可以通过MongoDb进行管理和存储。 元数据，签名和加密密钥等伪像通过设置为单个文档的形式保存在一个通过CAS传授给CAS的MongoDb集合中，该文档具有以下结构：

```json
{
    “ signingCertificate”：“ ...”，
    “ signingKey”：“ ...”，
    “ encryptionCertificate”：“ ...”，
    “ encryptionKey”：“ ...”，
    “ metadata”： “ ...”，
    “ applyTo”：“ CAS”
}
```

请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。 要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-mongodb)。

#### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 元数据文档，这将是适用于服务定义需要调整 `appliesTo` 字段中的元数据 文件使用携带服务定义的名字和数字标识符 `[service-name]-[service-numeric-identifier]` 格式。

## JPA

元数据文档也可以存储在关系数据库实例中或从关系数据库实例中获取。 这可能特别用于避免在集群中的CAS节点之间复制元数据文件，特别是在需要处理多个双边SAML集成的情况下。 元数据文档存储在单个预定义表（即 `SamlMetadataDocument`）中并从中获取，该表的连接信息通过设置传给CAS并自动生成。 该表的概要如下：

| 场地   | 描述                    |
| ---- | --------------------- |
| `ID` | 记录的标识符。               |
| `名称` | 索引字段，用于简要描述和命名元数据。    |
| `价值` | 表示服务提供者元数据的XML文档。     |
| `签名` | 用于验证元数据的签名证书的内容（如果有）。 |

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp-metadata-jpa</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后必须按以下方式设计SAML服务定义，以允许CAS从数据库实例中获取元数据文档：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “描述”： “A-基于关系的元数据DB分解器”，
  “metadataLocation”： “JDBC：//”
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>
只需将上面注册记录中的元数据位置指定为 <code>jdbc：//</code> 即可向CAS发出信号，表明必须从CAS配置中定义的JDBC数据源中获取已注册服务提供者的SAML元数据。 
</p></div>

要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-jpa)。

### 身份提供者元数据

属于CAS作为SAML2身份提供者的元数据工件也可以通过JPA进行管理和存储。 元数据，签名和加密密钥等工件 ，该表具有以下结构：

| 场地     | 描述                          |
| ------ | --------------------------- |
| `ID`   | 记录的标识符。                     |
| `签署证书` | 签名证书。                       |
| `签名密钥` | 签名密钥。                       |
| `加密证书` | 加密证书。                       |
| `加密密钥` | 加密密钥。                       |
| `元数据`  | SAML2身份提供者元数据。              |
| `适用于`  | SAML2身份提供者元数据的所有者（即 `CAS`）。 |

请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。 要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-jpa)。

#### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 元数据文档，这将是适用于服务定义需要调整 `appliesTo` 中的元数据中柱 文件使用携带服务定义的名字和数字标识符 `[service-name]-[service-numeric-identifier]` 格式。

## CouchDb

元数据文档也可以存储在NoSQL数据库中或从NoSQL数据库中获取。 这可能特别用于避免在集群中的CAS节点之间复制元数据文件，特别是在需要处理多个双边SAML集成的情况下。 元数据文档存储在单个预定义表（即 `SamlMetadataDocument`）中并从中获取，该表的连接信息通过设置传给CAS并自动生成。  数据库文档的概述如下：

| 场地   | 描述                    |
| ---- | --------------------- |
| `ID` | 记录的标识符。               |
| `名称` | 索引字段，用于简要描述和命名元数据。    |
| `价值` | 表示服务提供者元数据的XML文档。     |
| `签名` | 用于验证元数据的签名证书的内容（如果有）。 |

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp-metadata-couchdb</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后必须按以下方式设计SAML服务定义，以允许CAS从CouchDb实例中获取元数据文档：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “描述”： “A-基于关系的元数据DB分解器”，
  “metadataLocation”： “CouchDB的：//”，
}
```

<div class="alert alert-info"><strong>元数据位置</strong><p>
在上面的注册记录中的元数据的位置简单地需要被指定为 <code>的CouchDB：//</code> 发信号给CAS，作为在CAS配置中定义的用于注册的服务提供商SAML元数据必须取出从CouchDB的。
</p></div>

请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。 要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-couchdb)。

### 身份提供者元数据

属于CAS作为SAML2身份提供者的元数据工件也可以通过CouchDb进行管理和存储。 元数据，签名和加密密钥等工件 ，该文档具有以下结构：

| 场地     | 描述                          |
| ------ | --------------------------- |
| `ID`   | 记录的标识符。                     |
| `签署证书` | 签名证书。                       |
| `签名密钥` | 签名密钥。                       |
| `加密证书` | 加密证书。                       |
| `加密密钥` | 加密密钥。                       |
| `元数据`  | SAML2身份提供者元数据。              |
| `适用于`  | SAML2身份提供者元数据的所有者（即 `CAS`）。 |

请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。 要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-couchdb)。

#### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 适用于服务定义的元数据文档需要调整 `applyTo` 字段，以 `[service-name]_[service-numeric-identifier]` 格式携带服务定义的名称和数字标识符。

## Groovy

SAML服务定义的元数据位置可以指向外部Groovy脚本，从而允许该脚本以编程方式 确定并构建要添加到现有解析器集合的元数据解析机制。

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp”，
  “ name”：“ SAMLService” ，
  “ID”：10000003，
  “描述”： “基于Groovy的元数据解析器”，
  “metadataLocation”： “文件：/etc/cas/config/groovy-metadata.groovy”
}
```

脚本的概要如下：

```groovy
导入java.util。*
导入org.apereo.cas.support.saml。*
导入org.apereo.cas.support.saml.services。*
导入org.opensaml.saml.metadata.resolver。*

def集合<MetadataResolver> 运行（最终对象... args）{
    def已注册的服务= args[0]
    def samlConfigBean = args[1]
    def samlProperties = args[2]
    def条件[3]
    def logger = args[4]

    / *
     相关的元数据解析器实例。
     完成后，只需将结果包装到一个集合中并返回即可。
     空或空集合将被CAS忽略。
  * /
  def metadataResolver = ...
   返回CollectionUtils.wrap（metadataResolver）
}
```

传递的参数如下：

| 范围               | 描述                                 |
| ---------------- | ---------------------------------- |
| `已注册的服务`         | 代表注册表中相应服务定义的对象。                   |
| `samlConfigBean` | 表示OpenSAML配置类的对象，其中包含各种构建器和编组工厂实例。 |
| `saml属性`         | 负责捕获配置中定义的CAS SAML IdP属性的对象。       |
| `条件集`            | 负责捕获元数据解决方案条件的对象（如果有）。             |
| `记录器`            | 负责发布日志消息的对象，例如 `logger.info（...）`。 |

## 亚马逊S3

元数据文档也可以存储在MongoDb实例中或从中获取。 这可以专门用于避免在集群中的CAS节点之间复制元数据文件，特别是在其中一个需要 来处理多个双边SAML集成的情况下。 个预定义的存储桶中，或从中获取，该存储桶通过设置传给CAS。

通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
  <groupId>org.apereo.cas</groupId>
  <artifactId>cas-server-support-saml-idp-metadata-aws-s3</artifactId>
  <version>${cas.version}</version>
</dependency>
```

然后必须按以下方式设计SAML服务定义，以允许CAS从MongoDb实例中获取元数据文档：

```json
{
  “ @class”：“ org.apereo.cas.support.saml.services.SamlRegisteredService”，
  “ serviceId”：“ the-entity-id-of-the-sp（s）”，
  “ name”： “SAMLService”，
  “ID”：10000003，
  “描述”： “基于S3-亚马逊元数据解析器”，
  “metadataLocation”： “awss3：//”
}
```

以下参数适用于Amazon S3对象元数据：

| 范围   | 描述            |
| ---- | ------------- |
| `签名` | 元数据签名证书（如果有）。 |

<div class="alert alert-info"><strong>元数据位置</strong><p>
只需将上面注册记录中的元数据位置指定为 <code>awss3：//</code> 即可向CAS发出信号，表明必须从CAS配置中定义的Amazon S3提取 
 
</p></div>

要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-amazon-s3)。

### 身份提供者元数据

属于CAS作为SAML2身份提供者的元数据工件也可以通过Amazon S3存储桶进行管理和存储。 元数据，签名和加密密钥等工件在包含元数据 ，该元数据具有以下结构：

| 场地     | 描述                   |
| ------ | -------------------- |
| `ID`   | 记录的标识符。              |
| `签署证书` | 签名证书。                |
| `签名密钥` | 签名密钥。                |
| `加密证书` | 加密证书。                |
| `加密密钥` | 加密密钥。                |
| `适用于`  | 此元数据文档的所有者（即 `CAS`）。 |

实际对象的内容/主体应包含SAML2身份提供程序元数据。 请注意，期望使用CAS加密密钥对签名和加密密钥进行加密和签名。

要查看相关的CAS属性，请 [参见本指南](../configuration/Configuration-Properties.html#saml-metadata-amazon-s3)。

#### 每项服务

身份提供者元数据，证书和密钥也可以基于每个服务进行定义，以覆盖全局默认值。 需要将适用于服务定义的元数据文档使用 `[service-name][service-numeric-identifier]` 格式
