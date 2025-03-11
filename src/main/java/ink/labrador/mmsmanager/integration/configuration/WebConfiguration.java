package ink.labrador.mmsmanager.integration.configuration;

import ink.labrador.mmsmanager.integration.resolver.DtoArgumentResolver;
import ink.labrador.mmsmanager.integration.resolver.InjectSysUserResolver;
import ink.labrador.mmsmanager.integration.security.SecurityInterceptor;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final SecurityInterceptor securityInterceptor;
    private final SecurityProperties securityProperties;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new DtoArgumentResolver());
        resolvers.add(new InjectSysUserResolver(securityProperties));
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }
}
