此包下为自定义的注解;

### Rentention注解类

    注解的生命周期：Java源文件—》class文件—》内存中的字节码。编译或者运行时，都有可能会取消注解。Rentention的3种取值意味让注解保留到哪个阶段，RententionPolicy.SOURCE、RententionPolicy.CLASS(默认值)、RententionPolicy.RUNTIME。

    @Override、@SuppressWarnings是默认保留到SOURCE阶段；@Deprecated是保留到RUNTIME阶段。

    Rentention相当于注解类的一个属性，因为Rentention的值不同，注解类保留到的阶段不同。注解类内部Rentention的值使用value表示，例如，@Deprecated中，value=Runtime。

    Rentention的值是枚举RententionPolicy的值，只有3个：SOURCE、CLASS、RUNTIME。
    
### Target注解类

    性质和Rentention一样，都是注解类的属性，表示注解类应该在什么位置，对那一块的数据有效。例如，@Target(ElementType.METHOD)

    Target内部的值使用枚举ElementType表示，表示的主要位置有：注解、构造方法、属性、局部变量、函数、包、参数和类(默认值)。多个位置使用数组，例如，@Target({ElementType.METHOD,ElementType.TYPE})。

    类、接口、枚举、注解这一类事物用TYPE表示，Class的父类，JDK1.5的新特性。
    
    1.CONSTRUCTOR:用于描述构造器 
    2.FIELD:用于描述域 
    3.LOCAL_VARIABLE:用于描述局部变量 
    4.METHOD:用于描述方法 
    5.PACKAGE:用于描述包 
    6.PARAMETER:用于描述参数 
    7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
    ---------
    
### Inherited 注解

     它指明被注解的类会自动继承. 更具体地说,如果定义注解时使用了 @Inherited 标记,然后用定义的注解来标注另一个父类, 父类又有一个子类(subclass),则父类的所有属性将被继承到它的子类中.