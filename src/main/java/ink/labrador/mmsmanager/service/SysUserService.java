package ink.labrador.mmsmanager.service;

import ink.labrador.mmsmanager.domain.LoggedUser;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.mapper.SysUserMapper;

public interface SysUserService extends BaseService<SysUser, SysUserMapper>{
    LoggedUser login(SysUser user);
}
