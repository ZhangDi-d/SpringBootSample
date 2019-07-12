读写分离要做的事情就是对于一条SQL该选择哪个数据库去执行，至于谁来做选择数据库这件事儿，无非两个，要么中间件帮我们做，要么程序自己做。因此，一般来讲，读写分离有两种实现方式。第一种是依靠中间件（比如：MyCat），也就是说应用程序连接到中间件，中间件帮我们做SQL分离；第二种是应用程序自己去做分离。这里我们选择程序自己来做，主要是利用Spring提供的路由数据源，以及AOP

然而，应用程序层面去做读写分离最大的弱点（不足之处）在于无法动态增加数据库节点，因为数据源配置都是写在配置中的，新增数据库意味着新加一个数据源，必然改配置，并重启应用。当然，好处就是相对简单。

这里有两个AOP，一个是事务的AOP，另一个是自定义的切换数据源的AOP，那么这里就有一个顺序的问题了，默认基于注解的声明式事务它的order值是最大的意味着它的顺序在最后面。
事务当然一般是放在Service层，如果我们切换数据源的AOP定义在Dao层，那么事务永远用的是主数据源，因为在进到Service时线程上下文中还没有明确的数据源标识，因而用默认的数据源，而默认的正是主数据源。
如果切换数据源的AOP定义在Service，也就是事务和数据源切换都在Service层，这个时候进到Service方式时由于切换数据源在前事务在后，所以事务用哪个数据源完全取决于这个Service方法的命名。
另外，至于说调用同一个类（Service或者Dao）中的方法导致AOP不生效，或者命名是一个查询的方法，但是里面非要做查询以外的操作，这些就属于写法的问题了，而不属于切换数据源引起的事务问题了。



其中 execution 是用的最多的,其格式为:

execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)

returning type pattern,name pattern, and parameters pattern是必须的.
ret-type-pattern:可以为*表示任何返回值,全路径的类名等.
name-pattern:指定方法名,*代表所有,set*,代表以set开头的所有方法.
parameters pattern:指定方法参数(声明的类型),(..)代表所有参数,(*)代表一个参数,(*,String)代表第一个参数为任何值,第二个为String类型.

任意公共方法的执行：

execution(public * *(..))

任何一个以“set”开始的方法的执行：

execution(* set*(..))

AccountService 接口的任意方法的执行：

execution(* com.xyz.service.AccountService.*(..))

定义在service包里的任意方法的执行：

execution(* com.xyz.service.*.*(..))

定义在service包和所有子包里的任意类的任意方法的执行：

execution(* com.xyz.service..*.*(..))

定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行：

 execution(* com..*.*Dao.find*(..))

匹配包名前缀为com的任何包下类名后缀为Dao的方法，方法名必须以find为前缀。如com.baobaotao.UserDao#findByUserId()、com.baobaotao.dao.ForumDao#findById()的方法都匹配切点。