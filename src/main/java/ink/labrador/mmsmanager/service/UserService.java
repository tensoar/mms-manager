package ink.labrador.mmsmanager.service;

import ink.labrador.mmsmanager.domain.LoggedUser;
import ink.labrador.mmsmanager.entity.User;
import ink.labrador.mmsmanager.mapper.UserMapper;

public interface UserService extends BaseService<User, UserMapper>{
    LoggedUser login(User user);
}
