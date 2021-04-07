---
layout: 默认
title: CAS-高可用性性能测试
category: 高可用性
---

# 蝗虫性能测试

[Locust](http://locust.io/) 是易于使用的分布式用户负载测试工具。 它旨在对网站（或其他系统）进行负载测试，并弄清一个系统可以处理多少个并发用户。 [有关更多信息，请参见本指南](http://docs.locust.io/en/latest/what-is-locust.html)

### 设置

Locust的基本功能是用Python代码描述所有测试。 不需要笨拙的UI或庞大的XML，只需简单的代码即可。 为此，您需要 [下载Python](https://www.python.org/downloads/)。 接下来从此处</a> 和下载Locust测试套件 [，配置虚拟环境](https://github.com/apereo/cas/raw/master/etc/loadtests) 以安装模块：</p> 



```bash
pip3 install virtualenv
virtualenv mylocustenv /
pip3 install -r requirements.txt
```


通过以下方法安装蝗虫：



```bash
pip3 install locustio
```


创建一个 `凭据.csv` 文件，其中包含 `用户名和` 个用于负载测试的条目。



```bash
echo casuser，Mellon > cas / credentials.csv
```


像这样运行脚本：



```bash
locust -f cas / casLocust.py --host = https：//cas.example.org
...
[2017-05-02 16：31：49,742] test / INFO / locust.main：在*：8089
启动Web监控器[2017-05-02 16：31：49,744] test / INFO / locust.main：启动蝗虫0.8a2
```


导航到 [http：// localhost：8089](http://localhost:8089) 并继续启动测试群。

对于其他选项，请使用：



```bash
蝗虫-帮助
```
