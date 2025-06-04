package platform.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EnglishWay API")
                        .version("1.0")
                        .description("Документация API EnglishWay"));
    }

    @Bean
    public OpenApiCustomizer serverOpenApiCustomizer() {
        System.out.println("contextPath="+contextPath);
        return openApi -> openApi.setServers(List.of(new Server().url(contextPath)));
    }
}

