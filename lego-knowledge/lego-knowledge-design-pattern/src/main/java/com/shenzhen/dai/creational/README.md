## 二、如何实现一个单例
常见的单例设计模式，有如下五种写法，在编写单例模式时，需要注意以下几点：
1. 构造器需要私有化
2. 暴露一个公共的获取单例对象的接口
3. 是否支持懒加载（延迟加载）
4. 是否线程安全

### 1. 饿汉式
饿汉式的实现方式比较简单。在类加载的时候，instance静态实例就已经创建并初始化好了，所以instance实例的创建过程是线程安全的。从名字我们也可以看出这一点。具体的代码实现如下：
```java
public class Singleton {
    // 静态实例
    private static Singleton instance = new Singleton();

    // 私有化构造器
    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }
}
```
事实上，饿汉式的写法在工作中反而应该提倡，面试中不问是因为比较简单，很多人觉得饿汉式不支持懒加载，即使不使用也会浪费资源，但是实际使用中，其实：

1. 现代计算机不缺这一个对象的内存
2. 如果一个实例初始化的过程复杂，那更应该放在启动时处理，避免卡顿或者构造问题发生在运行时。满足fail-fast的设计原则。

> fail-safe和fail-fast是多线程并发操作集合时的一种失败处理机制。fail-fast是表示快速失败，在集合遍历过程中，一旦发现容器中的数据被修改过了，就会立刻抛出一个异常叫做Concurrent Modification Exception，java.util包下的所有集合类都是属于快速失败机制的。而fail-safe是表示失败安全，即在这样的机制下出现集合元素修改，不会抛出Concurrent Modification Exception，原因是采用安全失败机制的集合容器，在遍历集合的时候，不是直接在集合内容上进行访问，而是先复制原有集合的内容，而在拷贝的集合上去进行遍历，由于迭代器是对原始集合的拷贝内容进行遍历的，所以对原始集合的修改，并不能被迭代器检测到。在java.util.concurrent包下的所有容器，都是属于fail-safe的。它是可以在多线程情况下并发使用的时候，提供并发修改的机制的，常见的使用fail-safe机制的容器有ConcurrentHashMap和CopyOnWriteArrayList。

### 2. 懒汉式

有饿汉式，对应就有懒汉式，懒汉式相对于饿汉式的优势是支持延迟加载。具体代码如下：

``` java
public class LazySingleton {

    private static volatile LazySingleton instance = new LazySingleton();

    private LazySingleton() {
    }

    // 3. 暴露一个方法，用来获取实例
    public static LazySingleton getInstance() {
        if (null == instance) {
            instance = new LazySingleton();
        }
        return instance;
    }

}
```

以上的写法本质上是有问题的，当面对大量并发请求时，其实是无法保证其单例的特点的，很有可能会有超过一个线程同时执行了new Singleton();
解决方法很简单，加锁：

``` java
public class LazySingleton {

    private static LazySingleton instance = new LazySingleton();

    private LazySingleton() {
    }

    public synchronized static LazySingleton getInstance() {
        if (null == instance) {
            instance = new LazySingleton();
        }
        return instance;
    }

}
```

以上的写法确实可以保证JVM中有且仅有一个单例实例存在，但是方法上加锁会极大地降低获取单例对象的并发度。同一时间只有一个线程可以获取单例对象，为了解决以上问题则有了第三种写法。

### 3. 双重检查锁

饿汉式不支持延迟加载，懒汉式有性能问题，不支持高并发。双检锁既支持延迟加载，又支持高并发，在这种实现方式中，只要instance被创建之后，即使再调用getInstance()函数也不会再进入加锁逻辑中了。所以，这种实现方式解决了懒加载并发低的问题。具体的代码实现如下：

``` java
public class DclSingleton {

    private static volatile DclSingleton instance = new DclSingleton();

    private DclSingleton() {
    }

    // 3. 暴露一个方法，用来获取实例
    // 第一次创建需要双检锁，一旦创建好，就不再需要上锁。
    public static DclSingleton getInstance() {
        if (null == instance) {
            synchronized (DclSingleton.class) {
                if (null == instance) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

}

```

### 4. 静态内部类

我们再看一种比双检索检测更加简单的实现方式，那就是利用Java的静态内部类。它有点类似饿汉式，但又能做到延迟加载。具体怎么做到的呢？我们先看它的代码实现。

``` java
public class InnerSingleton {
    private InnerSingleton() {

    }

    public static InnerSingleton getInstance() {
        return InnerSingleton.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}
```

SingletonHolder是一个静态内部类，当外部类Singleton被加载的时候，并不会创建SingletonHolder实例对象。只有当调用getInstance()方法的时候，SingletonHolder才会被加载，这个时候才会创建instance。instance的唯一性，创建过程的线程安全性，都由JVM来保证。所以这种方法既保证了线程安全，又能做到延时加载。

### 5. 枚举

枚举这种方式通过Java枚举类型本身的特性，保证了实例创建的安全性和实例的唯一性。具体代码如下：

这是一个最简单的实现，因为枚举类中，每个枚举项本身就是一个单例的：

``` java
public enum EnumSingleton{
  INSTANCE;
}
```

更通用的写法：

``` java
public class NormalEnumSingleton {
    private NormalEnumSingleton() {
    }

    public enum SingletonEnum {
        EnumSingleton;
        private NormalEnumSingleton instance = null;

        SingletonEnum() {
            instance = new NormalEnumSingleton();
        }

        public NormalEnumSingleton getInstance() {
            return this.instance;
        }
    }
}
```

事实上我们还可以将单例项作为枚举的成员变量，我们的累加器可以这样写：

``` java
public enum GlobalCounter {
    INSTANCE;
    private AtomicLong atomicLong = new AtomicLong(0);

    public Long getNumber() {
        return atomicLong.incrementAndGet();
    }
}
```

这种写法是Head-First中推荐的写法，他除了可以和其他的方式一样实现单例，他还能有效防止反射入侵。

### 6. 反射入侵

事实上，我们想要**阻止他人构造实例**仅仅通过私有化构造器还是不够的，因为我们还可以**使用反射获取私有构造器**进行构造，当然使用枚举的方式是可以解决这个问题的，对于其他的方案，我们可以通过如下的方式来解决：

``` java 
public class Singleton {
    private volatile static Singleton singleton;

    private Singleton() {
        if (singleton != null) {
            throw new RuntimeException("实例：【" + this.getClass().getName() + "】已经存在，该实例只允许实例化一次");
        }
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

测试代码：

``` java
@Test
public void testReflect() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Class<Singleton> clazz = Singleton.class;
    Constructor<Singleton> constructor = clazz.getDeclaredConstructor();
    constructor.setAccessible(true);
    boolean flag = Singleton.getInstance() == constructor.newInstance();
    System.out.println("flag = " + flag);
}
```

### 7. 序列化和反序列化

事实上，到目前为止我们的单例依然是有漏洞的，看如下代码：

``` java
@Test
public void testSerialize() throws IOException, ClassNotFoundException {
    // 获取单例并序列化
    Singleton singleton = Singleton.getInstance();
    FileOutputStream fileOutputStream = new FileOutputStream("/Users/ivan/code/lego/lego-knowledge/lego-knowledge-design-pattern/src/test/java/com/shenzhen/dai/creational/singleton.txt");
    ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
    outputStream.writeObject(singleton);
    // 将实例反序列化出来
    FileInputStream fileInputStream = new FileInputStream("/Users/ivan/code/lego/lego-knowledge/lego-knowledge-design-pattern/src/test/java/com/shenzhen/dai/creational/singleton.txt");
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
    Object o = objectInputStream.readObject();
    System.out.println("是不是同一个实例" + (o == singleton));
}
```

我们发现，即使我们废了九牛二虎之力还是没能阻止他返回false。

readResolve()方法用于替换流中读取的对象，在进行反序列化时，会尝试执行readResolve()方法，并将返回值作为反序列化结果，而不会克隆一个新的实例，保证JVM中仅有一个实例存在：

``` java
public class Singleton implements Serializable {
  // 省略其他内容
  public static Singleton getInstance() {
    
  }
  
  // 需要加这样一个方法
  public Object readResolve() {
    return singleton;
  }
}
```





