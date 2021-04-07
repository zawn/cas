---
layout: 默认
title: CAS-测试流程
category: 开发者
---

# 测试过程

本页记录了CAS开发人员/贡献者在开发过程中测试CAS服务器部署应采取的步骤。 有关 说明和指南，请 [参见此页面](Build-Process.html)。

## 测试模块

要测试给定的CAS模块提供的功能，请执行以下步骤：

- 对于tomcat，undertow或jetty webapp，将模块引用添加到要运行的Web应用程序 `webapp.gradle`

```gradle
实施项目（“：support：cas-server-support-modulename”）
```

可替换地，设置一个 `casModules` 个项目的属性 `gradle.properties` 或 `〜/ .gradle / gradle.properties` 至 逗号分隔模块的列表，而不 `CAS -服务器` 前缀：

例如：

```properties
casModules = monitor，\
    ldap，\
    x509，\
    bootadmin-client
```

或在命令行上设置属性：

```bash
bc -PcasModules = ldap，x509
```

...其中 `bc` 是构建CAS</a>

别名。</p> 

准备 [嵌入式容器](Build-Process.html#embedded-containers)，以运行和部署Web应用程序。



## 单元/集成测试

为了简化测试执行过程，您可以利用在存储库根目录中找到 `testcas.sh`



```bash
＃chmod + x ./testcas.sh
./testcas.sh --category <category> [--test <test-class>] [--debug] [--with-coverage]
```


要了解有关脚本的更多信息，请使用：



```bash
./testcas.sh-帮助
```


所有单元测试和集成测试均由 [连续集成系统](Test-Process.html#continuous-integration)。



## 代码覆盖率 & 指标

以下平台收集和报告代码覆盖率指标：

|系统|徽章 | ----------------------------------- + ----------- -------------------------------------------------- -------------- + |编纂| [![Codacy Badge](https://app.codacy.com/project/badge/Coverage/29973e19266547dab7ab73f1a511c826)](https://www.codacy.com/gh/apereo/cas/dashboard?utm_source=github.com&utm_medium=referral&utm_content=apereo/cas&utm_campaign=Badge_Coverage) | SonarCloud | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.apereo.cas%3Acas-server&metric=coverage)](https://sonarcloud.io/dashboard?id=org.apereo.cas%3Acas-server) | CodeCov | [![编码](https://codecov.io/gh/apereo/cas/branch/master/graph/badge.svg)](https://codecov.io/gh/apereo/cas)

质量指标是由以下平台收集和报告的：

|系统|徽章 | ----------------------------------- + ----------- -------------------------------------------------- -------------- + |编纂| [![Codacy Badge](https://app.codacy.com/project/badge/Grade/29973e19266547dab7ab73f1a511c826)](https://www.codacy.com/gh/apereo/cas/dashboard?utm_source=github.com&utm_medium=referral&utm_content=apereo/cas&utm_campaign=Badge_Grade) | SonarCloud质量门| [![Sonarqube Quality](https://sonarcloud.io/api/project_badges/measure?project=org.apereo.cas%3Acas-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.apereo.cas%3Acas-server) | SonarCloud可维护性| [![声纳质量](https://sonarcloud.io/api/project_badges/measure?project=org.apereo.cas%3Acas-server&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=org.apereo.cas%3Acas-server) 



## 浏览器 & 功能测试

自动化浏览器测试是通过 [Puppeteer框架](https://pptr.dev/)。 Puppeteer是一个Node库，它提供了一个高级 API来通过DevTools协议控制Chrome或Chromium，并且默认情况下是无头运行的。

功能测试从生成普通的CAS覆盖作为基线开始，该覆盖可以使用预生成的密钥库在HTTPS下运行。 该覆盖图提供了测试方案配置，该配置说明了在嵌入式Apache Tomcat容器内将 一旦运行，Puppeteer脚本将由Node针对给定的测试场景执行，以验证 特定功能，例如成功登录，生成票证等。

所有功能和浏览器测试均由 [连续集成系统](Test-Process.html#continuous-integration)。 如果您 的新一批测试，请确保方案（即测试）名称包含在CI配置中。

为了帮助简化测试过程，您可以在 `.profile`使用以下bash函数：



```bash
函数pupcas（）{
  方案= $ 1
  /path/to/cas/ci/tests/puppeteer/run.sh / path / to / cas / ci / tests / puppeteer / scenarios /“${scenario}”
}
```


...以后可以通过以下方式调用：



```bash
up <scenario-name>
```


要成功运行测试，您需要安装 [jq](https://stedolan.github.io/jq/)



## 持续集成

单元和集成测试由CAS CI系统 [GitHub Actions](https://github.com/apereo/cas/actions)自动执行。
