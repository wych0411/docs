import org.apahe.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisTest{

	JedisCluster jedisCluster = null;

	static String prefix = "luffi:lbl";
	static String KEY_SPLIT = ":"; //用于隔开缓存前缀与缓存键值

	String nameKey = prefix + KEY_SPLIT + "name";

	@Before
	public void before(){
		String[] serverArray = "redis集群地址".splt(",");
		Set<HostAndPort> nodes = new HashSet<>();

		for(String ipPort : serverArray){
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}

		// 注意：这里超时时间不要太短，会有超时重试机制。而且其他像httpclient、dubbo等RPC框架也要注意这点
		jedisCluster = new JedisCluster(nodes, 1000, 1000, 1, "redis集群密码", new GenericObjectPoolConfig());

		// 大多数测试都是使用[nameKey]测试的，所以在启动之前先把这个key删掉
		jedisCluster.del(nameKey);
	}

	/**
	 * 发布
	 */
	@Test
	public void publish(){
		System.out.println(jedisCluster.publish("channel1", "ss"));
	}

	/**
	 * 订阅
	 */
	@Test
	public void psubscribe(){
		// jedisCluster.psubscribe(new JedisPubSubListener(), "channel1"); // 带通配符
		jedisCluster.subscribe(new JedisPubSubListener(), "channel1");
	}

	/**
	 * 简单字符串读写
	 */
	@Test
	public void setStringData(){
		System.out.println(jedisCluster.set(nameKey, "张三"));
		System.out.println(jedisCluster.get(nameKey));
	}

	/**
	 * setnx: 如果key存在，返回0;如果不存在，则设置成功。
	 * setnx的意思是set if not exist.
	 */
	@Test
	public void setnxTest(){
		System.out.println(jedisCluster.setnx(nameKey, "张三")); //key不存在, 返回值为1
		System.out.println(jedisCluster.get(nameKey));

		System.out.println(jedisCluster.setnx(nameKey, "张三")); //已经存在，返回值为0
		System.out.println(jedisCluster.get(nameKey));
	}

	/**
	 * 简单字符串读写，带过期时间
	 */
	@Test
	public void setexTest() throws InterruptedException{
		System.out.println(jedisCluster.setex(nameKey, 3, "张三")); //时间单位是秒
		for(int i = 0; i < 5; i++){
			System.out.println(jedisCluster.get(nameKey)); //过期以后redis集群自动删除
			Thread.sleep(1000);
		}
	}

	/**
	 * 操作子字符串
	 */
	@Test
	public void setrangeTest() throws InterruptedException{
		System.out.println(jedisCluster.set(nameKey, "852026881@qq.com"));
		System.out.println(jedisCluster.get(nameKey)); //结果：852026881@qq.com

		// 从offsete=8开始替换字符串value的值
		System.out.println(jedisCluster.setrange(nameKey, 8, "abc")); //结果85202688abcq.com
		System.out.println(jedisCluster.get(nameKey));

		System.out.println(jedisCluster.setrange(nameKey, 8, "abcdefghhhhh")); // 结果: 85202688abcdefghhhhh.com
		System.out.println(jedisCluster.get(nameKey));

		// 查询子串，返回startOffset到endOffset的字符
		System.out.println(jedisCluster.getrange(nameKey, 2, 5)); //结果：2026
	}

	public void msetTest() throws InterruptedException {

		String result = jedisCluster.mset("{" + prefix + KEY_SPLIT + "}" + "");
		System.out.println(result);

		String name = jedisCluster.get("{" + prefix + KEY_SPLIT + "}" + "name");
		System.out.println(name);

		Long del = jedisCluster.del("{" + prefix + KEY_SPLIT + "}" + "age");
		System.out.println(del);

		List<String> values = jedisCluster.mget("{" + prefix + KEY_SPLIT + "}" + "name");
		System.out.println(values);
	}

	public void msetnxTest() throws InterruptedException{

		Long msetnx = jedisCluster.msetnx(
			"{" + prefix + KEY_SPLIT + "}" + "name", "张三",
			"{" + prefix + KEY_SPLIT + "}" + "age", "23",
			"{" + prefix + KEY_SPLIT + "}" + "address", "adfsa",
			"{" + prefix + KEY_SPLIT + "}" + "score", "100");
		System.out.println(msetnx);

		System.out.println(jedisCluster.mget(
			"{" + prefix + KEY_SPLIT + "}" + "name",
			"{" + prefix + KEY_SPLIT + "}" + "age",
			"{" + prefix + KEY_SPLIT + "}" + "address",
			"{" + prefix + KEY_SPLIT + "}" + "score")); //[张三，23，adfsa，100]

		msetnx = jedisCluster.msetnx(
			"{" + prefix + KEY_SPLIT + "}" + "phone", "110",
			"{" + prefix + KEY_SPLIT + "}" + "name", "张三",
			"{" + prefix + KEY_SPLIT + "}" + "abc", "asdfasfdsa");
		System.out.println(msetnx);

		System.out.println(jedisCluster.mget(
			"{" + prefix + KEY_SPLIT + "}" + "name",
			"{" + prefix + KEY_SPLIT + "}" + "age",
			"{" + prefix + KEY_SPLIT + "}" + "address",
			"{" + prefix + KEY_SPLIT + "}" + "score",
			"{" + prefix + KEY_SPLIT + "}" + "phone",
			"{" + prefix + KEY_SPLIT + "}" + "abc")); //[张三, 23, adfsa, 100, null, null]
	}

	public void getsetTest() throws InterruptedException{
		System.out.println(jedisCluster.set(nameKey, "zhangsan"));
		System.out.println(jedisCluster.get(nameKey));
		System.out.println(jedisCluster.getSet(nameKey, "lisi"));
		System.out.println(jedisCluster.get(nameKey));
	}

	public void appendTest() throws InterruptedException{
		System.out.println(jedisCluster.append(nameKey, "aa"));
		System.out.println(jedisCluster.get(nameKey));
		System.out.println(jedisCluster.append(nameKey, "lisi"));
		System.out.println(jedisCluster.get(nameKey));
	}

	public void incrTest() throws InterruptedException{

		jedisCluster.del("incrNum");
		final AtomicInteger atomicInteger = new AtomicInteger(0);
		final CountDownLatch countDownLatch = new CountDownLatch(10);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i = 0; i < 10; i++){
			executorService.submit(new Runnable(){
				@Override
				public void run(){
					//每个线程增加1000次，每次加1
					for(int j = 0; j < 1000; j++){
						atomicInteger.incrementAndGet();
						jedisCluster.incr("incrNum");
					}

					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();
		System.out.println(jedisCluster.get("incrNum"));
		System.out.println(atomicInteger);
	}

	public void hashTest() throws InterruptedException{
		String hashKey = "hashKey";
		jedisCluster.del(hashKey);

		System.out.println(jedisCluster.hset(hashKey, "program_zhangsan", "111"));
		System.out.println(jedisCluster.hexists(hashKey, "program_zhangsan"));
		System.out.println(jedisCluster.hset(hashKey, "program_zhangsan", "222"));

		System.out.println(jedisCluster.hset(hashKey, "program_wangwu", "333"));
		System.out.println(jedsCluster.hset(hashKey, "program_lisi", "444"));

		System.out.println("hkeys:" + jedisCluster.hkeys(hashKey));

		System.out.println(jedisCluster.hgetAll(hashKey));
		System.out.println(jedisCluster.hincrBy(hashKey, "program_zhangsan", 2));
		System.out.println(jedisCluster.hmget(hashKey, "program_zhangsan", "program_lisi"));

		jedisCluster.hdel(hashKey, "program_wangwu");
		System.out.println(jedisCluster.hgetAll(hashKey));

		System.out.println("hsetnx:" + jedisCluster.hsetnx(hashKey, "program_lisi", "666"));

		System.out.println("hvals:" + jedisCluster.hvals(hashKey));

		System.out.println("expire:" + jedisCluster.expire(hashKey, 3));

		for(int i = 0; i < 5; i++){
			System.out.println(jedisCluster.hgetAll(hashKey));
			Thread.sleep(1000);
		}
	}

	public void queue() throws InterruptedException{
		String keyi = prefix + KEY_SPLIT + "queue";
		jedisCluster.del(key);

		System.out.println(jedisCluster.lpush(key, "1", "2", "3"));
		System.out.println(jedisCluster.lpush(key, "4"));
		System.out.println(jedisCluster.lpush(key, "5"));
		System.out.println("lrange:" + jedisCluster.lrange(key, 0, -1));

		System.out.println("lindex[2]:" + jedisCluster.lindex(key, 2));
		System.out.println("linsert:" + jedisCluster.linsert(key, Client.LIST_POSITION.BEFORE, "3", "100")); //在"3"前面插入"100"
		System.out.println("lrange:" + jedisCluster.lrange(key, 0, -1));

		for(int i = 0; i < 6; i++){ //写进去的顺序是12345, 读取出来的也是12345
			System.out.println(jedisCluster.rpop(key));
		}
		// 如果想达到生产者消费者哪种模式，需要使用阻塞式队列才可以。这个另外写多个客户端测试。
	}

	public void setTest() throws InterruptedException{
		String keyA = "{" + prefix + KEY_SPLIT + "set}a";
		String keyB = "{" + prefix + KEY_SPLIT + "set}b";
		jedisCluster.del(keyA);
		jedisCluster.del(keyB);

		System.out.println(jedisCluster.sadd(keyA, "a", "b", "c")); //给集合添加数据
		System.out.println(jedisCluster.sadd(keyA, "a")); // 给集合添加数据，集合是不可以重复的
		System.out.println(jedisCluster.sadd(keyA, "d")); // 给集合添加数据
		System.out.println(jedisCluster.smembers(keyA)); // 返回集合所有数据
		System.out.println(jedisCluster.scard(keyA)); //返回集合长度
		System.out.println("c是否在集合A中: " + jedisCluster.sismember(keyA, "c")); // 判断member元素是否是集合key的成员。

		System.out.println(jedisCluster.srandmember(keyA)); // 返回集合中的一个随机元素
		System.out.println(jedisCluster.spop(keyA)); // 移除并返回集合中的一个随机元素。
		System.out.println(jedisCluster.smembers(keyA)); // 返回集合所有数据
		System.out.println("-------------------");

		System.out.println(jedisCluster.smove(keyA, keyB, "a"));    // 返回集合所有数据
		System.out.println("keyA: " + jedisCluster.smembers(keyA)); // 返回集合所有数据
		System.out.println("keyB: " + jedisCluster.smembers(keyB)); // 返回集合所有数据

		System.out.println(jedisCluster.sadd(keyB, "a", "f", "c")); // 给集合添加数据
		System.out.println(jedisCluster.sdiff(keyA, keyB)); // 差集 keyA-keyB
		System.out.println(jedisCluster.sinter(keyA, keyB));// 交集
		System.out.println(jedisCluster.sunion(keyA, keyB));// 并集
	}

	public void sortedSetTest() throws InterruptedException{
		String keyA = "{" + prefix + KEY_SPLIT + "sortedSet}a";
		String keyB = "{" + prefix + KEY_SPLIT + "sortedSet}b";
		String keyC = "{" + prefix + KEY_SPLIT + "sortedSet}c";

		jedisCluster.del(keyA);
		jedisCluster.del(keyB);

		System.out.println(jedisCluster.zadd(keyA, 10, "aa"));
		Map<String, Double> map = new HashMap<>();
		map.put("b", 8.0);
		map.put("c", 4.0);
		map.put("d", 6.0);
		System.out.println(jedisCluster.zadd(keyA, map));
		System.out.println(jedisCluster.zcard(keyA)); // 返回有序集key的数量
		System.out.println(jedisluster.zcount(keyA, 3, 8)); // 返回有序集key中score某个范围的数量
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1)); // 返回有序集key中，指定区间内的成员。按score从小到达
		System.out.println("zrevrange: " + jedisCluster.zrevrange(keyA, 0, -1)); // 返回有序集key中，指定区间内的成员。按score从大到小
		System.out.println("zrangeWithScores: " + jedisCluster.zrangeWithScores(keyA, 0, -1)); // 返回有序集key中，指定区间内的成员。按score从小到大，包含分值
		System.out.println("zscore: " + jedisCluster.zscore(keyA, "aa"));

		System.out.println("zrangeByScore: " + jedisCluster.zrangeByScore(keyA, 3, 8));
		System.out.println("zrank: " + jedisCluster.zrank(keyA, "c")); // 返回有序集key中成员member的排名。其中有序集成员按score值递增（从小到大）顺序排列。
		System.out.println("zrevrank: " + jedisCluster.zrevrank(keyA, "c")); // 返回有序集key中成员member的排名。其中有序集成员按score值递增(从大到小)顺序排列。
		System.out.println("zrem: " + jedisCluster.zrem(keyA, "c", "a")); // 移除有序集key中的一个或多个成员，不存在的成员将被忽略。
		System.out.println("zrane: " + jedisCluster.zrange(keyA, 0, -1));

		System.out.println("zremrangeByRank: " + jedisCluster.zremrangeByRank(keyA, 1, 2)); //按下标删除
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1));
		System.out.println("zremrangeByScore: " + jedisCluster.zremrangeByScore(keyA, 1, 3)); //按评分删除
		System.out.println("zrange: " + jedisCluster.zrange(keyA, 0, -1));

		System.out.println("---------------------");
		System.out.println(jedisCluster.zadd(keyB, map));
		System.out.println("zrange: " + jedisCluster.zrange(keyB, 0, -1));

		System.out.println("zunionstore: " + jedisCluster.zunionstore(keyC, keyA, keyB)); // 合并keyA和keyB并保存到keyC中
		System.out.println("zrange: " + jedisCluster.zrange(keyC, 0, -1));
		System.out.println("zinterstore: " + jedisCluster.zinterstore(keyC, keyA, keyB)); // 交集
		System.out.println("zrange: " + jedisCluster.zrange(keyC, 0, -1));
	}

	public void sort() throws InterruptedException{
		String key = prefix + KEY_SPLIT + "queue";
		jedisCluster.del(key);

		System.out.println(jedisCluster.lpush(key, "1", "5", "3", "20", "6"));
		System.out.println(jedisCluster.lrange(key, 0, -1));

		System.out.println(jedisCluster.sort(key));
		System.out.println(jedisCluster.lrange(key, 0, -1));
	}

	public void script() throws InterruptedException{
		Object eval = jedisCluster.eval("return {KEYS[1], ARGV[1], ARGV[2]}", 1, "lua", "key1", "dd");
		System.out.println(eval);

		System.out.println(jedisCluster.eval("return redis.call('set', KEYS[1], ARGV[1])", 1, "luaTest", "cv"));
		System.out.println(jedisCluster.get("luaTest"));

		String scriptLoad = jedisCluster.scriptLoad("return redis.call('get', KEYS[1])", "luaTest"); // 加载脚本
		System.out.println(scriptLoad); // 返回的SHA1校验和，后续可以直接使用这个进行操作。
		System.out.println(jedisCluster.scriptExists(scriptLoad, "luaTest")); // 检查是否存在

		System.out.println(jedisCluster.evalsha(scriptLoad, "luaTest")); // 执行lua脚本

		System.out.println(jedisCluster.scriptFlush("luaTest".getBytes())); // 删除KEY as上的所有lua脚本
		System.out.println(jedisCluster.scriptExists(scriptLoad, "luaTest"));
		System.out.println(jedisCluster.evalsha(scriptLoad, 1, "luaTest")); // 脚本已经删除，返回错误：NOSCRIPT No matching script. Please use EVAL.
	}

	public void scriptFuc() throws InterruptedException{
		String key = "luaTest";
		System.out.println(jedisCluster.del(key));
		System.out.println(jedisCluster.sadd(key, "10", "3", "7", "40", "6"));
		System.out.println(jedisCluster.smembers(key)); // 这里怎么返回的值是有序的？[3, 6, 7, 10, 40]
		System.out.println(jedisCluster.eval("return redis.call('smembers', KEYS[1])", 1, key)); // 根据字母排序 [10, 3, 40, 6, 7]
	}

	public void redisLog() throws InterruptedException{
		System.out.println(jedisCluster.eval("redis.log(redis.LOG_WARNING, 'Something is wrong with this script.')", 1, "afd"));
	}

	public void queue1() throws InterruptedException{
		String key = prefix + KEY_SPLIT + "queue";
		Long del = jedisCluster.del(key);
		System.out.println(del);

		int length = 5;

		System.out.println(jedisCluster.lpush(key, "1", "2", "3", "4", "5", "6", "7"));

		List<String> list = jedisCluster.lrange(key, 0, -1); // 打印所有数据
		System.out.println(list);

		Long llen = jedisCluster.llen(key);
		System.out.println("目前队列长度: " + llen);

		System.out.println(jedisCluster.ltrim(key, 0, length -1));
		System.out.println(jedisCluster.lrange(key, 0, -1));
	}

	public void lock() throws InterruptedException{
		String key = prefix + KEY_SPLIT + "lock";

		System.out.println(jedisCluster.set(key, "本机ID", "nx", "ex", 3));

		for(int i = 0; i < 6; i++){
			System.out.println(jedisCluster.get(key));
			Thread.sleep(1000);
		}
	}

	public void after(){
		try{
			if(jedisCluster != null) jedisCluster.close();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}