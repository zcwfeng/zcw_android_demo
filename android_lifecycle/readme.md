LifeCycle之 ViewModel

- 核心：

    doClear方法
    再Activity finish的时候，自动调用doClear。帮助释放，解决内存泄漏
    
    ViewModelProviders这个类，本质上其实是一个工厂类。
    这个类内部包含了一个ViewModelStore实例，它负责存储创建的ViewModels。
    同时可以使用ViewModelProvider的get（）方法来获取作为参数传入的ViewModel类型的实例。
    
- ViewModel 被设计出来，不仅为了解决上面所说的configuration改变时候能保留数据。其真正意义在于以下几个方面：

    ① 职责分离：使Activity/Fragment不用再负责从某些数据源获取数据，只需要负责展示数据就好，同时还消除了在配置更改时保留数据对象实例的引用的责任。这两个职责都转给了ViewModel。
    ② 简化对没用数据的清理：当Activity/Fragment负责清理数据的操作时，需要使用大量代码来清理这些请求。但是将这些清理操作放到ViewModels onCleared()方法中，这些资源在Activity结束时会自动清除。
    ③ 减少类的膨胀：由于职责的转移，Activity/Fragment删除了许多用于处理请求，状态持久性和注销数据的代码。这些代码通常会导致Activity/Fragment变得非常臃肿，这样的代码会难以扩展和维护。使用ViewModels可以帮助开发者缓解Activity/Fragment的膨胀，使各个类的职责尽可能单一。
    ④ 容易测试：职责的分离会使测试这些职责更容易，而且还可以产生更细粒度的测试用例。
    
LiveData