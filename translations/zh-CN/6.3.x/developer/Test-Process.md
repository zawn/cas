---
layout: 违约
title: CAS - 测试过程
category: 开发 人员
---

# 测试过程

本页记录了 CAS 开发人员/贡献者在开发过程中测试 CAS 服务器部署时应采取的步骤。 有关一般构建过程的其他 说明和指导，请 [](Build-Process.html)查看此页面。

## 测试模块

要测试给定的 CAS 模块提供的功能，执行以下步骤：

- 对于 tomcat、底座或码头 Webapp，将模块引用添加到 `webapp.gradle` 构建您打算运行的 Web 应用程序脚本：

```gradle
实施项目（"：支持：cas-服务器-支持-模块名"）
```

或者，在根项目的 `语法中设置一个 <code>的 casmodules` 属性。属性</code> 或 `~/.gradle/gradle.属性` 到没有 `cas-server` 前缀的模块的 逗号分离列表：

例如：

```properties
卡斯模块=监视器，\
    ldap，\
    x509，\
    引导管理客户端
```

或将属性设置在命令行上：

```bash
bc -帕斯模块=阿尔达普，x509
```

...其中 `，因为` 是一个 [别名建设CAS](Build-Process.html#sample-build-aliases)。

准备 [嵌入式容器](Build-Process.html#embedded-containers)，以运行和部署 Web 应用程序。

## 单元/集成测试

为了简化测试执行过程，您可以利用存储库根部找到的 `testcas.sh` 脚本：

```bash
#chmod =x ./testcas.sh
。/testcas.sh - 类别 <category> [-测试 <test-class>] [--debug] [--with-coverage]
```

要了解有关脚本的更多内容，请使用：

```bash
。/testcas.sh - 帮助
```

所有单元和集成测试均由 [连续集成系统](Test-Process.html#continuous-integration)执行。

## 代码覆盖 & 指标

代码覆盖指标由以下平台收集和报告：

|系统|徽章 |--------------------------------------------------------------------------------------------------------------+ |科达西·| [![Codacy Badge](https://app.codacy.com/project/badge/Coverage/29973e19266547dab7ab73f1a511c826)](https://www.codacy.com/gh/apereo/cas/dashboard?utm_source=github.com&utm_medium=referral&utm_content=apereo/cas&utm_campaign=Badge_Coverage) |声纳云| [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.apereo.cas%3Acas-server&metric=coverage)](https://sonarcloud.io/dashboard?id=org.apereo.cas%3Acas-server) |代码科夫| [![科德科夫](https://codecov.io/gh/apereo/cas/branch/master/graph/badge.svg)](https://codecov.io/gh/apereo/cas)

质量指标由以下平台收集和报告：

|系统|徽章 |--------------------------------------------------------------------------------------------------------------+ |科达西·| [![Codacy Badge](https://app.codacy.com/project/badge/Grade/29973e19266547dab7ab73f1a511c826)](https://www.codacy.com/gh/apereo/cas/dashboard?utm_source=github.com&utm_medium=referral&utm_content=apereo/cas&utm_campaign=Badge_Grade) |声纳云质量门| [![Sonarqube Quality](https://sonarcloud.io/api/project_badges/measure?project=org.apereo.cas%3Acas-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.apereo.cas%3Acas-server) |声纳云可维护性| [![声纳库贝质量](https://sonarcloud.io/api/project_badges/measure?project=org.apereo.cas%3Acas-server&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=org.apereo.cas%3Acas-server)

## 浏览器 & 功能测试

自动浏览器测试是通过 [木偶框架](https://pptr.dev/)完成的。 木偶是一个节点库，它提供了一个高级 API控制铬或铬超过DevTools协议，并在默认情况下运行无头。

功能测试首先生成一个普通的 CAS 叠加，作为能够使用预生成的密钥存储在 HTTPS 下运行的基线。 此叠加提供测试场景配置，解释 CAS 部署在嵌入式 Apache Tomcat 容器内 时所需的模块、属性等。 运行后，Puppeteer 脚本由节点执行，用于给定的测试场景，以验证 特定功能，如成功登录、生成票证等。

所有功能和浏览器测试均由 [连续集成系统](Test-Process.html#continuous-integration)执行。 如果您 正在添加新一批测试，请确保将场景（即测试）名称包含在 CI 配置中。

为了帮助简化测试过程，您可以在 `中使用以下 bash 功能。配置文件`：

```bash
功能 pupcas （） [
  场景= $1
  /路径/到/cas/ci/测试/木偶/运行.sh/路径/到/cas/ci/测试/木偶/场景/"${scenario}"
|
```

...以后可以调用为：

```bash
普卡斯 <scenario-name>
```

要成功运行测试，您需要安装 [jq](https://stedolan.github.io/jq/) 。

## 持续集成

单位和集成测试由中科院CI系统自动执行， [GitHub行动](https://github.com/apereo/cas/actions)。
