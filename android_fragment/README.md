Fragment 通信原则：

很重要一个原则
    您常常希望 Fragment 之间能够进行通信，以实现某种目的，例如根据用户事件改变内容。所有 Fragment 间的通信通过共享的 ViewModel 或关联的 Activity 来完成。`两个 Fragment 不得直接通信。`
    
FragmentManager

Fragment 解耦

通信：接口，网络，eventbus，rxbus，广播，handler

    handler缺点：
        缺点：
        耦合，
        无法获取activity返回结果，handler单向通信
        内存泄露
        
    广播：
        缺点：
        1.性能差，延迟（充电，重启，wifi，蓝牙，电话，短信等）单一发射源，多个接收方
        2.通信体系重，一个发生，多个接受，广播的数量是有限的
        3.传播的数据有限
        4.代码冗余

    EventBus：
        缺点：
        1.通过反射，性能打折，效率低
        2.代码维护困难
        3.数据无法返回,单向传递

    接口：
            简单，效率高，方便，解耦合
        缺点：
            代码冗余，每个fragment都必须定义自己的独一无二的接口
            
    解决： 万能接口
    
        1. 定义Function抽象类，实现四种抽象类，有参数有返回值，无参数无返回值，只有参数，只有返回值
        2. 定义FunctionManager 作为管理Function接口和容器，提供单例模式对外使用
        3. 定义BaseFragment 引用FragmentManager set方法