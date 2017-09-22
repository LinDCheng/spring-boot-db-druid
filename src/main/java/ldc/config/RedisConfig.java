package ldc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用Spring Cache 注解方式使用redis缓存(只适合结构简单的对象,即没有包含其他对象的实体)
 * Created by Administrator on 2017/9/20.
 */
@Configuration
@EnableCaching //开启缓存
public class RedisConfig extends CachingConfigurerSupport{

    /**
     * 使用RedisTemple调用redis，用JSON格式作数据交换处理复杂对象
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 设置缓存失效时间
     * @param redisTemplate
     * @return
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager manager = new RedisCacheManager(redisTemplate);
        manager.setDefaultExpiration(43200);//设置失效时间为12小时
        return manager;
    }


    @Bean
    public KeyGenerator simpleKeyGenerator() {
       return new KeyGenerator() {
           @Override
           public Object generate(Object o, Method method, Object... objects) {
               StringBuilder builder = new StringBuilder();
               builder.append(o.getClass().getName().toString() + ":");
               for(Object object : objects) {
                    builder.append(object.toString());
               }
               return builder.toString();
           }
       };
    }

    @Bean
    public KeyGenerator objectId() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder builder = new StringBuilder();
                builder.append(o.getClass().getName().toString() + ":");
                try {
                    builder.append(objects[0].getClass().getMethod("getId", null).invoke(objects[0], null).toString());
                }catch (NoSuchMethodException no){
                    no.printStackTrace();
                }catch(IllegalAccessException il){
                    il.printStackTrace();
                }catch(InvocationTargetException iv){
                    iv.printStackTrace();
                }
                return builder.toString();
            }
        };
    }
}
