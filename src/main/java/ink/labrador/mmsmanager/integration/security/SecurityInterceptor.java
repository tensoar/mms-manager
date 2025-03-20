package ink.labrador.mmsmanager.integration.security;

import ink.labrador.mmsmanager.constant.UserConst;
import ink.labrador.mmsmanager.domain.LoggedSysUser;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import ink.labrador.mmsmanager.integration.exception.BusinessException;
import ink.labrador.mmsmanager.mapper.SysUserMapper;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import ink.labrador.mmsmanager.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
@AllArgsConstructor
public class SecurityInterceptor implements AsyncHandlerInterceptor, Ordered {

    private final SecurityProperties securityProperties;
    private final SysUserMapper sysUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod method) || !BooleanUtils.isTrue(securityProperties.getEnableToken())) {
            return true;
        }
        if (isMethodNoNeedAuth(method, request)) {
            return true;
        }

        String token = WebUtil.getToken(request, securityProperties);
        if (token == null) {
            throw new BusinessException("未登录");
        }
        LoggedSysUser user = SecurityHolder.get(token);
        if (user == null) {
            throw new BusinessException("Token非法或认证过期");
        }
        SysUser dbUser = sysUserMapper.selectById(user.getUserId());
        if (dbUser == null) {
            throw new BusinessException("用户不存在");
        }
        if (dbUser.getStatus() != UserConst.UserStatus.NORMAL) {
            throw new BusinessException("用户状态异常,请联系管理员");
        }
        SecurityHolder.put(token, user);
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

    @Override
    public int getOrder() {
        return 1;
    }

}
