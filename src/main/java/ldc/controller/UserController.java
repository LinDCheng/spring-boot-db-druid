package ldc.controller;

import ldc.domain.User;
import ldc.service.RoleService;
import ldc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/9/21.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/{id}")
    public User findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user;
    }
}
