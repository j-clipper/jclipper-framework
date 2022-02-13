package jclipper.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import jclipper.mybatis.handler.InjectFieldMetaObjectHandler;
import jclipper.mybatis.plus.extend.MySqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JclipperMybatisPlusExtendConfiguration
 *
 * @see <a href="https://www.cnblogs.com/hinsy/p/9668684.html">Mybatis Plus 入坑(含最新3.X配置)</a>
 * @since 15:11
 */
@Configuration
@EnableTransactionManagement
public class JclipperMybatisPlusExtendConfiguration {
    @Bean
    public MetaObjectHandler injectFieldMetaObjectHandler() {
        return new InjectFieldMetaObjectHandler();
    }

    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }
}
