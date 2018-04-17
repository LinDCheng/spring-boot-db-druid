package ldc.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Administrator on 2017/9/19.
 */

@Configuration
public class DruidConfig {

    /**
     * 配置监控服务器
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        //配置监控服务器后台地址为"/druid/*"
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        servletRegistrationBean.addInitParameter("allow","192.168.15.241,127.0.0.1");//IP白名单
        servletRegistrationBean.addInitParameter("deny","192.168.15.100");//IP黑名单
        servletRegistrationBean.addInitParameter("loginUsername","druid");//后台登录用户名
        servletRegistrationBean.addInitParameter("loginPassword","druid");//后台登录密码
        servletRegistrationBean.addInitParameter("resetEnable","false");//禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");//添加过滤规则
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.css,*.gif,*.jpg,*.png,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
