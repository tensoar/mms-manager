package ink.labrador.mmsmanager.integration.resolver;

import ink.labrador.mmsmanager.domain.LoggedSysUser;
import ink.labrador.mmsmanager.integration.security.SecurityHolder;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import ink.labrador.mmsmanager.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class InjectSysUserResolver implements HandlerMethodArgumentResolver {
    private final SecurityProperties securityProperties;
    public InjectSysUserResolver(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ink.labrador.mmsmanager.integration.annotation.InjectSysUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        String token = WebUtil.getToken(request, securityProperties);
        if (token == null) {
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "用户未登录");
        }
        LoggedSysUser sysUser = SecurityHolder.get(token);
        if (sysUser == null) {
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "用户状态无效");
        }
        return sysUser;
    }
}
