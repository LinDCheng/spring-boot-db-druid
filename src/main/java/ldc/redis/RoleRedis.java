package ldc.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ldc.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ldc.repository.RoleRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Cacheable为存取缓存,CachePut为修改缓存,如果不存在缓存则创建,CacheEvict为删除缓存，删除数据时，如果缓存还存在，就必须删除
 * 注解中的value参数是一个key的前缀，并由keyGenerator按一定的规则生成一个唯一的标识
 * Created by Administrator on 2017/9/20.
 */
@Repository
public class RoleRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RoleRepository roleRepository;

//    @Cacheable(cacheNames = "mysql:findById:role",keyGenerator = "simpleKeyGenerator")
//    public Role findById(Long id) {
//        return roleRepository.findOne(id);
//    }
//
//    @CachePut(cacheNames = "mysql:findById:role",keyGenerator = "objectId")
//    public Role create(Role role) {
//        return roleRepository.save(role);
//    }
//
//    @CachePut(cacheNames = "mysql:findById:role",keyGenerator = "objectId")
//    public Role update(Role role) {
//        return roleRepository.save(role);
//    }
//
//    @CacheEvict(cacheNames = "mysql:findById:role",keyGenerator = "simpleKeyGenerator")
//    public void delete(Long id) {
//        roleRepository.delete(id);
//    }

    public void add(String key, Long time, List<Role> roles) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(roles), time, TimeUnit.MINUTES);
    }


    public List<Role> getList(String key) {
        Gson gson = new Gson();
        List<Role> ts = null;
        String listJson = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(listJson))
            ts = gson.fromJson(listJson, new TypeToken<List<Role>>(){}.getType());
        return ts;
    }

    public void delete(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
