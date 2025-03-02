package ink.labrador.mmsmanager.service.impl;

import ink.labrador.mmsmanager.domain.LoggedUser;
import ink.labrador.mmsmanager.entity.User;
import ink.labrador.mmsmanager.integration.security.SecurityHolder;
import ink.labrador.mmsmanager.mapper.UserMapper;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import ink.labrador.mmsmanager.service.UserService;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements UserService {
    private final SecurityProperties securityProperties;

    public LoggedUser login(User user) {
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
