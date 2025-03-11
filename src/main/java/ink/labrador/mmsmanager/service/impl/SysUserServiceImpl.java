package ink.labrador.mmsmanager.service.impl;

import ink.labrador.mmsmanager.constant.SaltMixType;
import ink.labrador.mmsmanager.constant.UserConst;
import ink.labrador.mmsmanager.domain.LoggedSysUser;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.integration.security.SecurityHolder;
import ink.labrador.mmsmanager.mapper.SysUserMapper;
import ink.labrador.mmsmanager.properties.SecurityProperties;
import ink.labrador.mmsmanager.service.SysUserService;
import ink.labrador.mmsmanager.util.digest.DigestSHA512;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserMapper> implements SysUserService {
    private final SecurityProperties securityProperties;
    private final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    public LoggedSysUser login(SysUser user) {
        String uuidToken = UUID.randomUUID().toString();
        LoggedSysUser loggedSysUser = new LoggedSysUser();
        loggedSysUser.setUserId(user.getId());
        loggedSysUser.setUsername(user.getName());
        loggedSysUser.setType(user.getType());
        loggedSysUser.setTokenHeaderName(securityProperties.getTokenHeaderName());
        loggedSysUser.setTokenValue(securityProperties.getTokenPrefix() + uuidToken);
        SecurityHolder.put(uuidToken, loggedSysUser);
        return loggedSysUser;
    }

    @PostConstruct
    public void initAdmin() {
        SysUser sysUser = this.getOne(wrapper -> wrapper.eq(SysUser::getName, "admin"));
        LocalDateTime now = LocalDateTime.now();
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setName("admin");
            sysUser.setStatus(UserConst.UserStatus.NORMAL);
            sysUser.setType(UserConst.UserType.SYS_ADMIN_USER);
            sysUser.setPasswordSalt(UUID.randomUUID().toString());
            sysUser.setPasswordDigest(DigestSHA512.digestAsHex(
                    securityProperties.getAdminDefaultPass(),
                    sysUser.getPasswordSalt(),
                    SaltMixType.CHAR_BY_CHAR
            ));
            sysUser.setCreateTime(now);
            sysUser.setUpdateTime(now);
            logger.info("Init sys admin user ...");
            this.save(sysUser);
        }
        if (!DigestSHA512.verify(
                securityProperties.getAdminDefaultPass(),
                sysUser.getPasswordSalt(),
                SaltMixType.CHAR_BY_CHAR,
                sysUser.getPasswordDigest()
        )) {
            logger.warn("Default sys admin pass changed, update ...");
            sysUser.setUpdateTime(now);
            sysUser.setPasswordDigest(DigestSHA512.digestAsHex(
                    securityProperties.getAdminDefaultPass(),
                    sysUser.getPasswordSalt(),
                    SaltMixType.CHAR_BY_CHAR
            ));
            this.saveOrUpdate(sysUser);
        }
    }
}
