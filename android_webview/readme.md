架构

App层
业务组件1，业务组件…业务组件n（module）
common ——————所有程序员都可以改
network
base  ———通用，这层高级工程师，架构师才能改的

关于WebView架构设计

满足

1.好用，用户体验
2.高可靠，webview出问题不影响主进程
3.可扩展，html页面是不是经常和native交互，增加功能方便
4.满足activity + fragment
5. 模块化，层次化，组件化，控件话

不能用webview

toC 海量用户的app
安全性比较高的

-------------------------------

1. 通过webview 学习，搭建好用，可靠，方便扩展 实战app
2. 组件化（auto service比 arouter更简单可靠 cc 基本字符串）
    模块化
    层次化
    控件化
    接口下沉
    
3. webview 用单页面形式进行打开，同时支持是否显示actionbar

activity + fragment

4. loadSir 进度注入

BaseApplication mApplication 都能通过它拿到context
不需要UI的context database preference
这样和整个进程声明周期相同，减少内存泄露可能

5. smartRefreshLayout

6. WebViewChromeClient
    WebViewClient
    
    
    todo 跨进程，命令模式，cookie
    
-------------------------------