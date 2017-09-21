package ldc.service;

import ldc.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用注解方式对简单对象进行缓存,要使用redisTemplate对复杂对象缓存查看userService
 * Created by Administrator on 2017/9/21.
 */
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
}
