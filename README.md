
#最新版本App可从应用宝上下载安装（应用名：随心播）

[APK展示](http://android.myapp.com/myapp/detail.htm?apkName=com.tencent.qcloud.suixinbo)   

#集成太难？

[参见简单DEMO](https://github.com/zhaoyang21cn/ILiveSDK_Android_Demos)  
            

# 导入配置 (根目录下有快速入门文档)
###请注意配置jcenter库 腾讯内部是自己的maven
![maventojcenter](http://raw.github.com/zhaoyang21cn/Android_Suixinbo/master/raw/settings.png)



###配置自己的版本号
![version](http://raw.github.com/zhaoyang21cn/Android_Suixinbo/master/raw/settings2.png)




###如果你的项目中使用proguard等工具做了代码混淆，请保留以下选项。
<pre>
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**

-keep class tencent.**{*;}
-dontwarn tencent.**

-keep class qalsdk.**{*;}
-dontwarn qalsdk.**
</pre>

###配置权限和服务
![version](http://raw.github.com/zhaoyang21cn/Android_Suixinbo/master/raw/services.png)

![version](http://raw.github.com/zhaoyang21cn/Android_Suixinbo/master/raw/auth.png)




###随心播的Spear的配置
因随心播的参数配置较高，因此对主播上行带宽有要求
![Spear配置](https://raw.githubusercontent.com/zhaoyang21cn/Android_Suixinbo/master/QQ%E6%88%AA%E5%9B%BE20160520170326.jpg)


##最新版本App可从应用宝上下载安装（应用名：随心播）

http://android.myapp.com/myapp/detail.htm?apkName=com.tencent.qcloud.suixinbo




