---
layout: 违约
title: CAS - 高可用性性能测试
category: 高可用性
---

# 蝗虫性能测试

[蝗虫](http://locust.io/) 是一种易于使用、分布式、用户负载测试的工具。 它用于负载测试网站（或其他系统），并找出一个系统可以处理多少并发用户。 [有关详细信息，请参阅本指南](http://docs.locust.io/en/latest/what-is-locust.html) 。

### 设置

蝗虫的一个基本特征是，你用Python代码描述你所有的测试。 不需要笨重的 Uis 或膨胀的 Xml， 只是简单的代码。 要使此工作，您将需要 [下载Python](https://www.python.org/downloads/)。 接下来从此处下载蝗虫测试套件 [，](https://github.com/apereo/cas/raw/master/etc/loadtests) 并 [配置虚拟环境](https://virtualenv.pypa.io/en/stable/) 安装模块：

```bash
pip3安装虚拟
虚拟
pip3安装-r要求.txt
```

通过以下方式安装蝗虫：

```bash
pip3安装蝗虫
```

创建一个 `凭据.csv` 文件，其中包含用于加载测试的 `用户名、密码` 条目。

```bash
回声卡苏瑟，梅隆 > 卡/证书.csv
```

运行脚本如下：

```bash
蝗虫 - f 卡斯/卡斯洛库斯特.py -- 主
机
[2017-05-02 16：31：49，742] 测试/INFO/蝗虫.主： 开始网络监测在 *：8089
[2017-05-02 16：31：49，744] 测试/INFO/蝗虫.主： 开始蝗虫 0.8a2
```

导航到 [http://localhost:8089](http://localhost:8089) ，然后开始测试群。

有关其他选项，请使用：

```bash
蝗虫-帮助
```
