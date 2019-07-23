### NIO的SelectionKey(选择键):
#### 1.要点

- 是一个抽象类,表示selectableChannel在Selector中注册的标识.每个Channel向Selector注册时,都将会创建一个selectionKey
- 选择键将Channel与Selector建立了关系,并维护了channel事件.
- 可以通过cancel方法取消键,取消的键不会立即从selector中移除,而是添加到cancelledKeys中,在下一次select操作时移除它.所以在调用某个key时,需要使用isValid进行校验.

#### 2.操作属性
- OP_ACCEPT:连接可接受操作,仅ServerSocketChannel支持
- OP_CONNECT:连接操作,Client端支持的一种操作
- OP_READ/OP_WRITE

![](https://img-blog.csdn.net/20150813165705122?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)