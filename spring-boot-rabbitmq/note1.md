如果父pom中使用的是<dependencies>....</dependencies>的方式，则子pom会自动使用pom中的jar包；

如果父pom使用
```xml
<dependencyManagement>

<dependencies>....</dependencies>

</dependencyManagement>

```
的方式，则子pom不会自动使用父pom中的jar包，这时如果子pom想使用的话，就要给出groupId和artifactId，无需给出version。