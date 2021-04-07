---
layout: 默认
title: CAS-春季表达
category: 安装
---

# 春季表达语言

CAS中的许多组件都可以利用 即 [Spring Expression Language](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions) 语法 进行内部配置。 希望访问系统变量，环境属性或通常需要更具动态性或 编程策略才能完全起作用时，这尤其有用。

表达式应封装在 `${...}` 语法内。 预定义变量 应该以 `＃` 字符开头。 以下预定义变量可用：

| 多变的             | 描述                  |
| --------------- | ------------------- |
| `系统属性`          | 系统属性映射，通常在启动时加载一次。  |
| `系统道具`          | 与上述相同。              |
| `各种`            | 环境变量的映射，通常在启动时加载一次。 |
| `环境变量`          | 与上述相同。              |
| `信封`            | 与上述相同。              |
| `环保`            | 与上述相同。              |
| `临时目录`          | 临时目录的路径。            |
| `乌伊德`           | 自动生成的 `UUID` 值。     |
| `randomNumber2` | 2位随机数。              |
| `randomNumber4` | 4位随机数。              |
| `randomNumber6` | 6位随机数。              |
| `randomNumber8` | 8位随机数。              |
| `randomString4` | 4个字符的随机词。           |
| `randomString6` | 6个字符的随机词。           |
| `randomString8` | 8个字符的随机词。           |

## 例子

- 假设系统属性 `层` 的值为 `生产` ，则配置 值 `file：// $ {#systemProperties ['tier']} /file.json` 转换为 `file：// production / file .json`
- 假设值为 `qa` 环境变量 `tier` 可用，则配置 值为 `file：// $ {#environmentVariables ['tier']} /file.json` 转换为 `file：// qa / file .json`
- 使用 `${#randomString6}` 转换为6个字符的随机单词，例如 `qemguz`。
- 使用 `${#randomNumber8}` 转换为8位随机数，例如 `75915283`。
