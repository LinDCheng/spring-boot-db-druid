package ldc.servlet;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * druid数据库过滤器注解配置方式
 * Created by Administrator on 2017/9/19.
 */
@WebFilter(filterName = "druidWebStatFilter",urlPatterns = "/*",
    initParams = {
        @WebInitParam(name = "exclusions",value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源
    })
public class DruidStatFilter extends WebStatFilter{
}
