## 一、美柚Apm
用于收集美柚系APP的应用性能，包括网络请求,UI卡顿，SQL异常，错误异常等

### 收集维度：
- 应用崩溃： 
使用第三方bugly实现
- ANR/卡顿 
		[错误类别, 收集时间, "标签", "描述", "原因", [堆栈信息], {附加信息}]
- 关键页面响应数据
 [页面名字, 页面启动到可见时长(ms),页面进入时间（ms）,  内存百分比（%），内存占用量（kb）, CPU占比（%）]
 
- 异常HTTP响应数据 
   ["url", 请求发送时间, 总响应时间, 首包时间, DNS解析时间, TCP建联时间, SSL握手时间,
         HTTP状态码, 错误码, Content-Type, 网络类型, 总接受字节数, 总发送字节数, IP地址, 请求方法]
- 异常WebViw响应数据
 ["url", 请求发送时间, 总响应时间, 首包时间, DNS解析时间, TCP建联时间, SSL握手时间,
         白屏时间, 网络类型, [资源列表...]
- 异常SQL收集
[数据库文件路径, 数据库文件大小, 错误码, 错误消息, 语句, 执行时长, 执行时间],


### 收集方法：
1. APP启动时候，调用配置接口。获取是否开启，采集频率，阈值等参数等
2. SDK载入配置，并收集数据后保存到本地是数据库里面。
3. 通过HTTP/ TCP 通道等上传数据到服务端
4. 服务端根据数据，可视化显示出来。

### 收集方式：
- 包装方法给业务端端调用：sql,关键页面响应时间
- AspectJ AOP收集 调用OKHTTP库
- 拦截系统的方法，注入自己的功能：WebView, 卡顿等

### 补充：
1. release的包通常是关闭log 输出，并且代码是混淆的。 混淆产生的Map文件通常几十M比较大，自己去实现一个类似的异常日志系统有很大难道，采用bugly或者umeng
2. 服务端通常用ELK等分析请求上下文。
3. 服务端的APM工具比较多，客户端主要是两家：TalkingData，听云



### 二、SDK安装

1. 在buildscript模块中加入代码，类似：
```groovy
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
```

添2. 加Apm符号表插件

```groovy
apply plugin: 'android-aspectjx'
```  
为了加快 代码织入速度，可以使用过滤功能
aspectjx {
    //织入遍历符合条件的库
//    includeJarFilter 'xlog-temp'
//    过滤掉 okHTTP自身的库，避免重复代码
    excludeJarFilter 'com.squareup.okhttp3'
} 

3. 打开项目工程主模块下的build.gradle（Module）文件，在dependencies模块中添加代码
   
```groovy
compile "com.meiyou.apm:apm-agent:0.0.1-SNAPSHOT" 
```

4. apm.0.2.0版本开始，需要用到 主工程的 myClent和TCP 功能。因此，需要在主工程新增接口是实现类：
```java
 //com.meiyou.common.apm.ApmProxyImpl
 com.meiyou.common.apm.ApmProxyImpl
```
#### 三、插入初始化探针代码
1. 在“Application”中的onCreate()方法（如未找到该方法请新增onCreate()）中初始化Android SDK
```java
ApmAgent.setLicenseKey("XXX").setDebug(true).start(this);
```

#### 四、嵌码验证
嵌码完成后可通过“LogCat”查看SDK日志输出结果，用以进行数据收集服务器校验TAG为APM-Agent，标准日志输出结果如下所示：
```
APM-Agent start
APM-Agent enabled
connect success
```

## 附录
#### 支持协议类库
- Volley+OkHttpClient
- OkHttp 2.0+
- OkHttp 1.0