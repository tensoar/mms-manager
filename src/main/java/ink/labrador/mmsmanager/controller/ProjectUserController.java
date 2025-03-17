package ink.labrador.mmsmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.labrador.mmsmanager.constant.SaltMixType;
import ink.labrador.mmsmanager.controller.dto.ProjectUserControllerDto;
import ink.labrador.mmsmanager.domain.LoggedProjectUser;
import ink.labrador.mmsmanager.entity.ProjectInfo;
import ink.labrador.mmsmanager.entity.ProjectUser;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import ink.labrador.mmsmanager.service.CaptchaService;
import ink.labrador.mmsmanager.service.ProjectInfoService;
import ink.labrador.mmsmanager.service.ProjectUserService;
import ink.labrador.mmsmanager.util.digest.DigestSHA512;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("project_user")
@AllArgsConstructor
@Tag(name = "项目用户相关接口")
public class ProjectUserController {
    private final ProjectUserService projectUserService;
    private final ProjectInfoService projectInfoService;
    private final CaptchaService captchaService;

    @PostMapping("save")
    @ResponseBody
    @Operation(summary = "保存项目用户", description = "新增或修改项目用户,用户类型默认给1即可,不在页面展示")
    public R<String> saveUser(@Dto ProjectUserControllerDto.SaveUserDto dto) {
        ProjectUser sameNameUser = projectUserService.getOne(wrapper ->
                wrapper.eq(ProjectUser::getName, dto.getUsername())
        );
        if (dto.getUserId() != null && dto.getUserId() > 0) {
            if (sameNameUser != null && !sameNameUser.getId().equals(dto.getUserId())) {
                return R.fail("用户名已存在");
            }
        } else if (sameNameUser != null) {
            return R.fail("用户名已存在");
        }
        if (!projectInfoService.exists(wrapper -> wrapper.eq(ProjectInfo::getId, dto.getProjectId()))) {
            return R.fail("项目不存在");
        }

        ProjectUser user;
        if (dto.getUserId() != null && dto.getUserId() > 0) {
            user = projectUserService.getById(dto.getUserId());
            if (user == null) {
                return R.fail("用户不存在");
            }
        } else {
            user = new ProjectUser();
            user.setCreateTime(LocalDateTime.now());
        }
        user.setProjectId(dto.getProjectId());
        user.setName(dto.getUsername());
        user.setType(dto.getUserType());
        user.setStatus(dto.getUserStatus());
        if (StringUtils.hasText(dto.getPassword())) {
            if (!DigestSHA512.verify(dto.getPassword(), user.getPasswordSalt(), SaltMixType.CHAR_BY_CHAR, user.getPasswordDigest())) {
                user.setPasswordSalt(UUID.randomUUID().toString());
                user.setPasswordDigest(DigestSHA512.digestAsHex(dto.getPassword(), user.getPasswordSalt(), SaltMixType.CHAR_BY_CHAR));
            }
        } else if (!StringUtils.hasLength(user.getPasswordDigest()) || dto.getUserId() == null) {
            return R.fail("密码不能为空");
        }
        user.setUpdateTime(LocalDateTime.now());
        projectUserService.saveOrUpdate(user);
        return R.ok("操作成功");
    }

    @DeleteMapping("del")
    @ResponseBody
    @Operation(summary = "删除项目用户")
    public R<String> delUser(@Dto ProjectUserControllerDto.DelUser dto) {
        projectUserService.removeByIds(dto.getIds());
        return R.ok("操作成功");
    }

    @GetMapping("list")
    @ResponseBody
    @Operation(summary = "查询项目用户")
    public R<Page<ProjectUser>> listUser(@Dto ProjectUserControllerDto.ListUserDto dto) {

        ProjectUser u = new ProjectUser();
        u.setName(dto.getUsername());
        u.setProjectId(dto.getProjectId());
        u.setStatus(dto.getUserStatus());
        return R.ok(projectUserService.getBaseMapper().listJoinProject(dto.mapPage(), u));
    }

    @PostMapping("login")
    @ResponseBody
    @Operation(summary = "项目用户登录")
    @NotAuth
    public R<LoggedProjectUser> login(@Dto ProjectUserControllerDto.Login dto) {
//        if (!captchaService.verify(dto.getCaptchaId(), dto.getCaptchaAnswer())) {
//            return R.fail("验证码错误或失效");
//        }
        ProjectUser user = projectUserService.getOne(wrapper -> wrapper.eq(ProjectUser::getName, dto.getUsername()));
        if (user == null) {
            return R.fail("用户名或密码错误");
        }
        if (!DigestSHA512.verify(dto.getPassword(), user.getPasswordSalt(), SaltMixType.CHAR_BY_CHAR, user.getPasswordDigest())) {
            return R.fail("用户名或密码错误");
        }
        if (user.getProjectId() == null) {
            return R.fail("未关联项目或项目被删除,请联系管理员");
        }
//        captchaService.remove(dto.getCaptchaId());
        ProjectInfo projectInfo = projectInfoService.getById(user.getProjectId());
        return R.ok(LoggedProjectUser.of(user, projectInfo));
    }
}
