# Redis实现消息队列


## redis 几种数据类型的操作(制作思维导图)

https://www.jianshu.com/p/06534dca0a36


## Redis 动态数据源切换

https://blog.csdn.net/ron03129596/article/details/108847907



## 延时消息


### ApplicationListener spring事件监听机制

KeyExpirationEventMessageListener

ApplicationListener<Bean> - ApplicationEvent.publishEvent()

https://blog.csdn.net/wo541075754/article/details/96287667

**该方式 消息失效并不会立即执行`ApplicationEvent.publishEvent()`，只有访问失效消息时 或Redis 内部机制扫描到才执行**

### Redisson