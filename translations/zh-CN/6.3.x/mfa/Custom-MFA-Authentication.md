---
layout: 违约
title: CAS - 自定义多因素认证
category: 多因素认证
---

# 自定义多因素身份验证

要创建您自己的自定义多因素身份验证提供商，您需要设计主要在唯一标识符下注册进入 CAS 网络流引擎的自定义身份验证流的组件。 稍后，您还需要考虑策略，通过这些策略，您的自定义多因素身份验证提供商 [可以触发](Configuring-Multifactor-Authentication-Triggers.html)。

## 提供商 ID

每个多因素提供商都会被分配一个唯一的标识符，该标识符通常映射或与底层 Web 流相等。 唯一标识符可以是您选择的任何任意字符串，只要它保持独特和明智，因为它，根据使用案例，可用于其他系统和其他应用程序作为触发器。

为了本指南的目的，让我们选择 `mfa 定制` 作为我们的提供商 ID。

## 网络流 XML 配置

流配置文件需要放置在 `src/主/资源/网络流/mfa 自定义` 目录内，该目录命名为 `mfa-自定义.xml` 其轮廓如下：

```xml
<？xml 版本="1.0"编码="UTF-8"？>
<flow xmlns="http://www.springframework.org/schema/webflow"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/webflow http://www.springframework.org/schema/webflow/spring-webflow.xsd">

    <!-- 
        定义状态和行动... 
    ->
    <end-state id="success" />
</flow>
```

## 注册网络流配置

自定义提供商本身是它自己的独立网络流，然后在主身份验证流中注册。

```java
公共类自定义授权人Webflow配置器扩展抽象卡多因子网络流配置器
    公共静态最终字符串MFA_EVENT_ID= "mfa-自定义";

    /*
        根据主类定义适当的构造器
        公共自定义器网络流配置器（...） {
        }
    */  

    @Override
    受保护的无效做启动（）抛出例外{
        注册多因素提供者授权网络流（获取LoginFlow（），
                MFA_EVENT_ID，您的客户法花定义注册）;
    =
}
```

## 设计提供商

中科院的多因素认证提供者以 `多因素验证提供者` 实例的形式表示。 提供商的轮廓在下面简短地显示，其大部分行为被删除，转而支持默认值。

```java
公共类自定义多因素授权提供扩展抽象多因素授权提供器
    私人静态最终长串行VersionUID = 4789727148634156909L：
=
```

## 注册提供商

自定义网流配置需要在 CAS 注册。 配置注册的大纲如下示例和汇总：

```java
包组织.示例.cas;

@Configuration（"自定义验证器子系统配置"）
公共类自定义验证器系统配置器=
    。。。
    @Bean
    公共流量定义注册自定义流程注册 （） =
        var 构建器 = 新的流量定义注册构建器（应用配置、流量构建器服务）;
        构建器。setBasePath（"类路径*："网络流"）;
        构建器。 .xml）：
        返回建设者。build（）;
    =

    @Bean
    公共多因素授权商自定义授权提供者（）=
        var p= 新的自定义多因素授权提供者（）;
        p.setid（"mfa-custom"）;
        返回p：
    =

    @Bean
    @DependsOn（"默认网络流配置器"）
    公共卡斯韦弗罗配置器自定义网络流配置器（）{
        返回新的自定义签名者网络流配置器（。。。）;
    = 

    @Bean
    公共 CasWebflow 执行计划配置器自定义网络流执行计划配置器 （）
        返回计划 -> 计划. 注册网络流配置器 （自定义网络流配置器）：
    =
    ...
}
```

不要忘记在 CAS 注册配置类。 [请参阅本指南](../configuration/Configuration-Management-Extensions.html) 了解更多详细信息。

## 触发器

自定义身份验证网络流可以使用任何 [支持的选项](Configuring-Multifactor-Authentication-Triggers.html)触发。
