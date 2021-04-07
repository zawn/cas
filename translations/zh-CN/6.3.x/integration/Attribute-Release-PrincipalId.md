---
layout: 违约
title: CAS - 释放主体 ID
category: 属性
---

# 本金 ID 属性

注册的 CAS 应用程序能够允许配置 用户名属性提供商，该提供商控制应返回到应用程序的指定用户标识符 。 默认情况下，用户标识符是经过身份验证的 CAS 本金 ID，但它可选择地基于已为本金可用和解决的现有 属性。 更实际的是，此组件确定在返回应用程序的最终 CAS 验证有效载荷中应放置在 `<cas:user>` 标签中的内容。

<div class="alert alert-warning"><strong>主 ID 作为属性</strong><p>您还可以将经过验证的本金 ID 作为最终 CAS 有效载荷中的附加属性返回。 请参阅本指南 <a href="Attribute-Release-Policies.html"></a> 了解更多。</p></div>

许多提供商能够对返回的最终用户 ID 执行规范，将其 转换为大写/小写。 `规范化月` 指出了这一点，其允许值 `上`， `下` 或 `无`。

## 违约

无需明确定义的默认配置将已解决的 主 ID 返回为此服务的用户名。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"： 100，
  "描述"： "样本"，
  "用户名归因提供者"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务使用提供者"，
    "规范化妈妈"： "无"
  [
]
```

如果您不需要调整此提供商的行为（即修改 `规范化` 模式）， 则可以完全排除此块。

## 加密

大多数（如果不是所有提供商）能够加密已解决的用户名，假设服务定义为公共密钥。

密钥可以通过以下命令生成：

```bash
打开斯尔根萨 - 出私人.key 1024
打开斯尔 rsa - 酒吧 - 在私人.key - 出公共.key - 通知 Pem - 超越 Der
打开 pkcs8 - topk8 - 通知每 - 通知佩尔 - 诺克里普特 - 在私人.key - 出私人. p8
```

然后，将公共密钥配置为 CAS 中的服务定义：

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：100，
  "描述"："样本"，
  "用户名属性提供者"： [
    "@class"： "org. apereo. cas. 服务. 默认注册服务用户提供者"，
    "加密用户名"： "真实"
  [，
  "公共钥匙" "： [
    "@class"： "org. apereo. cas. 服务. 注册服务公共钥匙"，
    "位置"： "类路径： 公共.key"，
    "算法"： "Rsa"
  [
}
```

公共关键组件的配置有资格使用 [春季表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。

然后，应用程序可以继续使用自己的私钥解密用户名。 以下示例代码演示了在 Java 中如何做到这一点：

```java
最后的字符串卡塞纳梅=...
最终私钥私钥=...
最后的密码密码=密码。获取安装（私钥。获取高利特）：
最后一个缩写 [] 信任 64 = 解码基地 64 （编码;
密码. init （Cipher.DECRYPT_MODE， 私人钥匙）：
最后的旁记 [] 密码数据 = 密码。
返回新字符串（密码数据）：
```

## 属性

将本金已解决的属性返回为此服务的用户名。 如果属性 不可用，将使用默认本金 ID。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：600，
  "描述"："样本"，
  "用户名归因提供者"：{
    "@class"："org.apereo.cas.服务.委托注册服务"，
    "用户名属性"："cn"，
    "规范化"："上"
  =
}
```

## 爪哇脚本/Python/格劳维脚本

<div class="alert alert-warning"><strong>用法</strong>
<p><strong>此功能被弃用，并计划在将来删除。</strong></p>
</div>

让外部爪哇脚本、凹槽脚本或巨蛇脚本决定如何确定主 ID 属性。 此方法利用了内置于 Java 平台中的脚本功能。 虽然 Javascript 和 Groovy 应由 CAS 提供本地支持，但 Python 脚本可能需要 来按摩 CAS 配置，以将 [Python 模块](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22jython-standalone%22)。

脚本将接收并访问以下可变绑定：

- `id`： 已验证本金的现有标识符。
- `属性`：当前为委托人解决的属性图。
- `记录器`：记录器对象，能够提供 `logger.info（）` 操作等。


```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："示例"，
  "名称"："样本"，
  "id"：500，
  "描述"："样本"，
  "用户名归因提供者"：
    "@class"："org.apereo.cas.服务.脚本注册服务"，
    "脚本"："file:///etc/cas/sampleService"。凹凸不||。py]"，
    "规范化"："上"
  }
}
```

示例 Groovy 脚本如下：

```groovy
def运行（对象[]args）{
    定义属性=args[0]
    def id=args[1]
    def记录器=args[2]
    logger.info（"测试用户名属性"）
    返回"测试"
}
```

示例爪哇脚本功能如下：

```javascript
函数运行（uid，记录器）{
   返回"测试"
}
```

## 槽的

返回用户名属性值作为凹槽脚本执行的最终结果。 无论内衬脚本还是外部脚本都将接收并访问以下可变绑定：

- `id`： 已验证本金的现有标识符。
- `属性`：当前为委托人解决的属性图。
- `服务`：与注册服务定义匹配的服务对象。
- `伐木`：记录器对象，能够提供 `logger.info（。。。）` 操作等。

### 内嵌

将凹槽脚本直接嵌入到服务配置中。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：600，
  "描述"： "样本"，
  "用户名属性提供者"： [
    "@class"： "org. apereo. cas. 服务. Groovy 注册服务用户提供者"，
    "groovy 脚本"： "groovy { 返回" 属性['uid'][0] ="123456789"，
    "规范化"："上"
  =
}
```

请注意，上述示例中的 `uid` 属性作为多重属性在内部解决，CAS 提取时应将其视为所有属性。 因此， 上述示例使用 `[0]` 语法来获取属性的第一个值。

### 外部

将凹槽脚本用作服务配置之外的外部资源。 脚本必须返回单个 `字符串` 值。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：600，
  "描述"："样本"，
  "用户名属性提供者"： [
    "@class"： "org. apereo. cas. 服务. Groovy 注册服务用户提供者"，
    "凹槽脚本"： "file:///etc/cas/sampleService.groovy"，
    "规范化" ： "上"
  [
}
```

示例 Groovy 脚本如下：

```groovy
logger.info（"从属性中选择用户名属性 $attributes"）
返回"新原则"
```

此组件的配置有资格使用 [弹簧表达语言](../configuration/Configuration-Spring-Expressions.html) 语法。

## 匿名/ 瞬态

为用户名提供不透明的标识符。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："示例"，
  "id"：500，
  "描述"："样本"，
  "用户名归因提供者"：{
    "@class"："org.apereo.cas.服务。匿名注册服务用户属性提供者"
  =

```

## 匿名 / 持久

为用户名提供不透明的标识符。 默认情况下，不透明标识符符合 [个人目标ID](https://spaces.at.internet2.edu/display/federation/Persistent+Identifier+Support) 属性的要求 。 生成的 ID 可能基于现有主属性。 如果未指定或未找到属性，则使用经过验证的委托 ID。

```json
•
  "@class"："org.apereo.cas.服务.注册服务"，
  "服务ID"："样本"，
  "名称"："样本"，
  "id"：500，
  "描述"："样本"，
  "用户名归因提供者"：{
    "@class"："org.apereo.cas.服务.匿名注册服务"，
    "持久生成器"：{
      "@class"： "组织. apereo. cas. 认证. 校长. 希博莱特兼容常机生成器"，
      "盐"： "agvsbg93b3jsza]"，
      "属性"： "
    =
  =
]
```

要模拟行为，您也可以尝试以下命令：

```bash
佩尔 - e '使用文摘： ：沙 qw （sha1_base64）：\
    $digest =sha1_base64（"$SERVICE!$用户！$SALT"）：\
    $eqn =长度（$digest） % 4：打印 $digest：打印"="x（4-$eqn）。 "\n" 
```

将 `$SERVICE` （正在测试的应用程序的网址）、 `$USER` 和 `$SALT` 替换为测试的相应值。

