# PandaTvDanMu
Panda TV  弹幕助手 （ 熊猫TV 弹幕助手） （JAVA）

![](./doc/asset/testPic.png)

# 功能
* 接收某个房间的弹幕和礼物
* 调节界面透明度
* 设置保留的弹幕个数
* 拖动边框直接调节界面大小
* 语音朗读弹幕（需要设置中开启，5个人物提供选择）
* 记住设置信息
* 记住房间号
* 自动检测更新

# 使用
* 查看电脑是否已装java ： 按WIN+R两个键，输入cmd进入控制台，输入java -version，不提示错误即表示已装java
* 如果没装java，<a href="http://java.com/zh_CN/download/manual.jsp" target="_blank">下载</a>后安装
* <a href="https://github.com/Neutree/PandaTvDanMu/releases" target="_blank">点我</a>找到最新版本，下载压缩文件（Neucrack_PandaTV_DanMu.zip）并解压
* 双击start.bat文件运行

# 测试
* Win10测试通过
* win7测试通过
* Ubuntu16.04 测试通过（无全局键、语音）

# 提示
软件还在更新中，请关注新版本。如果因为压缩软件等占用了打开方式，不能直接双击打开，请搜索“双击运行jar”


# 原理简介
* 主要通过抓包软件获取到浏览器与PandaTv的弹幕服务器的通信方式和协议，然后进行编写。
* 主要使用socket进行通信，面向连接的方式来保证弹幕的实时性。开始会使用http来获得弹幕服务器信息以及验证信息，这些信息会在socket通信帧中使用到。
* 协议见[protocol.md](https://github.com/Neutree/PandaTvDanMu/blob/master/doc/protocol.md)

# 声明
源码仅供学习交流使用


# 反馈与参与
* [源码托管地址](https://github.com/Neutree/PandaTvDanMu)
* 如果有疑问或者有建议，欢迎添加issues或者[![Gitter](https://badges.gitter.im/Neutree/PandaTvDanMu.svg)](https://gitter.im/Neutree/PandaTvDanMu?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)，不会用github（这个网站）的，可以到[这里](http://neucrack.com/409)评论
* 可以pull request
