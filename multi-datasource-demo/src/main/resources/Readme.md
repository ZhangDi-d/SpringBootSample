## 只实现了读写分离,尚未实现主从同步 TODO
** https://www.cnblogs.com/cjsblog/p/9712457.html  
*****https://www.cnblogs.com/codehome/p/9356496.html  原理
**** https://blog.csdn.net/u013360850/article/details/78861442
*****https://blog.csdn.net/neosmith/article/details/61202084


## 读写分离
读写分离要做的事情就是对于一条SQL该选择哪个数据库去执行，至于谁来做选择数据库这件事儿，无非两个，要么中间件帮我们做，要么程序自己做。因此，一般来讲，读写分离有两种实现方式。第一种是依靠中间件（比如：MyCat），也就是说应用程序连接到中间件，中间件帮我们做SQL分离；第二种是应用程序自己去做分离。这里我们选择程序自己来做，主要是利用Spring提供的路由数据源，以及AOP



## execution
1. execution 其格式为:
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?)
returning type pattern,name pattern, and parameters pattern是必须的.
ret-type-pattern:可以为*表示任何返回值,全路径的类名等.
name-pattern:指定方法名,*代表所有,set*,代表以set开头的所有方法.
parameters pattern:指定方法参数(声明的类型),(..)代表所有参数,(*)代表一个参数,(*,String)代表第一个参数为任何值,第二个为String类型.

2.任意公共方法的执行：
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