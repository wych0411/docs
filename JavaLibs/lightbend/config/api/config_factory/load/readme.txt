默认加载classpath下的application.conf, application.json和application.properties文件。
也可以调用ConfigFactory.load(confFileName)加载指定的配置文件。

.json只能按json格式书写，.properties只能按properties格式书写，而*.conf可以是两者混合。
配置文件只能是以上三种后缀名。

如果多个config文件有冲突时，解决方案有：
(1) a.withFallback(b) //a和b合并，如果有相同的key，以a为准
    withFallback后调用resolve用于substitutions(${config_item})
(2) a.withOnlyPath(String path) //只取a里的path下的配置
(3) a.withoutPath(String path) //只取a里除path外的配置