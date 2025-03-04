package ink.labrador.mmsmanager.service.impl;

import ink.labrador.mmsmanager.domain.LoggedUser;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.integration.security.SecurityHolder;
import ink.labrador.mmsmanager.mapper.SysUserMapper;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import ink.labrador.mmsmanager.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserMapper> implements SysUserService {
    private final SecurityProperties securityProperties;

    public LoggedUser login(SysUser user) {
        String uuidToken = UUID.randomUUID().toString();
        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setUserId(user.getId());
        loggedUser.setUsername(user.getName());
        loggedUser.setType(user.getType());
        loggedUser.setTokenName(securityProperties.getTokenHeaderName());
        loggedUser.setTokenValue(securityProperties.getTokenPrefix() + uuidToken);
        SecurityHolder.put(uuidToken, loggedUser);
        return loggedUser;
    }
}
