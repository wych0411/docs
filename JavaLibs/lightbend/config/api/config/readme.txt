Config提供了getString, getBoolean等方法

可以解析不同单位的时长（分秒等）
- getDuration("xxx", java.util.concurrent.TimeUnit.MINUTES)
                     java.util.concurrent.TimeUnit.SECONDS
                     ... ...

可以解析不同单位的大小（G、M等）
- getMeorySize("xxx").toBytes()

可以处理列表或数组
- getIntList("xxx")
- getStringList("xxx")                     