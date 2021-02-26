package com.mall.jedis;

import com.mall.rest.component.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JedisTest {

	/**
	 * 单机版测试
	 */
    @Test
    public void testJedis() {
        // 创建一个Jedis对象
        Jedis jedis = new Jedis("192.168.25.133", 6379);
        // 设置连接密码
        jedis.set("single", "hello single redis");
        String str = jedis.get("single");
        System.out.println(str);
        jedis.close();
    }	
    /**
     **单机版一般使用连接池
     */
    @Test
    public void testJedisPool() {
        // 创建一个连接池对象 (系统中应该是单例的)
        JedisPool jedisPool = new JedisPool("192.168.25.133", 6379);
        Jedis jedis = jedisPool.getResource();

        String str = jedis.get("single");
        System.out.println(str);
        // jedis必须关闭，如果不关闭链接就会很快被用完。
        jedis.close();

        // 系统关闭时关闭连接池
        jedisPool.close();
    }
    
    
    //连接redis集群
    @Test
    public void testJedisCluster() throws IOException {
        // 创建节点集合
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.133", 7001));
        nodes.add(new HostAndPort("192.168.25.133", 7002));
        nodes.add(new HostAndPort("192.168.25.133", 7003));
        nodes.add(new HostAndPort("192.168.25.133", 7004));
        nodes.add(new HostAndPort("192.168.25.133", 7005));
        nodes.add(new HostAndPort("192.168.25.133", 7006));

        // 创建一个JedisCluster对象，在系统中是单例的。
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("cluster", "hello cluster redis");
        String str = jedisCluster.get("cluster");
        System.out.println(str);

        // 系统关闭时关闭jedisCluster
        jedisCluster.close();
    }
    
    
	@Test
	public void testJedisClientSpring() {//从spring容器获取Jedis，根据配置文件获取Jedis对象
		//创建一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		//从容器中获取对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		
		//jedisClient操作redis
		jedisClient.set("client1","1000");
		
		String string = jedisClient.get("client1");
		System.out.println(string);
	}
}
