# Jetpack 之 Paging

### 使用Paging的好处是什么:

​	好处一：分页库可以更轻松地在应用程序中的RecyclerView逐步和优雅地加载数据

​	好处二：数据请求消耗的网络带宽更少，系统资源更少

​	好处三：即使在数据更新和刷新期间，应用程序仍会继续快速响应用户输入

​	好处四：不过多浪费，显示多少就用多少



### Paging的使用：

角色一：DataSource（是数据源，包含了多种形式，例如：Room来源，PositionalDataSource来源，PageKeyedDataSource来源，ItemKeyedDataSource来源）

角色二：PagedList（是UIModel数据层，通过Factory的方式拿到数据源）

角色三：PagedAdapter（同学们注意，不再是之前使用RecycleView的那种适配器了，而是和Paging配套的PagedListAdapter）

角色四：RecycleView（是同学们之前学的RecycleView，只不过setAdapter的时候，绑定的适配器是 PagedAdapter）

![img](https://img-blog.csdn.net/20181021221030916?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FsZXh3bGw=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)



#### 角色1 数据源：

数据源就是数据的来源，可以有多种来源渠道，例如：“网络数据”，“本地数据”，“数据库数据”

![image-20200403102322405](Jetpack 之 Paging.assets\image-20200403102322405.png)



#### 角色2 数据工厂：

创建 管理 数据源 的工厂，为什么有一个工厂，除了可以去创建数据源之外，为了后续的扩展

![image-20200403103839064](Jetpack 之 Paging.assets\image-20200403103839064.png)



#### 角色3 数据模型:

数据模型其实就是 ViewModel，用来管理数据

```java
PagedList: 数据源获取的数据最终靠PagedList来承载。
对于PagedList,我们可以这样来理解，它就是一页数据的集合。
每请求一页，就是新的一个PagedList对象。
```

<img src="Jetpack 之 Paging.assets\image-20200403135908280.png" alt="image-20200403135908280" style="zoom:80%;" />



#### 角色4 适配器：

```
 同学们：这个Adapter就是一个RecyclerView的Adapter。
 不过我们在使用paging实现RecyclerView的分页加载效果，
 不能直接继承RecyclerView的Adapter，而是需要继承PagedListAdapter。
```

LiveData观察到的数据，把感应到的数据 给 适配器，适配器又绑定了 RecyclerView，那么RecyclerView的列表数据就改变了

![image-20200403141250802](Jetpack 之 Paging.assets\image-20200403141250802.png)



#### 展示结果：

<img src="Jetpack 之 Paging.assets\image-20200404173905936.png" alt="image-20200404173905936" style="zoom:70%;" />



#### Paging的各个角色职责:

DataSource：数据的来源
DataSource.Factory：工厂类提供DataSource的实例，在自定义DataSource时使用
PagedList：数据集散中心，根据需要向DataSource索取加载数据，并将得到的数据传递到PagedListAdapter
PagedListAdapter：数据适配器，这里除了起到普通界面加载适配器的作用外，更重要的是根据滑动显示的坐标，起到了确定什么时候要求向PagedList加载数据
DiffUtil.ItemCallback：判断数据是否发生改变以确定界面是否更新



#### 数据源详解：

DataSource是一个抽象类，但是我们不能直接继承它实现它的子类。但是Paging库里提供了它的三个子类供我们继承用于不同场景的实现：

第一种：PositionalDataSource<T>：适用于目标数据总数固定，通过特定的位置加载数据，这里Key是Integer类型的位置信息，T即Value。 比如从数据库中的1200条开始加在20条数据。

第二种：ItemKeyedDataSource<Key, Value>：适用于目标数据的加载依赖特定item的信息， 即Key字段包含的是Item中的信息，比如需要根据第N项的信息加载第N+1项的数据，传参中需要传入第N项的ID时，该场景多出现于论坛类应用评论信息的请求。

第三种：PageKeyedDataSource<Key, Value>：适用于目标数据根据页信息请求数据的场景，即Key字段是页相关的信息。比如请求的数据的参数中包含类似next / pervious页数的信息。



### Paging的源码分析：

##### 类之关系：

abstract class DataSource<Key, Value>：

<img src="Jetpack 之 Paging.assets\image-20200405164642394.png" alt="image-20200405164642394" style="zoom:80%;" />



abstract class ItemKeyedDataSource<Key, Value>：

<img src="Jetpack 之 Paging.assets\image-20200405163350643.png" alt="image-20200405163350643" style="zoom:80%;" />



abstract class PageKeyedDataSource<Key, Value>：

<img src="Jetpack 之 Paging.assets\image-20200405164844201.png" alt="image-20200405164844201" style="zoom:80%;" />



abstract class PositionalDataSource<T>： 我们刚刚使用的是这个 数据源子类

<img src="Jetpack 之 Paging.assets\image-20200405163546455.png" alt="image-20200405163546455" style="zoom:80%;" />



DataSource的三个子类:

PageKeyedDataSource：如果页面需要实现上一页、下一页，需要将请求的Token传递到下一步时使用
ItemKeyedDataSource：程序需要根据上一条数据信息（ID）获取下一条数据时使用
PositionalDataSource：需要从数据存储中选择的任何位置获取数据页；例如，请求可能返回以位置1200开头的20个数据项



当然是在拿取数据的地方开始分析，Paging组件的开始执行都是从创建 LiveData<PagedList>开始的，我们源码的分析也从LiveData<PagedList>的创建开始一探Paging背后的逻辑，同学们我们开始分析吧：



##### 初始化工作：

![image-20200405190707615](Jetpack 之 Paging.assets\image-20200405190707615.png)



开始查看 ”private final LiveData<PagedList<Student>> listLiveData;“ 此变量是如何创建的：

```java
 public StudentViewModel() {
        StudentDataSourceFactory factory = new StudentDataSourceFactory();
        this.listLiveData = new LivePagedListBuilder<Integer, Student>(factory, 20)
                .build();
 }
```

点击进入build函数分析：

```java
@NonNull
@SuppressLint("RestrictedApi")
public LiveData<PagedList<Value>> build() {
    return create(mInitialLoadKey, mConfig, mBoundaryCallback, mDataSourceFactory,
            ArchTaskExecutor.getMainThreadExecutor(), mFetchExecutor);
}
```

进入create函数分析：

使用LivePagedListBuilder配置Factory和Config，然后调用build创建实例，在build方法中直接调用了create（）方法创建LiveData

```java
	@AnyThread
    @NonNull
    private static <Key, Value> LiveData<PagedList<Value>> create(
            @Nullable final Key initialLoadKey,
            @NonNull final PagedList.Config config,
            @Nullable final PagedList.BoundaryCallback boundaryCallback,
            @NonNull final DataSource.Factory<Key, Value> dataSourceFactory,
            @NonNull final Executor notifyExecutor,
            @NonNull final Executor fetchExecutor) {
        
        // 同学们注意：在这里创建ComputableLiveData抽象类 
        return new ComputableLiveData<PagedList<Value>>(fetchExecutor) { 
 
            @Nullable
            private PagedList<Value> mList; 
            @Nullable
            private DataSource<Key, Value> mDataSource;
 
            private final DataSource.InvalidatedCallback mCallback =
                    new DataSource.InvalidatedCallback() {
                        @Override
                        public void onInvalidated() {
                            invalidate();
                        }
                    };
 
            // 同学们注意，在这里重写compute方法，   是我们需要的PagedList<Value>
            @Override
            protected PagedList<Value> compute() { 
                @Nullable Key initializeKey = initialLoadKey;
                if (mList != null) {
                    //noinspection unchecked
                    initializeKey = (Key) mList.getLastKey();
                }
 
                do {
                    if (mDataSource != null) {
                        mDataSource.removeInvalidatedCallback(mCallback);
                    }
                    // 从Builder中传入的Factory中创建DataSource
                    mDataSource = dataSourceFactory.create();
                    mDataSource.addInvalidatedCallback(mCallback);
                   // 创建PagedList
                    mList = new PagedList.Builder<>(mDataSource, config)
                            .setNotifyExecutor(notifyExecutor)
                            .setFetchExecutor(fetchExecutor)
                            .setBoundaryCallback(boundaryCallback)
                            .setInitialKey(initializeKey)
                            .build();
                } while (mList.isDetached());
                return mList;
            }
        }.getLiveData();
    }
```

在create（）中直接返回了ComputableLiveData的实例，在ComputableLiveData实例重写的compute中执行了一些主要操作：

​	一：调用传入的Factory的create（）创建DataSource实例
​	二：创建并返回PagedList实例
​	三：PagedList.build（） & PagedList.create()  就是如下代码（细节）:

```java
mList = new PagedList.Builder<>(mDataSource, config)
                            .setNotifyExecutor(notifyExecutor)
                            .setFetchExecutor(fetchExecutor)
                            .setBoundaryCallback(boundaryCallback)
                            .setInitialKey(initializeKey)
                            .build();
```

```java
public PagedList<Value> build() {
            // TODO: define defaults, once they can be used in module without android dependency
            if (mNotifyExecutor == null) {
                throw new IllegalArgumentException("MainThreadExecutor required");
            }
            if (mFetchExecutor == null) {
                throw new IllegalArgumentException("BackgroundThreadExecutor required");
            }

            //noinspection unchecked
            return PagedList.create(
                    mDataSource,
                    mNotifyExecutor,
                    mFetchExecutor,
                    mBoundaryCallback,
                    mConfig,
                    mInitialKey);
        }
```

PagedList的创建过程，在PagedList.build（）中调用了PagedList.create()，所以真正的创建是在create（）中发生的:

```java
 private static <K, T> PagedList<T> create(...) {
        if (dataSource.isContiguous() || !config.enablePlaceholders) {
            ......
            return new ContiguousPagedList<>(contigDataSource,
                    notifyExecutor,
                    fetchExecutor,
                    boundaryCallback,
                    config,
                    key,
                    lastLoad);
        } else {
            return new TiledPagedList<>((PositionalDataSource<T>) dataSource,
                    notifyExecutor,
                    fetchExecutor,
                    boundaryCallback,
                    config,
                    (key != null) ? (Integer) key : 0);
        }
    }
```

从上面的代码中看出根据 条件（dataSource.isContiguous() || !config.enablePlaceholders）的不同分别创建ContiguousPagedList和TiledPagedList，其实这里就是区分上面的**三个自定义DataSource的类型（三个数据源）**，如果是PositionalDataSource创建TiledPagedList，其他的返回ContiguousPagedList，我们依次查看三个DataSource中的isContiguous()方法：



PositionalDataSource类中的：

```java
@Override
boolean isContiguous() {
    return false;
}
```



ItemKeyedDataSource和PageKeyedDataSource都继承与ContiguousDataSource，只查看ContiguousDataSource类中的：

```java
@Override
boolean isContiguous() {
    return true;
}
```



又回来,从 **.build开始看:**

new ComputableLiveData有什么用 与 何时执行compute函数, 这两个疑问，查看ComputableLiveData源码，发现在ComputableLiveData的构造函数中创建LiveData实例，下面查看Runnable接口中执行了哪些逻辑：

```java
public ComputableLiveData(@NonNull Executor executor) {
        mExecutor = executor;
        mLiveData = new LiveData<T>() {
            @Override
            protected void onActive() {
                mExecutor.execute(mRefreshRunnable);
            }
        };
    }
```

```java
 final Runnable mRefreshRunnable = new Runnable() {
        @WorkerThread
        @Override
        public void run() {
            boolean computed;
            do {
                computed = false;
                // compute can happen only in 1 thread but no reason to lock others.
                if (mComputing.compareAndSet(false, true)) {
                    // as long as it is invalid, keep computing.
                    try {
                        T value = null;
                        while (mInvalid.compareAndSet(true, false)) {
                            computed = true;
                            // 同学们 这里会执行 compute(); 函数
                            // 调用了compuet创建了PagedList
                            value = compute();
                        }
                        if (computed) {
                            // 设置LiveData的值
                            mLiveData.postValue(value);
                        }
                    } finally {
                        // release compute lock
                        mComputing.set(false);
                    }
                }
				.......
            } while (computed && mInvalid.get());
        }
    };
```



在mRefreshRunnable中调用了ComputableLiveData的compute（）方法创建了PagedList，所以此处的Value就是PagedList，然后为mLiveData初始化赋值PagedList

细心的同学会留意到，在上面的create（）方法最后一句调用了getLiveData()获取到的就是ComputableLiveData构造函数中创建的LIveData：

```java
    @SuppressWarnings("WeakerAccess")
    @NonNull
    public LiveData<T> getLiveData() {
        return mLiveData;
    }
```

<u>到这里为止，LiveData<PagedList>终于创建完成了</u>



##### 数据的加载工作：

ContiguousPagedList 作为触发点：

当我们自定义实现ItemKeySource时，创建的PagedList实际为ContiguousPagedList，查看ContiguousPagedList构造函数源码：

```java
ContiguousPagedList(
            @NonNull ContiguousDataSource<K, V> dataSource,
            @NonNull Executor mainThreadExecutor,
            @NonNull Executor backgroundThreadExecutor,
            @Nullable BoundaryCallback<V> boundaryCallback,
            @NonNull Config config,
            final @Nullable K key,
            int lastLoad) {
        super(new PagedStorage<V>(), mainThreadExecutor, backgroundThreadExecutor,
                boundaryCallback, config);
        mDataSource = dataSource;
        mLastLoad = lastLoad;

        if (mDataSource.isInvalid()) {
            detach();
        } else {
            mDataSource.dispatchLoadInitial(key,
                    mConfig.initialLoadSizeHint,
                    mConfig.pageSize,
                    mConfig.enablePlaceholders,
                    mMainThreadExecutor,
                    mReceiver);
        }
        mShouldTrim = mDataSource.supportsPageDropping()
                && mConfig.maxSize != Config.MAX_SIZE_UNBOUNDED;
    }
```

在构造函数中执行一下逻辑，所以继续追踪代码：

​	第一点：创建PagedStorage实例，主要根据滑动的位置显示是否要继续加载数据

​	第二点：调用DataSource.dispatchLoadInitial方法，此时使用的时ItermKeyDataSource的dispatchLoadInitial	方法

```java
  @Override
    final void dispatchLoadInitial(@Nullable Key key, int initialLoadSize, int pageSize,
            boolean enablePlaceholders, @NonNull Executor mainThreadExecutor,
            @NonNull PageResult.Receiver<Value> receiver) {
        LoadInitialCallbackImpl<Value> callback =
                new LoadInitialCallbackImpl<>(this, enablePlaceholders, receiver);
        loadInitial(new LoadInitialParams<>(key, initialLoadSize, enablePlaceholders), callback);
        callback.mCallbackHelper.setPostExecutor(mainThreadExecutor);
    }
```

上面代码在ItermKeyDataSource的dispatchLoadInitial（）方法中调用了抽象函数loadInitial（），根据前面的学习我们知道在 loadInitial() 中设置了初始化的网络请求，到此实现了Paging组件初始化数据的加载



##### 数据的显示工作：

在自定义ItemDataSource的loadInitial（）中加载数据后，调用了callback.onResult(it?.data!!.datas!!)方法，此处的callback是LoadInitialCallback的实现类**LoadInitialCallbackImpl**，在onResult（）方法中又调用了LoadCallbackHelper.dispatchResultToReceiver（）

```java
static class LoadInitialCallbackImpl<Key, Value> extends LoadInitialCallback<Key, Value> {
        final LoadCallbackHelper<Value> mCallbackHelper;
        private final PageKeyedDataSource<Key, Value> mDataSource;
        private final boolean mCountingEnabled;
        LoadInitialCallbackImpl(@NonNull PageKeyedDataSource<Key, Value> dataSource,
                boolean countingEnabled, @NonNull PageResult.Receiver<Value> receiver) {
            mCallbackHelper = new LoadCallbackHelper<>(
                    dataSource, PageResult.INIT, null, receiver);
            mDataSource = dataSource;
            mCountingEnabled = countingEnabled;
        }

        @Override
        public void onResult(@NonNull List<Value> data, @Nullable Key previousPageKey,
                @Nullable Key nextPageKey) {
            if (!mCallbackHelper.dispatchInvalidResultIfInvalid()) {
                mDataSource.initKeys(previousPageKey, nextPageKey);
                mCallbackHelper.dispatchResultToReceiver(new PageResult<>(data, 0, 0, 0));
            }
        }
```



   狙击点：**LoadCallbackHelper.dispatchResultToReceiver（）** 

```java
void dispatchResultToReceiver(final @NonNull PageResult<T> result) {
            Executor executor;
            synchronized (mSignalLock) {
                if (mHasSignalled) {
                    throw new IllegalStateException(
                            "callback.onResult already called, cannot call again.");
                }
                mHasSignalled = true;
                executor = mPostExecutor;
            }

            if (executor != null) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mReceiver.onPageResult(mResultType, result);
                    }
                });
            } else {
                mReceiver.onPageResult(mResultType, result);
            }
        }
```

在dispatchResultToReceiver（）方法中，调用PageResult.Receiver.onPageResult()方法，这里的mReceiver是在调用 mDataSource.dispatchLoadInitial（）时传入的最后一个参数，他的实现在ContiguousPagedList中匿名创建：

```java
final PageResult.Receiver<T> mReceiver;

        // mSignalLock protects mPostExecutor, and mHasSignalled
        private final Object mSignalLock = new Object();
        private Executor mPostExecutor = null;
        private boolean mHasSignalled = false;

        LoadCallbackHelper(@NonNull DataSource dataSource, @PageResult.ResultType int resultType,
                @Nullable Executor mainThreadExecutor, @NonNull PageResult.Receiver<T> receiver) {
            mDataSource = dataSource;
            mResultType = resultType;
            mPostExecutor = mainThreadExecutor;
            mReceiver = receiver;
        }
```

ContiguousPagedList:

```java
  private PageResult.Receiver<V> mReceiver = new PageResult.Receiver<V>() {
        // Creation thread for initial synchronous load, otherwise main thread
        // Safe to access main thread only state - no other thread has reference during construction
        @AnyThread
        @Override
        public void onPageResult(@PageResult.ResultType int resultType,
                @NonNull PageResult<V> pageResult) {
            
 
            List<V> page = pageResult.page;
            if (resultType == PageResult.INIT) {
                mStorage.init(pageResult.leadingNulls, page, pageResult.trailingNulls,
                        pageResult.positionOffset, ContiguousPagedList.this);
                if (mLastLoad == LAST_LOAD_UNSPECIFIED) {
                    // Because the ContiguousPagedList wasn't initialized with a last load position,
                    // initialize it to the middle of the initial load
                    mLastLoad =
                            pageResult.leadingNulls + pageResult.positionOffset + page.size() / 2;
                }
            } else if (resultType == PageResult.APPEND) {
                mStorage.appendPage(page, ContiguousPagedList.this);
            } else if (resultType == PageResult.PREPEND) {
                mStorage.prependPage(page, ContiguousPagedList.this);
            } else {
                throw new IllegalArgumentException("unexpected resultType " + resultType);
            }
 
           
            }
        }
    };
```

在onPageResult（）方法中根据resultType的类型执行操作，PageResult的三个数据类型分别对应者ItemKeyDataSource的三个方法：

loadInitial：对应初始化状态PageResult.INIT
loadBefore：对应初始化状态PageResult.PREPEND
loadAfter：对应初始化状态PageResult.APPEND

此出分析初始化，回调的类型为PageResult.INIT，调用了PagedStorage的init（）方法：

```java
mStorage.init(pageResult.leadingNulls, page, pageResult.trailingNulls,
                        pageResult.positionOffset, ContiguousPagedList.this);
```

```java
void init(int leadingNulls, @NonNull List<T> page, int trailingNulls, int positionOffset,
            @NonNull Callback callback) {
        init(leadingNulls, page, trailingNulls, positionOffset);
        callback.onInitialized(size());
    }
```

在init（）方法中首先调用另一个init（）方法记录加载的位置，并保存加载的数据, 然后调用callback.onInitialized(），在onInitialzed（）方法中调用了notifyInserted（），在notifyInserted（）中遍历mCallbacks回调callback的onInserted（）

```java
interface Callback {
        void onInitialized(int count);
        void onPagePrepended(int leadingNulls, int changed, int added);
        void onPageAppended(int endPosition, int changed, int added);
        void onPagePlaceholderInserted(int pageIndex);
        void onPageInserted(int start, int count);
        void onPagesRemoved(int startOfDrops, int count);
        void onPagesSwappedToPlaceholder(int startOfDrops, int count);
        void onEmptyPrepend();
        void onEmptyAppend();
    }
```

继续追踪源码:

```java
ContiguousPagedList:
public void onInitialized(int count) {
        notifyInserted(0, count);
}
 
PagedList:
void notifyInserted(int position, int count) {
        if (count != 0) {
            for (int i = mCallbacks.size() - 1; i >= 0; i--) {
                Callback callback = mCallbacks.get(i).get();
                if (callback != null) {
                    callback.onInserted(position, count);
                }
            }
        }
    }
PagedList 的 接口:
public abstract void onInserted(int position, int count);
```

以上源码, 让我们明白了:

​	一: 加载的数据保存在PagedStorage中，并记录了加载的位置信息

​    二: 加载完成后根据数据的变化，回调callback.onInserted（）通知数据改变的数量和位置



**终于看到曙光了:**

那CallBack是从哪来的呢？应该是哪里需要哪里才会注册回调，想想数据位置的变化在哪个地方能用得着，哪个地方优惠根据position和count处理呢？答案就在PagedListAdapter中    终于要看到 **PagedListAdapter** 了



当然  下面我们也可以简单的追踪下代码  **能否到 PagedListAdapter**

AsyncPagedListDiffer:

```java
public AsyncPagedListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> config) {
        class NamelessClass_1 extends Callback {
            NamelessClass_1() {
            }

            public void onInserted(int position, int count) {
                AsyncPagedListDiffer.this.mUpdateCallback.onInserted(position, count);
            }

            public void onRemoved(int position, int count) {
                AsyncPagedListDiffer.this.mUpdateCallback.onRemoved(position, count);
            }

            public void onChanged(int position, int count) {
                AsyncPagedListDiffer.this.mUpdateCallback.onChanged(position, count, (Object)null);
            }
        }
```

ListUpdateCallback:

```
public interface ListUpdateCallback {
   ......
    void onInserted(int position, int count);
```

AdapterListUpdateCallback:

```
@Override
public void onInserted(int position, int count) {
  	mAdapter.notifyItemRangeInserted(position, count);
}
```





## **逆向方式，同学们自己去看文字**

##### 逆向源码收尾:

在我们开始写的使用Paging的实例中，使用submitList（）设置数据，而submiList（）直接调用了mDiffer.submitList(pagedList)：

```java
public void submitList(PagedList<T> pagedList) {
        mDiffer.submitList(pagedList);
}
```

```java
 public void submitList(final PagedList<T> pagedList) {
        if (mPagedList == null && mSnapshot == null) {
            // fast simple first insert
            mPagedList = pagedList;
            pagedList.addWeakCallback(null, mPagedListCallback);
            return;
        } 
    }
```



这里就调用了addWeakCallback（）添加Callback实例mPagedListCallback

```java
private PagedList.Callback mPagedListCallback = new PagedList.Callback() {
        @Override
        public void onInserted(int position, int count) {
            mUpdateCallback.onInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            mUpdateCallback.onRemoved(position, count);
        }

        @Override
        public void onChanged(int position, int count) {
            // NOTE: pass a null payload to convey null -> item
            mUpdateCallback.onChanged(position, count, null);
        }
    };
```

上面源码中, mPagedListCallback的onInserted（）直接回调mUPdateCallback.onInserted（），这里的mUPdateCallback正是在PagedListAdapter的构造函数中创建Differ，而在AsyncPagedListDiffer的构造函数中直接初始化了AdapterListUpdateCallback对象

```java
 public AsyncPagedListDiffer(@NonNull RecyclerView.Adapter adapter,
            @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        mUpdateCallback = new AdapterListUpdateCallback(adapter);
        mConfig = new AsyncDifferConfig.Builder<T>(diffCallback).build();
}
```

所以程序执行到AdapterListUpdateCallback中，在AdapterListUpdateCallback.onInserted（）中直接调用传入的Adapter的notifyItemRangeInserted(position, count)实现数据更新，这里的Adapter就是










