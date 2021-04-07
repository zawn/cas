---
layout: 违约
title: CAS - 云表记录配置
category: 日志 & 审计
---

# 云表记录

日志数据可以自动路由到 [AWS 云监视](https://aws.amazon.com/cloudwatch/)。 支持通过在覆盖中包括以下模块来启用：

```xml
<dependency>
     <groupId>组织.apereo.cas</groupId>
     <artifactId>卡斯服务器支持-记录-配置-云表</artifactId>
     <version>${cas.version}</version>
</dependency>
```

然后，通过上述模块，您可以声明特定的应用程序，以便与 AWS 云监视进行通信：

```xml
<云观察应用程序名称="云观察"
                    awsLog 组名="日志群名"
                    awsLogStream 名称="登录人名称"
                    awsLog 区域名称="我们-西-1"
                    凭据访问键]"..."
                    凭据秘密钥匙]"。。。"
                    "5"
                    创建"真实"
                    创建"假"
                    创建"假"="假">
    <PatternLayout>
        <Pattern>%5p | %d{ISO8601}{UTC} | %t | %C | %M：%L | %m %ex %n</Pattern>
    </PatternLayout>
</CloudWatchAppender>
...
<Logger name="org.apereo" additivity="true" level="debug">
    <appender-ref ref="cloudWatch" />
</Logger>
```

AWS 凭据会自动从以下来源获取，如果相关，则通过 CAS 配置实现：

1. 与 IAM 角色关联的 EC2 实例元数据。
2. 包含 `访问的外部属性文件键` 和 `密钥` 作为属性密钥。
3. AWS配置文件路径和配置文件名称。
4. 系统属性， 包括 `aws. 访问键`， `aws. 秘密钥匙` 和 `aws. 会话`
5. 包括 `AWS_ACCESS_KEY_ID`、 `AWS_SECRET_KEY` 和 `AWS_SESSION_TOKEN`的环境变量。
6. 分类路径上的属性文件为 `ascredentials.包含 <code>访问键` 和 `密钥` 作为属性密钥的属性</code> 。
7. 访问密钥和由手头配置直接提供的秘密的静态凭据（记录等）。

`创建"需要`"， `创建"需要`"， `创建"需要的"` 是可选的： `创建"如果需要的` 将默认 `真正的` ，而 `创建LogGroupIf需要` ，并 `创建LogStreamIfne` 默认 `虚假`。 任何 `真正的` 价值都需要 先见之（即，将一切设定为 `虚假的` ，以不创造任何东西）。
