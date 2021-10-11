# BLBLDL

> Download Bilibili Video.

## 1. Version 1.0.0

### 1.1 项目环境

+ JDK 1.8
+ MySQL 5.7
+ Tomcat 8.5.59
+ ffmpeg 4.3.1

### 1.2 主要功能

+ 根据视频链接查询视频信息
+ 将视频信息保存到数据库
+ 下载视频到本地

### 1.3 主要技术

+ 使用原生的Servlet容器
+ 使用 Druid 作为数据库连接池
+ 使用 DbUtils 与数据库进行CRUD操作
+ 使用原生的 `java.net.HttpURLConnection` 访问网页数据

### 1.4 项目特色

+ 实时更新视频数据
+ 多线程下载视频，下载速度大大提升

### 1.5 可提升空间

+ 视频更新耗时较久，可省略一些不必要的数据更新
+ 需要使用外部程序 ffmpeg
+ 没有显示下载进度
+ 只能下载到本地

