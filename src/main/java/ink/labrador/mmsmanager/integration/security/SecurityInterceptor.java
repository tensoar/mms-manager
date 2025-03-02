package ink.labrador.mmsmanager.integration.security;

import ink.labrador.mmsmanager.constant.UserStatus;
import ink.labrador.mmsmanager.domain.LoggedUser;
import ink.labrador.mmsmanager.entity.User;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import ink.labrador.mmsmanager.integration.exception.BusinessException;
import ink.labrador.mmsmanager.mapper.UserMapper;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
@AllArgsConstructor
public class SecurityInterceptor implements AsyncHandlerInterceptor, Ordered {

    private final SecurityProperties securityProperties;
    private final UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }
        if (isMethodNoNeedAuth(method, request)) {
            return true;
        }

        String token = getToken(request);
        if (token == null) {
            throw new BusinessException("未登录");
        }
        LoggedUser user = SecurityHolder.get(token);
        if (user == null) {
            throw new BusinessException("Token非法或认证过期");
        }
        User dbUser = userMapper.selectById(user.getUserId());
        if (dbUser == null) {
            throw new BusinessException("用户不存在");
        }
        if (!UserStatus.NORMAL.getValue().equals(dbUser.getStatus())) {
            throw new BusinessException("用户状态异常,请联系管理员");
        }
        return true;
    }

    private boolean isMethodNoNeedAuth(HandlerMethod method, HttpServletRequest request) {
        if (method.hasMethodAnnotation(NotAuth.class)) {
            return true;
        }
        String curPath = request.getRequestURI();
        for (String url: securityProperties.getIgnoredPaths()) {
            if (url.equals(curPath) || PatternMatchUtils.simpleMatch(url, curPath)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(securityProperties.getTokenHeaderName());
        if (token == null || token.length() < securityProperties.getTokenPrefix().length()) {
            return null;
        }
        return token.substring(securityProperties.getTokenPrefix().length());
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
