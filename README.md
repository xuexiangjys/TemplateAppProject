# TemplateAppProject

Android空壳模板工程，快速搭建（集成了XUI、XUtil、XAOP、XPage、XUpdate、XHttp2、友盟统计和walle多渠道打包)

## 关于我

[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)   [![简书](https://img.shields.io/badge/简书-xuexiangjys-red.svg)](https://www.jianshu.com/u/6bf605575337)   [![掘金](https://img.shields.io/badge/掘金-xuexiangjys-brightgreen.svg)](https://juejin.im/user/598feef55188257d592e56ed)   [![知乎](https://img.shields.io/badge/知乎-xuexiangjys-violet.svg)](https://www.zhihu.com/people/xuexiangjys) 

## 效果

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/demo.gif)

## 集成介绍（请star支持）

> 本项目是项目androidx项目。精选了X系列最实用的几个库，可大大提高开发的效率。

* [XUI 一个简洁而优雅的Android原生UI框架，解放你的双手！](https://github.com/xuexiangjys/XUI)

* [XUtil 一个方便实用的Android工具类库！](https://github.com/xuexiangjys/XUtil)

* [XAOP 一个轻量级的AOP(Android)应用框架。囊括了最实用的AOP应用。](https://github.com/xuexiangjys/XAOP)

* [XPage 一个非常方便的fragment页面框架。](https://github.com/xuexiangjys/XPage)

* [XUpdate 一个轻量级、高可用性的Android版本更新框架。](https://github.com/xuexiangjys/XUpdate)

* [XHttp2 一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp组合进行封装。](https://github.com/xuexiangjys/XHttp2)

除此之外，还集成了其他优秀的第三方库:

* [AndroidAutoSize 优秀的屏幕适配方案](https://github.com/JessYanCoding/AndroidAutoSize)

* [butterknife 控件依赖注入框架](https://github.com/JakeWharton/butterknife)

* [vlayout 阿里巴巴开源淘宝多布局组件](https://github.com/alibaba/vlayout)

* [MMKV 腾讯开源键值对存储](https://github.com/Tencent/MMKV)

* [leakcanary 内存泄漏检测](https://github.com/square/leakcanary)

## 更多资源

* [简化版Android空壳模板工程](https://github.com/xuexiangjys/TemplateSimpleProject)

* [X-Library系列Android应用框架详解](https://github.com/xuexiangjys/AndroidAdvancedLearning/blob/master/framework/X-Library系列Android应用框架详解.md)

* [Flutter空壳模板工程](https://github.com/xuexiangjys/flutter_template)

## 使用方式

[视频教程-如何使用模板工程](https://www.bilibili.com/video/av92348545)

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

* 修改app_name

![](https://github.com/xuexiangjys/Resource/blob/master/img/templateproject/5.png)

## 项目打包

1.修改工程根目录的`gradle.properties`中的`isNeedPackage=true`。

2.添加并配置keystore，在`versions.gradle`中修改`app_release`相关参数。

3.如果考虑使用友盟统计的话，在`local.properties`中设置应用的友盟ID:`APP_ID_UMENG`。

4.使用`./gradlew clean assembleReleaseChannels`进行多渠道打包。

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![](https://github.com/xuexiangjys/Resource/blob/master/img/pay/alipay.jpeg) &emsp; ![](https://github.com/xuexiangjys/Resource/blob/master/img/pay/weixinpay.jpeg)

感谢下面小伙伴的打赏：

姓名 | 金额 | 方式
:-|:-|:-
myie9 | 100￥ | 微信
*鸥 | 10.24￥ | 微信


## 联系方式

[![](https://img.shields.io/badge/点击一键加入QQ交流群-602082750-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=9922861ef85c19f1575aecea0e8680f60d9386080a97ed310c971ae074998887)

![](https://github.com/xuexiangjys/Resource/blob/master/img/qq/qq_group.jpg)