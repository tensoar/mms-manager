package ink.labrador.mmsmanager.service;

import ink.labrador.mmsmanager.domain.LoggedSysUser;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.mapper.SysUserMapper;

public interface SysUserService extends BaseService<SysUser, SysUserMapper>{
    LoggedSysUser login(SysUser user);
}
