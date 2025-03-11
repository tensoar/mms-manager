package ink.labrador.mmsmanager.controller;

import ink.labrador.mmsmanager.constant.SaltMixType;
import ink.labrador.mmsmanager.controller.dto.SysUserControllerDto;
import ink.labrador.mmsmanager.domain.LoggedSysUser;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import ink.labrador.mmsmanager.integration.security.SecurityHolder;
import ink.labrador.mmsmanager.service.CaptchaService;
import ink.labrador.mmsmanager.service.SysUserService;
import ink.labrador.mmsmanager.util.digest.DigestSHA512;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys_user")
@AllArgsConstructor
@Tag(name = "系统用户相关接口")
public class SysUserController {
    private final SysUserService sysUserService;
    private final CaptchaService captchaService;

    @NotAuth
    @PostMapping("login")
    @Operation(description = "系统登录")
    public R<LoggedSysUser> login(@Dto SysUserControllerDto.Login dto) {
        if (!captchaService.verify(dto.getCaptchaId(), dto.getCaptchaAnswer())) {
            return R.fail("验证码错误或失效");
        }
        SysUser user = sysUserService.getOne(wrapper -> wrapper.eq(SysUser::getName, dto.getUsername()));
        if (user == null) {
            return R.fail("用户名或密码错误");
        }
        if (!DigestSHA512.verify(dto.getPassword(), user.getPasswordSalt(), SaltMixType.CHAR_BY_CHAR, user.getPasswordDigest())) {
            return R.fail("用户名或密码错误");
        }
        captchaService.remove(dto.getCaptchaId());
        return R.ok(sysUserService.login(user));
    }

    @PostMapping("logout")
    @Operation(description = "退出系统登录")
    public R<String> logout(@Parameter(hidden = true) LoggedSysUser sysUser) {
        SecurityHolder.remove(sysUser.getTokenValue());
        return R.ok("退出成功");
    }
}
