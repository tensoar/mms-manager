package ink.labrador.mmsmanager.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ink.labrador.mmsmanager.entity.SysUser;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import ink.labrador.mmsmanager.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys_user")
@AllArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;

    @NotAuth
    @PostMapping("login")
    public R login() {
//        sysUserService.getOne(SysUser.builder().id(1L).build());
        sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getId, 1L));
        return R.ok("login ...");
    }
}
