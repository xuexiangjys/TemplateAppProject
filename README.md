# TemplateAppProject
模版空壳Android工程，快速搭建（集成了XUI、XUtil、XAOP、XPage、XUpdate和XHttp2)

## 集成介绍

> 精选了X系列最实用的几个库，可大大节约开发的效率。

* [XUI 一个简洁而优雅的Android原生UI框架，解放你的双手！](https://github.com/xuexiangjys/XUI)

* [XUtil 一个方便实用的Android工具类库！](https://github.com/xuexiangjys/XUtil)

* [XAOP 一个轻量级的AOP(Android)应用框架。囊括了最实用的AOP应用。](https://github.com/xuexiangjys/XAOP)

* [XPage 一个非常方便的fragment页面框架。](https://github.com/xuexiangjys/XPage)

* [XUpdate 一个轻量级、高可用性的Android版本更新框架。](https://github.com/xuexiangjys/XUpdate)

* [XHttp2 一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp组合进行封装。](https://github.com/xuexiangjys/XHttp2)

## 使用方式

1.克隆项目

```
git clone https://github.com/xuexiangjys/TemplateAppProject.git
```

2.修改项目名（文件夹名），并删除目录下的.git文件夹（隐藏文件）

3.使用AS打开项目，然后修改`包名`、`applicationId`和`app_name`

* 修改包名

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/1.png)

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/2.png)

* 修改applicationId

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/3.png)

* 修改apk打包名

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/4.png)

* 修改app_name

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/5.png)

## 项目打包

1.修改工程根目录的`gradle.properties`中的`isNeedPackage=true`。

2.添加并配置keystore，在`versions.gradle`中修改`app_release`相关参数。