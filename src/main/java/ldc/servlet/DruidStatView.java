package ldc.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.Serializable;


/**
 * druid监控数据库注解方式配置
 * Created by Administrator on 2017/9/19.
 */
@WebServlet(urlPatterns = "/druid/*",
    initParams = {
            @WebInitParam(name = "allow",value = "127.0.0.1,192.168.15.241"),//IP白名单,没有配置或者为空,则允许所有访问
            @WebInitParam(name = "deny",value = "192.168.15.100"),//IP黑名单,(共同存在白名单和黑名单时,deny优先于allow)
            @WebInitParam(name = "loginUsername",value = "druid"),//用户名
            @WebInitParam(name = "loginPassword",value = "druid"),//密码
            @WebInitParam(name = "resetEnable",value = "false")//禁用页面上的重置功能
    })
public class DruidStatView extends StatViewServlet {
    private static final long serialVersionUID = -2688872071445249539L;
}
