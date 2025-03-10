package ink.labrador.mmsmanager.controller;

import ink.labrador.mmsmanager.constant.SaltMixType;
import ink.labrador.mmsmanager.constant.UserConst;
import ink.labrador.mmsmanager.controller.dto.ProjectUserControllerDto;
import ink.labrador.mmsmanager.entity.ProjectInfo;
import ink.labrador.mmsmanager.entity.ProjectUser;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.service.ProjectInfoService;
import ink.labrador.mmsmanager.service.ProjectUserService;
import ink.labrador.mmsmanager.util.digest.DigestSHA512;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("project_user")
@AllArgsConstructor
@Tag(name = "项目用户相关接口")
public class ProjectUserController {
    private final ProjectUserService projectUserService;
    private final ProjectInfoService projectInfoService;

    @PostMapping("save")
    @ResponseBody
    @Operation(summary = "保存项目用户", description = "新增或修改项目用户")
    public R<String> saveUser(@Dto ProjectUserControllerDto.SaveUserDto dto) {
        ProjectUser sameNameUser = projectUserService.getOne(wrapper ->
                wrapper.eq(ProjectUser::getName, dto.getUsername())
                        .eq(ProjectUser::getProjectId, dto.getProjectId())
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
        user.setUpdateTime(LocalDateTime.now());
        if (!StringUtils.hasLength(user.getPasswordDigest()) || !DigestSHA512.verify(dto.getPassword(), user.getPasswordSalt(), SaltMixType.CHAR_BY_CHAR, user.getPasswordDigest())) {
            user.setPasswordSalt(UUID.randomUUID().toString());
            user.setPasswordDigest(DigestSHA512.digestAsHex(dto.getPassword(), user.getPasswordSalt(), SaltMixType.CHAR_BY_CHAR));
        }
        user.setUpdateTime(LocalDateTime.now());
        projectUserService.saveOrUpdate(user);
        return R.ok("操作成功");
    }
}
