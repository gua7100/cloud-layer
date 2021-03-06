package io.cloud.layer.config;

import io.cloud.layer.properties.ApiInfoProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * swagger核心配置文件
 * @see springfox.documentation.service.ApiInfo
 * @author RippleChan
 * @date 2019-02-27 01:52
 */
@Data
@ConfigurationProperties("swagger.config")
@EnableConfigurationProperties({ApiInfoProperties.class})
public class SwaggerCoreProperties {

    /**
     * ApiInfo 相关的配置
     */
    private ApiInfoProperties info = new ApiInfoProperties();


}
