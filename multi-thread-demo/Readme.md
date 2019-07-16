### 2.1 线程的状态
### 2.2 线程的基本操作
#### 1. 终止线程:
stop方法会立即终止线程释放线程持有的锁,这样可能会导致数据的不一致;stop方法是被废弃的方法;</br>

如何优雅的终止线程:只需要我们自行决定何时终止就可以了
```java
public static class Thread1 extends Thread{
    volatile boolean stopme = false;
    
    public void stopMe(){
        stopme = true;
    }
    run(){}  
} 
```
2. 线程中断interrupt:
线程中断不是立即中断,而是设置标志位,告诉线程有人希望你中断,之后如何处理,取决于线程自己 如果不做处理,那么线程不会退出

3. 生产者消费者模型
wait()->进入等待态
notify()->随机唤醒等待锁线程,
notifyAll()->唤醒等待锁的所有线程

4. suspend(挂起)和resume(恢复)也是被废弃的方法,我们可以使notify和wait实现其功能 

5. 等待线程结束(join) 和谦让(yeild) 一个是自己优先(ts.join -> ts线程优先),一个是让别人优先(ts.yeild-> ts线程谦让别人).

### 2.3 volatile 与内存管理
  volatile保证资源在线程间的可见性,指令重排与happen-before
### 2.4 线程组
   创建线程组ThreadGroup,创建线程Thread,并将线程放在线程组中
   
### 2.5 守护线程
系统只有守护线程时,整个程序就结束了 

### 2.6 线程优先级
线程优先级高的绝大部分情况下会比线程优先级低的先结束.

### 2.7 线程安全与synchronized
volatile不能保证线程安全,被synchronized限制的多个线程时顺序执行的.

### 2.8 隐蔽的错误
  1. int a = 1072741827;int b = 1431655768; 两者均值 avg = (a+b)/2 = -894784850 ; 原因在于a+b导出int 溢出
  2. 并发下的ArrayList是线程不安全的->可以用Vector代替,或者使用 synchronized
    或者List<Map<String,Object>> data=Collections.synchronizedList(new ArrayList<Map<String,Object>>());
  3. 并发下的HashMap也是线程不安全的 ->使用concurrentHashMap代替 
  4. 错误的加锁-> 对Integer 加锁,见com.ryze.chapter2.BadLockOnInteger

## 3 并发包
### 3.1 同步控制
#### 3.1.1 synchronized的扩展:重入锁


























 