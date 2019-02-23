1.MORE:
https://412887952-qq-com.iteye.com/blog/2299784 
https://blog.csdn.net/qq_34021712/article/details/80301808

2.RBAC 是基于角色的访问控制（Role-Based Access Control ）在 RBAC 中，权限与角色相关联，用户通过成为适当角色的成员而得到这些角色的权限。这就极大地简化了权限的管理。这样管理都是层级相互依赖的，权限赋予给角色，而把角色又赋予用户，这样的权限设计很清楚，管理起来很方便。

3.页面显示为默认springboot展示的页面Whitelabel Error Page，具体页面如下，并没有跳转到我们之前配置的页面 。 
  也就是说shiroFilterFactoryBean.setUnauthorizedUrl(“/unauthorized”);无效
  
  解决: shiroConfig.java 下添加simpleMappingExceptionResolver()
 
 4.解决乱输url地址出现Whitelabel Error Page的问题:
 shiroConfig.java 下添加containerCustomizer();
     然后在resource/static路径下也添加一个403.html，404.html 和500.html，
     页面都差不多,只是文字显示不一样。只能把页面放在resource/static下面 (???)。
     我也不知道为什么,如果没有这些页面,默认会是一片白,没有去进一步查看原因。
     