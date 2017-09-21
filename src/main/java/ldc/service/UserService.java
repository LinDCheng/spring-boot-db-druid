package ldc.service;

import ldc.domain.User;
import ldc.redis.UserRedis;
import ldc.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用redisTemplate方式作为User复杂对象缓存,要查看使用注解方式进行简单对象缓存查看roleService
 * 在使用原来mysql数据库的增删改查过程中，同时使用redis进行辅助缓存，达到提升访问速度的目的，缓解对原来数据库的访问压力
 * 访问一条数据时，首先从redis读取，如果存在则不再到Mysql中读取，如果不存在则从mysql读取，并将读取结果暂时保存在redis中
 * Created by Administrator on 2017/9/20.
 */
@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRedis userRedis;

    private static final String keyHead = "mysql:get:user";

    public User findById(Long id) {
        logger.info("开始根据id查询记录");
        User user = userRedis.get(keyHead + id);
        if(user == null) {
            user = userRepository.findOne(id);
            logger.info("从数据库中查询的记录");
            if(user != null)
                userRedis.add(keyHead + id,30L,user);
        }

        return user;
    }

    public User create(User user) {
        User newUser = userRepository.save(user);
        if(newUser != null)
            userRedis.add(keyHead + newUser.getId(),30L,newUser);
        return newUser;
    }

    public User update(User user) {
        if(user != null) {
            userRedis.delete(keyHead + user.getId());
            userRedis.add(keyHead + user.getId(),30L,user);
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRedis.delete(keyHead + id);
        userRepository.delete(id);

    }
}
