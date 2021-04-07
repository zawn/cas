---
layout: 违约
title: CAS - SAML2 属性发布
category: 属性
---

# SAML2 属性发布

属性筛选和发布策略是根据 SAML 服务定义的。 有关详细信息，请参阅本指南</a>

。</p> 



## 属性值类型

默认情况下，在最终 SAML2 响应中创建的属性值块不会在编码的 XML 中携带任何类型信息。 如有必要，您可以根据 SAML2 服务提供商的要求强制执行特定类型的属性值（如果有的话）。 用特定类型信息编码的属性的示例是：



```xml
<saml2:Attribute FriendlyName="givenName" Name="givenName" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:uri">
    <saml2:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xsd:string">你好世界</saml2:AttributeValue>
</saml2:Attribute>
```


支持以下属性值类型：

| 类型            | 描述                             |
| ------------- | ------------------------------ |
| `X斯特林`        | 将属性值类型标记为 `字符串`。               |
| `苏里`          | 将属性值类型标记为 `乌里`。                |
| `克斯博兰`        | 将属性值类型标记为 `布尔`。                |
| `辛特格`         | 将属性值类型标记为 `整数`。                |
| `XS日时间`       | 将属性值类型标记为 `日期` 。               |
| `XSBase64 二月` | 将属性值类型标记为 `基础64二`。             |
| `X主题`         | 跳过属性值类型，将值序列化为复杂的 XML 对象/POJO。 |


...每个属性的类型将被定义为：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务.萨姆注册服务"，
  "服务id"："实体-id-sp"，
  "名称"："SAML服务"，
  "元数据定位"："。/../sp-元数据.xml"，
  "id"：1、
  "属性价值类型"：{
    "@class"："java.il.hashMap"，
    "<attribute-name>"："<attribute-value-type>"
  }
}
```




## 属性名称格式

属性名称格式可以在服务注册表中指定每个依赖方。



```json
•
  "@class"："org.apereo.cas.支持.saml.服务.萨姆注册服务"，
  "服务id"："实体-id-sp"，
  "名称"："SAML服务"，
  "元数据定位"："。/../sp-元数据.xml"，
  "id"：100001，
  "属性名称表"：{
    "@class"："java.usil.HashMap"，
    "属性名称"："基本|uri|未指定|习惯格式等"
  }
}
```


您也可以选择通过 CAS 属性在全球 定义属性及其相关名称格式。 要查看 CAS 属性的相关列表，请 [查看本指南](../configuration/Configuration-Properties.html#saml-idp)。



## 属性友好名称

属性友好型名称可在服务注册表中指定每个依赖方，以及通过 CAS 设置在全球范围内指定。 如果属性没有定义友好的名称，则将代替 属性名称使用。 请注意，属性的名称是设计为发布给服务提供商的属性， 特别是如果原始属性 *映射* 到其他名称。



```json
•
  "@class"："org.apereo.cas.支持.saml.服务.萨姆注册服务"，
  "服务id"："实体-id-sp"，
  "名称"："SAML服务"，
  "元数据定位"："。/../sp-元数据.xml"，
  "id"：100001，
  "属性友好名称"：{
    "@class"："java.util.HashMap"，
    "urn：oid：2.5.4.42"："友好名称使用"
  =

```





## 不一起的研究和奖学金

使用实体属性值 `http://id.incommon.org/category/research-and-scholarship`，可以发布 InCommon 研究和奖学金服务提供商所需的 [属性捆绑](https://spaces.internet2.edu/display/InCFederation/Research+and+Scholarship+Attribute+Bundle) 的特定属性发布策略：



```json
•
  "@class"： "org. apereo. cas. 支持. saml. 服务"，
  "服务Id"： "实体 id 允许通过注册"，
  "名称"： "Saml"，
  "id"： 10，
  "元数据定位"："路径/到/不通信/元数据.xml"，
  "属性发布政策"：{
    "@class"："org.apereo.cas.服务。链属性发布政策"，
    " 政策"： [java. util. Arraylist"，
      [
         ] "@class"： "org. apereo. cas. 支持. saml. 服务" [
      ]
    ]
  ]
|
```


授权发布的属性设置为 `教育个人`， `教育人目标ID`， `电子邮件`， `显示`名， `姓`， `姓`， ``。



## 参考研究与奖学金

使用实体属性值 `http://refeds.org/category/research-and-scholarship`，可以发布 REFEDS 研究和奖学金服务提供商所需的 [属性捆绑](https://refeds.org/category/research-and-scholarship) 的特定属性发布策略：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务.萨姆注册服务"，
  "服务ID"："实体-id-允许-通过-注册"，
  "名称"："SAML"，
  "id"：10，
  "元数据定位"："路径/到/不通信/元数据.xml"，
  "属性发布政策"：
    "@class"："org.apereo.cas.服务。链条属性发布政策"，
    "政策" ："java. util. Arraylist"，
      [
         ]" @class"： "组织. apereo. cas. 支持. saml. 服务" [
      ]
    ]
  [
]
```


本政策是 `基于不同实体属性值运行的不公政策` 的延伸。



## 发布 `个人目标ID`

如果您没有预先计算的 `eduPerperedID` 属性在发布前提取的值， 您可以让 CAS 使用以下策略在发布时动态计算 `eduPerperpertargetID` 属性：



```json
•
  "@class"： "org. apereo. cas. 支持. saml. 服务"，
  "服务Id"： "实体 id 允许通过注册"，
  "名称"： "Saml"，
  "id"： 10，
  "元数据定位"："路径/到/元数据.xml"，
  "属性发布政策"：{
    "@class"："org.apereo.cas.支持.saml .服务.爱德华个人目标属性释放政策"，
    "盐"："OqmG80fEKBQt"，
    "属性"："
  }
}
```


生成的 ID 可能基于现有主属性。 如果未指定或未找到属性，则使用 已验证的本金 ID。



## 格罗夫脚本

此策略允许 Groovy 脚本计算已发布的属性的集合。



```json
•
  "@class"："org.apereo.cas.支持.saml.服务.萨姆注册服务"，
  "服务ID"："实体id允许通过regex"，
  "名称"："SAML"，
  "id"：10，
  "元数据定位"："路径/到/不通信/元数据.xml"，
  "属性发布政策"：+
    "@class"："org.apereo.cas.支持" .saml. 服务. 格劳维萨姆注册服务归因释放政策"，
    "凹槽脚本"： "文件： 等 / 卡斯 / 康菲 / 脚本. groovy"
  =
]
```


此组件的配置有资格使用 [弹簧表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。

脚本的大纲可设计为：



```groovy
进口 java. util.*
进口组织. apereo. cas. 支持. saml. 服务. *
进口组织. apereo. cas. 支持. saml. *

def 地图<String, Object> 运行 （最终对象...args）{
    def属性=args[0]
    定义服务=args[1]
    定义解析器=args[2]
    除法面=args[3]
    定义实体描述符=args[4]
    dexext=args[5]
    除法器=args[6]
    。。。
    返回无效;
}
```


以下参数传递到脚本：

| 参数      | 描述                                |
| ------- | --------------------------------- |
| `属性`    | 已解决并可用于发布的当前属性地图。                 |
| `服务`    | SAML 服务定义与服务注册表中匹配。               |
| `解析 器`  | 此服务提供商的元数据解析器实例。                  |
| `外观`    | 元数据解决器顶部的包装，允许访问实用功能。             |
| `实体描述符` | `实体描述器` 对象匹配并链接到此服务提供商的元数据。       |
| `应用康德信` | CAS应用程序上下文允许直接访问豆类等。              |
| `记录`    | 负责发布日志消息的对象，如 `logger.info（。。。）`。 |


示例脚本如下：



```groovy
进口 java. util.*
进口组织. apereo. cas. 支持. saml. 服务. *
进口组织. apereo. cas. 支持. saml. *

def 地图<String, Object> 运行 （最终对象...args）{
    定义属性=args[0]
    定义服务=args[1]
    定义解析器=args[2]
    面=args[3]
    定义实体描述器=args[4]
    def 应用程序转换 = args[5]
    def 记录器 = args[6]

    如果 （实体描述器. 实体Id = "测试SAML应用"） [
      返回 [用户名：["东西"] 另一个："属性"]
    =
    返回[：]
}
```




## 模式匹配实体 ID

如果聚合定义包含多个实体 ID，则以下属性发布策略可用于向按常规表达模式分组的实体 ID 发布允许属性集合：



```json
•
  "@class"："org.apereo.cas.支持.saml.服务.萨姆注册服务"，
  "服务ID"："实体-id允许通过注册"，
  "名称"："SAML"，
  "id"：10，
  "元数据定位"： "路径 / 到 / 不通信 / 元数据.xml"，
  "属性释放政策"： [
    "@class"： "org. apereo. cas. 支持. saml. 服务. 模式匹配"
    "允许属性"： [ "java. 4. ["cn"、"邮件"、"sn"]，
    "全匹配"："真实"，
    "反向匹配"："虚假"，
    "实体ids"："实体id1|entityId2|一些
  }
}
```




## 实体属性筛选器

此属性发布策略授权发布定义属性，前提是服务提供商的随附元数据包含与特定值匹配的属性。



```json
•
  "@class"："org.apereo.cas.支持.saml.服务"，
  "服务ID"："实体-id允许通过注册"，
  "名称"："SAML"，
  "id"：10，
  "元数据定位"： "路径 / 到 / 元数据.xml"，
  "属性释放政策"： [
    "@class"： "org. apereo. cas. 支持. saml. 服务. 元属性属性发布政策"，
    "允许属性"： [ "java. 4. ["cn"、"邮件"、"sn"]，
    "实体属性价值"："java.利用。LinkedHashSet"，"实体属性值"]，
    "实体归因" ："http://somewhere.org/category-x"，
    "实体归因形式"："urn：绿洲：名称：tc：SAML：2.0：格式：未指定"
  =
}
```


`实体归因形式` 的规格是可选的。



## 请求属性筛选器

此属性发布策略授权基于服务提供商的附带元数据发布定义属性，该元数据已请求属性作为其 `属性节约服务` 元素的一部分。



```json
•
  "@class"： "org. apereo. cas. 支持. saml. 服务"，
  "服务Id"： "实体 id 允许通过注册"，
  "名称"： "Saml"，
  "id"： 10，
  "元数据定位"："路径/到/元数据.xml"，
  "属性发布政策"：[
    "@class"："org.apereo.cas.支持.saml.服务.元数据请求属性发布政策"，
    "使用友好名称"：虚假
  =

```


使用友好名称</code> `允许筛选器将所请求属性的友好名称与已解决的属性进行比较。</p>

<h3 spaces-before="0">SAML IDP 属性定义</h3>

<p spaces-before="0">SAML 属性可以定义为 <a href="../integration/Attribute-Definitions.html">属性定义存储</a>的一部分。
<code>SamlIdpAtrribute 定义` 继承了默认属性 `定义` 的所有属性，并添加了两个特定于 SAML 属性的可选属性。  用此定义定义属性并不妨碍其他协议发布属性。



```json
•
  "@class"："java.util.TreeMap"，
  "爱德华·佩尔森原则名"：{
    "@class"："org.apereo.cas.支持.萨姆尔.web.idp.配置文件.建设者.attr.萨姆利德普属性定义"，
    "钥匙"："爱德华人原则名称"，
    "姓名"："爱德华个人原则名称"，
    "urn：oid：1.3.6.1.4.1.5923.1.1.1.1.1.6"，
    "范围"：真实，
    "加密"：虚假的，
    的"属性"："uid"，
    "友好名称"："教育个人原则名称"
  }
}
```


可以为 Saml IdP 属性定义指定以下其他设置：

| 名字     | 描述                                                  |
| ------ | --------------------------------------------------- |
| `友好的名` | （可选）属性在属性发布期间与目标应用程序共享的友好名称。                        |
| `瓮`    | （可选）定义属性的通用资源名称（即骨灰盒：oid：1.3.6.1.4.1.5923.1.1.1.6）。 |
