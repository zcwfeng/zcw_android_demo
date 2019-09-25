android_dagger2 展示了dagger2 如何使用快速实例

结合Okhttp例子

1. Dagger2:Module,Component,Container 的关系

2. 注解使用和关系 @Inject @Component @Module @Singleton @Providers

@Inject
@Module includes
@Providers 参数传递
@Singleton
@Component dependents...


# 依赖使用，三种方式：

- 指定子Module的方式   

@Module(includes = {HttpModule.class})//子模块(Component 指定也行)

- Component 里面进行modules 声明

@Component(modules={UserModule.class,HttpModule.class})

- Component 依赖子Component

@Component(modules={UserModule.class},dependencies = HttpComponent.class)

# 一个场景：

@Named 是 @Qualified 实现的一种

@Qualified 主要是用来区分不同对象的实例

同时有两个版本，一个测试，一个release。用自定义annotation实现加上@Qualified
也可以用@Named("tag") 做区分，分别测试和上线使用

# SubComponent

1. 同事具备了两种不同的生命周期Scope，Subcomponent具备了父Component拥有的Scope，也具备了自己的Scope

2. SubComponent的Scope范围小于父Component

# Lazy 与 Provider





# @singleton需要注意：
Componet和Module必须一致，同时加上或者不加
多个Component 使用同一个对象的@singleton 不同的，每个Component是一个单例


3. 源代码分析-本质

4. 注意事项

- Component的inject方法接受父类型参数，而调用时传入子类型对象，无法注入（为啥）
- Component关联的modules中不能有重复的provide
- Module的provide方法中使用了scope，那么关联的Component里面必须使用同一个注解如，singleton
- module的provide的方法没有使用scope，那么Compon和moudle是否加注解无关紧要
- Component的dependencies与Component自身的scope不能相同，及组件之间的scope不同
- Singleton组件不能依赖其他scope的组件，只能scope组件依赖Singlton
- 没有scope的Component不能依赖有scope的Component
- 一个Component不能有多个scope，（Subcomponent除外）