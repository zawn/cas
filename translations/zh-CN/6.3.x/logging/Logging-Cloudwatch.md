---
layout: 默认
title: CAS-Cloudwatch日志记录配置
category: 记录 & 审核
---

# Cloudwatch记录

日志数据可以自动路由到 [AWS CloudWatch](https://aws.amazon.com/cloudwatch/)。 通过在叠加层中包含以下模块来启用支持：

```xml
<dependency>
     <groupId>org.apereo.cas</groupId>
     <artifactId>cas-server-support-logging-config-cloudwatch</artifactId>
     <version>${cas.version}</version>
</dependency>
```

通过上述模块，您可以声明一个特定的附加程序以与AWS CloudWatch进行通信：

```xml
<CloudWatchAppender名称=“ cloudWatch”
                    awsLogGroupName =“ LogGroupName”
                    awsLogStreamName =“
                    awsLogRegionName =“ us-west-1”
                    credentialAccessKey =“ ...”
                    credentialSecretKey =“ ...”
                    awsLogStreamFlushPeriodInSeconds =“ 5”
                    createIfNeeded =“ true”
                    createLogGroupIfNeeded =“ false”
                    createLogStreamIfNeeded =“ false”>
    <PatternLayout>
        <Pattern>%5p | %d{ISO8601}{UTC} | %t | %C | %M：%L | %m %ex %n</Pattern>
    </PatternLayout>
</CloudWatchAppender>
...
<Logger name="org.apereo" additivity="true" level="debug">
    <appender-ref ref="cloudWatch" />
</Logger>
```

在相关时会自动从以下来源获取AWS凭证，并通过CAS配置使其成为可能：

1. 链接到IAM角色的EC2实例元数据。
2. 外部属性文件，其中包含 `accessKey` 和 `secretKey` 作为属性键。
3. AWS配置文件路径和配置文件名称。
4. 包括系统属性 `aws.accessKeyId`， `aws.secretKey` 和 `aws.sessionToken`
5. `AWS_ACCESS_KEY_ID` `AWS_SECRET_KEY` 和 `AWS_SESSION_TOKEN`环境变量。
6. 类路径上的属性文件为 `awscredentials.properties` ，其中包含 `accessKey` 和 `secretKey` 作为属性键。
7. 访问密钥和机密的静态凭据直接由手头的配置提供（日志记录等）。

`createIfNeeded`， `createLogGroupIfNeeded`，和 `createLogStreamIfNeeded` 是可选的; `createIfNeeded` 将默认为 `true` 而 `createLogGroupIfNeeded` 和 `createLogStreamIfNeeded` 默认为 `false`。 任何为 `true` 值都 优先级（即，将all设置为 `false` 不会创建任何东西）。
