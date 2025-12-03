<h1 align="center">ArknightsPriestess</h1>
<p align="center">
  明日方舟开源Java自动工具,名字取自<b>Eyes of Priestess - 女祭司之眼</b>(普瑞塞斯之眼)
  <br><br>
  <a href='https://blog.ysneko.com'><img src="https://img.shields.io/static/v1?label=Powered%20by&message=Y5neKO&color=green"></a>
  <a href='https://www.oracle.com/java/technologies/downloads/#java8'><img src="https://img.shields.io/static/v1?label=JDK&message=1.8&color=yellow"></a>
  <br><br>
  <a href="#">
    <img src="https://img.shields.io/badge/Supported%20by-Alipay🈲%20%E2%86%92-gray.svg?colorA=655BE1&colorB=4F44D6&style=for-the-badge"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Supported%20by-WechatPay🈲%20%E2%86%92-gray.svg?colorA=61c265&colorB=4CAF50&style=for-the-badge"/>
  </a>
  <br><br>
  <a>———— 不准忘记我。</a>
  <br>
  <a>———— Don't forget me.</a>
  <br><br>
  <img src="https://img-blog.csdnimg.cn/7512889713bd422dbd791e17359ed1d3.png" alt="image-20220503164740855" style="zoom:30%;" />
  <img src="https://img-blog.csdnimg.cn/66faca6cebf54f778e201362c8a34388.png" alt="image-20220503164740855" style="zoom:30%;" />
</p>

## AKP - ArknightsPriestess
正在开发中....

广泛向各位前端大佬征求前端设计!

## 开发界面

![img.png](README_IMG/img.png)

## 默认配置
`JDK 8u411`  |  `IDE Intellij 2024.2`  |  `语言等级 8`

## 架构
后端: SpringBoot

GUI: JavaFx + WebView + HTML5 + JavaScript + CSS

启动器: VBS(打包EXE)

_ps:后续或许会转为C++来写_

### 后端API说明
```zsh
heartbeat                       # 心跳包
api                             # API端点
 |----- main_console            # 执行著控制台函数（测试）
 |----- console_log             # 获取控制台日志
 |----- clear_log               # 清除控制台日志
 |----- screenshot              # 获取当前截图
 |----- eyes-of-priestess       # 普瑞塞斯之眼（处理后的截图）
```

## 目录结构
`AKPUI` GUI文件目录

`asset` 资产目录,包括用以识别的ui、按钮模板

`env` 内置运行环境(jdk8u411)

`files` 工具需要的重要文件

`log` 日志目录

`platform-tools` adb工具目录

`screenshots` 暂存adb截图文件,识别出错可以查看该文件夹内的截图状态

`AKP.vbs` 启动器vbs原文件

`asset.properties` 资产路径配置文件

`config.properties` 工具全局配置文件

`GUI.bat` GUI启动批处理文件

`Launcher.exe` VBS转EXE启动器

`SpringBoot.bat` SpringBoot启动批处理文件

## 版本&更新日志
**版本** v0.2

- *2022.04.30* | 发布了 v0.1 测试版本
- *2022.09.24* | 重新架构

## 贡献者

<a href="https://github.com/Y5neKO/ArknightsPriestessProject/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Y5neKO/ArknightsPriestessProject&t=123" />
</a>

## 贡献者们

[![Stargazers repo roster for @Y5neKO/ClosureVulnScanner](http://reporoster.com/stars/Y5neKO/ArknightsPriestessProject)](https://github.com/Y5neKO/ArknightsPriestessProject/stargazers)


## 使用许可
[MIT](LICENSE) © Y5neKO
