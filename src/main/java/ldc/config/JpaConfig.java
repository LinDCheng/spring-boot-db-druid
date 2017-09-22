package ldc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Jpa配置
 * Created by Administrator on 2017/9/22.
 */
@Order(Ordered.HIGHEST_PRECEDENCE) //设置配置加载等级为最高级 (项目启动就根据Jpa设置创建表)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ldc.repository")
@EntityScan(basePackages = "ldc.domain")
public class JpaConfig {
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {

        return new PersistenceExceptionTranslationPostProcessor();
    }
}
