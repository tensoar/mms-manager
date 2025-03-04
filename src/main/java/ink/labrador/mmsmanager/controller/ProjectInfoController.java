package ink.labrador.mmsmanager.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ink.labrador.mmsmanager.controller.dto.ProjectInfoControllerDto;
import ink.labrador.mmsmanager.entity.ProjectInfo;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.service.ProjectInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController("project")
@AllArgsConstructor
public class ProjectInfoController {
    private final ProjectInfoService projectInfoService;

    @PostMapping("add_project")
    @Operation(description = "添加项目")
    public R addProject(ProjectInfoControllerDto.SaveProjectDto dto) {
        ProjectInfo info;
        if (dto.getId() != null) {
            info = projectInfoService.getOne(wrapper -> wrapper.eq(ProjectInfo::getId, dto.getId()));
            if (info == null) {
                return R.fail("项目不存在");
            }
        }
        ProjectInfo sameNamePJ = projectInfoService.getOne(wrapper -> wrapper.eq(ProjectInfo::getProjectName, dto.getProjectName()));
        if ((sameNamePJ != null && dto.getId() == null) || (dto.getId() != null && !Objects.requireNonNull(sameNamePJ).getId().equals(dto.getId()))) {
            return R.fail("项目名称已存在");
        }
        if (info == null) {
            info = new ProjectInfo();
            info.setProjectName(dto.getProjectName());
            info.setUseHttps(dto.getUseHttps());
            info.setIpv4(dto.get);
        }
        projectInfoService.saveOrUpdate()
    }
}
